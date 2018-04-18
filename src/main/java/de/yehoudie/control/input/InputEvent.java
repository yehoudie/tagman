package de.yehoudie.control.input;

import java.io.File;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class InputEvent extends Event
{
	private static final long serialVersionUID = 1L;
	
	public static final EventType<InputEvent> ANY = new EventType<InputEvent>("ANY");
	public static final EventType<InputEvent> FOCUS_REQUEST = new EventType<InputEvent>(ANY, "de.yehoudie.control.input.InputEvent.FOCUS_REQUEST");
	public static final EventType<InputEvent> FILE_SELECTED = new EventType<InputEvent>(ANY, "de.yehoudie.control.input.InputEvent.FILE_SELECTED");

	File file;
	
	public InputEvent()
	{
		this(ANY);
	}

	public InputEvent(EventType<? extends Event> type)
	{
		super(type);
	}
	
	public InputEvent(File file, EventType<? extends Event> type)
	{
		super(type);
		this.file = file;
	}

	public InputEvent(Object source, EventTarget target, EventType<? extends Event> type)
	{
		super(source, target, type);
	}
	
	public File getFile()
	{
		return file;
	}
}
