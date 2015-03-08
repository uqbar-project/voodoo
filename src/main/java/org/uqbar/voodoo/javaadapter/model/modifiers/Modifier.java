package org.uqbar.voodoo.javaadapter.model.modifiers;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public abstract class Modifier {

	private static final int SYNTHETIC = 0x1000;
	private static final int FINAL = 0x0010;
	private static final int STATIC = 0x0008;

	private Visibility visibility;
	private boolean markedAsSynthetic;
	private boolean markedAsFinal;
	private boolean markedAsStatic;

	// ****************************************************************
	// ** CONSTRUCTOR
	// ****************************************************************

	public Modifier(int flag) {
		this.setVisibility(Visibility.fromFlag(flag));
		this.setMarkedAsSynthetic((flag & SYNTHETIC) != 0);
		this.setMarkedAsFinal((flag & FINAL) != 0);
		this.setMarkedAsStatic((flag & STATIC) != 0);
	}

	public Modifier() {
		this.setVisibility(Visibility.PUBLIC);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	protected int getFlagByte() {
		return this.getVisibility().getFlag()
				| (this.isMarkedAsSynthetic() ? SYNTHETIC : 0)
				| (this.isMarkedAsFinal() ? FINAL : 0)
				| (this.isMarkedAsStatic() ? STATIC : 0);
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(object.getClass() != this.getClass()) {
			return false;
		}

		Modifier target = (Modifier) object;

		return this.getFlagByte() == target.getFlagByte();
	}

	@Override
	public int hashCode() {
		return this.getFlagByte();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getFlagByte());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public Visibility getVisibility() {
		return this.visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isMarkedAsSynthetic() {
		return this.markedAsSynthetic;
	}

	public void setMarkedAsSynthetic(boolean markedAsSynthetic) {
		this.markedAsSynthetic = markedAsSynthetic;
	}

	public boolean isMarkedAsFinal() {
		return this.markedAsFinal;
	}

	public void setMarkedAsFinal(boolean markedAsFinal) {
		this.markedAsFinal = markedAsFinal;
	}

	public boolean isMarkedAsStatic() {
		return this.markedAsStatic;
	}

	public void setMarkedAsStatic(boolean markedAsStatic) {
		this.markedAsStatic = markedAsStatic;
	}
}