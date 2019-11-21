package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortingData {

	private final String name;
	private final int[] items;

	public SortingData(String name, int[] items) {
		this.name = name;
		this.items = items;
	}

	public int[] getItems() {
		return items;
	}

	public String getName() {
		return name;
	}

	private static void swapItems(int[] items, int index1, int index2) {
		int dummy = items[index1];
		items[index1] = items[index2];
		items[index2] = dummy;
	}

	public static List<SortingData> generateData(int size) {
		List<SortingData> ret = new ArrayList<SortingData>();

		// Sorted
		{
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = i + 1;
			}
			ret.add(new SortingData("Sorted", items));
		}

		// Inverse Sorted
		{
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = size - i;
			}
			ret.add(new SortingData("Inverse Sorted", items));
		}

		// Almost Sorted
		{
			Random random = new Random(0);
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = i + 1;
			}
			for (int j = 0; j < 3; ++j) {
				for (int i = 0; i < size - 1; ++i) {
					if (random.nextFloat() <= 0.25f) {
						swapItems(items, i, i + 1);
					}
				}
			}

			ret.add(new SortingData("Almost Sorted", items));
		}

		// All Equal
		{
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = 1;
			}
			ret.add(new SortingData("All Equal", items));
		}
		
		// Random - Few Values
		{
			Random random = new Random(0);
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = random.nextInt(5) + 1;
			}
			ret.add(new SortingData("Random - Few Values", items));
		}

		// Random 1
		{
			Random random = new Random(0);
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = random.nextInt(size) + 1;
			}
			ret.add(new SortingData("Random 1", items));
		}

		// Random 2
		{
			Random random = new Random(1);
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = random.nextInt(size) + 1;
			}
			ret.add(new SortingData("Random 2", items));
		}

		// Random 3
		{
			Random random = new Random(2);
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = random.nextInt(size) + 1;
			}
			ret.add(new SortingData("Random 3", items));
		}

		// Cycled
		{
			int[] items = new int[size];
			for (int i = 0; i < size; ++i) {
				items[i] = (i + size / 2) % size + 1;
			}
			ret.add(new SortingData("Cycled", items));

		}

		return ret;
	}

}