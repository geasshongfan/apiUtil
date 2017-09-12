package utils;

import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjigang
 */
public class OkHttpRequest2 {
	private static final Logger logger = LoggerFactory.getLogger( OkHttpRequest2.class) ;
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private OkHttpClient client ;
	
	private Integer connectTimeout;
	private Integer readTimeout;
	private Integer writeTimeout;

	public OkHttpRequest2() {
	}
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setWriteTimeout(int writeTimeout) {
		this.writeTimeout = writeTimeout;
	}

	public OkHttpClient client(){
		if(this.client==null){
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			try {
				builder.sslSocketFactory(SSLUtils.get(), new EasyX509TrustManager());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(connectTimeout!=null){
				builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
			} 
			if(readTimeout!=null){
				builder.readTimeout(readTimeout, TimeUnit.SECONDS);
			}  
			if(writeTimeout!=null){
				builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
			}  
			this.client = builder.build();
		}
		return this.client;
	}

	public String get(String url, Map<String,String>params) throws IOException {
		if(params!=null){
			String qs = getQueryString(params);
			url = url + "?"+ qs;
		}
		Request request = new Request.Builder().url(url).get().build();
		try (Response response = client().newCall(request).execute()) {
			return respString(response);
		}
	}

	public String get(String url, Map<String,String>params,boolean isEncode) throws IOException {
		String qs =null;
		if(isEncode){
			qs = getQueryString(params);
		}else{
			qs=getQueryString(params,isEncode);
		}
		url = url + "?"+ qs;
		Request request = new Request.Builder().url(url).get().build();
		try (Response response = client().newCall(request).execute()) {
			return respString(response);
		}
	}
	
	public String post(String url, Map<String,Object>params) throws IOException {
		return post(url, null, params);
	}
	
	public String post(String url, Map<String,String>headerMap, Map<String,Object>params) throws IOException {
		MultipartBody.Builder mbuilder = new MultipartBody.Builder();
		boolean haveFile = false;
		if(params!=null){
			for(Entry<String, Object> entry:params.entrySet()){
				String name = entry.getKey();
				Object value = entry.getValue();
				if(value instanceof String){
					mbuilder.addFormDataPart(name,value.toString());
				}
			}
			for(Entry<String, Object> entry:params.entrySet()){
				String name = entry.getKey();
				Object value = entry.getValue();
				if(value instanceof File){
					File file = (File) value;
					MediaType type = null;
					mbuilder.addFormDataPart(name, file.getName(), RequestBody.create(type, file));
					haveFile = true;
				}else if(value instanceof byte[]){
					byte[] buf = (byte[])value;
					MediaType type = MediaType.parse("");
					mbuilder.addFormDataPart(name, "tmp", RequestBody.create(type, buf));
					haveFile = true;
				}else if(value!=null && InputStream.class.isAssignableFrom(value.getClass())){
					OkInputStreamBody inBody = new OkInputStreamBody((InputStream)value);
					mbuilder.addFormDataPart(name, 
							params.get("filename")==null?"tmp":params.get("filename").toString()
							, inBody);
					haveFile = true;
				}
			}
			if(haveFile){
				mbuilder.setType(MultipartBody.FORM);
			}
		}
		
		Request.Builder reqBuilder  = new Request.Builder().url(url);
		if(headerMap!=null){
			for(Entry<String, String> entry:headerMap.entrySet()){
				reqBuilder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		Request request = reqBuilder.post(mbuilder.build()).build();
		
		try (Response response = client().newCall(request).execute()) {
			return respString(response);
		}
	}
	
	public String postF0rm(String url, Map<String,String>headerMap, Map<String,String>params) throws IOException {
		FormBody.Builder form = new FormBody.Builder();
		if(params!=null){
			for(Entry<String, String> entry:params.entrySet()){
				form.add(entry.getKey(), entry.getValue());
			}
		}
		
		Request.Builder reqBuilder  = new Request.Builder().url(url);
		if(headerMap!=null){
			for(Entry<String, String> entry:headerMap.entrySet()){
				reqBuilder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		Request request = reqBuilder.post(form.build()).build();
		
		try (Response response = client().newCall(request).execute()) {
			return respString(response);
		}
	}

	public String postJson(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client().newCall(request).execute()) {
			return respString(response);
		}
	}
	
	private String respString(Response response) throws IOException{
		logger.debug(response.toString());
		InputStream input = response.body().byteStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		IOUtils.copy(input, output);
		String html =  new String(output.toString("utf-8"));
		logger.debug(html);
		return html;
	}

	private SSLContext createEasySSLContext() throws IOException {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { new EasyX509TrustManager() }, null);
			return context;
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}
	
	private String getQueryString(Map<String,String> params) throws UnsupportedEncodingException{
		if(params==null || params.size()==0){
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry:params.entrySet()){
			String key = entry.getKey();
			String val = entry.getValue();
			if(key==null||key.isEmpty()||val==null||val.isEmpty()){
				continue;
			}
			sb.append(key);
			sb.append('=');
			sb.append(URLEncoder.encode(val, "utf-8"));
			sb.append('&');
		}
		
		if(sb.length()>0){
			return sb.substring(0, sb.length()-1);
		}

		return sb.toString();
	}

	private String getQueryString(Map<String,String> params,boolean isEncode) throws UnsupportedEncodingException{
		if(params==null || params.size()==0){
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry:params.entrySet()){
			String key = entry.getKey();
			String val = entry.getValue();
			if(key==null||key.isEmpty()||val==null||val.isEmpty()){
				continue;
			}
			sb.append(key);
			sb.append('=');
			if(isEncode){
				sb.append(URLEncoder.encode(val, "utf-8"));
			}else{
				sb.append(val);
			}
			sb.append('&');
		}

		if(sb.length()>0){
			return sb.substring(0, sb.length()-1);
		}

		return sb.toString();
	}
	
	
	public static void main(String args[]) throws IOException{
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("appId", "111");
//		params.put("moduleId", "222");
//		params.put("objectId", "333");
//		params.put("file", new File("D:/temp/tmp/icon.jpg"));
//		String s= OkHttpRequest.post("http://localhost:8080/fs/upload.xhtml", params);
//		System.out.println(s);
		
		System.out.println(new OkHttpRequest2().get("http://www.bilibili.com/index", null));
	}

}
