package org.uqbar.voodoo.javaadapter.model.attributes;

import org.uqbar.voodoo.javaadapter.model.Attributable;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.constantPool.UTF8Entry;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public abstract class Attribute {

	private Attributable owner;
	private String label;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Attribute(String label) {
		this.setLabel(label);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void fillConstantPool(ConstantPool constantPool) {
		constantPool.push(this.getLabel());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public abstract int getByteCount();

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public abstract boolean equals(Object object);

	// TODO: Mejorar.
	@Override
	public int hashCode() {
		return this.getLabel().hashCode();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(constantPool.indexOf(this.getLabel(), UTF8Entry.class));
		output.writeInt(this.getByteCount());

		this.doWriteTo(output, constantPool);
	}

	protected abstract void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool);

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public String getLabel() {
		return this.label;
	}

	protected void setLabel(String label) {
		this.label = label;
	}

	public Attributable getOwner() {
		return this.owner;
	}

	public void setOwner(Attributable owner) {
		this.owner = owner;
	}
}