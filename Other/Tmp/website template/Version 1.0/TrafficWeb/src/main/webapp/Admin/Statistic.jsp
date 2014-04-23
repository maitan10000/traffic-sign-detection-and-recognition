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
<link rel="stylesheet" href="Admin/Content/css/datepicker.css" />	
<link rel="stylesheet" href="Admin/Content/css/jquery.gritter.css" />
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style type="text/css">
#form-area
{
	width: 400px;
	margin: auto;
}
.form-horizontal .control-label
{
	width: 95px;
}

.form-horizontal .controls
{
	margin-left: 100px;
}

.form-actions
{
	padding: 0;
	border-top: 0px;
	border-bottom: 0px;
}

.widget-content
{
	border-bottom: 0px;
}

#type
{
	margin-left: 5px;
}
</style>

</head>
<%
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
			<li class=""><a onclick="logout(); return false;" title="Đăng xuất" href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%>"><i
					class="icon icon-share-alt" ></i> <span
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
				<a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_STATISTIC%>" title="Thống kê" class="tip-bottom"><i
					class="icon-signal"></i>Thống kê</a>
			</div>
		</div>		
		
		<div class="container-fluid">
			<div class="widget-title">
				<span class="icon"><i class="icon-bookmark"></i></span>
				<h5>Thống kê người dùng, số lượt tìm kiếm</h5>
			</div>
		 	<div id="form-area" class="widget-content nopadding">
            	<form class="form-horizontal">
            	 <div class="control-group">
	                <label class="control-label">Loại thống kê</label>
	                <div class="controls">
	                  <select id="type" name="type" class="span3" >
	                    <option value="user" >Người dùng</option>
	                    <option value="search" >Lượt tìm kiếm</option>	                   
	                  </select>
	                </div>
	              </div>
	              
            	   <div class="control-group">
	                <label class="control-label">Từ ngày</label>
	                <div class="controls">
	                  <div id="from-div" data-date="" class="input-append date datepicker">
	                    <input type="text" id="from" name="from" data-date-format="dd-mm-yyyy" disabled="disabled" class="span3" >
	                    <span class="add-on"><i class="icon-th"></i></span> </div>
	                </div>	                      
	              </div>
	              
	              <div class="control-group">
	                <label class="control-label">Đến ngày</label>
	                <div class="controls">
	                  <div id="to-div"  data-date="" class="input-append date datepicker">
	                    <input type="text" id="to" name="to"  data-date-format="dd-mm-yyyy" disabled="disabled" class="span3" >
	                    <span class="add-on"><i class="icon-th"></i></span> </div>
	                </div>
	              </div>
	            	 <div class="form-actions">
		                <button type="button" class="btn btn-success" onclick="statistic(); return false;" >Thống kê</button>		               
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
	

</body>
<script src="Admin/Content/js/excanvas.min.js"></script>
<script src="Admin/Content/js/jquery.min.js"></script>
<script src="Admin/Content/js/jquery.ui.custom.js"></script>
<script src="Admin/Content/js/bootstrap.min.js"></script>
<script src="Admin/Content/js/jquery.flot.min.js"></script>
<script src="Admin/Content/js/jquery.flot.resize.min.js"></script>
<script src="Admin/Content/js/jquery.peity.min.js"></script>
<!-- <script src="Admin/Content/js/fullcalendar.min.js"></script> -->
<script src="Admin/Content/js/jquery.gritter.min.js"></script> 
<script src="Admin/Content/js/maruti.js"></script>
<!-- <script src="Admin/Content/js/maruti.dashboard.js_bk"></script> -->
<!-- <script src="Admin/Content/js/maruti.calendar.js"></script> -->
<script src="Admin/Content/js/bootstrap-datepicker.js"></script> 
<script src="Admin/Content/js/locales/bootstrap-datepicker.vi.js" charset="UTF-8"></script>
<!-- <script src="Admin/Content/js/jquery.uniform.js"></script> -->
<script src="Admin/Content/js/select2.min.js"></script>
<script src="Admin/Content/js/jquery.dataTables.min.js"></script>
<!-- <script src="Admin/Content/js/maruti.tables.js"></script> -->
<script src="Admin/Content/js/bootbox.min.js"></script>
<script src="Admin/Content/js/tsrt.main.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">
function logout(){
	bootbox.dialog("Bạn có chắc chắn muốn đăng xuất?", [
       		         {
       				 "label" : "Hủy",
       				 "class" : "btn-success",
       				 "callback": function() {
       				 		
       				 	}
       				 }, {
       				 "label" : "Đăng xuất",
       				 "class" : "btn-danger",
       				 "callback": function() {
       					 window.location.href = "<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%>";
       				 	}
       				 }
   ]);
	
}
</script>

