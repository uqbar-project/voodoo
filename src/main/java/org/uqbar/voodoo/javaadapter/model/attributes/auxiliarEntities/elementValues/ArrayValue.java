package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ArrayValue extends ElementValue {

	private ElementValue[] values;

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int answer = 2;

		for(ElementValue element : this.getValues()) {
			answer += element.getByteCount();
		}

		return answer;
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getValues().length);

		for(ElementValue element : this.getValues()) {
			element.writeTo(output);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public ElementValue[] getValues() {
		return this.values;
	}

	public void setValues(ElementValue[] values) {
		this.values = values;
	}
}
