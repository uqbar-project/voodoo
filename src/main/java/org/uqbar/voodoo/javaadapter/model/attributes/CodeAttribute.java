package org.uqbar.voodoo.javaadapter.model.attributes;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.ClassVersion;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ExceptionHandler;
import org.uqbar.voodoo.javaadapter.model.attributes.code.CodeAttributeAttribute;
import org.uqbar.voodoo.javaadapter.model.constantPool.ConstantPool;
import org.uqbar.voodoo.javaadapter.model.methods.Method;
import org.uqbar.voodoo.javaadapter.model.methods.StackMapCalculator;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.InstructionCall;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.strategies.InstructionCallContext;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class CodeAttribute extends Attribute {

	private int maxStack;
	private int maxLocals;
	private List<InstructionCall> calls;
	private List<ExceptionHandler> exceptions;
	private List<CodeAttributeAttribute> attributes;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public CodeAttribute() {
		super("Code");
		this.setCalls(new ArrayList<InstructionCall>());
		this.setAttributes(new ArrayList<CodeAttributeAttribute>());
		this.setExceptions(new ArrayList<ExceptionHandler>());
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void fillConstantPool(ConstantPool constantPool) {
		super.fillConstantPool(constantPool);

		for(InstructionCall call : this.getCalls()) {
			call.fillConstantPool(constantPool);
		}

		for(CodeAttributeAttribute attribute : this.getAttributes()) {
			attribute.fillConstantPool(constantPool);
		}
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	@Override
	public int getByteCount() {
		int attributeCount = 0;
		for(CodeAttributeAttribute attribute : this.getAttributes()) {
			attributeCount += 6 + attribute.getByteCount();
		}

		return 2 + 2
				+ 4 + this.getCallsByteCount()
				+ 2 + this.getExceptions().size() * 8
				+ 2 + attributeCount;
	}

	protected int getCallsByteCount() {
		int answer = 0;

		for(InstructionCall call : this.getCalls()) {
			answer += call.getByteCount();
		}

		return answer;
	}

	public int getCallAbsoluteOffset(InstructionCall call) {
		int firstCallAbsoluteOffset = this.getFirstCallAbsoluteOffset();

		int callsOffsetDelta = 0;
		for(int i = 0; i < this.getCalls().indexOf(call); i++) {
			callsOffsetDelta += this.getCalls().get(i).getByteCount();
		}

		return firstCallAbsoluteOffset + callsOffsetDelta;
	}

	public int getFirstCallAbsoluteOffset() {
		return this.getOwner().getAttributeAbsoluteOffset(this) + 2 + 2 + 4;
	}

	@Override
	public String toString() {
		return "Code of: " + this.getOwner() + " " + this.getCalls();
	}

	// TODO: Y si separo los atributos por posible owner? Y si los atributos son absorbidos por los modelos?
	@Override
	public Method getOwner() {
		return (Method) super.getOwner();
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	@Override
	protected void doWriteTo(ExtendedDataOutputStream output, ConstantPool constantPool) {
		output.writeShort(this.getMaxStack());
		output.writeShort(this.getMaxLocals());

		int firstCallOffset = output.getWrited() + 4;
		InstructionCallContext callContext = new InstructionCallContext(this.getCalls(), firstCallOffset);
		this.writeCalls(output, callContext, constantPool);

		this.writeExceptionHandlers(output);

		this.writeAttributes(output, callContext, constantPool);
	}

	public void ensureIsReadyToWrite() {
		if(this.getAttributes().size() < 1 && this.getOwner().getOwner().getVersion().isBasedOn(ClassVersion.JAVA_7)) {
			StackMapCalculator stackCalculator = new StackMapCalculator(this);
			stackCalculator.iterate(this.getCalls());

			this.addAttribute(stackCalculator.getStackMap());
		}
	}

	protected void writeCalls(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeInt(this.getCallsByteCount());

		for(InstructionCall call : this.getCalls()) {
			call.writeTo(output, callContext, constantPool);
		}
	}

	protected void writeExceptionHandlers(ExtendedDataOutputStream output) {
		output.writeShort(this.getExceptions().size());
		for(ExceptionHandler handler : this.getExceptions()) {
			handler.writeTo(output);
		}
	}

	protected void writeAttributes(ExtendedDataOutputStream output, InstructionCallContext callContext, ConstantPool constantPool) {
		output.writeShort(this.getAttributes().size());

		for(CodeAttributeAttribute attribute : this.getAttributes()) {
			attribute.writeTo(output, callContext, constantPool);
		}
	}

	// ****************************************************************
	// ** COMPARING
	// ****************************************************************

	@Override
	public boolean equals(Object object) {

		if(object.getClass() != this.getClass()) {
			return false;
		}

		CodeAttribute target = (CodeAttribute) object;

		return this.getCalls().equals(target.getCalls())
				&& this.getMaxLocals() == target.getMaxLocals()
				&& this.getMaxStack() == target.getMaxStack()
				&& this.getExceptions().size() == target.getExceptions().size()
				&& this.getExceptions().containsAll(target.getExceptions())
				&& this.getAttributes().size() == target.getAttributes().size()
				&& this.getAttributes().containsAll(target.getAttributes());
	}

	@Override
	public int hashCode() {
		return this.getCalls().hashCode();
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	public void addAttribute(CodeAttributeAttribute attribute) {
		this.getAttributes().add(attribute);
	}

	public void addException(ExceptionHandler exception) {
		this.getExceptions().add(exception);
	}

	public void addCalls(List<InstructionCall> calls) {
		for(InstructionCall call : calls) {
			this.addCall(call);
		}
	}

	public void addCall(InstructionCall call) {
		this.getCalls().add(call);
		call.setOwner(this);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	// TODO: Calcular usando el stackMapAttribute
	public int getMaxStack() {
		return this.maxStack;
	}

	public void setMaxStack(int maxStack) {
		this.maxStack = maxStack;
	}

	// TODO: Calcular usando el stackMapAttribute
	public int getMaxLocals() {
		return this.maxLocals;
	}

	public void setMaxLocals(int maxLocals) {
		this.maxLocals = maxLocals;
	}

	public List<InstructionCall> getCalls() {
		return this.calls;
	}

	protected void setCalls(List<InstructionCall> calls) {
		this.calls = calls;
	}

	public List<ExceptionHandler> getExceptions() {
		return this.exceptions;
	}

	public void setExceptions(List<ExceptionHandler> exceptions) {
		this.exceptions = exceptions;
	}

	public List<CodeAttributeAttribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<CodeAttributeAttribute> attributes) {
		this.attributes = attributes;
	}
}