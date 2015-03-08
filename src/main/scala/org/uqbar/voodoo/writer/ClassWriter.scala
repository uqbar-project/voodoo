package org.uqbar.voodoo.writer

import java.io.DataOutputStream
import org.uqbar.Utils._
import org.uqbar.voodoo.model.constants._
import org.uqbar.voodoo.Globals._
import org.uqbar.voodoo.StringInterpreter._
import org.uqbar.voodoo.model._
import org.uqbar.voodoo.builder.ConstantPoolBuilder
import java.io.FileOutputStream
import java.io.OutputStream

object ClassWriter extends ClassWriter
trait ClassWriter {

  def writeClass(c: Class, filePath : String) : Unit =
    for(output <- new DataOutputStream(new FileOutputStream(filePath)))
      writeClass(c,output)

	def writeClass(c : Class, outputStream : OutputStream) : Unit =
	  for(output <- new DataOutputStream(outputStream)){
      val cp : ConstantPool = buildConstantPool(c)

  		writeMagic(output)
  		writeVersion(c.version)(output)
  		writeConstantPool(cp)(output)
  		writeAccessFlags(c.visibility, c.metatype, c.modifiers)(output)
  		writeType(c.ownType)(cp)(output)
  		writeType(c.superType)(cp)(output)
  		writeInterfaces(c.interfaces)(cp)(output)
  		writeFields(c.fields)(cp)(output)
  		writeMethods(c.methods)(cp)(output)
  		writeAttributes(c.attributes)(cp)(output)
  	}

	protected def writeMagic(output : DataOutputStream) = output.writeInt(MAGIC)

	protected def writeVersion(v : ClassVersion)(output : DataOutputStream) {
		output.writeShort(v.minor)
		output.writeShort(v.major)
	}

	protected def writeConstantPool(cp : ConstantPool)(output : DataOutputStream) = {
		output.writeShort(cp.constantCount + 1)

		cp foreach {
			case Type(name) ⇒
				output.writeByte(TypeTag)
				output.writeShort(cp.indexOf(UTF8(binaryNameFromTypeName(name))))

			case s : MethodType ⇒
				output.writeByte(MethodTypeTag)
				output.writeShort(cp.indexOf(UTF8(descriptorFromName(s))))

			case Signature(selector, signatureType) ⇒
				output.writeByte(SignatureTag)
				output.writeShort(cp.indexOf(UTF8(selector)))
				output.writeShort(cp.indexOf(UTF8(descriptorFromName(signatureType))))

			case SlotRef(owner, fromInterface, signature) ⇒
				output.writeByte(
					if (fromInterface) InterfaceMethodTag
					else signature.signatureType match {
						case t : Type ⇒ FieldTag
						case m : MethodType ⇒ MethodTag
					}
				)
				output.writeShort(cp.indexOf(owner))
				output.writeShort(cp.indexOf(signature))

			case UTF8(content) ⇒
				output.writeByte(UTF8Tag)
				output.writeUTF(content)

			case Data(content : String) ⇒
				output.writeByte(StringTag)
				output.writeShort(cp.indexOf(UTF8(content)))

			case Data(content : Int) ⇒
				output.writeByte(IntTag)
				output.writeInt(content)

			case Data(content : Float) ⇒
				output.writeByte(FloatTag)
				output.writeFloat(content)

			case Data(content : Long) ⇒
				output.writeByte(LongTag)
				output.writeLong(content)

			case Data(content : Double) ⇒
				output.writeByte(DoubleTag)
				output.writeDouble(content)

			case MethodHandleRef(m : MethodHandle) ⇒
				output.writeByte(MethodHandleTag)
				output.writeByte(kindOfReference(m))
				output.writeShort(cp.indexOf(m.slot))

			case InvokeDynamic(bootstrapMethodIndex, signature) ⇒
				output.writeByte(InvokeDynamicTag)
				output.writeShort(bootstrapMethodIndex)
				output.writeShort(cp.indexOf(signature))
		}
	}

