package org.uqbar.voodoo

import org.uqbar.voodoo.model.constants._
import org.uqbar.voodoo.model._
import scala.reflect.ClassTag

object Globals {
	final val MAGIC = 0xCAFEBABE

	type ConstantTag = Int
	final val TypeTag : ConstantTag = 7
	final val FieldTag : ConstantTag = 9
	final val MethodTag : ConstantTag = 10
	final val InterfaceMethodTag : ConstantTag = 11
	final val StringTag : ConstantTag = 8
	final val IntTag : ConstantTag = 3
	final val FloatTag : ConstantTag = 4
	final val LongTag : ConstantTag = 5
	final val DoubleTag : ConstantTag = 6
	final val SignatureTag : ConstantTag = 12
	final val UTF8Tag : ConstantTag = 1
	final val MethodHandleTag : ConstantTag = 15
	final val MethodTypeTag : ConstantTag = 16
	final val InvokeDynamicTag : ConstantTag = 18

	type ReferenceKind = Int
	final val GetField : ReferenceKind = 1
	final val GetStatic : ReferenceKind = 2
	final val PutField : ReferenceKind = 3
	final val PutStatic : ReferenceKind = 4
	final val InvokeVirtual : ReferenceKind = 5
	final val InvokeStatic : ReferenceKind = 6
	final val InvokeSpecial : ReferenceKind = 7
	final val NewInvokeSpecial : ReferenceKind = 8
	final val InvokeInterface : ReferenceKind = 9

	def referenceOfKind(kind : ReferenceKind, slotRef : SlotRef[_]) : MethodHandle = kind match {
		case GetField ⇒ GETFIELD(slotRef.asInstanceOf[SlotRef[Field]])
		case GetStatic ⇒ GETSTATIC(slotRef.asInstanceOf[SlotRef[Field]])
		case PutField ⇒ PUTFIELD(slotRef.asInstanceOf[SlotRef[Field]])
		case PutStatic ⇒ PUTSTATIC(slotRef.asInstanceOf[SlotRef[Field]])
		case InvokeVirtual ⇒ INVOKEVIRTUAL(slotRef.asInstanceOf[SlotRef[Method]])
		case InvokeStatic ⇒ INVOKESTATIC(slotRef.asInstanceOf[SlotRef[Method]])
		case InvokeSpecial ⇒ INVOKESPECIAL(slotRef.asInstanceOf[SlotRef[Method]])
		case NewInvokeSpecial ⇒ INVOKESPECIAL(slotRef.asInstanceOf[SlotRef[Method]])
		case InvokeInterface ⇒ INVOKEINTERFACE(slotRef.asInstanceOf[SlotRef[Method]])
	}

	def kindOfReference(m : MethodHandle) = m match {
		case GETFIELD(_) ⇒ GetField
		case GETSTATIC(_) ⇒ GetStatic
		case PUTFIELD(_) ⇒ PutField
		case PUTSTATIC(_) ⇒ PutStatic
		case INVOKEVIRTUAL(_) ⇒ InvokeVirtual
		case INVOKESTATIC(_) ⇒ InvokeStatic
		case INVOKESPECIAL(SlotRef(_, _, Signature("<init>", _))) ⇒ NewInvokeSpecial
		case INVOKESPECIAL(_) ⇒ InvokeSpecial
		case INVOKEINTERFACE(slotRef) ⇒ InvokeInterface
	}

	def companion(instance : Any) = {
		import scala.reflect.runtime._
		val rootMirror = universe.runtimeMirror(instance.getClass.getClassLoader)
		val classSymbol = rootMirror.classSymbol(instance.getClass)
		val classMirror = rootMirror.reflectClass(classSymbol)
		val moduleMirror = rootMirror.reflectModule(classMirror.symbol.companionSymbol.asModule)
		moduleMirror.instance
	}

