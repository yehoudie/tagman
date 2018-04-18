package de.yehoudie.tagman;

import java.io.File;

import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.utils.files.FileUtil;
import javafx.stage.Stage;

/**
 * @author yehoudie
 *
 * Handler for app title changes.
 */
public class AppTitle
{
	private String default_file_part;
	private String file_part;
	public static String DEFAULT_VALUE;
	private String value;
	public String getValue() { return value; };
	
	private Stage stage;
	public void setStage(Stage stage) { this.stage = stage; };

	public AppTitle()
	{
	}

	public void init(String version)
	{
		TextManager text_manager = TextManager.getInstance();

		DEFAULT_VALUE = text_manager.get("ApplicationTitle").replace("{0}", version);
		default_file_part = text_manager.get(TextManager.UNNAMED);
		file_part = default_file_part;
		value = DEFAULT_VALUE.concat(" - ").concat(file_part);
	}
	
	/**
	 * Set the file name part of the title: // * esg %version - fileName
	 * 
	 * @param file File the file to get the name of
	 */
	public void setFilePart(File file)
	{
		String file_name = FileUtil.getName(file);

		value = value.replace(file_part, file_name);
//		value = value.concat(" - ").concat(file_name);
		file_part = file_name;

		stage.setTitle(value);
	}

//	/**
//	 * Reset the file name part of the title to default.
//	 */
//	public void resetFilePart()
//	{
////		value = value.replace(file_part, default_file_part);
//		value = value.concat(" - ").concat(default_file_part);
//		file_part = default_file_part;
//
//		stage.setTitle(value);
//	}

	/**
	 * Mark the title to reflect a changed state.
	 * 
	 * @param has_changed boolean
	 */
	public void markChanged(boolean has_changed)
	{
		if ( value == null || value.isEmpty() ) return;

		if ( has_changed )
		{
			markChanged();
		}
		else
		{
			unmarkChanged();
		}
	}

	private void markChanged()
	{
		if ( value.charAt(value.length() - 1) != '*' )
		{
			value += "*";
			stage.setTitle(value);
		}
	}

	private void unmarkChanged()
	{
		if ( value.charAt(value.length() - 1) == '*' )
		{
			value = value.substring(0, value.length() - 1);
			stage.setTitle(value);
		}
	}
}
