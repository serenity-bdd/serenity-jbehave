package net.thucydides.jbehave;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Load classes from a given package.
 */
public class ClassFinder {

    public static ClassFinder loadClasses() {
        return new ClassFinder();
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     */
    public List<Class> fromPackage(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classResourcesOn(classLoader, path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private Enumeration<URL> classResourcesOn(ClassLoader classLoader, String path) {
        try {
            return classLoader.getResources(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not access class path at " + path, e);
        }
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     */
    private List<Class> findClasses(File directory, String packageName) {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class") && isNotAnInnerClass(file.getName())) {
                classes.add(correspondingClass(packageName, file));
            }
        }
        return classes;
    }

    private Class<?> correspondingClass(String packageName, File file) {
        try {
            return Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not find or access class for " + file, e);
        }
    }

    private boolean isNotAnInnerClass(String className) {
        return (!className.contains("$"));
    }

}
