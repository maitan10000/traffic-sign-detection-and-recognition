package com.trafficsign.ultils;

public final class Properties {
//	public static final String serviceIp = "http://bienbaogiaothong.tk/rest/Service/";
//	public static final String serverAddress = "http://bienbaogiaothong.tk/";
	public static final String serviceIpLocal = "http://192.168.0.102:8080/TrafficService/";
	public static final String serviceIpOnline = "http://bienbaogiaothong.tk/TrafficService/";

	public static boolean isTaken = false;
	
	//Username 
	public static String USER_NAME = "user1";
	// Internet setting
	public static int INTERNET_SETTING = 0;
	
	//Share preference constant
	public static final String SHARE_PREFERENCE_LOGIN = "pref_login";
	public static final String SHARE_PREFERENCE_SETTING = "pref_setting";
	public static final String SHARE_PREFERENCE_KEY_WIFI = "wifiSetting";
	public static final String SHARE_PREFERENCE__KEY_NOTI = "notiSetting";
	public static final String SHARE_PREFERENCE__KEY_SERVER = "serverSetting";
	public static final String SHARE_PREFERENCE__KEY_USER = "userID";
	public static final String SHARE_PREFERENCE__KEY_USER_SYNC = "isSync";
	public static final String SHARE_PREFERENCE__KEY_TRAFFIC_SYNC = "isSyncTraffic";
	public static final String SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_ALARM = "isUpdate";
	public static final String SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_TIME = "timeUpdate";
	public static final String SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_RUNNING = "trafficUpdateRunning";
	// properties file constant
	public static final String PROPERTIES_KEY_SERVER = "server";
	public static final String PROPERTIES_VALUE_ONLINE = "online";
	public static final String PROPERTIES_VALUE_OFFLINE = "offline";
	//folder constant
	public static final String APP_FOLDER = "TSRT/";
	public static final String SAVE_IMAGE_FOLDER = "save image/";
	public static final String MAIN_IMAGE_FOLDER = "main image/";
	public static final String DB_FILE_PATH = "traffic_sign.db";
	public static final String SETTING_FILE_PATH = "setting.properties";
	
	
	/*SERVICE CONSTANT*/
	// for list all category
	public final static String TRAFFIC_LIST_CATEGORY = "rest/Traffic/ListAllCategory";

	// for search manual
	public final static String TRAFFIC_SEARCH_MANUAL = "rest/Traffic/SearchManual";
	public final static String TRAFFIC_SEARCH_AUTO = "rest/Traffic/SearchAuto";

	// for view traffic detail
	public final static String TRAFFIC_TRAFFIC_VIEW = "rest/Traffic/ViewDetail";
	public final static String TRAFFIC_TRAFFIC_ADD = "rest/Traffic/AddTrafficInfo";

	// Favorite
	public final static String MANAGE_FAVORITE_CHECK = "rest/Manage/CheckFavorite";
	public final static String MANAGE_FAVORITE_ADD = "rest/Manage/AddFavorite";
	public final static String MANAGE_FAVORITE_DELETE = "rest/Manage/DeleteFavorite";
	public final static String MANAGE_FAVORITE_LIST = "rest/Manage/ListFavorite";

	// for view history
	public final static String TRAFFIC_HISTORY_LIST = "rest/Traffic/ListHistory";
	public final static String TRAFFIC_HISTORY_VIEW = "rest/Traffic/ViewHistory";
	public final static String TRAFFIC_HISTORY_DELETE = "rest/Traffic/DeleteHistory";

	// Train Image
	public final static String TRAFFIC_TRAIN_IMAGE_ADD_FROM_REPORT = "rest/Traffic/AddTrainImageFromReport";

	// Report Service
	public final static String MANAGE_REPORT_LIST = "rest/Manage/ListReportByType";
	public final static String MANAGE_REPORT_VIEW = "rest/Manage/GetReportDetail";
	public final static String MANAGE_REPORT_DELETE = "rest/Manage/DeleteReport";
	public final static String MANAGE_REPORT_SEND = "rest/Manage/SendReport";

	// for register
	public final static String MANAGE_REGISTER = "rest/Manage/Register";

	// for login
	public final static String MANAGE_LOGIN = "rest/Manage/Login";
	


}

