package com.panopset.lowerclass;

import java.io.File;
import java.io.IOException;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import com.panopset.compat.StatusListener;

public class VersionParser implements StatusListener {

  public static String DEFAULT_MAVEN_HOME = Stringop.USER_HOME + "/.m2";
  public static String PANOPSET_JAR = Stringop.USER_HOME + "/panopset.jar";

  public static void main(String... args) throws IOException {
    Logop.dspmsg("*** Entire repository example:");
    Logop.dspmsg(new VersionParser().getSummaryReport());
    Logop.dspmsg("*** Single jar example:");
    Logop.dspmsg(new VersionParser(PANOPSET_JAR).getDetailedReport());
  }

  public VersionParser() {}

  public VersionParser(final String pathToJar_or_directoryToTraverse) {
    this(new File(pathToJar_or_directoryToTraverse));
  }

  public VersionParser(final File jar_or_directoryToTraverse) {
    setFile(jar_or_directoryToTraverse);
  }

  private String mavenHome;

  private String getMavenHome() {
    if (mavenHome == null) {
      mavenHome = System.getenv().get("M2_HOME");
      if (mavenHome == null) {
        mavenHome = System.getenv().get("MAVEN_HOME");
      }
      if (mavenHome == null) {
        mavenHome = DEFAULT_MAVEN_HOME;
      }
    }
    return mavenHome;
  }

  private File file;

  private File getFile() {
    if (file == null) {
      file = new File(getMavenHome());
    }
    return file;
  }

  public void setFile(final File jarOrDirectory) {
    this.file = jarOrDirectory;
  }

  private String summaryReport;
  
  public String getSummaryReport() throws IOException {
    if (summaryReport == null) {
      summaryReport = createReport(false);
    }
    return summaryReport;
  }
  
  private String detailReport;

  public String getDetailedReport() throws IOException {
    if (detailReport == null) {
      detailReport = createReport(true);
    }
    return detailReport;
  }
 
  private String createReport(boolean printDetails) throws IOException {
    VersionMakeup vm = new VersionMakeup();
    vm.analyze(getFile(), printDetails);
    return vm.getReport();
  }

  public void update(String message) {
    Logop.dspmsg(message);
  }
}
