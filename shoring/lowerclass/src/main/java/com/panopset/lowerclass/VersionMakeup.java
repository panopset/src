package com.panopset.lowerclass;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import com.panopset.compat.ClassVersion;
import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.MajorVersion;
import com.panopset.compat.MajorVersionException;
import com.panopset.compat.Stringop;

class VersionMakeup {

  private Map<String, Map<MajorVersion, Integer>> map =
      Collections.synchronizedSortedMap(new TreeMap<>());

  private void clear() {
    map.clear();
    classReport = new StringWriter();
  }

  public void analyze(File file, boolean printDetails) throws IOException {
    clear();
    genRpt(file.getName(), file, printDetails);
  }

  public String getReport() {
    StringWriter tp = new StringWriter();
    for (Entry<String, Map<MajorVersion, Integer>> e : map.entrySet()) {
      tp.append(e.getKey());
      tp.append(" > ");
      Map<MajorVersion, Integer> jvs = e.getValue();
      boolean firstTime = true;
      for (Entry<MajorVersion, Integer> jv : jvs.entrySet()) {
        if (firstTime) {
          firstTime = false;
        } else {
          tp.append(",");
        }
        tp.append(jv.getKey().strRep);
        tp.append(" " + jv.getValue());
      }
      tp.append("\n");
    }
    if (Stringop.isPopulated(classReport.toString())) {
      tp.append("\n");
      tp.append("\n");
      tp.append(classReport.toString());
    }
    return tp.toString();
  }

  private void genRpt(String fileName, File file, boolean printDetails) throws IOException {
    if (file == null) {
      Logop.warn("No file selected.");
      return;
    }
    if (!file.exists()) {
      Logop.error("File doesn't exist.", file);
      return;
    }
    if (file.isDirectory()) {
      genDirectoryReport(fileName, file, printDetails);
    } else {
      String ext = Fileop.getExtension(file.getName());
      if ("class".equals(ext)) {
        genClassReport(fileName, file, printDetails);
      } else if ("jar".equals(ext)) {
        genJarReport(fileName, file, printDetails);
      } else {
        Logop.warn("Selected file is not a jar or class.");
        return;
      }
    }
    Logop.green(String.format("genReport complete for: %s", Fileop.getCanonicalPath(file)));
  }

  private void genDirectoryReport(String fileName, File file, boolean printDetails) {
    File[] list = file.listFiles();
    if (list != null) {
      for (File f : list) {
        if (f.isDirectory()) {
          genDirectoryReport(fileName, f, printDetails);
        } else {
          String ext = Fileop.getExtension(f.getName());
          if ("class".equals(ext)) {
            genClassReport(fileName, f, printDetails);
          } else if ("jar".equals(ext)) {
            genJarReport(fileName, f, printDetails);
          }
        }
      }
    }
  }

  private void genClassReport(String fileName, File file, boolean printDetails) {
    updateReportMap(fileName, updateStatsForClass(fileName, file, printDetails));
  }

  private void genJarReport(String fileName, File file, boolean printDetails) {
    Logop.green(String.format("Processing jar: %s", file.getName()));
    updateReportMap(file.getName(), updateStatsForJar(file, printDetails));
  }

  private void updateReportMap(String name, Map<MajorVersion, Integer> jvs) {
    if (jvs != null && !jvs.isEmpty()) {
      map.put(name, jvs);
    }
  }

  private Map<MajorVersion, Integer> updateStatsForJar(File dirFile, boolean printDetails) {
    Map<MajorVersion, Integer> jvs = createVersionMap();
    try (JarFile jar = new JarFile(dirFile)) {
      Enumeration<JarEntry> enumEntries = jar.entries();
      while (enumEntries.hasMoreElements()) {
        java.util.jar.JarEntry entry = (java.util.jar.JarEntry) enumEntries.nextElement();
        java.io.InputStream is = jar.getInputStream(entry);
        try (DataInputStream dis = new DataInputStream(is)) {
          ClassVersion cv = readClassVersion(entry.getName(), dis, printDetails);
          if (cv != null && cv.isValid()) {
            Integer count = jvs.get(cv.getMajorVersion());
            if (count == null) {
              jvs.put(cv.getMajorVersion(), 1);
            } else {
              jvs.put(cv.getMajorVersion(), count + 1);
            }
          }
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    } catch (IOException ex) {
      Logop.error(Fileop.getCanonicalPath(dirFile));
    }
    return jvs;
  }

  private StringWriter classReport = new StringWriter();

  private Map<MajorVersion, Integer> updateStatsForClass(String name, File dirFile,
      boolean printDetails) {
    Logop.green(String.format("Processing class: %s", name));
    Map<MajorVersion, Integer> jvs = createVersionMap();
    try (FileInputStream fis = new FileInputStream(dirFile);
        DataInputStream dis = new DataInputStream(fis)) {
      ClassVersion cv = readClassVersion(name, dis, printDetails);
      if (cv != null && cv.isValid()) {
        jvs.put(cv.getMajorVersion(), 1);
      }
    } catch (IOException ex) {
      Logop.error(ex);
    }
    return jvs;
  }

  private ClassVersion readClassVersion(String name, DataInputStream dis, boolean printDetails)
      throws IOException {
    if (dis.available() > 0) {
      if ("class".equals(Fileop.getExtension(name))) {
        int magic = dis.readInt();
        if (magic != 0xcafebabe) {
          Logop.error(name + " is not a java class! this should be 0xcafebabe:"
              + Integer.toHexString(magic));
        } else {
          int minor = dis.readUnsignedShort();
          int major = dis.readUnsignedShort();
          ClassVersion classVersion;
          try {
            classVersion =
                new ClassVersion(MajorVersion.findFromHexString(Integer.toHexString(major)),
                    Integer.toHexString(minor));
          } catch (MajorVersionException e) {
            Logop.handle(e);
            return new ClassVersion();
          }
          if (printDetails) {
            classReport.append(name);
            classReport.append(" major: ");
            classReport.append(classVersion.getMajorVersion().strRep);
            classReport.append(" minor: ");
            classReport.append(classVersion.getMinorVersion());
            classReport.append("\n");
          }
          return classVersion;
        }
      }
    }
    return null;
  }

  private Map<MajorVersion, Integer> createVersionMap() {
    return Collections.synchronizedSortedMap(new TreeMap<>());
  }
}
