package org.uqbar.voodoo.exhaustive

import org.uqbar.voodoo.model.Class

class ExhaustiveMaxStackTest extends ExhaustiveInstructionTest {
	protected def apply(instructionName: String, context: Class, maxLocals: Int, maxStack: Int, runs: Seq[Run]) {
		val code = context.methods.find(_.signature.selector == METHOD_SELECTOR).get.code

		assertResult(maxStack)(code.maxStack)
	}
}