package de.yehoudie.control.input;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Skin;

/**
 * An input textfield which restricts the values to some given choices.<br>
 * These are selectable via an opening ContextMenu.<br>
 * TODO: move skin and behavior into its classes.
 * 
 * @author yehoudie
 */
public class ChoiceInput extends TextFieldInput
{
	// entries to autocomplete
	private final SortedSet<String> options;
	public SortedSet<String> getOptions() { return options; }
	public void addOption(String value) { options.add(value); MenuItem item = addOptionEntry(value); selectEntry(item); }
	public void addOptions(String[] values) { for (String value : values ) { addOption(value); } }
	
	// popup GUI
	private ContextMenu options_popup;
	private boolean can_be_empty;
	private List<MenuItem> menu_items;
	private MenuItem active_item;
	
	/**
	 * An input textfield which restricts the values to some given choices.<br>
	 * These are selectable via an opening ContextMenu.
	 */
	public ChoiceInput()
	{
		super();
		this.options = new TreeSet<>();
		this.options_popup = new ContextMenu();
//		this.options_popup.setAutoHide(false);
		this.menu_items = new LinkedList<>();
		this.getStyleClass().add("choice-input");
		this.setEditable(false);
		
		setListener();
	}

	/**
	 * Listener
	 */
	private void setListener()
	{
		// since setText may not be overriden, listen for changed of the textProperty
		// and select a menu item, if available
		textProperty().addListener((observable, old_text, new_text) -> {
			if ( new_text != null && !new_text.isEmpty() )
			{
				if ( !verify(new_text) )
				{
//					System.out.println("not verified");
					setText(old_text.isEmpty()?options.first():old_text);
					return;
				}
				
//				System.out.println("verified");
				for ( MenuItem item : menu_items )
				{
					if ( selectEntry(item) ) return;
				}
			}
		});
		setOnMouseClicked(e->onFocusChanged(true));
	}
	
	/**
	 * Verify that text equals possible choices.
	 */
	private boolean verify(String text)
	{
//		System.out.printf("ChoiceInput.verify()\n");
		if ( options.size() == 0 ) return true;
		
		if ( options.contains(text) ) return true;
		
		return false;
	}
	
	/**
	 * Handle focus change.
	 * 
	 * @param	is_focused boolen
	 */
	protected void onFocusChanged(Boolean is_focused)
	{
//		System.out.println(getId()+" focus: "+is_focused);
		if ( is_focused && !options_popup.isShowing() )
		{
			options_popup.show(ChoiceInput.this, Side.BOTTOM, 0, 0);
		}
		else
		{
			options_popup.hide();
			autoComplete();
		}
	}
	
	/**
	 * Autocomplete entered text or fill with default.
	 */
	private void autoComplete()
	{
		if ( can_be_empty ) return;
		
		// check for exact matches
		if ( options.contains(getText()) ) return;

		// complte partly matches
		for ( String option : options )
		{
			if ( option.startsWith(getText()) ) setText(option);
		}
	}

	/**
	 * @param search_result
	 */
	protected MenuItem addOptionEntry(final String result)
	{
//		Text text = new Text(result);
//		CustomMenuItem item = new CustomMenuItem(text, true);
		MenuItem item = new MenuItem(result);
		menu_items.add(item);

		// if any suggestion is select set it into text and close popup
		item.setOnAction(actionEvent -> {
			setText(result);
			positionCaret(result.length());
			options_popup.hide();
			toggleItem(item);
		});
		
		options_popup.getItems().add(item);
		
		return item;
	}
	
	/**
	 * Select the item, if the current text is its value.
	 * 
	 * @param	item MenuItem
	 */
	private boolean selectEntry(MenuItem item)
	{
//		System.out.println("selectEntry("+item+")");
//		System.out.println(" . getText(): "+getText());
//		System.out.println(" . item.getText(): "+item.getText());
		if ( getText().equals(item.getText()) )
		{
			toggleItem(item);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param item
	 */
	protected void toggleItem(MenuItem item)
	{
		if ( item == active_item ) return;
		
		if ( active_item != null ) active_item.setDisable(false);
		item.setDisable(true);
		active_item = item;
	}

	/**
	 * Nothing to do here.
	 */
	@Override
	public void replaceText(int start, int end, String text)
	{}

	/**
	 * Nothing to do here.
	 */
	@Override
	public void replaceSelection(String text)
	{}

	public void setDefault()
	{
		
	}
	
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new ChoiceInputSkin(this);
	}
}
