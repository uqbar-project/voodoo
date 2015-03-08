package org.uqbar.voodoo.model

package object constants {
	type LongDataType = Long with Double
	type ShortDataType = Int with Float with String
	type DataType = LongDataType with ShortDataType
}