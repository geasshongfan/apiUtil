package constant;

public class CommonConstant {
	public static class AppConstant{
		public static final String AUTH_POINT_SERVER = "http://api.pop136.com/app/UserLogin.php?hash=${hash}&version=${version}" ;
		//public static final String AUTH_POINT_SERVER = "http://192.168.66.26/app/UserLogin.php?hash=${hash}&version=${version}" ;
		public static final String AUTH_VERSION = "v4.0.0" ;
		public static final String AUTH_USER_ACCOUNT = "sName" ;
		public static final String AUTH_USER_PASSWORD = "sPassWord" ;
		public static final String AUTH_DECRYPT_KEY = "pop_fabric_app!#^" ;
		public static final String AUTH_HASH_CODE = "fabric_app_hash" ;
		public static final String POPFASHION_FABRIC_HASH = "popfashion_fabric_hash" ;
		public static final String AUTH_POINT_POT="http://www.pop-fashion.com/api/reportlists/";
		//public static final String AUTH_POINT_POT="http://192.168.3.185/api/reportlists/";
		public static final String AUTH_POP_POT="http://www.pop-fashion.com/api/reportdict/";
	}
	
	/**
	 * 项目
	 */
	public enum  PROJECT {
		ULIAOBAO(1, "uliaobao");
		private int code;
		private String name;
		PROJECT(int code, String name) {
			this.code = code;
			this.name = name;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 模块
	 */
	public enum  ULIAOBAO_MODULE {
		FABRIC(1, "fabric"),
		MEMBER(2, "member"),
		SHOP(3,"shop"),
	    ACTIVITY(4,"activity"),
	    TREND(5,"trend"),
	    APPBOOT(6,"appboot"),
		FIND(7,"find"),
		AD(8,"ad"),
		DESCRIPTION(9,"description");
		private int code;
		private String name;
		
		ULIAOBAO_MODULE(int code, String name) {
			this.code = code;
			this.name = name;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 图片尺寸后缀
	 */
	public enum  IMG_SIZE_SUFFIX {
		W_400(400, "?x-oss-process=style/w_400");
		private int size;
		private String suffix;
		IMG_SIZE_SUFFIX(int size, String suffix) {
			this.size = size;
			this.suffix = suffix;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
	}
	
	public enum CACHED_KEY{
		IM_TOKEN("im.token","IM token", 30 * 60 ) ,
		POP_PRINT("pop.print","POP快印信息", 60 * 60 ) ,
		LIMIT_LOGIN_KEY("ulbLimitLogin_","登录、注册、忘记密码",30*60),
		LIMIT_LOGIN_POP_KEY("popLimitLogin_","登录、注册、忘记密码",30*60);
		private String key ;
		private String memo ;
		private int timeToLive ;
		CACHED_KEY( String key , String memo , int timeToLive ){
			this.key = key ;
			this.memo = memo ;
			this.timeToLive = timeToLive ;
		}
		public String key(){
			return this.key ;
		}
		
		public int timeToLive(){
			return this.timeToLive ;
		}
	}
	
	public enum LOGIN_TYPE{
		LOGIN("login",1) ,//账号密码登录失败次数
		FORGETPASSWORD("forgetpassword",2),//忘记密码后-短信修改密码失败次数
		REGISTER("register",3),//注册失败次数
		SMSLOGIN("smslogin",4),//短信登录失败次数
		VERIFY("verify",5),//注册 -验证码验证失败 次数
		VERIFY_FORGET_PWD("verifyfpwd",12),//忘记验证码 -验证失败次数
		LOGIN_GET_VERIFY_NUM("logingetverifynum",6),//登录获取验证码次数
		FORGETPASSWORD_GET_VERIFY_NUM("forgetpasswordgetverifynum",7),//忘记密码获取验证码次数
		REGISTER_GET_VERIFY_NUM("registergetverifynum",8),//注册获取验证码次数
		LOGIN_GET_VERIFY_TIME("logingetverifytime",9),//前一次登录获取验证码时间戳
		FORGETPASSWORD_GET_VERIFY_TIME("logingetverifytime",10),//前一次忘记密码获取验证码时间戳
		REGISTER_GET_VERIFY_TIME("logingetverifytime",11);//前一次注册获取验证码时间戳
		
		 public static String getKeyByType(Integer type){
	        	if(type==null){
	        		return LOGIN_TYPE.LOGIN.getKey();
	        	}
	        	LOGIN_TYPE[] loginTypes = LOGIN_TYPE.values();
	        	for(LOGIN_TYPE loginType:loginTypes){
	        		if(loginType.getType().equals(type)){
	        			return loginType.getKey();
	        		}
	        	}
	        	return LOGIN_TYPE.LOGIN.getKey();
	        }
		
		private LOGIN_TYPE(String key, Integer type) {
			this.key = key;
			this.type = type;
		}
		private String key ;
		private Integer type ;
		public String getKey() {
			return key;
		}
		public Integer getType() {
			return type;
		}
	}
	
	
	public static class AdminConstant{
		public enum FILE_SIZE{
			IMG_1000( 1000 , 1000 ) ,
			IMG_800( 800 , 800 ) ,
			IMG_400( 400 , 400 ) ,
			IMG_158( 158 , 158 ) , 
			IMG_200( 200 , 200 ) ,
			IMG_150( 150 , 150 ) ,
			IMG_100( 100 , 100 ) ,
			IMG_105( 105 , 105 ) ,
			IMG_70( 70 , 70 );
			private int width ;
			private int height ;
			FILE_SIZE( int width , int height ){
				this.width = width ;
				this.height = height ;
			}
			public int getWidth() {
				return width;
			}
			public int getHeight() {
				return height;
			}
		}
	}
	
	public static class SiteConstant{
		public static final int ADV_POSITION_INDEX_BANNER = 1 ;//首页banner
		public static final int ADV_PLATEFORM_WEB = 1 ;//广告位
	}
	
	public enum SEARTCH_PRIFIX {
		PREFIX_S_("s_") ;
		private String value ;
		SEARTCH_PRIFIX( String value ){
			this.value = value ;
		}
		public String value() {
			return this.value ;
		}
	}
	
	//广告位置,数量以及类型映射
	public enum ADV_POSITION{
		WEB_BANNER(2 , 1 , 10 , "首页广告"),
		WEB_TREND_BANNER_LEFT( 2 ,2 , 1 , "首页趋势广告左"),
		WEB_TREND_BANNER_RIGHT( 2 ,3 , 1 , "首页趋势广告右"),
		WEB_HOME_PROMOTE_1( 2 ,4 , 1 , "首页推介广告1"),
		WEB_HOME_PROMOTE_2( 2 ,5 , 1 , "首页推介广告2"),
		WEB_HOME_MKTACTIVETY(2 ,6 , 4 , "首页活动位" ),
		
		APP_BANNER( 1 ,1 , 10 , "设计师APP首页广告"),
		APP_TREND_BANNER(1 , 2 , 2 , "面料商APP首页广告"),
		APP_TREND_LEFT( 1 ,3 , 2 , "首页趋势广告左"),
		APP_TREND_RIGHT( 1 ,4 , 2 , "首页趋势广告右"),
		APP_HOME_MKTACTIVETY(1 , 6 , 4 , "首页活动位" ),
		
		M_BANNER( 3 ,1 , 10 , "设计师APP首页广告"),
		M_TREND_BANNER(3 , 2 , 2 , "面料商APP首页广告"),
		M_TREND_LEFT( 3 ,3 , 2 , "首页趋势广告左"),
		M_TREND_RIGHT( 3 ,4 , 2 , "首页趋势广告右"),
		M_HOME_MKTACTIVETY(3 , 6 , 4 , "首页活动位" );	
		
		private int plateForm ;
		private int position ;
		private int size ;
		private String memo ;
		ADV_POSITION(int plateForm , int position , int size , String memo ){
			this.plateForm = plateForm ;
			this.position = position ;
			this.size = size ;
			this.memo = memo ;
		}
		
		public static ADV_POSITION  parse( int plateForm , int position ){
			ADV_POSITION[] positions = ADV_POSITION.values() ;
			for( ADV_POSITION item : positions ){
				if( item.getPlateForm() == plateForm && item.getPosition() == position ){
					return item ;
				}
			}
			return null ;
		}
		
		public int getPlateForm() {
			return plateForm;
		}

		public void setPlateForm(int plateForm) {
			this.plateForm = plateForm;
		}

		public int getPosition() {
			return position;
		}
		public int getSize() {
			return size;
		}
		public String getMemo() {
			return memo;
		}
	}
	public enum SYS_SOURCE {
		SYS_IOS("ios",2),
		SYS_ANDROID("android",3),
		SYS_WEB("web",1),
		SYS_MSITE("msite",4);
		private String value ;
        private Integer id;
		
        public static String getSysSource(String value){
        	if(value==null){
        		return SYS_SOURCE.SYS_WEB.value();
        	}
        	SYS_SOURCE[] sysSources = SYS_SOURCE.values();
        	for(SYS_SOURCE sysSource:sysSources){
        		if(sysSource.value().equals(value)){
        			return sysSource.value();
        		}
        	}
        	return SYS_SOURCE.SYS_WEB.value();
        }
        
		SYS_SOURCE( String value,Integer id ){
			this.value = value ;
			this.id = id;
		}
		public String value() {
			return this.value ;
		}
		public static SYS_SOURCE getSource(String str){
		    switch (str){
		    case "ios":
		        return SYS_SOURCE.SYS_IOS;
		    case "android":
		        return SYS_SOURCE.SYS_ANDROID;
		    case "msite":
		    	return SYS_SOURCE.SYS_MSITE;
		    default:
		       return      SYS_SOURCE.SYS_WEB;
		    }
		}
        public Integer id() {
            return id;
        }
	}
	/**
	 * 消息模板
	* @author dengbo 
	* @date 2017年1月10日 下午3:48:59
	 */
	public enum SMS_TEMPLATE {
		
		WITHDRAW_CASH(61254, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"优料宝提取现金","亲爱的用户，您的提现申请已处理完成。本次共提现%s元，已转入您尾号为$s的银行账户，请查收。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),//2个填空
		SELLER_REFUSED_TETURN(45301, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"卖家拒绝退货","%s拒绝了%s的退货申请，订单号%s若您仍有问题可拨打客服电话4008-233-090。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		BUYERS_APPLY_RETURN(45297, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"买家申请退货","%s购买的商品%s申请退货，订单号%s请尽快响应买家退货申请，3日后若无响应将默认同意退货。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,1,true,false,false,false, SMS_JUMP_TYPE.NONE),
		CANCELLATION_ORDER(45296, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"取消订单","%s已取消了订单，订单号%s取消原因%s","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		SELLER_AGREES_RETURN(45295, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"卖家同意退货","%s已同意您%s的退货申请，货款将在两个工作日内打入您指定的账户中。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		BUYER_CONFIRM_RECEIPT(45293, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"买家确认收货","%s在优料宝购买的商品%s已确认收货，货款将在两个工作日内打入您指定的账户中，请确认查收。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		SELLER_RECEIPT_NOT_CONFIRMED_(45291, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"通知卖家-买家未确认收货","%s在优料宝购买的商品%s超过7日未确认收货，系统已自动默认签收，货款将在两个工作日内打入您指定的账户中，请确认查收。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		BUYER_RECEIPT_NOT_CONFIRMED_(45290, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"通知买家-买家未确认收货","您在优料宝购买的商品%s超过7日未确认收货，系统已自动默认签收。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		FIND_FABIRC_MATCHING_NOTICE(45287, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"寻样匹配通知","您好%s您提交的寻样需求已经有面料商匹配到面料了，可以登陆POP账户管理中的优料宝相关查看寻样结果。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		SELLER_HAS_SHIPPED(45281, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"卖家-已发货","您在优料宝购买的商品%s已发货%s请注意查收并7日内确认收货。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		BUYER_PAYMENT_SUCCESS(45280, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"买家-付款成功","您的商品已被成功购买，%s请在24小时内尽快发货，%s如不即时发货，%s订单将于48小时后自动取消。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,1,true,false,false,false, SMS_JUMP_TYPE.NONE),
		TRADE_STATISTIC_ANALYSIS(45282, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"优料宝对账报警","系统在%s检测到平台余额差额不为零，差额为%s，请及时处理。","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		VERIFICATION_CODE(45284, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"验证码","您的验证码为%s，为保证您的资料安全，请勿告知他人。有疑问请致电：4008-233-090","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		
		CUSTOM_MESSAGE(10000, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"自定义消息","%s","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),//大汉三通提供
		PHONE_MESSAGE(99999, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"语音验证码","%s","",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),
		//新版消息推送模板
		
		//设计师版 https://tower.im/projects/9553afcca92c4f8b88edd3ca6d48c333/todos/b17ec5986783450497706cf18219e236/
		REGISTER_VERIFICATION_CODE(100001, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"注册：注册手机验证码，系统自动触发，渠道为短信",
				"尊敬的用户，请在5分钟内输入以下验证码：%s，继续完成注册。优料宝，专业面料交易平台。","【验证码】",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE), //web-ok、app
		RETRIEVE_PASSWORD(100002, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"找回密码：找回密码手机验证码，系统自动触发，渠道为短信",
				"尊敬的用户，请在5分钟内输入以下验证码：%s，以找回密码。优料宝，专业面料交易平台。","【验证码】",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE), //web-ok--需要web修改type、app
		NOT_PAY_NOTICE(100003,"订单未支付提醒","未支付提醒：订单生成后，15分钟未支付系统自动触发，渠道为推送、站内信，目标页我的订单中的待支付列表",
				"您的订单【%s】还未支付哦，超过24小时未支付订单将自动取消，现在去支付吧~","【面料名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,2,false,true,true,true, SMS_JUMP_TYPE.NOT_PAY_NOTICE),//定时任务
		ORDER_REVISE_PRICE_NOTICE(100004,"订单改价提醒","订单改价提醒：商家修改订单后，系统自动触发，渠道为推送、站内信，目标页我的订单中的待支付列表",
				"您的订单【%s】修改为%s%s元，快去支付吧~","【面料名称】【物流方式】【订单价格】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,2,true,true,true,true, SMS_JUMP_TYPE.ORDER_REVISE_PRICE),//web-ok
		DELIVER_GOODS_NOTICE(100005,"发货通知","发货通知：卖家发货时系统自动触发，渠道未推送、站内信，目标页我的订单中的待支付列表",
				"您的订单【%s】卖家已发货，单号【%s】【%s】，请注意查收~","【面料名称】【快递公司】【快递单号】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,2,true,true,true,true, SMS_JUMP_TYPE.DELIVER_GOODS_NOTICE),//app
		AUDIT_ADOPT_NOTICE(100006,"审核通知","审核通知：设计师发布求购后业务人员在管理后台进行审核成功/失败操作时触发，渠道为站内信",
				"您的求购【%s】已通过审核，请等待商家抢单","【求购名称】",1, SMS_TEMPLATE_TYPE.WANT_TO_BUY,2,false,false,true,true, SMS_JUMP_TYPE.NONE),//admin
		AUDIT_NOT_ADOPT_NOTICE(100007,"审核通知","审核通知:未通过",
				"您的求购【%s】审核未通过，【%s】，请修改后发布","【求购名称】【未通过原因】",1, SMS_TEMPLATE_TYPE.WANT_TO_BUY,2,false,false,true,true, SMS_JUMP_TYPE.NONE),//admin
		MATCHING_NOTICE(100008,"匹配通知","匹配通知：管理后台审核商家匹配求购通过时触发，渠道为站内信，目标页该条求购的求购详情",
				"您的求购【%s】已有商家匹配面料，现在查看~","【求购名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY_MATCHING,2,true,false,true,true, SMS_JUMP_TYPE.MATCHING_NOTICE),//admin
		REFUND_NOTICE(100009,"退款入账","退款入账：订单退款成功时触发卖家同意退款，渠道为站内信,目标页为我的钱包",
				"您的订单【%s】商家已同意退款，3个工作日内退款将进入您的钱包，请注意查收~","【面料名称】",1, SMS_TEMPLATE_TYPE.ASSET_TYPE,2,false,false,true,true, SMS_JUMP_TYPE.REFUND_NOTICE),//app
		WITH_DRAWALS_NOTICE(100010,"提现通知","提现通知：管理后台进行打款操作时触发，渠道为推送、站内信",
				"您的提现已打款，可在优料宝钱包中查看状态。资金到账已银行通知为准哦~","",1, SMS_TEMPLATE_TYPE.ASSET_TYPE,2,false,true,true,true, SMS_JUMP_TYPE.WITHDRAW_CASH_NOTICE),//admin
		COLOR_CARD(100011,"订单到付提醒","订单到付提醒",
				"亲，您成功支付的订单已准备发货，收货时快递小哥将向您收取运费哦！默认顺丰到付，有疑问联系商家或优料宝客服4008233090","",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,2,true,false,false,true, SMS_JUMP_TYPE.NONE),//app、web
		LOGIN_VERIFICATION_CODE(100012, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"短信登录：登录手机验证码，系统自动触发，渠道为短信",
				"尊敬的用户，请在5分钟内输入以下验证码：%s，继续完成登录。优料宝，专业面料交易平台。","【验证码】",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),

		//pop找图短信验证码
		POP_GRAPHIC_VERIFICATION_CODE(100013, SMS_TEMPLATE_TYPE.NO_TYPE.getCodeName(),"短信登录：登录手机验证码，系统自动触发，渠道为短信",
				"验证码：%s，验证码5分钟有效，请输入手机后进行验证。","【验证码】",0, SMS_TEMPLATE_TYPE.NO_TYPE,2,true,false,false,false, SMS_JUMP_TYPE.NONE),

		//给买家推荐专人服务
		RECOMMEND_FABRIC_SERVICE(300001,"推荐专人服务","推荐专人服务：求购信息系发布成功24小时后，发送服务短信给买家推荐专人服务，系统自动触发，渠道为短信",
				"昨天要找的面料找到了么？什么？还没有，快体验下专人一对一精准服务吧，%s?findId=%s","【H5链接、求购ID、...】",0, SMS_TEMPLATE_TYPE.WANT_TO_BUY,2,true,false,false,true, SMS_JUMP_TYPE.WANT_BUY_AUDIT),

		//求购面料审核成功发送短信给匹配的商家
		WANT_BUY_SEARCH(300002,"审核通知","审核通知：设计师发布求购后业务人员在管理后台进行审核成功/失败操作时触发，渠道为短信",
				"有人正在求购【%s】面料,快去响应吧!%s","【求购名称、H5链接、...】",0, SMS_TEMPLATE_TYPE.WANT_TO_BUY,1,true,false,false,false, SMS_JUMP_TYPE.WANT_BUY_AUDIT),//admin

		// 商家版
		ORDER_DELIVERY_REMIND(200001,"发货提醒","发货提醒:回调成功提醒发货,渠道为推送、站内信",
				"您的订单【%s】用户已支付，请尽快发货哟","【商品名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,1,true,true,true,true, SMS_JUMP_TYPE.ORDER_DELIVERY),//ok
		ORDER_DELIVERY_REMIND_ONE(200002,"发货提醒","订单发货提醒：订单支付完成时和距离超时发货自动退款仅剩1天时系统自动触发，渠道为推送、站内信；当同时检测到多条时，仅发送一条推送，目标页为订单管理中的待发货tab",
				"您的订单【%s】距离超时发货仅剩1天，再不发货就自动退款给买家了哟，现在去发货吧","【商品名称、商品名称、...】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,1,true,true,true,true, SMS_JUMP_TYPE.ORDER_DELIVERY),//定时任务
		ORDER_CHANGE_PRICE(200003,"订单改价提醒","订单改价提醒：用户订单提交20分钟后未支付，系统自动触发，渠道为推送、站内信",
				"您的订单【%s】用户已下单，可以对订单修改价格及物流方式咯~","【商品名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,1,false,true,true,true, SMS_JUMP_TYPE.ORDER_CHANGE_PRICE),//定时任务
		ORDER_TAKE_DELIVERY(200004,"订单收货提醒","订单收货提醒：订单确认收货（系统自动收货、买家确认收货）时系统自动触发，渠道为站内信，目标页为订单管理中的退款／售后tab",
				"您的订单【%s】已确认收货，3天后订单账款即可提现，请注意查收。","【商品名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,1,false,false,true,true, SMS_JUMP_TYPE.NONE),//定时任务、app
		ORDER_CUSTOMER_SERVICE(200005,"订单售后提醒","订单售后提醒：买家申请退款时系统自动触发，渠道为推送、站内信",
				"买家对订单【%s】发起了退款申请，请及时处理","【商品名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,1,true,true,true,true, SMS_JUMP_TYPE.ORDER_REFUND),//app
		ORDER_CUSTOMER_SERVICE_GOODS(200006,"订单售后提醒","订单售后提醒：买家申请退货时系统自动触发，渠道为推送、站内信",
				"买家对订单【%s】发起了退货申请，请及时处理","【商品名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY,1,true,true,true,true, SMS_JUMP_TYPE.ORDER_RETURN_GOODS),//app
		WANT_BUY_AUDIT(200007,"求购匹配审核","求购匹配审核：卖家匹配求购请求审核通过时系统自动触发，渠道为站内信，目标页为求购匹配中的审核通过tab",
				"您对求购【%s】匹配的商品已通过审核，请查看","【求购名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY_MATCHING,1,false,false,true,true, SMS_JUMP_TYPE.WANT_BUY_AUDIT),//admin
		WANT_BUY_SELECTED(200008,"求购匹配选中","求购匹配选中：卖家匹配求购被买家选中时系统自动触发，渠道为推送、站内信",
				"您对求购【%s】匹配的商品已被设计师选中，请及时联系设计师哦","【求购名称】",1, SMS_TEMPLATE_TYPE.ORDER_TO_BUY_MATCHING,1,true,true,true,true, SMS_JUMP_TYPE.NONE),//admin
		WITHDRAW_CASH_NOTICE(200009,"入账通知","入账通知：订单完成后订单金额可提现时系统自动触发，渠道为站内信，目标页为我的钱包",
				"您的订单【%s】已完成，现在去提现吧","【商品名称】",1, SMS_TEMPLATE_TYPE.ASSET_TYPE,1,false,false,true,true, SMS_JUMP_TYPE.WITHDRAW_CASH_NOTICE),//admin
		PLAY_MONEY_NOTICE(200010,"打款通知","打款通知：管理后台进行打款操作时触发，渠道为推送、站内信",
				"您的【%s】的提现账款已打款，2-3个工作日到账，请注意查收","【提现金额】",1, SMS_TEMPLATE_TYPE.ASSET_TYPE,1,false,true,true,true, SMS_JUMP_TYPE.NONE),//admin
		
		
		AUDIT_DOES_NOT_PASS(200011,
				"面料审核不通过",
				"面料审核不通过：管理后台面料审核操作不通过时触发，渠道为站内信,目标页面料管理中的我审核未通过列表",
				"您的面料【%s】审核失败，原因为【%s】，请修改后重新提交审核",
				"【面料名称】【失败原因】",
				1,
				SMS_TEMPLATE_TYPE.FABRIC_TYPE,
				1,
				false,false,true,true,
				SMS_JUMP_TYPE.AUDIT_DOES_NOT_PASS),//admin
		
		FABRIC_DOWN(200012,"面料被下架","管理后台面料操作下架时触发，渠道为站内信，目标页面料管理中的列已下架列表",
				"您的面料【%s】已被下架，请修改后重新上架，任何疑问咨询客服4008233090。","【面料名称】",1, SMS_TEMPLATE_TYPE.FABRIC_TYPE,1,false,false,true,true, SMS_JUMP_TYPE.FABRIC_DOWN),//admin
		
		//企业/个人店铺认证审核通过-极光、站内信
		SHOP_AUTHC_SUCCESS_PUSH(200013,
				"店铺认证审核通过",
				"触发条件及目标页说明：触发条件：后台审核人员审核选择通过时系统自动触发，渠道：推送、站内信；目标页（推送、站内信）：app首页",
				"您的【认证类型】认证通过审核啦，赶紧去完善店铺信息，发布您的首款面料吧~",
				"【认证类型】",
				1,
				SMS_TEMPLATE_TYPE.NOTIFICATION_TYPE,
				1,
				false,true,true,true,
				SMS_JUMP_TYPE.SHOP_AUTHC_SUCCESS_PUSH),
		//企业/个人铺认证审核通过-短信
		SHOP_AUTHC_SUCCESS_SMS(200014,
				"店铺认证审核通过",
				"触发条件及目标页说明：触发条件：后台审核人员审核选择通过时系统自动触发，渠道：短信，推送至注册手机号码；当办理人手机号码与注册手机号码不一致，则两个手机号码都推送",
				"【优料宝】尊敬的用户，您的【认证类型】认证通过审核啦，赶紧去商家首页发布您的首款面料吧~",
				"【认证类型】",
				1,
				SMS_TEMPLATE_TYPE.NOTIFICATION_TYPE,
				1,
				true,false,false,true,
				SMS_JUMP_TYPE.NONE),
		//企业/个人铺认证审核未通过-极光、站内信
		SHOP_AUTHC_FAILED_PUSH(200015,
				"店铺认证审核未通过",
				"触发条件及目标页说明：后台审核人员审核选择不通过时系统自动触发，渠道：推送、站内信，目标页（推送、站内信）：认证信息编辑页",
				"【认证类型】认证审核未通过，【未通过原因】，请修改后重新提交审核",
				"【认证类型】【未通过原因】",
				1,
				SMS_TEMPLATE_TYPE.NOTIFICATION_TYPE,
				1,
				false,true,true,true,
				SMS_JUMP_TYPE.SHOP_AUTHC_FAILED_PUSH),
		//企业/个人铺认证审核未通过-极光、站内信
		SHOP_AUTHC_FAILED_MSG(200016,
				"店铺认证审核未通过",
				"触发条件及目标页说明：后台审核人员审核选择不通过时系统自动触发，渠道：短信，推送至注册手机号码；当办理人手机号码与注册手机号码不一致，则两个手机号码都推送",
				"【优料宝】尊敬的用户，您的【认证类型】认证未通过审核，进入商家首页可查看未通过原因，如有疑问请致电400-823-3090",
				"【认证类型】",
				1,
				SMS_TEMPLATE_TYPE.NOTIFICATION_TYPE,
				1,
				true,false,false,true,
				SMS_JUMP_TYPE.NONE);

		
		private int code;//短信类型编码
		private String title;//标题
		private String memo;//描述
		private String template;//模板
		private String content;//内容
		private int num;//数量
		private SMS_TEMPLATE_TYPE type;//分类
		private int option;//1:卖家，2：买家
		private boolean isSms;//是否发送短信
		private boolean isJpush;//是否极光推送
		private boolean isStation;//是否站内信
		private boolean isDelayPush;//是否延迟发送 22点到第二天8点中间的短信消息延迟到第二天8点以后发送。
		private SMS_JUMP_TYPE smsJumpType;
		
		
		
		/**
		 * 获取模板的get方法
		 * @param codeNum
		 * @return
		 */
		public static SMS_TEMPLATE getSmsTemplate(int codeNum){
				SMS_TEMPLATE [] smsTemplates = SMS_TEMPLATE.values() ;
				for( SMS_TEMPLATE smsTemplate : smsTemplates ){
					if( codeNum == smsTemplate.getCode() ) {
						return smsTemplate ;
					}
				}
			return null ;
		}
		
		
		private SMS_TEMPLATE(int code, String title, String memo, String template, String content, int num,
				SMS_TEMPLATE_TYPE type, int option, boolean isSms, boolean isJpush, boolean isStation,
				boolean isDelayPush, SMS_JUMP_TYPE smsJumpType) {
			this.code = code;
			this.title = title;
			this.memo = memo;
			this.template = template;
			this.content = content;
			this.num = num;
			this.type = type;
			this.option = option;
			this.isSms = isSms;
			this.isJpush = isJpush;
			this.isStation = isStation;
			this.isDelayPush = isDelayPush;
			this.smsJumpType = smsJumpType;
		}

		public int getCode() {
			return code;
		}
		public String getTitle() {
			return title;
		}
		public String getMemo() {
			return memo;
		}
		public String getTemplate() {
			return template;
		}
		public String getContent() {
			return content;
		}
		public int getNum() {
			return num;
		}
		public SMS_TEMPLATE_TYPE getType() {
			return type;
		}

		public boolean isSms() {
			return isSms;
		}

		public boolean isJpush() {
			return isJpush;
		}

		public boolean isStation() {
			return isStation;
		}

		public int getOption() {
			return option;
		}

		public boolean isDelayPush() {
			return isDelayPush;
		}

		public SMS_JUMP_TYPE getSmsJumpType() {
			return smsJumpType;
		}

		public void setSmsJumpType(SMS_JUMP_TYPE smsJumpType) {
			this.smsJumpType = smsJumpType;
		}
		
	}

	/**
	 * 消息推送页面跳转
	* @author dengbo 
	* @date 2017年1月12日 上午11:29:32
	 */
	public enum SMS_JUMP_TYPE{
		NONE("默认无","无","无",null,0),
		//商家
		ORDER_DELIVERY("订单发货提醒","目标页为订单管理中的待发货tab","推送、站内信","action:orderDelivery;option:1;orderId:",1),
		ORDER_CHANGE_PRICE("订单改价提醒","订单管理中的待支付tab","推送、站内信","action:orderChangePrice;option:1;orderId:",2),
		ORDER_REFUND("订单售后提醒：买家申请退款","订单管理中的退款tab","推送、站内信","action:orderRefund;option:1;orderId:",3),
		ORDER_RETURN_GOODS("订单售后提醒：买家申请退货","订单管理中的售后tab","推送、站内信","action:orderReturnGoods;option:1;orderId:",4),
		WANT_BUY_AUDIT("求购匹配审核","求购匹配中的审核通过tab","站内信",null,5),
		WITHDRAW_CASH_NOTICE("入账通知","我的钱包","站内信",null,6),
		AUDIT_DOES_NOT_PASS("面料审核不通过","面料管理中的我审核未通过列表","站内信",null,7),
		FABRIC_DOWN("面料被下架","面料管理中的列已下架列表","站内信",null,8),
		SHOP_AUTHC_SUCCESS_PUSH("认证审核通过","app首页","推送、站内信",null,14),
		SHOP_AUTHC_FAILED_PUSH("认证审核未通过","认证信息编辑页面","推送、站内信",null,15),
		
		//设计师
		NOT_PAY_NOTICE("未支付提醒","待支付列表","推送、站内信","action:notPayNotice;option:2;orderId:",9),
		ORDER_REVISE_PRICE("订单改价提醒","我的订单中的待支付列表","推送、站内信","action:orderRevisePrice;option:2;orderId:",10),
		DELIVER_GOODS_NOTICE("发货通知","我的订单中的待收货列表","推送、站内信","action:deliverGoods;option:2;orderId:",11),
		MATCHING_NOTICE("匹配通知（求购）","求购详情","站内信",null,12),//带id
		REFUND_NOTICE("退款入账","我的钱包","站内信",null,13);
		
		private String name;
		private String memo;//目标页
		private String channel;//渠道
		private String extras;//极光推送信息
		private int type;//站内信跳转
		
		private SMS_JUMP_TYPE(String name, String memo, String channel, String extras, int type) {
			this.name = name;
			this.memo = memo;
			this.channel = channel;
			this.extras = extras;
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public String getMemo() {
			return memo;
		}
		public String getExtras() {
			return extras;
		}
		public int getType() {
			return type;
		}
		public String getChannel() {
			return channel;
		}
		
	}
	
	/**
	 * 消息模板分类
	* @author dengbo 
	* @date 2017年1月10日 上午11:23:27
	 */
	public enum SMS_TEMPLATE_TYPE{
		ORDER_TO_BUY(0,"订单类"),
		WANT_TO_BUY(1,"求购审核类"),
		ORDER_TO_BUY_MATCHING(2,"求购匹配类"),
		NOTIFICATION_TYPE(3,"通知类消息"),
		ASSET_TYPE(4,"我的资产类消息-进账"),
		ASSET_TYPE_OUT(5,"我的资产类消息-出账"),
		FABRIC_TYPE(6,"面料类消息"),
		NO_TYPE(7,"优料宝通知");
		
		private SMS_TEMPLATE_TYPE(int code, String codeName) {
			this.code = code;
			this.codeName = codeName;
		}
		private int code;
		private String codeName;
		public int getCode() {
			return code;
		}
		public String getCodeName() {
			return codeName;
		}
	}


	public enum POP_CLASSIFY_KEY{
		PATTERN_CONTENT("sPat","图案内容"),
		GENDER("sGen","性别"),
		FORMAT("sFor","图案格式"),
		APPLICATION("sApp","图案应用"),
		TECHNOLOGY("sTec","图案工艺");
		private POP_CLASSIFY_KEY(String key, String keyName) {
			this.key = key;
			this.keyName = keyName;
		}
		private String key;
		private String keyName;
		public String getKey() {
			return key;
		}
		public String getKeyName() {
			return keyName;
		}
	}
	
	/**
	 * 交易业务编号
	 * @author dengbo
	 * @date 2016年8月23日 下午2:20:43
	 */
	public enum TRADE_SERVICE_NUMBER{
		PRODUCT_TRADE_BYMBER("10","商品交易编号"),
		RECHARGE_TRADE_BYMBER("11","充值");
		private TRADE_SERVICE_NUMBER(String code, String codeName) {
			this.code = code;
			this.codeName = codeName;
		}
		private String code;
		
		private String codeName;

		public String getCode() {
			return code;
		}

		public String getCodeName() {
			return codeName;
		}
	}
	
	
	/**
	 * 系统配置枚举 sys_config表
	* @Description: TODO
	* @author dengbo 
	* @date 2016年9月9日 下午4:49:47
	 */
	public enum SYS_CONFIG{
		EXHIBITION_REGISTER(1,"展厅报名配置"),
		WECAHT_CONFIG(2,"微信参数配置"),
		SHARE_SHOP_TITLE(3,"分享店铺标题配置"),
		SHARE_SHOP_CONTENT(4,"分享店铺内容配置"),
		SHARE_SHOP_PIC(5,"分享店铺图片配置"),
		SHOP_SIGNAGE_PIC_DEFULT_TEMPLATE(6,"店招系统匹配模板"),
		SHOP_SIGNAGE_PIC_TEMPLATE(7,"店招用户选择模板");
		private SYS_CONFIG(int code, String codeName) {
			this.code = code;
			this.codeName = codeName;
		}

		private int code;
		
		private String codeName;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getCodeName() {
			return codeName;
		}

		public void setCodeName(String codeName) {
			this.codeName = codeName;
		}

		
	}
	
	/**
	 * 关键词搜索日志类型 
	 */
	public enum KEYWORD_SEARCH_TYPE {
		FABRIC_KEYWORD(0, "面料关键字查询"),
		SHOP_KEYWORD(1,"店铺关键字查询"),
		TREND_TOPIC_KEYWORD(2,"趋势关键字查询");
		
		private KEYWORD_SEARCH_TYPE(int code, String memo) {
			this.code = code;
			this.memo = memo;
		}
		
		private int code;
		private String memo;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
	}
}
