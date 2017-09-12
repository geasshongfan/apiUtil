package json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangjg
 *
 */
public class JacksonUtil {
	public static Boolean beautifier = false;

	private ObjectMapper mapper = new ApiObjectMapper2();
	
	public JacksonUtil() {
	}

	public ObjectMapper getMapper(){
		return this.mapper;
	}
	
	public String objToJson(Object obj){
		try {
			if(beautifier){
				return this.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			}else{
				return this.getMapper().writeValueAsString(obj);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public Object jsonToObj(String jsonStr, Class<?> cls) {
		if (jsonStr == null || cls==null){
			return null;
		}
		try{
			return this.getMapper().readValue(jsonStr, cls);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String objToPrettyJson(Object obj){
		try {
			String json = this.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			return json;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> List<T> jsonToList(String jsonVal, Class<?> clazz) {
		TypeFactory t = TypeFactory.defaultInstance();
		try{
			List<T> list = this.getMapper().readValue(jsonVal,t.constructCollectionType(ArrayList.class, clazz));
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map<?,?> jsonToMap(String jsonVal) {
		try{
			Map<?,?> map = this.getMapper().readValue(jsonVal, Map.class);
			return map;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public Map<String,?> jsonToMap2(String jsonVal) {
		try{
			Map<String,?> map = this.getMapper().readValue(jsonVal, new TypeReference<Map<String , Object>> (){} );
			return map;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("all")
	public List jsonToMapList(String jsonVal){
		try{//TypeReference
			List<LinkedHashMap<?, ?>> list = this.getMapper().readValue(jsonVal, List.class);
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String args[]){
		String s = "";
	}
	
}

