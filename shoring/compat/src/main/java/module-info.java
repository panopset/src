module com.panopset.compat {
  requires transitive java.logging;
  requires transitive com.google.gson;
  opens com.panopset.compat to com.google.gson;
  
  exports com.panopset.compat;
  exports com.panopset.util.rpg;
}
