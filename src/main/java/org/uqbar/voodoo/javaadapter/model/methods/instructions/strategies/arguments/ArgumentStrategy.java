package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public interface ArgumentStrategy {
	public abstract int getArgumentByteCount(InstructionCall call);

	public abstract byte[] getArgumentBytes(InstructionCall call, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool);

	// TODO: Hace falta este método acá? Lo implementa un solo argumento...
	public abstract ArgumentStrategy asWideArgument();

	public abstract InstructionCall parseCall(ExtendedDataInputStream input, BytecodeInstruction instruction, int firstCallCodeOffset, ConstantPool constantPool);

	public abstract void adaptParsedCalls(InstructionCall targetCall, InstructionCallContext callContext, int callArgumentIndex, ConstantPool constantPool);

	public void fillConstantPool(InstructionCall call, ConstantPool constantPool);
}