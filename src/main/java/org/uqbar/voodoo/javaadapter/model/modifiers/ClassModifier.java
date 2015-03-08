package org.uqbar.voodoo.javaadapter.model.modifiers;

public class ClassModifier extends Modifier {

	private static final int SUPER = 0x0020;
	private static final int ABSTRACT = 0x0400;

	private MetaType metaType;
	private boolean markedAsSuper;
	private boolean markedAsAbstract;

	// ****************************************************************
	// ** CONSTRUCTOR
	// ****************************************************************

	// TODO: Setear el modifier con Flags de potencias de 2 (en un enum, para chequear tipos?).
	public ClassModifier() {
		this.setMetaType(MetaType.CLASS);
	}

	public ClassModifier(int flag) {
		super(flag);

		this.setMetaType(MetaType.fromFlag(flag));
		this.setMarkedAsAbstract((flag & ABSTRACT) != 0);
		this.setMarkedAsSuper((flag & SUPER) != 0);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	protected int getFlagByte() {
		return super.getFlagByte()
				| this.getMetaType().getFlagByte()
				| (this.isMarkedAsSuper() ? SUPER : 0)
				| (this.isMarkedAsAbstract() ? ABSTRACT : 0);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public MetaType getMetaType() {
		return this.metaType;
	}

	public void setMetaType(MetaType metaType) {
		this.metaType = metaType;
	}

	public boolean isMarkedAsSuper() {
		return this.markedAsSuper;
	}

	public void setMarkedAsSuper(boolean markedAsSuper) {
		this.markedAsSuper = markedAsSuper;
	}

	public boolean isMarkedAsAbstract() {
		return this.markedAsAbstract;
	}

	public void setMarkedAsAbstract(boolean markedAsAbstract) {
		this.markedAsAbstract = markedAsAbstract;
	}
}