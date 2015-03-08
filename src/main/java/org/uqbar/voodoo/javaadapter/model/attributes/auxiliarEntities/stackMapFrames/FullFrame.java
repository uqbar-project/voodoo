package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.SimpleVerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.VerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class FullFrame extends StackMapFrame {

	private int offsetDelta;
	private List<VerificationTypeInfo> locals;
	private List<VerificationTypeInfo> stack;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	protected int oversize(List<VerificationTypeInfo> types, int limit) {
		int answer = 0;

		int maxIndex = Math.min(limit, types.size());
		for(int index = 0; index < maxIndex; index++) {
			VerificationTypeInfo local = this.getLocals().get(index);
			if(local.equals(SimpleVerificationTypeInfo.DOUBLE) || local.equals(SimpleVerificationTypeInfo.LONG)) {
				answer++;
			}
		}

		return answer;
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public FullFrame(int offsetDelta) {
		super(255);

		this.setOffsetDelta(offsetDelta);
		this.setStack(new ArrayList<VerificationTypeInfo>());
		this.setLocals(new ArrayList<VerificationTypeInfo>());
	}

	public FullFrame(int offsetDelta, VerificationTypeInfo[] stack, VerificationTypeInfo... locals) {
		this(offsetDelta, Arrays.asList(stack), Arrays.asList(locals));
	}

	public FullFrame(int offsetDelta, List<VerificationTypeInfo> stack, List<VerificationTypeInfo> locals) {
		this(offsetDelta);
		this.setStack(new ArrayList<>(stack));
		this.setLocals(new ArrayList<>(locals));
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int answer = 2;

		answer += 2;
		for(VerificationTypeInfo verificationType : this.getLocals()) {
			answer += verificationType.getByteCount();

		}

		answer += 2;
		for(VerificationTypeInfo verificationType : this.getStack()) {
			answer += verificationType.getByteCount();
		}

		return answer;
	}

	@Override
	public String toString() {
		return "(" + this.getOffsetDelta() + ":" + this.getStack() + "|" + this.getLocals() + ")";
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		FullFrame target = (FullFrame) object;

		return this.getOffsetDelta() == target.getOffsetDelta()
				&& this.getLocals().equals(target.getLocals())
				&& this.getStack().equals(target.getStack());
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		for(VerificationTypeInfo local : this.getLocals()) {
			local.fillConstantPool(constantPool);
		}

		for(VerificationTypeInfo stackEntry : this.getStack()) {
			stackEntry.fillConstantPool(constantPool);
		}
	}

	// ****************************************************************
	// ** BYTECODE WRITER
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getOffsetDelta());

		output.writeShort(this.getLocals().size());
		for(VerificationTypeInfo verificationType : this.getLocals()) {
			verificationType.writeTo(output, callContext, constantPool);
		}

		output.writeShort(this.getStack().size());
		for(VerificationTypeInfo verificationType : this.getStack()) {
			verificationType.writeTo(output, callContext, constantPool);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public VerificationTypeInfo getLocal(int index) {
		return this.getLocals().get(index - this.oversize(this.getLocals(), index));
	}

	public VerificationTypeInfo peekStack(int jumps) {
		int targetIndex = this.getStack().size() - 1 - jumps;

		return this.getStack().get(targetIndex - this.oversize(this.getStack(), targetIndex));
	}

	public void addLocal(VerificationTypeInfo local) {
		this.getLocals().add(local);
	}

	public void updateLocal(int localIndex, VerificationTypeInfo newLocalValue) {
		int oversize = this.oversize(this.getLocals(), localIndex);

		if(this.getLocals().size() + oversize > localIndex) {
			this.getLocals().set(localIndex - oversize, newLocalValue);
		}
		else {
			for(int i = this.getLocals().size() + oversize; i < localIndex; i++) {
				this.addLocal(SimpleVerificationTypeInfo.TOP);
			}

			this.addLocal(newLocalValue);
		}
	}

	public void pushStack(VerificationTypeInfo stack) {
		this.getStack().add(stack);
	}

	public void popStack(int count) {
		while(count-- > 0) {
			this.getStack().remove(this.getStack().size() - 1);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getOffsetDelta() {
		return this.offsetDelta;
	}

	public void setOffsetDelta(int offsetDelta) {
		this.offsetDelta = offsetDelta;
	}

	public List<VerificationTypeInfo> getLocals() {
		return this.locals;
	}

	public void setLocals(List<VerificationTypeInfo> locals) {
		this.locals = locals;
	}

	public List<VerificationTypeInfo> getStack() {
		return this.stack;
	}

	public void setStack(List<VerificationTypeInfo> stack) {
		this.stack = stack;
	}
}
