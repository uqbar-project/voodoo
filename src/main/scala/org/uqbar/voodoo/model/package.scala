package org.uqbar.voodoo

import org.uqbar.voodoo.model.constants._
import org.uqbar.voodoo.model._
import org.uqbar.voodoo.model.MethodModifier._
import org.uqbar.voodoo.Globals._
import org.uqbar.voodoo.StringInterpreter.typeNameFromDescriptor
import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.universe.runtimeMirror
import scala.language.implicitConversions
import scala.collection.mutable.ListBuffer

package object model {
	def $(typeCanonicalName : String) = Type(typeCanonicalName)
	def $[T](implicit t : TypeTag[T]) : Type = {
		val runtimeClass = runtimeMirror(getClass.getClassLoader).runtimeClass(t.tpe)
		$(if (runtimeClass.getName.contains('[')) typeNameFromDescriptor(runtimeClass.getName.replace('.', '/')) else runtimeClass.getName)
	}

	def Init(args : Type*) = "<init>" :: MethodType($[Unit], args : _*)

	def defaultConstructor = Init()(
		ALOAD_0,
		INVOKESPECIAL($[Object] >> Init()),
		RETURN
	)

	implicit class UnitExt(u : Unit) {
		def ->(returnType : Type) = MethodType(returnType)
	}

	implicit def TypeToVerificationTypeInfo(t : Type) = t match {
		case i if (Seq($[Int], $[Short], $[Boolean], $[Char]) contains i) ⇒ IntegerType
		case f if (f == $[Float]) ⇒ FloatType
		case l if (l == $[Long]) ⇒ LongType
		case d if (d == $[Double]) ⇒ DoubleType
		case _ ⇒ new ObjectVariableInfo(t)
	}

	implicit def FieldFromSignature(s : Signature[Field]) = Field(s, Visibility.Private, Set(), Set())
	implicit class MethodFromSignature(s : Signature[Method]) {
		def apply(instructions : Instruction*) = {
			val code = Code(instructions, Seq())
			val answer = Method(s, Visibility.Public, Set(), Set(code))

			code.owner = answer

			answer
		}
	}

	implicit def SlotRef_to_Bootstrap(handle : MethodHandle) = Bootstrap(handle)

	type BootstrapArgument = Data[_] with Type with MethodType with MethodHandle //TODO: Estas definiciones no sirven
	type Label = String
}
