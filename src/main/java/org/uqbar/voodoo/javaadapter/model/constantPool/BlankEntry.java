package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class BlankEntry implements ConstantPoolEntry {

	public static final BlankEntry INSTANCE = new BlankEntry();

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	private BlankEntry() {
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public boolean contains(String content) {
		return false;
	};

	@Override
	public boolean containsContent(Object content) {
		return false;
	}

	@Override
	public Object getRealContent() {
		return null;
	}

	@Override
	public int getByteCount() {
		return 0;
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output) {
	}
}