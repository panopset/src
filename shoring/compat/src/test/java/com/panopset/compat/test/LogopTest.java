package com.panopset.compat.test;

import java.io.IOException;
import java.util.logging.Level;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.Logop.Listener;
import com.panopset.compat.Logop.LogEntry;
import com.panopset.compat.Stringop;

public class LogopTest {

  LogEntry lr;

  final Listener listener = new Listener() {
    @Override
    public void log(LogEntry logEntry) {
      lr = logEntry;
    }
  };
  
  @Test
  void simpleTest() {
    Logop.clear();
    Logop.info(Stringop.FOO);
    Assertions.assertEquals(1, Logop.getStack().size());
    Logop.info(Stringop.BAR);
  }

  @Test
  void test() throws IOException {
    Logop.clear();
    Logop.getLogger().info(Stringop.FOO);
    Fileop.touch(FileopTest.tempFile);
    Logop.error(Stringop.FOO, FileopTest.tempFile);
    Assertions.assertTrue(Logop.getStack().size() > 0);
    FileopTest.cleanup();
    Logop.clear();
    Logop.addLogListener(listener);
    Logop.dspmsg(Stringop.FOO);
    Assertions.assertEquals(Stringop.FOO, lr.getMessage());
    Assertions.assertEquals(Level.INFO, lr.getLevel());
    Logop.handle(new Exception(Stringop.FOO));
    Assertions.assertEquals(Level.SEVERE, lr.getLevel());
    Logop.debug(Stringop.FOO);
    Assertions.assertEquals(Level.FINE, lr.getLevel());
    Logop.warn(Stringop.FOO);
    Assertions.assertEquals(Level.WARNING, lr.getLevel());
    Logop.turnOnDebugging();
    Logop.warn(Stringop.FOO);
    Assertions.assertEquals(Stringop.FOO, lr.getMessage());
    Assertions.assertEquals(Level.WARNING, lr.getLevel());
    Exception ex = new Exception(Stringop.BAR);
    Logop.error(Stringop.FOO, ex);
    Assertions.assertEquals(7, Logop.getStack().size());
    MatcherAssert.assertThat(12, Matchers.lessThan(Logop.getEntryStackAsText().length()));
    Logop.clear();
    String stackTrace = Logop.getStackTraceAndCauses(ex);
    MatcherAssert.assertThat(12, Matchers.lessThan(stackTrace.length()));
    Logop.clear();
    Exception ex2 = new Exception(ex);
    stackTrace = Logop.getStackTraceAndCauses(ex2);
    MatcherAssert.assertThat(12, Matchers.lessThan(stackTrace.length()));
    Assertions.assertFalse(Logop.isDebugging());
    Logop.turnOnDebugging();
    Assertions.assertTrue(Logop.isDebugging());
    Logop.clear();
    Assertions.assertFalse(Logop.isDebugging());
    for (int i=0; i<1001; i++) {
      Logop.dspmsg(String.format("%s:%d", Stringop.FOO, i));
    }
    Assertions.assertEquals(901, Logop.getStack().size());
    Logop.clear();
    Logop.debug(Stringop.FOO);
    Assertions.assertEquals(1, Logop.getStack().size());
  }

}
