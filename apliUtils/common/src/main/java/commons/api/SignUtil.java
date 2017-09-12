package commons.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author wangjigang
 *
 */
public class SignUtil {
	
	/**
	 * md5签名
	 * @param params
	 * @param token 
	 * @return
	 */
	public static String sign_sha1(Map<String, String> params, String token){
        String str = buildQuery(params, token);
		return DigestUtils.sha1Hex(str);
	}
	
	/**
	 * md5签名
	 * @param params
	 * @param token 
	 * @return
	 */
	public static String sign(Map<String, String> params, String token){
        String str = buildQuery(params, token);
		return DigestUtils.md5Hex(str);
	}

	public static int verify(Map<String, String> params, String token) {
		long timestamp = 0;
		String t = params.get("timestamp");
		if(t!=null){
			timestamp = Long.parseLong(t);
			if(Math.abs(timestamp-System.currentTimeMillis())>1000*3600){
				return 2;
			}
		}
		String sign = params.get("sign");
		String sign2 = sign(params, token);
		
		if(sign2.equals(sign)){
			return 0;
		}
		return 1;
	}

	/**
	 * parameterMap.value是数组
	 * @param parameterMap
	 * @param appSecret
	 * @return
	 */
	public static String sign2(Map<String, String[]> parameterMap, String appSecret){
		Map<String, String> params = getParamMap(parameterMap);
		return sign(params, appSecret);
	}
	
	/**
	 * parameterMap.value是数组
	 * @param parameterMap
	 * @param appSecret
	 * @return
	 */
	public static int verify2(Map<String, String[]> parameterMap, String appSecret){
		Map<String, String> params = getParamMap(parameterMap);
		return verify(params, appSecret);
	}
	
	public static String buildQuery(Map<String, String> params, String token){
	    List<String> names = new ArrayList<String>(params.keySet());
	    Collections.sort(names);
	    
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i<names.size(); i++) {
	        String key = names.get(i);
	        String value = params.get(key);
	        
	        if (  value == null 
	        		|| value.isEmpty()
	        		||key==null
	         		||key.isEmpty()
	        		|| key.equalsIgnoreCase("sign")
	        		|| key.equalsIgnoreCase("sign_type")) {
	                continue;
	        }
	
	        sb.append(key);
	    	sb.append('=');
	    	sb.append(value);
	    	
	    	 if (i < names.size() - 1) {
	    		 sb.append('&');
	    	 }
	    }
	    
	    sb.append(token);
	    
		return sb.toString();
	}

	static Map<String, String> getParamMap(Map<String, String[]> parameterMap){
		Map<String, String> params = new HashMap<String,String>();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for(Entry<String, String[]> entry:entrySet){
			String key = entry.getKey();
			String[] value = entry.getValue();
			if(key!=null && value!=null && value.length!=0){
				params.put(key, StringUtils.join(value, ','));
			}
		}
		return params;
	}

	public static void main(String args[]){
		long t = System.currentTimeMillis();
		Map<String, String> params = new HashMap<String, String>();
		params.put("app_id", "app_id");
		params.put("method", "method");
		params.put("format", "format");
		params.put("v", "1.0");
		params.put("timestamp", ""+t);
		
		params.put("p1", "1");
		params.put("p2", "2");
		
//		String s = sign(params, "tnon4hdjryold5gkrz1z289iv3f6l46y");
		String s = sign(params, "fdti9x6qfsmwlDs6ppsnqxI;pax'uokc");
		System.out.println(t);
		System.out.println(s);
		 
	}
	
}
