[![Panopset](html/images/panopset.png)](https://panopset.com)


Quick ad idea, somebody on the phone:  


    "Thanks, just how did you get that report so quickly, some kind of template engine?"  
    
    ... pause...
    
    "Pancakes Flypaper?"
    
    ... pause...
    
    "Panopwho Flywhere?"
    
    que the new age dramatic music, blank screen, slowly filling with...
    
               *** Panopset Flywheel ***
    a multi-platform open source tedium killer.
    

# src

Source code for Panopset desktop applications, 
and the [panopset.com](https://panopset.com) website itself.

Should you make improvements that are
publicly available for any purpose, those improvements must be open source, 
under the [GNU General Public License Version 3](LICENSE) license.  Please
note the Panopset Additional Provisions, regarding the 
com.panopset.blackjackEngine Blackjack module.

# Overview

## JavaFX Desktop Applications

For installing Panopset JavaFX applications on your PC, please
start with the [Desktop Applications](docs/desk/desk.md) page.

## Site Mirror

If you would like to deploy a mirror of [panopset.com](https://panopset.com), 
you'll need a static and dynamic server, that may be hosted together or on separate servers.

These instructions assume that your primary development system is a Linux PC. There are Windows
and Macintosh scripts, however those are limited to creating and deploying the desktop
application artifacts particular to those platforms. If you wish to deploy a panopset
web site mirror, from a different platform, you will need to make the necessary platform
specific adjustments to this documentation.

* [Local workstation setup](docs/servers/local/README.md), applies to both setup methods.

These instructions are for hosting them separately:

* [Static server setup instructions](docs/servers/static/README.md).
* [Dynamic server setup instructions](docs/servers/dynamic/README.md).

These instructions are for hosting them together:

* [Single host server setup instructions](docs/servers/single/README.md).

**References**

* [Initial Server Setup with Ubuntu 20.04](https://www.digitalocean.com/community/tutorials/initial-server-setup-with-ubuntu-20-04).
* [How To Install Nginx on Ubuntu 20.04](https://www.digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-20-04).
* [How To Secure Nginx with Let\'s Encrypt on Ubuntu](https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-20-04)

The links above are considered as reference, 
these instructions make use the [digitalocean](https://digitalocean.com) 
'User data' feature, environment variables, scripts, 
and of course Panopset [Flywheel](https://panopset.com/flywheel), to eliminate some boilerplate typing.
## Android Applications

There is just one Panopset Android application, which is available
on the Google Play Store:  [Panopset Blackjack](https://play.google.com/store/apps/developer?id=Panopset).

The current minimum Android version is 5.0, Lollipop, API level 21.2, which represents well over 90% of devices.

# src Directory

* **arch**, scripts and templates used to deploy [panopset.com](https://panopset.com) to
a [digitalocean](https://digitalocean.com) server.
* **launchers**, JavaFX desktop application launcher specifications.
* **docs**, extra documentation.
* **[shoring](shoring/README.md)**, Java Maven parent project.
* **static**, content to be deployed directly to the [panopset.com](https://panopset.com)
static server nginx html directory.
* **templates**, Flywheel templates.
