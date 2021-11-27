package com.panopset.marin.bootstrap;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import com.panopset.flywheel.FlywheelBuilder;

public class VersionClassGenerator {

  private String versionString;  
  
  public VersionClassGenerator(String version) {
    versionString = version;
  }

  public static void main(String... args) throws IOException {
    new VersionClassGenerator(args == null ? "" : args.length < 1 ? "" : args[0]).updateVersion();
  }

  private void updateVersion() {
    updateAppVersionClass();
    updatePoms();
  }

  private void updatePoms() {
    updateProject("../shoring");
    updateProject("compat");
    updateProject("db");
    updateProject("blackjackEngine");
    updateProject("minpin");
    updateProject("lowerclass");
    updateProject("flywheel");
    updateProject("fxapp");
    updateProject("desk");
    updateProject("panopset");
    updateProject("web");
    updateProject("joist");
  }

  private void updateProject(String project) {
    String pp = String.format("../%s/pom.xml", project);
    updatePom(pp);
  }

  private void updatePom(String pp) {
    String fr = "<version>";
    String tm = "<version>%s</version>";
    String vr = getVersionString();
    replaceFirstLine(pp, fr, String.format(tm, vr));
  }

  private void replaceFirstLine(String path, String lineToReplaceContaining,
      String fullReplacementLine) {
    File file = new File(path);
    List<String> source = Fileop.readLines(file);
    List<String> strs = Stringop.replaceFirstLinePreserveIndentation(source,
        lineToReplaceContaining, fullReplacementLine);
    if (source.equals(strs)) {
      Logop.info(String.format("No changes to %s, skipping...", path));
    } else {
      Fileop.write(strs, file);
      Logop.info(String.format("%s, updated with %s.", path, fullReplacementLine));
    }
  }

  private void updateAppVersionClass() {
    File vf = new File("src/main/java/com/panopset/compat/AppVersion.java");
    if (!vf.getParentFile().exists()) {
      vf = new File("../compat/src/main/java/com/panopset/compat/AppVersion.java");
    }
    if (!vf.getParentFile().exists()) {
      Logop.error("Parent directory not found", vf);
      return;
    }
    String result = new FlywheelBuilder()
        .inputResourcePath(VersionClassGenerator.class,
            "/com/panopset/marin/bootstrap/version.tmplt")
        .map("version_timestamp", getVersionString()).construct().exec();
    if (Stringop.isPopulated(result)) {
      Fileop.write(result, vf);
    }
  }

  private String getVersionString() {
    if (Stringop.isBlank(versionString)) {
      versionString = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
    }
    return versionString;
  }
}
