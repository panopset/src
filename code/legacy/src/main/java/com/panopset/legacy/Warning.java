package com.panopset.legacy;

import com.panopset.compat.Cmd80;

public class Warning {
    public static void main(String... args) {
        new Warning().go();
    }

    private void go() {
        Cmd80 c = new Cmd80();
        c.line80();
        c.centerHeader("Panopset \"Legacy\" Method Launch Warning");
        c.line80();
        c.line80b();
        c.line80("Although Panopset considers one-jar files deprecated, panopset.jar is still");
        c.line80("provided as a convenience.");
        c.line80b();
        c.line80("That could change soon!");
        c.line80b();
        c.line80("Please consider installing Panopset applications as platform applications,");
        c.line80("see panopset.com/downloads.html.");
        c.line80b();
        c.line80("Because you are running this from a one-jar, you'll also get a JavaFX ");
        c.line80("unnamed module warning, which you may also ignore for now.");
        c.line80b();
        c.line80();
    }
}
