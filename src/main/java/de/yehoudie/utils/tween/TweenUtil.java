package de.yehoudie.utils.tween;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * miscellaneous util functions
 * 
 * @author kaptnabak
 *
 */
public class TweenUtil
{
	/**
	 * alpha tween wrapper with callback when finished
	 * 
	 * @param	target Node the target to tween
	 * @param	dir boolen true: tween in, false: tween out
	 * @param	tween_time int  the tween time in ms
	 * @param	onFinished TweenCallback an callback function on complete
	 */
	public static void alphaTween(final Node target, final boolean dir, final int tween_time, final TweenCallback onFinished)
	{
		float end_a = (dir) ? 1 : 0;

		FadeTransition ft = new FadeTransition(Duration.millis(tween_time), target);
		ft.setFromValue(target.getOpacity());
		ft.setToValue(end_a);
//		ft.setCycleCount(Timeline.INDEFINITE);
//		ft.setAutoReverse(true);
		ft.play();

		if ( onFinished != null )
		{
			ft.setOnFinished((ActionEvent e)->onFinished.call(target, dir));
		}
	}
	
	/**
	 * alpha tweens wrapper
	 * 
	 * @param	target Node the target to tween
	 * @param	dir boolen true: tween in, false: tween out
	 * @param	tween_time int  the tween time in ms
	 * @return	FadeTransition the transition object
	 */
	public static FadeTransition alphaTween(final Node target, final boolean dir, final int tween_time)
	{
		float end_a = (dir) ? 1 : 0;
		
		FadeTransition ft = new FadeTransition(Duration.millis(tween_time), target);
		ft.setFromValue(target.getOpacity());
		ft.setToValue(end_a);
//		ft.setCycleCount(Timeline.INDEFINITE);
//		ft.setAutoReverse(true);
		ft.play();

		return ft;
	}
	
	/**
	 * alpha tweens wrapper
	 * 
	 * @param	target Node the target to tween
	 * @param	end_a float the end alpha value 
	 * @param	tween_time int  the tween time in ms
	 * @return	FadeTransition the transition object
	 */
	public static FadeTransition alphaTween(final Node target, double end_a, final int tween_time)
	{
		FadeTransition ft = new FadeTransition(Duration.millis(tween_time), target);
		ft.setFromValue(target.getOpacity());
		ft.setToValue(end_a);
//		ft.setCycleCount(Timeline.INDEFINITE);
//		ft.setAutoReverse(true);
		ft.play();
		
		return ft;
	}
	
	/**
	 * x,y tweens wrapper
	 * 
	 * @param	target Node the target to tween
	 * @param	dir boolen true: tween in, false: tween out
	 * @param	tween_time int  the tween time in ms
	 * @param	onFinished TweenCallback an callback function on complete
	 * @param	FadeTransition the transition object
	 */
	public static TranslateTransition tweenTo(final Node target, final Point2D to, final int tween_time, final TweenCallback onFinished)
	{
		TranslateTransition tt = new TranslateTransition(Duration.millis(tween_time), target);
//		tt.setFromX(50);
		tt.setToX(to.getX());
		tt.setToY(to.getY());
//		ft.setCycleCount(Timeline.INDEFINITE);
//		ft.setAutoReverse(true);
		tt.play();
		
		if ( onFinished != null )
		{
			tt.setOnFinished(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent e)
				{
					onFinished.call(target, true);
				}
			});
		}
		
		return tt;
	}
	
	/**
	 * x,y tweens wrapper
	 * 
	 * @param	target Node the target to tween
	 * @param	dir boolen true: tween in, false: tween out
	 * @param	tween_time int  the tween time in ms
	 * @param	onFinished TweenCallback an callback function on complete
	 * @param	FadeTransition the transition object
	 */
	public static TranslateTransition tweenTo(final Node target, final Point2D to, final int tween_time)
	{
		TranslateTransition tt = new TranslateTransition(Duration.millis(tween_time), target);
//		tt.setFromX(50);
		tt.setToX(to.getX());
		tt.setToY(to.getY());
//		ft.setCycleCount(Timeline.INDEFINITE);
//		ft.setAutoReverse(true);
		tt.play();
		
		return tt;
	}
}
