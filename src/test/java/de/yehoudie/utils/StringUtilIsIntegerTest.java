package de.yehoudie.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author	yehoudie
 *
 */
@DisplayName("String Utils Integer Testing")
public class StringUtilIsIntegerTest
{
	@DisplayName("verifyIsInteger()")
	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@CsvSource({ "-, true, false", 
		"-, false, false", 
		"-, null, false", 
		"1, true, true", 
		"1, false, true", 
		"1, null, true", 
		"-1, true, false", // xx
		"-1, false, true", 
		"-1, null, true", 
		"a, true, false", // xx
		"a, false, false", // xx
		"ab, true, false", 
		"abcfgdfg, true, false", 
		"123456, true, true", 
		"123456, false, true", 
		"-123456, true, false", // xx
		"-123456, false, true", 
		"123456ass, true, false", 
		"-123456ass, true, false", 
		"-123456ass, false, false" 
		}
	)
	public void verifyIsInteger(String value, Boolean unsigned, boolean result) throws Exception
	{
		boolean r = false;
		if ( unsigned == null) r = StringUtil.isInteger(value);
		else r = StringUtil.isInteger(value, unsigned);

		assertEquals(r, result);
	}
	}
