#!/bin/bash
. ./bbl.sh
rsync -avuzh ~/beam.jar ${SITE_NAME}:/home/${SITE_USR}/beam.jar
rsync -avuzh temp/beam/prd/ ${SITE_NAME}:/home/${SITE_USR}/
