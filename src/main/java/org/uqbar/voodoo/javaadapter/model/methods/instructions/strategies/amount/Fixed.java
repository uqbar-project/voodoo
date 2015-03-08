package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

public class Fixed implements AmountStrategy {

	public static final Fixed _0 = new Fixed(0);
	public static final Fixed _1 = new Fixed(1);
	public static final Fixed _2 = new Fixed(2);
	public static final Fixed _3 = new Fixed(3);
	public static final Fixed _4 = new Fixed(4);
	public static final Fixed _5 = new Fixed(5);
	public static final Fixed _6 = new Fixed(6);

	private int amount;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Fixed(int amount) {
		this.setAmount(amount);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getAmount(InstructionCall call, ConstantPool constantPool) {
		return this.getAmount();
	};

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getAmount() {
		return this.amount;
	}

	protected void setAmount(int amount) {
		this.amount = amount;
	}
}