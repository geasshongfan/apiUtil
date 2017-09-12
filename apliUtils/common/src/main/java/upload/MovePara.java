package upload;

/**
 * 
 * @author wangjigang
 *
 */
public class MovePara {
	private String reqid;
	private String srcPath;
	private String appId;
	private String moduleId;
	private String objectId;
	private String destPath;
	private String error;
	
	public MovePara(){
		
	}
	
	public MovePara(String reqid, String srcPath, String appId, String moduleId, String objectId) {
		this.reqid = reqid;
		this.srcPath = srcPath;
		this.appId = appId;
		this.moduleId = moduleId;
		this.objectId = objectId;
	}

	public String getReqid() {
		return reqid;
	}
	public void setReqid(String reqid) {
		this.reqid = reqid;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getDestPath() {
		return destPath;
	}
	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "MovePara [reqid=" + reqid + ", srcPath=" + srcPath + ", appId=" + appId + ", moduleId=" + moduleId
				+ ", objectId=" + objectId + ", destPath=" + destPath + ", error=" + error + "]";
	}
	
}
