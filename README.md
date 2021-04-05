# Build

## local

`gradle publishToMavenLocal` for local development

## Publish (MavenCentral)

Developer's notes on how to publish this artifact to Maven Central via Bash/PowerShell:

* Prepare the release
    * `gradle release` to do version tagging stuff
    * git checkout the new version tag
* Publish to Maven Central
    * Just in case: `./gradlew clean`
    * (this `uploadArchives` task uploads to Sonatype OSS/Maven Central; `installArchives` would publish to local Maven
      repository).
    * `SIGNING_SECRET_KEY_RING_FILE="/home/YOUR_USER/.gnupg/YOUR_GPG_KEY.gpg" SIGNING_PASSWORD="YOUR_GPG_PASSWORD" SIGNING_KEY_ID="YOUR_KEY_ID"  SONATYPE_NEXUS_USERNAME="YOUR_USER" SONATYPE_NEXUS_PASSWORD="YOUR_PASSWORD" ./gradlew uploadArchives --no-daemon --no-parallel`
    * Go to https://s01.oss.sonatype.org -> "Staging Repositories"
        * "Close"
        * "Release"
        * TODO: try https://github.com/vanniktech/gradle-maven-publish-plugin#releasing

* On Maven Central:
    * -SNAPSHOT releases do not require signing.
    * Versions cannot be updated once uploaded. ("Close" will fail.)

### Ressources

* https://descry.eu/publishing-to-maven-central-repository-with-github-actions-gradle/
* https://getstream.io/blog/publishing-libraries-to-mavencentral-2021/
* https://proandroiddev.com/publishing-your-first-android-library-to-mavencentral-be2c51330b88
* https://jeroenmols.com/blog/2021/03/24/migrate-artifacts-mavencentral/