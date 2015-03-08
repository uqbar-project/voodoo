package org.uqbar.voodoo.javaadapter.model.constantPool;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typesFromSignatureDescriptor;

import java.util.List;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SignatureEntry extends ConcreteEntry {

	public static final int TAG = 12;

	private int selectorIndex;
	private int descriptorIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SignatureEntry(ConstantPool constantPool, int selectorIndex, int descriptorIndex) {
		super(constantPool, TAG);

		this.setSelectorIndex(selectorIndex);
		this.setDescriptorIndex(descriptorIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String getContent() {
		return this.getSelector() + ":" + this.getDescriptor();
	}

	public String getSelector() {
		return this.getConstantPool().get(this.getSelectorIndex()).getContent();
	}

	public String getDescriptor() {
		return this.getConstantPool().get(this.getDescriptorIndex()).getContent();
	}

	public List<TypeReference> getTypes() {
		return typesFromSignatureDescriptor(this.getDescriptor());
	}

	@Override
	public int getByteCount() {
		return 1 + 2 + 2;
	}

	@Override
	public String toString() {
		return "#" + this.getSelectorIndex() + ":#" + this.getDescriptorIndex() + " -> " + this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getSelectorIndex());
		output.writeShort(this.getDescriptorIndex());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)
				&& ((SignatureEntry) obj).getDescriptorIndex() == this.getDescriptorIndex()
				&& ((SignatureEntry) obj).getSelectorIndex() == this.getSelectorIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getSelectorIndex() {
		return this.selectorIndex;
	}

	protected void setSelectorIndex(int selectorIndex) {
		this.selectorIndex = selectorIndex;
	}

	protected int getDescriptorIndex() {
		return this.descriptorIndex;
	}

	protected void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}
}