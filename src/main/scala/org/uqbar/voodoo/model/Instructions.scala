package org.uqbar.voodoo.model

import org.uqbar.voodoo.model.constants._

trait Instruction {
	def minLocalsSizeRequired : Int
	def @:(label : Label) = LabeledInstruction(label, this)
}

case class LabeledInstruction(label : Label, instruction : Instruction) extends Instruction {
	def minLocalsSizeRequired = instruction.minLocalsSizeRequired
}

// TODO? GOTO("x"), "x" @: RETURN no debería ser igual a GOTO("y"), "y" @: RETURN
trait Jump extends Instruction { def jumpTarget : Label }
trait ConditionalJump extends Jump
trait UnaryConditionalJump extends ConditionalJump
trait BinaryConditionalJump extends ConditionalJump

trait LocalAccessor extends Instruction { def localIndex : Int }
trait Load extends LocalAccessor
trait Store extends LocalAccessor

trait FieldAccessor extends Instruction with MethodHandle

sealed abstract class BasicInstruction(val minLocalsSizeRequired : Int) extends Instruction //TODO: minLocalsSize va acá o en una función? Si va en una función, volar esta clase

// ****************************************************************
// ** INSTRUCTIONS
// ****************************************************************

case object AALOAD extends BasicInstruction(0)
case object BALOAD extends BasicInstruction(0)
case object CALOAD extends BasicInstruction(0)
case object DALOAD extends BasicInstruction(0)
case object FALOAD extends BasicInstruction(0)
case object IALOAD extends BasicInstruction(0)
case object LALOAD extends BasicInstruction(0)
case object SALOAD extends BasicInstruction(0)
case object AASTORE extends BasicInstruction(0)
case object BASTORE extends BasicInstruction(0)
case object CASTORE extends BasicInstruction(0)
case object DASTORE extends BasicInstruction(0)
case object FASTORE extends BasicInstruction(0)
case object IASTORE extends BasicInstruction(0)
case object LASTORE extends BasicInstruction(0)
case object SASTORE extends BasicInstruction(0)

case class ALOAD(localIndex : Int) extends BasicInstruction(localIndex + 1) with Load
case object ALOAD_0 extends BasicInstruction(1)
case object ALOAD_1 extends BasicInstruction(2)
case object ALOAD_2 extends BasicInstruction(3)
case object ALOAD_3 extends BasicInstruction(4)
case class DLOAD(localIndex : Int) extends BasicInstruction(localIndex + 2) with Load
case object DLOAD_0 extends BasicInstruction(2)
case object DLOAD_1 extends BasicInstruction(3)
case object DLOAD_2 extends BasicInstruction(4)
case object DLOAD_3 extends BasicInstruction(5)
case class ILOAD(localIndex : Int) extends BasicInstruction(localIndex + 1) with Load
case object ILOAD_0 extends BasicInstruction(1)
case object ILOAD_1 extends BasicInstruction(2)
case object ILOAD_2 extends BasicInstruction(3)
case object ILOAD_3 extends BasicInstruction(4)
case class FLOAD(localIndex : Int) extends BasicInstruction(localIndex + 1) with Load
case object FLOAD_0 extends BasicInstruction(1)
case object FLOAD_1 extends BasicInstruction(2)
case object FLOAD_2 extends BasicInstruction(3)
case object FLOAD_3 extends BasicInstruction(4)
case class LLOAD(localIndex : Int) extends BasicInstruction(localIndex + 2) with Load
case object LLOAD_0 extends BasicInstruction(2)
case object LLOAD_1 extends BasicInstruction(3)
case object LLOAD_2 extends BasicInstruction(4)
case object LLOAD_3 extends BasicInstruction(5)
case class ASTORE(localIndex : Int) extends BasicInstruction(localIndex + 1) with Store
case object ASTORE_0 extends BasicInstruction(1)
case object ASTORE_1 extends BasicInstruction(2)
case object ASTORE_2 extends BasicInstruction(3)
case object ASTORE_3 extends BasicInstruction(4)
case class DSTORE(localIndex : Int) extends BasicInstruction(localIndex + 2) with Store
case object DSTORE_0 extends BasicInstruction(2)
case object DSTORE_1 extends BasicInstruction(3)
case object DSTORE_2 extends BasicInstruction(4)
case object DSTORE_3 extends BasicInstruction(5)
case class ISTORE(localIndex : Int) extends BasicInstruction(localIndex + 1) with Store
case object ISTORE_0 extends BasicInstruction(1)
case object ISTORE_1 extends BasicInstruction(2)
case object ISTORE_2 extends BasicInstruction(3)
case object ISTORE_3 extends BasicInstruction(4)
case class FSTORE(localIndex : Int) extends BasicInstruction(localIndex + 1) with Store
case object FSTORE_0 extends BasicInstruction(1)
case object FSTORE_1 extends BasicInstruction(2)
case object FSTORE_2 extends BasicInstruction(3)
case object FSTORE_3 extends BasicInstruction(4)
case class LSTORE(localIndex : Int) extends BasicInstruction(localIndex + 2) with Store
case object LSTORE_0 extends BasicInstruction(2)
case object LSTORE_1 extends BasicInstruction(3)
case object LSTORE_2 extends BasicInstruction(4)
case object LSTORE_3 extends BasicInstruction(5)
case class RET(localIndex : Int) extends BasicInstruction(localIndex + 1) with LocalAccessor
case class IINC(localIndex : Int, increment : Int) extends BasicInstruction(localIndex + 1)

