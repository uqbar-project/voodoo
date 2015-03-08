package org.uqbar.voodoo.model

case class ClassVersion(minor : Int, major : Int)
object ClassVersion {
	final val Java_1 = ClassVersion(0, 45)
	final val Java_2 = ClassVersion(0, 46)
	final val Java_3 = ClassVersion(0, 47)
	final val Java_4 = ClassVersion(0, 48)
	final val Java_5 = ClassVersion(0, 49)
	final val Java_6 = ClassVersion(0, 50)
	final val Java_7 = ClassVersion(0, 51)
}

//TODO: Apply method to ask if it is contained in target?
trait Modifier[-T]

trait Visibility extends Modifier[ClassMember]
object Visibility {
	case object Public extends Visibility
	case object Private extends Visibility
	case object Protected extends Visibility
}

trait Metatype extends Modifier[Class]
object Metatype {
	case object Enum extends Metatype
	case object Class extends Metatype
	case object Interface extends Metatype
	case object Annotation extends Metatype
}

trait ClassModifier extends Modifier[Class]
object ClassModifier {
	case object Super extends ClassModifier
	case object Abstract extends ClassModifier
	case object Synthetic extends ClassModifier
	case object Final extends ClassModifier
	case object Static extends ClassModifier
}

trait FieldModifier extends Modifier[Field]
object FieldModifier {
	case object Super extends FieldModifier
	case object Abstract extends FieldModifier
	case object Enum extends FieldModifier
	case object Volatile extends FieldModifier
	case object Transient extends FieldModifier
	case object Synthetic extends FieldModifier
	case object Final extends FieldModifier
	case object Static extends FieldModifier
}
trait MethodModifier extends Modifier[Method]
object MethodModifier {
	case object Super extends MethodModifier
	case object Abstract extends MethodModifier
	case object Native extends MethodModifier
	case object Strict extends MethodModifier
	case object Synchronized extends MethodModifier
	case object Bridge extends MethodModifier
	case object Varargs extends MethodModifier
	case object Synthetic extends MethodModifier
	case object Final extends MethodModifier
	case object Static extends MethodModifier
}