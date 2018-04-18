package de.yehoudie.tagman;

import java.io.File;
import java.util.ArrayList;

import de.yehoudie.tagman.content.AbstractView;
import de.yehoudie.tagman.content.DataView;
import de.yehoudie.tagman.content.DirectoryContentView;
import de.yehoudie.tagman.content.EntryView;
import de.yehoudie.tagman.content.PreferencesView;
import de.yehoudie.tagman.content.controller.DataController;
import de.yehoudie.tagman.content.controller.EntryController;
import de.yehoudie.tagman.filemanager.TextManager;
import de.yehoudie.tagman.menu.TopMenuBar;
import de.yehoudie.tagman.objects.FileData;
import de.yehoudie.tagman.objects.TagData;
import de.yehoudie.tagman.utils.FileHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class Root extends VBox
{
	private TextManager text_manager;
	private EntryController entry_controller;
	private DataController data_controller;
	public EntryController getEntryController() { return entry_controller; };
	
	private Main main;
	private TopMenuBar menu_bar;
	private DirectoryContentView directory_content_view;

	private Node act_content;
	private Node last_content;
	
	private Clipboard clipboard;
	
	private boolean has_changed;

	private PreferencesView prefs_view;

	public boolean hasChanged() { return has_changed; };
	public void hasChanged(boolean has_changed)
	{
		this.has_changed = has_changed;
	};

	/**
	 * The root controller.
	 * 
	 * @param	main Main
	 */
	public Root(Main main)
	{
		this.main = main;
		
		this.getStyleClass().add("root_box");
	}
	
	public void init()
	{
		clipboard = Clipboard.getSystemClipboard();
		text_manager = TextManager.getInstance();

		directory_content_view = new DirectoryContentView(this);
		directory_content_view.activate();
		
		entry_controller = new EntryController(this);
		data_controller = new DataController(this);
		
		menu_bar = new TopMenuBar(this);
		menu_bar.activate();
		
		this.getChildren().add(menu_bar);
		addContent(directory_content_view);
	}
	
	/**
	 * Draw the content.
	 */
	public void draw()
	{
//		this.addContent(act_content);
	}
	
	/**
	 * Window resize handler.
	 * 
	 * @param	w int the new width
	 * @param	h int the new height
	 */
	public void onResize(final int w, final int h)
	{
		
	}


	/**
	 * Close a detailed view an return to last content.
	 * 
	 * @param	content AbstractView
	 */
	public void quitDetailView(AbstractView content)
	{
		System.out.println("Root.quitDetailView("+content+")");
		quitView(content, last_content);
	}
	
	/**
	 * Close a detailed view an return to another content.
	 * 
	 * @param	old_content Pane
	 * @param	new_content Pane
	 */
	public void quitView(Node old_content, Node new_content)
	{
		System.out.println("Root.quitView");
		removeContent(old_content);
		addContent(new_content);
	}
	
	/**
	 * Add content Node.
	 * 
	 * @param	content Node
	 */
	protected void addContent(Node content)
	{
		if ( content != null && content.getParent() != this )
		{
			last_content = act_content;
			act_content = content;
			this.getChildren().add(content);
			
//			last_content.deactivate();
//			act_content.activate();
		}
	}

	/**
	 * Remove actual content Node.
	 */
	protected void removeContent()
	{
		if ( act_content != null && act_content.getParent() == this )
		{
			this.getChildren().remove(act_content);
		}
	}
	
	/**
	 * Remove content node
	 * 
	 * @param	content Node the content node to remove
	 */
	protected void removeContent(Node content)
	{
		if ( content != null && content.getParent() == this )
		{
			this.getChildren().remove(content);
			act_content = null;
		}
	}
	
	public boolean save()
	{
		return save(null);
	}
	
	/**
	 * Save changed entries and directories.<br>
	 * TODO: save groups<br>
	 * TODO: update group ids with database ids
	 *  
	 * @param	item MenuItem dummy param to match consumer interface.
	 * @return	boolean success state
	 */
	public boolean save(MenuItem item)
	{
		System.out.println("Root.save("+item+")");

		hasChanged(false);
		main.getAppTitle().markChanged(false);
		
		return true;
	}

	/**
	 * @param has_changed
	 */
	public void markChanged(boolean has_changed)
	{
		main.getAppTitle().markChanged(has_changed);
	}

	/**
	 * Copy some content to the clipboard.
	 * 
	 * @param	item MenuItem
	 */
	public void copyToClipBoard(MenuItem item)
	{
		System.out.println("cooyToClipBoard("+item+")");
		
//		EntryData data = entry_view_controller.getSelected();
//		if ( data == null ) return;
//		
//		String value = null;
//		if ( item.getId().equals(EntryMenu.COPY_NAME_ID) ) value = data.getName();
//		else if ( item.getId().equals(EntryMenu.COPY_PW_ID) ) value = data.getPassword();
//		
//		if ( value == null ) return;
//		
//		final ClipboardContent content = new ClipboardContent();
//		content.putString(value);
//		clipboard.setContent(content);
	}
	
	/**
	 * Show the preferences view.
	 * 
	 * @param	item MenuItem unused clicked menu item
	 */
	public void showPreferences(MenuItem item)
	{
		System.out.println("Root.showPreferences("+item+")");
		removeContent();
		
		if ( prefs_view == null ) prefs_view = new PreferencesView(this);
		prefs_view.fill();
		prefs_view.activate();

		addContent(prefs_view);
	}
	
	public void open(MenuItem item)
	{
		System.out.println("Root.open("+item+")");
		
		File selected = getSelectedFile();
		System.out.println(" - selected: "+selected);
		
		open(selected);
	}
	
	public void open(File selected)
	{
		directory_content_view.update(selected);
	}

	private File getSelectedFile()
	{
		File last_path = FileHandler.getLastFilePath(new File(System.getProperty("user.home")));
		
		DirectoryChooser directory_chooser = new DirectoryChooser();
		directory_chooser.setInitialDirectory(last_path);
		File selected = directory_chooser.showDialog(this.getScene().getWindow());
		
		if ( selected != null ) FileHandler.setLastFilePath(selected);
		return selected;
	}
	
	

	/**
	 * @param	data EntryData
	 */
	public void submitChange(TagData data)
	{
		System.out.println("Root.submitChange("+data+")");
		entry_controller.submitChange(data);
	}
	
	/**
	 * @param	data EntryData
	 */
	public void submitNoChange(TagData data)
	{
		System.out.println("Root.submitNoChange("+data+")");
//		entry_view_controller.submitNoChange(data);
	}
	
	public void showFile(FileData file_data)
	{
		TagData tag_data = entry_controller.fill(file_data);
		data_controller.syncData(tag_data);
		directory_content_view.showFile(tag_data);
		main.getAppTitle().setFilePart(file_data.getFile());
	}
	
	public void submitDataChange(ArrayList<String> values, TextInputControl source_input)
	{
		System.out.printf("Root.submitDataChange(%s, %s)\n", values.toString(), source_input.toString());
		data_controller.submitChange(values, source_input);
	}
	
	public void fillData(TagData data)
	{
		directory_content_view.showData(data);
	}
	
	/**
	 * Exit the program.
	 * 
	 * @param	item MenuItem unused clicked menu item
	 */
	public void exit(MenuItem item)
	{
		System.out.println("Root.exit("+item+")");
		
		main.closeStage();
	}

	/**
	 * Clean up.
	 */
	private void dispose()
	{
//		act_content.deactivate();
	}

	public DataView getDataView()
	{
		return directory_content_view.getDataView();
	}

	public EntryView getEntryView()
	{
		return directory_content_view.getEntryView();
	}
}
