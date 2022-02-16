package com.panopset.compat;

import java.io.File;

public interface LineFilter {

  String filter(String str);
  
  default boolean fileFilter(File file) {
	  return true;
  }
}
