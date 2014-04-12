<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Hệ thống nhận dạng biển báo - Trang quản lý</title>
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

<style type="text/css">
#train-new-area
{
	margin: 0 auto;
	margin-top:10px;
	width: 700px;
}
.image-result-resize {
	min-height: 350px;
	width: 700px;
	position: absolute;
}

.tag-image {
	position: absolute;
	border: 3px solid rgba(255, 255, 255, .5);
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border-radius: 2px;
	/*margin-top: 100px;*/
}

.tag-image:HOVER {
	border: 3px solid rgba(0, 255, 255, .5);
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border-radius: 2px;
}

.image-result-number {
	left: 2px;
	top: 2px;
	font-weight: bold;
	font-size: 15px;
}

.tag-text-out {
	left: 50%;
	position: absolute;
	/*border: red thin solid;*/
	text-align: center;
}

.tag-text {
	left: -50%;
	display: none;
	position: relative;
	text-align: center;
	z-index: 1500;
	/*border: blue thin solid;*/
}

.tag-inner {
	height: 25px !important;
	/*border: green thin solid;*/
}

.auto-complete {
	display: block;
	position: absolute;
	margin-top: -1px;
	/*margin-left: -5px;*/
	/*border: yellow thin solid;*/
	text-align: left;
}

.auto-complete ul {
	float: none;
}

