package de.yehoudie.control.input;

import de.yehoudie.control.BehaviorBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class PasswordFieldBehavior<C extends PasswordField> extends BehaviorBase<C>
{
	protected C control;
	protected final PasswordFieldSkin<C> skin;

    private ObjectProperty<Node> icon = new SimpleObjectProperty<>(this, "icon");
    private ObjectProperty<Rectangle> icon_bg = new SimpleObjectProperty<>(this, "icon_bg");
    protected BooleanProperty mask_text = new SimpleBooleanProperty(this, "mask_text");
	
	/**
	 * @param	control C the PasswordField controller
	 */
	public PasswordFieldBehavior(C control, PasswordFieldSkin<C> skin)
	{
		super(control);
		this.control = control;
		this.skin = skin;
		
		init();
	}
	
	protected void init()
	{
		bindProperties();
		
		icon.addListener(this::iconChanged);
		icon_bg.addListener(this::iconChanged);
//		activate();
	}
	
	private void iconChanged(ObservableValue<? extends Node> value, Node o, Node n)
	{
//		System.out.printf("PasswordFieldBehavior.iconChanged(%s)\n",value.toString());
		if ( o != null )
		{
			deactivate(o);
		}
		if ( n != null )
		{
			activate(n);
		}
	}

	protected void bindProperties()
	{
		icon.bind(this.skin.iconProperty());
		icon_bg.bind(this.skin.iconBgProperty());
		mask_text.bindBidirectional(this.control.mask_text);
	}

	protected void unbindProperties()
	{
		icon.unbind();
		mask_text.unbindBidirectional(this.control.mask_text);
	}

	/**
	 * Activate all listener.
	 */
	protected void activate()
	{
		if ( icon.get() == null ) return;

		Node icon = this.icon.get();

		activate(icon);
	}

	/**
	 * @param icon
	 */
	private void activate(Node icon)
	{
		setIconState(icon, false, Cursor.HAND);
		addListener(icon);
	}

	/**
	 * @param icon
	 */
	private void setIconState(Node icon, boolean is_transparent, Cursor cursor)
	{
		icon.setMouseTransparent(is_transparent);
		icon.setCursor(cursor);
	}

	/**
	 * @param icon
	 */
	protected void addListener(Node icon)
	{
		icon.addEventHandler(MouseEvent.MOUSE_ENTERED, this::mouseEntered);
		icon.addEventHandler(MouseEvent.MOUSE_EXITED, this::mouseExited);
		icon.addEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressed);
		icon.addEventHandler(MouseEvent.MOUSE_RELEASED, this::mouseReleased);
		icon.addEventHandler(MouseEvent.MOUSE_CLICKED, this::mouseClicked);
	}
	
	/**
	 * Deactivate all listener.
	 */
	protected void deactivate()
	{
		if ( icon.get() == null ) return;
		
		Node icon = this.icon.get();
		
		deactivate(icon);
	}

	/**
	 * @param icon
	 */
	private void deactivate(Node icon)
	{
		setIconState(icon, true, Cursor.DEFAULT);
		removeListener(icon);
	}

	/**
	 * @param icon
	 */
	private void removeListener(Node icon)
	{
		icon.removeEventHandler(MouseEvent.MOUSE_ENTERED, this::mouseEntered);
		icon.removeEventHandler(MouseEvent.MOUSE_EXITED, this::mouseExited);
		icon.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressed);
		icon.removeEventHandler(MouseEvent.MOUSE_RELEASED, this::mouseReleased);
		icon.removeEventHandler(MouseEvent.MOUSE_CLICKED, this::mouseClicked);
	}
	
	protected void mouseEntered(MouseEvent e)
	{
//		System.out.println("PasswordField.mouseEntered");
	}
	
	protected void mouseExited(MouseEvent e)
	{
//		System.out.println("PasswordField.mouseExited");
	}
	
	protected void mousePressed(MouseEvent e)
	{
//		System.out.println("PasswordField.mousePressed");
	}
	
	protected void mouseReleased(MouseEvent e)
	{
//		System.out.println("PasswordField.mouseReleased");
	}
	
	protected void mouseClicked(MouseEvent e)
	{
//		System.out.println("PasswordField.mouseClicked");
		switchMaskTextProperty();
	}
	
	private void switchMaskTextProperty()
	{
//		System.out.println("switchMask");
//		System.out.println("  mask_text.get(): "+ mask_text.get());
		mask_text.set(!mask_text.get());
		
		skin.switchMask(mask_text.get());
		skin.switchIcon();
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
	

    // RT-18711 & RT-18854: Stub out word based navigation and editing
    // for security reasons.
    protected void deletePreviousWord() { }
    protected void deleteNextWord() { }
    protected void selectPreviousWord() { }
    public void selectNextWord() { }
    protected void previousWord() { }
    protected void nextWord() { }
    protected void selectWord() {
        selectAll();
    }
//    protected void mouseDoubleClick(HitInfo hit) {
//        getNode().selectAll();
//    }
    protected void selectAll() {
        getNode().selectAll();
//        if (SHOW_HANDLES && contextMenu.isShowing()) {
//            populateContextMenu();
//        }
    }
}
