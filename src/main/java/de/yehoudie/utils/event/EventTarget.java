package de.yehoudie.utils.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * custon class that implements EventTarget.
 * It may be extended to get rid of the event methods in the sub classes.
 */
public class EventTarget implements javafx.event.EventTarget
{
	// TODO: check merge
// 	@SuppressWarnings("rawtypes")
// 	private final Map<EventType<? extends Event>, Collection<EventHandler>> handlers = new HashMap<>();

// 	public final <T extends Event> void addEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler)
	// ---
	@SuppressWarnings("rawtypes")
	private final Map<EventType, Collection<EventHandler>> handlers = new HashMap<>();
	public final <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler)
	{
		handlers.computeIfAbsent(eventType, (k) -> new ArrayList<>()).add(eventHandler);
	}

	public final <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler)
	{
		handlers.computeIfPresent(eventType, (k, v) -> {
			v.remove(eventHandler);
			return v.isEmpty() ? null : v;
		});
	}

	@Override
	public final EventDispatchChain buildEventDispatchChain(EventDispatchChain tail)
	{
		return tail.prepend(this::dispatchEvent);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handleEvent(Event event, Collection<EventHandler> handlers)
	{
		if ( handlers != null )
		{
			handlers.forEach(handler -> handler.handle(event));
		}
	}

	private Event dispatchEvent(Event event, EventDispatchChain tail)
	{
		// go through type hierarchy and trigger all handlers
		EventType<?> type = event.getEventType();
		// TODO: check merge
// 		EventType<? extends Event> type = event.getEventType();
		// ---
		while ( type != Event.ANY )
		{
			handleEvent(event, handlers.get(type));
			type = type.getSuperType();
		}
		handleEvent(event, handlers.get(Event.ANY));
		return event;
	}

	public void fireEvent(Event event)
	{
		Event.fireEvent(this, event);
	}

}
