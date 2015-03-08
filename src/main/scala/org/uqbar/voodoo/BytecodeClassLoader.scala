package org.uqbar.voodoo

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import org.uqbar.voodoo.model.Class
import org.uqbar.Utils._
import org.uqbar.voodoo.writer.ClassWriter
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class BytecodeClassLoader extends ClassLoader with ClassWriter {

	def importClass(target: Class) = {
		var bytes: Array[Byte] = null

		for {
			baos ← new ByteArrayOutputStream
		} {
			writeClass(target, baos)
			baos.flush
			bytes = baos.toByteArray
		}

		defineClass(target.name, bytes, 0, bytes.length)
	}

	def importClass(path: String) = {
		val namePattern = """.*\.(.*)\.class""".r
		val namePattern(name) = path
		val bytes = Files.readAllBytes(Paths.get(path))

		defineClass(name, bytes, 0, bytes.length)
	}
}