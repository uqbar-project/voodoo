package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ChopFrame extends StackMapFrame {

	private int offsetDelta;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ChopFrame(int offsetDelta, int choppedLocals) {
		super(251 - choppedLocals);

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
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		ChopFrame target = (ChopFrame) object;

		return this.getType() == target.getType()
				&& this.getOffsetDelta() == target.getOffsetDelta();
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
