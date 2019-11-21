package visualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import logic.ConstraintWrapper;
import logic.ReflectionHelper;
import logic.SortableArray;
import logic.Sorter;
import logic.SortingData;
import visualisation.SorterVisualiser.DrawMode;

public class GUI {

	public static void main(String[] args) {
		new GUI();
	}

	private final List<Class<?>> sorterClasses;
	private List<SortingData> sortingData;
	private final JFrame frame;

	private JButton startButton;
	private ButtonGroup buttonGroup;
	private JCheckBox[] checkBoxes;
	private ButtonGroup buttonGroupDataSize;
	private JSlider speedSlider;

	private List<Sorter> sorters = new ArrayList<Sorter>();
	private List<SortableArray> sortableArrays = new ArrayList<SortableArray>();
	
	private int[] dataSetSizes = {10, 20, 50, 100, 200, 500};
	
	private float globalSleep = 0;

	private boolean singleStep = false;
	private boolean running = false;
	private boolean paused = false;

	/* Constructor */
	public GUI() {
		sorterClasses = ReflectionHelper.getChildren(Sorter.class);
		sortingData = SortingData.generateData(0);

		frame = new JFrame("Sorting");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createFrame(true);
		
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	/* Methods */
	private void checkStartVisibility() {
		if (buttonGroup.getSelection() == null || buttonGroupDataSize.getSelection() == null) {
			startButton.setEnabled(false);
			return;
		}
		startButton.setEnabled(true);
		for (int i = 0; i < checkBoxes.length; ++i) {
			if (checkBoxes[i].isSelected()) {
				return;
			}
		}
		startButton.setEnabled(false);
	}

	private void createFrame(boolean compareData) {
		JPanel contentPanel = new JPanel();
		frame.setContentPane(contentPanel);

		contentPanel.setLayout(new GridBagLayout());
		JPanel rightPanel = new JPanel();
		JPanel leftPanel = new JPanel();

		rightPanel.setLayout(new GridBagLayout());

		ConstraintWrapper constraints = new ConstraintWrapper();

		constraints
			.setGridWidth(1)
			.setGridHeight(1)
			.setWeightX(1)
			.setWeightY(1)
			.setFill(GridBagConstraints.BOTH);
		frame.add(leftPanel, constraints.getConstraints());

		constraints
			.setWeightX(0)
			.setWeightY(0)
			.setFill(GridBagConstraints.NONE)
			.setAnchor(GridBagConstraints.NORTHWEST)
			.setGridWidth(GridBagConstraints.REMAINDER)
			.setGridHeight(1);
		frame.add(rightPanel, constraints.getConstraints());

		constraints
			.setGridHeight(2)
			.setInsets(new Insets(0, 5, 0, 5));
		JLabel header = DrawLabel(rightPanel, "Sorting Algorithms", constraints);
		header.setFont(new Font("Sans Serif", Font.BOLD, 16));

		DrawSeparator(rightPanel, constraints);

		constraints
			.setGridHeight(1)
			.setAnchor(GridBagConstraints.WEST);
		DrawLabel(rightPanel, "Compare " + (compareData ? "Data" : "Algorithms:"), constraints);
		
		constraints.setGridWidth(1);
		DrawLabel(rightPanel, ">", constraints);
		
		constraints.setGridWidth(GridBagConstraints.REMAINDER);
		JButton switchButton = DrawButton(rightPanel, "Compare " + (!compareData ? "Data" : "Algorithms:"), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createFrame(!compareData);
				checkStartVisibility();
			}
		}, constraints);
		switchButton.setBorder(new LineBorder(Color.black, 1));
	
		
		
		
		
		constraints
			.setGridWidth(GridBagConstraints.REMAINDER)
			.setAnchor(GridBagConstraints.WEST);
		
		DrawLabel(rightPanel, "Algorithm:", constraints);

