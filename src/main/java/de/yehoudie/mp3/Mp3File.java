/**
 * 
 */
package de.yehoudie.mp3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.yehoudie.tagman.objects.TagData;

public class Mp3File
{
	private ID3v1 v1tag;
	private com.mpatric.mp3agic.Mp3File mp3;
	private File file;

	public Mp3File(File file) throws UnsupportedTagException, InvalidDataException, IOException
	{
		this.file = file;
		this.mp3 = new com.mpatric.mp3agic.Mp3File(file);

		if ( mp3.hasId3v2Tag() ) v1tag = mp3.getId3v2Tag();
		else if ( mp3.hasId3v1Tag() ) v1tag = mp3.getId3v1Tag();
		
		if (v1tag == null ) throw new NullPointerException();
	}

	public String getFileName()
	{
		return mp3.getFilename();
	}

	public String getTitle()
	{
		return v1tag.getTitle();
	}
	
	public String getArtist()
	{
		return v1tag.getArtist();
	}

	public String getAlbum()
	{
		return v1tag.getAlbum();
	}

	public String getYearValue()
	{
		return v1tag.getYear();
	}

	public int getYear()
	{
		return get0orValule(v1tag.getYear());
	}

	public String getTrackValue()
	{
		return v1tag.getTrack();
	}

	public int getTrack()
	{
		return get0orValule(v1tag.getTrack());
	}
	
	public int get0orValule(String raw)
	{
		if ( raw == null ) return 0;
		
		try
		{
			int value = Integer.valueOf(raw);
			return value;
		}
		catch ( NumberFormatException e )
		{
			return 0;
		}
	}

	public String getGenreDescription()
	{
		return v1tag.getGenreDescription();
	}
	
	public void fill(TagData data)
	{
		ID3v2 id3v2Tag;
		if ( mp3.hasId3v2Tag() )
		{
			id3v2Tag = mp3.getId3v2Tag();
		}
		else
		{
			id3v2Tag = new ID3v24Tag();
			mp3.setId3v2Tag(id3v2Tag);
		}
		if (  data.getTitle() != null ) id3v2Tag.setTitle(data.getTitle());
		if (  data.getInterpret() != null ) id3v2Tag.setArtist(data.getInterpret());
		if (  data.getAlbum() != null ) id3v2Tag.setAlbum(data.getAlbum());
		if ( data.getYear() != 0 ) id3v2Tag.setYear(String.valueOf(data.getYear()));
		if ( data.getTitleNumber() != 0 ) id3v2Tag.setTrack(String.valueOf(data.getTitleNumber()));
		if (  data.getGenreDescription() != null && !data.getGenreDescription().isEmpty() ) id3v2Tag.setGenreDescription(data.getGenreDescription());
	}
	
	public void save() throws NotSupportedException, IOException
	{
		String original_file_name = file.getAbsolutePath();
		String new_file_name = getFileName();
		String tmp_file_name = new_file_name+".tmp";
		String copy_file_name = original_file_name+".cpy";

		File original_file = new File(original_file_name);
		File copy_file = new File(copy_file_name);
		File new_file = new File(new_file_name);
		File tmp_file = new File(tmp_file_name);
		
		mp3.save(tmp_file_name);
		
		Files.copy(original_file.toPath(), copy_file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		original_file.delete();
		tmp_file.renameTo(new_file);
	}
}
