package org.uqbar.voodoo.javaadapter.model.constantPool;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.DataConstantReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class NumberEntry extends ConcreteEntry {

	public static final int INTEGER_TAG = 3;
	public static final int FLOAT_TAG = 4;
	public static final int LONG_TAG = 5;
	public static final int DOUBLE_TAG = 6;

	private Number number;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public NumberEntry(ConstantPool constantPool, int tag, Number number) {
		super(constantPool, tag);

		this.setNumber(number);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public boolean requiresPadding() {
		return this.getTag() == LONG_TAG || this.getTag() == DOUBLE_TAG;
	};

	@Override
	public String getContent() {
		return this.getNumber().toString();
	};

	@Override
	public DataConstantReference getRealContent() {
		return new DataConstantReference(this.getNumber());
	};

	@Override
	public int getByteCount() {
		switch(this.getTag()) {
			case INTEGER_TAG:
			case FLOAT_TAG:
				return 4;
			case LONG_TAG:
			case DOUBLE_TAG:
				return 8;
			default:
				throw new RuntimeException("Unsuported number type");
		}
	}

	@Override
	public String toString() {
		return this.getContent();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output) {
		switch(this.getTag()) {
			case INTEGER_TAG:
				output.writeInt((Integer) this.getNumber());
				break;
			case FLOAT_TAG:
				output.writeFloat((Float) this.getNumber());
				break;
			case LONG_TAG:
				output.writeLong((Long) this.getNumber());
				break;
			case DOUBLE_TAG:
				output.writeDouble((Double) this.getNumber());
				break;
			default:
				throw new RuntimeException("Unsuported number type");
		}
	}

	// ****************************************************************
	// ** COMPARATION
	// ****************************************************************

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((NumberEntry) obj).getNumber().equals(this.getNumber());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public Number getNumber() {
		return this.number;
	}

	protected void setNumber(Number number) {
		this.number = number;
	}
}