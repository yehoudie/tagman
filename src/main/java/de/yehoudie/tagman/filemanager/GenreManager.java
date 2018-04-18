package de.yehoudie.tagman.filemanager;

import java.util.HashMap;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.yehoudie.types.Status;
import de.yehoudie.utils.XMLUtil;

/**
 * Fills text from the texts.xml into a map.<br>
 * Singleton.
 * 
 * @author yehoudie
 */
public class GenreManager
{
	private Status status;
	private HashMap<Integer, String> values;
	
	/**
	 * Text manager to extract app texts out of xml file a add them to accessible objects.
	 */
	private GenreManager()
	{
		values = new HashMap<Integer, String>();
	}

	private static GenreManager instance;
	
	/**
	 * Get instance of the text manager.
	 * 
	 * @return	TextManager
	 */
	public static GenreManager getInstance()
	{
		if ( instance == null )
		{
			instance = new GenreManager();
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
	 */
	public void fill(String src)
	{
		if ( status == Status.LOADED) return;
		
		Document doc = load(src);
		
		NodeList items = doc.getElementsByTagName("type");
//		System.out.println("nList.getLength :" + nList.getLength());
		Node item;
		int items_ln = items.getLength();
		int id;
		String name;
		
		for ( int i = 0; i < items_ln; ++i )
		{
			item = items.item(i);

			id = Integer.valueOf( XMLUtil.getAttributeValue(item, "id") );
			name = XMLUtil.getAttributeValue(item, "name");
			
			values.put(id, name);
		}
	}

	/**
	 * Get a string value from the hash map.
	 * 
	 * @param	key String the key
	 * @return	String
	 */
	public String get(int key)
	{
		return values.get(key);
	}
	
	/**
	 * Add a value to the map.
	 * 
	 * @param	key String the key
	 * @param	value String the value
	 */
	public void set(int key, String value)
	{
		values.put(key, value);
	}
	
	public String[] getValues()
	{
		String[] v = new String[values.size()];
		int i = 0;
		
		for ( Entry<Integer, String> entry : values.entrySet() )
		{
			v[i++] = entry.getValue();
		}
		
		return v;
	}
}
