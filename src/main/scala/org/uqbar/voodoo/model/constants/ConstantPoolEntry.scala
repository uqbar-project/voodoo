package org.uqbar.voodoo.model.constants

import org.uqbar.voodoo.Globals._
import org.uqbar.voodoo.model._

case class ConstantPool(entries : Option[ConstantPoolEntry]*) {
	val constantCount = entries.size - 1

	def apply[T <% ConstantPoolEntry](index : Int) : T = get(index).getOrElse(throw new NoSuchElementException(s"$index")).asInstanceOf[T]

	def get(index : Int) : Option[ConstantPoolEntry] = if (index <= constantCount) entries(index) else None

	def indexOf(target : ConstantPoolEntry) =
		if (entries.contains(Some(target))) entries.indexOf(Some(target))
		else throw new NoSuchElementException(target.toString)

	def foreach(f : ConstantPoolEntry ⇒ Unit) = entries.foreach(_.map(f))
}

// ****************************************************************
// ** ENTRIES
// ****************************************************************

trait ConstantPoolEntry

trait MethodHandle { //TODO: Usar directamente SlotRef?
	def slot : SlotRef[_ <: Slot]
}

case class UTF8(content : String) extends ConstantPoolEntry

case class Data[T >: DataType](content : T) extends ConstantPoolEntry {
	override def equals(o : Any) = o.isInstanceOf[Data[T]] && {
		val otherConentent = o.asInstanceOf[Data[T]].content
		otherConentent.getClass == content.getClass && otherConentent == content
	}

	override def hashCode = content.hashCode
}

case class MethodHandleRef(handle : MethodHandle) extends ConstantPoolEntry //TODO: usar MethodHandle directamente?

case class InvokeDynamic(bootstrapIndex : Int, signature : Signature[Method]) extends ConstantPoolEntry //TODO: cambiar el index por el bootstrap