package net.thucydides.jbehave;


import ch.lambdaj.function.convert.Converter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static ch.lambdaj.Lambda.convert;

/**
 * Load classes from a given package.
 */
public class ClassFinder {

    private final ClassLoader classLoader;

    public ClassFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public static ClassFinder loadClasses() {
        return new ClassFinder(getDefaultClassLoader());
    }

    public ClassFinder withClassLoader(ClassLoader classLoader) {
        return new ClassFinder(classLoader);
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     */
    public List<Class<?>> fromPackage(String packageName) {
        if (expectedAnnotations == null) {
            return allClassesInPackage(packageName);
        } else {
            return annotatedClassesInPackage(packageName);
        }
    }

    private List<Class<?>> allClassesInPackage(String packageName) {
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classResourcesOn(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = Lists.newArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }


    private List<Class<? extends Annotation>> expectedAnnotations;

    public ClassFinder annotatedWith(Class<? extends Annotation>... someAnnotations) {
        expectedAnnotations = ImmutableList.copyOf(someAnnotations);
        return this;
    }

    public List<Class<?>> annotatedClassesInPackage(String packageName) {

        Reflections reflections = new Reflections(packageName,
                new SubTypesScanner(),
                new TypeAnnotationsScanner(),
                new MethodAnnotationsScanner(),
                new ResourcesScanner(), getClassLoader());

        Set<Class<?>> matchingClasses = Sets.newHashSet();
        for (Class<? extends Annotation> expectedAnnotation : expectedAnnotations) {
            matchingClasses.addAll(reflections.getTypesAnnotatedWith(expectedAnnotation));
            matchingClasses.addAll(classesFrom(reflections.getMethodsAnnotatedWith(expectedAnnotation)));
        }
        return ImmutableList.copyOf(matchingClasses);

    }

    private Collection<Class<?>> classesFrom(Set<Method> annotatedMethods) {
        return convert(annotatedMethods, toDeclaringCasses());
    }

    private Converter<Method, Class<?>> toDeclaringCasses() {
        return new Converter<Method, Class<?>>() {

            public Class convert(Method from) {
                return from.getDeclaringClass();
            }
        };
    }

    private Enumeration<URL> classResourcesOn(String path) {
        try {
            return getClassLoader().getResources(path);
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
    private List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = Lists.newArrayList();
        if (isJar(directory)) {
            return classesFromJar(directory, packageName);
        }
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class") && isNotAnInnerClass(file.getName())) {
                    classes.add(correspondingClass(packageName, file));
                }
            }
        }
        return classes;
    }

    private List<Class<?>> classesFromJar(File directory, String packageName) {
        try {
            List<Class<?>> classes = Lists.newArrayList();
            String [] split = directory.getPath().split("!");
            URL jar = new URL(split[0]);
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null)
                if (entry.getName().endsWith(".class")) {
                    String className = classNameFor(entry);
                    if (className.startsWith(packageName) && isNotAnInnerClass(className)) {
                        classes.add(loadClassWithName(className));
                    }
                }
            return classes;
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not find or access class for " + directory, e);
        }
    }

    private static String classNameFor(ZipEntry entry) {
        return entry.getName().replaceAll("[$].*", "").replaceAll("[.]class", "").replace('/', '.');
    }

    private boolean isJar(File dir) {
        return dir.getPath().startsWith("file:") && dir.getPath().contains("!");
    }

    private Class<?> loadClassWithName(String className){
        try {
            return getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not find or access class for " + className, e);
        }
     }

    private Class<?> correspondingClass(String packageName, File file) {
        String fullyQualifiedClassName = packageName + '.' + simpleClassNameOf(file);
        return loadClassWithName(fullyQualifiedClassName);
    }

    private static ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    private String simpleClassNameOf(File file) {
        return file.getName().substring(0, file.getName().length() - 6);
    }

    private boolean isNotAnInnerClass(String className) {
        return (!className.contains("$"));
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
