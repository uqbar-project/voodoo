package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies;

import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;

public class ByteHandler {

	private int byteCount;
	private boolean signed;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ByteHandler(int byteCount) {
		this(byteCount, false);
	}

	public ByteHandler(int byteCount, boolean signed) {
		this.setByteCount(byteCount);
		this.setSigned(signed);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public ByteHandler wide() {
		return new ByteHandler(this.getByteCount() * 2, this.isSigned());
	}

	public byte[] toBytes(int value) {
		byte[] answer = new byte[this.getByteCount()];

		for(int i = 0; i < answer.length; i++) {
			answer[answer.length - 1 - i] = (byte) (value >> i * 8 & 0xFF);
		}

		return answer;
	}

	public int parseBytes(ExtendedDataInputStream input) {
		int answer = 0;

		for(int i = 0; i < this.getByteCount(); i++) {
			answer += this.isSigned() && i == 0
				? input.readByte() << 8 * (this.getByteCount() - 1 - i)
				: input.readUnsignedByte() << 8 * (this.getByteCount() - 1 - i);
		}

		return answer;
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getByteCount() {
		return this.byteCount;
	}

	protected void setByteCount(int byteCount) {
		this.byteCount = byteCount;
	}

	protected boolean isSigned() {
		return this.signed;
	}

	protected void setSigned(boolean signed) {
		this.signed = signed;
	}
}