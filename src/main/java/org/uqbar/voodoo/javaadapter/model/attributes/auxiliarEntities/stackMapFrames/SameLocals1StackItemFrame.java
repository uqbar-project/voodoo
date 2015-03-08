package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.VerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SameLocals1StackItemFrame extends StackMapFrame {

	private VerificationTypeInfo typeInfo;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SameLocals1StackItemFrame(int offsetDelta, VerificationTypeInfo typeInfo) {
		super(offsetDelta + 64);

		this.setTypeInfo(typeInfo);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return this.getTypeInfo().getByteCount();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		SameLocals1StackItemFrame target = (SameLocals1StackItemFrame) object;

		return this.getType() == target.getType()
				&& this.getTypeInfo().equals(target.getTypeInfo());
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		this.getTypeInfo().fillConstantPool(constantPool);
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		this.getTypeInfo().writeTo(output, callContext, constantPool);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public VerificationTypeInfo getTypeInfo() {
		return this.typeInfo;
	}

	public void setTypeInfo(VerificationTypeInfo typeInfo) {
		this.typeInfo = typeInfo;
	}
}