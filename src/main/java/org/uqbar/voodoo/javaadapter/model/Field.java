package org.uqbar.voodoo.javaadapter.model;

import static org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry.FIELD_TAG;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.constantPool.UTF8Entry;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.modifiers.FieldModifier;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class Field extends Attributable {

	private FieldModifier modifier;
	private String name;
	private TypeReference type;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Field() {
		this.setModifier(new FieldModifier());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public int getByteCount() {
		return 2 + 2 + 2 + this.getAttributesByteCount();
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void fillConstantPool(ConstantPool constantPool) {
		constantPool.push(new SlotReference(FIELD_TAG, new TypeReference(this.getName()), this.getName(), this.getType()));
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		this.getModifier().writeTo(output);
		output.writeShort(constantPool.indexOf(this.getName(), UTF8Entry.class));
		output.writeShort(constantPool.indexOf(this.getType().getDescriptor(), UTF8Entry.class));
		this.writeAttributes(output, constantPool);
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object.getClass() != this.getClass()) {
			return false;
		}

		Field target = (Field) object;

		return super.equals(target)
				&& this.getName().equals(target.getName())
				&& this.getType().equals(target.getType())
				&& this.getModifier().equals(target.getModifier());
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public FieldModifier getModifier() {
		return this.modifier;
	}

	public void setModifier(FieldModifier modifier) {
		this.modifier = modifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeReference getType() {
		return this.type;
	}

	public void setType(TypeReference type) {
		this.type = type;
	}
}