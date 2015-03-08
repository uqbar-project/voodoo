package org.uqbar.voodoo.javaadapter.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uqbar.voodoo.builder.auxiliars.CustomBootstrap;
import org.uqbar.voodoo.javaadapter.BytecodeClassLoader;
import org.uqbar.voodoo.javaadapter.builder.auxiliars.Blah;
import org.uqbar.voodoo.javaadapter.model.Class;
import org.uqbar.voodoo.javaadapter.model.modifiers.Visibility;

public class ClassBuilderTest {
	@Test
	public void testSimpleBean() throws Exception {
		ClassBuilder builder = new ClassBuilder("SimpleBean");

		builder.field("field1", String.class);
		builder.field("field2", int.class);

		builder.method("getField1", String.class).code().ALOAD_0()
				.GETFIELD(builder.getType().getName(), "field1", String.class)
				.ARETURN();

		builder.method("setField1", void.class, String.class).code().ALOAD_0()
				.ALOAD_1()
				.PUTFIELD(builder.getType().getName(), "field1", String.class)
				.RETURN();

		builder.method("getField2", int.class).code().ALOAD_0()
				.GETFIELD(builder.getType().getName(), "field2", int.class)
				.IRETURN();

		builder.method("setField2", void.class, int.class).code().ALOAD_0()
				.ILOAD_1()
				.PUTFIELD(builder.getType().getName(), "field2", int.class)
				.RETURN();

		java.lang.Class<?> aClass = new BytecodeClassLoader()
				.importClass(builder.build());
		Object instance = aClass.newInstance();

		assertEquals(0, aClass.getMethod("getField2").invoke(instance));
		aClass.getMethod("setField2", Integer.TYPE).invoke(instance, 5);
		assertEquals(5, aClass.getMethod("getField2").invoke(instance));

		assertEquals(null, aClass.getMethod("getField1").invoke(instance));
		aClass.getMethod("setField1", String.class).invoke(instance, "Hola");
		assertEquals("Hola", aClass.getMethod("getField1").invoke(instance));
	}

	@Test
	public void testIfClause() throws Exception {
		ClassBuilder builder = new ClassBuilder("Conditional");

		builder.method("min", int.class, int.class, int.class).code().ILOAD_1() /* A */
		.ILOAD_2() /* B */
		.IF_ICMPGE("else").ILOAD_1().IRETURN().label("else").ILOAD_2()
				.IRETURN();

		java.lang.Class<?> aClass = new BytecodeClassLoader()
				.importClass(builder.build());

		Object instance = aClass.newInstance();

		assertEquals(2, aClass.getMethod("min", Integer.TYPE, Integer.TYPE)
				.invoke(instance, 2, 4));
		assertEquals(3, aClass.getMethod("min", Integer.TYPE, Integer.TYPE)
				.invoke(instance, 8, 3));
		assertEquals(5, aClass.getMethod("min", Integer.TYPE, Integer.TYPE)
				.invoke(instance, 5, 5));
	}

	@Test
	public void testWhileClause() throws Exception {
		ClassBuilder builder = new ClassBuilder("Looper");

		builder.field("count", int.class);

		builder.method("getCount", int.class).code().ALOAD_0()
				.GETFIELD(builder.getType().getName(), "count", int.class)
				.IRETURN();

		builder.method("loop", void.class, int.class).code().ICONST_0()
				.ISTORE_2().label("start").ILOAD_2().ILOAD_1()
				.IF_ICMPGE("exit").ALOAD_0().DUP()
				.GETFIELD(builder.getType().getName(), "count", int.class)
				.ICONST_1().IADD()
				.PUTFIELD(builder.getType().getName(), "count", int.class)
				.IINC(2, 1).GOTO("start").label("exit").RETURN();

		java.lang.Class<?> aClass = new BytecodeClassLoader()
				.importClass(builder.build());
		Object instance = aClass.newInstance();

		assertEquals(0, aClass.getMethod("getCount").invoke(instance));
		aClass.getMethod("loop", Integer.TYPE).invoke(instance, 12);
		assertEquals(12, aClass.getMethod("getCount").invoke(instance));
	}

