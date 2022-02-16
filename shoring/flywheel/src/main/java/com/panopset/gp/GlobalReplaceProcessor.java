package com.panopset.gp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.panopset.compat.FileProcessor;
import com.panopset.compat.Fileop;
import com.panopset.compat.LineFilter;
import com.panopset.compat.Logop;
import com.panopset.compat.RegexValidator;
import com.panopset.compat.Stringop;

public class GlobalReplaceProcessor {
	private final File file;
	private final String searchStr;
	private final String replacementStr;
	private final String extensionsList;
	private final boolean recursive;
	private String regex = "";
	private final List<LineFilter> filters = new ArrayList<>();
	private String priorLineMustContain;
	private String replacementLineMustContain;

	public GlobalReplaceProcessor(File file, String searchStr, String replacementStr, String extensionsList,
			String regex, boolean recursive) {
		this.file = file;
		this.searchStr = searchStr;
		this.replacementStr = replacementStr;
		this.extensionsList = extensionsList;
		this.recursive = recursive;
	}

	public void addLineFilter(LineFilter lineFilter) {
		filters.add(lineFilter);
	}

	public void process() throws IOException {
		final RegexValidator regexTester = Stringop.isPopulated(regex) ? null : new RegexValidator(regex);
		if (!Stringop.isPopulated(searchStr) && filters.isEmpty()) { // TODO: AND CRLF = do nothing.
			Logop.warn("No replacement specified, exiting.");
			return;
		}
		LineFilter lineFilter = new LineFilter() {

			@Override
			public boolean fileFilter(File file) {
				if (file.isDirectory()) {
					Logop.warn(String.format("Ignoring directory: %s", Fileop.getCanonicalPath(file)));
					return false;
				}
				if (Stringop.isPopulated(extensionsList) && !Fileop.isFileOneOfExtensions(file, extensionsList)) {
					return false;
				}
				if (!Stringop.isPopulated(extensionsList) && regexTester != null) {
					if (!regexTester.matches(file.getName())) {
						return false;
					}
				}
				return true;
			}

			@Override
			public String filter(String str) {
				String rtn = str;
				if (Stringop.isPopulated(getPriorLineMustContain())) {
					if (getPriorLine().contains(getPriorLineMustContain())) {
						rtn = str.replaceAll(searchStr, replacementStr);
					}
				} else {
					rtn = str.replaceAll(searchStr, replacementStr);
				}
				setPriorLine(str);
				return rtn;
			}

			String priorLine;

			public String getPriorLine() {
				if (priorLine == null) {
					priorLine = "";
				}
				return priorLine;
			}

			public void setPriorLine(String priorLine) {
				this.priorLine = priorLine;
			}
		};
		FileProcessor fp = new FileProcessor(file, lineFilter, recursive);
		fp.addFilters(filters);
		fp.exec();
		Logop.green("Done");
	}

	public void setPriorLineMustContain(String priorLineMustContain) {
		this.priorLineMustContain = priorLineMustContain;
	}

	public String getPriorLineMustContain() {
		if (priorLineMustContain == null) {
			priorLineMustContain = "";
		}
		return priorLineMustContain;
	}

	public void setReplacementLineMustContain(String replacementLineMustContain) {
		this.replacementLineMustContain = replacementLineMustContain;
	}

	public String getReplacementLineMustContain() {
		if (replacementLineMustContain == null) {
			replacementLineMustContain = "";
		}
		return replacementLineMustContain;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
