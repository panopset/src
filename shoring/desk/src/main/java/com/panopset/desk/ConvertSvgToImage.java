package com.panopset.desk;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import com.panopset.blackjackEngine.BlackjackCard;
import com.panopset.blackjackEngine.CardDefinition;
import com.panopset.marin.oldswpkg.games.blackjack.FxCardPainter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * 
 * Used this to create .png images from card .svg images, as
 * a temporary measure until a resolution to this is found
 * https://github.com/codecentric/javafxsvg/issues/7
 */
public class ConvertSvgToImage extends Application {

  private final String home = System.getProperty("user.home");
  private Stage stage;
  private WebView webView;
  private WebEngine webEngine;
  private Document doc;
  private List<String> cardIndexes = new ArrayList<>();

  private Sizer sizer = new Sizer("sm", 240, 336);
//  private Sizer sizer = new Sizer("lg", 480, 672); // Change the HTML here until the TODO below is done.

  public static void main(String... args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    cardIndexes.add("1B");
    cardIndexes.add("2B");
    for (CardDefinition cd : CardDefinition.values()) {
      cardIndexes.add(cd.getKey());
    }
    stage = primaryStage;
    primaryStage.setTitle("JavaFX WebView Example");
    if (Stringop.isEmpty(home)) {
      throw new RuntimeException();
    }
    webView = new WebView();
    webEngine = webView.getEngine();
    webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
      if (newState == State.SUCCEEDED) {
        setDocument();
      }
    });
    webView.setMinHeight(sizer.height);
    webView.setMinWidth(sizer.width);
    String htmlPath = ConvertSvgToImage.class.getResource("webview.html").toExternalForm();
    String cssPath = ConvertSvgToImage.class.getResource("webview.css").toExternalForm();
    webView.getEngine().setUserStyleSheetLocation(cssPath);
    webView.getEngine().load(htmlPath);
    VBox vBox = new VBox(webView);
    Scene scene = new Scene(vBox, sizer.width, sizer.width);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void setDocument() {
    doc = webEngine.getDocument();
    new Thread(() -> {
      goThroughSlides();
    }).start();
  }

  private void goThroughSlides() {
    for (String cardIndex : cardIndexes) {
      Platform.runLater(() -> {
        updateContents(cardIndex);
      });
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Logop.error(e);
        throw new RuntimeException(e);
      }
      Platform.runLater(() -> {
        snapshotToFile(cardIndex);
      });
    }
  }

  private void updateContents(String cardIndex) {
    // TODO swap the size here too.
    Element img = (Element) (doc.getElementsByTagName("img").item(0));
    img.getAttributes().getNamedItem("src")
        .setNodeValue(String.format("../marin/game/card/svg/poker-qr-plain/%s.svg", cardIndex));
  }

  private void snapshotToFile(String cardIndex) {
    File file = new File(String.format("%s/temp/%s/%s.png", home, sizer.name, cardIndex));
    WritableImage wim = webView.snapshot(null, null);
    try {
      ImageIO.write(colorToAlpha(SwingFXUtils.fromFXImage(wim, null), new Color(0xf5, 0x83, 0x87)),
          "png", file);
    } catch (Exception ex) {
      Logop.error(ex);
    }
  }

  public static BufferedImage colorToAlpha(BufferedImage sourceImage, Color origColor) {
    BufferedImage targetImage =
        new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());
    WritableRaster targetRaster = targetImage.getRaster();
    WritableRaster sourceRaster = sourceImage.getRaster();

    for (int row = 0; row < sourceImage.getHeight(); row++) {

      int[] rgba = new int[4 * sourceImage.getWidth()];
      int[] rgb = new int[4 * sourceImage.getWidth()];

      sourceRaster.getPixels(0, row, sourceImage.getWidth(), 1, rgb);

      for (int i = 0, j = 0; i < rgb.length; i += 4, j += 4) {
        if (origColor.equals(new Color(rgb[i], rgb[i + 1], rgb[i + 2]))) {
          rgba[j] = 0;
          rgba[j + 1] = 0;
          rgba[j + 2] = 0;
          rgba[j + 3] = 0;
        } else {
          rgba[j] = rgb[i];
          rgba[j + 1] = rgb[i + 1];
          rgba[j + 2] = rgb[i + 2];
          rgba[j + 3] = 255;
        }
      }
      targetRaster.setPixels(0, row, sourceImage.getWidth(), 1, rgba);
    }
    return targetImage;
  }


  public String getCardPath(BlackjackCard bc) {
    return new FxCardPainter().getCardPath(bc.getCard());
  }


  private static class Sizer {
    Sizer(String name, int width, int height) {
      this.name = name;
      this.width = width;
      this.height = height;
    }

    String name;
    int width;
    int height;
  }
}
