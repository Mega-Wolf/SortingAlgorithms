package logic;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ConstraintWrapper {
	
	private final GridBagConstraints constraints;
	
	public ConstraintWrapper() {
		constraints = new GridBagConstraints();
	}
	
	public ConstraintWrapper(GridBagConstraints constraints) {
		this.constraints = constraints;
	}
	
	public GridBagConstraints getConstraints() {
		return constraints;
	}
	
	public ConstraintWrapper setGridHeight(int gridheight) {
		constraints.gridheight = gridheight;
		return this;
	}
	public ConstraintWrapper setGridWidth(int gridwidth) {
		constraints.gridwidth = gridwidth;
		return this;
	}
	public ConstraintWrapper setGridX(int gridx) {
		constraints.gridx = gridx;
		return this;
	}
	public ConstraintWrapper setGridY(int gridy) {
		constraints.gridy = gridy;
		return this;
	}
	public ConstraintWrapper setAnchor(int anchor) {
		constraints.anchor = anchor;
		return this;
	}
	public ConstraintWrapper setFill(int fill) {
		constraints.fill = fill;
		return this;
	}
	public ConstraintWrapper setInsets(Insets insets) {
		constraints.insets = insets;
		return this;
	}
	public ConstraintWrapper setIPadX(int ipadx) {
		constraints.ipadx = ipadx;
		return this;
	}
	public ConstraintWrapper setIPadY(int ipady) {
		constraints.ipady = ipady;
		return this;
	}
	public ConstraintWrapper setWeightX(int weightx) {
		constraints.weightx = weightx;
		return this;
	}
	public ConstraintWrapper setWeightY(int weighty) {
		constraints.weighty = weighty;
		return this;
	}	
}