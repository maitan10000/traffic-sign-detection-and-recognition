<%@page import="java.io.PrintWriter"%>
<%@page import="utility.Constants"%>
<%@page import="utility.GlobalValue"%>
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
<link rel="stylesheet" href="Admin/Content/css/datepicker.css" />	
<link rel="stylesheet" href="Admin/Content/css/jquery.gritter.css" />
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style type="text/css">
#form-area
{
	width: 600px;
	margin: auto;
}
.form-horizontal .control-label
{
	width: 295px;
}

.form-horizontal .controls
{
	margin-left: 300px;
}

.form-actions
{
	padding: 0;
	border-top: 0px;
	border-bottom: 0px;
}

.form-actions .btn
{
	margin-left: 100px;
}

.widget-content
{
	border-bottom: 0px;
}


input
{
	width: 30px;
}
</style>

</head>
<%
	String role = (String) session.getAttribute(Constants.SESSION_ROLE);
	Integer activeDay = (Integer) request.getAttribute("activeDay");
	Integer reTrainCount = (Integer) request.getAttribute("reTrainCount");
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
				<a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_CONFIG %>" title="Thiếp lập hệ thống" class="tip-bottom"><i
					class="icon-cog"></i>Thiếp lập hệ thống</a>
			</div>
		</div>		
		<div class="container-fluid">
		 	<div id="form-area" class="widget-content nopadding">
            	<form id="configure-form" class="form-horizontal">            	 
            		              
            	   <div class="control-group">
	                <label class="control-label">Số ngày giới hạn kích hoạt tài khoản</label>
	                <div class="controls">
	                  <div id="active-day-div">
	                    <input type="text" name="activeDay" id="activeDay" value="<%=activeDay%>" > ngày	                    
	                   </div>
	                </div>	 	                                     
	              </div>
	              
	              <div class="control-group">
	                <label class="control-label">Tạo mẫu mới khi số lượng ảnh nhận dạng đạt</label>
	                <div class="controls">
	                    <div id="retrain-count-div">
	                    <input type="text" name="reTrainCount" id="reTrainCount" value="<%=reTrainCount%>" > ảnh                   
	                </div>
	              </div>
	              </div>
	            	 <div class="form-actions">
		                <button type="button" class="btn btn-success" onclick="saveConfigure(); return false;" >Lưu thiết lập</button>		               
	             	 </div>
            	</form>
            </div>
		
		 <div class="widget-content">
            <div id="chart"></div>
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
	


<script src="Admin/Content/js/excanvas.min.js"></script>
<script src="Admin/Content/js/jquery.min.js"></script>
<script src="Admin/Content/js/jquery.ui.custom.js"></script>
<script src="Admin/Content/js/bootstrap.min.js"></script>
<script src="Admin/Content/js/jquery.flot.min.js"></script>
<script src="Admin/Content/js/jquery.flot.resize.min.js"></script>
<script src="Admin/Content/js/jquery.peity.min.js"></script>
<script src="Admin/Content/js/fullcalendar.min.js"></script>
<script src="Admin/Content/js/jquery.gritter.min.js"></script> 
<script src="Admin/Content/js/jquery.validate.js"></script>
<!-- <script src="Admin/Content/js/maruti.dashboard.js_bk"></script> -->
<!-- <script src="Admin/Content/js/maruti.calendar.js"></script> -->
<!-- <script src="Admin/Content/js/bootstrap-datepicker.js"></script>  -->
<script src="Admin/Content/js/jquery.uniform.js"></script>
<script src="Admin/Content/js/select2.min.js"></script>
<script src="Admin/Content/js/jquery.dataTables.min.js"></script>
<!-- <script src="Admin/Content/js/maruti.tables.js"></script> -->
<script src="Admin/Content/js/maruti.js"></script>
<script src="Admin/Content/js/tsrt.main.js"></script>


<script type="text/javascript">

$(document).ready(function(){
	$("#configure-form").validate({
		rules : {
			activeDay : {
				required : true,
				number: true,
				 min: 1
			},
			reTrainCount : {
				required : true,
				number: true,
				 min: 1
			}			
		},
		errorClass : "help-inline",
		errorElement : "span",
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});

});


function saveConfigure() {
	var result = $("#configure-form").valid();
	console.log('ok');
	if (result == true) {
		var url = "<%=GlobalValue.getServiceAddress()%>"
			+"<%=Constants.SERVER_CONFIGURE_WRITE%>";
		var tmpForm = document.getElementById("configure-form");
		$.ajax({
			url : url,
			type : "POST",
			data : {
				activeDay : tmpForm.activeDay.value,
				reTrainCount : tmpForm.reTrainCount.value,			
			},
			error : function(result) {
				result = result.responseText;
				if ("Success" == result.trim()) {
					$.gritter.add({
						title : 'Thông báo',
						text : 'Thiết lập đã được lưu',
						sticky : false
					});
				} else
				{
					$.gritter.add({
						title : 'Thông báo',
						text : 'Thiếp lập không thành công, vui lòng thử lại sau',
						sticky : false
					});
				}
			}
		});
	}
}
</script>
</body>
</html>