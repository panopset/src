package com.panopset.flywheel.samples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.panopset.compat.Logop;
import com.panopset.compat.Rezop;
import com.panopset.compat.Streamop;
import com.panopset.compat.Stringop;

public enum FlywheelSamples {
  
  INSTANCE;
  
  public static List<FlywheelSample> all() {
    return INSTANCE.getSamples();
  }
  
  private List<FlywheelSample> samples;

  public List<FlywheelSample> getSamples() {
    if (samples == null) {
      samples = new ArrayList<>();
      for (String sfx : Rezop.textStreamToList(this.getClass().getResourceAsStream(String.format("%s/%s", BASE_PATH, "index.txt")))) {
        populateSample(sfx, samples);
      }
    }
    return samples;
  }
  
  private void populateSample(String sfx, List<FlywheelSample> list) {
    if (sfx == null || sfx.length()>0 && "#".equals(sfx.substring(0, 1))) {
      return;
    }
    FlywheelSample fs = new FlywheelSample();
    if ("Choose".equals(sfx)) {
      return;
    }
    Properties props = new Properties();
    try {
      props.load(this.getClass().getResourceAsStream(String.format("%s/%s/%s", BASE_PATH, sfx, "props.txt")));
    } catch (IOException e) {
      Logop.error(e);
      return;
    }
    fs.setName(sfx);
    fs.setListText(Streamop.getTextFromStream(this.getClass().getResourceAsStream(String.format("%s/%s/%s", BASE_PATH, sfx, "list.txt"))));
    fs.setTemplateText(Streamop.getTextFromStream(this.getClass().getResourceAsStream(String.format("%s/%s/%s", BASE_PATH, sfx, "template.txt"))));
    fs.setDesc(props.getProperty("desc"));
    fs.setLineBreaks(Stringop.parseBoolean(props.getProperty("lineBreaks"), true));
    fs.setListBreaks(Stringop.parseBoolean(props.getProperty("listBreaks"), true));
    fs.setTokens(props.getProperty("tokens"));
    fs.setSplitz(props.getProperty("splitz"));
    list.add(fs);
  }
  
  private static final String BASE_PATH = "/com/panopset/flywheel/samples";

  public static FlywheelSample find(String desc) {
    for (FlywheelSample fs : INSTANCE.getSamples()) {
      if (fs.getDesc().equals(desc)) {
        return fs;
      }
    }
    return null;
  }
}
