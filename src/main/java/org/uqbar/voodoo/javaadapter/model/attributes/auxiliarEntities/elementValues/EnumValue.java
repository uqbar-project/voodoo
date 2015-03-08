package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class EnumValue extends ElementValue {

	private int typeIndex;
	private int nameIndex;

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 4;
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getTypeIndex());
		output.writeShort(this.getNameIndex());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getTypeIndex() {
		return this.typeIndex;
	}

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public int getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}
}
