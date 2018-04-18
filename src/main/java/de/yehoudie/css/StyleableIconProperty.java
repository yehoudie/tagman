/**
 * 
 */
package de.yehoudie.css;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;

/**
 * @author yehoudie
 *
 */
public class StyleableIconProperty<T extends Styleable, Q extends Node> extends StyleableObjectProperty<T, Q>
{
	protected CssMetaData meta_data;
	
	/**
	 * Implementation of a StyleableObjectProperty bound to ImageView.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData the parsed css url meta data
	 * @param	name String the name of the property
	 */
	public StyleableIconProperty(T obj, CssMetaData meta_data, String name)
	{
		super(obj, null, name);
		this.meta_data = meta_data;
	}
	
	/**
	 * The graphic is styleable by css, but it is the<br>
	 * imageUrlProperty that handles the style value.
	 */
	@Override
	public CssMetaData getCssMetaData()
	{
		return meta_data;
	}
}
