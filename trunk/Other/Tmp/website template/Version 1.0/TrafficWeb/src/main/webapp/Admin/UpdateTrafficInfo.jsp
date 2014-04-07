<%@page import="java.util.ArrayList"%>
<%@page import="json.TrafficInfoJSON"%>
<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
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
TrafficInfoJSON trafficDetails = (TrafficInfoJSON) request.getAttribute("trafficDetail");%>

<body>
<!--Header-part-->
	<div id="header" >
		<div id="header-inner">
			<h4>				
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
					<span class="text">Profile</span></a></li>
			<li class=" dropdown" id="menu-messages"><a href="#"
				data-toggle="dropdown" data-target="#menu-messages"
				class="dropdown-toggle"><i class="icon icon-envelope"></i> <span
					class="text">Messages</span> <span class="label label-important">5</span>
					<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a class="sAdd" title="" href="#">new message</a></li>
					<li><a class="sInbox" title="" href="#">inbox</a></li>
					<li><a class="sOutbox" title="" href="#">outbox</a></li>
					<li><a class="sTrash" title="" href="#">trash</a></li>
				</ul></li>
			<li class=""><a title="" href="#"><i class="icon icon-cog"></i>
					<span class="text">Settings</span></a></li>
			<li class=""><a title="" href="login.html"><i
					class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
		</ul>
	</div>

	<!--close-top-Header-menu-->

	<div id="sidebar">
		<a href="#" class="visible-phone"><i class="icon icon-home"></i>
			Dashboard2</a>
		<ul>
			<li class="active"><a href="index-2.html"><i
					class="icon icon-home"></i> <span>Trang chủ</span></a></li>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFIC_ADD%>"><i class="icon icon-th"></i> <span>Quản
						lý biển báo</span></a></li>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_ACCOUNT_LIST%>"><i
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
					class="icon-home"></i> Home</a> <a href="#" class="tip-bottom">Form
					elements</a> <a href="#" class="current">Common elements</a>
			</div>
			<h1>Thông tin biển báo</h1>
		</div>
		<div class="container-fluid">		
			<div class="row-fluid">			
				<div class="span6">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5></h5>
						</div>
						<div class="widget-content nopadding">
							<form id="update_traffic_form" method="post" class="form-horizontal">
							<div class="control-group">
									<label class="control-label">Số hiệu Biển Báo :</label>
									<div class="controls">
										<input name="trafficID" type="text" class="span6" value="<%=trafficDetails.getTrafficID()%>" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Tên Biển Báo :</label>
									<div class="controls">
										<input name="name" type="text" class="span6" value=""/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Hình ảnh</label>
									<div class="controls">
										<input type="file" name="mainImage"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Loại biển báo</label>
									<div class="controls">
										<select name="categoryID">
											<option value="1">Biển báo cấm</option>
											<option value="2">Biển báo nguy hiểm</option>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Thông tin</label>
									<div class="controls">
										<textarea class="span11" name="information"></textarea>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Mức phạt</label>
									<div class="controls">
										<input name="penaltyfee" type="text" class="span11" placeholder="Last name" />
									</div>
								</div>
								<div class="control-group">
									<div class="controls">
										<input type="hidden" name="creator" value="user1" />
									</div>
								</div>

								<div class="form-actions">
									<button type="submit" class="btn btn-success" onclick="uploadFile(); return false;" >Save</button>
								</div>
							</form>
						</div>
					</div>
				</div>			
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>

	<script type="text/javascript">
	function updateTrafficInfo() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showResult(xhr.responseText);
			}
		}
		var tmpForm = document.getElementById("update_traffic_form");
		/* TrafficID : <input type="text" name="trafficID" /><br> Name: <input
		type="text" name="name" /><br> Image : <input type="file"
		name="mainImage" size="45" /><br> CategoryID:<input type="text"
		name="categoryID" /><br> Information:<input type="text"
		name="information" /><br> PenaltyFee: <input type="text"
		name="penaltyfee" /><br> Creator: <input type="text" name="creator"
		value="user1" /><br> */
		
		var formData = new FormData();
		formData.append("trafficID", tmpForm.trafficID.value);
		formData.append("name", tmpForm.name.value);
		console.log(tmpForm.mainImage);
		formData.append('mainImage', tmpForm.mainImage.files[0]);
		formData.append("categoryID", tmpForm.categoryID.value);
		formData.append("information", tmpForm.information.value);
		formData.append("penaltyfee", tmpForm.penaltyfee.value);
		formData.append('creator', tmpForm.creator.value);
		console.log(formData);
		
		xhr.upload.addEventListener("progress", function(e) {
			if (e.lengthComputable) {
				//var percentage = Math.round((e.loaded * 100) / e.total);
				//$("#progress").html('<span class="bar" style="width: ' + percentage + '%"></span>');
				//console.log("%: "+ percentage);
			}
		}, false);
		xhr.upload.addEventListener("load", function(e) {
			//$("#progress").html('<span class="bar" style="width: 100%"></span>');
			//console.log("%: 100");
		}, false);
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_ADD%>');
			//xhr.overrideMimeType('text/plain; charset=utf-8');
			xhr.send(formData);
		}

		function showResult(result) {
			alert(result);
		}
	</script>
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.ui.custom.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-colorpicker.js"></script>
	<script src="js/bootstrap-datepicker.js"></script>
	<script src="js/jquery.uniform.js"></script>
	<script src="js/select2.min.js"></script>
	<script src="js/maruti.js"></script>
	<script src="js/maruti.form_common.js"></script>
</body>
<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/tables.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:07 GMT -->
</html>