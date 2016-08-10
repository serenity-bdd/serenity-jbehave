package net.serenitybdd.jbehave;

import java.util.Arrays;

public class RootPackage {

    public static String forPackage(Package testPackage) {
        String[] elements = testPackage.getName().split("\\.");
        if (elements.length >= 1) {
            elements = Arrays.copyOfRange(elements, 0, elements.length - 1);
        }
        return concatElements(elements);
    }

    private static String concatElements(final String[] subpaths) {
        final StringBuilder builder = new StringBuilder();
        for (String path : subpaths) {
            builder.append(path).append(".");
        }
        return (builder.toString().isEmpty()) ? "" : builder.substring(0, builder.length() - 1);
    }
}
