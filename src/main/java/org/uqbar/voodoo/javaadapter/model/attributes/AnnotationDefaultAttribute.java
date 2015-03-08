package org.uqbar.voodoo.javaadapter.model.attributes;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues.ElementValue;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class AnnotationDefaultAttribute extends Attribute {

	private ElementValue defaultValue;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public AnnotationDefaultAttribute() {
		super("AnnotationDefault");
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return this.getDefaultValue().getByteCount();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		AnnotationDefaultAttribute target = (AnnotationDefaultAttribute) object;

		return this.getDefaultValue().equals(target.getDefaultValue());
	};

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		this.getDefaultValue().writeTo(output);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public ElementValue getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(ElementValue defaultValue) {
		this.defaultValue = defaultValue;
	}
}
