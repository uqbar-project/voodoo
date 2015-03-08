package org.uqbar.voodoo.javaadapter.model.methods;

import static org.uqbar.voodoo.javaadapter.BytecodeToolsUtils.append;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.uqbar.voodoo.javaadapter.builder.CodeVisitor;
import org.uqbar.voodoo.javaadapter.model.attributes.Attribute;
import org.uqbar.voodoo.javaadapter.model.attributes.BootstrapMethodsAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.BootstrapMethod;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ReferenceKind;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.FullFrame;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.ObjectVariableInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.SimpleVerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.UninitializedVariableInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.VerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.code.StackMapTableAttribute;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.DataConstantReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.InvokeDynamicReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;

public class StackMapCalculator extends CodeVisitor {

	private CodeAttribute codeAttribute;
	private Map<Integer, FullFrame> frames;
	private FullFrame lastFrame;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	// TODO: este método va acá?
	protected static VerificationTypeInfo verificationTypeInfoFromTypeReference(TypeReference type) {
		String typeName = type.getName();

		if(typeName.equals(int.class.getName())
				|| typeName.equals(short.class.getName())
				|| typeName.equals(boolean.class.getName())
				|| typeName.equals(char.class.getName())) {
			return SimpleVerificationTypeInfo.INTEGER;
		}
		if(typeName.equals(float.class.getName())) {
			return SimpleVerificationTypeInfo.FLOAT;
		}
		if(typeName.equals(long.class.getName())) {
			return SimpleVerificationTypeInfo.LONG;
		}
		if(typeName.equals(double.class.getName())) {
			return SimpleVerificationTypeInfo.DOUBLE;
		}

		return new ObjectVariableInfo(type);
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public StackMapCalculator(CodeAttribute codeAttribute) {
		this.codeAttribute = codeAttribute;

		Method owner = codeAttribute.getOwner();

		List<TypeReference> localTypes = owner.getArgumentTypes();
		String methodName = owner.getName();

		VerificationTypeInfo[] locals = new VerificationTypeInfo[localTypes.size()];
		for(int i = 0; i < locals.length; i++) {
			locals[i] = verificationTypeInfoFromTypeReference(localTypes.get(i));
		}

		if(!owner.getModifier().isMarkedAsStatic()) {
			locals = append(new VerificationTypeInfo[] { methodName.equals("<init>")
				? SimpleVerificationTypeInfo.UNINITIALIZED_THIS
				: verificationTypeInfoFromTypeReference(owner.getOwner().getType())
			}, locals);
		}

		this.lastFrame = new FullFrame(0, new VerificationTypeInfo[0], locals);

		this.frames = new HashMap<>();
		this.frames.put(0, this.lastFrame);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public StackMapTableAttribute getStackMap() {
		StackMapTableAttribute answer = new StackMapTableAttribute();

		for(int i = 0; i < this.frames.size() - 1; i++) {
			answer.addFrame(this.frames.get(i));
		}

		return answer;
	}

	protected FullFrame getNextFrame(InstructionCall call, FullFrame last) {
		FullFrame next = new FullFrame(0, last.getStack(), last.getLocals());
		next.setOffsetDelta(call.getByteCount() - 1);

		int localIndex;
		Class<?> constantType;
		SlotReference slot;

		switch(call.getInstruction()) {
			case AALOAD:
				next.popStack(2);
				ObjectVariableInfo arrayStack = (ObjectVariableInfo) last.peekStack(1);
				next.pushStack(new ObjectVariableInfo(arrayStack.getType().unarrayed()));
				break;
			case IALOAD:
			case BALOAD:
			case CALOAD:
			case SALOAD:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;
			case FALOAD:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				break;
			case DALOAD:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				break;
			case LALOAD:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.LONG);
				break;

			case AASTORE:
			case BASTORE:
			case CASTORE:
			case IASTORE:
			case FASTORE:
			case DASTORE:
			case LASTORE:
			case SASTORE:
				next.popStack(3);
				break;

			case ACONST_NULL:
				next.pushStack(SimpleVerificationTypeInfo.NULL);
				break;

			case ALOAD:
				next.pushStack(last.getLocal(call.<Integer>getArgument(0)));
				break;
			case ILOAD:
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;
			case DLOAD:
				next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				break;
			case FLOAD:
				next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				break;
			case LLOAD:
				next.pushStack(SimpleVerificationTypeInfo.LONG);
				break;

			case ALOAD_0:
				next.pushStack(last.getLocal(0));
				break;
			case ALOAD_1:
				next.pushStack(last.getLocal(1));
				break;
			case ALOAD_2:
				next.pushStack(last.getLocal(2));
				break;
			case ALOAD_3:
				next.pushStack(last.getLocal(3));
				break;

			case ILOAD_0:
			case ILOAD_1:
			case ILOAD_2:
			case ILOAD_3:
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;
			case DLOAD_0:
			case DLOAD_1:
			case DLOAD_2:
			case DLOAD_3:
				next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				break;
			case FLOAD_0:
			case FLOAD_1:
			case FLOAD_2:
			case FLOAD_3:
				next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				break;
			case LLOAD_0:
			case LLOAD_1:
			case LLOAD_2:
			case LLOAD_3:
				next.pushStack(SimpleVerificationTypeInfo.LONG);
				break;

			case ANEWARRAY:
				// TODO: hacer esto una sola vez: next.removeStack(call.getPopCount());
				next.popStack(1);
				next.pushStack(verificationTypeInfoFromTypeReference(call.<TypeReference>getArgument(0).arrayed()));
				break;

			case ARETURN:
			case IRETURN:
			case FRETURN:
			case DRETURN:
			case LRETURN:
				next.popStack(1);
				break;

			case ARRAYLENGTH:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;

			case ASTORE:
			case DSTORE:
			case FSTORE:
			case ISTORE:
			case LSTORE:
				localIndex = call.<Integer>getArgument(0);
				next.updateLocal(localIndex, last.peekStack(0));
				next.popStack(1);

				break;
			case ASTORE_0:
			case DSTORE_0:
			case FSTORE_0:
			case ISTORE_0:
			case LSTORE_0:
				localIndex = 0;
				next.updateLocal(localIndex, last.peekStack(0));
				next.popStack(1);
				break;
			case ASTORE_1:
			case DSTORE_1:
			case FSTORE_1:
			case ISTORE_1:
			case LSTORE_1:
				localIndex = 1;
				next.updateLocal(localIndex, last.peekStack(0));
				next.popStack(1);
				break;
			case ASTORE_2:
			case DSTORE_2:
			case FSTORE_2:
			case ISTORE_2:
			case LSTORE_2:
				localIndex = 2;
				next.updateLocal(localIndex, last.peekStack(0));
				next.popStack(1);
				break;
			case ASTORE_3:
			case DSTORE_3:
			case FSTORE_3:
			case ISTORE_3:
			case LSTORE_3:
				localIndex = 3;
				next.updateLocal(localIndex, last.peekStack(0));
				next.popStack(1);
				break;

			case CHECKCAST:
				next.popStack(1);
				next.pushStack(verificationTypeInfoFromTypeReference(call.<TypeReference>getArgument(0)));
				break;

			case D2F:
			case I2F:
			case L2F:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				break;
			case D2I:
			case F2I:
			case L2I:
			case I2B:
			case I2C:
			case I2S:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;
			case D2L:
			case F2L:
			case I2L:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.LONG);
				break;
			case I2D:
			case F2D:
			case L2D:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				break;
			case DCONST_0:
			case DCONST_1:
				next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				break;
			case DNEG:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				break;
			case DADD:
			case DSUB:
			case DDIV:
			case DMUL:
			case DREM:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				break;
			case DCMPG:
			case DCMPL:
			case FCMPG:
			case FCMPL:
			case LCMP:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;
			case DUP:
			case DUP2:
			case DUP2_X2:
				next.pushStack(last.peekStack(0));
				break;
			case DUP_X1:
			case DUP2_X1:
				next.popStack(2);
				next.pushStack(last.peekStack(0));
				next.pushStack(last.peekStack(1));
				next.pushStack(last.peekStack(0));
				break;
			case DUP_X2:
				next.popStack(3);
				next.pushStack(last.peekStack(0));
				next.pushStack(last.peekStack(2));
				next.pushStack(last.peekStack(1));
				next.pushStack(last.peekStack(0));
				break;

			case FADD:
			case FDIV:
			case FMUL:
			case FREM:
			case FSUB:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				break;

			case FCONST_0:
			case FCONST_1:
			case FCONST_2:
				next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				break;
			case FNEG:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				break;
			case GETFIELD:
				next.popStack(1);
			case GETSTATIC:
				next.pushStack(verificationTypeInfoFromTypeReference(call.<SlotReference>getArgument(0).getTypes().get(0)));
				break;

			case GOTO:
			case GOTO_W:
			case IINC:
			case IMPDEP1:
			case IMPDEP2:
			case INEG:
			case ATHROW:
			case BREAKPOINT:
			case NOP:
			case RET:
			case RETURN:
				break;

			case IADD:
			case IMUL:
			case IREM:
			case IDIV:
			case ISUB:
			case IAND:
			case IOR:
			case ISHL:
			case ISHR:
			case IUSHR:
			case IXOR:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;

			case BIPUSH:
			case SIPUSH:
			case ICONST_0:
			case ICONST_1:
			case ICONST_2:
			case ICONST_3:
			case ICONST_4:
			case ICONST_5:
			case ICONST_M1:
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;

			case IFEQ:
			case IFGE:
			case IFGT:
			case IFLE:
			case IFLT:
			case IFNE:
			case IFNONNULL:
			case IFNULL:
				next.popStack(1);
				break;
			case IF_ACMPEQ:
			case IF_ACMPNE:
			case IF_ICMPEQ:
			case IF_ICMPGE:
			case IF_ICMPGT:
			case IF_ICMPLE:
			case IF_ICMPLT:
			case IF_ICMPNE:
				next.popStack(2);
				break;

			case INSTANCEOF:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				break;

			case INVOKEDYNAMIC:
				InvokeDynamicReference invDyn = call.getArgument(0);

				BootstrapMethodsAttribute bootstrapTable = null;
				for(Attribute attribute : this.codeAttribute.getOwner().getOwner().getAttributes()) {
					if(attribute instanceof BootstrapMethodsAttribute) {
						bootstrapTable = (BootstrapMethodsAttribute) attribute;
						break;
					}
				}
				BootstrapMethod bootstrap = bootstrapTable.getBootstrapMethods().get(invDyn.getBootstrapMethodIndex());

				next.popStack(invDyn.getArgumentCount()
						+ (bootstrap.getReferenceKind().equals(ReferenceKind.INVOKESTATIC) ? 0 : 1));

				if(invDyn.getReturnCount() > 0) {
					next.pushStack(verificationTypeInfoFromTypeReference(invDyn.getReturnType()));
				}

				break;

			case INVOKESTATIC:
				slot = call.getArgument(0);

				next.popStack(slot.getArgumentCount());

				if(slot.getReturnCount() > 0) {
					next.pushStack(verificationTypeInfoFromTypeReference(slot.getReturnType()));
				}

				break;

			case INVOKEVIRTUAL:
				slot = call.getArgument(0);

				next.popStack(slot.getArgumentPopCount());

				if(slot.getReturnCount() > 0) {
					next.pushStack(verificationTypeInfoFromTypeReference(slot.getReturnType()));
				}

				break;

			case INVOKEINTERFACE:
				slot = call.getArgument(0);

				next.popStack(slot.getArgumentPopCount());

				if(slot.getReturnCount() > 0) {
					next.pushStack(verificationTypeInfoFromTypeReference(slot.getReturnType()));
				}

				break;

			case INVOKESPECIAL:
				slot = call.getArgument(0);

				next.popStack(slot.getArgumentPopCount());

				// TODO: Hace falta preguntar? Creo que no pasa nunca...
				if(slot.getReturnCount() > 0) {
					next.pushStack(verificationTypeInfoFromTypeReference(slot.getReturnType()));
				}

				// TODO: Extraer método en FullFrame?
				VerificationTypeInfo arg = last.peekStack(0);
				for(int i = 0; i < next.getStack().size(); i++) {
					VerificationTypeInfo stack = next.getStack().get(i);
					if(arg.equals(stack)) {
						VerificationTypeInfo newStack = verificationTypeInfoFromTypeReference(
								call.<SlotReference>getArgument(0).getOwner()
								);
						next.getStack().set(i, newStack);
					}
				}

				next.updateLocal(0, verificationTypeInfoFromTypeReference(
					this.codeAttribute.getOwner().getOwner().getType()
					));

				break;
			case JSR:
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);

				break;
			case JSR_W:
				next.pushStack(SimpleVerificationTypeInfo.INTEGER);

				break;
			case LADD:
			case LSUB:
			case LDIV:
			case LMUL:
			case LREM:
			case LOR:
			case LAND:
			case LXOR:
			case LSHL:
			case LSHR:
			case LUSHR:
				next.popStack(2);
				next.pushStack(SimpleVerificationTypeInfo.LONG);
				break;

			case LCONST_0:
			case LCONST_1:
				next.pushStack(SimpleVerificationTypeInfo.LONG);
				break;

			case LDC:
			case LDC_W:
				constantType = call.<DataConstantReference>getArgument(0).getConstant().getClass();
				if(constantType.equals(String.class)) {
					next.pushStack(new ObjectVariableInfo(String.class.getName()));
				}
				else if(constantType.equals(Integer.class)) {
					next.pushStack(SimpleVerificationTypeInfo.INTEGER);
				}
				else {
					next.pushStack(SimpleVerificationTypeInfo.FLOAT);
				}
				break;

			case LDC2_W:
				constantType = call.<DataConstantReference>getArgument(0).getConstant().getClass();
				if(constantType.equals(Long.class)) {
					next.pushStack(SimpleVerificationTypeInfo.LONG);
				}
				else {
					next.pushStack(SimpleVerificationTypeInfo.DOUBLE);
				}
				break;

			case LNEG:
				next.popStack(1);
				next.pushStack(SimpleVerificationTypeInfo.LONG);
				break;

			case LOOKUPSWITCH:
			case TABLESWITCH:
			case MONITORENTER:
			case MONITOREXIT:
			case POP:
				next.popStack(1);
				break;

			case MULTIANEWARRAY:
				next.popStack(call.<Integer>getArgument(1));
				next.pushStack(verificationTypeInfoFromTypeReference(call.<TypeReference>getArgument(0)));
				break;

			case NEW:
				next.pushStack(new UninitializedVariableInfo(call));
				break;

			case NEWARRAY:
				next.popStack(1);
				int typeCode = call.<Integer>getArgument(0);

				TypeReference type = null;
				switch(typeCode) {
					case 4:
						type = typeFromClass(boolean.class);
						break;
					case 5:
						type = typeFromClass(char.class);
						break;
					case 6:
						type = typeFromClass(float.class);
						break;
					case 7:
						type = typeFromClass(double.class);
						break;
					case 8:
						type = typeFromClass(byte.class);
						break;
					case 9:
						type = typeFromClass(short.class);
						break;
					case 10:
						type = typeFromClass(int.class);
						break;
					case 11:
						type = typeFromClass(long.class);
						break;
				}
				next.pushStack(verificationTypeInfoFromTypeReference(type.arrayed()));
				break;

			case POP2:
				next.popStack(last.peekStack(0).equals(SimpleVerificationTypeInfo.LONG)
						|| last.peekStack(0).equals(SimpleVerificationTypeInfo.DOUBLE) ? 1 : 2);
				break;

			case PUTFIELD:
				next.popStack(2);
				break;

			case PUTSTATIC:
				next.popStack(1);
				break;

			case SWAP:
				next.popStack(2);
				next.pushStack(last.peekStack(0));
				next.pushStack(last.peekStack(1));
				break;

			case WIDE:
				next = this.getNextFrame(call.<InstructionCall>getArgument(0), last);
				next.setOffsetDelta(next.getOffsetDelta() * 2 + 1);
				break;
		}
		return next;
	}

	@Override
	protected CodeVisitor split() {
		StackMapCalculator answer = (StackMapCalculator) super.split();

		answer.frames = new HashMap<>(this.frames);

		return answer;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	protected void visit(InstructionCall call, int callIndex) {
		FullFrame nextFrame = this.getNextFrame(call, this.lastFrame);
		this.frames.put(callIndex + 1, nextFrame);
		this.lastFrame = nextFrame;
	}

	@Override
	protected void mergeWith(CodeVisitor c) {
		StackMapCalculator other = (StackMapCalculator) c;

		for(Entry<Integer, FullFrame> entry : other.frames.entrySet()) {
			this.frames.put(entry.getKey(), entry.getValue());
		}
	}
}