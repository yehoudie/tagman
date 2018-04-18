package de.yehoudie.tagman.content;

import java.io.File;
import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.yehoudie.control.button.TextButton;
import de.yehoudie.tagman.Root;
import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.tagman.objects.FileData;
import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.utils.YAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DirectoryContentView extends SplitPane
{
	private Root root;
	private DirectoryBrowser directory_browser;
	private EntryView entry_view;
	private DataView data_view;

	/**
	 * SplitPane of a directory browser and its content preview.
	 * 
	 * @param	root Root
	 */
	public DirectoryContentView(Root root)
	{
		this.root = root;
//		this.getStyleClass().add("content");
		this.getStyleClass().add("directory_content_view");
		
		init();
	}
	
	/**
	 * Initialize.
	 */
	private void init()
	{
		createDirectoryBrowser();
		createEntryView();
		createDataView();
		
		this.setDividerPositions(0.2f);
	}

	/**
	 * Create the directory browser.
	 */
	private void createDirectoryBrowser()
	{
		directory_browser = new DirectoryBrowser(this::itemClicked);
		
		this.getItems().add(directory_browser);
	}
	
	/**
	 * Callback of a directory event.
	 * 
	 * @param	data GroupData
	 */
	private void itemClicked(MouseEvent e)
	{
		System.out.println("DirectoryContent.fileClicked("+e+")");

		TextButton source = (TextButton) e.getSource();
		FileData data = (FileData) source.getUserData();
		
		if ( data.isFile() ) root.showFile(data);
		else
		{
			if ( e.getButton().equals(MouseButton.PRIMARY) )
			{
	            if ( e.getClickCount() != 2) return;
			}
			changeDir(data);
		}
		
//		root.selectGroupEntries(data);
	}

	public void showFile(TagData data)
	{		
		entry_view.fill(data);
	}
	
	public void showData(TagData data)
	{
		data_view.fill(data);
	}

	private void changeDir(FileData data)
	{
		System.out.println("DirectoryContentView.changeDir("+data+")");
		entry_view.clear();
		root.open(data.getFile());
	}

	private void createEntryView()
	{
		entry_view = new EntryView(root);
		
		this.getItems().add(entry_view);
	}
	
	private void createDataView()
	{
		data_view = new DataView(root);
		
		this.getItems().add(data_view);
	}

	/**
	 * Update content.
	 */
	public void update(File file)
	{
		directory_browser.fill(file);
	}
	
	/**
	 * Activate the browser.
	 */
	public void activate()
	{
		directory_browser.activate();
		entry_view.activate();
		data_view.activate();
	}
	
	/**
	 * Deactivate the browser and the entry preview.
	 */
	public void deactivate()
	{
		directory_browser.deactivate();
		entry_view.deactivate();
		data_view.deactivate();
	}

	public DataView getDataView()
	{
		return data_view;
	}
	
	public EntryView getEntryView()
	{
		return entry_view;
	}
}
