package org.uqbar.voodoo.parser

import scala.reflect.runtime.universe.{ typeOf, runtimeMirror, TypeTag ⇒ TT }
import java.io.DataInputStream
import org.uqbar.Utils._
import org.uqbar.voodoo.Globals._
import org.uqbar.voodoo.StringInterpreter._
import org.uqbar.voodoo.model._
import org.uqbar.voodoo.model.constants._
import scala.collection.mutable.ListBuffer
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object ClassParser extends ClassParser
trait ClassParser {

	implicit class ExtendedDataInputStream(input : DataInputStream) {
		def readMany[T](readFunction : ⇒ T) = (1 to input.readUnsignedShort).map { _ ⇒ readFunction }
		def readConstant[T <: ConstantPoolEntry](cp : ConstantPool) = cp[T](input.readUnsignedShort)
	}


	def parseClass(filePath : String) : Class = {
	    var answer : Class = null

	    for(input <- new DataInputStream(new FileInputStream(filePath))) {
	      answer = parseClass(input)
	    }

	    answer
	}

	def parseClass(inputStream : InputStream) : Class = {
	  var answer : Class = null

	  for(input <- new DataInputStream(inputStream)){
  		parseMagic(input)
  		val version = parseVersion(input)
  		val constantPool = parseConstantPool(input)
  		val (visibility, metatype, modifiers) = parseAccessFlags(input)
  		val ownType = parseType(constantPool)(input)
  		val superType = parseType(constantPool)(input)
  		val interfaces = parseInterfaces(constantPool)(input)
  		val fields = parseFields(constantPool)(input)
  		val methodBuilders = parseMethods(constantPool)(input)
  		val attributes = parseAttributes[Class](constantPool)(input).map(_.apply(None)).toSet.filter(_ != null) //TODO: Bosta
  		val bootstraps = attributes.find(_.isInstanceOf[BootstrapMethods]).asInstanceOf[Option[BootstrapMethods]]
  		val methods = methodBuilders.map(mb ⇒ mb(bootstraps))

  		answer = Class(version, visibility, metatype, modifiers, ownType, superType, interfaces, fields ++ methods, attributes.filterNot(_.isInstanceOf[BootstrapMethods])) //TODO: Reemplazar null por option?

  		methods.foreach{m =>
  		  m.owner = answer
  		  m.attribute[Code].map(_.owner = m)
  		}
	  }

	  answer
	}

	protected def parseMagic(input : DataInputStream) = assume(input.readInt == MAGIC)

	protected def parseVersion(input : DataInputStream) = ClassVersion(input.readUnsignedShort, input.readUnsignedShort)

