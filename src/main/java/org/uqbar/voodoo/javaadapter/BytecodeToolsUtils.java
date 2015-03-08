package org.uqbar.voodoo.javaadapter;

import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;

public class BytecodeToolsUtils {

	public static int utf8Length(CharSequence sequence) {
		int count = 0;
		for(int i = 0, len = sequence.length(); i < len; i++) {
			char ch = sequence.charAt(i);
			if(ch <= 0x7F) {
				count++;
			}
			else if(ch <= 0x7FF) {
				count += 2;
			}
			else if(Character.isHighSurrogate(ch)) {
				count += 4;
				++i;
			}
			else {
				count += 3;
			}
		}
		return count;
	}

	public static byte[] append(byte[] a, byte[] b) {
		byte[] answer = copyOf(a, a.length + b.length);

		arraycopy(b, 0, answer, answer.length - b.length, b.length);

		return answer;
	}

	public static <T> T[] append(T[] a, T[] b) {
		T[] answer = copyOf(a, a.length + b.length);

		arraycopy(b, 0, answer, answer.length - b.length, b.length);

		return answer;
	}
}