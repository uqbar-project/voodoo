package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ExceptionHandler {
	private int startPC;
	private int endPC;
	private int handlerPC;
	private int catchType;

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getStartPC());
		output.writeShort(this.getEndPC());
		output.writeShort(this.getHandlerPC());
		output.writeShort(this.getCatchType());
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		ExceptionHandler target = (ExceptionHandler) object;

		return this.getStartPC() == target.getStartPC()
				&& this.getEndPC() == target.getEndPC()
				&& this.getHandlerPC() == target.getHandlerPC()
				&& this.getCatchType() == target.getCatchType();
	};

	@Override
	public int hashCode() {
		return this.getCatchType();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getStartPC() {
		return this.startPC;
	}

	public void setStartPC(int startPC) {
		this.startPC = startPC;
	}

	public int getEndPC() {
		return this.endPC;
	}

	public void setEndPC(int endPC) {
		this.endPC = endPC;
	}

	public int getHandlerPC() {
		return this.handlerPC;
	}

	public void setHandlerPC(int handlerPC) {
		this.handlerPC = handlerPC;
	}

	public int getCatchType() {
		return this.catchType;
	}

	public void setCatchType(int catchType) {
		this.catchType = catchType;
	}
}