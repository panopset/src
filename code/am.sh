#!/bin/bash
# Build and publish application for Apple Macintosh.

echo dev.properties:
cat ${HOME}/Documents/panopset/dev.properties

git pull
. ./checkProps4Mac.sh

rm -rf target
mkdir target

# Build Application Macintosh.
. ./bam.sh

# Build checksum files for Macintosh.
. ./bcm.sh

# Publish Application Macintosh.
. ./pam.sh
