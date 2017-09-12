package json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import commons.date.DateFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * API专用,用于序列化JSON对象
 * 本项目所有的json使用此对象序列化和反序列化
 * @author wangjg
 */
public class ApiObjectMapper2 extends ObjectMapper {
	private static final long serialVersionUID = 1L;
	
	private String dateFormat = DateFormat.FORMAT_DATE_TIME;
	private boolean ignoreNull = true;
	private boolean undefined = true;
	private String[] allowedFields;
	private String[] disallowedFields;

	public ApiObjectMapper2() {
		this.config1();
		this.config2();
	}
	
	private void config2(){
		//---------------
		if(allowedFields!=null && allowedFields.length>0){
			SimpleBeanPropertyFilter filter = null ;
			filter = SimpleBeanPropertyFilter.filterOutAllExcept(allowedFields);
			FilterProvider filterProvider = new SimpleFilterProvider().addFilter("allowedFields", filter);  
			this.setFilters(filterProvider);
		}
		if(disallowedFields!=null && disallowedFields.length>0){
			SimpleBeanPropertyFilter filter = null ;
			filter = SimpleBeanPropertyFilter.serializeAllExcept(disallowedFields);
			FilterProvider filterProvider = new SimpleFilterProvider().addFilter("allowedFields", filter);  
			this.setFilters(filterProvider);
		}
		//---------------
		if(undefined){
			JsonSerializer<Object> nullVs = new JsonSerializer<Object>(){
				public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,JsonProcessingException {
					jgen.writeRawValue("");
				}
				
			};
			this.getSerializerProvider().setNullValueSerializer(nullVs);
		}
		if(this.dateFormat!=null){
			this.setDateFormat(new SimpleDateFormat(dateFormat));
		}
	}

	private void config1() {
		this.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//this.setSerializationInclusion(JsonInclude.Include.ALWAYS );
//		this.registerModule(module);
	}

}