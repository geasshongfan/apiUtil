package commons.dao;

public class SearchCriteral {
	public enum ORDER_TYPE{
		ORDER_TYPE_DESC("desc"),
		ORDER_TYPE_ASC("asc");
		private String orderType ;
		ORDER_TYPE( String orderType ){
			this.orderType = orderType ;
		}
		public String getValue(){
			return this.orderType ;
		}
		public static ORDER_TYPE parse( String strType  ){
			ORDER_TYPE [] types = ORDER_TYPE.values() ;
			for( ORDER_TYPE type : types ){
				if( strType.equals( type.name() ) ){
					return type ;
				}
			}
			return ORDER_TYPE_DESC ;
		}
		public  static ORDER_TYPE from( String type ){
			if( type == null ){
				return null ;
			}
			ORDER_TYPE [] types = ORDER_TYPE.values() ;
			for( int i = 0 ; i < types.length ; i ++ ){
				if( type.equals( types[i].getValue()  ) ){
					return types[ i ] ;
				}
			}
			return  null ;
		}
	}
	private Page pageInfo ;
	private String orderType ;
	private String orderField ;
	private String [] columns ;
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	//倒序排序
	public void setOrderFieldDesc(String orderField) {
		this.orderField = orderField;
		this.setOrderType(ORDER_TYPE.ORDER_TYPE_DESC.getValue());
	}
	//正序排序
	public void setOrderFieldAsc(String orderField) {
		this.orderField = orderField;
		this.setOrderType(ORDER_TYPE.ORDER_TYPE_ASC.getValue());
	}
	public Page getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(Page pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	public void setPageNum(int pageNum) {
		pageNum = pageNum <= 0 ? 1 : pageNum ;
		if( this.pageInfo == null ){
			pageInfo = new Page() ;
		}
		this.pageInfo.setCurrentPage( pageNum );
	}
	public void setSize(int size) {
		size = size <= 0 ? 10 : size ;
		if( this.pageInfo == null ){
			pageInfo = new Page() ;
		}
		this.pageInfo.setPageSize( size );
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	
}
