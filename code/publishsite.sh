#!/bin/bash

# Run all the full builds on their respective platforms, including this one, first.
echo dev.properties:
cat ${HOME}/Documents/panopset/dev.properties

git pull
. ./checkProps4Linux.sh

rm -rf /var/www/html/*
mkdir -p /var/www/html/dox/site
. ./clearJsons.sh
mvn -f shoring site

# Synch up the remote and local downloads to temp
. ./synchDownloads.sh

# Build Checksums Linux.
. ./bcl.sh

# Build Website Linux. We only need to build the website on the
# primary dev box, which is always Linux.
#   * Copies over the documentation from the prior step, and
#   * the raw images etc.
. ./bwl.sh

# Deploy Website Linux. Calls /opt/panopset/gs (generate site), which
# uses Flywheel to generate the web pages.
. ./dwl.sh

# Deploy the HATEOAS REST app.  Only from Linux here too.
. ./deployBeam.sh

# Publish Website Linux. rsync /var/www/html up to the server ${SITE_NAME}.
# Again, only on Linux do we do this.
. ./pwl.sh
