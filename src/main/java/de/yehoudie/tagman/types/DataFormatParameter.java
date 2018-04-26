package de.yehoudie.tagman.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * File types of the app.
 *
 * @author yehoudie
 */
public enum DataFormatParameter 
{
	TRACK_NUMBER	("%#"),
	ALBUM			("%a"),
	INTERPRET		("%i"),
	TITLE			("%t"),
	YEAR			("%y")
	;
	
	private final String parameter;
	private final static DataFormatParameter[] values = values();

	public final static Character PREFIX = '%';
	
	/**
	 * @param	parameter String the format string parameter
	 */
	private DataFormatParameter(final String parameter)
	{
		this.parameter = parameter;
	}

	@Override
	public String toString()
	{
		return parameter;
	}
	
	/**
	 * Check if this equals other string represented object.
	 * 
	 * @param	other String
	 * @return	boolean
	 */
	public boolean equals(String other)
	{
		return this.parameter.equals(other);
	}

	// map for string to type
	private static final Map<String, DataFormatParameter> parameterToTypeMap = new HashMap<String, DataFormatParameter>();

	// map string to types
	static
	{
		for ( DataFormatParameter value : EnumSet.allOf(DataFormatParameter.class) )
		{
			parameterToTypeMap.put(value.toString(), value);
		}
	}

	/**
	 * Get type out of string representation.
	 * 
	 * @param	s String the format string parameter
	 * @return	ImageFileType
	 */
	public static DataFormatParameter forString(String s)
	{
		return parameterToTypeMap.get(s);
	}
	
	/**
	 * Get type out of char representation.
	 * 
	 * @param	c Character the format character without its PREFIX
	 * @return	ImageFileType
	 */
	public static DataFormatParameter forChar(Character c)
	{
		String p = ""+PREFIX+c;
		return parameterToTypeMap.get(p);
	}

	/**
	 * Check if the parameter is a supported type.<br>
	 * Parameter has the type of %x.
	 *  
	 * @param	parameter String the parameter to check
	 * @return	boolean
	 */
	public static boolean isSupportedParameter(String parameter)
	{
		return parameterToTypeMap.get(parameter) != null;
	}

	public static boolean isSupportedParameter(Character parameter)
	{
		String p = ""+PREFIX+parameter;
		return parameterToTypeMap.get(p) != null;
	}
	
	public static DataFormatParameter[] getValues()
	{
		return values;
	}
}
