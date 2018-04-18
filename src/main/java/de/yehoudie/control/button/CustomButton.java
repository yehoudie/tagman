package de.yehoudie.control.button;

import de.yehoudie.callback.MouseEventCallback;
import de.yehoudie.utils.event.EventWood;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Custom Button extending ButtonBase
 */
public abstract class CustomButton extends ButtonBase
{
//	private final static PseudoClass ARMED_PSEUDO_STAGE = PseudoClass.getPseudoClass("armed");
	private static final String DEF_STYLE_CLASS = "custom-button";

	protected int button_id;
	protected boolean is_active;
	
	protected BooleanProperty is_mouse_over = new SimpleBooleanProperty(this, "is_mouse over");
	protected BooleanProperty mouseOverProperty() { return is_mouse_over; };
	protected boolean isMouseOver() { return is_mouse_over.get(); };
	
	protected ObjectProperty<MouseEventCallback> clickCallback = new SimpleObjectProperty<>(this, "clickCallback");
	/**
	 * Get the callback, that is called, when the button is clicked.
	 * 
	 * @return ButtonCallback the onClickCallback
	 */
	public MouseEventCallback getClickCallback() { return clickCallback.get(); }
	/**
	 * Set the callback, that is called, when the button is clicked.<br>
	 * Prevents to another EventHandler.
	 * 
	 * @param clickCallback ButtonCallback the callback to set to set
	 */
	public void setClickCallback(MouseEventCallback clickCallback) { this.clickCallback.set(clickCallback); }
	
	// protected CustomToggleGroup _toggle_group;

	// key access
	protected ObjectProperty<Scene> key_target = new SimpleObjectProperty<>(this, "key_target");
	protected ObjectProperty<KeyCode> short_cut = new SimpleObjectProperty<>(this, "short_cut");

	protected ObjectProperty<CustomToggleGroup> toggle_group = new SimpleObjectProperty<>(this, "toggle_group");
	
	/**
	 * Custom button to easy add more layers of graphic or such.
	 * TODO: implemet animation
	 */
	public CustomButton()
	{
		init();
	}
	
	/**
	 * Custom button to easy add more layers of graphic or such.
	 * TODO: implemet animation
	 * 
	 * @param	value String the text value of the button
	 */
	public CustomButton(String value)
	{
		super(value);
		init();
	}

	private void init()
	{
		getStyleClass().add(DEF_STYLE_CLASS);
	}

	/**
	 * Activate the button.
	 */
	public void activate()
	{
		setDisable(false);
	}

	/**
	 * Deactivate the button.
	 */
	public void deactivate()
	{
		setDisable(true);
	}

	/**
	 * Fire mouseOver, set active and deactivate.
	 */
	public void pseudoPress()
	{
//		this.fireEvent(EventWood.getMouseWood(MouseEvent.MOUSE_ENTERED));
//		setSelectced(true);
//		deactivate();
		if ( toggle_group != null ) toggle_group.get().select(this);
		this.fireEvent(EventWood.getMouseWood(MouseEvent.MOUSE_EXITED));
	}

	/**
	 * Clean up.
	 */
	public void dispose()
	{
		deactivate();
	}

	/**
	 * Set a integer button id for array lookup.
	 * 
	 * @param id int the id
	 */
	public void setButtonId(int id)
	{
		button_id = id;
	}

	/**
	 * Get the integer button id for array lookup.
	 * 
	 * @return int the id
	 */
	public int getButtonId()
	{
		return button_id;
	}

	/**
	 * @param boolean mouse_over the _mouse_over to set
	 */
	/*
	 * public void setMouseOver(boolean mouse_over) { _mouse_over = mouse_over; }
	 */

	/**
	 * @return	boolean the is active button state
	 */
	public boolean isActive()
	{
		return is_active;
	}

	/**
	 * Set is active button state.
	 * 
	 * @param	selected boolean 
	 */
	public void setSelected(boolean selected)
	{
		this.is_active = selected;
	}

	/**
	 * Add a key code short cut to enable button click action with a key press
	 * 
	 * @param	short_cut KeyCode the key code to set
	 * @param	scene Scene the scene to add the key listener to
	 */
	public void setShortCut(KeyCode short_cut, Scene scene)
	{
		System.out.printf("CustomButton.setShortCut(%s, %s)\n",short_cut.toString(), scene.toString());
		setScene(scene);
		this.short_cut.set(short_cut);
	}

	/**
	 * Get the key code short cut which enables button click action with a key press.
	 * 
	 * @return	KeyCode the key code to set
	 */
	public KeyCode getShortCut()
	{
		return this.short_cut.get();
	}

	/**
	 * Set the scene to enable key short cuts.
	 * 
	 * @param	scene Scene
	 */
	public void setScene(Scene scene)
	{
		key_target.set(scene);
	}

	public CustomToggleGroup getToggleGroup() { return toggle_group.get(); }
	public void setToggleGroup(CustomToggleGroup toggle_group) { this.toggle_group.set(toggle_group); }
	
	@Override
	public String getUserAgentStylesheet()
	{
		return this.getClass().getResource("/css/main.css").toExternalForm();
	}
	
	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new CustomButtonSkin<CustomButton>(this);
	}

	@Override
	public Orientation getContentBias()
	{
		return Orientation.HORIZONTAL;
	}

	/**
	 * Fire the clicked event.
	 */
	@Override
	public void fire()
	{
		if ( !isDisabled() )
		{
//			fireEvent(new ActionEvent());
			fireEvent(new MouseEvent(null, null, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, false, false, false, false, false, false, null));
		}
	}
}
