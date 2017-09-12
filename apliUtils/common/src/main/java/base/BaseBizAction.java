package base;

import constant.CodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import upload.UploadUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author wangjg
 *
 */
public class BaseBizAction extends BaseAction{
	
	@Autowired(required=false)
	private UploadUtil uu;
	
	protected String getSuccessResult(){
		return this.getResult(CodeConstant.SUCCESS, null, null);
	}
	
	protected String getSuccessResult(Object data){
		return this.getResult(CodeConstant.SUCCESS, null, data);
	}
	
	protected Map<String,String> handleFiles(String appId, String moduleId, String objectId){
		String srcPathPrefix= "#";
		Map<String,String> imgParams= new LinkedHashMap<String,String>();
		Map<String, String> params = this.getParameterMap();
		for(Entry<String, String> entry:params.entrySet()){
			String name = entry.getKey();
			String value = entry.getValue();
			if(name!=null && value!=null && value.startsWith(srcPathPrefix)){
				value = value.substring(srcPathPrefix.length());
				imgParams.put(name, value);
			}
		}
		return this.handleFiles(imgParams, appId, moduleId, objectId);
	}
	
	protected Map<String,String> handleFiles(String namePrefix, String appId, String moduleId, String objectId){
		Map<String,String> imgParams= new LinkedHashMap<String,String>();
		Map<String, String> params = this.getParameterMap();
		for(Entry<String, String> entry:params.entrySet()){
			String name = entry.getKey();
			String value = entry.getValue();
			if(name!=null && value!=null && name.startsWith(namePrefix)){
				imgParams.put(name, value);
			}
		}
		return this.handleFiles(imgParams, appId, moduleId, objectId);
	}
	
	protected List<String> handleFiles(List<String> srcPathList, String appId, String moduleId, String objectId){
		try {
			Map<String,String> srcPathMap = new LinkedHashMap<String,String>();
			for(int i=0; i<srcPathList.size(); i++){
				String srcPath = srcPathList.get(i);
				srcPathMap.put(String.valueOf(i), srcPath);
			}
			Map<String, String> destMap = uu.moveFiles(srcPathMap, appId, moduleId, objectId);
			return new ArrayList<String>(destMap.values());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Map<String,String> handleFiles(Map<String,String> srcPathMap, String appId, String moduleId, String objectId){
		try {
			return uu.moveFiles(srcPathMap, appId, moduleId, objectId);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String handleFile(String srcPath, String appId, String moduleId, String objectId) throws IOException{
		try {
			return uu.moveFile(srcPath, appId, moduleId, objectId);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
