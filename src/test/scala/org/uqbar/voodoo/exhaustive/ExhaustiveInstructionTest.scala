package org.uqbar.voodoo.exhaustive

import java.lang.invoke.CallSite
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodType
import java.util.ArrayList
import java.util.Arrays
import java.util.Collection
import java.util.HashSet

import scala.reflect.runtime.universe

import org.scalatest.FunSuite
import org.uqbar.voodoo.builder.auxiliars.Bleh
import org.uqbar.voodoo.builder.auxiliars.CustomBootstrap
import org.uqbar.voodoo.model.$
import org.uqbar.voodoo.model.AALOAD
import org.uqbar.voodoo.model.AASTORE
import org.uqbar.voodoo.model.ACONST_NULL
import org.uqbar.voodoo.model.ALOAD
import org.uqbar.voodoo.model.ALOAD_0
import org.uqbar.voodoo.model.ALOAD_1
import org.uqbar.voodoo.model.ALOAD_2
import org.uqbar.voodoo.model.ALOAD_3
import org.uqbar.voodoo.model.ANEWARRAY
import org.uqbar.voodoo.model.ARETURN
import org.uqbar.voodoo.model.ARRAYLENGTH
import org.uqbar.voodoo.model.ASTORE
import org.uqbar.voodoo.model.ASTORE_0
import org.uqbar.voodoo.model.ASTORE_1
import org.uqbar.voodoo.model.ASTORE_2
import org.uqbar.voodoo.model.ASTORE_3
import org.uqbar.voodoo.model.ATHROW
import org.uqbar.voodoo.model.BALOAD
import org.uqbar.voodoo.model.BASTORE
import org.uqbar.voodoo.model.BIPUSH
import org.uqbar.voodoo.model.CALOAD
import org.uqbar.voodoo.model.CASTORE
import org.uqbar.voodoo.model.CHECKCAST
import org.uqbar.voodoo.model.Class
import org.uqbar.voodoo.model.ClassVersion.Java_6
import org.uqbar.voodoo.model.D2F
import org.uqbar.voodoo.model.D2I
import org.uqbar.voodoo.model.D2L
import org.uqbar.voodoo.model.DADD
import org.uqbar.voodoo.model.DALOAD
import org.uqbar.voodoo.model.DASTORE
import org.uqbar.voodoo.model.DCMPG
import org.uqbar.voodoo.model.DCMPL
import org.uqbar.voodoo.model.DCONST_0
import org.uqbar.voodoo.model.DCONST_1
import org.uqbar.voodoo.model.DDIV
import org.uqbar.voodoo.model.DLOAD
import org.uqbar.voodoo.model.DLOAD_0
import org.uqbar.voodoo.model.DLOAD_1
import org.uqbar.voodoo.model.DLOAD_2
import org.uqbar.voodoo.model.DLOAD_3
import org.uqbar.voodoo.model.DMUL
import org.uqbar.voodoo.model.DNEG
import org.uqbar.voodoo.model.DREM
import org.uqbar.voodoo.model.DRETURN
import org.uqbar.voodoo.model.DSTORE
import org.uqbar.voodoo.model.DSTORE_0
import org.uqbar.voodoo.model.DSTORE_1
import org.uqbar.voodoo.model.DSTORE_2
import org.uqbar.voodoo.model.DSTORE_3
import org.uqbar.voodoo.model.DSUB
import org.uqbar.voodoo.model.DUP
import org.uqbar.voodoo.model.DUP2
import org.uqbar.voodoo.model.DUP2_X1
import org.uqbar.voodoo.model.DUP2_X2
import org.uqbar.voodoo.model.DUP_X1
import org.uqbar.voodoo.model.DUP_X2
import org.uqbar.voodoo.model.F2D
import org.uqbar.voodoo.model.F2I
import org.uqbar.voodoo.model.F2L
import org.uqbar.voodoo.model.FADD
import org.uqbar.voodoo.model.FALOAD
import org.uqbar.voodoo.model.FASTORE
import org.uqbar.voodoo.model.FCMPG
import org.uqbar.voodoo.model.FCMPL
import org.uqbar.voodoo.model.FCONST_0
import org.uqbar.voodoo.model.FCONST_1
import org.uqbar.voodoo.model.FCONST_2
import org.uqbar.voodoo.model.FDIV
import org.uqbar.voodoo.model.FLOAD
import org.uqbar.voodoo.model.FLOAD_0
import org.uqbar.voodoo.model.FLOAD_1
import org.uqbar.voodoo.model.FLOAD_2
import org.uqbar.voodoo.model.FLOAD_3
import org.uqbar.voodoo.model.FMUL
import org.uqbar.voodoo.model.FNEG
import org.uqbar.voodoo.model.FREM
import org.uqbar.voodoo.model.FRETURN
import org.uqbar.voodoo.model.FSTORE
import org.uqbar.voodoo.model.FSTORE_0
import org.uqbar.voodoo.model.FSTORE_1
import org.uqbar.voodoo.model.FSTORE_2
import org.uqbar.voodoo.model.FSTORE_3
import org.uqbar.voodoo.model.FSUB
import org.uqbar.voodoo.model.GETFIELD
import org.uqbar.voodoo.model.GETSTATIC
import org.uqbar.voodoo.model.GOTO
import org.uqbar.voodoo.model.GOTO_W
import org.uqbar.voodoo.model.I2B
import org.uqbar.voodoo.model.I2C
import org.uqbar.voodoo.model.I2D
import org.uqbar.voodoo.model.I2F
import org.uqbar.voodoo.model.I2L
import org.uqbar.voodoo.model.I2S
import org.uqbar.voodoo.model.IADD
import org.uqbar.voodoo.model.IALOAD
import org.uqbar.voodoo.model.IAND
import org.uqbar.voodoo.model.IASTORE
import org.uqbar.voodoo.model.ICONST_0
import org.uqbar.voodoo.model.ICONST_1
import org.uqbar.voodoo.model.ICONST_2
import org.uqbar.voodoo.model.ICONST_3
import org.uqbar.voodoo.model.ICONST_4
import org.uqbar.voodoo.model.ICONST_5
import org.uqbar.voodoo.model.ICONST_M1
import org.uqbar.voodoo.model.IDIV
import org.uqbar.voodoo.model.IFEQ
import org.uqbar.voodoo.model.IFGE
import org.uqbar.voodoo.model.IFGT
import org.uqbar.voodoo.model.IFLE
import org.uqbar.voodoo.model.IFLT
import org.uqbar.voodoo.model.IFNE
import org.uqbar.voodoo.model.IFNONNULL
import org.uqbar.voodoo.model.IFNULL
import org.uqbar.voodoo.model.IF_ACMPEQ
import org.uqbar.voodoo.model.IF_ACMPNE
import org.uqbar.voodoo.model.IF_ICMPEQ
import org.uqbar.voodoo.model.IF_ICMPGE
import org.uqbar.voodoo.model.IF_ICMPGT
import org.uqbar.voodoo.model.IF_ICMPLE
import org.uqbar.voodoo.model.IF_ICMPLT
import org.uqbar.voodoo.model.IF_ICMPNE
import org.uqbar.voodoo.model.IINC
import org.uqbar.voodoo.model.ILOAD
import org.uqbar.voodoo.model.ILOAD_0
import org.uqbar.voodoo.model.ILOAD_1
import org.uqbar.voodoo.model.ILOAD_2
import org.uqbar.voodoo.model.ILOAD_3
import org.uqbar.voodoo.model.IMUL
import org.uqbar.voodoo.model.INEG
import org.uqbar.voodoo.model.INSTANCEOF
import org.uqbar.voodoo.model.INVOKEDYNAMIC
import org.uqbar.voodoo.model.INVOKEINTERFACE
import org.uqbar.voodoo.model.INVOKESPECIAL
import org.uqbar.voodoo.model.INVOKESTATIC
import org.uqbar.voodoo.model.INVOKEVIRTUAL
import org.uqbar.voodoo.model.IOR
import org.uqbar.voodoo.model.IREM
import org.uqbar.voodoo.model.IRETURN
import org.uqbar.voodoo.model.ISHL
import org.uqbar.voodoo.model.ISHR
import org.uqbar.voodoo.model.ISTORE
import org.uqbar.voodoo.model.ISTORE_0
import org.uqbar.voodoo.model.ISTORE_1
import org.uqbar.voodoo.model.ISTORE_2
import org.uqbar.voodoo.model.ISTORE_3
import org.uqbar.voodoo.model.ISUB
import org.uqbar.voodoo.model.IUSHR
import org.uqbar.voodoo.model.IXOR
import org.uqbar.voodoo.model.Init
import org.uqbar.voodoo.model.JSR
import org.uqbar.voodoo.model.JSR_W
import org.uqbar.voodoo.model.L2D
import org.uqbar.voodoo.model.L2F
import org.uqbar.voodoo.model.L2I
import org.uqbar.voodoo.model.LADD
import org.uqbar.voodoo.model.LALOAD
import org.uqbar.voodoo.model.LAND
import org.uqbar.voodoo.model.LASTORE
import org.uqbar.voodoo.model.LCMP
import org.uqbar.voodoo.model.LCONST_0
import org.uqbar.voodoo.model.LCONST_1
import org.uqbar.voodoo.model.LDC
import org.uqbar.voodoo.model.LDC2_W
import org.uqbar.voodoo.model.LDC_W
import org.uqbar.voodoo.model.LDIV
import org.uqbar.voodoo.model.LLOAD
import org.uqbar.voodoo.model.LLOAD_0
import org.uqbar.voodoo.model.LLOAD_1
import org.uqbar.voodoo.model.LLOAD_2
import org.uqbar.voodoo.model.LLOAD_3
import org.uqbar.voodoo.model.LMUL
import org.uqbar.voodoo.model.LNEG
import org.uqbar.voodoo.model.LOOKUPSWITCH
import org.uqbar.voodoo.model.LOR
import org.uqbar.voodoo.model.LREM
import org.uqbar.voodoo.model.LRETURN
import org.uqbar.voodoo.model.LSHL
import org.uqbar.voodoo.model.LSHR
import org.uqbar.voodoo.model.LSTORE
import org.uqbar.voodoo.model.LSTORE_0
import org.uqbar.voodoo.model.LSTORE_1
import org.uqbar.voodoo.model.LSTORE_2
import org.uqbar.voodoo.model.LSTORE_3
import org.uqbar.voodoo.model.LSUB
import org.uqbar.voodoo.model.LUSHR
import org.uqbar.voodoo.model.LXOR
import org.uqbar.voodoo.model.MONITORENTER
import org.uqbar.voodoo.model.MONITOREXIT
import org.uqbar.voodoo.model.MULTIANEWARRAY
import org.uqbar.voodoo.model.MethodFromSignature
import org.uqbar.voodoo.model.MethodModifier.Static
import org.uqbar.voodoo.model.NEW
import org.uqbar.voodoo.model.NEWARRAY
import org.uqbar.voodoo.model.NOP
import org.uqbar.voodoo.model.POP
import org.uqbar.voodoo.model.POP2
import org.uqbar.voodoo.model.PUTFIELD
import org.uqbar.voodoo.model.PUTSTATIC
import org.uqbar.voodoo.model.RET
import org.uqbar.voodoo.model.RETURN
import org.uqbar.voodoo.model.SALOAD
import org.uqbar.voodoo.model.SASTORE
import org.uqbar.voodoo.model.SIPUSH
import org.uqbar.voodoo.model.SWAP
import org.uqbar.voodoo.model.SlotRef_to_Bootstrap
import org.uqbar.voodoo.model.TABLESWITCH
import org.uqbar.voodoo.model.UnitExt
import org.uqbar.voodoo.model.WIDE
import org.uqbar.voodoo.mutator.MutableType

