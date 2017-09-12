package upload;

import commons.api.ResponseParser;
import commons.api.ResponseResult;
import json.JacksonUtil;
import json.JsonUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;
import utils.OkHttpRequest2;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author wangjigang
 *
 */
public class UploadUtil {
	private static final Logger logger = LoggerFactory.getLogger( UploadUtil.class) ;
	private OkHttpRequest2 httpRequest;
	private String url = null;
	
	public Map<String,String> moveFiles(Map<String,String> srcPathMap, String appId, String moduleId, String objectId) throws IOException{
		List<MovePara> params = new ArrayList<MovePara>();
		for(Entry<String, String> entry:srcPathMap.entrySet()){
			String inputName=entry.getKey();
			String tempPath = entry.getValue();
			MovePara mp = new MovePara(inputName, tempPath, appId, moduleId, objectId);
			params.add(mp);
		}
		return this.moveFiles(params);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> moveFiles(List<MovePara> params) throws IOException{
		Map<String,String> result = new LinkedHashMap<String,String>();
		
		Iterator<MovePara> it = params.iterator();
		while(it.hasNext()){
			MovePara mp = it.next();
			if(mp.getSrcPath().startsWith('/'+mp.getAppId()+'/') || !FilenameUtils.getName(mp.getSrcPath()).startsWith("TMP-")){
				logger.debug("非临时文件：{}", mp);
				result.put(mp.getReqid(), mp.getDestPath());
				it.remove();
			}else{//占位，保持顺序
				result.put(mp.getReqid(), null);
			}
		}
		
		String url = this.url+"/put/moveTmps";
		String json = getHttpRequest().postJson(url, JsonUtils.objectToJson(params));
		ResponseParser  rp = new ResponseParser();
		ResponseResult rr = rp.toBeanList(json, MovePara.class);
		List<MovePara> mpList = (List<MovePara>) rr.getData();
		for(MovePara mp:mpList){
			if(mp.getError()==null||mp.getError().isEmpty()){
				result.put(mp.getReqid(), mp.getDestPath());
			}else{
				throw new RuntimeException(""+mp);
			}
		}
		return result;
	}
	
	public String moveFile(String srcPath, String appId, String moduleId, String objectId) throws IOException{
		if(srcPath.startsWith('/'+appId+'/') || !FilenameUtils.getName(srcPath).startsWith("TMP-")){
			logger.debug("非临时文件：{}", srcPath);
			return srcPath;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("srcPath", srcPath);
		params.put("appId", appId);
		params.put("moduleId", moduleId);
		params.put("objectId", objectId);
		String url = this.url+"/put/moveTmp";
		String json = getHttpRequest().post(url, params);
		ResponseParser  rp = new ResponseParser();
		ResponseResult rr = rp.toValue(json);
		if(rr.getCode()==0){
			return (String)rr.getData();
		}else{
			throw new RuntimeException(rr.getMessage());
		}
	}
	/**
	 * 上传正式文件
	 * input:文件字节流
	 * appId：项目id CommonConstant.PROJECT
	 * moduleId：模块id CommonConstant.ULIAOBAO_MODULE
	 * objectId：对应实体id
	 * filename：文件名称-主要使用后缀名
	* @author dengbo  
	* @date 2016年12月1日 上午11:49:54
	 */
	public String upload(InputStream input, String appId, String moduleId, String objectId,String filename) throws IOException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		params.put("moduleId", moduleId);
		params.put("objectId", objectId);
		params.put("filename", filename);
		params.put("file", input);
		String url = this.url+"/put/upload.xhtml";
		String json = getHttpRequest().post(url, params);
		ResponseParser  rp = new ResponseParser();
		ResponseResult rr = rp.toMap(json);
		if(rr.getCode()!=0){
			throw new RuntimeException(rr.getMessage());
		}
		Map<String,String> map = rr.getDataObject();
		return map.get("path");
	}

	/**
	 *
	 * @param fStream
	 * @param fName 文件名称含文件后缀名
	 * @param fTargetPath 目录路径,   /avatar/
	 * @param fWidth 图片的宽高
	 * @param fHeight 图片的宽高
	 * @return
	 * @throws IOException
	 */
	public Map<String,Object> uploadPOP(InputStream fStream, String fName, String fTargetPath, String fWidth,String fHeight,String url) throws IOException{
		Map<String, Object> params = new HashMap<String, Object>();
		String content=getBase64ImageStr(fStream);//解析图片 转换成base64编码
		params.put("fStream",content);
		params.put("temp", fStream);
		params.put("fName", fName);
		params.put("fTargetPath", fTargetPath);
		params.put("fWidth", fWidth+"");
		params.put("fHeight", fHeight+"");
		params.put("iBase64","1");
		String json = getHttpRequest().post(url, params);
		ResponseParser  rp = new ResponseParser();
		Map map = new JacksonUtil().jsonToMap(json);
		return map;
	}
	
	/**
	 * 上传临时文件
	 * input:文件字节流
	 * filename：文件名称-主要使用后缀名
	* @author dengbo  
	* @date 2016年12月1日 上午11:49:54
	 */
	public String uploadTmp(InputStream input,String filename) throws IOException{
		Map<String, String> map = uploadTmpMap(input, filename);
		return map.get("path");
	}

	/**
	 * 上传临时文件返回全路径
	* @author dengbo  
	* @date 2016年12月5日 上午9:59:25
	 */
	public String uploadTmpAllPath(InputStream input,String filename) throws IOException{
		Map<String, String> map = uploadTmpMap(input, filename);
		return map.get("full_url");
	}
	
	private Map<String, String> uploadTmpMap(InputStream input, String filename) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("filename", filename);
		params.put("file", input);
		String url = this.url+"/put/uploadTmp.xhtml";
		String json = getHttpRequest().post(url, params);
		ResponseParser  rp = new ResponseParser();
		ResponseResult rr = rp.toMap(json);
		if(rr.getCode()!=0){
			throw new RuntimeException(rr.getMessage());
		}
		Map<String,String> map = rr.getDataObject();
		return map;
	}
	