	protected def parseConstantPool(input : DataInputStream) = {
		var entries : List[(Int, () ⇒ ConstantPoolEntry)] = Nil

		def parseEntry(tag : ConstantTag) = {
			def get[T <: ConstantPoolEntry](index : Int) : T = entries.find(_._1 == index).getOrElse(throw new NoSuchElementException)._2().asInstanceOf[T]

			def mkType(nameIndex : Int)() = $(typeNameFromBinaryName(get[UTF8](nameIndex).content))
			def mkSlotRef(classIndex : Int, fromInterface : Boolean, signatureIndex : Int)() = SlotRef(get(classIndex), fromInterface, get(signatureIndex))
			def mkString(stringIndex : Int)() = Data(get[UTF8](stringIndex).content)
			def mkData(content : Any)() = Data(content)
			def mkSignature(selectorIndex : Int, descriptorIndex : Int)() = Signature(get[UTF8](selectorIndex).content, signatureTypeFromDescriptor(get[UTF8](descriptorIndex).content))
			def mkUTF8(content : String)() = UTF8(content)
			def mkMethodHandle(referenceKind : ReferenceKind, referenceIndex : Int)() = new MethodHandleRef(referenceOfKind(referenceKind, get(referenceIndex)))
			def mkMethodType(descriptorIndex : Int)() = methodTypeFromDescriptor(get[UTF8](descriptorIndex).content)
			def mkInvokeDynamic(bootstrapMethodAttributeIndex : Int, signatureIndex : Int)() = InvokeDynamic(bootstrapMethodAttributeIndex, get(signatureIndex))

			tag match {
				case TypeTag ⇒ mkType(input.readUnsignedShort) _
				case FieldTag | MethodTag ⇒ mkSlotRef(input.readUnsignedShort, false, input.readUnsignedShort) _
				case InterfaceMethodTag ⇒ mkSlotRef(input.readUnsignedShort, true, input.readUnsignedShort) _
				case StringTag ⇒ mkString(input.readUnsignedShort) _
				case IntTag ⇒ mkData(input.readInt) _
				case FloatTag ⇒ mkData(input.readFloat) _
				case LongTag ⇒ mkData(input.readLong) _
				case DoubleTag ⇒ mkData(input.readDouble) _
				case SignatureTag ⇒ mkSignature(input.readUnsignedShort, input.readUnsignedShort) _
				case UTF8Tag ⇒ mkUTF8(input.readUTF) _
				case MethodHandleTag ⇒ mkMethodHandle(input.readUnsignedByte, input.readUnsignedShort) _
				case MethodTypeTag ⇒ mkMethodType(input.readUnsignedShort) _
				case InvokeDynamicTag ⇒ mkInvokeDynamic(input.readUnsignedShort, input.readUnsignedShort) _
			}
		}

		val constantCount = input.readUnsignedShort

		var n = 1
		while (n < constantCount) {
			val tag = input.readUnsignedByte

			entries = n -> parseEntry(tag) :: entries

			n += (tag match {
				case LongTag | DoubleTag ⇒ 2
				case _ ⇒ 1
			})
		}

		val realEntries = entries.map(e ⇒ e._1 -> Some(e._2())).toMap
		val fullEntries = (0 until constantCount).map{ i ⇒
			realEntries.get(i) match {
				case None ⇒ None
				case Some(some) ⇒ some
			}
		}
		new ConstantPool(fullEntries : _*)
	}

	protected def parseAccessFlags(input : DataInputStream) = {
		val code = input.readUnsignedShort

		val visibility = {
			val i = fromFlag[Visibility](code).iterator
			if (i.hasNext) i.next else Visibility.Protected
		}
		val metatype = {
			val ms = fromFlag[Metatype](code)
			if (ms contains (Metatype.Annotation)) Metatype.Annotation
			else if (ms.isEmpty) Metatype.Class
			else ms.iterator.next
		}
		val modifiers = fromFlag[ClassModifier](code)

		(visibility, metatype, modifiers)
	}

	protected def parseType(cp : ConstantPool)(input : DataInputStream) = input.readConstant[Type](cp)

	protected def parseInterfaces(cp : ConstantPool)(input : DataInputStream) = input.readMany{ input.readConstant[Type](cp) }.toSet

	protected def parseFields(cp : ConstantPool)(input : DataInputStream) = input.readMany {
		val flags = input.readUnsignedShort
		val name = input.readConstant[UTF8](cp).content
		val signatureType = typeFromDescriptor(input.readConstant[UTF8](cp).content)
		val attributes = parseAttributes[Field](cp)(input).map(_.apply(None)).filter(_ != null).toSet //TODO: Bosta

		Field(name :: signatureType, fromFlag[Visibility](flags).iterator.next, fromFlag[FieldModifier](flags), attributes)
	}.toSet

	protected def parseMethods(cp : ConstantPool)(input : DataInputStream) = input.readMany {
		val flags = input.readUnsignedShort
		val name = input.readConstant[UTF8](cp).content
		val signatureType = methodTypeFromDescriptor(input.readConstant[UTF8](cp).content)
		val attributes = parseAttributes[Method](cp)(input)

		{ bootstraps : Option[BootstrapMethods] ⇒ Method(name :: signatureType, fromFlag[Visibility](flags).iterator.next, fromFlag[MethodModifier](flags), attributes.map(f ⇒ f(bootstraps)).filter(_ != null).toSet) } //TODO: Bosta
	}

