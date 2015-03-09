package org.uqbar.voodoo.exhaustive

import java.lang.invoke.ConstantCallSite
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodType
import java.util.ArrayList
import java.util.Arrays
import java.util.Date
import java.util.HashSet
import org.scalatest.FunSuite
import org.uqbar.voodoo.model.$
import org.uqbar.voodoo.model.Class
import org.uqbar.voodoo.model.ClassVersion.Java_6
import org.uqbar.voodoo.mutator.MutableType
import java.awt.Point
import org.uqbar.voodoo.BytecodeClassLoader
import java.lang.reflect.Field

//TODO: Testear el byteSize
//TODO: Testear el stackMapTable / maxStack

//▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
// TESTS
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄

trait ExhaustiveInstructionTest extends FunSuite {

	protected val CONTEXT_TYPE = $("TestClass")
	protected val METHOD_SELECTOR = "test"

	var classLoader: BytecodeClassLoader = _

	testInstruction("AALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("AALOAD") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array[Object](new Date)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("AASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("AASTORE") },
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = "foo"
			val arg2 = new Array(1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("ACONST_NULL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ACONST_NULL") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == null })
		}
	)

	testInstruction("ALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ALOAD") },
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ALOAD_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ALOAD_0") },
		maxLocals = 1,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ALOAD_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ALOAD_1") },
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ALOAD_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ALOAD_2") },
		maxLocals = 3,
		maxStack = 1,

		runs = {
			val arg1 = null
			val arg2 = "foo"
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("ALOAD_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ALOAD_3") },
		maxLocals = 4,
		maxStack = 1,

		runs = {
			val arg1 = null
			val arg2 = null
			val arg3 = "foo"
			Seq(Run(arg1, arg2, arg3){ _ == arg3 })
		}
	)

	testInstruction("ANEWARRAY")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ANEWARRAY") },
		maxLocals = 1,
		maxStack = 1,

		runs = {
			Seq(Run(){ case a: Array[AnyRef] ⇒ Arrays.equals(a, new Array[AnyRef](5)) })
		}
	)

	testInstruction("ARETURN")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ARETURN") },
		maxLocals = 1,
		maxStack = 1,

		runs = {
			Seq(Run(){ _ == null })
		}
	)

	testInstruction("ARRAYLENGTH")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ARRAYLENGTH") },
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = new Array(5)
			Seq(Run(arg){ _ == 5 })
		}
	)

	testInstruction("ASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ASTORE") },
		maxLocals = 3,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ASTORE_0") },
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ASTORE_1") },
		maxLocals = 2,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ASTORE_2") },
		maxLocals = 3,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ASTORE_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ASTORE_3") },
		maxLocals = 4,
		maxStack = 1,

		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ATHROW")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ATHROW") },
		maxLocals = 1,
		maxStack = 2,

		runs = {
			Seq(Run(){ _.isInstanceOf[ArithmeticException] })
		}
	)

	testInstruction("BALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("BALOAD") },
		maxLocals = 2,
		maxStack = 2,

		runs = {
			val arg = Array(false)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("BASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("BASTORE") },
		maxLocals = 3,
		maxStack = 3,

		runs = {
			val arg1 = true
			val arg2 = new Array[Boolean](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("BIPUSH")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("BIPUSH") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("CALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("CALOAD") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array('c')
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("CASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("CASTORE") },
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = 'c'
			val arg2 = new Array[Char](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("CHECKCAST")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("CHECKCAST") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("D2F")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("D2F") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5.0
			Seq(Run(arg){ _ == 5.0f })
		}
	)

	testInstruction("D2I")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("D2I") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5.4
			Seq(Run(arg){ _ == 5 })
		}
	)

	testInstruction("D2L")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("D2L") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5.4
			Seq(Run(arg){ _ == 5L })
		}
	)

	testInstruction("DADD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DADD") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 5.0 })
		}
	)

	testInstruction("DALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DALOAD") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3.7)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("DASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DASTORE") },
		maxLocals = 4,
		maxStack = 4,
		runs = {
			val arg1 = 3.7
			val arg2 = new Array[Double](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("DCMPG")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DCMPG") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DCMPL") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DCONST_0") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 0.0 })
		}
	)

	testInstruction("DCONST_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DCONST_1") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1.0 })
		}
	)

	testInstruction("DDIV")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DDIV") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 2.5 })
		}
	)

	testInstruction("DLOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DLOAD") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DLOAD_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DLOAD_0") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DLOAD_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DLOAD_1") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DLOAD_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DLOAD_2") },
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg1 = 0
			val arg2 = 2.7
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("DLOAD_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DLOAD_3") },
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg1 = 0L
			val arg2 = 2.7
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("DMUL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DMUL") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 6.0 })
		}
	)

	testInstruction("DNEG")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DNEG") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -3 })
		}
	)

	testInstruction("DREM")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DREM") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("DRETURN")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DRETURN") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DSTORE") },
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSTORE_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DSTORE_0") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1.0 })
		}
	)

	testInstruction("DSTORE_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DSTORE_1") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSTORE_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DSTORE_2") },
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == 1.0 })
		}
	)

	testInstruction("DSTORE_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DSTORE_3") },
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("DSUB")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DSUB") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1.0 })
		}
	)

	testInstruction("DUP")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DUP") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 19
			Seq(Run(arg){ _ == 0 })
		}
	)

	testInstruction("DUP_X1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DUP_X1") },
		maxLocals = 2,
		maxStack = 3,
		runs = {
			val arg = 19
			Seq(Run(arg){ _ == -arg })
		}
	)

	testInstruction("DUP_X2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DUP_X2") },
		maxLocals = 2,
		maxStack = 4,
		runs = {
			val arg = 19
			Seq(Run(arg){ _ == -1 })
		}
	)

	testInstruction("DUP2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DUP2") },
		maxLocals = 3,
		maxStack = 4,
		runs = {
			val arg = 19L
			Seq(Run(arg){ _ == 0L })
		}
	)

	testInstruction("DUP2_X1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DUP2_X1") },
		maxLocals = 4,
		maxStack = 6,
		runs = {
			val arg = 1
			Seq(Run(arg){ _ == -1L })
		}
	)

	testInstruction("DUP2_X2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("DUP2_X2") },
		maxLocals = 3,
		maxStack = 6,
		runs = {
			val arg = 19L
			Seq(Run(arg){ _ == -arg })
		}
	)

	testInstruction("F2D")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("F2D") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5f
			Seq(Run(arg){ _ == 5.0 })
		}
	)

	testInstruction("F2I")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("F2I") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 5.4f
			Seq(Run(arg){ _ == 5 })
		}
	)

	testInstruction("F2L")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("F2L") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5.4f
			Seq(Run(arg){ _ == 5L })
		}
	)

	testInstruction("FADD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FADD") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5f })
		}
	)

	testInstruction("FALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FALOAD") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3.5f)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("FASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FASTORE") },
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = 3.7f
			val arg2 = new Array[Float](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("FCMPG")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FCMPG") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FCMPL") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FCONST_0") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 0f })
		}
	)

	testInstruction("FCONST_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FCONST_1") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 1f })
		}
	)

	testInstruction("FCONST_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FCONST_2") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 2f })
		}
	)

	testInstruction("FDIV")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FDIV") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2.5f })
		}
	)

	testInstruction("FLOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FLOAD") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FLOAD_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FLOAD_0") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FLOAD_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FLOAD_1") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FLOAD_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FLOAD_2") },
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg1 = 0
			val arg2 = 2.7f
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("FLOAD_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FLOAD_3") },
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg1 = 3L
			val arg2 = 2.7f
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("FMUL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FMUL") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 6f })
		}
	)

	testInstruction("FNEG")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FNEG") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == -3.0f })
		}
	)

	testInstruction("FREM")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FREM") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1.0f })
		}
	)

	testInstruction("FRETURN")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FRETURN") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FSTORE") },
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FSTORE_0") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FSTORE_1") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FSTORE_2") },
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSTORE_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FSTORE_3") },
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("FSUB")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("FSUB") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1f })
		}
	)

	testInstruction("GETFIELD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("GETFIELD") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = new Point(2, 3)
			Seq(Run(arg){ _ == 2 })
		}
	)

	testInstruction("GETSTATIC")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("GETSTATIC") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == Integer.MAX_VALUE })
		}
	)

	testInstruction("GOTO")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("GOTO") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("GOTO_W")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("GOTO_W") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("I2B")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("I2B") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = -134
			Seq(Run(arg){ _ == 122 })
		}
	)

	testInstruction("I2C")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("I2C") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 65600
			Seq(Run(arg){ _ == '@' })
		}
	)

	testInstruction("I2D")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("I2D") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == 5.0 })
		}
	)

	testInstruction("I2F")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("I2F") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == 5.0f })
		}
	)

	testInstruction("I2L")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("I2L") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 5
			Seq(Run(arg){ _ == 5L })
		}
	)

	testInstruction("I2S")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("I2S") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = -40000
			Seq(Run(arg){ _ == 25536 })
		}
	)

	testInstruction("IADD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IADD") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("IALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IALOAD") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("IAND")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IAND") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 4 })
		}
	)

	testInstruction("IASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IASTORE") },
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1 = 0
			val arg2 = new Array[Int](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("ICONST_M1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ICONST_M1") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == -1 })
		}
	)

	testInstruction("ICONST_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ICONST_0") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 0 })
		}
	)

	testInstruction("ICONST_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ICONST_1") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("ICONST_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ICONST_2") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("ICONST_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ICONST_3") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 3 })
		}
	)

	testInstruction("ICONST_4")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ICONST_4") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 4 })
		}
	)

	testInstruction("ICONST_5")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ICONST_5") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("IDIV")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IDIV") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("IF_ACMPEQ")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ACMPEQ") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ACMPNE") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ICMPEQ") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ICMPNE") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ICMPLT") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ICMPGE") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ICMPGT") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IF_ICMPLE") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFEQ") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFNE") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFLT") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFGE") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFGT") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFLE") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFNONNULL") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IFNULL") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IINC") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("ILOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ILOAD") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ILOAD_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ILOAD_0") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ILOAD_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ILOAD_1") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ILOAD_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ILOAD_2") },
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg1 = 0f
			val arg2 = 2
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("ILOAD_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ILOAD_3") },
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg1 = 0L
			val arg2 = 2
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("IMUL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IMUL") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 6 })
		}
	)

	testInstruction("INEG")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("INEG") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == -3 })
		}
	)

	testInstruction("INSTANCEOF")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("INSTANCEOF") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg1 = "foo"
			val arg2 = Array()
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 }
			)
		}
	)

	testInstruction("INVOKEDYNAMIC")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("INVOKEDYNAMIC") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1: Integer = 5
			val arg2: Integer = 4
			Seq(Run(arg1, arg2){ _ == (9: Integer) })
		}
	)

	testInstruction("INVOKEINTERFACE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("INVOKEINTERFACE") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = new HashSet
			val arg2 = "foo"
			Seq(Run(arg1, arg2){ _ ⇒ arg1 contains arg2 })
		}
	)

	testInstruction("INVOKESPECIAL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("INVOKESPECIAL") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _.isInstanceOf[Date] })
		}
	)

	testInstruction("INVOKESTATIC")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("INVOKESTATIC") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == "0" })
		}
	)

	testInstruction("INVOKEVIRTUAL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("INVOKEVIRTUAL") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = "foo"
			Seq(Run(arg){ _ == arg.length })
		}
	)

	testInstruction("IOR")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IOR") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("IREM")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IREM") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("IRETURN")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IRETURN") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISHL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISHL") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -8 })
		}
	)

	testInstruction("ISHR")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISHR") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -1 })
		}
	)

	testInstruction("ISTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISTORE") },
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISTORE_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISTORE_0") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == 3 })
		}
	)

	testInstruction("ISTORE_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISTORE_1") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISTORE_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISTORE_2") },
		maxLocals = 3,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISTORE_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISTORE_3") },
		maxLocals = 4,
		maxStack = 1,
		runs = {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("ISUB")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("ISUB") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("IUSHR")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IUSHR") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 536870911 })
		}
	)

	testInstruction("IXOR")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("IXOR") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1 })
		}
	)

	testInstruction("JSR")(
		context = CONTEXT_TYPE let { it =>
			it += BytecodeExample of ("JSR")
			it.version = Java_6

		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("JSR_W")(
		context = CONTEXT_TYPE let { it =>
			it += BytecodeExample of ("JSR_W")
			it.version = Java_6

		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("L2D")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("L2D") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 15L
			Seq(Run(arg){ _ == 15.0 })
		}
	)

	testInstruction("L2F")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("L2F") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 15L
			Seq(Run(arg){ _ == 15f })
		}
	)

	testInstruction("L2I")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("L2I") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = -4294967297L
			Seq(Run(arg){ _ == -1 })
		}
	)

	testInstruction("LADD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LADD") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 5L })
		}
	)

	testInstruction("LALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LALOAD") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array(3L)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("LAND")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LAND") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 4L })
		}
	)

	testInstruction("LASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LASTORE") },
		maxLocals = 4,
		maxStack = 4,
		runs = {
			val arg1 = 3L
			val arg2 = new Array[Long](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("LCMP")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LCMP") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LCONST_0") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 0L })
		}
	)

	testInstruction("LCONST_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LCONST_1") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("LDC")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LDC") },
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == 'o' })
		}
	)

	testInstruction("LDC2_W")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LDC2_W") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 7.5 })
		}
	)

	testInstruction("LDIV")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LDIV") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 2L })
		}
	)

	testInstruction("LLOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LLOAD") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LLOAD_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LLOAD_0") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LLOAD_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LLOAD_1") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LLOAD_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LLOAD_2") },
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg1 = 0
			val arg2 = 2L
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("LLOAD_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LLOAD_3") },
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg1 = 0.0
			val arg2 = 2L
			Seq(Run(arg1, arg2){ _ == arg2 })
		}
	)

	testInstruction("LMUL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LMUL") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 6L })
		}
	)

	testInstruction("LNEG")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LNEG") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -3L })
		}
	)

	testInstruction("LOOKUPSWITCH")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LOOKUPSWITCH") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LOR") },
		maxLocals = 1,
		maxStack = 4,
		runs = Seq(
			Run(){ _ == 5L }
		)
	)

	testInstruction("LREM")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LREM") },
		maxLocals = 1,
		maxStack = 4,
		runs = Seq(
			Run(){ _ == 1L }
		)
	)

	testInstruction("LRETURN")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LRETURN") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSHL")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSHL") },
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == -8L })
		}
	)

	testInstruction("LSHR")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSHR") },
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == -1L })
		}
	)

	testInstruction("LSTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSTORE") },
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSTORE_0")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSTORE_0") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("LSTORE_1")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSTORE_1") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSTORE_2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSTORE_2") },
		maxLocals = 4,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == 1L })
		}
	)

	testInstruction("LSTORE_3")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSTORE_3") },
		maxLocals = 5,
		maxStack = 2,
		runs = {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}
	)

	testInstruction("LSUB")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LSUB") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("LUSHR")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LUSHR") },
		maxLocals = 1,
		maxStack = 3,
		runs = {
			Seq(Run(){ _ == 2305843009213693951L })
		}
	)

	testInstruction("LXOR")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("LXOR") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 1L })
		}
	)

	testInstruction("MONITORENTER")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("MONITORENTER") },
		maxLocals = 2,
		maxStack = 4,
		runs = {
			val arg = Array(new ArrayList)
			Seq(Run(arg){ _ ⇒ null == arg(0) })
		}
	)

	testInstruction("MONITOREXIT")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("MONITOREXIT") },
		maxLocals = 2,
		maxStack = 4,
		runs = {
			val arg = Array(new ArrayList)
			Seq(Run(arg){ _ ⇒ null == arg(0) })
		}
	)

	testInstruction("MULTIANEWARRAY")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("MULTIANEWARRAY") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ case answer: Array[Array[Any]] ⇒ answer.size == 3 && answer(0) == null })
		}
	)

	testInstruction("NEW")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("NEW") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _.isInstanceOf[Date] })
		}
	)

	testInstruction("NEWARRAY")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("NEWARRAY") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ case answer: Array[Int] ⇒ answer.length == 2 })
		}
	)

	testInstruction("NOP")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("NOP") },
		maxLocals = 1,
		maxStack = 0,
		runs = {
			Seq(Run(){ _ ⇒ true })
		}
	)

	testInstruction("POP")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("POP") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("POP2")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("POP2") },
		maxLocals = 1,
		maxStack = 4,
		runs = {
			Seq(Run(){ _ == 5L })
		}
	)

	testInstruction("PUTFIELD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("PUTFIELD") },
		maxLocals = 3,
		maxStack = 2,
		runs = {
			val arg1 = new Point(1, 3)
			val arg2 = 15
			Seq(Run(arg1, arg2){ _ ⇒ arg1.x == arg2 })
		}
	)

	testInstruction("PUTSTATIC")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("PUTSTATIC") },
		maxLocals = 2,
		maxStack = 1,
		runs = {
			val arg = 15
			Seq(Run(arg){ _ =>
				val clazz = classLoader.loadClass("Bleh")
				val field = clazz.getField("ATT")

				field.get(null) == arg
			})
		}
	)

	testInstruction("RET")(
		context = CONTEXT_TYPE let { it =>
			it += BytecodeExample of ("RET")
			it.version = Java_6

		},
		maxLocals = 2,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == 2 })
		}
	)

	testInstruction("RETURN")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("RETURN") },
		maxLocals = 1,
		maxStack = 0,
		runs = {
			Seq(Run(){ _ ⇒ true })
		}
	)

	testInstruction("SALOAD")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("SALOAD") },
		maxLocals = 2,
		maxStack = 2,
		runs = {
			val arg = Array[Short](3)
			Seq(Run(arg){ _ == arg(0) })
		}
	)

	testInstruction("SASTORE")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("SASTORE") },
		maxLocals = 3,
		maxStack = 3,
		runs = {
			val arg1: Short = 3
			val arg2 = new Array[Short](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}
	)

	testInstruction("SIPUSH")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("SIPUSH") },
		maxLocals = 1,
		maxStack = 1,
		runs = {
			Seq(Run(){ _ == 5 })
		}
	)

	testInstruction("SWAP")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("SWAP") },
		maxLocals = 1,
		maxStack = 2,
		runs = {
			Seq(Run(){ _ == -1 })
		}
	)

	testInstruction("TABLESWITCH")(
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("TABLESWITCH") },
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
		context = CONTEXT_TYPE let { _ += BytecodeExample of ("WIDE") },
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