package com.panopset.fxapp;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import com.panopset.compat.AppVersion;
import com.panopset.compat.Fileop;
import com.panopset.compat.GlobalProperties;
import com.panopset.compat.GlobalPropertyKeys;
import com.panopset.compat.HiddenFolder;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import com.panopset.compat.Zombie;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class JavaFXapp extends Application implements Runnable, GlobalPropertyKeys {
  public static final String GLOBAL_LAST_PARENT_DIR = "com.panopset.global_last_parent_dir";

  private static JavaFXapp INSTANCE;

  public static Stage findStage() {
    return INSTANCE.findActiveStage();
  }

  private final Zombie zombie = new Zombie();

  public void setDefaultValue(String key, String value) {
    FxDoc fxDoc = (FxDoc) AnchorFactory.findAnchor();
    fxDoc.setDefaultValue(key, value);
  }

  private Stage findActiveStage() {
    FxDoc fxDoc = (FxDoc) AnchorFactory.findAnchor();
    if (fxDoc == null) {
      return null;
    }
    return fxDoc.getStage();
  }

  @Override
  public void start(Stage stage) {
    INSTANCE = this;
    HiddenFolder.setRootLogFileName(getPanApplication().getApplicationShortName());
    try {
      Logop.provideLogLocation();
      setUserAgentStylesheet(STYLESHEET_MODENA);
      launch(stage);
    } catch (ExceptionInInitializerError eiie) {
      Logop.dspmsg(getPanApplication().getApplicationDisplayName() + " Already running, exiting.");
      Platform.runLater(() -> {
        zombie.stop();
        stage.close();
      });
    }
  }

  @Override
  public void stop() {
    doExit();
  }

  private void launch(Stage stage) {
    String rawFiles = "";
    rawFiles = GlobalProperties.getGP(getPanApplication().getFilesKey());
    if (Stringop.isPopulated(rawFiles)) {
      String[] files = rawFiles.split(",");
      for (String rawPath : files) {
        try {
          String path = URLDecoder.decode(rawPath, Stringop.UTF_8);
          File file = new File(path);
          FxDoc fxDoc = new FxDoc(getPanApplication(), stage, file);
          show(fxDoc);
        } catch (UnsupportedEncodingException e) {
          Logop.error(e);
        }
      }
      GlobalProperties.putGP(getPanApplication().getFilesKey(), "");
    } else {
      addAndShow(stage);
    }
  }

  @Override
  public void run() {
    launch();
  }

  static AppDDSFX dds;

  private void addStage() {
    addAndShow(new Stage());
  }

  private void addAndShow(Stage stage) {
    show(new FxDoc(getPanApplication(), stage));
  }

  private void doSave(FxDoc fxDoc) {
    fxDoc.saveDataToFile();
    Logop.green("Saved to " + fxDoc.getFileName());
  }

  private void doSaveAs(FxDoc fxDoc) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save as...");
    String lpd = GlobalProperties.getGP(GLOBAL_LAST_PARENT_DIR);
    if (Stringop.isPopulated(lpd)) {
      fileChooser.setInitialDirectory(new File(lpd));
    }
    File file = fileChooser.showSaveDialog(findStage());
    if (file == null) {
      Logop.warn("New file not set.");
      return;
    }
    fxDoc.setFile(file);
    doSave(fxDoc);
    GlobalProperties.putGP(GLOBAL_LAST_PARENT_DIR, file.getParent());
  }

  private void doOpen() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open a previously saved " + getPanApplication().getApplicationShortName()
        + " properties file.");

    String lpd = GlobalProperties.getGP(GLOBAL_LAST_PARENT_DIR);
    if (Stringop.isPopulated(lpd)) {
      fileChooser.setInitialDirectory(new File(lpd));
    }
    File file = fileChooser.showOpenDialog(findStage());
    if (file == null) {
      Logop.warn("No file selected.");
      return;
    }
    if (!file.exists()) {
      Logop.warn("File " + Fileop.getCanonicalPath(file) + " does not exist.");
      return;
    }
    show(new FxDoc(getPanApplication(), new Stage(), file));
  }

  private void show(FxDoc fxDoc) {
    AnchorFactory.add(fxDoc);
    new StageManager().assembleAndShow(getPanApplication(), dds, fxDoc);
    fxDoc.setBoltValues();
  }

  public static void newWindow() {
    INSTANCE.addStage();
  }

  public static void showAboutPanel() {
    INSTANCE.doAbout();
  }

  public static void showLogPanel() {
    INSTANCE.doShowLog();
  }

  private void doAbout() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(String.format("About %s %s", getPanApplication().getCompanyName(),
        getPanApplication().getApplicationDisplayName()));
    alert.setHeaderText(getPanApplication().getDescription());
    alert.setContentText("Version " + AppVersion.getVersion());
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(dds.getFaviconImage());
    alert.showAndWait();
  }

  private Stage logStage;

  private Stage getLogStage() {
    if (logStage == null) {
      URL logSceneURL = getClass().getResource("/com/panopset/shared/LogScene.fxml");
      if (logSceneURL == null) {
        throw new NullPointerException(
            getClass().getCanonicalName() + " couldn't load LogScene.fxml");
      }
      FXMLLoader loader = new FXMLLoader(logSceneURL);
      try {
        Scene scene = new Scene(loader.load());
        logStage = new Stage();
        logStage.setTitle("Logs");
        logStage.setScene(scene);
        logStage.getIcons().add(dds.getFaviconImage());
        zombie.addStopAction(() -> {
          if (logStage != null) {
            logStage.close();
          }
        });
      } catch (IOException e) {
        Logop.error(e);
        throw new RuntimeException(e);
      }
    }
    return logStage;
  }

  private void doShowLog() {
    if (!getLogStage().isShowing()) {
      getLogStage().show();
      if (!getLogStage().isFocused()) {
        getLogStage().requestFocus();
        getLogStage().toFront();
      }
    }
  }

  public static void saveWindow(FxDoc fxDoc) {
    INSTANCE.doSave(fxDoc);
  }

  public static void saveWindowAs(FxDoc fxDoc) {
    INSTANCE.doSaveAs(fxDoc);
  }

  public static void openWindowFromFile() {
    INSTANCE.doOpen();
  }

  public static void panExit() {
    INSTANCE.doExit();
  }

  private synchronized void saveOpenWindowStateGlobally() {
    boolean firstTime = true;
    StringWriter sw = new StringWriter();
    for (Anchor anchor : AnchorFactory.getAnchors()) {
      try {
        String path = URLEncoder.encode(anchor.getPath(), Stringop.UTF_8);
        if (firstTime) {
          firstTime = false;
        } else {
          sw.append(",");
        }
        sw.append(path);
      } catch (UnsupportedEncodingException e) {
        Logop.error(e);
      }
    }
    GlobalProperties.putGP(getPanApplication().getFilesKey(), sw.toString());
    GlobalProperties.flush();
  }

  public static void closeWindow(FxDoc fxDoc) {
    int size = AnchorFactory.getAnchors().size();
    if (size == 1) {
      INSTANCE.doExit();
      return;
    }
    fxDoc.closeWindow();
    AnchorFactory.remove(fxDoc);
  }

  private void doExit() {
    zombie.stop();
    saveOpenWindowStateGlobally();
    for (Anchor anchor : AnchorFactory.getAnchors()) {
      ((FxDoc) anchor).closeWindow();
    }
  }

  public static PanApplication panApplication;

  private PanApplication getPanApplication() {
    return panApplication;
  }

  public static boolean isActive() {
    return JavaFXapp.INSTANCE.zombie.isActive();
  }

  public static Zombie getZombie() {
    return JavaFXapp.INSTANCE.zombie;
  }
}
