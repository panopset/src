[home](../README.md)

# Panopset Continuous Integration Manual

* Update code/deploy.properties with the new version.
  * PV: new version.
  * RV: prior version.
* Update docs/vh.html.
* Verify SITE_DN is where you want to publish:

...


    ../p.sh


next:


    ./generateVersion.sh
    ../u.sh


Follow instructions in the comments of


    code/shoring/pom.xml
    


* To get started with changes for next revision, update dependency versions in deploy.properties, shoring/pom.xml, and beam/build.gradle.
* [javafx](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml) usually has a new version fairly frequently.

## Staging on one server

If you are hosting multiple domains on a single server, you can quickly deploy from one to the other.

Make the necessary adjustments to these scripts to fit your environment.


    ./copyServerScripts.sh
    ./s.sh
    stagePractice.sh
    stageLive.sh
    exit
    ./updateContent.sh



