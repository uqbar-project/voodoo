package org.uqbar.voodoo.exhaustive

import java.io.File
import scala.annotation.migration
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.uqbar.voodoo.parser.ClassParser
import org.uqbar.voodoo.writer.ClassWriter
import org.scalatest.BeforeAndAfterAll

class ExhaustiveParseTest extends FreeSpec with TableDrivenPropertyChecks with Matchers with BeforeAndAfterAll {

	val TEMP_PATH = new File(s"${getClass.getProtectionDomain.getCodeSource.getLocation.getFile}GENERATED")
	
	override def beforeAll {
		TEMP_PATH.mkdir
	}

	override def afterAll {
		TEMP_PATH.listFiles.foreach{ _.delete } 
		TEMP_PATH.delete
	}

	"Parse should be as expected" in {
		for {
			instruction <- BytecodeExample.methods.keys
			example = BytecodeExample classFor instruction
			filePath = s"$TEMP_PATH${File.separator}$instruction.class"
		} {
			ClassWriter.writeClass(example, filePath)

			ClassParser.parseClass(filePath) should be (example)
		}
	}
}