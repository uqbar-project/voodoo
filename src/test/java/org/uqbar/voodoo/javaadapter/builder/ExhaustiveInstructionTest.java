package org.uqbar.voodoo.javaadapter.builder;

import static org.uqbar.voodoo.javaadapter.model.ClassVersion.JAVA_6;

import java.util.Collection;

import org.junit.Before;
import org.uqbar.voodoo.javaadapter.builder.auxiliars.Blah;
import org.uqbar.voodoo.javaadapter.builder.auxiliars.CustomBootstrap;
import org.uqbar.voodoo.javaadapter.builder.auxiliars.Link;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.SimpleInstructionCall;

public abstract class ExhaustiveInstructionTest {

	protected static final String CLASS_NAME = "TestClass";
	protected static final String METHOD_NAME = "test";

	private ClassBuilder classBuilder;

	// ****************************************************************
	// ** SETUP & TEARDOWN
	// ****************************************************************

	@Before
	public void setup() {
		this.setClassBuilder(new ClassBuilder(CLASS_NAME));
	}

	// ****************************************************************
	// ** TEST CASES
	// ****************************************************************

	protected void AALOAD() {

		this.getClassBuilder()
				.method(METHOD_NAME, Object.class, Object[].class).code()
				// Returns the first object of the given array.
				.ALOAD_1().ICONST_0().AALOAD().ARETURN();
	}

