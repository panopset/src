package com.panopset.compat.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.compat.AniGrid;

public class AniGridTest {

	  @Test
	  void test() {
	    AniGrid aniGrid = new AniGrid(100, 200, 10, 50);
	    Assertions.assertEquals(4, aniGrid.getDspHeight());
	    Assertions.assertEquals(10, aniGrid.getDspWidth());
	    Assertions.assertArrayEquals(new int[] {0, 0, 10, 4, 35, 27, 10, 4},
	        aniGrid.getPaintDimensions(0, 0, 35, 27));
	  }

	  @Test
	  void testWithMultiplier() {
	    AniGrid aniGrid = new AniGrid(100, 200, 10, 50, 2);
	    Assertions.assertEquals(2,  aniGrid.getMultiplier());
	    Assertions.assertEquals(8, aniGrid.getDspHeight());
	    Assertions.assertEquals(20, aniGrid.getDspWidth());
	    Assertions.assertArrayEquals(new int[] {0, 0, 10, 4, 35, 27, 20, 8},
	        aniGrid.getPaintDimensions(0, 0, 35, 27));
	  }
}
