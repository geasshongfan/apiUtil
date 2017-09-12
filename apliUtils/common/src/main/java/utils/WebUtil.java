package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangjg
 */
public class WebUtil {
	
	public static Map<String,String> getHeaders(HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		Enumeration<String> names = request.getHeaderNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			result.put(name, request.getHeader(name));
		}
		return result;
	}
	
	public static void setCacheExpireDate(HttpServletResponse response) {
		setCacheExpireDate(response, Integer.MAX_VALUE);
	}
	
	public static void setCacheExpireDate(HttpServletResponse response, int seconds) {
		if (response != null) {
			response.setHeader("Cache-Control", "PUBLIC, max-age=" + seconds + ", must-revalidate");
			
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.SECOND, seconds);
			response.setHeader("Expires", htmlExpiresDateFormat().format(cal.getTime()));
		}
	}
	
	public static void disableCache(HttpServletResponse response){
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
	}

	private static DateFormat htmlExpiresDateFormat() {
		DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return httpDateFormat;
	}
	
	
	public static String getCookieValue(HttpServletRequest request, String cookieName){
		if(cookieName==null){
			return null;
		}
		if(request==null){
			throw new RuntimeException("request==null");
		}
		Cookie[] cookies = request.getCookies();
		if(cookies==null||cookies.length==0){
			return null;
		}
		for(int i=0;i<cookies.length;i++){
			Cookie cookie = cookies[i];
			String name = cookie.getName();
			if(cookieName.equals(name)){
				return cookie.getValue();
			}
		}
		return null;
	}
	
	public static void addCookie(HttpServletResponse response, String name, String value){
		Cookie cookie = new Cookie(name, value); 
        cookie.setPath("/");
        response.addCookie(cookie); 
	}

	public static void addCookie(HttpServletResponse response, String name, String value, String path, int maxAge){
		Cookie cookie = new Cookie(name, value); 
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);        response.addCookie(cookie);
	}

	protected static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
	}

	public static void setDownloadHeader(HttpServletRequest request, HttpServletResponse response, String filename, String contentType) throws IOException{
		String DOWN_TYPE = "application/x-download";
		if(contentType==null){
			contentType = DOWN_TYPE;
		}
		response.setContentType(contentType);
		if(isIE(request)){
			filename = java.net.URLEncoder.encode(filename, "utf-8");
		}else{
			filename = new String(filename.getBytes("utf-8"), "ISO8859-1");
		}
		if(DOWN_TYPE.equals(contentType)){
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);	
		}
	}

	public static void setImageHeader(HttpServletResponse response, String extName, Integer length) throws IOException{
		response.setContentType("image/"+extName);
		if(length!=null){
			response.setContentLength(length);
		}
	}
	
	private static boolean isIE(HttpServletRequest request){
		String ua = request.getHeader("User-Agent");
		if(ua==null || ua.isEmpty()){
			return false;
		}
		ua = ua.toLowerCase();
		return ua.indexOf("msie")!=-1;
	}
	
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String[]> ms = request.getParameterMap();
		Map<String, String> result = new HashMap<String, String>();
		if (ms == null || ms.size() == 0)
			return result;
		for (Map.Entry<String, String[]> m : ms.entrySet()) {
			result.put(m.getKey(), m.getValue()[0]);
			if(m.getValue().length > 1){
				result.put(m.getKey(), join(m.getValue()));
			}
		}
		return result;
	}
	
	//当参数是多个数组时，组成maplist
	public static List<Map<String, String>> getParameterMapList(HttpServletRequest request, String primaryKey) {
		Map<String, String[]> ms = request.getParameterMap();
		
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		
		String[] values = ms.get(primaryKey);
		for(String val : values){
			Map<String, String> map = new HashMap<String, String>();
			map.put(primaryKey, val);
			maplist.add(map);
		}
		
		for (Map.Entry<String, String[]> m : ms.entrySet()) {
			String name = m.getKey();
			String[] val = m.getValue();
			
			for(int i=0;i<maplist.size();i++){
				Map<String, String> map = maplist.get(i);
				map.put(name, val[i]);
			}
		}
		
		return maplist;
	}
	
	private static String join(String[] ss){
		StringBuilder sb = new StringBuilder();
		for(String s:ss){
			sb.append(s);
			sb.append(',');
		}
		return sb.substring(0, sb.length()-1);
	}
}
