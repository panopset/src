#!/bin/bash
. ./checkProps4LinuxRPM.sh
scp ./target/* $TGT_HTML/downloads/
scp ~/panopset.jar $TGT_HTML/downloads/$PLATFORM_KEY/
scp ./legacy/target/*.json $TGT_HTML/downloads/
