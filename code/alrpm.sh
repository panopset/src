#!/bin/bash
# All RPM Linux
# See al.sh for DEB Linux, aw.cmd for Windows, and am.sh for Macinthosh.

echo dev.properties:
cat ${HOME}/Documents/panopset/dev.properties

git pull
. ./checkProps4LinuxRPM.sh
. ./balrpm.sh
. ./palrpm.sh

