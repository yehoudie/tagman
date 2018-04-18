package de.yehoudie.utils.event;

import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EventWood
{
	/**
	 * get wood for a mouse fireEvent
	 * 
	 * @param	type EventType<? extends MouseEvent> the event type
	 * @return	MouseEvent
	 */
	public static MouseEvent getMouseWood(EventType<? extends MouseEvent> type)
	{
		return new MouseEvent(null, null, type, 0, 0, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, false, false, false, false, false, false, null);
	}
	
	/**
	 * get wood for a mouse fireEvent
	 * 
	 * @param	source Object origin of the event
	 * @param	target EventTarget node on which the action occurred and the end node in the event dispatch chain. 
	 * @param	type EventType<? extends MouseEvent> the event type
	 * @return	MouseEvent
	 */
	public static MouseEvent getMouseWood(Object source, EventTarget target, EventType<? extends MouseEvent> type)
	{
		return new MouseEvent(source, target, type, 0, 0, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, true, false, false, false, false, false, null);
	}
}
