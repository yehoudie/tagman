package de.yehoudie.tagman.types;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import de.yehoudie.utils.files.FileUtil;

/**
 * File types of the app.
 *
 * @author yehoudie
 */
public enum AppFileType 
{
	MP3	("mp3", 0)
	;
	
	private final String extension;
	private final static AppFileType[] values = values();
	private int version;

	/**
	 * @param	extension String the file type extension
	 */
	private AppFileType(final String extension, int version)
	{
		this.extension = extension;
		this.version = version;
	}

	@Override
	public String toString()
	{
		return extension;
	}
	
	public String toUpperCase()
	{
		return extension.toUpperCase();
	}

	/**
	 * Check if this equals other string represented object.
	 * 
	 * @param	other String
	 * @return	boolean
	 */
	public boolean equals(String other)
	{
		return this.extension.equals(other);
	}

	// map for string to type
	private static final Map<String, AppFileType> extensionToTypeMap = new HashMap<String, AppFileType>();

	// map string to types
	static
	{
		for ( AppFileType value : EnumSet.allOf(AppFileType.class) )
		{
			extensionToTypeMap.put(value.toString(), value);
		}
	}

	/**
	 * Get type out of string representation.
	 * 
	 * @param	s String the extension string of the ImageFileType
	 * @return	ImageFileType
	 */
	public static AppFileType forString(String s)
	{
		return extensionToTypeMap.get(s);
	}

	/**
	 * Check if the extension is a supported type.
	 *  
	 * @param	extension String the file extension to check
	 * @return	boolean
	 */
	public static boolean isSupportedExtension(String extension)
	{
		return extensionToTypeMap.get(extension) != null;
	}
	
	/**
	 * Check if the file is a supported type.
	 *  
	 * @param	file File the file to check
	 * @return	boolean
	 */
	public static boolean isSupportedFileType(File file)
	{
		if ( file == null ) return false;
		return isSupportedExtension( FileUtil.getType(file) );
	}
	
	public int getVersion()
	{
		return this.version;
	}
	
	public static int getVersion(String extension)
	{
		AppFileType type = forString(extension);
		return ( type != null ) ? type.version : -1;
	}
	
	public static AppFileType[] getValues()
	{
		return values;
	}
}
