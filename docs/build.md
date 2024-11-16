[home](../README.md)

# Panopset Desktop Applications Build Manual

If you would like to simply download and run Panopset Desktop applications, they are available on the [downloads](https://panopset.com/downloads.html) page.

Those who wish to build the Panopset Desktop application from the source, you would run one of these scripts.
It will deploy:

* an all-in one panopset.jar to your home directory, and
* an application installer for your system in the target directory.

# Requirements

* [Java 23 JDK](https://adoptium.net/)
  * On all platforms, you may get the installer from [adoptium.net](https://adoptium.net/).


On a mac,

    brew install java

will give you Java 23. 
* [Maven 3.9.6](https://maven.apache.org/download.cgi)
* [git](https://git-scm.com/)


To clone this project:


    git clone https://github.com/panopset/src


To set up your git environment:


    git config --global user.email <email>
    git config --global user.name 'your name'

All build scripts are in the code folder:
    

    cd code


# Linux DEB

For Debian Linux systems:


    ./baldeb.sh


You can use [sdkman](https://sdkman.io/) to install the requirements.

# Linux RPM

For RedHat/Fedora Linux systems:

    ./balrpm.sh

Requirements may be installed with the yum command:

    sudo yum -y install java maven rpm-build vim gitk

Put this at the end of /etc/bashrc, and reboot:

    export JAVA_HOME=/etc/alternatives/jre


# Microsoft Windows

For Windows:

    baw.cmd

You can place a script in your home directory (%USERPROFILE%), to set your %PATH% to point
to the requirements. Here is what mine looks like:  [setenv.cmd](platforms/win/setenv.cmd). You would make adjustments
for your installation directories, unless you follow the same conventions. Keeping versions out of the path
makes your upgrades a much simpler process.


If you don't already have Windows developer tools installed, you'll also need [WiX](https://en.wikipedia.org/wiki/WiX), if you want the application installer (.msi file on Windows) to be built.

# Apple Macintosh

For Apple Macintosh:


    ./bam.sh


You can use [brew](https://brew.sh/) to install the requirements.