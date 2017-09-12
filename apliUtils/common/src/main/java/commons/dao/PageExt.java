package commons.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangjigang
 *
 * @param <T>
 */
	@SuppressWarnings("rawtypes")
public class PageExt extends Page {
	private static final long serialVersionUID = 1L;

	private List data;
	
	private Map<String,Object> attributes;

	@SuppressWarnings("unchecked")
	public <T> List<T> getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public <V> V getAttribute(String name) {
		if(this.attributes==null){
			return null;
		}
		return (V) this.attributes.get(name);
	}

	public void setAttribute(String name, Object value) {
		if(this.attributes==null){
			this.attributes = new HashMap<String,Object>();
		}
		this.attributes.put(name, value);
	}
	
	
	
}