	protected def parseAttributes[T <: Attributable](cp : ConstantPool)(input : DataInputStream) : Set[Option[BootstrapMethods] ⇒ Attribute[T]] =
		input.readMany {
			val name : String = input.readConstant[UTF8](cp).content
			val size = input.readInt
			val data = new Array[Byte](size)
			input.read(data)

			parseAttribute(name, size)(cp)(new DataInputStream(new ByteArrayInputStream(data)))
		}.toSet

	protected def parseAttribute[T <: Attributable](name : String, size : Int)(cp : ConstantPool)(input : DataInputStream) : Option[BootstrapMethods] ⇒ Attribute[T] = { bootstraps ⇒
		val attribute = (name match {
			case "ConstantValue" ⇒ ConstantValue(input.readConstant[Data[_]](cp).content)
			case "Code" ⇒
				input.readUnsignedShort
				input.readUnsignedShort

				var instructions = parseInstructions(bootstraps)(input.readInt)(cp)(input)

				val exceptionHandlers = input.readMany {
					def parseInstructionLabel = {
						val targetIndex = indexOfOffset(input.readUnsignedShort)(instructions)
						instructions(targetIndex) match {
							case LabeledInstruction(label, _) ⇒ label
							case instruction ⇒
								val wrappedInstruction = LabeledInstruction(targetIndex.toString, instruction)
								instructions = instructions.updated(targetIndex, wrappedInstruction)
								wrappedInstruction.label
						}
					}

					ExceptionHandler(parseInstructionLabel, parseInstructionLabel, parseInstructionLabel, input.readConstant(cp))
				}

				val attributes = parseAttributes[Code](cp)(input)

				Code(instructions, exceptionHandlers, attributes.map(f ⇒ f(bootstraps)).filter(_ != null)) //TODO: Bosta

			case "StackMapTable" ⇒
				input.read(new Array[Byte](size))
				null //TODO: improve this crap

			case "Exceptions" ⇒ Exceptions(input.readMany{ input.readConstant(cp) } : _*)

			case "InnerClasses" ⇒ input.readMany{
				val innerType = cp(input.readUnsignedShort)
				val outerType = cp(input.readUnsignedShort)
				input.readShort
				val accessflags = parseAccessFlags(input)

				ClassIdentifier(accessflags._1, accessflags._2, accessflags._3, innerType, outerType)
			}

			case "EnclosingMethod" ⇒ EnclosingMethod(input.readConstant[Type](cp) >> input.readConstant(cp))

			case "Synthetic" ⇒ Synthetic

			case "Signature" ⇒ SignatureRef(input.readConstant[UTF8](cp).content)

			case "SourceFile" ⇒ SourceFile(input.readConstant[UTF8](cp).content)

			case "SourceDebugExtension" ⇒
				val info = new Array[Byte](size)
				input.read(info)
				SourceDebugExtension(info.map(_.toChar).mkString)

			case "LineNumberTable" ⇒ LineNumberTable(input.readMany{ input.readUnsignedShort -> input.readUnsignedShort }.toMap)

			case "LocalVariableTable" ⇒ LocalVariableTable(input.readMany {
				val start = input.readUnsignedShort
				val length = input.readUnsignedShort
				val signature = input.readConstant[UTF8](cp).content :: typeFromDescriptor(input.readConstant[UTF8](cp).content)
				val index = input.readUnsignedShort

				LocalVariableRef(start, length, index, signature)
			} : _*)

			case "LocalVariableTypeTable" ⇒ LocalVariableTypeTable(input.readMany {
				val start = input.readUnsignedShort
				val length = input.readUnsignedShort
				val signature = input.readConstant[UTF8](cp).content :: typeFromDescriptor(input.readConstant[UTF8](cp).content)
				val index = input.readUnsignedShort

				LocalVariableRef(start, length, index, signature)
			} : _*)

			case "Deprecated" ⇒ Deprecated

			case "RuntimeVisibleAnnotations" ⇒ RuntimeVisibleAnnotations(input.readMany{ parseAnnotation(cp)(input) } : _*)

			case "RuntimeInvisibleAnnotations" ⇒ RuntimeInvisibleAnnotations(input.readMany{ parseAnnotation(cp)(input) } : _*)

			case "RuntimeVisibleParameterAnnotations" ⇒ RuntimeVisibleParameterAnnotations((0 until input.readUnsignedByte).map{ _ ⇒
				input.readMany{ parseAnnotation(cp)(input) }
			} : _*)

			case "RuntimeInvisibleParameterAnnotations" ⇒ RuntimeInvisibleParameterAnnotations((0 until input.readUnsignedByte).map{ _ ⇒
				input.readMany{ parseAnnotation(cp)(input) }
			} : _*)

			case "AnnotationDefault" ⇒ AnnotationDefault(parseAnnotationValue(cp)(input))

			case "BootstrapMethods" ⇒
				BootstrapMethods(input.readMany {
					Bootstrap(input.readConstant[MethodHandleRef](cp).handle, input.readMany{ input.readConstant(cp) } : _*)
				})

			case _ ⇒ null //TODO: CustomAttribute
		})

		input.close

		attribute.asInstanceOf[Attribute[T]] //TODO: Remove cast
	}