case object ACONST_NULL extends BasicInstruction(0)
case object DCONST_0 extends BasicInstruction(0)
case object DCONST_1 extends BasicInstruction(0)
case object FCONST_0 extends BasicInstruction(0)
case object FCONST_1 extends BasicInstruction(0)
case object FCONST_2 extends BasicInstruction(0)
case object ICONST_M1 extends BasicInstruction(0)
case object ICONST_0 extends BasicInstruction(0)
case object ICONST_1 extends BasicInstruction(0)
case object ICONST_2 extends BasicInstruction(0)
case object ICONST_3 extends BasicInstruction(0)
case object ICONST_4 extends BasicInstruction(0)
case object ICONST_5 extends BasicInstruction(0)
case object LCONST_0 extends BasicInstruction(0)
case object LCONST_1 extends BasicInstruction(0)
case class BIPUSH(byte : Int) extends BasicInstruction(0)
case class SIPUSH(aShort : Int) extends BasicInstruction(0)
case class LDC[T >: ShortDataType](constant : T) extends BasicInstruction(0)
case class LDC_W[T >: ShortDataType](constant : T) extends BasicInstruction(0)
case class LDC2_W[T >: LongDataType](constant : T) extends BasicInstruction(0)

case class MULTIANEWARRAY(targetType : Type, dimensions : Int, sizesToPop : Int) extends BasicInstruction(0) //TODO: Juntar la dimensions y el type?
case class ANEWARRAY(targetType : Type) extends BasicInstruction(0)
case class NEWARRAY(targetType : Type) extends BasicInstruction(0)
case class NEW(targetType : Type) extends BasicInstruction(0)
case class CHECKCAST(targetType : Type) extends BasicInstruction(0)
case class INSTANCEOF(targetType : Type) extends BasicInstruction(0)

case object ARETURN extends BasicInstruction(0)
case object DRETURN extends BasicInstruction(0)
case object FRETURN extends BasicInstruction(0)
case object IRETURN extends BasicInstruction(0)
case object LRETURN extends BasicInstruction(0)
case object RETURN extends BasicInstruction(0)

case object D2F extends BasicInstruction(0)
case object D2I extends BasicInstruction(0)
case object D2L extends BasicInstruction(0)
case object F2D extends BasicInstruction(0)
case object F2I extends BasicInstruction(0)
case object F2L extends BasicInstruction(0)
case object I2B extends BasicInstruction(0)
case object I2C extends BasicInstruction(0)
case object I2D extends BasicInstruction(0)
case object I2F extends BasicInstruction(0)
case object I2L extends BasicInstruction(0)
case object I2S extends BasicInstruction(0)
case object L2D extends BasicInstruction(0)
case object L2F extends BasicInstruction(0)
case object L2I extends BasicInstruction(0)

