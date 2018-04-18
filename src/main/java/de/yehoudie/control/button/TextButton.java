package de.yehoudie.control.button;

import de.yehoudie.callback.MouseEventCallback;
import de.yehoudie.utils.tween.TweenUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

public class TextButton extends CustomTButton
{
	private static final String DEF_STYLE_CLASS = "text_button";
	
//	protected int btn_w;
//	protected int btn_h;

	protected ObjectProperty<Label> label = new SimpleObjectProperty<>(this, "label");
	protected ObjectProperty<Label> labelProperty() { return label; };
	public Label getLabel() { return label.get(); };

	public TextButton(int id, String value, MouseEventCallback clickCallback)
	{
		super();
		super.setButtonId(id);
		super.setClickCallback(clickCallback);

		this.getStyleClass().add(DEF_STYLE_CLASS);

//		this.btn_w = btn_w;
//		this.btn_h = btn_h;

		setText(value);
		init();
	}

	protected void init()
	{
		label.set(new Label());
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
	}
	
	public void addLabelStyle(String style)
	{
		label.get().getStyleClass().add(style);
	}
	
	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new TextButtonSkin(this);
	}
}
