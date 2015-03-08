package org.uqbar.voodoo.builder.auxiliars

import java.lang.invoke.ConstantCallSite
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodType

class Foo
class Bleh {
	var att = 5
	def bleh = "Hello"
}
object Bleh {
	var ATT = 5
}

class CustomBootstrap {
	def dispatch(callerClass: Lookup, dynMethodName: String, dynMethodType: MethodType) = {
		var mh = callerClass.findStatic(
			classOf[Bleh],
			"adder",
			MethodType.methodType(classOf[Integer], classOf[Integer], classOf[Integer]))

		if (!dynMethodType.equals(mh.`type`)) {
			mh = mh.asType(dynMethodType)
		}

		new ConstantCallSite(mh)
	}
}