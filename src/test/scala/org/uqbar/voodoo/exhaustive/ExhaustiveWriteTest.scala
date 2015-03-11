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

	val examples = Table[String, Seq[Any], (=> Any) => Unit](
		("Instruction", "Arguments", "Expected Result"),
		("AALOAD", Seq(Array("foo")), _ should be ("foo")),
		{
			val arg = new Array[Any](1)
			("AASTORE", Seq("foo", arg), { r => r; arg(0) should be ("foo") })
		},
		("ACONST_NULL", Seq(), _ should be (null: AnyRef)),
		("ALOAD", Seq("foo"), _ should be ("foo")),
		("ALOAD_0", Seq("foo"), _ should be ("foo")),
		("ALOAD_1", Seq("foo"), _ should be ("foo")),
		("ALOAD_2", Seq(null, "foo"), _ should be ("foo")),
		("ALOAD_3", Seq(null, null, "foo"), _ should be ("foo")),
		("ANEWARRAY", Seq(), { case r: Array[_] ⇒ r should have size 5 }),
		("ARETURN", Seq(), _ should be (null: AnyRef)),
		("ARRAYLENGTH", Seq(new Array(5)), _ should be (5)),
		("ASTORE", Seq("foo"), _ should be ("foo")),
		("ASTORE_0", Seq("foo"), _ should be ("foo")),
		("ASTORE_1", Seq("foo"), _ should be ("foo")),
		("ASTORE_2", Seq("foo"), _ should be ("foo")),
		("ASTORE_3", Seq("foo"), _ should be ("foo")),
		("ATHROW", Seq(), an[ArithmeticException] should be thrownBy _),
		("BALOAD", Seq(Array(false)), { case r: Boolean => r should be (false) }),
		{
			val arg = new Array[Boolean](1)
			("BASTORE", Seq(true, arg), { r ⇒ r; arg(0) should be (true) })
		},
		("BIPUSH", Seq(), _ should be (5)),
		("CALOAD", Seq(Array('c')), _ should be ('c')),
		{
			val arg = new Array[Char](1)
			("CASTORE", Seq('c', arg), { r => r; arg(0) should be('c') })
		},
		("CHECKCAST", Seq("foo"), _ should be("foo")),
		("D2F", Seq(5.0), _ should be (5.0f)),
		("D2I", Seq(5.4), _ should be (5)),
		("D2L", Seq(5.4), _ should be (5L)),
		("DADD", Seq(), _ should be (5.0)),
		("DALOAD", Seq(Array(3.7)), _ should be (3.7)),
		{
			val arg = new Array[Double](1)
			("DASTORE", Seq(3.7, arg), { r => r; arg(0) should be (3.7) })
		},
		("DCMPG", Seq(3.7, 2.5), _ should be (1)),
		("DCMPL", Seq(3.7, 2.5), _ should be (1)),
		("DCONST_0", Seq(), _ should be (0.0)),
		("DCONST_1", Seq(), _ should be (1.0)),
		("DDIV", Seq(), _ should be (2.5)),
		("DLOAD", Seq(3.5), _ should be (3.5)),
		("DLOAD_0", Seq(3.5), _ should be (3.5)),
		("DLOAD_1", Seq(3.5), _ should be (3.5)),
		("DLOAD_2", Seq(0, 3), _ should be (3.0)),
		("DLOAD_3", Seq(0L, 3), _ should be (3.0)),
		("DMUL", Seq(), _ should be (6.0)),
		("DNEG", Seq(), _ should be (-3.0)),
		("DREM", Seq(), _ should be (1.0)),
		("DRETURN", Seq(5.0), _ should be (5.0)),
		("DSTORE", Seq(3.7), _ should be (3.7)),
		("DSTORE_0", Seq(), _ should be (1.0)),
		("DSTORE_1", Seq(3.7), _ should be (3.7)),
		("DSTORE_2", Seq(3.7), _ should be (1.0)),
		("DSTORE_3", Seq(3.7), _ should be (3.7)),
		("DSUB", Seq(), _ should be (1.0)),
		("DUP", Seq(19), _ should be (0)),
		("DUP_X1", Seq(19), _ should be (-19)),
		("DUP_X2", Seq(19), _ should be (-1)),
		("DUP2", Seq(19L), _ should be (0L)),
		("DUP2_X1", Seq(1), _ should be (-1L)),
		("DUP2_X2", Seq(19L), _ should be (-19L)),
		("F2D", Seq(5f), _ should be (5.0)),
		("F2I", Seq(5.4f), _ should be (5)),
		("F2L", Seq(5.4f), _ should be (5L)),
		("FADD", Seq(), _ should be (5)),
		("FALOAD", Seq(Array(3.5f)), _ should be (3.5f)),
		{
			val arg = new Array[Float](1)
			("FASTORE", Seq(3.7f, arg), { r => r; arg(0) should be (3.7f) })
		},
		("FCMPG", Seq(3.7f, 2.5f), _ should be (1)),
		("FCMPL", Seq(3.7f, 2.5f), _ should be (1)),
		("FCONST_0", Seq(), _ should be (0f)),
		("FCONST_1", Seq(), _ should be (1f)),
		("FCONST_2", Seq(), _ should be (2f)),
		("FDIV", Seq(), _ should be (2.5f)),
		("FLOAD", Seq(3.5f), _ should be (3.5f)),
		("FLOAD_0", Seq(3.5f), _ should be (3.5f)),
		("FLOAD_1", Seq(3.5f), _ should be (3.5f)),
		("FLOAD_2", Seq(0, 3), _ should be (3f)),
		("FLOAD_3", Seq(3L, 4), _ should be (4f)),
		("FMUL", Seq(), _ should be (6f)),
		("FNEG", Seq(), _ should be (-3f)),
		("FREM", Seq(), _ should be (1f)),
		("FRETURN", Seq(3.5f), _ should be (3.5f)),
		("FSTORE", Seq(3.5f), _ should be (3.5f)),
		("FSTORE_0", Seq(3.5f), _ should be (3.5f)),
		("FSTORE_1", Seq(3.5f), _ should be (3.5f)),
		("FSTORE_2", Seq(3.5f), _ should be (3.5f)),
		("FSTORE_3", Seq(3.5f), _ should be (3.5f)),
		("FSUB", Seq(), _ should be (1f)),
		("GETFIELD", Seq(new Point(2, 3)), _ should be (2)),
		("GETSTATIC", Seq(), _ should be (Integer.MAX_VALUE)),
		("GOTO", Seq(), _ should be (5)),
		("GOTO_W", Seq(), _ should be (5)),
		("I2B", Seq(-134), _ should be(122)),
		("I2C", Seq(65600), _ should be ('@')),
		("I2D", Seq(5), _ should be (5.0)),
		("I2F", Seq(5), _ should be (5f)),
		("I2L", Seq(5), _ should be (5L)),
		("I2S", Seq(-40000), _ should be (25536)),
		("IADD", Seq(), _ should be (5)),
		("IALOAD", Seq(Array(3)), _ should be (3)),
		("IAND", Seq(), _ should be (4)),
		{
			val arg = new Array[Int](1)
			("IASTORE", Seq(5, arg), { r => r; arg(0) should be (5) })
		},
		("ICONST_M1", Seq(), _ should be (-1)),
		("ICONST_0", Seq(), _ should be (0)),
		("ICONST_1", Seq(), _ should be (1)),
		("ICONST_2", Seq(), _ should be (2)),
		("ICONST_3", Seq(), _ should be (3)),
		("ICONST_4", Seq(), _ should be (4)),
		("ICONST_5", Seq(), _ should be (5)),
		("IDIV", Seq(), _ should be (2)),
		("IF_ACMPEQ", Seq(Nil, List(1)), _ should be (0)),
		("IF_ACMPNE", Seq(Nil, List(1)), _ should be (1)),
		("IF_ICMPEQ", Seq(5, 7), _ should be (0)),
		("IF_ICMPNE", Seq(5, 7), _ should be (1)),
		("IF_ICMPLT", Seq(5, 7), _ should be (1)),
		("IF_ICMPGE", Seq(5, 7), _ should be (0)),
		("IF_ICMPGT", Seq(5, 7), _ should be (0)),
		("IF_ICMPLE", Seq(5, 7), _ should be (1)),
		("IFEQ", Seq(0), _ should be (1)),
		("IFNE", Seq(0), _ should be (0)),
		("IFLT", Seq(5), _ should be (0)),
		("IFGE", Seq(5), _ should be (1)),
		("IFGT", Seq(5), _ should be (1)),
		("IFLE", Seq(5), _ should be (0)),
		("IFNONNULL", Seq(null), _ should be (0)),
		("IFNULL", Seq(null), _ should be (1)),
		("IINC", Seq(), _ should be (5)),
		("ILOAD", Seq(5), _ should be (5)),
		("ILOAD_0", Seq(5), _ should be (5)),
		("ILOAD_1", Seq(5), _ should be (5)),
		("ILOAD_2", Seq(0, 5), _ should be (5)),
		("ILOAD_3", Seq(0, 5), _ should be (5)),
		("IMUL", Seq(), _ should be (6)),
		("INEG", Seq(), _ should be (-3)),
		("INSTANCEOF", Seq("foo"), _ should be (1)),
		("INVOKEDYNAMIC", Seq(5, 4), _ should be (9)),
		{
			val arg = new HashSet[String]
			("INVOKEINTERFACE", Seq(arg, "foo"), { r => r; arg should contain ("foo") })
		},
		("INVOKESPECIAL", Seq(), _ shouldBe a[Date]),
		("INVOKESTATIC", Seq(), _ should be ("0")),
		("INVOKEVIRTUAL", Seq("foo"), _ should be (3)),
		("IOR", Seq(), _ should be (5)),
		("IREM", Seq(), _ should be (1)),
		("IRETURN", Seq(5), _ should be (5)),
		("ISHL", Seq(), _ should be (-8)),
		("ISHR", Seq(), _ should be (-1)),
		("ISTORE", Seq(5), _ should be (5)),
		("ISTORE_0", Seq(5), _ should be (5)),
		("ISTORE_1", Seq(5), _ should be (5)),
		("ISTORE_2", Seq(5), _ should be (5)),
		("ISTORE_3", Seq(5), _ should be (5)),
		("ISUB", Seq(), _ should be (1)),
		("IUSHR", Seq(), _ should be (536870911)),
		("IXOR", Seq(), _ should be (1)),
		("JSR", Seq(), _ should be (2)),
		("JSR_W", Seq(), _ should be (2)),
		("L2D", Seq(5L), _ should be (5.0)),
		("L2F", Seq(5L), _ should be (5f)),
		("L2I", Seq(-4294967297L), _ should be (-1)),
		("LADD", Seq(), _ should be (5L)),
		("LALOAD", Seq(Array(5L)), _ should be (5L)),
		("LAND", Seq(), _ should be (4L)),
		{
			val arg = new Array[Long](1)
			("LASTORE", Seq(5L, arg), { r => r; arg(0) should be(5L) })
		},
		("LCMP", Seq(5L, 7L), _ should be (-1)),
		("LCONST_0", Seq(), _ should be (0L)),
		("LCONST_1", Seq(), _ should be (1L)),
		("LDC", Seq(), _ should be ('o')),
		("LDC2_W", Seq(), _ should be (7.5)),
		("LDIV", Seq(), _ should be (2L)),
		("LLOAD", Seq(3L), _ should be (3L)),
		("LLOAD_0", Seq(3L), _ should be (3L)),
		("LLOAD_1", Seq(3L), _ should be (3L)),
		("LLOAD_2", Seq(0, 3), _ should be (3L)),
		("LLOAD_3", Seq(0, 3), _ should be (3L)),
		("LMUL", Seq(), _ should be (6L)),
		("LNEG", Seq(), _ should be (-3L)),
		("LOOKUPSWITCH", Seq(4), _ should be (2)),
		("LOR", Seq(), _ should be (5L)),
		("LREM", Seq(), _ should be (1L)),
		("LRETURN", Seq(5L), _ should be (5L)),
		("LSHL", Seq(), _ should be (-8L)),
		("LSHR", Seq(), _ should be (-1L)),
		("LSTORE", Seq(5L), _ should be (5L)),
		("LSTORE_0", Seq(), _ should be (1L)),
		("LSTORE_1", Seq(5L), _ should be (5L)),
		("LSTORE_2", Seq(5L), _ should be (1L)),
		("LSTORE_3", Seq(5L), _ should be (5L)),
		("LSUB", Seq(), _ should be (1L)),
		("LUSHR", Seq(), _ should be (2305843009213693951L)),
		("LXOR", Seq(), _ should be (1L)),
		{
			val arg = Array(new ArrayList)
			("MONITORENTER", Seq(arg), { r => r; arg(0) should be(null) })
		},
		{
			val arg = Array(new ArrayList)
			("MONITOREXIT", Seq(arg), { r => r; arg(0) should be(null) })
		},
		("MULTIANEWARRAY", Seq(), { case r: Array[_] => r should have size (3) }),
		("NEW", Seq(), _ shouldBe a[Date]),
		("NEWARRAY", Seq(), { case r: Array[_] => r should have size (2) }),
		("NOP", Seq(), _ should be (null: AnyRef)),
		("POP", Seq(), _ should be (5)),
		("POP2", Seq(), _ should be (5L)),
		{
			val arg = new Point(1, 3)
			("PUTFIELD", Seq(arg, 15), { r => r; arg.x should be(15) })
		},
		("PUTSTATIC", Seq(15), { r => r; classLoader.loadClass("Bleh").getField("ATT").get(null) should be(15) }),
		("RET", Seq(), _ should be (2)),
		("RETURN", Seq(), { _ should be(null: AnyRef) }),
		("SALOAD", Seq(Array[Short](3)), _ should be (3)),
		{
			val arg = new Array[Short](1)
			("SASTORE", Seq(3: Short, arg), { r => r; arg(0) should be(3) })
		},
		("SIPUSH", Seq(), _ should be (5)),
		("SWAP", Seq(), _ should be (-1)),
		("TABLESWITCH", Seq(4), _ should be (2)),
		("WIDE", Seq(7), _ should be (1007))
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

			expectedResult(
				try method.invoke(instance, arguments.map(_.asInstanceOf[AnyRef]): _*)
				catch { case e: InvocationTargetException => throw e.getCause }
			)
		}
	}
}