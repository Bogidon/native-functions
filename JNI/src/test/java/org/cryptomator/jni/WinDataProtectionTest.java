package org.cryptomator.jni;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class WinDataProtectionTest {
	
	@Ignore
	@Test
	public void testKeychainAccess() {
		if (!SystemUtils.IS_OS_WINDOWS) {
			Assert.fail();
		}
		WinDataProtection dataProtection = WinFunctions.loadWinFunctions().get().dataProtection();

		String storedPw = "h€llo wørld123";
		byte[] ciphertext = dataProtection.protect(storedPw.getBytes(), "salt".getBytes());
		Assert.assertNotNull(ciphertext);
		
		byte[] shouldBeNull = dataProtection.unprotect(ciphertext, "pepper".getBytes());
		Assert.assertNull(shouldBeNull);
		
		byte[] cleartext = dataProtection.unprotect(ciphertext, "salt".getBytes());
		Assert.assertArrayEquals(storedPw.getBytes(), cleartext);
	}

}
