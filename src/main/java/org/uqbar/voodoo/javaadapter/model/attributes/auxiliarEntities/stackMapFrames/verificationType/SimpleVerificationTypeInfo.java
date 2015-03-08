package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public enum SimpleVerificationTypeInfo implements VerificationTypeInfo {
	TOP(0), INTEGER(1), FLOAT(2), DOUBLE(3), LONG(4), NULL(5), UNINITIALIZED_THIS(6);

	private int type;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	private SimpleVerificationTypeInfo(int type) {
		this.setType(type);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 1;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeByte(this.getType());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