	val flagFromInstruction = Map[Any, Int]( //TODO: Unify with flagFor?
		AALOAD -> 0x32,
		AASTORE -> 0x53,
		ACONST_NULL -> 0x01,
		ALOAD -> 0x19,
		ALOAD_0 -> 0x2A,
		ALOAD_1 -> 0x2B,
		ALOAD_2 -> 0x2C,
		ALOAD_3 -> 0x2D,
		ANEWARRAY -> 0xBD,
		ARETURN -> 0xB0,
		ARRAYLENGTH -> 0xBE,
		ASTORE -> 0x3A,
		ASTORE_0 -> 0x4B,
		ASTORE_1 -> 0x4C,
		ASTORE_2 -> 0x4D,
		ASTORE_3 -> 0x4E,
		ATHROW -> 0xBF,
		BALOAD -> 0x33,
		BASTORE -> 0x54,
		BIPUSH -> 0x10,
		CALOAD -> 0x34,
		CASTORE -> 0x55,
		CHECKCAST -> 0xC0,
		D2F -> 0x90,
		D2I -> 0x8E,
		D2L -> 0x8F,
		DADD -> 0x63,
		DALOAD -> 0x31,
		DASTORE -> 0x52,
		DCMPG -> 0x98,
		DCMPL -> 0x97,
		DCONST_0 -> 0x0E,
		DCONST_1 -> 0x0F,
		DDIV -> 0x6F,
		DLOAD -> 0x18,
		DLOAD_0 -> 0x26,
		DLOAD_1 -> 0x27,
		DLOAD_2 -> 0x28,
		DLOAD_3 -> 0x29,
		DMUL -> 0x6B,
		DNEG -> 0x77,
		DREM -> 0x73,
		DRETURN -> 0xAF,
		DSTORE -> 0x39,
		DSTORE_0 -> 0x47,
		DSTORE_1 -> 0x48,
		DSTORE_2 -> 0x49,
		DSTORE_3 -> 0x4A,
		DSUB -> 0x67,
		DUP -> 0x59,
		DUP_X1 -> 0x5A,
		DUP_X2 -> 0x5B,
		DUP2 -> 0x5C,
		DUP2_X1 -> 0x5D,
		DUP2_X2 -> 0x5E,
		F2D -> 0x8D,
		F2I -> 0x8B,
		F2L -> 0x8C,
		FADD -> 0x62,
		FALOAD -> 0x30,
		FASTORE -> 0x51,
		FCMPG -> 0x96,
		FCMPL -> 0x95,
		FCONST_0 -> 0x0B,
		FCONST_1 -> 0x0C,
		FCONST_2 -> 0x0D,
		FDIV -> 0x6E,
		FLOAD -> 0x17,
		FLOAD_0 -> 0x22,
		FLOAD_1 -> 0x23,
		FLOAD_2 -> 0x24,
		FLOAD_3 -> 0x25,
		FMUL -> 0x6A,
		FNEG -> 0x76,
		FREM -> 0x72,
		FRETURN -> 0xAE,
		FSTORE -> 0x38,
		FSTORE_0 -> 0x43,
		FSTORE_1 -> 0x44,
		FSTORE_2 -> 0x45,
		FSTORE_3 -> 0x46,
		FSUB -> 0x66,
		GETFIELD -> 0xB4,
		GETSTATIC -> 0xB2,
		GOTO -> 0xA7,
		GOTO_W -> 0xC8,
		I2B -> 0x91,
		I2C -> 0x92,
		I2D -> 0x87,
		I2F -> 0x86,
		I2L -> 0x85,
		I2S -> 0x93,
		IADD -> 0x60,
		IALOAD -> 0x2E,
		IAND -> 0x7E,
		IASTORE -> 0x4F,
		ICONST_M1 -> 0x02,
		ICONST_0 -> 0x03,
		ICONST_1 -> 0x04,
		ICONST_2 -> 0x05,
		ICONST_3 -> 0x06,
		ICONST_4 -> 0x07,
		ICONST_5 -> 0x08,
		IDIV -> 0x6C,
		IF_ACMPEQ -> 0xA5,
		IF_ACMPNE -> 0xA6,
		IF_ICMPEQ -> 0x9F,
		IF_ICMPNE -> 0xA0,
		IF_ICMPLT -> 0xA1,
		IF_ICMPGE -> 0xA2,
		IF_ICMPGT -> 0xA3,
		IF_ICMPLE -> 0xA4,
		IFEQ -> 0x99,
		IFNE -> 0x9A,
		IFLT -> 0x9B,
		IFGE -> 0x9C,
		IFGT -> 0x9D,
		IFLE -> 0x9E,
		IFNONNULL -> 0xC7,
		IFNULL -> 0xC6,
		IINC -> 0x84,
		ILOAD -> 0x15,
		ILOAD_0 -> 0x1A,
		ILOAD_1 -> 0x1B,
		ILOAD_2 -> 0x1C,
		ILOAD_3 -> 0x1D,
		IMUL -> 0x68,
		INEG -> 0x74,
		INSTANCEOF -> 0xC1,
		INVOKEDYNAMIC -> 0xBA,
		INVOKEINTERFACE -> 0xB9,
		INVOKESPECIAL -> 0xB7,
		INVOKESTATIC -> 0xB8,
		INVOKEVIRTUAL -> 0xB6,
		IOR -> 0x80,
		IREM -> 0x70,
		IRETURN -> 0xAC,
		ISHL -> 0x78,
		ISHR -> 0x7A,
		ISTORE -> 0x36,
		ISTORE_0 -> 0x3B,
		ISTORE_1 -> 0x3C,
		ISTORE_2 -> 0x3D,
		ISTORE_3 -> 0x3E,
		ISUB -> 0x64,
		IUSHR -> 0x7C,
		IXOR -> 0x82,
		JSR -> 0xA8,
		JSR_W -> 0xC9,
		L2D -> 0x8A,
		L2F -> 0x89,
		L2I -> 0x88,
		LADD -> 0x61,
		LALOAD -> 0x2F,
		LAND -> 0x7F,
		LASTORE -> 0x50,
		LCMP -> 0x94,
		LCONST_0 -> 0x09,
		LCONST_1 -> 0x0A,
		LDC -> 0x12,
		LDC_W -> 0x13,
		LDC2_W -> 0x14,
		LDIV -> 0x6D,
		LLOAD -> 0x16,
		LLOAD_0 -> 0x1E,
		LLOAD_1 -> 0x1F,
		LLOAD_2 -> 0x20,
		LLOAD_3 -> 0x21,
		LMUL -> 0x69,
		LNEG -> 0x75,
		LOOKUPSWITCH -> 0xAB,
		LOR -> 0x81,
		LREM -> 0x71,
		LRETURN -> 0xAD,
		LSHL -> 0x79,
		LSHR -> 0x7B,
		LSTORE -> 0x37,
		LSTORE_0 -> 0x3F,
		LSTORE_1 -> 0x40,
		LSTORE_2 -> 0x41,
		LSTORE_3 -> 0x42,
		LSUB -> 0x65,
		LUSHR -> 0x7D,
		LXOR -> 0x83,
		MONITORENTER -> 0xC2,
		MONITOREXIT -> 0xC3,
		MULTIANEWARRAY -> 0xC5,
		NEW -> 0xBB,
		NEWARRAY -> 0xBC,
		NOP -> 0x00,
		POP -> 0x57,
		POP2 -> 0x58,
		PUTFIELD -> 0xB5,
		PUTSTATIC -> 0xB3,
		RET -> 0xA9,
		RETURN -> 0xB1,
		SALOAD -> 0x35,
		SASTORE -> 0x56,
		SIPUSH -> 0x11,
		SWAP -> 0x5F,
		TABLESWITCH -> 0xAA,
		WIDE -> 0xC4,
		BREAKPOINT -> 0xCA,
		IMPDEP1 -> 0xFE,
		IMPDEP2 -> 0xFF
	)
	val instructionFromFlag = flagFromInstruction.map(_.swap)

