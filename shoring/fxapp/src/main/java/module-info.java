module com.panopset.fxapp {
  requires transitive com.panopset.compat;
  requires transitive javafx.base;
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires transitive javafx.graphics;
  
  opens com.panopset.sample to javafx.fxml;
  opens com.panopset.fxapp to javafx.fxml;

  exports com.panopset.fxapp;
  exports com.panopset.sample;
}
