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
public class StyleableIntegerProperty<T extends Styleable> extends javafx.css.StyleableIntegerProperty
{
	private CssMetaData<T, Number> meta_data;
	private String name;
	private T obj;

	/**
	 * Implementation of a StyleableIntegerProperty.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData<T, Number> the parsed css meta data
	 * @param	name String the name of the property
	 */
	public StyleableIntegerProperty(T obj, CssMetaData<T, Number> meta_data, String name)
	{
		super();
		
		this.obj = obj;
		this.meta_data = meta_data;
		this.name = name;
	}
	
	
	@Override
	public CssMetaData<T, Number> getCssMetaData()
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
