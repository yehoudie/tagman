package de.yehoudie.control.input;

import de.yehoudie.control.BehaviorBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PasswordFieldSkin<T extends PasswordField> extends TextFieldInputSkin<T>
{
    static final char BULLET = '\u25cf';

    protected BooleanProperty mask_text = new SimpleBooleanProperty(this, "mask_text", true);
    
	protected ObjectProperty<ImageView> iconProperty() { return icon; };
	private ObjectProperty<ImageView> icon = new SimpleObjectProperty<>(this, "icon");
	
	protected ObjectProperty<Rectangle> iconBgProperty() { return icon_bg; };
	private ObjectProperty<Rectangle> icon_bg = new SimpleObjectProperty<>(this, "icon_bg");

	private ObjectProperty<Node> show_icon = new SimpleObjectProperty<>(this, "show_icon");
    private ObjectProperty<Node> hide_icon = new SimpleObjectProperty<>(this, "hide_icon");
    private ObjectProperty<Pos> icon_alignment = new SimpleObjectProperty<>(this, "icon_alignment");
	private double super_width;
	private boolean has_icons;

	protected BehaviorBase<T> behavior;
	private double width;

	public PasswordFieldSkin(T control)
	{
		super(control);

		this.behavior = new PasswordFieldBehavior<T>(this.control, this);
		
		init();
	}

	private void init()
	{
		bindProperties();

		initIcons();
		
		has_icons = show_icon.get()!=null && hide_icon.get()!=null;

		if ( !has_icons ) return;
		
		setIcon();
		initIconBg();
		
		this.getChildren().add(icon_bg.get());
		addIcon(icon.get());
	}

	private void initIconBg()
	{
		Rectangle icon_bg = new Rectangle(icon.get().getFitWidth(), icon.get().getFitHeight(), Color.web("#99000000"));
		icon_bg.setCursor(Cursor.HAND);
		icon_bg.setManaged(false);
		
		this.icon_bg.set(icon_bg);
	}

	/**
	 * Set the actual mask state icon.
	 */
	private void setIcon()
	{
		if ( !has_icons ) return;
		
		if ( mask_text.get() ) icon.set( (ImageView) show_icon.get() );
		else icon.set( (ImageView) hide_icon.get() );
	}

	/**
	 * 
	 */
	private void bindProperties()
	{
		show_icon.bind(this.control.showIconProperty());
		hide_icon.bind(this.control.hideIconProperty());
		icon_alignment.bind(this.control.iconAlignmentProperty());
		mask_text.bind(this.control.mask_text);
	}

	private void unbindProperties()
	{
		show_icon.unbind();
		hide_icon.unbind();
		icon_alignment.unbind();
		mask_text.unbind();
	}

	/**
	 * Add a button to change password visibility.
	 */
	private void initIcons()
	{
//		System.out.println("PasswordFieldSkin.addPasswordVisibilityButton()");
//		System.out.println(" - preserve_ratio: "+this.control.iconPreserveRatioProperty().get());

		initIcon((ImageView) show_icon.get());
		initIcon((ImageView) hide_icon.get());
	}

	private void initIcon(ImageView icon)
	{
		if ( icon == null ) return;
		
		icon.setManaged(false);
		icon.setFitWidth(this.control.iconWidthProperty().get());
		icon.setFitHeight(this.control.iconHeightProperty().get());
		icon.setPreserveRatio(this.control.iconPreserveRatioProperty().get());
	}

	/**
	 * Handle a change in visibility for the password value.
	 */
	protected void switchMask(boolean mask)
	{
//		System.out.println("PasswordFieldSkine.switchMask("+mask+")");
		control.setText(control.getText());
	}
	
	protected void switchIcon()
	{
		this.getChildren().remove(icon.get());
		setIcon();
		this.getChildren().add(icon.get());
	}
	
	private void addIcon(Node icon)
	{
		if ( icon == null ) return;
		
		getChildren().add(icon);
	}

	@Override
	protected String maskText(String txt)
	{
		if ( mask_text != null && mask_text.get() )
		{
			int n = txt.length();
			StringBuilder passwordBuilder = new StringBuilder(n);
			for ( int i = 0; i < n; i++ )
			{
				passwordBuilder.append(BULLET);
			}

			return passwordBuilder.toString();
		}
		else
		{
			return txt;
		}
	}

	@Override
	protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		super_width = super.computeMinWidth(height, topInset, rightInset, bottomInset, leftInset);
		width = super_width;
		return super_width;
	}

	@Override
	protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		return super.computeMinHeight(width, topInset, rightInset, bottomInset, leftInset);
	}

	@Override
	protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
