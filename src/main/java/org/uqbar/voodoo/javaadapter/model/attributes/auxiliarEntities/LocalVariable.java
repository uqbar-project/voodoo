package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class LocalVariable {
	private int startPC;
	private int length;
	private int nameIndex;
	private int descriptorIndex;
	private int index;

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getStartPC());
		output.writeShort(this.getLength());
		output.writeShort(this.getNameIndex());
		output.writeShort(this.getDescriptorIndex());
		output.writeShort(this.getIndex());
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		LocalVariable target = (LocalVariable) object;

		return this.getIndex() == target.getIndex()
				&& this.getNameIndex() == target.getNameIndex()
				&& this.getDescriptorIndex() == target.getDescriptorIndex()
				&& this.getStartPC() == target.getStartPC()
				&& this.getLength() == target.getLength();
	};

	@Override
	public int hashCode() {
		return this.getIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getStartPC() {
		return this.startPC;
	}

	public void setStartPC(int startPC) {
		this.startPC = startPC;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getNameIndex() {
		return this.nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public int getDescriptorIndex() {
		return this.descriptorIndex;
	}

	public void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
