package de.yehoudie.crypto;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * AES encryption transformation types.
 * 
 * @author yehoudie
 */
public enum AESTransformationMode 
{
	// no need for IV with ECB: Electronic code Book, unsecure for more than one block
	ELECTRONIC_CODE_BOOK	("AES/ECB/PKCS5Padding", "ECB"),
	OUTPUT_FEEDBACK_MODE	("AES/OFB/PKCS5Padding", "OFB"),
	CIPHER_BLOCK_CHAINING	("AES/CBC/PKCS5Padding", "CBC")
	;
	
	private final String value;
	private final String short_name;
	public String getShortName() { return short_name; };
	private final static AESTransformationMode[] values;
	static
	{
		values = values();
	}

	/**
	 * @param	value String the AESTransformationMode value
	 * @param	short_name String the short name of the mode
	 */
	private AESTransformationMode(final String value, final String short_name)
	{
		this.value = value;
		this.short_name = short_name;
	}

	/**
	 * Convert to string
	 */
	@Override
	public String toString()
	{
		return value;
	}

	/**
	 * Check if this equals other string represented object.
	 * 
	 * @param	other String
	 * @return	boolean
	 */
	public boolean equals(String other)
	{
		return this.value.equals(other);
	}

	// map for string to type
	private static final Map<String, AESTransformationMode> stringToValueMap = new HashMap<String, AESTransformationMode>();

	// map string to types
	static
	{
		for ( AESTransformationMode value : EnumSet.allOf(AESTransformationMode.class) )
		{
			stringToValueMap.put(value.toString(), value);
		}
	}

	/**
	 * get AESTransformationTypes out of string representation 
	 * 
	 * @param	s String the string of the DrawingType
	 * @return	DrawingType
	 */
	public static AESTransformationMode forString(String s)
	{
		AESTransformationMode type = stringToValueMap.get( s.toUpperCase() ); 
		return type;
	}

	/**
	 * @return the values
	 */
	public static AESTransformationMode[] getValues()
	{
		return values;
	}
}
