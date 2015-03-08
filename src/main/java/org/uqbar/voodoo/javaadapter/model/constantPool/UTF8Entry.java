package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.BytecodeToolsUtils;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class UTF8Entry extends ConcreteEntry {

	public static final int TAG = 1;
	private String content;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public UTF8Entry(ConstantPool constantPool, String content) {
		super(constantPool, TAG);

		this.setContent(content);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 1 + BytecodeToolsUtils.utf8Length(this.getContent());
	};

	@Override
	public String toString() {
		return this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		output.writeUTF(this.getContent());
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((UTF8Entry) obj).getContent().equals(this.getContent());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	@Override
	public String getContent() {
		return this.content;
	}

	protected void setContent(String content) {
		this.content = content;
	}
}