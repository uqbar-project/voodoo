package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

public abstract interface AmountStrategy {

	public abstract int getAmount(InstructionCall call, ConstantPool constantPool);
}