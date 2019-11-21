package visualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import logic.SortableArray;
import logic.SortableArray.AccessInfo;
import logic.SortableArray.AccessType;

@SuppressWarnings("serial")
public class SorterVisualiser extends JPanel {

	// Enums
	public enum DrawMode {
		Accesses, State
	}

	// Statics
	private static boolean s_drawLines = true;
	private static DrawMode s_drawMode = DrawMode.State;
	
	public static void setDrawLines(boolean drawLines) {
		s_drawLines = drawLines;
	}
	public static void setDrawMode(DrawMode drawMode) {
		s_drawMode = drawMode;
	}

	// Constants
	private static final int PREF_W = 500;
	private static final int PREF_H = 500;
	private static final int GRAPH_POINT_WIDTH = 4;
	private static final int GAP = 25;

	// Private Variables

	private SortableArray array;
	private int max;

	// Constructors
	public SorterVisualiser(SortableArray array) {
		this.array = array;
		
		setBackground(Color.black);

		max = Integer.MIN_VALUE;

		for (int i = 0; i < array.size(); ++i) {
			int a = array.getValueForVisualisation(i);
			if (a > max) {
				max = a;
			}
		}
	}

	// Overrides
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		switch (s_drawMode) {
			case Accesses: {
				drawAccesses(g2);
				break;
			}
			case State: {
				drawState(g2);
				break;
			}	
		}	
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	// Private Methods
	private void drawState(Graphics2D g2) {
		SortableArray extraArray = array.getExtraArrayWithoutCreate();
		if (extraArray != null) {
			drawStateArray(g2, getHeight() / 2, 0, array, GAP / 2);
			drawStateArray(g2, getHeight() / 2, getHeight() / 2, extraArray, GAP / 2);
		} else {
			drawStateArray(g2, getHeight(), 0, array, GAP);
		}	
	}
	
	private void drawStateArray(Graphics2D g2, double height, int y, SortableArray array, int gap) {
		int highlightStart = array.getHighlightStart();
		int highlightEnd = array.getHighlightEnd();
		Color highlightColor = array.getHighlightColor();

		double xScale = ((double) getWidth() - 2 * gap) / array.size();
		double yScale = (height - 2 * gap) / max;

		if (array.getAccessInfo().size() == 0) { return; }
		
		AccessInfo accessInfo = array.getAccessInfo().get(array.getAccessInfo().size() - 1);
		
		for (int i = 0; i < array.size(); ++i) {
			if (array.isStillRunning() && i >= highlightStart && i <= highlightEnd) {
				g2.setColor(highlightColor);
				g2.fillRect(gap + (int) ((i ) * xScale), y + (int) height - gap, (int) (xScale - 2), gap);
			}
			
			if (array.isStillRunning()) {
				g2.setColor(Color.white);
			} else {
				g2.setColor(Color.green);
			}
			
			if (i == accessInfo.getIndex() && array.isStillRunning()) { continue; }
			g2.fillRect(gap + (int) ((i ) * xScale), y + (int) height - gap - (int) (array.getValueForVisualisation(i) * yScale), (int) (xScale - 2), (int) (array.getValueForVisualisation(i) * yScale));
		}

		if (array.getAccessInfo().size() == 0 || !array.isStillRunning()) {
			return;
		}

		
		if (accessInfo.getAccessType() == AccessType.Reading) {
			g2.setColor(Color.green);
		} else {
			g2.setColor(Color.red);
		}
		
		g2.fillRect(gap + (int) (accessInfo.getIndex() * xScale), y + (int) height - gap - (int) (array.getValueForVisualisation(accessInfo.getIndex()) * yScale), (int) (xScale - 2), (int) (array.getValueForVisualisation(accessInfo.getIndex()) * yScale));

	}
	
	private void drawAccesses(Graphics2D g2) {
		SortableArray extraArray = array.getExtraArrayWithoutCreate();
		if (extraArray != null) {
			drawAccessesArray(g2, getHeight() / 2, 0, array, GAP / 2);
			drawAccessesArray(g2, getHeight() / 2, getHeight() / 2, extraArray, GAP / 2);
		} else {
			drawAccessesArray(g2, getHeight(), 0, array, GAP);			
		}
	}
	private void drawAccessesArray(Graphics2D g2, double height, int y, SortableArray array, int gap) {
		List<AccessInfo> accessInfos = array.getAccessInfo();

		double xScale = ((double) getWidth() - 2 * gap) / SortableArray.getAccessWidth();
		double yScale = (height - 2 * gap) / array.size();

		int xBefore = -1;
		int yBefore = -1;

		for (int i = 0; i < accessInfos.size(); ++i) {
			
			int accessIndex = accessInfos.get(i).getAccessNum();
			
			int x1 = gap + (int) (accessIndex * xScale) - GRAPH_POINT_WIDTH / 2;
			int y1 = y + gap + (int) ((array.size() - 1 - accessInfos.get(i).getIndex()) * yScale) - GRAPH_POINT_WIDTH / 2;

			if (accessInfos.get(i).getAccessType() == AccessType.Reading) {
				g2.setColor(Color.green);
			} else {
				g2.setColor(Color.red);
			}

			g2.fillOval(x1, y1, GRAPH_POINT_WIDTH, GRAPH_POINT_WIDTH);

			if (s_drawLines) {
				if (i > 0) { // TODO: This now is not correct
					g2.setColor(Color.blue);
					g2.drawLine(xBefore + GRAPH_POINT_WIDTH / 2, yBefore + GRAPH_POINT_WIDTH / 2, x1 + GRAPH_POINT_WIDTH / 2, y1 + GRAPH_POINT_WIDTH / 2);
				}

				xBefore = x1;
				yBefore = y1;
			}
		}
	}
}