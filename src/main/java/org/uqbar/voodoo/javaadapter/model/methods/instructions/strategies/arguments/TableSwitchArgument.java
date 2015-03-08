package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import static org.uqbar.voodoo.javaadapter.BytecodeToolsUtils.append;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.TargetedInstructionCall.target;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.SimpleInstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.ByteHandler;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public class TableSwitchArgument implements ArgumentStrategy {

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getArgumentByteCount(InstructionCall call) {
		int padding = this.getRequiredPadding(call);
		int low = call.getArgument(1);
		int high = low + call.getArguments().length - 2 - 1;
		int offsetCount = 1 + high - low;

		return padding + 4 + 4 + 4 + offsetCount * 4;
	}

	@Override
	public byte[] getArgumentBytes(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		int padding = this.getRequiredPadding(call);
		int defaultOffset = callContext.getOffsetDelta(call, call.<InstructionCall>getArgument(0));
		int low = call.getArgument(1);
		int high = low + call.getArguments().length - 2 - 1;

		ByteHandler byteHandler = new ByteHandler(4);
		byte[] answer = new byte[padding];

		answer = append(answer, byteHandler.toBytes(defaultOffset));
		answer = append(answer, byteHandler.toBytes(low));
		answer = append(answer, byteHandler.toBytes(high));

		while(answer.length < this.getArgumentByteCount(call)) {
			int offset = callContext.getOffsetDelta(call, call.<InstructionCall>getArgument((answer.length - padding) / 4 - 1));
			answer = append(answer, byteHandler.toBytes(offset));
		}

		return answer;
	}

	@Override
	public ArgumentStrategy asWideArgument() {
		return this;
	}

	// Repetido con LookupTableArgument
	protected int getRequiredPadding(InstructionCall call) {
		return (4 - (call.getCodeOffset() + 1) % 4) % 4;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public InstructionCall parseCall(ExtendedDataInputStream input, BytecodeInstruction instruction, int firstCallCodeOffset, ConstantPool constantPool) {
		while((input.getReaded() - firstCallCodeOffset) % 4 != 0) {
			input.readByte();
		}

		int defaultOffset = input.readInt();
		int low = input.readInt();
		int high = input.readInt();

		List<Object> answer = new ArrayList<>();
		answer.add(defaultOffset);
		answer.add(low);

		for(int i = 0; i < high - low + 1; i++) {
			answer.add(input.readInt());
		}

		return new SimpleInstructionCall(instruction, answer.toArray());
	};

	@Override
	public void adaptParsedCalls(InstructionCall currentCall, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		target(currentCall, callContext, 0);

		for(int argumentIndex = 2; argumentIndex < currentCall.getArguments().length; argumentIndex++) {
			target(currentCall, callContext, argumentIndex);
		}
	}

	@Override
	public void fillConstantPool(InstructionCall call, ConstantPool constantPool) {
	}
}