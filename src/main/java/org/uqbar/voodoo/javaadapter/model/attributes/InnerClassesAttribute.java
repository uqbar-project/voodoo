package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.InnerClass;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class InnerClassesAttribute extends Attribute {

	private List<InnerClass> classes;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InnerClassesAttribute() {
		super("InnerClasses");

		this.setClasses(new ArrayList<InnerClass>());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return this.getClasses().size() * 8;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		InnerClassesAttribute target = (InnerClassesAttribute) object;

		return this.getClasses().size() == target.getClasses().size()
				&& this.getClasses().containsAll(target.getClasses());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getClasses().size());

		for(InnerClass innerClass : this.getClasses()) {
			innerClass.writeTo(output);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addClass(InnerClass aClass) {
		this.getClasses().add(aClass);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public List<InnerClass> getClasses() {
		return this.classes;
	}

	public void setClasses(List<InnerClass> classes) {
		this.classes = classes;
	}
}
