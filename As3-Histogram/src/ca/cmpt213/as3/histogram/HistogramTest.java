
package ca.cmpt213.as3.histogram;

import java.util.Iterator;


import junit.framework.TestCase;

/**
 * JUnit Test class for the Histogram class. 
 */
public class HistogramTest extends TestCase {
	
	/**
	 * Custom test function which validates the distribution counts found
	 * in the Histogram's Bars (the input Iterator) match the expected counts.
	 * @param given An iterator to the Bars of the Histogram.
	 * @param expectedCounts The expected counts for each bar.
	 */
	public void assertBarCountEqual(Iterator<Histogram.Bar> given, int[] expectedCounts) {
		assertNotNull(given);
		assertNotNull(expectedCounts);
		int index = 0;
		while (given.hasNext()) {
			Histogram.Bar bar = given.next();

			assertTrue(index < expectedCounts.length);
			assertNotNull(bar);
			assertEquals(bar.getCount(), expectedCounts[index]);
			
			index++;
		}
		
		// In case no asserts were checked above (empty set).
		assertTrue(true);
	}
	
	/**
	 * Custom test function which validates the ranges are correct on the bars.
	 * @param given An iterator to the Bars of the Histogram.
	 * @param expectedRange The expected size of the range (i.e., span) of each bar.
	 */
	public void assertBarsRange(Iterator<Histogram.Bar> given, int expectedRange) {
		assertNotNull(given);
		while (given.hasNext()) {
			Histogram.Bar bar = given.next();

			assertNotNull(bar);
			int rangeWidth = bar.getRangeMax() - bar.getRangeMin() + 1;
			assertEquals(rangeWidth, expectedRange);
		}
		
		// In case no asserts were checked above (empty set).
		assertTrue(true);
	}
	 
	
	public void testRangeSize1() {
		Histogram hist;
		hist = new Histogram(new int[] {0,1,2,3,4,5}, 6);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 1);
		
		hist = new Histogram(new int[] {1,1,1,1,1}, 6);
		assertBarCountEqual(hist.iterator(), new int[]{0,5,0,0,0,0});
		assertBarsRange(hist.iterator(), 1);

		hist = new Histogram(new int[] {1,1,1,1,1}, 2);
		assertBarCountEqual(hist.iterator(), new int[]{0, 5});
		assertBarsRange(hist.iterator(), 1);

		hist = new Histogram(new int[] {0}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{1, 0, 0, 0, 0});
		assertBarsRange(hist.iterator(), 1);
		
		hist = new Histogram(new int[] {3}, 5);
		assertBarCountEqual(hist.iterator(), new int[]{0, 0, 0, 1, 0});
		assertBarsRange(hist.iterator(), 1);
	}

	public void testRangeSize10() {
		Histogram hist;
		hist = new Histogram(new int[] {100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{0,0,0,0,0,0,0,0,0,0,1});
		assertBarsRange(hist.iterator(), 10);

		hist = new Histogram(new int[] {10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{0,1,1,1,1,1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 10);
		
		hist = new Histogram(new int[] {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,1,1,1,1,1,1,1,1,1});
		assertBarsRange(hist.iterator(), 10);
	}

	public void testImperfectFit() {
		Histogram hist;
		hist = new Histogram(new int[] {0,1,2}, 2);
		assertBarCountEqual(hist.iterator(), new int[]{2, 1});
		assertBarsRange(hist.iterator(), 2);
		
		hist = new Histogram(new int[] {1,1,1,1,1}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{5});
		assertBarsRange(hist.iterator(), 2);
	}
	
	public void testEmptyData() {
		Histogram hist;
		hist = new Histogram(new int[] {}, 1);
		assertBarCountEqual(hist.iterator(), new int[]{0, 0, 0, 0, 0});
		assertBarsRange(hist.iterator(), 1);
	}
	
	public void testIterable() {
		Histogram hist;
		hist = new Histogram(new int[] {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, 11);
		assertBarCountEqual(hist.iterator(), new int[]{1,1,1,1,1,1,1,1,1,1,1});
		int count = 0;
		for (Histogram.Bar bar : hist) {
			count++;
			assertEquals(bar.getCount(), 1);
		}
		assertEquals(count, 11);

	}

}
