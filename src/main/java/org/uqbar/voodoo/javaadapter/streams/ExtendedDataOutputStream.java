package org.uqbar.voodoo.javaadapter.streams;

import static org.uqbar.voodoo.javaadapter.BytecodeToolsUtils.utf8Length;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.uqbar.voodoo.javaadapter.exceptions.RuntimeIOException;

public class ExtendedDataOutputStream implements AutoCloseable {

	private DataOutputStream innerStream;
	private int writed;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ExtendedDataOutputStream(String filePath) {
		try {
			this.setInnerStream(new DataOutputStream(new FileOutputStream(filePath)));
		}
		catch(FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}

	public ExtendedDataOutputStream(DataOutputStream stream) {
		this.setInnerStream(stream);
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

	public void flush() {
		try {
			this.getInnerStream().flush();
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	// ****************************************************************
	// ** WRITING
	// ****************************************************************

	public void writeInt(int x) {
		try {
			this.setWrited(this.getWrited() + 4);

			this.getInnerStream().writeInt(x);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public void writeFloat(float x) {
		try {
			this.setWrited(this.getWrited() + 4);

			this.getInnerStream().writeFloat(x);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public void writeLong(long x) {
		try {
			this.setWrited(this.getWrited() + 8);

			this.getInnerStream().writeLong(x);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public void writeDouble(double x) {
		try {
			this.setWrited(this.getWrited() + 8);

			this.getInnerStream().writeDouble(x);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public void writeShort(int x) {
		try {
			this.setWrited(this.getWrited() + 2);

			this.getInnerStream().writeShort(x);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public void writeByte(int x) {
		try {
			this.setWrited(this.getWrited() + 1);

			this.getInnerStream().writeByte(x);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public void writeUTF(String s) {
		try {
			this.setWrited(this.getWrited() + 2 + utf8Length(s));

			this.getInnerStream().writeUTF(s);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public void write(byte[] bytes) {
		try {
			this.setWrited(this.getWrited() + bytes.length);

			this.getInnerStream().write(bytes);
		}
		catch(IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected DataOutputStream getInnerStream() {
		return this.innerStream;
	}

	protected void setInnerStream(DataOutputStream innerStream) {
		this.innerStream = innerStream;
	}

	public int getWrited() {
		return this.writed;
	}

	protected void setWrited(int writed) {
		this.writed = writed;
	}
}
