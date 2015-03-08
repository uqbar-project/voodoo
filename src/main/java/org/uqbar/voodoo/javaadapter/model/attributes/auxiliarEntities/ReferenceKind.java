package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities;

import java.util.NoSuchElementException;

public enum ReferenceKind {
	GETFIELD(1), GETSTATIC(2), PUTFIELD(3), PUTSTATIC(4), INVOKEVIRTUAL(5), INVOKESTATIC(6), INVOKESPECIAL(7), NEWINVOKESPECIAL(8), INVOKEINTERFACE(9);

	private int key;

	// ****************************************************************
	// ** STATIC
	// ****************************************************************

	public static ReferenceKind fromKey(int key) {
		for(ReferenceKind referenceKind : values()) {
			if(referenceKind.getKey() == key) {
				return referenceKind;
			}
		}

		throw new NoSuchElementException();
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	private ReferenceKind(int key) {
		this.setKey(key);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getKey() {
		return this.key;
	}

	protected void setKey(int key) {
		this.key = key;
	}
}
