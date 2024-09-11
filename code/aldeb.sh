#!/bin/bash
# Build and publish application for DEB Linux
# See alrpm for RPM Linux, aw.cmd for Windows, and am.sh for Macinthosh.

echo dev.properties:
cat ${HOME}/Documents/panopset/dev.properties

rm -rf target
mkdir target

git pull
. ./checkProps4LinuxDEB.sh

# Build Application DEB Linux.
. ./baldeb.sh

# Build checksum files for DEB Linux.
. ./bcldeb.sh

# Publish Application DEB Linux
. ./paldeb.sh
