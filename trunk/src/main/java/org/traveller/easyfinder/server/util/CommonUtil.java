package org.traveller.easyfinder.server.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.traveller.easyfinder.shared.Location;

public class CommonUtil 
{
	private static final Logger logger = Logger.getLogger( CommonUtil.class.getName() );
	
	public static String getGeoCodeForSearchKey( String searchQuery )
	{
		String xmlString = "";
		
		try 
		{
			searchQuery = URLEncoder.encode( searchQuery, "UTF-8" );
			URL googleGeocodingAPIURL = new URL( "http://maps.google.com/maps/api/geocode/json?address=" + searchQuery + "&sensor=true" );
			
			BufferedInputStream urlInputStream = new BufferedInputStream( googleGeocodingAPIURL.openStream() );
			
			int CHUNK_SIZE = 4 * 1024;
			
			StringBuffer buffer = new StringBuffer();
			byte [] readBytes = new byte[ CHUNK_SIZE ];
			
			int i = 0;
			while( ( i = urlInputStream.read( readBytes, 0, CHUNK_SIZE ) ) != -1 )
			{
				buffer.append( new String( readBytes, 0, i ) );
			}
			
			System.out.println( buffer.toString() );
			
			xmlString = buffer.toString();
		} 
		catch( MalformedURLException e ) 
		{
			e.printStackTrace();
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
		}
		
		return xmlString;
	}
	
	public static List<Location> parseJSONResponse( String jsonResponseString )
	{
		List<Location> locations = new LinkedList<Location>();
		
		JSONObject jsonObject = JSONObject.fromObject( jsonResponseString );
		Iterator iterator = jsonObject.keys();
		
		while( iterator.hasNext() )
		{
			String key = ( String )iterator.next();
			
			//System.out.println( key );
			
			if( "results".equals( key ) )
			{
				//System.out.println( jsonObject.get( key ) );
				
				if( ( jsonObject.get( key ) instanceof JSONArray ) )
				{
					JSONArray level1JSONObject = JSONArray.fromObject( jsonObject.get( key )  );
					Iterator iterator1 = level1JSONObject.iterator();
					
					//System.out.println( level1JSONObject.size() );
					int index = 0;
					while( iterator1.hasNext() )
					{
						//Level 1 : START
						//String key1 = (String) iterator1.next();
						//System.out.println( key1 );
						Location location = new Location();
						
						JSONObject level2JSONObject = JSONObject.fromObject( iterator1.next() );
						Iterator iterator3 = level2JSONObject.keys();
						while( iterator3.hasNext() )
						{
							String key3 = ( String )iterator3.next();
							//System.out.println( iterator3.next() );
							if( "address_components".equals( key3 ) )
							{
								//System.out.println( level2JSONObject.get( key3 ) );
								//System.out.println( "\n\n" );
							}
							else if( "formatted_address".equals( key3 ) )
							{
								//System.out.println( "Formated Address : [ " + level2JSONObject.get( key3 ) + " ]" );
								//System.out.println( "\n\n" );
								location.setFormattedAddress( "" + level2JSONObject.get( key3 ) );
							}
							else if( "geometry".equals( key3 ) )
							{
								//System.out.println( level2JSONObject.get( key3 ) );
								
								JSONObject level3JSONObject = JSONObject.fromObject( level2JSONObject.get( key3 ) );
								Iterator iterator4 = level3JSONObject.keys();
								
								while( iterator4.hasNext() )
								{
									String key4 = ( String )iterator4.next();
									//System.out.println( key4 );
									
									if( "location".equals( key4 ) )
									{
										//System.out.println( level3JSONObject.get( key4 ) );
										
										JSONObject level4JSONObject = JSONObject.fromObject( level3JSONObject.get( key4 ) );
										Iterator iterator5 = level4JSONObject.keys();
										
										while( iterator5.hasNext() )
										{
											String key5 = ( String )iterator5.next();
											//System.out.println( key5 + " --> " + level4JSONObject.get( key5 ) );
											
											if( "lat".equals( key5 ) )
											{
												location.setLattitude( "" + level4JSONObject.get( key5 ) );
											}
											else if( "lng".equals( key5 ) )
											{
												location.setLangitude( "" + level4JSONObject.get( key5 ) );
											}
										}
									}
								}
								//System.out.println( "\n\n" );
							}
								
						}
						
						location.setSearchResultID( "" + index );
						locations.add( location );
						index++;
						//Level 1 : END
					}
				}
			}
		}
		
		return locations;
	}
}
