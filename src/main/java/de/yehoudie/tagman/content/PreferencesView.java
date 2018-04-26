/**
 * 
 */
package de.yehoudie.tagman.content;

import java.util.ArrayList;
import java.util.Properties;

import de.yehoudie.control.input.ChoiceInput;
import de.yehoudie.control.input.LabeledInput;
import de.yehoudie.control.input.NumberInput;
import de.yehoudie.crypto.Sha256;
import de.yehoudie.form.FormHandler;
import de.yehoudie.tagman.Main;
import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.tagman.utils.PreferencesHandler;
import de.yehoudie.types.Language;
import de.yehoudie.utils.StringUtil;
import javafx.scene.input.KeyEvent;

/**
 * @author yehoudie
 *
 */
public class PreferencesView extends AbstractView
{
	private static final int LANGUAGE_IPT_ID = 0;
	private static final int ATUO_DATA_TO_ENTRY_IPT_ID = 1;
	private static final int AUTO_ENTRY_TO_DATA_IPT_ID = 2;
	private static final int AUTO_CHANGE_FILE_NAME_IPT_ID = 3;
	
	// track data's initial state to know, if they have changed onSubmit
	private String hash;
	
	private PreferencesHandler prefs_handler;
	private Properties prefs;
	private int[] ids;
	private String[] boolean_options;
	private String[] langunage_options;

	/**
	 * Show a form with preferences values.
	 * 
	 * @param	root Root 
	 */
	public PreferencesView(Root root)
	{
		super(root);
		
		this.getStyleClass().add("content");
		this.getStyleClass().add("preferences_view");
		
		init();
	}
	
	@Override
	protected void init()
	{
		super.init();
		
		TextManager tm = TextManager.getInstance();
		
		LabeledInput.Type[] types = { 	LabeledInput.Type.CHOICE_IPT, 
										LabeledInput.Type.CHOICE_IPT,
										LabeledInput.Type.CHOICE_IPT,
										LabeledInput.Type.CHOICE_IPT
									};
		String[] labels = { tm.get(TextManager.PREFS_LANGUAGE),
							tm.get(TextManager.PREFS_AUTOMATIC_DATA_TO_ENTRY_FILL),
							tm.get(TextManager.PREFS_AUTOMATIC_ENTRY_TO_DATA_FILL),
							tm.get(TextManager.PREFS_AUTOMATIC_CHANGE_FILE_NAME)
						  };
		
		String[] css_classes = { 	"language_ipt",
									"data_to_entry_ipt",
									"entry_to_data_ipt",
									"change_file_name_ipt"
									};
		
		ids = new int[] { LANGUAGE_IPT_ID, ATUO_DATA_TO_ENTRY_IPT_ID, AUTO_ENTRY_TO_DATA_IPT_ID, AUTO_CHANGE_FILE_NAME_IPT_ID };
		
		loadPreferences();
		fill(types, labels, css_classes, ids);
		fillOptions();
	}

	/**
	 * Load the preferences from file.
	 */
	protected void loadPreferences()
	{
		// System.out.println("PreferencesView.loadPreferences()");
		prefs_handler = PreferencesHandler.getInstance();
		prefs = prefs_handler.load();
		// prefs_handler.print();
	}

	/**
	 * Fill form with an EntryData. If data==null, creat new data object.<br>
	 * Track act state of new data to know, if it has changed onSubmit.
	 */
	public void fill()
	{
		System.out.println("PreferencesView.fill()");
		
		String lang = prefs.getProperty(PreferencesHandler.LANGUAGE);
		String dte = StringUtil.booleanToLanguage(prefs.getProperty(PreferencesHandler.ATUO_DATA_TO_ENTRY), boolean_options);
		String etd = StringUtil.booleanToLanguage(prefs.getProperty(PreferencesHandler.ATUO_ENTRY_TO_DATA), boolean_options);
		String cfn = StringUtil.booleanToLanguage(prefs.getProperty(PreferencesHandler.ATUO_CHANGE_FILE_NAME), boolean_options);
	
		labeled_inputs.get(LANGUAGE_IPT_ID).setText(lang);
		labeled_inputs.get(ATUO_DATA_TO_ENTRY_IPT_ID).setText(dte);
		labeled_inputs.get(AUTO_ENTRY_TO_DATA_IPT_ID).setText(etd);
		labeled_inputs.get(AUTO_CHANGE_FILE_NAME_IPT_ID).setText(cfn);
	
		form.fillValues();
	
		if ( hash == null ) hash = getHash();
	}

