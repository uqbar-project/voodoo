package org.uqbar.voodoo.javaadapter.model.modifiers;

import org.uqbar.voodoo.javaadapter.parser.exceptions.ParseException;

public enum Visibility {
	PUBLIC(0x0001), PRIVATE(0x0002), PROTECTED(0x0004);

	private int flag;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static Visibility fromFlag(int flag) {
		for(Visibility visibility : Visibility.values()) {
			if((visibility.getFlag() & flag) != 0) {
				return visibility;
			}
		}

		throw new ParseException("Invalid Visibility flag!");
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	Visibility(int flag) {
		this.flag = flag;
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getFlag() {
		return this.flag;
	}

	protected void setFlag(int flag) {
		this.flag = flag;
	}
}