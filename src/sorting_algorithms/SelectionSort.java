package sorting_algorithms;

import java.awt.Color;

import logic.SortableArray;
import logic.Sorter;

//import java.util.List;

public class SelectionSort extends Sorter {

	// @Override
	// void Sort(List<ISortable> items) {
	// // TODO Auto-generated method stub
	//
	// }

//	@Override
//	public void Sort(int[] items) {
//		// Due to understandability reasons we use ArrayLists in this example
//		
//		List<Integer> returnList = new ArrayList<Integer>(items.length);
//
//		// Filling the list with the values from the original array
//		List<Integer> itemsList = new ArrayList<Integer>(items.length);
//		for (int i = 0; i < items.length; ++i) {
//			itemsList.add(items[i]);
//		}
//		
//		while (itemsList.size() > 0) {
//			int lowestValue = Integer.MAX_VALUE;
//			int indexLowestValue = -1;
//
//			// Searching for the lowest value
//			for (int i = 0; i < itemsList.size(); ++i) {
//				if (items[i] < lowestValue) {
//					indexLowestValue = i;
//					lowestValue = items[i];
//				}
//			}			
//			
//			// Removing the element from the original list and adding it to the other list
//			itemsList.remove(indexLowestValue);
//			returnList.add(lowestValue);
//		}
//	}
	
//	@Override
//	public void Sort(int[] items) {
//		int[] returnArray = new int[items.length]; // This time, we are using an array; obviously it has the same number of elements as the original array
//		
//		// We can now directly work on the input array instead of copying the values to a list
//		
//		for (int n = 0; n < items.length; ++n) {
//			
//			int lowestValue = Integer.MAX_VALUE;
//			int indexLowestValue = -1;
//			
//			for (int i = 0; i < items.length - n; ++i) {
//				if (items[i] < lowestValue) {
//					indexLowestValue = i;
//					lowestValue = items[i];
//				}
//			}
//			
//			// Removing the element from the original array
//			// We just copy the last element of the array to the gap since the elements are unordered anyway
//			// In the next iteration, we will just ignore the last element of the original array so that it appears to be shrinking
//			items[indexLowestValue] = items[items.length - n - 1];
//			
//			// ...and adding it to the other array
//			returnArray[n] = lowestValue;
//			
//		}
//		
//	}
	
	public void sort(int[] items) {
		// In this final version, we do use the original array
		
		for (int n = 0; n < items.length; ++n) {
			
			int lowestValue = Integer.MAX_VALUE;
			int indexLowestValue = -1;
			
			for (int i = n; i < items.length; ++i) {
				if (items[i] < lowestValue) {
					indexLowestValue = i;
					lowestValue = items[i];
				}
			}
			
			// We now put the lowest value at the end of the beginning group
			// The value there is used to fill the gap
			items[indexLowestValue] = items[n];
			items[n] = lowestValue;
		}
	}
	
	@Override
	public void sort(SortableArray array) {
		// In this final version, we do use the original array
		
		for (int n = 0; n < array.size(); ++n) {
			array.setHighlightArea(0, n, Color.GREEN);
			int lowestValue = Integer.MAX_VALUE;
			int indexLowestValue = -1;
			
			for (int i = n; i < array.size(); ++i) {
				if (array.getAt(i) < lowestValue) {
					indexLowestValue = i;
					lowestValue = array.getAt(i);
				}
			}
		
			
			// We now put the lowest value at the end of the beginning group
			// The value there is used to fill the gap
			array.setAt(indexLowestValue, array.getAt(n));
			array.setAt(n, lowestValue);
			
		}
	}

}