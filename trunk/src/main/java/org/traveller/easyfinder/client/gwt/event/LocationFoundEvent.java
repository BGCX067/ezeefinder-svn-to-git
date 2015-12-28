package org.traveller.easyfinder.client.gwt.event;

import java.util.ArrayList;
import java.util.List;

import org.traveller.easyfinder.shared.Location;

import com.google.gwt.event.shared.GwtEvent;

public class LocationFoundEvent extends GwtEvent<LocationFoundEventHandler> 
{
	public static Type<LocationFoundEventHandler> TYPE = new Type<LocationFoundEventHandler>();
	
	private List<Location> locations = new ArrayList<Location>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LocationFoundEventHandler> getAssociatedType() 
	{
		return TYPE;
	}

	@Override
	protected void dispatch( LocationFoundEventHandler handler ) 
	{
		handler.onLocationFound( this );
	}

	public List<Location> getLocations() 
	{
		return locations;
	}

	public void setLocations(List<Location> locations) 
	{
		this.locations = locations;
	}
}
