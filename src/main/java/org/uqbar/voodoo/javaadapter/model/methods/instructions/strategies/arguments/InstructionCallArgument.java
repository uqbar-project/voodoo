package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import static org.uqbar.voodoo.javaadapter.BytecodeToolsUtils.append;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.SimpleInstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.ByteHandler;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public class InstructionCallArgument implements ArgumentStrategy {

	private int callArgumentIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InstructionCallArgument(int callArgumentIndex) {
		this.setCallArgumentIndex(callArgumentIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public byte[] getArgumentBytes(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		InstructionCall innerCall = call.getArgument(this.getCallArgumentIndex());

		byte[] answer = new byte[] { (byte) innerCall.getInstruction().getCode() };

		for(int i = 0; i < innerCall.getArguments().length; i++) {
			answer = append(answer, new ByteHandler(2).toBytes(innerCall.<Integer>getArgument(i)));
		}

		return answer;
	}

	@Override
	public int getArgumentByteCount(InstructionCall call) {
		InstructionCall innerCall = call.getArgument(this.getCallArgumentIndex());

		return innerCall.getInstruction().getArgumentByteCount(innerCall) * 2 + 1;
	}

	@Override
	public ArgumentStrategy asWideArgument() {
		return this;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public InstructionCall parseCall(ExtendedDataInputStream input, BytecodeInstruction instruction, int firstCallCodeOffset, ConstantPool constantPool) {
		BytecodeInstruction innerInstruction = BytecodeInstruction.fromCode(input.readUnsignedByte());

		return new SimpleInstructionCall(instruction, innerInstruction.parseWideCall(input, innerInstruction, firstCallCodeOffset, constantPool));
	}

	@Override
	public void adaptParsedCalls(InstructionCall targetCall, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
	}

	@Override
	public void fillConstantPool(InstructionCall call, ConstantPool constantPool) {
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