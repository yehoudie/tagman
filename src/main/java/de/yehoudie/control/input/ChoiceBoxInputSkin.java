package de.yehoudie.control.input;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxInputSkin extends TextFieldInputSkin
{
	private ObjectProperty<ChoiceBox<String>> cb = new SimpleObjectProperty<>(this, "cb");
	private ChoiceBoxInput skinnable;
	private boolean is_choice_box_added;

	/**
	 * Skin for a ChoiceBoxInput.
	 * Handles presentation of the added/removed choice box.
	 * 
	 * @param	control TextInput the controller
	 */
	public ChoiceBoxInputSkin(TextFieldInput control)
	{
		super(control);
		init();
	}

	/**
	 * Initialize stuff.
	 */
	private void init()
	{
		bindProperties();
		cb.get().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				skinnable.setText( cb.get().getItems().get((Integer) newValue) );
//				ChoiceBoxInputSkin.this.getChildren().remove(cb.get());
//				removeChoiceBox();
			}
		});
	}

	/**
	 * Bind controllable porperties.
	 */
	private void bindProperties()
	{
		this.skinnable = (ChoiceBoxInput) getSkinnable();
		System.out.println("skinnable: "+skinnable);
		System.out.println("skinnable.choiceBoxProperty(): "+skinnable.choiceBoxProperty());
		this.cb.bind(skinnable.choiceBoxProperty());
	}
	
	/**
	 * Unbind bound properties.
	 */
	private void unbindProperties()
	{
		this.cb.unbind();
	}

	/**
	 * 
	 */
	public void addChoiceBox()
	{
		System.out.println("ChoiceBoxInputSkin.addChoiceBox()");
		if ( is_choice_box_added ) removeChoiceBox();
		
		this.getChildren().add(cb.get());
		is_choice_box_added = true;
		skinnable.removeClickHandler();
	}
	
	public void removeChoiceBox()
	{
		System.out.println("ChoiceBoxInputSkin.removeChoiceBox()");
		this.getChildren().remove(cb.get());
		is_choice_box_added = false;
	}
	
	/* (non-Javadoc)
	 * @see com.sun.javafx.scene.control.skin.TextFieldSkin#layoutChildren(double, double, double, double)
	 */
	@Override
	protected void layoutChildren(double x, double y, double w, double h)
	{
		super.layoutChildren(x, y, w, h);

		cb.get().resize(cb.get().prefWidth(-1), cb.get().getBoundsInLocal().getHeight());
//		sub_label.relocate(radioWidth + label_offset_x, labelHeight+label_offset_y);
		cb.get().relocate(x,y);
	}
	
	/* (non-Javadoc)
	 * @see com.sun.javafx.scene.control.skin.BehaviorSkinBase#dispose()
	 */
	@Override
	public void dispose()
	{
		super.dispose();
		
		this.unbindProperties();
	}
}
