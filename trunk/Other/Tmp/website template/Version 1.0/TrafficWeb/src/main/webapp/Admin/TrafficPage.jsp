<%@page import="json.TrafficInfoShortJSON"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="utility.Constants"%>
<%@page import="utility.GlobalValue"%>
<%@page import="model.Report"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Category"%>
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
<%
	ArrayList<Category> listCat = (ArrayList<Category>) request.getAttribute("category");
ArrayList<TrafficInfoShortJSON> listTraffic = (ArrayList<TrafficInfoShortJSON>) request.getAttribute("listTraffic");
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
					<span class="text">Tài khoản</span></a></li>
			<li class=""><a title="" href="login.html"><i
					class="icon icon-share-alt"></i> <span class="text">Thoát</span></a></li>
		</ul>
	</div>

	<!--close-top-Header-menu-->

	<div id="sidebar">
		<a href="#" class="visible-phone"><i class="icon icon-home"></i>
			Dashboard2</a>
		<ul>
			<li class="active"><a href="index-2.html"><i
					class="icon icon-home"></i> <span>Trang chủ</span></a></li>
			<li><a
				href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFICINFO_ADD%>"><i
					class="icon icon-th"></i> <span>Quản lý biển báo</span></a></li>
			<li><a
				href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_ACCOUNT_LIST%>"><i
					class="icon icon-user"></i> <span>Quản lý người dùng</span></a></li>
			<li><a
				href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_LIST%>"><i
					class="icon icon-exclamation-sign"></i> <span>Quản lý phản
						hồi</span></a></li>
			<li><a href="grid.html"><i class="icon icon-signal"></i> <span>Thống
						kê</span></a></li>
			<li><a href="grid.html"><i class="icon icon-cog"></i> <span>Thiếp
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
				<h5>Quản lý biển báo</h5>
			</div>
			<form action="/TrafficWeb/AdminController"
				enctype="application/x-www-form-urlencoded">
				<div class="options">
					<div class="searchName" style="margin-right: 30px;">
						Tên biển báo: <input name="searchKey" type="text" />
					</div>
					<div class="content-Selectbox font-StyleTitle needMargin">
						Loại Biển Báo: <select class="sortBy font-Style" id="catID"
							name="catID">
							<option class="font-Style" value="0">Tất Cả</option>
							<%
								for(int i = 0; i< listCat.size();i ++) {
							%>
							<option class="font-Style"
								value="<%=listCat.get(i).getCategoryID()%>"><%=listCat.get(i).getCategoryName()%></option>
							<%
								}
							%>
						</select>
					</div>
					<div class="searchName" style="padding-bottom: 5px">
						<button type="submit" class="btn btn-default btn-sm"
							value="searchManual" name="action">Tìm kiếm</button>
					</div>
					<div style="clear: both"></div>
				</div>
			</form>
			<%
				if( listTraffic != null){
			%>
			<div id="tableContent" class="panel-content">
				<div id="table-show">
					<table id="traffic-table" class="table table-bordered dataTable">
						<thead>
							<th>Hình Ảnh</th>
							<th>Số Hiệu</th>
							<th>Tên Biển Báo</th>
							<th>Danh Mục</th>
							<th></th>
						</thead>
						<tbody>
							<%
								if( listTraffic.size()> 0){
																																																																																for(int i = 0; i< listTraffic.size();i++){
							%>

							<tr>
								<td><img class="trafficImage"
									src="<%=GlobalValue.getServiceAddress()%><%=listTraffic.get(i).getImage()%>"
									alt="Responsive image" /></td>
								<td><%=listTraffic.get(i).getTrafficID()%></td>
								<td><a href="#myModal" data-toggle="modal"
									onclick="showDetails('<%=listTraffic.get(i).getTrafficID()%>')"><%=listTraffic.get(i).getName()%></a></td>
								<td><%=listTraffic.get(i).getCategoryName()%></td>
								<td><a href="#myModal" data-toggle="modal" onclick="showTrafficDetails(<%=listTraffic.get(i).getTrafficID()%>)">Sửa</a></td>								
							</tr>
							<%
								} 
																																																																																						}
							%>
						</tbody>
					</table>
				</div>
			</div>
			<!-- <div id="pageNavPosition" style="padding-top: 20px" align="right"></div> -->
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
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End modal 1 -->

	<!-- Detail modal -->
	<div class="modal fade" id="detailModal" tabindex="-1" role="dialog"
		style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End detail modal-->
	<!-- Traffic Detail modal -->
	<div class="modal fade" id="trafficDetailModal" tabindex="-1"
		role="dialog" style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End detail modal-->
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
<script src="Admin/Content/js/maruti.dashboard.js"></script>
<script src="Admin/Content/js/maruti.calendar.js"></script>
<script src="Admin/Content/js/jquery.uniform.js"></script>
<script src="Admin/Content/js/select2.min.js"></script>
<script src="Admin/Content/js/jquery.dataTables.min.js"></script>
<script src="Admin/Content/js/maruti.tables.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		oTable = $('#traffic-table').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"sDom" : '<"">t<"F"fp>'
		});
		$("#select-type").select2('destroy');
	});
	
	function showTrafficDetails(trafficID){
		var action = '<%=Constants.ACTION_TRAFFIC_VIEW%>';
		$.ajax({
			url: '<%=Constants.CONTROLLER_ADMIN%>',
			type: "GET",
			data: {action : action, trafficID : trafficID},
			success: function (result) {
				$("#myModal").modal('hide');
				$("#trafficDetailModal").html(result);
				$("#trafficDetailModal").modal('show');
			}
			
		});
	}
</script>




<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/tables.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:07 GMT -->
</html>