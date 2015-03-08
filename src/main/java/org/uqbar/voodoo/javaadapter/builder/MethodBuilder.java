package org.uqbar.voodoo.javaadapter.builder;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromClass;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromName;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.methods.Method;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.modifiers.MethodModifier;

public class MethodBuilder {

	private String name;
	private TypeReference returnType;
	private List<TypeReference> argumentTypes;
	private MethodModifier modifier;

	private CodeBuilder code;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public MethodBuilder(String name, Class<?> returnType, Class<?>... argumentTypes) {
		this(name, false, returnType, argumentTypes);
	}

	public MethodBuilder(String name, boolean isStatic, Class<?> returnType, Class<?>... argumentTypes) {
		this(name, isStatic, returnType.getName());

		List<TypeReference> argTypes = new ArrayList<>();
		for(Class<?> argumentType : argumentTypes) {
			argTypes.add(typeFromClass(argumentType));
		}

		this.setArgumentTypes(argTypes);
	}

	public MethodBuilder(String name, String returnTypeName, String... argumentTypeNames) {
		this(name, false, returnTypeName, argumentTypeNames);
	}

	public MethodBuilder(String name, boolean isStatic, String returnTypeName, String... argumentTypeNames) {
		List<TypeReference> argumentTypes = new ArrayList<>();
		for(String argumentTypeName : argumentTypeNames) {
			argumentTypes.add(typeFromName(argumentTypeName));
		}

		this.setName(name);
		this.setReturnType(typeFromName(returnTypeName));
		this.setArgumentTypes(argumentTypes);
		this.setModifier(new MethodModifier());

		this.setCode(new CodeBuilder(this));

		this.getModifier().setMarkedAsStatic(isStatic);
	}

	// ****************************************************************
	// ** BUILD CONFIGURATION
	// ****************************************************************

	public CodeBuilder code() {
		return this.getCode();
	}

	// ****************************************************************
	// ** BUILDING
	// ****************************************************************

	public Method build() {
		Method answer = new Method();

		answer.setName(this.getName());
		answer.setReturnType(this.getReturnType());
		answer.setArgumentTypes(this.getArgumentTypes());
		answer.setModifier(this.getModifier());

		answer.addAttribute(this.getCode().build());

		return answer;
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public TypeReference getReturnType() {
		return this.returnType;
	}

	protected void setReturnType(TypeReference returnType) {
		this.returnType = returnType;
	}

	public List<TypeReference> getArgumentTypes() {
		return this.argumentTypes;
	}

	protected void setArgumentTypes(List<TypeReference> argumentTypes) {
		this.argumentTypes = argumentTypes;
	}

	protected MethodModifier getModifier() {
		return this.modifier;
	}

	protected void setModifier(MethodModifier modifier) {
		this.modifier = modifier;
	}

	protected CodeBuilder getCode() {
		return this.code;
	}

	protected void setCode(CodeBuilder code) {
		this.code = code;
	}
}