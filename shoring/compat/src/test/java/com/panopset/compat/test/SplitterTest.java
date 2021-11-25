package com.panopset.compat.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Splitter;

public class SplitterTest {

  @Test
  void testSplitter() {
    List<String> result = Splitter.fixedLength(1).split(null);
    assertEquals(0, result.size());
    result = Splitter.fixedLength(1).split("A");
    assertEquals(1, result.size());
    assertEquals("A", result.get(0));
    result = Splitter.fixedLengths("3").split("A");
    assertEquals(1, result.size());
    assertEquals("A", result.get(0));
    result = Splitter.fixedLength(2).split("ABCD");
    assertEquals(2, result.size());
    assertEquals("CD", result.get(1));
    result = Splitter.fixedLength(2).split("ABCDE");
    assertEquals(3, result.size());
    assertEquals("AB", result.get(0));
    assertEquals("CD", result.get(1));
    assertEquals("E", result.get(2));
    result = Splitter.fixedLengths(2,3).split("ABCDE");
    assertEquals(2, result.size());
    assertEquals("AB", result.get(0));
    assertEquals("CDE", result.get(1));
    result = Splitter.fixedLengths("2,3").split("ABCDE");
    assertEquals(2, result.size());
    assertEquals("AB", result.get(0));
    assertEquals("CDE", result.get(1));
    result = Splitter.fixedLengths("2-").split("ABCDE");
    assertEquals(0, result.size());
  }
}
