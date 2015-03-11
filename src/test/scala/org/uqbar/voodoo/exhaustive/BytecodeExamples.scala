package org.uqbar.voodoo.exhaustive

import java.lang.invoke.CallSite
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodType
import java.util.ArrayList
import java.util.Arrays
import java.util.Collection
import java.util.HashSet
import scala.reflect.runtime.universe
import org.uqbar.voodoo.model.MethodModifier.Static
import org.uqbar.voodoo.model._
import org.uqbar.voodoo.mutator.MutableType
import java.lang.invoke.ConstantCallSite
import java.util.Date
import java.awt.Point
import org.uqbar.voodoo.model.ClassVersion.Java_6

object BytecodeExample {

	protected val CONTEXT_TYPE = $("TestClass")
	val METHOD_SELECTOR = "test"

	val methods = Map(
		"AALOAD" -> (METHOD_SELECTOR :: $[Array[Object]] -> $[Object])( // Returns the first object of the given array.
			ALOAD_1,
			ICONST_0,
			AALOAD,
			ARETURN
		),

		"AASTORE" -> (METHOD_SELECTOR :: $[Object] -> $[Array[Object]] -> $[Unit])( // Stores the given object as the first element of the given array.
			ALOAD_2,
			ICONST_0,
			ALOAD_1,
			AASTORE,
			RETURN
		),

		"ACONST_NULL" -> (METHOD_SELECTOR :: () -> $[Object])( // Returns null.
			ACONST_NULL,
			ARETURN
		),

		"ALOAD" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object.
			ALOAD(1),
			ARETURN
		),