	protected def writeAccessFlags(visibility : Visibility, metatype : Metatype, modifiers : Set[_ <: Modifier[_]])(output : DataOutputStream) {
		output.writeShort(flagFor(visibility) | flagFor(metatype) | flagFor(modifiers.toSeq : _*))
	}

	protected def writeType(t : Type)(cp : ConstantPool)(output : DataOutputStream) {
		output.writeShort(cp.indexOf(t))
	}

	protected def writeInterfaces(is : Set[Type])(cp : ConstantPool)(output : DataOutputStream) {
		output.writeShort(is.size)
		is.foreach(i ⇒ output.writeShort(cp.indexOf(i)))
	}

	protected def writeFields(fs : Set[Field])(cp : ConstantPool)(output : DataOutputStream) {
		output.writeShort(fs.size)
		fs.foreach{ f ⇒
			output.writeShort(flagFor(f.visibility) | flagFor(f.modifiers.toSeq : _*))
			output.writeShort(cp.indexOf(UTF8(f.selector)))
			output.writeShort(cp.indexOf(UTF8(descriptorFromName(f.signature.signatureType))))
			writeAttributes(f.attributes)(cp)(output)
		}
	}

	protected def writeMethods(ms : Set[Method])(cp : ConstantPool)(output : DataOutputStream) {
		output.writeShort(ms.size)
		ms.foreach{ m ⇒
			output.writeShort(flagFor(m.visibility) | flagFor(m.modifiers.toSeq : _*))
			output.writeShort(cp.indexOf(UTF8(m.selector)))
			output.writeShort(cp.indexOf(UTF8(descriptorFromName(m.signature.signatureType))))
			writeAttributes(m.attributes)(cp)(output)
		}
	}

