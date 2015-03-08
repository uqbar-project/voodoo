package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.DataConstantReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class StringEntry extends ConcreteEntry {

	public static final int STRING_TAG = 8;
	private int stringIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public StringEntry(ConstantPool constantPool, int nameIndex) {
		super(constantPool, STRING_TAG);

		this.setStringIndex(nameIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String getContent() {
		return this.getConstantPool().get(this.getStringIndex()).getContent();
	}

	@Override
	public DataConstantReference getRealContent() {
		return new DataConstantReference(this.getContent());
	}

	@Override
	public int getByteCount() {
		return 1 + 2;
	}

	@Override
	public String toString() {
		return "#" + this.getStringIndex() + " -> " + this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getStringIndex());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((StringEntry) obj).getStringIndex() == this.getStringIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getStringIndex() {
		return this.stringIndex;
	}

	protected void setStringIndex(int nameIndex) {
		this.stringIndex = nameIndex;
	}
}