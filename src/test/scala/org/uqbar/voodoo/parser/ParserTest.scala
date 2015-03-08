package org.uqbar.voodoo.parser

import java.io.DataInputStream
import java.io.FileInputStream

import org.scalatest.FunSuite
import org.uqbar.Utils.autoClose
import org.uqbar.voodoo.ACCESS_FLAGS_SAMPLE
import org.uqbar.voodoo.ACCESS_FLAGS_SAMPLE_FILE
import org.uqbar.voodoo.BytecodeClassLoader
import org.uqbar.voodoo.CLASS_VERSION_SAMPLE
import org.uqbar.voodoo.CLASS_VERSION_SAMPLE_FILE
import org.uqbar.voodoo.CONSTANT_POOL_SAMPLE
import org.uqbar.voodoo.CONSTANT_POOL_SAMPLE_FILE
import org.uqbar.voodoo.INTERFACES_SAMPLE
import org.uqbar.voodoo.INTERFACES_SAMPLE_FILE
import org.uqbar.voodoo.TYPE_SAMPLE
import org.uqbar.voodoo.TYPE_SAMPLE_FILE

class ParserTest extends FunSuite with ClassParser {

	test("ClassVersion should be parsed properly") {
		for (input ← new DataInputStream(new FileInputStream(CLASS_VERSION_SAMPLE_FILE))) {
			val result = parseVersion(input)

			assertResult(CLASS_VERSION_SAMPLE)(result)
		}
	}

	test("ConstantPool should be parsed properly") {
		for (input ← new DataInputStream(new FileInputStream(CONSTANT_POOL_SAMPLE_FILE))) {
			val result = parseConstantPool(input)

			assertResult(CONSTANT_POOL_SAMPLE)(result)
		}
	}

	test("AccessFlags should be parsed properly") {
		for (input ← new DataInputStream(new FileInputStream(ACCESS_FLAGS_SAMPLE_FILE))) {
			val result = parseAccessFlags(input)

			assertResult(ACCESS_FLAGS_SAMPLE)(result)
		}
	}

	test("Types should be parsed properly") {
		for (input ← new DataInputStream(new FileInputStream(TYPE_SAMPLE_FILE))) {
			val result = parseType(CONSTANT_POOL_SAMPLE)(input)

			assertResult(TYPE_SAMPLE)(result)
		}
	}

	test("Interfaces should be parsed properly") {
		for (input ← new DataInputStream(new FileInputStream(INTERFACES_SAMPLE_FILE))) {
			val result = parseInterfaces(CONSTANT_POOL_SAMPLE)(input)

			assertResult(INTERFACES_SAMPLE)(result)
		}
	}

	//TODO: Keep on testing

	test("A parsed class should be loadable") {
		val result = parseClass(getClass.getResource("/Foo.class").getFile)

		new BytecodeClassLoader().importClass(result)
	}
}