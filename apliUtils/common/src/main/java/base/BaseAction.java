package base;

import commons.date.MyDateEditor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.BeanHelper;
import utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * @author wangjg
 *
 */
public class BaseAction {

	protected <T> T currentUser(){
		return null;
	}

	protected void bindParams(Object bean){
		Map<String, String> params = this.getParameterMap();
		bindParams(bean, params, null, null);
	}
	
	protected void bindParams(Object bean, String...exclued){
		Map<String, String> params = this.getParameterMap();
		bindParams(bean, params, null, exclued);
	}
	
	@SuppressWarnings("rawtypes")
	protected static BindException bindParams(Object bean , Map map, String[] allowedFields ,String[] disallowedFields){
		DataBinder binder = new DataBinder(bean);
		if(allowedFields!=null && allowedFields.length>0){
			binder.setAllowedFields(allowedFields);
		}
		if(disallowedFields!=null && disallowedFields.length>0){
			binder.setDisallowedFields(disallowedFields);
		}
		MutablePropertyValues mpvs = new MutablePropertyValues(map);
		binder.bind(mpvs);
		BindException errors = new BindException(binder.getBindingResult());
		return errors;
	}

	protected Map<String, String> getParameterMap() {
		return WebUtil.getParameterMap(this.getRequest());
	}

	protected HttpServletRequest getRequest(){
		ServletRequestAttributes holder = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = holder.getRequest();
		return request;
	}

	protected String getSuccessResult(){
		return this.getResult(0, null, null);
	}
	
	protected String getSuccessResult(Object data){
		return this.getResult(0, null, data);
	}
	
	protected String getFailureResult(String message){
		return this.getResult(1, message, null); 
	}
	
	protected String getFailureResult(int code, String message){
		return this.getResult(code, message, null); 
	}

	/**
	 * 统一处理，还可以根据客户端类型做不同的处理。
	 * @return
	 */
	protected String getResult(int code, String message, Object data){
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("code", code);
		if(message!=null){
			result.put("message", message);
		}
		if(data!=null){
			result.put("data", data);
		}
		this.getRequest().setAttribute("jsonMap", result);
		return "common/json";
	}

	protected <O> List<O> filter(List<O> beanList, String...pps){
		for(Object bean:beanList){
			this.filter(bean, pps);	
		}
		return beanList;
	}
	
	protected Object filter(Object bean, String...pps){
		for(String pp:pps){
			try {
				BeanUtils.setProperty(bean, pp, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
	
	protected <O> List<O> include(List<O> beanList, String...properties){
		List<O> result = new ArrayList<O>();
		for(O bean:beanList){
			O newBean = this.include(bean, properties);
			result.add(newBean);
		}
		return result;
	}
	
	protected <O> O include(O bean, String...properties){
		return BeanHelper.beancopy(bean, properties);
	}
	
	protected String getContextPath(){
		return this.getRequest().getContextPath();
	}
	
	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new MyDateEditor());
	}

}
