package de.yehoudie.control.button;

import de.yehoudie.types.CssPseudoClasses;
import de.yehoudie.utils.tween.TweenUtil;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;

/**
 * Behavior of a TextButton:<br>
 * Mouse and key handler.
 * 
 * @author yehoudie
 *
 * @param	<C> the controller type extending TextButton
 */
public class TextButtonBehavior<C extends TextButton> extends CustomTButtonBehavior<C>
{
	/**
	 * Behavior of a CustomButton:<br>
	 * Mouse and key handler.
	 * 
	 * @param	control C the controller extending CustomButton
	 */
	public TextButtonBehavior(C control)
	{
		super(control);
		
//		init(); // called in super
	}
	
	@Override
	protected void init()
	{
		super.init();
		tween(false);
	}

	/**
	 * Tween the button.
	 * 
	 * @param	dir boolean tween direction
	 */
	@Override
	public void tween(boolean dir)
	{
		double end_a = (dir) ? control.overOpacityProperty().get() : control.outOpacityProperty().get();

		TweenUtil.alphaTween(control, end_a, control.tweenTimeProperty().get());
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
	}
}
