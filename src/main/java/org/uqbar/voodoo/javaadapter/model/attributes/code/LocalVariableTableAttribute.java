package org.uqbar.voodoo.javaadapter.model.attributes.code;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.LocalVariable;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class LocalVariableTableAttribute extends CodeAttributeAttribute {

	private List<LocalVariable> variables;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public LocalVariableTableAttribute() {
		super("LocalVariableTable");

		this.setVariables(new ArrayList<LocalVariable>());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return 2 + this.getVariables().size() * 10;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		LocalVariableTableAttribute target = (LocalVariableTableAttribute) object;

		return this.getVariables().size() == target.getVariables().size()
				&& this.getVariables().containsAll(target.getVariables());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getVariables().size());

		for(LocalVariable variable : this.getVariables()) {
			variable.writeTo(output);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addVariable(LocalVariable variable) {
		this.getVariables().add(variable);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public List<LocalVariable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<LocalVariable> variables) {
		this.variables = variables;
	}
}
