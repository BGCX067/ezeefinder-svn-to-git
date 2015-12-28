package org.traveller.easyfinder.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.traveller.easyfinder.server.pojo.Result;
import org.traveller.easyfinder.server.util.CommonUtil;
import org.traveller.easyfinder.shared.Location;

@Controller
public class SearchController 
{
	private static final Logger logger = Logger.getLogger( SearchController.class.getName() );

	@RequestMapping( value = "/ApplicationLauncher.do" )
	public String launchApplication() 
	{
		logger.info( "SearchController --> launchApplication" );
		
		return "ApplicationLauncher";
	}
	
	@RequestMapping( value = "/searchLocation.do" )
	public /*@ResponseBody Result*/ void searchLocations( @RequestParam String searchString, final HttpServletResponse response ) 
	{
		logger.info( "SearchController --> searchLocations" );
		
		try 
		{
			response.setContentType( "application/json" );
			
			logger.info( "Search String Value : " + searchString );
			//response.getWriter().write( "Search Successful ..." );
			List<Location> locations = CommonUtil.parseJSONResponse( CommonUtil.getGeoCodeForSearchKey( searchString ) );
			
			for( Location location : locations )
			{
				logger.info( "Formatted Address 	: " + location.getFormattedAddress() );
				logger.info( "Langitude 			: " + location.getLangitude() );
				logger.info( "Lattitude 			: " + location.getLattitude() );
			}
			
			getLocationAsJson( locations, response );
		}
		catch( Exception e ) 
		{
			e.printStackTrace();
		}
		
		//return ( new Result() ); 
	}
	
	public void getLocationAsJson( List<Location> locations, final HttpServletResponse response ) 
	{
		logger.info( "Called LocationServiceServlet --> getLocationAsJson" );
		
		try 
		{
			logger.info( "Content Type : " + response.getContentType() );
			
			PrintWriter out = response.getWriter();
			
			out.println( "{" + "\"resultSetSize\":\" " + locations.size() + "\","  );
			
			if( locations == null || locations.isEmpty() )
			{
				out.println("\"locations\":[");
				out.println("  {");
				out.print("    \"formattedAddress\": \"");
				out.print("No match found");
				out.println("\",");
				out.print("    \"longitude\": ");
				out.print("-1");
				out.println(',');
				out.print("    \"latitude\": ");
				out.println("-1");
				out.println("  }");
				out.println(']');
			}
			else
			{
				int i = 0;
				for( Location location : locations )
				{
					if( i == 0 )
					{
						out.println("\"locations\":[");
						out.println("  {");
						out.print("    \"formattedAddress\": \"");
						out.print(location.getFormattedAddress());
						out.println("\",");
						out.print("    \"longitude\": ");
						out.print("" + location.getLangitude());
						out.println(',');
						out.print("    \"latitude\": ");
						out.println("" + location.getLattitude());
						out.println("  }");
					}
					else
					{
						out.println(",  {");
						out.print("    \"formattedAddress\": \"");
						out.print(location.getFormattedAddress());
						out.println("\",");
						out.print("    \"longitude\": ");
						out.print("" + location.getLangitude());
						out.println(',');
						out.print("    \"latitude\": ");
						out.println("" + location.getLattitude());
						out.println("  }");
					}
					i++;
				}
				out.println(']');
			}
			
			out.println( "}" );
		    out.flush();
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
		}
	}
	
	/*
	private Result getLocationAsJson( List<Location> locations, final HttpServletResponse response ) 
	{
		logger.info( "Called SearchController --> getLocationAsJson not using Jackson API" );
		
		Result result = new Result();
        
        result.setLocations( locations );
        
        return result;
	}
	*/
	/*
	private void getLocationAsJson( List<Location> locations, final HttpServletResponse response ) 
	{
		logger.info( "Called SearchController --> getLocationAsJson" );
		
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        
        MediaType jsonMimeType = MediaType.ALL;
        
        Result result = new Result();
        
        result.setLocations( locations );
		
        if( jsonConverter.canWrite( result.getClass(), jsonMimeType ) ) 
		{
			try 
			{
				jsonConverter.write( result, jsonMimeType, new ServletServerHttpResponse( response ) );
			} 
			catch( IOException ioe ) 
			{
				ioe.printStackTrace();
			} 
			catch( HttpMessageNotWritableException e ) 
			{
				e.printStackTrace();
			}
        }
	}
	*/
	
}
