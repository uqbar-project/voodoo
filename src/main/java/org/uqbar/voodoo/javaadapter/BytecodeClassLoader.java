package org.uqbar.voodoo.javaadapter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.uqbar.voodoo.javaadapter.model.Class;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class BytecodeClassLoader extends ClassLoader {

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public java.lang.Class<?> importClass(Class aClass) {
		byte[] bytes;

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ExtendedDataOutputStream dos = new ExtendedDataOutputStream(new DataOutputStream(baos))) {
			aClass.writeTo(dos);
			dos.flush();
			bytes = baos.toByteArray();
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}

		return this.defineClass(aClass.getName(), bytes, 0, bytes.length);
	}
}