	//TODO: Quitar el code como argumento
	protected def writeAttributes[T <: Attributable](as : Iterable[Attribute[T]], code : Code = null)(cp : ConstantPool)(output : DataOutputStream) {
		output.writeShort(as.size)

		as.foreach{ a ⇒
			output.writeShort(cp.indexOf(UTF8(attributeLabel(a))))
			output.writeInt(byteSize(a))

			a match {
				case ConstantValue(value) ⇒
					output.writeShort(cp.indexOf(Data(value)))

				case code @ Code(instructions, exceptionHandlers, _) ⇒
					output.writeShort(code.maxStack)
					output.writeShort(code.maxLocals)

					output.writeInt(instructions.foldLeft(0){ (codeOffset, instruction) ⇒ byteSize(instruction, codeOffset) + codeOffset }) //TODO: Repeated code
					instructions.foldLeft(0){ (codeOffset, instruction) ⇒
						writeInstruction(instruction, code, codeOffset)(cp)(output)
						byteSize(instruction, codeOffset) + codeOffset
					}

					output.writeShort(exceptionHandlers.size)
					exceptionHandlers.foreach{
						case ExceptionHandler(start, end, handlerStart, catchType) ⇒
							output.writeShort(byteIndexOf(start)(code))
							output.writeShort(byteIndexOf(end)(code))
							output.writeShort(byteIndexOf(handlerStart)(code))
							output.writeShort(cp.indexOf(catchType))
					}

					writeAttributes[Code](code.attributes, code)(cp)(output)

				case Exceptions(checkedExceptions @ _*) ⇒
					output.writeShort(checkedExceptions.size)
					checkedExceptions.foreach(e ⇒ output.writeShort(cp.indexOf(e)))

				case RuntimeVisibleParameterAnnotations(parameterAnnotations @ _*) ⇒
					output.writeByte(parameterAnnotations.size)
					parameterAnnotations.foreach{ as ⇒
						output.writeShort(as.size)
						as.foreach{
							case Annotation(t, vs) ⇒
								output.writeShort(cp.indexOf(t))
								output.writeShort(vs.size)
								vs.foreach{
									case (key, value) ⇒
										output.writeShort(cp.indexOf(UTF8(key)))
									//TODO: write annotation value
								}
						}
					}

				case RuntimeInvisibleParameterAnnotations(parameterAnnotations @ _*) ⇒
					output.writeByte(parameterAnnotations.size)
					parameterAnnotations.foreach{ as ⇒
						output.writeShort(as.size)
						as.foreach(writeAnnotation(_)(cp)(output))
					}

				case AnnotationDefault(value : Any) ⇒ //TODO

				case LineNumberTable(entries : Map[Int, Int]) ⇒
					output.writeShort(entries.size)
					entries.foreach{
						case (start, line) ⇒
							output.writeShort(start)
							output.writeShort(line)
					}

				case LocalVariableTable(entries @ _*) ⇒
					output.writeShort(entries.size)
					entries.foreach{ e ⇒
						output.writeShort(e.start)
						output.writeShort(e.length)
						output.writeShort(cp.indexOf(UTF8(e.signature.selector)))
						output.writeShort(cp.indexOf(UTF8(descriptorFromName(e.signature.signatureType))))
						output.writeShort(e.localIndex)
					}

				case LocalVariableTypeTable(entries @ _*) ⇒
					output.writeShort(entries.size)
					entries.foreach{ e ⇒
						output.writeShort(e.start)
						output.writeShort(e.length)
						output.writeShort(cp.indexOf(UTF8(e.signature.selector)))
						output.writeShort(cp.indexOf(UTF8(descriptorFromName(e.signature.signatureType))))
						output.writeShort(e.localIndex)
					}

				case InnerClasses(classes @ _*) ⇒
					output.writeShort(classes.size)
					classes.foreach{
						case ClassIdentifier(visibility, metatype, modifiers, ownType, outerType) ⇒
							output.writeShort(cp.indexOf(ownType))
							output.writeShort(cp.indexOf(outerType))
							output.writeShort(cp.indexOf(UTF8(ownType.name)))
							output.writeShort(flagFor(visibility) | flagFor(metatype) | flagFor(modifiers.toSeq : _*))
					}

				case EnclosingMethod(method) ⇒
					output.writeShort(cp.indexOf(method.owner))
					output.writeShort(cp.indexOf(method.signature))

				case SourceFile(name) ⇒ output.writeShort(cp.indexOf(UTF8(name)))

				case SourceDebugExtension(info) ⇒ output.write(info.getBytes)

				case BootstrapMethods(bootstraps) ⇒
					output.writeShort(bootstraps.size)

					bootstraps.foreach{ b ⇒
						output.writeShort(cp.indexOf(MethodHandleRef(b.handle)))
						output.writeShort(b.arguments.size)

						b.arguments.foreach{ arg ⇒
							//TODO: output.writeShort(arg)
						}
					}

				case Synthetic | Deprecated ⇒

				case SignatureRef(signature : String) ⇒ output.writeShort(cp.indexOf(UTF8(signature)))

				case RuntimeVisibleAnnotations(annotations @ _*) ⇒
					output.writeShort(annotations.size)
					annotations.foreach(writeAnnotation(_)(cp)(output))

				case RuntimeInvisibleAnnotations(annotations @ _*) ⇒
					output.writeShort(annotations.size)
					annotations.foreach(writeAnnotation(_)(cp)(output))

				case _ : StackMapTable ⇒ writeStackMapTable(code)(cp)(output)

				case a : CustomAttribute[_] ⇒ output.write(a.bytes)
			}
		}
	}