	public OkHttpRequest2 getHttpRequest() {
		if(this.httpRequest==null){
			this.httpRequest = new OkHttpRequest2();
			this.httpRequest.setReadTimeout(30);
			this.httpRequest.setWriteTimeout(60);
		}
		return this.httpRequest;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public static void main(String args[]) throws IOException{
		UploadUtil uu = new UploadUtil();
		Map<String, Object> params = new HashMap<String, Object>();
		InputStream in=new FileInputStream(new File("D:\\image\\1.jpg"));
        params.put("temp",in);
		params.put("fName","1111.jpg");
		params.put("fTargetPath","/avatar/temp/"+System.currentTimeMillis());
		params.put("fWidth", "100");
		params.put("fHeight", "100");
		params.put("iBase64","1");
        String str=uu.getImageStr(in);
        str=str.replaceAll("\r|\n", "");
        System.out.println(str);
		params.put("fStream",str);
        String json = uu.getHttpRequest().post("http://api.pop136.com/internal/UploadApi.php", params);/**/
		System.out.println(json);
	}

	private String getImageStr(InputStream in) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		if(in==null){
			return null;
		}
		byte[] data = null;
		// 读取图片字节数组
		try {
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally {
//			if(in!=null){
//				try {
//					in.close();
//				} catch (IOException e) {}
//			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 兼容长字段的 base64编码图片转换
	 * @param in
	 * @return
	 */
	private String getBase64ImageStr(InputStream in){
		if(in==null){
			return null;
		}
		try {
			byte[] output = steamToByte(in);
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(output);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static byte[] steamToByte(InputStream input) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = input.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		byte[] buffer =  baos.toByteArray();
		return baos.toByteArray();
	}


}
