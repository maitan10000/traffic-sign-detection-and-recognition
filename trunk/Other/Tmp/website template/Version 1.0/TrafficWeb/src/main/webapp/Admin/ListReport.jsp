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
	String type = (String)request.getAttribute("type");
	ArrayList<ReportShortJSON> listReport = (ArrayList<ReportShortJSON>) request.getAttribute("listReport");
	String role = (String) session.getAttribute(Constants.SESSION_ROLE);
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
						hồi</span></a></li>
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
			</div>
		</div>
		<div class="container-fluid">
			<div class="widget-title">
				<span class="icon"><i class="icon-bookmark"></i></span>
				<h5>Quản lý phản hồi</h5>
				<div id="show-type" align="right" class="control-group">
					<div class="controls">
						<span>Loại phản hồi:</span> <select id="select-type" name="type"
							onchange="listReport(this.value); return false;">
							<option value="0">Tất cả</option>
							<%
								if("1".equals(type)){
							%>
							<option value="1" selected>Nhận diện sai</option>
							<option value="2">Thông tin sai</option>
							<%
								}else if("2".equals(type)){
							%>
							<option value="1">Nhận diện sai</option>
							<option value="2" selected>Thông tin sai</option>
							<%
								}else{
							%>
							<option value="1">Nhận diện sai</option>
							<option value="2">Thông tin sai</option>
							<%
								}
							%>

						</select>
					</div>
				</div>
			</div>
			<%
				if (listReport != null) {
			%>
			<div id="tableContent" class="panel-content">
				<div id="table-show">
					<table id="paging-table" class="table table-bordered dataTable">
						<thead>
							<tr role="row">
								<th width="4%">STT</th>
								<th width="45%">Nội dung</th>
								<th width="9%">Người gửi</th>
								<th width="15%">Loại phản hồi</th>
								<th width="17%">Thời gian gửi</th>
								<th ></th>
								<th ></th>
							</tr>
						</thead>
						<tbody role="alert" aria-live="polite" aria-relevant="all">
							<%
								if (listReport.size() > 0) {for (int i = 0; i < listReport.size(); i++) {
							%>
							<tr>

								<td style="text-align: center;"><%=i+1%></td>
								<%
									if (listReport.get(i).getContent().length() > 45){									
								String content = listReport.get(i).getContent().substring(0, 42) + "...";
								%>
								<td><%=content%></td>
								<%
									}else{
								%>
								<td><%=listReport.get(i).getContent()%></td>
								<%
									}
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								%>
								<td style="text-align: center;"><%=listReport.get(i).getCreator()%></td>
								<td style="text-align: center;">
								<%
								if(listReport.get(i).getType() == 1)
								{
									out.print("<span class='label label-warning'>Nhận diện sai</span>");
								}else
								{
									out.print("<span class='label label-important'>Thông tin sai</span>");
								}
								%>
								</td>								
								<td style="text-align: center; font-size: 12px;"><%=dateFormat.format(listReport.get(i).getCreateDate())%></td>
								<td style="text-align: center;"><button class="btn btn-primary btn-mini" href="#myModal"
									data-toggle="modal"
									onclick="showDetails('<%=listReport.get(i).getReportID()%>'); return false;"><i class="icon-list-alt"></i> Chi tiết</button></td>
								<td style="text-align: center;"><button class="btn btn btn-danger btn-mini"  href="#"
									onclick="deleteReport('<%=listReport.get(i).getReportID()%>'); return false;"><i class="icon-trash"></i> Xóa</button></td>
							</tr>
							<%
								}
																																																																																															}
							%>
						</tbody>

					</table>
				</div>
			</div>
			<%
				}
			%>
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
		aria-hidden="true" ></div>
	<!-- End detail modal-->
	
	
	<!-- Alert modal -->
	<div id="myAlert" class="modal hide">
              <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button">×</button>                
              </div>
              <div class="modal-body">
                <p></p>
              </div>
              <div class="modal-footer"> <a data-dismiss="modal" class="btn btn-primary" href="#">Đóng</a></div>
            </div>
	<!-- End Alert modal -->
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

function showDetails(reportID){
	var action = '<%=Constants.ACTION_REPORT_VIEW%>';
	$.ajax({
		url: '<%=Constants.CONTROLLER_ADMIN%>',
		type: "GET",
		data: {action : action, reportID : reportID},
		success: function (result) {
			$("#myModal").html(result);
		}
		
	});
}
function showTrafficDetails(trafficID){
	var action = '<%=Constants.ACTION_TRAFFIC_EDIT%>';
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

function showResultDetails(resultID){
	var action = '<%=Constants.ACTION_HISTORY_VIEW%>';
	$.ajax({
		url: '<%=Constants.CONTROLLER_ADMIN%>',
		type: "GET",
		data: {action : action, resultID : resultID},
		success: function (result) {
			$("#myModal").modal('hide');
			$("#detailModal").html(result);
			$("#detailModal").modal('show');			
		}
		
	});
}

//ajax to delete report
function deleteReport(reportID) {
	var action = '<%=Constants.ACTION_REPORT_DELETE%>';
	$
			.ajax({
				url : '<%=Constants.CONTROLLER_ADMIN%>',
				type : "GET",
				data : {
					action : action,
					reportID : reportID
				},
				success : function(result) {					
					if ("Success" == result.trim()) {									
						 location.reload(); 
												
					}
				}

			});
}

//List report by type
function listReport(type){
	window.location.href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_LIST%>&type="+type;	
}


</script>

</html>