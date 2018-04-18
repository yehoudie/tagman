package de.yehoudie.control.button;

import javafx.scene.control.skin.RadioButtonSkin;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * A radio button supporting a sub labe as a second line.<br>
 * The sub_label is stylable independently.<br>
 * TODO: Support alignment values other than top_left
 */
public class DoubleLineRadioButtonSkin extends RadioButtonSkin
{
	protected Label sub_label;
	private DoubleLineRadioButton control;
	private Text label;
	private StackPane radio;

	/**
	 * A radio button with support for a second line sub label.
	 * 
	 * @param	control DoubleLineRadioButton the controler
	 */
	public DoubleLineRadioButtonSkin(DoubleLineRadioButton control)
	{
		super(control);
		this.control = control;

		init();
	}

	private void init()
	{
		createSubTextLabel();
		bindProperties();
		updateChildren();
		
		ObservableList<Node> children = this.getChildren();
		label = (Text) children.get(0);
		radio = (StackPane) children.get(1);
	}

	private void bindProperties()
	{
		this.sub_label.textProperty().bind(this.control.subTextProperty());
	}

	private void unbindProperties()
	{
		this.sub_label.textProperty().unbind();
	}

	@Override
	protected void updateChildren()
	{
		super.updateChildren();
		if ( sub_label != null )
		{
			getChildren().add(sub_label);
		}
	}

	/**
	 * Create the sub text label
	 */
	public void createSubTextLabel()
	{
		sub_label = new Label();
		sub_label.getStyleClass().add("sub_label");
		sub_label.setMouseTransparent(true);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		unbindProperties();
	}

	/***************************************************************************
	 * * Layout * *
	 **************************************************************************/

	@Override
	protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
//		return super.computeMinWidth(height, topInset, rightInset, bottomInset, leftInset);
	}

	@Override
	protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
//		return super.computeMinHeight(width, topInset, rightInset, bottomInset, leftInset);
	}

	@Override
	protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
//		System.out.printf("DoubelLineRadioButton.computePrefWidth(%f, %f, %f, %f, %f, )\n", height, topInset, bottomInset, leftInset, rightInset);
		
		double super_pref_width = super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
		double radio_pref_width = snapSize(radio.prefWidth(-1));
		double sub_label_pref_width = sub_label.prefWidth(-1) + control.labelPaddingProperty().get().getLeft();
		
		return Math.max(super_pref_width, sub_label_pref_width+radio_pref_width) + leftInset + rightInset;
	}

	@Override
	protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
//		return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset)+50;
//		System.out.printf("DoubelLineRadioButton.computePrefHeight(%f, %f, %f, %f, %f, )\n", width, topInset, bottomInset, leftInset, rightInset);
		
		double label_height = label.prefHeight(-1) + control.labelPaddingProperty().get().getTop();
		double sub_label_height = sub_label.prefHeight(-1);
		
		return Math.max(radio.getHeight(), label_height+sub_label_height)+topInset+bottomInset;
	}
	
	@Override
	protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
	}
	
	@Override
	protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
	}

	@Override
	protected void layoutChildren(final double x, final double y, final double w, final double h)
	{
		super.layoutChildren(x, y, w, h);
//		System.out.printf("DoubleLineRadioButton.layoutChildren(%f, %f, %f, %f)\n", x, y, w, h);

		sub_label.resize(sub_label.prefWidth(-1), sub_label.prefHeight(-1));
		sub_label.relocate(label.getLayoutX(), label.prefHeight(-1)+control.labelPaddingProperty().get().getTop());
	}

	protected double computeXOffset(double width, double contentWidth, HPos hpos)
	{
		if ( hpos == null )
		{
			return 0;
		}

		switch ( hpos )
		{
			case LEFT:
				return 0;
			case CENTER:
				return (width - contentWidth) / 2;
			case RIGHT:
				return width - contentWidth;
			default:
				return 0;
		}
	}

	protected double computeYOffset(double height, double contentHeight, VPos vpos)
	{
		if ( vpos == null )
		{
			return 0;
		}

		switch ( vpos )
		{
			case TOP:
				return 0;
			case CENTER:
				return (height - contentHeight) / 2;
			case BOTTOM:
				return height - contentHeight;
			default:
				return 0;
		}
	}
}
