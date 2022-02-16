package com.panopset.flywheel;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Stack;
import java.util.TreeMap;

import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.MapProvider;
import com.panopset.compat.Nls;
import com.panopset.compat.Propop;
import com.panopset.compat.Rezop;
import com.panopset.compat.Stringop;

/**
 * Panopset Flywheel.
 * <h3>Commands</h3> There are 7 commands that you may use in a Flywheel template. Commands have a
 * default syntax that starts with <b>${&#064;</b>, followed by the letter associated with the
 * command, followed by a space, followed by a parameter, followed by the default closing syntax of
 * <b>}</b>.
 * <h1>f - File</h1>
 *
 * <pre>
 * ${&#064;f somefile.txt}
 * </pre>
 *
 * <p>
 * Output to the specified file, until the matching ${&#064;q} is found.
 * </p>
 * 
 * <h1>p -Push</h1>
 *
 * <pre>
 *
 * ${&#064;p variableName}
 * </pre>
 *
 * <p>
 * Everything following this command is pushed into a String buffer, until a q command is reached.
 * </p>
 * <h1>l - List</h1>
 * 
 * <pre>
 * ${&#064;l someListFile.txt} or ${&#064;l someFile.txt&tilde;,}
 * </pre>
 *
 * <p>
 * Read the given file, and for each line execute the template from this list command up until its
 * matching q command. If no <b>token</b> is defined as a variable, then each line will be stored in
 * variable <b>1</b>. If there is a token defined after a ~, the line will be split by that token,
 * and stored in variables named after integers, in order.
 * </p>
 *
 * <h1>q - Quit</h1>
 *
 * <pre>
 * ${&#064;q}
 * </pre>
 * <ul>
 * <li>If the q command follows a p command, the String buffer is defined as the variable name that
 * was provided in the p command.</li>
 * <li>If the q command follows an l command, the String buffer is read for the each item in the
 * list.</li>
 * </ul>
 * <h1>t - Template</h1>
 *
 * <pre>
 * ${&#064;t someTemplateFile.txt}
 * </pre>
 *
 * <p>
 * Continue execution using the supplied template script file.
 * </p>
 * 
 * <h1>Variable</h1>
 * <p>
 * There is no command associated with a variable, so you drop the <b>&#064;</b> directive
 * indicator, and then you specify a variable just as you would in any ant script or unix shell. The
 * variable must have been defined either in a map provided to the script through
 * Script.Builder.mergeMap, or a Push command.
 * </p>
 * 
 * <pre>
 * ${variableName}
 * </pre>
 *
 * <h1>e - Execute</h1> Excecute any static method. Parameters may be Flywheel defined variable
 * names only. For example:
 *
 * <pre>
 *
 * ${&#064;p name}panopset${q}
 * 
 * ${&#064;e com.panopset.compat.Strings.capitalize(name)}
 *
 * </pre>
 *
 * <p>
 * The above script will output:
 * </p>
 * 
 * <pre>
 *
 * Panopset
 *
 * </pre>
 */
public final class Flywheel implements MapProvider {

  private static boolean defined = false;

