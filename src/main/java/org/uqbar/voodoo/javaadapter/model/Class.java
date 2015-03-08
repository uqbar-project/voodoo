package org.uqbar.voodoo.javaadapter.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.Attribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.Method;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.modifiers.ClassModifier;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class Class extends Attributable {

	public static final int MAGIC = 0xCAFEBABE;

	private ClassVersion version;
	private ClassModifier modifier;
	private TypeReference type;
	private TypeReference supertype;
	private List<TypeReference> interfaces;
	private List<Field> fields;
	private List<Method> methods;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public Class() {
		this.setModifier(new ClassModifier());

		this.setInterfaces(new ArrayList<TypeReference>());
		this.setFields(new ArrayList<Field>());
		this.setMethods(new ArrayList<Method>());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	// TODO: Extraer la iteración sobre la estructura en un visitor??
	// TODO: Guardar el nombre de los attributes_Custom
	protected ConstantPool getConstantPool() {
		ConstantPool answer = new ConstantPool();

		answer.push(this.getType());
		answer.push(this.getSupertype());

		for(TypeReference interfaceType : this.getInterfaces()) {
			answer.push(interfaceType);
		}

		for(Field field : this.getFields()) {
			field.fillConstantPool(answer);
		}

		for(Attribute attribute : this.getAttributes()) {
			attribute.fillConstantPool(answer);
		}

		for(Method method : this.getMethods()) {
			method.fillConstantPool(answer);
		}

		return answer;
	}

	public int getMethodAbsoluteOffset(Method method) {
		int fieldsByteCount = 0;
		for(Field field : this.getFields()) {
			fieldsByteCount += field.getByteCount();
		}

		int firstMethodAbsoluteOffset = 4 + 4 + this.getConstantPool().getByteCount() + 4 + 2 + 2 + 2
				+ this.getInterfaces().size() * 2 + 2 + fieldsByteCount + 2;

		int offsetDelta = 0;
		for(int i = 0; i < this.getMethods().indexOf(method); i++) {
			offsetDelta += this.getMethods().get(i).getByteCount();
		}

		return firstMethodAbsoluteOffset + offsetDelta;
	}

	public String getName() {
		return this.getType().getName();
	}

	@Override
	public String toString() {
		return "#" + this.getName();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(String targetDirectory) {
		targetDirectory = targetDirectory.endsWith(File.separator) ? targetDirectory : targetDirectory + File.separator;

		String filePath = targetDirectory + this.getName() + ".class";

		new File(filePath).getParentFile().mkdirs();

		try (ExtendedDataOutputStream output = new ExtendedDataOutputStream(filePath)) {
			this.writeTo(output);
		}
	}

	public void writeTo(ExtendedDataOutputStream output) {
		this.ensureIsReadyToWrite();

		ConstantPool constantPool = this.getConstantPool();

		this.writeMagic(output);
		this.writeVersion(output);
		constantPool.writeTo(output);
		this.writeAccessFlags(output);
		this.writeThisClass(output, constantPool);
		this.writeSuperClass(output, constantPool);
		this.writeInterfaces(output, constantPool);
		this.writeFields(output, constantPool);
		this.writeMethods(output, constantPool);
		this.writeAttributes(output, constantPool);
	}

	protected void ensureIsReadyToWrite() {
		for(Method method : this.getMethods()) {
			method.ensureIsReadyToWrite();
		}
	}

	protected void writeMagic(ExtendedDataOutputStream output) {
		output.writeInt(MAGIC);
	}

	protected void writeVersion(ExtendedDataOutputStream output) {
		output.writeShort(this.getVersion().getMinor());
		output.writeShort(this.getVersion().getMajor());
	}

	protected void writeAccessFlags(ExtendedDataOutputStream output) {
		this.getModifier().writeTo(output);
	}

	protected void writeThisClass(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(constantPool.indexOfContent(this.getType()));
	}

	protected void writeSuperClass(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(constantPool.indexOfContent(this.getSupertype()));
	}

	protected void writeInterfaces(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getInterfaces().size());

		for(TypeReference interfaceType : this.getInterfaces()) {
			output.writeShort(constantPool.indexOfContent(interfaceType));
		}
	}

	protected void writeFields(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getFields().size());

		for(Field field : this.getFields()) {
			field.writeTo(output, constantPool);
		}
	}

	protected void writeMethods(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getMethods().size());

		for(Method method : this.getMethods()) {
			method.writeTo(output, constantPool);
		}
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object.getClass() != this.getClass()) {
			return false;
		}

		Class target = (Class) object;

		return super.equals(target)
				&& this.getVersion().equals(target.getVersion())
				&& this.getModifier().equals(target.getModifier())
				&& this.getType().equals(target.getType())
				&& this.getSupertype().equals(target.getSupertype())
				&& this.getInterfaces().size() == target.getInterfaces().size()
				&& this.getInterfaces().containsAll(target.getInterfaces())
				&& this.getFields().size() == target.getFields().size()
				&& this.getFields().containsAll(target.getFields())
				&& this.getMethods().size() == target.getMethods().size()
				&& this.getMethods().containsAll(target.getMethods());
	}

	@Override
	public int hashCode() {
		return this.getType().hashCode();
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addInterface(TypeReference interfaceType) {
		this.getInterfaces().add(interfaceType);
	}

	public void addField(Field field) {
		this.getFields().add(field);
	}

	public void addMethod(Method method) {
		this.getMethods().add(method);

		method.setOwner(this);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public ClassVersion getVersion() {
		return this.version;
	}

	public void setVersion(ClassVersion version) {
		this.version = version;
	}

	public ClassModifier getModifier() {
		return this.modifier;
	}

	public void setModifier(ClassModifier modifier) {
		this.modifier = modifier;
	}

	public TypeReference getType() {
		return this.type;
	}

	public void setType(TypeReference type) {
		this.type = type;
	}

	public TypeReference getSupertype() {
		return this.supertype;
	}

	public void setSupertype(TypeReference supertype) {
		this.supertype = supertype;
	}

	public List<TypeReference> getInterfaces() {
		return this.interfaces;
	}

	protected void setInterfaces(List<TypeReference> interfaces) {
		this.interfaces = interfaces;
	}

	public List<Field> getFields() {
		return this.fields;
	}

	protected void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<Method> getMethods() {
		return this.methods;
	}

	protected void setMethods(List<Method> methods) {
		this.methods = methods;
	}
}