package org.uqbar.voodoo.javaadapter.model.modifiers;

public enum MetaType {
	CLASS(0x0000),
	ENUM(0x4000),
	INTERFACE(0x0200),
	ANNOTATION(0x2000) {
		@Override
		public int getFlagByte() {
			return super.getFlagByte() | INTERFACE.getFlagByte();
		}
	};

	private int flag;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static MetaType fromFlag(int flag) {
		for(MetaType type : MetaType.values()) {
			if((type.getFlagByte() & flag) == type.getFlagByte()) {
				return type;
			}
		}

		return CLASS;
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	MetaType(int flag) {
		this.flag = flag;
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public int getFlagByte() {
		return this.getFlag();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getFlag() {
		return this.flag;
	}

	protected void setFlag(int flag) {
		this.flag = flag;
	}
}