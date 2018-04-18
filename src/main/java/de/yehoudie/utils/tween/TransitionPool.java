package de.yehoudie.utils.tween;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.animation.Transition;
import javafx.scene.Node;

public class TransitionPool<O extends Node, T extends Transition>
{
	protected HashMap<O, T> transitions;
	
	/**
	 * Tween pool.<br>
	 * Stores all tweens in a map, to handle concurent tweens on the same object.
	 */
	public TransitionPool()
	{
		transitions = new HashMap<O, T>();
	}

	/**
	 * Get a transition out of the map.<br>
	 * Stop it, if present.
	 */
	protected Transition get(O object)
	{
		Transition t = transitions.get(object);
		if ( t != null )
		{
			t.stop();
		}
		
		return t;
	}

	/**
	 * Play the transition and callback, when finished.<br>
	 * Remove finished tweens from the map.<br>
	 * TODO: use TransitionCallback to make it more generall, remove dir. 
	 * 
	 * @param	object O the tweened object
	 * @param	t T the transtion
	 * @param	dir boolean
	 * @param	time int the tween time
	 * @param	onFinished TweenCallback
	 */
	protected void play(O object, T t, boolean dir, int time, final TweenCallback onFinished)
	{
//		boolean running = t.getStatus() == Status.RUNNING;
		transitions.put(object, t);
		
		t.play();
		t.setOnFinished(e->{
			transitions.remove(object);
			if (onFinished!=null)onFinished.call(object, dir);
		});
	}
	
	/**
	 * Clean up.
	 */
	public void dispose()
	{
		for ( Entry<O, T> entry : transitions.entrySet() )
		{
			entry.getValue().stop();
		}
		transitions.clear();
	}
}