//		System.out.printf("PasswordFieldSkin.computePrefWidth(%f, %f, %f, %f, %f, )\n", height, topInset, bottomInset, leftInset, rightInset);
		super_width = super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
//		System.out.println(" - super_width: "+super_width);
		width = super_width;
		if ( has_icons )
		{
			ImageView icon = this.icon.get();
			
//			System.out.printf(" - icon pos: %f , %f\n", icon.getLayoutX(),icon.getLayoutY());
//			System.out.printf(" - icon width: %f , %f\n", icon.getFitWidth(),icon.getFitHeight());
			double w = icon.getFitWidth() + icon.getLayoutX();
//			System.out.printf(" - w: %f\n", w);
			width = Math.max(width, w);
		}
//		System.out.printf(" - width: %f\n", width);
		return super_width;
	}

	@Override
	protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
	}
	
	@Override
	protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset)
	{
//		System.out.printf("PasswordFieldSkin.computeMaxWidth(%f, %f, %f, %f, %f, )\n", height, topInset, bottomInset, leftInset, rightInset);
		super_width = super.computeMaxWidth(height, topInset, rightInset, bottomInset, leftInset);
		width = super_width;
		return super_width;
	}
	
	@Override
	protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset)
	{
		return super.computeMaxHeight(width, topInset, rightInset, bottomInset, leftInset);
	}
	
	/* (non-Javadoc)
	 * @see javafx.scene.control.skin.TextFieldSkin#layoutChildren(double, double, double, double)
	 */
	@Override
	protected void layoutChildren(final double x, final double y, final double w, final double h)
	{
//		System.out.printf("PasswordFieldSkin.layoutChildren(%f, %f, %f, %f, )\n", x, y, w, h);
//		System.out.println(" w : "+w);
//		System.out.printf(" insets : %f , %f\n",snappedLeftInset(), snappedRightInset());
//		double sw = super_width - snappedLeftInset() - snappedRightInset();
//		System.out.printf(" sw : %f\n",sw);
		super.layoutChildren(x, y, w, h);
//		System.out.println("PasswordFieldSkin.layoutChildren()");
//		System.out.println(" - this.control.getShowIcon(): "+this.control.getShowIcon());
//		System.out.println(" - this.control.showIconProperty().getValue(): "+this.control.showIconProperty().getValue());
		
		if ( has_icons )
		{
			ImageView icon = this.icon.get();
			
			layoutHPos(w, icon);
			layoutVPos(h, icon);
		}
	}

	/**
	 * @param w
	 * @param icon
	 */
	private void layoutHPos(final double w, ImageView icon)
	{
		switch ( icon_alignment.get().getHpos() )
		{
			case LEFT:
//				System.out.println("icon left");
				// textY = ascent;
//					icon.setX(10);
//					icon.relocate(10, 0);
					icon.setLayoutX(0);
				break;

			case CENTER:
//				System.out.println("icon center");
				// textY = (ascent + textGroup.getHeight() - descent) / 2;
				icon.setLayoutX(w/2);
				break;

			case RIGHT:
			default:
//				System.out.println("icon right");
				// textY = textGroup.getHeight() - descent;
				icon.setLayoutX(w);
				break;
		}
		icon_bg.get().setLayoutX(icon.getLayoutX());
	}
	
	/**
	 * @param w
	 * @param icon
	 */
	private void layoutVPos(final double h, ImageView icon)
	{
		switch ( icon_alignment.get().getVpos() )
		{
			case TOP:
//				System.out.println("icon top");
				// textY = ascent;
//					icon.setX(10);
				icon.setLayoutY(0);
				break;
				
			case CENTER:
//				System.out.println("icon center");
				// textY = (ascent + textGroup.getHeight() - descent) / 2;
				icon.setLayoutY(h/2);
				break;
				
			case BOTTOM:
			default:
//				System.out.println("icon bottom");
				// textY = textGroup.getHeight() - descent;
				icon.setLayoutX(h);
				break;
		}
	}
}
