package org.uqbar.voodoo.javaadapter.parser;

import java.util.HashMap;
import java.util.Map;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ReferenceKind;
import org.uqbar.voodoo.javaadapter.model.constantPool.ClassEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConcreteEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.constantPool.InvokeDynamicEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.MethodHandlerEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.MethodTypeEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.NumberEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.SignatureEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.StringEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.UTF8Entry;
import org.uqbar.voodoo.javaadapter.parser.exceptions.ParseException;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public enum ConstantPoolEntryParser {
	CLASS(ClassEntry.TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int nameIndex = input.readUnsignedShort();

			return new ClassEntry(constantPool, nameIndex);
		}
	},
	FIELD(SlotEntry.FIELD_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int classIndex = input.readUnsignedShort();
			int signatureIndex = input.readUnsignedShort();

			return new SlotEntry(constantPool, this.getTag(), classIndex, signatureIndex);
		}
	},
	METHOD(SlotEntry.METHOD_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int classIndex = input.readUnsignedShort();
			int signatureIndex = input.readUnsignedShort();

			return new SlotEntry(constantPool, this.getTag(), classIndex, signatureIndex);
		}
	},
	INTERFACE_METHOD(SlotEntry.INTERFACE_METHOD_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int classIndex = input.readUnsignedShort();
			int signatureIndex = input.readUnsignedShort();

			return new SlotEntry(constantPool, this.getTag(), classIndex, signatureIndex);
		}
	},
	STRING(StringEntry.STRING_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int stringIndex = input.readUnsignedShort();

			return new StringEntry(constantPool, stringIndex);
		}
	},
	INTEGER(NumberEntry.INTEGER_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int number = input.readInt();

			return new NumberEntry(constantPool, this.getTag(), number);
		}
	},
	FLOAT(NumberEntry.FLOAT_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			float number = input.readFloat();

			return new NumberEntry(constantPool, this.getTag(), number);
		}
	},
	LONG(NumberEntry.LONG_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			long number = input.readLong();

			return new NumberEntry(constantPool, this.getTag(), number);
		}
	},
	DOUBLE(NumberEntry.DOUBLE_TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			double number = input.readDouble();

			return new NumberEntry(constantPool, this.getTag(), number);
		}
	},
	SIGNATURE(SignatureEntry.TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int selectorIndex = input.readUnsignedShort();
			int descriptorIndex = input.readUnsignedShort();

			return new SignatureEntry(constantPool, selectorIndex, descriptorIndex);
		}
	},
	UTF8(UTF8Entry.TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			return new UTF8Entry(constantPool, input.readUTF());
		}
	},
	METHOD_HANDLE(MethodHandlerEntry.TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			ReferenceKind referenceKind = ReferenceKind.fromKey(input.readUnsignedByte());
			int referenceIndex = input.readUnsignedShort();

			return new MethodHandlerEntry(constantPool, referenceKind, referenceIndex);
		}
	},
	METHOD_TYPE(MethodTypeEntry.TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int descriptorIndex = input.readUnsignedShort();

			return new MethodTypeEntry(constantPool, descriptorIndex);
		}
	},
	INVOKE_DYNAMIC(InvokeDynamicEntry.TAG) {
		@Override
		protected ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool) {
			int bootstrapMethodAttributeIndex = input.readUnsignedShort();
			int signatureIndex = input.readUnsignedShort();

			return new InvokeDynamicEntry(constantPool, bootstrapMethodAttributeIndex, signatureIndex);
		}
	};

	private static final Map<Integer, ConstantPoolEntryParser> EQUIVALENCES = new HashMap<Integer, ConstantPoolEntryParser>() {
		{
			for(ConstantPoolEntryParser type : ConstantPoolEntryParser.values()) {
				this.put(type.getTag(), type);
			}
		}
	};

	private int tag;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static ConstantPoolEntryParser fromTag(int tag) {
		ConstantPoolEntryParser answer = EQUIVALENCES.get(tag);

		if(answer == null) {
			throw new ParseException("Invalid Constant Pool Entry tag: " + tag);
		}

		return answer;
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	ConstantPoolEntryParser(int tag) {
		this.setTag(tag);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void parse(ExtendedDataInputStream input, ConstantPool constantPool) {
		constantPool.pushEntry(this.doParse(input, constantPool));
	}

	protected abstract ConcreteEntry doParse(ExtendedDataInputStream input, ConstantPool constantPool);

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getTag() {
		return this.tag;
	}

	protected void setTag(int tag) {
		this.tag = tag;
	}
}