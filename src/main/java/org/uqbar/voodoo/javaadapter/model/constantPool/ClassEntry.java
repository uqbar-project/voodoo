package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ClassEntry extends ConcreteEntry {

	public static final int TAG = 7;

	private int nameIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ClassEntry(ConstantPool constantPool, int nameIndex) {
		super(constantPool, TAG);

		this.setNameIndex(nameIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String getContent() {
		return this.getConstantPool().get(this.getNameIndex()).getContent();
	}

	@Override
	public TypeReference getRealContent() {
		return new TypeReference(this.getContent());
	}

	@Override
	public int getByteCount() {
		return 1 + 2;
	}

	@Override
	public String toString() {
		return "#" + this.getNameIndex() + " -> " + this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getNameIndex());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ClassEntry) obj).getNameIndex() == this.getNameIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getNameIndex() {
		return this.nameIndex;
	}

	protected void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}
}