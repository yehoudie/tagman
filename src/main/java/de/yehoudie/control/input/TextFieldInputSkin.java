package de.yehoudie.control.input;

import javafx.scene.control.skin.TextFieldSkin;

public class TextFieldInputSkin<T extends TextFieldInput> extends TextFieldSkin
{
    protected T control;

	public TextFieldInputSkin(T control)
	{
		super(control);
		this.control = control;
	}
}
