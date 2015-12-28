package org.traveller.easyfinder.shared;

public class Location 
{
	private String searchResultID   = "";
	private String formattedAddress = "";
	private String lattitude 		= "";
	private String langitude 		= "";
	
	public String getSearchResultID() 
	{
		return searchResultID;
	}

	public void setSearchResultID(String searchResultID) 
	{
		this.searchResultID = searchResultID;
	}

	public String getFormattedAddress() 
	{
		return formattedAddress;
	}
	
	public void setFormattedAddress(String formattedAddress) 
	{
		this.formattedAddress = formattedAddress;
	}
	
	public String getLattitude() 
	{
		return lattitude;
	}
	
	public void setLattitude(String lattitude) 
	{
		this.lattitude = lattitude;
	}
	
	public String getLangitude() 
	{
		return langitude;
	}
	
	public void setLangitude(String langitude) 
	{
		this.langitude = langitude;
	}
}
