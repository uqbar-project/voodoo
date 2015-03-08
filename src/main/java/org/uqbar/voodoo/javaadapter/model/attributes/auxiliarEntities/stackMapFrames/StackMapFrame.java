package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public abstract class StackMapFrame {

	private int type;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public StackMapFrame(int type) {
		this.setType(type);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public abstract int getByteCount();

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public abstract boolean equals(Object object);

	// TODO: Mejorar.
	@Override
	public int hashCode() {
		return this.getType();
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void fillConstantPool(ConstantPool answer) {
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeByte(this.getType());

		this.doWriteTo(output, callContext, constantPool);
	}

	protected abstract void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool);

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
