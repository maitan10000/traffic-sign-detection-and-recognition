<%@page import="utility.Constants"%>
<%@page import="json.AccountJSON"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


<head>
<title>Hệ thống nhận dạng biển báo - Trang quản lý</title>
<link rel="shortcut icon" type="image/png" href="Admin/Content/images/favicon.png"/>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="Admin/Content/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="Admin/Content/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="Admin/Content/css/fullcalendar.css" />
<link rel="stylesheet" href="Admin/Content/css/maruti-style.css" />
<link rel="stylesheet" href="Admin/Content/css/maruti-media.css"
	class="skin-color" />
<!-- <link rel="stylesheet" href="Admin/Content/css/uniform.css" /> -->
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style type="text/css">
	.dataTables_filter {
		margin-left: 20px;
	}
	#table-show tbody .btn
	{
		width: 80px;
	}
	
	</style>
</head>
<%
	String jsonObject = (String) request.getAttribute("account");
	ArrayList<AccountJSON> listAccount = (ArrayList<AccountJSON>) request.getAttribute("listAccount");
	String role = (String) session.getAttribute(Constants.SESSION_ROLE);
%>

<body>
	<!--Header-part-->
	<div id="header">
		<h4>				
				<span>Hệ thống nhận dạng biển báo - Trang quản lý</span>
			</h4>
	</div>
	<!--close-Header-part-->

	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class=""><a title="" href="#"><i class="icon icon-user"></i>
					<span class="text"><%=(String) session.getAttribute(Constants.SESSION_USERID)%></span></a></li>
			<li class=""><a title="Đăng xuất" href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%>"><i
					class="icon icon-share-alt" onclick="logout()"></i> <span
					class="text">Đăng xuất</span></a></li>
		</ul>
	</div>

	<!--close-top-Header-menu-->
	
	<div id="sidebar">
	<a href="<%=Constants.CONTROLLER_ADMIN%>" class="visible-phone"><i class="icon icon-home"></i>
			Trang chủ</a>
		<ul>			
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>"><i
					class="icon icon-home"></i> <span>Trang chủ</span></a></li>
			<%
			if("staff".equals(role))
			{
			%>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFIC_LIST%>"><i class="icon icon-th"></i> <span>Quản
						lý biển báo</span></a></li>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_LIST%>"><i class="icon icon-exclamation-sign"></i> <span>Quản lý phản
						hồi</span></a></li>
			<%
			}
			%>
			
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_ACCOUNT_LIST%>"><i class="icon icon-user"></i> <span>Quản
						lý người dùng</span></a></li>			
			
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_STATISTIC%>"><i class="icon icon-signal"></i> <span>Thống
						kê</span></a></li>
			<%
			if("admin".equals(role))
			{
			%>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_CONFIG%>"><i class="icon icon-cog"></i> <span>Thiếp
						lập hệ thống</span></a></li>
			<%
			}
			%>
		</ul>
	</div>
	<!-- End slide bar -->
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_ACCOUNT_LIST%>" title="Quản lý người dùng" class="tip-bottom"><i
					class="icon-user"></i> Quản lý người dùng</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="widget-title">
				<span class="icon"><i class="icon-bookmark"></i></span>
				<h5>Quản lý người dùng</h5>
			</div>
			<%
				if( listAccount != null){
			%>
			
			<div id="table-show">
				<table id="account-table" class="table table-bordered dataTable">
					<thead>
						<tr>
							<th>Tên tài khoản</th>
							<th>Email</th>
							<th>Tên</th>
							<th>Loại tài khoản</th>
							<th>Thời gian tạo</th>
							<th></th>
							<%
							if("admin".equals(role))
							{
							%>
							 <th></th>
							<%
							} 
							%>
							
						</tr>
					</thead>
					<tbody>
						<%
							if( listAccount.size()> 0){for(int i = 0; i< listAccount.size();i++){
						%>
						<tr>
							<td><%=listAccount.get(i).getUserID()%></td>
							<td><%=listAccount.get(i).getEmail()%></td>
							<td><%=listAccount.get(i).getName()%></td>
							<td style="text-align: center;"><%=listAccount.get(i).getRole()%></td>
							<%
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							%>
							<td style="text-align: center;"><%=dateFormat.format(listAccount.get(i).getCreateDate())%></td>
							<%
								if(listAccount.get(i).getIsActive() == true){
							%>
							<td  style="text-align: center;"><button class="btn btn-danger btn-mini" href="#"
								onclick="deactiveAccount('<%=listAccount.get(i).getUserID()%>')">Khóa</button></td>
							<%
								}else if(listAccount.get(i).getIsActive() == false){
							%>
							<td  style="text-align: center;"><button class="btn btn-success btn-mini" href="#"
								onclick="activeAccount('<%=listAccount.get(i).getUserID()%>')">Kích hoạt</button></td>
							<%
								}
							%>
							
							<%
							if("admin".equals(role))
							{
								if("staff".equals(listAccount.get(i).getRole())){
							%>
							<td style="text-align: center;"><button class="btn btn-primary btn-mini" href="#"
								onclick="unsetStaffAccount('<%=listAccount.get(i).getUserID()%>')">Hạ quyền</button></td>
							<%
								}else if("user".equals(listAccount.get(i).getRole())){
							%>
							<td  style="text-align: center;"><button class="btn btn-success btn-mini" href="#"
								onclick="setStaffAccount('<%=listAccount.get(i).getUserID()%>')">Nâng quyền</button></td>
							<%
								}
							}//end if admin
							%>
							
						</tr>
						<%
							} 
																																																																																									}
						%>
					</tbody>
				</table>
			</div>
			<%
				}
			%>			
		</div>
	</div>
	<div class="row-fluid">
		<div id="footer" class="span12">
			<p>
				<b>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</b>
			</p>
			<p>"Hệ thống giúp đỡ người dùng tra cứu, học tập biển báo giao
				thông."</p>
		</div>
	</div>
