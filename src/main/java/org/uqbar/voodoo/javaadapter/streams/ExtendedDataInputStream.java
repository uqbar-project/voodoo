package org.uqbar.voodoo.javaadapter.streams;

import static java.lang.Math.max;
import static org.uqbar.voodoo.javaadapter.BytecodeToolsUtils.utf8Length;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.uqbar.voodoo.javaadapter.exceptions.RuntimeIOException;

//TODO: Extract common things with ExtendedDataOutputStream
public class ExtendedDataInputStream implements AutoCloseable {

	private DataInputStream innerStream;
	private int readed;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ExtendedDataInputStream(String filePath) {
		try {
			this.setInnerStream(new DataInputStream(new FileInputStream(filePath)));
		}
		catch(FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void close() {
		try {
			this.getInnerStream().close();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	// ****************************************************************
	// ** READING
	// ****************************************************************

	public int readInt() {
		try {
			this.setReaded(this.getReaded() + 4);

			return this.getInnerStream().readInt();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public float readFloat() {
		try {
			this.setReaded(this.getReaded() + 4);

			return this.getInnerStream().readFloat();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public long readLong() {
		try {
			this.setReaded(this.getReaded() + 8);

			return this.getInnerStream().readLong();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public double readDouble() {
		try {
			this.setReaded(this.getReaded() + 8);

			return this.getInnerStream().readDouble();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public int readUnsignedShort() {
		try {
			this.setReaded(this.getReaded() + 2);

			return this.getInnerStream().readUnsignedShort();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public int readUnsignedByte() {
		try {
			this.setReaded(this.getReaded() + 1);

			return this.getInnerStream().readUnsignedByte();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public int readByte() {
		try {
			this.setReaded(this.getReaded() + 1);

			return this.getInnerStream().readByte();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public String readUTF() {
		try {
			String answer = this.getInnerStream().readUTF();

			this.setReaded(this.getReaded() + 2 + utf8Length(answer));

			return answer;
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public int read(byte[] target) {
		try {
			int answer = this.getInnerStream().read(target);

			this.setReaded(this.getReaded() + max(0, answer));

			return answer;
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected DataInputStream getInnerStream() {
		return this.innerStream;
	}

	protected void setInnerStream(DataInputStream innerStream) {
		this.innerStream = innerStream;
	}

	public int getReaded() {
		return this.readed;
	}

	protected void setReaded(int readed) {
		this.readed = readed;
	}
}
