package sorting_algorithms;

import java.awt.Color;

import logic.SortableArray;
import logic.Sorter;

public class HeapSort extends Sorter{
	
	
	public void sort(int[] items) {
		// build heap
		// TODO; also possible with recursion
		
		for (int i = items.length / 2; --i >= 0; ){
			heapify(items, i, items.length);
		}
		
		// sort
		int length = items.length;
		for (int i = items.length; --i >= 1; ){
			--length;
			swap(items, 0, i);
			heapify(items, 0, length);
		}
	}
	
	private int leftIndex(int index) {
		return 2 * index + 1;
	}
	
	private int rightIndex(int index) {
		return 2 * index + 2;
	}
	
//	private int parentIndex(int index) {
//		return (index - 1) / 2;
//	}
	
	private void heapify(int[] items, int index, int length) {
		int currentIndex = index;
		while (true) {				
			int maxIndex  = currentIndex;
			int leftIndex = leftIndex(currentIndex);
			int rightIndex = rightIndex(currentIndex);
			
			if (leftIndex < length && items[leftIndex] > items[currentIndex]) {
				maxIndex = leftIndex;
			}
			if (rightIndex < length && items[rightIndex] > items[maxIndex]) {
				maxIndex = rightIndex;
			}
			if (maxIndex != currentIndex) {
				swap(items, currentIndex, maxIndex);
				currentIndex = maxIndex;
			} else {
				break;
			}
		}
	}

	@Override
	public void sort(SortableArray array) {
		// build heap; TODO; also possible with recursion
		array.setHighlightArea(0, array.size() - 1, Color.blue);
		for (int i = array.size() / 2; --i >= 0; ){
			heapify(array, i, array.size());
		}
		
		array.setHighlightArea(Integer.MAX_VALUE, Integer.MIN_VALUE, Color.blue);
		// sort
		int length = array.size();
		for (int i = array.size(); --i >= 1; ){
			--length;
			array.swap(0, i);
			heapify(array, 0, length);
		}
	}
	
	private void heapify(SortableArray array, int index, int length) {
		int currentIndex = index;
		while (true) {				
			int maxIndex  = currentIndex;
			int leftIndex = leftIndex(currentIndex);
			int rightIndex = rightIndex(currentIndex);
			
			if (leftIndex < length && array.getAt(leftIndex) > array.getAt(currentIndex)) {
				maxIndex = leftIndex;
			}
			if (rightIndex < length && array.getAt(rightIndex) > array.getAt(maxIndex)) {
				maxIndex = rightIndex;
			}
			if (maxIndex != currentIndex) {
				array.swap(currentIndex, maxIndex);
				currentIndex = maxIndex;
			} else {
				break;
			}
		}
	}

}