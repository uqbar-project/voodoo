package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import java.util.Arrays;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public class InvokeDynamicArgument extends ConstantPoolArgument {

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InvokeDynamicArgument(int callArgumentIndex) {
		super();
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getArgumentByteCount(InstructionCall call) {
		return super.getArgumentByteCount(call) + 2;
	}

	@Override
	public byte[] getArgumentBytes(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		byte[] answer = super.getArgumentBytes(call, callContext, callArgumentIndex, constantPool);

		return Arrays.copyOf(answer, answer.length + 2);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public InstructionCall parseCall(ExtendedDataInputStream input, BytecodeInstruction instruction, int firstCallCodeOffset, ConstantPool constantPool) {
		InstructionCall answer = super.parseCall(input, instruction, firstCallCodeOffset, constantPool);

		input.readByte(); // padding
		input.readByte(); // padding

		return answer;
	}
}