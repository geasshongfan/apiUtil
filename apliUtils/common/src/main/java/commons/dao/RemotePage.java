package commons.dao;

import java.io.Serializable;
import java.util.List;

public class RemotePage<T> implements Serializable{
	/**
	 * 
	 */
	public RemotePage( List<T> data , Page pageInfo ){
		this.data = data ;
		this.pageInfo = pageInfo ;
	}
	public RemotePage(){
		
	}
	private static final long serialVersionUID = 1L;
	private Page pageInfo ;
	private List<T> data ;
	
	public Page getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(Page pageInfo) {
		this.pageInfo = pageInfo;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public static <T> RemotePage<T> newRemotePage(List<T> data,Page pageInfo){
		return new RemotePage<T>(data,pageInfo);
	}
}
