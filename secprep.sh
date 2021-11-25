. ./loadprops_linux.sh

echo server is $1
echo domain is $2
echo user is $PAN_USR

if [ "$1" = "" ]; then
 echo 'Please specify a server, as defined in ~/.ssh/config.'
 exit
fi

if [ "$2" = "" ]; then
 echo 'Please define the domain you are securing, ie panopset.com.'
 exit
fi

if [ "$PAN_USR" = "" ]; then
 echo 'Please define PAN_USR as an environment variable, usually "$USER", but change it if your target server user is different.'
 exit
fi

rm -rf temp
mkdir temp
java -cp ~/panopset.jar -DPAN_USR=${PAN_USR} -DPAN_DOMAIN=${2} com.panopset.flywheel.Flywheel ./arch/nginx.txt ./temp
scp ./temp/${2} $PSS:/home/$PAN_USR

chmod +x ./temp/*.sh
scp ./temp/dolink.sh $PSS:/home/$PAN_USR
scp ./temp/docert.sh $PSS:/home/$PAN_USR

