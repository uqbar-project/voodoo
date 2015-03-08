package org.uqbar.voodoo.builder

import org.uqbar.voodoo.model.constants._
import org.uqbar.voodoo.model._
import scala.collection.mutable.ListBuffer
import scala.Option.option2Iterable
import org.uqbar.voodoo.StringInterpreter._

class ConstantPoolBuilder(initialEntries : ConstantPoolEntry*) {

	protected val entries = ListBuffer[Option[ConstantPoolEntry]](None)

	initialEntries foreach +=

	def +=(c : ConstantPoolEntry) : ConstantPoolBuilder = {
		if (!entries.contains(Some(c))) {
			entries += Some(c)

			c match {
				case Type(name) ⇒ this += UTF8(binaryNameFromTypeName(name))
				case SlotRef(owner, _, signature) ⇒
					this += owner
					this += signature
				case s : MethodType ⇒
					this += UTF8(descriptorFromName(s))
				case Signature(selector, signatureType) ⇒
					this += UTF8(selector)
					this += UTF8(descriptorFromName(signatureType))
				case Data(s : String) ⇒ this += UTF8(s)
				case Data(_ : Long) | Data(_ : Double) ⇒
					entries += None
				case MethodHandleRef(m : MethodHandle) ⇒
					this += m.slot
				case InvokeDynamic(_, signature) ⇒
					this += signature
				case _ ⇒
			}
		}
		this
	}

	def build = new ConstantPool(entries : _*)
}