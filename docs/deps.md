[home](../README.md) 

# Panopset Dependencies Manual

This section is for developers.

## [jquery](https://jquery.com/download/) v3.7.1

* Replace code/slab/raw/js/jquery.js with current version.
* code/shoring/pom.xml java.version
* code/legacy/pom.xml java.version
* code/beam/build.gradle languageVersion.
  * A Java upgrade would likely entail a new spring initializr [base](https://start.spring.io/) project for code/beam.

## Libraries

All libraries are open source.

Follow the instructions in the [Continuous Integration Manuel](ci.md) when upgrading.
