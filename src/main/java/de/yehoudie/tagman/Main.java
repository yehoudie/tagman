package de.yehoudie.tagman;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.yehoudie.tagman.dialogs.ConfirmClearDialog;
import de.yehoudie.tagman.filemanager.GenreManager;
import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.tagman.utils.PreferencesHandler;
import de.yehoudie.types.Language;
import de.yehoudie.types.OS;
import de.yehoudie.utils.HostServiceProvider;
import de.yehoudie.utils.files.FileUtil;
import de.yehoudie.utils.system.MacSystemActionHandler;
import de.yehoudie.utils.system.SystemActionEvent;
import de.yehoudie.utils.system.SystemActionHandler;
import de.yehoudie.utils.system.WindowsSystemActionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application
{
	private static final char HAS_CHANGED_MODIFIER = '*';

	public static final String MAIN_STYLE_SHEET = "/css/main.css";
	
	public final static String APP_NAME = "Tag Man";
	public final static String APP_VERSION = "1.0";

	public static final File APP_DATA_DIR_WIN = new File(System.getenv("LOCALAPPDATA")+"/yehoudie-software/"+APP_NAME);
	public static final File APP_DATA_DIR_MAC = new File(System.getProperty("user.home")+"/Library/yehoudie-software/"+APP_NAME);

	public static File PREFERENCES_FILE = new File(".config");
	
	private OS os;
	private File app_data_dir;
	public static File LAST_FILE_PATH_FILE;
	
	// sizes
	public static int STAGE_W = 1200;
	public static int STAGE_H = 650;
	public static int STAGE_MIN_W = 600;
	public static int STAGE_MIN_H = 325;

	// root objects 
	private Scene scene;
	private Stage stage;
	
	// screen objects
	private Root root;
	
	private SystemActionHandler system_action_handler;

	private Language app_language;

	private ConfirmClearDialog confirm_clear_dialog;
	private AppTitle app_title;

	private Map<String, String> params;

	public Main()
	{
		super();

		os = getOS();
		if ( os == OS.NONE )
		{
			throw new Error("Not an supported Operating System");
		}
		
		// create os specific system action handler
		system_action_handler = null;
		if ( os == OS.MAC )
		{
			system_action_handler = new MacSystemActionHandler();
			system_action_handler.activate();
		}
	}
	
	/**
	 * Init method.<br>
	 * First time to appear app parameters.
	 */
	@Override
	public void init() throws Exception
	{
		super.init();
		
		app_data_dir = createAppDataDir(os);
		System.out.println("app data dir: "+app_data_dir);
		
		if ( os == OS.WINDOWS )
		{
			setWindowsSystemActionHandler();
		}
	}

	protected void setWindowsSystemActionHandler()
	{
		system_action_handler = new WindowsSystemActionHandler();
		WindowsSystemActionHandler wsah = (WindowsSystemActionHandler) system_action_handler;
		wsah.setArgs(getParameters().getUnnamed());
		wsah.setLockFile(new File(app_data_dir, ".lock"));
		wsah.setMessageFile(new File(app_data_dir, ".message"));
//		system_action_handler.activate(); // uncomment !!
	}
	
	/**
	 * Start the application.<br>
	 * Do some  initialization.
	 * 
	 * @param	stage Stage the javafx stage
	 */
	@Override
	public void start(Stage stage)
	{
		LAST_FILE_PATH_FILE = createLastFilePathFile();
		System.out.println("last file path: "+LAST_FILE_PATH_FILE);
		
		app_language = getLanguage();
		System.out.println("APP_LANGUAGE: "+app_language);
		
		getAppParameters();
		
		Locale.setDefault( app_language.getLocale() );

		TextManager text_manager = TextManager.getInstance();
		text_manager.fill("/xml/texts.xml", app_language);
		
		GenreManager genre_manager = GenreManager.getInstance();
		genre_manager.fill("/xml/genres.xml");
		
		HostServiceProvider.getInstance().init(this);

		initPreferences();
		
		try
		{
			setUp(stage);
		}
		catch ( Exception e )
		{
//			LOGGER.log(Level.SEVERE, "exception", e);
			e.printStackTrace();
		}
	}

	private void setUp(Stage stage)
	{
		setUpAppTitle(stage);
		setUpRoot();
		setUpScene();
		setUpStage(stage);

		this.stage = stage;

		root.init();
		handleParams();
		root.draw();
		
		system_action_handler.setScene(scene);
		addSystemActionHandler();
	}

	private void setUpRoot()
	{
		root = new Root(this);
	}

	protected void setUpAppTitle(Stage stage)
	{
		app_title = new AppTitle();
		app_title.setStage(stage);
		app_title.init(APP_VERSION);
	}

	protected void setUpScene()
	{
		scene = new Scene(root, STAGE_W, STAGE_H);
		scene.setResizeCallback(this::onResizeStage);
		scene.init(os, app_language);
		scene.activate();
	}

	protected void setUpStage(Stage stage)
	{
//		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setTitle(app_title.getValue()); // title of the main window
		stage.setOnCloseRequest(this::onCloseStage);
		stage.getIcons().add( new Image(getClass().getResourceAsStream("/icon.png")) );
		stage.show();
	}

	private void handleParams()
	{
		System.out.println("params.get(\"directory\"): "+params.get("directory"));
		if ( params.get("directory") != null )
		{
			File directory = new File(params.get("directory"));
			if ( directory.isDirectory() ) root.open(directory);
		}
	}

	private void initPreferences()
	{
		System.out.println("Main.initPreferences()");
		PREFERENCES_FILE = new File(app_data_dir, PREFERENCES_FILE.getName());
		
		PreferencesHandler rh = PreferencesHandler.getInstance();
		rh.init(PREFERENCES_FILE, app_language);
	}

	/**
	 * Get the app language.
	 * 
	 * @return	Language
	 */
	public Language getLanguage()
	{
		String lang = FileUtil.getContent(this.getClass().getResource("/data/.lang"));
		
		return Language.forString(lang);
	}

	private OS getOS()
	{
		String os_prop = System.getProperty("os.name").toLowerCase();

		OS os = OS.NONE;
		if ( os_prop.indexOf("windows") != -1 ) os = OS.WINDOWS;
		else if ( os_prop.indexOf("os x") != -1 ) os = OS.MAC;
		
		return os;
	}

	/**
	 * Set the right app data dir and create it, if neccessary.
	 * 
	 * @param	os OS the operation system
	 * @return	boolean
	 */
	private File createAppDataDir(OS os)
	{
		File app_data_dir;
		if ( os == OS.WINDOWS ) app_data_dir = Main.APP_DATA_DIR_WIN;
		else if ( os == OS.MAC ) app_data_dir = Main.APP_DATA_DIR_MAC;
		else return null;

		if ( !app_data_dir.exists() )
		{
			if ( !app_data_dir.mkdirs() )
			{
				System.err.println("Could not create app data dir!");
				return null;
			}
		}
		
		return app_data_dir;
	}
	
	/**
	 * Create a file, <br>
	 * if neccessary, <br>
	 * that saves the last opened directory
	 * 
	 * @return	File
	 */
	private File createLastFilePathFile()
	{
		File file = new File(app_data_dir, ".lastFilePath");
		try
		{
			file.createNewFile();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		
		if ( os == OS.WINDOWS ) FileUtil.hide(file);
		
		return file;
	}

	/**
	 * Retrieve the command line parameters for this app.
	 */
	private void getAppParameters()
	{
		List<String> args = getParameters().getUnnamed();
		System.out.println("args:");

		for ( String arg : args )
		{
			System.out.println(" - "+arg);
			// open file handler
			if ( new File(arg).isFile() )
			{
				system_action_handler.setLastOpenedFile(new File(arg));
			}
		}
		
		// --name=value
		params = getParameters().getNamed();
		System.out.println("params: "+params);
	}

	/**
	 * Check for double clicked opened file, <br>
	 * handle it,<br>
	 * listen for more.<br>
	 * Listen for menu and doc quit requests and handle them properly.
	 */
	private void addSystemActionHandler()
	{
		system_action_handler.addEventHandler(SystemActionEvent.QUIT_REQUEST, e->handleQuitRequest(e));
	}

	/**
	 * Handle a system quit request.
	 * 
	 * @param	e SystemActionEvent
	 */
	private void handleQuitRequest(SystemActionEvent e)
	{
		boolean clear = confirmClear();
		if ( clear )
		{
			quit(e);
		}
		else
		{
			cancelQuit(e);
		}
	}

	/**
	 * Handle system quit.
	 * 
	 * @param	e SystemActionEvent
	 */
	private void quit(SystemActionEvent e)
	{
		// doc icon quit
		if ( e.getEventType() == SystemActionEvent.DOC_QUIT_REQUEST )
		{
			system_action_handler.performQuit();
		}
		// cmd+q / app menu quit
		else
		{
			Platform.exit();
			System.exit(0);
		}
	}

	/**
	 * Cancel system quit request.
	 * 
	 * @param	e SystemActionEvent
	 */
	protected void cancelQuit(SystemActionEvent e)
	{
		if ( e.getEventType() == SystemActionEvent.DOC_QUIT_REQUEST )
		{
			system_action_handler.cancelQuit();
		}
	}
	
	/**
	 * Stage resize handler.
	 * 
	 * @param	w int new stage witdth 
	 * @param	h int new stage height
	 */
	private void onResizeStage(final int w, final int h)
	{
		STAGE_W = w;
		STAGE_H = h;

		if ( STAGE_W < STAGE_MIN_W ) STAGE_W = STAGE_MIN_W;
		if ( STAGE_H < STAGE_MIN_H ) STAGE_H = STAGE_MIN_H;
		
		root.onResize(STAGE_W, STAGE_H);
	}
	
	/**
	 * Programmatically close the stage.
	 */
	public void closeStage()
	{
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	/**
	 * Catch stage closing event to offer a save option.<br>
	 * TODO: confirm closing, if unsaved
	 * 
	 * @param	e WindowEvent
	 */
	private void onCloseStage(WindowEvent e)
	{
		System.out.println("Main.onCloseStage("+e+")");
//		LOGGER.info("stage is closing");
		
		Stage stage = ((Stage) e.getSource());

//		boolean clear = true; // if testing / developing, skip check
		boolean clear = confirmClear();
		
		if ( clear )
		{
			stage.close();
		}
		else
		{
			e.consume();
		}
	}
	
	@Override
	public void stop() throws Exception
	{
		super.stop();
		system_action_handler.dispose();
	}

	/**
	 * Confirm a stage clear action: <br>
	 * Happens when closing window, new button click.<br>
	 * Offer an option to save, cancel or don't mind.
	 * 
	 * @return	boolean wether clearance is permitted or not
	 */
	public boolean confirmClear()
	{
		System.out.println("Main.confirmClear()");

		// if nothing has been changed, carry on
		if ( !root.hasChanged() )
		{
			return true;
		}
		
		// Save file ??
		if ( confirm_clear_dialog == null )
		{
			createConfirmDialog();
		}
		
		confirm_clear_dialog.showAndWait();
		return confirm_clear_dialog.handleChoice();
	}

	/**
	 * Create a confirmation dialog: <br>
	 * yes, no, cancel an option.
	 */
	private void createConfirmDialog()
	{
		confirm_clear_dialog = new ConfirmClearDialog(AppTitle.DEFAULT_VALUE);
		confirm_clear_dialog.setYesCallback(root::save);
	}
	
	public Root getRoot()
	{
		return root;
	}

	public AppTitle getAppTitle()
	{
		return app_title;
	}

	/*private static void initLogger()
	{
		LOGGER = Logger.getLogger(Main.class.getName());
		FileHandler fh;

		try
		{
			SimpleFormatter formatter = new SimpleFormatter();
			File log_file_dir = ( OS.getSystemOS() == OS.WINDOWS ) ? APP_DATA_DIR_WIN : APP_DATA_DIR_MAC;
			File log_file = new File(log_file_dir, "run.log");
			log_file.createNewFile();
			fh = new FileHandler(log_file.getAbsolutePath());
			fh.setFormatter(formatter);

			LOGGER.addHandler(fh);
			LOGGER.setUseParentHandlers(false);
		}
		catch ( SecurityException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Main function.
	 * 
	 * @param	args String[] command line arguments
	 */
	public static void main(String[] args)
	{
//		initLogger();
		launch(args);
	}
}
