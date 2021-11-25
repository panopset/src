package com.panopset.fxapp;

import com.panopset.compat.Logop;
import com.panopset.compat.Logop.LogEntry;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PanMenuFX {

  @FXML
  TextField menubarStatusMessage;

  @FXML
  HBox panMenuBar;

  @FXML
  MenuBar menubar;

  @FXML
  Menu fileMenu;

  @FXML
  Menu fontMenu;

  @FXML
  Pane menubarStatusPane;

  @FXML
  public void initialize() {
    FontManagerFX.registerMenubar(menubar);
    int size = FontManagerFX.getSize();
    for (MenuItem menuItem : fontMenu.getItems()) {
      CheckMenuItem cmi = (CheckMenuItem) menuItem;
      String canonicalSizeName = menuItem.getId().substring(2).toUpperCase();
      FontSize fontSize = FontSize.findFromName(canonicalSizeName);
      cmi.setSelected(size == fontSize.getValue());
    }
    Logop.addLogListener((LogEntry logEntry) -> {
      FontManagerFX.setMenubarLogRecord(logEntry, menubarStatusMessage);
    });
  }

  @FXML
  CheckMenuItem fmSmall;

  @FXML
  CheckMenuItem fmMedium;

  @FXML
  CheckMenuItem fmLarge;

  @FXML
  CheckMenuItem fmSuper;

  @FXML
  CheckMenuItem fmCinema;

  @FXML
  private void handleHmAboutAction(Event event) {
    JavaFXapp.showAboutPanel();
  }

  @FXML
  private void handleHmLogAction(Event event) {
    JavaFXapp.showLogPanel();
  }

  @FXML
  private void handleNewAction(Event event) {
    JavaFXapp.newWindow();
  }

  @FXML
  private void handleOpenAction(Event event) {
    JavaFXapp.openWindowFromFile();
  }

  public FxDoc getFxDoc() {
    return (FxDoc) fileMenu.getUserData();
  }

  @FXML
  private void handleSaveAction(Event event) {
    JavaFXapp.saveWindow(getFxDoc());
  }

  @FXML
  private void handleSaveAsAction(Event event) {
    JavaFXapp.saveWindowAs(getFxDoc());
  }

  @FXML
  private void handleCloseAction(Event event) {
    JavaFXapp.closeWindow(getFxDoc());
  }

  @FXML
  private void handleQuitAction(Event event) {
    JavaFXapp.panExit();
  }

  private void updateFontSize(CheckMenuItem selected, FontSize size) {
    FontManagerFX.setFontSize(size);
    fmSmall.setSelected(selected == fmSmall);
    fmMedium.setSelected(selected == fmMedium);
    fmLarge.setSelected(selected == fmLarge);
    fmSuper.setSelected(selected == fmSuper);
    fmCinema.setSelected(selected == fmCinema);
  }

  @FXML
  private void handleFmSmallAction(Event event) {
    updateFontSize(fmSmall, FontSize.SMALL);
  }

  @FXML
  private void handleFmMediumAction(Event event) {
    updateFontSize(fmMedium, FontSize.MEDIUM);
  }

  @FXML
  private void handleFmLargeAction(Event event) {
    updateFontSize(fmLarge, FontSize.LARGE);
  }

  @FXML
  private void handleFmSuperAction(Event event) {
    updateFontSize(fmSuper, FontSize.SUPER);
  }

  @FXML
  private void handleFmCinemaAction(Event event) {
    updateFontSize(fmCinema, FontSize.CINEMA);
  }
}
