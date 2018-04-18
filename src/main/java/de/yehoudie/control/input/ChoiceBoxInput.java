package de.yehoudie.control.input;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Skin;

public class ChoiceBoxInput extends TextFieldInput
{
	private static final String DEF_STYLE_CLASS = "choice_box-input";
	
	private ObjectProperty<ChoiceBox<String>> cb = new SimpleObjectProperty<>(this, "cb");
	protected ObjectProperty<ChoiceBox<String>> choiceBoxProperty() { return cb; };
	public ChoiceBox<String> getChoiceBox() { return cb.get(); };
	
	protected ArrayList<String> values;

	private ChoiceBoxInputSkin skin;

	public ChoiceBoxInput()
	{
		super();
		this.setEditable(false);
		this.getStyleClass().add(DEF_STYLE_CLASS);
		
		init();
	}

	/**
	 * Initialize stuff.
	 */
	protected void init()
	{
		addClickHandler();
		cb.set( new ChoiceBox<String>() );
	}

	/**
	 * Set choice box values.
	 * 
	 * @param	values ArrayList<String> values
	 */
	public void setValues(ArrayList<String> values)
	{
		this.values = values;
		cb.get().setItems(FXCollections.observableList(values));
		cb.get().setValue(getText());		
	}

	protected void addClickHandler()
	{
		this.setOnMouseClicked(e->requestFocus());
	}
	
	protected void removeClickHandler()
	{
		this.setOnMouseClicked(null);
	}

	@Override
	public void replaceText(int start, int end, String text)
	{
		if ( verify() )
		{
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text)
	{
		if ( verify() )
		{
			super.replaceSelection(text);
		}
	}
	/**
	 */
	@Override
	public void cut() { super.cut(); }

	/**
	 * Does nothing for FileInput.
	 */
	@Override
	public void copy() { super.copy(); }
	
	/**
	 */
	@Override
	public void paste() { super.paste(); }
	
	/**
	 * open file browser on focus
	 */
	@Override
	public void requestFocus()
	{
		System.out.println("ChoiceBoxInput.requestFocus()");
		fireEvent(new InputEvent(InputEvent.FOCUS_REQUEST));
		
		if ( skin != null ) skin.addChoiceBox();
		
		super.requestFocus();
	}

	/**
	 * Verify that text equals one of the possible choices.
	 */
	private boolean verify()
	{
		return true;
	}

	@Override
	protected Skin<?> createDefaultSkin()
	{
		skin = new ChoiceBoxInputSkin(this);
		return skin;
	}

	public void dispose()
	{
		super.dispose();
	}
}
