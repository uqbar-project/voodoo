package org.uqbar.voodoo.javaadapter.model.methods.instructions;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromDescriptor;

import java.util.Arrays;

import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.DataConstantReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.InvokeDynamicReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class SimpleInstructionCall implements InstructionCall {

	public static final SimpleInstructionCall AALOAD = new SimpleInstructionCall(BytecodeInstruction.AALOAD);
	public static final SimpleInstructionCall AASTORE = new SimpleInstructionCall(BytecodeInstruction.AASTORE);
	public static final SimpleInstructionCall ACONST_NULL = new SimpleInstructionCall(BytecodeInstruction.ACONST_NULL);

	public static final SimpleInstructionCall ALOAD(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.ALOAD, localIndex);
	}

	public static final SimpleInstructionCall ALOAD_0 = new SimpleInstructionCall(BytecodeInstruction.ALOAD_0);
	public static final SimpleInstructionCall ALOAD_1 = new SimpleInstructionCall(BytecodeInstruction.ALOAD_1);
	public static final SimpleInstructionCall ALOAD_2 = new SimpleInstructionCall(BytecodeInstruction.ALOAD_2);
	public static final SimpleInstructionCall ALOAD_3 = new SimpleInstructionCall(BytecodeInstruction.ALOAD_3);

	public static final SimpleInstructionCall ANEWARRAY(TypeReference type) {
		return new SimpleInstructionCall(BytecodeInstruction.ANEWARRAY, type);
	}

	public static final SimpleInstructionCall ARETURN = new SimpleInstructionCall(BytecodeInstruction.ARETURN);
	public static final SimpleInstructionCall ARRAYLENGTH = new SimpleInstructionCall(BytecodeInstruction.ARRAYLENGTH);

	public static final SimpleInstructionCall ASTORE(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.ASTORE, localIndex);
	}

	public static final SimpleInstructionCall ASTORE_0 = new SimpleInstructionCall(BytecodeInstruction.ASTORE_0);
	public static final SimpleInstructionCall ASTORE_1 = new SimpleInstructionCall(BytecodeInstruction.ASTORE_1);
	public static final SimpleInstructionCall ASTORE_2 = new SimpleInstructionCall(BytecodeInstruction.ASTORE_2);
	public static final SimpleInstructionCall ASTORE_3 = new SimpleInstructionCall(BytecodeInstruction.ASTORE_3);
	public static final SimpleInstructionCall ATHROW = new SimpleInstructionCall(BytecodeInstruction.ATHROW);
	public static final SimpleInstructionCall BALOAD = new SimpleInstructionCall(BytecodeInstruction.BALOAD);
	public static final SimpleInstructionCall BASTORE = new SimpleInstructionCall(BytecodeInstruction.BASTORE);

	public static final SimpleInstructionCall BIPUSH(int aByte) {
		return new SimpleInstructionCall(BytecodeInstruction.BIPUSH, aByte);
	}

	public static final SimpleInstructionCall CALOAD = new SimpleInstructionCall(BytecodeInstruction.CALOAD);
	public static final SimpleInstructionCall CASTORE = new SimpleInstructionCall(BytecodeInstruction.CASTORE);

	public static final SimpleInstructionCall CHECKCAST(TypeReference type) {
		return new SimpleInstructionCall(BytecodeInstruction.CHECKCAST, type);
	}

	public static final SimpleInstructionCall D2F = new SimpleInstructionCall(BytecodeInstruction.D2F);
	public static final SimpleInstructionCall D2I = new SimpleInstructionCall(BytecodeInstruction.D2I);
	public static final SimpleInstructionCall D2L = new SimpleInstructionCall(BytecodeInstruction.D2L);
	public static final SimpleInstructionCall DADD = new SimpleInstructionCall(BytecodeInstruction.DADD);
	public static final SimpleInstructionCall DALOAD = new SimpleInstructionCall(BytecodeInstruction.DALOAD);
	public static final SimpleInstructionCall DASTORE = new SimpleInstructionCall(BytecodeInstruction.DASTORE);
	public static final SimpleInstructionCall DCMPG = new SimpleInstructionCall(BytecodeInstruction.DCMPG);
	public static final SimpleInstructionCall DCMPL = new SimpleInstructionCall(BytecodeInstruction.DCMPL);
	public static final SimpleInstructionCall DCONST_0 = new SimpleInstructionCall(BytecodeInstruction.DCONST_0);
	public static final SimpleInstructionCall DCONST_1 = new SimpleInstructionCall(BytecodeInstruction.DCONST_1);
	public static final SimpleInstructionCall DDIV = new SimpleInstructionCall(BytecodeInstruction.DDIV);

	public static final SimpleInstructionCall DLOAD(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.DLOAD, localIndex);
	}

	public static final SimpleInstructionCall DLOAD_0 = new SimpleInstructionCall(BytecodeInstruction.DLOAD_0);
	public static final SimpleInstructionCall DLOAD_1 = new SimpleInstructionCall(BytecodeInstruction.DLOAD_1);
	public static final SimpleInstructionCall DLOAD_2 = new SimpleInstructionCall(BytecodeInstruction.DLOAD_2);
	public static final SimpleInstructionCall DLOAD_3 = new SimpleInstructionCall(BytecodeInstruction.DLOAD_3);
	public static final SimpleInstructionCall DMUL = new SimpleInstructionCall(BytecodeInstruction.DMUL);
	public static final SimpleInstructionCall DNEG = new SimpleInstructionCall(BytecodeInstruction.DNEG);
	public static final SimpleInstructionCall DREM = new SimpleInstructionCall(BytecodeInstruction.DREM);
	public static final SimpleInstructionCall DRETURN = new SimpleInstructionCall(BytecodeInstruction.DRETURN);

	public static final SimpleInstructionCall DSTORE(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.DSTORE, localIndex);
	}

	public static final SimpleInstructionCall DSTORE_0 = new SimpleInstructionCall(BytecodeInstruction.DSTORE_0);
	public static final SimpleInstructionCall DSTORE_1 = new SimpleInstructionCall(BytecodeInstruction.DSTORE_1);
	public static final SimpleInstructionCall DSTORE_2 = new SimpleInstructionCall(BytecodeInstruction.DSTORE_2);
	public static final SimpleInstructionCall DSTORE_3 = new SimpleInstructionCall(BytecodeInstruction.DSTORE_3);
	public static final SimpleInstructionCall DSUB = new SimpleInstructionCall(BytecodeInstruction.DSUB);
	public static final SimpleInstructionCall DUP = new SimpleInstructionCall(BytecodeInstruction.DUP);
	public static final SimpleInstructionCall DUP_X1 = new SimpleInstructionCall(BytecodeInstruction.DUP_X1);
	public static final SimpleInstructionCall DUP_X2 = new SimpleInstructionCall(BytecodeInstruction.DUP_X2);
	public static final SimpleInstructionCall DUP2 = new SimpleInstructionCall(BytecodeInstruction.DUP2);
	public static final SimpleInstructionCall DUP2_X1 = new SimpleInstructionCall(BytecodeInstruction.DUP2_X1);
	public static final SimpleInstructionCall DUP2_X2 = new SimpleInstructionCall(BytecodeInstruction.DUP2_X2);
	public static final SimpleInstructionCall F2D = new SimpleInstructionCall(BytecodeInstruction.F2D);
	public static final SimpleInstructionCall F2I = new SimpleInstructionCall(BytecodeInstruction.F2I);
	public static final SimpleInstructionCall F2L = new SimpleInstructionCall(BytecodeInstruction.F2L);
	public static final SimpleInstructionCall FADD = new SimpleInstructionCall(BytecodeInstruction.FADD);
	public static final SimpleInstructionCall FALOAD = new SimpleInstructionCall(BytecodeInstruction.FALOAD);
	public static final SimpleInstructionCall FASTORE = new SimpleInstructionCall(BytecodeInstruction.FASTORE);
	public static final SimpleInstructionCall FCMPG = new SimpleInstructionCall(BytecodeInstruction.FCMPG);
	public static final SimpleInstructionCall FCMPL = new SimpleInstructionCall(BytecodeInstruction.FCMPL);
	public static final SimpleInstructionCall FCONST_0 = new SimpleInstructionCall(BytecodeInstruction.FCONST_0);
	public static final SimpleInstructionCall FCONST_1 = new SimpleInstructionCall(BytecodeInstruction.FCONST_1);
	public static final SimpleInstructionCall FCONST_2 = new SimpleInstructionCall(BytecodeInstruction.FCONST_2);
	public static final SimpleInstructionCall FDIV = new SimpleInstructionCall(BytecodeInstruction.FDIV);

	public static final SimpleInstructionCall FLOAD(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.FLOAD, localIndex);
	}

	public static final SimpleInstructionCall FLOAD_0 = new SimpleInstructionCall(BytecodeInstruction.FLOAD_0);
	public static final SimpleInstructionCall FLOAD_1 = new SimpleInstructionCall(BytecodeInstruction.FLOAD_1);
	public static final SimpleInstructionCall FLOAD_2 = new SimpleInstructionCall(BytecodeInstruction.FLOAD_2);
	public static final SimpleInstructionCall FLOAD_3 = new SimpleInstructionCall(BytecodeInstruction.FLOAD_3);
	public static final SimpleInstructionCall FMUL = new SimpleInstructionCall(BytecodeInstruction.FMUL);
	public static final SimpleInstructionCall FNEG = new SimpleInstructionCall(BytecodeInstruction.FNEG);
	public static final SimpleInstructionCall FREM = new SimpleInstructionCall(BytecodeInstruction.FREM);
	public static final SimpleInstructionCall FRETURN = new SimpleInstructionCall(BytecodeInstruction.FRETURN);

	public static final SimpleInstructionCall FSTORE(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.FSTORE, localIndex);
	}

	public static final SimpleInstructionCall FSTORE_0 = new SimpleInstructionCall(BytecodeInstruction.FSTORE_0);
	public static final SimpleInstructionCall FSTORE_1 = new SimpleInstructionCall(BytecodeInstruction.FSTORE_1);
	public static final SimpleInstructionCall FSTORE_2 = new SimpleInstructionCall(BytecodeInstruction.FSTORE_2);
	public static final SimpleInstructionCall FSTORE_3 = new SimpleInstructionCall(BytecodeInstruction.FSTORE_3);
	public static final SimpleInstructionCall FSUB = new SimpleInstructionCall(BytecodeInstruction.FSUB);

	public static final SimpleInstructionCall GETFIELD(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.GETFIELD, slot);
	}

	public static final SimpleInstructionCall GETSTATIC(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.GETSTATIC, slot);
	}

	public static final SimpleInstructionCall GOTO(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.GOTO, jumpTarget);
	}

	// TODO: Que en el builder se elija automaticamente entre GOTO y GOTO_W
	public static final SimpleInstructionCall GOTO_W(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.GOTO_W, jumpTarget);
	}

	public static final SimpleInstructionCall I2B = new SimpleInstructionCall(BytecodeInstruction.I2B);
	public static final SimpleInstructionCall I2C = new SimpleInstructionCall(BytecodeInstruction.I2C);
	public static final SimpleInstructionCall I2D = new SimpleInstructionCall(BytecodeInstruction.I2D);
	public static final SimpleInstructionCall I2F = new SimpleInstructionCall(BytecodeInstruction.I2F);
	public static final SimpleInstructionCall I2L = new SimpleInstructionCall(BytecodeInstruction.I2L);
	public static final SimpleInstructionCall I2S = new SimpleInstructionCall(BytecodeInstruction.I2S);
	public static final SimpleInstructionCall IADD = new SimpleInstructionCall(BytecodeInstruction.IADD);
	public static final SimpleInstructionCall IALOAD = new SimpleInstructionCall(BytecodeInstruction.IALOAD);
	public static final SimpleInstructionCall IAND = new SimpleInstructionCall(BytecodeInstruction.IAND);
	public static final SimpleInstructionCall IASTORE = new SimpleInstructionCall(BytecodeInstruction.IASTORE);
	public static final SimpleInstructionCall ICONST_M1 = new SimpleInstructionCall(BytecodeInstruction.ICONST_M1);
	public static final SimpleInstructionCall ICONST_0 = new SimpleInstructionCall(BytecodeInstruction.ICONST_0);
	public static final SimpleInstructionCall ICONST_1 = new SimpleInstructionCall(BytecodeInstruction.ICONST_1);
	public static final SimpleInstructionCall ICONST_2 = new SimpleInstructionCall(BytecodeInstruction.ICONST_2);
	public static final SimpleInstructionCall ICONST_3 = new SimpleInstructionCall(BytecodeInstruction.ICONST_3);
	public static final SimpleInstructionCall ICONST_4 = new SimpleInstructionCall(BytecodeInstruction.ICONST_4);
	public static final SimpleInstructionCall ICONST_5 = new SimpleInstructionCall(BytecodeInstruction.ICONST_5);
	public static final SimpleInstructionCall IDIV = new SimpleInstructionCall(BytecodeInstruction.IDIV);

	public static final SimpleInstructionCall IF_ACMPEQ(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ACMPEQ, jumpTarget);
	}

	public static final SimpleInstructionCall IF_ACMPNE(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ACMPNE, jumpTarget);
	}

	public static final SimpleInstructionCall IF_ICMPEQ(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ICMPEQ, jumpTarget);
	}

	public static final SimpleInstructionCall IF_ICMPNE(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ICMPNE, jumpTarget);
	}

	public static final SimpleInstructionCall IF_ICMPLT(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ICMPLT, jumpTarget);
	}

	public static final SimpleInstructionCall IF_ICMPGE(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ICMPGE, jumpTarget);
	}

	public static final SimpleInstructionCall IF_ICMPGT(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ICMPGT, jumpTarget);
	}

	public static final SimpleInstructionCall IF_ICMPLE(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IF_ICMPLE, jumpTarget);
	}

	public static final SimpleInstructionCall IFEQ(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFEQ, jumpTarget);
	}

	public static final SimpleInstructionCall IFNE(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFNE, jumpTarget);
	}

	public static final SimpleInstructionCall IFLT(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFLT, jumpTarget);
	}

	public static final SimpleInstructionCall IFGE(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFGE, jumpTarget);
	}

	public static final SimpleInstructionCall IFGT(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFGT, jumpTarget);
	}

	public static final SimpleInstructionCall IFLE(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFLE, jumpTarget);
	}

	public static final SimpleInstructionCall IFNONNULL(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFNONNULL, jumpTarget);
	}

	public static final SimpleInstructionCall IFNULL(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.IFNULL, jumpTarget);
	}

	public static final SimpleInstructionCall IINC(int localIndex, int increment) {
		return new SimpleInstructionCall(BytecodeInstruction.IINC, localIndex, increment);
	}

	public static final SimpleInstructionCall ILOAD(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.ILOAD, localIndex);
	}

	public static final SimpleInstructionCall ILOAD_0 = new SimpleInstructionCall(BytecodeInstruction.ILOAD_0);
	public static final SimpleInstructionCall ILOAD_1 = new SimpleInstructionCall(BytecodeInstruction.ILOAD_1);
	public static final SimpleInstructionCall ILOAD_2 = new SimpleInstructionCall(BytecodeInstruction.ILOAD_2);
	public static final SimpleInstructionCall ILOAD_3 = new SimpleInstructionCall(BytecodeInstruction.ILOAD_3);
	public static final SimpleInstructionCall IMUL = new SimpleInstructionCall(BytecodeInstruction.IMUL);
	public static final SimpleInstructionCall INEG = new SimpleInstructionCall(BytecodeInstruction.INEG);

	public static final SimpleInstructionCall INSTANCEOF(TypeReference type) {
		return new SimpleInstructionCall(BytecodeInstruction.INSTANCEOF, type);
	}

	public static final SimpleInstructionCall INVOKEDYNAMIC(InvokeDynamicReference invokeDynamic) {
		return new SimpleInstructionCall(BytecodeInstruction.INVOKEDYNAMIC, invokeDynamic);
	}

	public static final SimpleInstructionCall INVOKEINTERFACE(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.INVOKEINTERFACE, slot);
	}

	public static final SimpleInstructionCall INVOKESPECIAL(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.INVOKESPECIAL, slot);
	}

	public static final SimpleInstructionCall INVOKESTATIC(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.INVOKESTATIC, slot);
	}

	public static final SimpleInstructionCall INVOKEVIRTUAL(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.INVOKEVIRTUAL, slot);
	}

	public static final SimpleInstructionCall IOR = new SimpleInstructionCall(BytecodeInstruction.IOR);
	public static final SimpleInstructionCall IREM = new SimpleInstructionCall(BytecodeInstruction.IREM);
	public static final SimpleInstructionCall IRETURN = new SimpleInstructionCall(BytecodeInstruction.IRETURN);
	public static final SimpleInstructionCall ISHL = new SimpleInstructionCall(BytecodeInstruction.ISHL);
	public static final SimpleInstructionCall ISHR = new SimpleInstructionCall(BytecodeInstruction.ISHR);

	public static final SimpleInstructionCall ISTORE(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.ISTORE, localIndex);
	}

	public static final SimpleInstructionCall ISTORE_0 = new SimpleInstructionCall(BytecodeInstruction.ISTORE_0);
	public static final SimpleInstructionCall ISTORE_1 = new SimpleInstructionCall(BytecodeInstruction.ISTORE_1);
	public static final SimpleInstructionCall ISTORE_2 = new SimpleInstructionCall(BytecodeInstruction.ISTORE_2);
	public static final SimpleInstructionCall ISTORE_3 = new SimpleInstructionCall(BytecodeInstruction.ISTORE_3);
	public static final SimpleInstructionCall ISUB = new SimpleInstructionCall(BytecodeInstruction.ISUB);
	public static final SimpleInstructionCall IUSHR = new SimpleInstructionCall(BytecodeInstruction.IUSHR);
	public static final SimpleInstructionCall IXOR = new SimpleInstructionCall(BytecodeInstruction.IXOR);

	public static final SimpleInstructionCall JSR(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.JSR, jumpTarget);
	}

	// TODO: Que en el builder se elija automáticamente entre JSR y JSR_W
	public static final SimpleInstructionCall JSR_W(TargetedInstructionCall jumpTarget) {
		return new SimpleInstructionCall(BytecodeInstruction.JSR_W, jumpTarget);
	}

	public static final SimpleInstructionCall L2D = new SimpleInstructionCall(BytecodeInstruction.L2D);
	public static final SimpleInstructionCall L2F = new SimpleInstructionCall(BytecodeInstruction.L2F);
	public static final SimpleInstructionCall L2I = new SimpleInstructionCall(BytecodeInstruction.L2I);
	public static final SimpleInstructionCall LADD = new SimpleInstructionCall(BytecodeInstruction.LADD);
	public static final SimpleInstructionCall LALOAD = new SimpleInstructionCall(BytecodeInstruction.LALOAD);
	public static final SimpleInstructionCall LAND = new SimpleInstructionCall(BytecodeInstruction.LAND);
	public static final SimpleInstructionCall LASTORE = new SimpleInstructionCall(BytecodeInstruction.LASTORE);
	public static final SimpleInstructionCall LCMP = new SimpleInstructionCall(BytecodeInstruction.LCMP);
	public static final SimpleInstructionCall LCONST_0 = new SimpleInstructionCall(BytecodeInstruction.LCONST_0);
	public static final SimpleInstructionCall LCONST_1 = new SimpleInstructionCall(BytecodeInstruction.LCONST_1);

	public static final SimpleInstructionCall LDC(String constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC, new DataConstantReference(constant));
	}

	public static final SimpleInstructionCall LDC(int constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC, new DataConstantReference(constant));
	}

	public static final SimpleInstructionCall LDC(float constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC, new DataConstantReference(constant));
	}

	// TODO: Que en el builder se elija automáticamente entre LDC y LDC_W
	// TODO: Podría haber una instrucción que elija entre LDC, LDC_W, LDC2_W, BIPUSH, SIPUSH y los CONS
	public static final SimpleInstructionCall LDC_W(String constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC_W, new DataConstantReference(constant));
	}

	public static final SimpleInstructionCall LDC_W(int constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC_W, new DataConstantReference(constant));
	}

	public static final SimpleInstructionCall LDC_W(float constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC_W, new DataConstantReference(constant));
	}

	public static final SimpleInstructionCall LDC2_W(long constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC2_W, new DataConstantReference(constant));
	}

	public static final SimpleInstructionCall LDC2_W(double constant) {
		return new SimpleInstructionCall(BytecodeInstruction.LDC2_W, new DataConstantReference(constant));
	}

	public static final SimpleInstructionCall LDIV = new SimpleInstructionCall(BytecodeInstruction.LDIV);

	public static final SimpleInstructionCall LLOAD(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.LLOAD, localIndex);
	}

	public static final SimpleInstructionCall LLOAD_0 = new SimpleInstructionCall(BytecodeInstruction.LLOAD_0);
	public static final SimpleInstructionCall LLOAD_1 = new SimpleInstructionCall(BytecodeInstruction.LLOAD_1);
	public static final SimpleInstructionCall LLOAD_2 = new SimpleInstructionCall(BytecodeInstruction.LLOAD_2);
	public static final SimpleInstructionCall LLOAD_3 = new SimpleInstructionCall(BytecodeInstruction.LLOAD_3);
	public static final SimpleInstructionCall LMUL = new SimpleInstructionCall(BytecodeInstruction.LMUL);
	public static final SimpleInstructionCall LNEG = new SimpleInstructionCall(BytecodeInstruction.LNEG);

	// TODO: Reemplazar el varargs por Links, como en el builder
	public static final SimpleInstructionCall LOOKUPSWITCH(TargetedInstructionCall defaultJumpTarget, int... keyValueOffsets) {
		Object[] args = new Object[keyValueOffsets.length + 1];
		args[0] = defaultJumpTarget;

		if(keyValueOffsets.length % 2 != 0) {
			throw new RuntimeException("Invalid number of arguments: There is a Key without Offset!");
		}

		for(int i = 0; i < keyValueOffsets.length; i++) {
			args[i + 1] = keyValueOffsets[i];
		}

		return new SimpleInstructionCall(BytecodeInstruction.LOOKUPSWITCH, args);
	}

	public static final SimpleInstructionCall LOR = new SimpleInstructionCall(BytecodeInstruction.LOR);
	public static final SimpleInstructionCall LREM = new SimpleInstructionCall(BytecodeInstruction.LREM);
	public static final SimpleInstructionCall LRETURN = new SimpleInstructionCall(BytecodeInstruction.LRETURN);
	public static final SimpleInstructionCall LSHL = new SimpleInstructionCall(BytecodeInstruction.LSHL);
	public static final SimpleInstructionCall LSHR = new SimpleInstructionCall(BytecodeInstruction.LSHR);

	public static final SimpleInstructionCall LSTORE(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.LSTORE, localIndex);
	}

	public static final SimpleInstructionCall LSTORE_0 = new SimpleInstructionCall(BytecodeInstruction.LSTORE_0);
	public static final SimpleInstructionCall LSTORE_1 = new SimpleInstructionCall(BytecodeInstruction.LSTORE_1);
	public static final SimpleInstructionCall LSTORE_2 = new SimpleInstructionCall(BytecodeInstruction.LSTORE_2);
	public static final SimpleInstructionCall LSTORE_3 = new SimpleInstructionCall(BytecodeInstruction.LSTORE_3);
	public static final SimpleInstructionCall LSUB = new SimpleInstructionCall(BytecodeInstruction.LSUB);
	public static final SimpleInstructionCall LUSHR = new SimpleInstructionCall(BytecodeInstruction.LUSHR);
	public static final SimpleInstructionCall LXOR = new SimpleInstructionCall(BytecodeInstruction.LXOR);
	public static final SimpleInstructionCall MONITORENTER = new SimpleInstructionCall(BytecodeInstruction.MONITORENTER);
	public static final SimpleInstructionCall MONITOREXIT = new SimpleInstructionCall(BytecodeInstruction.MONITOREXIT);

	public static final SimpleInstructionCall MULTIANEWARRAY(TypeReference type, int dimensionCount, int dimensionSizesToPop) {
		TypeReference typeReference = typeFromDescriptor(type.getDescriptor());
		for(int i = 0; i < dimensionCount; i++) {
			typeReference = typeReference.arrayed();
		}

		return new SimpleInstructionCall(BytecodeInstruction.MULTIANEWARRAY, typeReference, dimensionSizesToPop);
	}

	public static final SimpleInstructionCall NEW(TypeReference type) {
		return new SimpleInstructionCall(BytecodeInstruction.NEW, type);
	}

	public static final SimpleInstructionCall NEWARRAY(Class<?> type) {
		int typeCode;

		// TODO: Esto es horrible... Acá debería haber un TypeReference...
		if(type.equals(boolean.class)) {
			typeCode = 4;
		}
		else if(type.equals(char.class)) {
			typeCode = 5;
		}
		else if(type.equals(float.class)) {
			typeCode = 6;
		}
		else if(type.equals(double.class)) {
			typeCode = 7;
		}
		else if(type.equals(byte.class)) {
			typeCode = 8;
		}
		else if(type.equals(short.class)) {
			typeCode = 9;
		}
		else if(type.equals(int.class)) {
			typeCode = 10;
		}
		else if(type.equals(long.class)) {
			typeCode = 11;
		}
		else {
			throw new RuntimeException("Unsuported NEWARRAY type: " + type);
		}

		return new SimpleInstructionCall(BytecodeInstruction.NEWARRAY, typeCode);
	}

	public static final SimpleInstructionCall NOP = new SimpleInstructionCall(BytecodeInstruction.NOP);
	public static final SimpleInstructionCall POP = new SimpleInstructionCall(BytecodeInstruction.POP);
	public static final SimpleInstructionCall POP2 = new SimpleInstructionCall(BytecodeInstruction.POP2);

	public static final SimpleInstructionCall PUTFIELD(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.PUTFIELD, slot);
	}

	public static final SimpleInstructionCall PUTSTATIC(SlotReference slot) {
		return new SimpleInstructionCall(BytecodeInstruction.PUTSTATIC, slot);
	}

	public static final SimpleInstructionCall RET(int localIndex) {
		return new SimpleInstructionCall(BytecodeInstruction.RET, localIndex);
	}

	public static final SimpleInstructionCall RETURN = new SimpleInstructionCall(BytecodeInstruction.RETURN);
	public static final SimpleInstructionCall SALOAD = new SimpleInstructionCall(BytecodeInstruction.SALOAD);
	public static final SimpleInstructionCall SASTORE = new SimpleInstructionCall(BytecodeInstruction.SASTORE);

	public static final SimpleInstructionCall SIPUSH(int aShort) {
		return new SimpleInstructionCall(BytecodeInstruction.SIPUSH, aShort);
	}

	public static final SimpleInstructionCall SWAP = new SimpleInstructionCall(BytecodeInstruction.SWAP);

	// TODO: Que elija si usar esta o lookupswitch dependiendo de las claves
	public static final SimpleInstructionCall TABLESWITCH(int from, TargetedInstructionCall defaultJumpTarget, TargetedInstructionCall... callTargets) {
		Object[] args = new Object[callTargets.length + 2];
		args[0] = defaultJumpTarget;
		args[1] = from;

		for(int i = 0; i < callTargets.length; i++) {
			args[i + 2] = callTargets[i];
		}

		return new SimpleInstructionCall(BytecodeInstruction.TABLESWITCH, args);
	}

	public static final SimpleInstructionCall WIDE(InstructionCall innerCall) {
		return new SimpleInstructionCall(BytecodeInstruction.WIDE, innerCall);
	}

	public static final InstructionCall BREAKPOINT = new SimpleInstructionCall(BytecodeInstruction.BREAKPOINT);
	public static final InstructionCall IMPDEP1 = new SimpleInstructionCall(BytecodeInstruction.IMPDEP1);
	public static final InstructionCall IMPDEP2 = new SimpleInstructionCall(BytecodeInstruction.IMPDEP2);

	private BytecodeInstruction instruction;
	private Object[] arguments;

	private CodeAttribute owner;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public SimpleInstructionCall(BytecodeInstruction instruction, Object... arguments) {
		this.instruction = instruction;
		this.arguments = arguments;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		this.getInstruction().fillConstantPool(constantPool, this);
	}

	// ****************************************************************
	// ** BYTECODE WRITTING
	// ****************************************************************

	// TODO: No pedirle a la instruccion sus args, que los escriba ella.
	@Override
	public void writeTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		this.getInstruction().writeTo(output);

		output.write(this.getInstruction().getArgumentBytes(this, constantPool, callContext));
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getStackPopCount() {
		return this.getInstruction().getStackPopCount(this);
	}

	@Override
	public int getStackPushCount() {
		return this.getInstruction().getStackPushCount(this);
	}

	@Override
	public int getMinLocalsSizeRequired() {
		return this.getInstruction().getMinLocalsSizeRequired(this);
	}

	@Override
	public int getByteCount() {
		return this.getInstruction().getArgumentByteCount(this) + 1;
	}

	@Override
	public InstructionCall asJumpTarget() {
		return new TargetedInstructionCall(this);
	}

	@Override
	public int getAbsoluteOffset() {
		return this.getOwner().getCallAbsoluteOffset(this);
	}

	@Override
	public int getCodeOffset() {
		return this.getAbsoluteOffset() - this.getOwner().getFirstCallAbsoluteOffset();
	}

	@Override
	public String toString() {
		String argumentString = "";

		for(int i = 0; i < this.getArguments().length; i++) {
			argumentString += this.getArguments()[i].toString() + (i < this.getArguments().length - 1 ? ", " : "");
		}

		return this.getInstruction() + "(" + argumentString + ")";
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!(object instanceof InstructionCall)) {
			return false;
		}

		InstructionCall instructionCall = (InstructionCall) object;

		return instructionCall.getInstruction().equals(this.getInstruction())
				&& Arrays.equals(instructionCall.getArguments(), this.getArguments());
	}

	@Override
	public int hashCode() {
		return this.getInstruction().hashCode();
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getArgument(int index) {
		return (T) this.getArguments()[index];
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	@Override
	public BytecodeInstruction getInstruction() {
		return this.instruction;
	}

	protected void setInstruction(BytecodeInstruction instruction) {
		this.instruction = instruction;
	}

	@Override
	public Object[] getArguments() {
		return this.arguments;
	}

	protected void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	@Override
	public CodeAttribute getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(CodeAttribute owner) {
		this.owner = owner;
	}
}