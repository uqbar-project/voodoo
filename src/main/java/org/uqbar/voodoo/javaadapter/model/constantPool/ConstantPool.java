package org.uqbar.voodoo.javaadapter.model.constantPool;

import static org.uqbar.voodoo.javaadapter.model.constantPool.NumberEntry.DOUBLE_TAG;
import static org.uqbar.voodoo.javaadapter.model.constantPool.NumberEntry.FLOAT_TAG;
import static org.uqbar.voodoo.javaadapter.model.constantPool.NumberEntry.INTEGER_TAG;
import static org.uqbar.voodoo.javaadapter.model.constantPool.NumberEntry.LONG_TAG;
import static org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry.INTERFACE_METHOD_TAG;
import static org.uqbar.voodoo.javaadapter.model.constantPool.SlotEntry.METHOD_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.BootstrapMethod;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ReferenceKind;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.DataConstantReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.InvokeDynamicReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;
import org.uqbar.voodoo.javaadapter.streams.ExtendedDataOutputStream;

public class ConstantPool {

	private List<ConstantPoolEntry> entries;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public ConstantPool() {
		this.setEntries(new ArrayList<ConstantPoolEntry>());
		this.getEntries().add(BlankEntry.INSTANCE);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	// TODO: Reemplazar por api de alto nivel? Quitarle el type? indexOfClass(String content) ? Conviene?, etc...
	public <T extends ConstantPoolEntry> int indexOf(String content, Class<T> type) {
		int i = 0;
		for(ConstantPoolEntry entry : this.getEntries()) {
			if(entry.getClass().equals(type) && entry.contains(content)) {
				return i;
			}
			i++;
		}

		throw new NoSuchElementException();
	}

	// TODO: Esto debería reemplazar al indexOf?
	// TODO: Renombrar
	public <T extends ConstantPoolEntry> int indexOfContent(Object content) {
		int answer = 0;
		for(ConstantPoolEntry entry : this.getEntries()) {
			if(entry.containsContent(content)) {
				return answer;
			}
			answer++;
		}

		throw new NoSuchElementException();
	}

	public int getByteCount() {
		int entriesByteCount = 0;
		for(ConstantPoolEntry entry : this.getEntries()) {
			entriesByteCount += entry.getByteCount();
		}

		return 2 + entriesByteCount;
	}

	// ****************************************************************
	// ** ACCESS OPERATIONS
	// ****************************************************************

	// TODO: Reemplazar por get que devuelva el realcontent, con generic.
	@SuppressWarnings("unchecked")
	public <T extends ConcreteEntry> T get(int index) {
		return (T) this.getEntries().get(index);
	}

	public int pushEntry(ConcreteEntry entry) {
		int answer = this.getEntries().indexOf(entry);

		if(answer < 1) {
			answer = this.getEntries().size();

			this.getEntries().add(entry);

			if(entry.requiresPadding()) {
				this.getEntries().add(BlankEntry.INSTANCE);
			}
		}

		return answer;
	}

	// TODO: No sería mejor un ConstantPool de más alto nivel? Sin entries, solo con los objetos constantes.
	// TODO: Polimorfismo
	public int push(Object constant) {
		if(constant instanceof String) {
			return this.pushUTF8((String) constant);
		}
		if(constant instanceof TypeReference) {
			return this.pushClass((TypeReference) constant);
		}
		if(constant instanceof InvokeDynamicReference) {
			return this.pushInvokeDynamic((InvokeDynamicReference) constant);
		}
		if(constant instanceof SlotReference) {
			return this.pushSlot((SlotReference) constant);
		}
		if(constant instanceof BootstrapMethod) {
			return this.pushMethodHandle((BootstrapMethod) constant);
		}
		if(constant instanceof DataConstantReference) {
			return this.pushDataConstant((DataConstantReference) constant);
		}

		throw new RuntimeException("Constant " + constant + " is not a valid constant!");
	}

	protected int pushUTF8(String text) {
		return this.pushEntry(new UTF8Entry(this, text));
	}

	protected int pushClass(TypeReference type) {
		return this.pushEntry(new ClassEntry(this, this.push(type.getName())));
	}

	protected int pushInvokeDynamic(InvokeDynamicReference invokeDynamic) {
		return this.pushEntry(new InvokeDynamicEntry(this, invokeDynamic.getBootstrapMethodIndex(), this
			.pushEntry(new SignatureEntry(this, this.push(invokeDynamic
				.getName()), this.push(invokeDynamic.getDescriptor())))));
	}

	protected int pushSlot(SlotReference slot) {
		return this.pushEntry(new SlotEntry(this, slot.getTag(),
			this.push(slot.getOwner()),
			this.pushEntry(new SignatureEntry(this, this.push(slot.getName()), this.push(slot.getDescriptor())))
			));
	}

	protected int pushMethodHandle(BootstrapMethod bootstrapMethod) {
		ReferenceKind referenceKind = bootstrapMethod.getReferenceKind();
		String className = bootstrapMethod.getOwner().getName();
		String selector = bootstrapMethod.getName();
		String descriptor = bootstrapMethod.getMethodDescriptor();

		int slotIndex;
		switch(referenceKind) {
			case GETFIELD:
			case GETSTATIC:
			case PUTFIELD:
			case PUTSTATIC:
				slotIndex = this.push(SlotReference.field(className, selector, descriptor));
				break;
			case INVOKEINTERFACE:
				slotIndex = this
					.pushEntry(new SlotEntry(this, INTERFACE_METHOD_TAG, this.push(new TypeReference(className)), this
						.pushEntry(new SignatureEntry(this, this.push(selector), this.push(descriptor)))));
				break;
			default:
				slotIndex = this.pushEntry(new SlotEntry(this, METHOD_TAG, this.push(new TypeReference(className)), this
					.pushEntry(new SignatureEntry(this, this.push(selector), this.push(descriptor)))));
		}

		return this.pushEntry(new MethodHandlerEntry(this, referenceKind, slotIndex));
	}

	protected int pushDataConstant(DataConstantReference constantReference) {
		Object constant = constantReference.getConstant();

		if(constant instanceof String) {
			return this.pushEntry(new StringEntry(this, this.push(constant)));
		}
		else {
			int tag = 0;

			if(constant instanceof Integer) {
				tag = INTEGER_TAG;
			}
			if(constant instanceof Float) {
				tag = FLOAT_TAG;
			}
			if(constant instanceof Long) {
				tag = LONG_TAG;
			}
			if(constant instanceof Double) {
				tag = DOUBLE_TAG;
			}

			return this.pushEntry(new NumberEntry(this, tag, (Number) constant));
		}
	}

	// ****************************************************************
	// ** BYTECODE WRITING
	// ****************************************************************

	public void writeTo(ExtendedDataOutputStream output) {
		output.writeShort(this.getEntries().size());

		for(ConstantPoolEntry entry : this.getEntries()) {
			entry.writeTo(output);
		}
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	protected List<ConstantPoolEntry> getEntries() {
		return this.entries;
	}

	protected void setEntries(List<ConstantPoolEntry> entries) {
		this.entries = entries;
	}
}