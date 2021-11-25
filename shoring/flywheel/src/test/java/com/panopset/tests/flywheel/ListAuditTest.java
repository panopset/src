package com.panopset.tests.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.panopset.flywheel.ListAudit;

public class ListAuditTest {
  
  @Test
  void testListAudit() {
    ListAudit la = new ListAudit();
    la.add("foo", Arrays.asList(new String[]{"a", "b"}));
    la.add("bar", Arrays.asList(new String[]{"b", "c"}));
    la.add("zlist", Arrays.asList(new String[]{"x", "y"}));
    la.add("alist", Arrays.asList(new String[]{"b", "a"}));
    List<String> report = la.getReport();
    assertEquals(6, report.size());
    assertEquals(",alist,bar,foo,zlist", report.get(0));
    assertEquals("a,*,,*,", report.get(1));
    assertEquals("b,*,*,*,", report.get(2));
    assertEquals("c,,*,,", report.get(3));
    assertEquals("x,,,,*", report.get(4));
    assertEquals("y,,,,*", report.get(5));
  }
}
