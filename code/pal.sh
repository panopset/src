#!/bin/bash
. ./checkProps4Linux.sh
#Linux only, send it to downloads for web publication.
export LTGT="/var/www/html/downloads/"
mkdir -p $LTGT/$PLATFORM_KEY
cp ./target/* $LTGT
cp ~/panopset.jar $LTGT/$PLATFORM_KEY/
