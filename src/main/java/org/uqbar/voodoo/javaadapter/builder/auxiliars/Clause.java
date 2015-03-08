package org.uqbar.voodoo.javaadapter.builder.auxiliars;

import org.uqbar.voodoo.javaadapter.builder.CodeBuilder;

public interface Clause<T> {
	public void run(CodeBuilder builder, T element, int index);
}