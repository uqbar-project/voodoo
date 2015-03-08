package org.uqbar.voodoo.javaadapter.model;

import java.io.File;

import org.junit.After;
import org.junit.Test;
import org.uqbar.voodoo.javaadapter.model.Class;
import org.uqbar.voodoo.javaadapter.parser.ClassParser;

public class BytecodeWritingTest {

	String tempDirName = "generated";
	String targetPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile() + this.tempDirName;

	@After
	public void tearDown() {
		new File(this.targetPath).deleteOnExit();
	}

	@Test
	public void test() {
		Class classInfo = new ClassParser(this.getClass().getResource("/Foo.class").getPath()).parse();

		classInfo.writeTo(this.targetPath);
	}
}