package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SameFrame extends StackMapFrame {

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SameFrame(int offsetDelta) {
		super(offsetDelta);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 0;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		SameFrame target = (SameFrame) object;

		return this.getType() == target.getType();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
	}
}
