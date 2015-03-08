package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.Arrays;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class CustomAttribute extends Attribute {

	private int nameIndex;
	private byte[] info;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public CustomAttribute(String label) {
		super(label);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return this.getInfo().length;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		CustomAttribute target = (CustomAttribute) object;

		return this.getNameIndex() == target.getNameIndex()
				&& Arrays.equals(this.getInfo(), target.getInfo());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.write(this.getInfo());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public byte[] getInfo() {
		return this.info;
	}

	public void setInfo(byte[] info) {
		this.info = info;
	}
}