	def verificationTypeCode(verificationType : VerificationTypeInfo) = verificationType match {
		case TopType ⇒ 0
		case IntegerType ⇒ 1
		case FloatType ⇒ 2
		case DoubleType ⇒ 3
		case LongType ⇒ 4
		case NullType ⇒ 5
		case UninitializedThis ⇒ 6
		case ObjectVariableInfo(_) ⇒ 7
		case UninitializedVariableInfo(_) ⇒ 8
	}

	val flagFromArrayType = Map(
		$[Boolean] -> 4,
		$[Char] -> 5,
		$[Float] -> 6,
		$[Double] -> 7,
		$[Byte] -> 8,
		$[Short] -> 9,
		$[Int] -> 10,
		$[Long] -> 11
	)
	val arrayTypeFromFlag = flagFromArrayType.map(_.swap)

	private val flags = Map[Modifier[_], Int](
		Visibility.Public -> 0x0001,
		Visibility.Private -> 0x0002,
		Visibility.Protected -> 0x0004,
		Metatype.Enum -> 0x4000,
		Metatype.Interface -> 0x0200,
		Metatype.Class -> 0,
		Metatype.Annotation -> (0x2000 | 0x0200),
		ClassModifier.Super -> 0x0020,
		ClassModifier.Abstract -> 0x0400,
		ClassModifier.Synthetic -> 0x1000,
		ClassModifier.Final -> 0x0010,
		ClassModifier.Static -> 0x0008,
		FieldModifier.Super -> 0x0020,
		FieldModifier.Abstract -> 0x0400,
		FieldModifier.Enum -> 0x4000,
		FieldModifier.Volatile -> 0x0040,
		FieldModifier.Transient -> 0x0080,
		FieldModifier.Synthetic -> 0x1000,
		FieldModifier.Final -> 0x0010,
		FieldModifier.Static -> 0x0008,
		MethodModifier.Super -> 0x0020,
		MethodModifier.Abstract -> 0x0400,
		MethodModifier.Native -> 0x0100,
		MethodModifier.Strict -> 0x0800,
		MethodModifier.Synchronized -> 0x0020,
		MethodModifier.Bridge -> 0x0040,
		MethodModifier.Varargs -> 0x0080,
		MethodModifier.Synthetic -> 0x1000,
		MethodModifier.Final -> 0x0010,
		MethodModifier.Static -> 0x0008
	)

