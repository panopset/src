package com.panopset.marin.games.blackjack;

import java.net.URL;
import java.util.ResourceBundle;
import com.panopset.marin.game.card.images.BlackjackImages;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class BlackjackController implements Initializable {

  @FXML
  BlackjackGameController blackjackGameController;

  @FXML
  BlackjackConfigController blackjackConfigController;

  @FXML
  BlackjackRulesController blackjackRulesController;

  @FXML
  BlackjackCountingController blackjackCountingController;

  @FXML
  BlackjackBasicController blackjackBasicController;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    BlackjackImages.getChipMap();
    BlackjackImages.getCardMap();
    Platform.runLater(() -> {
      FxmlGlue fxmlGlue = new FxmlGlue();
      fxmlGlue.bcr = this;
      fxmlGlue.bgc = blackjackGameController;

      fxmlGlue.bcf = blackjackConfigController;

      fxmlGlue.brc = blackjackConfigController.blackjackRulesController;
      fxmlGlue.bcc = blackjackConfigController.blackjackCountingController;
      fxmlGlue.bbc = blackjackConfigController.blackjackBasicController;

      blackjackGameController.setFxmlGlue(fxmlGlue);
    });
    // SvgImageLoaderFactory.install();
  }

}
