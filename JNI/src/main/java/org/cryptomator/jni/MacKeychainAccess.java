package org.cryptomator.jni;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

public class MacKeychainAccess {

	MacKeychainAccess() {
	}

	/**
	 * Associates the specified password with the specified key in the system keychain.
	 * 
	 * @param key Unique account identifier
	 * @param password Passphrase to store
	 */
	public void storePassword(String key, CharSequence password) {
		ByteBuffer pwBuf = UTF_8.encode(CharBuffer.wrap(password));
		byte[] pwBytes = new byte[pwBuf.remaining()];
		pwBuf.get(pwBytes);
		int errorCode = storePassword0(key.getBytes(UTF_8), pwBytes);
		Arrays.fill(pwBytes, (byte) 0x00);
		Arrays.fill(pwBuf.array(), (byte) 0x00);
		if (errorCode != 0) {
			throw new JniException("Failed to store password. Error code " + errorCode);
		}
	}

	private native int storePassword0(byte[] key, byte[] value);

	/**
	 * Loads the password associated with the specified key from the system keychain.
	 * 
	 * @param key Unique account identifier
	 * @return password or <code>null</code> if no such keychain entry could be loaded from the keychain.
	 */
	public char[] loadPassword(String key) {
		byte[] pwBytes = loadPassword0(key.getBytes(UTF_8));
		if (pwBytes == null) {
			return null;
		} else {
			CharBuffer pwBuf = UTF_8.decode(ByteBuffer.wrap(pwBytes));
			char[] pw = new char[pwBuf.remaining()];
			pwBuf.get(pw);
			Arrays.fill(pwBytes, (byte) 0x00);
			Arrays.fill(pwBuf.array(), (char) 0x00);
			return pw;
		}
	}

	private native byte[] loadPassword0(byte[] key);

}
