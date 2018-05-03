package de.yehoudie.tagman.dialogs;

import java.util.function.Supplier;

import de.yehoudie.dialog.YAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

/**
 * @author yehoudie
 *
 */
public abstract class ConfirmDialog extends Alert
{
	protected String app_default_title;
	protected Supplier<Boolean> yesCallback;
	public void setYesCallback(Supplier<Boolean> yesCallback) { this.yesCallback = yesCallback; };

	public ConfirmDialog(String app_default_title)
	{
		super(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		this.app_default_title = app_default_title;
	}

	protected void init()
	{
		this.setTitle(app_default_title);
		this.setResizable(true);
		this.getDialogPane().setPrefWidth(YAlert.PREF_DIALOG_WIDTH);
		javafx.stage.Stage d_stage = (javafx.stage.Stage) this.getDialogPane().getScene().getWindow();
		d_stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
	}
	
	public boolean handleChoice()
	{
		if ( this.getResult() == ButtonType.YES )
		{
			System.out.println(" - YES");
			System.out.println(" -- start saving...");

			return yesCallback.get();
		}
		if ( this.getResult() == ButtonType.NO )
		{
			System.out.println(" - NO");
			return true;
		}
		else if ( this.getResult() == ButtonType.CANCEL )
		{
			System.out.println(" - CANCEL");
			return false;
		}

		return false;
	}
}
