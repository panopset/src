#!/bin/bash
echo "publishStaticSite.sh deploying from ${1}/html to ${2}"
rsync -avuzh ${1}/html ${2}

