package com.panopset.fxapp;

import java.util.ArrayList;
import java.util.List;
import com.panopset.compat.GlobalProperties;
import com.panopset.compat.GlobalPropertyKeys;
import com.panopset.compat.Logop;
import com.panopset.compat.Logop.LogEntry;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public enum FontManagerFX implements GlobalPropertyKeys, FontNames {

  INSTANCE;

  public final static String FONT_GREEN = "-fx-background-color: #000000; -fx-text-fill: #99ff99;";
  public final static String FONT_YELLOW = "-fx-background-color: #000000;-fx-text-fill: #ffff66;";
  public final static String FONT_ORANGE = "-fx-background-color: #000000;-fx-text-fill: #ffaa00;";
  public final static String FONT_BLUE = "-fx-background-color: #000000;-fx-text-fill: #0000ff;";
  public final static String FONT_RED = "-fx-background-color: #000000;-fx-text-fill: #ff3333;";
  public final static String FONT_PURPLE = "-fx-background-color: #000000;-fx-text-fill: #dd33dd;";

  private Font monospace;
  private Font boldArial;
  private Font plainArial;
  private Font boldSerif;
  private Font plainSerif;
  private Font borderTitle;

  private LogEntry logEntry;

  public static Font getMonospace() {
    return INSTANCE.getMonospaceFont();
  }

  private Font getMonospaceFont() {
    if (monospace == null) {
      monospace = new Font(MONOSPACE, getFontSize().getValue());
    }
    return monospace;
  }

  public static Font getBoldArial() {
    return INSTANCE.getBoldArialFont();
  }

  private Font getBoldArialFont() {
    if (boldArial == null) {
      boldArial = Font.font(ARIAL, FontWeight.BOLD, getFontSize().getValue());
    }
    return boldArial;
  }

  public static Font getBoldSerif() {
    return INSTANCE.getBoldSerifFont();
  }

  private Font getBoldSerifFont() {
    if (boldSerif == null) {
      boldSerif = Font.font(SERIF, FontWeight.BOLD, getFontSize().getValue());
    }
    return boldSerif;
  }

  public static Font getPlainArial() {
    return INSTANCE.getPlainArialFont();
  }

  private Font getPlainArialFont() {
    if (plainArial == null) {
      plainArial = new Font(ARIAL, getFontSize().getValue());
    }
    return plainArial;
  }

  public static Font getPlainSerif() {
    return INSTANCE.getPlainSerifFont();
  }

  private Font getPlainSerifFont() {
    if (plainSerif == null) {
      plainSerif = new Font(SERIF, getFontSize().getValue());
    }
    return plainSerif;
  }

  public static Font getBorderTitle() {
    return INSTANCE.getBorderTitleFont();
  }

  private Font getBorderTitleFont() {
    if (borderTitle == null) {
      borderTitle = Font.font(ARIAL, FontPosture.ITALIC, getFontSize().getValue());
    }
    return borderTitle;
  }

  List<MenuBar> mbs = new ArrayList<>();
  List<Node> nodes = new ArrayList<>();
  List<TabPane> tabPanes = new ArrayList<>();
  List<Tab> tabs = new ArrayList<>();

  public static void register(Node control) {
    if (!INSTANCE.nodes.contains(control)) {
      INSTANCE.nodes.add(control);
      if (control instanceof TextInputControl) {
        control.setStyle(INSTANCE.getMonoStyle(INSTANCE.getFontSize().getValue()));
      } else {
        control.setStyle(FontManagerFX.getStyleFor(INSTANCE.getFontSize().getValue()));
      }
    }
  }

  public static void registerTabPane(TabPane tabPane) {
    if (INSTANCE.tabPanes.contains(tabPane)) {
      Logop.debug("Ignoring duplicate FontManagerFX registration of tabPane " + tabPane.getId());
    } else {
      INSTANCE.tabPanes.add(tabPane);
    }
  }

  public static void registerTab(Tab tab) {
    if (INSTANCE.tabs.contains(tab)) {
      Logop.debug("Ignoring duplicate FontManagerFX registration of tab " + tab.getId());
    } else {
      INSTANCE.tabs.add(tab);
    }
  }

  public static void registerMenubar(MenuBar menuBar) {
    if (INSTANCE.mbs.contains(menuBar)) {
      Logop.debug("Ignoring duplicate FontManagerFX registration of menubar " + menuBar.getId());
    } else {
      INSTANCE.mbs.add(menuBar);
    }
  }

  public static void init() {
    setFontSize(INSTANCE.getFontSize());
  }

  public static int getSize() {
    return INSTANCE.getFontSize().getValue();
  }
  
  private FontSize fontSize;

  private FontSize getFontSize() {
    if (fontSize == null) {
      try {
        String savedFontSizeValue = GlobalProperties.getGP(FONT_SIZE_KEY);
        if (savedFontSizeValue == null || savedFontSizeValue.length() < 1) {
          fontSize = FontSize.DEFAULT_SIZE;
          return fontSize;
        }
        fontSize = FontSize.findFromValue(savedFontSizeValue);
      } catch (NumberFormatException ex) {
        Logop.error(ex);
        fontSize = FontSize.DEFAULT_SIZE;
      }
    }
    return fontSize;

  }

  public static void setFontSize(FontSize fontSize) {
    INSTANCE.fontSize = fontSize;
    INSTANCE.updateAllFontSizes();
  }

  private String getMonoStyle(int i) {
    return String.format("-fx-font-size: %dpx; -fx-font-family: 'monospaced';", i);
  }

  private void updateAllFontSizes() {
    String style = getStyleFor(getFontSize().getValue());
    String stylem = getMonoStyle(getFontSize().getValue());
    for (MenuBar mb : mbs) {
      mb.setStyle(style);
    }
    for (Node node : nodes) {
      if (node instanceof TextInputControl) {
        if ("menubarStatusMessage".equals(node.getId())) {
          Platform.runLater(() -> {
            if (node != null) {
              setMenubarLogRecord(logEntry, (TextField) node);
            }
          });
        } else {
          node.setStyle(stylem);
        }
      } else {
        node.setStyle(style);
      }
    }
    for (TabPane tabPane : tabPanes) {
      tabPane.setStyle(style);
    }
    for (Tab tab : tabs) {
      tab.setStyle(style);
    }
    GlobalProperties.putGP(FONT_SIZE_KEY, String.format("%d", getFontSize().getValue()));
    monospace = null;
    boldArial = null;
    plainArial = null;
    boldSerif = null;
    plainSerif = null;
    borderTitle = null;
  }

  public static double getImgRatio() {
    return INSTANCE.getFontSize().getImgRatio();
  }


  public static double getSvgRatio() {
    return INSTANCE.getFontSize().getSvgRatio();
  }

  public static String getCurrentMessageStyle(LogEntry logEntry) {
    String colorStyle = FONT_GREEN;
    if (logEntry.getAlert().equals(Logop.Alert.PURPLE)) {
      colorStyle = FONT_PURPLE;
    } else if (logEntry.getAlert().equals(Logop.Alert.BLUE)) {
      colorStyle = FONT_BLUE;
    } else if (logEntry.getAlert().equals(Logop.Alert.RED)) {
      colorStyle = FONT_RED;
    } else if (logEntry.getAlert().equals(Logop.Alert.ORANGE)) {
      colorStyle = FONT_ORANGE;
    } else if (logEntry.getAlert().equals(Logop.Alert.YELLOW)) {
      colorStyle = FONT_YELLOW;
    } else if (logEntry.getAlert().equals(Logop.Alert.GREEN)) {
      colorStyle = FONT_GREEN;
    }
    return colorStyle + getStyleFor(getSize());
  }

  public static String getStyleFor(int fontSizeValue) {
    return String.format("-fx-font-size: %dpx;", fontSizeValue);
  }

  public static synchronized void setMenubarLogRecord(LogEntry logEntry, TextField menubarStatusMessage) {
    if (logEntry == null) {
      return;
    }
    INSTANCE.logEntry = logEntry;
    if (INSTANCE.logEntry.getMessage() == null) {
      return;
    }
    Platform.runLater(() -> {
      menubarStatusMessage.setStyle(FontManagerFX.getCurrentMessageStyle(INSTANCE.logEntry));
      menubarStatusMessage.setText(INSTANCE.logEntry.getMessage());
    });
  }

}
