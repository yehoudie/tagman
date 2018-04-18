package de.yehoudie.tagman.filemanager;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.yehoudie.types.Language;
import de.yehoudie.types.Status;
import de.yehoudie.utils.XMLUtil;

/**
 * Fills text from the texts.xml into a map.<br>
 * Singleton.
 * 
 * @author yehoudie
 */
public class TextManager
{
	public static final String APPLICATION_TITLE = "ApplicationTitle";
	public static final String SAVE_CHANGES = "SaveChanges";
	public static final String UNNAMED = "Unnamed";

	public static final String MENU_FILE = "MenuFile";
	public static final String MENU_FILE_PREFERENCES = "MenuFilePreferences";
	public static final String MENU_FILE_OPEN = "MenuFileOpen";
	public static final String MENU_FILE_SAVE = "MenuFileSave";
	public static final String MENU_FILE_CLEAR = "MenuFileClear";
	public static final String MENU_FILE_EXIT = "MenuFileExit";
	
	public static final String MENU_ENTRY = "MenuEntry";
	public static final String MENU_ENTRY_FORMAT = "MenuEntryFormat";
	public static final String MENU_ENTRY_FILL = "MenuEntryFill";
	public static final String MENU_ENTRY_DATA = "MenuEntryData";
	
	public static final String ENTRY_FORMAT = "EntryFormat";
	public static final String ENTRY_FORMAT_VALUES = "EntryFormatValues";
	public static final String ENTRY_FORMAT_INFO = "EntryFormatInfo";
	public static final String ENTRY_FILE_NAME = "EntryFileName";
	public static final String ENTRY_TITLE = "EntryTitle";
	public static final String ENTRY_INTERPRET = "EntryInterpret";
	public static final String ENTRY_ALBUM = "EntryAlbum";
	public static final String ENTRY_YEAR = "EntryYear";
	public static final String ENTRY_TITLE_NUMBER = "EntryTitleNumber";
	public static final String ENTRY_GENRE = "EntryGenre";
	public static final String ENTRY_CHANGED = "EntryChanged";

	public static final String BOOLEAN_OPTIONS = "BooleanOptions";
	public static final String PREFS_LANGUAGE = "PrefsLanguage";
	public static final String PREFS_AUTOMATIC_ENTRY_TO_DATA_FILL = "PrefsAutomaticEntryToDataFill";
	public static final String PREFS_AUTOMATIC_DATA_TO_ENTRY_FILL = "PrefsAutomaticDataToEntryFill";
	
	public static final String FILE_EXISTS = "FileExists";
	public static final String FILE_NOT_EXISTS = "FileNotExists";
	public static final String INVALID_DATA = "InvalidData";
	public static final String UNSUPPORTED_TAG = "UnsupportedTag";
	
	public static final String FORMAT_DIALOG_TITLE = "FormatDialogTitle";
	public static final String FORMAT_DIALOG_LABEL = "FormatDialogLabel";
	
	private Status status;
	private HashMap<String, String> values;
	
	/**
	 * Text manager to extract app texts out of xml file a add them to accessible objects.
	 */
	private TextManager()
	{
		values = new HashMap<String, String>();
	}

	private static TextManager instance;
	
	/**
	 * Get instance of the text manager.
	 * 
	 * @return	TextManager
	 */
	public static TextManager getInstance()
	{
		if ( instance == null )
		{
			instance = new TextManager();
		}
		
		return instance;
	}

	/**
	 * Load a source file.
	 * 
	 * @param src String
	 */
	private Document load(String src)
	{
		src = this.getClass().getResource(src).toExternalForm();
	
		Document doc = XMLUtil.parseDocument(src);
		
		status = Status.LOADED;
		
		return doc;
	}
	
	/**
	 * Filter the key nodes for the right language and save the values to a hash map.
	 *  
	 * @param	src String the xml source
	 * @param	lang Language the desired language
	 */
	public void fill(String src, Language lang)
	{
		if ( lang == null ) throw new IllegalArgumentException("lang is null");
		
		if ( status == Status.LOADED) return;
		
		Document doc = load(src);
		
		NodeList keys = doc.getElementsByTagName("Key");
//		System.out.println("nList.getLength :" + nList.getLength());
		Node key;
		int key_ln = keys.getLength();
		String id;
		String lang_str = lang.toString();
		
		for ( int i = 0; i < key_ln; ++i )
		{
			key = keys.item(i);
			id = XMLUtil.getAttributeValue(key, "id");
			if ( id.indexOf( lang_str ) != -1 )
			{
//				System.out.println(id.substring(0, id.length()-3)+": "+key.getTextContent() );
				values.put(id.substring(0, id.length()-3), key.getTextContent());
			}
		}
	}

	/**
	 * Get a string value from the hash map.
	 * 
	 * @param	key String the key
	 * @return	String
	 */
	public String get(String key)
	{
		return values.get(key);
	}
	
	/**
	 * Add a value to the map.
	 * 
	 * @param	key String the key
	 * @param	value String the value
	 */
	public void set(String key, String value)
	{
		values.put(key, value);
	}
}
