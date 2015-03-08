package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ReferenceKind;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class MethodHandlerEntry extends ConcreteEntry {

	public static final int TAG = 15;

	private ReferenceKind referenceKind;
	private int referenceIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public MethodHandlerEntry(ConstantPool constantPool, ReferenceKind referenceKind, int referenceIndex) {
		super(constantPool, TAG);

		this.setReferenceKind(referenceKind);
		this.setReferenceIndex(referenceIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String getContent() {
		return "(" + this.getReferenceKind() + ")" + this.getConstantPool().get(this.getReferenceIndex()).getContent();
	}

	@Override
	public int getByteCount() {
		return 1 + 1 + 2;
	}

	@Override
	public String toString() {
		return "#" + this.getReferenceIndex() + " -> " + this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeByte(this.getReferenceKind().getKey());
		output.writeShort(this.getReferenceIndex());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)
				&& ((MethodHandlerEntry) obj).getReferenceKind() == this.getReferenceKind()
				&& ((MethodHandlerEntry) obj).getReferenceIndex() == this.getReferenceIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public ReferenceKind getReferenceKind() {
		return this.referenceKind;
	}

	protected void setReferenceKind(ReferenceKind referenceKind) {
		this.referenceKind = referenceKind;
	}

	public int getReferenceIndex() {
		return this.referenceIndex;
	}

	protected void setReferenceIndex(int referenceIndex) {
		this.referenceIndex = referenceIndex;
	}

}