package org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments;

import static org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry.FIELD_TAG;
import static org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry.INTERFACE_METHOD_TAG;
import static org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry.METHOD_TAG;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromClass;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: Buscar las referencias a ver si quiero instanciar esto ahí o directamente guardar uno de estos y ya.
public class SlotReference {

	private int tag;
	private TypeReference owner;
	private String name;
	private List<TypeReference> types;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static SlotReference interfaceMethod(Class<?> owner, String name, Class<?> returnType, Class<?>... argumentTypes) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromClass(returnType));
		for(Class<?> argumentType : argumentTypes) {
			types.add(typeFromClass(argumentType));
		}

		return new SlotReference(INTERFACE_METHOD_TAG, typeFromClass(owner), name, types);
	}

	public static SlotReference interfaceMethod(String ownerName, String name, String returnTypeName, String... argumentTypeNames) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromName(returnTypeName));
		for(String argumentTypeName : argumentTypeNames) {
			types.add(typeFromName(argumentTypeName));
		}

		return new SlotReference(INTERFACE_METHOD_TAG, typeFromName(ownerName), name, types);
	}

	public static SlotReference method(Class<?> owner, String name, Class<?> returnType, Class<?>... argumentTypes) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromClass(returnType));
		for(Class<?> argumentType : argumentTypes) {
			types.add(typeFromClass(argumentType));
		}

		return new SlotReference(METHOD_TAG, typeFromClass(owner), name, types);
	}

	public static SlotReference method(String ownerName, String name, String returnTypeName, String... argumentTypeNames) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromName(returnTypeName));
		for(String argumentTypeName : argumentTypeNames) {
			types.add(typeFromName(argumentTypeName));
		}

		return new SlotReference(METHOD_TAG, typeFromName(ownerName), name, types);
	}

	public static SlotReference field(Class<?> owner, String name, Class<?> type) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromClass(type));

		return new SlotReference(FIELD_TAG, typeFromClass(owner), name, types);
	}

	public static SlotReference field(Class<?> owner, String name, String typeName) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromName(typeName));

		return new SlotReference(FIELD_TAG, typeFromClass(owner), name, types);
	}

	public static SlotReference field(String owner, String name, Class<?> type) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromClass(type));

		return new SlotReference(FIELD_TAG, typeFromName(owner), name, types);
	}

	public static SlotReference field(String ownerName, String name, String typeName) {
		List<TypeReference> types = new ArrayList<>();
		types.add(typeFromName(typeName));

		return new SlotReference(FIELD_TAG, typeFromName(ownerName), name, types);
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SlotReference(int tag, TypeReference owner, String name, TypeReference... types) {
		this(tag, owner, name, Arrays.asList(types));
	}

	public SlotReference(int tag, TypeReference owner, String name, List<TypeReference> types) {
		this.setOwner(owner);
		this.setName(name);
		this.setTypes(types);
		this.setTag(tag);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	// TODO: Esto solo aplica para metodos. Tal vez habría que separar al field y al method del slot...
	public int getReturnCount() {
		return this.getReturnType().equals(TypeReference.typeFromClass(void.class)) ? 0 : 1;
	}

	// TODO: Esto solo aplica para metodos. Tal vez habría que separar al field y al method del slot...
	public int getArgumentCount() {
		return this.getTypes().size() - 1;
	}

	// TODO: Esto solo aplica para metodos. Tal vez habría que separar al field y al method del slot...
	public int getArgumentPopCount() {
		return this.getArgumentCount() + 1;
	}

	public String getDescriptor() {
		String answer = "";

		if(this.tag != FIELD_TAG) {
			answer += "(";
			for(int i = 1; i < this.getTypes().size(); i++) {
				TypeReference type = this.getTypes().get(i);
				answer += type.getDescriptor();
			}
			answer += ")";
		}

		answer += this.getReturnType().getDescriptor();

		return answer;
	}

	public TypeReference getReturnType() {
		return this.getTypes().get(0);
	}

	@Override
	public String toString() {
		return this.getOwner() + "." + this.getName() + ":" + this.getDescriptor();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object == null || !this.getClass().equals(object.getClass())) {
			return false;
		}

		SlotReference target = (SlotReference) object;

		return this.getTag() == target.getTag()
				&& this.getOwner().equals(target.getOwner())
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

	public int getTag() {
		return this.tag;
	}

	protected void setTag(int tag) {
		this.tag = tag;
	}

	public TypeReference getOwner() {
		return this.owner;
	}

	protected void setOwner(TypeReference owner) {
		this.owner = owner;
	}

	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public List<TypeReference> getTypes() {
		return this.types;
	}

	protected void setTypes(List<TypeReference> types) {
		this.types = types;
	}
}