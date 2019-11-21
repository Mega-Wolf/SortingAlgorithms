package sorting_algorithms;

import java.awt.Color;
import java.util.Arrays;

import logic.SortableArray;
import logic.Sorter;

public class MergeSort extends Sorter {

	public void sort(int[] items) {
		int[] items2 = Arrays.copyOf(items, items.length);	// TODO: Probably this could be made better
		divide(items, 0, items.length - 1, items2);			// At the end: items is sorted, items2 just was there as a helper thing
	}
	
	private void divide(int[] dst, int left, int right, int[] src) {
		if (right - left == 0) { return; }
		
		divide(src, left, (left + right) / 2, dst);
		divide(src, (left + right) / 2 + 1, right, dst);
		
		merge(dst, left, right, src);
	}
	
	private void merge(int[] dst, int left, int right, int[] src) {
		int middle = (left + right) / 2;
		
		int i1 = left;
		int i2 = middle + 1;
		
		for (int outputIndex = left; outputIndex <= right; ++outputIndex) {
			int lowestItem;
			
			if (i1 <= middle && (i2 > right || src[i1] < src[i2])) {
				lowestItem = src[i1];
				++i1;
			} else {
				lowestItem = src[i2];
				++i2;
			}
			
			dst[outputIndex] = lowestItem;			// adding the lowest of the two lists to the other list
		}
	}

	@Override
	public void sort(SortableArray array) {
		SortableArray dummy = array.getExtraArray();
		for (int i = 0; i < array.size(); ++i) {
			dummy.setAt(i, array.getAt(i));
		}
		divide(array, 0, array.size() - 1, dummy);
	}
	
	private void divide(SortableArray dst, int left, int right, SortableArray src) {
		if (right - left == 0) { return; }
		
		int middle = (left + (right - left) / 2);
		
		divide(src, left, middle, dst);
		divide(src, middle + 1, right, dst);
		
		merge(dst, left, right, src);
	}
	
	private void merge(SortableArray dst, int left, int right, SortableArray src) {
		dst.setHighlightArea(left, right, Color.red.darker());
		src.setHighlightArea(left, right, Color.green.darker());
		int middle = (left + (right - left) / 2);
		
		int i1 = left;
		int i2 = middle + 1;
		
		for (int outputIndex = left; outputIndex <= right; ++outputIndex) {
			int lowestValue;
			
			if (i1 <= middle && (i2 > right || src.getAt(i1) < src.getAt(i2))) {
				lowestValue = src.getAt(i1);
				++i1;
			} else {
				lowestValue = src.getAt(i2);
				++i2;
			}
			
			dst.setAt(outputIndex, lowestValue);
		}
	}

}