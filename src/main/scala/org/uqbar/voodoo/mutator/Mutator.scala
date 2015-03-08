package org.uqbar.voodoo.mutator

import org.uqbar.voodoo.model._
import org.uqbar.voodoo.model.Visibility._
import scala.collection.mutable.Set

object ClassMutator {

  def apply(target : Class)(changes : ClassMutator ⇒ Unit) = {
    val mutator = new ClassMutator(target.ownType)

    mutator.superType = target.superType
    mutator.version = target.version
    mutator.metatype = target.metatype
    mutator.visibility = target.visibility
    target.slots.map{mutator += _}
    target.modifiers.map{mutator += _}
    target.interfaces.map{mutator += _}
    target.attributes.map{mutator += _}

    changes(mutator)
    mutator()
  }

	def apply(target : Type)(changes : ClassMutator ⇒ Unit) = {
		val mutator = new ClassMutator(target)
		changes(mutator)
		mutator()
	}
}

class ClassMutator(ownType : Type) {
	var superType : Type = $[Object]
	var version : ClassVersion = ClassVersion.Java_7
	var metatype : Metatype = Metatype.Class
	var visibility : Visibility = Public

	protected val slots = Set[Slot]()
	protected val modifiers = Set[ClassModifier]()
	protected val interfaces = Set[Type]()
	protected val attributes = Set[Attribute[Class]]()

	// ****************************************************************
	// ** DEFINITION
	// ****************************************************************

	def +=(slot : Slot) {
	  slots.find(_.signature == slot.signature).map(slots -= _)
	  slots += slot
	}
	def -=(slot : Slot) = slots -= slot

	def +=(modifier : ClassModifier) = modifiers += modifier
	def -=(modifier : ClassModifier) = modifiers -= modifier

	def +=(interface : Type) = interfaces += interface
	def -=(interface : Type) = interfaces -= interface

	def +=(attribute : Attribute[Class]) = attributes += attribute
	def -=(attribute : Attribute[Class]) = attributes -= attribute

	// ****************************************************************
	// ** CONSTRUCTION
	// ****************************************************************

	def apply() = {
		val finalSlots = slots.collectFirst{ case m : Method if m.isConstructor ⇒ m }.fold(slots + defaultConstructor)(_ ⇒ slots).toSet

		val answer = Class(version, visibility, metatype, modifiers.toSet, ownType, superType, interfaces.toSet, finalSlots, attributes.toSet)

		finalSlots.map(_.owner = answer)

		answer
	}
}