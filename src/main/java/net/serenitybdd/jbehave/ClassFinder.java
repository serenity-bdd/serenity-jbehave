package net.serenitybdd.jbehave;


import com.google.common.base.Optional;
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
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        try {
            String path = packageName.replace('.', '/');
            if (packageName.isEmpty()) {
                packageName = "/";
            }
            Enumeration<URL> resources = classResourcesOn(path);
            List<URI> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(resource.toURI());
            }
            List<Class<?>> classes = Lists.newArrayList();
            for (URI directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
            return classes;
        } catch (Exception e) {
            throw new RuntimeException("failed to find all classes in package [" + packageName + "]", e);
        }
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

        return annotatedMethods.stream()
                .map(Method::getDeclaringClass)
                .collect(Collectors.toList());
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
    private List<Class<?>> findClasses(URI directory, String packageName) {
        try {
            final String scheme = directory.getScheme();
            final String schemeSpecificPart = directory.getSchemeSpecificPart();

            if (scheme.equals("jar") && schemeSpecificPart.contains("!")) {
                return findClassesInJar(directory, packageName);
            } else if (scheme.equals("file")) {
                return findClassesInFileSystemDirectory(directory, packageName);
            }

            throw new IllegalArgumentException("cannot handle URI with scheme [" + scheme + "]");
        } catch (Exception e) {
            throw new RuntimeException(
                    "failed to find classes" +
                            "in directory=[" + directory + "], with packageName=[" + packageName + "]",
                    e
            );
        }

    }

    private List<Class<?>> findClassesInJar(URI jarDirectory, String packageName) throws IOException {
        final String schemeSpecificPart = jarDirectory.getSchemeSpecificPart();

        List<Class<?>> classes = Lists.newArrayList();
        String[] split = schemeSpecificPart.split("!");
        URL jar = new URL(split[0]);
        try (ZipInputStream zip = new ZipInputStream(jar.openStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().endsWith(".class")) {
                    String className = classNameFor(entry);
                    if (className.startsWith(packageName) && isNotAnInnerClass(className)) {
                        classes.addAll(loadClassWithName(className).asSet());
                    }
                }
            }
        }

        return classes;
    }

    private List<Class<?>> findClassesInFileSystemDirectory(URI jarDirectory, String packageName) {
        List<Class<?>> classes = Lists.newArrayList();

        File directory = new File(jarDirectory);

        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file.toURI(), packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class") && isNotAnInnerClass(file.getName())) {
                    classes.addAll(correspondingClass(packageName, file).asSet());
                }
            }
        }

        return classes;
    }

    private static String classNameFor(ZipEntry entry) {
        return entry.getName().replaceAll("[$].*", "").replaceAll("[.]class", "").replace('/', '.');
    }

    private Optional<? extends Class<?>> loadClassWithName(String className) {
        try {
            return Optional.of(getClassLoader().loadClass(className));
        } catch (ClassNotFoundException e) {
//            throw new IllegalArgumentException("Could not find or access class for " + className, e);
            return Optional.absent();
        } catch (NoClassDefFoundError noClassDefFoundError) {
            return Optional.absent();
        }
    }

    private Optional<? extends Class<?>> correspondingClass(String packageName, File file) {
        String fullyQualifiedClassName = packagePrefixFor(packageName) + simpleClassNameOf(file);
        return loadClassWithName(fullyQualifiedClassName);
    }

    private String packagePrefixFor(String packageName) {
        return (packageName.isEmpty() || packageName.equals("/")) ? "" : packageName + '.';
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