	protected void AASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, Object.class, Object[].class)
				.code()
				// Stores the given object as the first element of the given
				// array.
				.ALOAD_2().ICONST_0().ALOAD_1().AASTORE().RETURN();
	}

	protected void ACONST_NULL() {

		this.getClassBuilder().method(METHOD_NAME, Object.class).code()
		// Returns null.
				.ACONST_NULL().ARETURN();
	}

	protected void ALOAD() {

		this.getClassBuilder().method(METHOD_NAME, Object.class, Object.class)
				.code()
				// Returns the given object.
				.ALOAD(1).ARETURN();
	}

	protected void ALOAD_0() {

		this.getClassBuilder()
				.method(METHOD_NAME, true, Object.class, Object.class).code()
				// Returns the given object.
				.ALOAD_0().ARETURN();
	}

	protected void ALOAD_1() {

		this.getClassBuilder().method(METHOD_NAME, Object.class, Object.class)
				.code()
				// Returns the given object.
				.ALOAD_1().ARETURN();
	}

	protected void ALOAD_2() {

		this.getClassBuilder()
				.method(METHOD_NAME, Object.class, Object.class, Object.class)
				.code()
				// Returns the second given object.
				.ALOAD_2().ARETURN();
	}

	protected void ALOAD_3() {

		this.getClassBuilder()
				.method(METHOD_NAME, Object.class, Object.class, Object.class,
						Object.class).code()
				// Returns the third given object.
				.ALOAD_3().ARETURN();
	}

	protected void ANEWARRAY() {

		this.getClassBuilder().method(METHOD_NAME, Object[].class).code()
		// Returns an empty Object[] of length 5.
				.ICONST_5().ANEWARRAY(Object.class).ARETURN();
	}

	protected void ARETURN() {

		this.getClassBuilder().method(METHOD_NAME, Object.class).code()
		// Returns null.
				.ACONST_NULL().ARETURN();
	}

	protected void ARRAYLENGTH() {

		this.getClassBuilder().method(METHOD_NAME, int.class, Object[].class)
				.code()
				// Returns the length of the given array.
				.ALOAD_1().ARRAYLENGTH().IRETURN();
	}

	protected void ASTORE() {

		this.getClassBuilder().method(METHOD_NAME, Object.class, Object.class)
				.code()
				// Returns the given object after storing it in the local 2.
				.ALOAD_1().ASTORE(2).ALOAD(2).ARETURN();
	}

	protected void ASTORE_0() {

		this.getClassBuilder().method(METHOD_NAME, Object.class, Object.class)
				.code()
				// Returns the given object after storing it in the local 0.
				.ALOAD_1().ASTORE_0().ALOAD_0().ARETURN();
	}

	protected void ASTORE_1() {

		this.getClassBuilder()
				.method(METHOD_NAME, true, Object.class, Object.class).code()
				// Returns the given object after storing it in the local 1.
				.ALOAD_0().ASTORE_1().ALOAD_1().ARETURN();
	}

	protected void ASTORE_2() {

		this.getClassBuilder().method(METHOD_NAME, Object.class, Object.class)
				.code()
				// Returns the given object after storing it in the local 2.
				.ALOAD_1().ASTORE_2().ALOAD_2().ARETURN();
	}

	protected void ASTORE_3() {

		this.getClassBuilder().method(METHOD_NAME, Object.class, Object.class)
				.code()
				// Returns the given object after storing it in the local 3.
				.ALOAD_1().ASTORE_2().ALOAD_1().ASTORE_3().ALOAD_3().ARETURN();
	}

	protected void ATHROW() {

		this.getClassBuilder().method(METHOD_NAME, void.class)
				.code()
				// Throws ArithmeticException.
				.NEW(ArithmeticException.class).DUP()
				.INVOKESPECIAL(ArithmeticException.class, "<init>", void.class)
				.ATHROW();
	}

	protected void BALOAD() {

		this.getClassBuilder()
				.method(METHOD_NAME, boolean.class, boolean[].class).code()
				// Returns the first boolean of the given array.
				.ALOAD_1().ICONST_0().BALOAD().IRETURN();
	}

	protected void BASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, boolean.class, boolean[].class)
				.code()
				// Stores the given boolean as the first element of the given
				// array.
				.ALOAD_2().ICONST_0().ILOAD_1().BASTORE().RETURN();
	}

	protected void BIPUSH() {

		this.getClassBuilder().method(METHOD_NAME, byte.class).code()
		// Returns 5.
				.BIPUSH(5).IRETURN();
	}

	protected void CALOAD() {

		this.getClassBuilder().method(METHOD_NAME, char.class, char[].class)
				.code()
				// Returns the first char of the given array.
				.ALOAD_1().ICONST_0().CALOAD().IRETURN();
	}

	protected void CASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, char.class, char[].class)
				.code()
				// Stores the given char as the first element of the given
				// array.
				.ALOAD_2().ICONST_0().ILOAD_1().CASTORE().RETURN();
	}

	protected void CHECKCAST() {

		this.getClassBuilder().method(METHOD_NAME, Blah.class, Object.class)
				.code()
				// Returns the given object casted to another type.
				.ALOAD_1().CHECKCAST(Blah.class).ARETURN();
	}

	protected void D2F() {

		this.getClassBuilder().method(METHOD_NAME, float.class, double.class)
				.code()
				// Returns the given argument converted to another type.
				.DLOAD_1().D2F().FRETURN();
	}

	protected void D2I() {

		this.getClassBuilder().method(METHOD_NAME, int.class, double.class)
				.code()
				// Returns the given argument converted to another type.
				.DLOAD_1().D2I().IRETURN();
	}

	protected void D2L() {

		this.getClassBuilder().method(METHOD_NAME, long.class, double.class)
				.code()
				// Returns the given argument converted to another type.
				.DLOAD_1().D2L().LRETURN();
	}

	protected void DADD() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 5.0.
				.LDC2_W(3.0).LDC2_W(2.0).DADD().DRETURN();
	}

	protected void DALOAD() {

		this.getClassBuilder()
				.method(METHOD_NAME, double.class, double[].class).code()
				// Returns the first double of the given array.
				.ALOAD_1().ICONST_0().DALOAD().DRETURN();
	}

	protected void DASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, double.class, double[].class)
				.code()
				// Stores the given double as the first element of the given
				// array.
				.ALOAD_3().ICONST_0().DLOAD_1().DASTORE().RETURN();
	}

	protected void DCMPG() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, double.class, double.class)
				.code()
				// Compares the received doubles. Answers 1 if either one is
				// NaN.
				.DLOAD_1().DLOAD_3().DCMPG().IRETURN();
	}

	protected void DCMPL() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, double.class, double.class)
				.code()
				// Compares the received doubles. Answers -1 if either one is
				// NaN.
				.DLOAD_1().DLOAD_3().DCMPL().IRETURN();
	}

	protected void DCONST_0() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 0.0.
				.DCONST_0().DRETURN();
	}

	protected void DCONST_1() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 1.0.
				.DCONST_1().DRETURN();
	}

	protected void DDIV() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 2.5.
				.LDC2_W(5.0).LDC2_W(2.0).DDIV().DRETURN();
	}

	protected void DLOAD() {

		this.getClassBuilder().method(METHOD_NAME, double.class, double.class)
				.code()
				// Returns the given double.
				.DLOAD(1).DRETURN();
	}

	protected void DLOAD_0() {

		this.getClassBuilder()
				.method(METHOD_NAME, true, double.class, double.class).code()
				// Returns the given double.
				.DLOAD_0().DRETURN();
	}

	protected void DLOAD_1() {

		this.getClassBuilder().method(METHOD_NAME, double.class, double.class)
				.code()
				// Returns the given double.
				.DLOAD_1().DRETURN();
	}

	protected void DLOAD_2() {

		this.getClassBuilder()
				.method(METHOD_NAME, double.class, int.class, double.class)
				.code()
				// Returns the given double.
				.DLOAD_2().DRETURN();
	}

	protected void DLOAD_3() {

		this.getClassBuilder()
				.method(METHOD_NAME, double.class, long.class, double.class)
				.code()
				// Returns the given double.
				.DLOAD_3().DRETURN();
	}

	protected void DMUL() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 6.0.
				.LDC2_W(3.0).LDC2_W(2.0).DMUL().DRETURN();
	}

	protected void DNEG() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns -3.0.
				.LDC2_W(3.0).DNEG().DRETURN();
	}

	protected void DREM() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 1.0.
				.LDC2_W(5.0).LDC2_W(2.0).DREM().DRETURN();
	}

	protected void DRETURN() {

		this.getClassBuilder().method(METHOD_NAME, double.class, double.class)
				.code()
				// Returns the given double.
				.DLOAD_1().DRETURN();
	}

	protected void DSTORE() {

		this.getClassBuilder().method(METHOD_NAME, double.class, double.class)
				.code()
				// Returns the given double after storing it in the local 3.
				.DLOAD_1().DSTORE(3).DLOAD(3).DRETURN();
	}

	protected void DSTORE_0() {

		this.getClassBuilder().method(METHOD_NAME, true, double.class).code()
		// Returns 1.0 after storing it in the local 0.
				.DCONST_1().DSTORE_0().DLOAD_0().DRETURN();
	}

	protected void DSTORE_1() {

		this.getClassBuilder().method(METHOD_NAME, double.class, double.class)
				.code()
				// Returns the given double after storing it in the local 1.
				.DLOAD_1().DSTORE_1().DLOAD_1().DRETURN();
	}

	protected void DSTORE_2() {

		this.getClassBuilder()
				.method(METHOD_NAME, true, double.class, double.class).code()
				// Returns 1.0 after storing it in the local 2.
				.DCONST_1().DSTORE_2().DLOAD_2().DRETURN();
	}

	protected void DSTORE_3() {

		this.getClassBuilder().method(METHOD_NAME, double.class, double.class)
				.code()
				// Returns the given double after storing it in the local 3.
				.DLOAD_1().DSTORE_3().DLOAD_3().DRETURN();
	}

	protected void DSUB() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 1.0.
				.LDC2_W(3.0).LDC2_W(2.0).DSUB().DRETURN();
	}

	protected void DUP() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Subtracts the received int from itself and returns 0.
				.ILOAD_1().DUP().ISUB().IRETURN();
	}

	protected void DUP_X1() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Subtracts 0 from the received int, and then subtracts it from the
		// result. Returns the received int negated.
				.ILOAD_1().ICONST_0().DUP_X1().ISUB().ISUB().IRETURN();
	}

	protected void DUP_X2() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class)
				.code()
				// Subtracts from the receiver int the result of 1 minus
				// subtracting it from 0. Returns -1.
				.ICONST_1().ICONST_0().ILOAD_1().DUP_X2().ISUB().ISUB().ISUB()
				.IRETURN();
	}

	protected void DUP2() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Subtracts the received long from itself and returns 0L.
				.LLOAD_1().DUP2().LSUB().LRETURN();
	}

	protected void DUP2_X1() {

		this.getClassBuilder().method(METHOD_NAME, long.class, int.class)
				.code()
				// Returns the received int converted to long and negated.
				.ILOAD_1().LCONST_0().DUP2_X1().LSTORE_2().I2L().LLOAD_2()
				.LSUB().LSUB().LRETURN();
	}

	protected void DUP2_X2() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Subtracts 0L from the received long, and then subtracts it
				// from the result. Returns the received long negated.
				.LLOAD_1().LCONST_0().DUP2_X2().LSUB().LSUB().LRETURN();
	}

	protected void F2D() {

		this.getClassBuilder().method(METHOD_NAME, double.class, float.class)
				.code()
				// Returns the given argument converted to another type.
				.FLOAD_1().F2D().DRETURN();
	}

	protected void F2I() {

		this.getClassBuilder().method(METHOD_NAME, int.class, float.class)
				.code()
				// Returns the given argument converted to another type.
				.FLOAD_1().F2I().IRETURN();
	}

	protected void F2L() {

		this.getClassBuilder().method(METHOD_NAME, long.class, float.class)
				.code()
				// Returns the given argument converted to another type.
				.FLOAD_1().F2L().LRETURN();
	}

	protected void FADD() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 5F.
				.LDC_W(3.0F).LDC_W(2.0F).FADD().FRETURN();
	}

	protected void FALOAD() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float[].class)
				.code()
				// Returns the first float of the given array.
				.ALOAD_1().ICONST_0().FALOAD().FRETURN();
	}

	protected void FASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, float.class, float[].class)
				.code()
				// Stores the given float as the first element of the given
				// array.
				.ALOAD_2().ICONST_0().FLOAD_1().FASTORE().RETURN();
	}

	protected void FCMPG() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, float.class, float.class)
				.code()
				// Compares the received floats. Answers 1 if either one is NaN.
				.FLOAD_1().FLOAD_2().FCMPG().IRETURN();
	}

	protected void FCMPL() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, float.class, float.class)
				.code()
				// Compares the received floats. Answers 1 if either one is NaN.
				.FLOAD_1().FLOAD_2().FCMPL().IRETURN();
	}

	protected void FCONST_0() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 0.0F.
				.FCONST_0().FRETURN();
	}

	protected void FCONST_1() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 1.0F.
				.FCONST_1().FRETURN();
	}

	protected void FCONST_2() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 2.0F.
				.FCONST_2().FRETURN();
	}

	protected void FDIV() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 2.5F.
				.LDC_W(5.0F).LDC_W(2.0F).FDIV().FRETURN();
	}

	protected void FLOAD() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float.
				.FLOAD(1).FRETURN();
	}

	protected void FLOAD_0() {

		this.getClassBuilder()
				.method(METHOD_NAME, true, float.class, float.class).code()
				// Returns the given float.
				.FLOAD_0().FRETURN();
	}

	protected void FLOAD_1() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float.
				.FLOAD_1().FRETURN();
	}

	protected void FLOAD_2() {

		this.getClassBuilder()
				.method(METHOD_NAME, float.class, int.class, float.class)
				.code()
				// Returns the given float.
				.FLOAD_2().FRETURN();
	}

	protected void FLOAD_3() {

		this.getClassBuilder()
				.method(METHOD_NAME, float.class, long.class, float.class)
				.code()
				// Returns the given float.
				.FLOAD_3().FRETURN();
	}

	protected void FMUL() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 6.0F.
				.LDC_W(3.0F).LDC_W(2.0F).FMUL().FRETURN();
	}

	protected void FNEG() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns -3.0F.
				.LDC_W(3.0F).FNEG().FRETURN();
	}

	protected void FREM() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 1.0F.
				.LDC_W(5.0F).LDC_W(2.0F).FREM().FRETURN();
	}

	protected void FRETURN() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float.
				.FLOAD_1().FRETURN();
	}

	protected void FSTORE() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float after storing it in the local 2.
				.FLOAD_1().FSTORE(2).FLOAD(2).FRETURN();
	}

	protected void FSTORE_0() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float after storing it in the local 0.
				.FLOAD_1().FSTORE_0().FLOAD_0().FRETURN();
	}

	protected void FSTORE_1() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float after storing it in the local 1.
				.FLOAD_1().FSTORE_1().FLOAD_1().FRETURN();
	}

	protected void FSTORE_2() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float after storing it in the local 2.
				.FLOAD_1().FSTORE_2().FLOAD_2().FRETURN();
	}

	protected void FSTORE_3() {

		this.getClassBuilder().method(METHOD_NAME, float.class, float.class)
				.code()
				// Returns the given float after storing it in the local 3.
				.FLOAD_1().FSTORE_2().FLOAD_1().FSTORE_3().FLOAD_3().FRETURN();
	}

	protected void FSUB() {

		this.getClassBuilder().method(METHOD_NAME, float.class).code()
		// Returns 1F.
				.LDC_W(3.0F).LDC_W(2.0F).FSUB().FRETURN();
	}

	protected void GETFIELD() {

		this.getClassBuilder().method(METHOD_NAME, int.class, Blah.class)
				.code()
				// Returns the target object field
				.ALOAD_1().GETFIELD(Blah.class, "att", int.class).IRETURN();
	}

	protected void GETSTATIC() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns the ATT static field
				.GETSTATIC(Blah.class, "ATT", int.class).IRETURN();

		int value = 15;
		Blah.ATT = value;
	}

	protected void GOTO() {

		this.getClassBuilder().method(METHOD_NAME, int.class)
				.code()
				// Returns 5.
				.GOTO("return5").label("return0").ICONST_0().IRETURN()
				.label("return5").ICONST_0().ICONST_1().IF_ICMPEQ("return0")
				.ICONST_5().IRETURN();
	}

	protected void GOTO_W() {

		this.getClassBuilder().method(METHOD_NAME, int.class)
				.code()
				// Returns 5.
				.GOTO_W("return5").label("return0").ICONST_0().IRETURN()
				.label("return5").ICONST_0().ICONST_1().IF_ICMPEQ("return0")
				.ICONST_5().IRETURN();
	}

	protected void I2B() {

		this.getClassBuilder().method(METHOD_NAME, byte.class, int.class)
				.code()
				// Returns the given argument converted to another type.
				.ILOAD_1().I2B().IRETURN();
	}

	protected void I2C() {

		this.getClassBuilder().method(METHOD_NAME, char.class, int.class)
				.code()
				// Returns the given argument converted to another type.
				.ILOAD_1().I2C().IRETURN();
	}

	protected void I2D() {

		this.getClassBuilder().method(METHOD_NAME, double.class, int.class)
				.code()
				// Returns the given argument converted to another type.
				.ILOAD_1().I2D().DRETURN();
	}

	protected void I2F() {

		this.getClassBuilder().method(METHOD_NAME, float.class, int.class)
				.code()
				// Returns the given argument converted to another type.
				.ILOAD_1().I2F().FRETURN();
	}

	protected void I2L() {

		this.getClassBuilder().method(METHOD_NAME, long.class, int.class)
				.code()
				// Returns the given argument converted to another type.
				.ILOAD_1().I2L().LRETURN();
	}

	protected void I2S() {

		this.getClassBuilder().method(METHOD_NAME, short.class, int.class)
				.code()
				// Returns the given argument converted to another type.
				.ILOAD_1().I2S().IRETURN();
	}

	protected void IADD() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 5.
				.ICONST_3().ICONST_2().IADD().IRETURN();
	}

	protected void IALOAD() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int[].class)
				.code()
				// Returns the first int of the given array.
				.ALOAD_1().ICONST_0().IALOAD().IRETURN();
	}

	protected void IAND() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 4.
				.ICONST_5().ICONST_4().IAND().IRETURN();
	}

	protected void IASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, int.class, int[].class).code()
				// Stores the given int as the first element of the given array.
				.ALOAD_2().ICONST_0().ILOAD_1().IASTORE().RETURN();
	}

	protected void ICONST_M1() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns -1.
				.ICONST_M1().IRETURN();
	}

	protected void ICONST_0() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 0.
				.ICONST_0().IRETURN();
	}

	protected void ICONST_1() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 1.
				.ICONST_1().IRETURN();
	}

	protected void ICONST_2() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 2.
				.ICONST_2().IRETURN();
	}

	protected void ICONST_3() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 3.
				.ICONST_3().IRETURN();
	}

	protected void ICONST_4() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 4.
				.ICONST_4().IRETURN();
	}

	protected void ICONST_5() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 5.
				.ICONST_5().IRETURN();
	}

	protected void IDIV() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 2.
				.ICONST_5().ICONST_2().IDIV().IRETURN();
	}

	protected void IF_ACMPEQ() {

		CodeBuilder code = this.getClassBuilder()
				.method(METHOD_NAME, int.class, Object.class, Object.class)
				.code();

		code
		// Returns 1 if arguments are equal and 0 if they are not.
		.ALOAD_1().ALOAD_2().IF_ACMPEQ("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IF_ACMPNE() {

		CodeBuilder code = this.getClassBuilder()
				.method(METHOD_NAME, int.class, Object.class, Object.class)
				.code();

		code
		// Returns 0 if arguments are equal and 1 if they are not.
		.ALOAD_1().ALOAD_2().IF_ACMPNE("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IF_ICMPEQ() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class, int.class)
				.code()
				// Returns 1 if arguments are equal and 0 if they are not.
				.ILOAD_1().ILOAD_2().IF_ICMPEQ("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().ISTORE_3().ILOAD_3().ICONST_1()
				.IRETURN();
	}

	protected void IF_ICMPNE() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class, int.class).code()
				// Returns 0 if arguments are equal and 1 if they are not.
				.ILOAD_1().ILOAD_2().IF_ICMPNE("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IF_ICMPLT() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class, int.class)
				.code()
				// Returns 1 if the first argument is lesser than the second
				// argument and 0 if it is not.
				.ILOAD_1().ILOAD_2().IF_ICMPLT("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IF_ICMPGE() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class, int.class)
				.code()
				// Returns 1 if the first argument is great or equal to the
				// second argument and 0 if it is not.
				.ILOAD_1().ILOAD_2().IF_ICMPGE("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IF_ICMPGT() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class, int.class)
				.code()
				// Returns 1 if the first argument is greater than the second
				// argument and 0 if it is not.
				.ILOAD_1().ILOAD_2().IF_ICMPGT("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IF_ICMPLE() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class, int.class)
				.code()
				// Returns 1 if the first argument is less or equal to the
				// second argument and 0 if it is not.
				.ILOAD_1().ILOAD_2().IF_ICMPLE("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IFEQ() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
				// Returns 1 if the argument is equal to 0 and 0 if not.
				.ILOAD_1().IFEQ("ifTrue").ICONST_0().IRETURN().label("ifTrue")
				.ICONST_1().IRETURN();
	}

	protected void IFNE() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
				// Returns 0 if the argument is equal to 0 and 1 if not.
				.ILOAD_1().IFNE("ifTrue").ICONST_0().IRETURN().label("ifTrue")
				.ICONST_1().IRETURN();
	}

	protected void IFLT() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
				// Returns 1 if the argument is less than to 0 and 0 if not.
				.ILOAD_1().IFLT("ifTrue").ICONST_0().IRETURN().label("ifTrue")
				.ICONST_1().IRETURN();
	}

	protected void IFGE() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class)
				.code()
				// Returns 1 if the argument is great or equal to 0 and 0 if
				// not.
				.ILOAD_1().IFGE("ifTrue").ICONST_0().IRETURN().label("ifTrue")
				.ICONST_1().IRETURN();
	}

	protected void IFGT() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
				// Returns 1 if the argument is greater than 0 and 0 if not.
				.ILOAD_1().IFGT("ifTrue").ICONST_0().IRETURN().label("ifTrue")
				.ICONST_1().IRETURN();
	}

	protected void IFLE() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
				// Returns 1 if the argument is less or equal to 0 and 0 if not.
				.ILOAD_1().IFLE("ifTrue").ICONST_0().IRETURN().label("ifTrue")
				.ICONST_1().IRETURN();
	}

	protected void IFNONNULL() {

		this.getClassBuilder().method(METHOD_NAME, int.class, Object.class)
				.code()
				// Returns 1 if the argument is not null and 0 if it is.
				.ALOAD_1().IFNONNULL("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IFNULL() {

		this.getClassBuilder().method(METHOD_NAME, int.class, Object.class)
				.code()
				// Returns 0 if the argument is not null and 1 if it is.
				.ALOAD_1().IFNULL("ifTrue").ICONST_0().IRETURN()
				.label("ifTrue").ICONST_1().IRETURN();
	}

	protected void IINC() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 5.
				.ICONST_0().ISTORE_1().IINC(1, 5).ILOAD_1().IRETURN();
	}

	protected void ILOAD() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int.
				.ILOAD(1).IRETURN();
	}

	protected void ILOAD_0() {

		this.getClassBuilder().method(METHOD_NAME, true, int.class, int.class)
				.code()
				// Returns the given int.
				.ILOAD_0().IRETURN();
	}

	protected void ILOAD_1() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int.
				.ILOAD_1().IRETURN();
	}

	protected void ILOAD_2() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, float.class, int.class).code()
				// Returns the given int.
				.ILOAD_2().IRETURN();
	}

	protected void ILOAD_3() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, long.class, int.class).code()
				// Returns the given int.
				.ILOAD_3().IRETURN();
	}

	protected void IMUL() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 6.
				.ICONST_3().ICONST_2().IMUL().IRETURN();
	}

	protected void INEG() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns -3.
				.ICONST_3().INEG().IRETURN();
	}

	protected void INSTANCEOF() {

		this.getClassBuilder().method(METHOD_NAME, int.class, Object.class)
				.code()
				// Returns 1 if the received object is intance of Blah and 0 if
				// it isn't.
				.ALOAD_1().INSTANCEOF(Blah.class).IRETURN();
	}

	protected void INVOKEDYNAMIC() {
		this.getClassBuilder().bootstrap(CustomBootstrap.class, "dispatch");

		this.getClassBuilder()
				.method(METHOD_NAME, Integer.class, Integer.class,
						Integer.class)
				.code()
				// Adds the given Integers.
				.ALOAD_1()
				.ALOAD_2()
				.INVOKEDYNAMIC(0, "adder", Integer.class, Integer.class,
						Integer.class).ARETURN();
	}

	protected void INVOKEINTERFACE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, Collection.class, Object.class)
				.code()
				// Adds the given object to the given collection.
				.ALOAD_1()
				.ALOAD_2()
				.INVOKEINTERFACE(Collection.class, "add", boolean.class,
						Object.class).RETURN();
	}

	protected void INVOKESPECIAL() {

		this.getClassBuilder().method(METHOD_NAME, Blah.class).code()
				// Returns a new Blah instance
				.NEW(Blah.class).DUP()
				.INVOKESPECIAL(Blah.class, "<init>", void.class).ARETURN();
	}

	protected void INVOKESTATIC() {

		this.getClassBuilder()
				.method(METHOD_NAME, String.class)
				.code()
				// Returns "0"
				.ICONST_0()
				.INVOKESTATIC(Integer.class, "toString", String.class,
						int.class).ARETURN();
	}

	protected void INVOKEVIRTUAL() {

		this.getClassBuilder()
				.method(METHOD_NAME, String.class, Blah.class)
				.code()
				// Returns the result of sending the bleh message to the
				// received object.
				.ALOAD_1().INVOKEVIRTUAL(Blah.class, "bleh", String.class)
				.ARETURN();
	}

	protected void IOR() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 5.
				.ICONST_1().ICONST_4().IOR().IRETURN();
	}

	protected void IREM() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 1.
				.ICONST_5().ICONST_2().IREM().IRETURN();
	}

	protected void IRETURN() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int.
				.ILOAD_1().IRETURN();
	}

	protected void ISHL() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns -8.
				.ICONST_M1().ICONST_3().ISHL().IRETURN();
	}

	protected void ISHR() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns -1.
				.BIPUSH(-8).ICONST_3().ISHR().IRETURN();
	}

	protected void ISTORE() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int after storing it in the local 2.
				.ILOAD_1().ISTORE(3).ILOAD(3).IRETURN();
	}

	protected void ISTORE_0() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int after storing it in the local 0.
				.ILOAD_1().ISTORE_0().ILOAD_0().IRETURN();
	}

	protected void ISTORE_1() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int after storing it in the local 1.
				.ILOAD_1().ISTORE_1().ILOAD_1().IRETURN();
	}

	protected void ISTORE_2() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int after storing it in the local 2.
				.ILOAD_1().ISTORE_2().ILOAD_2().IRETURN();
	}

	protected void ISTORE_3() {

		this.getClassBuilder().method(METHOD_NAME, int.class, int.class).code()
		// Returns the given int after storing it in the local 3.
				.ILOAD_1().ISTORE_2().ILOAD_1().ISTORE_3().ILOAD_3().IRETURN();
	}

	protected void ISUB() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 1.
				.ICONST_3().ICONST_2().ISUB().IRETURN();
	}

	protected void IUSHR() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 536870911.
				.BIPUSH(-8).ICONST_3().IUSHR().IRETURN();
	}

	protected void IXOR() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 1.
				.ICONST_4().ICONST_5().IXOR().IRETURN();
	}

	protected void JSR() {

		this.getClassBuilder().setVersion(JAVA_6);

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 2
				.ICONST_1().JSR("increaseByOne").IRETURN()

				.label("increaseByOne").ASTORE_1().ICONST_1().IADD().RET(1);
	}

	protected void JSR_W() {

		this.getClassBuilder().setVersion(JAVA_6);

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 2
				.ICONST_1().JSR_W("increaseByOne").IRETURN()

				.label("increaseByOne").ASTORE_1().ICONST_1().IADD().RET(1);
	}

	protected void L2D() {

		this.getClassBuilder().method(METHOD_NAME, double.class, long.class)
				.code()
				// Returns the given argument converted to another type.
				.LLOAD_1().L2D().DRETURN();
	}

	protected void L2F() {

		this.getClassBuilder().method(METHOD_NAME, float.class, long.class)
				.code()
				// Returns the given argument converted to another type.
				.LLOAD_1().L2F().FRETURN();
	}

	protected void L2I() {

		this.getClassBuilder().method(METHOD_NAME, int.class, long.class)
				.code()
				// Returns the given argument converted to another type.
				.LLOAD_1().L2I().IRETURN();
	}

	protected void LADD() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 5L.
				.LDC2_W(3L).LDC2_W(2L).LADD().LRETURN();
	}

	protected void LALOAD() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long[].class)
				.code()
				// Returns the first long of the given array.
				.ALOAD_1().ICONST_0().LALOAD().LRETURN();
	}

	protected void LAND() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 4.
				.LDC2_W(5L).LDC2_W(4L).LAND().LRETURN();
	}

	protected void LASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, long.class, long[].class)
				.code()
				// Stores the given long as the first element of the given
				// array.
				.ALOAD_3().ICONST_0().LLOAD_1().LASTORE().RETURN();
	}

	protected void LCMP() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, long.class, long.class).code()
				// Compares the received longs.
				.LLOAD_1().LLOAD_3().LCMP().IRETURN();
	}

	protected void LCONST_0() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 0L.
				.LCONST_0().LRETURN();
	}

	protected void LCONST_1() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 1L.
				.LCONST_1().LRETURN();
	}

	protected void LDC() {

		this.getClassBuilder().method(METHOD_NAME, char.class)
				.code()
				// Returns 'o'.
				.LDC("Hello").LDC(1).LDC(3.0F).F2I().IADD()
				.INVOKEVIRTUAL(String.class, "charAt", char.class, int.class)
				.IRETURN();
	}

	protected void LDC_W() {

		this.getClassBuilder().method(METHOD_NAME, char.class)
				.code()
				// Returns 'o'.
				.LDC_W("Hello").LDC_W(1).LDC_W(3.0F).F2I().IADD()
				.INVOKEVIRTUAL(String.class, "charAt", char.class, int.class)
				.IRETURN();
	}

	protected void LDC2_W() {

		this.getClassBuilder().method(METHOD_NAME, double.class).code()
		// Returns 7.5.
				.LDC2_W(5L).L2D().LDC2_W(2.5).DADD().DRETURN();
	}

	protected void LDIV() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 2L.
				.LDC2_W(5L).LDC2_W(2L).LDIV().LRETURN();
	}

	protected void LLOAD() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Returns the given long.
				.LLOAD(1).LRETURN();
	}

	protected void LLOAD_0() {

		this.getClassBuilder()
				.method(METHOD_NAME, true, long.class, long.class).code()
				// Returns the given long.
				.LLOAD_0().LRETURN();
	}

	protected void LLOAD_1() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Returns the given long.
				.LLOAD_1().LRETURN();
	}

	protected void LLOAD_2() {

		this.getClassBuilder()
				.method(METHOD_NAME, long.class, int.class, long.class).code()
				// Returns the given long.
				.LLOAD_2().LRETURN();
	}

	protected void LLOAD_3() {

		this.getClassBuilder()
				.method(METHOD_NAME, long.class, double.class, long.class)
				.code()
				// Returns the given long.
				.LLOAD_3().LRETURN();
	}

	protected void LMUL() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 6L.
				.LDC2_W(3L).LDC2_W(2L).LMUL().LRETURN();
	}

	protected void LNEG() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns -3L.
				.LDC2_W(3L).LNEG().LRETURN();
	}

	protected void LOOKUPSWITCH() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class)
				.code()
				// If arg is between 2 and 4 returns itself if odd and it's half
				// if even, otherwise returns 0.
				.ILOAD_1()
				.LOOKUPSWITCH("default:", new Link(2, "even:"),
						new Link(3, "odd:"), new Link(4, "even:"))
				.label("even:").ILOAD_1().ICONST_2().IDIV().IRETURN()
				.label("odd:").ILOAD_1().IRETURN().label("default:").ICONST_0()
				.IRETURN();
	}

	protected void LOR() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 5.
				.LCONST_1().LDC2_W(4L).LOR().LRETURN();
	}

	protected void LREM() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 2L.
				.LDC2_W(5L).LDC2_W(2L).LREM().LRETURN();
	}

	protected void LRETURN() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Returns the given long.
				.LLOAD_1().LRETURN();
	}

	protected void LSHL() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns -8L.
				.LDC2_W(-1L).ICONST_3().LSHL().LRETURN();
	}

	protected void LSHR() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns -1L.
				.LDC2_W(-8L).ICONST_3().LSHR().LRETURN();
	}

	protected void LSTORE() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Returns the given long after storing it in the local 3.
				.LLOAD_1().LSTORE(3).LLOAD(3).LRETURN();
	}

	protected void LSTORE_0() {

		this.getClassBuilder().method(METHOD_NAME, true, long.class).code()
		// Returns 1L after storing it in the local 0.
				.LCONST_1().LSTORE_0().LLOAD_0().LRETURN();
	}

	protected void LSTORE_1() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Returns the given long after storing it in the local 1.
				.LLOAD_1().LSTORE_1().LLOAD_1().LRETURN();
	}

	protected void LSTORE_2() {

		this.getClassBuilder()
				.method(METHOD_NAME, true, long.class, long.class).code()
				// Returns 1L after storing it in the local 2.
				.LCONST_1().LSTORE_2().LLOAD_2().LRETURN();
	}

	protected void LSTORE_3() {

		this.getClassBuilder().method(METHOD_NAME, long.class, long.class)
				.code()
				// Returns the given long after storing it in the local 3.
				.LLOAD_1().LSTORE_3().LLOAD_3().LRETURN();
	}

	protected void LSUB() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 1L.
				.LDC2_W(3L).LDC2_W(2L).LSUB().LRETURN();
	}

	protected void LUSHR() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 2305843009213693951L.
				.LDC2_W(-8L).ICONST_3().LUSHR().LRETURN();
	}

	protected void LXOR() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 1L.
				.LDC2_W(4L).LDC2_W(5L).LXOR().LRETURN();
	}

	protected void MONITORENTER() {

		this.getClassBuilder().method(METHOD_NAME, void.class, Object[].class)
				.code()
				// Removes the first element received array thread safely.
				.ALOAD_1().MONITORENTER().ALOAD_1().DUP().ICONST_0()
				.ACONST_NULL().AASTORE().MONITOREXIT().RETURN();
	}

	protected void MONITOREXIT() {

		this.getClassBuilder().method(METHOD_NAME, void.class, Object[].class)
				.code()
				// Removes the first element received array thread safely.
				.ALOAD_1().MONITORENTER().ALOAD_1().DUP().ICONST_0()
				.ACONST_NULL().AASTORE().MONITOREXIT().RETURN();
	}

	protected void MULTIANEWARRAY() {

		this.getClassBuilder().method(METHOD_NAME, Object[][].class).code()
		// Returns a new Object[3][]
				.ICONST_3().MULTIANEWARRAY(Object.class, 2, 1).ARETURN();
	}

	protected void NEW() {
		this.getClassBuilder().method(METHOD_NAME, Blah.class).code()
				// Returns a new Blah instance
				.NEW(Blah.class).DUP()
				.INVOKESPECIAL(Blah.class, "<init>", void.class).ARETURN();
	}

	protected void NEWARRAY() {

		this.getClassBuilder().method(METHOD_NAME, int[].class).code()
		// Returns a new int[2]
				.ICONST_2().NEWARRAY(int.class).ARETURN();
	}

	protected void NOP() {

		this.getClassBuilder().method(METHOD_NAME, void.class).code()
		// Does nothing.
				.NOP().RETURN();
	}

	protected void POP() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns 5.
				.ICONST_5().ICONST_2().POP().IRETURN();
	}

	protected void POP2() {

		this.getClassBuilder().method(METHOD_NAME, long.class).code()
		// Returns 5L.
				.LDC2_W(5L).LDC2_W(2L).POP2().LRETURN();
	}

	protected void PUTFIELD() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, Blah.class, int.class).code()
				// Sets the target object field to the given value
				.ALOAD_1().ILOAD_2().PUTFIELD(Blah.class, "att", int.class)
				.RETURN();
	}

	protected void PUTSTATIC() {

		this.getClassBuilder().method(METHOD_NAME, void.class, int.class)
				.code()
				// Sets the ATT static field to the given value
				.ILOAD_1().PUTSTATIC(Blah.class, "ATT", int.class).RETURN();

		Blah.ATT = 10;
	}

	protected void RET() {

		this.getClassBuilder().setVersion(JAVA_6);

		CodeBuilder code = this.getClassBuilder()
				.method(METHOD_NAME, int.class).code();

		code
		// Returns 2
		.ICONST_1().JSR("increaseByOne").IRETURN()

		.label("increaseByOne").ASTORE_1().ICONST_1().IADD().RET(1);
	}

	protected void RETURN() {

		this.getClassBuilder().method(METHOD_NAME, void.class).code()
		// Does nothing.
				.RETURN();
	}

	protected void SALOAD() {

		this.getClassBuilder().method(METHOD_NAME, short.class, short[].class)
				.code()
				// Returns the first short of the given array.
				.ALOAD_1().ICONST_0().SALOAD().IRETURN();
	}

	protected void SASTORE() {

		this.getClassBuilder()
				.method(METHOD_NAME, void.class, short.class, short[].class)
				.code()
				// Stores the given short as the first element of the given
				// array.
				.ALOAD_2().ICONST_0().ILOAD_1().SASTORE().RETURN();
	}

	protected void SIPUSH() {

		this.getClassBuilder().method(METHOD_NAME, byte.class).code()
		// Returns 5.
				.SIPUSH(5).IRETURN();
	}

	protected void SWAP() {

		this.getClassBuilder().method(METHOD_NAME, int.class).code()
		// Returns -1.
				.ICONST_3().ICONST_2().SWAP().ISUB().IRETURN();
	}

	protected void TABLESWITCH() {

		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class)
				.code()
				// If arg is between 2 and 4 returns itself if odd and it's half
				// if even, otherwise returns 0.
				.ILOAD_1().TABLESWITCH(2, "default:", "even:", "odd:", "even:")
				.label("even:").ILOAD_1().ICONST_2().IDIV().IRETURN()
				.label("odd:").ILOAD_1().IRETURN().label("default:").ICONST_0()
				.IRETURN();
	}

	protected void WIDE() {
		this.getClassBuilder()
				.method(METHOD_NAME, int.class, int.class)
				.code()
				// Returns the given int increased by 1000 after storing it in
				// the local 300.
				.ILOAD_1().WIDE(SimpleInstructionCall.ISTORE(300))
				.WIDE(SimpleInstructionCall.IINC(300, 1000))
				.WIDE(SimpleInstructionCall.ILOAD(300)).IRETURN();
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected ClassBuilder getClassBuilder() {
		return this.classBuilder;
	}

	protected void setClassBuilder(ClassBuilder classBuilder) {
		this.classBuilder = classBuilder;
	}
}