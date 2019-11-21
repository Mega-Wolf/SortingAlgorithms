package sorting_algorithms;

import java.awt.Color;

import logic.SortableArray;
import logic.Sorter;

public class BubbleSort extends Sorter {

	public void sort(int[] items) {
		 for (int n = items.length; n > 1; --n) {			// The correct elements bubble to the end; this means every round one element less has to be sorted 
			 for (int i = 0; i < n - 1; ++i) {				// Iterating form the first to the last element of unsorted elements 
				 if (items[i] > items[i + 1]) {				// Only neighbouring elements are compared
					 swap(items, i, i + 1);
				 }
			 }
		 }
	}
	
	public void sort(SortableArray array) {
		for (int n = array.size(); n > 1; --n) {					// The correct elements bubble to the end; this means every round one element less has to be sorted
			array.setHighlightArea(n, array.size(), Color.GREEN);	// The right side of the array holds the already sorted values
			 for (int i = 0; i < n - 1; ++i) {						// Iterating form the first to the last element of unsorted elements 
				 if (array.getAt(i) > array.getAt(i + 1)) {			// Neighbouring elements are compared
					 array.swap(i, i + 1);
				 }
			 }
		 }
	}

}