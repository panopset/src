package com.panopset.fxapp;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.panopset.compat.Fileop;
import com.panopset.compat.GlobalPropertyKeys;
import com.panopset.compat.Logop;
import com.panopset.compat.PersistentMapFile;
import com.panopset.compat.Stringop;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;

public abstract class Anchor implements GlobalPropertyKeys {

  public Anchor(PanApplication anchorManager) {
    am = anchorManager;
  }

  public Anchor(PanApplication anchorManager, File file) {
    this(anchorManager);
    setPersistentMapFile(file);
  }

  public void setDefaultValue(String key, String value) {
    boltDefaults.put(key, value);
  }

  public String createWindowTitle() {
    String rtn = String.format("%s ~ %s", am.getApplicationDisplayName(), getPath());
    return rtn;
  }

  protected abstract void updateTitle();

  public void loadDataFromFile() {
    getPersistentMapFile().load();
  }

  public void setBoltValues() {
    for (Map.Entry<String, Bolt> e : bolts.entrySet()) {
      String value = "";
      try {
        value = getPersistentMapFile().get(e.getKey());
      } catch (IOException ex) {
        Logop.error(ex);
      }
      if (Stringop.isPopulated(value)) {
        e.getValue().setBoltValue(value);
      } else {
        e.getValue().setBoltValue(e.getValue().getBoltDefault());
      }
    }
    updateTitle();
  }

  public String getFileName() {
    return getPersistentMapFile().getFileName();
  }

  public void saveDataToFile() {
    for (Map.Entry<String, Bolt> e : bolts.entrySet()) {
      String key = e.getKey();
      String value = e.getValue().getBoltValue();
      if (!Stringop.isPopulated(value)) {
        value = e.getValue().getBoltDefault();
      }
      getPersistentMapFile().put(key, value);
    }
    getPersistentMapFile().flush();
  }

  public void setFile(File file) {
    getPersistentMapFile().setFile(file);
  }

  public static String KEY_WINDOW_XLOC = "X";
  public static String KEY_WINDOW_YLOC = "Y";
  public static String KEY_WINDOW_W = "W";
  public static String KEY_WINDOW_H = "H";

  private final Map<String, Bolt> bolts = new HashMap<>();
  private final Map<String, String> boltDefaults = new HashMap<>();

  private PersistentMapFile pmf;

  protected PersistentMapFile getPersistentMapFile() {
    if (pmf == null) {
      pmf = new PersistentMapFile(new File(getPath()));
    }
    return pmf;
  }

  private void setPersistentMapFile(File file) {
    pmf = new PersistentMapFile(file);
    path = Fileop.getCanonicalPath(file);
  }

  private String path;

  protected String getPath() {
    if (path == null) {
      path =
          Fileop
              .combinePaths(Fileop.getUserHome(),
                  String
                      .join(Stringop.FILE_SEP, "temp",
                          String.join(".", String.format("%s_Untitled%s",
                              am.getApplicationShortName(), Stringop.getNextJvmUniqueIDstr()),
                              "properties")));
      while (new File(path).exists()) {
        path = null;
        getPath();
      }
    }
    return path;
  }

  public Double getDouble(String key) {
    String val = "";
    try {
      val = getPersistentMapFile().get(key);
    } catch (IOException ex) {
      Logop.error(ex);
    }
    if (Stringop.isPopulated(val)) {
      try {
        Double rtn = Double.parseDouble(val);
        return rtn;
      } catch (NumberFormatException ex) {
        return null;
      }
    }
    return null;
  }

