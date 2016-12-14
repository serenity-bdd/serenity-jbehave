package net.serenitybdd.jbehave;

import com.ClassWithOnePackage;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class WhenDeterminingTheRootPathOfAPackage {

    static class SomeClass {}

    @Test
    public void should_find_the_parent_package_of_a_class() {
        Package somePackage = SomeClass.class.getPackage();

        assertThat(RootPackage.forPackage(somePackage)).isEqualTo("net.serenitybdd");
    }

    @Test
    public void should_find_the_parent_package_of_a_class_when_there_is_only_one_package() {
        Package somePackage = ClassWithOnePackage.class.getPackage();

        assertThat(RootPackage.forPackage(somePackage)).isEqualTo("com");
    }
}
