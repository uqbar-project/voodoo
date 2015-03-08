package org.uqbar.voodoo.javaadapter.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.uqbar.voodoo.javaadapter.builder.MethodBuilder;
import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;

public class ExhaustiveMaxStackTest extends ExhaustiveInstructionTest {

	@Test
	@Override
	public void AALOAD() {
		super.AALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void AASTORE() {
		super.AASTORE();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void ACONST_NULL() {
		super.ACONST_NULL();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ALOAD() {
		super.ALOAD();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ALOAD_0() {
		super.ALOAD_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ALOAD_1() {
		super.ALOAD_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ALOAD_2() {
		super.ALOAD_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ALOAD_3() {
		super.ALOAD_3();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ANEWARRAY() {
		super.ANEWARRAY();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ARETURN() {
		super.ARETURN();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ARRAYLENGTH() {
		super.ARRAYLENGTH();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ASTORE() {
		super.ASTORE();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ASTORE_0() {
		super.ASTORE_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ASTORE_1() {
		super.ASTORE_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ASTORE_2() {
		super.ASTORE_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ASTORE_3() {
		super.ASTORE_3();

		this.assertMaxStackEquals(1);

	}

	@Override
	@Test()
	public void ATHROW() {
		super.ATHROW();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void BALOAD() {
		super.BALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void BASTORE() {
		super.BASTORE();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void BIPUSH() {
		super.BIPUSH();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void CALOAD() {
		super.CALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void CASTORE() {
		super.CASTORE();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void CHECKCAST() {
		super.CHECKCAST();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void D2F() {
		super.D2F();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void D2I() {
		super.D2I();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void D2L() {
		super.D2L();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DADD() {
		super.DADD();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DALOAD() {
		super.DALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DASTORE() {
		super.DASTORE();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DCMPG() {
		super.DCMPG();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DCMPL() {
		super.DCMPL();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DCONST_0() {
		super.DCONST_0();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DCONST_1() {
		super.DCONST_1();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DDIV() {
		super.DDIV();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DLOAD() {
		super.DLOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DLOAD_0() {
		super.DLOAD_0();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DLOAD_1() {
		super.DLOAD_1();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DLOAD_2() {
		super.DLOAD_2();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DLOAD_3() {
		super.DLOAD_3();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DMUL() {
		super.DMUL();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DNEG() {
		super.DNEG();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DREM() {
		super.DREM();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DRETURN() {
		super.DRETURN();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DSTORE() {
		super.DSTORE();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DSTORE_0() {
		super.DSTORE_0();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DSTORE_1() {
		super.DSTORE_1();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DSTORE_2() {
		super.DSTORE_2();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DSTORE_3() {
		super.DSTORE_3();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DSUB() {
		super.DSUB();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DUP() {
		super.DUP();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void DUP_X1() {
		super.DUP_X1();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void DUP_X2() {
		super.DUP_X2();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DUP2() {
		super.DUP2();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void DUP2_X1() {
		super.DUP2_X1();

		this.assertMaxStackEquals(6);

	}

	@Test
	@Override
	public void DUP2_X2() {
		super.DUP2_X2();

		this.assertMaxStackEquals(6);

	}

	@Test
	@Override
	public void F2D() {
		super.F2D();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void F2I() {
		super.F2I();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void F2L() {
		super.F2L();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FADD() {
		super.FADD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FALOAD() {
		super.FALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FASTORE() {
		super.FASTORE();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void FCMPG() {
		super.FCMPG();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FCMPL() {
		super.FCMPL();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FCONST_0() {
		super.FCONST_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FCONST_1() {
		super.FCONST_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FCONST_2() {
		super.FCONST_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FDIV() {
		super.FDIV();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FLOAD() {
		super.FLOAD();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FLOAD_0() {
		super.FLOAD_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FLOAD_1() {
		super.FLOAD_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FLOAD_2() {
		super.FLOAD_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FLOAD_3() {
		super.FLOAD_3();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FMUL() {
		super.FMUL();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FNEG() {
		super.FNEG();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FREM() {
		super.FREM();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void FRETURN() {
		super.FRETURN();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FSTORE() {
		super.FSTORE();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FSTORE_0() {
		super.FSTORE_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FSTORE_1() {
		super.FSTORE_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FSTORE_2() {
		super.FSTORE_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FSTORE_3() {
		super.FSTORE_3();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void FSUB() {
		super.FSUB();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void GETFIELD() {
		super.GETFIELD();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void GETSTATIC() {
		super.GETSTATIC();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void GOTO() {
		super.GOTO();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void GOTO_W() {
		super.GOTO_W();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void I2B() {
		super.I2B();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void I2C() {
		super.I2C();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void I2D() {
		super.I2D();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void I2F() {
		super.I2F();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void I2L() {
		super.I2L();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void I2S() {
		super.I2S();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IADD() {
		super.IADD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IALOAD() {
		super.IALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IAND() {
		super.IAND();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IASTORE() {
		super.IASTORE();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void ICONST_M1() {
		super.ICONST_M1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ICONST_0() {
		super.ICONST_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ICONST_1() {
		super.ICONST_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ICONST_2() {
		super.ICONST_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ICONST_3() {
		super.ICONST_3();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ICONST_4() {
		super.ICONST_4();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ICONST_5() {
		super.ICONST_5();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IDIV() {
		super.IDIV();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ACMPEQ() {
		super.IF_ACMPEQ();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ACMPNE() {
		super.IF_ACMPNE();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ICMPEQ() {
		super.IF_ICMPEQ();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ICMPNE() {
		super.IF_ICMPNE();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ICMPLT() {
		super.IF_ICMPLT();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ICMPGE() {
		super.IF_ICMPGE();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ICMPGT() {
		super.IF_ICMPGT();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IF_ICMPLE() {
		super.IF_ICMPLE();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IFEQ() {
		super.IFEQ();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IFNE() {
		super.IFNE();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IFLT() {
		super.IFLT();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IFGE() {
		super.IFGE();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IFGT() {
		super.IFGT();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IFLE() {
		super.IFLE();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IFNONNULL() {
		super.IFNONNULL();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IFNULL() {
		super.IFNULL();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IINC() {
		super.IINC();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ILOAD() {
		super.ILOAD();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ILOAD_0() {
		super.ILOAD_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ILOAD_1() {
		super.ILOAD_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ILOAD_2() {
		super.ILOAD_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ILOAD_3() {
		super.ILOAD_3();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IMUL() {
		super.IMUL();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void INEG() {
		super.INEG();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void INSTANCEOF() {
		super.INSTANCEOF();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void INVOKEDYNAMIC() {
		super.INVOKEDYNAMIC();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void INVOKEINTERFACE() {
		super.INVOKEINTERFACE();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void INVOKESPECIAL() {
		super.INVOKESPECIAL();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void INVOKESTATIC() {
		super.INVOKESTATIC();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void INVOKEVIRTUAL() {
		super.INVOKEVIRTUAL();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void IOR() {
		super.IOR();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IREM() {
		super.IREM();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IRETURN() {
		super.IRETURN();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ISHL() {
		super.ISHL();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void ISHR() {
		super.ISHR();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void ISTORE() {
		super.ISTORE();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ISTORE_0() {
		super.ISTORE_0();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ISTORE_1() {
		super.ISTORE_1();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ISTORE_2() {
		super.ISTORE_2();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ISTORE_3() {
		super.ISTORE_3();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void ISUB() {
		super.ISUB();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IUSHR() {
		super.IUSHR();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void IXOR() {
		super.IXOR();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void JSR() {
		super.JSR();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void JSR_W() {
		super.JSR_W();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void L2D() {
		super.L2D();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void L2F() {
		super.L2F();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void L2I() {
		super.L2I();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LADD() {
		super.LADD();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LALOAD() {
		super.LALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LAND() {
		super.LAND();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LASTORE() {
		super.LASTORE();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LCMP() {
		super.LCMP();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LCONST_0() {
		super.LCONST_0();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LCONST_1() {
		super.LCONST_1();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LDC() {
		super.LDC();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void LDC_W() {
		super.LDC_W();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void LDC2_W() {
		super.LDC2_W();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LDIV() {
		super.LDIV();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LLOAD() {
		super.LLOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LLOAD_0() {
		super.LLOAD_0();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LLOAD_1() {
		super.LLOAD_1();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LLOAD_2() {
		super.LLOAD_2();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LLOAD_3() {
		super.LLOAD_3();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LMUL() {
		super.LMUL();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LNEG() {
		super.LNEG();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LOOKUPSWITCH() {
		super.LOOKUPSWITCH();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LOR() {
		super.LOR();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LREM() {
		super.LREM();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LRETURN() {
		super.LRETURN();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LSHL() {
		super.LSHL();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void LSHR() {
		super.LSHR();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void LSTORE() {
		super.LSTORE();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LSTORE_0() {
		super.LSTORE_0();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LSTORE_1() {
		super.LSTORE_1();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LSTORE_2() {
		super.LSTORE_2();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LSTORE_3() {
		super.LSTORE_3();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void LSUB() {
		super.LSUB();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void LUSHR() {
		super.LUSHR();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void LXOR() {
		super.LXOR();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void MONITORENTER() {
		super.MONITORENTER();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void MONITOREXIT() {
		super.MONITOREXIT();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void MULTIANEWARRAY() {
		super.MULTIANEWARRAY();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void NEW() {
		super.NEW();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void NEWARRAY() {
		super.NEWARRAY();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void NOP() {
		super.NOP();

		this.assertMaxStackEquals(0);

	}

	@Test
	@Override
	public void POP() {
		super.POP();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void POP2() {
		super.POP2();

		this.assertMaxStackEquals(4);

	}

	@Test
	@Override
	public void PUTFIELD() {
		super.PUTFIELD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void PUTSTATIC() {
		super.PUTSTATIC();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void RET() {
		super.RET();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void RETURN() {
		super.RETURN();

		this.assertMaxStackEquals(0);

	}

	@Test
	@Override
	public void SALOAD() {
		super.SALOAD();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void SASTORE() {
		super.SASTORE();

		this.assertMaxStackEquals(3);

	}

	@Test
	@Override
	public void SIPUSH() {
		super.SIPUSH();

		this.assertMaxStackEquals(1);

	}

	@Test
	@Override
	public void SWAP() {
		super.SWAP();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void TABLESWITCH() {
		super.TABLESWITCH();

		this.assertMaxStackEquals(2);

	}

	@Test
	@Override
	public void WIDE() {
		super.WIDE();

		this.assertMaxStackEquals(1);

	}

	// ****************************************************************
	// ** ASSERTIONS
	// ****************************************************************

	protected void assertMaxStackEquals(int expected) {
		for(MethodBuilder method : this.getClassBuilder().getMethods()) {
			if(METHOD_NAME.equals(method.getName())) {
				assertEquals(expected, ((CodeAttribute) method.build().getAttributes().get(0)).getMaxStack());
				return;
			}
		}

		fail("No such method");
	}
}