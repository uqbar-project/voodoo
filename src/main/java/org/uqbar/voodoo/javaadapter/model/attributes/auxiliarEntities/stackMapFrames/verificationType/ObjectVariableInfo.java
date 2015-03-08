package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ObjectVariableInfo implements VerificationTypeInfo {

	private TypeReference type;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ObjectVariableInfo(TypeReference type) {
		this.setType(type);
	}

	public ObjectVariableInfo(String typeName) {
		this.setType(TypeReference.typeFromName(typeName));
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
		return this.getType().getName();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		ObjectVariableInfo target = (ObjectVariableInfo) object;

		return this.getType().equals(target.getType());
	}

	@Override
	public int hashCode() {
		return this.getType().hashCode();
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		constantPool.push(this.getType());
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeByte(7);
		output.writeShort(constantPool.indexOfContent(this.getType()));
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public TypeReference getType() {
		return this.type;
	}

	protected void setType(TypeReference type) {
		this.type = type;
	}
}