	protected def writeStackMapTable(code : Code)(cp : ConstantPool)(output : DataOutputStream) {
		val entries = code.stackMapTable.entries

		output.writeShort(entries.size)

		entries.foreach{ entry ⇒
			if (entry != null) { //TODO: This is only because of JSR instruction. Fix.
				output.writeByte(entry.frameType)

				entry match {
					case AppendFrame(offsetDelta, locals @ _*) ⇒
						output.writeShort(offsetDelta)
						locals.foreach(writeVerificationType(_, code)(cp)(output))

					case ChopFrame(offsetDelta, _) ⇒ output.writeShort(offsetDelta)

					case SameFrame(_) ⇒

					case SameFrameExtended(offsetDelta) ⇒ output.writeShort(offsetDelta)

					case SameLocals1StackItemFrame(_, typeInfo) ⇒ writeVerificationType(typeInfo, code)(cp)(output)

					case SameLocals1StackItemFrameExtended(offsetDelta, typeInfo) ⇒
						output.writeShort(offsetDelta)
						writeVerificationType(typeInfo, code)(cp)(output)

					case FullFrame(offsetDelta, stack, locals) ⇒
						output.writeShort(offsetDelta)

						output.writeShort(locals.size)
						locals.foreach(writeVerificationType(_, code)(cp)(output))

						output.writeShort(stack.size)
						stack.foreach(writeVerificationType(_, code)(cp)(output))
				}
			}
		}
	}

	protected def writeVerificationType(verificationType : VerificationTypeInfo, code : Code)(cp : ConstantPool)(output : DataOutputStream) {
		output.writeByte(verificationTypeCode(verificationType))

		verificationType match {
			case ObjectVariableInfo(targetType) ⇒ output.writeShort(cp.indexOf(targetType))
			case UninitializedVariableInfo(target) ⇒
				val codeOffset = code.instructions.takeWhile(_ != target).foldLeft(0){ (acum, instruction) ⇒ byteSize(instruction, acum) + acum }
				output.writeShort(codeOffset)
			case _ ⇒
		}
	}

	protected def writeAnnotation(annotation : Annotation)(cp : ConstantPool)(output : DataOutputStream) : Unit = {
		output.writeShort(cp.indexOf(annotation.ownType))
		output.writeShort(annotation.values.size)
		annotation.values.foreach{
			case (key, value) ⇒
				output.writeShort(cp.indexOf(UTF8(key)))
				writeAnnotationValue(value)(cp)(output)
		}
	}

	protected def writeAnnotationValue(value : Any)(cp : ConstantPool)(output : DataOutputStream) : Unit = value match {
		case d : Data[_] ⇒
			output.writeByte(d.content match { //TODO: Mejorar
				case _ : Byte ⇒ 'B'
				case _ : Char ⇒ 'C'
				case _ : Double ⇒ 'D'
				case _ : Float ⇒ 'F'
				case _ : Int ⇒ 'I'
				case _ : Long ⇒ 'J'
				case _ : Short ⇒ 'S'
				case _ : Boolean ⇒ 'Z'
				case _ : String ⇒ 's'
			})
			output.writeShort(cp.indexOf(d))

		case (enumTypeName : String, enumValueName : String) ⇒
			output.writeByte('e')
			output.writeShort(cp.indexOf(UTF8(enumTypeName)))
			output.writeShort(cp.indexOf(UTF8(enumValueName)))
		case t : Type ⇒
			output.writeByte('c')
			output.writeShort(cp.indexOf(UTF8(descriptorFromName(t))))
		case a : Annotation ⇒
			output.writeByte('@')
			writeAnnotation(a)(cp)(output)
		case s : Seq[_] ⇒
			output.writeByte('[')
			output.writeShort(s.size)
			s.foreach{ writeAnnotationValue(_)(cp)(output) }
	}

