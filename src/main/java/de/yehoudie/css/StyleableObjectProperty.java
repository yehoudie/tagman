/**
 * 
 */
package de.yehoudie.css;

import javafx.css.CssMetaData;
import javafx.css.Styleable;

/**
 * @author yehoudie
 *
 */
public class StyleableObjectProperty<T extends Styleable, Q> extends javafx.css.StyleableObjectProperty<Q>
{
	protected CssMetaData<T, Q> meta_data;
	protected String name;
	protected T obj;

	/**
	 * Implementation of a StyleableObjectProperty.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData<T, Q> the parsed css meta data
	 * @param	name String the name of the property
	 */
	public StyleableObjectProperty(T obj, CssMetaData<T, Q> meta_data, String name)
	{
		super();
		
		this.obj = obj;
		this.meta_data = meta_data;
		this.name = name;
	}
	
	
	@Override
	public CssMetaData<T, Q> getCssMetaData()
	{
		return meta_data;
	}

	@Override
	public Object getBean()
	{
		return obj;
	}

	@Override
	public String getName()
	{
		return this.name;
	}
}
