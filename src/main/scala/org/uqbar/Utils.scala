package org.uqbar

import scala.language.implicitConversions
import scala.language.reflectiveCalls

object Utils {
	protected type Closeable = { def close() }

	protected class AutoCloseable[T <: Closeable](closeable : T) {
		def foreach(f : T ⇒ Unit) {
			f(closeable)
			closeable.close
		}
	}

	implicit def autoClose[T <: Closeable](closeable : T) : AutoCloseable[T] = new AutoCloseable(closeable)

}