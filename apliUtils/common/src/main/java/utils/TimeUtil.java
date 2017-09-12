package utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	private static Logger logger = LoggerFactory.getLogger( TimeUtil.class ) ;
	private static SimpleDateFormat formator = new SimpleDateFormat();
	public static final String DEFAULT_PATTERM = "yyyy-MM-dd HH:mm:ss" ;
	public static final String YEAR_MOTH = "yyyy-MM-dd" ;
	
	public static String currentTime(){
		
		return currentTime( null ) ;
	}
	
	public static String currentTime( String pattern ){
		String tempPattern =  pattern ;
		if( StringUtils.isEmpty( tempPattern ) ) tempPattern = DEFAULT_PATTERM ;
		formator.applyPattern( tempPattern );
		return formator.format( getCurrentDate() ) ;
	}
	
	public static long currentTimes( Date date  ){
		Date temp = date ;
		if( temp == null ) temp = getCurrentDate() ;
		return temp.getTime()  ;
	}
	
	public static Date getCurrentDate(){
		return new Date() ;
	}
	
	public static String add( String time , int count , int field ){
		Date date = string2Date( time , DEFAULT_PATTERM ) ;
		Calendar calendar = Calendar.getInstance() ;
		calendar.setTime( date );
		calendar.add( field , count );
		return format( calendar.getTime() , DEFAULT_PATTERM ) ;
	}
	
	public static Date string2Date( String source , String pattern ){
		formator.applyPattern( pattern );
		Date date = null ;
		try {
			date = formator.parse( source ) ;
		} catch (ParseException e) {
			logger.error("TimeUtils has error , string to date is fail ... " , e );
		}
		return date ;
	}
	
	public static String format( Date source , String pattern ){
		if(StringUtils.isEmpty( pattern ) ){
			pattern = DEFAULT_PATTERM;
		}
		if( source == null ) return currentTime( pattern ) ;
		formator.applyPattern( pattern );
		return formator.format( source ) ;
	}
	
	public static String format( Date source  ){
		if( source == null ) return "" ;
		formator.applyPattern( DEFAULT_PATTERM );
		return formator.format( source ) ;
	}
	
	public static String format( String source , String sourcePattern , String targetPattern ){
		if( StringUtils.isEmpty( source )  ) {
			return null ;
		}
		if( StringUtils.isEmpty( sourcePattern ) ) {
			sourcePattern = DEFAULT_PATTERM ;
		}
		if( StringUtils.isEmpty( targetPattern ) ){
			targetPattern = DEFAULT_PATTERM ;
		}
		return  format( string2Date( source , sourcePattern ) , targetPattern ) ;
	}
	
	public static Date sumDate(Date source,Integer dayNum){
		if( source==null||dayNum==null){
			return null;
		}
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.DATE, dayNum);
		return calendar.getTime();
	}
	/**
	 * 计算 minute 分钟后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	public static Date addTime( Date date , int num , int type ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add( type , num );
		return calendar.getTime() ;
	}
}
