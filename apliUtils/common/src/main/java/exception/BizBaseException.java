package exception;

public class BizBaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int code ;
	private Throwable cause ;
	
	public BizBaseException(int code , String message ){
		super( message ) ;
		this.code = code ;
	}
	public BizBaseException(int code , String message , Throwable cause ){
		super( message ) ;
		this.code = code ;
		this.cause = cause ;
	}
	public BizBaseException(){
		super() ;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Throwable getCause() {
		return cause;
	}
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	
}
