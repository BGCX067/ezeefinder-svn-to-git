package org.traveller.easyfinder.client.gwt;

import org.traveller.easyfinder.client.gwt.event.LocationFoundEvent;
import org.traveller.easyfinder.client.gwt.event.LocationFoundEventHandler;
import org.traveller.easyfinder.client.gwt.presenter.Presenter;
import org.traveller.easyfinder.client.gwt.presenter.findlocation.FindLocationPresenter;
import org.traveller.easyfinder.client.gwt.presenter.location.LocationDetailsPresenter;
import org.traveller.easyfinder.client.gwt.view.findlocation.FindLocationView;
import org.traveller.easyfinder.client.gwt.view.location.LocationDetailsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class ApplicationController implements Presenter
{
	HasWidgets container = null;
	
	private final HandlerManager eventBus;
	
	public ApplicationController( HandlerManager eventBus )
	{
		//Initialization Code
		this.eventBus = eventBus;
		
		bind();
	}
	
	public void go( HasWidgets container ) 
	{
		this.container = container;
		
		FindLocationPresenter findLocationPresenter = new FindLocationPresenter( new FindLocationView(), eventBus );
		
		findLocationPresenter.go( container );
	}
	
	void bind()
	{
		//Bind All Event Here
		/*
		eventBus.addHandler( LocationFoundEvent.TYPE, 	new LocationFoundEventHandler()
														{
															public void onLocationFound( LocationFoundEvent event ) 
															{
																GWT.log( "Location Found Event Fired" );
																//Window.alert( "Location Found Event Fired" );
																
																//container.clear();
																//LocationDetailsPresenter presenter = new LocationDetailsPresenter( new LocationDetailsView(), eventBus );
																//presenter.go( container );
															}
														} );
		*/
	}
}
