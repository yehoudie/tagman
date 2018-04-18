package de.yehoudie.tagman.objects;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.yehoudie.mp3.Mp3File;
import de.yehoudie.utils.EqualsUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TagData implements Comparable<TagData>
{
	public static final String FILE_NAME = "file_name";
	public static final String NEW_FILE_NAME = "new_file_name";
	public static final String TITLE = "title";
	public static final String INTERPRET = "interpret";
	public static final String ALBUM = "album";
	public static final String YEAR = "year";
	public static final String TITLE_NUMBER = "title_number";
	public static final String GENRE_DESCRIPTION = "genre_description";
	
	private StringProperty file_name = new SimpleStringProperty(this, FILE_NAME);
	public StringProperty fileNameProperty() { return file_name; };
	public String getFileName() { return file_name.get(); };
	public void setFileName(String value) { file_name.set(value); };
	
	private StringProperty new_file_name = new SimpleStringProperty(this, NEW_FILE_NAME);
	public StringProperty newFileNameProperty() { return new_file_name; };
	public String getnewFileName() { return new_file_name.get(); };
	public void setnewFFileName(String value) { new_file_name.set(value); };
	
	private StringProperty title = new SimpleStringProperty(this, TITLE);
	public StringProperty titleProperty() { return title; };
	public String getTitle() { return title.get(); };
	public void setTitle(String value) { title.set(value); };
	
	private StringProperty interpret = new SimpleStringProperty(this, INTERPRET);
	public StringProperty interpretProperty() { return interpret; };
	public String getInterpret() { return interpret.get(); };
	public void setInterpret(String value) { interpret.set(value); };
	
	private StringProperty album = new SimpleStringProperty(this, ALBUM);
	public StringProperty albumProperty() { return album; };
	public String getAlbum() { return album.get(); };
	public void setAlbum(String value) { album.set(value); };
	
	private IntegerProperty year = new SimpleIntegerProperty(this, YEAR);
	public IntegerProperty yearProperty() { return year; };
	public int getYear() { return year.get(); };
	public void setYear(int value) { year.set(value); };
	
	private IntegerProperty title_number = new SimpleIntegerProperty(this, TITLE_NUMBER);
	public IntegerProperty titleNumberProperty() { return title_number; };
	public int getTitleNumber() { return title_number.get(); };
	public void setTitleNumber(int value) { title_number.set(value); };
	
	private StringProperty genre_description = new SimpleStringProperty(this, GENRE_DESCRIPTION);
	public StringProperty genreDescriptionProperty() { return genre_description; };
	public String getGenreDescription() { return genre_description.get(); };
	public void setGenreDescription(String value) { genre_description.set(value); };
	
	public TagData()
	{
	}
	
	public TagData(String file_name, String title, String interpret, String album, int year, int title_number, String genre)
	{
		this.file_name.set(file_name);
		this.title.set(title);
		this.interpret.set(interpret);
		this.album.set(album);
		this.year.set(year);
		this.title_number.set(title_number);
		this.genre_description.set(genre);
	}
	
	@Override
	public String toString()
	{
		return toFullString();
	}
	
	public String toFullString()
	{
		return new StringBuilder()
				.append("{ ")
				.append("file_name: ")
				.append(file_name.get())
				.append(", title: ")
				.append(title.get())
				.append(", interpret: ")
				.append(interpret.get())
				.append(", album: ")
				.append(album.get())
				.append(", year: ")
				.append(year.get())
				.append(", title_number: ")
				.append(title_number.get())
				.append(", genre_description: ")
				.append(genre_description.get())
				.append(" }").toString();
	}
	
	public void fill(File file) throws UnsupportedTagException, InvalidDataException, IOException
	{
		System.out.println("EntryData.fill("+file+")");
		Mp3File mp3file = new Mp3File(file);
		
		this.file_name.set(mp3file.getFileName());
		this.title.set(mp3file.getTitle());
		this.interpret.set(mp3file.getArtist());
		this.album.set(mp3file.getAlbum());
		if ( mp3file.getYearValue() != null ) this.year.set(mp3file.getYear());
		if ( mp3file.getTrackValue() != null ) this.title_number.set(mp3file.getTrack());
		this.genre_description.set(mp3file.getGenreDescription());
	}
	
	/**
	 * Comarator by title.
	 */
	public static Comparator<TagData> COMPARATOR = new Comparator<TagData>()
	{
		public int compare(TagData one, TagData other)
		{
			return one.compareTo(other);
		}
	};
	
	@Override
	public int compareTo(TagData other)
	{
		int value = title.get().compareTo(other.title.get());;
		if ( value == 0 ) value = file_name.get().compareTo(other.file_name.get());
		if ( value == 0 ) value = interpret.get().compareTo(other.interpret.get());
		if ( value == 0 ) value = album.get().compareTo(other.album.get());
		if ( value == 0 ) value = year.getValue().compareTo(other.year.getValue());
		if ( value == 0 ) value = genre_description.get().compareTo(other.genre_description.get());
		if ( value == 0 ) value = title_number.getValue().compareTo(other.title_number.getValue());
		
		return value;
	}
	
	/**
	 * Check for equality
	 */
	@Override
	public boolean equals(Object obj)
	{
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( !( obj instanceof TagData) ) return false;
	 
		TagData that = (TagData) obj;

		return	this.title_number.get() == that.title_number.get() &&
				EqualsUtil.areEqual(this.file_name.get(), that.file_name.get()) &&
				EqualsUtil.areEqual(this.title.get(), that.title.get()) &&
				EqualsUtil.areEqual(this.interpret.get(), that.interpret.get()) &&
				EqualsUtil.areEqual(this.album.get(), that.album.get()) &&
				this.year.get() == that.year.get();
	}
}
