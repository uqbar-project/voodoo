package org.uqbar.voodoo

import scala.util.parsing.combinator.RegexParsers
import org.uqbar.voodoo.model.constants._
import org.uqbar.voodoo.model._

object StringInterpreter {
	protected object BinaryNameInterpreter extends StringInterpreter {
		def name(s : String) = try parseWith(fullTypeName("/")("."))(s) catch {
			case _ : IllegalArgumentException ⇒ typeNameFromDescriptor(s)
		}
		def binaryName(s : String) = try parseWith(fullTypeName(".")("/"))(s) catch {
			case _ : IllegalArgumentException ⇒ descriptorFromName($(s))
		}
	}

	protected object DescriptorInterpreter extends StringInterpreter {
		protected val baseTypeNameFromDescriptor = descriptorFromBaseTypeName.map(_.swap)

		protected def returnTypeName : Parser[String] = "V" ^^ baseTypeNameFromDescriptor | argumentTypeName
		protected def argumentTypeName : Parser[String] = arrayTypeName | arrayableTypeName
		protected def arrayTypeName : Parser[String] = "\\[".r.+ ~ arrayableTypeName ^^ { case as ~ t ⇒ t + as.mkString("", "]", "]") }
		protected def arrayableTypeName : Parser[String] = baseArgumentTypeName | objectTypeName
		protected def baseArgumentTypeName : Parser[String] = s"[${baseTypeNameFromDescriptor.keys.filter(_ != "V").mkString}]".r ^^ baseTypeNameFromDescriptor
		protected def objectTypeName : Parser[String] = "L" ~> fullTypeName("/")(".") <~ ";"

		protected def anySignatureType = fieldSignatureType | methodSignatureType
		protected def fieldSignatureType : Parser[Type] = argumentTypeName ^^ { n ⇒ $(n) }
		protected def methodSignatureType : Parser[MethodType] = ("(" ~> argumentTypeName.* <~ ")") ~ returnTypeName ^^ { case args ~ ret ⇒ MethodType($(ret), args map $ : _*) }

		def typeName = parseWith(argumentTypeName) _
		def fieldType = parseWith(fieldSignatureType) _
		def methodType = parseWith(methodSignatureType) _
		def signatureType = parseWith(anySignatureType) _
	}

	protected object TypeNameInterpreter extends StringInterpreter {
		protected def anyTypeDescriptor : Parser[String] = (objectTypeDescriptor ||| baseTypeDescriptor) ~ arrayTypeTailDescriptor ^^ { case t ~ as ⇒ as + t }
		protected def baseTypeDescriptor : Parser[String] = s"${descriptorFromBaseTypeName.keys.mkString("(", "|", ")")}".r ^^ descriptorFromBaseTypeName
		protected def arrayTypeTailDescriptor : Parser[String] = "(\\[\\])*".r ^^ (d ⇒ d.replace("]", ""))
		protected def objectTypeDescriptor : Parser[String] = fullTypeName(".")("/") ^^ (n ⇒ s"L$n;")

		def descriptor(s : SignatureType[_ <: Slot]) : String = s match {
			case Type(name) ⇒ parseWith(anyTypeDescriptor)(name)
			case MethodType(returnType, argumentTypes @ _*) ⇒ argumentTypes.map(descriptor(_)).mkString("(", "", ")") + descriptor(returnType)
		}
	}

	def typeNameFromBinaryName = BinaryNameInterpreter.name _
	def binaryNameFromTypeName = BinaryNameInterpreter.binaryName _

	def typeNameFromDescriptor = DescriptorInterpreter.typeName
	def typeFromDescriptor = DescriptorInterpreter.fieldType
	def methodTypeFromDescriptor = DescriptorInterpreter.methodType
	def signatureTypeFromDescriptor : String ⇒ SignatureType[_ <: Slot] = DescriptorInterpreter.signatureType

	def descriptorFromName = TypeNameInterpreter.descriptor _
}

trait StringInterpreter extends RegexParsers {
	protected val descriptorFromBaseTypeName = Map(
		$[Unit].name -> "V",
		$[Byte].name -> "B",
		$[Char].name -> "C",
		$[Double].name -> "D",
		$[Float].name -> "F",
		$[Int].name -> "I",
		$[Long].name -> "J",
		$[Short].name -> "S",
		$[Boolean].name -> "Z"
	)

	protected def unqualifiedName : Parser[String] = """[^.;\[/ ]+""".r
	protected def fullTypeName(inSep : String)(outSep : String) : Parser[String] = unqualifiedName ~ (inSep ~> unqualifiedName).* ^^ { case n ~ ns ⇒ (n :: ns).mkString(outSep) }

	protected def parseWith[T](p : Parser[T])(input : String) = parseAll(p, input) match {
		case Success(result, _) ⇒ result
		case failure : NoSuccess ⇒ throw new IllegalArgumentException(failure.msg)
	}
}