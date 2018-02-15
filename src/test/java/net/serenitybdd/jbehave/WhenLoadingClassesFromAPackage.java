package net.serenitybdd.jbehave;

import org.jbehave.core.annotations.Given;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

public class WhenLoadingClassesFromAPackage {

    @Test
    public void shouldLoadAllClassesInAPackage() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("net.serenitybdd.jbehave.pages");
        assertThat(classes.size(), is(1));
        assertThat(classes.get(0).getName(), is("net.serenitybdd.jbehave.pages.StaticSitePage"));
    }

    @Test
    public void shouldNotCrashForARootPackage() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("net");
        assertThat(classes.size(), not(0));
    }

    @Test
    public void shouldLoadAllClassesInNestedPackages() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("net.serenitybdd.jbehave");
        assertThat(classes.size(), greaterThan(10));
    }

    @Test
    public void shouldLoadAllAnnotatedClassesInNestedPackages() {
        List<Class<?>> classes = ClassFinder.loadClasses().annotatedWith(Given.class).fromPackage("net.serenitybdd.jbehave");
        assertThat(classes.size(), greaterThan(10));
    }

    @Test
    public void shouldLoadNoClassesIfThePackageDoesNotExist() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("that.does.not.exist");
        assertThat(classes.size(), is(0));
    }


    @Test
    public void shouldNotLoadResourcesOnClasspath() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("stories");
        assertThat(classes.size(), is(0));
    }

    @Test
    public void shouldLoadClassesFromDependencies() {
        List<Class<?>> classes = ClassFinder.loadClasses().annotatedWith(Ignore.class).fromPackage("net.thucydides.jbehave");
        List<String> classnames = classes.stream().map(Class::getName).collect(Collectors.toList());
        assertThat(classnames, hasItem("net.thucydides.jbehave.SomeBoilerplateSteps"));
    }

    @Test
    public void  shouldLoadAllClassesInAGivenPackageFromAnotherModuleAndAllSubpackages() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("net.thucydides.jbehave");
        List<String> classnames = classes.stream().map(Class::getName).collect(Collectors.toList());
        assertThat(classnames, hasItem("net.thucydides.jbehave.SomeBoilerplateSteps"));
    }

    // enable testing from an IDE, where otherwise the classpath is setup to depend directly on .class files, without packaging to .jar
    @Test
    public void shouldLoadClassesInAGivenPackageFromADependencyJar() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("org.junit.runners");
        List<String> classnames = classes.stream().map(Class::getName).collect(Collectors.toList());
        assertThat(classnames, hasItem("org.junit.runners.JUnit4"));
    }

    // enable testing from an IDE, where otherwise the classpath is setup to depend directly on .class files, without packaging to .jar
    @Test
    public void shouldLoadNestedClassesInAGivenPackageFromADependencyJar() {
        List<Class<?>> classes = ClassFinder.loadClasses().fromPackage("org.junit.runners");
        List<String> classnames = classes.stream().map(Class::getName).collect(Collectors.toList());
        assertThat(classnames, hasItem("org.junit.runners.model.RunnerScheduler"));
    }

    // enable testing from an IDE, where otherwise the classpath is setup to depend directly on .class files, without packaging to .jar
    @Test
    public void shouldLoadAnnotatedClassesInAGivenPackageFromADependencyJar() {
        List<Class<?>> classes = net.thucydides.core.reflection.ClassFinder.loadClasses()
                                                                           .annotatedWith(Deprecated.class)
                                                                           .fromPackage("junit.framework");
        List<String> classnames = classes.stream().map(Class::getName).collect(Collectors.toList());
        assertThat(classnames, hasItem("junit.framework.Assert"));
    }
}
