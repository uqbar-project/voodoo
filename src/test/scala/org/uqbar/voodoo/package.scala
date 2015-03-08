package org.uqbar

import scala.reflect.runtime.universe

import org.uqbar.voodoo.model.$
import org.uqbar.voodoo.model.ClassModifier
import org.uqbar.voodoo.model.ClassVersion
import org.uqbar.voodoo.model.ConstantValue
import org.uqbar.voodoo.model.FieldFromSignature
import org.uqbar.voodoo.model.FieldModifier.Enum
import org.uqbar.voodoo.model.FieldModifier.Static
import org.uqbar.voodoo.model.FieldModifier.Transient
import org.uqbar.voodoo.model.FieldModifier.Volatile
import org.uqbar.voodoo.model.GETFIELD
import org.uqbar.voodoo.model.Metatype
import org.uqbar.voodoo.model.UnitExt
import org.uqbar.voodoo.model.Visibility
import org.uqbar.voodoo.model.Visibility.Private
import org.uqbar.voodoo.model.constants.ConstantPool
import org.uqbar.voodoo.model.constants.Data
import org.uqbar.voodoo.model.constants.InvokeDynamic
import org.uqbar.voodoo.model.constants.MethodHandleRef
import org.uqbar.voodoo.model.constants.UTF8

package object voodoo {

	lazy final val CLASS_VERSION_SAMPLE_FILE = getClass.getResource("/bytecode/ClassVersion").getFile
	lazy final val CLASS_VERSION_SAMPLE = ClassVersion.Java_7

	lazy final val CONSTANT_POOL_SAMPLE_FILE = getClass.getResource("/bytecode/ConstantPool").getFile
	lazy final val CONSTANT_POOL_SAMPLE = ConstantPool(
		None,
		Some($("Foo")),
		Some(UTF8("Foo")),
		Some(($("Foo") >> "bar" :: $[String])),
		Some(("bar" :: $[String])),
		Some(UTF8("bar")),
		Some(UTF8("Ljava/lang/String;")),
		Some(($("Foo") >>> "getBar" :: () -> $[String])),
		Some(("getBar" :: () -> $[String])),
		Some(UTF8("getBar")),
		Some(UTF8("()Ljava/lang/String;")),
		Some(($("Foo") >> "setBar" :: $[String] -> $[Unit])),
		Some(("setBar" :: $[String] -> $[Unit])),
		Some(UTF8("setBar")),
		Some(UTF8("(Ljava/lang/String;)V")),
		Some(Data("bar")),
		Some(Data(1)),
		Some(Data(1.0f)),
		Some(Data(1L)),
		None,
		Some(Data(1.0)),
		None,
		Some(MethodHandleRef(GETFIELD($("Foo") >> "bar" :: $[String]))),
		Some((() -> $[String])),
		Some(InvokeDynamic(0, "getBar" :: () -> $[String])),
		Some(UTF8("ConstantValue")),
		Some(Data("ConstantValue"))
	)

	lazy final val ACCESS_FLAGS_SAMPLE_FILE = getClass.getResource("/bytecode/AccessFlags").getFile
	lazy final val ACCESS_FLAGS_SAMPLE = (Visibility.Protected, Metatype.Annotation, Set(ClassModifier.Final, ClassModifier.Synthetic))

	lazy final val TYPE_SAMPLE_FILE = getClass.getResource("/bytecode/Type").getFile
	lazy final val TYPE_SAMPLE = $("Foo")

	lazy final val INTERFACES_SAMPLE_FILE = getClass.getResource("/bytecode/Interfaces").getFile
	lazy final val INTERFACES_SAMPLE = Set($("Foo"))

	lazy final val FIELDS_SAMPLE_FILE = getClass.getResource("/bytecode/Fields").getFile
	lazy final val FIELDS_SAMPLE = Set(
		("bar" :: $[String]).as(Private, Static, Volatile, Transient, Enum).constantValue = ConstantValue("ConstantValue")
	)
}