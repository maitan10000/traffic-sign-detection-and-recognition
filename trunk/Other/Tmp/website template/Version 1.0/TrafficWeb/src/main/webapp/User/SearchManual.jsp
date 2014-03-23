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
	ArrayList<Category> listCat = (ArrayList<Category>) request.getAttribute("category");
ArrayList<TrafficInfoShortJSON> listTraffic = (ArrayList<TrafficInfoShortJSON>) request.getAttribute("listTraffic");
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
						<li class="level0 nav-4 level-top"><a
							href="/TrafficWeb/UserController?action=viewFavorite"
							class="level-top"> <span>Danh Sách Đã Lưu</span>
						</a></li>
						<li class="level0 nav-5 level-top last"><a
							href="/TrafficWeb/UserController?action=viewHistory"
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
					<div class="content-title">TRA CỨU BIỂN BÁO</div>
					<form action="UserController"
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
							<div class="content-Selectbox font-StyleTitle needMargin">
								Sắp xếp theo: <select class="sortBy font-Style">
									<option class="font-Style">Tên biển báo</option>
									<option class="font-Style">Số hiệu</option>
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
					<div class="contentTable " style="margin-top: 20px">
						<table id="resultTable"
							class="table table-striped .table-condensed">
							<thead>
								<th>Hình Ảnh</th>
								<th>Số Hiệu</th>
								<th>Tên Biển Báo</th>
								<th>Danh Mục</th>
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


		<!-- Modal for report-->
		<div id="reportModal" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">Gửi Ý Kiến</h3>
			</div>
			<div class="modal-body">
				<strong>Nội dung ý kiến :</strong><br>
				<textarea rows="3" cols="12" id="txtContent" style="width: 515px; resize:none;"></textarea>
				<input type="hidden" id="reference_id" />
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
				<button class="btn btn-primary" id="btnSubmitReport" onclick="">Gửi Ý Kiến</button>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	var pager = new Pager('resultTable', 5);
	pager.init();
	pager.showPageNav('pager', 'pageNavPosition');
	pager.showPage(1);
</script>
<script type="text/javascript">
	//ajax to get traffic detail when click link
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
				checkFavorite(trafficID);
			}

		});
	}
	// ajax to add favorite when click button luu bien bao
	function addFavorite(trafficID) {
		var action = "AddFavorite";
		$
				.ajax({
					url : "/TrafficWeb/UserController",
					type : "GET",
					data : {
						action : action,
						trafficID : trafficID
					},
					success : function(result) {
						// if add ok, change buuton to xoa bien bao
						if ("Success" == result.trim()) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id="btnAddFavorite" type=\42button\42 class=\42btn btn-primary\42 onclick=\42deleteFavorite(\''
													+ trafficID
													+ '\')\42>Xóa biển báo</button>');

						}
					}

				});
	}
	// ajax to delete favorite when click button xoa bien bao
	function deleteFavorite(trafficID) {
		var action = "DeleteFavorite";
		$
				.ajax({
					url : "/TrafficWeb/UserController",
					type : "GET",
					data : {
						action : action,
						trafficID : trafficID
					},
					success : function(result) {
						// if delete ok, change buuton to luu bien bao
						if ("Success" == result.trim()) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id="btnAddFavorite" type=\42button\42 class=\42btn btn-primary\42 onclick=\42addFavorite(\''
													+ trafficID
													+ '\')\42>Lưu biểnbáo</button>');
						}
					}

				});
	}
	// ajax to display "luu bien bao" button if traffic is not added
	function checkFavorite(trafficID) {
		var action = "checkFavorite";
		$
				.ajax({
					url : "/TrafficWeb/UserController",
					type : "GET",
					data : {
						action : action,
						trafficID : trafficID
					},
					success : function(result) {
						if ('true' == result) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id=\42btnAddFavorite\42 type=\42button\42 class=\42btn btn-primary\42 onclick=\42addFavorite(\''
													+ trafficID
													+ '\')\42>Lưu biểnbáo</button>');
						} else if ('false' == result) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id=\42btnAddFavorite\42 type=\42button\42 class=\42btn btn-primary\42 onclick=\42deleteFavorite(\''
													+ trafficID
													+ '\')\42>Xóa biển báo</button>');
						} else {
							$("#btnAddFavorite").remove();
						}
					}

				});
	}
	// ajax send report
	function sendReport(trafficID){
		var type = '2';
		var content = document.getElementById("txtContent").value;
		var action = "reportTraffic";
		$.ajax({
			url : "/TrafficWeb/UserController",
			type : "GET",
			data : {
				action : action,
				trafficID : trafficID,
				type : type,
				content : content
			},
			success : function(result) {
				$('#reportModal').modal('hide');
			}

		});
		
	}
	// function show popup for send report
	function showFromReport(trafficID) {
		$('#reference_id').val(trafficID);
		$('#myModal').modal('hide');
		$('#reportModal').modal('show');

	}
	// event on shown to set onclick
	$('#reportModal').on('show', function() {
		document.getElementById("txtContent").value = '';
		var trafficID = document.getElementById("reference_id").value;
		var functionName = 'sendReport(' + trafficID +')';
		var button = document.getElementById("btnSubmitReport");
		button.getAttributeNode("onclick").value = functionName;
	});
</script>
</html>