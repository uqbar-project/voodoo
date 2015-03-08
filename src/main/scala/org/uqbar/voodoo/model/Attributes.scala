package org.uqbar.voodoo.model

import org.uqbar.voodoo.model.constants._
import org.uqbar.voodoo.model.MethodModifier._
import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.universe.runtimeMirror
import scala.reflect.runtime.universe._

sealed trait Attributable {
	def attributes : Set[_ <: Attribute[_ <: Attributable]]

	def attribute[T : TypeTag] = attributes collectFirst {
		case a if runtimeMirror(getClass.getClassLoader).reflect(a).symbol.toType <:< typeOf[T] ⇒ a.asInstanceOf[T]
	}
}

trait ClassMember extends Attributable

trait Attribute[+Attributable]

trait CustomAttribute[T <: Attributable] extends Attribute[T] {
	def byteSize : Int
	def label : String
	def bytes : Array[Byte]
}

// ****************************************************************
// ** FIELD ATTRIBUTES
// ****************************************************************

case class ConstantValue[T >: DataType](value : T) extends Attribute[Field] //TODO; Unificar con Data?

// ****************************************************************
// ** METHOD ATTRIBUTES
// ****************************************************************

case class Code(instructions : Seq[Instruction], exceptionHandlers : Seq[ExceptionHandler] = Seq(), baseAttributes : Set[Attribute[Code]] = Set()) extends Attributable with Attribute[Method] { //TODO: Fix _Attributes
	var owner : Method = _

	def maxStack = stackMapTable.maxStack

	def maxLocals = stackMapTable.maxLocals

	def attributes = baseAttributes + stackMapTable

	lazy val stackMapTable = StackMapTable(this)
}

case class Exceptions(checkedExceptions : Type*) extends Attribute[Method]
case class RuntimeVisibleParameterAnnotations(parameterAnnotations : Seq[Annotation]*) extends Attribute[Method]
case class RuntimeInvisibleParameterAnnotations(parameterAnnotations : Seq[Annotation]*) extends Attribute[Method]
case class AnnotationDefault(value : Any) extends Attribute[Method] //TODO: Restringir a Data[_] | Slot /*Enum values*/ | Type | Annotation | List[ValueElement]

// ****************************************************************
// ** CODE ATTRIBUTES
// ****************************************************************

case class LineNumberTable(entries : Map[Int, Int]) extends Attribute[Code] //TODO: El mapa relaciona Linea con instruccion. No usar indices para la instrucción?
case class LocalVariableTable(entries : LocalVariableRef*) extends Attribute[Code]
case class LocalVariableTypeTable(entries : LocalVariableRef*) extends Attribute[Code]

// ****************************************************************
// ** CLASS ATTRIBUTES
// ****************************************************************

case class InnerClasses(classes : ClassIdentifier*) extends Attribute[Class]
case class EnclosingMethod(method : SlotRef[Method]) extends Attribute[Class]
case class SourceFile(name : String) extends Attribute[Class]
case class SourceDebugExtension(info : String) extends Attribute[Class]
case class BootstrapMethods(bootstraps : Seq[Bootstrap]) extends Attribute[Class]

// ****************************************************************
// ** CLASS MEMBER
// ****************************************************************

case object Synthetic extends Attribute[ClassMember]
case class SignatureRef(signature : String) extends Attribute[ClassMember] //TODO: Improve
case object Deprecated extends Attribute[ClassMember]
case class RuntimeVisibleAnnotations(annotations : Annotation*) extends Attribute[ClassMember]
case class RuntimeInvisibleAnnotations(annotations : Annotation*) extends Attribute[ClassMember]

// ****************************************************************
// ** COMPLEMENTARY STRUCTURES
// ****************************************************************

case class Bootstrap(handle : MethodHandle, arguments : BootstrapArgument*)
case class ExceptionHandler(start : Label, end : Label, handlerStart : Label, catchType : Type)
case class ClassIdentifier(visibility : Visibility, metatype : Metatype, modifiers : Set[ClassModifier], ownType : Type, outerType : Type) //TODO: Unify with Class?
case class Annotation(ownType : Type, values : Map[String, Any]) //TODO: Restringir a Data[_] | Slot /*Enum values*/ | Type | Annotation | List[ValueElement]
case class LocalVariableRef(start : Int, length : Int, localIndex : Int, signature : Signature[Field]) //TODO start & length(end) as labels?