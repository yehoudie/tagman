package de.yehoudie.control.button;

import de.yehoudie.callback.MouseEventCallback;
import de.yehoudie.control.BehaviorBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Behavior of a custom button.
 * 
 * @author yehoudie
 *
 * @param	<C> the controller type extending CustomButton
 */
public class CustomButtonBehavior<C extends CustomButton> extends BehaviorBase<C>
{
	protected C control;
	
	protected BooleanProperty is_mouse_over = new SimpleBooleanProperty(this, "is_mouse over");
	protected ObjectProperty<KeyCode> short_cut = new SimpleObjectProperty<>(this, "short_cut");
	protected ObjectProperty<Scene> key_target = new SimpleObjectProperty<>(this, "key_target");
	protected ObjectProperty<CustomToggleGroup> toggle_group = new SimpleObjectProperty<>(this, "toggle_group");
	protected ObjectProperty<MouseEventCallback> clickCallback = new SimpleObjectProperty<>(this, "clickCallback");
	
	/**
	 * Behavior of a custom button.
	 * 
	 * @param	control C the CustomButton controller
	 */
	public CustomButtonBehavior(C control)
	{
		super(control);
		this.control = control;
		
		init();
	}
	
	protected void init()
	{
		bindProperties();
		activate();
	}

	protected void bindProperties()
	{
		is_mouse_over.bindBidirectional(this.control.mouseOverProperty());
		key_target.bind(this.control.key_target);
		short_cut.bind(this.control.short_cut);
		short_cut.addListener(this::handleShortCutChange);
		toggle_group.bind(this.control.toggle_group);
		clickCallback.bind(this.control.clickCallback);
	}

	protected void handleShortCutChange(ObservableValue<? extends KeyCode> observable, KeyCode old_v, KeyCode new_v)
	{
		if ( new_v != null )
		{
			addKeyListener();
		}
	}
	
	protected void unbindProperties()
	{
		is_mouse_over.unbindBidirectional(this.control.mouseOverProperty());
		short_cut.unbind();
		key_target.unbind();
	}

	/**
	 * Activate all listener.
	 */
	protected void activate()
	{
		this.control.addEventHandler(MouseEvent.MOUSE_ENTERED, this::mouseEntered);
		this.control.addEventHandler(MouseEvent.MOUSE_EXITED, this::mouseExited);
		this.control.addEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressed);
		this.control.addEventHandler(MouseEvent.MOUSE_RELEASED, this::mouseReleased);
		this.control.addEventHandler(MouseEvent.MOUSE_CLICKED, this::mouseClicked);
		
		if ( this.short_cut.get() != null ) addKeyListener();
	}
	
	/**
	 * Deactivate all listener.
	 */
	protected void deactivate()
	{
		this.control.removeEventHandler(MouseEvent.MOUSE_ENTERED, this::mouseEntered);
		this.control.removeEventHandler(MouseEvent.MOUSE_EXITED, this::mouseExited);
		this.control.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressed);
		this.control.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::mouseReleased);
		this.control.removeEventHandler(MouseEvent.MOUSE_CLICKED, this::mouseClicked);
		
		removeKeyListener();
	}

	/**
	 * Add key listener.
	 */
	protected void addKeyListener()
	{
		if ( key_target.get() == null ) return;
		key_target.get().addEventHandler(KeyEvent.KEY_PRESSED, this::keyHandler);
	}

	/**
	 * Remove the key listener.
	 */
	protected void removeKeyListener()
	{
		if ( key_target.get() == null ) return;
		key_target.get().removeEventHandler(KeyEvent.KEY_PRESSED, this::keyHandler);
	}
	
	protected void mouseEntered(MouseEvent e)
	{
		is_mouse_over.set(true);
	}
	
	protected void mouseExited(MouseEvent e)
	{
		is_mouse_over.set(false);
	}
	
	protected void mousePressed(MouseEvent e)
	{
		this.control.arm();
	}
	
	protected void mouseReleased(MouseEvent e)
	{
		this.control.disarm();
	}
	
	protected void mouseClicked(MouseEvent e)
	{
		System.out.println("CustomButtonBehavior.mouseClicked("+e+")");
		MouseButton button = e.getButton();

		if ( button == MouseButton.PRIMARY )
		{
			if ( clickCallback.get() != null ) clickCallback.get().call(e);
			if ( toggle_group.get() != null ) toggle_group.get().select(((CustomButton) e.getSource()));
		}
	}

	/**
	 * Handler for key events.
	 * 
	 * @param e KeyEvent
	 */
	protected void keyHandler(KeyEvent e)
	{
		// System.out.println("CustomButton.onKeyHandler("+e+")");
		// System.out.println(" - e.getCode(): "+e.getCode());
		// System.out.println(" - short_cut: "+_short_cut);
		// System.out.println(" - e.getCode() == _short_cut: "+(e.getCode() == _short_cut));
		if ( e.getCode() == short_cut.get() )
		{
			 this.control.requestFocus();
			 this.control.fireEvent(new MouseEvent(null, null, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, false, false, false, false, false, false, null));
		}
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		unbindProperties();
	}

	/* (non-Javadoc)
	 * @see com.sun.javafx.scene.control.behavior.BehaviorBase#getInputMap()
	 */
	/*@Override
	public InputMap<C> getInputMap()
	{
		// TODO Auto-generated method stub
		return null;
	}*/
}
