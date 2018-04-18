package de.yehoudie.utils.system;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

import de.yehoudie.utils.event.EventTarget;
import de.yehoudie.utils.files.FileChangeListener;
import de.yehoudie.utils.files.FileEvent;
import de.yehoudie.utils.files.FileUtil;
import javafx.application.Platform;

public class SingleInstanceGuard extends EventTarget
{
	private static final String instanceId = UUID.randomUUID().toString();

	private static SingleInstanceGuard instance;

	private File lock_file;
	private File message_file;

	private boolean is_server;
	private boolean is_guarding;

	private FileChangeListener fcl;
	private RandomAccessFile random_file = null;

	private List<String> args;

	/**
	 * guard, that keeps a single instance of the app running,
	 * by setting a file lock.
	 * messages (e.g. a start up file) may be sent from the cloesed app to the single running app by writing to message file.
	 * 
	 * @param	lock_file File the file to set the lock on 
	 * @param	message_file File the file to write messages to
	 */
	private SingleInstanceGuard(File lock_file, File message_file)
	{
		System.out.printf("SingleInstanceGuard(%s, %s)\n",lock_file.toString(), message_file.toString());
		this.lock_file = lock_file;
		this.message_file = message_file;
		
		init();
	}
	
	public static SingleInstanceGuard getInstance(File lock_file, File message_file)
	{
		if ( instance == null )
		{
			instance = new SingleInstanceGuard(lock_file, message_file);
		}
		
		return instance;
	}
	
	
	public void init()
	{
		createFile(lock_file);
		createFile(message_file);
	}

	/**
	 * start guarding.
	 */
	public void guard()
	{
//		System.out.println("SingleInstanceGuard.guard()");
		if ( is_guarding ) return;
		
		is_guarding = true;
		
		// try to get a lock on a file
		try
		{
			System.out.println("open file for random access");
			// lock_file = new File("bin/singleInstance/SingleInstanceAppFileLock.class");
			random_file = new RandomAccessFile(lock_file, "rw");

			FileChannel channel = random_file.getChannel();

			// no lock possible, cause it's already locked
			if ( channel.tryLock() == null )
			{
				doClient();
			}
			else
			{
				doServer();
			}
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
		}
	}

	/**
	 * 
	 */
	private void doServer()
	{
//		System.out.println("SingleInstanceGuard.doServer()");
		is_server = true;

		FileUtil.write("", message_file);

		fcl = new FileChangeListener();
		fcl.addFile(message_file);
		fcl.addEventHandler(FileEvent.CHANGED, e -> fileChanged(e));
		fcl.start();
	}

	private void fileChanged(FileEvent e)
	{
		fireEvent(new FileEvent(e.getFile(), FileEvent.CHANGED));
	}

	/**
	 * app is already running.
	 * write params to message file and close this one.
	 */
	private void doClient()
	{
//		System.out.println("SingleInstanceGuard.doClient()");
		String args_str = getArgsString();
//		System.out.println(" - args string: "+args_str);
		if ( args_str != null && !args_str.isEmpty() ) FileUtil.write(args_str, message_file, true);
		Platform.exit();
	}

	private String getArgsString()
	{
		if ( args == null || args.size() == 0 ) return "";
		
		StringBuilder sb = new StringBuilder();
		for ( String arg : args )
		{
			sb.append(arg).append("|");
		}
		return sb.toString();
	}

	/**
	 * create a file, if neccessary, that saves the last opened directory
	 * 
	 * @return File
	 */
	private File createFile(File file)
	{
		try
		{
			file.createNewFile();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		return file;
	}

	public void setArgs(List<String> args)
	{
		this.args = args;
	}

	public void clearMessage()
	{
		if ( is_server )
		{
			fcl.stop();
			FileUtil.write("", message_file);
			fcl.start();
		}		
	}

	public void dispose()
	{
		System.out.println("Exiting instance " + instanceId);
		// message file if server
		clearMessage();
		message_file = null;
		
		is_guarding = false;
		
		if ( fcl != null )
		{
			fcl.dispose();
		}
		
		if ( random_file != null )
		{
			try
			{
				random_file.close();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		lock_file = null;
	}
}
