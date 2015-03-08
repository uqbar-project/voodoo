package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.ElementValue;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class Annotation {

	private int typeIndex;
	private Map<Integer, ElementValue> elements = new HashMap<>();

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public int getByteCount() {
		int answer = 2 + 2;

		for(Entry<Integer, ElementValue> element : this.getElements().entrySet()) {
			answer += 1 + element.getValue().getByteCount();
		}

		return answer;
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getTypeIndex());

		output.writeShort(this.getElements().size());
		for(Entry<Integer, ElementValue> element : this.getElements().entrySet()) {
			output.writeByte(element.getKey());

			element.getValue().writeTo(output);
		}
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		Annotation target = (Annotation) object;

		return this.getTypeIndex() == target.getTypeIndex()
				&& this.getElements().equals(target.getElements());
	}

	@Override
	public int hashCode() {
		return this.getTypeIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getTypeIndex() {
		return this.typeIndex;
	}

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public Map<Integer, ElementValue> getElements() {
		return this.elements;
	}

	public void setElements(Map<Integer, ElementValue> elements) {
		this.elements = elements;
	}
}
