package com.panopset.compat.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Nls;
import com.panopset.compat.RegexValidator;
import com.panopset.compat.Stringop;

public class RegexAndNlsTest {
  
  @Test
  void testRegex() {
    RegexValidator rv = new RegexValidator("a");
    Assertions.assertTrue(rv.matches("a"));
    Assertions.assertFalse(rv.matches("x"));
    Assertions.assertTrue(rv.matches("ab"));
    Assertions.assertTrue(rv.matches("ba"));
    Assertions.assertFalse(rv.matches("xy"));
    Assertions.assertFalse(rv.matches(""));
    Assertions.assertFalse(rv.matches(null));
    Assertions.assertFalse(rv.matches(""));
    rv = new RegexValidator(null);
    Assertions.assertFalse(rv.matches(null));
    Assertions.assertFalse(rv.matches("a"));
  }
  
  @Test
  void testNls() {
    Assertions.assertEquals(Stringop.FOO, Nls.xlate(Stringop.FOO));
  }

}
