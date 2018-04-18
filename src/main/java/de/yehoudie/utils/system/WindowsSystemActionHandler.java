package de.yehoudie.utils.system;

import java.io.File;
import java.util.List;

import de.yehoudie.utils.files.FileEvent;
import de.yehoudie.utils.files.FileUtil;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * handles 
 * - clicks and drags on app icon of associated file types.
 * - app menu quit (osx)
 * TODO: implement Windows
 * 
 * @author henning
 *
 */
public class WindowsSystemActionHandler extends SystemActionHandler
{
	private SingleInstanceGuard sig;
	private File lock_file;
	private File message_file;
	private List<String> args;

	/**
	 * constructor to handle associtated file type actions like click and drag.
	 * the windows handler also guards a one instance mode.
	 */
	public WindowsSystemActionHandler()
	{
		super();
	}

	public void activate()
	{
		setWhileRunningHandler();
	}
	
	/**
	 * handle file events, which start the app.
	 * !! NO NEED, PASSED AS ARGUMENT !!
	 */
	@Override
	protected void setOnStartHandler()
	{}

	/**
	 * handle file events, while app is running.
	 * handle doc quit requests.
	 */
	@Override
	protected  void setWhileRunningHandler()
	{
		System.out.println("listenOpenFiles()");

		addRunningFileOpenHandler();
		addDroppedOverHandler();
//		addRunningFileOpenHandler();
//		addDocQuitHandler();
	}

	/**
	 * handler for file click while app is running. not fx thread!
	 */
	private void addRunningFileOpenHandler()
	{
		// activate single instance mode
		sig = SingleInstanceGuard.getInstance(lock_file, message_file);
		sig.setArgs(args);
		sig.addEventHandler(FileEvent.CHANGED, e -> fileChanged(e));
		sig.guard();
	}

	/**
	 * inter app message file has changed:
	 * read out open files and fire event to open one.
	 * 
	 * @param	e FileEvent
	 */
	private void fileChanged(FileEvent e)
	{
//		System.out.println("WindowsSystemActionHandler.FileChanged");
		String[] args = FileUtil.getContent(message_file).split("[\\r\\n\\|]+");

		opened_files.clear();
		
		for ( String arg : args )
		{
//			System.out.println(" . arg: "+arg);
			File file = new File(arg);
			
			if ( file.isFile() && file.exists() )
			{
				opened_files.add(file);
			}
		}
		
		Platform.runLater(
		() -> {
			fireEvent(new SystemActionEvent(SystemActionEvent.FILE_OPENED_WHILE_RUNNING));
		});
		
//		sig.clearMessage();
	}

	private void addDroppedOverHandler()
	{
		if ( scene == null ) return;
		
		scene.setOnDragOver(new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent e)
			{
				System.out.println("dragOver: "+e);
				Dragboard db = e.getDragboard();
				if ( db.hasFiles() )
				{
					e.acceptTransferModes(TransferMode.COPY);
				}
				else
				{
					e.consume();
				}
			}
		});

		// Dropping over surface
		scene.setOnDragDropped(new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent e)
			{
				System.out.println("dropped");
				Dragboard db = e.getDragboard();
				boolean success = false;
				if ( db.hasFiles() )
				{
					success = true;
					String file_path = null;
					for ( File file : db.getFiles() )
					{
						file_path = file.getAbsolutePath();
						System.out.println(" - "+file_path);
						opened_files.add(new File(file_path));
//						Main.LOGGER.info("dropped: "+file_path);
					}
					// nothing special about it, so just fire FILE_OPENED_WHILE_RUNNING
					fireEvent(new SystemActionEvent(SystemActionEvent.FILE_OPENED_WHILE_RUNNING));
//					fireEvent(new SystemActionEvent(SystemActionEvent.FILE_DROPPED_WHILE_RUNNING));
				}
				
				e.setDropCompleted(success);
				e.consume();
			}
		});
	}

	/**
	 * handler for doc icon quit. 
	 * not fx thread!
	 */
	/*private void addDocQuitHandler()
	{
	}*/

	/**
	 * perform the menu or doc quit request
	 */
	public void performQuit()
	{
//		quit_response.performQuit();		
	}

	/**
	 * cancel the menu or doc quit request
	 */
	public void cancelQuit()
	{
//		quit_response.cancelQuit();		
	}

	/**
	 * @param	scene Scene the Scene to set
	 */
	@Override
	public void setScene(Scene scene)
	{
		this.scene = scene;
		addDroppedOverHandler();
	}
	
	@Override
	public void dispose()
	{
		if ( sig != null )
		{
			sig.dispose();
			sig = null;
		}
	}

	public void setArgs(List<String> args)
	{
		this.args = args;
	}

	public void setLockFile(File file)
	{
		this.lock_file = file;
	}
	
	public void setMessageFile(File file)
	{
		this.message_file = file;
	}
}
