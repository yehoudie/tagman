package de.yehoudie.control.input;

public class TextAreaInput extends javafx.scene.control.TextArea implements TextInput
{
	private static final String DEF_STYLE_CLASS = "text-area-input";

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
	 * Wraps javafx.scene.control.TextArea to (be editable by default and) have a length restriction.
	 */
	public TextAreaInput()
	{
		super();
		this.setEditable(true);
		this.getStyleClass().add(DEF_STYLE_CLASS);
	}

	@Override
	public void replaceText(int start, int end, String text)
	{
		super.replaceText(start, end, text);
		verify();
	}

	@Override
	public void replaceSelection(String text)
	{
		super.replaceSelection(text);
		verify();
	}

	private void verify()
	{
		if ( getText().length() > limit )
		{
			setText(getText().substring(0, limit));
		}
	}

	@Override
	public String getUserAgentStylesheet()
	{
		return this.getClass().getResource("/css/main.css").toExternalForm();
	}

	public void dispose()
	{
		this.getChildren().clear();
	}
}
