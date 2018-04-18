package de.yehoudie.utils;

import javafx.application.Application;
import javafx.application.HostServices;

public class HostServiceProvider
{
//	private Application app;
	private HostServices services;
	private static HostServiceProvider instance;

	private HostServiceProvider()
	{
	}
	
	public static HostServiceProvider getInstance()
	{
		if ( instance == null )
		{
			instance = new HostServiceProvider();
		}
		
		return instance;
	}
	
	public void init(Application app)
	{
//		this.app = app;
		this.services = app.getHostServices();
	}
	
	public void openLinkInBrower(String uri)
	{
		services.showDocument(uri);
	}
}
