package org.uqbar.voodoo.javaadapter.model.attributes.code;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.LocalVariable;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class LocalVariableTypeTableAttribute extends CodeAttributeAttribute {

	private List<LocalVariable> types;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public LocalVariableTypeTableAttribute() {
		super("LocalVariableTypeTable");

		this.setTypes(new ArrayList<LocalVariable>());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2 + this.getTypes().size() * 10;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		LocalVariableTypeTableAttribute target = (LocalVariableTypeTableAttribute) object;

		return this.getTypes().size() == target.getTypes().size()
				&& this.getTypes().containsAll(target.getTypes());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getTypes().size());

		for(LocalVariable variable : this.getTypes()) {
			variable.writeTo(output);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addType(LocalVariable type) {
		this.getTypes().add(type);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public List<LocalVariable> getTypes() {
		return this.types;
	}

	public void setTypes(List<LocalVariable> types) {
		this.types = types;
	}

}
