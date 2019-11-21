package sorting_algorithms;

import java.awt.Color;

import logic.SortableArray;
import logic.Sorter;

public class QuickSort extends Sorter {
	
	public void sort(int[] items) {
		quicksort(items, 0, items.length - 1);
	}
	
	private void quicksort(int[] items, int left, int right) {
		if (left >= right) { return; }
		int pivot = divide(items, left, right);
		quicksort(items, left, pivot - 1);
		quicksort(items, pivot + 1, right);
	}
	
	private int divide(int[] items, int left, int right) {
		int i = left;
		int j = right - 1;
		
		int mid = (left + right)/2;
		if (items[right] < items[left]) {
			swap(items, right, left);
		}
		if (items[mid] < items[left]) {
			swap(items, mid, left);
		}
		if (items[right] < items[mid]) {
			swap(items, right, mid);
		}
		
		int pivot = items[mid];
		
		while (i < j) {
			while (i < right && items[i] < pivot) {
				++i;
			}
			
			while (j > left && items[j] >= pivot) {
				--j;
			}
			
			if (i < j) {
				swap(items, i, j);				
			}
		}
		
		// writing the pivot to the position of i and writing the element on i to the right since it is bigger than the pivot
		items[right] = items[i];
		items[i] = pivot;
		
		return i;
	}

	@Override
	public void sort(SortableArray array) {
		quicksort(array, 0, array.size() - 1);
	}
	
	private void quicksort(SortableArray array, int left, int right) {
		array.setHighlightArea(left, right, Color.blue);
		if (left >= right) { return; }
		int pivot = divide(array, left, right);
		quicksort(array, left, pivot - 1);
		quicksort(array, pivot + 1, right);
	}
	
	private int divide(SortableArray array, int left, int right) {
		int i = left;
		int j = right - 1;
		
		int mid = (left + right)/2;
		if (array.compareAt(right, left) < 0) {
			array.swap(right, left);
		}
		if (array.compareAt(mid, left) < 0) {
			array.swap(mid, left);
		}
		if (array.compareAt(right, mid) < 0) {
			array.swap(right, mid);
		}
		
		int pivot = array.getAt(mid);
		
		while (i < j) {
			while (i < right && array.getAt(i) < pivot) {
				++i;
			}
			
			while (j > left && array.getAt(j) >= pivot) {
				--j;
			}
			
			if (i < j) {
				array.swap(i, j);
			}
		}
		
		// writing the pivot to the position of i and writing the element on i to the right since it is bigger than the pivot
		array.setAt(right, array.getAt(i));
		array.setAt(i, pivot);
		
		return i;
	}
	

}