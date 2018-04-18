package de.yehoudie.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class to perform aes encryption.
 */
public class AESEncryption
{
	private static final String UTF_8 = "UTF-8";
	public static final int SALT_LENGTH = 32;
	public static final int INITIAL_VECTOR_LENGTH = 16; // 16 bytes is 128 bits. 32 bytes for 256 bits not possible
	public static final int KEY_SIZE = 256; // aes secret key size
	private static final int PSWD_ITERATIONS = 65536;
	
	private final String key_factory_algorithm = "PBKDF2WithHmacSHA1"; // PBKDF2--WithHmac--SHA1
	private final AESTransformationMode transformation;
	public AESTransformationMode getTransformationMode() { return transformation; };
//	public void setTransformationMode(AESTransformationMode value) { this.transformation = value; };
	private final String algorithm = "AES"; // en/decryption algorithm
	
	private SecureRandom random;

	/**
	 * Constructor that uses the default transformation mode: CBC.
	 */
	public AESEncryption()
	{
		this(AESTransformationMode.CIPHER_BLOCK_CHAINING);
	}
	
	/**
	 * Constructor with a transformation mode to choose.
	 * 
	 * @param	transformation AESTransformationMode
	 */
	public AESEncryption(AESTransformationMode transformation)
	{
		this.transformation = transformation;
		random = new SecureRandom();
	}
	
	/**
	 * Encrypt plain text.
	 * 
	 * @param	plain_text String the text to encrypt.
	 * @param	password String the password
	 * @param	salt byte[] the salt
	 * @param	initial_vector byte[] the initial vector, if needed
	 * @return	byte[]
	 * @throws	Exception an exception
	 */
	public byte[] encrypt(String plain_text, String password, byte[] salt, byte[] initial_vector) throws Exception
	{
		return encrypt(plain_text.getBytes(UTF_8), password, salt, initial_vector);
	}
	
	/**
	 * Encrypt a file.
	 * 
	 * @param	file File the file to encrypt.
	 * @param	password String the password
	 * @param	salt byte[] the salt
	 * @param	initial_vector byte[] the initial vector, if needed
	 * @return	byte[]
	 * @throws	Exception an exception
	 */
	public byte[] encrypt(File file, String password, byte[] salt, byte[] initial_vector) throws Exception
	{
		FileInputStream stream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        stream.read(bytes);
        stream.close();
		
		return encrypt(bytes, password, salt, initial_vector);
	}

	/**
	 * Encrypt input byte[].
	 * 
	 * @param	input byte[] the input byte array
	 * @param	password String the password
	 * @param	salt byte[] the salt
	 * @param	initial_vector byte[] the initial vector, if needed
	 * @return	byte[] the encrypted byte[]
	 * @throws	Exception an exception
	 */
	public byte[] encrypt(byte[] input, String password, byte[] salt, byte[] initial_vector) throws Exception
	{
		if ( input == null ) throw new IllegalArgumentException("Input is null!");
		if ( password == null ) throw new IllegalArgumentException("Password is null!");
		if ( salt == null ) throw new IllegalArgumentException("salt is null!");

		byte[] key = generateSecretKey(password, salt);
		
		return encrypt(input, key, initial_vector);
	}
	
	/**
	 * Encrypt input byte[].
	 * 
	 * @param	input byte[] the input byte array
	 * @param	key byte[] the key
	 * @param	initial_vector byte[] the initial vector, if needed
	 * @return	byte[] the encrypted byte[]
	 * @throws	Exception an exception
	 */
	public byte[] encrypt(byte[] input, byte[] key, byte[] initial_vector) throws Exception
	{
		SecretKeySpec secret = new SecretKeySpec(key, algorithm);

//		AlgorithmParameters params = AlgorithmParameters.getInstance(algorithm);
//		params.init(new IvParameterSpec(initial_vector));
		
		IvParameterSpec iv_param = null;
		if ( transformation != AESTransformationMode.ELECTRONIC_CODE_BOOK )
		{
			iv_param = new IvParameterSpec(initial_vector);
		}
		
		// encrypt the message
		Cipher cipher = Cipher.getInstance(transformation.toString());
		cipher.init(Cipher.ENCRYPT_MODE, secret, iv_param);
//		AlgorithmParameters params = cipher.getParameters();
//		iv_bytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encrypted = cipher.doFinal(input);
		return encrypted;
	}
	
