package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ConstantValue extends ElementValue {

	private int valueIndex;

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2;
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getValueIndex());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getValueIndex() {
		return this.valueIndex;
	}

	public void setValueIndex(int valueIndex) {
		this.valueIndex = valueIndex;
	}
}
