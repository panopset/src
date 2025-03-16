[home](../README.md)

# Panopset Workstation Configuration Manual

## Requirements

* A [server](server.md) somewhere.

## Panopset dev.properties

You will need an external configuration file, to hold your deployment specifics.


Edit/verify your ~/Documents/panopset/dev.properties file:


    ./editDevProps.sh


Place the following in ~/Documents/panopset/dev.properties:



    SITE_FAS21=<fas21.com site deployment domain>
    SITE_DN=<panopset.com site deployment domain>
    SITE_NAME=<your .ssh/config alias, for the server hosting the above domains>
    SITE_USR=<your server user name>
    WKSN_USR=<your workstation user name>
    SITE_PWD=<your server password>
    WKSN_PWD=<your workstation password>
    REDS_USR=<Redis user>
    REDS_PWD=<Redis password>
    REDS_HST=<Redis server>
    REDS_PRT=<Redis port>




SITE_FAS21 is for the fas21.com [tutorial](../docs/tutorials/fas21/README.md), you may replace this with your own.



## ssh Configuration


If you don't have a .ssh folder yet:


    mkdir ~/.ssh
    chmod 700 ~/.ssh
    cd ~/.ssh


On any of your private key files:


    chmod 400 <your private key file>
    



Edit your ssh config file, on your PC


    ./vc.sh


Add the following lines, replacing anything in &lt;&gt; brackets, with your values:


    Host <your host name, as defined in $SITE_NAME>
    HostName <your host ip address>
    User root
    IdentityFile ~/.ssh/<your private key file>
    Host github
    HostName github.com
    User git
    IdentityFile ~/.ssh/<your private key file>


Next, [Server Deployment](./server.md)

