package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ClassInfoValue extends ElementValue {

	private int classInfoIndex;

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
		output.writeShort(this.getClassInfoIndex());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getClassInfoIndex() {
		return this.classInfoIndex;
	}

	public void setClassInfoIndex(int classInfoIndex) {
		this.classInfoIndex = classInfoIndex;
	}
}
