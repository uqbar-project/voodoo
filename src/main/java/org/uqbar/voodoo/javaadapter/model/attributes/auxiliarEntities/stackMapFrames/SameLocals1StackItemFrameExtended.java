package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.VerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SameLocals1StackItemFrameExtended extends SameLocals1StackItemFrame {

	private int offsetDelta;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SameLocals1StackItemFrameExtended(int offsetDelta, VerificationTypeInfo typeInfo) {
		super(0, typeInfo);

		this.setType(247);
		this.setOffsetDelta(offsetDelta);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2 + this.getTypeInfo().getByteCount();

	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		return super.equals(object) && this.getOffsetDelta() == ((SameLocals1StackItemFrameExtended) object).getOffsetDelta();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getOffsetDelta());

		super.writeTo(output, callContext, constantPool);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getOffsetDelta() {
		return this.offsetDelta;
	}

	public void setOffsetDelta(int offsetDelta) {
		this.offsetDelta = offsetDelta;
	}
}
