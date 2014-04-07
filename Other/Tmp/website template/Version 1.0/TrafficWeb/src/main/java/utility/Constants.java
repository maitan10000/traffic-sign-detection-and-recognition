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

	// for traffic info
	public final static String TRAFFIC_TRAFFIC_VIEW = "rest/Traffic/ViewDetail";
	public final static String TRAFFIC_TRAFFIC_ADD = "rest/Traffic/AddTrafficInfo";
	public final static String TRAFFIC_TRAFFIC_EDIT = "rest/Traffic/EditTrafficInfo";	
	public final static String TRAFFIC_TRAFFIC_LIST = "rest/Traffic/ListTrafficByCateID";
	public final static String TRAFFIC_TRAFFIC_DELETE = "rest/Traffic/DeleteTrafficInfo";
	
	// for traffic train image
	public final static String TRAFFIC_TRAFFIC_TRAIN_IMAGE_ADD = "rest/Traffic/AddTrainImage";
	public final static String TRAFFIC_TRAFFIC_TRAIN_IMAGE_LIST = "rest/Traffic/ListTrainImageByTrafficID";
	public final static String TRAFFIC_TRAFFIC_TRAIN_IMAGE_DELETE = "rest/Traffic/DeleteTrainImageByID";
	
	// for view history
	public final static String TRAFFIC_LIST_HISTORY = "rest/Traffic/ListHistory";
	public final static String TRAFFIC_HISTORY_VIEW = "rest/Traffic/ViewHistory";
	
	// Train Image
	public final static String TRAFFIC_TRAIN_IMAGE_ADD_FROM_REPORT = "rest/Traffic/AddTrainImageFromReport";

	// Favorite
	public final static String MANAGE_FAVORITE_CHECK = "rest/Manage/CheckFavorite";
	public final static String MANAGE_FAVORITE_ADD = "rest/Manage/AddFavorite";
	public final static String MANAGE_FAVORITE_DELETE = "rest/Manage/DeleteFavorite";
	public final static String MANAGE_FAVORITE_LIST = "rest/Manage/ListFavorite";
	// Export, import
	public final static String SERVER_IMPORT = "rest/Server/ImportFile";
	public final static String SERVER_EXPORT = "rest/Server/ExportFile";

	// for view history
	public final static String TRAFFIC_LIST_HISTORY = "rest/Traffic/ListHistory";
	public final static String TRAFFIC_HISTORY_VIEW = "rest/Traffic/ViewHistory";
	public final static String TRAFFIC_HISTORY_DELETE = "rest/Traffic/DeleteHistory";

	// Train Image
	public final static String TRAFFIC_TRAIN_IMAGE_ADD_FROM_REPORT = "rest/Traffic/AddTrainImageFromReport";

	// action constant
	public final static String ACTION_SEARCH_MANUAL = "searchManual";
	public final static String ACTION_SEARCH_AUTO = "searchAuto";	
	public final static String ACTION_TRAIN_IMAGE_ADD_FROM_REPORT = "addTrainImageFromReport";
	public final static String ACTION_FAVORITE_VIEW = "viewFavorite";
	public final static String ACTION_FARVORITE_VIEW_SHORT = "viewFavoriteShort";
	public final static String ACTION_HISTORY_VIEW = "viewHistory";
	public final static String ACTION_HISTORY_LIST = "listHistory";
	public final static String ACTION_HISTORY_LIST_SHORT = "listHistoryShort";
	public final static String ACTION_ADD_TRAINIMAGE = "trainImage";
	public final static String ACTION_DELETE_TRAINIMAGE = "deleteTrainImage";

	// Admin controler action
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
	public final static String ACTION_ACCOUNT_FORGOTPASSWORD = "forgotpassword";

	public final static String ACTION_TRAFFIC_ADD = "addTraffic";
	public final static String ACTION_TRAFFIC_EDIT = "editTraffic";
	public final static String ACTION_TRAFFIC_LIST = "listTraffic";
	public final static String ACTION_TRAFFIC_DELETE = "deleteTraffic";
	
	
	public final static String ACTION_TRAFFIC_VIEWTRAINIMAGE = "viewTrainImage";
	

	// controller
	public final static String CONTROLLER_USER = "/TrafficWeb/UserController";
	public final static String CONTROLLER_ADMIN = "/TrafficWeb/AdminController";

	// Report Service
	public final static String MANAGE_REPORT_LIST = "rest/Manage/ListReportByType";
	public final static String MANAGE_REPORT_VIEW = "rest/Manage/GetReportDetail";
	public final static String MANAGE_REPORT_DELETE = "rest/Manage/DeleteReport";
	public final static String MANAGE_REPORT_SEND = "rest/Manage/SendReport";

	// register, login
	public final static String MANAGE_REGISTER = "rest/Manage/Register";
	public final static String MANAGE_LOGIN = "rest/Manage/Login";

	// for inActive Report

	// Account Service
	public final static String MANAGE_ACCOUNT_LIST = "rest/Manage/ListAllAccount";
	public final static String MANAGE_ACCOUNT_DEACTIVE = "rest/Manage/Deactive";
	public final static String MANAGE_ACCOUNT_ACTIVE = "rest/Manage/Active";
	public final static String MANAGE_ACCOUNT_SETSTAFF = "rest/Manage/SetStaff";
	public final static String MANAGE_ACCOUNT_UNSETSTAFF = "rest/Manage/UnsetStaff";
	public final static String MANAGE_ACCOUNT_SENDMAIL = "rest/Manage/SendMail";
	public final static String MANAGE_ACCOUNT_FORGOTPASSWORD = "rest/Manage/ForgotPassword";

	// Session Constant
	public final static String SESSION_USERID = "userid";
	public final static String SESSION_ROLE = "role";

}
