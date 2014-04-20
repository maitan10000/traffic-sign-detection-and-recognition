<%@page import="json.ResultExtraShortJSON"%>
<%@page import="json.ReportShortJSON"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="utility.Constants"%>
<%@page import="model.Report"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Hệ thống nhận dạng biển báo - Trang quản lý</title>
<link rel="shortcut icon" type="image/png" href="Admin/Content/images/favicon.png"/>
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
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style>
#paging-table_filter {
	margin-left: 20px;
}
#trafficDetailModal{
width: 800px;
    margin-left: -400px;
}
.trainImage-resize
{
	width:50px;
}

.trainImage-resize .actions
{
	margin: 0;
	padding: 0;
	left: 45%;
	width: 16px;
}
#table-show tbody .btn
{
	width: 80px;
}
</style>
</head>
<%
	ArrayList<ResultExtraShortJSON> listWrongRecognition = (ArrayList<ResultExtraShortJSON>) request.getAttribute("listResultExtra");
	String role = (String) session.getAttribute(Constants.SESSION_ROLE);
	String info = (String) request.getAttribute("info");
	String showRead = (String)request.getAttribute("showRead");
%>
<body>
	<!--Header-part-->
	<div id="header" >
		<div id="header-inner">
			<h4>				
				<span>Hệ thống nhận dạng biển báo - Trang quản lý</span>
			</h4>
		</div>
	</div>
	<!--close-Header-part-->

	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class=""><a title="" href="#"><i class="icon icon-user"></i>
					<span class="text"><%=(String) session.getAttribute(Constants.SESSION_USERID)%></span></a></li>
			<li class=""><a title="Đăng xuất" href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%>"><i
					class="icon icon-share-alt" onclick="logout()"></i> <span
					class="text">Đăng xuất</span></a></li>
		</ul>
	</div>

	<!--close-top-Header-menu-->
	<div id="sidebar">
	<a href="<%=Constants.CONTROLLER_ADMIN%>" class="visible-phone"><i class="icon icon-home"></i>
			Trang chủ</a>
		<ul>			
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>"><i
					class="icon icon-home"></i> <span>Trang chủ</span></a></li>
			<%
			if("staff".equals(role))
			{
			%>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFIC_LIST%>"><i class="icon icon-th"></i> <span>Quản
						lý biển báo</span></a></li>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_LIST%>"><i class="icon icon-exclamation-sign"></i> <span>Quản lý phản
						hồi</span></a>
				<ul>
        			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_EXTRA%>">Lịch sử tìm kiếm</a></li>      
      			</ul>
      		</li>
			<%
			}
			%>
			
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_ACCOUNT_LIST%>"><i class="icon icon-user"></i> <span>Quản
						lý người dùng</span></a></li>			
			
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_STATISTIC%>"><i class="icon icon-signal"></i> <span>Thống
						kê</span></a></li>
			<%
			if("admin".equals(role))
			{
			%>
			<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_CONFIG%>"><i class="icon icon-cog"></i> <span>Thiếp
						lập hệ thống</span></a></li>
			<%
			}
			%>
		</ul>
	</div>
	<!-- End slide bar -->
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_LIST%>" title="Quản lý phản hồi" class="tip-bottom"><i
					class="icon-exclamation-sign"></i>Quản lý phản hồi</a>
					<a href="#" title="Lịch sử tìm kiếm" class="tip-bottom">Lịch sử tìm kiếm</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="widget-title">
				<span class="icon"><i class="icon-bookmark"></i></span>
				<h5>Lịch sử tìm kiếm</h5>
				<div id="show-type" align="right" class="control-group">
					<div class="controls">				
						<input type="checkbox" name="showRead" id="showRead"
						 <%
						 if("true".equals(showRead))
						 {
							 out.print(" checked=\"\" ");
						 }
 						 %>
						  onclick="changeShow();">Đã xem
						  
						 <input type="checkbox" name="showInfo" id="showInfo"
						 <%
						 if("all".equals(info))
						 {
							 out.print(" checked=\"\" ");
						 }
 						 %>
						  onclick="changeShow();">Cả nhận dạng được
					</div>
				</div>
			</div>			
		
			<div id="tableContent" class="panel-content ">
				<div id="table-show">
					<table id="paging-table" class="table table-bordered dataTable">
						<thead>
							<tr role="row">
								<th width="10%">STT</th>
								<th width="50%">Thời gian tạo</th>
								<th >Chức năng</th>
							</tr>
						</thead>
						<tbody role="alert" aria-live="polite" aria-relevant="all">
							<%
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								for (int i = 0; i < listWrongRecognition.size(); i++) {
 							%>
							<tr>
								<td style="text-align: center;"><%=i+1%></td>								
								<td style="text-align: center;"><%= dateFormat.format(listWrongRecognition.get(i).getCreateDate())%></td>
								<%
								if(listWrongRecognition.get(i).getIsRead() == true)
								{
								%>								
									<td style="text-align: center;"><a class="btn btn-primary btn-mini" href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAIN_IMAGE_ADD_FROM_REPORT%>&resultID=<%=listWrongRecognition.get(i).getResultID()%>"><i class="icon-wrench"></i> Sửa</a></td>
								<%
								}else
								{
								%>
									<td style="text-align: center;"><a class="btn btn-primary btn-mini" href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAIN_IMAGE_ADD_FROM_REPORT%>&resultID=<%=listWrongRecognition.get(i).getResultID()%>"><i style="background-color: red;" class="icon-asterisk"></i><i class="icon-wrench"></i> Sửa</a></td>
								<%
								}//end if isRead
								%>								
							</tr>
							<%
								}
 							%>
						</tbody>

					</table>
				</div>
			</div>			
			</div>
	</div>
	<div class="row-fluid">
		<div id="footer" class="span12">
			<p>
				<b>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</b>
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
<!-- <script src="Admin/Content/js/maruti.tables.js"></script> -->
<script src="Admin/Content/js/tsrt.main.js"></script>

<script type="text/javascript">
function changeShow(){
	var isRead = $('#showRead').is(':checked');	
	var isChecked = $('#showInfo').is(':checked');
	var info = "";
	if(isChecked == true)
	{
		info = "all";
	}
	window.location.href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_EXTRA%>&info="+info+"&showRead="+isRead;
}
</script>
</html>