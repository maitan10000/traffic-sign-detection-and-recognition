<%@page import="java.awt.Container"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
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
<body>
	<!--Header-part-->
	<div id="header" >
		<div id="header-inner">
			<!-- <img src="Admin/Content/images/logo.png" style="width: 100px; height: 100px; position:fix;"/> -->
			<h4>
				<!--  <a href="#">HỆ THỐNG NHẬN DIỆN BIỂN BÁO - TRANG QUẢN
				LÝ</a>
			-->
				<span>Hệ thống nhận diện biển báo - Trang quản lý</span>
			</h4>
		</div>
	</div>
	<!--close-Header-part-->

	<!--top-Header-messaages-->
	<div class="btn-group rightzero">
		<a class="top_message tip-left" title="Manage Files"><i
			class="icon-file"></i></a> <a class="top_message tip-bottom"
			title="Manage Users"><i class="icon-user"></i></a> <a
			class="top_message tip-bottom" title="Manage Comments"><i
			class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="top_message tip-bottom" title="Manage Orders"><i
			class="icon-shopping-cart"></i></a>
	</div>
	<!--close-top-Header-messaages-->

	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class=""><a title="" href="#"><i class="icon icon-user"></i>
					<span class="text"><%=(String) session.getAttribute(Constants.SESSION_USERID)%></span></a></li>
			<li class=""><a title="" data-toggle="modal" href="#logout-Modal"><i
					class="icon icon-share-alt"></i> <span class="text">Đăng
						xuất</span></a></li>
		</ul>		
	</div>

	<!--close-top-Header-menu-->

	<div id="sidebar">
		<a href="#" class="visible-phone"><i class="icon icon-home"></i>
			Trang chủ</a>
		<ul>
			<li class="active"><a href="<%=Constants.CONTROLLER_ADMIN%>"><i
					class="icon icon-home"></i> <span>Trang chủ</span></a></li>
			<li><a
				href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFIC_LIST%>"><i
					class="icon icon-th"></i> <span>Quản lý biển báo</span></a></li>
			<li><a
				href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_ACCOUNT_LIST%>"><i
					class="icon icon-user"></i> <span>Quản lý người dùng</span></a></li>
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
				<a href="<%=Constants.CONTROLLER_ADMIN%>" title="Trang chủ"
					class="tip-bottom"><i class="icon-home"></i>Trang chủ</a>
			</div>
		</div>
		<div class="container-fluid">

			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-tasks"></i></span>
						<h5>Site Analytics</h5>
						<div class="buttons">
							<a href="#" class="btn btn-mini btn-success"><i
								class="icon-refresh"></i> Update stats</a>
						</div>
					</div>
					<div class="widget-content">
						<div class="row-fluid">
							<div class="span8">
								<div class="chart"></div>
							</div>
							<div class="span4">
								<ul class="stat-boxes2">
									<li>
										<div class="left peity_bar_neutral">
											<span><span style="display: none;">2,4,9,7,12,10,12</span>
												<canvas width="50" height="24"></canvas> </span>+10%
										</div>
										<div class="right">
											<strong>15598</strong> Visits
										</div>
									</li>
									<li>
										<div class="left peity_line_neutral">
											<span><span style="display: none;">10,15,8,14,13,10,10,15</span>
												<canvas width="50" height="24"></canvas> </span>10%
										</div>
										<div class="right">
											<strong>150</strong> New Users
										</div>
									</li>
									<li>
										<div class="left peity_bar_bad">
											<span><span style="display: none;">3,5,6,16,8,10,6</span>
												<canvas width="50" height="24"></canvas> </span>-40%
										</div>
										<div class="right">
											<strong>4560</strong> Orders
										</div>
									</li>
									<li>
										<div class="left peity_line_good">
											<span><span style="display: none;">12,6,9,13,14,10,17</span>
												<canvas width="50" height="24"></canvas> </span>+60%
										</div>
										<div class="right">
											<strong>936</strong> Register
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</div>
	<div class="row-fluid">
		<div id="footer" class="span12">
			<p>
				<b>HỆ THỐNG NHẬN DIỆN BIỂN BÁO</b><br> <span
					style="font-size: 11px;">"Hệ thống giúp đỡ người dùng tra
					cứu, học tập biển báo giao thông"</span>
			</p>
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
	<script src="Admin/Content/js/maruti.dashboard.js"></script>
	<script src="Admin/Content/js/maruti.calendar.js"></script>
	<script type="text/javascript">
		// This function is called from the pop-up menus to transfer to
		// a different page. Ignore if the value returned is a null string:
		function goPage(newURL) {

			// if url is empty, skip the menu dividers and reset the menu selection to default
			if (newURL != "") {

				// if url is "-", it is this page -- reset the menu:
				if (newURL == "-") {
					resetMenu();
				}
				// else, send page to designated URL            
				else {
					document.location.href = newURL;
				}
			}
		}

		// resets the menu selection upon entry to this page:
		function resetMenu() {
			document.gomenu.selector.selectedIndex = 2;
		}
	</script>

	<!-- Logout confirm  modal -->
	<div id="logout-Modal" class="modal hide">
		<div class="modal-header">
			<button data-dismiss="modal" class="close" type="button">×</button>
			<h3>Xác nhận đăng xuất</h3>
		</div>
		<div class="modal-body">
			<p>Bạn có chắc chắn muốn đăng xuất</p>
		</div>
		<div class="modal-footer">
			<a class="btn btn-primary" href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%>">Đồng ý</a>
			<a data-dismiss="modal" class="btn" href="#">Hủy</a>
		</div>
	</div>
</body>

</html>
