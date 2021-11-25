[home](../../README.md) ~ [shoring](../README.md) 
~ [desktop](../../docs/desk/desk.md) ~ [build](../../docs/desk/build.md)

# panopset

Creates a single jar with dependencies, called panopset.jar.

Because there are different JavaFX dependencies for various platforms,
it should be built on a system like the one it will be installed on.

If you just want panopset.jar, all you have to do is run

    mvn clean install


in the src/shoring directory.  Then look in src/shoring/paopset/target for
panopset-jar-with-dependencies.jar.  Rename that to panopset.jar
and put it in your home directory.  If you run the full build script, for 
your platform, it will rename/copy for you.

# Build Requirement

* A current [JDK](https://jdk.java.net/17/), installed on your system.




