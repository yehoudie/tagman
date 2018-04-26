package de.yehoudie.tagman.content;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import de.yehoudie.control.button.CustomButton;
import de.yehoudie.control.button.CustomToggleGroup;
import de.yehoudie.control.button.TextButton;
import de.yehoudie.tagman.objects.FileData;
import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.tagman.types.AppFileType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DirectoryBrowser extends ScrollPane
{
	private Consumer<MouseEvent> callback;
	private ArrayList<FileData> file_data;
	private ArrayList<CustomButton> buttons;
	private CustomToggleGroup toggle_group;
	private VBox content;
	private File dir;

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
		file_data = new ArrayList<FileData>();
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
	
		this.dir = dir;
		fill();
	}

	private void fill()
	{
		clear();
		fillFiles(dir);
		draw();
	}

	public void update(TagData datum)
	{
		CustomButton button = toggle_group.getSelectedButton();
		if ( button == null ) fill();
		else
		{
			File file = new File(datum.getFileName());
			
			FileData file_data = (FileData) button.getUserData();
			file_data.setName(file.getName());
			file_data.setFile(file);

			button.setUserData(file_data);
			button.setText(file_data.getName());
		}
	}

	private void clear()
	{
		content.getChildren().clear();
		file_data.clear();
		buttons.clear();
	}

	private void fillFiles(File dir)
	{
		File parent = new File(dir.getParent());
		System.out.println("parent: "+parent);
		
		file_data.add(new FileData("..", parent));
		
		for ( final File file : dir.listFiles() )
		{
			if ( file.isDirectory() )
			{
				file_data.add(new FileData(file.getName(), file));
			}
			else
			{
				if ( AppFileType.isSupportedFileType(file) )
				{
					file_data.add(new FileData(file.getName(), file));
				}
			}
		}
		
		Collections.sort(file_data);
	}
	
	private void draw()
	{
		toggle_group = new CustomToggleGroup();
		
		int i = 0;
		for ( FileData datum : file_data )
		{
			TextButton button = new TextButton(i++, datum.getName(), this::entryClicked);
			if ( datum.isFile() )
			{
				button.getStyleClass().add("file_button");
				button.setToggleGroup(toggle_group);
			}
			else button.getStyleClass().add("directory_button");
			button.setUserData(datum);
			button.setMaxWidth(Double.MAX_VALUE);

			buttons.add(button);
			content.getChildren().add(button);
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
