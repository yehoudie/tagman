package de.yehoudie.control.button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.yehoudie.css.StyleableDoubleProperty;
import de.yehoudie.css.StyleableIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.converter.SizeConverter;

/**
 * A CustomButton with a tween interface.
 * 
 * @author yehoudie
 */
public abstract class CustomTButton extends CustomButton
{
	/**
	 * A CustomButton with a tween interface.<br>
	 */
	public CustomTButton()
	{
		super();
	}
	
	/**
	 * A CustomButton with a tween interface.
	 * 
	 * @param	value String the text value of the button
	 */
	public CustomTButton(String value)
	{
		super(value);
	}
	
	/**
	 * Fire mouseOver, set active and deactivate.
	 */
	@Override
	public void pseudoPress()
	{
		super.pseudoPress();
		if ( this.getSkin() != null )
		{
			CustomTButtonSkin<CustomTButton> skin = (CustomTButtonSkin<CustomTButton>) this.getSkin();
			CustomTButtonBehavior<CustomTButton> bb = (CustomTButtonBehavior<CustomTButton>) skin.behavior;
			bb.tween(true);
		}
	}

	/**
	 * Custom css attributes.<br>
	 * The most common ones like:<br>
	 *  * -fx-on-opacity, -fx-off-opacity : for opacity tweening<br>
	 *  * -fx-tween-time : for the tween time
	 */
	
    private double default_over_opacity = 1.0;
	/**
	 * The opacity value of an mouse over state.
	 * 
	 * @return	DoubleProperty the on opacity of this button
	 */
	public final DoubleProperty overOpacityProperty()
	{
		if ( over_opacity == null ) over_opacity = new StyleableDoubleProperty<CustomTButton>(this, StyleableProperties.OVER_OPACITY, "over_opacity");
		return over_opacity;
	}
    private DoubleProperty over_opacity;
    public final void setOverOpacityProperty(double value) { overOpacityProperty().setValue(value); }
    public final double getOverOpacityProperty() { return over_opacity == null ? default_over_opacity : over_opacity.getValue(); }
    
    private double default_out_opacity = 0.75;
    /**
     * The opacity value of an mouse out state.
     * 
	 * @return	DoubleProperty the on opacity of this button
     */
    public final DoubleProperty outOpacityProperty()
    {
    	if ( out_opacity == null ) out_opacity = new StyleableDoubleProperty<CustomTButton>(this, StyleableProperties.OUT_OPACITY, "out_opacity");
    	return out_opacity;
    }
    private DoubleProperty out_opacity;
    public final void setOutOpacityProperty(double value) { outOpacityProperty().setValue(value); }
    public final double getOutOpacityProperty() { return out_opacity == null ? default_out_opacity : out_opacity.getValue(); }
    
    private int default_tween_time = 250;
    /**
     * The tween time value.
     * 
     * @return	IntegerProperty the on opacity of this button
     */
    public final IntegerProperty tweenTimeProperty()
    {
    	if ( tween_time == null ) tween_time = new StyleableIntegerProperty<CustomTButton>(this, StyleableProperties.TWEEN_TIME, "tween_time");
    	return tween_time;
    }
    private IntegerProperty tween_time;
    public final void setTweenTimeProperty(int value) { tweenTimeProperty().setValue(value); }
    public final int getTweenTimeProperty() { return tween_time == null ? default_tween_time : tween_time.getValue(); }
    
    
	private static class StyleableProperties
	{
		private static final CssMetaData<CustomTButton, Number> OVER_OPACITY 
		= new CssMetaData<CustomTButton, Number>("-fx-tween-over-opacity", SizeConverter.getInstance(), 1.0)
		{
			@Override
			public boolean isSettable(CustomTButton n)
			{
				return n.over_opacity == null || !n.over_opacity.isBound();
			}

			@Override
			public StyleableProperty<Number> getStyleableProperty(CustomTButton n)
			{
				return (StyleableProperty<Number>) n.overOpacityProperty();
			}
		};
		private static final CssMetaData<CustomTButton, Number> OUT_OPACITY
		= new CssMetaData<CustomTButton, Number>("-fx-tween-out-opacity", SizeConverter.getInstance(), 0.75)
		{
			@Override
			public boolean isSettable(CustomTButton n)
			{
				return n.out_opacity == null || !n.out_opacity.isBound();
			}
			
			@Override
			public StyleableProperty<Number> getStyleableProperty(CustomTButton n)
			{
				return (StyleableProperty<Number>) n.outOpacityProperty();
			}
		};
		private static final CssMetaData<CustomTButton, Number> TWEEN_TIME
		= new CssMetaData<CustomTButton, Number>("-fx-tween-time", SizeConverter.getInstance(), 250)
		{
			@Override
			public boolean isSettable(CustomTButton n)
			{
				return n.tween_time == null || !n.tween_time.isBound();
			}
			
			@Override
			public StyleableProperty<Number> getStyleableProperty(CustomTButton n)
			{
				return (StyleableProperty<Number>) n.tweenTimeProperty();
			}
		};

		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
		static
		{
			final List<CssMetaData<? extends Styleable, ?>> styleables = 
					new ArrayList<CssMetaData<? extends Styleable, ?>>(CustomButton.getClassCssMetaData());
			Collections.addAll(
					styleables, 
					OVER_OPACITY,
					OUT_OPACITY,
					TWEEN_TIME);
			STYLEABLES = Collections.unmodifiableList(styleables);
		}
	}

	/**
	 * @return The CssMetaData associated with this class, which may include the CssMetaData of its
	 *         superclasses.
	 * @since JavaFX 8.0
	 */
	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
	{
		return StyleableProperties.STYLEABLES;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since JavaFX 8.0
	 */
	@Override
	public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
	{
		return getClassCssMetaData();
	}
}
