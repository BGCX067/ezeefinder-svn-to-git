package org.traveller.easyfinder.client.gwt;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * org.traveller.easyfinder.client.gwt.ApplicationLauncher
 * 
 * @author skandikonda
 */
public class ApplicationLauncher implements EntryPoint  
{
	public void onModuleLoad() 
	{
		//final Button sendButton = new Button( "Send" );
		//RootPanel.get( "mainPanel" ).add( sendButton );
		/*
		ApplicationUiBinder uiBinder = new ApplicationUiBinder();
		RootPanel.get().add(uiBinder);
		*/
		
		ApplicationController controller = new ApplicationController( new HandlerManager( null ) );
		controller.go( RootLayoutPanel.get() );
	}
}
