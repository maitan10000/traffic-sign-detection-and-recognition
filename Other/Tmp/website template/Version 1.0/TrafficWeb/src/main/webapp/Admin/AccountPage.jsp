<%@page import="utility.Constants"%>
<%@page import="model.Account"%>
<%@page import="java.util.ArrayList"%>
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
<%
	String jsonObject = (String) request.getAttribute("account");
	ArrayList<Account> listAccount = (ArrayList<Account>) request.getAttribute("listAccount");
%>

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
							class="level-top"> <span>Quản lì phản hồi</span>
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
					<div class="content-title">QUẢN LÍ NGƯỜI DÙNG</div>
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
					<form action="/TrafficWeb/AdminController">

						<%
							if( listAccount != null){
						%>
						<div class="panel-content">
							<table cellpadding="0" cellspacing="0" border="0"
								class="table table-bordered" id="contentTable">
								<thead>
									<tr>
										<th>User Account</th>
										<th>Email</th>
										<th>Name</th>
										<th>Role</th>
										<th>CreateDate</th>
										<th>IsActive</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<%
										if( listAccount.size()> 0){for(int i = 0; i< listAccount.size();i++){
									%>
									<tr>
										<td><a href="#myModal" data-toggle="modal"
											onclick="showDetails(<%=listAccount.get(i).getUserID()%>)"><%=listAccount.get(i).getUserID()%></a></td>
										<td><%=listAccount.get(i).getEmail()%></td>
										<td><%=listAccount.get(i).getName()%></td>
										<td><%=listAccount.get(i).getRole()%></td>
										<td><%=listAccount.get(i).getCreateDate()%></td>
										<td><%=listAccount.get(i).getIsActive()%></td>
										<%
											if(listAccount.get(i).getIsActive() == true){
										%>
										<td><a href="#"
											onclick="deactiveAccount('<%=listAccount.get(i).getUserID()%>')">Deactive</a></td>
										<%
											}else if(listAccount.get(i).getIsActive() == false){
										%>
										<td><a href="#"
											onclick="activeAccount('<%=listAccount.get(i).getUserID()%>')">Active</a></td>
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

						</div>
						<%
							}
						%>
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

</body>
<script type="text/javascript">
function deactiveAccount(userID) {
	var action = '<%=Constants.ACTION_ACCOUNT_DEACTIVE%>';
	$.ajax({	url : '<%=Constants.CONTROLLER_ADMIN%>',
				type : "GET",
				data : {action : action,userID : userID},
				success : function(result) {
					// if delete ok, change buuton to luu bien bao
					if ("Success" == result.trim()) {					
						reloadTable();
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
						reloadTable();
					}
				}

			});
}

function reloadTable() {
	var action = '<%=Constants.ACTION_ACCOUNT_LIST%>';
	$.ajax({
		url : '<%=Constants.CONTROLLER_ADMIN%>',
		type : "GET",
		data : {
			action : action
		},
		success : function(result) {
			$("#contentTable").html(result);
		}

	});
}
</script>

<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/tables.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:07 GMT -->
</html>