package org.traveller.easyfinder.client.gwt.presenter.findlocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.traveller.easyfinder.client.gwt.event.LocationFoundEvent;
import org.traveller.easyfinder.client.gwt.event.LocationFoundEventHandler;
import org.traveller.easyfinder.client.gwt.presenter.Presenter;
import org.traveller.easyfinder.client.gwt.view.Display;
import org.traveller.easyfinder.client.gwt.view.findlocation.FindLocationDisplay;
import org.traveller.easyfinder.shared.Location;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FindLocationPresenter implements Presenter 
{
	private final FindLocationDisplay 	display;
	private final HandlerManager 		eventBus;
	
	public FindLocationPresenter( FindLocationDisplay display, HandlerManager eventBus )
	{
		this.display = display;
		this.eventBus = eventBus;
		
		bind();
	}
	
	void bind()
	{
		eventBus.addHandler( LocationFoundEvent.TYPE, 	new LocationFoundEventHandler()
		{
			public void onLocationFound( LocationFoundEvent event ) 
			{
				GWT.log( "Location Found Event Fired" );
				
		        display.getPopupVerticalPanel().clear();
		        /*
		        for( Location location : event.getLocations() )
		        {
		        	GWT.log( "Formatted Address : " + location.getFormattedAddress() );
		        	
		        	display.getPopupVerticalPanel().add(  new Label( location.getFormattedAddress() )  );
		        }

		        Button button = new Button( "Close", new ClickHandler() 
														{
															public void onClick(ClickEvent event) 
															{
																display.getPopupPanel().hide();
															}
														} 
		        						  );
		        
		        display.getPopupVerticalPanel().add( button );
				*/
		        display.getPopupVerticalPanel().add( createAdvancedForm( event.getLocations() ) );
		        
		        display.getPopupPanel().center();
			}
		} );

		display.getSubmitButton().addClickHandler	( 
														new ClickHandler() 
														{
															public void onClick(ClickEvent event) 
															{
														        if ( display.getSearchTextbox().getText().length() == 0) 
														        {
														        	Window.alert("The text box must not be empty");
															    }
														        else
														        {
														        	makeServerCall( display.getSearchTextbox().getText() );
														        }
															}
														}
													);
		
		display.getFormPanel().addSubmitHandler(
													new FormPanel.SubmitHandler() 
													{
															public void onSubmit(FormPanel.SubmitEvent event) 
															{
														        if ( display.getSearchTextbox().getText().length() == 0) 
														        {
														        	Window.alert("The text box must not be empty");
															        event.cancel();
															    }
															}
													}
												);
		
		display.getFormPanel().addSubmitCompleteHandler	(
															new FormPanel.SubmitCompleteHandler() 
															{
															      public void onSubmitComplete( SubmitCompleteEvent event ) 
															      {
															        GWT.log( event.getResults() );
															        
															        String jsonStr = removePreTags( event.getResults() );
															        List<Location> locations = parseJSONResponse( jsonStr );
															        
															        //String str = event.getResults();
															        
															        //eventBus.fireEvent( new LocationFoundEvent() );
															        //display.getPopupPanel().add( new Label( event.getResults() ) );
															        
															        display.getPopupVerticalPanel().clear();
															        
															        for( Location location : locations )
															        {
															        	GWT.log( "Formatted Address : " + location.getFormattedAddress() );
															        	
															        	display.getPopupVerticalPanel().add(  new Label( location.getFormattedAddress() )  );
															        }

															        Button button = new Button( "Close", new ClickHandler() 
																											{
																												public void onClick(ClickEvent event) 
																												{
																													display.getPopupPanel().hide();
																												}
																											} 
															        						  );
															        
															        display.getPopupVerticalPanel().add( button );

															        display.getPopupPanel().center();
															        //display.getVerticalPanel().add( new Label( "No Results found" ) );
															      }
															    }
														);
	}
	
	public List<Location> parseJSONResponse( String jsonResponse )
	{
		List<Location> locations = new ArrayList<Location>();
		
		GWT.log( jsonResponse );

		try
		{
			JSONValue jsonValue = JSONParser.parseStrict( jsonResponse );
			  
			//GWT.log( jsonValue.toString() );
			  
			//GWT.log( "Is Array  : " + jsonValue.isArray() );
			//GWT.log( "Is Object : " + jsonValue.isObject());
			  
			//JSONArray array = null;
			  
			if( ( jsonValue.isObject() ) != null )
			{
				//GWT.log( array.toString() );  
				JSONObject initialObject = jsonValue.isObject();
			  
				Set<String> initialKeys = initialObject.keySet();
			  
				for( String key : initialKeys )
				{
					GWT.log( "Inital Object Key : " + key );
					if( "locations".equals( key ) )
					{
						//GWT.log( "" + initialObject.get( key ).isArray() );
			  
						if( ( initialObject.get( key ).isArray() ) != null )
						{
							JSONArray locationsArray = initialObject.get( key ).isArray();
				  
							for( int i = 0; i < locationsArray.size(); i ++ )
							{
								JSONValue locationsArrayValue = locationsArray.get( i );
					  
								//GWT.log( "Location Object : " + locationsArrayValue.toString() );
			  
								JSONValue locationsArrayObjects = JSONParser.parseStrict( locationsArrayValue.toString() );
			  
								if( locationsArrayObjects.isObject() != null )
								{
									Location location = new Location();
				  
									JSONObject contentOject = locationsArrayObjects.isObject();
				  
									Set<String> contentKeys = contentOject.keySet();
				  
									for( String contentKey : contentKeys )
									{
										if( "formattedAddress".equals( contentKey ) )
										{
											JSONValue str = contentOject.get( contentKey );
											JSONString jsonStr = str.isString();
				  
											//GWT.log( "Formatted Address : " + jsonStr.toString() );
											location.setFormattedAddress( jsonStr.toString() );
										}
									}
									locations.add( location );
								}
							}
						}
					}
				}
			}
		}
		catch( Exception e )
		{
			GWT.log( e.getMessage() );
		}
		
		return locations;
	}
	
	private List<Location> makeServerCall( String searchString )
	{
		final List<Location> locations = new ArrayList<Location>();
		
		try
		{
			String url = "/searchLocation.do?searchString=" + searchString;
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
	
			com.google.gwt.http.client.Request httpRequest = builder.sendRequest(null, new com.google.gwt.http.client.RequestCallback() 
			{
				public void onResponseReceived(Request request, Response response) 
				{
				      if (200 == response.getStatusCode()) 
				      {
				    	  String responseStr = response.getText();
				    	  
				    	  GWT.log( responseStr );
				    	  
				    	  JSONValue jsonValue = JSONParser.parseStrict( responseStr );
				    	  
				    	  //GWT.log( jsonValue.toString() );
				    	  
				    	  //GWT.log( "Is Array  : " + jsonValue.isArray() );
				    	  //GWT.log( "Is Object : " + jsonValue.isObject());
				    	  
				    	  if( ( jsonValue.isObject() ) != null )
				    	  {
				    		  //GWT.log( array.toString() );  
				    		  JSONObject initialObject = jsonValue.isObject();
				    		  
			    			  Set<String> initialKeys = initialObject.keySet();
			    			  
			    			  for( String key : initialKeys )
			    			  {
			    				  GWT.log( "Inital Object Key : " + key );
			    				  
			    				  if( "locations".equals( key ) )
			    				  {
			    					  //GWT.log( "" + initialObject.get( key ).isArray() );
			    					  
			    					  if( ( initialObject.get( key ).isArray() ) != null )
			    					  {
			    						  JSONArray locationsArray = initialObject.get( key ).isArray();
			    						  
			    						  for( int i = 0; i < locationsArray.size(); i ++ )
			    						  {
				    						  JSONValue locationsArrayValue = locationsArray.get( i );
				    						  
				    						  //GWT.log( "Location Object : " + locationsArrayValue.toString() );
				    						  
				    						  JSONValue locationsArrayObjects = JSONParser.parseStrict( locationsArrayValue.toString() );
				    						  
				    						  if( locationsArrayObjects.isObject() != null )
				    						  {
				    							  Location location = new Location();
				    							  
				    							  JSONObject contentOject = locationsArrayObjects.isObject();
				    							  
								    			  Set<String> contentKeys = contentOject.keySet();
								    			  
								    			  for( String contentKey : contentKeys )
								    			  {
								    				  if( "formattedAddress".equals( contentKey ) )
								    				  {
								    					  JSONValue str = contentOject.get( contentKey );
								    					  JSONString jsonStr = str.isString();
								    					  
								    					  //GWT.log( "Formatted Address : " + jsonStr.toString() );
								    					  location.setFormattedAddress( jsonStr.toString() );
								    				  }
								    			  }
								    			  locations.add( location );
				    						  }
			    						  }
			    					  }
			    				  }
			    			  }
				    	  }
				      } 
				      else 
				      {
				    	  GWT.log( "HTTP request failed" );
				      }
				      
				      LocationFoundEvent event = new LocationFoundEvent();
				      event.setLocations( locations );
				      
				      eventBus.fireEvent( event );
				}
	
				public void onError( Request request, Throwable exception ) 
				{
					GWT.log( exception.getMessage() );
				}
				
			});
		}
		catch (com.google.gwt.http.client.RequestException e) 
		{
			GWT.log( e.getMessage() );
		}

		for( Location location : locations )
		{
			GWT.log( "Formatted Address : " + location.getFormattedAddress() );
		}

		return locations;
	}
	
	public String removePreTags( String tagStr )
	{
		String strippedStr = tagStr;
		
		try
		{
			Integer firstGtr = strippedStr.indexOf( ">" );
			Integer lastGtr  = strippedStr.lastIndexOf( "<" );
			
			strippedStr = strippedStr.substring( firstGtr + 1, lastGtr );
			GWT.log( strippedStr );
		}
		catch( Exception e )
		{
			GWT.log( e.getMessage() );
			strippedStr = tagStr;
		}
		
		return strippedStr;
	}
	
	private Widget createAdvancedForm( List<Location> locations ) 
	{
	    // Create a table to layout the form options
	    FlexTable layout = new FlexTable();
	    //layout.setCellSpacing(6);
	    //layout.setWidth("300px");
	    
	    layout.addStyleName( "popupFlexTable" );
	    
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        Button button = new Button( "", new ClickHandler() 
											{
												public void onClick(ClickEvent event) 
												{
													display.getPopupPanel().hide();
												}
											} 
        						  );
        
        button.addStyleName( "popupCloseButton" );

	    cellFormatter.setColSpan( 0, 0, 2);
	    cellFormatter.setHorizontalAlignment( 0, 0, HasHorizontalAlignment.ALIGN_RIGHT );

        layout.setWidget( 0, 0, button );

	    //Add a title to the form
	    layout.setHTML( 1, 0, "Search Results" );
	    cellFormatter.setColSpan(1, 0, 2);
	    cellFormatter.setHorizontalAlignment( 1, 0, HasHorizontalAlignment.ALIGN_CENTER );
	    
	    for( int i = 0; i < locations.size(); i++ )
	    {
	    	Location location = locations.get( i );
	    	
		    // Add advanced options to form in a disclosure panel
		    //DisclosurePanel advancedDisclosure = new DisclosurePanel( location.getFormattedAddress() );
		    //advancedDisclosure.setAnimationEnabled(true);
	    	Anchor locationDetailsHyperlink = new Anchor();
	    	//locationDetailsHyperlink.setHTML( location.getFormattedAddress() );
	    	locationDetailsHyperlink.setHTML( "Suman Kumar Kandikonda" );
	    	locationDetailsHyperlink.addStyleName( "searchResultsHyperlink" );
	    	
	    	locationDetailsHyperlink.addClickHandler( 	new ClickHandler() 
	    												{
															public void onClick(ClickEvent event) 
															{
																Window.alert( "Hyperlink Clicked!" );
															}
	    												}
	    											);
		    //Label locationLabel = new Label( location.getFormattedAddress() );
		    layout.setWidget( i + 2, 0, locationDetailsHyperlink );
	    }
	    
	    // Wrap the contents in a DecoratorPanel
	    DecoratorPanel decPanel = new DecoratorPanel();
	    
	    decPanel.setStyleName( "popupDecorationPanel" );
	    
	    //decPanel.addStyleName( "popupDecorationPanel" );
	    decPanel.setWidget( layout );
	    return decPanel;
	}
	
	public void go( HasWidgets container ) 
	{
		container.add( display.asWidget() );
	}
}
