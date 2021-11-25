package com.panopset.compat.test;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.panopset.compat.DirectoryProcessor;
import com.panopset.compat.Fileop;
import com.panopset.compat.DirectoryProcessor.Listener;

public class DirectoryProcessorTest {

  static final File dir = new File("./testdir");
  static final File temp = new File(Fileop.combinePaths(dir, "temp.txt"));
  static final File subdir = new File(Fileop.combinePaths(dir, "subdir"));
  static final File temp0 = new File(Fileop.combinePaths(subdir, "temp0.txt"));

  int count;
  
  @BeforeEach
  public void beforeEach() throws IOException {
    Fileop.delete(dir);
    Assertions.assertFalse(dir.exists()); 
  }

  @Test
  void test() throws IOException {
    Fileop.mkdirs(dir);
    Assertions.assertTrue(dir.exists());
    Assertions.assertTrue(dir.isDirectory());
    Fileop.touch(temp);
    Assertions.assertTrue(temp.exists());
    DirectoryProcessor dp = new DirectoryProcessor(dir, new DirectoryProcessor.Listener() {
      
      @Override
      public boolean processFile(File file) {
        Assertions.assertTrue(file.exists());
        return true;
      }
    });
    dp.exec();
  }


  @Test
  void testFalseProcess() throws IOException {
    Fileop.mkdirs(dir);
    Assertions.assertTrue(dir.exists());
    Assertions.assertTrue(dir.isDirectory());
    Fileop.touch(temp);
    Assertions.assertTrue(temp.exists());
    DirectoryProcessor dp = new DirectoryProcessor(dir, new DirectoryProcessor.Listener() {
      
      @Override
      public boolean processFile(File file) {
        Assertions.assertTrue(file.exists());
        return false;
      }
    });
    dp.exec();
  }

  @Test
  void testRecursive() throws IOException {
    Fileop.mkdirs(dir);
    Assertions.assertTrue(dir.exists());
    Assertions.assertTrue(dir.isDirectory());
    Fileop.touch(temp);
    Assertions.assertTrue(temp.exists());
    Fileop.touch(temp0);
    Assertions.assertTrue(temp0.exists());
    count = 0;
    DirectoryProcessor dp = new DirectoryProcessor(dir, new DirectoryProcessor.Listener() {

      @Override
      public boolean processFile(File file) {
        Assertions.assertTrue(file.exists());
        count++;
        return true;
      }
      
    });
    dp.exec();
    Assertions.assertEquals(2, count);
  }

  @Test
  void testNonRecursive() throws IOException {
    Fileop.mkdirs(dir);
    Assertions.assertTrue(dir.exists());
    Assertions.assertTrue(dir.isDirectory());
    Fileop.touch(temp);
    Assertions.assertTrue(temp.exists());
    Fileop.touch(temp0);
    Assertions.assertTrue(temp0.exists());
    count = 0;
    DirectoryProcessor dp = new DirectoryProcessor(dir, new DirectoryProcessor.Listener() {
      
      @Override
      public boolean processFile(File file) {
        Assertions.assertTrue(file.exists());
        count++;
        return true;
      }
    }, false);
    dp.exec();
    Assertions.assertEquals(1, count);
  }

  @Test
  void testDoNothing() throws IOException {
    Fileop.mkdirs(dir);
    Assertions.assertTrue(dir.exists());
    Assertions.assertTrue(dir.isDirectory());
    Fileop.touch(temp);
    Assertions.assertTrue(temp.exists());
    DirectoryProcessor dp = new DirectoryProcessor(dir, doNothingListener);
    dp.exec();
    dp = new DirectoryProcessor(dir,doNothingListener);
    dp.exec((File[]) null);
  }
  
  @Test
  void testEmptyDir() {
    Fileop.mkdirs(dir);
    Assertions.assertTrue(dir.exists());
    Assertions.assertTrue(dir.isDirectory());
    DirectoryProcessor dp = new DirectoryProcessor(dir, doNothingListener);
    dp.exec();
    dp = new DirectoryProcessor(dir, doNothingListener);
    dp.exec();
    Assertions.assertTrue(dir.exists());
    Assertions.assertTrue(dir.isDirectory());
  }
  
  private static final Listener doNothingListener = new DirectoryProcessor.Listener() {

    @Override
    public boolean processFile(File file) {
      return true;
    }
    
  };
      
      
  
}
