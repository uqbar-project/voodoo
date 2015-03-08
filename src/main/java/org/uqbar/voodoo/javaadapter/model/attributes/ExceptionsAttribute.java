package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.Arrays;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ExceptionsAttribute extends Attribute {

	private int[] exceptionIndexes;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ExceptionsAttribute() {
		super("Exceptions");
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2 + this.getExceptionIndexes().length * 2;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		ExceptionsAttribute target = (ExceptionsAttribute) object;

		return Arrays.equals(this.getExceptionIndexes(), target.getExceptionIndexes());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getExceptionIndexes().length);

		for(int index : this.getExceptionIndexes()) {
			output.writeShort(index);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int[] getExceptionIndexes() {
		return this.exceptionIndexes;
	}

	public void setExceptionIndexes(int[] exceptionIndexes) {
		this.exceptionIndexes = exceptionIndexes;
	}
}