  public void put(String key, String value) throws IOException {
    if (key == null) {
      Logop.error("Null key");
      return;
    }
    if (getPersistentMapFile() == null) {
      Logop.error("Anchor not initialized properly.");
      return;
    }
    try {
      String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8.name());
      getPersistentMapFile().put(key, encodedValue);
    } catch (UnsupportedEncodingException ex) {
      Logop.error(ex);
      getPersistentMapFile().put(key, value);
    }
    getPersistentMapFile().put(key, value);
    Bolt bolt = bolts.get(key);
    if (bolt != null) {
      bolt.setBoltValue(value);
    }
  }

  public String get(String key, String defaultValue) throws IOException {
    String rtn = getPersistentMapFile().get(key);
    if (rtn == null) {
      String dft = boltDefaults.get(key);
      if (dft == null) {
        return defaultValue;
      }
      return dft;
    }
    try {
      rtn = URLDecoder.decode(rtn, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException ex) {
      Logop.error(ex);
      return rtn;
    }
    return rtn;
  }

  public String get(String key) throws IOException {
    return get(key, "");
  }

  public void registerChoiceBox(String keyChain, ChoiceBox<String> cb) {
    final String defaultValue = "" + cb.getSelectionModel().getSelectedIndex();
    registerData(keyChain, new Bolt() {

      @Override
      public String getBoltValue() {
        return "" + cb.getSelectionModel().getSelectedIndex();
      }

      @Override
      public String getBoltDefault() {
        return defaultValue;
      }

      @Override
      public void setBoltValue(String value) {
        int i = Stringop.parseInt(value, 10);
        if (i > -1) {
          cb.getSelectionModel().select(i);
        }
      }
    });
  }

  public void registerCheckBox(String keyChain, CheckBox cb) {
    final String defaultValue = "" + cb.isSelected();
    registerData(keyChain, new Bolt() {

      @Override
      public void setBoltValue(String value) {
        if (Stringop.isPopulated(value)) {
          boolean bv = Boolean.parseBoolean(value);
          cb.setSelected(bv);
        }
      }

      @Override
      public String getBoltValue() {
        return "" + cb.isSelected();
      }

      @Override
      public String getBoltDefault() {
        return defaultValue;
      }
    });
  }

  public void registerToggleButton(String keyChain, ToggleButton tb) {
    final String defaultValue = "" + tb.isSelected();
    registerData(keyChain, new Bolt() {

      @Override
      public void setBoltValue(String value) {
        if (Stringop.isPopulated(value)) {
          boolean bv = Boolean.parseBoolean(value);
          tb.setSelected(bv);
        }
      }

      @Override
      public String getBoltValue() {
        return "" + tb.isSelected();
      }

      @Override
      public String getBoltDefault() {
        return defaultValue;
      }
    });
  }

  public void registerTextInputControl(String keyChain, TextInputControl tf) {
    if (FieldHelper.isPasswordField(tf)) {
      return;
    }
    final String currentTextValue = tf.getText();
    registerData(keyChain, new Bolt() {

      @Override
      public void setBoltValue(String value) {
        Platform.runLater(() -> tf.setText(value));
      }

      @Override
      public String getBoltValue() {
        return tf.getText();
      }

      @Override
      public String getBoltDefault() {
        String dft = boltDefaults.get(tf.getId());
        if (Stringop.isBlank(dft)) {
          return currentTextValue;
        }
        return dft;
      }
    });
  }

  public void registerSplitPaneLocations(String keyChain, SplitPane splitPane) {
    String currentValue = Arrays.toString(splitPane.getDividerPositions());
    registerData(keyChain, new Bolt() {

      @Override
      public void setBoltValue(String value) {
        if (!Stringop.isPopulated(value)) {
          return;
        }
        if ("[]".equalsIgnoreCase(value)) {
          return;
        }
        String[] str = value.replace("[", "").replace("]", "").split(",");
        double[] positions = new double[str.length];
        for (int i = 0; i < str.length; i++) {
          positions[i] = Double.parseDouble(str[i]);
        }
        splitPane.setDividerPositions(positions);
      }

      @Override
      public String getBoltValue() {
        return Arrays.toString(splitPane.getDividerPositions());
      }

      @Override
      public String getBoltDefault() {
        return currentValue;
      }
    });
  }

  public void registerTabSelected(String keyChain, TabPane tabPane, FxDoc fxDoc) {
    int currentValue = tabPane.getSelectionModel().getSelectedIndex();
    registerData(keyChain, new Bolt() {

      @Override
      public String getBoltValue() {
        return "" + tabPane.getSelectionModel().getSelectedIndex();
      }

      @Override
      public void setBoltValue(String value) {
        if (Stringop.isPopulated(value)) {
          try {
            int i = Integer.parseInt(value);
            tabPane.getSelectionModel().select(i);
          } catch (NumberFormatException ex) {
            Logop.error(ex);
          }
        }
      }

      @Override
      public String getBoltDefault() {
        return "" + currentValue;
      }

    });
  }

  private void registerData(String key, Bolt bolt) {
    bolts.computeIfAbsent(key, k -> bolt);
  }

  public static void setChoiceBoxChoices(ChoiceBox<String> cb, List<String> choices) {
    ObservableList<String> ol = FXCollections.observableArrayList(choices);
    cb.setItems(ol);
    int selected = cb.getSelectionModel().getSelectedIndex();
    if (selected == -1) {
      cb.getSelectionModel().select(0);
    }
  }

  private final PanApplication am;

  public PanApplication getApplication() {
    return am;
  }

  public static void setChoiceBoxChoices(ChoiceBox<String> cb, String... choices) {
    setChoiceBoxChoices(cb, Stringop.arrayToList(choices));
  }

}
