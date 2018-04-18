package de.yehoudie.tagman.objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author yehoudie
 *
 */
class FileDataTest
{
	private static FileData data0;
	private static FileData data1;
	private static FileData data2;
	private static FileData data3;
	private static FileData data4;
	private static FileData data5;
	private static FileData data6;

	@BeforeAll
	public static void init()
	{
		data0 = new FileData("data0", new File("."));
		data1 = new FileData("data1", new File("./file1"));
		data2 = new FileData("Data2", new File("./file2.mp3"));
		data3 = new FileData("data3", new File("./file3.txt"));
		data4 = new FileData(null, new File("./file4.txt"));
		data5 = new FileData("data5", null);
		data6 = new FileData(null, null);
	}
	
	@AfterAll
	public static void dispose()
	{
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#FileData(java.lang.String, java.io.File)}.
	 */
	@Test
	void testFileData()
	{
		String data_name = "the name";
		File data_file = new File("./theFile.type");
		
		FileData data = new FileData();
		data.setFile(data_file);
		data.setName(data_name);
		
		assertTrue(data_name.equals(data.nameProperty().get()));
		assertTrue(data_file.equals(data.fileProperty().get()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#FileData(java.lang.String, java.io.File)}.
	 */
	@Test
	void testFileDataStringFile()
	{
		String data_name = "the name";
		File data_file = new File("./theFile.type");
		FileData data = new FileData(data_name, data_file);

		assertTrue(data_name.equals(data.nameProperty().get()));
		assertTrue(data_file.equals(data.fileProperty().get()));
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#nameProperty()}.
	 */
	@Test
	public void testNamePropertyID()
	{
		assertTrue(FileData.NAME.equals(data0.nameProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#fileProperty()}.
	 */
	@Test
	public void testFileProperty()
	{
		assertTrue(FileData.FILE.equals(data0.fileProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#toString()}.
	 */
	@Test
	void testToString()
	{
		String data_name = "the name";
		File data_file = new File("./theFile.type");
		FileData data = new FileData(data_name, data_file);
		
		assertTrue(data.toFullString().equals(data.toString()));
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#toFullString()}.
	 */
	@Test
	void testToFullString()
	{
		String data_name = "the name";
		File data_file = new File("./theFile.type");
		FileData data = new FileData(data_name, data_file);
		
		String expected = new StringBuilder()
			.append("{ ")
			.append("name: ")
			.append(data_name)
			.append(", file: ")
			.append(data_file)
			.append(" }").toString();
		
		assertTrue(expected.equals(data.toFullString()));
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#compareTo(de.yehoudie.tagman.objects.FileData)}.
	 */
	@Test
	void testCompareTo()
	{
		int c00 = data0.compareTo(data0);
		int c01 = data0.compareTo(data1);
		int c02 = data0.compareTo(data2);
		int c03 = data0.compareTo(data3);
		int c11 = data1.compareTo(data1);
		int c12 = data1.compareTo(data2);
		int c13 = data1.compareTo(data3);
		int c22 = data2.compareTo(data2);
		int c23 = data2.compareTo(data3);
		int c33 = data3.compareTo(data3);
		
		assertTrue(c00 == 0);
		assertTrue(c01 < 0);
		assertTrue(c02 < 0);
		assertTrue(c03 < 0);
		assertTrue(c11 == 0);
		assertTrue(c12 < 0);
		assertTrue(c13 < 0);
		assertTrue(c22 == 0);
		assertTrue(c23 < 0);
		assertTrue(c33 == 0);
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject()
	{
		boolean are_equal00 = data0.equals(data0);
		assertTrue(are_equal00);

		boolean are_equal01 = data0.equals(data1);
		assertFalse(are_equal01);
		
		boolean are_equal10 = data1.equals(data0);
		assertFalse(are_equal10);
		
		boolean are_equal12 = data1.equals(data2);
		assertFalse(are_equal12);
		
		boolean are_equal02 = data0.equals(data2);
		assertFalse(are_equal02);
		
		boolean are_equal06 = data0.equals(data6);
		assertFalse(are_equal06);
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.FileData#isFile()}.
	 */
	@Test
	void testIsFile()
	{
		assertFalse(data0.isFile());
	}
}
