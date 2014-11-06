package net.thucydides.builds

class ProjectVersionCounter {
    final Boolean isRelease;

    ProjectVersionCounter(Boolean isRelease) {
        this.isRelease = isRelease
    }

    @Override
    String toString() {
        String fullVersion = "$major.$minor"
        if (build) {
            fullVersion += ".$build"
        } else {
            fullVersion += "-SNAPSHOT"
        }
        fullVersion
    }

    def getNextVersion() {
        def currentVersion = "git describe --exact-match --abbrev=0".execute().text
        if (currentVersion.isEmpty()) {
            currentVersion = "v0.1.0"
        }
        def matcher = currentVersion =~ "\\d+"
        def majorMinorNumbers = "v" + matcher[0] + "." + matcher[1]
        def currentBuildNumber = Integer.valueOf(matcher[2])
        def nextBuildNumber = currentBuildNumber + 1
        return (isRelease) ?
                majorMinorNumbers + "." + nextBuildNumber :
                majorMinorNumbers + "." + nextBuildNumber + "-SNAPSHOT"
    }

    def tagNextVersion() {
        println "git tag -a $nextVersion -m'release tag'".execute().text
        println "git push origin $nextVersion".execute().text
    }
}
