package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class UninitializedVariableInfo implements VerificationTypeInfo {

	private InstructionCall targetCall;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public UninitializedVariableInfo(InstructionCall targetCall) {
		this.setTargetCall(targetCall);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 3;
	}

	@Override
	public String toString() {
		return "Uninitialized " + this.getTargetCall().<TypeReference>getArgument(0).getName();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		UninitializedVariableInfo target = (UninitializedVariableInfo) object;

		return this.getTargetCall().equals(target.getTargetCall());
	}

	@Override
	public int hashCode() {
		return this.getTargetCall().hashCode();
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeByte(8);
		output.writeShort(callContext.getCodeOffset(this.getTargetCall()));
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected InstructionCall getTargetCall() {
		return this.targetCall;
	}

	protected void setTargetCall(InstructionCall targetCall) {
		this.targetCall = targetCall;
	}
}