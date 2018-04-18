package de.yehoudie.control.button;

import de.yehoudie.control.BehaviorBase;
import javafx.scene.control.SkinBase;

/**
 * Skin of a Custom Button
 * 
 * @author yehoudie
 *
 * @param	<C> the type extending CustomButton
 */
public class CustomButtonSkin<C extends CustomButton> extends SkinBase<C>
{
	protected C control;
	protected BehaviorBase<C> behavior;
	public BehaviorBase<C> getBehavior() { return behavior; }
	
	/**
	 * Skin of a Custom Button
	 * 
	 * @param	control C the CustomButton controller
	 */
	public CustomButtonSkin(C control)
	{
		super(control);
		this.control = control;
        setBehaviorBase();
	}

	/**
	 * Overridable function that set the BehaviorBase
	 */
	protected void setBehaviorBase()
	{
		this.behavior = new CustomButtonBehavior<C>(this.control);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	/*@Override
	protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( width < 0 )
		{
			return Double.MAX_VALUE;
		}
		else
		{
			return 240000.0 / width;
		}
	}

	@Override
	protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( height < 0 )
		{
			return Double.MAX_VALUE;
		}
		else
		{
			return 240000.0 / height;
		}
	}

	@Override
	protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( width < 0 )
		{
			return Double.MIN_VALUE;
		}
		else
		{
			return 240000.0 / width;
		}
	}

	@Override
	protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( height < 0 )
		{
			return Double.MIN_VALUE;
		}
		else
		{
			return 240000.0 / height;
		}
	}

	@Override
	protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( width < 0 )
		{
			return 400;
		}
		else
		{
			return 240000.0 / width;
		}
	}

	@Override
	protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( height < 0 )
		{
			return 600;
		}
		else
		{
			return 240000.0 / height;
		}
	}*/
}
