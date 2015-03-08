package org.uqbar.voodoo.model

import scala.reflect.runtime.universe.TypeTag.Object
import scala.reflect.runtime.universe.TypeTag.Unit
import scala.language.existentials
import org.uqbar.voodoo.model.constants.ConstantPoolEntry
import org.uqbar.voodoo.model.constants.DataType

case class Class(
		version : ClassVersion,
		visibility : Visibility,
		metatype : Metatype,
		modifiers : Set[ClassModifier],
		ownType : Type,
		superType : Type,
		interfaces : Set[Type],
		slots : Set[Slot],
		_attributes : Set[Attribute[Class]]) extends ClassMember {

	def name = ownType.name

	def bootstrapMethods : Seq[Bootstrap] = for {
		m ← methods.toSeq
		INVOKEDYNAMIC(bootstrap, _) ← m.code.instructions
	} yield bootstrap

	def fields = slots collect { case f : Field ⇒ f }
	def field(signature : Signature[Field]) = fields.find(signature == _.signature).get

	def methods = slots collect { case m : Method ⇒ m }
	def method(signature : Signature[Method]) = methods.find(signature == _.signature).get

	def >>(signature : Signature[Method]) = method(signature)

	def attributes = _attributes + BootstrapMethods(bootstrapMethods)
}

trait Slot extends ClassMember {
	def signature : Signature[_ <: Slot]
	def visibility : Visibility

	var owner : Class = _

	def selector = signature.selector
}

case class Field(
		signature : Signature[Field],
		visibility : Visibility,
		modifiers : Set[FieldModifier],
		attributes : Set[Attribute[Field]]) extends Slot {

	def constantValue[T >: DataType] = attributes.collectFirst{ case a : ConstantValue[T] ⇒ a }
	def constantValue_=(a : ConstantValue[_]) = copy(attributes = attributes + a)

	def as(ms : Modifier[_ <: Field]*) = copy( //TODO: What if wee just remove the as method?
		visibility = ms.find(_.isInstanceOf[Visibility]).fold(visibility)(_.asInstanceOf[Visibility]),
		modifiers = modifiers ++ ms.collect{ case m : FieldModifier ⇒ m }
	)
}

case class Method(
		signature : Signature[Method],
		visibility : Visibility,
		modifiers : Set[MethodModifier],
		attributes : Set[Attribute[Method]]) extends Slot {

	def isConstructor = selector == Init().selector

	def code = attribute[Code].get

	def as(ms : Modifier[_ <: Method]*) = { //TODO: What if wee just remove the as method?
		val answer = copy(
			visibility = ms.collectFirst{ case m : Visibility ⇒ m }.getOrElse(visibility),
			modifiers = modifiers ++ ms.collect{ case m : MethodModifier ⇒ m },
			attributes = attributes.map{
				case code : Code ⇒ code.copy()
				case other ⇒ other
			}
		)

		//TODO: Setear arriba
		answer.attributes.collect{ case c : Code ⇒ c.owner = answer }

		answer
	}
}

case class SlotRef[+T <: Slot](owner : Type, fromInterface : Boolean, signature : Signature[T]) extends ConstantPoolEntry {
	override def toString = s"$owner ${if (fromInterface) ">>>" else ">>"} $signature"
}

case class Signature[+T <: Slot](selector : String, signatureType : SignatureType[T]) extends ConstantPoolEntry {
	override def toString = s"$selector :: $signatureType"
}

sealed trait SignatureType[+T <: Slot] {
	def ::(selector : String) = Signature(selector, this)
}

case class Type(name : String) extends ConstantPoolEntry with SignatureType[Field] {
	def ->(t : Type) = if (this != $[Unit]) MethodType(t, this) else throw new IllegalArgumentException
	def >>[T <: Slot](s : Signature[T]) = SlotRef(this, false, s)
	def >>>(s : Signature[Method]) = SlotRef(this, true, s)

	lazy val size = if (this == $[Double] || this == $[Long]) 2 else 1 //TODO: Mover o usar en todos lados

	def arrayed = $(name + "[]")
	def unarrayed = if (name.endsWith("[]")) $(name.dropRight(2)) else this

	override def toString = "$" + name
}

case class MethodType(returnType : Type, argumentTypes : Type*) extends ConstantPoolEntry with SignatureType[Method] {
	def ->(t : Type) = if (returnType != $[Unit]) MethodType(t, argumentTypes :+ returnType : _*) else throw new IllegalArgumentException

	override def toString = argumentTypes.mkString(if (argumentTypes.isEmpty) "()" else "", " -> ", " -> ") + returnType
}