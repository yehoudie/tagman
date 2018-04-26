package de.yehoudie.tagman.content.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.content.DataView;
import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.tagman.types.DataFormatParameter;
import de.yehoudie.tagman.utils.FileNameBuilder;
import de.yehoudie.tagman.utils.PreferencesHandler;
import de.yehoudie.utils.files.FileUtil;
import javafx.scene.control.TextInputControl;

/**
 * @author yehoudie
 *
 */
public class DataController
{
	private Root root;
	private DataView data_view;
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

		if ( isInputFilled(DataView.FORMAT_GET_IPT_ID) )
		{
			HashMap<DataFormatParameter, String> tag_values = FileNameBuilder.getFileNameData(values.get(DataView.FORMAT_GET_IPT_ID), tag_data.getFileName());
			System.out.println(" - tag_Values: "+tag_values);
			TagData sync_data = new TagData();
			
			String value = tag_values.get(DataFormatParameter.ALBUM);
			if ( value != null && !isFilled(tag_data.getAlbum()) )
			{
				tag_data.setAlbum(value);
				sync_data.setAlbum(value);
			}
			
			value = tag_values.get(DataFormatParameter.INTERPRET);
			if ( value != null && !isFilled(tag_data.getInterpret()) )
			{
				tag_data.setInterpret(value);
				sync_data.setInterpret(value);
			}
			
			value = tag_values.get(DataFormatParameter.TITLE);
			if ( value != null && !isFilled(tag_data.getTitle()) )
			{
				tag_data.setTitle(value);
				sync_data.setTitle(value);
			}
			
			value = tag_values.get(DataFormatParameter.TRACK_NUMBER);
			if ( value != null && ( !isFilled(String.valueOf(tag_data.getTitleNumber())) || tag_data.getTitleNumber() == 0 ) )
			{
				tag_data.setTitleNumber(Integer.valueOf(value));
				sync_data.setTitleNumber(Integer.valueOf(value));
			}
			
			value = tag_values.get(DataFormatParameter.YEAR);
			if ( value != null && ( !isFilled(String.valueOf(tag_data.getYear())) || tag_data.getYear() == 0 ) )
			{
				tag_data.setYear(Integer.valueOf(value));
//				sync_data.setTitleNumber(Integer.valueOf(value));
			}
			
			data_view.sync(sync_data);
		}
	}

	private void fillEntryData()
	{
		System.out.println("DataController.fillEntryData()");
		System.out.println(" - tag_data: "+tag_data);
		if ( tag_data == null ) return;
		
		if ( isInputFilled(DataView.ALBUM_IPT_ID) )
		{
			if ( !isFilled(tag_data.getAlbum()) )
			{
				tag_data.setAlbum(values.get(DataView.ALBUM_IPT_ID));
			}
		}
		if ( isInputFilled(DataView.GENRE_DESCRIPTION_IPT_ID) )
		{
			if ( !isFilled(tag_data.getGenreDescription()) )
			{
				tag_data.setGenreDescription(values.get(DataView.GENRE_DESCRIPTION_IPT_ID));
			}
		}
		if ( isInputFilled(DataView.INTERPRET_IPT_ID) )
		{
			if ( !isFilled(tag_data.getInterpret()) )
			{
				tag_data.setInterpret(values.get(DataView.INTERPRET_IPT_ID));
			}
		}
		if ( isInputFilled(DataView.YEAR_IPT_ID) )
		{
			if ( !isFilled(String.valueOf(tag_data.getYear())) || tag_data.getYear() == 0 )
			{
				tag_data.setYear(Integer.valueOf(values.get(DataView.YEAR_IPT_ID)));
			}
		}

		boolean auto_change_file_name = PreferencesHandler.getInstance().getBooleanProperty(PreferencesHandler.ATUO_CHANGE_FILE_NAME);
		if ( auto_change_file_name && isInputFilled(DataView.FORMAT_SET_IPT_ID) )
		{
			String new_file_name = FileNameBuilder.buildNewFileName(values.get(DataView.FORMAT_SET_IPT_ID), tag_data);
			
			File old_file = new File(tag_data.getFileName());
			String old_file_name = old_file.getName();

			if ( !old_file_name.equals(new_file_name+"."+FileUtil.getType(old_file)) )
			{
				tag_data.setNewFileName(new_file_name);
			}
		}
	}
	
	private boolean isInputFilled(int id)
	{
		return values.get(id)!=null&&!values.get(id).isEmpty();
	}
	
	private boolean isFilled(String data)
	{
		return data != null && !data.isEmpty();
	}

	public void syncData(TagData tag_data)
	{
		System.out.println("DataController.syncData()");
		if ( tag_data == null ) return;
		
		data_view.update();
		
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

		if ( !values.get(DataView.FORMAT_GET_IPT_ID).isEmpty() )
		{
			setDataFormat(values.get(DataView.FORMAT_GET_IPT_ID));
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
		if ( !isInputFilled(DataView.ALBUM_IPT_ID) )
		{
			if ( isFilled(tag_data.getAlbum()) )
			{
				data_data.setAlbum(tag_data.getAlbum());
			}
		}
		if ( !isInputFilled(DataView.GENRE_DESCRIPTION_IPT_ID) )
		{
			if ( isFilled(tag_data.getGenreDescription()) )
			{
				data_data.setGenreDescription(tag_data.getGenreDescription());
			}
		}
		if ( !isInputFilled(DataView.INTERPRET_IPT_ID) )
		{
			if ( isFilled(tag_data.getInterpret()) )
			{
				data_data.setInterpret(tag_data.getInterpret());
			}
		}
		if ( !isInputFilled(DataView.YEAR_IPT_ID) )
		{
			if ( isFilled(String.valueOf(tag_data.getYear())) && tag_data.getYear() != 0 )
			{
				data_data.setYear(tag_data.getYear());
			}
		}
		
		data_view.sync(data_data);
	}
	
	public TagData getTagData()
	{
		return tag_data;
	}
}
