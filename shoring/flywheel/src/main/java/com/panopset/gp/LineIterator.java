package com.panopset.gp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.panopset.compat.Logop;

public class LineIterator {
	List<String> lines = new ArrayList<>();
	int i = 0;

	public LineIterator(Reader reader) {
		try (BufferedReader br = new BufferedReader(reader)) {
			String str = br.readLine();
			while (str != null) {
				lines.add(str);
				str = br.readLine();
			}
		} catch(IOException ex) {
			Logop.handle(ex);
		}
		
	}

	public String next() {
		if (hasNext()) {
			return lines.get(i++);
		}
		return "";
	}

	public boolean hasNext() {
		return i < lines.size();
	}
}
