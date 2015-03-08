package org.uqbar.voodoo.javaadapter.model.attributes;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ConstantValueAttribute extends Attribute {

	private int valueIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ConstantValueAttribute() {
		super("ConstantValue");
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		ConstantValueAttribute target = (ConstantValueAttribute) object;

		return this.getValueIndex() == target.getValueIndex();
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
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
