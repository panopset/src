#!/bin/bash
. ./checkProps4LinuxDEB.sh
rsync -a --exclude={'*.jar','*.deb','*.rpm','*.dmg','*.msi'} --include="*.json" $TGT_HTML/downloads /var/www/html/