	/**
	 * Fill the choice input options.
	 */
	private void fillOptions()
	{
		TextManager tm = TextManager.getInstance();
		
		boolean_options = tm.get(TextManager.BOOLEAN_OPTIONS).split(",");
		
		Language[] languages = Language.getValues();
		langunage_options = new String[languages.length];
		for ( int i = 0; i < languages.length; i++ ) langunage_options[i] = languages[i].getFullString();
	
		for ( int i = 0; i < inputs.size(); i++ )
		{
			ChoiceInput ci = (ChoiceInput)inputs.get(i);
			if ( i == LANGUAGE_IPT_ID )
			{
				ci.addOptions(langunage_options);
			}
			else
			{
				ci.addOptions(boolean_options);
			}
		}
	}

	/**
	 * Recreate hash string of all form values for updated data and inserted data.
	 */
	public void update()
	{
		System.out.println("PreferencesView.update()");
		
		hash = getHash();
	}

	/**
	 * Callback function of the form submit method.
	 * Validate form.
	 * Add changed entries to the changed list in root.
	 * Remove unchanged entries from the changed list in root.
	 * 
	 * @param	form FormHandler the submitting form handler
	 */
	@Override
	protected void onSubmit(FormHandler form)
	{
//		System.out.println("PreferencesView.onSubmit()");
//		System.out.println(" - "+form.getValues());
		
		ArrayList<String> values = form.getValues();

		if ( hasErrors(values) ) return;
		
		if ( hasChanged(values) )
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
		System.out.println(" - values have change, save");
		fillData(values);
		prefs_handler.save();
	}

	private void submitNoChange(ArrayList<String> values)
	{
		System.out.println(" - has not changed");
		fillData(values); // ??
	}

	/**
	 * Check if form has errors.
	 * 
	 * @param	values ArrayList<String> the form values
	 * @return	boolean
	 */
	private boolean hasErrors(ArrayList<String> values)
	{
		boolean has_errors = false;
		boolean act_error = false;
		
		// check for empty values
		/*for ( int id : ids )
		{
			act_error = inputs.get(id).getText().isEmpty();
			markErrorField(id, act_error);
			if ( act_error ) has_errors = true;
		}*/
		
		// check for types
		// - is languageized boolean
		int[] boolean_type_ids = { ATUO_DATA_TO_ENTRY_IPT_ID, AUTO_ENTRY_TO_DATA_IPT_ID, AUTO_CHANGE_FILE_NAME_IPT_ID };
		for ( int id : boolean_type_ids )
		{
			act_error = !StringUtil.hasValue( inputs.get(id).getText(), boolean_options );
			markErrorField(id, act_error);
			if ( act_error ) has_errors = true;
		}
		
		// test language
		act_error = !StringUtil.hasValue( inputs.get(LANGUAGE_IPT_ID).getText(), langunage_options );
		markErrorField(LANGUAGE_IPT_ID, act_error);
		if ( act_error ) has_errors = true;
		
		return has_errors;
	}

	/**
	 * Fill a EntryData object with actual form values
	 * 
	 * @param	data EntryData
	 * @param	values ArrayList<String>
	 */
	private void fillData(ArrayList<String> values)
	{
		String dte = StringUtil.languageBooleanToBooleanString(values.get(ATUO_DATA_TO_ENTRY_IPT_ID), boolean_options);
		String etd = StringUtil.languageBooleanToBooleanString(values.get(AUTO_ENTRY_TO_DATA_IPT_ID), boolean_options);
		String cfn = StringUtil.languageBooleanToBooleanString(values.get(AUTO_CHANGE_FILE_NAME_IPT_ID), boolean_options);
		
		prefs.setProperty(PreferencesHandler.LANGUAGE, values.get(LANGUAGE_IPT_ID));
		prefs.setProperty(PreferencesHandler.ATUO_DATA_TO_ENTRY, dte);
		prefs.setProperty(PreferencesHandler.ATUO_ENTRY_TO_DATA, etd);
		prefs.setProperty(PreferencesHandler.ATUO_CHANGE_FILE_NAME, cfn);
	}

	/**
	 * Check if form values have changed to their initial state.
	 * This is done by comparing the hash of the actual state with the saved state in the map.
	 * 
	 * @param	values ArrayList<String>
	 * @return	boolean
	 */
	private boolean hasChanged(ArrayList<String> values)
	{
		String values_string = valuesToString(values);
		String hash = Sha256.hash(values_string);
		
		return !this.hash.equals(hash);
	}
	
	@Override
	public void activate()
	{
		if ( form == null ) return;
		
		form.activate();
		this.addEventHandler(KeyEvent.KEY_RELEASED, on_key);
	}

	@Override
	public void deactivate()
	{
		if ( form == null ) return;
		
		form.activate();
		this.removeEventHandler(KeyEvent.KEY_RELEASED, on_key);
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
