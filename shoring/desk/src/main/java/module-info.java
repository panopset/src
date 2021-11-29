module com.panopset.desk {
  requires transitive com.panopset.blackjackEngine;
  requires transitive com.panopset.minpin;
  requires transitive com.panopset.flywheel;
  requires transitive com.panopset.lowerclass;
  requires transitive com.panopset.fxapp;
  requires transitive java.desktop;
  requires javafx.web;
  requires javafx.swing;

  opens com.panopset.marin.games.blackjack to javafx.fxml;
  opens com.panopset.marin.games.minpin to javafx.fxml;
  opens com.panopset.marin.apps.fw to javafx.fxml;
  opens com.panopset.marin.apps.gr to javafx.fxml;
  opens com.panopset.marin.apps.lc to javafx.fxml;
  opens com.panopset.marin.cs to javafx.fxml;
  opens com.panopset.marin.apps.scrambler to javafx.fxml;
  
  opens com.panopset.marin.bootstrap to com.google.gson;
  
  exports com.panopset.desk;
  exports com.panopset.desk.utilities;
  exports com.panopset.desk.security;
  exports com.panopset.desk.games;
}
