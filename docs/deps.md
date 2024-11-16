[home](../README.md) 

# Panopset Dependencies Manual

## [jquery](https://jquery.com/download/) v3.7.1

* Replace code/slab/raw/js/jquery.js with current version.

## Java 23

Get the appropriate installer from [adoptium.net](https://adoptium.net/).

On a mac, 

    brew install java

will give you Java 23.


On Debian based Linux systems, extract the downloaded OpenJDK23U-jdk_x64_linux_hotspot_23.0.1_11.tar.gz, then:

    cd /usr/lib/jvm
    sudo mv ~/Downloads/jdk-23.0.1+11/ .
    sudo rm default-java
    sudo ln -s jdk-23.0.1+11 default-java





* code/shoring/pom.xml java.version
* code/legacy/pom.xml java.version
* code/beam/build.gradle languageVersion.
  * A Java upgrade would likely entail a new spring initializr [base](https://start.spring.io/) project for code/beam.

## Libraries

All libraries are open source.

Follow the instructions in the [Continuous Integration Manuel](ci.md) when upgrading.
