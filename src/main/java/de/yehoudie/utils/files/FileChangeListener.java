package de.yehoudie.utils.files;

import java.io.File;

import de.yehoudie.utils.event.EventTarget;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

public class FileChangeListener extends EventTarget
{
	private File file;
	private Task<Void> task;
	private LongProperty last_modified = new SimpleLongProperty();
	private ChangeListener<Number> change_listener;

	private boolean is_running;
	
	/**
	 * create change listener for one file.
	 * TODO: create new class to support list of files.
	 */
	public FileChangeListener()
	{
		init();
	}
	
	private void init()
	{
		this.change_listener = new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
//				System.out.printf("file changed: %s, %s, %s\n",observable.toString(),oldValue.toString(),newValue.toString());
				fireEvent(new FileEvent(file, FileEvent.CHANGED));
			}
		};
	}

	public void addFile(File file)
	{
		this.file = file;

		last_modified.set( file.lastModified() );
	}
	
	public void removeFile()
	{
		this.file = null;
		stop();
	}
	
	public void start()
	{
		is_running = true;
		last_modified.set( file.lastModified() );
		last_modified.addListener(change_listener);
		
		createTask();
//		task.setOnSucceeded( (WorkerStateEvent t) -> buildDone() );

		Thread thread = new Thread(task);
		thread.start();
		
	}

	/**
	 * 
	 */
	private void createTask()
	{
		task = new Task<Void>()
		{
			@Override
			protected Void call()
			{
				while ( is_running )
				{
					last_modified.set( file.lastModified() );
				}
				
				return null;
			}
		};
	}
	
	public void stop()
	{
		last_modified.removeListener(change_listener);
		is_running = false;
	}

	public void dispose()
	{
		stop();
		task.cancel();
		task = null;
	}
}