  public static synchronized void defineAllowedScriptCalls() {
    if (defined) {
      return;
    }
    defined = true;

    ReflectionInvoker.defineTemplateAllowedReflection("toUpperCase", "com.panopset.compat",
        "Stringop", "toUpperCase", "${@e toUpperCase(foo)}");
    ReflectionInvoker.defineTemplateAllowedReflection("toLowerCase", "com.panopset.compat",
        "Stringop", "toLowerCase", "${@e toLowerCase(foo)}");
    ReflectionInvoker.defineTemplateAllowedReflection("capitalize", "com.panopset.compat",
        "Stringop", "capitalize", "${@e capitalize(foo)}");
    ReflectionInvoker.defineTemplateAllowedReflection("check4match", "com.panopset.compat",
        "Stringop", "check4match", "${@e check4match(foo,bar,matches,doesnotmatch)}");
    ReflectionInvoker.defineTemplateAllowedReflection("getVersion", "com.panopset.compat",
        "AppVersion", "getVersion", "${@e getVersion()}");
    ReflectionInvoker.defineTemplateAllowedReflection("capund", "com.panopset.compat", "Stringop",
        "capund", "${@e capund(MakeThisCapUnderscore)}");
    ReflectionInvoker.defineTemplateAllowedReflection("findLine", "com.panopset.flywheel", "Filter",
        "findLine", "${@e findLine(foo)}");
    ReflectionInvoker.defineTemplateAllowedReflection("findLines", "com.panopset.flywheel",
        "Filter", "findLines", "${@e findLines(foo, bar)}");
    ReflectionInvoker.defineTemplateAllowedReflection("combine", "com.panopset.flywheel", "Filter",
        "combine", "${@e combine(2)}");
    ReflectionInvoker.defineTemplateAllowedReflection("regex", "com.panopset.flywheel", "Filter",
        "regex", "${@e regex(foo)} result: ${r0}");
    ReflectionInvoker.defineTemplateAllowedReflection("replace", "com.panopset.compat", "Stringop",
        "replace", "${@e replace(text, foo, bar)}");
    ReflectionInvoker.defineTemplateAllowedReflection("replaceAll", "com.panopset.compat", "Stringop",
        "replaceAll", "${@e replaceAll(text, foo, bar)}");
  }

  /**
   * @param args
   * 
   *        <pre>
   *     [0] = script file name, args[1] = target directory name.
   *       or
   *     [0] = properties file name where these properties are required:
   *            com.panopset.flywheel.script=&lt;your script file name&gt;
   * com.panopset.flywheel.target=&lt;your target file name&gt;
   *        </pre>
   */
  public static void main(final String... args) throws IOException {

    // Logop.turnOnDebugging();

    if (args == null || args.length == 0 || args.length > 2) {
      Logop.dspmsg("Params are script and target directory.");
    } else {
      Flywheel flywheel = null;
      if (args.length == 1) {
        flywheel = new FlywheelBuilder().properties(new File(args[0])).construct();
      } else {
        String scriptFileName = args[0];
        String targetDirectoryName = args[1];
        File scriptFile = new File(scriptFileName);
        File targetDirectory = new File(targetDirectoryName);
        if (!scriptFile.exists()) {
          Logop.error("File does not exist.", scriptFile);
          return;
        }
        if (!scriptFile.canRead()) {
          Logop.error("Can not read.", scriptFile);
          return;
        }
        Logop.info(String.format(Stringop.CS, "Script file", Fileop.getCanonicalPath(scriptFile)));
        Logop.info(String.format(Stringop.CS, "Target directory",
            Fileop.getCanonicalPath(targetDirectory)));
        flywheel =
            new FlywheelBuilder().file(scriptFile).targetDirectory(targetDirectory).construct();
      }
      flywheel.exec();
    }
  }

  private File targetDirectory;

  public File getTargetDirectory() {
    return targetDirectory;
  }

  private File file;

  /**
   * String line supplier.
   */
  private final TemplateSource sls;

  /**
   * Currently resolving command.
   */
  private Command resolvingCommand;

  /**
   * Used for debugging.
   *
   * @return Currently resolving command.
   */
  public Command getResolvingCommand() {
    return resolvingCommand;
  }

  /**
   * Writer for this Flywheel.
   */
  private StringWriter pwriter;

  /**
   * Map stack.
   */
  private final Stack<NamedMap<String, String>> mapStack = new Stack<>();

  /**
   * @return Map stack.
   */
  Stack<NamedMap<String, String>> getMapStack() {
    return mapStack;
  }
  
  public Map<String, String> getTopMap() {
    NamedMap<String, String> namedMap = getMapStack().peek();
    return namedMap.getMap();
  }

