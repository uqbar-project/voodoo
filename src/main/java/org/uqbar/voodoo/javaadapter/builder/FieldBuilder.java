package org.uqbar.voodoo.javaadapter.builder;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromName;

import org.uqbar.voodoo.javaadapter.model.Field;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.modifiers.FieldModifier;

public class FieldBuilder {
	private String fieldName;
	private TypeReference fieldType;
	private FieldModifier fieldModifier;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public FieldBuilder(String fieldName, Class<?> fieldType) {
		this(fieldName, false, fieldType);
	}

	public FieldBuilder(String fieldName, boolean markedAsStatic, Class<?> fieldType) {
		this(fieldName, markedAsStatic, fieldType.getName());
	}

	public FieldBuilder(String fieldName, boolean markedAsStatic, String fieldTypeName) {
		this.setFieldName(fieldName);
		this.setFieldType(typeFromName(fieldTypeName));
		this.setFieldModifier(new FieldModifier());

		this.getFieldModifier().setMarkedAsStatic(markedAsStatic);
	}

	// ****************************************************************
	// ** BUILDING
	// ****************************************************************

	public Field build() {
		Field answer = new Field();

		answer.setName(this.getFieldName());
		answer.setType(this.getFieldType());
		answer.setModifier(this.getFieldModifier());

		return answer;
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public String getFieldName() {
		return this.fieldName;
	}

	protected void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public TypeReference getFieldType() {
		return this.fieldType;
	}

	protected void setFieldType(TypeReference fieldType) {
		this.fieldType = fieldType;
	}

	public FieldModifier getFieldModifier() {
		return this.fieldModifier;
	}

	protected void setFieldModifier(FieldModifier fieldModifier) {
		this.fieldModifier = fieldModifier;
	}
}