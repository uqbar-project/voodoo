package org.uqbar.voodoo.javaadapter.model;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.Attribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public abstract class Attributable {

	private List<Attribute> attributes;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Attributable() {
		this.setAttributes(new ArrayList<Attribute>());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	protected int getAttributesByteCount() {
		int answer = 0;

		for(Attribute attribute : this.getAttributes()) {
			answer += attribute.getByteCount();
		}

		return answer;
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	protected void writeAttributes(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getAttributes().size());

		for(Attribute attribute : this.getAttributes()) {
			attribute.writeTo(output, constantPool);
		}
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object.getClass() != this.getClass()) {
			return false;
		}

		Attributable target = (Attributable) object;

		return this.getAttributes().size() == target.getAttributes().size()
				&& this.getAttributes().containsAll(target.getAttributes());
	}

	@Override
	public abstract int hashCode();

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addAttribute(Attribute attribute) {
		this.getAttributes().add(attribute);

		attribute.setOwner(this);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public List<Attribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
