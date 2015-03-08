package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import java.util.Arrays;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public class InterfaceMethodArgument extends ConstantPoolArgument {

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InterfaceMethodArgument(int callArgumentIndex) {
		super();
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getArgumentByteCount(InstructionCall call) {
		return super.getArgumentByteCount(call) + 2;
	};

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public InstructionCall parseCall(ExtendedDataInputStream input, BytecodeInstruction instruction, int firstCallCodeOffset, ConstantPool constantPool) {
		InstructionCall answer = super.parseCall(input, instruction, firstCallCodeOffset, constantPool);

		input.readByte(); // n

		input.readByte(); // padding

		return answer;
	}

	@Override
	public byte[] getArgumentBytes(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		byte[] answer = super.getArgumentBytes(call, callContext, callArgumentIndex, constantPool);

		answer = Arrays.copyOf(answer, answer.length + 2);
		answer[answer.length - 2] = (byte) (1 + call.<SlotReference>getArgument(0).getArgumentCount());

		return answer;
	}
}