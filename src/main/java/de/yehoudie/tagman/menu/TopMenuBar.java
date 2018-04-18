package de.yehoudie.tagman.menu;

import java.util.function.Consumer;

import de.yehoudie.tagman.Root;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopMenuBar extends MenuBar
{
	private Root root;
	private TopMenu file_menu;
	private TopMenu entry_menu;

	private EventHandler<ActionEvent> click_callback;

	/**
	 * menu
	 * TODO: add shortcuts to map
	 * 
	 * @param	root Root
	 */
	public TopMenuBar(Root root)
	{
		this.root = root;
		this.getStyleClass().add("pm_menu_bar");
		
		init();
	}

	private void init()
	{
		click_callback = e->handleItemClick(e);
		
		creeateMenues();
		addClickCallbacks();
		
		this.getMenus().addAll(file_menu, entry_menu);
	}

	private void creeateMenues()
	{
		file_menu = new FileMenu(root);
		entry_menu = new EntryMenu(root);
	}
	
	private void addClickCallbacks()
	{
		file_menu.setClickCallback(click_callback);
		entry_menu.setClickCallback(click_callback);
	}

	public void activate()
	{
		file_menu.activate();
		entry_menu.activate();
	}
	
	public void deactivate()
	{
		file_menu.deactivate();
		entry_menu.deactivate();
	}
	
	private void handleItemClick(ActionEvent e)
	{
		System.out.println("PMMenuBar.handleItemClick("+e+")");
		MenuItem item = (MenuItem) e.getSource();
		System.out.println(" - item: "+item);
		System.out.println(" - data: "+item.getUserData());

		Consumer<MenuItem> c = (Consumer<MenuItem>) item.getUserData();
		c.accept(item);
	}
}
