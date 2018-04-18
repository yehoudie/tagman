package de.yehoudie.utils.system;

//import com.apple.eawt.AppEvent.OpenFilesEvent;
//import com.apple.eawt.AppEvent.QuitEvent;
//import com.apple.eawt.OpenFilesHandler;
//import com.apple.eawt.QuitHandler;
//import com.apple.eawt.QuitResponse;

/**
 * handles 
 * - clicks and drags on app icon of associated file types.
 * - app menu quit (osx)
 * 
 * @author henning
 *
 */
public class MacSystemActionHandler extends SystemActionHandler
{
//	protected QuitResponse quit_response;

	/**
	 * constructor to handle associtated file type actions like click and drag.
	 */
	public MacSystemActionHandler()
	{
		super();
	}

	/*public void activate()
	{
		super.activate();
	}*/
	
	/**
	 * handle file events, which start the app.
	 * handle menu->close or command+q event.
	 * fx thread!
	 */
	@Override
	protected void setOnStartHandler()
	{
		// OSX
//		com.sun.glass.ui.Application glassApp = com.sun.glass.ui.Application.GetApplication();
//		glassApp.setEventHandler(new com.sun.glass.ui.Application.EventHandler()
//		{
//			/**
//			 * hander for click file to open not yet running app.
//			 */
//			@Override
//			public void handleOpenFilesAction(com.sun.glass.ui.Application app, long time, String[] file_names)
//			{
//				super.handleOpenFilesAction(app, time, file_names);
//
//				opened_files.clear();
//
//				for ( String file : file_names )
//				{
//					opened_files.add(new File(file_names[file_names.length - 1]));
//
//					System.out.println(" - open file: " + file);
//				}
//
//				fireEvent(new SystemActionEvent(SystemActionEvent.FILE_OPENED_WHILE_OFF));
//			}
//
//			/**
//			 * handler for menu quit, cmd+q.
//			 */
//			@Override
//			public void handleQuitAction(com.sun.glass.ui.Application app, long time)
//			{
//				super.handleQuitAction(app, time);
//				fireEvent(new SystemActionEvent(SystemActionEvent.MENU_QUIT_REQUEST));
//			}
//		});
		// ---
	}

	/**
	 * handle file events, while app is running.
	 * handle doc quit requests.
	 */
	@Override
	protected void setWhileRunningHandler()
	{
		System.out.println("listenOpenFiles()");

		addRunningFileOpenHandler();
		addDocQuitHandler();
		
		//a.setAboutHandler(arg0);
		//a.setOpenURIHandler(arg0);
		//a.setQuitStrategy(QuitStrategy.SYSTEM_EXIT_0);
	}

	/**
	 * handler for file click while app is running.
	 * not fx thread!
	 */
	private void addRunningFileOpenHandler()
	{
//		com.apple.eawt.Application a = com.apple.eawt.Application.getApplication();
//		a.setOpenFileHandler(new OpenFilesHandler()
//		{
//
//			@Override
//			public void openFiles(OpenFilesEvent e)
//			{
//				opened_files.clear();
//				for ( File file : e.getFiles() )
//				{
//					opened_files.add(file);
//
//					System.out.println(" - open file: " + file);
//				}
//				
//				Platform.runLater(() -> fireEvent(new SystemActionEvent(SystemActionEvent.FILE_OPENED_WHILE_RUNNING)));
////				Platform.runLater(() -> opened_filess.addAll(e.getFiles()));
//			}
//		});
	}
	
	/**
	 * handler for doc icon quit. 
	 * not fx thread!
	 */
	private void addDocQuitHandler()
	{
//		com.apple.eawt.Application a = com.apple.eawt.Application.getApplication();
//		
////		a.setQuitStrategy(QuitStrategy.CLOSE_ALL_WINDOWS);
//		
//		// doc icon quit.
//		a.setQuitHandler(new QuitHandler()
//		{
//			@Override
//			public void handleQuitRequestWith(QuitEvent e, QuitResponse r)
//			{
//				quit_response = r;
//				Platform.runLater(
//					() -> 
//					{
//						fireEvent(new SystemActionEvent(SystemActionEvent.DOC_QUIT_REQUEST));
//					}
//				);
//			}
//		});
	}

	/**
	 * perform the menu or doc quit request
	 */
	@Override
	public void performQuit()
	{
//		quit_response.performQuit();		
	}

	/**
	 * cancel the menu or doc quit request
	 */
	@Override
	public void cancelQuit()
	{
//		quit_response.cancelQuit();		
	}
}
