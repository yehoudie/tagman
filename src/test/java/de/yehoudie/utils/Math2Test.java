/**
 * 
 */
package de.yehoudie.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author yehoudie
 *
 */
public class Math2Test
{

	/**
	 * Test method for {@link de.yehoudie.utils.Math2#randRange(double, double)}.
	 */
	@Test
	public void testDoubleRandRange()
	{
		double min = 1;
		double max = 10;
		double r = Math2.randRange(min, max);
		assertTrue(min <= r && r <= max);
	}
	
	/**
	 * Test method for {@link de.yehoudie.utils.Math2#randRange(double, double)}.
	 */
	@Test
	public void testIntRandRange()
	{
		int min = 1;
		int max = 10;
		int r = Math2.randRange(min, max);
		assertTrue(min <= r && r <= max);
	}

	/**
	 * Test method for {@link de.yehoudie.utils.Math2#round(double, int)}.
	 */
	@Test
	public void testRoundDoubleInt()
	{
		double v = 2.12345;
		int p = 2;
		double r = Math2.round(v, p);
		assertEquals(r, 2.12, 0.0001);
	}

	/**
	 * Test method for {@link de.yehoudie.utils.Math2#round(double)}.
	 */
	@Test
	public void testRoundDouble()
	{
		double v = 2.12345;
		int r = Math2.round(v);
		assertEquals(r, 2);
	}

	/**
	 * Test method for {@link de.yehoudie.utils.Math2#inBoundsInt(int, int, int)}.
	 */
	@Test
	public void testInBoundsInt()
	{
		int v = 2;
		int min = 1;
		int max = 10;
		boolean r = Math2.inBounds(v, min, max);
		assertTrue(r);	
	}

	/**
	 * Test method for {@link de.yehoudie.utils.Math2#inBounds(double, double, double)}.
	 */
	@Test
	public void testInBounds()
	{
		double v = 2.12345;
		double min = 1.0;
		double max = 10.0;
		boolean r = Math2.inBounds(v, min, max);
		assertTrue(r);
	}

}
