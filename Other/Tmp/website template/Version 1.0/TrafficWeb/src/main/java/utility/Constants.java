package utility;

public final class Constants {
	// Error page
	public final static String ERROR_PAGE = "";

	// Session user login timeout or not login yet page
	public final static String SESSION_ERROR_PAGE = "";

	// for list all category
	public final static String TRAFFIC_LIST_CATEGORY = "rest/Traffic/ListAllCategory";

	// for search manual
	public final static String TRAFFIC_SEARCH_MANUAL = "rest/Traffic/SearchManual";
	public final static String TRAFFIC_SEARCH_AUTO = "rest/Traffic/SearchAuto";

	// for view traffic detail
	public final static String TRAFFIC_TRAFFIC_VIEW = "rest/Traffic/ViewDetail";
	public final static String TRAFFIC_TRAFFIC_ADD = "rest/Traffic/AddTrafficInfo";
	public final static String TRAFFIC_TRAFFIC_UPDATE = "rest/Traffic/UpdateTrafficInfo";
	public final static String TRAFFIC_TRAFFIC_LIST = "rest/Traffic/ListTrafficByCateID";
	

	// Favorite
	public final static String MANAGE_FAVORITE_CHECK = "rest/Manage/CheckFavorite";
	public final static String MANAGE_FAVORITE_ADD = "rest/Manage/AddFavorite";
	public final static String MANAGE_FAVORITE_DELETE = "rest/Manage/DeleteFavorite";
	public final static String MANAGE_FAVORITE_LIST = "rest/Manage/ListFavorite";

	// for view history
	public final static String TRAFFIC_LIST_HISTORY = "rest/Traffic/ListHistory";
	public final static String TRAFFIC_HISTORY_VIEW = "rest/Traffic/ViewHistory";

	// Train Image
	public final static String TRAFFIC_TRAIN_IMAGE_ADD_FROM_REPORT = "rest/Traffic/AddTrainImageFromReport";

	// action constant
	public final static String ACTION_SEARCH_MANUAL = "searchManual";
	public final static String ACTION_TRAFFICINFO_ADD = "addTrafficInfo";
	public final static String ACTION_SEARCH_AUTO = "searchAuto";
	public final static String ACTION_TRAIN_IMAGE_ADD_FROM_REPORT = "addTrainImageFromReport";
	public final static String ACTION_FAVORITE_VIEW = "viewFavorite";
	public final static String ACTION_FARVORITE_VIEW_SHORT = "viewFavoriteShort";
	public final static String ACTION_HISTORY_VIEW = "viewHistory";
	public final static String ACTION_HISTORY_LIST = "listHistory";
	
	//Admin controler action
	public final static String ACTION_LOGIN = "login";
	public final static String ACTION_REGISTER = "register";
	public final static String ACTION_LOGOUT = "logout";
	
	public final static String ACTION_REPORT_LIST = "listReport";
	public final static String ACTION_REPORT_VIEW = "viewReport";
	public final static String ACTION_REPORT_DELETE = "deleteReport";
	
	public final static String ACTION_ACCOUNT_LIST = "listAccount";
	public final static String ACTION_ACCOUNT_DEACTIVE = "deactiveAcccount";
	public final static String ACTION_ACCOUNT_ACTIVE = "activeAccount";
	public final static String ACTION_ACCOUNT_SETSTAFF = "setStaff";
	public final static String ACTION_ACCOUNT_UNSETSTAFF = "unsetStaff";
	
	public final static String ACTION_TRAFFIC_VIEW = "viewTraffic";
	public final static String ACTION_TRAFFIC_LIST = "listTraffic";
	
	
	
	
	// controller
	public final static String CONTROLLER_USER = "/TrafficWeb/UserController";
	public final static String CONTROLLER_ADMIN = "/TrafficWeb/AdminController";

	// Report Service
	public final static String MANAGE_REPORT_LIST = "rest/Manage/ListReportByType";
	public final static String MANAGE_REPORT_VIEW = "rest/Manage/GetReportDetail";
	public final static String MANAGE_REPORT_DELETE = "rest/Manage/DeleteReport";
	public final static String MANAGE_REPORT_SEND = "rest/Manage/SendReport";

	// for register
	public final static String MANAGE_REGISTER = "rest/Manage/Register";

	// for login
	public final static String MANAGE_LOGIN = "rest/Manage/Login";

	// for inActive Report

	// Account Service
	public final static String MANAGE_ACCOUNT_LIST = "rest/Manage/ListAllAccount";
	public final static String MANAGE_ACCOUNT_DEACTIVE = "rest/Manage/Deactive";
	public final static String MANAGE_ACCOUNT_ACTIVE = "rest/Manage/Active";
	public final static String MANAGE_ACCOUNT_SETSTAFF = "rest/Manage/SetStaff";
	public final static String MANAGE_ACCOUNT_UNSETSTAFF = "rest/Manage/UnsetStaff";
	
	//Session Constant
	public final static String SESSION_USERID = "userid";
	public final static String SESSION_ROLE = "role";
	
}
