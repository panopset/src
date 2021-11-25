package com.panopset.sample;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import com.panopset.compat.HiddenFolder;
import com.panopset.compat.Logop;
import com.panopset.fxapp.FontManagerFX;
import com.panopset.fxapp.FxDoc;
import com.panopset.fxapp.JavaFXappLauncher;
import com.panopset.fxapp.PanApplication;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SampleApp extends JavaFXappLauncher {

  public static void main(String... args) {
    HiddenFolder.setHiddenFolderName("sample");
    new SampleApp().go();
  }

  @Override
  protected void initializeFxDoc(Task<Void> task, FXMLLoader loader) {
    Logop.info("Green log entry.");
    Logop.debug("Yellow log entry.");
    Logop.warn("Orange log entry.");
    Logop.error("Red log entry.");
  }

  @Override
  protected final URL createPaneFXMLresourceURL() {
    return this.getClass().getResource("/com/panopset/sample/SampleScene.fxml");
  }

  @Override
  public String getApplicationDisplayName() {
    return "Sample";
  }

  @Override
  public String getDescription() {
    return "Sample application.";
  }

  @Override
  public String getCompanyName() {
    return "ACME Anvils";
  }

  @Override
  public Pane createPane(FxDoc fxDoc) {
    Class<? extends PanApplication> baseClass = fxDoc.getApplication().getClass();
    String i8nBundle = baseClass.getPackage().getName() + ".bundles." + baseClass.getSimpleName();

    URL url = getPaneFXMLresource();
    
    FXMLLoader loader = new FXMLLoader(url);
    
    try {
      // To test other languages: Locale.setDefault(new Locale("ja"));
      ResourceBundle bundle = ResourceBundle.getBundle(i8nBundle, Locale.getDefault());
      loader.setResources(bundle);
    } catch (MissingResourceException mre) {
      Logop.debug(String.format("Language bundle not found: %s", mre.getMessage()));
    }
    
    try {
      Pane pane = loader.load();
      for (Entry<String, Object> entry : loader.getNamespace().entrySet()) {
        if (entry.getKey().equals("panMenuBar")) {
          for (Node node : ((HBox) entry.getValue()).getChildren()) {
            if ("menubar".equals(node.getId())) {
              MenuBar menuBar = (MenuBar) node;
              for (Menu menu : menuBar.getMenus()) {
                if ("fileMenu".equals(menu.getId())) {
                  menu.setUserData(fxDoc);
                }
              }
            }
          }
        }
        Object object = entry.getValue();
        if (object != null) {
          bindFxml("", object, fxDoc);
        }
      }
      Task<Void> task = new Task<Void>() {
        // https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx
        @Override
        public Void call() {
          initializeFxDoc(this, loader);
          FontManagerFX.init();
          return null;
        }
      };
      ProgressBar bar = new ProgressBar();
      bar.progressProperty().bind(task.progressProperty());
      new Thread(task, String.format("%s Launcher", getClass().getSimpleName())).start();
      return pane;
    } catch (IOException e) {
      e.printStackTrace();
      return new Pane();
    }
  }
}
