<%@page import="java.awt.Container"%>
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
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style>
.widget-content
{
	border-bottom: 0px;
}
.stat-boxes2 li
{
/* 	height: 100px; */
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
				<a href="<%=Constants.CONTROLLER_ADMIN%>" title="Trang chủ"
					class="tip-bottom"><i class="icon-home"></i>Trang chủ</a>
			</div>
		</div>
		<div class="container-fluid">
		
		<div class="widget-content">
			<div class="row-fluid">
				<div class="span8">
	            	<div id="chart"></div>
	         	</div>         	
	            <div class="span4">
              <ul class="stat-boxes2">
                <li>
                  <div class="left peity_bar_neutral"><span><span style="display: none;">2,4,9,7,12,10,12</span>
                    <img src="Admin/Content/images/chart1.png"/>
                    </span></div>
                  <div class="right"> <strong><span id="totalSearch" ></span></strong>  Lượt tìm kiếm </div>
                </li>
                <li>
                  <div class="left peity_line_neutral"><span><span style="display: none;">10,15,8,14,13,10,10,15</span>
                    <img src="Admin/Content/images/chart2.png"/>
                    </span></div>
                  <div class="right"> <strong><span id="totalAccount" ></span></strong>  Người dùng</div>
                </li>
<!--                 <li> -->
<!--                   <div class="left peity_bar_bad"> -->
<!--                   	<div id="pieChart"></div> -->
<!--                   </div> -->
<!--                 </li>                 -->
              </ul>
            </div>
          	</div>
          </div>
        </div>
		<!-- End container fluid -->                
	</div>
	
	<div class="row-fluid">
		<div id="footer" class="span12">
			<p>
				<b>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</b><br> <span
					style="font-size: 11px;">"Hệ thống giúp đỡ người dùng tra
					cứu, học tập biển báo giao thông"</span>
			</p>
		</div>
	</div>
	<script src="Admin/Content/js/excanvas.min.js"></script>
	<script src="Admin/Content/js/jquery.min.js"></script>
	<script src="Admin/Content/js/jquery.ui.custom.js"></script>
	<script src="Admin/Content/js/bootstrap.min.js"></script>
<!-- 	<script src="Admin/Content/js/jquery.flot.min.js"></script> -->
<!-- 	<script src="Admin/Content/js/jquery.flot.resize.min.js"></script> -->
	<script src="Admin/Content/js/jquery.peity.min.js"></script>
	<script src="Admin/Content/js/fullcalendar.min.js"></script>
	<script src="Admin/Content/js/maruti.js"></script>
<!-- 	<script src="Admin/Content/js/maruti.dashboard.js"></script> -->
	<script src="Admin/Content/js/maruti.calendar.js"></script>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
		// This function is called from the pop-up menus to transfer to
		// a different page. Ignore if the value returned is a null string:
		function goPage(newURL) {

			// if url is empty, skip the menu dividers and reset the menu selection to default
			if (newURL != "") {

				// if url is "-", it is this page -- reset the menu:
				if (newURL == "-") {
					resetMenu();
				}
				// else, send page to designated URL            
				else {
					document.location.href = newURL;
				}
			}
		}

		// resets the menu selection upon entry to this page:
		function resetMenu() {
			document.gomenu.selector.selectedIndex = 2;
		}
		
		google.load("visualization", "1", {packages:["corechart"],'language': 'vi'});
		var dataJson;
		
		function getStatic()
		{

			var to = new Date();
			//to.setDate(to.getDate()+1);
			var from = new Date();
			from.setDate(from.getDate()-30);
			
			var toString = to.toISOString().slice(0, 10);
			var fromString = from.toISOString().slice(0, 10);
			
			var url = "<%=GlobalValue.getServiceAddress()%>"
					+"<%=Constants.SERVER_STATISTIC_SEARCH%>";			
			$.ajax({
				url : url,
				type : "POST",
				data : {
					from : fromString,
					to: toString
				},
				success : function(result) {
					dataJson = result;
					drawChart();
				}
			});		
		}
		

		function drawChart()
		{
			var axisTitle = "Số lượt tìm kiếm";
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
				    title: "Số lượt tìm kiếm trong vòng 30 ngày trở lại",
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
				    animation: { duration: 250 },
				    'height':350,
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
		
		
		function getServerInfo()
		{
			var url = "<%=GlobalValue.getServiceAddress()%>"
				+"<%=Constants.SERVER_SERVER_INFO%>";			
			$.ajax({
				url : url,
				type : "GET",				
				success : function(result) {
					$('#totalSearch').text(result.totalSearch);
					$('#totalAccount').text(result.totalUser);
					
					/*
					var freePercent = Math.round(result.freeSpace/(1024*1024*1024)*100)/100;
					var used = Math.round((result.space- result.freeSpace)/(1024*1024*1024)*100)/100;
					
					  var data = google.visualization.arrayToDataTable([
					                                                    ['', 'GB'],
					                                                    ['Đã dùng',  used],
					                                                    ['Còn trống',    freePercent ]
					                                                  ]);


					var options = {
					          title: 'Dung lượng lưu trữ (GB)',
					          'width':350,
					          colors:['#49CCED','#5BB75B'],
					          animation:{
					              duration: 1000,
					              easing: 'out'
					            },

					        };

			        var piechart = new google.visualization.PieChart(document.getElementById('pieChart'));
			        piechart.draw(data, options);
			        */

				}
			});		
		}
		
		
		$(document).ready(function(){
			getStatic();
			getServerInfo();
		});
		
	</script>
</body>

</html>