	def flagFor(xs : Modifier[_]*) = xs.foldLeft(0)(_ | flags(_))

	def fromFlag[T : ClassTag](code : Int) = flags.filter {
		case (k : T, c) ⇒ (c & code) == c
		case _ ⇒ false
	}.map(_._1.asInstanceOf[T]).toSet

	// ****************************************************************
	// ** SIZE
	// ****************************************************************

	def byteSize(attribute : Attribute[_ <: Attributable]) : Int = attribute match {
		case ConstantValue(value) ⇒ 2

		case code @ Code(instructions, exceptionHandlers, _) ⇒
			2 + 2 +
				4 + byteSize(instructions) +
				2 + exceptionHandlers.size * 8 +
				2 + code.attributes.map(byteSize(_) + 6).sum

		case Exceptions(checkedExceptions @ _*) ⇒ 2 + checkedExceptions.size * 2

		case RuntimeVisibleParameterAnnotations(parameterAnnotations @ _*) ⇒ 1 + parameterAnnotations.map(2 + _.map(byteSize(_)).sum).sum

		case RuntimeInvisibleParameterAnnotations(parameterAnnotations @ _*) ⇒ 1 + parameterAnnotations.map(2 + _.map(byteSize(_)).sum).sum

		case AnnotationDefault(value : Any) ⇒ annotationValueByteSize(value)

		case StackMapTable(entries @ _*) ⇒ 2 + entries.map(byteSize(_)).sum

		case LineNumberTable(entries) ⇒ 2 + entries.size * 4

		case LocalVariableTable(entries @ _*) ⇒ 2 + entries.size * 10

		case LocalVariableTypeTable(entries @ _*) ⇒ 2 + entries.size * 10

		case InnerClasses(classes @ _*) ⇒ classes.size * 8

		case EnclosingMethod(_) ⇒ 4

		case SourceFile(_) ⇒ 2

		case SourceDebugExtension(info) ⇒ info.size

		case BootstrapMethods(bootstraps) ⇒ 2 + bootstraps.map(2 + 2 + 2 * _.arguments.size).sum

		case Synthetic | Deprecated ⇒ 0

		case SignatureRef(_) ⇒ 2

		case RuntimeVisibleAnnotations(annotations @ _*) ⇒ 2 + annotations.map(byteSize(_)).sum

		case RuntimeInvisibleAnnotations(annotations @ _*) ⇒ 2 + annotations.map(byteSize(_)).sum

		case a : CustomAttribute[_] ⇒ a.byteSize
	}

