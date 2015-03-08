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

public class LookupSwitchArgument implements ArgumentStrategy {

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getArgumentByteCount(InstructionCall call) {
		return this.getRequiredPadding(call) + 4 + 4 + (call.getArguments().length - 1) * 4;
	}

	@Override
	public byte[] getArgumentBytes(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		int padding = this.getRequiredPadding(call);
		int defaultOffset = callContext.getOffsetDelta(call, call.<InstructionCall>getArgument(0));
		int keyValueOffsetCount = (call.getArguments().length - 1) / 2;

		ByteHandler byteHandler = new ByteHandler(4);
		byte[] answer = new byte[padding];

		answer = append(answer, byteHandler.toBytes(defaultOffset));
		answer = append(answer, byteHandler.toBytes(keyValueOffsetCount));

		while(answer.length < this.getArgumentByteCount(call)) {
			Integer key = call.<Integer>getArgument((answer.length - padding) / 4 - 1);
			answer = append(answer, byteHandler.toBytes(key));

			int value = callContext.getOffsetDelta(call, call.<InstructionCall>getArgument((answer.length - padding) / 4 - 1));
			answer = append(answer, byteHandler.toBytes(value));
		}

		return answer;
	}

	@Override
	public ArgumentStrategy asWideArgument() {
		return this;
	}

	protected int getRequiredPadding(InstructionCall call) {
		return (4 - (call.getCodeOffset() + 1) % 4) % 4;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	// TODO: Acá hay código común con CallOffsetArgument y SwitchTableArgument
	public InstructionCall parseCall(ExtendedDataInputStream input, BytecodeInstruction instruction, int firstCallCodeOffset, ConstantPool constantPool) {
		while((input.getReaded() - firstCallCodeOffset) % 4 != 0) {
			input.readByte();
		}

		int defaultOffset = input.readInt();

		List<Object> args = new ArrayList<>();
		args.add(defaultOffset);

		int keyValueOffsetCount = input.readInt();
		for(int i = 0; i < keyValueOffsetCount * 2; i++) {
			args.add(input.readInt());
		}

		return new SimpleInstructionCall(instruction, args.toArray());
	}

	@Override
	// TODO: Acá hay código común con CallOffsetArgument y SwitchTableArgument
	public void adaptParsedCalls(InstructionCall currentCall, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool) {
		target(currentCall, callContext, 0);

		for(int argumentIndex = 2; argumentIndex < currentCall.getArguments().length; argumentIndex += 2) {
			target(currentCall, callContext, argumentIndex);
		}
	}

	@Override
	public void fillConstantPool(InstructionCall call, ConstantPool constantPool) {
	}
}