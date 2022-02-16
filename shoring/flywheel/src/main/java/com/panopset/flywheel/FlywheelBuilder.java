package com.panopset.flywheel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.Nls;
import com.panopset.compat.Propop;
import com.panopset.compat.Stringop;
import com.panopset.gp.TextFileProcessor;

public final class FlywheelBuilder {

	private InputStream inputStream;
	private boolean isOutputEnabled = true;
	private LineFeedRules lineFeedRules;
	private StringWriter writer;
	private File targetDirectory;
	private String[] array;
	private TemplateSource sls;
	private File scriptFile;
	private String baseDirectoryPath;
	private Map<String, String> map = new HashMap<>();
	private Map<String, Object> registeredObjects = new HashMap<>();

	/**
	 * Create a script. Example usage:
	 *
	 * <pre>
	 * new Script.Builder()
	 * 
	 * 		.targetDirectory(&quot;publish/site&quot;).scriptFile(
	 * 
	 * 				&quot;templates/index.txt&quot;)
	 * 		.properties(
	 * 
	 * 				&quot;my.properties&quot;) // Script
	 * 		// variables.
	 * 		.constructScript().exec();
	 * </pre>
	 *
	 * @return Panopset Flywheel Script object.
	 */
	public Flywheel construct() {
		Flywheel flywheel = new Flywheel(getStringLineSupplier());
		flywheel.setBaseDirectoryPath(getBaseDirectoryPath());
		flywheel.mergeMap(map);
		flywheel.setReplacements(replacements);
		flywheel.setTargetDirectory(Fileop.getCanonicalPath(getTargetDirectory()));
		flywheel.put(ReservedWords.TARGET, Fileop.getCanonicalPath(getTargetDirectory()));
		flywheel.setWriter(writer);
		registeredObjects.put(ReservedWords.FLYWHEEL, flywheel);
		flywheel.setRegisteredObjects(registeredObjects);
		flywheel.setFile(getScriptFile());
		flywheel.setOutputEnabled(isOutputEnabled);
		flywheel.setLineFeedRules(getLineFeedRules());
		return flywheel;
	}

	/**
	 * Set target directory. This is where your output is going. Directory
	 * structures will be created as needed, and <b>existing files are wiped without
	 * warning</b>.
	 *
	 * @param newTargetDirectory All output files specified by the ${&#064;f
	 *                           fileName} command are relative paths based here.
	 * @return Builder.
	 */
	public FlywheelBuilder targetDirectory(final File newTargetDirectory) {
		targetDirectory = newTargetDirectory.getAbsoluteFile();
		return this;
	}

	/**
	 * Set script file.
	 *
	 * @param value Specify a single controlling script file.
	 * @return Builder.
	 */
	public FlywheelBuilder file(final File value) {
		scriptFile = value;
		return this;
	}

	/**
	 * You do not need to specify this if you are specifying a File in
	 * Flywheel.Builder.file, as the parent directory of the main template file
	 * would be used. This method would rarely be needed, but is available for
	 * flexibility.
	 *
	 * @param path Base directory path.
	 * @return Builder.
	 */
	public FlywheelBuilder baseDirectoryPath(final String path) {
		baseDirectoryPath = path;
		return this;
	}

	/**
	 * If you supply a String array here, the <b>file</b> Flywheel.Builder method
	 * will be ignored.
	 *
	 * @param newStringArray Script commands in String array.
	 * @return Builder.
	 */
	public FlywheelBuilder input(final String[] newStringArray) {
		array = newStringArray;
		return this;
	}

	/**
	 * The <b>file</b> Flywheel.Builder method will be ignored if you supply a
	 * String template here.
	 * 
	 * @param template Simple single String template.
	 * @return Builder.
	 */
	public FlywheelBuilder input(final String template) {
		return input(new String[] { template });
	}

	/**
	 * If you supply a List of Strings here, do not use the <b>file</b>
	 * Flywheel.Builder method.
	 *
	 * @param newInput Script commands in String List.
	 * @return Builder.
	 */
	public FlywheelBuilder input(final List<String> newInput) {
		final String[] sa = new String[newInput.size()];
		int incr = 0;
		for (String s : newInput) {
			sa[incr++] = s;
		}
		return this.input(sa);
	}

	public FlywheelBuilder input(Class<?> clazz, String resourcePath) {
		return input(clazz.getResourceAsStream(resourcePath));
	}

	public FlywheelBuilder inputResourcePath(Class<?> clazz, String resourcePath) {
		return input(clazz.getResourceAsStream(resourcePath));
	}

	public FlywheelBuilder input(InputStream value) {
		inputStream = value;
		return this;
	}

	public FlywheelBuilder withLineFeedRules(LineFeedRules lineFeedRules) {
		this.lineFeedRules = lineFeedRules;
		return this;
	}

	/**
	 * Defaults to &quot;temp&quot;.
	 *
	 * @return target directory File.
	 */
	private File getTargetDirectory() {
		if (targetDirectory == null) {
			String targetDirName = this.map.get(ReservedWords.TARGET);
			if (Stringop.isPopulated(targetDirName)) {
				return new File(targetDirName);
			}
			return new File("temp");
		}
		return targetDirectory;
	}

	private File getScriptFile() {
		if (scriptFile == null) {
			String scriptFileName = this.map.get(ReservedWords.SCRIPT);
			if (Stringop.isPopulated(scriptFileName)) {
				scriptFile = new File(scriptFileName);
				return scriptFile;
			}
			scriptFile = new File("com.panopset.flywheel.EmptyFile.txt");
		}
		return scriptFile;
	}

