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
public class StyleableBooleanProperty<T extends Styleable> extends javafx.css.StyleableBooleanProperty
{
	private CssMetaData<T, Boolean> meta_data;
	private String name;
	private T obj;

	/**
	 * Implementation of a StyleableBooleanProperty.
	 * 
	 * @param	obj T the styled object
	 * @param	meta_data CssMetaData<T, Boolean> the parsed css meta data
	 * @param	name String the name of the property
	 */
	public StyleableBooleanProperty(T obj, CssMetaData<T, Boolean> meta_data, String name)
	{
		super();
		
		this.obj = obj;
		this.meta_data = meta_data;
		this.name = name;
	}
	
	
	@Override
	public CssMetaData<T, Boolean> getCssMetaData()
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
