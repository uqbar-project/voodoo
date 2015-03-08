package org.uqbar.voodoo.javaadapter.model;

public class ClassVersion {
	private int minor;
	private int major;

	public static final ClassVersion JAVA_7 = new ClassVersion(0, 51);
	public static final ClassVersion JAVA_6 = new ClassVersion(0, 50);
	public static final ClassVersion JAVA_5 = new ClassVersion(0, 49);
	public static final ClassVersion JAVA_4 = new ClassVersion(0, 48);
	public static final ClassVersion JAVA_3 = new ClassVersion(0, 47);
	public static final ClassVersion JAVA_2 = new ClassVersion(0, 46);
	public static final ClassVersion JAVA_1 = new ClassVersion(0, 45);

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ClassVersion(int minor, int major) {
		this.setMinor(minor);
		this.setMajor(major);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public String toString() {
		return this.getMajor() + "." + this.getMinor();
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	public boolean isBasedOn(ClassVersion other) {
		return this.getMinor() >= other.getMinor() && this.getMajor() >= other.getMajor();
	}

	@Override
	public boolean equals(Object object) {
		if(object.getClass() != this.getClass()) {
			return false;
		}

		ClassVersion target = (ClassVersion) object;

		return this.getMajor() == target.getMajor() && this.getMinor() == target.getMinor();
	}

	@Override
	public int hashCode() {
		return this.getMajor() + this.getMinor();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getMinor() {
		return this.minor;
	}

	protected void setMinor(int minor) {
		this.minor = minor;
	}

	public int getMajor() {
		return this.major;
	}

	protected void setMajor(int major) {
		this.major = major;
	}
}
