package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.ByteHandler;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;

public class ConstantPoolArgument extends ByteHandledArgument {

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ConstantPoolArgument() {
		this(2);
	}

	public ConstantPoolArgument(int byteCount) {
		super(new ByteHandler(byteCount));
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	protected int getArgumentDigit(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		return constantPool.indexOfContent(call.getArgument(callArgumentIndex));
	};

	@Override
	public ArgumentStrategy asWideArgument() {
		return this;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void adaptParsedCalls(InstructionCall currentCall, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		currentCall.getArguments()[callArgumentIndex] = constantPool.get((int) currentCall.getArgument(callArgumentIndex)).getRealContent();
	}
	
	public void fillConstantPool(InstructionCall call, ConstantPool constantPool) {
		constantPool.push(call.getArgument(0));
	}
}