#!/bin/bash
# Build and publish application for RPM Linux
# See al.sh for DEB Linux, aw.cmd for Windows, and am.sh for Macinthosh.

echo dev.properties:
cat ${HOME}/Documents/panopset/dev.properties

rm -rf target
mkdir target

git pull
. ./checkProps4LinuxRPM.sh

# Build Application RPM Linux.
. ./balrpm.sh

# Build checksum files for RPM Linux.
. ./bclrpm.sh

# Publish Application RPM Linux.
. ./palrpm.sh

