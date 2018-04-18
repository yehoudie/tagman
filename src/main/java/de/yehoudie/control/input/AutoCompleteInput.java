package de.yehoudie.control.input;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AutoCompleteInput extends TextFieldInput
{
	// entries to autocomplete
	private final SortedSet<String> options;
	 /**
	   * Get the existing set of autocomplete entries.
	   * 
	   * @return The existing autocomplete entries.
	   */
	public SortedSet<String> getOptions() { return options; }
	public void addOption(String value) { options.add(value); }
	
	// popup GUI
	private ContextMenu options_popup;
	final private int max_entries = 10;
	private boolean can_be_empty;

	public AutoCompleteInput()
	{
		super();
		this.options = new TreeSet<>();
		this.options_popup = new ContextMenu();
		this.getStyleClass().add("choice-input");
		
		setListener();
	}

	/**
	 * "Suggestion" specific listners
	 */
	private void setListener()
	{
		// Add "suggestions" by changing text
		textProperty().addListener((observable, old_text, new_text) -> {

			// always hide suggestion if nothing has been entered
//			if ( enteredText == null || enteredText.isEmpty() )
//			{
//				options_popup.hide();
//			}
//			else
			{
				// filter all possible suggestions depends on "Text", case insensitive
				filterText(new_text);
			}
		});

		// Hide always by focus-in (optional) and out
		focusedProperty().addListener((observable, old_value, new_value) -> {
			onFocusChanged(new_value);
		});
	}
	
	/**
	 * Handle focus change.
	 * 
	 * @param	is_focused boolen
	 */
	protected void onFocusChanged(Boolean is_focused)
	{
		System.out.println(getId()+" focus: "+is_focused);
		options_popup.hide();
		if ( !is_focused ) autoComplete();
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
	 * @param	entered_text String the entered text
	 */
	protected void filterText(String entered_text)
	{
		System.out.println();
		List<String> filtered_entries = options.stream().filter(e -> e.toLowerCase().contains(entered_text.toLowerCase())).collect(Collectors.toList());

		if ( filtered_entries.isEmpty() ) options.stream().forEach(o->filtered_entries.add(o));
		
		// some suggestions are found
		if ( !filtered_entries.isEmpty() )
		{
			// build popup - list of "CustomMenuItem"
			populatePopup(filtered_entries, entered_text);
			if ( !options_popup.isShowing() )
			{
				// optional position of popup
				options_popup.show(AutoCompleteInput.this, Side.BOTTOM, 0, 0);
			}
			// no suggestions -> hide
		}
		else
		{
			options_popup.hide();
		}
	}

	/**
	 * Populate the entry set with the given search results. Display is limited to 10 entries, for
	 * performance.
	 * 
	 * @param search_result List<String> the set of matching strings.
	 * @param search_request String the set of matching strings.
	 */
	private void populatePopup(List<String> search_result, String search_request)
	{
		// List of "suggestions"
		List<CustomMenuItem> menu_items = new LinkedList<>();
		int count = Math.min(search_result.size(), max_entries);
		// Build list as set of labels
		for ( int i = 0; i < count; i++ )
		{
			final String result = search_result.get(i);
			// label with graphic (text flow) to highlight founded subtext in suggestions
			Label label = new Label("dd",new TextFlow(new Text("ff")));
//			label.setGraphic(Styles.buildTextFlow(result, search_request));
//			label.setPrefHeight(10); // don't sure why it's changed with "graphic"
			CustomMenuItem item = new CustomMenuItem(Styles.buildTextFlow(result, search_request), true);
			menu_items.add(item);

			// if any suggestion is select set it into text and close popup
			item.setOnAction(actionEvent -> {
				setText(result);
				positionCaret(result.length());
				options_popup.hide();
			});
		}

		// "Refresh" context menu
		options_popup.getItems().clear();
		options_popup.getItems().addAll(menu_items);
	}

	@Override
	public void replaceText(int start, int end, String text)
	{
		// System.out.printf("TextInput.replaceText(%d, %d, %s)\n",start,end,text);
		if ( verify(start, end, text) )
		{
			super.replaceText(start, end, text);
		}
		else
		{
			if ( getText().isEmpty() ) filterText("");
		}
	}

	@Override
	public void replaceSelection(String text)
	{
		// System.out.printf("TextInput.replaceSelection(%s)\n",text);
		super.replaceSelection(text);
	}

	/**
	 * Verify that text equals possible choices.
	 */
	private boolean verify(int start, int end, String text)
	{
//		System.out.printf("ChoiceInput.verify()\n");
		if ( options.size() == 0 ) return true;
		
		String text_before = getText().substring(0, start);
		String text_after = getText().substring(end);
		StringBuilder new_text_sb = new StringBuilder();
		new_text_sb.append(text_before).append(text).append(text_after); 
		String new_text = new_text_sb.toString();

//		filterText(new_text);
		
		for ( String option : options )
		{
			if ( option.startsWith(new_text) ) return true;
		}
		
		return false;
	}

	
	/**
	 * open file browser on focus
	 */
	@Override
	public void requestFocus()
	{
		System.out.println("ChoiceInput.requestFocus() "+getId());
		fireEvent(new InputEvent(InputEvent.FOCUS_REQUEST));
		
		super.requestFocus();
	}
	
	public void dispose()
	{
		super.dispose();
	}
}
class Styles
{
	/**
	 * Build TextFlow with selected text. Return "case" dependent.
	 * 
	 * @param	haystack String the text
	 * @param	needle String substring to select in text
	 * @return	TextFlow
	 */
	public static TextFlow buildTextFlow(String haystack, String needle)
	{
//		System.out.printf("buildTextFlow(%s, %s)\n",haystack,needle);
//		System.out.println(" - needle.isEmpty(): "+needle.isEmpty());
		if ( needle.isEmpty() ) return new TextFlow(new Text(haystack));
		
//		boolean found = true;
		int filter_index = haystack.toLowerCase().indexOf(needle.toLowerCase());
		int needle_ln = needle.length();
//		System.out.println(" - filterIndex: "+filter_index);
//		System.out.println(" - needle_ln: "+ needle_ln);
		/*if  ( filter_index < 0 )
		{
			found = false;
			needle_ln -= filter_index;
			filter_index = 0;
		}*/
//		if  ( filterIndex < 0 ) return new TextFlow(new Text(haystack));
//		System.out.println(" - haystack ln: "+ haystack.length());
//		System.out.println(" - filterIndex + needle_ln: "+ (filter_index + needle_ln));
//		System.out.println(" - before: "+ haystack.substring(0, filter_index));
//		System.out.println(" - after: "+ haystack.substring(filter_index, needle_ln));
//		System.out.println(" - filter: "+ haystack.substring(filter_index, filter_index + needle_ln));
		Text before = new Text(haystack.substring(0, filter_index));
		Text after = new Text(haystack.substring(filter_index + needle_ln));
		// instead of "filter" to keep all "case sensitive"
		Text filter = new Text(haystack.substring(filter_index, filter_index + needle_ln));
		
		before.getStyleClass().add("before");
		filter.getStyleClass().add("filter");
		after.getStyleClass().add("after");

		TextFlow flow = new TextFlow(before, filter, after);
		flow.getStyleClass().add("option_label");
		
		return flow;
	} 
}