<script type="text/javascript">
google.load("visualization", "1", {packages:["corechart"],'language': 'vi'});
var dataJson;



$(document).ready(function(){
    $('.datepicker').datepicker({
    	 format: "yyyy-mm-dd",
    	 endDate: "+1d",
    	 todayBtn: "linked",
    	 language: "vi",
    	 autoclose: true,
    	 todayHighlight: true,
    	 calendarWeeks: true
    });

});

function statistic()
{
	var valid = false;
	var type=$('#type').val();
	var from = $('#from').val();
	var to = $('#to').val();
	
	if(from == "")
	{		
		$.gritter.add({
			title : 'Thông báo',
			text : 'Vui lòng nhập thêm thông tin',
			sticky : false
		});
		$('#from-div i').trigger('click');
	}else if(to == "")
	{		
		$.gritter.add({
			title : 'Thông báo',
			text : 'Vui lòng nhập thêm thông tin',
			sticky : false
		});
		$('#to-div i').trigger('click');
	}else
	{
		var fromDate  = Date.parse(from);
		var toDate = Date.parse(to);
		if(toDate < fromDate)
		{			
			$.gritter.add({
				title : 'Thông báo',
				text : 'Vui lòng chọn lại "Đến ngày" sau "Từ ngày"',
				sticky : false
			});
			$('#to-div i').trigger('click');
		}else
		{
			valid = true;
		}
	
	}
	
	if(valid == true)
	{
		var url="<%=GlobalValue.getServiceAddress()%>"
			+"<%=Constants.SERVER_STATISTIC_USER%>";
		if(type=="search")
		{
			url = "<%=GlobalValue.getServiceAddress()%>"
				+"<%=Constants.SERVER_STATISTIC_SEARCH%>";
		}
		
		$.ajax({
			url : url,
			type : "POST",
			data : {
				from : from,
				to: to
			},
			success : function(result) {
				dataJson = result;
				drawChart();
			}
		});			
	}	
}


function drawChart()
{
	var type=$('#type').val();
	var axisTitle = "Số lượt đăng ký";
	var title = "Biểu đồ số lượt đăng ký";
	if(type == "search")
	{
		axisTitle = "Số lượt tìm kiếm";
		title = "Biểu đồ số lượt tìm kiếm";
	}
	title += " từ ngày "+ dataJson[0].date +" đến ngày " + dataJson[dataJson.length -1].date;
	var dataChart = new google.visualization.DataTable();	
	dataChart.addColumn('date', 'Ngày');
	dataChart.addColumn('number', axisTitle);
		
	var maxValue = 0;
	var index = 0;
	while(index < dataJson.length)
 	{	
		if(dataJson[index].num > maxValue)
		{
			maxValue = dataJson[index].num;
		}
		dataChart.addRows(1);
 		dataChart.setValue(index, 0, new Date(Date.parse(dataJson[index++].date)));
 	}
	
	var options = {
		    title: title,
		    curveType: 'function',
		    "vAxis": {
				viewWindowMode:'explicit',
	            viewWindow: {
	                min:0
	            },
            },		           
		    "pointSize": "5",
		    "hAxis": {
                "slantedTextAngle": "45",
                "slantedText": "true",
            },		       
		    "pointSize": "5",		    
		    animation: { duration: 250 },
		    'height':350
		  };
	
	var chart = new google.visualization.LineChart(document.getElementById('chart'));
	
	var index = 0;
	var drawChartAnimate = function() {
		if(index < dataJson.length){
	    	dataChart.setValue(index, 1, dataJson[index++].num);
	        chart.draw(dataChart, options);
	    }
	};
	google.visualization.events.addListener(chart, 'animationfinish', drawChartAnimate);
	chart.draw(dataChart, options);    
    drawChartAnimate();
}

</script>
</html>