#!/bin/bash
# Build application for DEB Linux.
mvn -f shoring clean install
mvn -f legacy clean install
. ./lldeb.sh

# Copy the all-in-one jar to the home directory.
cp legacy/target/panopset-jar-with-dependencies.jar ~/panopset.jar