	protected def parseAnnotation(cp : ConstantPool)(input : DataInputStream) = Annotation($(input.readConstant[UTF8](cp).content), input.readMany{
		input.readConstant[UTF8](cp).content -> parseAnnotationValue(cp)(input)
	}.toMap)

	protected def parseAnnotationValue(cp : ConstantPool)(input : DataInputStream) : Any = input.readUnsignedByte.toChar match { //TODO: Mover los caracteres a un solo lugar (repetido en writer)
		case 'B' | 'C' | 'D' | 'F' | 'I' | 'J' | 'S' | 'Z' | 's' ⇒ input.readConstant[Data[_]](cp)
		case 'e' ⇒ input.readConstant[UTF8](cp).content -> input.readConstant[UTF8](cp).content
		case 'c' ⇒ typeFromDescriptor(input.readConstant[UTF8](cp).content)
		case '@' ⇒ parseAnnotation(cp)(input)
		case '[' ⇒ input.readMany{ parseAnnotationValue(cp)(input) }
	}

	protected def parseInstructions(bootstraps : Option[BootstrapMethods])(instructionsByteSize : Int)(cp : ConstantPool)(input : DataInputStream) = {
		def parseInstructions(codeOffset : Int) : Seq[Instruction] = if (codeOffset == instructionsByteSize) Seq()
		else {
			val instruction = parseInstruction(bootstraps)(codeOffset)(cp)(input)
			instruction +: parseInstructions(codeOffset + byteSize(instruction, codeOffset))
		}

		val unlabeledInstructions = parseInstructions(0)

		unlabeledInstructions.foldLeft(Seq[Instruction](), 0){
			case ((answer, codeOffset), instruction) ⇒
				val label = codeOffset.toString
				val wrappedInstruction = if (unlabeledInstructions.exists{
					case i : Jump ⇒ i.jumpTarget == label
					case LOOKUPSWITCH(default, jumps @ _*) ⇒ default == label || jumps.exists(_._2 == label)
					case TABLESWITCH(_, default, jumps @ _*) ⇒ default == label || jumps.contains(label)
					case _ ⇒ false
				}) codeOffset.toString @: instruction else instruction

				(answer ++ Seq(wrappedInstruction), codeOffset + byteSize(instruction, codeOffset))
		}._1
	}

