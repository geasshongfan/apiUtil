package com.apli.common.property;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class SystemProperties extends PropertyPlaceholderConfigurer{
	
	private static Properties properties ;
	
	public static String getProperty( String name , String def ){
		if( properties == null || properties.get( name ) == null ){
			return def ;
		}
		return properties.getProperty( name ).trim() ;
	}
	
	public static int getProperty( String name , int def ){
		String property = getProperty( name , null )  ;
		if( property == null ){
			return def ;
		}
		int rs = def ;
		try{
			rs = Integer.parseInt( property.trim() ) ;
		}catch(Exception e){
			rs = def ;
		}
		return rs ;
	}
	
	public static long getProperty( String name , long def ){
		String property = getProperty( name , null )  ;
		if( property == null ){
			return def ;
		}
		long rs = def ;
		try{
			rs = Long.parseLong( property.trim() ) ;
		}catch(Exception e){
			rs = def ;
		}
		return rs ;
	}
	
	public void setLocation( Resource location ){
		if( location == null ){
			throw new IllegalArgumentException("location is not found !") ;
		}
		this.setLocations( new Resource[]{ location } );
	}
	
	public void setLocations( Resource ... locations ){
		super.setLocations(locations);
		try {
			SystemProperties.properties = super.mergeProperties() ;
		} catch (IOException e) {
			logger.error("merge properties error ... " +e.getMessage() );
		}
	}
	
}
