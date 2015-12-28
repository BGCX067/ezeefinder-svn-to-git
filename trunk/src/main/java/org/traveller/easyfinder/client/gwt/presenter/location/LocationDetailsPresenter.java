package org.traveller.easyfinder.client.gwt.presenter.location;

import org.traveller.easyfinder.client.gwt.presenter.Presenter;
import org.traveller.easyfinder.client.gwt.view.Display;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class LocationDetailsPresenter implements Presenter 
{
	private final Display display;
	private final HandlerManager eventBus;

	public LocationDetailsPresenter( Display display, HandlerManager eventBus ) 
	{
		this.display = display;
		this.eventBus = eventBus;
		
		bind();
	}

	void bind()
	{
	}
	
	public void go( HasWidgets container ) 
	{
		container.add( display.asWidget() );
	}
}
