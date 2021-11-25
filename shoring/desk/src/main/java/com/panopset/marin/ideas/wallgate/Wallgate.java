package com.panopset.marin.ideas.wallgate;

import com.panopset.marin.fx.PanopsetBrandedApp;

import java.net.URL;

public class Wallgate extends PanopsetBrandedApp {

    public static void main(String... args) {
        new Wallgate().go();
    }

    @Override
    protected final URL createPaneFXMLresourceURL() {
        return this.getClass().getResource("/com/panopset/marin/games/wallgate/Wallgate.fxml");
    }

    @Override
    public String getApplicationDisplayName() {
        return "Wallgate";
    }

    @Override
    public String getDescription() {
        return "Welcome to Wallgate";
    }
}
