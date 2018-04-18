package de.yehoudie.tagman.content.controller;

import java.util.ArrayList;

import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.content.DataView;
import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.tagman.utils.PreferencesHandler;
import javafx.scene.control.TextInputControl;

/**
 * @author yehoudie
 *
 */
public class DataController
{
	private Root root;
	private DataView data_view;
	private String data_format;
	private ArrayList<String> values;
	private TagData tag_data;

	public DataController(Root root)
	{
		this.root = root;
		this.data_view = root.getDataView();
	}
	
	public void submitChange(ArrayList<String> values, TextInputControl source_input)
	{
		System.out.printf("DataController.submitDataChange(%s, %s)\n", values.toString(), source_input.toString());
		
		if ( isFormatInput(source_input) )
		{
			setDataFormat(source_input.getText());
		}
		else
		{
			this.values = values;
			fillEntryData();
		}
	}

	private boolean isFormatInput(TextInputControl source_input)
	{
		return data_view.isFormatInput(source_input);
	}

	private void setDataFormat(String text)
	{
		System.out.println("DataController.setDataFormat("+text+")");
		this.data_format = text;
	}

	private void fillEntryData()
	{
		if ( isInputFilled(DataView.FORM_ALBUM_ID) )
		{
			if ( !isFilled(tag_data.getAlbum()) )
			{
				tag_data.setAlbum(values.get(DataView.FORM_ALBUM_ID));
			}
		}
		if ( isInputFilled(DataView.FORM_GENRE_DESCRIPTION_ID) )
		{
			if ( !isFilled(tag_data.getGenreDescription()) )
			{
				tag_data.setGenreDescription(values.get(DataView.FORM_GENRE_DESCRIPTION_ID));
			}
		}
		if ( isInputFilled(DataView.FORM_INTERPRET_ID) )
		{
			if ( !isFilled(tag_data.getInterpret()) )
			{
				tag_data.setInterpret(values.get(DataView.FORM_INTERPRET_ID));
			}
		}
		if ( isInputFilled(DataView.FORM_YEAR_ID) )
		{
			if ( !isFilled(String.valueOf(tag_data.getYear())) || tag_data.getYear() == 0 )
			{
				tag_data.setYear(Integer.valueOf(values.get(DataView.FORM_YEAR_ID)));
			}
		}
	}
	
	private boolean isInputFilled(int id)
	{
		return !values.get(id).isEmpty();
	}
	
	private boolean isFilled(String data)
	{
		return data != null && !data.isEmpty();
	}

	public void syncData(TagData tag_data)
	{
		System.out.println("DataController.syncData()");
		
		boolean auto_entry_to_data = PreferencesHandler.getInstance().getBooleanProperty(PreferencesHandler.ATUO_ENTRY_TO_DATA);
		boolean auto_data_to_entry = PreferencesHandler.getInstance().getBooleanProperty(PreferencesHandler.ATUO_DATA_TO_ENTRY);
		
		this.tag_data = tag_data;
		this.values = data_view.getValues();
//		System.out.println(" - tag_data: "+tag_data);
//		System.out.println(" - values: "+values);
		
		if ( values.size() == 0 )
		{
			if ( auto_entry_to_data ) fillDataData();
			return;
		}

		if ( !values.get(DataView.FORM_FORMAT_ID).isEmpty() )
		{
			setDataFormat(values.get(DataView.FORM_FORMAT_ID));
//			fileDataValues();
		}
		
		values = data_view.getValues();
		if ( auto_data_to_entry ) fillEntryData();
		if ( auto_entry_to_data ) fillDataData();
	}

	private void fillDataData()
	{
		System.out.println("DataController.fillValues()");
		TagData data_data = new TagData();
		if ( !isInputFilled(DataView.FORM_ALBUM_ID) )
		{
			if ( isFilled(tag_data.getAlbum()) )
			{
				data_data.setAlbum(tag_data.getAlbum());
			}
		}
		if ( !isInputFilled(DataView.FORM_GENRE_DESCRIPTION_ID) )
		{
			if ( isFilled(tag_data.getGenreDescription()) )
			{
				data_data.setGenreDescription(tag_data.getGenreDescription());
			}
		}
		if ( !isInputFilled(DataView.FORM_INTERPRET_ID) )
		{
			if ( isFilled(tag_data.getInterpret()) )
			{
				data_data.setInterpret(tag_data.getInterpret());
			}
		}
		if ( !isInputFilled(DataView.FORM_YEAR_ID) )
		{
			if ( isFilled(String.valueOf(tag_data.getYear())) && tag_data.getYear() != 0 )
			{
				data_data.setYear(tag_data.getYear());
			}
		}
		
		data_view.sync(data_data);
	}
}
