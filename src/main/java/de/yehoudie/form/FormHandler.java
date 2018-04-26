package de.yehoudie.form;

import java.util.ArrayList;
import java.util.function.Consumer;

import de.yehoudie.control.input.TextInput;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FormHandler
{
	ArrayList<TextInputControl> inputs;
	ArrayList<String> values;

	// key access
	private EventHandler<KeyEvent> onKey;
//	private Scene _scene;
	private KeyCode short_cut;
	
	private Consumer<FormHandler> submit_callback;
	private String name;
	
	private TextInputControl key_handling_source;
	
	/**
	 * A form action handler.<br>
	 * Inputs may be added, that get submitted by a key event.
	 * 
	 * @param	submit_callback Consumer<FormHandler> the submit callback
	 */
	public FormHandler(Consumer<FormHandler> submit_callback)
	{
		this.submit_callback = submit_callback;
		this.inputs = new ArrayList<>();
		this.values = new ArrayList<>();
		
		init();
	}

	private void init() {}

	/**
	 * Add a textfield to the input list.
	 * 
	 * @param	input TextField the text input
	 * @return	int the array index
	 */
	public int addInput(TextInputControl input)
	{
		inputs.add(input);
		return inputs.size()-1;
	}

	/**
	 * Handler for key events:<br>
	 *  - handles {@link #short_cut} key
	 * 
	 * @param	e KeyEvent
	 */
	private void onKeyHandler(KeyEvent e)
	{
//		System.out.println("FormHandler.onKeyHandler("+e+")");
//		System.out.println(" - source: "+e.getSource());
		// System.out.println(" - e.getCode(): "+e.getCode());
		// System.out.println(" - short_cut: "+_short_cut);
		// System.out.println(" - e.getCode() == _short_cut: "+(e.getCode() == _short_cut));
		key_handling_source = (TextInputControl) e.getSource();
		
		if ( e.getCode() == short_cut )
		{
			 submit();
		}
	}
	
	/**
	 * Submit the form.<br>
	 * Fill values and call back.
	 */
	private void submit()
	{
		/*for ( TextField input : inputs )
		{
			values.add(input.getText());
		}*/
		fillValues();
		
		submit_callback.accept(this);
	}

	/**
	 * Fill all input values into a list.
	 */
	public void fillValues()
	{
		values.clear();
		inputs.stream()
			.filter(ipt->(ipt.getText()!=null))
			.forEach(ipt->values.add(ipt.getText().trim()));
	}
	
	public void clear()
	{
		inputs.forEach(ipt->ipt.clear());
	}

	/**
	 * Activate the button.
	 */
	public void activate()
	{
		addListener();
	}

	/**
	 * Deactivate the button.
	 */
	public void deactivate()
	{
		// System.out.println("ToolboxItem.deactivate");
		removeListener();
	}

	/**
	 * Add all event listener.
	 */
	protected void addListener()
	{
		if ( short_cut != null ) inputs.stream().forEach(i->i.addEventHandler(KeyEvent.KEY_PRESSED, onKey));
//		if ( _short_cut != null &&  _key_target != null ) _key_target.addEventHandler(KeyEvent.KEY_PRESSED, _onKey);
	}

	/**
	 * Remove all event listener.
	 */
	protected void removeListener()
	{
		if ( short_cut != null ) inputs.stream().forEach(i->i.removeEventHandler(KeyEvent.KEY_PRESSED, onKey));
//		if ( _short_cut != null &&  _key_target != null ) _key_target.removeEventHandler(KeyEvent.KEY_PRESSED, _onKey);
	}
	
	public boolean hasEmptyValues()
	{
		if ( values.size() == 0 ) return true;
		
		for ( String value : values ) if ( value.isEmpty()  ) return true;
		
		return false;
	}
	
	/**
	 * Add a key code short cut to enable button click action with a key press.
	 * 
	 * @param short_cut KeyCode the key code to set
	 */
	public void setShortCut(KeyCode short_cut)
	{
//		System.out.printf("FormHandler.setShortCut(%s, %s)\n",short_cut, scene);
		this.short_cut = short_cut;

		if ( onKey == null ) onKey = e -> onKeyHandler(e);
	}

	/**
	 * Get the key code short cut which enables button click action with a key press.
	 */
	public KeyCode getShortCut()
	{
		return short_cut;
	}
	
	public ArrayList<TextInputControl> getInputs() { return inputs; }
	public ArrayList<String> getValues() { return values; }

	public void setName(String value)
	{
		this.name = value;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public TextInputControl getKeyHandlingSource()
	{
		return key_handling_source;
	}
}
