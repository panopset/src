#!/bin/bash
# Dry run for aldeb.sh, doesn't publish artifacts to server.

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

