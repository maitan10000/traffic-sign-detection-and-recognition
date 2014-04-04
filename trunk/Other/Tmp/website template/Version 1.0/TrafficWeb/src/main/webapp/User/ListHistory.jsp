<%@page import="java.text.SimpleDateFormat"%>
<%@page import="json.ResultShortJSON"%>
<%@page import="json.FavoriteJSON"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
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
<link href="User/Content/Css/paging.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="User/Content/Scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="User/Content/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="User/Content/Scripts/paging.js"></script>
<title>Traffic Sign Recognition</title>
</head>


<%
	ArrayList<ResultShortJSON> listHistory = (ArrayList<ResultShortJSON>) request.getAttribute("listHistory");
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
				<h2 class="logo" style="color: #FFF;">HỆ THỐNG NHẬN DIỆN, TRA CỨU BIỂN
						BÁO</h2>
					<!--   _____________ -->
					<%
						String userID = (String)session.getAttribute(Constants.SESSION_USERID);
					if(userID != null && !userID.isEmpty())
					{
					%>
					<ul class="links">
						<li><a href="#"
							title=""><%=userID %></a></li>
						<li class="separator">|</li>
						<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%> ">Đăng xuất</a></li>
						<li class="separator">
					</ul>
					<%}else{ %>
					<ul class="links">
						<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGIN%>"
							title="Đăng nhập">Đăng Nhập</a></li>
						<li class="separator">|</li>
						<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REGISTER%>" title="Đăng ký">Đăng Ký</a></li>
						<li class="separator">
					</ul>
					<%}//end if session useID %>
				</div>
				</header>
				<div class="menu-container">
					<nav class="olegnax">
					<ul id="nav">
						<li class="level0 nav-4 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_SEARCH_AUTO%>
								"
							class="level-top"> <span>Nhận Diện Tự Động</span>
						</a></li>
						<li class="level0 nav-3 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_SEARCH_MANUAL%>"
							class="level-top"> <span>Tra Cứu Biển Báo</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a
							href="<%=Constants.CONTROLLER_USER %>?action=<%=Constants.ACTION_FAVORITE_VIEW%>"
							class="level-top"> <span>Danh Sách Đã Lưu</span>
						</a></li>
						<li class="level0 nav-5 level-top last"><a
							href="<%=Constants.CONTROLLER_USER %>?action=<%=Constants.ACTION_HISTORY_LIST%>"
							class="level-top"> <span>Lịch Sử</span>
						</a></li>
					</ul>
					</nav>
					<div style="clear: both"></div>

				</div>
			</div>
			<div class="main-container">
				<div class="main-content content-cat notHomepage">
					<div class="content-title">LỊCH SỬ TÌM KIẾM</div>
					<%
						if( listHistory != null){
					%>
					<div class="contentTable " style="margin-top: 20px">
						<table id="resultTable"
							class="table table-striped .table-condensed">
							<thead>
								<th>STT</th>
								<th>Ngày tạo</th>
							</thead>
							<tbody>
								<%								
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									if( listHistory.size()> 0){
															for(int i = listHistory.size() -1; i >= 0 ;i--){
								%>

								<tr>
									<td><%=listHistory.size() - i%></td>
									<td><a href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_HISTORY_VIEW%>&resultID=<%=listHistory.get(i).getResultID()%>" data-toggle="modal"><%=dateFormat.format(listHistory.get(i).getCreateDate()) %></a></td>
								</tr>
								<%
									} 
																																													}
								%>
							</tbody>
						</table>
					</div>
					<div id="pageNavPosition" style="padding-top: 20px" align="center"></div>
					<%
						}
					%>
					<div style="clear: both"></div>
				</div>
			<div class="footer-container">
					<div class="footer">
						<div class="footer-brands">
							<div class="brands"></div>
						</div>
						<div class="footer-left">
							<p>
								<b>HỆ THỐNG NHẬN DIỆN, TRA CỨU BIỂN BÁO</b>
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
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			style="display: none;" aria-labelledby="myModalLabel"
			aria-hidden="true"></div>
	</div>

</body>
<script type="text/javascript">
	var pager = new Pager('resultTable', 10);
	pager.init();
	pager.showPageNav('pager', 'pageNavPosition');
	pager.showPage(1);
</script>
<script type="text/javascript">
	//ajax to get traffic when click link
	function showDetails(trafficID) {
		var action = "viewDetail";
		$.ajax({
			url : "/TrafficWeb/UserController",
			type : "GET",
			data : {
				action : action,
				trafficID : trafficID
			},
			success : function(result) {
				$("#myModal").html(result);
			}

		});
	}
</script>
</html>