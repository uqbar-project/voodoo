package org.uqbar.voodoo.javaadapter.builder;

import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.InvokeDynamicReference.invokeDynamic;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference.field;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference.interfaceMethod;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference.method;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromClass;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.uqbar.voodoo.javaadapter.builder.auxiliars.Clause;
import org.uqbar.voodoo.javaadapter.builder.auxiliars.Link;
import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.StackMapFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.code.StackMapTableAttribute;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.SimpleInstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.TargetedInstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;

public class CodeBuilder {

	private MethodBuilder owner;
	private List<InstructionCall> calls;
	private List<StackMapFrame> frames;
	private Map<String, Integer> labels;
	private Map<Integer, Map<Integer, String>> labelMappedCalls;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	protected static InstructionCall wideIfRequired(InstructionCall call) {
		for(int i = 0; i < call.getArguments().length; i++) {
			if(call.<Integer>getArgument(i) > 255) {
				return SimpleInstructionCall.WIDE(call);
			}
		}

		return call;
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public CodeBuilder(MethodBuilder owner) {
		this.setOwner(owner);
		this.setCalls(new ArrayList<InstructionCall>());
		this.setFrames(new ArrayList<StackMapFrame>());
		this.setLabels(new HashMap<String, Integer>());
		this.setLabelMappedCalls(new HashMap<Integer, Map<Integer, String>>());
	}

	// ****************************************************************
	// ** BUILD CONFIGURATION
	// ****************************************************************

	public CodeBuilder AALOAD() {
		this.addCall(SimpleInstructionCall.AALOAD);
		return this;
	}

	public CodeBuilder AASTORE() {
		this.addCall(SimpleInstructionCall.AASTORE);
		return this;
	}

	public CodeBuilder ACONST_NULL() {
		this.addCall(SimpleInstructionCall.ACONST_NULL);
		return this;
	}

	public CodeBuilder ALOAD(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.ALOAD(localIndex)));
		return this;
	}

	public CodeBuilder ALOAD_0() {
		this.addCall(SimpleInstructionCall.ALOAD_0);
		return this;
	}

	public CodeBuilder ALOAD_1() {
		this.addCall(SimpleInstructionCall.ALOAD_1);
		return this;
	}

	public CodeBuilder ALOAD_2() {
		this.addCall(SimpleInstructionCall.ALOAD_2);
		return this;
	}

	public CodeBuilder ALOAD_3() {
		this.addCall(SimpleInstructionCall.ALOAD_3);
		return this;
	}

	public CodeBuilder ANEWARRAY(Class<?> type) {
		this.addCall(SimpleInstructionCall.ANEWARRAY(typeFromClass(type)));
		return this;
	}

	public CodeBuilder ANEWARRAY(String typeName) {
		this.addCall(SimpleInstructionCall.ANEWARRAY(typeFromName(typeName)));
		return this;
	}

	public CodeBuilder ARETURN() {
		this.addCall(SimpleInstructionCall.ARETURN);
		return this;
	}

	public CodeBuilder ARRAYLENGTH() {
		this.addCall(SimpleInstructionCall.ARRAYLENGTH);
		return this;
	}

	public CodeBuilder ASTORE(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.ASTORE(localIndex)));
		return this;
	}

	public CodeBuilder ASTORE_0() {
		this.addCall(SimpleInstructionCall.ASTORE_0);
		return this;
	}

	public CodeBuilder ASTORE_1() {
		this.addCall(SimpleInstructionCall.ASTORE_1);
		return this;
	}

	public CodeBuilder ASTORE_2() {
		this.addCall(SimpleInstructionCall.ASTORE_2);
		return this;
	}

	public CodeBuilder ASTORE_3() {
		this.addCall(SimpleInstructionCall.ASTORE_3);
		return this;
	}

	public CodeBuilder ATHROW() {
		this.addCall(SimpleInstructionCall.ATHROW);
		return this;
	}

	public CodeBuilder BALOAD() {
		this.addCall(SimpleInstructionCall.BALOAD);
		return this;
	}

	public CodeBuilder BASTORE() {
		this.addCall(SimpleInstructionCall.BASTORE);
		return this;
	}

	public CodeBuilder BIPUSH(int aByte) {
		this.addCall(SimpleInstructionCall.BIPUSH(aByte));
		return this;
	}

	public CodeBuilder CALOAD() {
		this.addCall(SimpleInstructionCall.CALOAD);
		return this;
	}

	public CodeBuilder CASTORE() {
		this.addCall(SimpleInstructionCall.CASTORE);
		return this;
	}

	public CodeBuilder CHECKCAST(Class<?> type) {
		this.addCall(SimpleInstructionCall.CHECKCAST(typeFromClass(type)));
		return this;
	}

	public CodeBuilder CHECKCAST(String typeName) {
		this.addCall(SimpleInstructionCall.CHECKCAST(typeFromName(typeName)));
		return this;
	}

	public CodeBuilder D2F() {
		this.addCall(SimpleInstructionCall.D2F);
		return this;
	}

	public CodeBuilder D2I() {
		this.addCall(SimpleInstructionCall.D2I);
		return this;
	}

	public CodeBuilder D2L() {
		this.addCall(SimpleInstructionCall.D2L);
		return this;
	}

	public CodeBuilder DADD() {
		this.addCall(SimpleInstructionCall.DADD);
		return this;
	}

	public CodeBuilder DALOAD() {
		this.addCall(SimpleInstructionCall.DALOAD);
		return this;
	}

	public CodeBuilder DASTORE() {
		this.addCall(SimpleInstructionCall.DASTORE);
		return this;
	}

	public CodeBuilder DCMPG() {
		this.addCall(SimpleInstructionCall.DCMPG);
		return this;
	}

	public CodeBuilder DCMPL() {
		this.addCall(SimpleInstructionCall.DCMPL);
		return this;
	}

	public CodeBuilder DCONST_0() {
		this.addCall(SimpleInstructionCall.DCONST_0);
		return this;
	}

	public CodeBuilder DCONST_1() {
		this.addCall(SimpleInstructionCall.DCONST_1);
		return this;
	}

	public CodeBuilder DDIV() {
		this.addCall(SimpleInstructionCall.DDIV);
		return this;
	}

	public CodeBuilder DLOAD(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.DLOAD(localIndex)));
		return this;
	}

	public CodeBuilder DLOAD_0() {
		this.addCall(SimpleInstructionCall.DLOAD_0);
		return this;
	}

	public CodeBuilder DLOAD_1() {
		this.addCall(SimpleInstructionCall.DLOAD_1);
		return this;
	}

	public CodeBuilder DLOAD_2() {
		this.addCall(SimpleInstructionCall.DLOAD_2);
		return this;
	}

	public CodeBuilder DLOAD_3() {
		this.addCall(SimpleInstructionCall.DLOAD_3);
		return this;
	}

	public CodeBuilder DMUL() {
		this.addCall(SimpleInstructionCall.DMUL);
		return this;
	}

	public CodeBuilder DNEG() {
		this.addCall(SimpleInstructionCall.DNEG);
		return this;
	}

	public CodeBuilder DREM() {
		this.addCall(SimpleInstructionCall.DREM);
		return this;
	}

	public CodeBuilder DRETURN() {
		this.addCall(SimpleInstructionCall.DRETURN);
		return this;
	}

	public CodeBuilder DSTORE(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.DSTORE(localIndex)));
		return this;
	}

	public CodeBuilder DSTORE_0() {
		this.addCall(SimpleInstructionCall.DSTORE_0);
		return this;
	}

	public CodeBuilder DSTORE_1() {
		this.addCall(SimpleInstructionCall.DSTORE_1);
		return this;
	}

	public CodeBuilder DSTORE_2() {
		this.addCall(SimpleInstructionCall.DSTORE_2);
		return this;
	}

	public CodeBuilder DSTORE_3() {
		this.addCall(SimpleInstructionCall.DSTORE_3);
		return this;
	}

	public CodeBuilder DSUB() {
		this.addCall(SimpleInstructionCall.DSUB);
		return this;
	}

	public CodeBuilder DUP() {
		this.addCall(SimpleInstructionCall.DUP);
		return this;
	}

	public CodeBuilder DUP_X1() {
		this.addCall(SimpleInstructionCall.DUP_X1);
		return this;
	}

	public CodeBuilder DUP_X2() {
		this.addCall(SimpleInstructionCall.DUP_X2);
		return this;
	}

	public CodeBuilder DUP2() {
		this.addCall(SimpleInstructionCall.DUP2);
		return this;
	}

	public CodeBuilder DUP2_X1() {
		this.addCall(SimpleInstructionCall.DUP2_X1);
		return this;
	}

	public CodeBuilder DUP2_X2() {
		this.addCall(SimpleInstructionCall.DUP2_X2);
		return this;
	}

	public CodeBuilder F2D() {
		this.addCall(SimpleInstructionCall.F2D);
		return this;
	}

	public CodeBuilder F2I() {
		this.addCall(SimpleInstructionCall.F2I);
		return this;
	}

	public CodeBuilder F2L() {
		this.addCall(SimpleInstructionCall.F2L);
		return this;
	}

	public CodeBuilder FADD() {
		this.addCall(SimpleInstructionCall.FADD);
		return this;
	}

	public CodeBuilder FALOAD() {
		this.addCall(SimpleInstructionCall.FALOAD);
		return this;
	}

	public CodeBuilder FASTORE() {
		this.addCall(SimpleInstructionCall.FASTORE);
		return this;
	}

	public CodeBuilder FCMPG() {
		this.addCall(SimpleInstructionCall.FCMPG);
		return this;
	}

	public CodeBuilder FCMPL() {
		this.addCall(SimpleInstructionCall.FCMPL);
		return this;
	}

	public CodeBuilder FCONST_0() {
		this.addCall(SimpleInstructionCall.FCONST_0);
		return this;
	}

	public CodeBuilder FCONST_1() {
		this.addCall(SimpleInstructionCall.FCONST_1);
		return this;
	}

	public CodeBuilder FCONST_2() {
		this.addCall(SimpleInstructionCall.FCONST_2);
		return this;
	}

	public CodeBuilder FDIV() {
		this.addCall(SimpleInstructionCall.FDIV);
		return this;
	}

	public CodeBuilder FLOAD(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.FLOAD(localIndex)));
		return this;
	}

	public CodeBuilder FLOAD_0() {
		this.addCall(SimpleInstructionCall.FLOAD_0);
		return this;
	}

	public CodeBuilder FLOAD_1() {
		this.addCall(SimpleInstructionCall.FLOAD_1);
		return this;
	}

	public CodeBuilder FLOAD_2() {
		this.addCall(SimpleInstructionCall.FLOAD_2);
		return this;
	}

	public CodeBuilder FLOAD_3() {
		this.addCall(SimpleInstructionCall.FLOAD_3);
		return this;
	}

	public CodeBuilder FMUL() {
		this.addCall(SimpleInstructionCall.FMUL);
		return this;
	}

	public CodeBuilder FNEG() {
		this.addCall(SimpleInstructionCall.FNEG);
		return this;
	}

	public CodeBuilder FREM() {
		this.addCall(SimpleInstructionCall.FREM);
		return this;
	}

	public CodeBuilder FRETURN() {
		this.addCall(SimpleInstructionCall.FRETURN);
		return this;
	}

	public CodeBuilder FSTORE(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.FSTORE(localIndex)));
		return this;
	}

	public CodeBuilder FSTORE_0() {
		this.addCall(SimpleInstructionCall.FSTORE_0);
		return this;
	}

	public CodeBuilder FSTORE_1() {
		this.addCall(SimpleInstructionCall.FSTORE_1);
		return this;
	}

	public CodeBuilder FSTORE_2() {
		this.addCall(SimpleInstructionCall.FSTORE_2);
		return this;
	}

	public CodeBuilder FSTORE_3() {
		this.addCall(SimpleInstructionCall.FSTORE_3);
		return this;
	}

	public CodeBuilder FSUB() {
		this.addCall(SimpleInstructionCall.FSUB);
		return this;
	}

	public CodeBuilder GETFIELD(Class<?> aClass, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.GETFIELD(field(aClass, fieldName, type)));
		return this;
	}

	public CodeBuilder GETFIELD(Class<?> aClass, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.GETFIELD(field(aClass, fieldName, typeName)));
		return this;
	}

	public CodeBuilder GETFIELD(String className, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.GETFIELD(field(className, fieldName, type)));
		return this;
	}

	public CodeBuilder GETFIELD(String className, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.GETFIELD(field(className, fieldName, typeName)));
		return this;
	}

	public CodeBuilder GETSTATIC(Class<?> aClass, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.GETSTATIC(field(aClass, fieldName, type)));
		return this;
	}

	public CodeBuilder GETSTATIC(Class<?> aClass, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.GETSTATIC(field(aClass, fieldName, typeName)));
		return this;
	}

	public CodeBuilder GETSTATIC(String className, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.GETSTATIC(field(className, fieldName, type)));
		return this;
	}

	public CodeBuilder GETSTATIC(String className, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.GETSTATIC(field(className, fieldName, typeName)));
		return this;
	}

	public CodeBuilder GOTO(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.GOTO(null));

		return this;
	}

	public CodeBuilder GOTO_W(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.GOTO_W(null));

		return this;
	}

	public CodeBuilder I2B() {
		this.addCall(SimpleInstructionCall.I2B);
		return this;
	}

	public CodeBuilder I2C() {
		this.addCall(SimpleInstructionCall.I2C);
		return this;
	}

	public CodeBuilder I2D() {
		this.addCall(SimpleInstructionCall.I2D);
		return this;
	}

	public CodeBuilder I2F() {
		this.addCall(SimpleInstructionCall.I2F);
		return this;
	}

	public CodeBuilder I2L() {
		this.addCall(SimpleInstructionCall.I2L);
		return this;
	}

	public CodeBuilder I2S() {
		this.addCall(SimpleInstructionCall.I2S);
		return this;
	}

	public CodeBuilder IADD() {
		this.addCall(SimpleInstructionCall.IADD);
		return this;
	}

	public CodeBuilder IALOAD() {
		this.addCall(SimpleInstructionCall.IALOAD);
		return this;
	}

	public CodeBuilder IAND() {
		this.addCall(SimpleInstructionCall.IAND);
		return this;
	}

	public CodeBuilder IASTORE() {
		this.addCall(SimpleInstructionCall.IASTORE);
		return this;
	}

	public CodeBuilder ICONST_M1() {
		this.addCall(SimpleInstructionCall.ICONST_M1);
		return this;
	}

	public CodeBuilder ICONST_0() {
		this.addCall(SimpleInstructionCall.ICONST_0);
		return this;
	}

	public CodeBuilder ICONST_1() {
		this.addCall(SimpleInstructionCall.ICONST_1);
		return this;
	}

	public CodeBuilder ICONST_2() {
		this.addCall(SimpleInstructionCall.ICONST_2);
		return this;
	}

	public CodeBuilder ICONST_3() {
		this.addCall(SimpleInstructionCall.ICONST_3);
		return this;
	}

	public CodeBuilder ICONST_4() {
		this.addCall(SimpleInstructionCall.ICONST_4);
		return this;
	}

	public CodeBuilder ICONST_5() {
		this.addCall(SimpleInstructionCall.ICONST_5);
		return this;
	}

	public CodeBuilder IDIV() {
		this.addCall(SimpleInstructionCall.IDIV);
		return this;
	}

	public CodeBuilder IF_ACMPEQ(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ACMPEQ(null));

		return this;
	}

	public CodeBuilder IF_ACMPNE(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ACMPNE(null));

		return this;
	}

	public CodeBuilder IF_ICMPEQ(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ICMPEQ(null));

		return this;
	}

	public CodeBuilder IF_ICMPNE(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ICMPNE(null));

		return this;
	}

	public CodeBuilder IF_ICMPLT(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ICMPLT(null));

		return this;
	}

	public CodeBuilder IF_ICMPGE(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ICMPGE(null));

		return this;
	}

	public CodeBuilder IF_ICMPGT(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ICMPGT(null));

		return this;
	}

	public CodeBuilder IF_ICMPLE(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IF_ICMPLE(null));

		return this;
	}

	public CodeBuilder IFEQ(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFEQ(null));

		return this;
	}

	public CodeBuilder IFNE(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFNE(null));

		return this;
	}

	public CodeBuilder IFLT(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFLT(null));

		return this;
	}

	public CodeBuilder IFGE(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFGE(null));

		return this;
	}

	public CodeBuilder IFGT(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFGT(null));

		return this;
	}

	public CodeBuilder IFLE(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFLE(null));

		return this;
	}

	public CodeBuilder IFNONNULL(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFNONNULL(null));

		return this;
	}

	public CodeBuilder IFNULL(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.IFNULL(null));

		return this;
	}

	public CodeBuilder IINC(int localIndex, int increment) {
		this.addCall(wideIfRequired(SimpleInstructionCall.IINC(localIndex, increment)));
		return this;
	}

	public CodeBuilder ILOAD(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.ILOAD(localIndex)));
		return this;
	}

	public CodeBuilder ILOAD_0() {
		this.addCall(SimpleInstructionCall.ILOAD_0);
		return this;
	}

	public CodeBuilder ILOAD_1() {
		this.addCall(SimpleInstructionCall.ILOAD_1);
		return this;
	}

	public CodeBuilder ILOAD_2() {
		this.addCall(SimpleInstructionCall.ILOAD_2);
		return this;
	}

	public CodeBuilder ILOAD_3() {
		this.addCall(SimpleInstructionCall.ILOAD_3);
		return this;
	}

	public CodeBuilder IMUL() {
		this.addCall(SimpleInstructionCall.IMUL);
		return this;
	}

	public CodeBuilder INEG() {
		this.addCall(SimpleInstructionCall.INEG);
		return this;
	}

	public CodeBuilder INSTANCEOF(Class<?> type) {
		this.addCall(SimpleInstructionCall.INSTANCEOF(typeFromClass(type)));
		return this;
	}

	public CodeBuilder INSTANCEOF(String typeName) {
		this.addCall(SimpleInstructionCall.INSTANCEOF(typeFromName(typeName)));
		return this;
	}

	public CodeBuilder INVOKEDYNAMIC(int bootstrapMethodIndex, String methodName, Class<?> methodReturnType, Class<?>... methodArgumentTypes) {
		this.addCall(SimpleInstructionCall.INVOKEDYNAMIC(
			invokeDynamic(bootstrapMethodIndex, methodName, methodReturnType, methodArgumentTypes)));
		return this;
	}

	public CodeBuilder INVOKEDYNAMIC(int bootstrapMethodIndex, String methodName, String methodReturnTypeName, String... methodArgumentTypeNames) {
		this.addCall(SimpleInstructionCall.INVOKEDYNAMIC(
			invokeDynamic(bootstrapMethodIndex, methodName, methodReturnTypeName, methodArgumentTypeNames)));
		return this;
	}

	public CodeBuilder INVOKEINTERFACE(Class<?> aClass, String methodName, Class<?> methodReturnType, Class<?>... methodArgumentTypes) {
		this.addCall(SimpleInstructionCall
			.INVOKEINTERFACE(interfaceMethod(aClass, methodName, methodReturnType, methodArgumentTypes)));
		return this;
	}

	public CodeBuilder INVOKEINTERFACE(String className, String methodName, String methodReturnTypeName, String... methodArgumentTypeNames) {
		this.addCall(SimpleInstructionCall
			.INVOKEINTERFACE(interfaceMethod(className, methodName, methodReturnTypeName, methodArgumentTypeNames)));
		return this;
	}

	public CodeBuilder INVOKESPECIAL(Class<?> aClass, String methodName, Class<?> methodReturnType, Class<?>... methodArgumentTypes) {
		this.addCall(SimpleInstructionCall.INVOKESPECIAL(method(aClass, methodName, methodReturnType, methodArgumentTypes)));
		return this;
	}

	public CodeBuilder INVOKESPECIAL(String className, String methodName, String methodReturnTypeName, String... methodArgumentTypeNames) {
		this.addCall(SimpleInstructionCall.INVOKESPECIAL(
			method(className, methodName, methodReturnTypeName, methodArgumentTypeNames)));
		return this;
	}

	public CodeBuilder INVOKESTATIC(Class<?> aClass, String methodName, Class<?> methodReturnType, Class<?>... methodArgumentTypes) {
		this.addCall(SimpleInstructionCall.INVOKESTATIC(method(aClass, methodName, methodReturnType, methodArgumentTypes)));
		return this;
	}

	public CodeBuilder INVOKESTATIC(String className, String methodName, String methodReturnTypeName, String... methodArgumentTypeNames) {
		this.addCall(SimpleInstructionCall.INVOKESTATIC(
			method(className, methodName, methodReturnTypeName, methodArgumentTypeNames)));
		return this;
	}

	public CodeBuilder INVOKEVIRTUAL(Class<?> aClass, String methodName, Class<?> methodReturnType, Class<?>... methodArgumentTypes) {
		this.addCall(SimpleInstructionCall.INVOKEVIRTUAL(method(aClass, methodName, methodReturnType, methodArgumentTypes)));
		return this;
	}

	public CodeBuilder INVOKEVIRTUAL(String className, String methodName, String methodReturnTypeName, String... methodArgumentTypeNames) {
		this.addCall(SimpleInstructionCall.INVOKEVIRTUAL(
			method(className, methodName, methodReturnTypeName, methodArgumentTypeNames)));
		return this;
	}

	public CodeBuilder IOR() {
		this.addCall(SimpleInstructionCall.IOR);
		return this;
	}

	public CodeBuilder IREM() {
		this.addCall(SimpleInstructionCall.IREM);
		return this;
	}

	public CodeBuilder IRETURN() {
		this.addCall(SimpleInstructionCall.IRETURN);
		return this;
	}

	public CodeBuilder ISHL() {
		this.addCall(SimpleInstructionCall.ISHL);
		return this;
	}

	public CodeBuilder ISHR() {
		this.addCall(SimpleInstructionCall.ISHR);
		return this;
	}

	public CodeBuilder ISTORE(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.ISTORE(localIndex)));
		return this;
	}

	public CodeBuilder ISTORE_0() {
		this.addCall(SimpleInstructionCall.ISTORE_0);
		return this;
	}

	public CodeBuilder ISTORE_1() {
		this.addCall(SimpleInstructionCall.ISTORE_1);
		return this;
	}

	public CodeBuilder ISTORE_2() {
		this.addCall(SimpleInstructionCall.ISTORE_2);
		return this;
	}

	public CodeBuilder ISTORE_3() {
		this.addCall(SimpleInstructionCall.ISTORE_3);
		return this;
	}

	public CodeBuilder ISUB() {
		this.addCall(SimpleInstructionCall.ISUB);
		return this;
	}

	public CodeBuilder IUSHR() {
		this.addCall(SimpleInstructionCall.IUSHR);
		return this;
	}

	public CodeBuilder IXOR() {
		this.addCall(SimpleInstructionCall.IXOR);
		return this;
	}

	public CodeBuilder JSR(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.JSR(null));

		return this;
	}

	public CodeBuilder JSR_W(String label) {
		this.linkToLabel(label, 0);
		this.addCall(SimpleInstructionCall.JSR_W(null));

		return this;
	}

	public CodeBuilder L2D() {
		this.addCall(SimpleInstructionCall.L2D);
		return this;
	}

	public CodeBuilder L2F() {
		this.addCall(SimpleInstructionCall.L2F);
		return this;
	}

	public CodeBuilder L2I() {
		this.addCall(SimpleInstructionCall.L2I);
		return this;
	}

	public CodeBuilder LADD() {
		this.addCall(SimpleInstructionCall.LADD);
		return this;
	}

	public CodeBuilder LALOAD() {
		this.addCall(SimpleInstructionCall.LALOAD);
		return this;
	}

	public CodeBuilder LAND() {
		this.addCall(SimpleInstructionCall.LAND);
		return this;
	}

	public CodeBuilder LASTORE() {
		this.addCall(SimpleInstructionCall.LASTORE);
		return this;
	}

	public CodeBuilder LCMP() {
		this.addCall(SimpleInstructionCall.LCMP);
		return this;
	}

	public CodeBuilder LCONST_0() {
		this.addCall(SimpleInstructionCall.LCONST_0);
		return this;
	}

	public CodeBuilder LCONST_1() {
		this.addCall(SimpleInstructionCall.LCONST_1);
		return this;
	}

	public CodeBuilder LDC(String constant) {
		this.addCall(SimpleInstructionCall.LDC(constant));
		return this;
	}

	public CodeBuilder LDC(int constant) {
		this.addCall(SimpleInstructionCall.LDC(constant));
		return this;
	}

	public CodeBuilder LDC(float constant) {
		this.addCall(SimpleInstructionCall.LDC(constant));
		return this;
	}

	public CodeBuilder LDC_W(String constant) {
		this.addCall(SimpleInstructionCall.LDC_W(constant));
		return this;
	}

	public CodeBuilder LDC_W(int constant) {
		this.addCall(SimpleInstructionCall.LDC_W(constant));
		return this;
	}

	public CodeBuilder LDC_W(float constant) {
		this.addCall(SimpleInstructionCall.LDC_W(constant));
		return this;
	}

	public CodeBuilder LDC2_W(long constant) {
		this.addCall(SimpleInstructionCall.LDC2_W(constant));
		return this;
	}

	public CodeBuilder LDC2_W(double constant) {
		this.addCall(SimpleInstructionCall.LDC2_W(constant));
		return this;
	}

	public CodeBuilder LDIV() {
		this.addCall(SimpleInstructionCall.LDIV);
		return this;
	}

	public CodeBuilder LLOAD(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.LLOAD(localIndex)));
		return this;
	}

	public CodeBuilder LLOAD_0() {
		this.addCall(SimpleInstructionCall.LLOAD_0);
		return this;
	}

	public CodeBuilder LLOAD_1() {
		this.addCall(SimpleInstructionCall.LLOAD_1);
		return this;
	}

	public CodeBuilder LLOAD_2() {
		this.addCall(SimpleInstructionCall.LLOAD_2);
		return this;
	}

	public CodeBuilder LLOAD_3() {
		this.addCall(SimpleInstructionCall.LLOAD_3);
		return this;
	}

	public CodeBuilder LMUL() {
		this.addCall(SimpleInstructionCall.LMUL);
		return this;
	}

	public CodeBuilder LNEG() {
		this.addCall(SimpleInstructionCall.LNEG);
		return this;
	}

	public CodeBuilder LOOKUPSWITCH(String defaultLabel, Link... keyLabelPairs) {
		this.linkToLabel(defaultLabel, 0);

		int[] keyValueOffsets = new int[keyLabelPairs.length * 2];
		for(int i = 0; i < keyLabelPairs.length; i++) {
			keyValueOffsets[i * 2] = keyLabelPairs[i].getKey();

			this.linkToLabel(keyLabelPairs[i].getLabel(), 1 + i * 2 + 1);
		}

		this.addCall(SimpleInstructionCall.LOOKUPSWITCH(null, keyValueOffsets));

		return this;
	}

	public CodeBuilder LOR() {
		this.addCall(SimpleInstructionCall.LOR);
		return this;
	}

	public CodeBuilder LREM() {
		this.addCall(SimpleInstructionCall.LREM);
		return this;
	}

	public CodeBuilder LRETURN() {
		this.addCall(SimpleInstructionCall.LRETURN);
		return this;
	}

	public CodeBuilder LSHL() {
		this.addCall(SimpleInstructionCall.LSHL);
		return this;
	}

	public CodeBuilder LSHR() {
		this.addCall(SimpleInstructionCall.LSHR);
		return this;
	}

	public CodeBuilder LSTORE(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.LSTORE(localIndex)));
		return this;
	}

	public CodeBuilder LSTORE_0() {
		this.addCall(SimpleInstructionCall.LSTORE_0);
		return this;
	}

	public CodeBuilder LSTORE_1() {
		this.addCall(SimpleInstructionCall.LSTORE_1);
		return this;
	}

	public CodeBuilder LSTORE_2() {
		this.addCall(SimpleInstructionCall.LSTORE_2);
		return this;
	}

	public CodeBuilder LSTORE_3() {
		this.addCall(SimpleInstructionCall.LSTORE_3);
		return this;
	}

	public CodeBuilder LSUB() {
		this.addCall(SimpleInstructionCall.LSUB);
		return this;
	}

	public CodeBuilder LUSHR() {
		this.addCall(SimpleInstructionCall.LUSHR);
		return this;
	}

	public CodeBuilder LXOR() {
		this.addCall(SimpleInstructionCall.LXOR);
		return this;
	}

	public CodeBuilder MONITORENTER() {
		this.addCall(SimpleInstructionCall.MONITORENTER);
		return this;
	}

	public CodeBuilder MONITOREXIT() {
		this.addCall(SimpleInstructionCall.MONITOREXIT);
		return this;
	}

	public CodeBuilder MULTIANEWARRAY(Class<?> type, int dimensionCount) {
		this.addCall(SimpleInstructionCall.MULTIANEWARRAY(typeFromClass(type), dimensionCount, dimensionCount));
		return this;
	}

	public CodeBuilder MULTIANEWARRAY(Class<?> type, int dimensionCount, int dimensionSizesToPop) {
		this.addCall(SimpleInstructionCall.MULTIANEWARRAY(typeFromClass(type), dimensionCount, dimensionSizesToPop));
		return this;
	}

	public CodeBuilder MULTIANEWARRAY(String typeName, int dimensionCount) {
		this.addCall(SimpleInstructionCall.MULTIANEWARRAY(typeFromName(typeName), dimensionCount, dimensionCount));
		return this;
	}

	public CodeBuilder MULTIANEWARRAY(String typeName, int dimensionCount, int dimensionSizesToPop) {
		this.addCall(SimpleInstructionCall.MULTIANEWARRAY(typeFromName(typeName), dimensionCount, dimensionSizesToPop));
		return this;
	}

	public CodeBuilder NEW(Class<?> type) {
		this.addCall(SimpleInstructionCall.NEW(typeFromClass(type)));
		return this;
	}

	public CodeBuilder NEW(String typeName) {
		this.addCall(SimpleInstructionCall.NEW(typeFromName(typeName)));
		return this;
	}

	public CodeBuilder NEWARRAY(Class<?> type) {
		this.addCall(SimpleInstructionCall.NEWARRAY(type));
		return this;
	}

	public CodeBuilder NOP() {
		this.addCall(SimpleInstructionCall.NOP);
		return this;
	}

	public CodeBuilder POP() {
		this.addCall(SimpleInstructionCall.POP);
		return this;
	}

	public CodeBuilder POP2() {
		this.addCall(SimpleInstructionCall.POP2);
		return this;
	}

	public CodeBuilder PUTFIELD(Class<?> aClass, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.PUTFIELD(field(aClass, fieldName, type)));
		return this;
	}

	public CodeBuilder PUTFIELD(Class<?> aClass, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.PUTFIELD(field(aClass, fieldName, typeName)));
		return this;
	}

	public CodeBuilder PUTFIELD(String className, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.PUTFIELD(field(className, fieldName, type)));
		return this;
	}

	public CodeBuilder PUTFIELD(String className, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.PUTFIELD(field(className, fieldName, typeName)));
		return this;
	}

	public CodeBuilder PUTSTATIC(Class<?> aClass, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.PUTSTATIC(field(aClass, fieldName, type)));
		return this;
	}

	public CodeBuilder PUTSTATIC(Class<?> aClass, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.PUTSTATIC(field(aClass, fieldName, typeName)));
		return this;
	}

	public CodeBuilder PUTSTATIC(String className, String fieldName, Class<?> type) {
		this.addCall(SimpleInstructionCall.PUTSTATIC(field(className, fieldName, type)));
		return this;
	}

	public CodeBuilder PUTSTATIC(String className, String fieldName, String typeName) {
		this.addCall(SimpleInstructionCall.PUTSTATIC(field(className, fieldName, typeName)));
		return this;
	}

	public CodeBuilder RET(int localIndex) {
		this.addCall(wideIfRequired(SimpleInstructionCall.RET(localIndex)));
		return this;
	}

	public CodeBuilder RETURN() {
		this.addCall(SimpleInstructionCall.RETURN);
		return this;
	}

	public CodeBuilder SALOAD() {
		this.addCall(SimpleInstructionCall.SALOAD);
		return this;
	}

	public CodeBuilder SASTORE() {
		this.addCall(SimpleInstructionCall.SASTORE);
		return this;
	}

	public CodeBuilder SIPUSH(int aShort) {
		this.addCall(SimpleInstructionCall.SIPUSH(aShort));
		return this;
	}

	public CodeBuilder SWAP() {
		this.addCall(SimpleInstructionCall.SWAP);
		return this;
	}

	public CodeBuilder TABLESWITCH(int from, String defaultLabel, String... labels) {
		this.linkToLabel(defaultLabel, 0);

		for(int i = 0; i < labels.length; i++) {
			this.linkToLabel(labels[i], i + 2);
		}

		this.addCall(SimpleInstructionCall.TABLESWITCH(from, null, new TargetedInstructionCall[labels.length]));

		return this;
	}

	public CodeBuilder WIDE(InstructionCall innerCall) {
		this.addCall(wideIfRequired(innerCall));
		return this;
	}

	// ****************************************************************
	// ** EXTENDED BUILDING
	// ****************************************************************

	public CodeBuilder ICONST(int x) {
		switch(x) {
			case -1:
				return this.ICONST_M1();
			case 0:
				return this.ICONST_0();
			case 1:
				return this.ICONST_1();
			case 2:
				return this.ICONST_2();
			case 3:
				return this.ICONST_3();
			case 4:
				return this.ICONST_4();
			case 5:
				return this.ICONST_5();
			default:
				return this.BIPUSH(x);
		}
	}

	// ****************************************************************
	// ** EXTRA BUILDING
	// ****************************************************************

	public CodeBuilder label(String label) {
		if(this.getLabels().containsKey(label)) {
			throw new RuntimeException("Label '" + label + "' is already registered.");
		}

		this.getLabels().put(label, this.getCalls().size());

		return this;
	}

	public CodeBuilder stackMapFrame(StackMapFrame frame) {
		this.getFrames().add(frame);

		return this;
	}

	public <T> CodeBuilder iterate(T[] array, Clause<T> clause) {
		return this.iterate(Arrays.asList(array), clause);
	}

	public <T> CodeBuilder iterate(Iterable<T> collection, Clause<T> clause) {

		int i = 0;
		for(T element : collection) {
			CodeBuilder subCode = new CodeBuilder(null);
			clause.run(subCode, element, i++);

			this.getCalls().addAll(subCode.getCalls());
		}

		return this;
	}

	// ****************************************************************
	// ** BUILDING
	// ****************************************************************

	public CodeAttribute build() {
		CodeAttribute answer = new CodeAttribute();

		this.materializeLabels();

		// TODO: Sacar de acá y que el frames se calcule directamente ?
		if(this.getFrames().size() > 0) {
			StackMapTableAttribute stackMapTable = new StackMapTableAttribute();
			stackMapTable.setFrames(this.getFrames());

			answer.addAttribute(stackMapTable);
		}

		answer.setMaxStack(this.getMaxStack());
		answer.setMaxLocals(this.getMaxLocals());
		answer.addCalls(this.getCalls());

		return answer;
	}

	protected void materializeLabels() {
		for(int index : this.getLabelMappedCalls().keySet()) {
			Map<Integer, String> linkedLabel = this.getLabelMappedCalls().get(index);

			if(linkedLabel != null) {
				for(Entry<Integer, String> labelLink : linkedLabel.entrySet()) {
					Integer targetIndex = this.getLabels().get(labelLink.getValue());

					InstructionCall target = this.getCalls().get(targetIndex);

					if(!(target instanceof TargetedInstructionCall)) {
						target = new TargetedInstructionCall(target);
						this.getCalls().set(targetIndex, target);
					}

					this.getCalls().get(index).getArguments()[labelLink.getKey()] = target;
				}
			}
		}
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	// TODO: calcular esto en lugar de que esté en un atributo en el método.
	protected int getMaxLocals() {
		int argumentsLocalCount = this.getOwner().getModifier().isMarkedAsStatic() ? 0 : 1;
		for(TypeReference argumentType : this.getOwner().getArgumentTypes()) {
			argumentsLocalCount += argumentType.getName().equals(double.class.getName())
					|| argumentType.getName().equals(long.class.getName())
				? 2
				: 1;
		}

		int maxLocalAccessed = 0;
		for(InstructionCall call : this.getCalls()) {
			maxLocalAccessed = Math.max(maxLocalAccessed, call.getMinLocalsSizeRequired());
		}

		return Math.max(maxLocalAccessed, argumentsLocalCount);
	}

	// TODO: calcular esto en lugar de que esté en un atributo en el método.
	protected int getMaxStack() {
		MaxStackCalculator maxStackCalculator = new MaxStackCalculator();
		maxStackCalculator.iterate(this.getCalls());
		return maxStackCalculator.getMaxStack();
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	protected void addCall(InstructionCall call) {
		this.getCalls().add(call);
	}

	protected void linkToLabel(String label, int argumentIndex) {
		int index = this.getCalls().size();
		Map<Integer, String> linkedLabels = this.getLabelMappedCalls().get(index);

		if(linkedLabels == null) {
			linkedLabels = new HashMap<Integer, String>();
			this.getLabelMappedCalls().put(index, linkedLabels);
		}

		linkedLabels.put(argumentIndex, label);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected MethodBuilder getOwner() {
		return this.owner;
	}

	protected void setOwner(MethodBuilder owner) {
		this.owner = owner;
	}

	public List<InstructionCall> getCalls() {
		return this.calls;
	}

	protected void setCalls(List<InstructionCall> calls) {
		this.calls = calls;
	}

	protected List<StackMapFrame> getFrames() {
		return this.frames;
	}

	protected void setFrames(List<StackMapFrame> frames) {
		this.frames = frames;
	}

	protected Map<String, Integer> getLabels() {
		return this.labels;
	}

	protected void setLabels(Map<String, Integer> labels) {
		this.labels = labels;
	}

	protected Map<Integer, Map<Integer, String>> getLabelMappedCalls() {
		return this.labelMappedCalls;
	}

	protected void setLabelMappedCalls(Map<Integer, Map<Integer, String>> labelMappedCalls) {
		this.labelMappedCalls = labelMappedCalls;
	}
}
