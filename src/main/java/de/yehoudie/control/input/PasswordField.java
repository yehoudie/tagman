package de.yehoudie.control.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.yehoudie.css.StyleableBooleanProperty;
import de.yehoudie.css.StyleableIconProperty;
import de.yehoudie.css.StyleableImageURLProperty;
import de.yehoudie.css.StyleableIntegerProperty;
import de.yehoudie.css.StyleablePosProperty;
import de.yehoudie.css.StyleableStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.WritableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.css.converter.EnumConverter;
import javafx.css.converter.SizeConverter;
import javafx.css.converter.StringConverter;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Skin;

/**
 * A password input field.<br>
 * Hides characters by a bullet.<br>
 * Enables switching between hiden and clear text mode.
 */
public class PasswordField extends TextFieldInput
{
	protected BooleanProperty mask_text = new SimpleBooleanProperty(this, "mask_text", true);

	/**
	 * A password input field.<br>
	 * Hides characters by a bullet.<br>
	 * Enables switching between hiden and clear text mode.
	 */
	public PasswordField()
	{
		super();
		getStyleClass().add("password-field");
		setAccessibleRole(AccessibleRole.PASSWORD_FIELD);
	}

	/***************************************************************************
	 *                                                                         *
	 * Methods                                                                 *
	 *                                                                         *
	 **************************************************************************/

	/**
	 * Does nothing for PasswordField.
	 */
	@Override
	public void cut()
	{
		// No-op
	}

	/**
	 * Does nothing for PasswordField.
	 */
	@Override
	public void copy()
	{
		// No-op
	}