		if (compareData) {
			buttonGroup = new ButtonGroup();
			for (int i = 0; i < sorterClasses.size(); ++i) {
				DrawRadioButton(rightPanel, sorterClasses.get(i).getSimpleName(), i, buttonGroup, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						checkStartVisibility();
					}
				}, constraints);
			}
		} else {
			checkBoxes = new JCheckBox[sorterClasses.size()];
			for (int i = 0; i < sorterClasses.size(); ++i) {
				JCheckBox rb = DrawCheckBox(rightPanel, sorterClasses.get(i).getSimpleName(), i, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						checkStartVisibility();
					}
				}, constraints);
				checkBoxes[i] = rb;
			}
		}

		DrawSeparator(rightPanel, constraints);
		DrawLabel(rightPanel, "Data Set Size:", constraints);
		
		buttonGroupDataSize = new ButtonGroup();
		
		for (int i = 0; i < dataSetSizes.length; ++i) {
			DrawRadioButton(rightPanel, dataSetSizes[i] + "", dataSetSizes[i], buttonGroupDataSize, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					checkStartVisibility();
				}
			}, constraints);
		}
		
		DrawSeparator(rightPanel, constraints);
		DrawLabel(rightPanel, "Data sets:", constraints);

		if (compareData) {
			checkBoxes = new JCheckBox[sortingData.size()];
			for (int i = 0; i < sortingData.size(); ++i) {
				JCheckBox rb = DrawCheckBox(rightPanel, sortingData.get(i).getName(), i, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						checkStartVisibility();
					}
				}, constraints);
				checkBoxes[i] = rb;
			}
		} else {
			buttonGroup = new ButtonGroup();
			for (int i = 0; i < sortingData.size(); ++i) {
				DrawRadioButton(rightPanel, sortingData.get(i).getName(), i, buttonGroup, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						checkStartVisibility();
					}
				}, constraints);
			}
		}

		DrawSeparator(rightPanel, constraints);

		startButton = DrawButton(rightPanel, "Start", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paused = false;
				SortableArray.setInRenderMode(true);
				SorterVisualiser.setDrawMode(DrawMode.State);

				
				sortingData = SortingData.generateData(buttonGroupDataSize.getSelection().getMnemonic());
				
				sorters.clear();
				sortableArrays.clear();
				try {
					if (!compareData) {
						int[] data = sortingData.get(buttonGroup.getSelection().getMnemonic()).getItems();

						for (int i = 0; i < checkBoxes.length; ++i) {
							if (!checkBoxes[i].isSelected()) {
								continue;
							}

							Sorter sorter = (Sorter) sorterClasses.get(i).newInstance();
							SortableArray sortableArray = new SortableArray(data);
							SorterVisualiser visualiser = new SorterVisualiser(sortableArray);
							leftPanel.add(visualiser);
							sorters.add(sorter);
							sortableArrays.add(sortableArray);
						}
					} else {
						Class<?> sorterClass = sorterClasses.get(buttonGroup.getSelection().getMnemonic());

						for (int i = 0; i < checkBoxes.length; ++i) {
							if (!checkBoxes[i].isSelected()) {
								continue;
							}

							Sorter sorter = (Sorter) sorterClass.newInstance();
							SortableArray sortableArray = new SortableArray(sortingData.get(i).getItems());
							SorterVisualiser visualiser = new SorterVisualiser(sortableArray);
							leftPanel.add(visualiser);
							sorters.add(sorter);
							sortableArrays.add(sortableArray);
						}
					}

				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				leftPanel.setLayout(new GridLayout(sorters.size(), 1, 0, 10));

				rightPanel.remove(startButton);
				for (int i = 0; i < checkBoxes.length; ++i) {
					checkBoxes[i].setEnabled(false);
				}

				Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
				while (enumeration.hasMoreElements()) {
					AbstractButton ab = enumeration.nextElement();
					ab.setEnabled(false);
				}
				
				enumeration = buttonGroupDataSize.getElements();
				while (enumeration.hasMoreElements()) {
					AbstractButton ab = enumeration.nextElement();
					ab.setEnabled(false);
				}

				JLabel visualisationLabel = DrawLabel(rightPanel, "Visualisation Speed", constraints);

				constraints.setFill(GridBagConstraints.HORIZONTAL);
				speedSlider = DrawSlider(rightPanel, 0, 50, 40, constraints);
				speedSlider.setPreferredSize(new Dimension(5, speedSlider.getPreferredSize().height));

				JButton quickSkip = new JButton("Skip visualisation");
				quickSkip.setBorder(new LineBorder(Color.black, 1));
				quickSkip.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SortableArray.setInRenderMode(false);
					}
				});
				rightPanel.add(quickSkip, constraints.getConstraints());

				JButton singleStepButton = new JButton("Single Step");
				singleStepButton.setBorder(new LineBorder(Color.black, 1));
				singleStepButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						singleStep = true;	
					}
				});
				
				JButton pauseButton = new JButton("Pause");
				pauseButton.setBorder(new LineBorder(Color.black, 1));
				pauseButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						paused = !paused;
						if (paused) {
							pauseButton.setText("Resume");
							rightPanel.add(singleStepButton, constraints.getConstraints());
						} else {
							pauseButton.setText("Pause");
							rightPanel.remove(singleStepButton);
						}
					};
				});
				rightPanel.add(pauseButton, constraints.getConstraints());
				
				JButton abortButton = new JButton("Abort");
				abortButton.setBorder(new LineBorder(Color.black, 1));
				abortButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						running = false;
						createFrame(compareData);
					}
				});
				rightPanel.add(abortButton, constraints.getConstraints());

				frame.pack();
				frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

				Thread t = new Thread() {

					public void run() {

						try {
							Thread[] threads = new Thread[sorters.size()];

							for (int i = 0; i < sorters.size(); ++i) {
								final int index = i;
								final Thread thread = new Thread() {
									@Override
									public void run() {
										sortableArrays.get(index).setAssociatedThread(this);
										sorters.get(index).sort(sortableArrays.get(index));
									};
								};
								threads[i] = thread;
								thread.start();
							}

							Thread.sleep(1000);
							running = true;

							while (running) {
								if (paused && !singleStep) {
									Thread.sleep(250);
									continue;
								}
								leftPanel.repaint();
								if (SortableArray.isInRenderMode()) {
									float sleep = (float) Math.pow(10,
											((float) speedSlider.getValue() / speedSlider.getMaximum()) * 7 - 4);
									
									if (singleStep) {
										singleStep = false;
									} else {
										if (sleep >= 1) {
											Thread.sleep((int)sleep);										
										}
										if (sleep < 1) {
											globalSleep += sleep;
											if (globalSleep > 1) {
												globalSleep -= 1;
												Thread.sleep(1);
											}
										}										
									}
								}

								boolean finished = true;
								for (int i = 0; i < threads.length; ++i) {
									if (threads[i].isAlive()) {
										finished = false;
										break;
									}
								}

								if (finished) {
									rightPanel.remove(singleStepButton);
									SorterVisualiser.setDrawMode(DrawMode.Accesses);
									frame.repaint();

									int max = 1;
									for (int i = 0; i < sortableArrays.size(); ++i) {
										int iMax = sortableArrays.get(i).getAccessCount();
										if (iMax > max) {
											max = iMax;
										}
									}
									SortableArray.setAccessWidth(max);

									rightPanel.remove(visualisationLabel);
									rightPanel.remove(quickSkip);
									rightPanel.remove(speedSlider);
									
									frame.validate();
									
									return;
								}

								for (int i = 0; i < threads.length; ++i) {
									threads[i].resume();
								}

							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				};
				t.start();
			}

		} ,constraints);
		startButton.setEnabled(false);

		frame.pack();
		
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	private JSeparator DrawSeparator(JPanel parent, ConstraintWrapper constraints) {
		constraints.setFill(GridBagConstraints.HORIZONTAL);
		JSeparator c = new JSeparator(SwingConstants.HORIZONTAL);
		parent.add(c, constraints.getConstraints());
		constraints.setFill(GridBagConstraints.NONE);
		return c;
	}

	private JLabel DrawLabel(JPanel parent, String text, ConstraintWrapper constraints) {
		JLabel c = new JLabel(text);
		parent.add(c, constraints.getConstraints());
		return c;
	}
	
	private JButton DrawButton(JPanel parent, String text, ActionListener actionlistener, ConstraintWrapper constraints) {
		JButton c = new JButton(text);
		parent.add(c, constraints.getConstraints());
		c.addActionListener(actionlistener);
		return c;
	}
	
	private JSlider DrawSlider(JPanel parent, int min, int max, int value, ConstraintWrapper constraints) {
		JSlider c = new JSlider(min, max, value);
		parent.add(c, constraints.getConstraints());
		return c;
	}
	
	private JCheckBox DrawCheckBox(JPanel parent, String text, int mnemonic, ActionListener actionListener, ConstraintWrapper constraints) {
		JCheckBox c = new JCheckBox(text);
		c.setMnemonic(mnemonic);
		parent.add(c, constraints.getConstraints());
		
		if (actionListener != null) {
			c.addActionListener(actionListener);
		}
		
		return c;
	}
	
	private JRadioButton DrawRadioButton(JPanel parent, String text, int mnemonic, ButtonGroup buttonGroup, ActionListener actionListener, ConstraintWrapper constraints) {
		JRadioButton c = new JRadioButton(text);
		c.setMnemonic(mnemonic);
		buttonGroup.add(c);
		parent.add(c, constraints.getConstraints());
		
		if (actionListener != null) {
			c.addActionListener(actionListener);
		}
		
		return c;
	}

}