	private String getBaseDirectoryPath() {
		if (!Stringop.isPopulated(baseDirectoryPath)) {
			baseDirectoryPath = Fileop.getCanonicalPath(getScriptFile().getAbsoluteFile().getParentFile());
		}
		return baseDirectoryPath;
	}

	private TemplateSource getStringLineSupplier() {
		if (sls == null) {
			if (array != null) {
				sls = new TemplateArray(array);
				return sls;
			}
			if (inputStream != null) {
				sls = new TemplateInputStream(inputStream);
				return sls;
			}
			sls = new TemplateFile(getScriptFile());
		}
		return sls;
	}

	private LineFeedRules getLineFeedRules() {
		if (lineFeedRules == null) {
			lineFeedRules = new LineFeedRules();
		}
		return lineFeedRules;
	}

	/**
	 * You may pre-define variables by supplying a map. Please remember to stay away
	 * from the the numbers, as they are used in lists.
	 *
	 * @param additionalMap Additional map. In the case of duplicate keys, the most
	 *                      recently added map takes precedence.
	 * @return Builder.
	 */
	public FlywheelBuilder map(final Map<String, String> additionalMap) {
		Javop.copyMap(additionalMap, map);
		return this;
	}

	/**
	 * Add a key value pair to the map.
	 *
	 * @param key   Variable command name.
	 * @param value Variable value.
	 * @return Builder.
	 */
	public FlywheelBuilder map(final String key, final String value) {
		this.map.put(key, value);
		return this;
	}

	/**
	 * Convert Object values using toString, prior to applying to map.
	 *
	 * @param additionalMap Additional map. In the case of duplicate keys, the most
	 *                      recently added map takes precedence.
	 * @return Builder.
	 */
	public FlywheelBuilder mapObjects(final Map<String, Object> additionalMap) {
		Javop.copyMapObjects(additionalMap, map);
		return this;
	}

	/**
	 * Specify list line tokens, default is none ("").
	 *
	 * @param tokens List line tokens.
	 * @return Builder.
	 */
	public FlywheelBuilder tokens(final String tokens) {
		map.put(ReservedWords.TOKENS, tokens);
		return this;
	}

	/**
	 * Register any instantiated objects you want available to the Execute command
	 * here. You will be able to use any method of those objects that accept 0 or
	 * more String parameters, and that return a single String result. Recommended,
	 * but not required, to qualify objects with their path names.
	 * com.panopset.flywheel.Flywheel is registered automatically.
	 *
	 * @param key Name object will be known by, in Execute commands.
	 * @param obj Instance Execute commands will invoke methods on.
	 * @return Builder.
	 */
	public FlywheelBuilder registerObject(final String key, final Object obj) {
		registeredObjects.put(key, obj);
		return this;
	}

	/**
	 * Load properties file into pre-defined variables.
	 *
	 * @see FlywheelBuilder#map
	 * @param propertiesFile Properties file.
	 * @return Builder.
	 */
	public FlywheelBuilder properties(final File propertiesFile) throws IOException {
		if (propertiesFile != null && propertiesFile.exists()) {
			return map(Propop.loadPropsFromFile(propertiesFile));
		}
		if (propertiesFile != null) {
			Logop.warn(Fileop.getCanonicalPath(propertiesFile) + " " + Nls.xlate("does not exist, skipping."));
		}
		return this;
	}

	public FlywheelBuilder properties(final Properties properties) {
		return map(Propop.loadMapFromProperties(properties));
	}

	/**
	 * Define replacements in a file specified by the replacements parameter. First
	 * line of that file contains a single character that is the separator for
	 * subsequent lines that define replacements.
	 *
	 * @param replacementsFile file.
	 * @return Builder.
	 */
	public FlywheelBuilder replacements(final File replacementsFile) {
		final List<String[]> v = new ArrayList<>();
		try {
			TextFileProcessor tfp = TextFileProcessor.textFileIterator(replacementsFile);
			String separator = null;
			while (tfp.hasNext()) {
				String str = tfp.next();
				if (separator == null) {
					separator = str;
				} else {
					StringTokenizer st = new StringTokenizer(str, separator);
					v.add(new String[] { st.nextToken(), st.nextToken() });
				}
			}
			for (String[] r : v) {
				replacement(r);
			}
		} catch (IOException ex) {
			Logop.error(ex);
		}
		return this;
	}

	/**
	 * Add a global replacement. Replacements are performed in the order added.
	 *
	 * @param sa String Array sa[0] = from sa[1] = to.
	 * @return Builder.
	 */
	public FlywheelBuilder replacement(final String[] sa) {
		return replacement(sa[0], sa[1]);
	}

	/**
	 * Add a global replacement. Replacements are performed in the order added.
	 *
	 * @param from String to replace with the to String.
	 * @param to   String to put in place of the from String.
	 * @return Builder.
	 */
	public FlywheelBuilder replacement(final String from, final String to) {
		getReplacements().add(new String[] { from, to });
		return this;
	}

	private List<String[]> replacements;

	private List<String[]> getReplacements() {
		if (replacements == null) {
			replacements = new ArrayList<>();
		}
		return replacements;
	}

	/**
	 * If you specify a writer, you need not specify a targetDirectory, as it would
	 * be meaningless, and any File commands are ignored, as you are intending your
	 * output to be a StringWriter.
	 *
	 * @param newWriter StringWriter to write output to.
	 * @return Builder.
	 */
	public FlywheelBuilder withWriter(final StringWriter newWriter) {
		writer = newWriter;
		isOutputEnabled = false;
		return this;
	}
}
