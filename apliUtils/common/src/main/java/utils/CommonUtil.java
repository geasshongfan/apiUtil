package utils;

import constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import property.SystemProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonUtil {
	
	public static boolean isEmpty( Object [] object ){
		
		return object == null || object.length == 0 ;
	}
	
	public static boolean isEmpty( List<?> list ){
		
		return list == null || list.isEmpty() ;
	}
	
	public static boolean isEmpty( Map<? , ? > map ){
		
		return map == null || map.isEmpty() ;
	}
	
	public static int parsetIntIgnoreError( Object source , int defalultValue ) {
		int temp = 0 ;
		try{
			temp = Integer.parseInt( source.toString() ) ;
		}catch( Exception e ){
			temp = defalultValue ;
		}
		return temp ;
	}
	
	public static List<String> explode( String source , String regex ){
		if( source == null || source.trim().equals("") ){
			return null ;
		}
		if( regex == null || regex.trim().equals("") ){
			regex = "" ;
		}
		String [] strary = source.split( regex ) ;
		List<String> resList = new ArrayList<String>() ;
		for( String temp : strary ){
			if( temp.trim().equals("") ){
				continue ;
			}
			resList.add( temp ) ;
		}
		return resList ;
	}
	
	public static List<Integer> explodeForIds( String source , String regex  ){
		List<Integer> resultLists = new ArrayList<Integer>() ;
		List<String> temp = explode( source , regex ) ;
		if( temp == null || temp.isEmpty() ){
			return null ;
		}
		for( String item : temp ){
			resultLists.add( parsetIntIgnoreError( item , 0 ) ) ;
		}
		return resultLists ;
	}
	
	public static String merge(List<String> source,String regex){
		if(source==null||source.size()==0||regex==null){
			return null;
		}
		StringBuffer bf= new StringBuffer();
		for (String string : source) {
			bf.append(string);
			bf.append(regex);
		}
		bf.delete(bf.lastIndexOf(regex), bf.length());
		return bf.toString();
		
	}
	
	public static List<String> concatImageDomain( List<String> relativeImages ){
		if( relativeImages == null || relativeImages.isEmpty() ){
			return null ;
		}
		List<String> fullImages = new ArrayList<String>() ;
		for( String image : relativeImages ){
			fullImages.add( concatImageDomain( image ) ) ;
		}
		return fullImages ;
	}
	public static List<String> concatImageDomainshuiyin( List<String> relativeImages){
		if( relativeImages == null || relativeImages.isEmpty() ){
			return null ;
		}
		List<String> fullImages = new ArrayList<String>() ;
		for( String image : relativeImages ){
			fullImages.add(concatImageDomainOfSubTrend( image) ) ;
		}
		return fullImages ;
	}
	
	public static List<String> concatFabricImageDomain( List<String> relativeImages ){
		if( relativeImages == null || relativeImages.isEmpty() ){
			return null ;
		}
		List<String> fullImages = new ArrayList<String>() ;
		for( String image : relativeImages ){
			fullImages.add( concatFabricImageDomain( image ) ) ;
		}
		return fullImages ;
	}
	
	
	/**
	 * 拼接域名
	* @author dengbo  
	* @date 2016年10月31日 上午11:02:18
	 */
	public static String concatDomain(String relativeImage,String key){
		return SystemProperties.getProperty(key,"")+relativeImage;
	}
	
	/**
	 * 获取非面料图片
	* @author dengbo  
	* @date 2016年10月31日 上午10:31:54
	 */
	public static  String concatImageDomain( String relativeImage ){
		return getImageLocation(relativeImage)+relativeImage ;
	}
	
	/**
	 * 获取非面料图片（父趋势加水印）
	* @author dengbo  
	* @date 2016年10月31日 上午10:31:54
	 */
	public static  String concatImageDomainOfTrend( String relativeImage ){
		String key = "trend.watermark.url";
		return getImageLocation(relativeImage)+relativeImage +SystemProperties.getProperty(key, "");
	}
	
	/**
	 * 获取非面料图片(子趋势加水印)
	* @author dengbo  
	* @date 2016年10月31日 上午10:31:54
	 */
	public static  String concatImageDomainOfSubTrend( String relativeImage){
		String subkey = "subtrend.watermark.url";
		return getImageLocation(relativeImage)+relativeImage+SystemProperties.getProperty(subkey, "");
	}
	
	/**
	 * 获取非面料400图片
	* @author wanghui 
	* @date 2016年12月20日 上午17:10:21
	 */
	public static  String concatImageOss_400( String relativeImage ){
		return getImageLocation(relativeImage)+relativeImage+CommonConstant.IMG_SIZE_SUFFIX.W_400.getSuffix() ;
	}
	
	
	/**
	 * 获取面料图片
	* @author dengbo  
	* @date 2016年10月31日 上午10:32:08
	 */
	public static  String concatFabricImageDomain( String relativeImage){
		if(StringUtils.isBlank(relativeImage)){
			return null;
		}
		relativeImage = delStartDiagonal(relativeImage);
		String[] paths = relativeImage.trim().split("/");
		if(paths.length > 0 && paths[0].equals(CommonConstant.PROJECT.ULIAOBAO.getName())) {
			String absolutePath = SystemProperties.getProperty( "fabric.image.oss.url" , "")+'/' + relativeImage ;
			//判断环境，如果是云环境则使用OSS命名规则 .
			//if( SystemProperties.getProperty("evn.property.name","").equals("ecs") ){
				absolutePath += CommonConstant.IMG_SIZE_SUFFIX.W_400.getSuffix();
			//}
			return absolutePath;
		}
		return SystemProperties.getProperty( "fabric.image.location.url" , "")+"/"+relativeImage;
	}
	
	public static  String concatFabricImageDomainAd( String relativeImage){
		if(StringUtils.isBlank(relativeImage)){
			return null;
		}
		relativeImage = delStartDiagonal(relativeImage);
		String[] paths = relativeImage.trim().split("/");
		if(paths.length > 0 && paths[0].equals(CommonConstant.PROJECT.ULIAOBAO.getName())) {
			String absolutePath = SystemProperties.getProperty( "fabric.image.oss.url" , "")+'/' + relativeImage ;
			//判断环境，如果是云环境则使用OSS命名规则 .
			//if( SystemProperties.getProperty("evn.property.name","").equals("ecs") ){
				/*absolutePath += CommonConstant.IMG_SIZE_SUFFIX.W_400.getSuffix();*/
			//}
			return absolutePath;
		}
		return SystemProperties.getProperty( "fabric.image.location.url" , "")+"/"+relativeImage;
	}
	
	/**
	 * 根据非面料图片路径获取文件服务器域名
	* @author dengbo  
	* @date 2016年10月31日 上午10:44:03
	 */
	public static String getImageLocation(String relativeImage){
		if(StringUtils.isBlank(relativeImage)){
			return null;
		}
		String key = "image.location.url";
		relativeImage = delStartDiagonal(relativeImage);
		String[] paths = relativeImage.trim().split("/");
		if(paths.length > 0 && paths[0].equals(CommonConstant.PROJECT.ULIAOBAO.getName())) {
			key = "image.oss.url";
		}
		return SystemProperties.getProperty( key , "");
	}
	
	/**
	 * 根据面料图片路径获取文件服务器域名
	* @author dengbo  
	* @date 2016年10月31日 上午10:44:03
	 */
	public static String getFabricImageLocation(String relativeImage){
		if(StringUtils.isBlank(relativeImage)){
			return null;
		}
		String key = "fabric.image.location.url";
		relativeImage = delStartDiagonal(relativeImage);
		String[] paths = relativeImage.trim().split("/");
		if(paths.length > 0 && paths[0].equals(CommonConstant.PROJECT.ULIAOBAO.getName())) {
			key = "fabric.image.oss.url";
		}
		return SystemProperties.getProperty( key , "");
	}
	

	private static String delStartDiagonal(String relativeImage) {
		for(int i=0; i<relativeImage.length(); i++) {
			if(relativeImage.charAt(i) != '/') {
				return relativeImage.substring(i, relativeImage.length());
			}
		}
		return "";
	}
	
	/**
	 * 获取面料图片 获取 size 尺寸图
	* @author dengbo  
	* @date 2016年11月3日 下午6:28:22
	 */
	public static String concatFabricImageDomainAndSize(String relativeImage,int size){
		return updatePicSize(concatFabricImageDomain(relativeImage),size);
	}
	
	public static List<String> concatFabricImagesDomainAndSize( List<String> urls,int size){
		if( urls == null || urls.isEmpty() ){
			return null;
		}
		List<String> tempUrls = new ArrayList<String>() ;
		for( int i = 0 ; i < urls.size() ; i ++ ){
			tempUrls.add(
					updatePicSize(concatFabricImageDomain(urls.get( i )),size)) ;
		}
		urls.clear(); 
		urls.addAll( tempUrls ) ;
		return urls;
	}
	
	public static String updatePicSize(String url,int size){
		if(StringUtils.isBlank(url)){
			return null;
		}
		if(size!=0){
			return url.replaceAll("_400", "_"+size); 
		}
		return url;
	}
	
}
