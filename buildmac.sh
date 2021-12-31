. ./loadprops_mac.sh
rm -rf target
mvn -f shoring clean install
echo 'Linking, this will take a minute...'
. ./linkmac.sh
chgrp admin target/*.dmg
echo 'Deploying to ${HTTP_HOME}'
ant
