package de.yehoudie.utils.system;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class SystemActionEvent extends Event
{
	private static final long serialVersionUID = 1L;
	
	public static final EventType<SystemActionEvent> ANY = new EventType<SystemActionEvent>("de.yehoudie.utils.system.SystemActionEvent.ANY");
	
	public static final EventType<SystemActionEvent> FILE_OPENED = new EventType<SystemActionEvent>(ANY, "de.yehoudie.utils.system.SystemActionEvent.FILE_OPENED");
	public static final EventType<SystemActionEvent> FILE_OPENED_WHILE_OFF = new EventType<SystemActionEvent>(FILE_OPENED, "de.yehoudie.utils.system.SystemActionEvent.FILE_OPENED_WHILE_OFF");
	public static final EventType<SystemActionEvent> FILE_OPENED_WHILE_RUNNING = new EventType<SystemActionEvent>(FILE_OPENED, "de.yehoudie.utils.system.SystemActionEvent.FILE_OPENED_WHILE_RUNNING");
	public static final EventType<SystemActionEvent> FILE_DROPPED_WHILE_RUNNING = new EventType<SystemActionEvent>(FILE_OPENED_WHILE_RUNNING, "de.yehoudie.utils.system.SystemActionEvent.FILE_OPENED_DROPPED_RUNNING");
	
	public static final EventType<SystemActionEvent> QUIT_REQUEST = new EventType<SystemActionEvent>(ANY, "de.yehoudie.utils.system.SystemActionEvent.QUIT_REQUEST");
	public static final EventType<SystemActionEvent> DOC_QUIT_REQUEST = new EventType<SystemActionEvent>(QUIT_REQUEST, "de.yehoudie.utils.system.SystemActionEvent.DOC_QUIT_REQUEST");
	public static final EventType<SystemActionEvent> MENU_QUIT_REQUEST = new EventType<SystemActionEvent>(QUIT_REQUEST, "de.yehoudie.utils.system.SystemActionEvent.MENU_QUIT_REQUEST");

	public SystemActionEvent()
	{
		this(ANY);
	}

	public SystemActionEvent(EventType<? extends Event> type)
	{
		super(type);
	}

	public SystemActionEvent(Object source, EventTarget target, EventType<? extends Event> type)
	{
		super(source, target, type);
	}
}
