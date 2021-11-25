package com.panopset.fxapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class JavaFXappLauncher implements AppDDSFX {

  protected abstract void initializeFxDoc(Task<Void> task, FXMLLoader loader);

  public void setDefaultValue(String key, String value) {
    getApp().setDefaultValue(key, value);
  }

  public void go() {
    new Thread(getApp()).start();
  }

  private JavaFXapp app;

  private JavaFXapp getApp() {
    if (app == null) {
      if (JavaFXapp.dds != null) {
        throw new IndexOutOfBoundsException("One JavaFX application per runtime allowed.");
      }
      JavaFXapp.dds = this;
      app = new JavaFXapp();
      JavaFXapp.panApplication = this;
    }
    return app;
  }

  protected abstract URL createPaneFXMLresourceURL();

  private URL url;

  protected final URL getPaneFXMLresource() {
    if (url == null) {
      url = createPaneFXMLresourceURL();
    }
    return url;
  }

  /**
   * Template only, use this as a model for createPane in the module that contains
   * the fxml resources.
   * @param fxDoc FxDoc.
   * @return Pane.
   */
  private final Pane createPaneTemplate(FxDoc fxDoc) {
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

  /**
   * Binds an FXML Object to a font manager and, if it contains user data, such as a TextField or
   * control, to its document.
   *
   * @param parentKey parent key, so that includes work too.
   * @param object FXML Object to bind.
   * @param fxDoc Document to bind user data to.
   */
  @SuppressWarnings("rawtypes")
  protected void bindFxml(String parentKey, Object object, FxDoc fxDoc) {
    String keyChain = "";
    FxmlBindDebug.updatePad();
    Node node = null;
    if (object instanceof Node) {
      node = ((Node) object);
      if (node.getId() != null) {
    	  FxmlBindDebug.padId(node.getId());
    	  FontManagerFX.register(node);
          keyChain = Stringop.isBlank(parentKey) ? node.getId() : String.format("%s_%s", parentKey, node.getId());
      }
    } else {
      Logop.debug(
          String.format("Can not bind %s.", object.getClass().getCanonicalName()));
      keyChain = parentKey;
    }
    FxmlBindDebug.logmsg(String.format("Binding: %s", object.getClass().getName()));
    if (object instanceof TextInputControl) {
      TextInputControl textInputControl = (TextInputControl) object;
      if (!FieldHelper.isPasswordField(textInputControl) && textInputControl.isEditable()) {
        fxDoc.registerTextInputControl(keyChain, textInputControl);
      }
      FontManagerFX.register((TextInputControl) object);
    } else if (object instanceof ChoiceBox) {
      fxDoc.registerChoiceBox(keyChain, (ChoiceBox) object);
      FontManagerFX.register((ChoiceBox) object);
    } else if (object instanceof CheckBox) {
      fxDoc.registerCheckBox(keyChain, (CheckBox) object);
      FontManagerFX.register((CheckBox) object);
    } else if (object instanceof ToggleButton) {
      fxDoc.registerToggleButton(keyChain, (ToggleButton) object);
      FontManagerFX.register((ToggleButton) object);
    } else if (object instanceof Button) {
      FontManagerFX.register((Button) object);
    } else if (object instanceof SplitPane) {
      SplitPane splitPane = (SplitPane) object;
      fxDoc.registerSplitPaneLocations(keyChain, splitPane);
      for (Node childNode : splitPane.getItems()) {
        bindFxml(keyChain, childNode, fxDoc);
      }
    } else if (object instanceof TitledPane) {
      TitledPane titledPane = (TitledPane) object;
      bindFxml(keyChain, titledPane.getContent(), fxDoc);
    } else if (object instanceof ScrollPane) {
      ScrollPane sp = (ScrollPane) object;
      bindFxml(keyChain, sp.getContent(), fxDoc);
    } else if (object instanceof Tab) {
      Tab tab = (Tab) object;
      FontManagerFX.registerTab(tab);
    } else if (object instanceof TabPane) {
      TabPane tabPane = (TabPane) object;
      fxDoc.registerTabSelected(keyChain, tabPane, fxDoc);
      for (Tab tab : tabPane.getTabs()) {
        bindFxml(keyChain, tab.getContent(), fxDoc);
      }
      FontManagerFX.registerTabPane(tabPane);
    } else if (object instanceof Pane) {
      Pane pane = (Pane) object;
      for (Node childNode : pane.getChildren()) {
        bindFxml(keyChain, childNode, fxDoc);
      }
    } else if (object instanceof Labeled) {
      FontManagerFX.register((Labeled) object);
    }
    FxmlBindDebug.reduceDepth();
  }

  private Image faviconImage;

  @Override
  public Image getFaviconImage() {
    if (faviconImage == null) {
      String logoName = "/logo16_" + this.getClass().getSimpleName().toLowerCase() + ".png";
      InputStream is = getClass().getResourceAsStream(logoName);
      if (is == null) {
        is = getClass().getResourceAsStream("/logo16.png");
      }
      if (is == null) {
        Logop.error("Could not find image " + logoName);
      } else {
        faviconImage = new Image(is);
      }
    }
    return faviconImage;
  }
}