	/***************************************************************************
	 *                                                                         *
	 * Accessibility handling                                                  *
	 *                                                                         *
	 **************************************************************************/
	@Override
	public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters)
	{
		switch ( attribute )
		{
			case TEXT:
				return null;
			default:
				return super.queryAccessibleAttribute(attribute, parameters);
		}
	}

	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new PasswordFieldSkin<PasswordField>(this);
	}
	
	/*******************************************************************************
	 * Custom css attributes.<br>                                                  *
	 *  * -fx-show-icon, -fx-show-icon : the icon for switching the view state<br> *
	 *  * -fx-icon-alignment : the icon alignment                                  *
	 *  * -fx-icon-width, -fx-icon-height: the icon size                           *
	 *  * -fx-icon-preserve-ratio: keep icon size proportional                     *
	 *******************************************************************************/
	
	/**
     * An optional icon for the Labeled. This can be positioned relative to the
     * text by using {@link #setContentDisplay}.  The node specified for this
     * variable cannot appear elsewhere in the scene graph, otherwise
     * the {@code IllegalArgumentException} is thrown.  See the class
     * description of {@link javafx.scene.Node Node} for more detail.
     * @return the optional icon for this labeled
     */
    public final ObjectProperty<Node> showIconProperty() {
		if ( show_icon == null )
		{
			show_icon = new StyleableIconProperty<PasswordField, Node>(this, StyleableProperties.SHOW_ICON, "show_icon");
		}
		if ( show_icon.get() != null ) show_icon.get().getStyleClass().add("show_icon");
		return show_icon;
    }
    private ObjectProperty<Node> show_icon;
    public final void setShowIcon(Node value) {
        showIconProperty().setValue(value);
    }
    public final Node getShowIcon() { return show_icon == null ? null : show_icon.getValue(); }

	private StyleableStringProperty<PasswordField> show_icon_url = null;

	/**
	 * The imageUrl property is set from CSS and then the graphic property is set from the
	 * invalidated method. This ensures that the same image isn't reloaded.
	 */
	private StyleableStringProperty<PasswordField> showIconUrlProperty()
	{
		if ( show_icon_url == null )
		{
			show_icon_url = new StyleableImageURLProperty<PasswordField>(
					this, StyleableProperties.SHOW_ICON, "show_icon_url", show_icon, this::showIconProperty, this::getShowIcon);
		}
		return show_icon_url;
	}
	
	public final ObjectProperty<Node> hideIconProperty() {
		if ( hide_icon == null )
		{
			hide_icon = new StyleableIconProperty<PasswordField, Node>(this, StyleableProperties.HIDE_ICON, "hide_icon");
		}
		if ( hide_icon.get() != null ) hide_icon.get().getStyleClass().add("hide_icon");
		return hide_icon;
	}
	private ObjectProperty<Node> hide_icon;
	public final void setHideIcon(Node value) {
		hideIconProperty().setValue(value);
	}
	public final Node getHideIcon() { return hide_icon == null ? null : hide_icon.getValue(); }
	
	private StyleableStringProperty<PasswordField> hide_icon_url = null;
	
	/**
	 * The imageUrl property is set from CSS and then the graphic property is set from the
	 * invalidated method. This ensures that the same image isn't reloaded.
	 */
	private StyleableStringProperty<PasswordField> hideIconUrlProperty()
	{
		if ( hide_icon_url == null )
		{
			hide_icon_url = new StyleableImageURLProperty<PasswordField>(
					this, StyleableProperties.HIDE_ICON, "hide_icon_url", hide_icon, this::hideIconProperty, this::getHideIcon);
		}
		return hide_icon_url;
	}
	
	/**
	 * icon width style
	 */
    public final StyleableIntegerProperty<PasswordField> iconWidthProperty() {
        if (icon_width == null) {
        	icon_width = new StyleableIntegerProperty<PasswordField>(this, StyleableProperties.ICON_WIDTH, "icon_width");
        }
        return icon_width;
    }
    private StyleableIntegerProperty<PasswordField> icon_width;
    public final void setIconWidth(int value) {
        iconWidthProperty().setValue(value);
    }
    public final int getIconWidth() { return icon_width == null ? 0 : icon_width.getValue(); }
    
    public final StyleableIntegerProperty<PasswordField> iconHeightProperty() {
    	if (icon_height == null) {
    		icon_height = new StyleableIntegerProperty<PasswordField>(this, StyleableProperties.ICON_HEIGHT, "icon_height");
    	}
    	return icon_height;
    }
    private StyleableIntegerProperty<PasswordField> icon_height;
    public final void setIconHeight(int value) {
    	iconHeightProperty().setValue(value);
    }
    public final int getIconHeight() { return icon_height == null ? 0 : icon_height.getValue(); }
    
    public final StyleableBooleanProperty<PasswordField> iconPreserveRatioProperty() {
    	if (icon_preserve_ratio == null) {
    		icon_preserve_ratio = new StyleableBooleanProperty<PasswordField>(this, StyleableProperties.ICON_PRESERVE_RATIO, "icon_preserve_ratio");
    	}
    	return icon_preserve_ratio;
    }
    private StyleableBooleanProperty<PasswordField> icon_preserve_ratio;
    public final void setIconPreserveRatio(boolean value) {
    	iconPreserveRatioProperty().setValue(value);
    }
    public final boolean getIconPreserveRatio() { return icon_preserve_ratio == null ? false : icon_preserve_ratio.getValue(); }

    /**
     * Specifies how the graphic should be aligned.
     * 
     * @return	ObjectProperty<Pos>
     */
    public final ObjectProperty<Pos> iconAlignmentProperty() {
		if ( icon_alignment == null )
		{
			icon_alignment = new StyleablePosProperty<PasswordField>(this, StyleableProperties.ICON_ALIGNMENT, "icon_alignment");
		}
		return icon_alignment;
    }
    private ObjectProperty<Pos> icon_alignment;
    public final void setIconAlignment(Pos value) { iconAlignmentProperty().set(value); }
    public final Pos getIconAlignment() { return icon_alignment == null ? getInitialIconAlignment() : icon_alignment.get(); }
    protected Pos getInitialIconAlignment() { return Pos.CENTER_LEFT; }
    
	private static class StyleableProperties
	{
		private static final CssMetaData<PasswordField, Pos> ICON_ALIGNMENT = 
				new CssMetaData<PasswordField, Pos>("-fx-icon-alignment", new EnumConverter<Pos>(Pos.class), Pos.CENTER_LEFT)
		{
			@Override
			public boolean isSettable(PasswordField n)
			{
				return n.icon_alignment == null || !n.icon_alignment.isBound();
			}

			@Override
			public StyleableProperty<Pos> getStyleableProperty(PasswordField n)
			{
				return (StyleableProperty<Pos>) (WritableValue<Pos>) n.iconAlignmentProperty();
			}

			@Override
			public Pos getInitialValue(PasswordField n)
			{
				return n.getInitialIconAlignment();
			}
		};

		private static final CssMetaData<PasswordField, String> SHOW_ICON = 
				new CssMetaData<PasswordField, String>("-fx-show-icon", StringConverter.getInstance())
		{
			@Override
			public boolean isSettable(PasswordField n)
			{
				// Note that we care about the graphic, not imageUrl
				return n.show_icon == null || !n.show_icon.isBound();
			}

			@Override
			public StyleableProperty<String> getStyleableProperty(PasswordField n)
			{
				return n.showIconUrlProperty();
			}
		};
		
		private static final CssMetaData<PasswordField, String> HIDE_ICON = 
				new CssMetaData<PasswordField, String>("-fx-hide-icon", StringConverter.getInstance())
		{
			@Override
			public boolean isSettable(PasswordField n)
			{
				// Note that we care about the graphic, not imageUrl
				return n.hide_icon == null || !n.hide_icon.isBound();
			}
			
			@Override
			public StyleableProperty<String> getStyleableProperty(PasswordField n)
			{
				return n.hideIconUrlProperty();
			}
		};

		static final CssMetaData<PasswordField, Number> ICON_WIDTH = 
				new CssMetaData<PasswordField, Number>("-fx-icon-width", SizeConverter.getInstance())
		{
			@Override
			public boolean isSettable(PasswordField n)
			{
				return n.icon_width == null || !n.icon_width.isBound();
			}

			@Override
			public StyleableProperty<Number> getStyleableProperty(PasswordField n)
			{
				return n.iconWidthProperty();
			}
		};
		
		static final CssMetaData<PasswordField, Number> ICON_HEIGHT = 
				new CssMetaData<PasswordField, Number>("-fx-icon-height", SizeConverter.getInstance())
		{
			@Override
			public boolean isSettable(PasswordField n)
			{
				return n.icon_height == null || !n.icon_height.isBound();
			}
			
			@Override
			public StyleableProperty<Number> getStyleableProperty(PasswordField n)
			{
				return n.iconHeightProperty();
			}
		};
		
		static final CssMetaData<PasswordField, Boolean> ICON_PRESERVE_RATIO = 
				new CssMetaData<PasswordField, Boolean>("-fx-icon-preserve-ratio", BooleanConverter.getInstance())
		{
			@Override
			public boolean isSettable(PasswordField n)
			{
				return n.icon_preserve_ratio == null || !n.icon_preserve_ratio.isBound();
			}
			
			@Override
			public StyleableProperty<Boolean> getStyleableProperty(PasswordField n)
			{
				return n.iconPreserveRatioProperty();
			}
		};
		
		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
		static
		{
			final List<CssMetaData<? extends Styleable, ?>> styleables = 
					new ArrayList<CssMetaData<? extends Styleable, ?>>(TextFieldInput.getClassCssMetaData());
			Collections.addAll(
					styleables,
					ICON_ALIGNMENT,
					ICON_PRESERVE_RATIO,
					ICON_WIDTH,
					ICON_HEIGHT,
					HIDE_ICON,
					SHOW_ICON
					);
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
