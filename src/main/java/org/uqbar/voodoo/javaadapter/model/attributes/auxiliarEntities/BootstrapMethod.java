package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.constantPool.MethodHandlerEntry;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

//TODO: Cosas repetidas con SlotReference
public class BootstrapMethod {

	private TypeReference owner;
	private String name;
	private List<Integer> argumentIndexes;
	private ReferenceKind referenceKind;

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output
			.writeShort(constantPool.indexOf("(" + this.referenceKind + ")" + this.getOwner().getName() + "." + this.getName()
					+ ":" + this.getMethodDescriptor(), MethodHandlerEntry.class));
		output.writeShort(this.getArgumentIndexes().size());

		for(int index : this.getArgumentIndexes()) {
			output.writeShort(index);
		}
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public String getMethodDescriptor() {
		return SlotReference
			.method(Object.class, "", CallSite.class, Lookup.class, String.class, MethodType.class)
			.getDescriptor();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {

		if(object.getClass() != this.getClass()) {
			return false;
		}

		BootstrapMethod target = (BootstrapMethod) object;

		return this.getOwner().equals(target.getOwner())
				&& this.getName().equals(target.getName())
				&& this.getArgumentIndexes().size() == target.getArgumentIndexes().size()
				&& this.getArgumentIndexes().containsAll(target.getArgumentIndexes())
				&& this.getReferenceKind().equals(target.getReferenceKind());
	}

	@Override
	public int hashCode() {
		return this.getOwner().hashCode();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public TypeReference getOwner() {
		return this.owner;
	}

	public void setOwner(TypeReference owner) {
		this.owner = owner;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getArgumentIndexes() {
		return this.argumentIndexes;
	}

	public void setArgumentIndexes(List<Integer> argumentIndexes) {
		this.argumentIndexes = argumentIndexes;
	}

	public ReferenceKind getReferenceKind() {
		return this.referenceKind;
	}

	public void setReferenceKind(ReferenceKind i) {
		this.referenceKind = i;
	}
}