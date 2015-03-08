package org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments;

import java.util.ArrayList;
import java.util.List;

//TODO: multiton?
public class TypeReference {

	private static final char VOID_DESCRIPTOR = 'V';
	private static final char BYTE_DESCRIPTOR = 'B';
	private static final char CHAR_DESCRIPTOR = 'C';
	private static final char DOUBLE_DESCRIPTOR = 'D';
	private static final char FLOAT_DESCRIPTOR = 'F';
	private static final char INT_DESCRIPTOR = 'I';
	private static final char LONG_DESCRIPTOR = 'J';
	private static final char SHORT_DESCRIPTOR = 'S';
	private static final char BOOLEAN_DESCRIPTOR = 'Z';
	private static final char OBJECT_BEGIN_DESCRIPTOR = 'L';
	private static final char OBJECT_END_DESCRIPTOR = ';';
	private static final char ARRAY_BEGIN_DESCRIPTOR = '[';

	private String name;

	// ****************************************************************
	// ** FACTORY METHODS
	// ****************************************************************

	public static TypeReference typeFromDescriptor(String typeDescriptor) {
		return new TypeReference(nameFromDescriptor(typeDescriptor));
	}

	public static TypeReference typeFromClass(Class<?> type) {
		return typeFromName(type.getName());
	}

	public static TypeReference typeFromName(String typeName) {
		return new TypeReference(typeName.replace('.', '/'));
	}

	public static List<TypeReference> typesFromSignatureDescriptor(String descriptor) {
		List<TypeReference> answer = new ArrayList<>();

		String returnDescriptor;
		if(descriptor.startsWith("(")) {
			String argumentDescriptor = descriptor.split("\\(")[1].split("\\)")[0];
			returnDescriptor = descriptor.split("\\(")[1].split("\\)")[1];

			while(!argumentDescriptor.isEmpty()) {
				TypeReference readed = readNextTypeFromMethodDescriptor(argumentDescriptor);
				argumentDescriptor = argumentDescriptor.substring(readed.getDescriptor().length());
				answer.add(readed);
			}
		}
		else {
			returnDescriptor = descriptor;
		}
		answer.add(0, readNextTypeFromMethodDescriptor(returnDescriptor));

		return answer;
	}

	protected static TypeReference readNextTypeFromMethodDescriptor(String descriptor) {
		switch(descriptor.charAt(0)) {
			case VOID_DESCRIPTOR:
				return typeFromClass(void.class);
			case BYTE_DESCRIPTOR:
				return typeFromClass(byte.class);
			case CHAR_DESCRIPTOR:
				return typeFromClass(char.class);
			case DOUBLE_DESCRIPTOR:
				return typeFromClass(double.class);
			case FLOAT_DESCRIPTOR:
				return typeFromClass(float.class);
			case INT_DESCRIPTOR:
				return typeFromClass(int.class);
			case LONG_DESCRIPTOR:
				return typeFromClass(long.class);
			case SHORT_DESCRIPTOR:
				return typeFromClass(short.class);
			case BOOLEAN_DESCRIPTOR:
				return typeFromClass(boolean.class);
			case OBJECT_BEGIN_DESCRIPTOR:
				return typeFromDescriptor(descriptor.substring(0, descriptor.indexOf(OBJECT_END_DESCRIPTOR) + 1));
			case ARRAY_BEGIN_DESCRIPTOR:
				return readNextTypeFromMethodDescriptor(descriptor.substring(1)).arrayed();
			default:
				throw new RuntimeException("Invalid descriptor sequence: " + descriptor);
		}
	}

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	protected static String nameFromDescriptor(String descriptor) {
		switch(descriptor.charAt(0)) {
			case VOID_DESCRIPTOR:
				return void.class.getName();
			case BYTE_DESCRIPTOR:
				return byte.class.getName();
			case CHAR_DESCRIPTOR:
				return char.class.getName();
			case DOUBLE_DESCRIPTOR:
				return double.class.getName();
			case FLOAT_DESCRIPTOR:
				return float.class.getName();
			case INT_DESCRIPTOR:
				return int.class.getName();
			case LONG_DESCRIPTOR:
				return long.class.getName();
			case SHORT_DESCRIPTOR:
				return short.class.getName();
			case BOOLEAN_DESCRIPTOR:
				return boolean.class.getName();
			case OBJECT_BEGIN_DESCRIPTOR:
				return descriptor.substring(1, descriptor.length() - 1);
			case ARRAY_BEGIN_DESCRIPTOR:
				return descriptor;
			default:
				throw new RuntimeException("Invalid descriptor: " + descriptor);
		}
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public TypeReference(String name) {
		this.setName(name);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public String getDescriptor() {
		switch(this.getName()) {
			case "void":
				return Character.toString(VOID_DESCRIPTOR);
			case "byte":
				return Character.toString(BYTE_DESCRIPTOR);
			case "char":
				return Character.toString(CHAR_DESCRIPTOR);
			case "double":
				return Character.toString(DOUBLE_DESCRIPTOR);
			case "float":
				return Character.toString(FLOAT_DESCRIPTOR);
			case "int":
				return Character.toString(INT_DESCRIPTOR);
			case "long":
				return Character.toString(LONG_DESCRIPTOR);
			case "short":
				return Character.toString(SHORT_DESCRIPTOR);
			case "boolean":
				return Character.toString(BOOLEAN_DESCRIPTOR);
			default:
				String adjustedTypeName = this.getName().replace('.', '/');

				return this.getName().startsWith(Character.toString(ARRAY_BEGIN_DESCRIPTOR))
					? adjustedTypeName
					: OBJECT_BEGIN_DESCRIPTOR + adjustedTypeName + OBJECT_END_DESCRIPTOR;
		}
	}

	public TypeReference arrayed() {
		return typeFromDescriptor(ARRAY_BEGIN_DESCRIPTOR + this.getDescriptor());
	}

	public TypeReference unarrayed() {
		return typeFromDescriptor(this.getDescriptor().substring(1));
	}

	@Override
	public String toString() {
		return this.getName();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) {
			return false;
		}

		TypeReference target = (TypeReference) object;

		return this.getName().equals(target.getName());
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	// TODO: Buscar todos los que lo llaman. Fijarse si no deberían usar directamente el TypeReference.
	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		this.name = name;
	}
}