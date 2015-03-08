package org.uqbar.voodoo.javaadapter.model.modifiers;

public class FieldModifier extends Modifier {

	private static final int ENUM = 0x4000;
	private static final int VOLATILE = 0x0040;
	private static final int TRANSIENT = 0x0080;

	private boolean markedAsEnum;
	private boolean markedAsVolatile;
	private boolean markedAsTransient;

	// ****************************************************************
	// ** CONSTRUCTOR
	// ****************************************************************

	public FieldModifier() {
		this.setVisibility(Visibility.PRIVATE);
	}

	public FieldModifier(int flag) {
		super(flag);

		this.setMarkedAsEnum((flag & ENUM) != 0);
		this.setMarkedAsVolatile((flag & VOLATILE) != 0);
		this.setMarkedAsTransient((flag & TRANSIENT) != 0);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	protected int getFlagByte() {
		return super.getFlagByte()
				| (this.isMarkedAsEnum() ? ENUM : 0)
				| (this.isMarkedAsVolatile() ? VOLATILE : 0)
				| (this.isMarkedAsTransient() ? TRANSIENT : 0);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public boolean isMarkedAsEnum() {
		return this.markedAsEnum;
	}

	protected void setMarkedAsEnum(boolean markedAsEnum) {
		this.markedAsEnum = markedAsEnum;
	}

	public boolean isMarkedAsVolatile() {
		return this.markedAsVolatile;
	}

	protected void setMarkedAsVolatile(boolean markedAsVolatile) {
		this.markedAsVolatile = markedAsVolatile;
	}

	public boolean isMarkedAsTransient() {
		return this.markedAsTransient;
	}

	protected void setMarkedAsTransient(boolean markedAsTransient) {
		this.markedAsTransient = markedAsTransient;
	}
}