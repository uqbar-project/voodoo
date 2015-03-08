package org.uqbar.voodoo.javaadapter.builder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.uqbar.voodoo.javaadapter.BytecodeClassLoader;
import org.uqbar.voodoo.javaadapter.builder.auxiliars.Blah;

public class ExhaustiveBuildTest extends ExhaustiveInstructionTest {

	// ****************************************************************
	// ** TEST CASES
	// ****************************************************************

	@Test
	@Override
	public void AALOAD() {
		super.AALOAD();

		Object[] arg = new Object[] { new Blah() };
		Object answer = this.invoke(METHOD_NAME, (Object) arg);

		assertEquals(arg[0], answer);
	}

	@Test
	@Override
	public void AASTORE() {
		super.AASTORE();

		Object arg1 = new Blah();
		Object[] arg2 = new Object[1];
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg1, arg2[0]);
	}

	@Test
	@Override
	public void ACONST_NULL() {
		super.ACONST_NULL();

		Object answer = this.invoke(METHOD_NAME);

		assertEquals(null, answer);
	}

	@Test
	@Override
	public void ALOAD() {
		super.ALOAD();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ALOAD_0() {
		super.ALOAD_0();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ALOAD_1() {
		super.ALOAD_1();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ALOAD_2() {
		super.ALOAD_2();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, null, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ALOAD_3() {
		super.ALOAD_3();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, null, null, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ANEWARRAY() {
		super.ANEWARRAY();

		Object[] answer = (Object[]) this.invoke(METHOD_NAME);

		assertArrayEquals(new Object[5], answer);
	}

	@Test
	@Override
	public void ARETURN() {
		super.ARETURN();

		Object answer = this.invoke(METHOD_NAME);

		assertEquals(null, answer);
	}

	@Test
	@Override
	public void ARRAYLENGTH() {
		super.ARRAYLENGTH();

		Object[] arg = new Object[5];
		Object answer = this.invoke(METHOD_NAME, (Object) arg);

		assertEquals(arg.length, answer);
	}

	@Test
	@Override
	public void ASTORE() {
		super.ASTORE();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ASTORE_0() {
		super.ASTORE_0();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ASTORE_1() {
		super.ASTORE_1();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ASTORE_2() {
		super.ASTORE_2();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ASTORE_3() {
		super.ASTORE_3();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Override
	@Test(expected = ArithmeticException.class)
	public void ATHROW() {
		super.ATHROW();

		this.invoke(METHOD_NAME);
	}

	@Test
	@Override
	public void BALOAD() {
		super.BALOAD();

		boolean[] arg = new boolean[] { false };
		boolean answer = (boolean) this.invoke(METHOD_NAME, arg);

		assertEquals(arg[0], answer);
	}

	@Test
	@Override
	public void BASTORE() {
		super.BASTORE();

		boolean arg1 = true;
		boolean[] arg2 = new boolean[1];
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg1, arg2[0]);
	}

	@Test
	@Override
	public void BIPUSH() {
		super.BIPUSH();

		byte answer = (byte) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void CALOAD() {
		super.CALOAD();

		char[] arg1 = new char[] { 'c' };
		char answer1 = (char) this.invoke(METHOD_NAME, arg1);

		assertEquals(arg1[0], answer1);
	}

	@Test
	@Override
	public void CASTORE() {
		super.CASTORE();

		char arg1_1 = 'c';
		char[] arg1_2 = new char[1];
		this.invoke(METHOD_NAME, arg1_1, arg1_2);

		assertEquals(arg1_1, arg1_2[0]);
	}

	@Test
	@Override
	public void CHECKCAST() {
		super.CHECKCAST();

		Object arg = new Blah();
		Object answer = this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void D2F() {
		super.D2F();

		double arg = 5;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void D2I() {
		super.D2I();

		double arg = 5.4;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(5, answer, 0);
	}

	@Test
	@Override
	public void D2L() {
		super.D2L();

		double arg = 5.4;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(5, answer, 0);
	}

	@Test
	@Override
	public void DADD() {
		super.DADD();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(5.0, answer, 0);
	}

	@Test
	@Override
	public void DALOAD() {
		super.DALOAD();

		double[] arg = new double[] { 3.7 };
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg[0], answer, 0);
	}

	@Test
	@Override
	public void DASTORE() {
		super.DASTORE();

		double arg1 = 3.7;
		double[] arg2 = new double[1];
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg1, arg2[0], 0);
	}

	@Test
	@Override
	public void DCMPG() {
		super.DCMPG();

		double arg1 = 3.7;
		double arg2 = 2.5;
		double arg3 = 0.0 / 1.0 / 0.0;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer4 = (int) this.invoke(METHOD_NAME, arg1, arg3);
		int answer5 = (int) this.invoke(METHOD_NAME, arg3, arg1);

		assertEquals(1, answer1);
		assertEquals(-1, answer2);
		assertEquals(0, answer3);
		assertEquals(1, answer4);
		assertEquals(1, answer5);
	}

	@Test
	@Override
	public void DCMPL() {
		super.DCMPL();

		double arg1 = 3.7;
		double arg2 = 2.5;
		double arg3 = 0 / 1.0 / 0;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer4 = (int) this.invoke(METHOD_NAME, arg1, arg3);
		int answer5 = (int) this.invoke(METHOD_NAME, arg3, arg1);

		assertEquals(1, answer1);
		assertEquals(-1, answer2);
		assertEquals(0, answer3);
		assertEquals(-1, answer4);
		assertEquals(-1, answer5);
	}

	@Test
	@Override
	public void DCONST_0() {
		super.DCONST_0();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(0.0, answer, 0);
	}

	@Test
	@Override
	public void DCONST_1() {
		super.DCONST_1();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(1.0, answer, 0);
	}

	@Test
	@Override
	public void DDIV() {
		super.DDIV();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(2.5, answer, 0);
	}

	@Test
	@Override
	public void DLOAD() {
		super.DLOAD();

		double arg = 3.5;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void DLOAD_0() {
		super.DLOAD_0();

		double arg = 3.5;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void DLOAD_1() {
		super.DLOAD_1();

		double arg = 3.5;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void DLOAD_2() {
		super.DLOAD_2();

		int arg1 = 3;
		double arg2 = 2.7;
		double answer = (double) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer, 0);
	}

	@Test
	@Override
	public void DLOAD_3() {
		super.DLOAD_3();

		long arg1 = 3L;
		double arg2 = 2.7;
		double answer = (double) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer, 0);
	}

	@Test
	@Override
	public void DMUL() {
		super.DMUL();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(6.0, answer, 0);
	}

	@Test
	@Override
	public void DNEG() {
		super.DNEG();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(-3.0, answer, 0);
	}

	@Test
	@Override
	public void DREM() {
		super.DREM();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(1.0, answer, 0);
	}

	@Test
	@Override
	public void DRETURN() {
		super.DRETURN();

		double arg = 5;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void DSTORE() {
		super.DSTORE();

		double arg = 3.7;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void DSTORE_0() {
		super.DSTORE_0();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(1.0, answer, 0);
	}

	@Test
	@Override
	public void DSTORE_1() {
		super.DSTORE_1();

		double arg = 3.7;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void DSTORE_2() {
		super.DSTORE_2();

		double arg = 3.7;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(1.0, answer, 0);
	}

	@Test
	@Override
	public void DSTORE_3() {
		super.DSTORE_3();

		double arg = 3.7;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void DSUB() {
		super.DSUB();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(1.0, answer, 0);
	}

	@Test
	@Override
	public void DUP() {
		super.DUP();

		int arg = 19;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(0, answer, 0);
	}

	@Test
	@Override
	public void DUP_X1() {
		super.DUP_X1();

		int arg = 19;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(-arg, answer, 0);
	}

	@Test
	@Override
	public void DUP_X2() {
		super.DUP_X2();

		int arg = 19;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(-1, answer, 0);
	}

	@Test
	@Override
	public void DUP2() {
		super.DUP2();

		long arg = 19L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(0L, answer, 0);
	}

	@Test
	@Override
	public void DUP2_X1() {
		super.DUP2_X1();

		int arg = 1;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(-1.0, answer, 0);
	}

	@Test
	@Override
	public void DUP2_X2() {
		super.DUP2_X2();

		long arg = 19L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(-arg, answer, 0);
	}

	@Test
	@Override
	public void F2D() {
		super.F2D();

		float arg = 5F;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void F2I() {
		super.F2I();

		float arg = 5.4F;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(5, answer, 0);
	}

	@Test
	@Override
	public void F2L() {
		super.F2L();

		float arg = 5.4F;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(5, answer, 0);
	}

	@Test
	@Override
	public void FADD() {
		super.FADD();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(5F, answer, 0);
	}

	@Test
	@Override
	public void FALOAD() {
		super.FALOAD();

		float[] arg = new float[] { 3.7F };
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg[0], answer, 0);
	}

	@Test
	@Override
	public void FASTORE() {
		super.FASTORE();

		float arg1 = 3.7F;
		float[] arg2 = new float[1];
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg1, arg2[0], 0);
	}

	@Test
	@Override
	public void FCMPG() {
		super.FCMPG();

		float arg1 = 3.7F;
		float arg2 = 2.5F;
		float arg3 = 0.0F / 1.0F / 0.0F;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer4 = (int) this.invoke(METHOD_NAME, arg1, arg3);
		int answer5 = (int) this.invoke(METHOD_NAME, arg3, arg1);

		assertEquals(1, answer1);
		assertEquals(-1, answer2);
		assertEquals(0, answer3);
		assertEquals(1, answer4);
		assertEquals(1, answer5);
	}

	@Test
	@Override
	public void FCMPL() {
		super.FCMPL();

		float arg1 = 3.7F;
		float arg2 = 2.5F;
		float arg3 = 0.0F / 1.0F / 0.0F;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer4 = (int) this.invoke(METHOD_NAME, arg1, arg3);
		int answer5 = (int) this.invoke(METHOD_NAME, arg3, arg1);

		assertEquals(1, answer1);
		assertEquals(-1, answer2);
		assertEquals(0, answer3);
		assertEquals(-1, answer4);
		assertEquals(-1, answer5);
	}

	@Test
	@Override
	public void FCONST_0() {
		super.FCONST_0();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(0.0F, answer, 0);
	}

	@Test
	@Override
	public void FCONST_1() {
		super.FCONST_1();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(1.0F, answer, 0);
	}

	@Test
	@Override
	public void FCONST_2() {
		super.FCONST_2();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(2.0F, answer, 0);
	}

	@Test
	@Override
	public void FDIV() {
		super.FDIV();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(2.5F, answer, 0);
	}

	@Test
	@Override
	public void FLOAD() {
		super.FLOAD();

		float arg = 3.5F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FLOAD_0() {
		super.FLOAD_0();

		float arg = 3.5F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FLOAD_1() {
		super.FLOAD_1();

		float arg = 3.5F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FLOAD_2() {
		super.FLOAD_2();

		int arg1 = 3;
		float arg2 = 2.7F;
		float answer = (float) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer, 0);
	}

	@Test
	@Override
	public void FLOAD_3() {
		super.FLOAD_3();

		long arg1 = 3L;
		float arg2 = 2.7F;
		float answer = (float) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer, 0);
	}

	@Test
	@Override
	public void FMUL() {
		super.FMUL();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(6.0F, answer, 0);
	}

	@Test
	@Override
	public void FNEG() {
		super.FNEG();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(-3.0F, answer, 0);
	}

	@Test
	@Override
	public void FREM() {
		super.FREM();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(1.0F, answer, 0);
	}

	@Test
	@Override
	public void FRETURN() {
		super.FRETURN();

		float arg = 3.5F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FSTORE() {
		super.FSTORE();

		float arg = 3.7F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FSTORE_0() {
		super.FSTORE_0();

		float arg = 3.7F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FSTORE_1() {
		super.FSTORE_1();

		float arg = 3.7F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FSTORE_2() {
		super.FSTORE_2();

		float arg = 3.7F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FSTORE_3() {
		super.FSTORE_3();

		float arg = 3.7F;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer, 0);
	}

	@Test
	@Override
	public void FSUB() {
		super.FSUB();

		float answer = (float) this.invoke(METHOD_NAME);

		assertEquals(1F, answer, 0);
	}

	@Test
	@Override
	public void GETFIELD() {
		super.GETFIELD();

		int value = 15;
		Blah arg = new Blah();
		arg.att = value;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(value, answer);
	}

	@Test
	@Override
	public void GETSTATIC() {
		super.GETSTATIC();

		int value = 15;
		Blah.ATT = value;
		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(value, answer);
	}

	@Test
	@Override
	public void GOTO() {

		super.GOTO();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void GOTO_W() {
		super.GOTO_W();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void I2B() {
		super.I2B();

		int arg = -134;
		byte answer = (byte) this.invoke(METHOD_NAME, arg);

		assertEquals(122, answer);
	}

	@Test
	@Override
	public void I2C() {
		super.I2C();

		int arg = 65600;
		char answer = (char) this.invoke(METHOD_NAME, arg);

		assertEquals('@', answer);
	}

	@Test
	@Override
	public void I2D() {
		super.I2D();

		int arg = 5;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(5.0, answer, 0);
	}

	@Test
	@Override
	public void I2F() {
		super.I2F();

		int arg = 5;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(5.0F, answer, 0);
	}

	@Test
	@Override
	public void I2L() {
		super.I2L();

		int arg = 5;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(5L, answer, 0);
	}

	@Test
	@Override
	public void I2S() {
		super.I2S();

		int arg = -40000;
		short answer = (short) this.invoke(METHOD_NAME, arg);

		assertEquals(25536, answer);
	}

	@Test
	@Override
	public void IADD() {
		super.IADD();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void IALOAD() {
		super.IALOAD();

		int[] arg = new int[] { 3 };
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg[0], answer);
	}

	@Test
	@Override
	public void IAND() {
		super.IAND();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(4, answer);
	}

	@Test
	@Override
	public void IASTORE() {
		super.IASTORE();

		int arg1 = 3;
		int[] arg2 = new int[1];
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg1, arg2[0]);
	}

	@Test
	@Override
	public void ICONST_M1() {
		super.ICONST_M1();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(-1, answer);
	}

	@Test
	@Override
	public void ICONST_0() {
		super.ICONST_0();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(0, answer);
	}

	@Test
	@Override
	public void ICONST_1() {
		super.ICONST_1();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(1, answer);
	}

	@Test
	@Override
	public void ICONST_2() {
		super.ICONST_2();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(2, answer);
	}

	@Test
	@Override
	public void ICONST_3() {
		super.ICONST_3();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(3, answer);
	}

	@Test
	@Override
	public void ICONST_4() {
		super.ICONST_4();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(4, answer);
	}

	@Test
	@Override
	public void ICONST_5() {
		super.ICONST_5();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void IDIV() {
		super.IDIV();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(2, answer);
	}

	@Test
	@Override
	public void IF_ACMPEQ() {
		super.IF_ACMPEQ();

		Object arg1 = new ArrayList<>();
		Object arg2 = new ArrayList<>();
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
	}

	@Test
	@Override
	public void IF_ACMPNE() {
		super.IF_ACMPNE();

		Object arg1 = new ArrayList<>();
		Object arg2 = new ArrayList<>();
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
	}

	@Test
	@Override
	public void IF_ICMPEQ() {
		super.IF_ICMPEQ();

		int arg1 = 5;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
	}

	@Test
	@Override
	public void IF_ICMPNE() {
		super.IF_ICMPNE();

		int arg1 = 5;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
	}

	@Test
	@Override
	public void IF_ICMPLT() {
		super.IF_ICMPLT();

		int arg1 = 5;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
		assertEquals(0, answer3);
	}

	@Test
	@Override
	public void IF_ICMPGE() {
		super.IF_ICMPGE();

		int arg1 = 5;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
		assertEquals(1, answer3);
	}

	@Test
	@Override
	public void IF_ICMPGT() {
		super.IF_ICMPGT();

		int arg1 = 5;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
		assertEquals(0, answer3);
	}

	@Test
	@Override
	public void IF_ICMPLE() {
		super.IF_ICMPLE();

		int arg1 = 5;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
		assertEquals(1, answer3);
	}

	@Test
	@Override
	public void IFEQ() {
		super.IFEQ();

		int arg1 = 0;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
	}

	@Test
	@Override
	public void IFNE() {
		super.IFNE();

		int arg1 = 0;
		int arg2 = 7;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
	}

	@Test
	@Override
	public void IFLT() {
		super.IFLT();

		int arg1 = 0;
		int arg2 = 7;
		int arg3 = -5;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);
		int answer3 = (int) this.invoke(METHOD_NAME, arg3);

		assertEquals(0, answer1);
		assertEquals(0, answer2);
		assertEquals(1, answer3);
	}

	@Test
	@Override
	public void IFGE() {
		super.IFGE();

		int arg1 = 0;
		int arg2 = 7;
		int arg3 = -5;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);
		int answer3 = (int) this.invoke(METHOD_NAME, arg3);

		assertEquals(1, answer1);
		assertEquals(1, answer2);
		assertEquals(0, answer3);
	}

	@Test
	@Override
	public void IFGT() {
		super.IFGT();

		int arg1 = 0;
		int arg2 = 7;
		int arg3 = -5;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);
		int answer3 = (int) this.invoke(METHOD_NAME, arg3);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
		assertEquals(0, answer3);
	}

	@Test
	@Override
	public void IFLE() {
		super.IFLE();

		int arg1 = 0;
		int arg2 = 7;
		int arg3 = -5;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);
		int answer3 = (int) this.invoke(METHOD_NAME, arg3);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
		assertEquals(1, answer3);
	}

	@Test
	@Override
	public void IFNONNULL() {
		super.IFNONNULL();

		Object arg1 = null;
		Object arg2 = new ArrayList<>();
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
	}

	@Test
	@Override
	public void IFNULL() {
		super.IFNULL();

		Object arg1 = null;
		Object arg2 = new ArrayList<>();
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
	}

	@Test
	@Override
	public void IINC() {
		super.IINC();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void ILOAD() {
		super.ILOAD();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ILOAD_0() {
		super.ILOAD_0();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ILOAD_1() {
		super.ILOAD_1();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ILOAD_2() {
		super.ILOAD_2();

		float arg1 = 3.5F;
		int arg2 = 2;
		int answer = (int) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer);
	}

	@Test
	@Override
	public void ILOAD_3() {
		super.ILOAD_3();

		long arg1 = 3L;
		int arg2 = 2;
		int answer = (int) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer);
	}

	@Test
	@Override
	public void IMUL() {
		super.IMUL();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(6, answer);
	}

	@Test
	@Override
	public void INEG() {
		super.INEG();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(-3, answer);
	}

	@Test
	@Override
	public void INSTANCEOF() {
		super.INSTANCEOF();

		Object arg1_1 = new Blah();
		Object arg1_2 = new int[0];
		int answer1 = (int) this.invoke(METHOD_NAME, arg1_1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg1_2);

		assertEquals(1, answer1);
		assertEquals(0, answer2);
	}

	@Test
	@Override
	public void INVOKEDYNAMIC() {
		super.INVOKEDYNAMIC();

		Integer arg1 = 5;
		Integer arg2 = 4;
		int answer = (int) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(9, answer);
	}

	@Test
	@Override
	public void INVOKEINTERFACE() {
		super.INVOKEINTERFACE();

		Collection<?> arg1 = new HashSet<>();
		Object arg2 = new Blah();
		this.invoke(METHOD_NAME, arg1, arg2);

		assertTrue(arg1.contains(arg2));
	}

	@Test
	@Override
	public void INVOKESPECIAL() {
		super.INVOKESPECIAL();

		Blah answer = (Blah) this.invoke(METHOD_NAME);

		assertEquals(Blah.class, answer.getClass());
	}

	@Test
	@Override
	public void INVOKESTATIC() {
		super.INVOKESTATIC();

		String answer = (String) this.invoke(METHOD_NAME);

		assertEquals("0", answer);
	}

	@Test
	@Override
	public void INVOKEVIRTUAL() {
		super.INVOKEVIRTUAL();

		Blah arg = new Blah();
		String answer = (String) this.invoke(METHOD_NAME, arg);

		assertEquals(arg.bleh(), answer);
	}

	@Test
	@Override
	public void IOR() {
		super.IOR();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void IREM() {
		super.IREM();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(1, answer);
	}

	@Test
	@Override
	public void IRETURN() {
		super.IRETURN();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ISHL() {
		super.ISHL();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(-8, answer);
	}

	@Test
	@Override
	public void ISHR() {
		super.ISHR();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(-1, answer);
	}

	@Test
	@Override
	public void ISTORE() {
		super.ISTORE();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ISTORE_0() {
		super.ISTORE_0();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ISTORE_1() {
		super.ISTORE_1();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ISTORE_2() {
		super.ISTORE_2();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ISTORE_3() {
		super.ISTORE_3();

		int arg = 3;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void ISUB() {
		super.ISUB();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(1, answer);
	}

	@Test
	@Override
	public void IUSHR() {
		super.IUSHR();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(536870911, answer);
	}

	@Test
	@Override
	public void IXOR() {
		super.IXOR();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(1, answer);
	}

	@Test
	@Override
	public void JSR() {
		super.JSR();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(2, answer);
	}

	@Test
	@Override
	public void JSR_W() {
		super.JSR_W();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(2, answer);
	}

	@Test
	@Override
	public void L2D() {
		super.L2D();

		long arg = 15L;
		double answer = (double) this.invoke(METHOD_NAME, arg);

		assertEquals(15.0F, answer, 0);
	}

	@Test
	@Override
	public void L2F() {
		super.L2F();

		long arg = 15L;
		float answer = (float) this.invoke(METHOD_NAME, arg);

		assertEquals(15.0F, answer, 0);
	}

	@Test
	@Override
	public void L2I() {
		super.L2I();

		long arg = -4294967297L;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(-1, answer);
	}

	@Test
	@Override
	public void LADD() {
		super.LADD();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(5L, answer);
	}

	@Test
	@Override
	public void LALOAD() {
		super.LALOAD();

		long[] arg = new long[] { 3L };
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg[0], answer);
	}

	@Test
	@Override
	public void LAND() {
		super.LAND();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(4L, answer);
	}

	@Test
	@Override
	public void LASTORE() {
		super.LASTORE();

		long arg1 = 3L;
		long[] arg2 = new long[1];
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg1, arg2[0]);
	}

	@Test
	@Override
	public void LCMP() {
		super.LCMP();

		long arg1 = 3;
		long arg2 = 2;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1, arg2);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2, arg1);
		int answer3 = (int) this.invoke(METHOD_NAME, arg1, arg1);

		assertEquals(1, answer1);
		assertEquals(-1, answer2);
		assertEquals(0, answer3);
	}

	@Test
	@Override
	public void LCONST_0() {
		super.LCONST_0();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(0L, answer);
	}

	@Test
	@Override
	public void LCONST_1() {
		super.LCONST_1();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(1L, answer);
	}

	@Test
	@Override
	public void LDC() {
		super.LDC();

		char answer = (char) this.invoke(METHOD_NAME);

		assertEquals('o', answer);
	}

	@Test
	@Override
	public void LDC_W() {
		super.LDC_W();

		char answer = (char) this.invoke(METHOD_NAME);

		assertEquals('o', answer);
	}

	@Test
	@Override
	public void LDC2_W() {
		super.LDC2_W();

		double answer = (double) this.invoke(METHOD_NAME);

		assertEquals(7.5, answer, 0);
	}

	@Test
	@Override
	public void LDIV() {
		super.LDIV();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(2L, answer);
	}

	@Test
	@Override
	public void LLOAD() {
		super.LLOAD();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void LLOAD_0() {
		super.LLOAD_0();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void LLOAD_1() {
		super.LLOAD_1();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void LLOAD_2() {
		super.LLOAD_2();

		int arg1 = 3;
		long arg2 = 2L;
		long answer = (long) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer);
	}

	@Test
	@Override
	public void LLOAD_3() {
		super.LLOAD_3();

		double arg1 = 3.5;
		long arg2 = 2L;
		long answer = (long) this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, answer);
	}

	@Test
	@Override
	public void LMUL() {
		super.LMUL();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(6L, answer);
	}

	@Test
	@Override
	public void LNEG() {
		super.LNEG();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(-3L, answer);
	}

	@Test
	@Override
	public void LOOKUPSWITCH() {
		super.LOOKUPSWITCH();

		int arg1 = 1;
		int arg2 = 2;
		int arg3 = 3;
		int arg4 = 4;
		int arg5 = 5;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);
		int answer3 = (int) this.invoke(METHOD_NAME, arg3);
		int answer4 = (int) this.invoke(METHOD_NAME, arg4);
		int answer5 = (int) this.invoke(METHOD_NAME, arg5);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
		assertEquals(3, answer3);
		assertEquals(2, answer4);
		assertEquals(0, answer5);
	}

	@Test
	@Override
	public void LOR() {
		super.LOR();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(5L, answer);
	}

	@Test
	@Override
	public void LREM() {
		super.LREM();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(1L, answer);
	}

	@Test
	@Override
	public void LRETURN() {
		super.LRETURN();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void LSHL() {
		super.LSHL();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(-8L, answer);
	}

	@Test
	@Override
	public void LSHR() {
		super.LSHR();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(-1L, answer);
	}

	@Test
	@Override
	public void LSTORE() {
		super.LSTORE();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void LSTORE_0() {
		super.LSTORE_0();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(1L, answer);
	}

	@Test
	@Override
	public void LSTORE_1() {
		super.LSTORE_1();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void LSTORE_2() {
		super.LSTORE_2();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(1L, answer);
	}

	@Test
	@Override
	public void LSTORE_3() {
		super.LSTORE_3();

		long arg = 3L;
		long answer = (long) this.invoke(METHOD_NAME, arg);

		assertEquals(arg, answer);
	}

	@Test
	@Override
	public void LSUB() {
		super.LSUB();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(1L, answer);
	}

	@Test
	@Override
	public void LUSHR() {
		super.LUSHR();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(2305843009213693951L, answer);
	}

	@Test
	@Override
	public void LXOR() {
		super.LXOR();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(1L, answer);
	}

	@Test
	@Override
	public void MONITORENTER() {
		super.MONITORENTER();

		Object[] arg = new Object[] { new ArrayList<>() };
		this.invoke(METHOD_NAME, (Object) arg);

		assertEquals(null, arg[0]);
	}

	@Test
	@Override
	public void MONITOREXIT() {
		super.MONITOREXIT();

		Object[] arg = new Object[] { new ArrayList<>() };
		this.invoke(METHOD_NAME, (Object) arg);

		assertEquals(null, arg[0]);
	}

	@Test
	@Override
	public void MULTIANEWARRAY() {
		super.MULTIANEWARRAY();

		Object[][] answer = (Object[][]) this.invoke(METHOD_NAME);

		assertEquals(3, answer.length);
		assertNull(answer[0]);
	}

	@Test
	@Override
	public void NEW() {
		super.NEW();

		Blah answer = (Blah) this.invoke(METHOD_NAME);

		assertEquals(Blah.class, answer.getClass());
	}

	@Test
	@Override
	public void NEWARRAY() {
		super.NEWARRAY();

		int[] answer = (int[]) this.invoke(METHOD_NAME);

		assertEquals(2, answer.length);
	}

	@Test
	@Override
	public void NOP() {
		super.NOP();

		this.invoke(METHOD_NAME);
	}

	@Test
	@Override
	public void POP() {
		super.POP();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void POP2() {
		super.POP2();

		long answer = (long) this.invoke(METHOD_NAME);

		assertEquals(5L, answer);
	}

	@Test
	@Override
	public void PUTFIELD() {
		super.PUTFIELD();

		Blah arg1 = new Blah();
		int arg2 = 15;
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg2, arg1.att);
	}

	@Test
	@Override
	public void PUTSTATIC() {
		super.PUTSTATIC();

		Blah.ATT = 10;
		int arg = 15;
		this.invoke(METHOD_NAME, arg);

		assertEquals(arg, Blah.ATT);
	}

	@Test
	@Override
	public void RET() {
		super.RET();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(2, answer);
	}

	@Test
	@Override
	public void RETURN() {
		super.RETURN();

		this.invoke(METHOD_NAME);
	}

	@Test
	@Override
	public void SALOAD() {
		super.SALOAD();

		short[] arg = new short[] { 3 };
		short answer = (short) this.invoke(METHOD_NAME, arg);

		assertEquals(arg[0], answer);
	}

	@Test
	@Override
	public void SASTORE() {
		super.SASTORE();

		short arg1 = 3;
		short[] arg2 = new short[1];
		this.invoke(METHOD_NAME, arg1, arg2);

		assertEquals(arg1, arg2[0]);
	}

	@Test
	@Override
	public void SIPUSH() {
		super.SIPUSH();

		byte answer = (byte) this.invoke(METHOD_NAME);

		assertEquals(5, answer);
	}

	@Test
	@Override
	public void SWAP() {
		super.SWAP();

		int answer = (int) this.invoke(METHOD_NAME);

		assertEquals(-1, answer);
	}

	@Test
	@Override
	public void TABLESWITCH() {
		super.TABLESWITCH();

		int arg1 = 1;
		int arg2 = 2;
		int arg3 = 3;
		int arg4 = 4;
		int arg5 = 5;
		int answer1 = (int) this.invoke(METHOD_NAME, arg1);
		int answer2 = (int) this.invoke(METHOD_NAME, arg2);
		int answer3 = (int) this.invoke(METHOD_NAME, arg3);
		int answer4 = (int) this.invoke(METHOD_NAME, arg4);
		int answer5 = (int) this.invoke(METHOD_NAME, arg5);

		assertEquals(0, answer1);
		assertEquals(1, answer2);
		assertEquals(3, answer3);
		assertEquals(2, answer4);
		assertEquals(0, answer5);
	}

	@Test
	@Override
	public void WIDE() {
		super.WIDE();

		int arg = 7;
		int answer = (int) this.invoke(METHOD_NAME, arg);

		assertEquals(1007, answer);
	}

	// ****************************************************************
	// ** AUXILIARS
	// ****************************************************************

	protected Object instantiate() {
		try {
			return new BytecodeClassLoader().importClass(this.getClassBuilder().build()).newInstance();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object invoke(String methodName, Object... args) {
		Object instance = this.instantiate();

		for(Method method : instance.getClass().getMethods()) {
			if(method.getName().equals(methodName)) {
				try {
					return method.invoke(instance, args);
				}
				catch(Exception e) {
					throw (RuntimeException) e.getCause();
				}
			}
		}

		throw new RuntimeException("Method not found: " + methodName);
	}
}