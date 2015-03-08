package org.uqbar.voodoo.javaadapter.model.attributes;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SyntheticAttribute extends Attribute {

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SyntheticAttribute() {
		super("Synthetic");
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 0;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		return this.getClass().equals(object.getClass());
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
	}
}