//TODO: Testear el byteSize
//TODO: Testear el stackMapTable / maxStack

class Foo

trait ExhaustiveInstructionTest extends FunSuite {

	protected val CONTEXT_TYPE = $("TestClass")
	protected val METHOD_SELECTOR = "test"

	testInstruction("AALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Object]] -> $[Object])( // Returns the first object of the given array.
				ALOAD_1,
				ICONST_0,
				AALOAD,
				ARETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array[Object](new Foo)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("AASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Array[Object]] -> $[Unit])( // Stores the given object as the first element of the given array.
				ALOAD_2,
				ICONST_0,
				ALOAD_1,
				AASTORE,
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = new Bleh
			val arg2 = new Array(1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("ACONST_NULL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Object])( // Returns null.
				ACONST_NULL,
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == null })
		}
	)

	testInstruction("ALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object.
				ALOAD(1),
				ARETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ALOAD_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object.
				ALOAD_0,
				ARETURN
			).as(Static)
		},
		maxLocals = 1,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ALOAD_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object.
				ALOAD_1,
				ARETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ALOAD_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Object])( // Returns the second given object.
				ALOAD_2,
				ARETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,

		runs = {
			val arg1 = null
			val arg2 = new Bleh
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("ALOAD_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Object] -> $[Object])( // Returns the third given object.
				ALOAD_3,
				ARETURN
			)
		},
		maxLocals = 4,
		maxStack = 1,

		runs = {
			val arg1 = null
			val arg2 = null
			val arg3 = new Bleh
			Seq(Run(arg1, arg2, arg3){ _ == arg3 })
		}
	)

	testInstruction("ANEWARRAY")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Array[Object]])( // Returns an empty Object[] of length 5.
				ICONST_5,
				ANEWARRAY($[Object]),
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,

		runs = {
			Seq(Run(){ case a: Array[AnyRef] ⇒ Arrays.equals(a, new Array[AnyRef](5)) })
		}
	)

	testInstruction("ARETURN")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Object])( // Returns null.
				ACONST_NULL,
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,

		runs = {
			Seq(Run(){ _ == null })
		}
	)

	testInstruction("ARRAYLENGTH")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Object]] -> $[Int])( // Returns the length of the given array.
				ALOAD_1,
				ARRAYLENGTH,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = new Array(5)
			Seq(Run(arg){ _ == 5 })
		}
	)

	testInstruction("ASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 2.
				ALOAD_1,
				ASTORE(2),
				ALOAD(2),
				ARETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 0.
				ALOAD_1,
				ASTORE_0,
				ALOAD_0,
				ARETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 1.
				ALOAD_0,
				ASTORE_1,
				ALOAD_1,
				ARETURN
			).as(Static)
		},
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 2.
				ALOAD_1,
				ASTORE_2,
				ALOAD_2,
				ARETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object])( // Returns the given object after storing it in the local 3.
				ALOAD_1,
				ASTORE_2,
				ALOAD_1,
				ASTORE_3,
				ALOAD_3,
				ARETURN
			)
		},
		maxLocals = 4,
		maxStack = 1,

		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ATHROW")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Unit])( // Throws ArithmeticException.
				NEW($[ArithmeticException]),
				DUP,
				INVOKESPECIAL($[ArithmeticException] >> Init()),
				ATHROW
			)
		},
		maxLocals = 1,
		maxStack = 2,

		runs = {
			Seq(Run(){ _.isInstanceOf[ArithmeticException] })
		}
	)

	testInstruction("BALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Boolean]] -> $[Boolean])( // Returns the first Boolean of the given array.
				ALOAD_1,
				ICONST_0,
				BALOAD,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,

		runs = {
			val arg = Array(false)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("BASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Boolean] -> $[Array[Boolean]] -> $[Unit])( // Stores the given Boolean as the first element of the given array.
				ALOAD_2,
				ICONST_0,
				ILOAD_1,
				BASTORE,
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 3,

		runs = {
			val arg1 = true
			val arg2 = new Array[Boolean](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("BIPUSH")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Byte])( // Returns 5.
				BIPUSH(5),
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("CALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Char]] -> $[Char])( // Returns the first char of the given array.
				ALOAD_1,
				ICONST_0,
				CALOAD,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array('c')
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("CASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Char] -> $[Array[Char]] -> $[Unit])( // Stores the given char as the first element of the given array.
				ALOAD_2,
				ICONST_0,
				ILOAD_1,
				CASTORE,
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = 'c'
			val arg2 = new Array[Char](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("CHECKCAST")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Foo])( // Returns the given object casted to another type.
				ALOAD_1,
				CHECKCAST($[Foo]),
				ARETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = new Foo
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("D2F")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Float])( // Returns the given argument converted to another type.
				DLOAD_1,
				D2F,
				FRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5.0
			Seq(Run(arg){ _ == 5.0f })
		}
	)

	testInstruction("D2I")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Int])( // Returns the given argument converted to another type.
				DLOAD_1,
				D2I,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5.4
			Seq(Run(arg){ _ == 5 })
		}
	)

	testInstruction("D2L")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Long])( // Returns the given argument converted to another type.
				DLOAD_1,
				D2L,
				LRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5.4
			Seq(Run(arg){ _ == 5L })
		}
	)

	testInstruction("DADD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 5.0.
				LDC2_W(3.0),
				LDC2_W(2.0),
				DADD,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 5.0 })
		}
	)

	testInstruction("DALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Double]] -> $[Double])( // Returns the first double of the given array.
				ALOAD_1,
				ICONST_0,
				DALOAD,
				DRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3.7)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("DASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Array[Double]] -> $[Unit])( // Stores the given double as the first element of the given array.
				ALOAD_3,
				ICONST_0,
				DLOAD_1,
				DASTORE,
				RETURN
			)
		},
		maxLocals = 4,
		maxStack = 4,
		runs = {
			val arg1 = 3.7
			val arg2 = new Array[Double](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("DCMPG")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double] -> $[Int])( // Compares the received doubles. Answers 1 if either one is NaN.
				DLOAD_1,
				DLOAD_3,
				DCMPG,
				IRETURN
			)
		},
		maxLocals = 5,
		maxStack = 4,
		runs = {
			val arg1 = 3.7
			val arg2 = 2.5
			val arg3 = Double.NaN
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == -1 },
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg3){ _ == 1 },
				Run(arg3, arg1){ _ == 1 }
			)
		}
	)

	testInstruction("DCMPL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double] -> $[Int])( // Compares the received doubles. Answers -1 if either one is NaN.
				DLOAD_1,
				DLOAD_3,
				DCMPL,
				IRETURN
			)
		},
		maxLocals = 5,
		maxStack = 4,
		runs = {
			val arg1 = 3.7
			val arg2 = 2.5
			val arg3 = Double.NaN
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == -1 },
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg3){ _ == -1 },
				Run(arg3, arg1){ _ == -1 }
			)
		}
	)

	testInstruction("DCONST_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 0.0.
				DCONST_0,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 0.0 })
		}
	)

	testInstruction("DCONST_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0.
				DCONST_1,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1.0 })
		}
	)

	testInstruction("DDIV")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 2.5.
				LDC2_W(5.0),
				LDC2_W(2.0),
				DDIV,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 2.5 })
		}
	)

	testInstruction("DLOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
				DLOAD(1),
				DRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DLOAD_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
				DLOAD_0,
				DRETURN
			).as(Static)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DLOAD_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
				DLOAD_1,
				DRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DLOAD_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Double] -> $[Double])( // Returns the given double.
				DLOAD_2,
				DRETURN
			)
		},
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg1 = 0
			val arg2 = 2.7
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("DLOAD_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Double] -> $[Double])( // Returns the given double.
				DLOAD_3,
				DRETURN
			)
		},
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg1 = 0L
			val arg2 = 2.7
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("DMUL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 6.0.
				LDC2_W(3.0),
				LDC2_W(2.0),
				DMUL,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 6.0 })
		}
	)

	testInstruction("DNEG")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns -3.0.
				LDC2_W(3.0),
				DNEG,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -3 })
		}
	)

	testInstruction("DREM")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0.
				LDC2_W(5.0),
				LDC2_W(2.0),
				DREM,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("DRETURN")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double.
				DLOAD_1,
				DRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double after storing it in the local 3.
				DLOAD_1,
				DSTORE(3),
				DLOAD(3),
				DRETURN
			)
		},
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSTORE_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0 after storing it in the local 0.
				DCONST_1,
				DSTORE_0,
				DLOAD_0,
				DRETURN
			).as(Static)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1.0 })
		}
	)

	testInstruction("DSTORE_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double after storing it in the local 1.
				DLOAD_1,
				DSTORE_1,
				DLOAD_1,
				DRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSTORE_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns 1.0 after storing it in the local 2.
				DCONST_1,
				DSTORE_2,
				DLOAD_2,
				DRETURN
			).as(Static)
		},
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == 1.0 })
		}
	)

	testInstruction("DSTORE_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Double])( // Returns the given double after storing it in the local 3.
				DLOAD_1,
				DSTORE_3,
				DLOAD_3,
				DRETURN
			)
		},
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSUB")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 1.0.
				LDC2_W(3.0),
				LDC2_W(2.0),
				DSUB,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1.0 })
		}
	)

	testInstruction("DUP")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Subtracts the received int from itself and returns 0.
				ILOAD_1,
				DUP,
				ISUB,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 19
			Seq(Run(arg){ _ == 0 })
		}
	)

	testInstruction("DUP_X1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Subtracts 0 from the received int, and then subtracts it from the result. Returns the received int negated.
				ILOAD_1,
				ICONST_0,
				DUP_X1,
				ISUB,
				ISUB,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 3,
		runs = {
			val arg = 19
			Seq(Run(arg){ _ == -arg })
		}
	)

	testInstruction("DUP_X2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Subtracts from the receiver int the result of 1 minus subtracting it from 0. Returns -1.
				ICONST_1,
				ICONST_0,
				ILOAD_1,
				DUP_X2,
				ISUB,
				ISUB,
				ISUB,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 4,
		runs = {
			val arg = 19
			Seq(Run(arg){ _ == -1 })
		}
	)

	testInstruction("DUP2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Subtracts the received long from itself and returns 0L.
				LLOAD_1,
				DUP2,
				LSUB,
				LRETURN
			)
		},
		maxLocals = 3,
		maxStack = 4,
		runs = {
			val arg = 19L
			Seq(Run(arg){ _ == 0L })
		}
	)

	testInstruction("DUP2_X1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Long])( // Returns the received int converted to long and negated.
				ILOAD_1,
				LCONST_0,
				DUP2_X1,
				LSTORE_2,
				I2L,
				LLOAD_2,
				LSUB,
				LSUB,
				LRETURN
			)
		},
		maxLocals = 4,
		maxStack = 6,
		runs = {
			val arg = 1
			Seq(Run(arg){ _ == -1L })
		}
	)

	testInstruction("DUP2_X2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Subtracts 0L from the received long, and then subtracts it from the result. Returns the received long negated.
				LLOAD_1,
				LCONST_0,
				DUP2_X2,
				LSUB,
				LSUB,
				LRETURN
			)
		},
		maxLocals = 3,
		maxStack = 6,
		runs = {
			val arg = 19L
			Seq(Run(arg){ _ == -arg })
		}
	)

	testInstruction("F2D")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Double])( // Returns the given argument converted to another type.
				FLOAD_1,
				F2D,
				DRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5f
			Seq(Run(arg){ _ == 5.0 })
		}
	)

	testInstruction("F2I")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Int])( // Returns the given argument converted to another type.
				FLOAD_1,
				F2I,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 5.4f
			Seq(Run(arg){ _ == 5 })
		}
	)

	testInstruction("F2L")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Long])( // Returns the given argument converted to another type.
				FLOAD_1,
				F2L,
				LRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5.4f
			Seq(Run(arg){ _ == 5L })
		}
	)

	testInstruction("FADD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 5F.
				LDC_W(3.0F),
				LDC_W(2.0F),
				FADD,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5f })
		}
	)

	testInstruction("FALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Float]] -> $[Float])( // Returns the first float of the given array.
				ALOAD_1,
				ICONST_0,
				FALOAD,
				FRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3.5f)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("FASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Array[Float]] -> $[Unit])( // Stores the given float as the first element of the given array.
				ALOAD_2,
				ICONST_0,
				FLOAD_1,
				FASTORE,
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = 3.7f
			val arg2 = new Array[Float](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("FCMPG")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float] -> $[Int])( // Compares the received floats. Answers 1 if either one is NaN.
				FLOAD_1,
				FLOAD_2,
				FCMPG,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = 3.7f
			val arg2 = 2.5f
			val arg3 = Float.NaN
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == -1 },
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg3){ _ == 1 },
				Run(arg3, arg1){ _ == 1 }
			)
		}
	)

	testInstruction("FCMPL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float] -> $[Int])( // Compares the received floats. Answers 1 if either one is NaN.
				FLOAD_1,
				FLOAD_2,
				FCMPL,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = 3.7f
			val arg2 = 2.5f
			val arg3 = Float.NaN
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == -1 },
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg3){ _ == -1 },
				Run(arg3, arg1){ _ == -1 }
			)
		}
	)

	testInstruction("FCONST_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 0.0F.
				FCONST_0,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 0f })
		}
	)

	testInstruction("FCONST_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 1.0F.
				FCONST_1,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 1f })
		}
	)

	testInstruction("FCONST_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 2.0F.
				FCONST_2,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 2f })
		}
	)

	testInstruction("FDIV")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 2.5F.
				LDC_W(5.0F),
				LDC_W(2.0F),
				FDIV,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2.5f })
		}
	)

	testInstruction("FLOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
				FLOAD(1),
				FRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FLOAD_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
				FLOAD_0,
				FRETURN
			).as(Static)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FLOAD_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
				FLOAD_1,
				FRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FLOAD_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Float] -> $[Float])( // Returns the given float.
				FLOAD_2,
				FRETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 2.7f
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("FLOAD_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Float] -> $[Float])( // Returns the given float.
				FLOAD_3,
				FRETURN
			)
		},
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg1 = 3L
			val arg2 = 2.7f
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("FMUL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 6.0F.
				LDC_W(3.0F),
				LDC_W(2.0F),
				FMUL,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 6f })
		}
	)

	testInstruction("FNEG")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns -3.0F.
				LDC_W(3.0F),
				FNEG,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == -3.0f })
		}
	)

	testInstruction("FREM")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 1.0F.
				LDC_W(5.0F),
				LDC_W(2.0F),
				FREM,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1.0f })
		}
	)

	testInstruction("FRETURN")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float.
				FLOAD_1,
				FRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 2.
				FLOAD_1,
				FSTORE(2),
				FLOAD(2),
				FRETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 0.
				FLOAD_1,
				FSTORE_0,
				FLOAD_0,
				FRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 1.
				FLOAD_1,
				FSTORE_1,
				FLOAD_1,
				FRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 2.
				FLOAD_1,
				FSTORE_2,
				FLOAD_2,
				FRETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Float])( // Returns the given float after storing it in the local 3.
				FLOAD_1,
				FSTORE_2,
				FLOAD_1,
				FSTORE_3,
				FLOAD_3,
				FRETURN
			)
		},
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSUB")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Float])( // Returns 1F.
				LDC_W(3.0F),
				LDC_W(2.0F),
				FSUB,
				FRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1f })
		}
	)

	testInstruction("GETFIELD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Bleh] -> $[Int])( // Returns the target object field
				ALOAD_1,
				GETFIELD($[Bleh] >> "att" :: $[Int]),
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = new Bleh
			arg.att = 15
			Seq(Run(arg){ _ == arg.att })
		}
	)

	testInstruction("GETSTATIC")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns the ATT static field
				GETSTATIC($[Bleh] >> "ATT" :: $[Int]),
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Bleh.ATT = 15
			Seq(Run(){ _ == Bleh.ATT })
		}
	)

	testInstruction("GOTO")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
				GOTO("5"),
				"3" @: ICONST_0,
				IRETURN,

				"5" @: ICONST_0,
				ICONST_1,
				IF_ICMPEQ("3"),
				ICONST_5,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("GOTO_W")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
				GOTO_W("7"),
				"5" @: ICONST_0,
				IRETURN,

				"7" @: ICONST_0,
				ICONST_1,
				IF_ICMPEQ("5"),
				ICONST_5,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("I2B")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Byte])( // Returns the given argument converted to another type.
				ILOAD_1,
				I2B,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = -134
			Seq(Run(arg){ _ == 122 })
		}
	)

	testInstruction("I2C")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Char])( // Returns the given argument converted to another type.
				ILOAD_1,
				I2C,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 65600
			Seq(Run(arg){ _ == '@' })
		}
	)

	testInstruction("I2D")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Double])( // Returns the given argument converted to another type.
				ILOAD_1,
				I2D,
				DRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == 5.0 })
		}
	)

	testInstruction("I2F")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Float])( // Returns the given argument converted to another type.
				ILOAD_1,
				I2F,
				FRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == 5.0f })
		}
	)

	testInstruction("I2L")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Long])( // Returns the given argument converted to another type.
				ILOAD_1,
				I2L,
				LRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == 5L })
		}
	)

	testInstruction("I2S")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Short])( // Returns the given argument converted to another type.
				ILOAD_1,
				I2S,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = -40000
			Seq(Run(arg){ _ == 25536 })
		}
	)

	testInstruction("IADD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
				ICONST_3,
				ICONST_2,
				IADD,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("IALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Int]] -> $[Int])( // Returns the first int of the given array.
				ALOAD_1,
				ICONST_0,
				IALOAD,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("IAND")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 4.
				ICONST_5,
				ICONST_4,
				IAND,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 4 })
		}
	)

	testInstruction("IASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Array[Int]] -> $[Unit])( // Stores the given int as the first element of the given array.
				ALOAD_2,
				ICONST_0,
				ILOAD_1,
				IASTORE,
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = 0
			val arg2 = new Array[Int](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("ICONST_M1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns -1.
				ICONST_M1,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == -1 })
		}
	)

	testInstruction("ICONST_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 0.
				ICONST_0,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 0 })
		}
	)

	testInstruction("ICONST_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
				ICONST_1,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("ICONST_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 2.
				ICONST_2,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("ICONST_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 3.
				ICONST_3,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 3 })
		}
	)

	testInstruction("ICONST_4")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 4.
				ICONST_4,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 4 })
		}
	)

	testInstruction("ICONST_5")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
				ICONST_5,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("IDIV")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 2.
				ICONST_5,
				ICONST_2,
				IDIV,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("IF_ACMPEQ")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Int])( // Returns 1 if arguments are equal and 0 if they are not.
				ALOAD_1,
				ALOAD_2,
				IF_ACMPEQ("7"),
				ICONST_0,
				IRETURN,
				"7" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = Nil
			val arg2 = List(1)
			Seq(
				Run(arg1, arg1){ _ == 1 },
				Run(arg1, arg2){ _ == 0 }
			)
		}
	)

	testInstruction("IF_ACMPNE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Object] -> $[Int])( // Returns 0 if arguments are equal and 1 if they are not.
				ALOAD_1,
				ALOAD_2,
				IF_ACMPNE("7"),
				ICONST_0,
				IRETURN,
				"7" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = Nil
			val arg2 = List(1)
			Seq(
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg2){ _ == 1 }
			)
		}
	)

	testInstruction("IF_ICMPEQ")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if arguments are equal and 0 if they are not.
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
			)
		},
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg1){ _ == 1 },
				Run(arg1, arg2){ _ == 0 }
			)
		}
	)

	testInstruction("IF_ICMPNE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 0 if arguments are equal and 1 if they are not.
				ILOAD_1,
				ILOAD_2,
				IF_ICMPNE("7"),
				ICONST_0,
				IRETURN,
				"7" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg2){ _ == 1 }
			)
		}
	)

	testInstruction("IF_ICMPLT")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is lesser than the second argument and 0 if it is not.
				ILOAD_1,
				ILOAD_2,
				IF_ICMPLT("7"),
				ICONST_0,
				IRETURN,
				"7" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == 0 },
				Run(arg1, arg1){ _ == 0 }
			)
		}
	)

	testInstruction("IF_ICMPGE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is great or equal to the second argument and 0 if it is not.
				ILOAD_1,
				ILOAD_2,
				IF_ICMPGE("7"),
				ICONST_0,
				IRETURN,
				"7" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 0 },
				Run(arg2, arg1){ _ == 1 },
				Run(arg1, arg1){ _ == 1 }
			)
		}
	)

	testInstruction("IF_ICMPGT")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is greater than the second argument and 0 if it is not.
				ILOAD_1,
				ILOAD_2,
				IF_ICMPGT("7"),
				ICONST_0,
				IRETURN,
				"7" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 0 },
				Run(arg2, arg1){ _ == 1 },
				Run(arg1, arg1){ _ == 0 }
			)
		}
	)

	testInstruction("IF_ICMPLE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int] -> $[Int])( // Returns 1 if the first argument is less or equal to the second argument and 0 if it is not.
				ILOAD_1,
				ILOAD_2,
				IF_ICMPLE("7"),
				ICONST_0,
				IRETURN,
				"7" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == 0 },
				Run(arg1, arg1){ _ == 1 }
			)
		}
	)

	testInstruction("IFEQ")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is equal to 0 and 0 if not.
				ILOAD_1,
				IFEQ("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 7
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 }
			)
		}
	)

	testInstruction("IFNE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 0 if the argument is equal to 0 and 1 if not.
				ILOAD_1,
				IFNE("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 7
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 1 }
			)
		}
	)

	testInstruction("IFLT")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is less than to 0 and 0 if not.
				ILOAD_1,
				IFLT("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 0 },
				Run(arg3){ _ == 1 }
			)
		}
	)

	testInstruction("IFGE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is great or equal to 0 and 0 if not.
				ILOAD_1,
				IFGE("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 1 },
				Run(arg3){ _ == 0 }
			)
		}
	)

	testInstruction("IFGT")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is greater than 0 and 0 if not.
				ILOAD_1,
				IFGT("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 1 },
				Run(arg3){ _ == 0 }
			)

		}
	)

	testInstruction("IFLE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns 1 if the argument is less or equal to 0 and 0 if not.
				ILOAD_1,
				IFLE("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 },
				Run(arg3){ _ == 1 }
			)
		}
	)

	testInstruction("IFNONNULL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Int])( // Returns 1 if the argument is not null and 0 if it is.
				ALOAD_1,
				IFNONNULL("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = null
			val arg2 = Nil
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 1 }
			)
		}
	)

	testInstruction("IFNULL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Int])( // Returns 0 if the argument is not null and 1 if it is.
				ALOAD_1,
				IFNULL("6"),
				ICONST_0,
				IRETURN,
				"6" @: ICONST_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = null
			val arg2 = Nil
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 }
			)
		}
	)

	testInstruction("IINC")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
				ICONST_0,
				ISTORE_1,
				IINC(1, 5),
				ILOAD_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("ILOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
				ILOAD(1),
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ILOAD_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
				ILOAD_0,
				IRETURN
			).as(Static)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ILOAD_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
				ILOAD_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ILOAD_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Float] -> $[Int] -> $[Int])( // Returns the given int.
				ILOAD_2,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg1 = 0f
			val arg2 = 2
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("ILOAD_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Int] -> $[Int])( // Returns the given int.
				ILOAD_3,
				IRETURN
			)
		},
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg1 = 0L
			val arg2 = 2
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("IMUL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 6.
				ICONST_3,
				ICONST_2,
				IMUL,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 6 })
		}
	)

	testInstruction("INEG")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns -3.
				ICONST_3,
				INEG,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == -3 })
		}
	)

	testInstruction("INSTANCEOF")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Object] -> $[Int])( // Returns 1 if the received object is intance of Bleh and 0 if it isn't.
				ALOAD_1,
				INSTANCEOF($[Bleh]),
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = new Bleh
			val arg2 = Array()
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 }
			)
		}
	)

	testInstruction("INVOKEDYNAMIC")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Integer] -> $[Integer] -> $[Integer])( // Adds the given Integers.
				ALOAD_1,
				ALOAD_2,
				//TODO: El tipo del bootstrap es siempre el mismo. No ponerlo.
				INVOKEDYNAMIC(SlotRef_to_Bootstrap(INVOKESTATIC($[CustomBootstrap] >> "dispatch" :: $[Lookup] -> $[String] -> $[MethodType] -> $[CallSite])), "adder" :: $[Integer] -> $[Integer] -> $[Integer]),
				ARETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1: Integer = 5
			val arg2: Integer = 4
			Seq(Run(arg1, arg2){ _ == (9: Integer) })
		}
	)

	testInstruction("INVOKEINTERFACE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Collection[_]] -> $[Object] -> $[Unit])( // Adds the given object to the given collection.
				ALOAD_1,
				ALOAD_2,
				INVOKEINTERFACE($[Collection[_]] >>> "add" :: $[Object] -> $[Boolean]),
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = new HashSet
			val arg2 = new Bleh
			Seq(Run(arg1, arg2){ _ ⇒ arg1 contains arg2 })
		}
	)

	testInstruction("INVOKESPECIAL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Bleh])( // Returns a new Bleh instance
				NEW($[Bleh]),
				DUP,
				INVOKESPECIAL($[Bleh] >> Init()),
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _.isInstanceOf[Bleh] })
		}
	)

	testInstruction("INVOKESTATIC")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[String])( // Returns "0"
				ICONST_0,
				INVOKESTATIC($[Integer] >> "toString" :: $[Int] -> $[String]),
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == "0" })
		}
	)

	testInstruction("INVOKEVIRTUAL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Bleh] -> $[String])( // Returns the result of sending the bleh message to the received object.
				ALOAD_1,
				INVOKEVIRTUAL($[Bleh] >> "bleh" :: () -> $[String]),
				ARETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = new Bleh
			Seq(Run(arg){ _ == arg.bleh })
		}
	)

	testInstruction("IOR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
				ICONST_1,
				ICONST_4,
				IOR,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("IREM")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
				ICONST_5,
				ICONST_2,
				IREM,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("IRETURN")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int.
				ILOAD_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISHL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns -8.
				ICONST_M1,
				ICONST_3,
				ISHL,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -8 })
		}
	)

	testInstruction("ISHR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns -1.
				BIPUSH(-8),
				ICONST_3,
				ISHR,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -1 })
		}
	)

	testInstruction("ISTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 2.
				ILOAD_1,
				ISTORE(3),
				ILOAD(3),
				IRETURN
			)
		},
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISTORE_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 0.
				ILOAD_1,
				ISTORE_0,
				ILOAD_0,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == 3 })
		}
	)

	testInstruction("ISTORE_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 1.
				ILOAD_1,
				ISTORE_1,
				ILOAD_1,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISTORE_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 2.
				ILOAD_1,
				ISTORE_2,
				ILOAD_2,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISTORE_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int after storing it in the local 3.
				ILOAD_1,
				ISTORE_2,
				ILOAD_1,
				ISTORE_3,
				ILOAD_3,
				IRETURN
			)
		},
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISUB")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
				ICONST_3,
				ICONST_2,
				ISUB,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("IUSHR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 536870911.
				BIPUSH(-8),
				ICONST_3,
				IUSHR,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 536870911 })
		}
	)

	testInstruction("IXOR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 1.
				ICONST_4,
				ICONST_5,
				IXOR,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("JSR")(
		context = CONTEXT_TYPE let { it =>
			it.version = Java_6

			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 2
				ICONST_1,
				JSR("5"),
				IRETURN,
				"5" @: ASTORE_1,
				ICONST_1,
				IADD,
				RET(1)
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("JSR_W")(
		context = CONTEXT_TYPE let { it =>
			it.version = Java_6

			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 2
				ICONST_1,
				JSR_W("7"),
				IRETURN,
				"7" @: ASTORE_1,
				ICONST_1,
				IADD,
				RET(1)
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("L2D")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Double])( // Returns the given argument converted to another type.
				LLOAD_1,
				L2D,
				DRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 15L
			Seq(Run(arg){ _ == 15.0 })
		}
	)

	testInstruction("L2F")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Float])( // Returns the given argument converted to another type.
				LLOAD_1,
				L2F,
				FRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 15L
			Seq(Run(arg){ _ == 15f })
		}
	)

	testInstruction("L2I")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Int])( // Returns the given argument converted to another type.
				LLOAD_1,
				L2I,
				IRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = -4294967297L
			Seq(Run(arg){ _ == -1 })
		}
	)

	testInstruction("LADD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 5L.
				LDC2_W(3L),
				LDC2_W(2L),
				LADD,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 5L })
		}
	)

	testInstruction("LALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Long]] -> $[Long])( // Returns the first long of the given array.
				ALOAD_1,
				ICONST_0,
				LALOAD,
				LRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3L)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("LAND")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 4.
				LDC2_W(5L),
				LDC2_W(4L),
				LAND,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 4L })
		}
	)

	testInstruction("LASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Array[Long]] -> $[Unit])( // Stores the given long as the first element of the given array.
				ALOAD_3,
				ICONST_0,
				LLOAD_1,
				LASTORE,
				RETURN
			)
		},
		maxLocals = 4,
		maxStack = 4,
		runs = {
			val arg1 = 3L
			val arg2 = new Array[Long](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("LCMP")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long] -> $[Int])( // Compares the received longs.
				LLOAD_1,
				LLOAD_3,
				LCMP,
				IRETURN
			)
		},
		maxLocals = 5,
		maxStack = 4,
		runs = {
			val arg1 = 3L
			val arg2 = 2L
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == -1 },
				Run(arg1, arg1){ _ == 0 }
			)
		}
	)

	testInstruction("LCONST_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 0L.
				LCONST_0,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 0L })
		}
	)

	testInstruction("LCONST_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L.
				LCONST_1,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("LDC")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Char])( // Returns 'o'.
				LDC("Hello"),
				LDC(1),
				LDC(3.0F),
				F2I,
				IADD,
				INVOKEVIRTUAL($[String] >> "charAt" :: $[Int] -> $[Char]),
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == 'o' })
		}
	)
	testInstruction("LDC_W")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Char])( // Returns 'o'.
				LDC_W("Hello"),
				LDC_W(1),
				LDC_W(3.0F),
				F2I,
				IADD,
				INVOKEVIRTUAL($[String] >> "charAt" :: $[Int] -> $[Char]),
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == 'o' })
		}
	)

	testInstruction("LDC2_W")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Double])( // Returns 7.5.
				LDC2_W(5L),
				L2D,
				LDC2_W(2.5),
				DADD,
				DRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 7.5 })
		}
	)

	testInstruction("LDIV")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 2L.
				LDC2_W(5L),
				LDC2_W(2L),
				LDIV,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 2L })
		}
	)

	testInstruction("LLOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
				LLOAD(1),
				LRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LLOAD_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
				LLOAD_0,
				LRETURN
			).as(Static)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LLOAD_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
				LLOAD_1,
				LRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LLOAD_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Long] -> $[Long])( // Returns the given long.
				LLOAD_2,
				LRETURN
			)
		},
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg1 = 0
			val arg2 = 2L
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("LLOAD_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Double] -> $[Long] -> $[Long])( // Returns the given long.
				LLOAD_3,
				LRETURN
			)
		},
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg1 = 0.0
			val arg2 = 2L
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("LMUL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 6L.
				LDC2_W(3L),
				LDC2_W(2L),
				LMUL,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 6L })
		}
	)

	testInstruction("LNEG")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns -3L.
				LDC2_W(3L),
				LNEG,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -3L })
		}
	)

	testInstruction("LOOKUPSWITCH")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // If arg is between 2 and 4 returns itself if odd and it's half if even, otherwise returns 0.
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
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = Seq(
			Run(1){ _ == 0 },
			Run(2){ _ == 1 },
			Run(3){ _ == 3 },
			Run(4){ _ == 2 },
			Run(5){ _ == 0 }
		)
	)

	testInstruction("LOR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 5.
				LCONST_1,
				LDC2_W(4L),
				LOR,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = Seq(
			Run(){ _ == 5L }
		)
	)

	testInstruction("LREM")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 2L.
				LDC2_W(5L),
				LDC2_W(2L),
				LREM,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = Seq(
			Run(){ _ == 1L }
		)
	)

	testInstruction("LRETURN")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long.
				LLOAD_1,
				LRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSHL")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns -8L.
				LDC2_W(-1L),
				ICONST_3,
				LSHL,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == -8L })
		}
	)

	testInstruction("LSHR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns -1L.
				LDC2_W(-8L),
				ICONST_3,
				LSHR,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == -1L })
		}
	)

	testInstruction("LSTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long after storing it in the local 3.
				LLOAD_1,
				LSTORE(3),
				LLOAD(3),
				LRETURN
			)
		},
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSTORE_0")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L after storing it in the local 0.
				LCONST_1,
				LSTORE_0,
				LLOAD_0,
				LRETURN
			).as(Static)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("LSTORE_1")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long after storing it in the local 1.
				LLOAD_1,
				LSTORE_1,
				LLOAD_1,
				LRETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSTORE_2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns 1L after storing it in the local 2.
				LCONST_1,
				LSTORE_2,
				LLOAD_2,
				LRETURN
			).as(Static)
		},
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == 1L })
		}
	)

	testInstruction("LSTORE_3")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Long] -> $[Long])( // Returns the given long after storing it in the local 3.
				LLOAD_1,
				LSTORE_3,
				LLOAD_3,
				LRETURN
			)
		},
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSUB")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L.
				LDC2_W(3L),
				LDC2_W(2L),
				LSUB,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("LUSHR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 2305843009213693951L.
				LDC2_W(-8L),
				ICONST_3,
				LUSHR,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == 2305843009213693951L })
		}
	)

	testInstruction("LXOR")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 1L.
				LDC2_W(4L),
				LDC2_W(5L),
				LXOR,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("MONITORENTER")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Object]] -> $[Unit])( // Removes the first element received array thread safely.
				ALOAD_1,
				MONITORENTER,
				ALOAD_1,
				DUP,
				ICONST_0,
				ACONST_NULL,
				AASTORE,
				MONITOREXIT,
				RETURN
			)
		},
		maxLocals = 2,
		maxStack = 4,
		runs = {
			val arg = Array(new ArrayList)
			Seq(Run(arg){ _ ⇒ null == arg(0) })
		}
	)

	testInstruction("MONITOREXIT")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Object]] -> $[Unit])( // Removes the first element received array thread safely.
				ALOAD_1,
				MONITORENTER,
				ALOAD_1,
				DUP,
				ICONST_0,
				ACONST_NULL,
				AASTORE,
				MONITOREXIT,
				RETURN
			)
		},
		maxLocals = 2,
		maxStack = 4,
		runs = {
			val arg = Array(new ArrayList)
			Seq(Run(arg){ _ ⇒ null == arg(0) })
		}
	)

	testInstruction("MULTIANEWARRAY")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Array[Array[Object]]])( // Returns a new Object[3][]
				ICONST_3,
				MULTIANEWARRAY($[Object], 2, 1),
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ case answer: Array[Array[Any]] ⇒ answer.size == 3 && answer(0) == null })
		}
	)

	testInstruction("NEW")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Foo])( // Returns a new Foo instance
				NEW($[Foo]),
				DUP,
				INVOKESPECIAL($[Foo] >> Init()),
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _.isInstanceOf[Foo] })
		}
	)

	testInstruction("NEWARRAY")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Array[Int]])( // Returns a new int[2]
				ICONST_2,
				NEWARRAY($[Int]),
				ARETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ case answer: Array[Int] ⇒ answer.length == 2 })
		}
	)

	testInstruction("NOP")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Unit])( // Does nothing.
				NOP,
				RETURN
			)
		},
		maxLocals = 1,
		maxStack = 0,
		runs = {
			Seq(Run(){ _ ⇒ true })
		}
	)

	testInstruction("POP")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 5.
				ICONST_5,
				ICONST_2,
				POP,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("POP2")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Long])( // Returns 5L.
				LDC2_W(5L),
				LDC2_W(2L),
				POP2,
				LRETURN
			)
		},
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 5L })
		}
	)

	testInstruction("PUTFIELD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Bleh] -> $[Int] -> $[Unit])( // Sets the target object field to the given value
				ALOAD_1,
				ILOAD_2,
				PUTFIELD($[Bleh] >> "att" :: $[Int]),
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = new Bleh
			val arg2 = 15
			Seq(Run(arg1, arg2){ _ ⇒ arg1.att == arg2 })
		}
	)

	testInstruction("PUTSTATIC")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Unit])( // Sets the ATT static field to the given value
				ILOAD_1,
				PUTSTATIC($[Bleh] >> "ATT" :: $[Int]),
				RETURN
			)
		},
		maxLocals = 2,
		maxStack = 1,
		runs = {
			Bleh.ATT = 10
			val arg = 15
			Seq(Run(arg){ _ ⇒ Bleh.ATT == arg })
		}
	)

	testInstruction("RET")(
		context = CONTEXT_TYPE let { it =>
			it.version = Java_6

			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns 2.
				ICONST_1,
				JSR("5"),
				IRETURN,
				"5" @: ASTORE_1,
				ICONST_1,
				IADD,
				RET(1)
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("RETURN")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Unit])( // Does nothing.
				RETURN
			)
		},
		maxLocals = 1,
		maxStack = 0,
		runs = {
			Seq(Run(){ _ ⇒ true })
		}
	)

	testInstruction("SALOAD")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Array[Short]] -> $[Short])( // Returns the first short of the given array.
				ALOAD_1,
				ICONST_0,
				SALOAD,
				IRETURN
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array[Short](3)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("SASTORE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Short] -> $[Array[Short]] -> $[Unit])( // Stores the given short as the first element of the given array.
				ALOAD_2,
				ICONST_0,
				ILOAD_1,
				SASTORE,
				RETURN
			)
		},
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1: Short = 3
			val arg2 = new Array[Short](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("SIPUSH")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Byte])( // Returns 5.
				SIPUSH(5),
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("SWAP")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: () -> $[Int])( // Returns -1.
				ICONST_3,
				ICONST_2,
				SWAP,
				ISUB,
				IRETURN
			)
		},
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -1 })
		}
	)

	testInstruction("TABLESWITCH")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // If arg is between 2 and 4 returns itself if odd and it's half if even, otherwise returns 0.
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
			)
		},
		maxLocals = 2,
		maxStack = 2,
		runs = Seq(
			Run(1){ _ == 0 },
			Run(2){ _ == 1 },
			Run(3){ _ == 3 },
			Run(4){ _ == 2 },
			Run(5){ _ == 0 }
		)
	)

	testInstruction("WIDE")(
		context = CONTEXT_TYPE let { it =>
			it += (METHOD_SELECTOR :: $[Int] -> $[Int])( // Returns the given int increased by 1000 after storing it in the local 300.
				ILOAD_1,
				WIDE(ISTORE(300)),
				WIDE(IINC(300, 1000)),
				WIDE(ILOAD(300)),
				IRETURN
			)
		},
		maxLocals = 301,
		maxStack = 1,
		runs = {
			val arg = 7
			Seq(Run(arg){ _ == 1007 })
		}
	)

	// ****************************************************************
	// ** ASSERTIONS
	// ****************************************************************

	protected def testInstruction(instructionName: String)(context: Class, maxLocals: Int, maxStack: Int, runs: Seq[Run]) = test(instructionName){
		this(instructionName, context, maxLocals, maxStack, runs)
	}

	protected def apply(instructionName: String, context: Class, maxLocals: Int, maxStack: Int, runs: Seq[Run]): Unit
}

case class Run(args: Any*)(val expected: Any ⇒ Boolean)