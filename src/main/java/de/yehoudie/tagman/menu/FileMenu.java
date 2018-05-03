package de.yehoudie.tagman.menu;

import java.util.function.Consumer;

import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.filemanager.TextManager;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class FileMenu extends TopMenu
{

	public FileMenu(Root root)
	{
		super(root);
		
		init();
	}

	private void init()
	{
		TextManager tm = TextManager.getInstance();
		
		setText(tm.get(TextManager.MENU_FILE));
		
		MenuItem preferences = createPreferencesItem(tm);
		MenuItem open = createOpenItem(tm);
//		MenuItem save = createSaveItem(tm);
//		MenuItem clear = createClearItem(tm);
		MenuItem exit = createExitItem(tm);

		this.getItems().addAll(
				preferences, 
				new SeparatorMenuItem(), 
				open, 
//				save, clear, 
				new SeparatorMenuItem(), 
				exit);
	}

	private MenuItem createPreferencesItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_FILE_PREFERENCES));
		Consumer<MenuItem> preferences_c = p->root.showPreferences(p);
		item.setUserData(preferences_c);
		return item;
	}

	private MenuItem createOpenItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_FILE_OPEN));
		item.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
		Consumer<MenuItem> c = p->root.open(p);
		item.setUserData(c);
		return item;
	}
	
	private MenuItem createSaveItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_FILE_SAVE));
		item.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
//		Consumer<MenuItem> c = p->root.save(p);
//		item.setUserData(c);
		return item;
	}
	
	private MenuItem createClearItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_FILE_CLEAR));
		item.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		return item;
	}

	private MenuItem createExitItem(TextManager tm)
	{
		MenuItem item = new MenuItem(tm.get(TextManager.MENU_FILE_EXIT));
		item.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN));
		Consumer<MenuItem> c = p->root.exit(p);
		item.setUserData(c);
		return item;
	}
}
