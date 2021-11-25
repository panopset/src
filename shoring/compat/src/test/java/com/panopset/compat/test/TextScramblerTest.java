package com.panopset.compat.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Randomop;
import com.panopset.compat.Stringop;
import com.panopset.compat.TextScrambler;

public class TextScramblerTest {

  private static final String TEST_PWD = "4traN";
  private static final String TEN_MILES = "ten miles";
  
  private static final String MANY_HAPPY_RETURNS = "FRqy4hq6gPqAWnN-D-gQ3wQG"
      + Stringop.DOS_RTN + "pPsEzqGA7yu7F8sczbGBMM8_wmeBZg==\n" + Stringop.DOS_RTN;
  private final String txtLong = "This is not very long but long enough";
  private String txtShort = "FOO";
  private String txtRandom = "" + Randomop.nextLong();

  @Test
  void testScrambler() throws Exception {
    ts = new TextScrambler();
    testScramblerOn(txtShort);
    testScramblerOn(txtLong);
    testScramblerOn(txtRandom);
    testScramblerOn(TEN_MILES);
    assertEquals(TEN_MILES, ts.decrypt(TEST_PWD, MANY_HAPPY_RETURNS));
  }

  private void testScramblerOn(String txt0) throws Exception {
    String txt1 = txt0 + "a";
    String scrt0 = ts.encrypt(TEST_PWD, txt0);
    String scrt1 = ts.encrypt(TEST_PWD, txt1);
    assertFalse(scrt0.equals(txt0));
    assertFalse(scrt1.equals(txt1));
    assertFalse(scrt0.substring(0, 20).equals(scrt1.substring(0, 20)));
    String rslt0 = ts.decrypt(TEST_PWD, scrt0);
    String rslt1 = ts.decrypt(TEST_PWD, scrt1);
    assertEquals(rslt0, txt0);
    assertEquals(rslt1, txt1);

  }

  TextScrambler ts;
}
