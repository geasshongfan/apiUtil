package base;

import commons.dao.RemotePage;
import constant.CommonConstant;
import exception.BizBaseException;
import json.JacksonUtil;
import json.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import utils.BeanHelper;
import utils.CommonUtil;
import utils.Reflections;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class BaseApiAction extends BaseBizAction{
	
	public Logger logger = LoggerFactory.getLogger(getClass()) ;
	
	private JsonSerializer jsonSerializer ;
	private String [] includeFields ;
	private String [] excludeFields ;
	
	private ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();
	
	@Resource(name = "messageSource")
	private MessageSource validateMessageResouce;
	/**
	 * 非业务性异常统一处理.
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ResultModal> runtimeExceptionHandler(RuntimeException exception) {
		logger.error("global.internet.server.error", exception);
		StringWriter sw = new StringWriter() ;
		PrintWriter pw = new PrintWriter( sw ) ;
		exception.printStackTrace(pw) ;
		return buildResponseEntity(handleMessage(sw.toString()), null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 参数校验异常处理。
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ResultModal> argumentBindExceptionHandler(MethodArgumentNotValidException exception) {
		BindingResult bindResult = exception.getBindingResult();
		List<FieldError> fieldErrors = bindResult.getFieldErrors();
		String fieldError = fieldErrors.get( 0 ).getDefaultMessage()  ;
		logger.error(fieldError, exception);
		return buildResponseEntity(handleMessage( fieldError ), null , HttpStatus.BAD_REQUEST);
	}

	/**
	 * 所有@RequestMapping方法执行前执行此方法.
	 * 
	 * @param includeFields	当前请求客户端动态返回包含字段
	 * @param excludeFields 当前请求客户端动态返回过滤字段
	 */
	@ModelAttribute
	public void preMethod( 
			HttpServletRequest request , HttpServletResponse response ,
			@RequestHeader(value="Response-Fields-Include" , required=false ) String includeFields ,
			@RequestHeader(value="Response-Fields-Exclude" , required=false ) String excludeFields ){
		//设置返回体包含属性
		this.includeFields = explode( includeFields , "," ) ;
		//设置返回体过滤属性
		this.excludeFields = explode( excludeFields , "," ) ;
		
		localRequest.set( request );
	}
	
	private String [] explode( String source , String spec ){
		String [] properties = null ;
		if( source == null || source.trim().equals("") ){
			return null ;
		}
		String [] fields = source.split(spec) ;
		List<String> fieldList = new ArrayList<String>() ;
		for( String field : fields ){
			String temp = field.trim() ;
			if( !temp.equals("") ){
				fieldList.add( temp ) ;
			}
		}
		properties = new String [ fieldList.size() ] ;
		for( int i = 0 ; i < properties.length ; i ++ ){
			properties[ i ] = fieldList.get( i ) ;
		}
		return properties ;
	}
	
	
	
	/** 
	 * 业务性异常统一处理。
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = BizBaseException.class)
	public ResponseEntity<ResultModal> apiExceptionHandler(BizBaseException exception) {
		logger.error(exception.getCode() + ":" + exception.getMessage(), exception);
		return buildResponseEntity(handleMessage(exception), null, HttpStatus.OK);
	}


	/**
	 * 统一返回数据模型封装.
	 * 
	 * @param exception
	 * @return
	 */
	private ResultModal handleMessage(BizBaseException exception) {
		ResultModal codeMessage = new ResultModal(exception.getCode(), exception.getMessage());
		return codeMessage;
	}

	/**
	 * 从配置文件里读取异常处理信息。
	 * 配置文件处理格式：key = code;message
	 * 
	 * @param messageCode 
	 * @param object
	 * @return
	 */
	private ResultModal handleMessage(String messageCode, Object... object) {
		ResultModal codeMessage = new ResultModal();
		try {
			String messageAndCodeStr = validateMessageResouce.getMessage(messageCode, object, null);
			if (messageAndCodeStr.indexOf(";") == -1) {
				codeMessage.setMessage(messageAndCodeStr);
			}else{
				String messageAndCodeAry[] = messageAndCodeStr.split(";");
				codeMessage.setCode(Integer.parseInt(messageAndCodeAry[0].trim()));
				codeMessage.setMessage(messageAndCodeAry[1].trim());
			}
		} catch (Exception e) {
			codeMessage.setMessage(messageCode);
		}
		if( codeMessage.getCode() == 0 ){
			codeMessage.setCode( ResultModal.DEFAULT_ERROR_CODE );
		}
		return codeMessage;
	}

	/**
	 * 构建返回体.
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	private String buildResponseString(int code, String message, Object data , Integer count ) {
		ResultModal reultModal = getResultModal(code, message, data , count ) ;
		String repstr = null ;
		if( reultModal instanceof ResultModalWithData && jsonSerializer != null ){
			String tempResult = jsonSerializer.toJson( reultModal ) ;
			jsonSerializer = null ;
			includeFields = null ;
			excludeFields = null ;
			repstr = tempResult ;
		}else{
			repstr = new JacksonUtil().objToJson(reultModal);
		}
		//做请求拦截缓存返回内容使用，仅仅缓存成功请求 .
		if( localRequest.get() != null && code == 0  ){
			localRequest.get().setAttribute("response", repstr );
		}
		return repstr ;
	}

	/**
	 * 构建成功返回体.
	 * 
	 * @param data
	 * @return
	 */
	protected String buildSuccessResponseString(Object data , Integer count ) {
		setJsonSerializer( data ) ;
		return buildResponseString(ResultModal.DEFAULT_SUCCESS_CODE, ResultModal.DEFAULT_SUCCESS_MESSAGE, data , count );
	}
	protected String buildSuccessResponseStringError(Object data , Integer count ) {
		setJsonSerializer( data ) ;
		return buildResponseString(1, ResultModal.DEFAULT_ERROR_MESSAGE, data , count );
	}
	protected String buildSuccessResponseStringError(Object data ,String eroor, Integer count ) {
		setJsonSerializer( data ) ;
		return buildResponseString(1,eroor, data , count );
	}
	protected String buildSuccessResponseString(Object data  ){
		return buildSuccessResponseString( data , null ) ;
	}
	
	protected <T> String buildSuccessResponseRemotePage(RemotePage<T> remotePage ) {
		if(remotePage==null|| CommonUtil.isEmpty(remotePage.getData())){
			return buildSuccessResponseString(new ArrayList<>());
		}
		int count = remotePage.getPageInfo() == null?0:remotePage.getPageInfo().getAllRows();
		return buildSuccessResponseString(remotePage.getData(),count);
	}
	
	
	/**
	 * 返回包含字段
	* @author dengbo  
	 * @param <T>
	* @date 2016年12月20日 下午12:07:44
	 */
	protected <T> String buildSuccessRemotePageInclude(RemotePage<T> remotePage, String... fieldNames ) {
		if(remotePage==null||CommonUtil.isEmpty(remotePage.getData())){
			return buildSuccessResponseString(new ArrayList<>());
		}
		int count = remotePage.getPageInfo() == null?0:remotePage.getPageInfo().getAllRows();
		return buildSuccessResponseInclude(remotePage.getData() , count );
	}
	
	
	/**
	 * 返回包含字段
	* @author dengbo  
	* @date 2016年12月20日 下午12:07:44
	 */
	protected String buildSuccessResponseInclude(Object data , Integer count ,String... fieldNames ) {
		data = includes(data,fieldNames);
		return buildResponseString(ResultModal.DEFAULT_SUCCESS_CODE, ResultModal.DEFAULT_SUCCESS_MESSAGE, data , count );
	}
	
	protected String buildSuccessResponseInclude(Object data , String... fieldNames ) {
		return buildSuccessResponseInclude( data , null, fieldNames ) ;
	}
	
	protected Object includes(Object data,String... fieldNames ){
		if(data!=null&&!CommonUtil.isEmpty(fieldNames)){
			if(data instanceof List ){
				List<?> tempList = (ArrayList<?>) data ;
				List tempListNew = new ArrayList();
				for(Object temp:tempList){
					tempListNew.add(include(temp,fieldNames));
				}
				return tempListNew;
			}else{
				return include(data,fieldNames);
			}
		}
		return null;
	}
	
	/**
	 * 根据返回类型以及客户端需要的动态返回字段选择序列化对象.
	 * 
	 * @param data
	 */
	private void setJsonSerializer( Object data ){
		if( (includeFields == null || includeFields.length == 0) && 
				( excludeFields == null || excludeFields.length == 0 )  ){
			return ;
		}
		if( data != null && data instanceof ArrayList ){
			List<?> tempList = (ArrayList<?>) data ;
			if( !tempList.isEmpty() ){
				this.jsonSerializer = new JsonSerializer().filterNullValues() ;
			}
			if( excludeFields != null && excludeFields.length > 0){
				jsonSerializer.exclude(tempList.get(0).getClass(), excludeFields ) ;
			}
			if( includeFields != null && includeFields.length > 0){
				jsonSerializer.include(tempList.get(0).getClass(), includeFields ) ;
			}
		}
	}

	/**
	 * 构建失败返回体.
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	protected String buildFailResponseString(int code, String message, Object data) {

		return buildResponseString(code, message, data , null );
	}

	/**
	 * 构建成功返回体.
	 * 
	 * @param data
	 * @return
	 */
	protected ResponseEntity<ResultModal> buildSuccessResponseEntity(Object data) {

		return buildResponseEntity(ResultModal.DEFAULT_SUCCESS_CODE, ResultModal.DEFAULT_SUCCESS_MESSAGE, data,
				HttpStatus.OK);
	}

	private ResponseEntity<ResultModal> buildResponseEntity(ResultModal codeMessage, Object data, HttpStatus status) {

		return buildResponseEntity(codeMessage.getCode(), codeMessage.getMessage(), data, status);
	}

	/**
	 * 设置返回包含字段.客户端动态返回字段优先于服务端设置的返回字段.
	 * 
	 * @param serializeClass
	 * @param includeProperties
	 */
	protected void setIncludeProperties( Class<?> serializeClass ,String ... includeProperties) {
		if( jsonSerializer == null ){
			jsonSerializer = new JsonSerializer() ;
		}
		jsonSerializer.filterNullValues().include( serializeClass , includeProperties ) ;
	}

	/**
	 * 设置返回过滤字段.客户端设置的动态返回过滤字段优先于服务端设置返回字段.
	 * 
	 * @param serializeClass
	 * @param excludeProperties
	 */
	protected void setExcludeProperties( Class<?> serializeClass , String ... excludeProperties) {
		if( jsonSerializer == null ){
			jsonSerializer = new JsonSerializer() ;
		}
		jsonSerializer.exclude( serializeClass , excludeProperties ) ;
	}

	/**
	 * 得到多个对象map,属性名替换
	 * @author dengbo
	 * @date 2016年8月1日 下午4:15:07
	 */
	public List<Map<String,Object>> getResultMaps(String [] sourceProperties , String [] resultProperties,Object data){
		if(data==null){
			return null;
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap = null;
		if( data instanceof ArrayList ){
			List<?> tempList = (ArrayList<?>) data ;
			if( !tempList.isEmpty() ){
				for(int j=0;j<tempList.size();j++){
					resultMap = getResultMap(sourceProperties,resultProperties,tempList.get(j));
					if(resultMap!=null){
						list.add(resultMap);
					}
				}
			}
		}
		return list;
	}
	/**
	 * 得到单个对象map,属性名替换
	 * @author dengbo
	 * @date 2016年8月1日 下午4:15:20
	 */
	public Map<String,Object> getResultMap(String [] sourceProperties , String [] resultProperties,Object obj){
		if(obj==null){
			return null;
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int length = sourceProperties.length<=resultProperties.length?
				sourceProperties.length:resultProperties.length;
		for (int i = 0;i<length;i++) {  
			try {
				Object temp = Reflections.getFieldValue(obj, sourceProperties[i]);
				String date = BeanHelper.validDate(temp,null);//判别转换日期类型
				if(date!=null){
					temp = date;
				}
				resultMap.put(resultProperties[i], temp);
			} catch (Exception e) {
				//throw new RuntimeException(e);
			}
		}  
		return resultMap;
	}
	
	/**
	 * 根据前缀获取request参数
	 * 
	 * @param request
	 * @param searchPrefix
	 * @return
	 */
	protected Map< String , Object > getRequestParamMapByPrefix( HttpServletRequest request , CommonConstant.SEARTCH_PRIFIX searchPrefix ){
		Map<String , String[] > requestParams = request.getParameterMap() ;
		if( requestParams == null || requestParams.isEmpty() ){
			return null ;
		}
		Set<String> reqeustParamKeys = requestParams.keySet() ;
		Map<String , Object> resultParamMap = new HashMap<String , Object>() ;
		for( String requestParamKey : reqeustParamKeys ){
			if( searchPrefix != null && !requestParamKey.startsWith( searchPrefix.value() ) ){
				continue ;
			}
			String [] temp = requestParams.get( requestParamKey ) ;
			if( temp.length == 1 ){
				if( searchPrefix == null )
					resultParamMap.put( requestParamKey , temp[0] ) ;
				else
					resultParamMap.put( requestParamKey.replace( searchPrefix.value(), "") , temp[0] ) ;
				continue ;
			}
			List<String> params = new ArrayList< String >() ;
			for( String param : temp ){
				params.add( param ) ;
			}
			if( searchPrefix == null )
				resultParamMap.put( requestParamKey , params ) ;
			else
				resultParamMap.put( requestParamKey.replace(searchPrefix.value(), "") ,  params ) ;
		}
		return resultParamMap ;
	}

	private ResultModal getResultModal(int code, String message, Object data , Integer count ) {
		ResultModal resultModal = null;
		if (data == null)
			resultModal = new ResultModal(code, message);
		else
			resultModal = new ResultModalWithData(code, message, data , count );
		return resultModal;
	}

	private ResponseEntity<ResultModal> buildResponseEntity(int code, String message, Object data, HttpStatus status) {

		return new ResponseEntity<ResultModal>(getResultModal(code, message, data , null ), status);
	}

	public class ResultModal {
		private int code;
		private String message;
		public static final int DEFAULT_SUCCESS_CODE = 0; //默认成功状态码
		public static final String DEFAULT_SUCCESS_MESSAGE = "OK"; //默认成功信息
		public static final int DEFAULT_ERROR_CODE = 10002;//默认统一失败状态吗
		public static final String DEFAULT_ERROR_MESSAGE = "ERROR" ; //默认失败信息

		public ResultModal(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public ResultModal() {
			this.code = ResultModal.DEFAULT_SUCCESS_CODE;
			this.message = ResultModal.DEFAULT_SUCCESS_MESSAGE;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	class ResultModalWithData extends ResultModal {
		private Object data;
		private Integer count ;

		public ResultModalWithData(int code, String message, Object data , Integer count ) {
			super(code, message);
			this.data = data;
			this.count = count ;
		}

		public ResultModalWithData(int code, String message) {
			super(code, message);
		}

		public ResultModalWithData(Object data) {
			super();
			this.data = data;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}
		
	}
}
