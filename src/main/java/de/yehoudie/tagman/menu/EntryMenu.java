/**
 * 
 */
package de.yehoudie.tagman.menu;

import java.util.function.Consumer;

import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.content.controller.DataController;
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
	private DataController data_controller;
	
	public EntryMenu(Root root)
	{
		super(root);
		
		init();
	}

	private void init()
	{
		TextManager tm = TextManager.getInstance();
		entry_controller = root.getEntryController();
		data_controller = root.getDataController();
		
		setText(tm.get(TextManager.MENU_ENTRY));
		
		MenuItem fill_data = createFillDataItem(tm);
		MenuItem fill_entry= createFillEntryItem(tm);
		MenuItem change_file_names = createChangeFileNamesItem(tm);

		this.getItems().addAll(fill_data, fill_entry, change_file_names);
	}

	private MenuItem createFillDataItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_ENTRY_TO_DATA));
		item.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
		Consumer<MenuItem> item_c = p->entry_controller.setData();
		item.setUserData(item_c);
		return item;
	}

	private MenuItem createFillEntryItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_DATA_TO_ENTRY));
		item.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
//		Consumer<MenuItem> item_c = p->entry_controller.fill();
//		item.setUserData(item_c);
		return item;
	}
	
	private MenuItem createChangeFileNamesItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_CHANGE_ALL_FILE_NAMES));
		item.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
		Consumer<MenuItem> item_c = p->data_controller.changeAllFileNames();
		item.setUserData(item_c);
		return item;
	}
}
