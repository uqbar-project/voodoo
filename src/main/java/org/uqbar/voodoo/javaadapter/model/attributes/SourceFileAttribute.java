package org.uqbar.voodoo.javaadapter.model.attributes;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.constantPool.UTF8Entry;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SourceFileAttribute extends Attribute {

	private String sourceFileName;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SourceFileAttribute() {
		super("SourceFile");
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		super.fillConstantPool(constantPool);

		constantPool.push(this.getSourceFileName());
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

		SourceFileAttribute target = (SourceFileAttribute) object;

		return this.getSourceFileName().equals(target.getSourceFileName());
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(constantPool.indexOf(this.getSourceFileName(), UTF8Entry.class));
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public String getSourceFileName() {
		return this.sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
}
