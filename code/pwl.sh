#!/bin/bash
. ./checkProps4LinuxDEB.sh
echo "publishing to $TGT_HTML"
rsync -avuzh /var/www/html/ $TGT_HTML/
