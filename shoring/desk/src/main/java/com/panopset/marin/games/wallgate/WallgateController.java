package com.panopset.marin.games.wallgate;

import java.net.URL;
import java.util.ResourceBundle;
import com.panopset.fxapp.ReflectorFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WallgateController implements Initializable {

    @FXML
    public Canvas yard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReflectorFX.bindBundle(this, resourceBundle);

        GraphicsContext gc = yard.getGraphicsContext2D();
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(0,0, 700,700);

    }
}
