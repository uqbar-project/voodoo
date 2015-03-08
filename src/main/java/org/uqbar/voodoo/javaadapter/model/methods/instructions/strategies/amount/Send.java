package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

public class Send implements AmountStrategy {

	private String messageName;
	private int callArgumentIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Send(String messageName, int callArgumentIndex) {
		this.setMessageName(messageName);
		this.setCallArgumentIndex(callArgumentIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getAmount(InstructionCall call, ConstantPool constantPool) {
		Object argument = call.getArgument(this.getCallArgumentIndex());

		try {
			return (int) argument.getClass().getMethod(this.getMessageName()).invoke(argument);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	};

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected String getMessageName() {
		return this.messageName;
	}

	protected void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	protected int getCallArgumentIndex() {
		return this.callArgumentIndex;
	}

	protected void setCallArgumentIndex(int callArgumentIndex) {
		this.callArgumentIndex = callArgumentIndex;
	}
}