package org.uqbar.voodoo.exhaustive

import java.lang.reflect.InvocationTargetException

import org.uqbar.voodoo.BytecodeClassLoader
import org.uqbar.voodoo.model.Class

class ExhaustiveWriteTest extends ExhaustiveInstructionTest {
	protected def apply(instructionName: String, context: Class, maxLocals: Int, maxStack: Int, runs: Seq[Run]) {
		try
			for (run ← runs) {
				val instance = new BytecodeClassLoader().importClass(context).newInstance
				val method = instance.getClass.getMethods.find{ _.getName == METHOD_SELECTOR }.get
				val answer = try method.invoke(instance, run.args.map(_.asInstanceOf[AnyRef]): _*)
				catch { case e: InvocationTargetException ⇒ e.getTargetException }

				assert(run.expected(answer))
			}
		catch { case e: Throwable ⇒ throw new RuntimeException(e) }
	}
}