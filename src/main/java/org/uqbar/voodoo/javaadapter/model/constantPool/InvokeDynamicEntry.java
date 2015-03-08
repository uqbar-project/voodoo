package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.InvokeDynamicReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class InvokeDynamicEntry extends ConcreteEntry {

	public static final int TAG = 18;

	private int bootstrapMethodAttributeIndex;
	private int signatureIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InvokeDynamicEntry(ConstantPool constantPool, int bootstrapMethodAttributeIndex, int signatureIndex) {
		super(constantPool, TAG);

		this.setBootstrapMethodAttributeIndex(bootstrapMethodAttributeIndex);
		this.setSignatureIndex(signatureIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String getContent() {
		return this.bootstrapMethodAttributeIndex + ":" + this.getConstantPool().get(this.getSignatureIndex()).getContent();
	}

	@Override
	public Object getRealContent() {
		SignatureEntry signatureEntry = this.getConstantPool().<SignatureEntry>get(this.getSignatureIndex());

		return new InvokeDynamicReference(this.bootstrapMethodAttributeIndex, this.getSelector(), signatureEntry.getTypes());
	}

	public String getSelector() {
		return this.getConstantPool().<SignatureEntry>get(this.getSignatureIndex()).getSelector();
	}

	public String getDescriptor() {
		return this.getConstantPool().<SignatureEntry>get(this.getSignatureIndex()).getDescriptor();
	}

	@Override
	public int getByteCount() {
		return 1 + 2 + 2;
	}

	@Override
	public String toString() {
		return "#" + this.getSignatureIndex() + " -> " + this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getBootstrapMethodAttributeIndex());
		output.writeShort(this.getSignatureIndex());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)
				&& ((InvokeDynamicEntry) obj).getBootstrapMethodAttributeIndex() == this.getBootstrapMethodAttributeIndex()
				&& ((InvokeDynamicEntry) obj).getSignatureIndex() == this.getSignatureIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getBootstrapMethodAttributeIndex() {
		return this.bootstrapMethodAttributeIndex;
	}

	protected void setBootstrapMethodAttributeIndex(int bootstrapMethodAttributeIndex) {
		this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
	}

	protected int getSignatureIndex() {
		return this.signatureIndex;
	}

	protected void setSignatureIndex(int signatureIndex) {
		this.signatureIndex = signatureIndex;
	}
}