package org.uqbar.voodoo.javaadapter.parser;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromDescriptor;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typesFromSignatureDescriptor;

import java.io.File;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.Attributable;
import org.uqbar.voodoo.javaadapter.model.Class;
import org.uqbar.voodoo.javaadapter.model.ClassVersion;
import org.uqbar.voodoo.javaadapter.model.Field;
import org.uqbar.voodoo.javaadapter.model.constantPool.ClassEntry;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.Method;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.modifiers.ClassModifier;
import org.uqbar.voodoo.javaadapter.model.modifiers.FieldModifier;
import org.uqbar.voodoo.javaadapter.model.modifiers.MethodModifier;
import org.uqbar.voodoo.javaadapter.parser.exceptions.ParseException;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public class ClassParser {

	private String classfilePath;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ClassParser(String classfilePath) {

		classfilePath = classfilePath.startsWith(File.separator) ? classfilePath : File.separator + classfilePath;

		if(!new File(classfilePath).exists()) {
			throw new RuntimeException("Target class file: " + classfilePath + " does not exist.");
		}

		this.classfilePath = classfilePath;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public Class parse() {
		try (ExtendedDataInputStream input = new ExtendedDataInputStream(this.classfilePath)) {
			return this.doParse(input);
		}
	}

	protected Class doParse(ExtendedDataInputStream input) {
		Class answer = new Class();

		this.parseMagic(input, answer);
		this.parseVersion(input, answer);

		ConstantPool constantPool = this.parseConstantPool(input);

		this.parseAccessFlags(input, answer);
		this.parseThisClass(input, constantPool, answer);
		this.parseSuperClass(input, constantPool, answer);
		this.parseInterfaces(input, constantPool, answer);
		this.parseFields(input, constantPool, answer);
		this.parseMethods(input, constantPool, answer);
		this.parseAttributes(input, constantPool, answer);

		// TODO: Validar la "consistencia" de la clase parseada? Esto también sería útil en el builder.
		return answer;
	}

	protected void parseMagic(ExtendedDataInputStream input, Class answer) {
		if(input.readInt() != Class.MAGIC) {
			throw new ParseException("Magic Númber is not right!");
		}
	}

	protected void parseVersion(ExtendedDataInputStream input, Class answer) {
		answer.setVersion(new ClassVersion(input.readUnsignedShort(), input.readUnsignedShort()));
	}

	protected ConstantPool parseConstantPool(ExtendedDataInputStream input) {
		ConstantPool answer = new ConstantPool();

		int constantCount = input.readUnsignedShort();

		if(constantCount < 1) {
			throw new ParseException("Invalid Constant Pool Count!");
		}

		for(int i = 1; i < constantCount; i++) {
			ConstantPoolEntryParser parser = ConstantPoolEntryParser.fromTag(input.readUnsignedByte());
			parser.parse(input, answer);

			if(parser.equals(ConstantPoolEntryParser.LONG) || parser.equals(ConstantPoolEntryParser.DOUBLE)) {
				i++;
			}
		}

		return answer;
	}

	protected void parseAccessFlags(ExtendedDataInputStream input, Class answer) {
		answer.setModifier(new ClassModifier(input.readUnsignedShort()));
	}

	protected void parseThisClass(ExtendedDataInputStream input, ConstantPool constantPool, Class answer) {
		answer.setType(constantPool.<ClassEntry>get(input.readUnsignedShort()).getRealContent());
	}

	protected void parseSuperClass(ExtendedDataInputStream input, ConstantPool constantPool, Class answer) {
		answer.setSupertype(constantPool.<ClassEntry>get(input.readUnsignedShort()).getRealContent());
	}

	protected void parseInterfaces(ExtendedDataInputStream input, ConstantPool constantPool, Class answer) {
		int interfaceCount = input.readUnsignedShort();
		while(interfaceCount-- > 0) {
			answer.addInterface(constantPool.<ClassEntry>get(input.readUnsignedShort()).getRealContent());
		}
	}

	protected void parseFields(ExtendedDataInputStream input, ConstantPool constantPool, Class answer) {
		int fieldCount = input.readUnsignedShort();

		for(int i = 0; i < fieldCount; i++) {
			this.parseField(input, constantPool, answer);
		}
	}

	protected void parseField(ExtendedDataInputStream input, ConstantPool constantPool, Class answer) {
		Field field = new Field();

		field.setModifier(new FieldModifier(input.readUnsignedShort()));
		field.setName(constantPool.get(input.readUnsignedShort()).getContent().toString());
		field.setType(typeFromDescriptor(constantPool.get(input.readUnsignedShort()).getContent().toString()));

		this.parseAttributes(input, constantPool, field);

		answer.addField(field);
	}

	protected void parseMethods(ExtendedDataInputStream input, ConstantPool constantPool, Class answer) {
		int methodCount = input.readUnsignedShort();

		for(int i = 0; i < methodCount; i++) {
			this.parseMethod(input, constantPool, answer);
		}
	}

	protected void parseMethod(ExtendedDataInputStream input, ConstantPool constantPool, Class answer) {
		Method method = new Method();
		answer.addMethod(method);

		method.setModifier(new MethodModifier(input.readUnsignedShort()));
		method.setName(constantPool.get(input.readUnsignedShort()).getContent().toString());

		List<TypeReference> types = typesFromSignatureDescriptor(constantPool
			.get(input.readUnsignedShort())
			.getContent()
			.toString());

		method.setReturnType(types.get(0));
		method.setArgumentTypes(types.subList(1, types.size()));

		this.parseAttributes(input, constantPool, method);
	}

	protected void parseAttributes(ExtendedDataInputStream input, ConstantPool constantPool, Attributable answer) {
		int attributeCount = input.readUnsignedShort();
		for(int i = 0; i < attributeCount; i++) {
			AttributeParser.parse(input, constantPool, answer);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected String getClassfilePath() {
		return this.classfilePath;
	}

	protected void setClassfilePath(String classfilePath) {
		this.classfilePath = classfilePath;
	}
}