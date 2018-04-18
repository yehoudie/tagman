package de.yehoudie.utils;

/**
 * Check equality of objects.
 * 
 * @author yehoudie
 */
public class EqualsUtil
{
	/**
	 * Check equality of two objects by checking for null and calling their equals function.
	 * 
	 * @param	first Object the first object
	 * @param	second Object the second object
	 * @return	boolean
	 */
	public static boolean areEqual(Object first, Object second)
	{
		return first == null ? second == null : first.equals(second);
	}
}