  /**
   * @return All map values.
   */
  public Map<String, String> getAllValues() {
    Map<String, String> rtn = Collections.synchronizedMap(new TreeMap<>());
    for (NamedMap<String, String> nm : getMapStack()) {
      for (Entry<String, String> e : nm.getMap().entrySet()) {
        rtn.put(e.getKey(), e.getValue());
      }
    }
    return rtn;
  }

  private CommandFile currentCommandFile;

  CommandFile getCurrentCommandFile() {
    return currentCommandFile;
  }

  void setCurrentCommandFile(final CommandFile commandFile) {
    currentCommandFile = commandFile;
  }

  private List<String[]> replacements;
  private Map<String, Object> registeredObjs;


  private LineFeedRules lineFeedRules;

  public LineFeedRules getLineFeedRules() {
    if (lineFeedRules == null) {
      lineFeedRules = new LineFeedRules();
    }
    return lineFeedRules;
  }

  public void setLineFeedRules(LineFeedRules templateRules) {
    this.lineFeedRules = templateRules;
  }

  /**
   * Copy package resource.
   *
   * @param resourcePath Java source path resource.
   * @param targetPath Target path, relative to Flywheel target directory.
   * @throws IOException IO Exception.
   */
  public void copyPackageResource(final String resourcePath, final String targetPath)
      throws IOException {
    try {
      Rezop.copyTextResourceToFile(this.getClass(), resourcePath,
          Fileop.getCanonicalPath(targetDirectory) + "/" + targetPath);
    } catch (IOException ex) {
      StringWriter sw = new StringWriter();

      sw.append("baseDirectoryPath: " + baseDirectoryPath);
      sw.append(Stringop.getEol());
      sw.append("Template file: " + Fileop.getCanonicalPath(file));
      sw.append(Stringop.getEol());
      sw.append("targetDirectory: " + targetDirectory);
      sw.append(Stringop.getEol());
      sw.append("resourcePath: " + resourcePath);
      sw.append(Stringop.getEol());
      sw.append("targetPath: " + targetPath);
      sw.append(Stringop.getEol());
      if (template != null && template.getTemplateSource() != null) {
        sw.append("executing from: " + template.getTemplateSource().getName());
        sw.append(Stringop.getEol());
        sw.append("Line: ");
        sw.append("" + template.getTemplateSource().getLine());
        sw.append(Stringop.getEol());
      }
      Logop.warn(sw.toString());
      throw (ex);
    }
  }

  /**
   * Flywheel constructor.
   *
   * @param stringLineSupplier line supplier.
   */
  Flywheel(final TemplateSource stringLineSupplier) {
    defineAllowedScriptCalls();
    sls = stringLineSupplier;
    this.mapStack.push(new NamedMap<>(Stringop.getNextJvmUniqueIDstr()));
    this.mapStack.peek().put(ReservedWords.FILE, sls.getName());
  }

  /**
   * Is target directory valid.
   *
   * @return true if a valid, writable target directory has been specified.
   */
  public boolean isTargetDirectoryValid() {
    if (targetDirectory != null && !targetDirectory.exists()) {
      Fileop.mkdirs(targetDirectory);
    }
    return targetDirectory != null && targetDirectory.isDirectory() && targetDirectory.exists()
        && targetDirectory.canWrite();
  }

  /**
   * This method should only be used when a writer was specified in the builder. In other words,
   * when you are using the templates to generate a text String, instead of files, then you would
   * use this method to get the output.
   *
   * @return StringWriter holds script output.
   */
  public StringWriter getWriter() {
    if (pwriter == null) {
      pwriter = new StringWriter();
    }
    return pwriter;
  }

  /**
   * @param writer Writer.
   */
  public void setWriter(final StringWriter writer) {
    pwriter = writer;
  }

  /**
   * Put map value, that will be available to future variable commands.
   *
   * @param key Variable name.
   * @param value Variable value.
   */
  public void put(final String key, final String value) {
    mapStack.peek().put(key, value);
  }

