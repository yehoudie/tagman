/**
 * 
 */
package de.yehoudie.utils.files;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.yehoudie.utils.StringUtil;

/**
 * @author yehoudie
 *
 */
public class FileUtilTest
{
	/**
	 * Test method for FileUtil.writeToFile(File, byte[]) and FileUtil.readBytes(File)
	 */
	@Test
	public void testWriteReadBytes()
	{
		byte[] write = {0,1,2,3,4,5,6,7,8};
		File file = new File("./src/test/java/de/yehoudie/utils/fileUtilTest.file");
		boolean s = FileUtil.write(write, file);
		
		byte[] read = FileUtil.getBytes(file);

		assertTrue(Arrays.equals(write, read));
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#writeToTempFile(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testWriteToTempFile()
	{
		String content = "temp file content";
		String prefix = "tempFile";
		String suffix = ".tmp";
		System.out.println("content: "+content);
		
		File file = FileUtil.writeToTempFile(content, prefix, suffix);
		
		String read_name_parts[] = file.getName().split("\\.");
		String read_prefix = read_name_parts[0];
		String read_suffix = "."+read_name_parts[1];
		
		String read_content = FileUtil.getContent(file);
		
		assertTrue(read_prefix.indexOf(prefix)>-1);
		assertTrue(read_suffix.equals(suffix));
		assertTrue(read_content.equals(content));
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#write(java.lang.String, java.io.File, boolean)}.
	 */
	@Test
	public void testAppendStringToFile()
	{
		File file = new File("./src/test/java/de/yehoudie/utils/testAppendStringToFile.file");
		String content = "temp file content";

		boolean s = FileUtil.write(content, file, false);
		
		assertTrue(s);

		s = FileUtil.write(content, file, true);
		assertTrue(s);
		String read_content = FileUtil.getContent(file);
		
		assertFalse(read_content.equals(content));
		assertTrue(read_content.equals(content+content));
		
		file.delete();
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#write(java.lang.String, java.io.File)}.
	 */
	@Test
	public void testWriteStringToFile()
	{
		File file = new File("./src/test/java/de/yehoudie/utils/testWriteStringToFile.file");
		String content = "temp file content";

		boolean s = FileUtil.write(content, file, false);
		assertTrue(s);

		String read_content = FileUtil.getContent(file);
		assertTrue(content.equals(read_content));
		
		file.delete();
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#write(byte[], java.io.File)}.
	 */
	@Test
	public void testWriteByteArrayToFile()
	{
		File file = new File("./src/test/java/de/yehoudie/utils/testWriteByteArrayToFile.file");
		byte[] content = getRandomBytes(256);
		
		boolean s = FileUtil.write(content, file);
		assertTrue(s);
		
		byte[] read_content = FileUtil.getBytes(file);
		assertTrue(Arrays.equals(read_content,content));

		file.delete();
	}

	private byte[] getRandomBytes(int n)
	{
		byte[] bytes = new byte[n];
		new Random().nextBytes(bytes);
		return bytes;
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#getContent(java.io.File)}.
	 */
	@Test
	public void testGetFileContent()
	{
		File file = new File("./src/test/java/de/yehoudie/utils/testGetFileContent.file");
		String content = "test get file content";
		
		boolean s = FileUtil.write(content, file);
		assertTrue(s);
		
		String read_content = FileUtil.getContent(file);
		
		assertTrue(read_content.equals(content));
		
		file.delete();
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#getContent(java.net.URL)}.
	 * @throws MalformedURLException 
	 */
	@Test
	public void testGetURLContent() throws MalformedURLException
	{
		File file = new File("./src/test/java/de/yehoudie/utils/testGetFileContent.file");
		String content = "test get file content";
		
		boolean s = FileUtil.write(content, file);
		assertTrue(s);
		
		URL url = file.toURI().toURL();
		
		String read_content = FileUtil.getContent(url);
		
		assertTrue(read_content.equals(content));
		
		file.delete();
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#getBytes(java.io.File)}.
	 */
	@Test
	public void testReadBytes()
	{
		File file = new File("./src/test/java/de/yehoudie/utils/testReadBytes.file");
		byte[] content = getRandomBytes(256);
		
		boolean s = FileUtil.write(content, file);
		assertTrue(s);
		
		byte[] read_content = FileUtil.getBytes(file);
		assertTrue(Arrays.equals(read_content,content));

		file.delete();
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#getContentAsList(java.io.File)}.
	 * @throws IOException 
	 */
	@Test
	public void testGetContentAsList() throws IOException
	{
		File file = new File("./src/test/java/de/yehoudie/utils/testGetContentAsList.file");
		file.delete();
		file.createNewFile();
		
		int min_length = 15;
		int max_length = 30;
		
		List<String> write_content = getRandomList(min_length, max_length);
		
		for ( String line : write_content )
		{
			FileUtil.write(line+"\n", file, true);
		}
		
		List<String> read_content = FileUtil.getContentAsList(file);
		
		assertTrue(read_content.equals(write_content));
		
		file.delete();
	}

	private List<String> getRandomList(int min_length, int max_length)
	{
		List<String> list = new ArrayList<String>();
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		list.add(StringUtil.randomString(min_length, max_length));
		
		return list;
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#getNameAndType(java.io.File)}.
	 */
	@Test
	public void testGetNameAndType()
	{
		FileName file_name_0 = new FileName("a.file.name", "type");
		FileName file_name_1 = new FileName("a.file.name", "");
		FileName file_name_2 = new FileName("aFileName", "");
		file_name_2.file_name = file_name_2.name;
		FileName file_name_3 = new FileName("", "type");
		
		File file_0 = new File("./src/test/java/de/yehoudie/utils/", file_name_0.file_name);
		File file_1 = new File("./src/test/java/de/yehoudie/utils/", file_name_1.file_name);
		File file_2 = new File("./src/test/java/de/yehoudie/utils/", file_name_2.file_name);
		File file_3 = new File("./src/test/java/de/yehoudie/utils/", file_name_3.file_name);

		String[] read_name_0_parts = FileUtil.getNameAndType(file_0);
		String[] read_name_1_parts = FileUtil.getNameAndType(file_1);
		String[] read_name_2_parts = FileUtil.getNameAndType(file_2);
		String[] read_name_3_parts = FileUtil.getNameAndType(file_3);
		
		assertTrue(read_name_0_parts.length==4);
		String read_name_0_name = read_name_0_parts[0]+"."+read_name_0_parts[1]+"."+read_name_0_parts[2]; 
		assertTrue(read_name_0_name.equals(file_name_0.name));
		assertTrue(read_name_0_parts[3].equals(file_name_0.type));
		
		assertTrue(read_name_1_parts.length==4);
		String read_name_1_name = read_name_1_parts[0]+"."+read_name_1_parts[1]+"."+read_name_1_parts[2]; 
		assertTrue(read_name_1_name.equals(file_name_1.name));
		assertTrue(read_name_1_parts[3].equals(file_name_1.type));
		
		assertTrue(read_name_2_parts.length==2);
		String read_name_2_name = read_name_2_parts[0]; 
		assertTrue(read_name_2_name.equals(file_name_2.name));
		assertTrue(read_name_2_parts[1].equals(file_name_2.type));
		
		assertTrue(read_name_3_parts.length==2);
		String read_name_3_name = read_name_3_parts[0]; 
		assertTrue(read_name_3_name.equals(file_name_3.name));
		assertTrue(read_name_3_parts[1].equals(file_name_3.type));
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#getName(java.io.File)}.
	 */
	@Test
	public void testGetName()
	{
		FileName file_name_0 = new FileName("a.file.name", "type");
		FileName file_name_1 = new FileName("a.file.name", "");
		FileName file_name_2 = new FileName("aFileName", "");
		file_name_2.file_name = file_name_2.name;
		FileName file_name_3 = new FileName("", "type");
		
		File file_0 = new File("./src/test/java/de/yehoudie/utils/", file_name_0.file_name);
		File file_1 = new File("./src/test/java/de/yehoudie/utils/", file_name_1.file_name);
		File file_2 = new File("./src/test/java/de/yehoudie/utils/", file_name_2.file_name);
		File file_3 = new File("./src/test/java/de/yehoudie/utils/", file_name_3.file_name);

		String read_name_0_name = FileUtil.getName(file_0);
		String read_name_1_name = FileUtil.getName(file_1);
		String read_name_2_name = FileUtil.getName(file_2);
		String read_name_3_name = FileUtil.getName(file_3);
		
		assertTrue(read_name_0_name.equals(file_name_0.name));
		assertTrue(read_name_1_name.equals(file_name_1.name));
		assertTrue(read_name_2_name.equals(file_name_2.name));
		assertTrue(read_name_3_name.equals(file_name_3.name));
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#getType(java.io.File)}.
	 */
	@Test
	public void testGetType()
	{
		FileName file_name_0 = new FileName("a.file.name", "type");
		FileName file_name_1 = new FileName("a.file.name", "");
		FileName file_name_2 = new FileName("aFileName", "");
		file_name_2.file_name = file_name_2.name;
		FileName file_name_3 = new FileName("", "type");
		
		File file_0 = new File("./src/test/java/de/yehoudie/utils/", file_name_0.file_name);
		File file_1 = new File("./src/test/java/de/yehoudie/utils/", file_name_1.file_name);
		File file_2 = new File("./src/test/java/de/yehoudie/utils/", file_name_2.file_name);
		File file_3 = new File("./src/test/java/de/yehoudie/utils/", file_name_3.file_name);

		String read_name_0_type = FileUtil.getType(file_0);
		String read_name_1_type = FileUtil.getType(file_1);
		String read_name_2_type = FileUtil.getType(file_2);
		String read_name_3_type = FileUtil.getType(file_3);
		
		assertTrue(read_name_0_type.equals(file_name_0.type));
		assertTrue(read_name_1_type.equals(file_name_1.type));
		assertTrue(read_name_2_type.equals(file_name_2.type));
		assertTrue(read_name_3_type.equals(file_name_3.type));
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#urlToFile(java.net.URL)}.
	 */
	@Disabled("Not yet implemented.")
	@Test
	public void testUrlToFile()
	{
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.yehoudie.utils.files.FileUtil#hide(java.io.File)}.
	 */
	@Disabled("Not yet implemented.")
	@Test
	public void testHide()
	{
//		fail("Not yet implemented");
	}
}

class FileName
{
	public String name;
	public String type;
	public String file_name;
	
	public FileName(String name, String type)
	{
		this.name = name;
		this.type = type;
		this.file_name = name + "." + type;
	}
	
	public String toString()
	{
		return file_name;
	}
}