	protected def writeInstruction(instruction : Instruction, code : Code, codeOffset : Int)(cp : ConstantPool)(output : DataOutputStream) : Unit = instruction match {
		case LabeledInstruction(_, i) ⇒ writeInstruction(i, code, codeOffset)(cp)(output)
		case _ ⇒
			output.writeByte(flagFromInstruction(companion(instruction))) //TODO: Llamar al companion desde adentro de flagFromInstruction

			instruction match {
				case i : LocalAccessor ⇒ output.writeByte(i.localIndex)
				case IINC(localIndex, increment) ⇒
					output.writeByte(localIndex)
					output.writeByte(increment)

				case GOTO_W(jumpTarget) ⇒ output.writeInt(byteIndexOf(jumpTarget)(code) - codeOffset)
				case JSR_W(jumpTarget) ⇒ output.writeInt(byteIndexOf(jumpTarget)(code) - codeOffset)
				case i : Jump ⇒ output.writeShort(byteIndexOf(i.jumpTarget)(code) - codeOffset)

				case ANEWARRAY(targetType) ⇒ output.writeShort(cp.indexOf(targetType))
				case CHECKCAST(targetType) ⇒ output.writeShort(cp.indexOf(targetType))
				case INSTANCEOF(targetType) ⇒ output.writeShort(cp.indexOf(targetType))
				case NEW(targetType) ⇒ output.writeShort(cp.indexOf(targetType))
				case NEWARRAY(targetType) ⇒ output.writeByte(flagFromArrayType(targetType))
				case MULTIANEWARRAY(targetType, dimensions, sizesToPop) ⇒
					output.writeShort(cp.indexOf((0 until dimensions).foldLeft(targetType)((t, _) ⇒ t.arrayed)))
					output.writeByte(sizesToPop)

				case i : FieldAccessor ⇒ output.writeShort(cp.indexOf(i.slot))

				case INVOKESPECIAL(slot) ⇒ output.writeShort(cp.indexOf(slot))
				case INVOKESTATIC(slot) ⇒ output.writeShort(cp.indexOf(slot))
				case INVOKEVIRTUAL(slot) ⇒ output.writeShort(cp.indexOf(slot))

				case INVOKEDYNAMIC(bootstrap, message) ⇒
					output.writeShort(cp.indexOf(InvokeDynamic(code.owner.owner.bootstrapMethods.indexOf(bootstrap), message)))
					output.writeShort(0)
				case INVOKEINTERFACE(slot) ⇒
					output.writeShort(cp.indexOf(slot))
					output.writeByte(slot.signature.signatureType.asInstanceOf[MethodType].argumentTypes.size + 1) //TODO: Remove cast
					output.writeByte(0)

				case BIPUSH(byte) ⇒ output.writeByte(byte)
				case SIPUSH(short) ⇒ output.writeShort(short)
				case LDC(constant) ⇒ output.writeByte(cp.indexOf(Data(constant)))
				case LDC_W(constant) ⇒ output.writeShort(cp.indexOf(Data(constant)))
				case LDC2_W(constant) ⇒ output.writeShort(cp.indexOf(Data(constant)))

				case LOOKUPSWITCH(defaultJumpTarget, jumpTargets @ _*) ⇒
					val paddingSize = (4 - (codeOffset + 1) % 4) % 4
					output.write((0 until paddingSize).map(_ ⇒ 0 : Byte).toArray)
					output.writeInt(byteIndexOf(defaultJumpTarget)(code) - codeOffset)
					output.writeInt(jumpTargets.size)
					jumpTargets.foreach{
						case (key, value) ⇒
							output.writeInt(key)
							output.writeInt(byteIndexOf(value)(code) - codeOffset)
					}
				case TABLESWITCH(from, defaultJumpTarget, callTargets @ _*) ⇒
					val paddingSize = (4 - (codeOffset + 1) % 4) % 4
					output.write((0 until paddingSize).map(_ ⇒ 0 : Byte).toArray)
					output.writeInt(byteIndexOf(defaultJumpTarget)(code) - codeOffset)
					output.writeInt(from)
					output.writeInt(from + callTargets.size - 1)
					callTargets.foreach(label ⇒ output.writeInt(byteIndexOf(label)(code) - codeOffset))

				case WIDE(i) ⇒ //TODO: Código repetido
					output.writeByte(flagFromInstruction(companion(i)))
					i match {
						case i : LocalAccessor ⇒ output.writeShort(i.localIndex)
						case IINC(localIndex, increment) ⇒
							output.writeShort(localIndex)
							output.writeShort(increment)
						case _ ⇒ throw new IllegalArgumentException
					}
				case _ ⇒
			}
	}

