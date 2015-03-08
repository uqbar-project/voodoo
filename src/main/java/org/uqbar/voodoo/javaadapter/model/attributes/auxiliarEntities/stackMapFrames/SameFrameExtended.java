package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SameFrameExtended extends SameFrame {

	private int offsetDelta;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SameFrameExtended(int offsetDelta) {
		super(251);

		this.setOffsetDelta(offsetDelta);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		return super.equals(object) && this.getOffsetDelta() == ((SameFrameExtended) object).getOffsetDelta();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getOffsetDelta());
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