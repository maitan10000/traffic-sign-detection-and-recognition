<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@page import="model.Account"%>
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
<script type="text/javascript"
	src="User/Content/Scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="User/Content/bootstrap/js/bootstrap.js"></script>
<title>Traffic Sign Recognition</title>

<style type="text/css">
.image-result-resize {
	min-height: 350px;
	width: 900px;
	position: absolute;
}

.tag-image {
	position: absolute;
	border: 2px solid rgba(255, 255, 255, .5);
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border-radius: 2px;
	/*margin-top: 100px;*/
}

.tag-image:HOVER {
	border: 2px solid rgba(0, 255, 255, .5);
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
.tag-text-out
{

}
.tag-text
{
 display: none;
 position: absolute;
 margin-left: -20px;
}

.tag-inner
{
height: 25px !important;

}
.auto-complete
{
display: block;
position: absolute;
margin-top: -1px;
margin-left: -5px;
}
</style>
</head>


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
						<li><a href="/TrafficWeb/Admin/Register.jsp">Đăng Ký</a></li>
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
							class="level-top"> <span>Quản lí biển báo</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a
							href="/TrafficWeb/AdminController?action=listAccount"
							class="level-top"> <span>Quản lí người dùng</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a
							href="/TrafficWeb/AdminController?action=listReport"
							class="level-top"> <span>Quản lì phản hồi</span>
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
					<div class="image-result">
						<img id="image-result" class="image-result-resize" />
						<div class="draw-div"></div>
					</div>

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

var server = '<%=GlobalValue.getServiceAddress()%>';
var resultID = '<%= request.getAttribute("resultID")%>';// '360';//'241';
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
		//console.log(imageLink);
		$('#image-result').attr('src', imageLink);
		var orgImage = new Image();
		orgImage.onload = function() {
			var orgWidth = this.width;
			var orgHeight = this.height;
			var showWidth = $('#image-result').width();
			var showHeight = $('#image-result').height();
			//console.log(orgWidth + '---' + showWidth);
			//console.log(orgHeight + '---' + showHeight);

			$('.main-content').height(showHeight + 20);
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
			var style2 = 'padding-top: '+(listTraffic[i].locate.height * heightScale -15) + 'px;';
			var trafficID = listTraffic[i].trafficID;
			var imageLink = 'User/Content/Image/Traffic/no-image.png';			
			var trafficName = '';
			if(trafficID !== undefined)
			{
				imageLink = server + listTraffic[i].trafficImage+'?size=small';			
				trafficName = listTraffic[i].trafficName;
			}
			var content = '<div class="tag-image" order="'+(i+1)+'" id="tag-' + (i + 1)+ '" style="' + style
					+ '"><span class="image-result-number badge badge-info">'+ (i + 1) + '</span>'
					+ '<div class="tag-text-out"><div id= "tag-text'+(i+1)+'" class="tag-text input-prepend" '
					+ 'style="' + style2+'"><img id="tag-img-'+(i+1)+'" class="add-on tag-inner" src='+imageLink+' />'
					+ '<input type="hidden" name="tag-trafficID-'+(i+1)+'" value="'+trafficID+'" />'
					+ '<input class="span2 tag-inner" id="prependedInput-'+(i+1)+'" order="'+(i+1)+'" type="text" placeholder="Nhập tên biển báo" value="'+trafficName+'"/>'
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
			   // console.log("over");
			    $('#tag-text'+id).show();
			    $('#auto-complete-'+id).show();
			    $('#tag-text'+id + ' #prependedInput').focus();			    
		  }
		  else if (e.type == "mouseout") {
		    	//console.log("out");
		    	$('#tag-text'+id).hide();
		    	$('#auto-complete-'+id).hide();
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
	showResult(resultID);
</script>

</html>