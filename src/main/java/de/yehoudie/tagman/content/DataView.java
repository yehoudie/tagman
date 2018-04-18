package de.yehoudie.tagman.content;

import java.util.ArrayList;

import de.yehoudie.control.input.ChoiceInput;
import de.yehoudie.control.input.LabeledInput;
import de.yehoudie.form.FormHandler;
import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.filemanager.GenreManager;
import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.tagman.objects.TagData;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;

public class DataView extends AbstractView
{
	public final static int FORM_FORMAT_ID = 0;
	public final static int FORM_INTERPRET_ID = 1;
	public final static int FORM_ALBUM_ID = 2;
	public final static int FORM_YEAR_ID = 3;
	public final static int FORM_GENRE_DESCRIPTION_ID = 4;
//	private TagData act_data;
	
	/**
	 * Show a form with values of global data.
	 * 
	 * @param	root Root 
	 */
	public DataView(Root root)
	{
		super(root);
		
		this.getStyleClass().add("content");
		this.getStyleClass().add("data_view");
		
		init();
	}
	
	@Override
	protected void init()
	{
		super.init();
		
		TextManager tm = TextManager.getInstance();
		
		LabeledInput format_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_FORMAT), "file_name_ipt");
		setFormatInputInfo(format_ipt, tm);
		LabeledInput interpret_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_INTERPRET), "interpret_ipt");
		LabeledInput album_ipt = new LabeledInput(LabeledInput.Type.TEXT_IPT, tm.get(TextManager.ENTRY_ALBUM), "album_ipt");
		LabeledInput year_ipt = new LabeledInput(LabeledInput.Type.NUMBER_IPT, tm.get(TextManager.ENTRY_YEAR), "year_ipt");
		year_ipt.setLimit(4);
		LabeledInput genre_ipt = new LabeledInput(LabeledInput.Type.CHOICE_IPT, tm.get(TextManager.ENTRY_GENRE), "genre_ipt");
		ChoiceInput genre_ci = (ChoiceInput) genre_ipt.getInput();
		genre_ci.addOptions(GenreManager.getInstance().getValues());
		
		inputs.add(FORM_FORMAT_ID, format_ipt.getInput());
		inputs.add(FORM_INTERPRET_ID, interpret_ipt.getInput());
		inputs.add(FORM_ALBUM_ID, album_ipt.getInput());
		inputs.add(FORM_YEAR_ID, year_ipt.getInput());
		inputs.add(FORM_GENRE_DESCRIPTION_ID, genre_ipt.getInput());
		
		labeled_inputs.add(FORM_FORMAT_ID, format_ipt);
		labeled_inputs.add(FORM_INTERPRET_ID, interpret_ipt);
		labeled_inputs.add(FORM_ALBUM_ID, album_ipt);
		labeled_inputs.add(FORM_YEAR_ID, year_ipt);
		labeled_inputs.add(FORM_GENRE_DESCRIPTION_ID, genre_ipt);
		
		inputs.stream().forEach( input->form.addInput(input) );
		
		this.getChildren().addAll(labeled_inputs);
		
		form.fillValues();
	}

	private void setFormatInputInfo(LabeledInput format_ipt, TextManager tm)
	{
		String[] parts = tm.get(TextManager.ENTRY_FORMAT_INFO).split(";");
		
		StringBuilder tooltip_value = new StringBuilder();
		for ( String line : parts ) tooltip_value.append(line).append("\n"); 
		
		format_ipt.setTooltip(tooltip_value.toString());
		format_ipt.setPrompt(tm.get(TextManager.ENTRY_FORMAT_VALUES));
	}

	public void fill(TagData data)
	{
		System.out.println("DataView.fill("+data.toFullString()+")");
		
		clear();
		
		labeled_inputs.get(FORM_INTERPRET_ID).setText(data.getInterpret());
		labeled_inputs.get(FORM_ALBUM_ID).setText(data.getAlbum());
		if (data.getYear()!=0) labeled_inputs.get(FORM_YEAR_ID).setText(String.valueOf(data.getYear()));
		labeled_inputs.get(FORM_GENRE_DESCRIPTION_ID).setText(data.getGenreDescription());

		form.fillValues();
		
//		act_data = data;
	}
	
	public void sync(TagData data)
	{
		System.out.println("DataView.fill("+data.toFullString()+")");
		
		syncInput(FORM_INTERPRET_ID, data.getInterpret());
		syncInput(FORM_ALBUM_ID, data.getAlbum());
		if (data.getYear()!=0) syncInput(FORM_YEAR_ID, String.valueOf(data.getYear()));
		syncInput(FORM_GENRE_DESCRIPTION_ID, data.getGenreDescription());
		
		form.fillValues();
		
//		act_data = data;
	}

	private void syncInput(int id, String value)
	{
		if ( value == null || value.isEmpty() ) return;
		labeled_inputs.get(id).setText(value);
	}

	public void clear()
	{
		form.clear();
//		act_data = null;
	}
	
	/**
	 * Callback function of the form submit method.<br>
	 * Validate form.<br>
	 * Submit.
	 * 
	 * @param	form FormHandler the submitting form handler
	 */
	@Override
	protected void onSubmit(FormHandler form)
	{
		System.out.println("DataView.onSubmit()");
//		System.out.println(" - "+form.getValues());
		
		ArrayList<String> values = form.getValues();

		if ( hasErrors(values) ) return;
		
		submitChange(values);
	}

	private void submitChange(ArrayList<String> values)
	{
		System.out.println(" - values have change, add to save list");
//			System.out.println(" - act_data: "+act_data.toFullString());
		root.submitDataChange(values, form.getKeyHandlingSource());
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
//		boolean act_error = false;
		
		return has_errors;
	}

//	/**
//	 * Fill a EntryData object with actual form values.<br>
//	 * 
//	 * @param	data EntryData
//	 * @param	values ArrayList<String>
//	 */
//	private void fillData(TagData data, ArrayList<String> values)
//	{
//		data.setFileName(values.get(FORM_FORMAT_ID));
//		data.setInterpret(values.get(FORM_INTERPRET_ID));
//		data.setAlbum(values.get(FORM_ALBUM_ID));
//		data.setYear(Integer.valueOf(values.get(FORM_YEAR_ID)));
//		data.setGenreDescription(values.get(FORM_GENRE_DESCRIPTION_ID));
//	}

	/**
	 * Activate the form.
	 */
	@Override
	public void activate()
	{
		if ( form == null ) return;
		
		form.activate();
		this.addEventHandler(KeyEvent.KEY_RELEASED, on_key);
	}

	/**
	 * Deactivate the form.
	 */
	@Override
	public void deactivate()
	{
		if ( form == null ) return;
		
		form.activate();
		this.removeEventHandler(KeyEvent.KEY_RELEASED, on_key);
	}

	public boolean isFormatInput(TextInputControl source_input)
	{
		return inputs.get(FORM_FORMAT_ID).equals(source_input);
	}
	
	/* (non-Javadoc)
	 * @see de.yehoudie.tagman.content.AbstractView#quit()
	 */
	@Override
	protected void quit()
	{
		this.root.quitDetailView(this);
	}
}
