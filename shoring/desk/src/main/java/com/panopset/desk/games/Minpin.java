package com.panopset.desk.games;

import com.panopset.marin.fx.PanopsetBrandedApp;

import java.net.URL;

public class Minpin extends PanopsetBrandedApp {
    public static void main(String... args) {
        new Minpin().go();
    }

    @Override
    protected URL createPaneFXMLresourceURL() {
        return this.getClass().getResource("/com/panopset/marin/games/minpin/Minpin.fxml");
    }

    @Override
    public String getApplicationDisplayName() {
        return "Minpin";
    }

    @Override
    public String getDescription() {
        return "Minpin simulator";
    }
}
