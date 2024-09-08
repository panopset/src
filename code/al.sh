#!/bin/bash
# All Linux
# See aw.cmd for Windows, and am.sh for Macinthosh.

echo dev.properties:
cat ${HOME}/Documents/panopset/dev.properties

git pull
. ./checkProps4Linux.sh
. ./bal.sh
. ./pal.sh
