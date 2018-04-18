package de.yehoudie.tagman.utils;

import java.io.File;

import de.yehoudie.tagman.Main;
import de.yehoudie.utils.files.FileUtil;

public class FileHandler
{
//	public static String PREF_OPEN_PATH_ID = "lastOpenPath";
//	public static String PREF_SAVE_PATH_ID = "lastOpenPath";
	
	/**
	 * Returns the last file preference, i.e. the file path that was last opened.
	 * 
	 * @return	File
	 */
	public static File getLastFilePath()
	{
		if ( Main.LAST_FILE_PATH_FILE == null ) return null;
		String file_path = FileUtil.getContent(Main.LAST_FILE_PATH_FILE);
		if ( file_path != null && !file_path.isEmpty() )
		{
			File file = new File (file_path);
			if ( file.exists() ) return file;
		}

		return null;
	}
	
	/**
	 * Returns the last file preference, i.e. the file path that was last opened.<br>
	 * If null, it returns the default file.
	 * 
	 * @param	default_file File
	 * @return	File
	 */
	public static File getLastFilePath(File default_file)
	{
		File path = getLastFilePath();
		
		return ( path != null ) ? path : default_file;
	}

	/**
	 * Sets the file directory path of the currently loaded file. 
	 * 
	 * @param	file File the file or null to remove the path
	 */
	public static void setLastFilePath(File file)
	{
		if ( file == null ) throw new IllegalArgumentException("file is null");

		setLastPath(file.getParentFile());
	}
	
	/**
	 * Sets the directory path of the currently loaded directory. 
	 * 
	 * @param	file File the file or null to remove the path
	 */
	public static void setLastDirectoryPath(File file)
	{
		setLastPath(file);
	}
	
	/**
	 * Sets the file path of the currently loaded file. 
	 * 
	 * @param	file File the file or null to remove the path
	 */
	public static void setLastPath(File file)
	{
		if ( file == null ) throw new IllegalArgumentException("file is null");
		
		FileUtil.write(file.getAbsolutePath(), Main.LAST_FILE_PATH_FILE);
	}
}