		"ALOAD_0" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object.
			ALOAD_0,
			ARETURN
		).as(Static),

		"ALOAD_1" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object.
			ALOAD_1,
			ARETURN
		),

		"ALOAD_2" -> (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Object])( // Returns the second given object.
			ALOAD_2,
			ARETURN
		),

		"ALOAD_3" -> (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Object] -> $[Object])( // Returns the third given object.
			ALOAD_3,
			ARETURN
		),

		"ANEWARRAY" -> (METHOD_SELECTOR :: () -> $[Array[Object]])( // Returns an empty Object[] of length 5.
			ICONST_5,
			ANEWARRAY($[Object]),
			ARETURN
		),

		"ARETURN" -> (METHOD_SELECTOR :: () -> $[Object])( // Returns null.
			ACONST_NULL,
			ARETURN
		),

		"ARRAYLENGTH" -> (METHOD_SELECTOR :: $[Array[Object]] -> $[Int])( // Returns the length of the given array.
			ALOAD_1,
			ARRAYLENGTH,
			IRETURN
		),

		"ASTORE" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 2.
			ALOAD_1,
			ASTORE(2),
			ALOAD(2),
			ARETURN
		),

		"ASTORE_0" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 0.
			ALOAD_1,
			ASTORE_0,
			ALOAD_0,
			ARETURN
		),

		"ASTORE_1" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 1.
			ALOAD_0,
			ASTORE_1,
			ALOAD_1,
			ARETURN
		).as(Static),

		"ASTORE_2" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 2.
			ALOAD_1,
			ASTORE_2,
			ALOAD_2,
			ARETURN
		),

		"ASTORE_3" -> (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 3.
			ALOAD_1,
			ASTORE_2,
			ALOAD_1,
			ASTORE_3,
			ALOAD_3,
			ARETURN
		),

		"ATHROW" -> (METHOD_SELECTOR :: () -> $[Unit])( // Throws ArithmeticException.
			NEW($[ArithmeticException]),
			DUP,
			INVOKESPECIAL($[ArithmeticException] >> Init()),
			ATHROW
		),

		"BALOAD" -> (METHOD_SELECTOR :: $[Array[Boolean]] -> $[Boolean])( // Returns the first Boolean of the given array.
			ALOAD_1,
			ICONST_0,
			BALOAD,
			IRETURN
		),

		"BASTORE" -> (METHOD_SELECTOR :: $[Boolean] -> $[Array[Boolean]] -> $[Unit])( // Stores the given Boolean as the first element of the given array.
			ALOAD_2,
			ICONST_0,
			ILOAD_1,
			BASTORE,
			RETURN
		),

		"BIPUSH" -> (METHOD_SELECTOR :: () -> $[Byte])( // Returns 5.
			BIPUSH(5),
			IRETURN
		),

		"CALOAD" -> (METHOD_SELECTOR :: $[Array[Char]] -> $[Char])( // Returns the first char of the given array.
			ALOAD_1,
			ICONST_0,
			CALOAD,
			IRETURN
		),

		"CASTORE" -> (METHOD_SELECTOR :: $[Char] -> $[Array[Char]] -> $[Unit])( // Stores the given char as the first element of the given array.
			ALOAD_2,
			ICONST_0,
			ILOAD_1,
			CASTORE,
			RETURN
		),

		"CHECKCAST" -> (METHOD_SELECTOR :: $[Object] -> $[String])( // Returns the given object casted to another type.
			ALOAD_1,
			CHECKCAST($[String]),
			ARETURN
		),

		"D2F" -> (METHOD_SELECTOR :: $[Double] -> $[Float])( // Returns the given argument converted to another type.
			DLOAD_1,
			D2F,
			FRETURN
		),

		"D2I" -> (METHOD_SELECTOR :: $[Double] -> $[Int])( // Returns the given argument converted to another type.
			DLOAD_1,
			D2I,
			IRETURN
		),

		"D2L" -> (METHOD_SELECTOR :: $[Double] -> $[Long])( // Returns the given argument converted to another type.
			DLOAD_1,
			D2L,
			LRETURN
		),

		"DADD" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 5.0.
			LDC2_W(3.0),
			LDC2_W(2.0),
			DADD,
			DRETURN
		),

		"DALOAD" -> (METHOD_SELECTOR :: $[Array[Double]] -> $[Double])( // Returns the first double of the given array.
			ALOAD_1,
			ICONST_0,
			DALOAD,
			DRETURN
		),

		"DASTORE" -> (METHOD_SELECTOR :: $[Double] -> $[Array[Double]] -> $[Unit])( // Stores the given double as the first element of the given array.
			ALOAD_3,
			ICONST_0,
			DLOAD_1,
			DASTORE,
			RETURN
		),

		"DCMPG" -> (METHOD_SELECTOR :: $[Double] -> $[Double] -> $[Int])( // Compares the received doubles. Answers 1 if either one is NaN.
			DLOAD_1,
			DLOAD_3,
			DCMPG,
			IRETURN
		),

		"DCMPL" -> (METHOD_SELECTOR :: $[Double] -> $[Double] -> $[Int])( // Compares the received doubles. Answers -1 if either one is NaN.
			DLOAD_1,
			DLOAD_3,
			DCMPL,
			IRETURN
		),

		"DCONST_0" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 0.0.
			DCONST_0,
			DRETURN
		),

		"DCONST_1" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0.
			DCONST_1,
			DRETURN
		),

		"DDIV" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 2.5.
			LDC2_W(5.0),
			LDC2_W(2.0),
			DDIV,
			DRETURN
		),

		"DLOAD" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
			DLOAD(1),
			DRETURN
		),

		"DLOAD_0" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
			DLOAD_0,
			DRETURN
		).as(Static),

		"DLOAD_1" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
			DLOAD_1,
			DRETURN
		),

		"DLOAD_2" -> (METHOD_SELECTOR :: $[Int] -> $[Double] -> $[Double])( // Returns the given double.
			DLOAD_2,
			DRETURN
		),

		"DLOAD_3" -> (METHOD_SELECTOR :: $[Long] -> $[Double] -> $[Double])( // Returns the given double.
			DLOAD_3,
			DRETURN
		),

		"DMUL" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 6.0.
			LDC2_W(3.0),
			LDC2_W(2.0),
			DMUL,
			DRETURN
		),

		"DNEG" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns -3.0.
			LDC2_W(3.0),
			DNEG,
			DRETURN
		),

		"DREM" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0.
			LDC2_W(5.0),
			LDC2_W(2.0),
			DREM,
			DRETURN
		),

		"DRETURN" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
			DLOAD_1,
			DRETURN
		),

		"DSTORE" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double after storing it in the local 3.
			DLOAD_1,
			DSTORE(3),
			DLOAD(3),
			DRETURN
		),

		"DSTORE_0" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0 after storing it in the local 0.
			DCONST_1,
			DSTORE_0,
			DLOAD_0,
			DRETURN
		).as(Static),

		"DSTORE_1" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double after storing it in the local 1.
			DLOAD_1,
			DSTORE_1,
			DLOAD_1,
			DRETURN
		),

		"DSTORE_2" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns 1.0 after storing it in the local 2.
			DCONST_1,
			DSTORE_2,
			DLOAD_2,
			DRETURN
		).as(Static),

		"DSTORE_3" -> (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double after storing it in the local 3.
			DLOAD_1,
			DSTORE_3,
			DLOAD_3,
			DRETURN
		),

		"DSUB" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0.
			LDC2_W(3.0),
			LDC2_W(2.0),
			DSUB,
			DRETURN
		),

		"DUP" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Subtracts the received int from itself and returns 0.
			ILOAD_1,
			DUP,
			ISUB,
			IRETURN
		),

		"DUP_X1" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Subtracts 0 from the received int, and then subtracts it from the result. Returns the received int negated.
			ILOAD_1,
			ICONST_0,
			DUP_X1,
			ISUB,
			ISUB,
			IRETURN
		),

		"DUP_X2" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Subtracts from the receiver int the result of 1 minus subtracting it from 0. Returns -1.
			ICONST_1,
			ICONST_0,
			ILOAD_1,
			DUP_X2,
			ISUB,
			ISUB,
			ISUB,
			IRETURN
		),

		"DUP2" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Subtracts the received long from itself and returns 0L.
			LLOAD_1,
			DUP2,
			LSUB,
			LRETURN
		),

		"DUP2_X1" -> (METHOD_SELECTOR :: $[Int] -> $[Long])( // Returns the received int converted to long and negated.
			ILOAD_1,
			LCONST_0,
			DUP2_X1,
			LSTORE_2,
			I2L,
			LLOAD_2,
			LSUB,
			LSUB,
			LRETURN
		),

		"DUP2_X2" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Subtracts 0L from the received long, and then subtracts it from the result. Returns the received long negated.
			LLOAD_1,
			LCONST_0,
			DUP2_X2,
			LSUB,
			LSUB,
			LRETURN
		),

		"F2D" -> (METHOD_SELECTOR :: $[Float] -> $[Double])( // Returns the given argument converted to another type.
			FLOAD_1,
			F2D,
			DRETURN
		),

		"F2I" -> (METHOD_SELECTOR :: $[Float] -> $[Int])( // Returns the given argument converted to another type.
			FLOAD_1,
			F2I,
			IRETURN
		),

		"F2L" -> (METHOD_SELECTOR :: $[Float] -> $[Long])( // Returns the given argument converted to another type.
			FLOAD_1,
			F2L,
			LRETURN
		),

		"FADD" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 5F.
			LDC_W(3.0F),
			LDC_W(2.0F),
			FADD,
			FRETURN
		),

		"FALOAD" -> (METHOD_SELECTOR :: $[Array[Float]] -> $[Float])( // Returns the first float of the given array.
			ALOAD_1,
			ICONST_0,
			FALOAD,
			FRETURN
		),

		"FASTORE" -> (METHOD_SELECTOR :: $[Float] -> $[Array[Float]] -> $[Unit])( // Stores the given float as the first element of the given array.
			ALOAD_2,
			ICONST_0,
			FLOAD_1,
			FASTORE,
			RETURN
		),

		"FCMPG" -> (METHOD_SELECTOR :: $[Float] -> $[Float] -> $[Int])( // Compares the received floats. Answers 1 if either one is NaN.
			FLOAD_1,
			FLOAD_2,
			FCMPG,
			IRETURN
		),

		"FCMPL" -> (METHOD_SELECTOR :: $[Float] -> $[Float] -> $[Int])( // Compares the received floats. Answers 1 if either one is NaN.
			FLOAD_1,
			FLOAD_2,
			FCMPL,
			IRETURN
		),

		"FCONST_0" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 0.0F.
			FCONST_0,
			FRETURN
		),

		"FCONST_1" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 1.0F.
			FCONST_1,
			FRETURN
		),

		"FCONST_2" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 2.0F.
			FCONST_2,
			FRETURN
		),

		"FDIV" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 2.5F.
			LDC_W(5.0F),
			LDC_W(2.0F),
			FDIV,
			FRETURN
		),

		"FLOAD" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
			FLOAD(1),
			FRETURN
		),

		"FLOAD_0" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
			FLOAD_0,
			FRETURN
		).as(Static),

		"FLOAD_1" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
			FLOAD_1,
			FRETURN
		),

		"FLOAD_2" -> (METHOD_SELECTOR :: $[Int] -> $[Float] -> $[Float])( // Returns the given float.
			FLOAD_2,
			FRETURN
		),

		"FLOAD_3" -> (METHOD_SELECTOR :: $[Long] -> $[Float] -> $[Float])( // Returns the given float.
			FLOAD_3,
			FRETURN
		),

		"FMUL" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 6.0F.
			LDC_W(3.0F),
			LDC_W(2.0F),
			FMUL,
			FRETURN
		),

		"FNEG" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns -3.0F.
			LDC_W(3.0F),
			FNEG,
			FRETURN
		),

		"FREM" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 1.0F.
			LDC_W(5.0F),
			LDC_W(2.0F),
			FREM,
			FRETURN
		),

		"FRETURN" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
			FLOAD_1,
			FRETURN
		),

		"FSTORE" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 2.
			FLOAD_1,
			FSTORE(2),
			FLOAD(2),
			FRETURN
		),

		"FSTORE_0" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 0.
			FLOAD_1,
			FSTORE_0,
			FLOAD_0,
			FRETURN
		),

		"FSTORE_1" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 1.
			FLOAD_1,
			FSTORE_1,
			FLOAD_1,
			FRETURN
		),

		"FSTORE_2" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 2.
			FLOAD_1,
			FSTORE_2,
			FLOAD_2,
			FRETURN
		),

		"FSTORE_3" -> (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 3.
			FLOAD_1,
			FSTORE_2,
			FLOAD_1,
			FSTORE_3,
			FLOAD_3,
			FRETURN
		),

		"FSUB" -> (METHOD_SELECTOR :: () -> $[Float])( // Returns 1F.
			LDC_W(3.0F),
			LDC_W(2.0F),
			FSUB,
			FRETURN
		),

		"GETFIELD" -> (METHOD_SELECTOR :: $[Point] -> $[Int])( // Returns the target object field
			ALOAD_1,
			GETFIELD($[Point] >> "x" :: $[Int]),
			IRETURN
		),

		"GETSTATIC" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns the target static field
			GETSTATIC($[Integer] >> "MAX_VALUE" :: $[Int]),
			IRETURN
		),

		"GOTO" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
			GOTO("5"),
			"3" @: ICONST_0,
			IRETURN,

			"5" @: ICONST_0,
			ICONST_1,
			IF_ICMPEQ("3"),
			ICONST_5,
			IRETURN
		),

		"GOTO_W" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
			GOTO_W("7"),
			"5" @: ICONST_0,
			IRETURN,

			"7" @: ICONST_0,
			ICONST_1,
			IF_ICMPEQ("5"),
			ICONST_5,
			IRETURN
		),

		"I2B" -> (METHOD_SELECTOR :: $[Int] -> $[Byte])( // Returns the given argument converted to another type.
			ILOAD_1,
			I2B,
			IRETURN
		),

		"I2C" -> (METHOD_SELECTOR :: $[Int] -> $[Char])( // Returns the given argument converted to another type.
			ILOAD_1,
			I2C,
			IRETURN
		),

		"I2D" -> (METHOD_SELECTOR :: $[Int] -> $[Double])( // Returns the given argument converted to another type.
			ILOAD_1,
			I2D,
			DRETURN
		),

		"I2F" -> (METHOD_SELECTOR :: $[Int] -> $[Float])( // Returns the given argument converted to another type.
			ILOAD_1,
			I2F,
			FRETURN
		),

		"I2L" -> (METHOD_SELECTOR :: $[Int] -> $[Long])( // Returns the given argument converted to another type.
			ILOAD_1,
			I2L,
			LRETURN
		),

		"I2S" -> (METHOD_SELECTOR :: $[Int] -> $[Short])( // Returns the given argument converted to another type.
			ILOAD_1,
			I2S,
			IRETURN
		),

		"IADD" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
			ICONST_3,
			ICONST_2,
			IADD,
			IRETURN
		),

		"IALOAD" -> (METHOD_SELECTOR :: $[Array[Int]] -> $[Int])( // Returns the first int of the given array.
			ALOAD_1,
			ICONST_0,
			IALOAD,
			IRETURN
		),

		"IAND" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 4.
			ICONST_5,
			ICONST_4,
			IAND,
			IRETURN
		),

		"IASTORE" -> (METHOD_SELECTOR :: $[Int] -> $[Array[Int]] -> $[Unit])( // Stores the given int as the first element of the given array.
			ALOAD_2,
			ICONST_0,
			ILOAD_1,
			IASTORE,
			RETURN
		),

		"ICONST_M1" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns -1.
			ICONST_M1,
			IRETURN
		),

		"ICONST_0" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 0.
			ICONST_0,
			IRETURN
		),

		"ICONST_1" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
			ICONST_1,
			IRETURN
		),

		"ICONST_2" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 2.
			ICONST_2,
			IRETURN
		),

		"ICONST_3" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 3.
			ICONST_3,
			IRETURN
		),

		"ICONST_4" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 4.
			ICONST_4,
			IRETURN
		),

		"ICONST_5" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
			ICONST_5,
			IRETURN
		),

		"IDIV" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 2.
			ICONST_5,
			ICONST_2,
			IDIV,
			IRETURN
		),

		"IF_ACMPEQ" -> (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Int])( // Returns 1 if arguments are equal and 0 if they are not.
			ALOAD_1,
			ALOAD_2,
			IF_ACMPEQ("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			IRETURN
		),

		"IF_ACMPNE" -> (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Int])( // Returns 0 if arguments are equal and 1 if they are not.
			ALOAD_1,
			ALOAD_2,
			IF_ACMPNE("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			IRETURN
		),

		"IF_ICMPEQ" -> (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if arguments are equal and 0 if they are not.
			ILOAD_1,
			ILOAD_2,
			IF_ICMPEQ("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			ISTORE_3,
			ILOAD_3,
			ICONST_1,
			IRETURN
		),

		"IF_ICMPNE" -> (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 0 if arguments are equal and 1 if they are not.
			ILOAD_1,
			ILOAD_2,
			IF_ICMPNE("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			IRETURN
		),

		"IF_ICMPLT" -> (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is lesser than the second argument and 0 if it is not.
			ILOAD_1,
			ILOAD_2,
			IF_ICMPLT("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			IRETURN
		),

		"IF_ICMPGE" -> (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is great or equal to the second argument and 0 if it is not.
			ILOAD_1,
			ILOAD_2,
			IF_ICMPGE("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			IRETURN
		),

		"IF_ICMPGT" -> (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is greater than the second argument and 0 if it is not.
			ILOAD_1,
			ILOAD_2,
			IF_ICMPGT("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			IRETURN
		),

		"IF_ICMPLE" -> (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is less or equal to the second argument and 0 if it is not.
			ILOAD_1,
			ILOAD_2,
			IF_ICMPLE("7"),
			ICONST_0,
			IRETURN,
			"7" @: ICONST_1,
			IRETURN
		),

		"IFEQ" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is equal to 0 and 0 if not.
			ILOAD_1,
			IFEQ("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IFNE" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 0 if the argument is equal to 0 and 1 if not.
			ILOAD_1,
			IFNE("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IFLT" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is less than to 0 and 0 if not.
			ILOAD_1,
			IFLT("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IFGE" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is great or equal to 0 and 0 if not.
			ILOAD_1,
			IFGE("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IFGT" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is greater than 0 and 0 if not.
			ILOAD_1,
			IFGT("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IFLE" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is less or equal to 0 and 0 if not.
			ILOAD_1,
			IFLE("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IFNONNULL" -> (METHOD_SELECTOR :: $[Object] -> $[Int])( // Returns 1 if the argument is not null and 0 if it is.
			ALOAD_1,
			IFNONNULL("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IFNULL" -> (METHOD_SELECTOR :: $[Object] -> $[Int])( // Returns 0 if the argument is not null and 1 if it is.
			ALOAD_1,
			IFNULL("6"),
			ICONST_0,
			IRETURN,
			"6" @: ICONST_1,
			IRETURN
		),

		"IINC" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
			ICONST_0,
			ISTORE_1,
			IINC(1, 5),
			ILOAD_1,
			IRETURN
		),

		"ILOAD" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
			ILOAD(1),
			IRETURN
		),

		"ILOAD_0" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
			ILOAD_0,
			IRETURN
		).as(Static),

		"ILOAD_1" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
			ILOAD_1,
			IRETURN
		),

		"ILOAD_2" -> (METHOD_SELECTOR :: $[Float] -> $[Int] -> $[Int])( // Returns the given int.
			ILOAD_2,
			IRETURN
		),

		"ILOAD_3" -> (METHOD_SELECTOR :: $[Long] -> $[Int] -> $[Int])( // Returns the given int.
			ILOAD_3,
			IRETURN
		),

		"IMUL" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 6.
			ICONST_3,
			ICONST_2,
			IMUL,
			IRETURN
		),

		"INEG" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns -3.
			ICONST_3,
			INEG,
			IRETURN
		),

		"INSTANCEOF" -> (METHOD_SELECTOR :: $[Object] -> $[Int])( // Returns 1 if the received object is intance of Bleh and 0 if it isn't.
			ALOAD_1,
			INSTANCEOF($[String]),
			IRETURN
		),

		"INVOKEDYNAMIC" -> (METHOD_SELECTOR :: $[Integer] -> $[Integer] -> $[Integer])( // Adds the given Integers.
			ALOAD_1,
			ALOAD_2,
			//TODO: El tipo del bootstrap es siempre el mismo. No ponerlo.
			INVOKEDYNAMIC(SlotRef_to_Bootstrap(INVOKESTATIC($("CustomBootstrap") >> "dispatch" :: $[Lookup] -> $[String] -> $[MethodType] -> $[CallSite])), "adder" :: $[Integer] -> $[Integer] -> $[Integer]),
			ARETURN
		),

		"INVOKEINTERFACE" -> (METHOD_SELECTOR :: $[Collection[_]] -> $[Object] -> $[Unit])( // Adds the given object to the given collection.
			ALOAD_1,
			ALOAD_2,
			INVOKEINTERFACE($[Collection[_]] >>> "add" :: $[Object] -> $[Boolean]),
			RETURN
		),

		"INVOKESPECIAL" -> (METHOD_SELECTOR :: () -> $[Date])( // Returns a new Date instance
			NEW($[Date]),
			DUP,
			INVOKESPECIAL($[Date] >> Init()),
			ARETURN
		),

		"INVOKESTATIC" -> (METHOD_SELECTOR :: () -> $[String])( // Returns "0"
			ICONST_0,
			INVOKESTATIC($[Integer] >> "toString" :: $[Int] -> $[String]),
			ARETURN
		),

		"INVOKEVIRTUAL" -> (METHOD_SELECTOR :: $[String] -> $[Int])( // Returns the result of sending the length message to the received object.
			ALOAD_1,
			INVOKEVIRTUAL($[String] >> "length" :: () -> $[Int]),
			IRETURN
		),

		"IOR" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
			ICONST_1,
			ICONST_4,
			IOR,
			IRETURN
		),

		"IREM" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
			ICONST_5,
			ICONST_2,
			IREM,
			IRETURN
		),

		"IRETURN" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
			ILOAD_1,
			IRETURN
		),

		"ISHL" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns -8.
			ICONST_M1,
			ICONST_3,
			ISHL,
			IRETURN
		),

		"ISHR" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns -1.
			BIPUSH(-8),
			ICONST_3,
			ISHR,
			IRETURN
		),

		"ISTORE" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 2.
			ILOAD_1,
			ISTORE(3),
			ILOAD(3),
			IRETURN
		),

		"ISTORE_0" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 0.
			ILOAD_1,
			ISTORE_0,
			ILOAD_0,
			IRETURN
		),

		"ISTORE_1" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 1.
			ILOAD_1,
			ISTORE_1,
			ILOAD_1,
			IRETURN
		),

		"ISTORE_2" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 2.
			ILOAD_1,
			ISTORE_2,
			ILOAD_2,
			IRETURN
		),

		"ISTORE_3" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 3.
			ILOAD_1,
			ISTORE_2,
			ILOAD_1,
			ISTORE_3,
			ILOAD_3,
			IRETURN
		),

		"ISUB" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
			ICONST_3,
			ICONST_2,
			ISUB,
			IRETURN
		),

		"IUSHR" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 536870911.
			BIPUSH(-8),
			ICONST_3,
			IUSHR,
			IRETURN
		),

		"IXOR" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
			ICONST_4,
			ICONST_5,
			IXOR,
			IRETURN
		),

		"JSR" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 2
			ICONST_1,
			JSR("5"),
			IRETURN,
			"5" @: ASTORE_1,
			ICONST_1,
			IADD,
			RET(1)
		),

		"JSR_W" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 2
			ICONST_1,
			JSR_W("7"),
			IRETURN,
			"7" @: ASTORE_1,
			ICONST_1,
			IADD,
			RET(1)
		),

		"L2D" -> (METHOD_SELECTOR :: $[Long] -> $[Double])( // Returns the given argument converted to another type.
			LLOAD_1,
			L2D,
			DRETURN
		),

		"L2F" -> (METHOD_SELECTOR :: $[Long] -> $[Float])( // Returns the given argument converted to another type.
			LLOAD_1,
			L2F,
			FRETURN
		),

		"L2I" -> (METHOD_SELECTOR :: $[Long] -> $[Int])( // Returns the given argument converted to another type.
			LLOAD_1,
			L2I,
			IRETURN
		),

		"LADD" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 5L.
			LDC2_W(3L),
			LDC2_W(2L),
			LADD,
			LRETURN
		),

		"LALOAD" -> (METHOD_SELECTOR :: $[Array[Long]] -> $[Long])( // Returns the first long of the given array.
			ALOAD_1,
			ICONST_0,
			LALOAD,
			LRETURN
		),

		"LAND" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 4.
			LDC2_W(5L),
			LDC2_W(4L),
			LAND,
			LRETURN
		),

		"LASTORE" -> (METHOD_SELECTOR :: $[Long] -> $[Array[Long]] -> $[Unit])( // Stores the given long as the first element of the given array.
			ALOAD_3,
			ICONST_0,
			LLOAD_1,
			LASTORE,
			RETURN
		),

		"LCMP" -> (METHOD_SELECTOR :: $[Long] -> $[Long] -> $[Int])( // Compares the received longs.
			LLOAD_1,
			LLOAD_3,
			LCMP,
			IRETURN
		),

		"LCONST_0" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 0L.
			LCONST_0,
			LRETURN
		),

		"LCONST_1" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L.
			LCONST_1,
			LRETURN
		),

		"LDC" -> (METHOD_SELECTOR :: () -> $[Char])( // Returns 'o'.
			LDC("Hello"),
			LDC(1),
			LDC(3.0F),
			F2I,
			IADD,
			INVOKEVIRTUAL($[String] >> "charAt" :: $[Int] -> $[Char]),
			IRETURN
		),

		"LDC_W" -> (METHOD_SELECTOR :: () -> $[Char])( // Returns 'o'.
			LDC_W("Hello"),
			LDC_W(1),
			LDC_W(3.0F),
			F2I,
			IADD,
			INVOKEVIRTUAL($[String] >> "charAt" :: $[Int] -> $[Char]),
			IRETURN
		),

		"LDC2_W" -> (METHOD_SELECTOR :: () -> $[Double])( // Returns 7.5.
			LDC2_W(5L),
			L2D,
			LDC2_W(2.5),
			DADD,
			DRETURN
		),

		"LDIV" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 2L.
			LDC2_W(5L),
			LDC2_W(2L),
			LDIV,
			LRETURN
		),

		"LLOAD" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
			LLOAD(1),
			LRETURN
		),

		"LLOAD_0" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
			LLOAD_0,
			LRETURN
		).as(Static),

		"LLOAD_1" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
			LLOAD_1,
			LRETURN
		),

		"LLOAD_2" -> (METHOD_SELECTOR :: $[Int] -> $[Long] -> $[Long])( // Returns the given long.
			LLOAD_2,
			LRETURN
		),

		"LLOAD_3" -> (METHOD_SELECTOR :: $[Double] -> $[Long] -> $[Long])( // Returns the given long.
			LLOAD_3,
			LRETURN
		),

		"LMUL" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 6L.
			LDC2_W(3L),
			LDC2_W(2L),
			LMUL,
			LRETURN
		),

		"LNEG" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns -3L.
			LDC2_W(3L),
			LNEG,
			LRETURN
		),

		"LOOKUPSWITCH" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // If arg is between 2 and 4 returns itself if odd and it's half if even, otherwise returns 0.
			ILOAD_1,
			LOOKUPSWITCH("42", (2, "36"), (3, "40"), (4, "36")),

			"36" @: ILOAD_1,
			ICONST_2,
			IDIV,
			IRETURN,

			"40" @: ILOAD_1,
			IRETURN,

			"42" @: ICONST_0,
			IRETURN
		),

		"LOR" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 5.
			LCONST_1,
			LDC2_W(4L),
			LOR,
			LRETURN
		),

		"LREM" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 2L.
			LDC2_W(5L),
			LDC2_W(2L),
			LREM,
			LRETURN
		),

		"LRETURN" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
			LLOAD_1,
			LRETURN
		),

		"LSHL" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns -8L.
			LDC2_W(-1L),
			ICONST_3,
			LSHL,
			LRETURN
		),

		"LSHR" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns -1L.
			LDC2_W(-8L),
			ICONST_3,
			LSHR,
			LRETURN
		),

		"LSTORE" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long after storing it in the local 3.
			LLOAD_1,
			LSTORE(3),
			LLOAD(3),
			LRETURN
		),

		"LSTORE_0" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L after storing it in the local 0.
			LCONST_1,
			LSTORE_0,
			LLOAD_0,
			LRETURN
		).as(Static),

		"LSTORE_1" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long after storing it in the local 1.
			LLOAD_1,
			LSTORE_1,
			LLOAD_1,
			LRETURN
		),

		"LSTORE_2" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns 1L after storing it in the local 2.
			LCONST_1,
			LSTORE_2,
			LLOAD_2,
			LRETURN
		).as(Static),

		"LSTORE_3" -> (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long after storing it in the local 3.
			LLOAD_1,
			LSTORE_3,
			LLOAD_3,
			LRETURN
		),

		"LSUB" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L.
			LDC2_W(3L),
			LDC2_W(2L),
			LSUB,
			LRETURN
		),

		"LUSHR" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 2305843009213693951L.
			LDC2_W(-8L),
			ICONST_3,
			LUSHR,
			LRETURN
		),

		"LXOR" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L.
			LDC2_W(4L),
			LDC2_W(5L),
			LXOR,
			LRETURN
		),

		"MONITORENTER" -> (METHOD_SELECTOR :: $[Array[Object]] -> $[Unit])( // Removes the first element received array thread safely.
			ALOAD_1,
			MONITORENTER,
			ALOAD_1,
			DUP,
			ICONST_0,
			ACONST_NULL,
			AASTORE,
			MONITOREXIT,
			RETURN
		),

		"MONITOREXIT" -> (METHOD_SELECTOR :: $[Array[Object]] -> $[Unit])( // Removes the first element received array thread safely.
			ALOAD_1,
			MONITORENTER,
			ALOAD_1,
			DUP,
			ICONST_0,
			ACONST_NULL,
			AASTORE,
			MONITOREXIT,
			RETURN
		),

		"MULTIANEWARRAY" -> (METHOD_SELECTOR :: () -> $[Array[Array[Object]]])( // Returns a new Object[3][]
			ICONST_3,
			MULTIANEWARRAY($[Object], 2, 1),
			ARETURN
		),

		"NEW" -> (METHOD_SELECTOR :: () -> $[Date])( // Returns a new Date instance
			NEW($[Date]),
			DUP,
			INVOKESPECIAL($[Date] >> Init()),
			ARETURN
		),

		"NEWARRAY" -> (METHOD_SELECTOR :: () -> $[Array[Int]])( // Returns a new int[2]
			ICONST_2,
			NEWARRAY($[Int]),
			ARETURN
		),

		"NOP" -> (METHOD_SELECTOR :: () -> $[Unit])( // Does nothing.
			NOP,
			RETURN
		),

		"POP" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
			ICONST_5,
			ICONST_2,
			POP,
			IRETURN
		),

		"POP2" -> (METHOD_SELECTOR :: () -> $[Long])( // Returns 5L.
			LDC2_W(5L),
			LDC2_W(2L),
			POP2,
			LRETURN
		),

		"PUTFIELD" -> (METHOD_SELECTOR :: $[Point] -> $[Int] -> $[Unit])( // Sets the target object field to the given value
			ALOAD_1,
			ILOAD_2,
			PUTFIELD($[Point] >> "x" :: $[Int]),
			RETURN
		),

		"PUTSTATIC" -> (METHOD_SELECTOR :: $[Int] -> $[Unit])( // Sets the ATT static field to the given value
			ILOAD_1,
			PUTSTATIC($("Bleh") >> "ATT" :: $[Int]),
			RETURN
		),

		"RET" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns 2.
			ICONST_1,
			JSR("5"),
			IRETURN,
			"5" @: ASTORE_1,
			ICONST_1,
			IADD,
			RET(1)
		),

		"RETURN" -> (METHOD_SELECTOR :: () -> $[Unit])( // Does nothing.
			RETURN
		),

		"SALOAD" -> (METHOD_SELECTOR :: $[Array[Short]] -> $[Short])( // Returns the first short of the given array.
			ALOAD_1,
			ICONST_0,
			SALOAD,
			IRETURN
		),

		"SASTORE" -> (METHOD_SELECTOR :: $[Short] -> $[Array[Short]] -> $[Unit])( // Stores the given short as the first element of the given array.
			ALOAD_2,
			ICONST_0,
			ILOAD_1,
			SASTORE,
			RETURN
		),

		"SIPUSH" -> (METHOD_SELECTOR :: () -> $[Byte])( // Returns 5.
			SIPUSH(5),
			IRETURN
		),

		"SWAP" -> (METHOD_SELECTOR :: () -> $[Int])( // Returns -1.
			ICONST_3,
			ICONST_2,
			SWAP,
			ISUB,
			IRETURN
		),

		"TABLESWITCH" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // If arg is between 2 and 4 returns itself if odd and it's half if even, otherwise returns 0.
			ILOAD_1,
			TABLESWITCH(2, "34", "28", "32", "28"),

			"28" @: ILOAD_1,
			ICONST_2,
			IDIV,
			IRETURN,

			"32" @: ILOAD_1,
			IRETURN,

			"34" @: ICONST_0,
			IRETURN
		),

		"WIDE" -> (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int increased by 1000 after storing it in the local 300.
			ILOAD_1,
			WIDE(ISTORE(300)),
			WIDE(IINC(300, 1000)),
			WIDE(ILOAD(300)),
			IRETURN
		)
	)

	def classFor(instruction: String) = CONTEXT_TYPE.let{ it =>
		if (List("RET", "JSR", "JSR_W") contains instruction) it.version = Java_6
		it += methods(instruction)
	}

	def of(instruction: String) = classFor(instruction).methods.find(_.signature.selector == METHOD_SELECTOR).get
}