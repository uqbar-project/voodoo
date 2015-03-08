package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

public class CallArgument implements AmountStrategy {

	private int callArgumentIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public CallArgument(int callArgumentIndex) {
		this.setCallArgumentIndex(callArgumentIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getAmount(InstructionCall call, ConstantPool constantPool) {
		return call.getArgument(this.getCallArgumentIndex());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getCallArgumentIndex() {
		return this.callArgumentIndex;
	}

	protected void setCallArgumentIndex(int callArgumentIndex) {
		this.callArgumentIndex = callArgumentIndex;
	}
}