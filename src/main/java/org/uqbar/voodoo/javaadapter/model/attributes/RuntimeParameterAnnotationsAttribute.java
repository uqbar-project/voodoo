package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.Arrays;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.Annotation;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class RuntimeParameterAnnotationsAttribute extends Attribute {

	private boolean visible;
	private Annotation[][] parameterAnnotations;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public RuntimeParameterAnnotationsAttribute(boolean visible) {
		super(visible ? "RuntimeVisibleParameterAnnotations" : "RuntimeInvisibleParameterAnnotations");

		this.setVisible(visible);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int answer = 1;
		for(Annotation[] annotations : this.getParameterAnnotations()) {
			answer += 2;
			for(Annotation annotation : annotations) {
				answer += annotation.getByteCount();
			}
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

		RuntimeParameterAnnotationsAttribute target = (RuntimeParameterAnnotationsAttribute) object;

		return this.isVisible() == target.isVisible()
				&& Arrays.equals(this.getParameterAnnotations(), target.getParameterAnnotations());
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeByte(this.getParameterAnnotations().length);

		for(Annotation[] annotations : this.getParameterAnnotations()) {
			output.writeShort(annotations.length);

			for(Annotation annotation : annotations) {
				annotation.writeTo(output);
			}
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public boolean isVisible() {
		return this.visible;
	}

	protected void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Annotation[][] getParameterAnnotations() {
		return this.parameterAnnotations;
	}

	public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
		this.parameterAnnotations = parameterAnnotations;
	}
}
