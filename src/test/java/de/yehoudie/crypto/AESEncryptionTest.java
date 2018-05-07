package de.yehoudie.crypto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author yehoudie
 *
 */
public class AESEncryptionTest
{
	/**
	 * Test method for {@link de.yehoudie.utils.Math2#randRange(double, double)}.
	 */
	@Test
	public void testStringEncryption()
	{
		String value = "Ich bin der Teststring zum verschüsseln ;).";
		String password = "Sehr geheimes Passwort.";

		AESEncryption aes = new AESEncryption();
		byte[] salt = aes.generateSalt();
		byte[] iv = aes.generateInitialVector();

		byte[] enc_bytes = null;
		try
		{
			enc_bytes = aes.encrypt(value, password, salt, iv);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		byte[] dec_bytes;
		String dec_value = null;
		try
		{
			dec_bytes = aes.decrypt(enc_bytes, password, salt, iv);
			dec_value = new String(dec_bytes);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
//		System.out.println(" -     value: "+value);
//		System.out.println(" - dec_value: "+dec_value);
//		System.out.println(" - value.equals(dec_value): "+value.equals(dec_value));
		
		assertTrue(value.equals(dec_value));
	}
}
