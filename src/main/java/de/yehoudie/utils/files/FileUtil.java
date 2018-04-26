package de.yehoudie.utils.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for file actions.
 * 
 * @author yehoudie
 */
public class FileUtil
{
	private static final Charset UTF_8 = Charset.forName("UTF-8");
		
	/**
	 * Write a string to a newly created file.
	 * 
	 * @param content String the content
	 * @param name String the name of the file to create
	 * @param type String the type of the file to create
	 * @return File the created file
	 */
	public static File writeToTempFile(final String content, final String name, final String type)
	{
		File file = createTempFile(name, type);

		if ( write(content, file, false) ) return file;
		else return null;
	}

	/**
	 * @param name
	 * @param type
	 * @param file
	 * @return
	 */
	private static File createTempFile(final String name, final String type)
	{
		try
		{
			return File.createTempFile(name, type);
		}
		catch ( IOException e1 )
		{
			e1.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Write a string to a newly created file.<br>
	 * Overwrites existing content.
	 * @param	content String the content
	 * @param	file File the file to fill
	 * @param	append boolean append the content or overwrite existing content
	 * 
	 * @return	boolean success state
	 */
	public static boolean write(final String content, final File file, final boolean append)
	{
		Writer fw = null;
		
		try
		{
			fw = fileWrite(content, file, append);
		}
		catch ( IOException e )
		{
			System.err.println("Can't write to file: "+e.getMessage());
			return false;
		}
		finally
		{
			closeFileWriter(fw);
		}
		
		return true;
	}

	/**
	 * @param fw
	 */
	private static void closeFileWriter(Writer fw)
	{
		if ( fw != null ) try
		{
			fw.close();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param content
	 * @param file
	 * @param append
	 * @return
	 * @throws IOException
	 */
	private static Writer fileWrite(final String content, final File file, final boolean append) throws IOException
	{
		Writer fw;
		fw = new FileWriter(file, append);
		fw.write(content);
		return fw;
	}
	
	/**
	 * Write a string to a newly created file.<br>
	 * Overwrites existing content.
	 * 
	 * @param	content String the content
	 * @param	file File the file to fill
	 * @return	boolean success state
	 */
	public static boolean write(final String content, final File file)
	{
		return write(content, file, false);
	}

	/**
	 * Write a string to a newly created file url.
	 * @param	content String the content
	 * @param	url Url the url of the file to fill
	 * @param	append boolean append the content or overwrite existing content
	 * @return	boolean success state
	 */
	/*public static boolean write(final String content, final URL url, final boolean append)
	{
		File file = new File(url.getPath());
		
		return write(content, file, append);
//		return write(content, append, file);
	}*/

	/**
	 * @param content
	 * @param append
	 * @param file
	 * @return
	 */
	/*private static boolean write(final String content, final boolean append, File file)
	{
		PrintWriter writer = null;
		try
		{
			writer = printWrite(content, file, append);
			
			return true;
		}
		catch ( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		finally
		{
			writer.close();
		}

		return false;
	}*/

	/**
	 * @param content
	 * @param file
	 * @param append
	 * @return
	 * @throws FileNotFoundException
	 */
	/*private static PrintWriter printWrite(final String content, File file, final boolean append) throws FileNotFoundException
	{
		PrintWriter writer;
		writer = new PrintWriter(file);
		if ( append ) writer.append(content);
		else writer.write(content);
		return writer;
	}*/

	/**
	 * Write a string to a newly created file.<br>
	 * Overwrite existing content.
	 * 
	 * @param	content String the content
	 * @param	url Url the url of the file to fill
	 * @return	boolean success state
	 */
	/*public static boolean write(final String content, final URL url)
	{
		return write(content, url, false);
	}*/

	/**
	 * Write byte[] to file.
	 * @param	content byte[] the content
	 * @param	file File the fiel to write
	 * 
	 * @return	boolean success state
	 */
	public static boolean write(final byte[] content, final File file)
	{
		FileOutputStream output_stream = null;
		try
		{
			output_stream = streanWrite(content, file);
			return true;
		}
		catch ( FileNotFoundException e )
		{
			e.printStackTrace();
			return false;
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			closeStream(output_stream);
		}
	}

	/**
	 * @param output_stream
	 */
	private static void closeStream(FileOutputStream output_stream)
	{
		if ( output_stream != null )
		{
			try
			{
				output_stream.close();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param content
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static FileOutputStream streanWrite(final byte[] content, final File file) throws FileNotFoundException, IOException
	{
		FileOutputStream output_stream;
		output_stream = new FileOutputStream(file);
		output_stream.write(content);
		return output_stream;
	}
	
	/**
	 * Write content of a (text) file into a string.
	 * 
	 * @param	file File the file
	 * @return	String the string content of the file
	 */
	public static String getContent(final File file)
	{
		try
		{
			return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get string file content of an url ressource.
	 * 
	 * @param	url URL
	 * @return	String
	 */
	public static String getContent(URL url)
	{
		// read text returned by server
		BufferedReader in;
		String line;
		StringBuilder content = new StringBuilder();
		try
		{
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ( (line = in.readLine()) != null )
			{
				content.append(line);
			}
			in.close();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	
		return content.toString();
	}

	/**
	 * Read byte[] of a file.<br>
	 * Simple wrapper for Files.readAllBytes(File);
	 * 
	 * @param	file File the file to read
	 * @return	byte[] the bytes
	 */
	public static byte[] getBytes(File file)
	{
		try
		{
			byte[] data = Files.readAllBytes(file.toPath());
			return data;
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Write lines of a (text) file into a List of strings.<br>
	 * Simple wrapper of Files.readAllLines();
	 * 
	 * @param	file File the file
	 * @return	String the string content of the file
	 */
	public static List<String> getContentAsList(final File file)
	{
		try
		{
			return Files.readAllLines(Paths.get(file.getAbsolutePath()), UTF_8);
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get name and type of file.
	 * 
	 * @param	file File the file
	 * @return	String[] [name, ..., type]
	 */
	public static String[] getNameAndType(final File file)
	{
		boolean has_type = hasType(file);
		
		String[] parts = file.getName().split("\\.");
		
		if ( has_type )
		{
			return parts;
		}
		else
		{
			int i = 0;
			String[] typed_parts = new String[parts.length+1];
			for ( i = 0; i < parts.length; i++ ) typed_parts[i] = parts[i];
			typed_parts[i] = "";
			return typed_parts;
		}
	}

	/**
	 * @param file
	 * @return
	 */
	private static boolean hasType(final File file)
	{
		String name = file.getName();
		int point_index = name.lastIndexOf(".");
		boolean has_type = point_index !=-1 && point_index!=name.length()-1;
		return has_type;
	}
	
	/**
	 * Get file name without type.
	 * 
	 * @param	file File the file
	 * @return	String
	 */
	public static String getName(final File file)
	{
		boolean has_type = hasType(file);
		String[] parts = file.getName().split("\\.");
		
		StringBuilder sb = new StringBuilder();
		int name_ln = ( has_type ) ? parts.length - 1 : parts.length;
		
		for ( int i = 0; i < name_ln; i++ )
		{
			sb.append(parts[i]);
			if ( i < name_ln - 1 ) sb.append(".");
		}
		
		return sb.toString();
	}
	
	/**
	 * Get type of file without the point.<br>
	 * 
	 * @param	file File the file
	 * @return	String the type
	 */
	public static String getType(final File file)
	{
		if ( file == null ) return null;
		
		String type = "";
		int i = file.getName().lastIndexOf('.');
		if ( i >= 0 )
		{
			type = file.getName().substring(i + 1);
		}
		return type;
	}

	/**
	 * Convert URL to File.
	 *  
	 * @param	url URL
	 * @return	File
	 */
	public static File urlToFile(URL url)
	{
		File file = null;
		try
		{
			file = new File(url.toURI());
		}
		catch ( URISyntaxException e )
		{
			file = new File(url.getPath());
		}
		return file;
	}

	/**
	 * Hide a file (windows only).
	 * 
	 * @param	file File the file to hide
	 */
	public static void hide(File file)
	{
		try
		{
			Runtime.getRuntime().exec("attrib +H "+file.toString());
		}
		catch ( IOException e1 )
		{
			e1.printStackTrace();
		}
	}
	
	/**
	 * Create dirs of a directory structure.
	 * 
	 * @param	dir File
	 * @return	File the file or null on failure
	 */
	public static File createDirs(File dir)
	{
		if ( !dir.exists() )
		{
			if ( !dir.mkdirs() )
			{
				System.err.println("Could not create app data dir!");
				return null;
			}
		}
		return dir;
	}
}
