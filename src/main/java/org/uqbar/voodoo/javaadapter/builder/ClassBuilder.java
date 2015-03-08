package org.uqbar.voodoo.javaadapter.builder;

import static org.uqbar.voodoo.javaadapter.model.ClassVersion.JAVA_7;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromClass;
import static org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference.typeFromName;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.voodoo.javaadapter.model.Class;
import org.uqbar.voodoo.javaadapter.model.ClassVersion;
import org.uqbar.voodoo.javaadapter.model.attributes.Attribute;
import org.uqbar.voodoo.javaadapter.model.attributes.BootstrapMethodsAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.SourceFileAttribute;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ReferenceKind;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;

public class ClassBuilder {
	private static final String CONSTRUCTOR_SELECTOR = "<init>";
	private ClassVersion version;
	private TypeReference type;
	private TypeReference superType;
	private List<TypeReference> interfaces;
	private List<FieldBuilder> fields;
	private List<MethodBuilder> methods;
	private List<BootstrapBuilder> bootstraps;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	private ClassBuilder() {
		this.setFields(new ArrayList<FieldBuilder>());
		this.setMethods(new ArrayList<MethodBuilder>());
		this.setInterfaces(new ArrayList<TypeReference>());
		this.setBootstraps(new ArrayList<BootstrapBuilder>());

		this.setVersion(JAVA_7);
	}

	public ClassBuilder(String className) {
		this(className, Object.class);
	}

	public ClassBuilder(String className, java.lang.Class<?> superclass,
			java.lang.Class<?>... interfaces) {
		this(className, superclass.getName());

		this.implement(interfaces);
	}

