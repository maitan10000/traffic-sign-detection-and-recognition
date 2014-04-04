<%@page import="utility.Constants"%>
<%@page import="model.Account"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


<!-- Mirrored from themedesigner.in/demo/maruti-admin/index2.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 24 Mar 2014 10:09:23 GMT -->
<head>
<title>HỆ THỐNG NHẬN DIỆN BIỂN BÁO - TRANG QUẢN LÝ</title>
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
</head>
<%
	String jsonObject = (String) request.getAttribute("account");
	ArrayList<Account> listAccount = (ArrayList<Account>) request.getAttribute("listAccount");
%>

<body>
	<!--Header-part-->
	<div id="header">
		<h4>
			<a href="dashboard.html">HỆ THỐNG NHẬN DIỆN BIỂN BÁO - TRANG QUẢN
				LÝ</a>
		</h4>
	</div>
	<!--close-Header-part-->

	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class=""><a title="" href="#"><i class="icon icon-user"></i>
					<span class="text"><%=(String) session.getAttribute(Constants.SESSION_USERID)%></span></a></li>
			<li class=""><a title="" href="#"><i
					class="icon icon-share-alt"></i> <span class="text">Đăng xuất</span></a></li>
		</ul>
	</div>

	<!--close-top-Header-menu-->

	<div id="sidebar">
		<a href="#" class="visible-phone"><i class="icon icon-home"></i>
			Trang chủ</a>
		<ul>
			<li class="active"><a href="<%=Constants.CONTROLLER_ADMIN%>"><i
					class="icon icon-home"></i> <span>Trang chủ</span></a></li>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFIC_LIST%>"><i class="icon icon-th"></i> <span>Quản
						lý biển báo</span></a></li>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_ACCOUNT_LIST%>"><i class="icon icon-user"></i> <span>Quản
						lý người dùng</span></a></li>
			<li><a
				href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_LIST%>"><i
					class="icon icon-exclamation-sign"></i> <span>Quản lý phản
						hồi</span></a></li>
			<li><a href="#"><i class="icon icon-signal"></i> <span>Thống
						kê</span></a></li>
			<li><a href="#"><i class="icon icon-cog"></i> <span>Thiếp
						lập hệ thống</span></a></li>

		</ul>
	</div>

	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="index-2.html" title="Go to Home" class="tip-bottom"><i
					class="icon-home"></i> Home</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="widget-title">
				<span class="icon"><i class="icon-th"></i></span>
				<h5>Quản lý người dùng</h5>
			</div>
			<%
				if( listAccount != null){
			%>
			<div class="widget-content nopadding">
			<div id="table-show">
				<table id="account-table" class="table table-bordered dataTable">
					<thead>
						<tr>
							<th>Tên tài khoản</th>
							<th>Email</th>
							<th>Tên người dùng</th>
							<th>Loại tài khoản</th>
							<th>Ngày tạo</th>
							<th>Trạng thái</th>
							<th></th>
							<th></th>
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
							<td><%=listAccount.get(i).getRole()%></td>
							<td><%=listAccount.get(i).getCreateDate()%></td>
							<td><%=listAccount.get(i).getIsActive()%></td>
							<%
								if(listAccount.get(i).getIsActive() == true){
							%>
							<td><button class="btn btn-danger btn-mini" href="#"
								onclick="deactiveAccount('<%=listAccount.get(i).getUserID()%>')">Khóa</button></td>
							<%
								}else if(listAccount.get(i).getIsActive() == false){
							%>
							<td><button class="btn btn-danger btn-mini" href="#"
								onclick="activeAccount('<%=listAccount.get(i).getUserID()%>')">Kích hoạt</button></td>
							<%
								}
							%>
							<%
								if("staff".equals(listAccount.get(i).getRole())){
							%>
							<td><button class="btn btn-primary btn-mini" href="#"
								onclick="unsetStaffAccount('<%=listAccount.get(i).getUserID()%>')">Unset Staff</button></td>
							<%
								}else if("user".equals(listAccount.get(i).getRole())){
							%>
							<td><button class="btn btn-primary btn-mini" href="#"
								onclick="setStaffAccount('<%=listAccount.get(i).getUserID()%>')">Set Staff</button></td>
							<%
								}else{
							%>
							<td></td>
							<%
								}
							%>
						</tr>
						<%
							} 
																																																																																									}
						%>
					</tbody>
				</table>
				<div id="pageNavPosition" style="padding-top: 20px" align="right"></div>
				</div>
				<%
					}
				%>
			</div>
		</div>
	

	<div class="row-fluid">
		<div id="footer" class="span12">
			<p>
				<b>HỆ THỐNG NHẬN DIỆN BIỂN BÁO</b>
			</p>
			<p>"Hệ thống giúp đỡ người dùng tra cứu, học tập biển báo giao
				thông."</p>
		</div>
	</div>
</body>
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
<script src="Admin/Content/js/jquery.uniform.js"></script>
<script src="Admin/Content/js/select2.min.js"></script>
<script src="Admin/Content/js/jquery.dataTables.min.js"></script>
<script src="Admin/Content/js/maruti.tables.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    oTable = $('#account-table').dataTable({
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "sDom": '<"F"f>t<""p>',
        "oLanguage": { 
        	"oPaginate": {
        		"sFirst":    "Đầu",
        		"sPrevious": "Trước",
        		"sNext":     "Sau",
        		"sLast":     "Cuối"
        	},
        "sSearch":"Tìm kiếm"
        }
    });
    $("#select-type").select2('destroy'); 
} );
</script>
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


<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/tables.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:07 GMT -->
</html>