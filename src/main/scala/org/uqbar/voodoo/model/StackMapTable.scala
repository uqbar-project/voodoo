package org.uqbar.voodoo.model

import org.uqbar.voodoo.model.MethodModifier._
import org.uqbar.voodoo.Globals._

abstract class StackMapFrame(val frameType : Int)
case class AppendFrame(offsetDelta : Int, locals : VerificationTypeInfo*) extends StackMapFrame(251 + locals.size) //TODO: estas cuentas deberían ir en global. Quitar el frameType
case class ChopFrame(offsetDelta : Int, choppedLocals : Int) extends StackMapFrame(251 - choppedLocals)
case class SameFrame(offsetDelta : Int) extends StackMapFrame(offsetDelta)
case class SameFrameExtended(offsetDelta : Int) extends StackMapFrame(251)
case class SameLocals1StackItemFrame(offsetDelta : Int, typeInfo : VerificationTypeInfo) extends StackMapFrame(offsetDelta + 64)
case class SameLocals1StackItemFrameExtended(offsetDelta : Int, typeInfo : VerificationTypeInfo) extends StackMapFrame(247)
case class FullFrame(offsetDelta : Int, stack : Seq[VerificationTypeInfo], locals : Seq[VerificationTypeInfo]) extends StackMapFrame(255) //TODO: Use Stacks instead of seq?

trait VerificationTypeInfo // Unify with Type somehow
case class ObjectVariableInfo(targetType : Type) extends VerificationTypeInfo
case object TopType extends VerificationTypeInfo
case object IntegerType extends VerificationTypeInfo
case object FloatType extends VerificationTypeInfo
case object DoubleType extends VerificationTypeInfo
case object LongType extends VerificationTypeInfo
case object NullType extends VerificationTypeInfo
case object UninitializedThis extends VerificationTypeInfo
case class UninitializedVariableInfo(target : Instruction) extends VerificationTypeInfo //TODO: do we have a problem with identity here?

case class StackMapTable(entries : StackMapFrame*) extends Attribute[Code] {
	def maxStack = entries.map{
		case f : FullFrame ⇒ f.stack.map{
			case LongType | DoubleType ⇒ 2
			case _ ⇒ 1
		}.sum
	}.max

	def maxLocals = entries.map{
		case f : FullFrame ⇒ f.locals.map{
			case LongType | DoubleType ⇒ 2
			case _ ⇒ 1
		}.sum
	}.max
}

