package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.Arrays;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SourceDebugExtensionAttribute extends Attribute {

	private byte[] debugExtension;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SourceDebugExtensionAttribute() {
		super("SourceDebugExtension");
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return this.getDebugExtension().length;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		SourceDebugExtensionAttribute target = (SourceDebugExtensionAttribute) object;

		return Arrays.equals(this.getDebugExtension(), target.getDebugExtension());
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.write(this.getDebugExtension());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public byte[] getDebugExtension() {
		return this.debugExtension;
	}

	public void setDebugExtension(byte[] debugExtension) {
		this.debugExtension = debugExtension;
	}
}
