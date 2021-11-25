[home](../../README.md) ~ [shoring](../README.md)

# [flywheel](https://panopset.com/maven/flywheel/index.html)

Free form template processor.

## The Seven core Flywheel Commands

* __Template__  Templates calling other templates, all freeform.  Never define anything twice.

    ${@t relpath}
    
* __File__  This is a path relative to the output directory you specified in calling the Flywheel class.

    ${@f relpath}everything here gets put in the file.${@q}

* __Push__  The sublime push command, which defines a variable!  All the freeform templates, text, and other commands to follow until its matched quit command will be available as its defined variable to subsequent commands.

    ${@p foo}bar{@q}
    
* __List__  This is a path to a list, relative to the main template parent directory.  There is an example of using the list command [here](https://github.com/panopset/flywheel_tasks/blob/main/nginx/README.md).

    ${@l relpath}everything here gets processed for each line in the given file.${@q}

* __Quit__  Partners with the File, Push, and List commands to complete them, allowing for all the freeform creativity you can come up with.

    ${@q}  The template processor will add un-matched quit commands if necessary, but if a quit command appears with no matchable command preceding it, flywheel will output an error message and stop.

* __Execute__ This allows you to do anything you like.  You have to define your custom executables statically, before calling Flywheel:

    ReflectionInvoker.defineTemplateAllowedReflection("getVersion", "com.panopset.compat.AppVersion.getVersion");
    
*  The seventh flywheel command has no command designated by @, it is just like a Linux script variable.

    ${foo} will output bar, if defined as shown in the Push example above.
    
## A Debugging Flywheel Command

* __Break__ Is recognized only when running Flywheel as a desktop application.

    ${@b} Will set a breakpoint anywhere you like.

## Other Flywheel Commands

There are other Flywheel commands written long ago, but there probably isn't anything in them that can't be accomplished using the core commands.

You may, of course, write your own as well.  Please do a pull request if it solves an interesting problem in general, not already covered.
