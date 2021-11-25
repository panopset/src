#!/bin/bash
. ./scripts/init.sh
dir=$(pwd)
. ./scripts/publishDynamicSite.sh $dir ${PDS}:/home/${USER}/

