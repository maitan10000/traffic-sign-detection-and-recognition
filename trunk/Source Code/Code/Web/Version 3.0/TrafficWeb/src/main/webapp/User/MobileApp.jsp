<%@page import="utility.Constants"%>
<%@page import="utility.GlobalValue"%>
<%@page import="json.TrafficInfoShortJSON"%>
<%@page import="json.TrafficInfoJSON"%>
<%@page import="model.Category"%>
<%@page import="model.TrafficSign"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="User/Content/Css/Main.css" rel="stylesheet" type="text/css" />

<link href="User/Content/bootstrap/css/bootstrap.css" rel="stylesheet"/>
<link href="User/Content/Css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<!-- <link href="User/Content/Css/paging.css" rel="stylesheet" -->
<!-- 	type="text/css" /> -->
<script type="text/javascript"
	src="User/Content/Scripts/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
	src="User/Content/Scripts/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="User/Content/bootstrap/js/bootstrap.js"></script>



</head>


<%
	ArrayList<Category> listCat = (ArrayList<Category>) request.getAttribute("category");
ArrayList<TrafficInfoShortJSON> listTraffic = (ArrayList<TrafficInfoShortJSON>) request.getAttribute("listTraffic");
%>
<body on>
	<div class="wrapper">
		<div class="page">
			<div class="header-container">
				<header>
				<div class="clearfix">
				
				</div>
				<div class="logo-Container">
				<img id="logoImage"  alt="" src="User/Content/Image/eye_logo.png" style="height: 80px; width: 90px;">
					<h2  id="titleHeader" class="logo" style="color: #FFF;">HỆ THỐNG NHẬN DẠNG BIỂN BÁO</h2>
					<!--   _____________ -->
					<%
						String userID = (String)session.getAttribute(Constants.SESSION_USERID);
														if(userID != null && !userID.isEmpty())
														{
					%>
					<ul class="links">
						<li><a href="#" title=""><%=userID%></a></li>
						<li class="separator">|</li>
						<li><a
							href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%> ">Đăng
								xuất</a></li>
						<li class="separator">
					</ul>
					<%
						}else{
					%>
					<ul class="links">
						<li><a
							href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGIN%>"
							title="Đăng nhập">Đăng Nhập</a></li>
						<li class="separator">|</li>
						<li><a
							href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REGISTER%>"
							title="Đăng ký">Đăng Ký</a></li>
						<li class="separator">
					</ul>
					<%
						}//end if session useID
					%>
				</div>
				</header>
				
				<div class="menu-container">
					<nav class="olegnax">
					<ul id="nav">
						<li class="level0 nav-4 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_SEARCH_AUTO%>
								"
							class="level-top"> <span>Nhận Dạng Tự Động</span>
						</a></li>
						<li class="level0 nav-3 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_SEARCH_MANUAL%>"
							class="level-top"> <span>Tra Cứu Biển Báo</span>
						</a></li>
						<%if(userID != null && "".equals(userID) == false){ %>
						<li class="level0 nav-4 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_FAVORITE_VIEW%>"
							class="level-top"> <span>Danh Sách Đã Lưu</span>
						</a></li>
						<li class="level0 nav-5 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_HISTORY_LIST%>"
							class="level-top"> <span>Lịch Sử</span>
						</a></li>
						<%} %>
						<%
						if(GlobalValue.getShowMobile()== true)
						{
						%>
						<li class="level0 <%= userID == null ? "":"nav-6"%> level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_MOBILE_VIEW%>"
							class="level-top"> <span>Ứng dụng điện thoại</span>
						</a></li>
						<%
						} //end if showMobile
						%>
					</ul>
					</nav>
					<div style="clear: both"></div>

				</div>
			</div>
			<div class="main-container">
				<div class="main-content content-cat notHomepage">
					<div class="content-title">ỨNG DỤNG ĐIỆN THOẠI</div>
					<div id="mobile-area" class="row-fluid">
						 <ul class="thumbnails">
			              <li class="span4">
			                <div class="thumbnail">
			                  <img src="User/Content/Image/android.png" style="height: 200px;">
			                  <div class="caption">
			                    <h3>Android</h3>
			                    <p>Tải ngay để trải nghiệm</p>
			                    <p><a href="<%=GlobalValue.getServiceAddress()%><%=Constants.DOWNLOAD_ANDROID_APP%>" class="btn btn-primary"><img src="User/Content/Image/download.png" style="width: 25px;"/>  Tải về máy</a></p>
			                  </div>
			                </div>
			              </li>
			              <li class="span4">
			                <div class="thumbnail">
			                  <img src="User/Content/Image/windowphone.png" style="height: 200px;">
			                  <div class="caption">
			                    <h3>Window phone</h3>
			                    <p>Đang phát triển...</p>
			                    <p><a href="#" class="btn btn-primary btn-disabled" disabled><img src="User/Content/Image/download.png" style="width: 25px;"/>  Tải về máy</a></p>
			                  </div>
			                </div>
			              </li>
			              <li class="span4">
			                <div class="thumbnail">
			                  <img src="User/Content/Image/iOS.png" style="height: 200px;">
			                  <div class="caption">
			                    <h3>iOS</h3>
			                    <p>Đang phát triển...</p>
			                    <p><a href="#" class="btn btn-primary btn-disabled" disabled><img src="User/Content/Image/download.png" style="width: 25px;"/>  Tải về máy</a></p>
			                  </div>
			                </div>
			              </li>
			            </ul>
					</div>
					<div style="clear: both"></div>
				</div>
				<div class="footer-container">
					<div class="footer">
						<div class="footer-brands">
							<div class="brands"></div>
						</div>
						<div class="footer-left">
							<p>
								<b>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</b>
							</p>
							<p>"Hệ thống giúp đỡ người dùng tra cứu, học tập biển báo
								giao thông."</p>
						</div>
						<div class="footer-left">
							<p>
								<span style="border-bottom: dotted 1px #fafafa;">TRƯỜNG
									ĐẠI HOC FPT</span>
							</p>
							<p style="padding-bottom: 7px;">
								Công viên phần mềm Quang Trung - Quận 12 - TP Hồ Chí Minh <a
									class="location" href="#">&nbsp;</a>
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
	</div>
</html>