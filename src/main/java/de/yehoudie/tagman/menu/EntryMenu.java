/**
 * 
 */
package de.yehoudie.tagman.menu;

import java.util.function.Consumer;

import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.content.controller.EntryController;
import de.yehoudie.tagman.filemanager.TextManager;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author yehoudie
 *
 */
public class EntryMenu extends TopMenu
{
	private EntryController entry_controller;
	
	public EntryMenu(Root root)
	{
		super(root);
		
		init();
	}

	private void init()
	{
		TextManager tm = TextManager.getInstance();
		entry_controller = root.getEntryController();
		
		setText(tm.get(TextManager.MENU_ENTRY));
		
		MenuItem data = createDataItem(tm);
		MenuItem fill = createFillItem(tm);

		this.getItems().addAll(data, fill);
	}

	private MenuItem createDataItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_ENTRY_DATA));
		item.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
		Consumer<MenuItem> item_c = p->entry_controller.setData();
		item.setUserData(item_c);
		return item;
	}

	private MenuItem createFillItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_ENTRY_FILL));
		item.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
//		Consumer<MenuItem> item_c = p->entry_controller.fill();
//		item.setUserData(item_c);
		return item;
	}
}
