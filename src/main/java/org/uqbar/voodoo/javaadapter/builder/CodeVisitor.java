package org.uqbar.voodoo.javaadapter.builder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;

public abstract class CodeVisitor implements Cloneable {

	private Set<Integer> visited = new HashSet<>();

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	protected int getIndexOf(InstructionCall target, List<InstructionCall> calls) {
		int answer = 0;

		while(calls.get(answer) != target) {
			answer++;
		}

		return answer;
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void iterate(List<InstructionCall> calls) {
		this.iterateFrom(0, calls);
	}

	protected void iterateFrom(int index, List<InstructionCall> calls) {
		if(index < calls.size() && this.getVisited().add(index)) {

			InstructionCall call = calls.get(index);

			this.visit(call, index);

			switch(call.getInstruction()) {
				case IF_ACMPEQ:
				case IF_ACMPNE:
				case IF_ICMPEQ:
				case IF_ICMPNE:
				case IF_ICMPLT:
				case IF_ICMPGE:
				case IF_ICMPGT:
				case IF_ICMPLE:
				case IFEQ:
				case IFNE:
				case IFLT:
				case IFGE:
				case IFGT:
				case IFLE:
				case IFNONNULL:
				case IFNULL:
					CodeVisitor copy = this.split();
					copy.iterateFrom(this.getIndexOf(call.<InstructionCall>getArgument(0), calls), calls);
					this.mergeWith(copy);

					this.iterateFrom(index + 1, calls);

					break;
				case GOTO:
				case GOTO_W:
				case JSR:
				case JSR_W:
					this.iterateFrom(this.getIndexOf(call.<InstructionCall>getArgument(0), calls), calls);
					break;
				case TABLESWITCH:
					CodeVisitor defaultBranch = this.split();
					defaultBranch.iterateFrom(this.getIndexOf(call.<InstructionCall>getArgument(0), calls), calls);
					this.mergeWith(defaultBranch);

					for(int i = 2; i < call.getArguments().length; i++) {
						CodeVisitor branch = this.split();
						branch.iterateFrom(this.getIndexOf(call.<InstructionCall>getArgument(i), calls), calls);
						this.mergeWith(branch);
					}
					break;
				case LOOKUPSWITCH:
					for(int i = 0; i < call.getArguments().length; i += 2) {
						CodeVisitor branch = this.split();
						branch.iterateFrom(this.getIndexOf(call.<InstructionCall>getArgument(i), calls), calls);
						this.mergeWith(branch);
					}
					break;
				case RET:
					// TODO: buscar el JSR correspondiente y retomar desde ahí.
					break;
				case RETURN:
				case ARETURN:
				case IRETURN:
				case FRETURN:
				case LRETURN:
				case DRETURN:
					break;
				default:
					this.iterateFrom(index + 1, calls);
					break;
			}
		}
	}

	protected CodeVisitor split() {
		try {
			return (CodeVisitor) this.clone();
		}
		catch(CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract void visit(InstructionCall call, int callIndex);

	protected abstract void mergeWith(CodeVisitor other);

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected Set<Integer> getVisited() {
		return this.visited;
	}

	protected void setVisited(Set<Integer> visited) {
		this.visited = visited;
	}

}
