package org.uqbar.voodoo

import org.uqbar.voodoo.model._

package object mutator {

  implicit class MutableClass(target : Class) {
    def let(changes : ClassMutator ⇒ Unit) = ClassMutator(target)(changes)
  }

  implicit class MutableType(target : Type) {
    def let(changes : ClassMutator ⇒ Unit) = ClassMutator(target)(changes)
  }

}