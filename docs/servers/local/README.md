[![Panopset](../../../html/images/panopset.png)](https://panopset.com) ~ [home](../../../README.md)

# Local Workstation Setup

## Required variables

Create, in your home directory, a file called .com.panopset.deploy.properties, with


    PAN_USR=<your .ssh/config defined user for the deployment host.>
    PAN_PWD=<ONLY needed for the initial server creation steps, not content deployment.>
    
    PSD=panopset.com
    PDD=panopset.com
    # As defined in .ssh/config:
    # PSS stands for Panopset Static Server.
    PSS=static
    # PDS stands for Panopset Dynamic Server.
    PDS=dynamic
    
Use the loadprops script for your platform (as shown below), to make them available in your terminal.

Leave PAN_STATIC_DOMAIN and/or PAN_DYNAMIC_DOMAIN blank if hosting on dev servers with no nginx server blocks.  In that case, content will be deployed to /var/www/html, on the static content server.

## Configuration


Before you run your build, update pan.static in the appropriate shoring/web/src/main/resources/ application properties file.
This tells the dynamic server where to find its static content.  If you are not going to define a domain, you'll have to update it
after your server is created.


## Build Script

To build and stage the desktop applications, and their json info files, run the build script for your platform:

* Mac


    ./buildmac.sh
    
    
* Windows


     build.cmd
     
     
* Linux


     ./build.sh
     


## Development System Requirements


* Setup git:



    git config --global user.name "Your Name"
    git config --global user.email "youremail@yourdomain.com"



* Install latest JDK, and adjust the JAVA_HOME environment variable to match.

