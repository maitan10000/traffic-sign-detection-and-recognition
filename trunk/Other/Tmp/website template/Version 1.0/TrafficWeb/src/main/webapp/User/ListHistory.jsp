<%@page import="json.ResultShortJSON"%>
<%@page import="json.FavoriteJSON"%>
<%@page import="model.Category"%>
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
					<h1 class="logo">
						<a href="#"> <img src="User/Content/Image/logo.png" />
						</a>
					</h1>
					<!--   _____________ -->
					<ul class="links">
						<li><a href="/TrafficWeb/Admin/Login.jsp" title="Log In">Đăng
								Nhập</a></li>
						<li class="separator">|</li>
						<li><a href="customer/account/create/index.html">Đăng Ký</a></li>
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
							class="level-top"> <span>Tra Cứu Biển Báo</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a href="#"
							class="level-top"> <span>Nhận Diện Tự Động</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a href="/TrafficWeb/UserController?action=viewFavorite"
							class="level-top"> <span>Danh Sách Đã Lưu</span>
						</a></li>
						<li class="level0 nav-5 level-top last"><a href="/TrafficWeb/UserController?action=viewHistory"
							class="level-top"> <span>Lịch Sử</span>
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
								<th>Người dùng</th>
							</thead>
							<tbody>
								<%
									if( listHistory.size()> 0){
															for(int i = 0; i< listHistory.size();i++){
								%>

								<tr>
									<td><%=i+1%></td>
									<td><a href="#" data-toggle="modal"><%=listHistory.get(i).getCreateDate().toString()%></a></td>
									<td><%=listHistory.get(i).getCreator()%></td>
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
	var pager = new Pager('resultTable', 5);
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