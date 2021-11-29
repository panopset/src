. ./loadprops_linux.sh
rm -rf ${HTTP_HOME}
mkdir ${HTTP_HOME}
rm -rf target
mvn -f shoring clean install
echo 'Linking, this will take a minute...'
. ./link.sh
cp target/*.deb ${HTTP_HOME}/installers/
echo "Deploying to ${HTTP_HOME}"
ant
