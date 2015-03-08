package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.ByteHandler;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;

public class NumericArgument extends ByteHandledArgument {

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public NumericArgument(int argumentByteCount) {
		this(argumentByteCount, false);
	}

	public NumericArgument(int argumentByteCount, boolean signed) {
		super(new ByteHandler(argumentByteCount, signed));
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	protected int getArgumentDigit(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		return call.getArgument(callArgumentIndex);
	};

	@Override
	public ArgumentStrategy asWideArgument() {
		return new NumericArgument(this.getByteHandler().getByteCount() * 2);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void adaptParsedCalls(InstructionCall targetCall, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
	}
}