	def buildConstantPool(c : Class) = {
		val builder = new ConstantPoolBuilder

		builder += c.ownType
		builder += c.superType

		c.interfaces.foreach(builder += _)

		c.fields.foreach{ f ⇒
			builder += f.signature
			f.attributes.foreach(insertAttribute(_))
		}

		c.methods.foreach{ m ⇒
			builder += UTF8(m.signature.selector)
			builder += UTF8(descriptorFromName(m.signature.signatureType))

			m.attributes.foreach(insertAttribute(_))
		}

		c.attributes.foreach(insertAttribute(_))

		def insertAttribute(attribute : Attribute[_ <: Attributable]) : Unit = {
			builder += UTF8(attributeLabel(attribute))

			attribute match { //TODO: other attributes?
				case code @ Code(instructions, exceptionHandlers, _) ⇒
					instructions.foreach{
						case ANEWARRAY(t) ⇒ builder += t
						case CHECKCAST(t) ⇒ builder += t
						case i : FieldAccessor ⇒ builder += i.slot
						case INSTANCEOF(t) ⇒ builder += t
						case INVOKEDYNAMIC(b, m) ⇒ builder += InvokeDynamic(code.owner.owner.bootstrapMethods.indexOf(b), m)
						case INVOKEINTERFACE(m) ⇒ builder += m
						case INVOKESPECIAL(m) ⇒ builder += m
						case INVOKESTATIC(m) ⇒ builder += m
						case INVOKEVIRTUAL(m) ⇒ builder += m
						case LDC(v) ⇒ builder += Data(v)
						case LDC_W(v) ⇒ builder += Data(v)
						case LDC2_W(v) ⇒ builder += Data(v)
						case MULTIANEWARRAY(targetType, dimensions, _) ⇒ builder += (0 until dimensions).foldLeft(targetType)((t, _) ⇒ t.arrayed)
						case NEW(t) ⇒ builder += t
						case _ ⇒
					}

					code.attributes.foreach(insertAttribute(_))

				case StackMapTable(entries @ _*) ⇒ entries foreach {
					case AppendFrame(_, locals @ _*) ⇒ locals.foreach(insertVerificationType(_))

					case SameLocals1StackItemFrame(_, typeInfo) ⇒ insertVerificationType(typeInfo)
					case SameLocals1StackItemFrameExtended(_, typeInfo) ⇒ insertVerificationType(typeInfo)

					case FullFrame(_, stack, locals) ⇒
						locals.foreach(insertVerificationType(_))
						stack.foreach(insertVerificationType(_))

					case _ ⇒
				}

				case BootstrapMethods(bootstraps) ⇒ bootstraps.foreach{ b ⇒ builder += MethodHandleRef(b.handle) }

				case SourceFile(name) ⇒ builder += UTF8(name)

				case LocalVariableTable(entries @ _*) => entries.foreach(builder += _.signature)

				case LocalVariableTypeTable(entries @ _*) => entries.foreach(builder += _.signature)

				case SignatureRef(signature) => builder += UTF8(signature)

				case _ ⇒
			}
		}

		def insertVerificationType(verificationType : VerificationTypeInfo) = verificationType match {
			case ObjectVariableInfo(t) ⇒ builder += t
			case _ ⇒
		}

		builder.build
	}

	def attributeLabel(attribute : Attribute[_ <: Attributable]) = attribute match {
		case a : CustomAttribute[_] ⇒ a.label
		case _ : SignatureRef ⇒ "Signature" //TODO: Y si esto fuera solo el signature?
		case other ⇒ other.getClass.getSimpleName.stripSuffix("$")
	}

	def byteIndexOf(label : Label)(code : Code) = {
		val target = code.instructions.collectFirst{ case i @ LabeledInstruction(`label`, _) ⇒ i }.get
		code.instructions.takeWhile(_ != target).foldLeft(0){ (codeOffset, instruction) ⇒ byteSize(instruction, codeOffset) + codeOffset }
	}
}