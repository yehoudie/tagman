package de.yehoudie.control.button;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

public class TextButtonSkin extends CustomTButtonSkin<TextButton>
{
//	protected int btn_w;
//	protected int btn_h;
	protected float out_a = 1f;
	protected float over_a = 0.75f;

	protected ObjectProperty<Label> label = new SimpleObjectProperty<>(this, "label");
	
	public TextButtonSkin(TextButton control)
	{
		super(control);

//		this.btn_w = control.btn_w;
//		this.btn_h = control.btn_h;
		
		init();
	}

	protected void init()
	{
		bindProperties();
		
		addLabel();
	}
	
	private void bindProperties()
	{
		TextButton control = (TextButton) getSkinnable();
		
		label.bind(control.labelProperty());
	}
	
	private void unbindProperties()
	{
		label.unbind();
		label.get().textProperty().unbind();
	}

	protected void addLabel()
	{
		Label label = this.label.get();
//		label.setText(_value);
		label.getStyleClass().add("label");
//		label.textProperty().bind(control.valueProperty());
		label.textProperty().bind(control.textProperty());

		this.getChildren().add(label);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		unbindProperties();
		this.getChildren().remove(label.get());
		label = null;
	}
	
	/***************************************/
	/*             LAYOUT                  */
	/***************************************/
	
	@Override
	protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
//		System.out.printf("TextButtonSkin.computeMaxWidth(%f, %f, %f, %f, %f)\n",height,topInset,rightInset,bottomInset,leftInset);
		return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
	}

	/*@Override
	protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( width < 0 )
		{
			return Double.MIN_VALUE;
		}
		else
		{
			return 240000.0 / width;
		}
	}*/

	@Override
	protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
//		System.out.printf("TextButtonSkin.computeMinWidth(%f, %f, %f, %f, %f)\n",height,topInset,rightInset,bottomInset,leftInset);
		return label.get().minWidth(height);
	}

	/*@Override
	protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		if ( width < 0 )
		{
			return 400;
		}
		else
		{
			return 240000.0 / width;
		}
	}*/

	@Override
	protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
//		System.out.printf("TextButtonSkin.computePrefWidth(%f, %f, %f, %f, %f)\n",height,topInset,rightInset,bottomInset,leftInset);
		return label.get().prefWidth(height)+leftInset+rightInset;
	}
	
	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight)
	{
		label.get().resizeRelocate(contentX, contentY, contentWidth, contentHeight);
	}

	/* (non-Javadoc)
	 * @see de.yehoudie.control.button.CustomTButtonSkin#setBehaviorBase()
	 */
	@Override
	protected void setBehaviorBase()
	{
		this.behavior = new TextButtonBehavior<TextButton>(this.control);
	}
}
