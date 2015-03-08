package org.uqbar.voodoo.javaadapter.model.attributes.code;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.StackMapFrame;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class StackMapTableAttribute extends CodeAttributeAttribute {

	private List<StackMapFrame> frames;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public StackMapTableAttribute() {
		super("StackMapTable");

		this.setFrames(new ArrayList<StackMapFrame>());
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		super.fillConstantPool(constantPool);

		for(StackMapFrame frame : this.getFrames()) {
			frame.fillConstantPool(constantPool);
		}
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int entriesSize = 0;

		for(StackMapFrame frame : this.getFrames()) {
			entriesSize += 1 + frame.getByteCount();
		}

		return 2 + entriesSize;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		StackMapTableAttribute target = (StackMapTableAttribute) object;

		return this.getFrames().equals(target.getFrames());
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getFrames().size());

		for(StackMapFrame frame : this.getFrames()) {
			frame.writeTo(output, callContext, constantPool);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addFrame(StackMapFrame frame) {
		this.getFrames().add(frame);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public List<StackMapFrame> getFrames() {
		return this.frames;
	}

	public void setFrames(List<StackMapFrame> frames) {
		this.frames = frames;
	}
}
