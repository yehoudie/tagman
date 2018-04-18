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
public class StyleableStringProperty<T extends Styleable> extends javafx.css.StyleableStringProperty
{
	protected CssMetaData<T, String> meta_data;
	protected String name;
	protected T obj;

	/**
	 * Implementation of a StyleableStringProperty.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData<T, String> the parsed css meta data
	 * @param	name String the name of the property
	 */
	public StyleableStringProperty(T obj, CssMetaData<T, String> meta_data, String name)
	{
		super();
		
		this.obj = obj;
		this.meta_data = meta_data;
		this.name = name;
	}
	
	
	@Override
	public CssMetaData<T, String> getCssMetaData()
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
