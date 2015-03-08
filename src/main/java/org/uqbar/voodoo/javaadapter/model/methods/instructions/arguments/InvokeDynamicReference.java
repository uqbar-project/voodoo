package org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromClass;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromName;

import java.util.ArrayList;
import java.util.List;

//TODO: Cosas en común con SlotReference
public class InvokeDynamicReference {

	private String name;
	private List<TypeReference> types;
	private int bootstrapMethodIndex;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static InvokeDynamicReference invokeDynamic(int bootstrapMethodIndex, String name, Class<?> returnType, Class<?>... argumentTypes) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromClass(returnType));
		for(Class<?> argumentType : argumentTypes) {
			types.add(typeFromClass(argumentType));
		}

		return new InvokeDynamicReference(bootstrapMethodIndex, name, types);
	}

	public static InvokeDynamicReference invokeDynamic(int bootstrapMethodIndex, String name, String returnTypeName, String... argumentTypeNames) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromName(returnTypeName));
		for(String argumentTypeName : argumentTypeNames) {
			types.add(typeFromName(argumentTypeName));
		}

		return new InvokeDynamicReference(bootstrapMethodIndex, name, types);
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InvokeDynamicReference(int bootstrapMethodIndex, String name, List<TypeReference> types) {
		this.setBootstrapMethodIndex(bootstrapMethodIndex);
		this.setName(name);
		this.setTypes(types);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public int getReturnCount() {
		return this.getTypes().get(0).getDescriptor().equals('V') ? 0 : 1;
	}

	public int getArgumentCount() {
		return this.getTypes().size() - 1;
	}

	// TODO: Repetido con SlotReference
	public TypeReference getReturnType() {
		return this.getTypes().get(0);
	}

	// TODO: Repetido con SlotReference
	public int getArgumentPopCount() {
		return this.getArgumentCount() + 1;
	}

	public String getDescriptor() {
		String answer = "(";
		for(int i = 1; i < this.getTypes().size(); i++) {
			TypeReference type = this.getTypes().get(i);
			answer += type.getDescriptor();
		}
		answer += ")";

		answer += this.getTypes().get(0).getDescriptor();

		return answer;
	}

	@Override
	public String toString() {
		return this.getBootstrapMethodIndex() + ":" + this.getName() + ":" + this.getDescriptor();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object == null || !this.getClass().equals(object.getClass())) {
			return false;
		}

		InvokeDynamicReference target = (InvokeDynamicReference) object;

		return this.getBootstrapMethodIndex() == target.getBootstrapMethodIndex()
				&& this.getName().equals(target.getName())
				&& this.getTypes().equals(target.getTypes());
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

	protected void setName(String name) {
		this.name = name;
	}

	protected List<TypeReference> getTypes() {
		return this.types;
	}

	protected void setTypes(List<TypeReference> types) {
		this.types = types;
	}

	public int getBootstrapMethodIndex() {
		return this.bootstrapMethodIndex;
	}

	protected void setBootstrapMethodIndex(int bootstrapMethodIndex) {
		this.bootstrapMethodIndex = bootstrapMethodIndex;
	}
}
