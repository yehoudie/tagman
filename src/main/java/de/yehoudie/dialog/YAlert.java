package de.yehoudie.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class YAlert 
{
	public static final int PREF_DIALOG_WIDTH = 480;
	
	private YAlert()
	{
	}
	
	private static Alert _instance;
	
	/**
	 * get instance of the alert dialog with cutstom esg icon
	 * 
	 * @param	type AlertType the type
	 * @param	header	String the header/content text
	 * @return	Alert the dialog
	 */
	public static Alert getInstance(AlertType type, String header)
	{
		if ( _instance == null )
		{
			_instance = createDialog();
		}
		
		_instance.setAlertType(type);
		_instance.setHeaderText(header);
//		setTitle(type);
		
		_instance.setResizable(true);
		_instance.getDialogPane().setPrefWidth(PREF_DIALOG_WIDTH);
		
		return _instance;
	}

	/**
	 * create the actual button and add a custom icon
	 */
	private static Alert createDialog()
	{
		Alert alert = new Alert(AlertType.WARNING, "", ButtonType.OK);
		javafx.stage.Stage d_stage = (javafx.stage.Stage) alert.getDialogPane().getScene().getWindow();
		d_stage.getIcons().add(new Image(YAlert.class.getResourceAsStream("/icon.png")));
//		alert.setTitle("");
//		alert.setContentText("");
//		alert.initOwner(_window.getScene().getWindow());
//		alert.initStyle(StageStyle.UTILITY);
		return alert;
	}

	/**
	 * set localiced title
	 * @param type
	 */
	/*private static void setTitle(AlertType type)
	{
		TextManager tm = TextManager.getInstance();
		if ( type == AlertType.CONFIRMATION ) _instance.setTitle( tm.get("Confirmation") );
		else if ( type == AlertType.WARNING ) _instance.setTitle( tm.get("Warning") );
		else if ( type == AlertType.ERROR ) _instance.setTitle( tm.get("ErrorLabel") );
			
		
	}*/
}
