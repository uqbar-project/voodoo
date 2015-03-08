package org.uqbar.voodoo.javaadapter.builder;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

public class MaxStackCalculator extends CodeVisitor {

	private int maxStack = 0;
	private int currentStack = 0;

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	protected void visit(InstructionCall call, int callIndex) {
		this.setCurrentStack(this.getCurrentStack() - call.getStackPopCount());
		this.setCurrentStack(this.getCurrentStack() + call.getStackPushCount());
		this.setMaxStack(Math.max(this.getMaxStack(), this.getCurrentStack()));
	}

	@Override
	protected void mergeWith(CodeVisitor other) {
		this.setMaxStack(Math.max(this.getMaxStack(), ((MaxStackCalculator) other).getMaxStack()));
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getMaxStack() {
		return this.maxStack;
	}

	protected void setMaxStack(int maxStack) {
		this.maxStack = maxStack;
	}

	protected int getCurrentStack() {
		return this.currentStack;
	}

	protected void setCurrentStack(int currentStack) {
		this.currentStack = currentStack;
	}
}