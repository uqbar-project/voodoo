package org.uqbar.voodoo.writer

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.FileInputStream

import org.scalatest.FunSuite
import org.uqbar.Utils.autoClose
import org.uqbar.voodoo.CONSTANT_POOL_SAMPLE
import org.uqbar.voodoo.CONSTANT_POOL_SAMPLE_FILE

class WriterTest extends FunSuite with ClassWriter {

	test("ConstantPool writer should write properly") {
		for {
			expectedInput ← new FileInputStream(CONSTANT_POOL_SAMPLE_FILE)
			output ← new ByteArrayOutputStream
		} {
			val expected = Stream.continually(expectedInput.read).takeWhile(-1 != _).map(_.toByte).toArray

			writeConstantPool(CONSTANT_POOL_SAMPLE)(new DataOutputStream(output))

			assert(expected.toArray === output.toByteArray)
		}
	}
}