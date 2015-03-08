package org.uqbar.voodoo.exhaustive

import java.io.File

import org.uqbar.voodoo.model.Class
import org.uqbar.voodoo.parser.ClassParser
import org.uqbar.voodoo.writer.ClassWriter

class ExhaustiveParseTest extends ExhaustiveInstructionTest {
	protected def apply(instructionName: String, context: Class, maxLocals: Int, maxStack: Int, runs: Seq[Run]) {
		val path = getClass.getProtectionDomain.getCodeSource.getLocation.getFile + "GENERATED/"
		val filePath = s"$path$instructionName.class"
		new File(path).mkdir

		ClassWriter.writeClass(context, filePath)

		val parsed = ClassParser.parseClass(filePath)

		assertResult(context)(parsed)
	}
}