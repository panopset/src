package com.panopset.tests.transformer;

import org.junit.jupiter.api.Assertions;

import com.panopset.tests.TestDirectory;

public abstract class TransformerTest implements TestDirectory  {

	
	protected abstract ResultsDataSupplier createResultsDataSupplier();
	
	private ResultsDataSupplier rds;
	
	private ResultsDataSupplier getResultsDataSupplier() {
		if (rds == null) {
			rds = createResultsDataSupplier();
		}
		return rds;
	}
	
//	public TransformerTest(final String expectedResults) {
//		this(new ResultsDataSupplier() {
//			
//			@Override
//			public String getExpectedResults() {
//				return expectedResults;
//			}
//		});
//	}
//	
//	public TransformerTest(final File expectedResultsFile) {
//		this(new ResultsDataSupplier() {
//			
//			@Override
//			public String getExpectedResults() {
//				return Fileop.readTextFile(expectedResultsFile);
//			}
//		});
//	}
	
	public void test() {
		init();
		Assertions.assertEquals(getResultsDataSupplier().getExpectedResults(), getResultsDataSupplier().getActualResults());
	}
	
	protected void init() {
		
	}

}
