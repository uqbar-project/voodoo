package org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames;

import java.util.Arrays;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.stackMapFrames.verificationType.VerificationTypeInfo;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class AppendFrame extends StackMapFrame {

	private int offsetDelta;
	private List<VerificationTypeInfo> locals;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public AppendFrame(int offsetDelta, VerificationTypeInfo... locals) {
		super(251 + locals.length);

		this.setOffsetDelta(offsetDelta);
		this.setLocals(Arrays.asList(locals));
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int answer = 2;

		for(VerificationTypeInfo verificationType : this.getLocals()) {
			answer += verificationType.getByteCount();
		}

		return answer;
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {
		if(!this.getClass().equals(object.getClass())) {
			return false;
		}

		AppendFrame target = (AppendFrame) object;

		return this.getType() == target.getType()
				&& this.getOffsetDelta() == target.getOffsetDelta()
				&& this.getLocals().equals(target.getLocals());
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		for(VerificationTypeInfo local : this.getLocals()) {
			local.fillConstantPool(constantPool);
		}
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getOffsetDelta());

		for(VerificationTypeInfo verificationType : this.getLocals()) {
			verificationType.writeTo(output, callContext, constantPool);
		}
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addLocal(VerificationTypeInfo local) {
		this.getLocals().add(local);
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
}