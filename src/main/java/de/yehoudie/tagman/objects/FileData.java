package de.yehoudie.tagman.objects;

import java.io.File;
import java.util.Comparator;

import org.w3c.dom.Element;

import de.yehoudie.utils.EqualsUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileData implements Comparable<FileData>
{
	public static final String NAME = "name";
	public static final String FILE = "file";
	
	private StringProperty name = new SimpleStringProperty(this, NAME);
	public StringProperty nameProperty() { return name; };
	public String getName() { return name.get(); };
	public void setName(String value) { name.set(value); };
	
	private ObjectProperty<File> file = new SimpleObjectProperty<File>(this, FILE);
	public ObjectProperty<File> fileProperty() { return file; };
	public File getFile() { return file.get(); };
	public void setFile(File value) { file.set(value); };
	
	public FileData()
	{
	}
	
	public FileData(String name, File file)
	{
		this.name.set(name);
		this.file.set(file);
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
				.append("name: ")
				.append(name.get())
				.append(", file: ")
				.append(file.get())
				.append(" }").toString();
	}
	
	/**
	 * Comarator by title.
	 */
	public static Comparator<FileData> COMPARATOR = new Comparator<FileData>()
	{
		public int compare(FileData one, FileData other)
		{
			return one.compareTo(other);
		}
	};
	
	@Override
	public int compareTo(FileData other)
	{
		int value = COMPARE_BY_TYPE.compare(this, other);
		if ( value == 0 ) value = name.get().toLowerCase().compareTo(other.name.get().toLowerCase());;
		if ( value == 0 ) value = file.get().compareTo(other.file.get());
		
		return value;
	}
	
	/**
	 * Comarator by type.<br>
	 * To use in {@code Arrays.sort(object, COMPARE_BY_TYPE)}.
	 */
	private static Comparator<FileData> COMPARE_BY_TYPE = new Comparator<FileData>()
	{
		@Override
		public int compare(FileData one, FileData other)
		{
			int value = 0;
			if ( one.getFile().isDirectory() && other.getFile().isDirectory() ) value = 0;
			else if ( one.getFile().isDirectory() && !other.getFile().isDirectory() ) value = -1;
			else if ( !one.getFile().isDirectory() && other.getFile().isDirectory() ) value = 1;
			
			return value;
		}
	};
	
	/**
	 * Check for equality
	 */
	@Override
	public boolean equals(Object obj)
	{
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( obj.getClass() != this.getClass() ) return false;
	 
		FileData that = (FileData) obj;

		return 	EqualsUtil.areEqual(this.name.get(), that.name.get()) &&
				EqualsUtil.areEqual(this.file.get(), that.file.get());
	}

	public boolean isFile()
	{
		return file.get() != null && file.get().isFile();
	}
}
