package de.yehoudie.utils;

public class Math2
{

	/**
	 * Get a random number in a min / max range.
	 *
	 * @param	min double start range
	 * @param	max double end range
	 * @return	double the random number
	 */
	public static double randRange(double min, double max)
	{
		double random_num = Math.floor(Math.random() * (max - min + 1)) + min;
		
		return random_num;
	}
	
	/**
	 * Get a random number in a min / max range.
	 *
	 * @param	min double start range
	 * @param	max double end range
	 * @return	double the random number
	 */
	public static int randRange(int min, int max)
	{
		int random_num = (int) Math.floor(Math.random() * (max - min + 1)) + min;
		
		return random_num;
	}
	
	/**
	 * Get a rounded number to decimal precision.
	 *
	 * @param	value double the number to round
	 * @param	decimals uint the decimal places
	 * @return	double the rounded value
	 */
	public static double round(double value, int decimals)
	{
		if ( decimals < 0  ) decimals = 0;
		
		double precision = Math.pow(10, decimals);
		return Math.round(value * precision) / precision;
	}
	
	/**
	 * Get a rounded int.
	 *
	 * @param	value double the number to round
	 * @return	int the rounded value
	 */
	public static int round(double value)
	{
		return (int) Math.round(value);
	}
	
	/**
	 * Check if value is between min and max: value \in [min, max].
	 * 
	 * @param	value int the value to check
	 * @param	min	int the min value inclusive
	 * @param	max	int the max value inclusive
	 * @return	boolean
	 */
	public static boolean inBoundsInt(int value, int min, int max)
	{
		return value >= min && value <= max;
	}
	
	/**
	 * Check if value is between min and max: value \in [min, max].
	 * 
	 * @param	value double the value to check
	 * @param	min	double the min value inclusive
	 * @param	max	double the max value inclusive
	 * @return	boolean
	 */
	public static boolean inBounds(double value, double min, double max)
	{
		return value >= min && value <= max;
	}
}
