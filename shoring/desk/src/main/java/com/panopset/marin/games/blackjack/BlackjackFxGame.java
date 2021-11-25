package com.panopset.marin.games.blackjack;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SURRENDER;

import java.util.ResourceBundle;

import com.panopset.blackjackEngine.BlackjackGameEngine;
import com.panopset.fxapp.FontManagerFX;

import javafx.scene.control.Button;

public class BlackjackFxGame {
  private final ResourceBundle bundle;
  private final BlackjackGameEngine bge;
  private final BlackjackGameController bgc;

  public  BlackjackFxGame(ResourceBundle resourceBundle, BlackjackGameEngine bge,
    BlackjackGameController bgc) {
    this.bundle = resourceBundle;
    this.bge = bge;
    this.bgc = bgc;
  }

  public void updateControls() {
    bgc.gameControlFlow.getChildren().clear();
    bgc.gameControlFlow.getChildren().add(getDealButton());
    bgc.gameControlFlow.getChildren().add(getStandButton());
    bgc.gameControlFlow.getChildren().add(getHitButton());
    bgc.gameControlFlow.getChildren().add(getSplitButton());
    bgc.gameControlFlow.getChildren().add(getDoubleButton());
    bgc.gameControlFlow.getChildren().add(getSurrenderButton());
  }

  private Button splitButton;
  private Button getSplitButton() {
    if (splitButton == null) {
      splitButton = new Button(bundle.getString("cmd_split"));
      splitButton.setOnAction((event) ->
        bge.exec(CMD_SPLIT)
      );
      FontManagerFX.register(splitButton);
    }
    return splitButton;
  }


  private Button surrenderButton;
  private Button getSurrenderButton() {
    if (surrenderButton == null) {
      surrenderButton = new Button(bundle.getString("surrender"));
      surrenderButton.setOnAction((event) ->
        bge.exec(CMD_SURRENDER)
      );
      FontManagerFX.register(surrenderButton);
    }
    return surrenderButton;
  }


  private Button doubleButton;
  private Button getDoubleButton() {
    if (doubleButton == null) {
      doubleButton = new Button(bundle.getString("cmd_double"));
      doubleButton.setOnAction((event) ->
        bge.exec(CMD_DOUBLE)
      );
      FontManagerFX.register(doubleButton);
    }
    return doubleButton;
  }


  private Button dealButton;
  private Button getDealButton() {
    if (dealButton == null) {
      dealButton = new Button(bundle.getString("deal"));
      dealButton.setOnAction((event) ->
        bge.exec(CMD_DEAL)
      );
      FontManagerFX.register(dealButton);
    }
    return dealButton;
  }


  private Button hitButton;
  private Button getHitButton() {
    if (hitButton == null) {
      hitButton = new Button(bundle.getString("cmd_hit"));
      hitButton.setOnAction((event) ->
        bge.exec(CMD_HIT)
      );
      FontManagerFX.register(hitButton);
    }
    return hitButton;
  }


  private Button standButton;
  private Button getStandButton() {
    if (standButton == null) {
      standButton = new Button(bundle.getString("cmd_stand"));
      standButton.setOnAction((event) ->
        bge.exec(CMD_STAND)
      );
      FontManagerFX.register(standButton);
    }
    return standButton;
  }
}