	@Test
	public void testInvokeVirtual() throws Exception {
		ClassBuilder builder = new ClassBuilder("DispatchVirtual");

		builder.method("sendBleh", String.class, Blah.class).code().ALOAD_1()
				.INVOKEVIRTUAL(Blah.class, "bleh", String.class).ARETURN();

		java.lang.Class<?> aClass = new BytecodeClassLoader()
				.importClass(builder.build());

		Object instance = aClass.newInstance();

		assertEquals(
				"Hello",
				aClass.getMethod("sendBleh", Blah.class).invoke(instance,
						new Blah()));
	}

	@Test
	public void testInvokeSpecial() throws Exception {
		ClassBuilder builder = new ClassBuilder("DispatchSpecial");

		builder.method("newBlah", Blah.class).code().NEW(Blah.class).DUP()
				.INVOKESPECIAL(Blah.class, "<init>", void.class).ARETURN();

		java.lang.Class<?> aClass = new BytecodeClassLoader()
				.importClass(builder.build());
		Object instance = aClass.newInstance();

		assertTrue(aClass.getMethod("newBlah").invoke(instance) instanceof Blah);
	}

	@Test
	public void testInvokeDynamic() throws Exception {
		ClassBuilder builder = new ClassBuilder("DispatchDynamic");

		builder.method("sum", Integer.class, Integer.class, Integer.class)
				.code()
				.ALOAD_1()
				.ALOAD_2()
				.INVOKEDYNAMIC(0, "adder", Integer.class, Integer.class,
						Integer.class).ARETURN();

		builder.bootstrap(CustomBootstrap.class, "dispatch");

		java.lang.Class<?> aClass = new BytecodeClassLoader()
				.importClass(builder.build());
		Object instance = aClass.newInstance();

		assertEquals(7, aClass.getMethod("sum", Integer.class, Integer.class)
				.invoke(instance, Integer.valueOf(2), Integer.valueOf(5)));
	}

	@Test
	public void testStaticFieldAccess() throws Exception {
		String className = "StaticFielded";
		String fieldName = "blah";

		ClassBuilder builder = new ClassBuilder(className);

		builder.field(fieldName, true, int.class);

		builder.method("setBlah", true, void.class, int.class).code().ILOAD_0()
				.PUTSTATIC(className, fieldName, int.class).RETURN();

		builder.method("getBlah", true, int.class).code()
				.GETSTATIC(className, fieldName, int.class).IRETURN();

		java.lang.Class<?> aClass = new BytecodeClassLoader()
				.importClass(builder.build());

		aClass.getMethod("setBlah", int.class).invoke(null, 15);

		assertEquals(15, aClass.getMethod("getBlah").invoke(null));
	}

	@Test
	public void testStaticFieldOverrideAccess() throws Exception {
		String className = "StaticFielded";
		String fieldName = "blah";
		BytecodeClassLoader classLoader = new BytecodeClassLoader();

		ClassBuilder builder = new ClassBuilder(className);
		builder.method("getBlah", true, int.class).code()
				.GETSTATIC(className, fieldName, int.class).IRETURN();
		Class aClass = builder.build();

		classLoader.importClass(aClass);

		FieldBuilder fieldBuilder = new FieldBuilder(fieldName, true, int.class);
		fieldBuilder.getFieldModifier().setVisibility(Visibility.PUBLIC);
		aClass.addField(fieldBuilder.build());

		classLoader = new BytecodeClassLoader();
		classLoader.importClass(aClass);

		java.lang.Class<?> javaClass = java.lang.Class.forName(className, true,
				classLoader);
		javaClass.getField(fieldName).set(null, 15);

		assertEquals(15, javaClass.getMethod("getBlah").invoke(null));
	}
}