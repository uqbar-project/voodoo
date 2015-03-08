package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities;

import org.uqbar.voodoo.javaadapter.model.modifiers.ClassModifier;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class InnerClass {

	private int innerClassInfoIndex;
	private int outerClassInfoIndex;
	private int innerNameIndex;
	private ClassModifier modifier;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InnerClass() {
		this.setModifier(new ClassModifier());
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getInnerClassInfoIndex());
		output.writeShort(this.getOuterClassInfoIndex());
		output.writeShort(this.getInnerNameIndex());
		this.getModifier().writeTo(output);
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		InnerClass target = (InnerClass) object;

		return this.getInnerClassInfoIndex() == target.getInnerClassInfoIndex()
				&& this.getInnerNameIndex() == target.getInnerNameIndex()
				&& this.getOuterClassInfoIndex() == this.getOuterClassInfoIndex()
				&& this.getModifier().equals(target.getModifier());
	};

	@Override
	public int hashCode() {
		return this.getInnerNameIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getInnerClassInfoIndex() {
		return this.innerClassInfoIndex;
	}

	public void setInnerClassInfoIndex(int innerClassInfoIndex) {
		this.innerClassInfoIndex = innerClassInfoIndex;
	}

	public int getOuterClassInfoIndex() {
		return this.outerClassInfoIndex;
	}

	public void setOuterClassInfoIndex(int outerClassInfoIndex) {
		this.outerClassInfoIndex = outerClassInfoIndex;
	}

	public int getInnerNameIndex() {
		return this.innerNameIndex;
	}

	public void setInnerNameIndex(int innerNameIndex) {
		this.innerNameIndex = innerNameIndex;
	}

	public ClassModifier getModifier() {
		return this.modifier;
	}

	public void setModifier(ClassModifier modifier) {
		this.modifier = modifier;
	}
}