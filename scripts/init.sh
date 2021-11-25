#!/bin/bash

function doInit() {
 . ~/.com.panopset.deploy.properties
 if [ -z "${PSS}" ] || [ -z "${PDS}" ] || [ -z "${PSD}" ] || [ -z "${PDD}" ]
 then 
  echo "Please define PSS and PDS, as your .ssh/config static and dynamic deployment servers, and PSD and PDD as your static and dynamic domain names, in ~/com.panopset.deploy.properties"
  exit 0
 fi
 export initialized=true
}

if [[ -v initialized ]]
then
 echo "Already initialized, skipping."
else
 echo "doInit."
 doInit
fi

