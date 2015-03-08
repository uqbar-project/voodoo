package org.uqbar.voodoo.javaadapter.model.attributes;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SignatureAttribute extends Attribute {

	private int signatureIndex;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SignatureAttribute() {
		super("Signature");
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		SignatureAttribute target = (SignatureAttribute) object;

		return this.getSignatureIndex() == target.getSignatureIndex();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getSignatureIndex());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getSignatureIndex() {
		return this.signatureIndex;
	}

	public void setSignatureIndex(int signatureIndex) {
		this.signatureIndex = signatureIndex;
	}
}
