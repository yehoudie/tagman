package de.yehoudie.control.input;

/**
 * Textfiled that only allows decimal number input.
 */
public class NumberInput extends TextFieldInput
{
	private static final String DEF_STYLE_CLASS = "number-input";

	/**
	 * Textfiled that only allows decimal number input.
	 */
	public NumberInput()
	{
		super();
		this.getStyleClass().add(DEF_STYLE_CLASS);
	}

	@Override
	public void replaceText(int start, int end, String text)
	{
//		System.out.printf("NumberInput.replaceText(%s, %s, %s)\n",start,end,text);
		if ( verify(text) )
		{
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text)
	{
//		System.out.printf("NumberInput.replaceText(%s)\n",text);
		if ( verify(text) )
		{
			super.replaceSelection(text);
		}
	}

	private boolean verify(String text)
	{
//		System.out.printf("NumberInput.verify(%s)\n",text);
		return text.matches("[0-9]*");
	}
}
