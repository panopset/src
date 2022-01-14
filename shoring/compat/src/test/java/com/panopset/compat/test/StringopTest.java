package com.panopset.compat.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Randomop;
import com.panopset.compat.Stringop;

public class StringopTest {

	final List<String> emptyList = new ArrayList<>();
	final String[] fooBarArray = new String[] { "foo", "bar" };
	final String[] fooBarArrayWithEOL = new String[] { "foo\r\n", "bar\r\n" };

	@Test
	void test() {
		Stringop.setEol(Stringop.DOS_RTN);
		assertTrue(Stringop.isBlank(null));
		assertTrue(Stringop.isBlank(""));
		assertTrue(Stringop.isBlank(" "));
		assertTrue(Stringop.isBlank("\t"));
		assertTrue(Stringop.isBlank(Stringop.DOS_RTN));
		assertTrue(Stringop.isBlank("\t \n"));
		assertFalse(Stringop.isBlank("\ta \n"));
		assertTrue(Stringop.isPopulated("x"));
		assertFalse(Stringop.isPopulated("\t "));
		assertFalse(Stringop.isPopulated("\t"));
		assertEquals("Foo", Stringop.capitalize("foo"));
		assertEquals("", Stringop.capitalize(""));
		assertEquals("A", Stringop.capitalize("a"));
		assertEquals("", Stringop.capitalize(null));
		assertEquals(Stringop.DOS_RTN, Stringop.getEol());
		Stringop.setEol("\n");
		assertEquals("\n", Stringop.getEol());
		assertEquals(3, Stringop.countReturns("\n\nasdf\n"));
		assertEquals(0, Stringop.countReturns(""));
		assertEquals(0, Stringop.countReturns(null));
		Stringop.setEol(Stringop.DOS_RTN);
		assertEquals(emptyList, Stringop.stringToList(null));
		assertEquals(Arrays.asList(fooBarArray), Stringop.stringToList("foo\r\nbar\r\n"));
		assertEquals(Arrays.asList(fooBarArray), Stringop.stringToList("foo\r\nbar"));
		assertEquals(Arrays.asList(fooBarArray), Stringop.stringToList("foo\r\nbar"));
		assertEquals(Arrays.asList(fooBarArrayWithEOL), Stringop.stringToListWithEol("foo\r\nbar"));
		assertEquals(Arrays.asList(fooBarArrayWithEOL), Stringop.stringToListWithEol("foo\r\nbar\r\n"));
		assertEquals(Arrays.asList("\r\n", "bar\r\n"), Stringop.stringToListWithEol("\r\nbar"));
		assertEquals(emptyList, Stringop.stringToListWithEol(null));
		Stringop.setEol("\n");
	}

	@Test
	void toMaxLengthTest() {
		assertEquals("", Stringop.toMaxLength(null, Randomop.random(-5, 5)));
		assertEquals("", Stringop.toMaxLength("", Randomop.random(-5, 5)));
		assertEquals(Stringop.FOO, Stringop.toMaxLength(Stringop.FOO, 100));
		assertEquals("", Stringop.toMaxLength(Stringop.FOO, -1));
		assertEquals("", Stringop.toMaxLength(Stringop.FOO, 0));
		assertEquals(Stringop.FOO, Stringop.toMaxLength(Stringop.FOO, 3));
		assertEquals("fo", Stringop.toMaxLength(Stringop.FOO, 2));
		assertEquals("f", Stringop.toMaxLength(Stringop.FOO, 1));
	}

	@Test
	void linesToListTest() {
		assertEquals(emptyList, Stringop.stringToList(null));
		assertEquals(emptyList, Stringop.stringToList(""));
		assertEquals(Arrays.asList("foo"), Stringop.stringToList("foo"));
		assertEquals(Arrays.asList("foo"), Stringop.stringToList("foo\n"));
		assertEquals(Arrays.asList("foo"), Stringop.stringToList("foo\r\n"));
		assertEquals(Arrays.asList("foo", "bar"), Stringop.stringToList("foo\r\nbar"));
		assertEquals(Arrays.asList("foo", "bar"), Stringop.stringToList("foo\nbar"));
	}

	@Test
	void testSkipComment() {
		assertTrue(Stringop.skipComment(null));
		assertTrue(Stringop.skipComment("#"));
		assertTrue(Stringop.skipComment("//"));
		assertFalse(Stringop.skipComment("/"));
		assertTrue(Stringop.skipComment("#asdf"));
		assertTrue(Stringop.skipComment("//asdf"));
		assertFalse(Stringop.skipComment("foo"));
		assertFalse(Stringop.skipComment("x"));
		assertTrue(Stringop.skipComment(""));
	}

	@Test
	void testMathFilters() {
		assertTrue(Stringop.isInteger("0"));
		assertTrue(Stringop.isInteger("1"));
		assertTrue(Stringop.isInteger("2"));
		assertTrue(Stringop.isInteger("-1"));
		assertTrue(Stringop.isInteger("-2"));
		assertTrue(Stringop.isInteger("-999"));
		assertTrue(Stringop.isNumber("0"));
		assertTrue(Stringop.isNumber("1"));
		assertTrue(Stringop.isNumber("2"));
		assertFalse(Stringop.isNumber("-1"));
		assertFalse(Stringop.isNumber("-999"));
		assertEquals(0, Stringop.parseInt(null));
		assertEquals(0, Stringop.parseInt(""));
		assertEquals(0, Stringop.parseInt("x"));
		assertEquals(5323, Stringop.parseInt("5,323"));
		assertEquals(5, Stringop.parseInt("5"));
		assertEquals("$5.00", Stringop.getDollarString(500));
		assertEquals("$5.95", Stringop.getDollarString(595));
		assertEquals("$1,002.05", Stringop.getDollarString(100205));
		assertEquals(5, Stringop.parseInt("5", 0));
		assertEquals(5, Stringop.parseInt("5", 5));
		assertEquals(0, Stringop.parseInt("", null));
		assertEquals(5, Stringop.parseInt("5", null));
		assertEquals(0, Stringop.parseInt(null, null));
		assertEquals(5, Stringop.parseInt(null, 5));
		assertEquals(0, Stringop.parseInt("x", null));
		assertEquals(5, Stringop.parseInt("x", 5));
		assertEquals(5, Stringop.parseInt("+5"));
	}

