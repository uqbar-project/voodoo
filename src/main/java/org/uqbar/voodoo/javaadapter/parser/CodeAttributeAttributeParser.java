package org.uqbar.voodoo.javaadapter.parser;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.LineNumber;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.LocalVariable;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.AppendFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.ChopFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.FullFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.SameFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.SameFrameExtended;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.SameLocals1StackItemFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.SameLocals1StackItemFrameExtended;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.StackMapFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.ObjectVariableInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.SimpleVerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.UninitializedVariableInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.VerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.code.CodeAttributeAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.code.LineNumberTableAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.code.LocalVariableTableAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.code.LocalVariableTypeTableAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.code.StackMapTableAttribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.parser.exceptions.ParseException;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public enum CodeAttributeAttributeParser {

	STACKMAPTABLE {
		@Override
		protected CodeAttributeAttribute doParse(ExtendedDataInputStream input, ConstantPool constantPool, InstructionCallContext callContext) {
			StackMapTableAttribute answer = new StackMapTableAttribute();
			int frameCount = input.readUnsignedShort();

			for(int i = 0; i < frameCount; i++) {
				answer.addFrame(this.parseStackFrame(input, callContext, constantPool));
			}

			return answer;
		}

		protected StackMapFrame parseStackFrame(ExtendedDataInputStream input, InstructionCallContext callContext, ConstantPool constantPool) {
			int frameType = input.readUnsignedByte();
			if(frameType < 64) {
				return new SameFrame(frameType);
			}
			if(frameType < 128) {
				return new SameLocals1StackItemFrame(frameType - 64, this.parseVerificationType(input, callContext, constantPool));
			}
			if(frameType < 247) {
				throw new ParseException("Invalid StackMapFrame type! ( " + frameType + "is reserved)");
			}
			if(frameType == 247) {
				return new SameLocals1StackItemFrameExtended(input.readUnsignedShort(), this
					.parseVerificationType(input, callContext, constantPool));
			}
			if(frameType < 251) {
				return new ChopFrame(input.readUnsignedShort(), 251 - frameType);
			}
			if(frameType == 251) {
				return new SameFrameExtended(input.readUnsignedShort());
			}
			if(frameType < 255) {
				AppendFrame answer = new AppendFrame(input.readUnsignedShort());

				for(int i = 0; i < frameType - 251; i++) {
					answer.addLocal(this.parseVerificationType(input, callContext, constantPool));
				}

				return answer;
			}
			if(frameType == 255) {
				FullFrame answer = new FullFrame(input.readUnsignedShort());

				int localCount = input.readUnsignedShort();
				for(int i = 0; i < localCount; i++) {
					answer.addLocal(this.parseVerificationType(input, callContext, constantPool));
				}

				int stackCount = input.readUnsignedShort();
				for(int i = 0; i < stackCount; i++) {
					answer.pushStack(this.parseVerificationType(input, callContext, constantPool));
				}

				return answer;
			}

			throw new ParseException("Invalid Stack Frame type!");
		}

		protected VerificationTypeInfo parseVerificationType(ExtendedDataInputStream input, InstructionCallContext callContext, ConstantPool constantPool) {
			int type = input.readUnsignedByte();
			switch(type) {
				case 0:
					return SimpleVerificationTypeInfo.TOP;
				case 1:
					return SimpleVerificationTypeInfo.INTEGER;
				case 2:
					return SimpleVerificationTypeInfo.FLOAT;
				case 3:
					return SimpleVerificationTypeInfo.DOUBLE;
				case 4:
					return SimpleVerificationTypeInfo.LONG;
				case 5:
					return SimpleVerificationTypeInfo.NULL;
				case 6:
					return SimpleVerificationTypeInfo.UNINITIALIZED_THIS;
				case 7:
					return new ObjectVariableInfo(constantPool.get(input.readUnsignedShort()).getContent().toString());
				case 8:
					return new UninitializedVariableInfo(callContext.getCallAtCodeOffset(input.readUnsignedShort()));
				default:
					throw new ParseException("Invalid Verification Type Info!");
			}
		}
	},

	LINENUMBERTABLE {
		@Override
		protected CodeAttributeAttribute doParse(ExtendedDataInputStream input, ConstantPool constantPool, InstructionCallContext callContext) {
			LineNumberTableAttribute answer = new LineNumberTableAttribute();

			int lineNumberCount = input.readUnsignedShort();
			for(int i = 0; i < lineNumberCount; i++) {
				LineNumber lineNumber = new LineNumber();
				lineNumber.setStartPC(input.readUnsignedShort());
				lineNumber.setLineNumber(input.readUnsignedShort());

				answer.addLineNumber(lineNumber);
			}

			return answer;
		}
	},

	LOCALVARIABLETABLE {
		@Override
		protected CodeAttributeAttribute doParse(ExtendedDataInputStream input, ConstantPool constantPool, InstructionCallContext callContext) {
			LocalVariableTableAttribute answer = new LocalVariableTableAttribute();

			int variableCount = input.readUnsignedShort();
			for(int i = 0; i < variableCount; i++) {
				LocalVariable variable = new LocalVariable();

				variable.setStartPC(input.readUnsignedShort());
				variable.setLength(input.readUnsignedShort());
				variable.setNameIndex(input.readUnsignedShort());
				variable.setDescriptorIndex(input.readUnsignedShort());
				variable.setIndex(input.readUnsignedShort());

				answer.addVariable(variable);
			}

			return answer;
		}
	},

	LOCALVARIABLETYPETABLE {
		@Override
		protected CodeAttributeAttribute doParse(ExtendedDataInputStream input, ConstantPool constantPool, InstructionCallContext callContext) {
			LocalVariableTypeTableAttribute answer = new LocalVariableTypeTableAttribute();

			int typeCount = input.readUnsignedShort();
			for(int i = 0; i < typeCount; i++) {
				LocalVariable type = new LocalVariable();

				type.setStartPC(input.readUnsignedShort());
				type.setLength(input.readUnsignedShort());
				type.setNameIndex(input.readUnsignedShort());
				type.setDescriptorIndex(input.readUnsignedShort());
				type.setIndex(input.readUnsignedShort());

				answer.addType(type);
			}

			return answer;
		}
	};

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static CodeAttributeAttribute parse(ExtendedDataInputStream input, InstructionCallContext callContext, ConstantPool constantPool) {
		int name = input.readUnsignedShort();
		input.readInt(); // size
		CodeAttributeAttributeParser parser = valueOf(constantPool.get(name).getContent().toString().toUpperCase());

		return parser.doParse(input, constantPool, callContext);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	protected abstract CodeAttributeAttribute doParse(ExtendedDataInputStream input, ConstantPool constantPool, InstructionCallContext callContext);
}