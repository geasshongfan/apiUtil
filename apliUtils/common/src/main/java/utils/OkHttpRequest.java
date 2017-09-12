package utils;

import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author wangjigang
 */
public class OkHttpRequest {
	private static final Logger logger = LoggerFactory.getLogger( OkHttpRequest.class) ;
	private static OkHttpClient client ;

	static{
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		try {
			builder.sslSocketFactory(SSLUtils.get(), new EasyX509TrustManager());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		client = builder.build();
	}

	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	public static String get(String url, Map<String,String>params) throws IOException {
		String qs = getQueryString(params);
		url = url + "?"+ qs;
		Request request = new Request.Builder().url(url).get().build();
		try (Response response = client.newCall(request).execute()) {
			return respString(response);
		}
	}
	
	public  HttpResponse get(
			String url, Map<String,String>params , Map<String , String> headers ) throws IOException {
		if( params != null && !params.isEmpty() ){
			url = url + "?"+ getQueryString(params) ;
		}
		Request request = header( new Request.Builder().url( url )  , headers ).get().build() ;
		try ( Response response = client.newCall(request).execute() ) {
			return new OkHttpRequest.HttpResponse( response ) ;
		}
	}
	
	public class HttpResponse{
		private int status ;
		private String body ;
		public HttpResponse( int status  ){
			this.status = status ;
		}
		public HttpResponse( int status , ResponseBody body ){
			this.status = status ;
			this.setBody( body );
		}
		public HttpResponse( Response response ){
			this.status = response.code() ;
			this.setBody( response.body() );
		}
		public HttpResponse( ){
			
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getBody() {
			return body;
		}
		public void setBody( ResponseBody body ) {
			if( body == null ){
				return ;
			}
			InputStream input = body.byteStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				IOUtils.copy(input, output);
				this.body = new String(output.toString("utf-8"));
			} catch (IOException e) {
			
			}
		}
	}
	private static Request.Builder header( Request.Builder builder , Map<String , String> headers ){
		if( headers == null || headers.isEmpty() ){
			return builder ;
		}
		for( Entry<String , String> head : headers.entrySet() ){
			builder.header( head.getKey() , head.getValue() );
		}
		return builder ;
	}
	
	public static String post(String url, Map<String,Object>params) throws IOException {
		return post(url, null, params);
	}
	
	public static String post(String url, Map<String,String>headerMap, Map<String,Object>params) throws IOException {
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
		
		try (Response response = client.newCall(request).execute()) {
			return respString(response);
		}
	}
	
	public static String postF0rm(String url, Map<String,String>headerMap, Map<String,String>params) throws IOException {
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
		
		try (Response response = client.newCall(request).execute()) {
			return respString(response);
		}
	}

	public static String postJson(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client.newCall(request).execute()) {
			return respString(response);
		}
	}
	
	private static String respString(Response response) throws IOException{
		logger.debug(response.toString());
		InputStream input = response.body().byteStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		IOUtils.copy(input, output);
		String html =  new String(output.toString("utf-8"));
		logger.debug(html);
		return html;
	}

	private static SSLContext createEasySSLContext() throws IOException {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { new EasyX509TrustManager() }, null);
			return context;
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}
	
	private static String getQueryString(Map<String,String> params) throws UnsupportedEncodingException{
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
	
	
	public static void main(String args[]) throws IOException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "a41c8c9471a42e98590844a1e6c71c6d");
		params.put("com", "yunda");
		params.put("nu", "3932429911000");
		params.put("show", "0");
		params.put("muti", "0");
		params.put("order", "desc");
		//String s= OkHttpRequest.post("http://localhost:8080/fs/upload.xhtml", params);
		//System.out.println(s);
		
		System.out.println(OkHttpRequest.get("http://api.kuaidi.com/openapi.html", params));
	}

}
