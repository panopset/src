package com.panopset.compat.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Streamop;
import com.panopset.compat.Stringop;

public final class StreamopTest {

  /**
   * Test Streamop with byte arrays.
   */
  @Test
  void test() throws IOException {
    String rand = Stringop.randomString();
    ByteArrayInputStream bis = new ByteArrayInputStream(rand.getBytes());
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Streamop.copyStream(bis, bos);
    Assertions.assertEquals(rand, bos.toString());
    Assertions.assertEquals(rand,
        Streamop.getTextFromStream(new ByteArrayInputStream(rand.getBytes())));
  }

  @Test
  void testNulls() throws IOException {
    String rand = Stringop.randomString();
    Streamop.copyStream(new ByteArrayInputStream(rand.getBytes()), null);
    String rtn = Streamop.getTextFromStream(null);
    Assertions.assertEquals("", rtn);
  }

  @Test
  void testErrors() {
    String rslt = Streamop.getTextFromStream(new InputStream() {

      @Override
      public int read() throws IOException {
        throw new IOException("Make sure exception returns blank.");
      }

    });
    Assertions.assertEquals("", rslt);
    List<String> list = Streamop.getLinesFromReader(new Reader() {

      @Override
      public void close() {
        
      }

      @Override
      public int read(char[] arg0, int arg1, int arg2) throws IOException {
        throw new IOException("Make sure exception returns empty list.");
      }
    });
    Assertions.assertEquals(new ArrayList<String>(), list);
    list = Streamop.getLinesFromReaderWithEol(new Reader() {

      @Override
      public void close() {
        
      }

      @Override
      public int read(char[] arg0, int arg1, int arg2) throws IOException {
        throw new IOException("Make sure exception returns empty list.");
      }
    });
    Assertions.assertEquals(new ArrayList<String>(), list);
  }

}
