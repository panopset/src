if [ "$1" = "" ]; then
 echo 'Please specify a server, defined in ~/.com.panopset.deploy.properties'
 exit
fi

. ~/.com.panopset.deploy.properties

if [ "$PAN_USR" = "" ]; then
 echo 'Please define properties, see documentation in docs/servers/local/README.md.'
 exit
fi

# Create the adduser.sh command for your new server

java -cp ~/panopset.jar -DPAN_USR=${PAN_USR} -DPAN_PWD=${PAN_PWD} com.panopset.flywheel.Flywheel ./arch/crtusr.txt ./temp
chmod +x ./temp/*.sh
scp ./temp/crtusr.sh root@$1:/root/
