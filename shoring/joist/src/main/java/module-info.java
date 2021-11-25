module com.panopset.joist {
  requires transitive com.panopset.desk;
  
  requires transitive javafx.base;
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires transitive javafx.graphics;
  
  opens com.panopset.joist to javafx.fxml;
  
  exports com.panopset.joist;
}