case object DADD extends BasicInstruction(0)
case object DCMPG extends BasicInstruction(0)
case object DCMPL extends BasicInstruction(0)
case object DDIV extends BasicInstruction(0)
case object DMUL extends BasicInstruction(0)
case object DNEG extends BasicInstruction(0)
case object DREM extends BasicInstruction(0)
case object DSUB extends BasicInstruction(0)
case object FADD extends BasicInstruction(0)
case object FCMPG extends BasicInstruction(0)
case object FCMPL extends BasicInstruction(0)
case object FDIV extends BasicInstruction(0)
case object FMUL extends BasicInstruction(0)
case object FNEG extends BasicInstruction(0)
case object FREM extends BasicInstruction(0)
case object FSUB extends BasicInstruction(0)
case object IADD extends BasicInstruction(0)
case object IAND extends BasicInstruction(0)
case object IDIV extends BasicInstruction(0)
case object IMUL extends BasicInstruction(0)
case object INEG extends BasicInstruction(0)
case object IOR extends BasicInstruction(0)
case object IREM extends BasicInstruction(0)
case object ISHL extends BasicInstruction(0)
case object ISHR extends BasicInstruction(0)
case object ISUB extends BasicInstruction(0)
case object IUSHR extends BasicInstruction(0)
case object IXOR extends BasicInstruction(0)
case object LADD extends BasicInstruction(0)
case object LAND extends BasicInstruction(0)
case object LCMP extends BasicInstruction(0)
case object LDIV extends BasicInstruction(0)
case object LMUL extends BasicInstruction(0)
case object LNEG extends BasicInstruction(0)
case object LOR extends BasicInstruction(0)
case object LREM extends BasicInstruction(0)
case object LSHL extends BasicInstruction(0)
case object LSHR extends BasicInstruction(0)
case object LSUB extends BasicInstruction(0)
case object LUSHR extends BasicInstruction(0)
case object LXOR extends BasicInstruction(0)

case class GETFIELD(slot : SlotRef[Field]) extends BasicInstruction(0) with FieldAccessor
case class GETSTATIC(slot : SlotRef[Field]) extends BasicInstruction(0) with FieldAccessor
case class PUTFIELD(slot : SlotRef[Field]) extends BasicInstruction(0) with FieldAccessor
case class PUTSTATIC(slot : SlotRef[Field]) extends BasicInstruction(0) with FieldAccessor

case class INVOKEDYNAMIC(bootstrap : Bootstrap, message : Signature[Method]) extends BasicInstruction(0)
case class INVOKEINTERFACE(slot : SlotRef[Method]) extends BasicInstruction(0) with MethodHandle
case class INVOKESPECIAL(slot : SlotRef[Method]) extends BasicInstruction(0) with MethodHandle
case class INVOKESTATIC(slot : SlotRef[Method]) extends BasicInstruction(0) with MethodHandle
case class INVOKEVIRTUAL(slot : SlotRef[Method]) extends BasicInstruction(0) with MethodHandle

case class GOTO(jumpTarget : Label) extends BasicInstruction(0) with Jump
case class GOTO_W(jumpTarget : Label) extends BasicInstruction(0) with Jump
case class JSR(jumpTarget : Label) extends BasicInstruction(0) with Jump
case class JSR_W(jumpTarget : Label) extends BasicInstruction(0) with Jump

//TODO: Unificar los if en una? instrucción con un argumento más
//TODO: In case If gets refactored to be a single parametric instruction, delegate unary/binary to the argument
case class IF_ACMPEQ(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IF_ACMPNE(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IF_ICMPEQ(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IF_ICMPNE(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IF_ICMPLT(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IF_ICMPGE(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IF_ICMPGT(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IF_ICMPLE(jumpTarget : Label) extends BasicInstruction(0) with BinaryConditionalJump
case class IFEQ(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump
case class IFNE(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump
case class IFLT(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump
case class IFGE(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump
case class IFGT(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump
case class IFLE(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump
case class IFNONNULL(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump
case class IFNULL(jumpTarget : Label) extends BasicInstruction(0) with UnaryConditionalJump

case class LOOKUPSWITCH(defaultJumpTarget : Label, jumpTargets : (Int, Label)*) extends BasicInstruction(0)
case class TABLESWITCH(from : Int, defaultJumpTarget : Label, jumpTargets : Label*) extends BasicInstruction(0)

case object ARRAYLENGTH extends BasicInstruction(0)

case object ATHROW extends BasicInstruction(0)

case object MONITORENTER extends BasicInstruction(0)
case object MONITOREXIT extends BasicInstruction(0)

case object POP extends BasicInstruction(0)
case object POP2 extends BasicInstruction(0)
case object SWAP extends BasicInstruction(0)
case object DUP extends BasicInstruction(0)
case object DUP_X1 extends BasicInstruction(0)
case object DUP_X2 extends BasicInstruction(0)
case object DUP2 extends BasicInstruction(0)
case object DUP2_X1 extends BasicInstruction(0)
case object DUP2_X2 extends BasicInstruction(0)

case class WIDE(target : Instruction) extends BasicInstruction(target.minLocalsSizeRequired)

case object NOP extends BasicInstruction(0)
case object BREAKPOINT extends BasicInstruction(0)
case object IMPDEP1 extends BasicInstruction(0)
case object IMPDEP2 extends BasicInstruction(0)