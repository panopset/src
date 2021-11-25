[![Panopset](../../static/images/panopset.png)](https://panopset.com) ~ [home](../../README.md) ~ [version history](../../vh.md)

## Build [Desktop](./desk.md) Applications

Build scripts are in the top level, src, folder of this project:

* [build.sh](../../build.sh) for Linux,
* [buildmac.sh](../../buildmac.sh) for Apple Macintosh, or
* [build.cmd](../../build.cmd) for Microsoft Windows systems.

If you are using a different OS, or are not on a 64-bit x86 cpu,
you may have to create a script, using
the others as examples, that best fits your PC.  

If the script completes successfully, you will find

* panopset.jar in your home directory.
    * See [panopset](../../shoring/panopset/README.md) project for details.
* An installer for your system in the src/target directory.
    * See [joist](../../shoring/joist/README.md) project for details.
