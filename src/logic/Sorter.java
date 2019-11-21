package logic;
//import java.util.List;

public abstract class Sorter {

	//public abstract void Sort(int[] items);	
	public abstract void sort(SortableArray arrayy);
	
	
	public void swap(int[] items, int index1, int index2) {
		int dummy = items[index1];
		items[index1] = items[index2];
		items[index2] = dummy;
	}
	
	public boolean CheckConsistency(int[] items) {
		for (int i = 0; i < items.length - 1; ++i) {
			if (items[i] > items[i + 1]) {
				return false;
			}
		}
		return true;
	}	
	
}