package de.yehoudie.control.input;

import java.io.File;
import javafx.stage.DirectoryChooser;

/**
 * An input field which opens a directory browser.
 *  
 * @author yehoudie
 */
public class DirectoryInput extends FileInput
{
	protected DirectoryChooser directory_chooser;
	private File file;
	public File getFile() { return file; };

	/**
	 * Directory input text field.<br>
	 * Opens a directory browser on focus.
	 */
	public DirectoryInput()
	{
		super();
		
	}

	/* (non-Javadoc)
	 * @see de.yehoudie.control.input.FileInput#init()
	 */
	@Override
	protected void init()
	{
		initClickHandler();
		initDirectoryChooser();
	}
	
	/**
	 * init a file chooser
	 * 
	 * @param	title String the title
	 */
	private void initDirectoryChooser()
	{
		directory_chooser = new DirectoryChooser();
		directory_chooser.setInitialDirectory(new File(System.getProperty("user.home")));
	}
	
	/**
	 * Open file browser on focus
	 */
	@Override
	public void requestFocus()
	{
		super.requestFocus();
	}

	/**
	 * @return
	 */
	@Override
	protected File selectFile()
	{
		return directory_chooser.showDialog(this.getScene().getWindow());
	}

	/**
	 * Set the initial directory.
	 * 
	 * @param	path File
	 */
	@Override
	public void setInitialDirectory(File path)
	{
		directory_chooser.setInitialDirectory(path);
	}
	
	/*@Override
	protected void handleSelection(File file)
	{
		setText(file.getAbsolutePath());
		positionCaret(getText().length());
	}*/

	public void setTitle(String value)
	{
		directory_chooser.setTitle(value);
	}
}
