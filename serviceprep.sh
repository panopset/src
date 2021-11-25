. ./loadprops_linux.sh

java -cp ~/panopset.jar -DPAN_USR=${PAN_USR} com.panopset.flywheel.Flywheel ./arch/springbootservice.txt ./temp
scp ./temp/web.service $PDS:/home/$PAN_USR
chmod +x ./temp/*.sh
scp ./temp/installservice.sh $PDS:/home/$PAN_USR

# To be installed by: sudo systemctl enable web.service