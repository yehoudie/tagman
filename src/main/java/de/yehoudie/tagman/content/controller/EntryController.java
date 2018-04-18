/**
 * 
 */
package de.yehoudie.tagman.content.controller;

import java.io.File;
import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.yehoudie.mp3.Mp3File;
import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.content.EntryView;
import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.tagman.objects.FileData;
import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.utils.YAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author yehoudie
 *
 */
public class EntryController
{
	private Root root;
	private EntryView entry_view;

	public EntryController(Root root)
	{
		this.root = root;
		this.entry_view = root.getEntryView();
	}
	
	public TagData fill(FileData file_data)
	{
		TagData entry = new TagData();
		try
		{
			entry.fill(file_data.getFile());
		}
		catch ( UnsupportedTagException e )
		{
//			e.printStackTrace();
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.UNSUPPORTED_TAG));
			alert.showAndWait();
		}
		catch ( InvalidDataException e )
		{
//			e.printStackTrace();
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.INVALID_DATA));
			alert.showAndWait();
		}
		catch ( IOException e )
		{
//			e.printStackTrace();
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.FILE_NOT_EXISTS));
			alert.showAndWait();
		}		
		catch ( NullPointerException e )
		{
//			e.printStackTrace();
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.UNSUPPORTED_TAG));
			alert.showAndWait();
		}
		return entry;
	}

	public void setData()
	{
		System.out.println("EntryController.setData()");
		root.fillData(entry_view.getActData());
	}

	public void submitChange(TagData data)
	{
		File file = new File(data.getFileName());
		try
		{
			changeMp3Data(data, file);
		}
		catch ( IllegalArgumentException e )
		{
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, e.getMessage());
			alert.showAndWait();
//			e.printStackTrace();
		}
		catch ( UnsupportedTagException e )
		{
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, e.getMessage());
//			Alert alert = YAlert.getInstance(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.UNSUPPORTED_TAG));
			alert.showAndWait();
//			e.printStackTrace();
		}
		catch ( InvalidDataException e )
		{
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, e.getMessage());
//			Alert alert = YAlert.getInstance(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.INVALID_DATA));
			alert.showAndWait();
//			e.printStackTrace();
		}
		catch ( IOException e )
		{
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, e.getMessage());
//			Alert alert = YAlert.getInstance(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.UNSUPPORTED_TAG));
			alert.showAndWait();
//			e.printStackTrace();
		}
		catch ( NotSupportedException e )
		{
			Alert alert = YAlert.getInstance(AlertType.INFORMATION, e.getMessage());
//			Alert alert = new Alert(AlertType.INFORMATION, TextManager.getInstance().get(TextManager.UNSUPPORTED_TAG));
			alert.showAndWait();
//			e.printStackTrace();
		}
	}

	private void changeMp3Data(TagData data, File file) throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException
	{
		Mp3File mp3 = new Mp3File(file);
		mp3.fill(data);
		mp3.save();
	}
}
