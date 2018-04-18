package de.yehoudie.utils.files;

import java.io.File;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class FileEvent extends Event
{
	private static final long serialVersionUID = 1L;
	
	public static final EventType<FileEvent> ANY = new EventType<FileEvent>("ANY");
	public static final EventType<FileEvent> CHANGED = new EventType<FileEvent>(ANY, "de.yehoudie.utils.files.FileEvent.CHANGED");

	File file;
	
	public FileEvent()
	{
		this(ANY);
	}

	public FileEvent(EventType<? extends Event> type)
	{
		super(type);
	}
	
	public FileEvent(File file, EventType<? extends Event> type)
	{
		super(type);
		this.file = file;
	}

	public FileEvent(Object source, EventTarget target, EventType<? extends Event> type)
	{
		super(source, target, type);
	}
	
	public File getFile()
	{
		return file;
	}
}
