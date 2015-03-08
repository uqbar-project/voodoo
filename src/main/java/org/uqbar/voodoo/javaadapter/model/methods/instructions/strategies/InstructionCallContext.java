package org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies;

import java.util.List;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

//TODO: Eliminar. Que el call pueda responder a todo esto.
public class InstructionCallContext {

	private List<InstructionCall> calls;
	private int firstCallOffset;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public InstructionCallContext(List<InstructionCall> calls, int firstCallOffset) {
		this.setCalls(calls);
		this.setFirstCallOffset(firstCallOffset);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public int getOffsetDelta(InstructionCall from, InstructionCall to) {
		int fromOffset = 0;
		int toOffset = 0;
		int nextCallOffset = 0;
		for(InstructionCall call : this.getCalls()) {
			if(call == from) {
				fromOffset = nextCallOffset;
			}
			if(call == to) {
				toOffset = nextCallOffset;
			}

			nextCallOffset += call.getByteCount();
		}

		return toOffset - fromOffset;
	}

	public int getAbsoluteOffset(InstructionCall call) {
		int nextCallOffset = this.getFirstCallOffset();
		for(InstructionCall aCall : this.getCalls()) {
			if(aCall == call) {
				return nextCallOffset;
			}

			nextCallOffset += aCall.getByteCount();
		}

		throw new RuntimeException("No such call: " + call);
	}

	public int getCodeOffset(InstructionCall call) {
		int nextCallOffset = 0;
		for(InstructionCall aCall : this.getCalls()) {
			if(aCall == call) {
				return nextCallOffset;
			}

			nextCallOffset += aCall.getByteCount();
		}

		throw new RuntimeException("No such call: " + call);
	}

	public InstructionCall getCallAtCodeOffset(int codeOffset) {
		return this.getCallAtOffset(this.getCalls().get(0), codeOffset);
	}

	public InstructionCall getCallAtOffset(InstructionCall origin, int offsetDelta) {
		int offset = this.getAbsoluteOffset(origin) + offsetDelta;
		int nextCallOffset = this.getFirstCallOffset();
		for(InstructionCall call1 : this.getCalls()) {
			if(nextCallOffset == offset) {
				return call1;
			}

			nextCallOffset += call1.getByteCount();
		}

		throw new RuntimeException("Invalid offset: " + offset);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void replace(InstructionCall oldCall, InstructionCall newCall) {
		this.getCalls().set(this.getCalls().indexOf(oldCall), newCall);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected List<InstructionCall> getCalls() {
		return this.calls;
	}

	protected void setCalls(List<InstructionCall> calls) {
		this.calls = calls;
	}

	protected int getFirstCallOffset() {
		return this.firstCallOffset;
	}

	protected void setFirstCallOffset(int firstCallOffset) {
		this.firstCallOffset = firstCallOffset;
	}
}