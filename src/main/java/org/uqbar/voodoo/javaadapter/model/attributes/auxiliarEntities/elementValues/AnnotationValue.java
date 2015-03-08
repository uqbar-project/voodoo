package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.elementValues;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.Annotation;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class AnnotationValue extends ElementValue {

	private Annotation annotation;

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		return this.getAnnotation().getByteCount();
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	public void writeTo(ExtendedDataOutputStream output) {
		this.getAnnotation().writeTo(output);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public Annotation getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
}
