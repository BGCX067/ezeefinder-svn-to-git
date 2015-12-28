package org.traveller.easyfinder.server.pojo;

import java.util.LinkedList;
import java.util.List;

import org.traveller.easyfinder.shared.Location;

public class Result 
{
	List<Location> locations = new LinkedList<Location>();

	public String getResultSetSize()
	{
		return "" + ( locations.size() );
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
