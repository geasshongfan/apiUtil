package json;


import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonSerializer {
	private JsonMapper jsonMapper = new JsonMapper() ;
	public JsonSerializer(){
		
	}
	public  String toJson( Object object ){
		if( object == null ){
			object = new Object() ;
		}
		String json = null ;
		try {
			json = jsonMapper.writeValueAsString( object ) ;
		} catch (JsonProcessingException e) {
		}
		return json ;
	}
	public <T> JsonSerializer exclude( Class<T> clazz ,  String ... properties ){
		jsonMapper.filterSerializationProperties( properties , false , clazz ) ;
		return this ;
	}
	public <T> JsonSerializer include( Class<T> clazz , String ... properties ){
		jsonMapper.filterSerializationProperties( properties , true , clazz ) ;
		return this ;
	}
	public JsonSerializer filterNullValues(){
		jsonMapper.excludeNullValues() ;
		return this ;
	}
	public JsonSerializer convertNullValuesToEmpty(){
		jsonMapper.convertNullValueProperties() ;
		return this ;
	}
	public <T>  T fromJson( String json , Class<T> clazz ){
		if(json == null || clazz == null ){
			return null ;
		}
		T obj = null ;
		try {
			obj = jsonMapper.readValue(json, clazz ) ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return obj ;
	}
	
}
