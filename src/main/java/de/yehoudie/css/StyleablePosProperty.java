/**
 * 
 */
package de.yehoudie.css;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.geometry.Pos;

/**
 * @author yehoudie
 *
 */
public class StyleablePosProperty<T extends Styleable> extends StyleableObjectProperty<T, Pos>
{
	/**
	 * Implementation of a StyleableObjectProperty bound to Pos enum.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData<T, Pos> the parsed css meta data
	 * @param	name String the name of the property
	 */
	public StyleablePosProperty(T obj, CssMetaData<T, Pos> meta_data, String name)
	{
		super(obj, meta_data, name);
	}
}
