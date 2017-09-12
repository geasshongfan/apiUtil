package commons.dao;

import java.io.Serializable;

public class Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_PAGE_SIZE = 10;
	private static final int MAX_PAGE_SIZE = 1000 ;
	// 总条数
	private int allRows = 0;
	// 总页码
	private int allPages = 0;
	// 当前页码
	private int currentPage = 1;
	// 每页数量
	private int pageSize = DEFAULT_PAGE_SIZE;
	//查询开始行数
	private int rowOffset = 0 ;
	//是否做分页
	private boolean isPage = false ;
	//查询行数，不做统计总数
	private int limit = 0 ;
	
	public void limit( int count ){
		if( count <= 0 ){
			count = 1 ;
		}
		this.limit = count ;
		isPage = false ;
	}
	public int limit(){
		return limit ;
	}
	public boolean isPage(){
		return this.isPage ;
	}

	public Page() {
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getAllRows() {
		return allRows;
	}

	private void setPageInfo(int allRows) {
		if( allRows <= 0 ){
			allPages = 1 ;
		}else{
			allPages = allRows % pageSize == 0 ? allRows / pageSize : allRows / pageSize + 1;
		}
		if( currentPage > allPages ) currentPage = allPages ;
	}

	public void setAllRows(int allRows) {
		setPageInfo(allRows);
		this.allRows = allRows;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage <= 0){
			this.currentPage = 1;
		}else{
			this.currentPage = currentPage;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if( pageSize < 1 ) pageSize = 1 ;
		if( pageSize > MAX_PAGE_SIZE ) pageSize = MAX_PAGE_SIZE ;
		this.pageSize = pageSize;
		this.isPage = true ;
	}

	public int getRowOffset() {
		rowOffset = ( currentPage - 1 ) * pageSize ;
		return rowOffset < 0 ? 0 : rowOffset;
	}

}
