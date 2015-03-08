package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.Annotation;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class RuntimeAnnotationsAttribute extends Attribute {

	private boolean visible;
	private List<Annotation> annotations;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public RuntimeAnnotationsAttribute(boolean visible) {
		super(visible ? "RuntimeVisibleAnnotations" : "RuntimeInvisibleAnnotations");

		this.setVisible(visible);
		this.setAnnotations(new ArrayList<Annotation>());
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int answer = 2;

		for(Annotation annotation : this.getAnnotations()) {
			answer += annotation.getByteCount();
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

		RuntimeAnnotationsAttribute target = (RuntimeAnnotationsAttribute) object;

		return this.getAnnotations().size() == target.getAnnotations().size()
				&& this.getAnnotations().containsAll(target.getAnnotations())
				&& this.isVisible() == target.isVisible();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getAnnotations().size());

		for(Annotation annotation : this.getAnnotations()) {
			annotation.writeTo(output);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addAnnotation(Annotation annotation) {
		this.getAnnotations().add(annotation);
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

	public List<Annotation> getAnnotations() {
		return this.annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}
}
