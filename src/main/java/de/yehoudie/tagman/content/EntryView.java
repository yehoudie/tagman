package de.yehoudie.tagman.content;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.yehoudie.control.input.ChoiceInput;
import de.yehoudie.control.input.LabeledInput;
import de.yehoudie.form.FormHandler;
import de.yehoudie.mp3.Mp3File;
import de.yehoudie.tagman.AppTitle;
import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.dialogs.ConfirmSaveDialog;
import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.tagman.objects.TagData;
import javafx.scene.input.KeyEvent;

public class EntryView extends AbstractView
{
	private final static int FILE_NAME_IPT_ID = 0;
	private final static int NEW_FILE_NAME_IPT_ID = 1;
	private final static int TITLE_IPT_ID = 2;
	private final static int INTERPRET_IPT_ID = 3;
	private final static int ALBUM_IPT_ID = 4;
	private final static int YEAR_IPT_ID = 5;
	private final static int TITLE_NUMBER_IPT_ID = 6;
	private final static int GENRE_DESCRIPTION_IPT_ID = 7;
	
	// map to track data's initial state to know, if they have changed onSubmit
	private TagData act_data;
	private ConfirmSaveDialog confirm_clear_dialog;

	/**
	 * Show a form with values of an EntryData object.
	 * 
	 * @param	root Root 
	 */
	public EntryView(Root root)
	{
		super(root);
		
		this.getStyleClass().add("content");
		this.getStyleClass().add("entry_view");
		
		init();
	}
	
