import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class CustomBootstrap {
	public static CallSite dispatch(Lookup callerClass, String dynMethodName, MethodType dynMethodType) throws Throwable {
		MethodHandle mh =
				callerClass.findStatic(
					Bleh.class,
					"adder",
					MethodType.methodType(Integer.class, Integer.class, Integer.class));

		if(!dynMethodType.equals(mh.type())) {
			mh = mh.asType(dynMethodType);
		}

		return new ConstantCallSite(mh);
	}
}