package com.panopset.flywheel;

import java.io.File;
import com.panopset.compat.Fileop;

public final class TemplateFile extends TemplateArray {
  private final String fn;

  public TemplateFile(final File file) {
    super(Fileop.readLines(file));
    fn = Fileop.getCanonicalPath(file);
  }

  @Override
  public String getName() {
    return fn;
  }
}