	public ClassBuilder(String className, String superclassName,
			String... interfaceNames) {
		this();
		this.setType(typeFromName(className));
		this.setSuperType(typeFromName(superclassName));
		this.implement(interfaceNames);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	protected void ensureConstructorExists() {
		List<MethodBuilder> constructors = new ArrayList<>();
		for (MethodBuilder methodBuilder : this.getMethods())
			if (methodBuilder.getName().equals(CONSTRUCTOR_SELECTOR))
				constructors.add(0, methodBuilder);

		if (constructors.isEmpty())
			this.constructor()
					.code()
					.ALOAD_0()
					.INVOKESPECIAL(Object.class, CONSTRUCTOR_SELECTOR,
							void.class).RETURN();
	}

	// ****************************************************************
	// ** BUILD CONFIGURATION
	// ****************************************************************

	public ClassBuilder implement(java.lang.Class<?>... interfaces) {
		for (java.lang.Class<?> interfaceType : interfaces)
			this.getInterfaces().add(typeFromClass(interfaceType));

		return this;
	}

	public ClassBuilder implement(String... interfacesNames) {
		for (String interfaceName : interfacesNames)
			this.getInterfaces().add(typeFromName(interfaceName));

		return this;
	}

	public FieldBuilder field(String fieldName, java.lang.Class<?> fieldType) {
		return this.field(fieldName, false, fieldType);
	}

	public FieldBuilder field(String fieldName, boolean markedAsStatic,
			java.lang.Class<?> fieldType) {
		FieldBuilder answer = new FieldBuilder(fieldName, markedAsStatic,
				fieldType);

		this.getFields().add(answer);

		return answer;
	}

	public FieldBuilder field(String fieldName, boolean markedAsStatic,
			String fieldType) {
		FieldBuilder answer = new FieldBuilder(fieldName, markedAsStatic,
				fieldType);

		this.getFields().add(answer);

		return answer;
	}

	public MethodBuilder method(String methodName,
			java.lang.Class<?> returnType, java.lang.Class<?>... argumentTypes) {
		return this.method(methodName, false, returnType, argumentTypes);
	}

	public MethodBuilder method(String methodName, Boolean isStatic,
			java.lang.Class<?> returnType, java.lang.Class<?>... argumentTypes) {
		MethodBuilder answer = new MethodBuilder(methodName, isStatic,
				returnType, argumentTypes);

		this.getMethods().add(answer);

		return answer;
	}

	public MethodBuilder method(String methodName, String returnTypeName,
			String... argumentTypeNames) {
		return this
				.method(methodName, false, returnTypeName, argumentTypeNames);
	}

	public MethodBuilder method(String methodName, Boolean isStatic,
			String returnTypeName, String... argumentTypeNames) {
		MethodBuilder answer = new MethodBuilder(methodName, isStatic,
				returnTypeName, argumentTypeNames);

		this.getMethods().add(answer);

		return answer;
	}

	public MethodBuilder constructor(java.lang.Class<?>... argumentTypes) {
		return this.method(CONSTRUCTOR_SELECTOR, void.class, argumentTypes);
	}

	// TODO: Quiero hacer esto por separado? O quiero deducirlo en base a los
	// invokedynamic?
	// INVOKEDYNAMIC(bootstrapClass,bootstrapMethodName, methodName,
	// methodReturnType, methodArgumentTypes)?
	public ClassBuilder bootstrap(java.lang.Class<?> aClass, String methodName) {
		return this.bootstrap(ReferenceKind.INVOKESTATIC, aClass, methodName);
	}

	public ClassBuilder bootstrap(ReferenceKind referenceKind,
			java.lang.Class<?> aClass, String methodName) {
		this.getBootstraps().add(
				new BootstrapBuilder(referenceKind, typeFromClass(aClass),
						methodName));

		return this;
	}

	// ****************************************************************
	// ** BUILDING
	// ****************************************************************

	public Class build() {
		Class answer = new Class();

		answer.setType(this.getType());
		answer.setSupertype(this.getSuperType());
		answer.setVersion(this.getVersion());

		this.buildModifiers(answer);

		this.buildInterfaces(answer);

		this.buildFields(answer);

		this.buildMethods(answer);

		this.buildAttributes(answer);

		// TODO: Validar la "consistencia" de la clase parseada? Esto también
		// sería útil en el parser.
		return answer;
	}

	protected void buildModifiers(Class answer) {
	}

	protected void buildInterfaces(Class answer) {
		for (TypeReference interfaceType : this.getInterfaces())
			answer.addInterface(interfaceType);
	}

	protected void buildFields(Class answer) {
		for (FieldBuilder field : this.getFields())
			answer.addField(field.build());
	}

	protected void buildMethods(Class answer) {
		this.ensureConstructorExists();

		for (MethodBuilder method : this.getMethods())
			answer.addMethod(method.build());
	}

	protected void buildAttributes(Class answer) {
		List<Attribute> attributes = new ArrayList<>();

		SourceFileAttribute source = new SourceFileAttribute();
		source.setSourceFileName(answer.getName() + ".java");
		// attributes.add(source);

		if (this.getBootstraps().size() > 0) {
			BootstrapMethodsAttribute attribute = new BootstrapMethodsAttribute();

			for (BootstrapBuilder bootstrap : this.getBootstraps())
				attribute.addBootstrapMethod(bootstrap.build());

			attributes.add(attribute);
		}

		answer.getAttributes().addAll(attributes);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public ClassVersion getVersion() {
		return this.version;
	}

	public void setVersion(ClassVersion version) {
		this.version = version;
	}

	public TypeReference getType() {
		return this.type;
	}

	protected void setType(TypeReference type) {
		this.type = type;
	}

	protected TypeReference getSuperType() {
		return this.superType;
	}

	protected void setSuperType(TypeReference superType) {
		this.superType = superType;
	}

	protected List<TypeReference> getInterfaces() {
		return this.interfaces;
	}

	protected void setInterfaces(List<TypeReference> interfaces) {
		this.interfaces = interfaces;
	}

	protected List<FieldBuilder> getFields() {
		return this.fields;
	}

	protected void setFields(List<FieldBuilder> fields) {
		this.fields = fields;
	}

	protected List<MethodBuilder> getMethods() {
		return this.methods;
	}

	protected void setMethods(List<MethodBuilder> methods) {
		this.methods = methods;
	}

	protected List<BootstrapBuilder> getBootstraps() {
		return this.bootstraps;
	}

	protected void setBootstraps(List<BootstrapBuilder> bootstraps) {
		this.bootstraps = bootstraps;
	}
}