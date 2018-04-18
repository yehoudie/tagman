/**
 * 
 */
package de.yehoudie.tagman.dialogs;

import de.yehoudie.tagman.filemanager.TextManager;

/**
 * @author yehoudie
 *
 */
public class ConfirmClearDialog extends ConfirmDialog
{
	public ConfirmClearDialog(String app_default_title)
	{
		super(app_default_title);
		
		init();
	}

	@Override
	protected void init()
	{
		super.init();
		String header = TextManager.getInstance().get(TextManager.SAVE_CHANGES);
		this.setHeaderText(header);
	}
}