	/**
	 * Initialize form.
	 */
	@Override
	protected void init()
	{
		super.init();
		
		TextManager tm = TextManager.getInstance();
		
		LabeledInput file_name_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_FILE_NAME), "file_name_ipt");
		LabeledInput new_file_name_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_NEW_FILE_NAME), "new_file_name_ipt");
		LabeledInput title_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_TITLE), "title_ipt");
		LabeledInput interpret_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_INTERPRET), "interpret_ipt");
		LabeledInput album_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_ALBUM), "album_ipt");
		LabeledInput year_ipt = new LabeledInput(LabeledInput.Type.NUMBER_IPT, tm.get(TextManager.ENTRY_YEAR), "year_ipt");
		year_ipt.setLimit(4);
		LabeledInput title_number_ipt = new LabeledInput(LabeledInput.Type.NUMBER_IPT, tm.get(TextManager.ENTRY_TITLE_NUMBER), "title_number_ipt");
		title_number_ipt.setLimit(3);
		LabeledInput genre_ipt = new LabeledInput(LabeledInput.Type.CHOICE_IPT, tm.get(TextManager.ENTRY_GENRE), "genre_ipt");
		ChoiceInput genre_ci = (ChoiceInput) genre_ipt.getInput();
		genre_ci.addOptions(Mp3File.GENRES);
		
		inputs.add(FILE_NAME_IPT_ID, file_name_ipt.getInput());
		inputs.add(NEW_FILE_NAME_IPT_ID, new_file_name_ipt.getInput());
		inputs.add(TITLE_IPT_ID, title_ipt.getInput());
		inputs.add(INTERPRET_IPT_ID, interpret_ipt.getInput());
		inputs.add(ALBUM_IPT_ID, album_ipt.getInput());
		inputs.add(YEAR_IPT_ID, year_ipt.getInput());
		inputs.add(TITLE_NUMBER_IPT_ID, title_number_ipt.getInput());
		inputs.add(GENRE_DESCRIPTION_IPT_ID, genre_ipt.getInput());
		
		labeled_inputs.add(FILE_NAME_IPT_ID, file_name_ipt);
		labeled_inputs.add(NEW_FILE_NAME_IPT_ID, new_file_name_ipt);
		labeled_inputs.add(TITLE_IPT_ID, title_ipt);
		labeled_inputs.add(INTERPRET_IPT_ID, interpret_ipt);
		labeled_inputs.add(ALBUM_IPT_ID, album_ipt);
		labeled_inputs.add(YEAR_IPT_ID, year_ipt);
		labeled_inputs.add(TITLE_NUMBER_IPT_ID, title_number_ipt);
		labeled_inputs.add(GENRE_DESCRIPTION_IPT_ID, genre_ipt);
		
		inputs.stream().forEach( input->form.addInput(input) );
		
		this.getChildren().addAll(labeled_inputs);
	}

	/**
	 * Fill form with an EntryData.<br>
	 * If data==null, create new data object.<br>
	 * Track act state of new data to know, if it has changed onSubmit.
	 * 
	 * @param	data EntryData
	 */
	public void fill(TagData data)
	{
		System.out.println("EntryView.fill("+data+")");
		
		checkUnsavedChanges();
		clear();
		fillForm(data);
		
		act_data = data;
	}

	private void fillForm(TagData data)
	{
		if ( data == null ) return;
		
		labeled_inputs.get(FILE_NAME_IPT_ID).setText(data.getFileName());
		labeled_inputs.get(NEW_FILE_NAME_IPT_ID).setText(data.getNewFileName());
		labeled_inputs.get(TITLE_IPT_ID).setText(data.getTitle());
		labeled_inputs.get(INTERPRET_IPT_ID).setText(data.getInterpret());
		labeled_inputs.get(ALBUM_IPT_ID).setText(data.getAlbum());
		if (data.getYear()!=0) labeled_inputs.get(YEAR_IPT_ID).setText(String.valueOf(data.getYear()));
		if (data.getTitleNumber()!=0) labeled_inputs.get(TITLE_NUMBER_IPT_ID).setText(String.valueOf(data.getTitleNumber()));
		labeled_inputs.get(GENRE_DESCRIPTION_IPT_ID).setText(data.getGenreDescription());

		form.fillValues();
	}

	private void checkUnsavedChanges()
	{
		if ( act_data == null ) return;
		
		fillData(act_data, form.getValues());
		
		if ( hasChanged() )
		{
			if ( confirm_clear_dialog == null )
			{
				createConfirmDialog();
			}
			confirm_clear_dialog.showAndWait();
			confirm_clear_dialog.handleChoice();
		}
	}

	private void createConfirmDialog()
	{
		confirm_clear_dialog = new ConfirmSaveDialog(AppTitle.DEFAULT_VALUE);
		confirm_clear_dialog.setYesCallback(this::saveChanged);
	}
	
	private boolean saveChanged()
	{
		root.submitChange(act_data);
		return true;
	}
	
	public void clear()
	{
		form.clear();
		act_data = null;
	}

	public void update()
	{
		System.out.println("EntryView.update");
		System.out.println(" - act_data: "+act_data);
		if ( act_data == null ) return;
		
		fillForm(act_data);
	}

	/**
	 * Callback function of the form submit method.<br>
	 * Validate form.<br>
	 * Add changed entries to the changed list in root.<br>
	 * Remove unchanged entries from the changed list in root.
	 * 
	 * @param	form FormHandler the submitting form handler
	 */
	@Override
	protected void onSubmit(FormHandler form)
	{
		System.out.println("EntryView.onSubmit()");
		System.out.println(" - "+form.getValues());
		
		ArrayList<String> values = form.getValues();

		if ( hasErrors(values) ) return;
		
		fillData(act_data, values);
		
		if ( hasChanged() )
		{
			submitChange(values);
		}
		else
		{
			submitNoChange(values);
		}
	}

	private void submitChange(ArrayList<String> values)
	{
		System.out.println(" - values have changed, add to save list");
//			System.out.println(" - act_data: "+act_data.toFullString());
		root.submitChange(act_data);
	}

	private void submitNoChange(ArrayList<String> values)
	{
		System.out.println(" - has not changed, remove from save list");
//			System.out.println(" - act_data: "+act_data.toFullString());
		root.submitNoChange(act_data);
	}

	/**
	 * Check if form has errors.<br>
	 * 
	 * @param	values ArrayList<String> the form values
	 * @return	boolean
	 */
	private boolean hasErrors(ArrayList<String> values)
	{
		boolean has_errors = false;
		boolean act_error = false;
		
		// check for empty file name
		act_error = isEmpty(FILE_NAME_IPT_ID);
		if ( act_error ) has_errors = true;

		return has_errors;
	}

	/**
	 * Fill a EntryData object with actual form values.<br>
	 * 
	 * @param	data EntryData
	 * @param	values ArrayList<String>
	 */
	private void fillData(TagData data, ArrayList<String> values)
	{
		data.setFileName(valueOrNull(values.get(FILE_NAME_IPT_ID)));
		data.setNewFileName(valueOrNull(values.get(NEW_FILE_NAME_IPT_ID)));
		data.setTitle(valueOrNull(values.get(TITLE_IPT_ID)));
		data.setInterpret(valueOrNull(values.get(INTERPRET_IPT_ID)));
		data.setAlbum(valueOrNull(values.get(ALBUM_IPT_ID)));
		data.setYear(intOrZero(values.get(YEAR_IPT_ID)));
		data.setTitleNumber(intOrZero(values.get(TITLE_NUMBER_IPT_ID)));
		data.setGenreDescription(valueOrNull(values.get(GENRE_DESCRIPTION_IPT_ID)));
	}

	private int intOrZero(String value)
	{
		try
		{
			return Integer.valueOf(value);
		}
		catch ( NumberFormatException e)
		{
			return 0;
		}
	}

	private String valueOrNull(String value)
	{
		return (value==null||value.isEmpty()) ? null : value;
	}

	/**
	 * Check if form values have changed to their initial state.<br>
	 * This is done by comparing the mp3 data of the file with the act_data object.
	 * 
	 * @return	boolean
	 */
	private boolean hasChanged()
	{
		TagData data = getOriginalData();
		
		System.out.println(" - act_data: "+act_data.toFullString());
		System.out.println(" - data: "+data.toFullString());
		
		return !act_data.equals(data);
	}

	private TagData getOriginalData()
	{
		TagData data = new TagData();
		try
		{
			data.fill(new File(act_data.getFileName()));
		}
		catch ( UnsupportedTagException e )
		{
			e.printStackTrace();
		}
		catch ( InvalidDataException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Activate the form.
	 */
	@Override
	public void activate()
	{
		System.out.println("EntryView.activate()");
		System.out.println(" - form: "+form);
		if ( form == null ) return;
		
		form.activate();
		labeled_inputs.get(FILE_NAME_IPT_ID).deactivate();
		this.addEventHandler(KeyEvent.KEY_RELEASED, on_key);
	}

	/**
	 * Deactivate the form.
	 */
	@Override
	public void deactivate()
	{
		if ( form == null ) return;
		
		form.deactivate();
		this.removeEventHandler(KeyEvent.KEY_RELEASED, on_key);
	}
	
	/* (non-Javadoc)
	 * @see de.yehoudie.tagman.content.AbstractView#quit()
	 */
	@Override
	protected void quit()
	{
//		this.root.quitDetailView(this);
	}

	public TagData getActData()
	{
		return act_data;
	}
}
