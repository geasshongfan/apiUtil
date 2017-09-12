package commons.api;

import json.JacksonUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangjigang
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ResponseParser {
	
	public static final int TYPE_MAP = 0;
	public static final int TYPE_BEAN = 1;
	public static final int TYPE_MAPLIST = 2;
	public static final int TYPE_BEANLIST = 3;
	public static final int TYPE_PRIMITIVE = 4;

	public ResponseResult toMap(String json){
		Map  map = new JacksonUtil().jsonToMap(json);
		ResponseResult result = new ResponseResult(map);
		return result;
	}
	
	public ResponseResult toValue(String json){
		return toMap(json);
	}

	public ResponseResult toBean(String json, Class beanClass){
		Map map = new JacksonUtil().jsonToMap(json);
		ResponseResult result = new ResponseResult(map);

		try {
			Object bean = beanClass.newInstance();
			BeanUtils.populate(bean, (Map)map.get("data"));
			result.setData(bean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public ResponseResult toBeanList(String json, Class beanClass){
		Map map = new JacksonUtil().jsonToMap(json);
		ResponseResult result = new ResponseResult(map);

		List<Map> list = (List<Map>)map.get("data");
		List beanList = new ArrayList();
		for(Map map1:list){
			try {
				Object bean = beanClass.newInstance();
				BeanUtils.populate(bean, map1);
				beanList.add(bean);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		result.setData(beanList);
		
		return result;
	}
	
	public static  void main(String args[]){
		String json = "{\"code\":\"200\",\"data\":{\"code\":201,\"message\":\"5.5.19-log\"}}";
		ResponseParser  rp = new ResponseParser();
		ResponseResult rr = rp.toBean(json, ResponseResult.class);
		
		System.out.println(rr);
//		System.out.println(rp.toMap(json));
	}
	
}