	protected def parseInstruction(bootstraps : Option[BootstrapMethods])(codeOffset : Int)(cp : ConstantPool)(input : DataInputStream) : Instruction = {
		def seedType(s : Any) = runtimeMirror(getClass.getClassLoader).staticClass(s.getClass.getName).selfType

		instructionFromFlag(input.readUnsignedByte) match {
			case t if (seedType(t) <:< typeOf[_ ⇒ LocalAccessor]) ⇒ t.asInstanceOf[Int ⇒ LocalAccessor](input.readUnsignedByte)
			case t @ IINC ⇒ t(input.readUnsignedByte, input.readByte)

			case t @ GOTO_W ⇒ t((codeOffset + input.readInt).toString)
			case t @ JSR_W ⇒ t((codeOffset + input.readInt).toString)
			case t if (seedType(t) <:< typeOf[_ ⇒ Jump]) ⇒ t.asInstanceOf[Label ⇒ Jump]((codeOffset + input.readShort).toString)

			case t @ NEWARRAY ⇒ t(arrayTypeFromFlag(input.readUnsignedByte))
			case t @ NEW ⇒ t(input.readConstant(cp))
			case t @ ANEWARRAY ⇒ t(input.readConstant(cp))
			case t @ CHECKCAST ⇒ t(input.readConstant(cp))
			case t @ INSTANCEOF ⇒ t(input.readConstant(cp))
			case t @ MULTIANEWARRAY ⇒
				val arrayedType : Type = input.readConstant(cp)
				def dimensions(tpe : Type) : Int = if (tpe.name.endsWith("[]")) 1 + dimensions($(tpe.name.dropRight(2))) else 0
				val baseType = (0 until dimensions(arrayedType)).foldLeft(arrayedType){ (tpe, _) ⇒ tpe.unarrayed }
				t(baseType, dimensions(arrayedType), input.readUnsignedByte)

			case t if (seedType(t) <:< typeOf[_ ⇒ FieldAccessor]) ⇒ t.asInstanceOf[Field ⇒ FieldAccessor](input.readConstant(cp))

			case t @ INVOKESPECIAL ⇒ t(input.readConstant(cp))
			case t @ INVOKESTATIC ⇒ t(input.readConstant(cp))
			case t @ INVOKEVIRTUAL ⇒ t(input.readConstant(cp))
			case t @ INVOKEDYNAMIC ⇒
				val invDyn : InvokeDynamic = input.readConstant(cp)
				input.readShort
				t(bootstraps.get.bootstraps(invDyn.bootstrapIndex), invDyn.signature)
			case t @ INVOKEINTERFACE ⇒
				val i = t(input.readConstant(cp))
				input.readShort
				i
			case t @ BIPUSH ⇒ t(input.readByte)
			case t @ LDC ⇒ t(cp[Data[_]](input.readUnsignedByte).content)
			case t @ LDC_W ⇒ t(input.readConstant[Data[_]](cp).content)
			case t @ LDC2_W ⇒ t(input.readConstant[Data[_]](cp).content)
			case t @ SIPUSH ⇒ t(input.readShort)

			case t @ LOOKUPSWITCH ⇒
				val paddingSize = (4 - (codeOffset + 1) % 4) % 4
				input.read(new Array[Byte](paddingSize))
				val default = (codeOffset + input.readInt).toString
				val targets = (0 until input.readInt) map { _ ⇒ input.readInt -> (codeOffset + input.readInt).toString }
				t(default, targets : _*)
			case t @ TABLESWITCH ⇒
				val paddingSize = (4 - (codeOffset + 1) % 4) % 4
				input.read(new Array[Byte](paddingSize))
				val default = (codeOffset + input.readInt).toString
				val low = input.readInt
				val high = input.readInt
				val targets = (low to high) map { _ ⇒ (codeOffset + input.readInt).toString }
				t(low, default, targets : _*)

			case w @ WIDE ⇒ instructionFromFlag(input.readUnsignedByte) match { //TODO: código repetido
				case t if (seedType(t) <:< typeOf[_ ⇒ LocalAccessor]) ⇒ w(t.asInstanceOf[Int ⇒ LocalAccessor](input.readUnsignedShort))
				case t @ IINC ⇒ w(t(input.readUnsignedShort, input.readShort))
				case _ ⇒ throw new IllegalArgumentException
			}

			case instruction : Instruction ⇒ instruction
		}
	}

	// ****************************************************************
	// ** UTILS
	// ****************************************************************

	def indexOfOffset(targetCodeOffset : Int)(instructions : Seq[Instruction]) = {
		def indexOfOffset(codeOffset : Int, instructions : Seq[Instruction]) : Int = instructions match {
			case Nil ⇒
				5
			case _ ⇒
				if (codeOffset == targetCodeOffset) 0
				else 1 + indexOfOffset(codeOffset + byteSize(instructions.head, codeOffset), instructions.tail)

		}

		indexOfOffset(0, instructions)
	}
}