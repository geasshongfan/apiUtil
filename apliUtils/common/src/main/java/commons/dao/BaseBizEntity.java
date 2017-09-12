package commons.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author wangjg
 *
 */
public class BaseBizEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//创建时间
	private Date created;
	//更新时间
	private Date modified;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
}
