package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public abstract class ElementValue {

	private char tag;

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public abstract int getByteCount();

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		ElementValue target = (ElementValue) object;

		return this.getTag() == target.getTag();
	};

	@Override
	public int hashCode() {
		return this.getTag();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public abstract void writeTo(ExtendedDataOutputStream output);

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public char getTag() {
		return this.tag;
	}

	public void setTag(char tag) {
		this.tag = tag;
	}
}
