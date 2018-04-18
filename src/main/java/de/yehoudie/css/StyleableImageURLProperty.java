/**
 * 
 */
package de.yehoudie.css;

import java.util.function.Supplier;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.WritableValue;
import javafx.css.CssMetaData;
import javafx.css.StyleOrigin;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author yehoudie
 *
 */
public class StyleableImageURLProperty<T extends Styleable> extends StyleableStringProperty<T>
{
	private ObjectProperty<Node> icon_prop;
	private Supplier<ObjectProperty<Node>> iconProperty;
	private Supplier<Node> icon;

	/**
	 * Implementation of a StyleableStringProperty extending to an ImageUrl.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData<T, String> the parsed css meta data
	 * @param	name String the name of the property
	 */
	public StyleableImageURLProperty(T obj, CssMetaData<T, String> meta_data, String name, ObjectProperty<Node> icon_prop, Supplier<ObjectProperty<Node>> iconProperty, Supplier<Node> icon)
	{
		super(obj, meta_data, name);
		
		this.icon_prop = icon_prop;
		this.iconProperty = iconProperty;
		this.icon = icon;
	}
	//
	// If imageUrlProperty is invalidated, this is the origin of the style that
	// triggered the invalidation. This is used in the invaildated() method where the
	// value of super.getStyleOrigin() is not valid until after the call to set(v)
	// returns,
	// by which time invalidated will have been called.
	// This value is initialized to USER in case someone calls set on the
	// imageUrlProperty, which
	// is possible:
	// CssMetaData metaData =
	// ((StyleableProperty)labeled.graphicProperty()).getCssMetaData();
	// StyleableProperty prop = metaData.getStyleableProperty(labeled);
	// prop.set(someUrl);
	//
	// TODO: Note that prop != labeled, which violates the contract between
	// StyleableProperty and CssMetaData.
	//
	StyleOrigin origin = StyleOrigin.USER;

	@Override
	public void applyStyle(StyleOrigin origin, String v)
	{
		this.origin = origin;

		// Don't want applyStyle to throw an exception which would leave this.origin set
		// to the wrong value
		if ( icon_prop == null || icon_prop.isBound() == false ) super.applyStyle(origin, v);

		// Origin is only valid for this invocation of applyStyle, so reset it to USER
		// in case someone calls set.
		this.origin = StyleOrigin.USER;
	}

	@Override
	protected void invalidated()
	{
		// need to call super.get() here since get() is overridden to return the
		// graphicProperty's value
		final String url = super.get();

		if ( url == null )
		{
			((StyleableProperty<Node>) (WritableValue<Node>) iconProperty.get()).applyStyle(origin, null);
		}
		else
		{
			// RT-34466 - if graphic's url is the same as this property's value, then
			// don't overwrite.
			final Node graphic_node = icon.get();
			if ( graphic_node instanceof ImageView )
			{
				final ImageView imageView = (ImageView) graphic_node;
				final Image image = imageView.getImage();
				if ( image != null )
				{
					final String imageViewUrl = image.getUrl();
					if ( url.equals(imageViewUrl) ) return;
				}

			}

//			final Image img = StyleManager.getInstance().getCachedImage(url);
			final Image img = new Image(url);

			if ( img != null )
			{
				//
				// Note that it is tempting to try to re-use existing ImageView simply
				// by setting
				// the image on the current ImageView, if there is one. This would
				// effectively change
				// the image, but not the ImageView which means that no graphicProperty
				// listeners would
				// be notified. This is probably not what we want.
				//

				//
				// Have to call applyStyle on graphicProperty so that the
				// graphicProperty's
				// origin matches the imageUrlProperty's origin.
				//
				((StyleableProperty<Node>) (WritableValue<Node>) iconProperty.get()).applyStyle(origin, new ImageView(img));
			}
		}
	}

	@Override
	public String get()
	{
		// The value of the imageUrlProperty is that of the graphicProperty.
		// Return the value in a way that doesn't expand the graphicProperty.
		final Node graphic = icon.get();
		if ( graphic instanceof ImageView )
		{
			final Image image = ((ImageView) graphic).getImage();
			if ( image != null )
			{
				return image.getUrl();
			}
		}
		return null;
	}

	@Override
	public StyleOrigin getStyleOrigin()
	{
		//
		// The origin of the imageUrlProperty is that of the graphicProperty.
		// Return the origin in a way that doesn't expand the graphicProperty.
		//
		return icon_prop != null ? ((StyleableProperty<Node>) (WritableValue<Node>) icon_prop).getStyleOrigin() : null;
	}
}