  /**
   * Load properties.
   *
   * @param relativePath Path is relative to the parent of the main Flywheel script file.
   * @return empty String.
   */
  public String loadProperties(final String relativePath) throws IOException {
    File file = new File(getBaseDirectoryPath() + "/" + relativePath);
    Properties props = new Properties();
    if (!file.exists()) {
      try {
        Rezop.copyTextResourceToFile(this.getClass(), relativePath, file);
      } catch (IOException ex) {
        Logop.warn("relativePath: " + relativePath);
        Logop.error(ex);
      }
    }
    Propop.load(props, file);
    for (Object k : props.keySet()) {
      put(k.toString(), props.getProperty(k.toString()));
    }
    return "";
  }

  /**
   * Suppress replacements.
   *
   * <b>Usage</b>
   *
   * <pre>
   * ${&#064;p foo}bar{&#064;q}
   * ${&#064;e script.suppressReplacements(true)}
   * A: ${&#064;v foo}
   * ${&#064;e script.suppressReplacements(false)}
   * B: ${&#064;v foo}
   * </pre>
   *
   * <b>Output</b>
   *
   * <pre>
   *
   * A:
   * B: bar
   *
   * </pre>
   *
   * @param value If true, all v commands will not output anything.
   */
  public void suppressReplacements(final String value) {
    if (value == null) {
      control.setReplacementsSuppressed(false);
      return;
    }
    control.setReplacementsSuppressed(Boolean.parseBoolean(value));
  }

  @Override
  public String get(final String key) {
    if (mapStack.isEmpty()) {
      stop("mapStack empty, this should be an impossible condition.");
      return null;
    }
    Stack<NamedMap<String, String>> stack = new Stack<>();
    for (NamedMap<String, String> item : mapStack) {
      stack.push(item);
    }
    while (!stack.isEmpty()) {
      NamedMap<String, String> nm = stack.pop();
      String rtn = nm.get(key);
      if (rtn != null) {
        return rtn;
      }
    }
    if (Stringop.isPopulated(key)) {
      String rtn = System.getProperty(key);
      if (Stringop.isPopulated(rtn)) {
        return rtn;
      }
      rtn = System.getenv(key);
      if (Stringop.isPopulated(rtn)) {
        return rtn;
      }
    }
    return "";
  }

  public void mergeMap(final Map<String, String> map) {
    mapStack.push(new NamedMap<>(Stringop.getNextJvmUniqueIDstr(), map));
  }

  public String exec() {
    getTemplate().output();
    return getWriter().toString();
  }

  private Template template;

  public Template getTemplate() {
    if (template == null) {
      template = new Template(this, sls, getLineFeedRules());
    }
    return template;
  }

  public void stop(final String message) {
    Logop.error(message);
    if (Logop.isDebugging()) {
      if (getResolvingCommand() != null) {
        Logop.debug(" at: " + getResolvingCommand().toString());
      }
      Logop.debug(Javop.dump(mapStack.peek().getMap()));
      Logop.warn(Nls.xlate("Template parsing stopped.  More information was dumped in the log."));
    } else {
      Logop.warn(Nls
          .xlate("Template parsing stopped.  Check debug in help->Show log, for more information"));
    }
    if (resolvingCommand != null) {
      Logop.warn(Nls.xlate("Stopped while executing line") + " " + resolvingCommand.toString());
    }
    control.stop(message);
  }

  public boolean isStopped() {
    return control.isStopped();
  }

  private String baseDirectoryPath;

  /**
   * Get the directory in which the script top template file resides. This is used as a relative
   * path base for all subsequent template files created with the template command: <b>${&#064;t
   * templateRelativePathName}</b>.
   *
   * @return Full path of parent directory of the top script template file.
   */
  public String getBaseDirectoryPath() {
    if (baseDirectoryPath == null) {
      if (file != null && file.exists()) {
        try {
          baseDirectoryPath = file.getParentFile().getCanonicalPath();
        } catch (IOException e) {
          Logop.error(e);
          return "";
        }
      } else {
        baseDirectoryPath = System.getProperty("user.home");
      }
    }
    return baseDirectoryPath;
  }

