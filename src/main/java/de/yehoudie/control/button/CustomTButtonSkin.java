package de.yehoudie.control.button;

/**
 * CustomTButton skin.
 * 
 * @author yehoudie
 *
 * @param <C> the controller extending CustomTButton
 */
public abstract class CustomTButtonSkin<C extends CustomTButton> extends CustomButtonSkin<C>
{
	/**
	 * CustomTButton skin.
	 * 
	 * @param	control C
	 */
	public CustomTButtonSkin(C control)
	{
		super(control);
	}
	
	@Override
	protected abstract void setBehaviorBase();
}
