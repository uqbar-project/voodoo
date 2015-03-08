package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

public class Inc extends CallArgument {

	private int increment;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Inc(int callArgumentIndex) {
		this(callArgumentIndex, 1);
	}

	public Inc(int callArgumentIndex, int increment) {
		super(callArgumentIndex);

		this.setIncrement(increment);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getAmount(InstructionCall call, ConstantPool constantPool) {
		return super.getAmount(call, constantPool) + this.getIncrement();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getIncrement() {
		return this.increment;
	}

	protected void setIncrement(int increment) {
		this.increment = increment;
	};
}