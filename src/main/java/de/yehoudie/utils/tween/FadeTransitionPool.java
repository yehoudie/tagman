package de.yehoudie.utils.tween;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeTransitionPool<O extends Node> extends TransitionPool<O, FadeTransition>
{
	/**
	 * tween pool with fade transitions.
	 */
	public FadeTransitionPool()
	{
		super();
	}

	/**
	 * start a fade transition.
	 * if there is an old one with the target obect, stop and reuse it.
	 * 
	 * @param	object O the tweened object
	 * @param	dir boolean
	 * @param	time int the tween time
	 * @param	onFinished TweenCallback
	 */
	public void fade(O object, boolean dir, int time, final TweenCallback onFinished)
	{
		FadeTransition ft = (FadeTransition) get(object);
		if ( ft == null )
		{
			ft = new FadeTransition(Duration.millis(time), object);
		}
		ft.setFromValue(object.getOpacity());
		ft.setToValue((dir)?1:0);
		
		play(object, ft, dir, time, onFinished);
	}
}
