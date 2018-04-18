package de.yehoudie.control.input;

import de.yehoudie.tagman.Main;
import javafx.scene.control.Skin;

public class TextFieldInput extends javafx.scene.control.TextField implements TextInput
{
	private static final String DEF_STYLE_CLASS = "text-field-input";

	private int limit = 100;

	/**
	 * Set input max character length.
	 * 
	 * @param	value int
	 */
	public void setLimit(int value)
	{
		limit = value;
	};

	/**
	 * Get input max character length.
	 */
	public int getLimit()
	{
		return limit;
	};

	/**
	 * Wraps javafx.scene.control.TextField to (be editable by default and) have a length restriction.
	 */
	public TextFieldInput()
	{
		super();
		this.setEditable(true);
		this.getStyleClass().add(DEF_STYLE_CLASS);
	}

	@Override
	public void replaceText(int start, int end, String text)
	{
//		System.out.printf("TextInput.replaceText(%d, %d, %s)\n",start,end,text);
		if ( getText().length() + text.length() - (end - start) > limit && !text.isEmpty() ) return;
		super.replaceText(start, end, text);
//		verify();
	}

	@Override
	public void replaceSelection(String text)
	{
//		System.out.printf("TextInput.replaceSelection(%s)\n",text);
//		verify();
//		super.replaceSelection(text);
		final int start = getSelection().getStart();
        final int end = start + getSelection().getLength();

        replaceText(start, end, text);
	}

	/**
	 * Verify length of text is in its limits.
	 */
	/*private void verify()
	{
		System.out.printf("TextInput.verify()\n");
		if ( getText().length() > limit )
		{
			setText(getText().substring(0, limit));
			positionCaret(getText().length());
		}
	}*/

	@Override
	public String getUserAgentStylesheet()
	{
		return this.getClass().getResource(Main.MAIN_STYLE_SHEET).toExternalForm();
	}

	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new TextFieldInputSkin<TextFieldInput>(this);
	}

	public void dispose()
	{
		this.getChildren().clear();
	}
}
