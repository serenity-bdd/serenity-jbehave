package net.serenitybdd.jbehave;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.thucydides.core.util.EnvironmentVariables;
import org.codehaus.plexus.util.StringUtils;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.serenitybdd.jbehave.SerenityJBehaveSystemProperties.JBEHAVE_STORY_PACKAGES;
import static net.serenitybdd.jbehave.SerenityJBehaveSystemProperties.STORY_DIRECTORY;

class StoryPathFinder {

    private final String storyNames;
    private final EnvironmentVariables environmentVariables;

    Set<String> identifiedStoryPaths = new HashSet<>();

    public StoryPathFinder(EnvironmentVariables environmentVariables, String storyNames) {
        this.environmentVariables = environmentVariables;
        this.storyNames = storyNames;
    }

    public Set<String> findAllElements() {
        List<String> rootStoryNames = rootStoryNamesFrom(storyNames);
        Set<String> storyPathElements = new HashSet<>();

        for(String rootStoryName : rootStoryNames) {
            rootStoryName = stripLeadingWildcards(rootStoryName);
            Set<String> newPathElements = new HashSet<>();

            Optional<URL> storyOnClasspath = storyOnClasspath(rootStoryName);

            if (storyOnClasspath.isPresent() && unidentified(storyOnClasspath.get())) {
                addPathElement(newPathElements, rootStoryName, storyOnClasspath.get());
            }

            for (String packagePath : getClasspathPackages()) {
                String storyFile = sanitizedPath(join(packagePath, rootStoryName));
                storyOnClasspath = storyOnClasspath(storyFile);
                if (storyOnClasspath.isPresent() && unidentified(storyOnClasspath.get())) {
                    addPathElement(newPathElements, storyFile, storyOnClasspath.get());
                }
            }

            if (newPathElements.isEmpty()) {
                storyPathElements.add(withWildcard(rootStoryName));
            } else {
                storyPathElements.addAll(newPathElements);
            }
        }
        return storyPathElements;
    }

    private void addPathElement(Set<String> storyPathElements, String storyName, URL storyPath) {
        storyPathElements.add(storyName);
        identifiedStoryPaths.add(storyPath.getFile().toLowerCase());
    }

    private String stripLeadingWildcards(String rootStoryName) {
        return rootStoryName.startsWith("**/") ? rootStoryName.substring(3) : rootStoryName;
    }

    private boolean unidentified(URL storyPath) {
        return !identifiedStoryPaths.contains(storyPath.getFile().toLowerCase());
    }

    private String sanitizedPath(String rawPath) {
        return rawPath.replace("//","/");
    }

    private String withWildcard(String resourceName) {
        return "**/" + resourceName;
    }

    private Optional<URL> storyOnClasspath(String storyFile) {
        return Optional.fromNullable(getClassLoader().getResource(storyFile));
    }


    private List<String> rootStoryNamesFrom(String storyNames) {
        return Lists.newArrayList(Splitter.on(";").trimResults().omitEmptyStrings().split(storyNames));
    }

    private String join(String packagePath, String storyName) {
        if (packagePath == null) {
            return storyName;
        } else if (packagePath.endsWith("/")) {
            return packagePath + storyName;
        } else {
            return packagePath + "/" + storyName;
        }
    }


    private List<String> getClasspathPackages() {

        String storyDirectory = environmentVariables.getProperty(STORY_DIRECTORY,"stories");
        List<String> packages = Lists.newArrayList("/",storyDirectory,"/" + storyDirectory);
        String storyPackages = environmentVariables.getProperty(JBEHAVE_STORY_PACKAGES.getName());
        if (StringUtils.isNotEmpty(storyPackages)) {
            packages.addAll(Lists.newArrayList(Splitter.on(";").trimResults().omitEmptyStrings().split(storyPackages)));
        }
        return packages;
    }

    private ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
