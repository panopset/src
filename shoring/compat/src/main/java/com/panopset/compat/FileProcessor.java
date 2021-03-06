package com.panopset.compat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
	private final File targetFile;
	private final boolean isRecursive;

	public FileProcessor(File file) {
		this(file, true);
	}

	public FileProcessor(File file, boolean isRecursive) {
		targetFile = file;
		this.isRecursive = isRecursive;
	}
	
	public FileProcessor(File file, LineFilter lineFilter) {
		this(file, lineFilter, true);
	}
	
	public FileProcessor(File file, LineFilter lineFilter, boolean isRecursive) {
		this(file, isRecursive);
		lineFilters.add(lineFilter);
	}

	private final List<LineFilter> lineFilters = new ArrayList<>();

	public FileProcessor withLineFilter(LineFilter lf) {
		lineFilters.add(lf);
		return this;
	}

	public boolean exec() {
		if (isRecursive) {
			return recursiveExec(targetFile);
		}
		return new Report(targetFile, lineFilters).exec();
	}

	private boolean recursiveExec(File rFile) {
		if (rFile.isDirectory()) {
			for (File f : rFile.listFiles()) {
				if (!recursiveExec(f)) {
					return false;
				}
			}
		} else {
			return new Report(rFile, lineFilters).exec();
		}
		return true;
	}

	public void addFilters(List<LineFilter> filters) {
		if (filters == null || filters.isEmpty()) {
			return;
		}
		for (LineFilter lf : filters) {
			lineFilters.add(lf);
		}
	}
}
