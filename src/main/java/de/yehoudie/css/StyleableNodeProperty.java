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
public class StyleableNodeProperty<T extends Styleable, Q extends Node> extends StyleableObjectProperty<T, Q>
{
	/**
	 * Implementation of a StyleableObjectProperty bound to Node objects.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData<T, Q> the parsed css meta data
	 * @param	name String the name of the property
	 */
	public StyleableNodeProperty(T obj, CssMetaData<T, Q> meta_data, String name)
	{
		super(obj, meta_data, name);
	}
}
