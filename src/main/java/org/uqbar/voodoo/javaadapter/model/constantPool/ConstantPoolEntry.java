package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public interface ConstantPoolEntry {

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public abstract boolean contains(String content);

	// TODO: Renombrar cuando ya no esté el containss
	public abstract boolean containsContent(Object content);

	// TODO: Renombrar cuando ya no esté el containss
	public abstract Object getRealContent();

	public abstract int getByteCount();

	// ****************************************************************
	// ** BYTECODE WRITTING
	// ****************************************************************

	public abstract void writeTo(ExtendedDataOutputStream output);

}