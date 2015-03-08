package org.uqbar.voodoo.javaadapter.parser;

import java.util.ArrayList;

import org.uqbar.voodoo.javaadapter.model.Attributable;
import org.uqbar.voodoo.javaadapter.model.attributes.AnnotationDefaultAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.BootstrapMethodsAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.ConstantValueAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.CustomAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.DeprecatedAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.EnclosingMethodAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.ExceptionsAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.InnerClassesAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.RuntimeAnnotationsAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.RuntimeParameterAnnotationsAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.SignatureAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.SourceDebugExtensionAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.SourceFileAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.SyntheticAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.Annotation;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.BootstrapMethod;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ExceptionHandler;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.InnerClass;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.AnnotationValue;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.ArrayValue;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.ClassInfoValue;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.ConstantValue;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.ElementValue;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.EnumValue;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.constantPool.MethodHandlerEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.model.modifiers.ClassModifier;
import org.uqbar.voodoo.javaadapter.parser.exceptions.ParseException;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public enum AttributeParser {

	CONSTANTVALUE {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			ConstantValueAttribute answer = new ConstantValueAttribute();
			owner.addAttribute(answer);

			answer.setValueIndex(input.readUnsignedShort());
		}
	},

	CODE {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			CodeAttribute answer = new CodeAttribute();
			owner.addAttribute(answer);

			answer.setMaxStack(input.readUnsignedShort());
			answer.setMaxLocals(input.readUnsignedShort());

			int byteCount = input.readInt();

			int firstCallCodeOffset = input.getReaded();
			while(byteCount > 0) {
				BytecodeInstruction instruction = BytecodeInstruction.fromCode(input.readUnsignedByte());
				InstructionCall call = instruction.parseCall(input, firstCallCodeOffset, constantPool);
				answer.addCall(call);

				byteCount -= call.getByteCount();
			}

			InstructionCallContext callContext = new InstructionCallContext(answer.getCalls(), firstCallCodeOffset);
			for(InstructionCall call : answer.getCalls()) {
				call.getInstruction().adaptParsedCalls(call, callContext, constantPool);
			}

			int exceptionCount = input.readUnsignedShort();
			for(int i = 0; i < exceptionCount; i++) {
				ExceptionHandler handler = new ExceptionHandler();
				handler.setStartPC(input.readUnsignedShort());
				handler.setEndPC(input.readUnsignedShort());
				handler.setHandlerPC(input.readUnsignedShort());
				handler.setCatchType(input.readUnsignedShort());

				answer.addException(handler);
			}

			int attributeCount = input.readUnsignedShort();
			for(int i = 0; i < attributeCount; i++) {
				answer.addAttribute(CodeAttributeAttributeParser.parse(input, callContext, constantPool));
			}
		}
	},

	EXCEPTIONS {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			ExceptionsAttribute answer = new ExceptionsAttribute();
			owner.addAttribute(answer);

			answer.setExceptionIndexes(new int[input.readUnsignedShort()]);
			for(int i = 0; i < answer.getExceptionIndexes().length; i++) {
				answer.getExceptionIndexes()[i] = input.readUnsignedShort();
			}
		}
	},

	INNERCLASSES {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			InnerClassesAttribute answer = new InnerClassesAttribute();
			owner.addAttribute(answer);

			int classesCount = input.readUnsignedShort();
			for(int i = 0; i < classesCount; i++) {
				InnerClass innerClass = new InnerClass();
				innerClass.setInnerClassInfoIndex(input.readUnsignedShort());
				innerClass.setOuterClassInfoIndex(input.readUnsignedShort());
				innerClass.setInnerNameIndex(input.readUnsignedShort());

				innerClass.setModifier(new ClassModifier(input.readUnsignedShort()));

				answer.addClass(innerClass);
			}
		}
	},

	ENCLOSINGMETHOD {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			EnclosingMethodAttribute answer = new EnclosingMethodAttribute();
			owner.addAttribute(answer);

			answer.setClassIndex(input.readUnsignedShort());
			answer.setMethodIndex(input.readUnsignedShort());
		}
	},

	SYNTHETIC {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			owner.addAttribute(new SyntheticAttribute());
		}
	},

	SIGNATURE {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			SignatureAttribute answer = new SignatureAttribute();
			owner.addAttribute(answer);

			answer.setSignatureIndex(input.readUnsignedShort());
		}
	},

	SOURCEFILE {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			SourceFileAttribute answer = new SourceFileAttribute();
			owner.addAttribute(answer);

			answer.setSourceFileName(constantPool.get(input.readUnsignedShort()).getContent().toString());
		}
	},

	SOURCEDEBUGEXTENSION {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			SourceDebugExtensionAttribute answer = new SourceDebugExtensionAttribute();
			owner.addAttribute(answer);

			answer.setDebugExtension(new byte[size]);
			input.read(answer.getDebugExtension());
		}
	},

	DEPRECATED {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			owner.addAttribute(new DeprecatedAttribute());
		}
	},

	RUNTIMEVISIBLEANNOTATIONS {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			RuntimeAnnotationsAttribute answer = new RuntimeAnnotationsAttribute(true);
			owner.addAttribute(answer);

			int annotationCount = input.readUnsignedShort();
			for(int i = 0; i < annotationCount; i++) {
				answer.addAnnotation(this.parseAnnotation(input));
			}
		}
	},

	RUNTIMEINVISIBLEANNOTATIONS {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			RuntimeAnnotationsAttribute answer = new RuntimeAnnotationsAttribute(false);
			owner.addAttribute(answer);

			int annotationCount = input.readUnsignedShort();
			for(int i = 0; i < annotationCount; i++) {
				answer.addAnnotation(this.parseAnnotation(input));
			}
		}
	},

	RUNTIMEVISIBLEPARAMETERANNOTATIONS {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			RuntimeParameterAnnotationsAttribute answer = new RuntimeParameterAnnotationsAttribute(true);
			owner.addAttribute(answer);

			answer.setParameterAnnotations(new Annotation[input.readUnsignedByte()][]);
			for(int i = 0; i < answer.getParameterAnnotations().length; i++) {
				answer.getParameterAnnotations()[i] = new Annotation[input.readUnsignedShort()];
				for(int j = 0; j < answer.getParameterAnnotations()[i].length; j++) {
					answer.getParameterAnnotations()[i][j] = this.parseAnnotation(input);
				}
			}
		}
	},

	RUNTIMEINVISIBLEPARAMETERANNOTATIONS {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			RuntimeParameterAnnotationsAttribute answer = new RuntimeParameterAnnotationsAttribute(false);
			owner.addAttribute(answer);

			answer.setParameterAnnotations(new Annotation[input.readUnsignedByte()][]);
			for(int i = 0; i < answer.getParameterAnnotations().length; i++) {
				answer.getParameterAnnotations()[i] = new Annotation[input.readUnsignedShort()];
				for(int j = 0; j < answer.getParameterAnnotations()[i].length; j++) {
					answer.getParameterAnnotations()[i][j] = this.parseAnnotation(input);
				}
			}
		}
	},

	ANNOTATIONDEFAULT {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			AnnotationDefaultAttribute answer = new AnnotationDefaultAttribute();
			owner.addAttribute(answer);

			answer.setDefaultValue(this.parseElementValue(input));
		}
	},

	BOOTSTRAPMETHODS {
		@Override
		protected void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner) {
			BootstrapMethodsAttribute answer = new BootstrapMethodsAttribute();
			owner.addAttribute(answer);

			int bootstrapMethodCount = input.readUnsignedShort();
			for(int i = 0; i < bootstrapMethodCount; i++) {
				BootstrapMethod method = new BootstrapMethod();

				MethodHandlerEntry entry = constantPool.<MethodHandlerEntry>get(input.readUnsignedShort());
				method.setReferenceKind(entry.getReferenceKind());

				// TODO: Estas dos cosas tendría que poder preguntarselas al entry, no pedirle el index y buscarlas yo. Atento,
				// porque podría no ser un SlotEntry el reference.
				SlotEntry referenceEntry = constantPool.<SlotEntry>get(entry.getReferenceIndex());
				method.setOwner(TypeReference.typeFromName(referenceEntry.getClassName()));
				method.setName(referenceEntry.getSelector());

				int argumentCount = input.readUnsignedShort();
				method.setArgumentIndexes(new ArrayList<Integer>());
				for(int j = 0; j < argumentCount; j++) {
					method.getArgumentIndexes().add(input.readUnsignedShort());
				}
				answer.addBootstrapMethod(method);
			}
		}
	},

	CUSTOM {
		@Override
		protected void parse(ExtendedDataInputStream input, int nameIndex, int size, ConstantPool constantPool, Attributable owner) {
			CustomAttribute answer = new CustomAttribute(constantPool.get(nameIndex).getContent().toString());
			owner.addAttribute(answer);

			answer.setNameIndex(nameIndex);
			answer.setInfo(new byte[size]);

			input.read(answer.getInfo());
		}
	};

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static void parse(ExtendedDataInputStream input, ConstantPool constantPool, Attributable owner) {
		int name = input.readUnsignedShort();
		int size = input.readInt();
		AttributeParser parser;

		try {
			parser = AttributeParser.valueOf(constantPool.get(name).getContent().toString().toUpperCase());
		}
		catch(IllegalArgumentException e) {
			parser = CUSTOM;
		}

		parser.parse(input, name, size, constantPool, owner);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	protected abstract void parse(ExtendedDataInputStream input, int name, int size, ConstantPool constantPool, Attributable owner);

	protected Annotation parseAnnotation(ExtendedDataInputStream input) {
		Annotation answer = new Annotation();
		answer.setTypeIndex(input.readUnsignedShort());

		int elementCount = input.readUnsignedShort();
		for(int i = 0; i < elementCount; i++) {
			int key = input.readUnsignedShort();
			ElementValue value = this.parseElementValue(input);
			answer.getElements().put(key, value);
		}

		return answer;
	}

	protected ElementValue parseElementValue(ExtendedDataInputStream input) {
		char tag = (char) input.readUnsignedByte();

		switch(tag) {
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'I':
			case 'J':
			case 'S':
			case 'Z':
			case 's':
				ConstantValue constant = new ConstantValue();
				constant.setValueIndex(input.readUnsignedShort());
				constant.setTag(tag);
				return constant;
			case 'e':
				EnumValue enu = new EnumValue();
				enu.setTypeIndex(input.readUnsignedShort());
				enu.setNameIndex(input.readUnsignedShort());
				enu.setTag(tag);
				return enu;
			case 'c':
				ClassInfoValue classInfo = new ClassInfoValue();
				classInfo.setClassInfoIndex(input.readUnsignedShort());
				classInfo.setTag(tag);
				return classInfo;
			case '@':
				AnnotationValue annotation = new AnnotationValue();
				annotation.setAnnotation(this.parseAnnotation(input));
				annotation.setTag(tag);
				return annotation;
			case '[':
				ArrayValue array = new ArrayValue();
				array.setValues(new ElementValue[input.readUnsignedShort()]);
				for(int i = 0; i < array.getValues().length; i++) {
					array.getValues()[i] = this.parseElementValue(input);
				}
				array.setTag(tag);
				return array;
			default:
				throw new ParseException("Invalid Element Value tag!");
		}
	}
}