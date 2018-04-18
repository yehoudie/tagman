package de.yehoudie.utils.system;

import java.io.File;
import java.util.ArrayList;

import de.yehoudie.utils.event.EventTarget;
import javafx.scene.Scene;

/**
 * handles 
 * - clicks and drags on app icon of associated file types.
 * - app menu quit (osx)
 * 
 * @author henning
 *
 */
public abstract class SystemActionHandler extends EventTarget
{
	// would be easy to listen for changes without additionally event.
	// con: prevents distinction between running and starting files.
	// pro: this distinciton is not really neccessary anyway
//	private ObservableList<File> opened_filess;
	
	protected ArrayList<File> opened_files;
	protected Scene scene;

	/**
	 * constructor to handle associtated file type actions like click and drag.
	 */
	public SystemActionHandler()
	{
		this.opened_files = new ArrayList<File>();
//		this.opened_filess = FXCollections.observableArrayList();
	}

	/**
	 * activate system event handler
	 */
	public void activate()
	{
		setOnStartHandler();
		setWhileRunningHandler();
	}
	
	/**
	 * handle file events, which start the app.
	 */
	protected abstract void setOnStartHandler();

	/**
	 * handle file events, while app is running.
	 * handle doc quit requests.
	 */
	protected abstract void setWhileRunningHandler();

	/**
	 * get the opened file list
	 * 
	 * @return ArrayList<File>
	 */
	public ArrayList<File> getOpenedFiles()
	{
		return opened_files;
	}

	/**
	 * get the the last file of the opened file list
	 * 
	 * @return File
	 */
	public File getLastOpenedFile()
	{
		return (opened_files.size() == 0) ? null : opened_files.get(opened_files.size() - 1);
	}

	/**
	 * set a last opened file.
	 * used in windows.
	 * 
	 * @param	file File
	 */
	public void setLastOpenedFile(File file)
	{
		opened_files.add(file);
	}
	
	/**
	 * perform the menu or doc quit request
	 */
	public abstract void performQuit();

	/**
	 * cancel the menu or doc quit request
	 */
	public abstract void cancelQuit();

	/**
	 * @return Scene
	 */
	public Scene getScene()
	{
		return scene;
	}

	/**
	 * @param	scene Scene the Scene to set
	 */
	public void setScene(Scene scene)
	{
		this.scene = scene;
	}

	public void clearOpenedFiles()
	{
		this.opened_files.clear();
	}

	public void dispose()
	{
	}
}
