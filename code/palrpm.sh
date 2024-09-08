#!/bin/bash
. ./checkProps4LinuxRPM.sh
scp ./target/* $TGT_HTML/downloads/
scp ~/panopset.jar $TGT_HTML/downloads/$PLATFORM_KEY/
