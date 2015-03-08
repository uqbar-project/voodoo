package org.uqbar.voodoo.javaadapter.builder;

import java.util.ArrayList;

import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.BootstrapMethod;
import org.uqbar.voodoo.javaadapter.model.attributes.auxiliarEntities.ReferenceKind;
import org.uqbar.voodoo.javaadapter.model.methods.instructions.arguments.TypeReference;

public class BootstrapBuilder {

	private ReferenceKind referenceKind;
	private TypeReference type;
	private String methodName;

	public BootstrapBuilder(ReferenceKind referenceKind, TypeReference type, String methodName) {
		this.referenceKind = referenceKind;
		this.type = type;
		this.methodName = methodName;
	}

	public BootstrapMethod build() {
		BootstrapMethod answer = new BootstrapMethod();

		answer.setReferenceKind(this.referenceKind);
		answer.setOwner(this.type);
		answer.setName(this.methodName);
		answer.setArgumentIndexes(new ArrayList<Integer>());

		return answer;
	}
}
