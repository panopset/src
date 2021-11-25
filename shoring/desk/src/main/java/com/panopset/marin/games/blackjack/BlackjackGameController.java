package com.panopset.marin.games.blackjack;

import java.net.URL;
import java.util.ResourceBundle;

import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.blackjackEngine.CycleSnapshot;
import com.panopset.fxapp.FontManagerFX;
import com.panopset.fxapp.ReflectorFX;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class BlackjackGameController implements Initializable {

  public void initialize(URL location, ResourceBundle resources) {
    ReflectorFX.bindBundle(this, resources);
    startPaintCycle();
  }

  private void handleKey(KeyEvent keyEvent) {
    if (bge == null) {
      return;
    }
    bge.exec(keyEvent.getText().toLowerCase());
  }

  private int fontSize = 0;
  private boolean gameStarted = false;
  private CycleSnapshot paintedSnapshot;
  private boolean dirty = true;
  private AnimationTimer timer;

  public AnimationTimer getTimer() {
    if (timer == null) {
      timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
          if (now - lastUpdate > 5000000L) {
            paintFelt();
            lastUpdate = now;
          }
        }
      };

    }
    return timer;
  }

  long lastUpdate = 0;

  private void startPaintCycle() {
    Platform.runLater(() -> {
      getTimer().start();
    });
  }

  private boolean isDirty() {
    if (!dirty) {
      if (bge == null) {
        return false;
      }
      if (bge.getCurrentSnapshot() == null) {
        return false;
      }
      if (paintedSnapshot != bge.getCurrentSnapshot()) {
        paintedSnapshot = bge.getCurrentSnapshot();
        dirty = true;
      }
      if (fontSize != FontManagerFX.getSize()) {
        fontSize = FontManagerFX.getSize();
        dirty = true;
      }
    }
    return dirty;
  }

  public void update() {
    dirty = true;
  }

  private CycleSnapshot paintFelt() {
    if (felt == null) {
      return null;
    }
    int layoutHeight = (int) felt.getParent().getLayoutBounds().getHeight();
    int layoutWidth = (int) felt.getParent().getLayoutBounds().getWidth();

    if (layoutHeight < 10 || layoutWidth < 10) {
      return null;
    }

    felt.setWidth(layoutWidth);
    felt.setHeight(layoutHeight);

    GraphicsContext g = felt.getGraphicsContext2D();


    if (fxmlGlue == null) {
      error(g, "fxmlGlue is null.");
      return null;
    }

    if (!fxmlGlue.isSet()) {
      g.setFill(Color.BLANCHEDALMOND);
      g.fillRect(0, 0, layoutWidth, layoutHeight);
      g.setFill(Color.DARKGREEN);
      g.fillText(fxmlGlue.toString(), 100, 100);
      return null;
    }

    if (getEngine() == null) {
      error(g, "bge is null.");
      return null;
    }

    if (!bge.isActive()) {
      g.setFill(Color.DARKRED);
      g.fillRect(0, 0, layoutWidth, layoutHeight);
      return null;
    }

    CycleSnapshot rtn = bge.getCurrentSnapshot();

    if (!gameStarted) {
      felt.getScene().setOnKeyPressed(this::handleKey);
      gameStarted = true;
    }

    if (!isDirty()) {
      return null;
    }
    getFeltPainter().draw(rtn, g, layoutWidth, layoutHeight);



    dirty = false;

    return rtn;
  }

  private void error(GraphicsContext g, String msg) {
    g.setFill(Color.DARKRED);
    g.fillRect(0, 0, 1000, 1000);
    g.setFill(Color.YELLOW);
    g.fillText(msg, 100, 100);
  }

  private FxmlGlue fxmlGlue;

  public void setFxmlGlue(FxmlGlue fxmlGlue) {
    this.fxmlGlue = fxmlGlue;
  }

  private FeltPainter fp;

  private FeltPainter getFeltPainter() {
    if (fp == null) {
      fp = new FeltPainter();
    }
    return fp;
  }

  @FXML
  public Canvas felt;

  @FXML
  public FlowPane gameControlFlow;

  private BlackjackGameEngine bge;

  public void setEngine(BlackjackGameEngine bge) {
    this.bge = bge;
  }

  private BlackjackGameEngine getEngine() {
    if (bge == null) {
      if (fxmlGlue.isSet()) {
        bge = new BlackjackGameEngineFactory().create(fxmlGlue);
        bge.getZombie().addStopAction(getTimer()::stop);
      }
    }
    return bge;
  }
}
