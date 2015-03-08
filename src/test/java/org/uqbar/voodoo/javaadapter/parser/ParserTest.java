//package org.uqbar.voodoo.javaadapter.parser;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//import org.uqbar.voodoo.javaadapter.model.Class;
//import org.uqbar.voodoo.javaadapter.model.Field;
//import org.uqbar.voodoo.javaadapter.model.attributes.CodeAttribute;
//import org.uqbar.voodoo.javaadapter.model.attributes.SourceFileAttribute;
//import org.uqbar.voodoo.javaadapter.model.attributes.code.LineNumberTableAttribute;
//import org.uqbar.voodoo.javaadapter.model.methods.Method;
//import org.uqbar.voodoo.javaadapter.model.methods.instructions.BytecodeInstruction;
//import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.SlotReference;
//import org.uqbar.voodoo.javaadapter.model.modifiers.MetaType;
//import org.uqbar.voodoo.javaadapter.model.modifiers.Visibility;
//import org.uqbar.voodoo.javaadapter.parser.ClassParser;
//
////TODO: Este test es horrible. Usar equals en lugar de abrir para ver el contenido.
//public class ParserTest {
//	@Test
//	public void testParseFooBytecode() {
//		Class fooClass = new ClassParser(this.getClass()
//				.getResource("/Foo.class").getPath()).parse();
//
//		assertEquals(0, fooClass.getVersion().getMinor());
//		assertEquals(51, fooClass.getVersion().getMajor());
//
//		assertEquals(Visibility.PUBLIC, fooClass.getModifier().getVisibility());
//
//		assertEquals(MetaType.CLASS, fooClass.getModifier().getMetaType());
//		assertTrue(fooClass.getModifier().isMarkedAsSuper());
//		assertFalse(fooClass.getModifier().isMarkedAsAbstract());
//		assertFalse(fooClass.getModifier().isMarkedAsFinal());
//		assertFalse(fooClass.getModifier().isMarkedAsStatic());
//		assertFalse(fooClass.getModifier().isMarkedAsSynthetic());
//
//		assertEquals("Foo", fooClass.getName());
//		assertEquals("java/lang/Object", fooClass.getSupertype().getName());
//
//		assertEquals(0, fooClass.getInterfaces().size());
//
//		assertEquals(1, fooClass.getFields().size());
//
//		Field barField = fooClass.getFields().get(0);
//		assertEquals("bar", barField.getName());
//		assertEquals("Ljava/lang/String;", barField.getType().getDescriptor());
//		assertEquals(0, barField.getAttributes().size());
//		assertEquals(Visibility.PRIVATE, barField.getModifier().getVisibility());
//		assertFalse(barField.getModifier().isMarkedAsEnum());
//		assertFalse(barField.getModifier().isMarkedAsFinal());
//		assertFalse(barField.getModifier().isMarkedAsStatic());
//		assertFalse(barField.getModifier().isMarkedAsSynthetic());
//		assertFalse(barField.getModifier().isMarkedAsTransient());
//		assertFalse(barField.getModifier().isMarkedAsVolatile());
//
//		assertEquals(3, fooClass.getMethods().size());
//
//		Method initMethod = fooClass.getMethods().get(0);
//		assertEquals("<init>", initMethod.getName());
//		assertEquals("()V", initMethod.getDescriptor());
//		assertEquals(Visibility.PUBLIC, initMethod.getModifier()
//				.getVisibility());
//		assertFalse(initMethod.getModifier().isMarkedAsAbstract());
//		assertFalse(initMethod.getModifier().isMarkedAsBridge());
//		assertFalse(initMethod.getModifier().isMarkedAsFinal());
//		assertFalse(initMethod.getModifier().isMarkedAsNative());
//		assertFalse(initMethod.getModifier().isMarkedAsStatic());
//		assertFalse(initMethod.getModifier().isMarkedAsStrict());
//		assertFalse(initMethod.getModifier().isMarkedAsSynchronized());
//		assertFalse(initMethod.getModifier().isMarkedAsSynthetic());
//		assertFalse(initMethod.getModifier().isMarkedAsVarargs());
//		assertEquals(1, initMethod.getAttributes().size());
//		assertEquals(CodeAttribute.class, initMethod.getAttributes().get(0)
//				.getClass());
//		CodeAttribute initMethodCode = (CodeAttribute) initMethod
//				.getAttributes().get(0);
//		assertEquals("Code", initMethodCode.getLabel());
//		assertEquals(0, initMethodCode.getExceptions().size());
//		assertEquals(1, initMethodCode.getMaxLocals());
//		assertEquals(1, initMethodCode.getMaxStack());
//		assertEquals(3, initMethodCode.getCalls().size());
//		assertEquals(BytecodeInstruction.ALOAD_0, initMethodCode.getCalls()
//				.get(0).getInstruction());
//		assertEquals(0, initMethodCode.getCalls().get(0).getArguments().length);
//		assertEquals(BytecodeInstruction.INVOKESPECIAL, initMethodCode
//				.getCalls().get(1).getInstruction());
//		assertEquals(1, initMethodCode.getCalls().get(1).getArguments().length);
//		assertEquals("java/lang/Object", initMethodCode.getCalls().get(1)
//				.<SlotReference> getArgument(0).getOwner().getName());
//		assertEquals("<init>", initMethodCode.getCalls().get(1)
//				.<SlotReference> getArgument(0).getName());
//		assertEquals("()V", initMethodCode.getCalls().get(1)
//				.<SlotReference> getArgument(0).getDescriptor());
//		assertEquals(BytecodeInstruction.RETURN,
//				initMethodCode.getCalls().get(2).getInstruction());
//		assertEquals(0, initMethodCode.getCalls().get(2).getArguments().length);
//		assertEquals(1, initMethodCode.getAttributes().size());
//		assertEquals(LineNumberTableAttribute.class, initMethodCode
//				.getAttributes().get(0).getClass());
//		assertEquals(
//				"LineNumberTable",
//				((LineNumberTableAttribute) initMethodCode.getAttributes().get(
//						0)).getLabel());
//		assertEquals(1, ((LineNumberTableAttribute) initMethodCode
//				.getAttributes().get(0)).getLineNumbers().size());
//		assertEquals(1, ((LineNumberTableAttribute) initMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(0)
//				.getLineNumber());
//		assertEquals(0, ((LineNumberTableAttribute) initMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(0).getStartPC());
//
//		Method getMethod = fooClass.getMethods().get(1);
//		assertEquals("getBar", getMethod.getName());
//		assertEquals("()Ljava/lang/String;", getMethod.getDescriptor());
//		assertEquals(Visibility.PUBLIC, getMethod.getModifier().getVisibility());
//		assertFalse(getMethod.getModifier().isMarkedAsAbstract());
//		assertFalse(getMethod.getModifier().isMarkedAsBridge());
//		assertFalse(getMethod.getModifier().isMarkedAsFinal());
//		assertFalse(getMethod.getModifier().isMarkedAsNative());
//		assertFalse(getMethod.getModifier().isMarkedAsStatic());
//		assertFalse(getMethod.getModifier().isMarkedAsStrict());
//		assertFalse(getMethod.getModifier().isMarkedAsSynchronized());
//		assertFalse(getMethod.getModifier().isMarkedAsSynthetic());
//		assertFalse(getMethod.getModifier().isMarkedAsVarargs());
//		assertEquals(1, getMethod.getAttributes().size());
//		assertEquals(CodeAttribute.class, getMethod.getAttributes().get(0)
//				.getClass());
//		CodeAttribute getMethodCode = (CodeAttribute) getMethod.getAttributes()
//				.get(0);
//		assertEquals("Code", getMethodCode.getLabel());
//		assertEquals(0, getMethodCode.getExceptions().size());
//		assertEquals(1, getMethodCode.getMaxLocals());
//		assertEquals(1, getMethodCode.getMaxStack());
//		assertEquals(3, getMethodCode.getCalls().size());
//		assertEquals(BytecodeInstruction.ALOAD_0,
//				getMethodCode.getCalls().get(0).getInstruction());
//		assertEquals(0, getMethodCode.getCalls().get(0).getArguments().length);
//		assertEquals(BytecodeInstruction.GETFIELD, getMethodCode.getCalls()
//				.get(1).getInstruction());
//		assertEquals(1, getMethodCode.getCalls().get(1).getArguments().length);
//		assertEquals("Foo", getMethodCode.getCalls().get(1)
//				.<SlotReference> getArgument(0).getOwner().getName());
//		assertEquals("bar", getMethodCode.getCalls().get(1)
//				.<SlotReference> getArgument(0).getName());
//		assertEquals("Ljava/lang/String;", getMethodCode.getCalls().get(1)
//				.<SlotReference> getArgument(0).getDescriptor());
//		assertEquals(BytecodeInstruction.ARETURN,
//				getMethodCode.getCalls().get(2).getInstruction());
//		assertEquals(0, getMethodCode.getCalls().get(2).getArguments().length);
//		assertEquals(1, getMethodCode.getAttributes().size());
//		assertEquals(LineNumberTableAttribute.class, getMethodCode
//				.getAttributes().get(0).getClass());
//		assertEquals("LineNumberTable",
//				((LineNumberTableAttribute) getMethodCode.getAttributes()
//						.get(0)).getLabel());
//		assertEquals(1, ((LineNumberTableAttribute) getMethodCode
//				.getAttributes().get(0)).getLineNumbers().size());
//		assertEquals(6, ((LineNumberTableAttribute) getMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(0)
//				.getLineNumber());
//		assertEquals(0, ((LineNumberTableAttribute) getMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(0).getStartPC());
//
//		Method setMethod = fooClass.getMethods().get(2);
//		assertEquals("setBar", setMethod.getName());
//		assertEquals("(Ljava/lang/String;)V", setMethod.getDescriptor());
//		assertEquals(Visibility.PUBLIC, setMethod.getModifier().getVisibility());
//		assertFalse(setMethod.getModifier().isMarkedAsAbstract());
//		assertFalse(setMethod.getModifier().isMarkedAsBridge());
//		assertFalse(setMethod.getModifier().isMarkedAsFinal());
//		assertFalse(setMethod.getModifier().isMarkedAsNative());
//		assertFalse(setMethod.getModifier().isMarkedAsStatic());
//		assertFalse(setMethod.getModifier().isMarkedAsStrict());
//		assertFalse(setMethod.getModifier().isMarkedAsSynchronized());
//		assertFalse(setMethod.getModifier().isMarkedAsSynthetic());
//		assertFalse(setMethod.getModifier().isMarkedAsVarargs());
//		assertEquals(1, setMethod.getAttributes().size());
//		assertEquals(CodeAttribute.class, setMethod.getAttributes().get(0)
//				.getClass());
//		CodeAttribute setMethodCode = (CodeAttribute) setMethod.getAttributes()
//				.get(0);
//		assertEquals("Code", setMethodCode.getLabel());
//		assertEquals(0, setMethodCode.getExceptions().size());
//		assertEquals(2, setMethodCode.getMaxLocals());
//		assertEquals(2, setMethodCode.getMaxStack());
//		assertEquals(4, setMethodCode.getCalls().size());
//		assertEquals(BytecodeInstruction.ALOAD_0,
//				setMethodCode.getCalls().get(0).getInstruction());
//		assertEquals(0, setMethodCode.getCalls().get(0).getArguments().length);
//		assertEquals(BytecodeInstruction.ALOAD_1,
//				setMethodCode.getCalls().get(1).getInstruction());
//		assertEquals(0, setMethodCode.getCalls().get(1).getArguments().length);
//		assertEquals(BytecodeInstruction.PUTFIELD, setMethodCode.getCalls()
//				.get(2).getInstruction());
//		assertEquals(1, setMethodCode.getCalls().get(2).getArguments().length);
//		assertEquals("Foo", setMethodCode.getCalls().get(2)
//				.<SlotReference> getArgument(0).getOwner().getName());
//		assertEquals("bar", setMethodCode.getCalls().get(2)
//				.<SlotReference> getArgument(0).getName());
//		assertEquals("Ljava/lang/String;", setMethodCode.getCalls().get(2)
//				.<SlotReference> getArgument(0).getDescriptor());
//		assertEquals(BytecodeInstruction.RETURN, setMethodCode.getCalls()
//				.get(3).getInstruction());
//		assertEquals(0, setMethodCode.getCalls().get(3).getArguments().length);
//		assertEquals(1, setMethodCode.getAttributes().size());
//		assertEquals(LineNumberTableAttribute.class, setMethodCode
//				.getAttributes().get(0).getClass());
//		assertEquals("LineNumberTable",
//				((LineNumberTableAttribute) setMethodCode.getAttributes()
//						.get(0)).getLabel());
//		assertEquals(2, ((LineNumberTableAttribute) setMethodCode
//				.getAttributes().get(0)).getLineNumbers().size());
//		assertEquals(10, ((LineNumberTableAttribute) setMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(0)
//				.getLineNumber());
//		assertEquals(0, ((LineNumberTableAttribute) setMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(0).getStartPC());
//		assertEquals(11, ((LineNumberTableAttribute) setMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(1)
//				.getLineNumber());
//		assertEquals(5, ((LineNumberTableAttribute) setMethodCode
//				.getAttributes().get(0)).getLineNumbers().get(1).getStartPC());
//
//		assertEquals(1, fooClass.getAttributes().size());
//		assertEquals(SourceFileAttribute.class, fooClass.getAttributes().get(0)
//				.getClass());
//		assertEquals("SourceFile", ((SourceFileAttribute) fooClass
//				.getAttributes().get(0)).getLabel());
//		assertEquals("Foo.java", ((SourceFileAttribute) fooClass
//				.getAttributes().get(0)).getSourceFileName());
//	}
// }
