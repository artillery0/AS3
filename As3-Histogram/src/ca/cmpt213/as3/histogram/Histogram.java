package ca.cmpt213.as3.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Histogram class, generated from an array of integers. 
 * Supports a configurable number of bars, and can be changed after creation (mutable).
 */
public class Histogram implements Iterable<Histogram.Bar>{
	private final int MIN_VALUE = 0;
	
	private int[] data;
	private int numDivisions;
	private List<Bar> bars;

	public Histogram(int[] data, int numBars) {
		assert numBars > 0;
		this.data = Arrays.copyOf(data,data.length);
		this.numDivisions = numBars;
		updateHistogram();
	}
	
	public void setData(int[] data) {
		this.data = Arrays.copyOf(data,data.length);
		updateHistogram();
	}
	
	public void setNumberBars(int numBars) {
		assert numBars >= 1;
		this.numDivisions = numBars;
		updateHistogram();
	}
	
	private void updateHistogram() {
		int[] counts = getCountPerBar();
		populateBars(counts);
	}
	private int[] getCountPerBar() {
		int valuesPerBar = calculateValuesPerBar(numDivisions);

		int[] counts = new int[numDivisions];
		for (int value : data) {
			int barIndex = getIndexFromValue(value, valuesPerBar);
			counts[barIndex] ++;
		}
		return counts;
	}
	
	private void populateBars(int[] counts) {
		assert counts.length == numDivisions;
		int valuesPerBar = calculateValuesPerBar(numDivisions);
		
		bars = new ArrayList<Bar>(numDivisions);
		for (int barNum = 0; barNum < numDivisions; barNum++) {
			int minRange = barNum * valuesPerBar;
			int maxRange = minRange + valuesPerBar - 1;
			int count = counts[barNum];

			bars.add(new Bar(minRange, maxRange, count));
		}
	}

	private int calculateValuesPerBar(int numDivisions) {
		int max = findMax(data);
		return (int) Math.ceil((double)(max+1) / numDivisions);
	}

	private int findMax(int[] data) {
		int max = MIN_VALUE ;
		for(int value : data) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	private int getIndexFromValue(int value, int valuesPerBar) {
		int index = value/ valuesPerBar;
		assert index >= 0;
		assert index < numDivisions;
		return index;
	}

	public int getNumberBars() {
		return bars.size();
	}
	
	public Iterator<Bar> iterator() {
		return Collections.unmodifiableList(bars).iterator();
	}
	
	public int getMaxBarCount() {
		if (bars.size() == 0) {
			return 0;
		}
		int max = bars.get(0).getCount();
		for(Bar bar : bars) {
			if (bar.getCount() > max) {
				max = bar.getCount();
			}
		}
		return max;
	}
	
	

	/**
	 * Store information about a single bar in a histogram including:
	 * - Interval (minimum and maximum): The range of values mapped to this bar, inclusive.
	 * - The number of elements that mapped to this bar (bar's height/count).
	 */
	public class Bar {
		private int rangeMin = 0;
		private int rangeMax = 0;
		private int count = 0;
		
		public Bar(int min, int max, int count) {
			rangeMin = min;
			rangeMax = max;
			this.count = count;
		}
		
		public int getRangeMin() {
			return rangeMin;
		}
		public int getRangeMax() {
			return rangeMax;
		}
		public int getCount() {
			return count;
		}
		public String toString() {
			return "Bar [" + rangeMin + ", " + rangeMax + "] = " + count;
		}
	}
}
