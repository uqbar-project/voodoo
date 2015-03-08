package org.uqbar.voodoo.javaadapter.model.methods.instructions;

import static org.uqbar.voodoo.javaadapter.BytecodeToolsUtils.append;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Fixed._0;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Fixed._1;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Fixed._2;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Fixed._3;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Fixed._4;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Fixed._5;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Fixed._6;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.AmountStrategy;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.CallArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Inc;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.amount.Send;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.ArgumentStrategy;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.CallOffsetArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.ConstantPoolArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.InstructionCallArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.InterfaceMethodArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.InvokeDynamicArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.LookupSwitchArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.NumericArgument;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.arguments.TableSwitchArgument;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataInputStream;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public enum BytecodeInstruction {
	AALOAD(0x32, _0, _2, _1),
	AASTORE(0x53, _0, _3, _0),
	ACONST_NULL(0x01, _0, _0, _1),
	ALOAD(0x19, new Inc(0), _0, _1, new NumericArgument(1)),
	ALOAD_0(0x2A, _1, _0, _1),
	ALOAD_1(0x2B, _2, _0, _1),
	ALOAD_2(0x2C, _3, _0, _1),
	ALOAD_3(0x2D, _4, _0, _1),
	ANEWARRAY(0xBD, _0, _1, _1, new ConstantPoolArgument()),
	ARETURN(0xB0, _0, _1, _0),
	ARRAYLENGTH(0xBE, _0, _1, _1),
	ASTORE(0x3A, new Inc(0), _1, _0, new NumericArgument(1)),
	ASTORE_0(0x4B, _1, _1, _0),
	ASTORE_1(0x4C, _2, _1, _0),
	ASTORE_2(0x4D, _3, _1, _0),
	ASTORE_3(0x4E, _4, _1, _0),
	ATHROW(0xBF, _0, _1, _1),
	BALOAD(0x33, _0, _2, _1),
	BASTORE(0x54, _0, _3, _0),
	BIPUSH(0x10, _0, _0, _1, new NumericArgument(1, true)),
	CALOAD(0x34, _0, _2, _1),
	CASTORE(0x55, _0, _3, _0),
	CHECKCAST(0xC0, _0, _1, _1, new ConstantPoolArgument()),
	D2F(0x90, _0, _2, _1),
	D2I(0x8E, _0, _2, _1),
	D2L(0x8F, _0, _2, _2),
	DADD(0x63, _0, _4, _2),
	DALOAD(0x31, _0, _2, _2),
	DASTORE(0x52, _0, _4, _0),
	DCMPG(0x98, _0, _4, _1),
	DCMPL(0x97, _0, _4, _1),
	DCONST_0(0x0E, _0, _0, _2),
	DCONST_1(0x0F, _0, _0, _2),
	DDIV(0x6F, _0, _4, _2),
	DLOAD(0x18, new Inc(0, 2), _0, _2, new NumericArgument(1)),
	DLOAD_0(0x26, _2, _0, _2),
	DLOAD_1(0x27, _3, _0, _2),
	DLOAD_2(0x28, _4, _0, _2),
	DLOAD_3(0x29, _5, _0, _2),
	DMUL(0x6B, _0, _4, _2),
	DNEG(0x77, _0, _2, _2),
	DREM(0x73, _0, _4, _2),
	DRETURN(0xAF, _0, _2, _0),
	DSTORE(0x39, new Inc(0, 2), _2, _0, new NumericArgument(1)),
	DSTORE_0(0x47, _2, _2, _0),
	DSTORE_1(0x48, _3, _2, _0),
	DSTORE_2(0x49, _4, _2, _0),
	DSTORE_3(0x4A, _5, _2, _0),
	DSUB(0x67, _0, _4, _2),
	DUP(0x59, _0, _1, _2),
	DUP_X1(0x5A, _0, _2, _3),
	DUP_X2(0x5B, _0, _3, _4),
	DUP2(0x5C, _0, _2, _4),
	DUP2_X1(0x5D, _0, _3, _5),
	DUP2_X2(0x5E, _0, _4, _6),
	F2D(0x8D, _0, _1, _2),
	F2I(0x8B, _0, _1, _1),
	F2L(0x8C, _0, _1, _2),
	FADD(0x62, _0, _2, _1),
	FALOAD(0x30, _0, _2, _1),
	FASTORE(0x51, _0, _3, _0),
	FCMPG(0x96, _0, _2, _1),
	FCMPL(0x95, _0, _2, _1),
	FCONST_0(0x0B, _0, _0, _1),
	FCONST_1(0x0C, _0, _0, _1),
	FCONST_2(0x0D, _0, _0, _1),
	FDIV(0x6E, _0, _2, _1),
	FLOAD(0x17, new Inc(0), _0, _1, new NumericArgument(1)),
	FLOAD_0(0x22, _1, _0, _1),
	FLOAD_1(0x23, _2, _0, _1),
	FLOAD_2(0x24, _3, _0, _1),
	FLOAD_3(0x25, _4, _0, _1),
	FMUL(0x6A, _0, _2, _1),
	FNEG(0x76, _0, _1, _1),
	FREM(0x72, _0, _2, _1),
	FRETURN(0xAE, _0, _1, _0),
	FSTORE(0x38, new Inc(0), _1, _0, new NumericArgument(1)),
	FSTORE_0(0x43, _1, _1, _0),
	FSTORE_1(0x44, _2, _1, _0),
	FSTORE_2(0x45, _3, _1, _0),
	FSTORE_3(0x46, _4, _1, _0),
	FSUB(0x66, _0, _2, _1),
	GETFIELD(0xB4, _0, _1, _1, new ConstantPoolArgument()),
	GETSTATIC(0xB2, _0, _0, _1, new ConstantPoolArgument()),
	GOTO(0xA7, _0, _0, _0, new CallOffsetArgument()),
	GOTO_W(0xC8, _0, _0, _0, new CallOffsetArgument().asWideArgument()),
	I2B(0x91, _0, _1, _1),
	I2C(0x92, _0, _1, _1),
	I2D(0x87, _0, _1, _2),
	I2F(0x86, _0, _1, _1),
	I2L(0x85, _0, _1, _2),
	I2S(0x93, _0, _1, _1),
	IADD(0x60, _0, _2, _1),
	IALOAD(0x2E, _0, _2, _1),
	IAND(0x7E, _0, _2, _1),
	IASTORE(0x4F, _0, _3, _0),
	ICONST_M1(0x02, _0, _0, _1),
	ICONST_0(0x03, _0, _0, _1),
	ICONST_1(0x04, _0, _0, _1),
	ICONST_2(0x05, _0, _0, _1),
	ICONST_3(0x06, _0, _0, _1),
	ICONST_4(0x07, _0, _0, _1),
	ICONST_5(0x08, _0, _0, _1),
	IDIV(0x6C, _0, _2, _1),
	IF_ACMPEQ(0xA5, _0, _2, _0, new CallOffsetArgument()),
	IF_ACMPNE(0xA6, _0, _2, _0, new CallOffsetArgument()),
	IF_ICMPEQ(0x9F, _0, _2, _0, new CallOffsetArgument()),
	IF_ICMPNE(0xA0, _0, _2, _0, new CallOffsetArgument()),
	IF_ICMPLT(0xA1, _0, _2, _0, new CallOffsetArgument()),
	IF_ICMPGE(0xA2, _0, _2, _0, new CallOffsetArgument()),
	IF_ICMPGT(0xA3, _0, _2, _0, new CallOffsetArgument()),
	IF_ICMPLE(0xA4, _0, _2, _0, new CallOffsetArgument()),
	IFEQ(0x99, _0, _1, _0, new CallOffsetArgument()),
	IFNE(0x9A, _0, _1, _0, new CallOffsetArgument()),
	IFLT(0x9B, _0, _1, _0, new CallOffsetArgument()),
	IFGE(0x9C, _0, _1, _0, new CallOffsetArgument()),
	IFGT(0x9D, _0, _1, _0, new CallOffsetArgument()),
	IFLE(0x9E, _0, _1, _0, new CallOffsetArgument()),
	IFNONNULL(0xC7, _0, _1, _0, new CallOffsetArgument()),
	IFNULL(0xC6, _0, _1, _0, new CallOffsetArgument()),
	IINC(0x84, new CallArgument(0), _0, _0, new NumericArgument(1), new NumericArgument(1)),
	ILOAD(0x15, new Inc(0), _0, _1, new NumericArgument(1)),
	ILOAD_0(0x1A, _1, _0, _1),
	ILOAD_1(0x1B, _2, _0, _1),
	ILOAD_2(0x1C, _3, _0, _1),
	ILOAD_3(0x1D, _4, _0, _1),
	IMUL(0x68, _0, _2, _1),
	INEG(0x74, _0, _1, _1),
	INSTANCEOF(0xC1, _0, _1, _1, new ConstantPoolArgument()),
	INVOKEDYNAMIC(0xBA, _0, new Send("getArgumentPopCount", 0), new Send("getReturnCount", 0), new InvokeDynamicArgument(0)),
	INVOKEINTERFACE(0xB9, _0, new Send("getArgumentPopCount", 0), new Send("getReturnCount", 0), new InterfaceMethodArgument(0)),
	INVOKESPECIAL(0xB7, _0, new Send("getArgumentPopCount", 0), new Send("getReturnCount", 0), new ConstantPoolArgument()),
	INVOKESTATIC(0xB8, _0, new Send("getArgumentCount", 0), new Send("getReturnCount", 0), new ConstantPoolArgument()),
	INVOKEVIRTUAL(0xB6, _0, new Send("getArgumentPopCount", 0), new Send("getReturnCount", 0), new ConstantPoolArgument()),
	IOR(0x80, _0, _2, _1),
	IREM(0x70, _0, _2, _1),
	IRETURN(0xAC, _0, _1, _0),
	ISHL(0x78, _0, _2, _1),
	ISHR(0x7A, _0, _2, _1),
	ISTORE(0x36, new Inc(0), _1, _0, new NumericArgument(1)),
	ISTORE_0(0x3B, _1, _1, _0),
	ISTORE_1(0x3C, _2, _1, _0),
	ISTORE_2(0x3D, _3, _1, _0),
	ISTORE_3(0x3E, _4, _1, _0),
	ISUB(0x64, _0, _2, _1),
	IUSHR(0x7C, _0, _2, _1),
	IXOR(0x82, _0, _2, _1),
	JSR(0xA8, _0, _0, _1, new CallOffsetArgument()),
	JSR_W(0xC9, _0, _0, _1, new CallOffsetArgument().asWideArgument()),
	L2D(0x8A, _0, _2, _2),
	L2F(0x89, _0, _2, _1),
	L2I(0x88, _0, _2, _1),
	LADD(0x61, _0, _4, _2),
	LALOAD(0x2F, _0, _2, _2),
	LAND(0x7F, _0, _4, _2),
	LASTORE(0x50, _0, _4, _0),
	LCMP(0x94, _0, _4, _1),
	LCONST_0(0x09, _0, _0, _2),
	LCONST_1(0x0A, _0, _0, _2),
	LDC(0x12, _0, _0, _1, new ConstantPoolArgument(1)),
	LDC_W(0x13, _0, _0, _1, new ConstantPoolArgument()),
	LDC2_W(0x14, _0, _0, _2, new ConstantPoolArgument()),
	LDIV(0x6D, _0, _4, _2),
	LLOAD(0x16, new Inc(0, 2), _0, _2, new NumericArgument(1)),
	LLOAD_0(0x1E, _2, _0, _2),
	LLOAD_1(0x1F, _3, _0, _2),
	LLOAD_2(0x20, _4, _0, _2),
	LLOAD_3(0x21, _5, _0, _2),
	LMUL(0x69, _0, _4, _2),
	LNEG(0x75, _0, _2, _2),
	LOOKUPSWITCH(0xAB, _0, _1, _0, new LookupSwitchArgument()),
	LOR(0x81, _0, _4, _2),
	LREM(0x71, _0, _4, _2),
	LRETURN(0xAD, _0, _2, _0),
	LSHL(0x79, _0, _3, _2),
	LSHR(0x7B, _0, _3, _2),
	LSTORE(0x37, new Inc(0, 2), _2, _0, new NumericArgument(1)),
	LSTORE_0(0x3F, _2, _2, _0),
	LSTORE_1(0x40, _3, _2, _0),
	LSTORE_2(0x41, _4, _2, _0),
	LSTORE_3(0x42, _5, _2, _0),
	LSUB(0x65, _0, _4, _2),
	LUSHR(0x7D, _0, _3, _2),
	LXOR(0x83, _0, _4, _2),
	MONITORENTER(0xC2, _0, _1, _0),
	MONITOREXIT(0xC3, _0, _1, _0),
	MULTIANEWARRAY(0xC5, _0, new CallArgument(1), _1, new ConstantPoolArgument(), new NumericArgument(1)),
	NEW(0xBB, _0, _0, _1, new ConstantPoolArgument()),
	NEWARRAY(0xBC, _0, _1, _1, new NumericArgument(1)),
	NOP(0x00, _0, _0, _0),
	POP(0x57, _0, _1, _0),
	POP2(0x58, _0, _2, _0),
	PUTFIELD(0xB5, _0, _2, _0, new ConstantPoolArgument()),
	PUTSTATIC(0xB3, _0, _1, _0, new ConstantPoolArgument()),
	RET(0xA9, new CallArgument(0), _0, _0, new NumericArgument(1)),
	RETURN(0xB1, _0, _0, _0),
	SALOAD(0x35, _0, _2, _1),
	SASTORE(0x56, _0, _3, _0),
	SIPUSH(0x11, _0, _0, _1, new NumericArgument(2)),
	SWAP(0x5F, _0, _2, _2),
	TABLESWITCH(0xAA, _0, _1, _0, new TableSwitchArgument()),
	WIDE(0xC4, new Send("getMinLocalsSizeRequired", 0), new Send("getStackPopCount", 0), new Send("getStackPushCount", 0), new InstructionCallArgument(0)),
	BREAKPOINT(0xCA, _0, _0, _0),
	IMPDEP1(0xFE, _0, _0, _0),
	IMPDEP2(0xFF, _0, _0, _0);

	private int code;
	private AmountStrategy localAccessStrategy;
	private AmountStrategy popStrategy;
	private AmountStrategy pushStrategy;
	private List<ArgumentStrategy> argumentStrategies;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static BytecodeInstruction fromCode(int code) {
		for(BytecodeInstruction instruction : BytecodeInstruction.values()) {
			if(instruction.getCode() == code) {
				return instruction;
			}
		}

		throw new NoSuchElementException();
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	private BytecodeInstruction(int code, AmountStrategy localAccessStrategy, AmountStrategy popStrategy, AmountStrategy pushStrategy, ArgumentStrategy... argumentStrategies) {
		this.setCode(code);
		this.setLocalAccessStrategy(localAccessStrategy);
		this.setPopStrategy(popStrategy);
		this.setPushStrategy(pushStrategy);
		this.setArgumentStrategies(Arrays.asList(argumentStrategies));
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public int getStackPopCount(InstructionCall call) {
		return this.getPopStrategy().getAmount(call, null);
	}

	public int getStackPushCount(InstructionCall call) {
		return this.getPushStrategy().getAmount(call, null);
	}

	public int getMinLocalsSizeRequired(InstructionCall call) {
		return this.getLocalAccessStrategy().getAmount(call, null);
	}

	public int getArgumentByteCount(InstructionCall call) {
		int answer = 0;

		for(ArgumentStrategy argument : this.getArgumentStrategies()) {
			answer += argument.getArgumentByteCount(call);
		}

		return answer;
	}

	public byte[] getArgumentBytes(InstructionCall call, ConstantPool constantPool, InstructionCallContext callContext) {
		byte[] answer = new byte[0];

		for(int i = 0; i < this.getArgumentStrategies().size(); i++) {
			answer = append(answer, this.getArgumentStrategies().get(i).getArgumentBytes(call, callContext, i, constantPool));
		}

		return answer;
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeByte(this.getCode());
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void fillConstantPool(ConstantPool constantPool, InstructionCall call) {
		for(ArgumentStrategy argument : this.getArgumentStrategies()) {
			argument.fillConstantPool(call, constantPool);
		}
	}

	// TODO: Este método no tiene que estar acá. En todo caso que retorne su parser...
	public InstructionCall parseCall(ExtendedDataInputStream input, int firstCallCodeOffset, ConstantPool constantPool) {
		Object[] args = new Object[0];

		for(ArgumentStrategy argument : this.getArgumentStrategies()) {
			args = append(args, argument.parseCall(input, this, firstCallCodeOffset, constantPool).getArguments());
		}

		return new SimpleInstructionCall(this, args);
	}

	// TODO: Este método no tiene derecho a existir.
	public InstructionCall parseWideCall(ExtendedDataInputStream input, BytecodeInstruction innerInstruction, int firstCallCodeOffset, ConstantPool constantPool) {
		Object[] args = new Object[0];

		for(ArgumentStrategy argument : this.getArgumentStrategies()) {
			args = append(args, argument
				.asWideArgument()
				.parseCall(input, this, firstCallCodeOffset, constantPool)
				.getArguments());
		}

		return new SimpleInstructionCall(this, args);
	}

	public void adaptParsedCalls(InstructionCall call, InstructionCallContext callContext, ConstantPool constantPool) {
		for(int i = 0; i < this.getArgumentStrategies().size(); i++) {
			this.getArgumentStrategies().get(i).adaptParsedCalls(call, callContext, i, constantPool);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getCode() {
		return this.code;
	}

	protected void setCode(int code) {
		this.code = code;
	}

	protected AmountStrategy getLocalAccessStrategy() {
		return this.localAccessStrategy;
	}

	protected void setLocalAccessStrategy(AmountStrategy localAccessStrategy) {
		this.localAccessStrategy = localAccessStrategy;
	}

	protected AmountStrategy getPopStrategy() {
		return this.popStrategy;
	}

	protected void setPopStrategy(AmountStrategy popStrategy) {
		this.popStrategy = popStrategy;
	}

	protected AmountStrategy getPushStrategy() {
		return this.pushStrategy;
	}

	protected void setPushStrategy(AmountStrategy pushStrategy) {
		this.pushStrategy = pushStrategy;
	}

	protected List<ArgumentStrategy> getArgumentStrategies() {
		return this.argumentStrategies;
	}

	protected void setArgumentStrategies(List<ArgumentStrategy> argumentStrategies) {
		this.argumentStrategies = argumentStrategies;
	}
}