	@Test
	void isRegexFoundTest() {
		assertTrue(Stringop.isRegexFound("foo", "foo"));
		assertTrue(Stringop.isRegexFound("foo", "foobar"));
		assertTrue(Stringop.isRegexFound("foo", "xfoobar"));
		assertFalse(Stringop.isRegexFound("foo", "bar"));
	}

	@Test
	void pullAfterTest() {
		assertEquals("com/panopset/jnlp/games",
				Stringop.pullAfter("p_some_apps/src/main/java/com/panopset/jnlp/games", "src/main/java/"));
		assertEquals("", Stringop.pullAfter("foo", null));
		assertEquals("", Stringop.pullAfter(null, "foo"));
		assertEquals("", Stringop.pullAfter("foo", "foobar"));
		assertEquals("", Stringop.pullAfter("foo", "bar"));
	}

	@Test
	void trimTrailingEmptyLinesTest() {
		String[] fubar = new String[] { "foo", "", "bar", "" };
		assertArrayEquals(new String[] { "foo", "", "bar" }, Stringop.trimTrailingEmptyLines(fubar));
		assertArrayEquals(new String[] {}, Stringop.trimTrailingEmptyLines(null));
		assertArrayEquals(new String[] {}, Stringop.trimTrailingEmptyLines(new String[] {}));
		assertArrayEquals(new String[] { "foo" }, Stringop.trimTrailingEmptyLines(new String[] { "foo" }));
		assertArrayEquals(new String[] { "foo" }, Stringop.trimTrailingEmptyLines(new String[] { "foo", "" }));
	}

	@Test
	void concatTest() {
		assertEquals("foobar", Stringop.concat("foo", "bar"));
		assertEquals("", Stringop.concat("", ""));
		assertEquals("foobar", Stringop.concat("foo", "", "bar"));
		assertEquals("foo", Stringop.concat(null, "foo"));
		assertEquals("", Stringop.concat((String) null));
		assertEquals("", Stringop.concat((List<String>) null));
	}

	@Test
	void check4matchTest() {
		assertEquals("bat", Stringop.check4match("foo", "foo", "bat", "baz"));
		assertEquals("baz", Stringop.check4match("foo", "bar", "bat", "baz"));
		assertEquals("", Stringop.check4match(null, "bar", "bat", "baz"));
		assertEquals("", Stringop.check4match("foo", null, "bat", "baz"));
		assertEquals("", Stringop.check4match("foo", "bar", null, "baz"));
		assertEquals("", Stringop.check4match("foo", "bar", "bat", null));
	}

	@Test
	void wrapFixedWidthTest() {
		Stringop.setEol(Stringop.DOS_RTN);
		assertEquals("fo\r\no", Stringop.wrapFixedWidth("foo", 2));
		assertEquals("", Stringop.wrapFixedWidth(null, 2));
		assertEquals("", Stringop.wrapFixedWidth("", 2));
		assertEquals("foo", Stringop.wrapFixedWidth("foo", 0));
		assertEquals("foo", Stringop.wrapFixedWidth("foo", 5));
		Stringop.setEol("\n");
	}

	@Test
	void testNotNull() {
		assertEquals("", Stringop.notNull(null));
		assertEquals("", Stringop.notNull(""));
		assertEquals("foo", Stringop.notNull("foo"));
	}

	@Test
	void testArrayToList() {
		assertEquals(0, Stringop.arrayToList(null).size());
		assertEquals(0, Stringop.arrayToList(new String[] {}).size());
		assertEquals("bar", Stringop.arrayToList(new String[] { "foo", "bar" }).get(1));
	}

	@Test
	void testToLowerCase() {
		assertEquals("foo", Stringop.toLowerCase("Foo"));
		assertEquals("", Stringop.toLowerCase(null));
		assertEquals("foo", Stringop.toLowerCase("FOO"));
		assertEquals("foo", Stringop.toLowerCase("fOO"));
	}

	@Test
	void testUniqueIds() {
		List<String> ids = new ArrayList<>();
		String str = Stringop.getNextJvmUniqueIDstr();
		for (int i = 0; i < 1000; i++) {
			ids.add(str);
			assertNotEquals(str, str = Stringop.getNextJvmUniqueIDstr());
			assertFalse(ids.contains(str));
		}
	}

	@Test
	void testParseBoolean() {
		assertFalse(Stringop.parseBoolean(null));
		assertFalse(Stringop.parseBoolean(""));
		assertFalse(Stringop.parseBoolean("42"));
		assertFalse(Stringop.parseBoolean("foo"));
		assertFalse(Stringop.parseBoolean("FalSe"));
		assertFalse(Stringop.parseBoolean("false"));
		assertFalse(Stringop.parseBoolean("no"));
		assertTrue(Stringop.parseBoolean("trUe"));
		assertTrue(Stringop.parseBoolean("on"));
		assertTrue(Stringop.parseBoolean("yes"));
		assertTrue(Stringop.parseBoolean("Y"));
	}

	@Test
	void testCapund() {
		assertEquals("FOO_BAR", Stringop.capund("FooBar"));
		assertEquals("FOO", Stringop.capund("Foo"));
		assertEquals("FOO", Stringop.capund("foo"));
	}
}