#action-panel
{
	
	width:150px;
	margin: auto;
	padding-top: 5px;
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
				<a href="#" title="Thêm ảnh nhận diện" class="tip-bottom"></i> Thêm ảnh nhận diện</a>
			</div>
		</div>
		
		<div id="action-panel">
					<button class="btn btn-danger" href="#" onclick="deteleReport(); return false;"><i class="icon-trash"></i> Xóa phản hồi</button>
		</div>	
		<div class="container-fluid">
		<div id="train-new-area">
			<div class="image-result">
				<img id="image-result" class="image-result-resize" />
				<div class="draw-div"></div>
			</div>

			<div style="clear: both"></div>
		</div>
		</div>
	</div>
	<div class="row-fluid">
		<div id="footer" class="span12">			
			<p>
				<b>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</b><br>
			
			<span style="font-size:11px;">"Hệ thống giúp đỡ người dùng tra cứu, học tập biển báo
				giao thông"</span>
				</p>
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
	<script src="Admin/Content/js/maruti.js"></script>
	<script src="Admin/Content/js/maruti.dashboard.js"></script>
	<script src="Admin/Content/js/maruti.calendar.js"></script>
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
	</script>
</body>

<script type="text/javascript">
var server = '<%=GlobalValue.getServiceAddress()%>';
var resultID = '<%=request.getAttribute("resultID")%>';
var reportID = '<%=request.getAttribute("reportID")%>';
var dataJSON;

function showResult(resultID)
{
	$.ajax({
		url : server + '<%=Constants.TRAFFIC_HISTORY_VIEW%>',
			type : "GET",
			data : {
				id : resultID
			},
			success : function(resultJSON) {
				//console.log(resultJSON);
				showImage(resultJSON);
				dataJSON = resultJSON;
			}
		});
	}

	function showImage(resultJSON) {
		var imageLink = server + resultJSON.uploadedImage;		
		$('#image-result').attr('src', imageLink);
		var orgImage = new Image();
		orgImage.onload = function() {
			var orgWidth = this.width;
			var orgHeight = this.height;
			var showWidth = $('#image-result').width();
			var showHeight = $('#image-result').height();
			//console.log(orgWidth + '---' + showWidth);
			//console.log(orgHeight + '---' + showHeight);

			$('#train-new-area').height(showHeight);
			var widthScale = showWidth / orgWidth;
			var heightScale = showHeight / orgHeight;
			drawImage(resultJSON.listTraffic, widthScale, heightScale);
		}

		orgImage.src = imageLink;
	}

	//draw rectangle in image result
	function drawImage(listTraffic, widthScale, heightScale) {
		$('.draw-div').html('');
		//console.log(resultJSON.length);
		//console.log(listTraffic);
		for (var i = 0; i < listTraffic.length; i++) {
			var style = 'width: ' + listTraffic[i].locate.width * widthScale
					+ 'px;';
			style += 'height: ' + listTraffic[i].locate.height * heightScale
					+ 'px;';
			style += 'margin-left: ' + listTraffic[i].locate.x * widthScale
					+ 'px;';
			style += 'margin-top: ' + listTraffic[i].locate.y * heightScale
					+ 'px;';
			var style2 = 'padding-top: '+(listTraffic[i].locate.height * heightScale ) + 'px;';
			var trafficID = listTraffic[i].trafficID;
			var imageLink = 'User/Content/Image/Traffic/no-image.png';			
			var trafficName = '';
			if(trafficID !== undefined)
			{
				imageLink = server + listTraffic[i].trafficImage+'?size=small';			
				trafficName = listTraffic[i].trafficName;
			}
			var content = '<div class="tag-image" order="'+(i+1)+'" id="tag-' + (i + 1)+ '" style="' + style
					+ '"><span id="tag-num-'+(i+1)+'" class="image-result-number badge badge-info">'+ (i + 1) + '</span>'
					+ '<div class="tag-text-out"><div id= "tag-text'+(i+1)+'" class="tag-text input-prepend" '
					+ 'style="' + style2+'"><div><img id="tag-img-'+(i+1)+'" class="add-on tag-inner" src='+imageLink+' />'
					+ '<input type="hidden" name="tag-trafficID-'+(i+1)+'" value="'+trafficID+'" />'
					+ '<input class="span2 tag-inner" id="prependedInput-'
					+ (i+1)+'" order="'+(i+1)+'" type="text" placeholder="Nhập tên biển báo" value="'+trafficName+'"/></div>'
					+ '<div class="auto-complete" id="auto-complete-'+(i+1)+'"></div></div></div></div>';
					//+'<button class="btn tag-inner" type="button">Lưu</button></div></div></div>';
					
			//console.log(content);										
			$('.draw-div').append(content);
		}//end for							
	};

	//handle mouseover and mouseout
	$(".draw-div").on("mouseover mouseout", ".tag-image", function(e){
		var id = $(this).attr('order');
		//console.log(id);	
		 if(e.type == "mouseover") {
			    $('#tag-text'+id).show();
			    $('#auto-complete-'+id).show();
			    $('#tag-text'+id + ' #prependedInput').focus();
			    $('#tag-num-'+id).hide();
		  }
		  else if (e.type == "mouseout") {
		    	$('#tag-text'+id).hide();
		    	$('#auto-complete-'+id).hide();
		    	$('#tag-num-'+id).show();
		    	//$('#auto-complete-'+id).html('');
		  }
		});	
	
	$('.draw-div').on('input', ".tag-image",function(e){
	     var id = $(this).attr('order');
	     var keyword = $('#prependedInput-'+id).val();
	     autoComplete(keyword, id);
	 });
	
	function autoComplete(keyword, id)
	{		
		$.ajax({
			url : server + '<%=Constants.TRAFFIC_SEARCH_MANUAL%>',
			type : "GET",
			data : {
				name : keyword,
				limit : 5
			},
			success : function(resultJSON) {
				//console.log(resultJSON.length);
				appendListResult(resultJSON, id);
			}
		});
	}
	
	function appendListResult(resultJSON, id)
	{		
		var content = '<ul class="dropdown-menu auto-complete" role="menu" aria-labelledby="dropdownMenu">';
		for(var i = 0; i < resultJSON.length; i++)
		{
			var trafficName = resultJSON[i].name;
			var trafficID = resultJSON[i].trafficID;
			var imageLink = server + resultJSON[i].image;
			content += '<li><a tabindex="-1" href="#" onclick="selectTS('+id+',\''+trafficID+'\',\''+trafficName+'\',\''+imageLink +'\'); return false;"><img src="'+imageLink+'" style="width:30px; height:30px;"/>     '+trafficName+'</a></li>';
		}
		if(resultJSON.length == 0)
		{
			content += '<li style="text-align: center;" >Không tìm thấy</li>';
		}
	    content += '</ul>';	    
	    //console.log(content);
	    $('#auto-complete-'+id).html(content);
	}
	
	function selectTS(id,trafficID, name, imageLink)
	{
		$('#prependedInput-'+id).val(name);
		$('#tag-img-'+id).attr('src', imageLink);
		addTrainImage(id, trafficID);
	}
	
	function addTrainImage(id, trafficID)
	{
		//console.log(dataJSON);
		var assginTrafficID = trafficID;
		var resultID = dataJSON.resultID;
		var order = id -1;		
		console.log(assginTrafficID);
		console.log(resultID);
		console.log(order);
		$('#auto-complete-'+id).html('');
		
		$.ajax({
			url : server + '<%=Constants.TRAFFIC_TRAIN_IMAGE_ADD_FROM_REPORT%>',
			type : "GET",
			data : {
				trafficID : assginTrafficID,
				resultID : resultID,
				order : order
			},
			success : function(resultJSON) {
				console.log(resultJSON);
			}
		});
	};
	
	
	function deteleReport() {
		var action = '<%=Constants.ACTION_REPORT_DELETE%>';
		$.ajax({
				url : '<%=Constants.CONTROLLER_ADMIN%>',
				type : "GET",
				data : {
					action : action,
					reportID : reportID
				},
				success : function(result) {					
					if ("Success" == result.trim()) {									
						window.location.href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REPORT_LIST%>";
					}
				}
		});
	}
	
	showResult(resultID);
</script>
</html>
