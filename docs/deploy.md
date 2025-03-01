[home](../README.md)

# Website Deployment Manual

This may be used to publish a mirror of panopset.com, or as a guide to build your own Kotlin JavaFX
[WORA](https://en.wikipedia.org/wiki/Write_once,_run_anywhere) desktop software publication website.

If you just want the application, see [Desktop Applications Build](build.md).

## Requirements

* [Workstation Configuration](workstation.md)
* [Desktop application build instructions](build.md).
* A [server](server.md) somewhere.


## Install Flywheel


If you do not have Panopset desktop application software installed, 
build it using the [Desktop Applications Build Manual](build.md).


Then, go into target and install it using the .deb file you see there.


## Create user:

Make sure your $user.home/Documents/panopset/dev.properties is correct, 

    ./editDevProps.sh

then:

    cd code
    ./s.sh

to verify the server is running.

    exit
    ./userprep.sh
    ./s.sh
    ./crtusr.sh
    exit


...back to your workstation again and update your


    ./vc.sh


config file again, replacing root with your username, as defined as 


    $SITE_USR


, in your 


    ~/Documents/panopset/dev.properties


file. Then:


    ./s.sh
    ./createDomain.sh


Once that is done, you need to edit your /etc/nginx/sites-available/(site) file,
and add the following before "location /beam/ {":


    add_header 'Cache-control' 'max-age="86400"';


## Beam API deployment


    exit
    ./deployBeam.sh
    ./s.sh
    chmod +x *.sh
    sudo su
    ./installservice.sh
    sudo reboot 0

To test locally, run with the DEV profile, to put it on port 8090:


    -Dspring.profiles.active=DEV
	

Use journalctl to look at systemd logs for panopsetweb.


    sudo journalctl --unit=panopsetweb
	sudo journalctl --vacuum-time=1h


## Platform specific deployment

To build and publishes the application, and generate the checksums, 
run the script for the intended platform.

On Debian based linux systems, it is "all linux (build scripts) deb":

    aldeb.sh

On RPM based linux systems, it is "all linux rpm":

    alrpm.sh

On a mac it is "all mac",


    am.sh


... and on Windows it is "all windows":


    aw.sh


# Multiple domains on one server


    ../p.sh


Update SITE_DN


    ./userprep.sh
    ./s.sh
    sudo su
    cd
    ./createDomain.sh



    

References


* https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-20-04
* https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-as-a-reverse-proxy-on-ubuntu-22-04



