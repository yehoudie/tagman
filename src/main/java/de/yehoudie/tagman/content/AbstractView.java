package de.yehoudie.tagman.content;

import java.util.ArrayList;

import de.yehoudie.control.input.LabeledInput;
import de.yehoudie.crypto.Sha256;
import de.yehoudie.form.FormHandler;
import de.yehoudie.tagman.Root;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextInputControl;

/**
 * @author	yehoudie
 *
 */
public abstract class AbstractView extends VBox
{
	protected Root root;

	protected EventHandler<KeyEvent> on_key;

	protected FormHandler form;

	protected ArrayList<TextInputControl> inputs;
	protected ArrayList<LabeledInput> labeled_inputs;

	/**
	 * An abstract content view base class.
	 * 
	 * @param	root Root the root controller object.
	 */
	public AbstractView(Root root)
	{
		super();
		
		this.root = root;
	}
	
	/**
	 * Initialize.
	 */
	protected void init()
	{
		on_key = e -> onKeyHandler(e);
		
		inputs = new ArrayList<>();
		labeled_inputs = new ArrayList<>();

		form = new FormHandler(this::onSubmit);
		form.setShortCut(KeyCode.ENTER);
	}

	/**
	 * Fill up the view with elements.
	 * 
	 * @param	types LabeledInput.Type[]
	 * @param	labels String[]
	 * @param	css_classes String[]
	 * @param	ids int[]
	 */
	protected void fill(LabeledInput.Type[] types, String[] labels, String[] css_classes, int[] ids)
	{
		int input_size = types.length;
		for ( int i = 0; i < input_size; i++ )
		{
			LabeledInput ipt = new LabeledInput(types[i], labels[i], css_classes[i]);
			inputs.add(ids[i], ipt.getInput());
			labeled_inputs.add(ids[i], ipt);
			
			form.addInput(ipt.getInput());

			this.getChildren().add(ipt);
		}
	}

	/**
	 * Handler for key events<br>
	 *  - handles ESCAPE key
	 * 
	 * @param e KeyEvent
	 */
	protected void onKeyHandler(KeyEvent e)
	{
//		 System.out.println("FormHandler.onKeyHandler("+e+")");
		// System.out.println(" - e.getCode(): "+e.getCode());
		// System.out.println(" - short_cut: "+_short_cut);
		// System.out.println(" - e.getCode() == _short_cut: "+(e.getCode() == _short_cut));
		if ( e.getCode() == KeyCode.ESCAPE )
		{
			 quit();
		}
	}

	/**
	 * Quit/Close entry.<br>
	 * Loses changes.
	 */
	abstract protected void quit();

	public abstract void activate();
	public abstract void deactivate();

	/**
	 * Submit handler.
	 * 
	 * @param	form FormHandler the submitting form.
	 */
	protected void onSubmit(FormHandler form) {}

	/**
	 * Convert form values list to concatenated string.<br>
	 * This is used for hashing, not to print.
	 * 
	 * @param	values ArrayList<String>
	 * @return	String
	 */
	protected String valuesToString(ArrayList<String> values)
	{
		StringBuilder values_sb = new StringBuilder();
		for ( String value : values ) values_sb.append(value);
		return values_sb.toString();
	}

	/**
	 * Create hash string of all form values.
	 * 
	 * @return	String
	 */
	protected String getHash()
	{
		return Sha256.hash(valuesToString(form.getValues()));
	}

	/**
	 * Mark an error field.
	 * 
	 * @param	ipt_id int the input field id
	 * @param	has_error boolean the error status
	 */
	protected void markErrorField(int ipt_id, boolean has_error)
	{
		labeled_inputs.get(ipt_id).setError(has_error);
	}
	
	protected boolean isEmpty(int input_id)
	{
		boolean error = inputs.get(input_id).getText().isEmpty();
		markErrorField(input_id, error);
		
		return error;
	}
	
	public ArrayList<String> getValues()
	{
		return form.getValues();
	}
}
