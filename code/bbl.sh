#!/bin/bash
# Build Beam for Linux
. ./checkProps4Linux.sh
/opt/panopset/bin/fw ./slab/templates/beam/beamServiceDev.txt temp/beam/dev
/opt/panopset/bin/fw ./slab/templates/beam/beamServicePrd.txt temp/beam/prd
gradle -p beam clean build
cp beam/build/libs/beam.jar ~/
pushd temp/beam/dev
sed -i -e 's/prod/DEV/g' panopsetweb.service
. ./installservice.sh
popd