  /**
   * Set replacements vector.
   *
   * @param replacementsList Replacements vector.
   */
  void setReplacements(final List<String[]> replacementsList) {
    this.replacements = replacementsList;
  }

  /**
   * @return Replacements vector.
   */
  protected List<String[]> getReplacements() {
    if (replacements == null) {
      replacements = new ArrayList<>();
    }
    return replacements;
  }

  /**
   * Append simple file name to getBaseDirectoryPath.
   *
   * @param simpleFileName Simple file name (no path).
   * @return Full relative path of simpleFileName
   */
  public String getBaseRelativePath(final String simpleFileName) {
    return getBaseDirectoryPath() + "/" + simpleFileName;
  }

  /**
   * Is replacements repressed.
   *
   * @return true if replacements suppressed flag is set.
   */
  public boolean isReplacementsSuppressed() {
    return getControl().isReplacementsSuppressed();
  }

  /**
   * @param registeredObjects Registered objects.
   */
  public void setRegisteredObjects(final Map<String, Object> registeredObjects) {
    for (Entry<String, Object> e : registeredObjects.entrySet()) {
      getRegisteredObjects().put(e.getKey(), e.getValue());
    }
  }

  /**
   * @return Registered objects for execute commands.
   */
  public Map<String, Object> getRegisteredObjects() {
    if (registeredObjs == null) {
      registeredObjs = new HashMap<String, Object>();
    }
    return registeredObjs;
  }

  /**
   * Flywheel control.
   */
  private final Control control = new Control();

  /**
   * Get control.
   *
   * @return Flywheel Control.
   */
  public Control getControl() {
    return control;
  }

  /**
   * Is output to files enabled.
   */
  private boolean isOutputEnabled = true;

  /**
   * @return True iff output to files is enabled, default is true.
   */
  public boolean isOutputEnabled() {
    return isOutputEnabled;
  }

  /**
   * Set whether or not Flywheel will write output to text files. Default is true, you might want to
   * set to false if all you are doing is String manipulation in memory.
   *
   * @param value Value to set.
   */
  public void setOutputEnabled(final boolean value) {
    this.isOutputEnabled = value;
  }

  /**
   * @param value New base directory path.
   */
  void setBaseDirectoryPath(final String value) {
    if (value != null) {
      baseDirectoryPath = value;
    }
    if (this.file != null && baseDirectoryPath == null) {
      baseDirectoryPath = Fileop.getParentDirectory(this.file);
    }
  }

  /**
   * @param value Target directory.
   */
  void setTargetDirectory(final String value) {
    if (Stringop.isPopulated(value)) {
      targetDirectory = new File(value);
    }
    if (targetDirectory == null) {
      targetDirectory = getTargetDirectory();
    }
  }

  /**
   * @param value New file.
   */
  void setFile(final File value) {
    if (value != null) {
      file = value;
    }
  }

  private List<String> findLines = new ArrayList<String>();

  public void setFindLine(String value) {
    findLines.clear();
    findLines.add(value);
  }

  public void setFindLines(String v0, String v1) {
    findLines.clear();
    findLines.add(v0);
    findLines.add(v1);
  }

  public List<String> getFindLines() {
    return findLines;
  }

  private String combine;

  public void setCombine(String value) {
    combine = value;
  }

  public String getCombine() {
    return combine;
  }

  private String regex;

  public void setRegex(String value) {
    regex = value;
  }

  public String getRegex() {
    return regex;
  }

  private boolean isPaused = false;

  public void releasePause() {
    isPaused = false;
  }

  public void pause() {
    isPaused = true;
    while (isPaused) {
      try {
        Thread.sleep(400);
      } catch (InterruptedException e) {
        Logop.warn(e);
        Thread.currentThread().interrupt();
      }
    }
  }
}
