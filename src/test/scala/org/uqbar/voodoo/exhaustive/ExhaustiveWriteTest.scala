package org.uqbar.voodoo.exhaustive

import java.lang.reflect.InvocationTargetException
import org.uqbar.voodoo.BytecodeClassLoader
import org.uqbar.voodoo.model.Class
import org.scalatest.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.FreeSpec
import java.util.Date
import java.util.Arrays
import java.awt.Point
import java.util.HashSet
import java.util.ArrayList
import org.uqbar.voodoo.BytecodeClassLoader

class ExhaustiveWriteTest extends FreeSpec with TableDrivenPropertyChecks with Matchers {

	var classLoader: BytecodeClassLoader = _

	val examples = Table[String, Seq[Any], (=>Any) => Unit](
		("Instruction", "Arguments", "Expected Result"),
		{
			val arg = Array[Object](new Date)
			("AALOAD", Seq(arg), _ should be (4) )
		}/*,
		{
			val arg1 = "foo"
			val arg2 = new Array(1)
			("AASTORE", Seq(arg1, arg2), { _ => arg1 == arg2(0) })
		},
		("ACONST_NULL", Seq(), { _ == null }),
		{
			val arg = "foo"
			("ALOAD", Seq(arg), { _ == arg })
		},
		("ALOAD_0",

			{
				val arg = "foo"
				Seq(Run(arg){ _ == arg })
			}),
		("ALOAD_1",

			{
				val arg = "foo"
				Seq(Run(arg){ _ == arg })
			}),
		("ALOAD_2",

			{
				val arg1 = null
				val arg2 = "foo"
				Seq(Run(arg1, arg2){ _ == arg2 })
			}),
		("ALOAD_3",

			{
				val arg1 = null
				val arg2 = null
				val arg3 = "foo"
				Seq(Run(arg1, arg2, arg3){ _ == arg3 })
			}),
		("ANEWARRAY", Seq(Run(){ case a: Array[AnyRef] ⇒ Arrays.equals(a, new Array[AnyRef](5)) })),
		("ARETURN", Seq(Run(){ _ == null })),
		("ARRAYLENGTH",

			{
				val arg = new Array(5)
				Seq(Run(arg){ _ == 5 })
			}),
		("ASTORE",

			{
				val arg = "foo"
				Seq(Run(arg){ _ == arg })
			}),
		("ASTORE_0",

			{
				val arg = "foo"
				Seq(Run(arg){ _ == arg })
			}),
		("ASTORE_1",

			{
				val arg = "foo"
				Seq(Run(arg){ _ == arg })
			}),
		("ASTORE_2",

			{
				val arg = "foo"
				Seq(Run(arg){ _ == arg })
			}),
		("ASTORE_3",

			{
				val arg = "foo"
				Seq(Run(arg){ _ == arg })
			}),
		("ATHROW",

			{
				Seq(Run(){ _.isInstanceOf[ArithmeticException] })
			}),
		("BALOAD",

			{
				val arg = Array(false)
				Seq(Run(arg){ _ == arg(0) })
			}),
		("BASTORE",

			{
				val arg1 = true
				val arg2 = new Array[Boolean](1)
				Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
			}),
		("BIPUSH", {
			Seq(Run(){ _ == 5 })
		}),
		("CALOAD", {
			val arg = Array('c')
			Seq(Run(arg){ _ == arg(0) })
		}),
		("CASTORE", {
			val arg1 = 'c'
			val arg2 = new Array[Char](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}),
		("CHECKCAST", {
			val arg = "foo"
			Seq(Run(arg){ _ == arg })
		}),
		("D2F", {
			val arg = 5.0
			Seq(Run(arg){ _ == 5.0f })
		}),
		("D2I", {
			val arg = 5.4
			Seq(Run(arg){ _ == 5 })
		}),
		("D2L", {
			val arg = 5.4
			Seq(Run(arg){ _ == 5L })
		}),
		("DADD", {
			Seq(Run(){ _ == 5.0 })
		}),
		("DALOAD", {
			val arg = Array(3.7)
			Seq(Run(arg){ _ == arg(0) })
		}),
		("DASTORE", {
			val arg1 = 3.7
			val arg2 = new Array[Double](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}),
		("DCMPG", {
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
		}),
		("DCMPL", {
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
		}),
		("DCONST_0", {
			Seq(Run(){ _ == 0.0 })
		}),
		("DCONST_1", {
			Seq(Run(){ _ == 1.0 })
		}),
		("DDIV", {
			Seq(Run(){ _ == 2.5 })
		}),
		("DLOAD", {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}),
		("DLOAD_0", {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}),
		("DLOAD_1", {
			val arg = 3.5
			Seq(Run(arg){ _ == arg })
		}),
		("DLOAD_2", {
			val arg1 = 0
			val arg2 = 2.7
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("DLOAD_3", {
			val arg1 = 0L
			val arg2 = 2.7
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("DMUL", {
			Seq(Run(){ _ == 6.0 })
		}),
		("DNEG", {
			Seq(Run(){ _ == -3 })
		}),
		("DREM", {
			Seq(Run(){ _ == 1 })
		}),
		("DRETURN", {
			val arg = 5
			Seq(Run(arg){ _ == arg })
		}),
		("DSTORE", {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}),
		("DSTORE_0", {
			Seq(Run(){ _ == 1.0 })
		}),
		("DSTORE_1", {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}),
		("DSTORE_2", {
			val arg = 3.7
			Seq(Run(arg){ _ == 1.0 })
		}),
		("DSTORE_3", {
			val arg = 3.7
			Seq(Run(arg){ _ == arg })
		}),
		("DSUB", {
			Seq(Run(){ _ == 1.0 })
		}),
		("DUP", {
			val arg = 19
			Seq(Run(arg){ _ == 0 })
		}),
		("DUP_X1", {
			val arg = 19
			Seq(Run(arg){ _ == -arg })
		}),
		("DUP_X2", {
			val arg = 19
			Seq(Run(arg){ _ == -1 })
		}),
		("DUP2", {
			val arg = 19L
			Seq(Run(arg){ _ == 0L })
		}),
		("DUP2_X1", {
			val arg = 1
			Seq(Run(arg){ _ == -1L })
		}),
		("DUP2_X2", {
			val arg = 19L
			Seq(Run(arg){ _ == -arg })
		}),
		("F2D", {
			val arg = 5f
			Seq(Run(arg){ _ == 5.0 })
		}),
		("F2I", {
			val arg = 5.4f
			Seq(Run(arg){ _ == 5 })
		}),
		("F2L", {
			val arg = 5.4f
			Seq(Run(arg){ _ == 5L })
		}),
		("FADD", {
			Seq(Run(){ _ == 5f })
		}),
		("FALOAD", {
			val arg = Array(3.5f)
			Seq(Run(arg){ _ == arg(0) })
		}),
		("FASTORE", {
			val arg1 = 3.7f
			val arg2 = new Array[Float](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}),
		("FCMPG", {
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
		}),
		("FCMPL", {
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
		}),
		("FCONST_0", {
			Seq(Run(){ _ == 0f })
		}),
		("FCONST_1", {
			Seq(Run(){ _ == 1f })
		}),
		("FCONST_2", {
			Seq(Run(){ _ == 2f })
		}),
		("FDIV", {
			Seq(Run(){ _ == 2.5f })
		}),
		("FLOAD", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FLOAD_0", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FLOAD_1", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FLOAD_2", {
			val arg1 = 0
			val arg2 = 2.7f
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("FLOAD_3", {
			val arg1 = 3L
			val arg2 = 2.7f
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("FMUL", {
			Seq(Run(){ _ == 6f })
		}),
		("FNEG", {
			Seq(Run(){ _ == -3.0f })
		}),
		("FREM", {
			Seq(Run(){ _ == 1.0f })
		}),
		("FRETURN", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FSTORE", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FSTORE_0", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FSTORE_1", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FSTORE_2", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FSTORE_3", {
			val arg = 3.5f
			Seq(Run(arg){ _ == arg })
		}),
		("FSUB", {
			Seq(Run(){ _ == 1f })
		}),
		("GETFIELD", {
			val arg = new Point(2, 3)
			Seq(Run(arg){ _ == 2 })
		}),
		("GETSTATIC", {
			Seq(Run(){ _ == Integer.MAX_VALUE })
		}),
		("GOTO", {
			Seq(Run(){ _ == 5 })
		}),
		("GOTO_W", {
			Seq(Run(){ _ == 5 })
		}),
		("I2B", {
			val arg = -134
			Seq(Run(arg){ _ == 122 })
		}),
		("I2C", {
			val arg = 65600
			Seq(Run(arg){ _ == '@' })
		}),
		("I2D", {
			val arg = 5
			Seq(Run(arg){ _ == 5.0 })
		}),
		("I2F", {
			val arg = 5
			Seq(Run(arg){ _ == 5.0f })
		}),
		("I2L", {
			val arg = 5
			Seq(Run(arg){ _ == 5L })
		}),
		("I2S", {
			val arg = -40000
			Seq(Run(arg){ _ == 25536 })
		}),
		("IADD", {
			Seq(Run(){ _ == 5 })
		}),
		("IALOAD", {
			val arg = Array(3)
			Seq(Run(arg){ _ == arg(0) })
		}),
		("IAND", {
			Seq(Run(){ _ == 4 })
		}),
		("IASTORE", {
			val arg1 = 0
			val arg2 = new Array[Int](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}),
		("ICONST_M1", {
			Seq(Run(){ _ == -1 })
		}),
		("ICONST_0", {
			Seq(Run(){ _ == 0 })
		}),
		("ICONST_1", {
			Seq(Run(){ _ == 1 })
		}),
		("ICONST_2", {
			Seq(Run(){ _ == 2 })
		}),
		("ICONST_3", {
			Seq(Run(){ _ == 3 })
		}),
		("ICONST_4", {
			Seq(Run(){ _ == 4 })
		}),
		("ICONST_5", {
			Seq(Run(){ _ == 5 })
		}),
		("IDIV", {
			Seq(Run(){ _ == 2 })
		}),
		("IF_ACMPEQ", {
			val arg1 = Nil
			val arg2 = List(1)
			Seq(
				Run(arg1, arg1){ _ == 1 },
				Run(arg1, arg2){ _ == 0 }
			)
		}),
		("IF_ACMPNE", {
			val arg1 = Nil
			val arg2 = List(1)
			Seq(
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg2){ _ == 1 }
			)
		}),
		("IF_ICMPEQ", {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg1){ _ == 1 },
				Run(arg1, arg2){ _ == 0 }
			)
		}),
		("IF_ICMPNE", {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg1){ _ == 0 },
				Run(arg1, arg2){ _ == 1 }
			)
		}),
		("IF_ICMPLT", {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == 0 },
				Run(arg1, arg1){ _ == 0 }
			)
		}),
		("IF_ICMPGE", {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 0 },
				Run(arg2, arg1){ _ == 1 },
				Run(arg1, arg1){ _ == 1 }
			)
		}),
		("IF_ICMPGT", {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 0 },
				Run(arg2, arg1){ _ == 1 },
				Run(arg1, arg1){ _ == 0 }
			)
		}),
		("IF_ICMPLE", {
			val arg1 = 5
			val arg2 = 7
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == 0 },
				Run(arg1, arg1){ _ == 1 }
			)
		}),
		("IFEQ", {
			val arg1 = 0
			val arg2 = 7
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 }
			)
		}),
		("IFNE", {
			val arg1 = 0
			val arg2 = 7
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 1 }
			)
		}),
		("IFLT", {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 0 },
				Run(arg3){ _ == 1 }
			)
		}),
		("IFGE", {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 1 },
				Run(arg3){ _ == 0 }
			)
		}),
		("IFGT", {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 1 },
				Run(arg3){ _ == 0 }
			)

		}),
		("IFLE", {
			val arg1 = 0
			val arg2 = 7
			val arg3 = -5
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 },
				Run(arg3){ _ == 1 }
			)
		}),
		("IFNONNULL", {
			val arg1 = null
			val arg2 = Nil
			Seq(
				Run(arg1){ _ == 0 },
				Run(arg2){ _ == 1 }
			)
		}),
		("IFNULL", {
			val arg1 = null
			val arg2 = Nil
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 }
			)
		}),
		("IINC", {
			Seq(Run(){ _ == 5 })
		}),
		("ILOAD", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ILOAD_0", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ILOAD_1", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ILOAD_2", {
			val arg1 = 0f
			val arg2 = 2
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("ILOAD_3", {
			val arg1 = 0L
			val arg2 = 2
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("IMUL", {
			Seq(Run(){ _ == 6 })
		}),
		("INEG", {
			Seq(Run(){ _ == -3 })
		}),
		("INSTANCEOF", {
			val arg1 = "foo"
			val arg2 = Array()
			Seq(
				Run(arg1){ _ == 1 },
				Run(arg2){ _ == 0 }
			)
		}),
		("INVOKEDYNAMIC", {
			val arg1: Integer = 5
			val arg2: Integer = 4
			Seq(Run(arg1, arg2){ _ == (9: Integer) })
		}),
		("INVOKEINTERFACE", {
			val arg1 = new HashSet
			val arg2 = "foo"
			Seq(Run(arg1, arg2){ _ ⇒ arg1 contains arg2 })
		}),
		("INVOKESPECIAL", {
			Seq(Run(){ _.isInstanceOf[Date] })
		}),
		("INVOKESTATIC", {
			Seq(Run(){ _ == "0" })
		}),
		("INVOKEVIRTUAL", {
			val arg = "foo"
			Seq(Run(arg){ _ == arg.length })
		}),
		("IOR", {
			Seq(Run(){ _ == 5 })
		}),
		("IREM", {
			Seq(Run(){ _ == 1 })
		}),
		("IRETURN", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ISHL", {
			Seq(Run(){ _ == -8 })
		}),
		("ISHR", {
			Seq(Run(){ _ == -1 })
		}),
		("ISTORE", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ISTORE_0", {
			val arg = 3
			Seq(Run(arg){ _ == 3 })
		}),
		("ISTORE_1", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ISTORE_2", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ISTORE_3", {
			val arg = 3
			Seq(Run(arg){ _ == arg })
		}),
		("ISUB", {
			Seq(Run(){ _ == 1 })
		}),
		("IUSHR", {
			Seq(Run(){ _ == 536870911 })
		}),
		("IXOR", {
			Seq(Run(){ _ == 1 })
		}),
		("JSR", {
			Seq(Run(){ _ == 2 })
		}),
		("JSR_W",
			{
				Seq(Run(){ _ == 2 })
			}),
		("L2D", {
			val arg = 15L
			Seq(Run(arg){ _ == 15.0 })
		}),
		("L2F", {
			val arg = 15L
			Seq(Run(arg){ _ == 15f })
		}),
		("L2I", {
			val arg = -4294967297L
			Seq(Run(arg){ _ == -1 })
		}),
		("LADD", {
			Seq(Run(){ _ == 5L })
		}),
		("LALOAD", {
			val arg = Array(3L)
			Seq(Run(arg){ _ == arg(0) })
		}),
		("LAND", {
			Seq(Run(){ _ == 4L })
		}),
		("LASTORE", {
			val arg1 = 3L
			val arg2 = new Array[Long](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}),
		("LCMP", {
			val arg1 = 3L
			val arg2 = 2L
			Seq(
				Run(arg1, arg2){ _ == 1 },
				Run(arg2, arg1){ _ == -1 },
				Run(arg1, arg1){ _ == 0 }
			)
		}),
		("LCONST_0", {
			Seq(Run(){ _ == 0L })
		}),
		("LCONST_1", {
			Seq(Run(){ _ == 1L })
		}),
		("LDC", {
			Seq(Run(){ _ == 'o' })
		}),
		("LDC2_W", {
			Seq(Run(){ _ == 7.5 })
		}),
		("LDIV", {
			Seq(Run(){ _ == 2L })
		}),
		("LLOAD", {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}),
		("LLOAD_0", {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}),
		("LLOAD_1", {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}),
		("LLOAD_2", {
			val arg1 = 0
			val arg2 = 2L
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("LLOAD_3", {
			val arg1 = 0.0
			val arg2 = 2L
			Seq(Run(arg1, arg2){ _ == arg2 })
		}),
		("LMUL", {
			Seq(Run(){ _ == 6L })
		}),
		("LNEG", {
			Seq(Run(){ _ == -3L })
		}),
		("LOOKUPSWITCH",
			Seq(
				Run(1){ _ == 0 },
				Run(2){ _ == 1 },
				Run(3){ _ == 3 },
				Run(4){ _ == 2 },
				Run(5){ _ == 0 }
			)
		),
		("LOR",
			Seq(
				Run(){ _ == 5L }
			)
		),
		("LREM",
			Seq(
				Run(){ _ == 1L }
			)
		),
		("LRETURN", {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}),
		("LSHL", {
			Seq(Run(){ _ == -8L })
		}),
		("LSHR", {
			Seq(Run(){ _ == -1L })
		}),
		("LSTORE", {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}),
		("LSTORE_0", {
			Seq(Run(){ _ == 1L })
		}),
		("LSTORE_1", {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}),
		("LSTORE_2", {
			val arg = 3L
			Seq(Run(arg){ _ == 1L })
		}),
		("LSTORE_3", {
			val arg = 3L
			Seq(Run(arg){ _ == arg })
		}),
		("LSUB", {
			Seq(Run(){ _ == 1L })
		}),
		("LUSHR", {
			Seq(Run(){ _ == 2305843009213693951L })
		}),
		("LXOR", {
			Seq(Run(){ _ == 1L })
		}),
		("MONITORENTER", {
			val arg = Array(new ArrayList)
			Seq(Run(arg){ _ ⇒ null == arg(0) })
		}),
		("MONITOREXIT", {
			val arg = Array(new ArrayList)
			Seq(Run(arg){ _ ⇒ null == arg(0) })
		}),
		("MULTIANEWARRAY", {
			Seq(Run(){ case answer: Array[Array[Any]] ⇒ answer.size == 3 && answer(0) == null })
		}),
		("NEW", {
			Seq(Run(){ _.isInstanceOf[Date] })
		}),
		("NEWARRAY", {
			Seq(Run(){ case answer: Array[Int] ⇒ answer.length == 2 })
		}),
		("NOP", {
			Seq(Run(){ _ ⇒ true })
		}),
		("POP", {
			Seq(Run(){ _ == 5 })
		}),
		("POP2", {
			Seq(Run(){ _ == 5L })
		}),
		("PUTFIELD", {
			val arg1 = new Point(1, 3)
			val arg2 = 15
			Seq(Run(arg1, arg2){ _ ⇒ arg1.x == arg2 })
		}),
		("PUTSTATIC", {
			val arg = 15
			Seq(Run(arg){ _ =>
				val clazz = classLoader.loadClass("Bleh")
				val field = clazz.getField("ATT")

				field.get(null) == arg
			})
		}),
		("RET", {
			Seq(Run(){ _ == 2 })
		}),
		("RETURN", {
			Seq(Run(){ _ ⇒ true })
		}),
		("SALOAD", {
			val arg = Array[Short](3)
			Seq(Run(arg){ _ == arg(0) })
		}),
		("SASTORE", {
			val arg1: Short = 3
			val arg2 = new Array[Short](1)
			Seq(Run(arg1, arg2){ _ ⇒ arg1 == arg2(0) })
		}),
		("SIPUSH", {
			Seq(Run(){ _ == 5 })
		}),
		("SWAP", {
			Seq(Run(){ _ == -1 })
		}),
		("TABLESWITCH",
			Seq(
				Run(1){ _ == 0 },
				Run(2){ _ == 1 },
				Run(3){ _ == 3 },
				Run(4){ _ == 2 },
				Run(5){ _ == 0 }
			)
		),
		("WIDE", {
			val arg = 7
			Seq(Run(arg){ _ == 1007 })
		}
		)*/
	)

	"Write should be as expected" in {

		forAll(examples){ (instruction, arguments, expectedResult) =>

				classLoader = new BytecodeClassLoader
				val blehClassName = getClass.getResource("/Bleh.class").getFile
				val clazz = classLoader.importClass(blehClassName)
				val field = clazz.getField("ATT")
				field.set(null, 10)
				val cbClassName = getClass.getResource("/CustomBootstrap.class").getFile
				classLoader.importClass(cbClassName)

				val instance = classLoader.importClass(BytecodeExample classFor instruction).newInstance
				val method = instance.getClass.getMethods.find{ _.getName == BytecodeExample.METHOD_SELECTOR }.get

				expectedResult(method.invoke(instance, arguments.map(_.asInstanceOf[AnyRef]): _*))
		}
	}
}