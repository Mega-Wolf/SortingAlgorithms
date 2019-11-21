package sorting_algorithms;
//import java.util.List;

import logic.SortableArray;
import logic.Sorter;

public class InsertionSort extends Sorter {

	public void sort(int[] items) {
		for (int i = 0; i < items.length; ++i) {				// Iterate once through the whole array
			int valueToSortIn = items[i];
			int j;
			for (j = i - 1; j >= 0; --j) {
				if (valueToSortIn < items[j]) {
					items[j + 1] = items[j];
				} else {
					break;
				}
			}
			items[j + 1] = valueToSortIn;
		}
	}
	
	@Override
	public void sort(SortableArray array) {
		for (int i = 0; i < array.size(); ++i) {				// Iterate once through the whole array
			int valueToSortIn = array.getAt(i);
			int j;
			for (j = i - 1; j >= 0; --j) {
				if (valueToSortIn < array.getAt(j)) {
					array.setAt(j + 1, array.getAt(j));
				} else {
					break;
				}
			}
			array.setAt(j + 1, valueToSortIn);
		}
	}

}