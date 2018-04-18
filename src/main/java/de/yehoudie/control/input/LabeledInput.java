package de.yehoudie.control.input;

import de.yehoudie.types.CssPseudoClasses;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

/**
 * A VBox layouted label / input pair wrapper.
 */
public class LabeledInput extends VBox
{
	public static enum Type { TEXT_IPT, TEXTAREA_IPT, FILE_IPT, DIRECTORY_IPT, PASSWORD_IPT, NUMBER_IPT, CHOICE_BOX_IPT, CHOICE_IPT };

	private static PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

	BooleanProperty error = new BooleanPropertyBase(false)
	{
		public void invalidated()
		{
			pseudoClassStateChanged(ERROR_PSEUDO_CLASS, get());
		}

		@Override
		public Object getBean()
		{
			return LabeledInput.this;
		}

		@Override
		public String getName()
		{
			return "error";
		}
	};

	public void setError(boolean error)
	{
		this.error.set(error);
	}
	    
	private Type type;
	private TextInputControl input;
	private Label label;
//	private EventHandler<MouseEvent> input_mouse_handler;
	protected ChangeListener<Boolean> focus_listener;
	
	/**
	 * Input Textfield with a label.<br>
	 * Layouted by a VBox.
	 * 
	 * @param	type Type the textfield type
	 * @param	label_text String the value of the label
	 * @param	css_class String a css class name
	 */
	public LabeledInput(Type type, String label_text, String css_class)
	{
		this.type = type;
		this.getStyleClass().add("labeled-input");
		this.getStyleClass().add(css_class);
		
		init();

		label.setText(label_text);
	}

	/**
	 * 
	 */
	protected void init()
	{
		draw();
		initHandler();
		
		input.focusedProperty().addListener(focus_listener);
	}

	protected void initHandler()
	{
//		input_mouse_handler = e->onMouseEvent(e);
		focus_listener = new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean old_value, Boolean new_value)
			{
				onFocusChanged(new_value);
			}
		};
	}

	/**
	 * Add the typed input and the label.
	 */
	protected void draw()
	{
		addInput();
		addLabel();	
	}

	/**
	 * Add the label.
	 */
	protected void addInput()
	{
		input = null;
		if ( type == Type.FILE_IPT ) input = new FileInput();
		else if ( type == Type.DIRECTORY_IPT ) input = new DirectoryInput();
		else if ( type == Type.PASSWORD_IPT ) input = new PasswordField();
		else if ( type == Type.TEXTAREA_IPT ) input = new TextAreaInput();
		else if ( type == Type.NUMBER_IPT ) input = new NumberInput();
		else if ( type == Type.CHOICE_BOX_IPT ) input = new ChoiceBoxInput();
		else if ( type == Type.CHOICE_IPT ) input = new ChoiceInput();
		else input = new TextFieldInput();
		input.getStyleClass().add("input");

		this.getChildren().add(input);	
	}

	/**
	 * Add the input.
	 */
	protected void addLabel()
	{
		label = new Label();
		label.getStyleClass().add("label");
		
		this.getChildren().add(label);
	}
	
	public String getText()
	{
		return input.getText();
	}
	
	public void setText(String value)
	{
		if ( value == null ) return;
		input.setText(value);
	}
	
	public void setLimit(int value)
	{
		((TextInput)input).setLimit(value);
	}
	
	public int getLimit()
	{
		return ((TextInput)input).getLimit();
	}

	/**
	 * Get the text of the label.
	 * 
	 * @return	String
	 */
	public String getLabelText()
	{
		return label.getText();
	}
	
	/**
	 * Set the text of the label.
	 * 
	 * @param	value String
	 */
	public void setLabelText(String value)
	{
		label.setText(value);
	}
	
	public TextInputControl getInput()
	{
		return input;
	}
	
	public void activate()
	{
//		input.addEventHandler(MouseEvent.MOUSE_ENTERED, input_mouse_handler);
//		input.addEventHandler(MouseEvent.MOUSE_EXITED, input_mouse_handler);
//		input.focusedProperty().addListener(focus_listener);
	}
	
	public void deactivate()
	{
//		input.removeEventHandler(MouseEvent.MOUSE_ENTERED, input_mouse_handler);
//		input.removeEventHandler(MouseEvent.MOUSE_EXITED, input_mouse_handler);
//		input.focusedProperty().removeListener(focus_listener);
	}
	
	/*private void onMouseEvent(MouseEvent e)
	{
		System.out.println("LabeledInput.onMouseEvent("+e+")");
		System.out.println(" - type: "+e.getEventType());
	}*/
	
	private void onFocusChanged(boolean is_focused)
	{
		this.pseudoClassStateChanged(CssPseudoClasses.FOCUSED_PSEUDO_CLASS, is_focused);
	}

	/**
	 * Clean up.
	 */
	public void dispose()
	{
		if ( input != null )
		{
			input.focusedProperty().removeListener(focus_listener);
		}
	}

	public void setTooltip(String value)
	{
		label.setTooltip(new Tooltip(value));
	}

	public void setPrompt(String value)
	{
		input.setPromptText(value);
	}
}
