/**
 * 
 */
package de.yehoudie.tagman.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import de.yehoudie.types.Language;

/**
 * @author	yehoudie
 *
 */
public class PreferencesHandler
{
	private static final String COMMENT = "tag man preferences";
	
	public static final String LANGUAGE = "language";
	public static final String ATUO_DATA_TO_ENTRY = "auto_data_to_entry";
	public static final String ATUO_ENTRY_TO_DATA = "auto_entry_to_data";
	
	private File file;
	private boolean is_initialized;
	private Properties preferences;
	public Properties getPreferences() { return preferences; };

	private static PreferencesHandler instance;
	public static PreferencesHandler getInstance()
	{
		if ( instance == null  )
		{
			instance = new PreferencesHandler();
		}
		
		return instance;
	}

	private PreferencesHandler()
	{
		this.preferences = new Properties();
	}
	
	public void init(File file, Language lang)
	{
		if ( is_initialized ) return;
		
		this.file = file;
		
		if ( !file.exists() )
		{
			preferences = createDefaultPreferences();
			preferences.setProperty(LANGUAGE, lang.toString());
			System.out.println("prefs: "+preferences);
			save();
		}
		else
		{
			load();
		}
		
		is_initialized = true;
	}
	
	/**
	 * Create default preferences.
	 * 
	 * @return	Properties
	 */
	protected Properties createDefaultPreferences()
	{
		Properties p = new Properties();
		p.setProperty(LANGUAGE, Language.EN.getFullString());
		p.setProperty(ATUO_DATA_TO_ENTRY, "false");
		p.setProperty(ATUO_ENTRY_TO_DATA, "false");
		
		return p;
	}

	public String getProperty(String key)
	{
		return preferences.getProperty(key);
	}

	public boolean getBooleanProperty(String key)
	{
		String value = getProperty(key);
		
		return Boolean.valueOf(value);
	}
	
	/**
	 * Update a preference property.
	 * 
	 * @param	key String the key
	 * @param	value String the value
	 */
	public void updateProperty(String key, String value)
	{
		if ( !preferences.containsKey(key) ) return;
		
		preferences.setProperty(key, value);
	}

	/**
	 * Save the preferences.
	 */
	public void save()
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(file);
			preferences.store(writer, COMMENT);
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load the Preferences into a Properties object
	 * 
	 * @return	Properties
	 */
	public Properties load()
	{
		FileReader reader = null;
		try
		{
			reader = new FileReader(file);
			preferences = new Properties();
			preferences.load(reader);
			
			return preferences;
		}
		catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}

		return null;
	}

	public void print()
	{
		System.out.println("PreferencesHandler.print()");
		Set<Entry<Object, Object>> s = preferences.entrySet();
		
		for ( Entry<Object, Object> e : s )
		{
			System.out.println(" - "+e.getKey()+" : "+e.getValue());
		}
	}
}
