package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

//TODO: Reemplazar esta interfaz por TypeReference directamente?
public interface VerificationTypeInfo {
	public abstract void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool);

	public abstract int getByteCount();

	public abstract void fillConstantPool(ConstantPool constantPool);
}