object StackMapTable {
	def apply(code : Code) = {
	  val method = code.owner
		val methodSignature = method.signature.signatureType.asInstanceOf[MethodType]
		val instructions = code.instructions

		val localTypes = methodSignature.argumentTypes.map(t ⇒ t : VerificationTypeInfo)
		val locals = if (!method.modifiers.contains(Static)) {
			val t : VerificationTypeInfo = if (method.isConstructor) UninitializedThis else method.owner.ownType
			t +: localTypes
		} else localTypes

		val entries = new Array[StackMapFrame](instructions.size + 1)
		val last = new FullFrame(0, Seq(), locals)
		entries(0) = last

		def iterateFrom(index : Int, last : FullFrame) : Unit = if (entries(index + 1) == null) {
			val call = instructions(index)
			val currentFrame = nextFrame(index, last)

			entries(index + 1) = currentFrame

			call match {
				case i : ConditionalJump ⇒
					iterateFrom(indexOf(i.jumpTarget), currentFrame)
					iterateFrom(index + 1, currentFrame)
				case i : Jump if (i.isInstanceOf[JSR] || i.isInstanceOf[JSR_W]) ⇒ //TODO: This is temporally until RET can be processed (then just remove this case and process as Jump)
					iterateFrom(indexOf(i.jumpTarget), currentFrame)
					iterateFrom(index + 1, currentFrame)
				case i : Jump ⇒ iterateFrom(indexOf(i.jumpTarget), currentFrame)
				case TABLESWITCH(_, defaultJumpTarget, jumpTargets @ _*) ⇒
					iterateFrom(indexOf(defaultJumpTarget), currentFrame)
					jumpTargets.foreach(e ⇒ iterateFrom(indexOf(e), currentFrame))
				case LOOKUPSWITCH(defaultJumpTarget, jumpTargets @ _*) ⇒
					iterateFrom(indexOf(defaultJumpTarget), currentFrame)
					jumpTargets.foreach(e ⇒ iterateFrom(indexOf(e._2), currentFrame))
				case RET(_) ⇒ // TODO: search the corresponding JSR and iterate from there
				case RETURN | ARETURN | IRETURN | FRETURN | LRETURN | DRETURN | ATHROW ⇒
				case _ ⇒ iterateFrom(index + 1, currentFrame)
			}
		}

		def indexOf(label : Label, is : Seq[Instruction] = instructions) : Int = is.head match {
			case LabeledInstruction(`label`, _) ⇒ 0
			case _ ⇒ 1 + indexOf(label, is.tail)
		}

		def nextFrame(index : Int, last : FullFrame) : FullFrame = {
			val instruction = instructions(index) match {
				case LabeledInstruction(_, i) ⇒ i
				case other ⇒ other
			}
			val offsetDelta = byteSize(instructions.take(index + 1)) - byteSize(instructions.take(index)) - 1
			var next = new FullFrame(offsetDelta, last.stack, last.locals)

			def popStack(count : Int) = next = next.copy(stack = next.stack.dropRight(count))

			def pushStack(e : VerificationTypeInfo) = next = next.copy(stack = next.stack ++ Seq(e))

			def updateLocal(localIndex : Int, value : VerificationTypeInfo) {
				val oversize = next.locals.take(localIndex min next.locals.size).count(Seq(DoubleType, LongType).contains(_))
				next = next.copy(locals = if (next.locals.size + oversize > localIndex) next.locals.updated(localIndex - oversize, value)
				else next.locals ++ (next.locals.size + oversize until localIndex).map(_ ⇒ TopType) ++ Seq(value)
				)
			}
			def peekStack(jumps : Int) = last.stack(last.stack.size - 1 - jumps)

			def peekLocal(targetIndex : Int) = {
				val oversize = last.locals.take(targetIndex min last.locals.size).count(Seq(DoubleType, LongType).contains(_))
				last.locals(targetIndex - oversize)
			}

			instruction match {
				case AALOAD ⇒
					popStack(2)
					pushStack(new ObjectVariableInfo(peekStack(1).asInstanceOf[ObjectVariableInfo].targetType.unarrayed))

				case IALOAD | BALOAD | CALOAD | SALOAD ⇒
					popStack(2)
					pushStack(IntegerType)
				case FALOAD ⇒
					popStack(2)
					pushStack(FloatType)
				case DALOAD ⇒
					popStack(2)
					pushStack(DoubleType)
				case LALOAD ⇒
					popStack(2)
					pushStack(LongType)

				case AASTORE | BASTORE | CASTORE | IASTORE | FASTORE | DASTORE | LASTORE | SASTORE ⇒ popStack(3)

				case ACONST_NULL ⇒ pushStack(NullType)

				case i : Load ⇒ pushStack(peekLocal(i.localIndex))
				case ALOAD_0 ⇒ pushStack(peekLocal(0))
				case ALOAD_1 ⇒ pushStack(peekLocal(1))
				case ALOAD_2 ⇒ pushStack(peekLocal(2))
				case ALOAD_3 ⇒ pushStack(peekLocal(3))
				case ILOAD_0 | ILOAD_1 | ILOAD_2 | ILOAD_3 ⇒ pushStack(IntegerType)
				case DLOAD_0 | DLOAD_1 | DLOAD_2 | DLOAD_3 ⇒ pushStack(DoubleType)
				case FLOAD_0 | FLOAD_1 | FLOAD_2 | FLOAD_3 ⇒ pushStack(FloatType)
				case LLOAD_0 | LLOAD_1 | LLOAD_2 | LLOAD_3 ⇒ pushStack(LongType)

				case i : Store ⇒
					updateLocal(i.localIndex, peekStack(0))
					popStack(1)
				case ASTORE_0 | DSTORE_0 | FSTORE_0 | ISTORE_0 | LSTORE_0 ⇒
					updateLocal(0, peekStack(0))
					popStack(1)
				case ASTORE_1 | DSTORE_1 | FSTORE_1 | ISTORE_1 | LSTORE_1 ⇒
					updateLocal(1, peekStack(0))
					popStack(1)
				case ASTORE_2 | DSTORE_2 | FSTORE_2 | ISTORE_2 | LSTORE_2 ⇒
					updateLocal(2, peekStack(0))
					popStack(1)
				case ASTORE_3 | DSTORE_3 | FSTORE_3 | ISTORE_3 | LSTORE_3 ⇒
					updateLocal(3, peekStack(0))
					popStack(1)

				case ANEWARRAY(targetType) ⇒
					popStack(1)
					pushStack(targetType.arrayed)

				case ARETURN | IRETURN | FRETURN | DRETURN | LRETURN ⇒ popStack(1)

				case ARRAYLENGTH ⇒
					popStack(1)
					pushStack(IntegerType)

				case CHECKCAST(targetType) ⇒
					popStack(1)
					pushStack(targetType)

				case D2F | I2F | L2F ⇒
					popStack(1)
					pushStack(FloatType)

				case D2I | F2I | L2I | I2B | I2C | I2S ⇒
					popStack(1)
					pushStack(IntegerType)

				case D2L | F2L | I2L ⇒
					popStack(1)
					pushStack(LongType)

				case I2D | F2D | L2D ⇒
					popStack(1)
					pushStack(DoubleType)

				case DCONST_0 | DCONST_1 ⇒ pushStack(DoubleType)
				case DNEG ⇒
					popStack(1)
					pushStack(DoubleType)

				case DADD | DSUB | DDIV | DMUL | DREM ⇒
					popStack(2)
					pushStack(DoubleType)

				case DCMPG | DCMPL | FCMPG | FCMPL | LCMP ⇒
					popStack(2)
					pushStack(IntegerType)
				case DUP | DUP2 | DUP2_X2 ⇒ pushStack(peekStack(0))
				case DUP_X1 | DUP2_X1 ⇒
					popStack(2)
					pushStack(peekStack(0))
					pushStack(peekStack(1))
					pushStack(peekStack(0))
				case DUP_X2 ⇒
					popStack(3)
					pushStack(peekStack(0))
					pushStack(peekStack(2))
					pushStack(peekStack(1))
					pushStack(peekStack(0))

				case FADD | FDIV | FMUL | FREM | FSUB ⇒
					popStack(2)
					pushStack(FloatType)

				case FCONST_0 | FCONST_1 | FCONST_2 ⇒
					pushStack(FloatType)
				case FNEG ⇒
					popStack(1)
					pushStack(FloatType)

				case GETFIELD(slot) ⇒
					popStack(1)
					pushStack(slot.signature.signatureType.asInstanceOf[Type])
				case GETSTATIC(slot) ⇒ pushStack(slot.signature.signatureType.asInstanceOf[Type])
				case PUTFIELD(_) ⇒ popStack(2)
				case PUTSTATIC(_) ⇒ popStack(1)

				case IADD | IMUL | IREM | IDIV | ISUB | IAND | IOR | ISHL | ISHR | IUSHR | IXOR ⇒
					popStack(2)
					pushStack(IntegerType)

				case BIPUSH(_) | SIPUSH(_) | ICONST_0 | ICONST_1 | ICONST_2 | ICONST_3 | ICONST_4 | ICONST_5 | ICONST_M1 ⇒ pushStack(IntegerType)

				case JSR(_) | JSR_W(_) ⇒ pushStack(IntegerType)
				case _ : UnaryConditionalJump ⇒ popStack(1)
				case _ : BinaryConditionalJump ⇒ popStack(2)

				case INSTANCEOF(_) ⇒
					popStack(1)
					pushStack(IntegerType)

				case INVOKEDYNAMIC(Bootstrap(handle), message) ⇒
					val methodSignature = message.signatureType.asInstanceOf[MethodType] //TODO: Quitar casteo

					popStack(methodSignature.argumentTypes.size)
					handle match {
						case INVOKESTATIC(_) ⇒
						case _ ⇒ popStack(1)
					}

					if (methodSignature.returnType != $[Unit]) pushStack(methodSignature.returnType)

				case INVOKESTATIC(slot) ⇒
					val signatureType = slot.signature.signatureType.asInstanceOf[MethodType] //TODO: Quitar Casteo
					popStack(signatureType.argumentTypes.size)

					if (signatureType.returnType != $[Unit]) pushStack(signatureType.returnType)

				case INVOKEVIRTUAL(slot) ⇒
					val signatureType = slot.signature.signatureType.asInstanceOf[MethodType] //TODO: Quitar Casteo
					popStack(1 + signatureType.argumentTypes.size)

					if (signatureType.returnType != $[Unit]) pushStack(signatureType.returnType)

				case INVOKEINTERFACE(slot) ⇒
					val signatureType = slot.signature.signatureType.asInstanceOf[MethodType] //TODO: Quitar Casteo
					popStack(1 + signatureType.argumentTypes.size)

					if (signatureType.returnType != $[Unit]) pushStack(signatureType.returnType)

				case INVOKESPECIAL(slot) ⇒
					val signatureType = slot.signature.signatureType.asInstanceOf[MethodType] //TODO: Quitar Casteo
					popStack(1 + signatureType.argumentTypes.size)

					next = next.copy(stack = next.stack.map(s ⇒ if (peekStack(0) == s) slot.owner : VerificationTypeInfo else s))
					updateLocal(0, code.owner.owner.ownType)

				case LADD | LSUB | LDIV | LMUL | LREM | LOR | LAND | LXOR | LSHL | LSHR | LUSHR ⇒
					popStack(2)
					pushStack(LongType)

				case LCONST_0 | LCONST_1 ⇒ pushStack(LongType)

				case LDC(constant) ⇒ constant match {
					case s : String ⇒ pushStack($[String])
					case i : Int ⇒ pushStack($[Int])
					case i : Float ⇒ pushStack($[Float])
				}
				case LDC_W(constant) ⇒ constant match {
					case s : String ⇒ pushStack($[String])
					case i : Int ⇒ pushStack($[Int])
					case i : Float ⇒ pushStack($[Float])
				}

				case LDC2_W(constant) ⇒ constant match {
					case l : Long ⇒ pushStack($[Long])
					case d : Double ⇒ pushStack($[Double])
				}

				case LNEG ⇒
					popStack(1)
					pushStack(LongType)

				case LOOKUPSWITCH(_, _@ _*) | TABLESWITCH(_, _, _@ _*) | MONITORENTER | MONITOREXIT | POP ⇒ popStack(1)

				case MULTIANEWARRAY(targetType, dimensions, sizesToPop) ⇒
					popStack(sizesToPop)
					pushStack((0 until dimensions).foldLeft(targetType)((t, _) ⇒ t.arrayed))

				case NEW(_) ⇒ pushStack(new UninitializedVariableInfo(instruction))

				case NEWARRAY(targetType) ⇒
					popStack(1)
					pushStack(targetType.arrayed)

				case POP2 ⇒ popStack(if (peekStack(0) == LongType || peekStack(0) == DoubleType) 1 else 2)

				case SWAP ⇒
					popStack(2)
					pushStack(peekStack(0))
					pushStack(peekStack(1))

				case WIDE(i : Load) ⇒ pushStack(peekLocal(i.localIndex))
				case WIDE(i : Store) ⇒
					updateLocal(i.localIndex, peekStack(0))
					popStack(1)

				case WIDE(_) ⇒

				case GOTO(_) | GOTO_W(_) | IINC(_, _) | IMPDEP1 | IMPDEP2 | INEG | ATHROW | BREAKPOINT | NOP | RET(_) | RETURN ⇒
			}

			next
		}

		iterateFrom(0, last)

		new StackMapTable(entries.init : _*)
	}
}