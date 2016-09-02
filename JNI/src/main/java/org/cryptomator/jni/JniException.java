package org.cryptomator.jni;

import java.util.function.Consumer;

/**
 * Thrown to indicate that a JNI call didn't succeed, i.e. returned an unexpected return value.
 */
public class JniException extends RuntimeException {

	protected JniException(String message) {
		super(message);
	}

	public static <T> Consumer<T> ignore(Consumer<T> consumer) {
		return value -> {
			try {
				consumer.accept(value);
			} catch (JniException e) {
				// no-op
			}
		};
	}

}
