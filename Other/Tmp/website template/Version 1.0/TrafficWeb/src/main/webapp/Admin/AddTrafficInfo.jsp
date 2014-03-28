
<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="User/Content/Css/Main.css" rel="stylesheet" type="text/css" />
<link href="User/Content/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="User/Content/Scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="User/Content/bootstrap/js/bootstrap.js"></script>
<title>Traffic Sign Recognition</title>
</head>
<body on>
	<div class="wrapper">
		<div class="page">
			<div class="header-container">
				<header>
				<div class="clearfix">
					<div class="card-top"></div>
				</div>
				<div class="logo-Container">
					<h1 class="logo">
						<a href="#"> <img src="User/Content/Image/logo.png" />
						</a>
					</h1>
					<!--   _____________ -->
					<ul class="links">
						<li><a href="/TrafficWeb/Admin/Login.jsp" title="Log In">Đăng
								Nhập</a></li>
						<li class="separator">|</li>
						<li><a href="/TrafficWeb/Admin/Register.jsp">Đăng Ký</a></li>
						<li class="separator">|</li>
						<li><a href="customer/account/create/index.html">Liên Hệ</a></li>
					</ul>
				</div>
				</header>
				<div class="menu-container">
					<nav class="olegnax">
					<ul id="nav">
						<li class="level0 nav-3 level-top"><a
							href="/TrafficWeb/UserController?action=searchManual"
							class="level-top"> <span>Quản lí biển báo</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a
							href="/TrafficWeb/AdminController?action=listAccount"
							class="level-top"> <span>Quản lí người dùng</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a
							href="/TrafficWeb/AdminController?action=listReport"
							class="level-top"> <span>Quản lí phản hồi</span>
						</a></li>
					</ul>
					</nav>
					<div style="clear: both"></div>
					<form id="search_mini_form"
						action="http://celebrity.olegnax.com/catalogsearch/result/"
						method="get">
						<div class="form-search">
							<input id="search" type="text" name="q" value=""
								class="input-text" />
							<button type="submit" title="Search"></button>
						</div>
						<div id="search_autocomplete" class="search-autocomplete"></div>
					</form>
				</div>
			</div>
			<div class="main-container">
				<div class="main-content content-cat notHomepage">
					<div class="content-title">THÊM MỚI BIỂN BÁO</div>
					<!-- <form action="UserController" enctype="application/x-www-form-urlencoded">
						<div class="options">
							<div class="searchName" style="margin-right: 30px;">
								Tên biển báo: <input name="searchKey" type="text" />
							</div>
							<div class="content-Selectbox font-StyleTitle needMargin">
								Loại Biển Báo: <select class="sortBy font-Style" id="catID"
									onclick="loadCat()">
									<option class="font-Style">Tất Cả</option>
								</select>
							</div>
							<div class="content-Selectbox font-StyleTitle needMargin">
								Sắp xếp theo: <select class="sortBy font-Style">
									<option class="font-Style">Tên biển báo</option>
									<option class="font-Style">Số hiệu</option>
								</select>
							</div>
							<div class="searchName" style="padding-bottom: 5px">
								<button type="submit" class="btn btn-default btn-sm"
									value="searchTraffic" name="action">Tìm kiếm</button>
							</div>
							<div style="clear: both"></div>
						</div>
					</form> -->
					<form id="add_traffic_form" method="post"
						enctype="multipart/form-data">
						<fieldset>
							<legend>Thêm mới thông tin biển báo</legend>
							<label>Số hiệu biển báo</label> <input type="text"
								name="trafficID" /> <label>Tên biển báo</label><input
								type="text" name="name" /> <label>Hình ảnh</label> <input
								type="file" name="mainImage" size="45" /> <label>Loại
								biển báo</label> <input type="text" name="categoryID" /> <label>Thông
								tin biển báo</label><input type="text" name="information" /> <label>Mức
								phạt</label><input type="text" name="penaltyfee" /> <label>Người
								tạo</label><input type="text" name="creator" value="user1" /><br>
							<button type="button" onclick="uploadFile(); return false;">Add</button>
						</fieldset>


					</form>

					<div style="clear: both"></div>
				</div>
				<div class="footer-container">
					<div class="footer">
						<div class="footer-brands">
							<div class="brands">
								<a href="#"><img src="Content/Image/brands/brand1.gif"
									alt=""></a> <a href="#"><img
									src="User/Content/Image/brands/brand2.gif" alt=""></a> <a
									href="#"><img src="User/Content/Image/brands/brand3.gif"
									alt=""></a> <a href="#"><img
									src="User/Content/Image/brands/brand4.gif" alt=""></a> <a
									href="#"><img src="User/Content/Image/brands/brand5.gif"
									alt=""></a> <a href="#"><img
									src="User/Content/Image/brands/brand6.gif" alt=""></a> <a
									href="#"><img src="User/Content/Image/brands/brand7.gif"
									alt=""></a> <a href="#"><img
									src="User/Content/Image/brands/brand8.gif" alt=""></a> <a
									href="#"><img src="User/Content/Image/brands/brand9.gif"
									alt=""></a>
							</div>
						</div>
						<div class="footer-left">
							<p>
								<b>HỆ THỐNG NHẬN DIỆN BIỂN BÁO</b>
							</p>
							<p>"Chúng tôi khác biệt...!" - Sau 3 năm thành lập đến nay hệ
								thống cửa hàng Celebrity đã ngày càng phát triển và hoàn thiện
								hơn so với những năm về trước</p>
						</div>
						<div class="footer-left">
							<p>
								<span style="border-bottom: dotted 1px #fafafa;">TRUNG
									TÂM CELEBRITY</span>
							</p>
							<p style="padding-bottom: 7px;">
								770F, Sư Vạn Hạnh (ND), P.12, Q.10, Tp. HCM <a class="location"
									href="#">&nbsp;</a>
							</p>
						</div>
						<div class="footer-left">
							<ul class="social">
								<li><a href="#"><span class="facebook icon"></span>Join
										us on Facebook</a></li>
								<li><a href="#"><span class="email icon"></span>Send an
										Email</a></li>
								<li><a href="#"><span class="rss icon"></span>Subscrible
										RSS Feed</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			style="display: none;" aria-labelledby="myModalLabel"
			aria-hidden="true"></div>
	</div>
	<script type="text/javascript">
	function uploadFile() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showResult(xhr.responseText);
			}
		}
		var tmpForm = document.getElementById("add_traffic_form");
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

</body>
<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/tables.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:07 GMT -->
</html>