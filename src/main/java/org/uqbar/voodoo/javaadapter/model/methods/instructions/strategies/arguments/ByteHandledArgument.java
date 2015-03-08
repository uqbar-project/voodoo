package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.SimpleInstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.ByteHandler;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public abstract class ByteHandledArgument implements ArgumentStrategy {

	private ByteHandler byteHandler;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ByteHandledArgument(ByteHandler byteHandler) {
		this.setByteHandler(byteHandler);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getArgumentByteCount(InstructionCall call) {
		return this.getByteHandler().getByteCount();
	}

	@Override
	public byte[] getArgumentBytes(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		int digit = this.getArgumentDigit(call, callContext, callArgumentIndex, constantPool);

		return this.getByteHandler().toBytes(digit);
	}

	protected abstract int getArgumentDigit(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool);

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public InstructionCall parseCall(ExtendedDataInputStream input, BytecodeInstruction instruction, int firstCallCodeOffset, ConstantPool constantPool) {
		return new SimpleInstructionCall(instruction, this.getByteHandler().parseBytes(input));
	}

	@Override
	public void fillConstantPool(InstructionCall call, ConstantPool constantPool) {
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected ByteHandler getByteHandler() {
		return this.byteHandler;
	}

	protected void setByteHandler(ByteHandler byteHandler) {
		this.byteHandler = byteHandler;
	}
}