	def byteSize(frame : StackMapFrame) : Int = 1 + (frame match {
		case AppendFrame(_, locals @ _*) ⇒ 2 + locals.map(byteSize(_)).sum
		case ChopFrame(_, _) ⇒ 2
		case SameFrame(_) ⇒ 0
		case SameFrameExtended(_) ⇒ 2
		case SameLocals1StackItemFrame(_, typeInfo) ⇒ byteSize(typeInfo)
		case SameLocals1StackItemFrameExtended(_, typeInfo) ⇒ 2 + byteSize(typeInfo)
		case FullFrame(_, stack, locals) ⇒ 2 + 2 + stack.map(byteSize(_)).sum + 2 + locals.map(byteSize(_)).sum
	})

	def byteSize(verificationType : VerificationTypeInfo) = verificationType match {
		case ObjectVariableInfo(_) | UninitializedVariableInfo(_) ⇒ 3
		case _ ⇒ 1
	}

	def byteSize(instructions : Seq[Instruction]) : Int = instructions.foldLeft(0){ (acum, instruction) ⇒ byteSize(instruction, acum) + acum }

	def byteSize(instruction : Instruction, codeOffset : Int) : Int = 1 + (instruction match {
		case _ : LocalAccessor ⇒ 1
		case IINC(_, _) ⇒ 2

		case NEWARRAY(_) ⇒ 1
		case NEW(_) | ANEWARRAY(_) | CHECKCAST(_) | INSTANCEOF(_) ⇒ 2
		case MULTIANEWARRAY(_, _, _) ⇒ 3

		case _ : FieldAccessor ⇒ 2

		case LDC(_) | BIPUSH(_) ⇒ 1
		case LDC_W(_) | LDC2_W(_) | SIPUSH(_) ⇒ 2

		case GOTO_W(_) | JSR_W(_) ⇒ 4
		case _ : Jump ⇒ 2

		case INVOKESPECIAL(_) | INVOKESTATIC(_) | INVOKEVIRTUAL(_) ⇒ 2
		case INVOKEINTERFACE(_) | INVOKEDYNAMIC(_, _) ⇒ 4

		case LOOKUPSWITCH(_, ts @ _*) ⇒
			val paddingSize = (4 - (codeOffset + 1) % 4) % 4
			paddingSize + 4 + 4 + ts.size * 2 * 4
		case TABLESWITCH(_, _, ts @ _*) ⇒
			val paddingSize = (4 - (codeOffset + 1) % 4) % 4
			paddingSize + 4 + 4 + 4 + ts.size * 4

		case WIDE(IINC(_, _)) ⇒ 5
		case WIDE(_) ⇒ 3
		case _ ⇒ 0
	})

	def byteSize(annotation : Annotation) : Int = 2 + 2 + annotation.values.map(annotationValueByteSize(_)).sum

	def annotationValueByteSize(v : Any) : Int = 1 + (v match {
		case _ : Data[_] ⇒ 2
		case (_ : String, _ : String) ⇒ 4
		case _ : Type ⇒ 2
		case a : Annotation ⇒ byteSize(a)
		case s : Seq[_] ⇒ 2 + s.map(annotationValueByteSize(_)).sum
	})
}