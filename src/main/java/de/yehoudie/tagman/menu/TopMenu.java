/**
 * 
 */
package de.yehoudie.tagman.menu;

import de.yehoudie.tagman.Root;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * @author yehoudie
 *
 */
public abstract class TopMenu extends Menu
{
	protected Root root;
	
	protected EventHandler<ActionEvent> click_callback;

	public TopMenu(Root root)
	{
		super();
		
		this.root = root;
	}

	public void activate()
	{
		for ( MenuItem item : this.getItems() )
		{
			item.setOnAction(click_callback);
		}
	}

	public void deactivate()
	{
		for ( MenuItem item : this.getItems() )
		{
			item.setOnAction(null);
		}
	}
	
	public void setClickCallback(EventHandler<ActionEvent> click_callback)
	{
		this.click_callback = click_callback;
	}
}
