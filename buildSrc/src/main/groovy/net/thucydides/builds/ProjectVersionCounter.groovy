package net.thucydides.builds

class ProjectVersionCounter {
    final Boolean isRelease;

    ProjectVersionCounter(Boolean isRelease) {
        this.isRelease = isRelease
    }

    def getNextVersion() {
        def currentVersion = "git describe --tags".execute().text
        if (currentVersion.isEmpty()) {
            currentVersion = "v0.1.0"
        }
        def matcher = currentVersion =~ "\\d+"
        def majorMinorNumbers = matcher[0] + "." + matcher[1]
        def currentBuildNumber = Integer.valueOf(matcher[2])
        def nextBuildNumber = currentBuildNumber + 1
        return (isRelease) ?
                majorMinorNumbers + "." + nextBuildNumber :
                majorMinorNumbers + "." + nextBuildNumber + "-SNAPSHOT"
    }

    def tagNextVersion() {
        println "Creating a tag in git for $nextVersion"
        "git tag -a v$nextVersion -m'release tag'".execute().text
        "git push origin --tags".execute().text
    }
}
