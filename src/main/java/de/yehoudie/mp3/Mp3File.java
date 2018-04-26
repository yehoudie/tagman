package de.yehoudie.mp3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.mpatric.mp3agic.AbstractID3v2Tag;
import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.utils.files.FileUtil;

public class Mp3File
{
	private ID3v2 tag;
	private com.mpatric.mp3agic.Mp3File mp3;
	private File file;
	private String new_file_name;

	public static String[] GENRES;
	static {
		int genres_ln = ID3v1Genres.GENRES.length + 1;
		GENRES = new String[genres_ln];
		GENRES[0] = "NONE";
		for ( int i = 1; i < genres_ln; i++ )
		{
			GENRES[i] = ID3v1Genres.GENRES[i-1];
		}
	}
	
	public Mp3File(File file) throws UnsupportedTagException, InvalidDataException, IOException
	{
		this.file = file;
		this.mp3 = new com.mpatric.mp3agic.Mp3File(file);

		if ( mp3.hasId3v2Tag() ) tag = mp3.getId3v2Tag();
		else
		{
			tag = new ID3v24Tag();
			mp3.setId3v2Tag(tag);
		}
		
		if ( tag == null ) throw new NullPointerException();
	}

	public String getFileName()
	{
		return mp3.getFilename();
	}
	
	public String getNewFileName()
	{
		return new_file_name;
	}

	public String getTitle()
	{
		return tag.getTitle();
	}
	
	public String getArtist()
	{
		return tag.getArtist();
	}

	public String getAlbum()
	{
		return tag.getAlbum();
	}

	public String getYearValue()
	{
		return tag.getYear();
	}

	public int getYear()
	{
		return get0orValule(tag.getYear());
	}

	public String getTrackValue()
	{
		return tag.getTrack();
	}

	public int getTrack()
	{
		return get0orValule(tag.getTrack());
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
		return tag.getGenreDescription();
	}
	
	public void fill(TagData data)
	{
		System.out.println("Mp3File.fillData()");
		System.out.println(" - data: "+data);
//		ID3v2 id3v2Tag;
//		if ( mp3.hasId3v2Tag() )
//		{
//			id3v2Tag = mp3.getId3v2Tag();
//		}
//		else
//		{
//			mp3.setId3v2Tag(id3v2Tag);
//		}
		
		ID3v2 new_tag = tag;
//		ID3v2 new_tag = new ID3v24Tag();
//		copy(tag, new_tag);
		
		new_file_name = data.getNewFileName();
		new_tag.setTitle(data.getTitle());
		new_tag.setArtist(data.getInterpret());
		new_tag.setAlbum(data.getAlbum());
		new_tag.setYear(nullOrValue(data.getYear()));
		new_tag.setTrack(nullOrValue(data.getTitleNumber()));
		if ( isGenreSet(data) ) new_tag.setGenreDescription(data.getGenreDescription());;
		
//		mp3.setId3v2Tag(new_tag);
	}
	
	private boolean isGenreSet(TagData data)
	{
		return data.getGenreDescription() != null && !data.getGenreDescription().isEmpty() && !data.getGenreDescription().equals(GENRES[0]);
	}

	private void copy(ID3v2  from, ID3v2 to)
	{
//		to.setAlbum(from.getAlbum());
		to.setAlbumArtist(from.getAlbumArtist());
		to.setAlbumImage(from.getAlbumImage(), from.getAlbumImageMimeType());
//		to.setArtist(from.getArtist());
		to.setArtistUrl(from.getArtistUrl());
		to.setAudiofileUrl(from.getAudiofileUrl());
		to.setAudioSourceUrl(from.getAudioSourceUrl());
		to.setBPM(from.getBPM());
		to.setChapters(from.getChapters());
		to.setChapterTOC(from.getChapterTOC());
		to.setComment(from.getComment());
		to.setCommercialUrl(from.getCommercialUrl());
//		to.setCompilation(from.getCompilation());
		to.setComposer(from.getComposer());
		to.setCopyright(from.getCopyright());
		to.setCopyrightUrl(from.getCopyrightUrl());
		to.setDate(from.getDate());
		to.setEncoder(from.getEncoder());
//		to.setFooter(from.getFooter());
//		to.setGenre(from.getGenre());
//		to.setGenreDescription(from.getGenreDescription());
		to.setKey(from.getKey());
		to.setLyrics(from.getLyrics());
		to.setOriginalArtist(from.getOriginalArtist());
		to.setPadding(from.getPadding());
		to.setPartOfSet(from.getPartOfSet());
		to.setPaymentUrl(from.getPaymentUrl());
		to.setPublisher(from.getPublisher());
		to.setPublisherUrl(from.getPublisherUrl());
		to.setRadiostationUrl(from.getRadiostationUrl());
//		to.setTitle(from.getTitle());
//		to.setTrack(from.getTrack());
//		to.setUnsynchronisation(from.getun);
		to.setUrl(from.getUrl());
		to.setWmpRating(from.getWmpRating());
//		to.setYear(from.getYear());
	}

	private String nullOrValue(int value)
	{
		return ( value == 0 ) ? null : String.valueOf(value);
	}
	
	private String nullOrValue(String value)
	{
		return ( value == null || value.isEmpty() ) ? "" : value;
	}

	public void save() throws NotSupportedException, IOException
	{
		String original_file_name = file.getAbsolutePath();
		
		String new_file_path;
		if ( new_file_name != null ) new_file_path = file.getParent() +"/"+ getNewFileName() + "."+ FileUtil.getType(file);  
		else new_file_path = getFileName();
		String tmp_file_name = new_file_path+".tmp";
		String copy_file_name = original_file_name+".cpy";

		File original_file = new File(original_file_name);
		File copy_file = new File(copy_file_name);
		File new_file = new File(new_file_path);
		File tmp_file = new File(tmp_file_name);
		
		mp3.save(tmp_file_name);
		
		Files.copy(original_file.toPath(), copy_file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		original_file.delete();
		tmp_file.renameTo(new_file);
		
		new_file_name = new_file.getAbsolutePath();
		System.out.println("Mp3File.save()");
		System.out.println(" - new_file_name: "+getNewFileName());
	}
}
