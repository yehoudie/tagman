package de.yehoudie.tagman.content;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import de.yehoudie.control.button.CustomButton;
import de.yehoudie.control.button.CustomToggleGroup;
import de.yehoudie.control.button.TextButton;
import de.yehoudie.tagman.objects.FileData;
import de.yehoudie.tagman.types.AppFileType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DirectoryBrowser extends ScrollPane
{
	private Consumer<MouseEvent> callback;
	private ArrayList<FileData> data;
	private ArrayList<CustomButton> buttons;
	private CustomToggleGroup toggle_group;
	private VBox content;

	/**
	 * Directory browser.
	 * 
	 * @param	callback Consumer<GroupData> called on item click
	 */
	public DirectoryBrowser(Consumer<MouseEvent> callback)
	{
		this.callback = callback;
		this.getStyleClass().add("diretory_browser");
		
		init();
	}
	
	/**
	 * Initialize:<br>
	 *  - set click listener on items
	 */
	private void init()
	{
		data = new ArrayList<FileData>();
		buttons = new ArrayList<CustomButton>();
		
		content = new VBox();
		content.setMaxWidth(Double.MAX_VALUE);
		
		this.setMaxWidth(Double.MAX_VALUE);
		this.setContent(content);
	}

	/**
	 * Fill the directory browser with content.
	 * 
	 * @param	dir File
	 */
	public void fill(File dir)
	{
		if ( dir == null ) throw new NullPointerException(dir+" is null!");
		if ( !dir.isDirectory() ) throw new IllegalArgumentException(dir+" is not a directory!");
	
		clear();
		fillFiles(dir);
		draw();
	}

	private void clear()
	{
		content.getChildren().clear();
		data.clear();
		buttons.clear();
	}

	private void fillFiles(File dir)
	{
		File parent = new File(dir.getParent());
		System.out.println("parent: "+parent);
		
		data.add(new FileData("..", parent));
		
		for ( final File file : dir.listFiles() )
		{
			if ( file.isDirectory() )
			{
				data.add(new FileData(file.getName(), file));
			}
			else
			{
				if ( AppFileType.isSupportedFileType(file) )
				{
					data.add(new FileData(file.getName(), file));
				}
			}
		}
		
		Collections.sort(data);
	}
	
	private void draw()
	{
		toggle_group = new CustomToggleGroup();
		
		int i = 0;
		for ( FileData entry : data )
		{
			TextButton item = new TextButton(i++, entry.getName(), this::entryClicked);
			if ( entry.isFile() )
			{
				item.getStyleClass().add("file_button");
				item.setToggleGroup(toggle_group);
			}
			else item.getStyleClass().add("directory_button");
			item.setUserData(entry);
			item.setMaxWidth(Double.MAX_VALUE);

			buttons.add(item);
			content.getChildren().add(item);
		}
	}
	
	private void entryClicked(MouseEvent e)
	{
		callback.accept(e);
	}

	/**
	 * Activate tree navigation and click listener.
	 */
	public void activate()
	{
		buttons.forEach(b->b.activate());
	}

	/**
	 * Deactivate tree navigation and click listener.
	 */
	public void deactivate()
	{
		buttons.forEach(b->b.deactivate());
	}
}
