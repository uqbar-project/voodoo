package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.BootstrapMethod;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class BootstrapMethodsAttribute extends Attribute {

	private List<BootstrapMethod> bootstrapMethods;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public BootstrapMethodsAttribute(BootstrapMethod... bootstrapMethods) {
		super("BootstrapMethods");

		this.setBootstrapMethods(new ArrayList<>(Arrays.asList(bootstrapMethods)));
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		super.fillConstantPool(constantPool);

		for(BootstrapMethod bootstrapMethod : this.getBootstrapMethods()) {
			constantPool.push(bootstrapMethod);
		}
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int answer = 2;

		for(BootstrapMethod method : this.getBootstrapMethods()) {
			answer += 2 + 2 + 2 * method.getArgumentIndexes().size();
		}

		return answer;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		BootstrapMethodsAttribute target = (BootstrapMethodsAttribute) object;

		return this.getBootstrapMethods().size() == target.getBootstrapMethods().size()
				&& this.getBootstrapMethods().containsAll(target.getBootstrapMethods());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getBootstrapMethods().size());

		for(BootstrapMethod method : this.getBootstrapMethods()) {
			method.writeTo(output, constantPool);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addBootstrapMethod(BootstrapMethod method) {
		this.getBootstrapMethods().add(method);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public List<BootstrapMethod> getBootstrapMethods() {
		return this.bootstrapMethods;
	}

	public void setBootstrapMethods(List<BootstrapMethod> bootstrapMethods) {
		this.bootstrapMethods = bootstrapMethods;
	}
}
