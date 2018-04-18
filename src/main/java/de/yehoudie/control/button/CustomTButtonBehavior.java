package de.yehoudie.control.button;

import de.yehoudie.types.CssPseudoClasses;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;

/**
 * Behavior of a CustomButton:<br>
 * Mouse and key handler.
 * 
 * @author yehoudie
 *
 * @param	<C> the controller type extending CustomTButton
 */
public abstract class CustomTButtonBehavior<C extends CustomTButton> extends CustomButtonBehavior<C>
{
	private ObservableSet<PseudoClass> states;
//	private SetChangeListener<PseudoClass> state_change_listener;
	
	/**
	 * Behavior of a CustomButton:<br>
	 * Mouse and key handler.
	 * 
	 * @param	control C the controller extending CustomButton
	 */
	public CustomTButtonBehavior(C control)
	{
		super(control);
		
//		init(); // called in super
	}
	
	@Override
	protected void init()
	{
		states = this.control.getPseudoClassStates();
		super.init();
	}
	
	/**
	 * Handle a css pseudo class state change
	 * 
	 * @param 	change Change<? extends PseudoClass> the changed class
	 */
	protected void handlePseudoClassStateChange(Change<? extends PseudoClass> change)
	{
		if ( change.wasAdded() )
		{
			handlePseudoClassStateChange(change.getElementAdded(), true);
		}
		else if ( change.wasRemoved() )
		{
			handlePseudoClassStateChange(change.getElementRemoved(), false);
		}
	}

	/**
	 * Handle a css pseudo class state change
	 * 
	 * @param 	change Change<? extends PseudoClass> the changed class
	 * @param	was_added boolean states if a pseudo class has been added or removed
	 */
	protected void handlePseudoClassStateChange(PseudoClass pseudo_class, boolean was_added)
	{
		if ( pseudo_class.equals(CssPseudoClasses.ACTIVE_PSEUDO_CLASS) )
		{
			tween(was_added);
		}
	}

	/**
	 * Activate all listener.
	 */
	@Override
	protected void activate()
	{
		super.activate();
		states.addListener((Change<? extends PseudoClass> change)->handlePseudoClassStateChange(change));
	}
	
	/**
	 * Deactivate all listener.
	 */
	@Override
	protected void deactivate()
	{
		super.deactivate();
		states.removeListener((Change<? extends PseudoClass> change)->handlePseudoClassStateChange(change));
	}

	@Override
	protected void mouseEntered(MouseEvent e)
	{
		super.mouseEntered(e);
		tween(true);
	}

	@Override
	protected void mouseExited(MouseEvent e)
	{
		super.mouseExited(e);
		tween(false);
	}

	/**
	 * Tween the button.
	 * 
	 * @param	dir boolean tween direction
	 */
	public abstract void tween(boolean dir);
	
	@Override
	public void dispose()
	{
		super.dispose();
	}
}
