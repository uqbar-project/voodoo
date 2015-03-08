package org.uqbar.voodoo.javaadapter.model.methods.instructions;

import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

//TODO: Reemplazar esto por la clase Proxy de java?
public class TargetedInstructionCall implements InstructionCall {

	private InstructionCall innerCall;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static void target(InstructionCall call, InstructionCallContext callContext, int argumentIndex) {
		int offsetDelta = call.getArgument(argumentIndex);
		InstructionCall targetCall = callContext.getCallAtOffset(call, offsetDelta);

		InstructionCall newCall = targetCall.asJumpTarget();
		callContext.replace(targetCall, newCall);
		call.getArguments()[argumentIndex] = newCall;
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public TargetedInstructionCall(InstructionCall innerCall) {
		this.setInnerCall(innerCall);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		this.getInnerCall().fillConstantPool(constantPool);
	}

	@Override
	public void setOwner(CodeAttribute owner) {
		this.getInnerCall().setOwner(owner);
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		this.getInnerCall().writeTo(output, callContext, constantPool);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getStackPopCount() {
		return this.getInnerCall().getStackPopCount();
	}

	@Override
	public int getStackPushCount() {
		return this.getInnerCall().getStackPushCount();
	}

	@Override
	public int getMinLocalsSizeRequired() {
		return this.getInnerCall().getMinLocalsSizeRequired();
	}

	@Override
	public int getByteCount() {
		return this.getInnerCall().getByteCount();
	}

	@Override
	public <T> T getArgument(int index) {
		return this.getInnerCall().getArgument(index);
	}

	@Override
	public BytecodeInstruction getInstruction() {
		return this.getInnerCall().getInstruction();
	}

	@Override
	public Object[] getArguments() {
		return this.getInnerCall().getArguments();
	}

	@Override
	public InstructionCall asJumpTarget() {
		return this;
	}

	@Override
	public CodeAttribute getOwner() {
		return this.getInnerCall().getOwner();
	}

	@Override
	public int getAbsoluteOffset() {
		return this.getInnerCall().getAbsoluteOffset();
	}

	@Override
	public int getCodeOffset() {
		return this.getInnerCall().getCodeOffset();
	}

	@Override
	public String toString() {
		return this.getInnerCall().toString();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		return this.getInnerCall().equals(object);
	}

	@Override
	public int hashCode() {
		return this.getInnerCall().hashCode();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected InstructionCall getInnerCall() {
		return this.innerCall;
	}

	protected void setInnerCall(InstructionCall innerCall) {
		this.innerCall = innerCall;
	}
}