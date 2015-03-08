package org.uqbar.voodoo.javaadapter.model.modifiers;

public class MethodModifier extends Modifier {
	private static final int ABSTRACT = 0x0400;
	private static final int NATIVE = 0x0100;
	private static final int STRICT = 0x0800;
	private static final int SYNCHRONIZED = 0x0020;
	private static final int BRIDGE = 0x0040;
	private static final int VARARGS = 0x0080;

	private boolean markedAsAbstract;
	private boolean markedAsNative;
	private boolean markedAsStrict;
	private boolean markedAsSynchronized;
	private boolean markedAsBridge;
	private boolean markedAsVarargs;

	// ****************************************************************
	// ** CONSTRUCTOR
	// ****************************************************************

	public MethodModifier() {
	}

	public MethodModifier(int flag) {
		super(flag);

		this.setMarkedAsAbstract((flag & ABSTRACT) != 0);
		this.setMarkedAsNative((flag & NATIVE) != 0);
		this.setMarkedAsStrict((flag & STRICT) != 0);
		this.setMarkedAsSynchronized((flag & SYNCHRONIZED) != 0);
		this.setMarkedAsBridge((flag & BRIDGE) != 0);
		this.setMarkedAsVarargs((flag & VARARGS) != 0);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	protected int getFlagByte() {
		return super.getFlagByte()
				| (this.isMarkedAsAbstract() ? ABSTRACT : 0)
				| (this.isMarkedAsNative() ? NATIVE : 0)
				| (this.isMarkedAsStrict() ? STRICT : 0)
				| (this.isMarkedAsSynchronized() ? SYNCHRONIZED : 0)
				| (this.isMarkedAsBridge() ? BRIDGE : 0)
				| (this.isMarkedAsVarargs() ? VARARGS : 0);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public boolean isMarkedAsAbstract() {
		return this.markedAsAbstract;
	}

	public void setMarkedAsAbstract(boolean markedAsAbstract) {
		this.markedAsAbstract = markedAsAbstract;
	}

	public boolean isMarkedAsNative() {
		return this.markedAsNative;
	}

	public void setMarkedAsNative(boolean markedAsNative) {
		this.markedAsNative = markedAsNative;
	}

	public boolean isMarkedAsStrict() {
		return this.markedAsStrict;
	}

	public void setMarkedAsStrict(boolean markedAsStrict) {
		this.markedAsStrict = markedAsStrict;
	}

	public boolean isMarkedAsSynchronized() {
		return this.markedAsSynchronized;
	}

	public void setMarkedAsSynchronized(boolean markedAsSynchronized) {
		this.markedAsSynchronized = markedAsSynchronized;
	}

	public boolean isMarkedAsBridge() {
		return this.markedAsBridge;
	}

	public void setMarkedAsBridge(boolean markedAsBridge) {
		this.markedAsBridge = markedAsBridge;
	}

	public boolean isMarkedAsVarargs() {
		return this.markedAsVarargs;
	}

	public void setMarkedAsVarargs(boolean markedAsVarargs) {
		this.markedAsVarargs = markedAsVarargs;
	}
}