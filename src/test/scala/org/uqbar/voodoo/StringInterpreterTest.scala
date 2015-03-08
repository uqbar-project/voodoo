package org.uqbar.voodoo

import java.lang.invoke.MethodHandles

import scala.reflect.runtime.universe

import org.scalatest.FunSuite
import org.uqbar.voodoo.StringInterpreter.binaryNameFromTypeName
import org.uqbar.voodoo.StringInterpreter.descriptorFromName
import org.uqbar.voodoo.StringInterpreter.signatureTypeFromDescriptor
import org.uqbar.voodoo.StringInterpreter.typeNameFromBinaryName
import org.uqbar.voodoo.model.$
import org.uqbar.voodoo.model.UnitExt

class DescriptorTest extends FunSuite {

	val typeInfo = Seq( // (Type, name, binary name, descriptor)
		($[Unit], "void", "void", "V"),
		($[Byte], "byte", "byte", "B"),
		($[Char], "char", "char", "C"),
		($[Double], "double", "double", "D"),
		($[Float], "float", "float", "F"),
		($[Int], "int", "int", "I"),
		($[Long], "long", "long", "J"),
		($[Short], "short", "short", "S"),
		($[Boolean], "boolean", "boolean", "Z"),
		($[AnyRef], "java.lang.Object", "java/lang/Object", "Ljava/lang/Object;"),
		($[String], "java.lang.String", "java/lang/String", "Ljava/lang/String;"),
		($[MethodHandles.Lookup], "java.lang.invoke.MethodHandles$Lookup", "java/lang/invoke/MethodHandles$Lookup", "Ljava/lang/invoke/MethodHandles$Lookup;"),

		($[Array[Byte]], "byte[]", "[B", "[B"),
		($[Array[Char]], "char[]", "[C", "[C"),
		($[Array[Double]], "double[]", "[D", "[D"),
		($[Array[Float]], "float[]", "[F", "[F"),
		($[Array[Int]], "int[]", "[I", "[I"),
		($[Array[Long]], "long[]", "[J", "[J"),
		($[Array[Short]], "short[]", "[S", "[S"),
		($[Array[Boolean]], "boolean[]", "[Z", "[Z"),
		($[Array[AnyRef]], "java.lang.Object[]", "[Ljava/lang/Object;", "[Ljava/lang/Object;"),
		($[Array[String]], "java.lang.String[]", "[Ljava/lang/String;", "[Ljava/lang/String;"),
		($[Array[MethodHandles.Lookup]], "java.lang.invoke.MethodHandles$Lookup[]", "[Ljava/lang/invoke/MethodHandles$Lookup;", "[Ljava/lang/invoke/MethodHandles$Lookup;"),

		($[Array[Array[Byte]]], "byte[][]", "[[B", "[[B"),
		($[Array[Array[Char]]], "char[][]", "[[C", "[[C"),
		($[Array[Array[Double]]], "double[][]", "[[D", "[[D"),
		($[Array[Array[Float]]], "float[][]", "[[F", "[[F"),
		($[Array[Array[Int]]], "int[][]", "[[I", "[[I"),
		($[Array[Array[Long]]], "long[][]", "[[J", "[[J"),
		($[Array[Array[Short]]], "short[][]", "[[S", "[[S"),
		($[Array[Array[Boolean]]], "boolean[][]", "[[Z", "[[Z"),
		($[Array[Array[AnyRef]]], "java.lang.Object[][]", "[[Ljava/lang/Object;", "[[Ljava/lang/Object;"),
		($[Array[Array[String]]], "java.lang.String[][]", "[[Ljava/lang/String;", "[[Ljava/lang/String;"),
		($[Array[Array[MethodHandles.Lookup]]], "java.lang.invoke.MethodHandles$Lookup[][]", "[[Ljava/lang/invoke/MethodHandles$Lookup;", "[[Ljava/lang/invoke/MethodHandles$Lookup;")
	)

	test("Type binary name should be obtainable from type name") {
		for ((_, n, bn, _) ← typeInfo) assert(binaryNameFromTypeName(n) === bn)
	}

	test("Type name should be obtainable from type binary name") {
		for ((_, n, bn, _) ← typeInfo) assert(typeNameFromBinaryName(bn) === n)
	}

	test("Types built from Classes should equal the equivalent types built from Names") {
		for ((t, n, _, _) ← typeInfo) assert($(n) === t)
	}

	test("Types should be obtainable from Descriptors and vice versa") {
		for {
			(t1, _, _, d1) ← typeInfo
			(t2, _, _, d2) ← typeInfo
			if (t1 != $[Unit] && t2 != $[Unit])
			(tr, _, _, dr) ← typeInfo
			(t, d) ← (tr, dr) :: (() -> tr, s"()$dr") :: (t1 -> tr, s"($d1)$dr") :: (t1 -> t2 -> tr, s"($d1$d2)$dr") :: Nil
		} {
			if (t != $[Unit]) assertResult(t)(signatureTypeFromDescriptor(d))
			assertResult(d)(descriptorFromName(t))
		}
	}

	test("Void should not be a valid argument type") {
		intercept[IllegalArgumentException] { $[Unit] -> $[Int] }
		intercept[IllegalArgumentException] { $[Int] -> $[Unit] -> $[Int] }
	}

	test("Void should not be a valid array argument type") {
		intercept[IllegalArgumentException] { $[Array[Unit]] }
	}

	test("Invalid descriptor interpretation should raise exception") {
		intercept[IllegalArgumentException] { signatureTypeFromDescriptor("") }
		intercept[IllegalArgumentException] { signatureTypeFromDescriptor("X") }
		intercept[IllegalArgumentException] { signatureTypeFromDescriptor("V") }
		intercept[IllegalArgumentException] { signatureTypeFromDescriptor("(IXI)V") }
		intercept[IllegalArgumentException] { signatureTypeFromDescriptor("(IJ)X") }
	}
}