package de.yehoudie.utils;

public class StringUtil
{
	
	/**
	 * captialize first letter of a string
	 * make the rest lower case
	 * 
	 * @param	s String the string to convert
	 * @return	String the converted string
	 */
	public static String capitalizeFirstLetter(String s)
	{
		if ( s == null || s.equals("") ) return null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(s.substring(0,1).toUpperCase());
		sb.append(s.substring(1).toLowerCase());
		
		return sb.toString();
	}

	/**
	 * convert int to uc char, starting from 1: 1=>A, 2=>B, ...
	 * 
	 * @param	value int the integer value
	 * @return	char the resulting Character
	 */
	public static char getOrdinalChar(int value)
	{
		if ( value < 1 || value > 58 ) throw new IllegalArgumentException("Value is not a char");
		return (char) (value + 64);
	}

	/**
	 * convert int to uc char string, starting from 1: 1=>A, 2=>B, ...
	 * 
	 * @param	value int the integer value
	 * @return	String the resulting Character String
	 */
	public static String getOrdinalString(int value)
	{
		return String.valueOf( getOrdinalChar(value) );
	}

	/**
	 * Check, if the value is a boolean, like "true", "false".
	 * 
	 * @param	s String the value to check
	 * @return	boolean
	 */
	public static boolean isBoolean(String s)
	{
		s = s.toLowerCase();
		return "true".equals(s) || "false".equals(s);
	}

	/**
	 * Check if the text is an Integer.
	 * 
	 * @param	s String the value to check
	 * @return	boolean
	 */
	public static  boolean isInteger(String s)
	{
	    return isInteger(s, 10, false);
	}
	
	/**
	 * Check if the text is an Integer.
	 * 
	 * @param	s String the value to check
	 * @param	unsigned boolean check if integer is unsigned
	 * @return	boolean
	 */
	public static  boolean isInteger(String s, boolean unsigned)
	{
		return isInteger(s, 10, unsigned);
	}

	/**
	 * Check if the text is an Integer.
	 * 
	 * @param	s String the value to check
	 * @param	radix int the radix
	 * @param	unsigned boolean check if integer is unsigned
	 * @return	boolean
	 */
	public static boolean isInteger(String s, int radix, boolean unsigned)
	{
		if ( s.isEmpty() ) return false;
		
		for ( int i = 0; i < s.length(); i++ )
		{
			// check first char for "-"
			if ( i == 0 && s.charAt(i) == '-' )
			{
				if ( s.length() == 1 || unsigned ) return false;
				else continue;
			}
			if ( Character.digit(s.charAt(i), radix) < 0 ) return false;
		}
		return true;
	}

	/**
	 * Check if the text is one of the values.
	 * 
	 * @param	text String the text
	 * @param	values String[] the values
	 * @return	boolean
	 */
	public static boolean hasValue(String text, String[] values)
	{
		if ( text == null ) return false;
		if ( values == null || values.length == 0 ) return true;
		
		for ( String value : values )
		{
			if ( text.equals(value) ) return true;
		}
			
		return false;
	}

	/**
	 * Build random string.
	 * 
	 * @param	min_length int the minimum length
	 * @param	max_length int the maximum length
	 * @return	String
	 */
	public static String randomString(int min_length, int max_length)
	{
		StringBuilder value = new StringBuilder();
		int size = Math2.randRange(min_length, max_length);
		
		for ( int i = 0; i < size; i++ )
		{
			char c = (char) Math2.randRange(65,90);
			value.append(c);

		}
		return value.toString();
	}
	
	/**
	 * Convert boolean String to language string.
	 * 
	 * @param	value String the boolean string value
	 * @param	options String[] the to language values for [false, true]
	 * @return	String
	 */
	public static String booleanToLanguage(String value, String[] options)
	{
		System.out.printf("StringUtil.booleanToLanguage(%s, [%s, %s])\n",value,options[0],options[1]);
		boolean b = Boolean.valueOf(value);
		
		return booleanToLanguage(b, options);
	}
	
	/**
	 * Convert boolean to language string.
	 * 
	 * @param	value boolean the boolean value
	 * @param	options String[] the to language values for [false, true]
	 * @return	String
	 */
	public static String booleanToLanguage(boolean value, String[] options)
	{
		System.out.printf("StringUtil.booleanToLanguage(%b, [%s, %s])\n",value,options[0],options[1]);
		int i = value ? 1 : 0;
		System.out.println(" - val: "+i);
		String language_value = options[i];
		
		return language_value;
	}
	
	/**
	 * Convert language boolean string to boolean string.
	 * 
	 * @param	value String the boolean string value
	 * @param	options String[] the to language values for [false, true]
	 * @return	String
	 */
	public static String languageBooleanToBooleanString(String value, String[] options)
	{
		System.out.printf("StringUtil.languageBooleanToBooleanString(%s, [%s, %s])\n",value,options[0],options[1]);
		if ( value.equals(options[1]) ) return "true";
		
		return "false";
	}
}
