#!/bin/bash
. ./scripts/init.sh
dir=$(pwd)
. ./scripts/publishStaticSite.sh $dir ${PSS}:/var/www/${PSD}
