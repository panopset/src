package com.panopset.compat;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PersistentMapFile implements PersistentMap {

	public PersistentMapFile() {
	}

	public PersistentMapFile(File newFile) {
		setFile(newFile);
	}

	public void setFile(final File newFile) {
		file = newFile;
	}

	public void put(String key, String value) {
		if (key == null) {
			Logop.warn("key is null");
			return;
		}
		if (value == null) {
			Logop.warn("value is null");
			return;
		}
		try {
			getProps().setProperty(key, value);
		} catch (IOException ex) {
			Logop.error(ex);
		}
	}

	public Set<Map.Entry<Object, Object>> entrySet() throws IOException {
		return getProps().entrySet();
	}

	@Override
	public String get(String key, String defaultValue) throws IOException {
		if (!Stringop.isPopulated(key)) {
			return defaultValue;
		}
		String rtn = getProps().getProperty(key);
		if (rtn == null) {
			return defaultValue;
		}
		return rtn;
	}

	public String get(String key) throws IOException {
		return get(key, "");
	}

	public void purge() {
		if (Fileop.fileExists(file)) {
			file.delete();
		}
	}

	public void flush() {
		try {
			Propop.save(getProps(), file);
		} catch (IOException ex) {
			Logop.error(ex);
		}
	}

	private Properties getProps() throws IOException {
		if (props == null) {
			props = Propop.load(file);
		}
		return props;
	}

	private File file;
	private Properties props;

	public void load() {
		props = null;
	}

	public String getFileName() {
		if (file == null) {
			return "";
		}
		return Fileop.getCanonicalPath(file);
	}
}
