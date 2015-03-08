package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public abstract class ConcreteEntry implements ConstantPoolEntry {

	private ConstantPool constantPool;
	private int tag;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ConcreteEntry(ConstantPool constantPool, int tag) {
		this.setConstantPool(constantPool);
		this.setTag(tag);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public boolean requiresPadding() {
		return false;
	}

	@Override
	public boolean contains(String content) {
		return this.getContent().equals(content);
	}

	public abstract String getContent();

	@Override
	public boolean containsContent(Object content) {
		return content.equals(this.getRealContent());
	}

	// TODO: Renombrar cuando ya no esté el getContent.
	// TODO: convertir a abstracto cuando todos tengan una implementación.
	public Object getRealContent() {
		return null;
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output) {
		output.writeByte(this.getTag());

		this.doWriteTo(output);
	}

	protected abstract void doWriteTo(ExtendedDataOutputStream output);

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return obj.getClass().equals(this.getClass()) && ((ConcreteEntry) obj).getTag() == this.getTag();
	}

	@Override
	public int hashCode() {
		return this.getTag();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected ConstantPool getConstantPool() {
		return this.constantPool;
	}

	protected void setConstantPool(ConstantPool constantPool) {
		this.constantPool = constantPool;
	}

	public int getTag() {
		return this.tag;
	}

	protected void setTag(int tag) {
		this.tag = tag;
	}
}
