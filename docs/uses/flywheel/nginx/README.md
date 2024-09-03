# Flywheel script to create initial nginx config files

If you want to set up several web sites on a single server,
using [nginx server blocks](https://www.digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-20-04#step-5-%E2%80%93-setting-up-server-blocks-%28recommended%29),
you need to create nearly identical nginx configuration files for each site hosted on that server.

## Prerequisites

If you have installed the Panopset Desktop Application Suite for your platform,
there are no prerequisites.

If you are using panopset.jar, you need:

* Java 21 or better.
* [panopset.jar](https://panopset.com/download.html) in your home directory.

# Running this sample script

## Using the installed fw executable

### Linux

    /opt/panopset/bin/fw go.txt target

### Mac

    /Applications/panopset.app/Contents/MacOS/fw go.txt target

### Windows

    "c:\Program Files\panopset\fw.exe" go.txt target

## From the jar file, using your JVM

### Linux and Mac

    java -cp ~/panopset.jar fw go.txt target

### Windows

    java -cp %USERPROFILE%\panopset.jar fw go.txt target
    
# In General

Any time you are faced with a tedious task that requires typing the same thing over and over
again, consider trying Panopset Flywheel.  

There are many other template engines out there, 
but how many are [open source](https://github.com/panopset/home), extensible and freeform, with just seven easy to learn commands?
