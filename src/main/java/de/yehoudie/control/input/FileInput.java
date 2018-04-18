package de.yehoudie.control.input;

import java.io.File;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * An input field which opens a file browser.
 *  
 * @author yehoudie
 */
public class FileInput extends TextFieldInput
{
	protected FileChooser file_chooser;
	private File file;
	public File getFile() { return file; };
	
	/**
	 * File input text field.<br>
	 * Opens a file browser on focus.
	 */
	public FileInput()
	{
		super();
		this.getStyleClass().add("file-input");
		
		init();
	}

	/**
	 * Initialize stuff.
	 */
	protected void init()
	{
		initClickHandler();
		initFileChooser();
	}

	protected void initClickHandler()
	{
		this.setOnMouseClicked(e->requestFocus());
	}

	/**
	 * Init a file chooser.
	 * 
	 * @param	title String the title
	 */
	private void initFileChooser()
	{
		file_chooser = new FileChooser();
		file_chooser.setInitialDirectory(new File(System.getProperty("user.home")));
	}
	
	/**
	 * Open file browser on focus.
	 */
	@Override
	public void requestFocus()
	{
		fireEvent(new InputEvent(InputEvent.FOCUS_REQUEST));
		
		file = selectFile();
		if ( file != null )
		{
			handleSelection(file);
			fireEvent(new InputEvent(file, InputEvent.FILE_SELECTED));
		}
		super.requestFocus();
	}

	/**
	 * Show the open Dialog.
	 * 
	 * @return	File
	 */
	protected File selectFile()
	{
		return file_chooser.showOpenDialog(this.getScene().getWindow());
	}

	/**
	 * Set the initial directory.
	 * 
	 * @param	path File
	 */
	public void setInitialDirectory(File path)
	{
		file_chooser.setInitialDirectory(path);
	}
	
	protected void handleSelection(File file)
	{
		setText(file.getAbsolutePath());
		positionCaret(getText().length());
	}

	/**
	 * Does nothing for FileInput.
	 */
	@Override
	public void replaceText(int start, int end, String text) {}

	/**
	 * Does nothing for FileInput.
	 */
	@Override
	public void replaceSelection(String text) {}
	
	/**
	 * Does nothing for FileInput.
	 */
	@Override
	public void cut() {}

	/**
	 * Does nothing for FileInput.
	 */
	@Override
	public void copy() {}
	
	/**
	 * Does nothing for FileInput.
	 */
	@Override
	public void paste() {}

	public void setTitle(String value)
	{
		file_chooser.setTitle(value);
	}

	public void setExtensions(ArrayList<ExtensionFilter> extensions)
	{
		file_chooser.getExtensionFilters().addAll(extensions);		
	}
	
	@Override
	public void dispose()
	{
		super.dispose();

		this.setOnMouseClicked(null);
	}
}
