package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.TargetedInstructionCall.target;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.ByteHandler;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;

public class CallOffsetArgument extends ByteHandledArgument {

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public CallOffsetArgument() {
		super(new ByteHandler(2, true));
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	protected int getArgumentDigit(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		return callContext.getOffsetDelta(call, call.<InstructionCall>getArgument(callArgumentIndex));
	}

	@Override
	public ArgumentStrategy asWideArgument() {
		CallOffsetArgument answer = new CallOffsetArgument();

		answer.setByteHandler(this.getByteHandler().wide());

		return answer;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void adaptParsedCalls(InstructionCall currentCall, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		target(currentCall, callContext, callArgumentIndex);
	}
}