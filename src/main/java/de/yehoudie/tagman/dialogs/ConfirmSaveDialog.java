package de.yehoudie.tagman.dialogs;

import de.yehoudie.tagman.filemanager.TextManager;

/**
 * @author yehoudie
 *
 */
public class ConfirmSaveDialog extends ConfirmDialog
{
	public ConfirmSaveDialog(String app_default_title)
	{
		super(app_default_title);
		
		init();
	}

	@Override
	protected void init()
	{
		super.init();
		String header = TextManager.getInstance().get(TextManager.ENTRY_CHANGED);
		this.setHeaderText(header);
	}
}
