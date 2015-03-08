package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class MethodTypeEntry extends ConcreteEntry {

	public static final int TAG = 16;

	private int descriptorIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public MethodTypeEntry(ConstantPool constantPool, int descriptorIndex) {
		super(constantPool, TAG);

		this.setDescriptorIndex(descriptorIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String getContent() {
		return this.getConstantPool().get(this.getDescriptorIndex()).getContent();
	}

	@Override
	public int getByteCount() {
		return 1 + 2;
	}

	@Override
	public String toString() {
		return "#" + this.getDescriptorIndex() + " -> " + this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getDescriptorIndex());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((MethodTypeEntry) obj).getDescriptorIndex() == this.getDescriptorIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getDescriptorIndex() {
		return this.descriptorIndex;
	}

	protected void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}
}