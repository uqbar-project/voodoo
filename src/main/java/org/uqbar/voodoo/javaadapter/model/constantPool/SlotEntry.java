package org.uqbar.voodoo.javaadapter.model.constantPool;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromName;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SlotEntry extends ConcreteEntry {

	public static final int FIELD_TAG = 9;
	public static final int METHOD_TAG = 10;
	public static final int INTERFACE_METHOD_TAG = 11;

	private int classIndex;
	private int signatureIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SlotEntry(ConstantPool constantPool, int tag, int classIndex, int signatureIndex) {
		super(constantPool, tag);

		this.setClassIndex(classIndex);
		this.setSignatureIndex(signatureIndex);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public String getClassName() {
		return this.getConstantPool().get(this.getClassIndex()).getContent();
	}

	public String getSignature() {
		return this.getConstantPool().get(this.getSignatureIndex()).getContent();
	}

	public String getSelector() {
		return this.getConstantPool().<SignatureEntry>get(this.getSignatureIndex()).getSelector();
	}

	public String getDescriptor() {
		return this.getConstantPool().<SignatureEntry>get(this.getSignatureIndex()).getDescriptor();
	}

	@Override
	public String getContent() {
		return this.getClassName() + "." + this.getSignature();
	}

	@Override
	public Object getRealContent() {
		SignatureEntry signatureEntry = this.getConstantPool().<SignatureEntry>get(this.getSignatureIndex());

		return new SlotReference(this.getTag(), typeFromName(this.getClassName()), signatureEntry.getSelector(), signatureEntry
			.getTypes());
	}

	@Override
	public int getByteCount() {
		return 1 + 2 + 2;
	}

	@Override
	public String toString() {
		return "#" + this.getClassIndex() + ".#" + this.getSignatureIndex() + " -> " + this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getClassIndex());
		output.writeShort(this.getSignatureIndex());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)
				&& ((SlotEntry) obj).getClassIndex() == this.getClassIndex()
				&& ((SlotEntry) obj).getSignatureIndex() == this.getSignatureIndex();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected int getClassIndex() {
		return this.classIndex;
	}

	protected void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	protected int getSignatureIndex() {
		return this.signatureIndex;
	}

	protected void setSignatureIndex(int signatureIndex) {
		this.signatureIndex = signatureIndex;
	}
}