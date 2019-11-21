package logic;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SortableArray {
	
	// Statics
	private static int s_accessWidth = 1;
	public static void setAccessWidth(int accessWidth) {
		s_accessWidth = accessWidth;
	}
	public static int getAccessWidth() {
		return s_accessWidth;
	}
	
	private static boolean s_inRenderMode = false;
	public static void setInRenderMode(boolean inRenderMode) {
		s_inRenderMode = inRenderMode;
	}
	public static boolean isInRenderMode() {
		return s_inRenderMode;
	}
	
	// Extra classes / enums
	public enum AccessType {
		Reading,
		Writing
	}
	
	public class AccessInfo {
		
		private final AccessType accessType;
		private final int index;
		private final int accessNum;
		
		public AccessInfo(AccessType accessType, int index, int accessNum) {
			this.accessType = accessType;
			this.index = index;
			this.accessNum = accessNum;
		}
		
		public AccessType getAccessType() {
			return accessType;
		}
		
		public int getIndex() {
			return index;
		}
		
		public int getAccessNum() {
			return accessNum;
		}
	}

	// Variables
	private int[] items;
	private int index1 = -1;
	private int index2 = -1;
	
	private boolean usedIndex1 = false;
	
	private List<AccessInfo> accessInfos = new ArrayList<AccessInfo>();
	
	private Thread thread;
	
	private int highlightStart = Integer.MAX_VALUE;
	private int highlightEnd = Integer.MIN_VALUE;
	private Color highlightColor = Color.blue;
		
	private SortableArray extraArray;
	private SortableArray parent;
	
	private int accessCount;
	
	// Constructors
	public SortableArray(int[] items) {
		reset(items.clone());
	}
	
	// Private Methods
	private int getAndIncrementAccessCount() {
		if (parent != null) {
			return parent.accessCount++;
		}
		return accessCount++;
	}
	
	// Public Methods
	public SortableArray getExtraArray() {
		if (extraArray == null) {
			extraArray = new SortableArray(new int[items.length]);
			extraArray.thread = thread;
			extraArray.parent = this;
		}
		return extraArray;
	}
	
	public SortableArray getExtraArrayWithoutCreate() {
		return extraArray;
	}
	
	public void reset(int[] items) {
		this.items = items;
		reset();		
	}
	
	public void reset() {
		index1 = -1;
		index2 = -1;
		usedIndex1 = false;
		thread = null;
		accessCount = 0;
	}
	
	public int getAt(int index) {
		if (index != index1 && index != index2) {
			if (usedIndex1) {
				index2 = index;
			} else {
				index1 = index;
			}
			usedIndex1 = !usedIndex1;
			
			AccessInfo info = new AccessInfo(AccessType.Reading, index, getAndIncrementAccessCount());
			accessInfos.add(info);
			if (s_inRenderMode) {
				try {
					Thread.currentThread().suspend();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return items[index];
	}
	
	public void setAt(int index, int value) {
		items[index] = value;
		
		AccessInfo info = new AccessInfo(AccessType.Writing, index, getAndIncrementAccessCount());
		accessInfos.add(info);
		if (s_inRenderMode) {
			try {
				Thread.currentThread().suspend();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		// TODO Theoretically this is now cached, but I guess I can ignore that
		
//		if (index1 == index) {
//			index1 = -1;
//		}
//		if (index2 == index) {
//			index2 = -1;
//		}
	}
	
	public void swap(int index1, int index2) {
		int dummy = getAt(index1);
		setAt(index1, getAt(index2));
		setAt(index2, dummy);
	}
	
	public int compareAt(int index1, int index2) {
		return getAt(index1) - getAt(index2);		
	}
	
	public int size() {
		return items.length;
	}
	
	public boolean isSorted() {
		for (int i = 0; i < items.length - 1; ++i) {
			if (items[i] > items[i + 1]) {
				return false;
			}
		}
		return true;
	}
	
	public int getValueForVisualisation(int index) {
		return items[index];
	}
	
	public List<AccessInfo> getAccessInfo() {
		return accessInfos;
	}
	
	public void setAssociatedThread(Thread thread) {
		this.thread = thread;
	}
	
	public boolean isStillRunning() {
		return thread != null && thread.isAlive();
	}
	
	public void setHighlightArea(int start, int end, Color color) {
		highlightStart = start;
		highlightEnd = end;
		highlightColor = color;
	}
	
	public void setHighlightArea(int start, int end, Color color, String description) {
		System.out.println(description + ": " + start + " - " + end);
		setHighlightArea(start, end, color);
	}
	
	public int getHighlightStart() {
		return highlightStart;
	}
	
	public int getHighlightEnd() {
		return highlightEnd;
	}
	
	public Color getHighlightColor() {
		return highlightColor;
	}
	
	public int getAccessCount() {
		return accessCount;
	}
}