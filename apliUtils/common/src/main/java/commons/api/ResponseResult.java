package commons.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wangjigang
 *
 */
public class ResponseResult {

	private Map<String, Object> map;

	public ResponseResult() {
		this.map = new HashMap<String, Object>();
	}

	public ResponseResult(Map<String, Object> map) {
		this.map = map;
	}

	public int getCode() {
		return (Integer) this.map.get("code");
	}

	public void setCode(int code) {
		this.map.put("code", code);
	}

	public String getMessage() {
		return (String) this.map.get("message");
	}

	public void setMessage(String message) {
		this.map.put("message", message);
	}

	public Object getData() {
		return this.map.get("data");
	}

	@SuppressWarnings("unchecked")
	public <T> T getDataObject() {
		return (T) this.map.get("data");
	}

	public void setData(Object data) {
		this.map.put("data", data);
	}

	public void setAttribute(String key, Object data) {
		this.map.put(key, data);
	}

	public Object getAttribute(String key) {
		return this.map.get(key);
	}

	@Override
	public String toString() {
		return "" + map;
	}

}
