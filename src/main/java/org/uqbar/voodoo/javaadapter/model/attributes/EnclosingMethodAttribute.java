package org.uqbar.voodoo.javaadapter.model.attributes;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class EnclosingMethodAttribute extends Attribute {

	private int classIndex;
	private int methodIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public EnclosingMethodAttribute() {
		super("EnclosingMethod");
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 4;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		EnclosingMethodAttribute target = (EnclosingMethodAttribute) object;

		return this.getClassIndex() == target.getClassIndex()
				&& this.getMethodIndex() == target.getMethodIndex();
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getClassIndex());
		output.writeShort(this.getMethodIndex());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getClassIndex() {
		return this.classIndex;
	}

	public void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	public int getMethodIndex() {
		return this.methodIndex;
	}

	public void setMethodIndex(int methodIndex) {
		this.methodIndex = methodIndex;
	}
}
