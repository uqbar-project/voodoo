package org.uqbar.voodoo.javaadapter.model.methods;

import java.util.List;

import org.uqbar.voodoo.javaadapter.model.Attributable;
import org.uqbar.voodoo.javaadapter.model.Class;
import org.uqbar.voodoo.javaadapter.model.attributes.Attribute;
import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.constantPool.UTF8Entry;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.modifiers.MethodModifier;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

//TODO: getCode() que devuelva el attribute code
public class Method extends Attributable {

	private Class owner;

	private String name;
	private TypeReference returnType;
	private List<TypeReference> argumentTypes;
	private MethodModifier modifier;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Method() {
		this.setModifier(new MethodModifier());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public String getDescriptor() {
		String argumentsDescriptor = "";
		for(TypeReference argumentType : this.getArgumentTypes()) {
			argumentsDescriptor += argumentType.getDescriptor();
		}

		return "(" + argumentsDescriptor + ")" + this.getReturnType().getDescriptor();
	}

	public int getByteCount() {
		return 2 + 2 + 2 + 2 + this.getAttributesByteCount();
	}

	public int getAttributeAbsoluteOffset(Attribute attribute) {
		int firstAttributeAbsoluteOffset = this.getOwner().getMethodAbsoluteOffset(this) + 2 + 2 + 2 + 2;
		int offsetDelta = 0;

		for(int i = 0; i < this.getAttributes().indexOf(attribute); i++) {
			offsetDelta += this.getAttributes().get(i).getByteCount();
		}

		return firstAttributeAbsoluteOffset + offsetDelta;
	}

	@Override
	public String toString() {
		return this.getOwner() + ">>" + this.getName();
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void fillConstantPool(ConstantPool answer) {
		answer.push(this.getName());
		answer.push(this.getDescriptor());

		for(Attribute attribute : this.getAttributes()) {
			attribute.fillConstantPool(answer);
		}
	}

	public void ensureIsReadyToWrite() {
		((CodeAttribute) this.getAttributes().get(0)).ensureIsReadyToWrite();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		this.getModifier().writeTo(output);

		output.writeShort(constantPool.indexOf(this.getName(), UTF8Entry.class));
		output.writeShort(constantPool.indexOf(this.getDescriptor(), UTF8Entry.class));

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

		Method target = (Method) object;

		return super.equals(target)
				&& target.getName().equals(this.getName())
				&& target.getReturnType().equals(this.getReturnType())
				&& target.getArgumentTypes().size() == this.getArgumentTypes().size()
				&& target.getArgumentTypes().containsAll(this.getArgumentTypes());
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeReference getReturnType() {
		return this.returnType;
	}

	public void setReturnType(TypeReference returnType) {
		this.returnType = returnType;
	}

	public List<TypeReference> getArgumentTypes() {
		return this.argumentTypes;
	}

	public void setArgumentTypes(List<TypeReference> argumentTypes) {
		this.argumentTypes = argumentTypes;
	}

	public MethodModifier getModifier() {
		return this.modifier;
	}

	public void setModifier(MethodModifier modifier) {
		this.modifier = modifier;
	}

	public Class getOwner() {
		return this.owner;
	}

	public void setOwner(Class owner) {
		this.owner = owner;
	}
}