package de.yehoudie.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author yehoudie
 *
 */
public class StringUtilTest
{

	/**
	 * Test method for {@link de.yehoudie.utils.StringUtil#capitalizeFirstLetter(java.lang.String)}.
	 */
	@Test
	public void testCapitalizeFirstLetter()
	{
		String s = "derTestString";
		String sr = "Derteststring";
		String r = StringUtil.capitalizeFirstLetter(s);
		assertTrue(r.equals(sr));
	}

	/**
	 * Test method for {@link de.yehoudie.utils.StringUtil#getOrdinalChar(int)}.
	 */
	@Test
	public void testGetOrdinalChar()
	{
		char c = StringUtil.getOrdinalChar(1);
		
		assertTrue(c=='A');
	}

	/**
	 * Test method for {@link de.yehoudie.utils.StringUtil#getOrdinalString(int)}.
	 */
	@Test
	public void testGetOrdinalString()
	{
		String s = StringUtil.getOrdinalString(2);
		
		assertTrue("B".equals(s));
	}

	/**
	 * Test method for {@link de.yehoudie.utils.StringUtil#isBoolean(java.lang.String)}.
	 */
	@Test
	public void testIsBoolean()
	{
		boolean b = StringUtil.isBoolean("true");
		
		assertTrue(b);
	}
	
	/**
	 * Test method for {@link de.yehoudie.utils.StringUtil#isBoolean(java.lang.String)}.
	 */
	@Test
	public void testHasValues()
	{
		String s = "a";
		String[] v0 = { "a", "b", "c"};
		boolean r = StringUtil.hasValue(s, v0);
		assertTrue(r);

		s = "b";
		String[] v1 = { "a", "b", "c"};
		r = StringUtil.hasValue(s, v1);
		assertTrue(r);
		
		s = "c";
		String[] v2 = { "a", "b", "c"};
		r = StringUtil.hasValue(s, v2);
		assertTrue(r);
		
		s = "d";
		String[] v3 = { "a", "b", "c"};
		r = StringUtil.hasValue(s, v3);
		assertTrue(!r);
	}

	@Test
	public void testRandomString()
	{
		String a = StringUtil.randomString(5, 10);
		String b = StringUtil.randomString(15, 20);
		String c = StringUtil.randomString(5, 5);
		
		assertTrue(a.length()>=5&&a.length()<=10);
		assertTrue(b.length()>=15&&a.length()<=20);
		assertTrue(c.length()==5);
	}
}
