<%@page import="java.io.PrintWriter"%>
<%@page import="json.CategoryJSON"%>
<%@page import="json.TrafficInfoShortJSON"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="utility.Constants"%>
<%@page import="utility.GlobalValue"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Category"%>
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
<link rel="stylesheet" href="Admin/Content/css/jquery.gritter.css" />
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style type="text/css">

#paging-table_filter {
	margin-left: 20px;
}

#trafficDetailModal {
	width: 800px;
	margin-left: -400px;
}

#AddTrafficModal{
	width: 800px;
	margin-left: -400px;
}

#traffic-table_filter
{
	margin-left: 30px;
}

 .modal-body
{
	padding: 0;
	max-height: 500px;
}

.modal.fade.in
{
	top:2%;
}

#gritter-notice-wrapper
{
	z-index: 99999;
}

#EditTrafficModal{
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

#ImportFileModal .modal.fade.in
{
	top:10%;
}

#action-panel
{
	margin: 5px 20px 5px 0px;
	text-align: right;
}


</style>

</head>
<%
	ArrayList<CategoryJSON> listCat = (ArrayList<CategoryJSON>) request.getAttribute("category");
	ArrayList<TrafficInfoShortJSON> listTraffic = (ArrayList<TrafficInfoShortJSON>) request.getAttribute("listTraffic");
	int currentCate = request.getAttribute("cateID")!=null ? Integer.parseInt((String)request.getAttribute("cateID")):0;	
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
					<span class="text"><%=(String) session.getAttribute(Constants.SESSION_USERID)%></span></a></li>
			<li class=""><a title="Đăng xuất" href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%>"><i
					class="icon icon-share-alt" onclick="logout(); return false;"></i> <span
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
				<a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFIC_LIST%>" title="Quản lý biển báo" class="tip-bottom"><i
					class="icon-th"></i>Quản lý biển báo</a>
			</div>
		</div>
		<div id="action-panel">
					<button class="btn btn-success" href="#AddTrainImageModal" onclick="showAddTrafficModal(); return false;"><i class="icon-plus"></i> Thêm mới</button>
					<button class="btn btn-success" href="#" onclick="showImportFileModal(); return false;"><i class="icon-upload"></i> Nhập từ tập tin</button>
					<a class="btn btn-success" href="<%=GlobalValue.getServiceAddress()%><%=Constants.SERVER_EXPORT%>"><i class="icon-download"></i> Xuất ra tập tin</a>
					<button class="btn btn-primary" href="#" onclick="showForceTrainModal(); return false;"><i class="icon-refresh"></i> Tạo mẫu</button>
		</div>	
		<div class="container-fluid">
			<div class="widget-title">
				<span class="icon"><i class="icon-bookmark"></i></span>
				<h5>Quản lý biển báo</h5>
				<div id="show-type" align="right" class="control-group">
					<div class="controls">
						<span>Loại biển báo:</span><select id="select-type" name="cateID"
							onchange="listTrafficByCate(this.value); return false;">							
							<option class="font-Style" value="0">Tất cả</option>
							<%
								for(int i = listCat.size() - 1; i>=0 ;i--) {
							%>							
							<option class="font-Style"
								value="<%=listCat.get(i).getCategoryID()%>" 
								<%
								if(listCat.get(i).getCategoryID().equals(currentCate+""))
								{
									out.print("selected");
								}
								%>><%=listCat.get(i).getCategoryName()%></option>
							<%
								}
							%>
						</select> </select>
					</div>
				</div>
				
			</div>
			<%
				if( listTraffic != null){
			%>
			<div id="tableContent" class="panel-content">
				<div id="table-show">
					<table id="traffic-table" class="table table-bordered dataTable">
						<thead>
							<th width="5%">STT</th>
							<th width="7%">Hình ảnh</th>
							<th width="7%">Số Hiệu</th>
							<th>Tên Biển Báo</th>
							<th>Loại biển báo</th>
							<th colspan="1"></th>
							<th colspan="1"></th>
						</thead>
						<tbody>
							<%
								if( listTraffic.size()> 0){
								for(int i = 0; i< listTraffic.size();i++){
							%>

							<tr>
								<td style="text-align: center;"><%=i+1%></td>
								<td style="text-align: center;padding: 3px 2px 3px 2px; "><img style="width: 30px; height: 30px;" src="<%=GlobalValue.getServiceAddress()%><%=listTraffic.get(i).getImage()%>?size=small"/></td>
								<td style="text-align: center;"><%=listTraffic.get(i).getTrafficID()%></td>
								<td><%=listTraffic.get(i).getName()%></td>
								<td style="text-align: center;"><%=listTraffic.get(i).getCategoryName()%></td>
								<td style="text-align: center;"><button class="btn btn-primary btn-mini" href="#" data-toggle="modal"
									onclick="showEditTrafficModal('<%=listTraffic.get(i).getTrafficID()%>'); return false;"><i class="icon-edit"></i> Sửa</button></td>
								<td style="text-align: center;"><button class="btn btn-danger btn-mini" href="#" onclick="showDeleteTrafficModal('<%=listTraffic.get(i).getTrafficID()%>'); return false;"><i class="icon-trash"></i> Xóa</button></td>

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
	
	<!-- Add Traffic modal -->
	<div class="modal fade" id="AddTrafficModal" tabindex="-1"
		role="dialog" style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End Add Traffic modal-->
	
	<!-- Edit Traffic modal -->
	<div class="modal fade" id="EditTrafficModal" tabindex="-1" role="dialog"
		style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End Edit Traffic modal-->
		
	<!-- Delete Traffic modal-->
        <div id="DeleteTrafficModal" class="modal hide">
        <input id="delete-trafficID" type="hidden"/>
          <div class="modal-header">
            <button data-dismiss="modal" class="close" type="button">×</button>
            <h3>Xác nhận xóa</h3>
          </div>
          <div class="modal-body" style="text-align: center;">   
          <p>Xác nhận xóa</p>         
          </div>
          <div class="modal-footer"> 
	          <a data-dismiss="modal" class="btn btn-primary" href="#" onclick="deteleTrafficInfo(); return false;">Đồng ý</a> 
	          <a data-dismiss="modal" class="btn" href="#">Hủy</a> 
          </div>
        </div>
	<!-- End Delete Traffic modal-->
	
	<!-- Import File modal-->
	<div class="modal fade" id="ImportFileModal" tabindex="-1" role="dialog"
		style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true">
		 <div class="modal-header">
            <button data-dismiss="modal" class="close" type="button">×</button>
            <h3>Nhập thông tin từ tập tin</h3>
          </div>
          <div class="modal-body">   
	          <div style="text-align: center;">          
		          <form id="import-form" method="post"
					enctype="multipart/form-data">			
					Chọn tập tin:<input type="file" name="file" />	
					 
		          <input type="hidden" name="userID" value="<%=(String) session.getAttribute(Constants.SESSION_USERID)%>" />
					</form>
				</div>						
          </div>          
          <div class="modal-footer">
          	<img id="loading-image" src="Admin/Content/images/loading2.gif"/>  
	          <a class="btn btn-primary" href="#" onclick="importFile(); return false;">Gửi</a> 
	          <a data-dismiss="modal" class="btn" href="#">Hủy</a> 
          </div>
	</div>
	<!-- End Import File modal-->
	
	
	
	
	
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End modal 1 -->
	
	<!-- Traffic Detail modal -->
	<div class="modal fade" id="EditTrafficModal" tabindex="-1"
		role="dialog" style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End detail modal-->
	
	<!-- Add Train Image modal -->
	<div class="modal fade" id="AddTrainImageModal" tabindex="-1"
		role="dialog" style="display: none;" aria-labelledby="myModalLabel"
		aria-hidden="true"></div>
	<!-- End Add Train Image modal-->
	
	<div id="myAlert" class="modal hide">
		<div class="modal-header">
			<button data-dismiss="modal" class="close" type="button">×</button>
		</div>
		<div class="modal-body">
			<p></p>
		</div>
		<div class="modal-footer">
			<a data-dismiss="modal" class="btn btn-primary" href="#">Đóng</a>
		</div>
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
<script src="Admin/Content/js/jquery.gritter.min.js"></script> 
<script src="Admin/Content/js/maruti.js"></script>
<script src="Admin/Content/js/maruti.dashboard.js_bk"></script>
<script src="Admin/Content/js/maruti.calendar.js"></script>
<!-- <script src="Admin/Content/js/jquery.uniform.js"></script> -->
<script src="Admin/Content/js/select2.min.js"></script>
<script src="Admin/Content/js/jquery.dataTables.min.js"></script>
<!-- <script src="Admin/Content/js/maruti.tables.js"></script> -->
<script src="Admin/Content/js/tsrt.main.js"></script>
<script type="text/javascript">
function logout(){
	var action = 'logout';
	$.ajax({
		url: '<%=Constants.CONTROLLER_ADMIN%>',
		type: "GET",		
	});
}
</script>


<script type="text/javascript">		
	function showAddTrafficModal(){
		var action = '<%=Constants.ACTION_TRAFFIC_ADD%>';
		$.ajax({
			url: '<%=Constants.CONTROLLER_ADMIN%>',
			type: "GET",
			data: {action : action},
			success: function (result) {
				$("#AddTrafficModal").html(result);
				$("#AddTrafficModal").modal('show');
			}
			
		});
	}
	
	function showEditTrafficModal(trafficID){
		var action = '<%=Constants.ACTION_TRAFFIC_EDIT%>';
		$.ajax({
			url: '<%=Constants.CONTROLLER_ADMIN%>',
			type: "GET",
			data: {action : action, trafficID : trafficID},
			success: function (result) {
				//$("#myModal").modal('hide');
				$("#EditTrafficModal").html(result);
				$("#EditTrafficModal").modal('show');
			}
			
		});
	}	
	
	function showDeleteTrafficModal(trafficID)
	{
		$("#DeleteTrafficModal").modal("show");
		//$("#DeleteTrafficModal .modal-body").html('Xác nhận xóa');
		$("#DeleteTrafficModal #delete-trafficID").val(trafficID);
	}
	
	function deteleTrafficInfo()
	{
		var trafficID = $('#delete-trafficID').val();
		$.ajax({
			url: '<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_DELETE%>',
			type: "GET",
			data: {trafficID : trafficID},
			success: function (result) {
				if("Success" == result.trim())
				{
					location.reload();
				}
			}
			
		});		
	}

	function listTrafficByCate(cateID)
	{
		window.location.href='<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAFFIC_LIST%>&cateID='+cateID;	
	}
	
	function showImportFileModal()
	{
		$("#ImportFileModal").modal("show");
		$('#loading-image').hide();
	}
	
	function importFile()
	{		
		$('#loading-image').show();
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showResult(xhr.responseText);
			}
		}
		var tmpForm = document.getElementById("import-form");		
		
		var formData = new FormData();
		formData.append("file", tmpForm.file.files[0]);
		formData.append("userID", tmpForm.userID.value);		
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.SERVER_IMPORT%>');
		xhr.overrideMimeType('text/plain; charset=utf-8');
		xhr.send(formData);
	}

	function showResult(result) {
		if (result.trim() != "Success") {
			$.gritter.add({
				title : 'Thông báo',
				text : 'Nhập tập tin thất bại',
				sticky : false
			});
		} else {	
			$("#ImportFileModal").modal('hide');
			$.gritter.add({
				title : 'Thông báo',
				text : 'Thông tin đã được nhập',
				sticky : false
			});
		}
	}
	
	function showForceTrainModal()
	{
		$.gritter.add({
			title : 'Thông báo',
			text : 'Đang tạo lại mẫu',
			sticky : false
		});
		$.ajax({
			url: '<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAIN_IMAGE_RETRAIN_ALL%>',
			type: "GET",
			success: function (result) {
				$.gritter.add({
					title : 'Thông báo',
					text : 'Tạo mẫu hoàn thành',
					sticky : false
				});
			}
			
		});		
	}
</script>

</html>