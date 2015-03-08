package org.uqbar.voodoo.builder

import scala.reflect.runtime.universe

import org.scalatest.FunSuite
import org.uqbar.voodoo.CONSTANT_POOL_SAMPLE
import org.uqbar.voodoo.model.$
import org.uqbar.voodoo.model.GETFIELD
import org.uqbar.voodoo.model.UnitExt
import org.uqbar.voodoo.model.constants.Data
import org.uqbar.voodoo.model.constants.InvokeDynamic
import org.uqbar.voodoo.model.constants.MethodHandleRef
import org.uqbar.voodoo.model.constants.UTF8

class BuilderTest extends FunSuite {

	test("ConstantPoolBuilder should add all implicit entries") {
		val result = new ConstantPoolBuilder(
			$("Foo"),
			$("Foo") >> "bar" :: $[String],
			$("Foo") >>> "getBar" :: () -> $[String],
			$("Foo") >> "setBar" :: $[String] -> $[Unit],
			Data("bar"),
			Data(1),
			Data(1.0f),
			Data(1L),
			Data(1.0),
			MethodHandleRef(GETFIELD($("Foo") >> "bar" :: $[String])),
			() -> $[String],
			InvokeDynamic(0, "getBar" :: () -> $[String]),
			UTF8("ConstantValue"),
			Data("ConstantValue")
		).build

		assertResult(CONSTANT_POOL_SAMPLE)(result)
	}

	test("8 bit ConstantPool entries should cover two entries") {
		val result = new ConstantPoolBuilder(
			Data(1.0),
			Data(1L)
		).build

		assertResult(Data(1.0))(result(1))
		assertResult(Data(1L))(result(3))
		assertResult(4)(result.constantCount)
	}
}