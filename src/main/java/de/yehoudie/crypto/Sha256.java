package de.yehoudie.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Sha256 utility class.
 * 
 * @author	yehoudie
 *
 */
public class Sha256
{
	/**
	 * Hash a string in utf-8.
	 * 
	 * @param	base String the string to hash
	 * @return	String the hash a string
	 */
	public static String hash(String base)
	{
		return hash(base, "UTF-8");
	}
	
	/**
	 * Hash a string in a charset.
	 * 
	 * @param	base String the string to hash
	 * @param	charset String i.e. "UTF-8"
	 * @return	String the hash a string
	 */
	public static String hash(String base, String charset)
	{
		try
		{
			byte[] bytes = hash(base.getBytes(charset));

			return bytesToString(bytes);
		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convert hash bytes to string.
	 * 
	 * @param	bytes byte[] the bytes to convert
	 * @return	String
	 */
	protected static String bytesToString(byte[] bytes)
	{
		StringBuilder hexString = new StringBuilder();

		for ( int i = 0; i < bytes.length; i++ )
		{
//				System.out.println(hash[i]+", "+Integer.toBinaryString(hash[i])+", "+Integer.toHexString(hash[i]));
			String hex = Integer.toHexString(0xff & bytes[i]); // hex string is ffffffxx: delete leading ffffff
			if ( hex.length() == 1 ) hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	/**
	 * Hash a byte[].
	 * 
	 * @param	base byte[] the bytes to hash
	 * @return	byte[] the hashed bytes
	 */
	public static byte[] hash(byte[] base)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base);
			return hash;
		}
		catch ( Exception ex )
		{
			throw new RuntimeException(ex);
		}
	}
}
