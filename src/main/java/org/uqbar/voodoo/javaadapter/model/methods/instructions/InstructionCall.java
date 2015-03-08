package org.uqbar.voodoo.javaadapter.model.methods.instructions;

import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public interface InstructionCall {

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public abstract void fillConstantPool(ConstantPool constantPool);

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public abstract void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool);

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public abstract int getStackPopCount();

	public abstract int getStackPushCount();

	public abstract int getMinLocalsSizeRequired();

	public abstract int getByteCount();

	public abstract <T> T getArgument(int index);

	public abstract BytecodeInstruction getInstruction();

	public abstract Object[] getArguments();

	public abstract InstructionCall asJumpTarget();

	// TODO: No amerita hacer una clase abstracta y ya?
	public abstract CodeAttribute getOwner();

	public abstract void setOwner(CodeAttribute owner);

	public abstract int getAbsoluteOffset();

	public abstract int getCodeOffset();
}