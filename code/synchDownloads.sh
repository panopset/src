#!/bin/bash
. ./checkProps4Linux.sh
rsync -avuzh --include "*/" --exclude="*" --include="*.json" $TGT_HTML/downloads ~/temp/
rsync -avuzh --include "*/" --exclude="*" --include="*.json" /var/www/html/downloads ~/temp/
