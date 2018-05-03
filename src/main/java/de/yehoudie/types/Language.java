package de.yehoudie.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public enum Language 
{
	DE		("DE", "Deutsch", Locale.GERMAN),
	EN		("EN", "English", Locale.ENGLISH),
	ES		("ES", "Español", new Locale("es", "ES")),
	FR		("FR", "Français", Locale.FRENCH),
	IT		("IT", "Italiano", Locale.ITALIAN),
	RU		("RU", "Русский", new Locale("ru", "RU"))
	;
	
	private final String id;
	private final String full;
	private final Locale locale;
	private final static Language[] values;
	static
	{
		values = values();
	}

	/**
	 * @param	id String the Language id
	 * @param	full String the Language full string
	 * @param	locale Locale the locale to set the system language
	 */
	private Language(final String id, final String full, Locale locale)
	{
		this.id = id;
		this.full = full;
		this.locale = locale;
	}

	@Override
	public String toString()
	{
		return id;
	}
	
	public String getFullString()
	{
		return full;
	}

	/**
	 * check if this equals other string represented object
	 * 
	 * @param	other String
	 * @return	boolean
	 */
	public boolean equals(String other)
	{
		return this.id.equals(other);
	}

	// map for string to type
	private static final Map<String, Language> stringToValueMap = new HashMap<String, Language>();

	// map string to types
	static
	{
		for ( Language value : EnumSet.allOf(Language.class) )
		{
			stringToValueMap.put(value.toString(), value);
		}
	}

	/**
	 * get Language out of string representation 
	 * 
	 * @param	s String the string of the DrawingType
	 * @return	DrawingType
	 */
	public static Language forString(String s)
	{
		Language lang = stringToValueMap.get( s.toUpperCase() ); 
		return ( lang == null ) ? EN : lang;
	}

	/**
	 * @return the values
	 */
	public static Language[] getValues()
	{
		return values;
	}

	/**
	 * Get the locale code for the language
	 * 
	 * @return	Locale
	 */
	public Locale getLocale()
	{
		return locale;
	}
}
