package de.yehoudie.tagman.objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author yehoudie
 *
 */
class TagDataTest
{
	private static TagData data0;
	private static TagData data1;
	private static TagData data2;
	private static TagData data3;
	private static TagData data4;
	private static TagData data5;
	private static TagData data6;

	@BeforeAll
	public static void init()
	{
		data0 = new TagData("data0", "titl00", "artist0", "album0", 0, 0, "");
		data1 = new TagData("data1", "titl01", "interpret1", "album0", 2004, 1, "psycho");
		data2 = new TagData("data2", "titl2", "artist2", "album1", 0, 2, "Jungle");
		data3 = new TagData("data3", "titl3", "artis3", "album1", 2004, 3, "Death Core");
		data4 = new TagData("data4", "titl40", "interpret0", "album0", 2005, 4, "Raggae");
		data5 = new TagData("data5", "titl5", "artist0", "album1", 2018, 5, "");
		data6 = new TagData("data6", "titl60", "artist0", "album0", 2004, 6, "");
	}
	
	@AfterAll
	public static void dispose()
	{
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#fileNameProperty()}.
	 */
	@Test
	public void testFileNamePropertyID()
	{
		assertTrue(TagData.FILE_NAME.equals(data0.fileNameProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#titleProperty()}.
	 */
	@Test
	public void testTitlePropertyID()
	{
		assertTrue(TagData.TITLE.equals(data0.titleProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#interpretProperty()}.
	 */
	@Test
	public void testInterpretPropertyID()
	{
		assertTrue(TagData.INTERPRET.equals(data0.interpretProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#albumProperty()}.
	 */
	@Test
	public void testAlbumPropertyID()
	{
		assertTrue(TagData.ALBUM.equals(data0.albumProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#yearProperty()}.
	 */
	@Test
	public void testYearPropertyID()
	{
		assertTrue(TagData.YEAR.equals(data0.yearProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#titleNumberProperty()}.
	 */
	@Test
	public void testTitleNumberPropertyID()
	{
		assertTrue(TagData.TITLE_NUMBER.equals(data0.titleNumberProperty().getName()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#genreDescriptionProperty()}.
	 */
	@Test
	public void testGenrePropertyID()
	{
		assertTrue(TagData.GENRE_DESCRIPTION.equals(data0.genreDescriptionProperty().getName()));
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#EntryData()}.
	 */
	@Test
	void testEntryData()
	{
		String file_name = "file naem";
		String album = "album";
		String artist = "artist";
		int year = 2005;
		int track = 5;
		String genre = "";
		String title = "tititle";
				
		
		TagData data = new TagData();
		data.setAlbum(album);
		data.setFileName(file_name);
		data.setTitle(title);
		data.setYear(year);
		data.setTitleNumber(track);
		data.setInterpret(artist);
		data.setGenreDescription(genre);
		
		assertTrue(file_name.equals(data.getFileName()));
		assertTrue(title.equals(data.getTitle()));
		assertTrue(artist.equals(data.getInterpret()));
		assertTrue(album.equals(data.getAlbum()));
		assertTrue(year == data.getYear());
		assertTrue(track == data.getTitleNumber());
		assertTrue(genre.equals(data.getGenreDescription()));
	}
	
	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#EntryData(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String)}.
	 */
	@Test
	void testEntryDataStringStringStringStringIntIntString()
	{
		String file_name = "file naem";
		String album = "album";
		String artist = "artist";
		int year = 2005;
		int track = 5;
		String genre = "";
		String title = "tititle";
				
		
		TagData data = new TagData(file_name, title, artist, album, year, track, genre);
		
		assertTrue(file_name.equals(data.getFileName()));
		assertTrue(title.equals(data.getTitle()));
		assertTrue(artist.equals(data.getInterpret()));
		assertTrue(album.equals(data.getAlbum()));
		assertTrue(year == data.getYear());
		assertTrue(track == data.getTitleNumber());
		assertTrue(genre.equals(data.getGenreDescription()));
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#toString()}.
	 */
	@Test
	void testToString()
	{
		String file_name = "file naem";
		String album = "album";
		String artist = "artist";
		int year = 2005;
		int track = 5;
		String genre_description = "";
		String title = "tititle";
		
		TagData data = new TagData(file_name, title, artist, album, year, track, genre_description);
		
		String expected = new StringBuilder()
						.append("{ ")
						.append("file_name: ")
						.append(file_name)
						.append(", title: ")
						.append(title)
						.append(", interpret: ")
						.append(artist)
						.append(", album: ")
						.append(album)
						.append(", year: ")
						.append(year)
						.append(", title_number: ")
						.append(track)
						.append(", genre_description: ")
						.append(genre_description)
						.append(" }").toString();
		
		assertTrue(expected.equals(data.toString()));
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#toFullString()}.
	 */
	@Test
	void testToFullString()
	{
		String file_name = "file naem";
		String album = "album";
		String artist = "artist";
		int year = 2005;
		int track = 5;
		String genre_description = "";
		String title = "tititle";
		
		TagData data = new TagData(file_name, title, artist, album, year, track, genre_description);
		
		String expected = new StringBuilder()
						.append("{ ")
						.append("file_name: ")
						.append(file_name)
						.append(", title: ")
						.append(title)
						.append(", interpret: ")
						.append(artist)
						.append(", album: ")
						.append(album)
						.append(", year: ")
						.append(year)
						.append(", title_number: ")
						.append(track)
						.append(", genre_description: ")
						.append(genre_description)
						.append(" }").toString();
		
		assertTrue(expected.equals(data.toFullString()));
	}

	/**
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#compareTo(de.yehoudie.tagman.objects.TagData)}.
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
	 * Test method for {@link de.yehoudie.tagman.objects.TagData#equals(java.lang.Object)}.
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
}
