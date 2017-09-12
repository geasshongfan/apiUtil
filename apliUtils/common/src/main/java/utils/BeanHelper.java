
package utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings({"rawtypes","unchecked"})
public class BeanHelper {
	
	/**
	 * bean copy ... 
	 * 
	 * @param source
	 * @param desc
	 * @param sourceProperties
	 * @param descProperties
	 * @throws RuntimeException
	 */
	public synchronized static <T> void beancopy (
			Object source , T desc , String [] sourceProperties , String [] descProperties )
					throws RuntimeException {
		if( source == null || desc == null 
				|| CommonUtil.isEmpty( sourceProperties ) || CommonUtil.isEmpty( descProperties ) ){
			throw new RuntimeException("bean copy is error , method arguments is not valid ... ") ;
		}
		int loop = sourceProperties.length - 1 ;
		if( sourceProperties.length > descProperties.length ){
			loop = descProperties.length - 1 ;
		}
		while( loop >= 0 ){
			if( isExistBeanFiled( source , sourceProperties[loop] , source.getClass() ) 
					&& isExistBeanFiled( desc , descProperties[loop] , desc.getClass() ) ) {
				try{
					Object temp = Reflections.getFieldValue( source , sourceProperties[loop] ) ;
					/*Date date = validDate(temp);//判别转换日期类型
					if(date!=null){
						temp= date;
					}*/
					Reflections.setFieldValue(desc ,  descProperties[loop] , temp);
				 /*  ConvertUtils.register(new DoubleConverter(null), Double.class);
					BeanUtils.setProperty( desc ,  descProperties[loop] , temp );*/
				}catch(Exception e){
					//System.out.println(e);
				}
			}
			loop -- ;
		}
	}
	
	
	
	
	static SimpleDateFormat format = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	public static String validDate(Object str,String pattern) {
		if(str==null){
			return null;
		}
		String date = null;
		// 指定日期格式为美国标准时间
		try {
		// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			//format.setLenient(false);
			Date dateNew = format.parse(str.toString());
			date = TimeUtil.format( dateNew,null );
		} catch (ParseException e) {
				// e.printStackTrace();
				// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
		} 
		return date;
	}
	/**
	 * field is exists ... 
	 * 
	 * @param bean
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
	private static boolean isExistBeanFiled( Object bean , String fieldName , Class<?> clazz ){
		if( bean == null || fieldName == null || clazz == null ) return false ;
		Field [] fields = clazz.getDeclaredFields( ) ;
		for( Field field : fields ){
			if( field.getName().equals( fieldName ) ){
				return true ;
			}
		}
		return false ;
	}
	
	/**
	 * bean copy with equal properties ... 
	 * 
	 * @param source
	 * @param desc
	 * @param properties
	 */
	public static <T> void beancopy( Object source , T desc , String [] properties ){
		
		beancopy( source , desc , properties , properties ) ;
	}
	
	public static<T> void beancopy( Object source , T desc ,  T def ){
		beancopy( source , desc , null , def ) ;
	}
	/**
	 * 对象属性复制时采用默认对象做空值复制 .
	 * 
	 * @param source		源对象
	 * @param desc			目标对象
	 * @param properties	字段
	 * @param def			默认值对象
	 */
	public static<T> void beancopy( Object source , T desc , String [] properties , T def ){
		if( properties == null || properties.length == 0 ){
			beancopy( source , desc  ) ;
		}else{
			beancopy( source , desc , properties , properties ) ;
		}
		if( def == null ){
			return ;
		}
		Field [] defFields = def.getClass().getDeclaredFields() ;
		for( Field defField : defFields ){
			try{
				if( Reflections.getFieldValue( desc , defField.getName() ) == null ){
					Reflections.setFieldValue(
							desc, defField.getName() , Reflections.getFieldValue(def, defField.getName() ));
				}
			}catch(Exception e){
				//skip ...
			}
		}
	}
	
	/**
	 * bean copy with equal bean ...
	 * 
	 * @param source
	 * @param desc
	 */
	public static <T> void beancopy( Object source , T desc ){
		if( source == null )
			throw new RuntimeException("bean copy fail , source is null ..." ) ;
		Field [] fields = source.getClass().getDeclaredFields()  ;
		String [] properties = new String [ fields.length ] ;
		for( int i = 0 ; i < fields.length ; i ++ ){
			properties[ i ] = fields[ i ].getName() ;
		}
		beancopy( source , desc , properties ) ; 
	}
	
	/**
	 * deep copy object ... 
	 * 
	 * @param t
	 * @return
	 */
	public static <T> T beancopy( T t ){
		if( t == null ) return null ;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
		ObjectOutputStream objectOutputStream = null ;
		ByteArrayInputStream byteArrayInputStream  = null ;
		ObjectInputStream objectInputStream = null ;
		T temp = null ;
		try{
			objectOutputStream = new ObjectOutputStream( byteArrayOutputStream ) ;
			objectOutputStream.writeObject( t );
			byteArrayInputStream = new ByteArrayInputStream( byteArrayOutputStream.toByteArray() ) ;
			objectInputStream = new ObjectInputStream( byteArrayInputStream ) ;
			temp =  (T) objectInputStream.readObject() ;
		}catch(Exception e){
			e.printStackTrace();
		}
		return temp ;
	}
	
	public static <T> T beancopy( T source, String [] properties ){
		T desc = null;
		try {
			desc = (T) source.getClass().newInstance();
			beancopy( source , desc , properties , properties ) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}
	
	public static <T> T beancopy( Object source, Class<T> clazz, String [] properties ){
		T desc = null;
		try {
			desc = (T) clazz.newInstance();
			beancopy( source , desc , properties , properties ) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}
	
	/**
	 * @param list 对象集合
	 * @param property 分组属性
	 * @return Map<Bean.property, List<Bean>>
	 */
	public static Map groupByProperty(List list, String property){
		HashMap map = new HashMap();
		for(Object bean:list){
			try {
				Object pv = getProperty(bean, property);
				List pList = (List) map.get(pv);
				if(pList==null){
					pList = new ArrayList();
					map.put(pv, pList);
				}
				pList.add(bean);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return map;
	}
	
	/**
	 * 根据唯一属性分组对象集合,一个key对应一个对象
	 * @param list
	 * @param property
	 * @return Map<Bean.property, Bean>
	 */
	public static Map groupByUniqueProperty(List list, String property){
		HashMap map = new HashMap();
		for(Object bean:list){
			try {
				Object pv = getProperty(bean, property);
				if(!map.containsKey(pv)){
					map.put(pv, bean);
				}else{
					throw new RuntimeException("duplicate property : "+property+"=" + pv);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return map;
	}

	public static <T> List<T> getPropertyList(List list, String property){
		List<T> plist = new ArrayList<T>();
		for(int i=0;i<list.size();i++){
			Object bean = list.get(i);
			try {
				T pv = (T) getProperty(bean, property);
				plist.add(pv);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return plist;
	}
	
	public static List getDistinctPropertyList(List list, String property){
		ArrayList plist = new ArrayList();
		for(int i=0;i<list.size();i++){
			Object bean = list.get(i);
			try {
				Object pv = getProperty(bean, property);
				if(!plist.contains(pv)){
					plist.add(pv);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return plist;
	}
	
	static Object getProperty(Object bean, String property){
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}


