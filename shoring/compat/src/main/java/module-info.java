module com.panopset.compat {
  requires transitive com.google.gson;
  requires transitive java.logging;
  opens com.panopset.compat to com.google.gson;
  
  exports com.panopset.compat;
  exports com.panopset.util.rpg;
}
