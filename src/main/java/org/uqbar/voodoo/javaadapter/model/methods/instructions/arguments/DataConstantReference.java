package org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments;


public class DataConstantReference {

	private Object constant;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public DataConstantReference(Object constant) {
		this.setConstant(constant);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String toString() {
		return this.getConstant().toString();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass()) {
			return false;
		}

		DataConstantReference target = (DataConstantReference) object;

		return this.getConstant().equals(target.getConstant());
	}

	@Override
	public int hashCode() {
		return this.getConstant().hashCode();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public Object getConstant() {
		return this.constant;
	}

	protected void setConstant(Object constant) {
		this.constant = constant;
	}
}