	/**
	 * Decrypt encrypted data represented as an encrypted string.
	 * 
	 * @param	encrypted_text String the encrypted text
	 * @param	password String the password
	 * @param	salt byte[] the salt
	 * @param	initial_vector byte[] the initial vector, if needed
	 * @return	String the resulting string
	 * @throws	Exception an exception
	 */
	public String decrypt(String encrypted_text, String password, byte[] salt, byte[] initial_vector) throws Exception
	{
		return new String(decrypt(encrypted_text.getBytes(), password, salt, initial_vector));
	}

	/**
	 * Decrypt encrypted byte array.
	 * 
	 * @param	encrypted_bytes byte[] the encrypted data
	 * @param	password String the password
	 * @param	salt byte[] the salt
	 * @param	initial_vector byte[] the initial vector, if needed
	 * @return	byte[] the resulting byte[]
	 * @throws	Exception an exception
	 */
	public byte[] decrypt(byte[] encrypted_bytes, String password, byte[] salt, byte[] initial_vector) throws Exception
	{
		byte[] key = generateSecretKey(password, salt);
		return decrypt(encrypted_bytes, key, initial_vector);
	}
	
	/**
	 * Decrypt encrypted byte array.
	 * 
	 * @param	encrypted_bytes byte[] the encrypted data
	 * @param	key byte[] the key
	 * @param	initial_vector byte[] the initial vector, if needed
	 * @return	byte[] the resulting byte[]
	 * @throws	Exception an exception
	 */
	public byte[] decrypt(byte[] encrypted_bytes, byte[] key, byte[] initial_vector) throws Exception
	{
		SecretKeySpec secret = new SecretKeySpec(key, algorithm);
		IvParameterSpec iv_param = null;
		if ( transformation != AESTransformationMode.ELECTRONIC_CODE_BOOK )
		{
			iv_param = new IvParameterSpec(initial_vector);
		}
		
		// Decrypt the message
		Cipher cipher = Cipher.getInstance(transformation.toString());
		cipher.init(Cipher.DECRYPT_MODE, secret, iv_param);
		
		byte[] decrypted = null;
		try
		{
			decrypted = cipher.doFinal(encrypted_bytes);
		}
		catch ( IllegalBlockSizeException e )
		{
			e.printStackTrace();
		}
		catch ( BadPaddingException e )
		{
			e.printStackTrace();
		}
		
		return decrypted;
	}

	/**
	 * Generate a secret key of specific length using a input string and maybe salt.
	 * 
	 * @param	password String the input string to generate the key with
	 * @param	salt byte[] a salt value
	 * @return	byte[] the secret key as byte array 
	 * @throws	NoSuchAlgorithmException
	 * @throws	UnsupportedEncodingException
	 * @throws	InvalidKeySpecException
	 */
	private byte[] generateSecretKey(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException
	{
		byte[] key = null;

//		key = getSHA256Hash(input);
		key = getKeyFactoryKey(password, salt);
		
		return key;
	}
	
	/**
	 * Get a key.
	 * 
	 * @param	password String the password
	 * @param	salt byte[] the salt
	 * @return	byte[] the key
	 * @throws	UnsupportedEncodingException
	 * @throws	NoSuchAlgorithmException
	 * @throws	InvalidKeySpecException
	 */
	private byte[] getKeyFactoryKey(String password, byte[] salt) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		byte[] key = null;
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance(key_factory_algorithm);
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PSWD_ITERATIONS, KEY_SIZE); 
		SecretKey secret_key = factory.generateSecret(spec);
		key = secret_key.getEncoded();
		
		return key;
	}

	/**
	 * Generate salt.
	 * 
	 * @return	byte[] the salt 
	 */
	public byte[] generateSalt()
	{
		return generateRandomBytes(SALT_LENGTH);
	}
	
	/**
	 * Generate initial vector.
	 * 
	 * @return	byte[] the initial vector 
	 */
	public byte[] generateInitialVector()
	{
		return generateRandomBytes(INITIAL_VECTOR_LENGTH);
	}

	/**
	 * Generate random bytes of a specified lenth.
	 * 
	 * @return	byte[] the salt 
	 */
	private byte[] generateRandomBytes(int size)
	{
		byte bytes[] = new byte[size];
		random.nextBytes(bytes);

		return bytes;
	}
}
