package org.uqbar.voodoo.javaadapter.model.attributes.code;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.LineNumber;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class LineNumberTableAttribute extends CodeAttributeAttribute {

	private List<LineNumber> lineNumbers;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public LineNumberTableAttribute() {
		super("LineNumberTable");

		this.setLineNumbers(new ArrayList<LineNumber>());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2 + this.getLineNumbers().size() * 4;

	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		LineNumberTableAttribute target = (LineNumberTableAttribute) object;

		return this.getLineNumbers().equals(target.getLineNumbers());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getLineNumbers().size());

		for(LineNumber lineNumber : this.getLineNumbers()) {
			lineNumber.writeTo(output);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addLineNumber(LineNumber lineNumber) {
		this.getLineNumbers().add(lineNumber);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public List<LineNumber> getLineNumbers() {
		return this.lineNumbers;
	}

	public void setLineNumbers(List<LineNumber> lineNumbers) {
		this.lineNumbers = lineNumbers;
	}
}
