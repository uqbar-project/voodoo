package org.uqbar.voodoo.javaadapter.builder.auxiliars;

public class Link {

	private int key;
	private String label;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Link(int key, String label) {
		this.setKey(key);
		this.setLabel(label);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public String getLabel() {
		return this.label;
	}

	protected void setLabel(String label) {
		this.label = label;
	}

	public int getKey() {
		return this.key;
	}

	protected void setKey(int key) {
		this.key = key;
	}
}