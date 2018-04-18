package de.yehoudie.control.button;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Skin;

/**
 * A radio button that supports a second line.<br>
 * Extending a custom javaFX RadioButton.
 */
public class DoubleLineRadioButton extends RadioButton
{
	private static final String DEF_STYLE_CLASS = "doubleline-radio-button";
	
	private StringProperty _sub_text = new SimpleStringProperty();
	protected StringProperty subTextProperty() { return _sub_text; };
	public String getSubText() { return _sub_text.get(); };
	public void setSubText(String value) { _sub_text.set(value); };
	
	/**
	 * A radio button that supports a second line.
	 */
	public DoubleLineRadioButton()
	{
		super();
		init();
	}

	/**
	 * A radio button that supports a second line.
	 * 
	 * @param	text String the label text
	 */
	public DoubleLineRadioButton(String text)
	{
		super(text);
		init();
	}
	
	
	/**
	 * A radio button that supports a second line.
	 * 
	 * @param	text String the label text
	 * @param	sub_text String the sub label text
	 */
	public DoubleLineRadioButton(String text, String sub_text)
	{
		super(text);
		_sub_text.set(sub_text);
		init();
	}
	
	private void init()
	{
		this.getStyleClass().add(DEF_STYLE_CLASS);
	}

	@Override
	public String getUserAgentStylesheet()
	{
		return this.getClass().getResource("/css/main.css").toExternalForm();
	}
	
	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new DoubleLineRadioButtonSkin(this);
	}
}
