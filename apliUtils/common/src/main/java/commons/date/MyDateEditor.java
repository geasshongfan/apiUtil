package commons.date;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 线程安全的 
 * @author wangjg
 */
public class MyDateEditor extends PropertyEditorSupport {
	private String full_timeformat = "yyyy-MM-dd HH:mm:ss SSS";

	public MyDateEditor(){
	}

	public MyDateEditor(String dateFormat){
		this.full_timeformat = dateFormat;
	}
	
	public void setAsText(String value) {
		if(value==null){
			this.setValue(null);
			return;
		}
		String formtText = getFormat(value);
		Date date = format(formtText, value);
		this.setValue(date);
	}
	
	protected String getFormat(String value){
		String formtText;
		if(value.length()==full_timeformat.length()){
			formtText = full_timeformat;
		}else{
			formtText = full_timeformat.substring(0, value.length());
		}
		return formtText;
	}
	
	protected Date format(String formtText, String value){
		try {
			DateFormat format = new SimpleDateFormat(formtText);
			Date date = format.parse(value);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? new SimpleDateFormat(full_timeformat).format(value) : "");
	}

}