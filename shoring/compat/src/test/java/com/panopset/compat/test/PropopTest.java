package com.panopset.compat.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.panopset.compat.Fileop;
import com.panopset.compat.Propop;
import com.panopset.compat.Stringop;

public class PropopTest {

  @BeforeEach
  public void beforeEach() throws IOException {
    FileopTest.cleanup();
  }

  @Test
  void test() throws IOException {
    File file = FileopTest.tempFile;
    createSampleData(file);
    Map<String, String> map = Propop.loadPropsFromFile(FileopTest.tempFile);
    Assertions.assertEquals(Stringop.BAR, map.get(Stringop.FOO));
    map = Propop.loadPropsFromFile(null);
    Assertions.assertEquals(0, map.size());
    Properties props = Propop.load(null);
    Assertions.assertEquals(0, props.size());
    props = Propop.load(file);
    Assertions.assertEquals(1, props.size());
    Propop.load(props, null);
    Assertions.assertEquals(1, props.size());
    props.clear();
    props.put("bar", "foo");
    Propop.load(null, file);
    Propop.load(props, file);
    Propop.load(props, null);
    Propop.load(null, null);
    Assertions.assertEquals(2, props.size());
    Propop.save(null, file);
    Propop.save(props, null);
    Propop.save(null, null);
    Propop.save(props, file);
    props = Propop.load(file);
    Assertions.assertEquals(2, props.size());
  }

  @Test
  void emptyTest() throws IOException {
    Map<String, String> map = Propop.loadPropsFromFile(FileopTest.tempFile);
    Assertions.assertEquals(0, map.size());
  }

  private void createSampleData(File file) throws IOException {
    String sampleProp = "foo=bar";
    Fileop.write(sampleProp, file);
  }
}
