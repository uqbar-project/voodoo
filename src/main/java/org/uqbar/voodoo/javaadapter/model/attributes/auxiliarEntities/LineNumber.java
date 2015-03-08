package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class LineNumber {

	private int startPC;
	private int lineNumber;

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getStartPC());
		output.writeShort(this.getLineNumber());
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		LineNumber target = (LineNumber) object;

		return this.getStartPC() == target.getStartPC()
				&& this.getLineNumber() == target.getLineNumber();
	};

	@Override
	public int hashCode() {
		return this.getLineNumber();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getStartPC() {
		return this.startPC;
	}

	public void setStartPC(int startPC) {
		this.startPC = startPC;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