<script src="Admin/Content/js/excanvas.min.js"></script>
<script src="Admin/Content/js/jquery.min.js"></script>
<script src="Admin/Content/js/jquery.ui.custom.js"></script>
<script src="Admin/Content/js/bootstrap.min.js"></script>
<script src="Admin/Content/js/jquery.flot.min.js"></script>
<script src="Admin/Content/js/jquery.flot.resize.min.js"></script>
<script src="Admin/Content/js/jquery.peity.min.js"></script>
<script src="Admin/Content/js/fullcalendar.min.js"></script>
<script src="Admin/Content/js/maruti.js"></script>
<script src="Admin/Content/js/maruti.dashboard.js_bk"></script>
<script src="Admin/Content/js/maruti.calendar.js"></script>
<!-- <script src="Admin/Content/js/jquery.uniform.js"></script> -->
<script src="Admin/Content/js/select2.min.js"></script>
<script src="Admin/Content/js/jquery.dataTables.min.js"></script>
<!-- <script src="Admin/Content/js/maruti.tables.js"></script> -->
<script src="Admin/Content/js/tsrt.main.js"></script>
<script type="text/javascript">

function deactiveAccount(userID) {
	var action = '<%=Constants.ACTION_ACCOUNT_DEACTIVE%>';
	$.ajax({	url : '<%=Constants.CONTROLLER_ADMIN%>',
				type : "GET",
				data : {action : action,userID : userID},
				success : function(result) {
					// if delete ok, change buuton to luu bien bao
					if ("Success" == result.trim()) {					
						location.reload();
					}
				}

			});
}

function activeAccount(userID) {
	var action = '<%=Constants.ACTION_ACCOUNT_ACTIVE%>';
	$.ajax({
				url : '<%=Constants.CONTROLLER_ADMIN%>',
				type : "GET",
				data : {
					action : action,
					userID : userID
				},
				success : function(result) {
					// if delete ok, change buuton to luu bien bao
					if ("Success" == result.trim()) {
						location.reload();
					}
				}

			});
}

function setStaffAccount(userID) {
	var action = '<%=Constants.ACTION_ACCOUNT_SETSTAFF%>';
	$.ajax({	url : '<%=Constants.CONTROLLER_ADMIN%>',
				type : "GET",
				data : {action : action,userID : userID},
				success : function(result) {
					// if delete ok, change buuton to luu bien bao
					if ("Success" == result.trim()) {					
						location.reload();
					}
				}

			});
}

function unsetStaffAccount(userID) {
	var action = '<%=Constants.ACTION_ACCOUNT_UNSETSTAFF%>';
	$.ajax({	url : '<%=Constants.CONTROLLER_ADMIN%>',
				type : "GET",
				data : {action : action,userID : userID},
				success : function(result) {
					// if delete ok, change buuton to luu bien bao
					if ("Success" == result.trim()) {					
						location.reload();
					}
				}

			});
}
</script>
</body>
</html>