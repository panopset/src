#!/bin/bash
echo "buildStaticSite.sh is just deploying from ${1}/html to localhost nginx content folder /var/www/html"
rm -rf /var/www/html/*
cp -r ${1}/html /var/www/

