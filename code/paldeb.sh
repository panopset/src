#!/bin/bash
. ./checkProps4Linux.sh
rsync -avuzh  ./target/* $TGT_HTML/downloads/
rsync -avuzh  ~/panopset.jar $TGT_HTML/downloads/$PLATFORM_KEY/
rsync -avuzh  ./legacy/target/*.json $TGT_HTML/downloads/
