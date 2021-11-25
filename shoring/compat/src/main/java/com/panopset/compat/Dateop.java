package com.panopset.compat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Dateop {
	public static ZoneId ZONE_ID = ZoneId.of("America/Los_Angeles");

	public static String show(LocalDate date) {
		return YMD_FMT.format(date);
	}

	public static LocalDate parse(String source) {
		return LocalDate.parse(source.trim(), YMD_FMT);
	}

	private static final DateTimeFormatter YMD_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static String fill2(int val) {
		return val < 10 ? "0" + val : "" + val;
	}

	public static String createDateKey(LocalDate ld) {
		return createDateKey(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
	}

	public static String createMonthKey(LocalDate ld) {
		return createMonthKey(ld.getYear(), ld.getMonthValue());
	}

	public static String createDateKey(int yr, int mn, int dy) {
		return String.format("%s-%s", createMonthKey(yr, mn), Dateop.fill2(dy));
	}

	public static String createMonthKey(int yr, int mn) {
		return String.format("%d-%s", yr, Dateop.fill2(mn));
	}

	public static Date toPacific(LocalDate oldest) {
		// http://stackoverflow.com/questions/33066904
		return Date.from(oldest.atStartOfDay(ZONE_ID).toInstant());
	}

	public static LocalDate toLocalDate(Date date) {
		// http://stackoverflow.com/questions/21242110
		return Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDate();
	}
}
