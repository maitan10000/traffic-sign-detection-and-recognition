<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
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
<link href="User/Content/Css/searchauto.css" rel="stylesheet"
	type="text/css" />

<style>
.list-result {
	min-height: 350px;
	width: 300px;
	/*border: red thin solid;*/
	float: left;
	padding-bottom: 20px;
	overflow: auto;
}

.search-area {
	min-height: 350px;
	width: 600px;
	/*border: blue thin solid;*/
	float: right;
	position: relative;
	/*z-index: 1500;*/
}

.list-image-result {
	width: 50px;
	height: 50px;
	/*border: blue thin solid;*/
	margin-top: 10px;
}

.image-result {
	min-height: 350px;
}

.image-result-resize {
	min-height: 350px;
	width: 600px;
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

.image-result-number {
	left: 2px;
	top: 2px;
	font-weight: bold;
	font-size: 15px;
}

legend {
	margin-bottom: 5px;
}

#btn-wrong-recognize {
	float: right;
}

#previewPane {
	margin-left: 0px;
}
</style>
<title>Traffic Sign Recognition</title>
</head>
<body>
	<div class="wrapper">
		<div class="page">
			<div class="header-container">
				<header>
				<div class="clearfix">
					<div class="card-top"></div>
				</div>
				<div class="logo-Container">
					<h1 class="logo">
						<a href="#"> <img src="Content/Image/logo.png" />
						</a>
					</h1>
					<!--   _____________ -->
					<ul class="links">
						<li><a href="customer/account/login/index.html"
							title="Log In">Đăng Nhập</a></li>
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
						<li class="level0 nav-3 level-top"><a href="SearchManual.jsp"
							class="level-top"> <span>Tra Cứu Biển Báo</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a href="#"
							class="level-top"> <span>Nhận Diện Tự Động</span>
						</a></li>
						<li class="level0 nav-4 level-top"><a href="#"
							class="level-top"> <span>Danh Sách Đã Lưu</span>
						</a></li>
						<li class="level0 nav-5 level-top last"><a href="#"
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
					<div class="list-result">
						<fieldset>
							<legend>Kết quả:</legend>
							<ol>
							</ol>
						</fieldset>

					</div>
					<div class="search-area">
						<!-- HTML upload form and preview grid using block grid  -->
						<div class="image-upload">
							<div class="form-upload">
								<form>
									<fieldset>
										<legend>
											Tải hình ảnh: <input type="file" id="file" accept="image/*"
												name="file" onchange="showThumbnails()" />
											<button id="btn-wrong-recognize" class="btn btn-warning"
												onclick="reportWrongRecognize(this.value); return false;" disabled="disabled">Sai
												kết quả</button>
										</legend>
										<div id="preview"></div>
										<div class="image-result">
											<img id="image-result" class="image-result-resize" />
											<div class="draw-div"></div>
										</div>
									</fieldset>
								</form>
							</div>

						</div>

						<script>
						var server = '<%=GlobalValue.getServiceAddress()%>';
							//var server = 'http://bienbaogiaothong.tk/';
							//server = 'http://localhost:8080/Traffic/';
							
							//<!-- Resie image -->
							function dataURItoBlob(dataURI) {
							    var binary = atob(dataURI.split(',')[1]);
							    var array = [];
							    for(var i = 0; i < binary.length; i++) {
							        array.push(binary.charCodeAt(i));
							    }
							    return new Blob([new Uint8Array(array)], {type: 'image/jpeg'});
							}
							
							//<!-- Javascript function to add thumbnails and progress bars to the grid -->
							function showThumbnails() {
								$('#preview').html('');
								$('.draw-div').html('');
								$('#preview').show();
								$('#preview').append('<div id="progress" class="progress progress-striped active"><span class="bar" style="width: 0%"></span></div>');
								
								var file = document.getElementById('file').files[0];
								var image = document.getElementById("image-result");

								var tempImage = new Image();
								tempImage.onload = function() {						             
							         var MAX_WIDTH = 1000;
							         var MAX_HEIGHT = 1000;
							         var tempW = tempImage.width;
							         var tempH = tempImage.height;
							         if (tempW > tempH) {
							            if (tempW > MAX_WIDTH) {
							               tempH *= MAX_WIDTH / tempW;
							               tempW = MAX_WIDTH;
							            }
							         } 
							         else {
							            if (tempH > MAX_HEIGHT) {
							               tempW *= MAX_HEIGHT / tempH;
							               tempH = MAX_HEIGHT;
							            }
							         }
							         
							         var canvas = document.createElement('canvas');
							         canvas.width = tempW;
							         canvas.height = tempH;
							         var ctx = canvas.getContext("2d");
							         ctx.drawImage(this, 0, 0, tempW, tempH);
							         var data = canvas.toDataURL("image/jpeg");
							         image.src = data;
							         var blodFile = dataURItoBlob(data);							
									uploadFile(blodFile);
								};
							         
								var fileReader = new FileReader();
								fileReader.onload = (function(img) {
									return function(e) {
										img.src = e.target.result;										
									};
								})(tempImage);
								fileReader.readAsDataURL(file);	
								//uploadFile(file);							
							};
							
							//<!-- The actual upload function that updates progress bars and uploads the images to a script (in my case php) -->							 
							function uploadFile(file) {
								var xhr = new XMLHttpRequest();
								xhr.onreadystatechange = function() {
									if (xhr.readyState == 4) {
										showResult(xhr.responseText, file.name);
									}
								}
								var formData = new FormData();
								var fileName = document.getElementById('file').files[0].name;
								formData.append('file', file, fileName);
								formData.append('userID', 'user1');
								xhr.upload.addEventListener("progress", function(e) {
									if (e.lengthComputable) {
										var percentage = Math.round((e.loaded * 100) / e.total);
										$("#progress").html('<span class="bar" style="width: ' + percentage + '%"></span>');
										//console.log("%: "+ percentage);
									}
								}, false);
								xhr.upload.addEventListener("load", function(e) {
									$("#progress").html('<span class="bar" style="width: 100%"></span>');
									//console.log("%: 100");
									$('#preview').hide();
								}, false);
								xhr.open("POST", server + '<%=Constants.TRAFFIC_SEARCH_AUTO%>');
								xhr.overrideMimeType('text/plain; charset=utf-8');
								xhr.send(formData);
							};

							function showResult(resultData, fileName) {
								//add list result
								//console.log(resultData);
								var resultJSON = JSON.parse(resultData);
								if(resultJSON.resultID != -1)
								{
									$('#btn-wrong-recognize').val(resultJSON.resultID);
									$('#btn-wrong-recognize').prop("disabled",false);
								}else
								{
									$('#btn-wrong-recognize').prop("disabled",true);
								}
								
								$('.list-result ol').html('');
								var listTraffic = resultJSON.listTraffic;
								for (var i = 0; i < listTraffic.length; i++) {
									var trafficName = 'Không rõ';
									var imageLink = 'User/Content/Image/Traffic/no-image.png';
									var addInfo = '';
									if (listTraffic[i].trafficID != undefined) {
										trafficName = listTraffic[i].trafficName;
										addInfo = 'title="Kích để xem chi tiết" onclick="viewDetail(\'' + listTraffic[i].trafficID + '\');return false;" ';
										imageLink = server + listTraffic[i].trafficImage;
									}
									var liContent = '<li><a href="#" '+ addInfo + '><img class="list-image-result list-group-item" src="'+imageLink+'"> <span >' + trafficName + '</span></a></li>';
									$('.list-result ol').append(liContent);
								}

								//show image result
								//var iamgeLink = server + 'rest/Image/Upload/'
								//		+ fileName;
								//$('#image-result').attr('src', iamgeLink);
								//$('.image-result').show();

								var orgImage = new Image();
								orgImage.onload = function() {
									var orgWidth = this.width;
									var orgHeight = this.height;
									var showWidth = $('#image-result').width();
									var showHeight = $('#image-result').height();
									//console.log(orgWidth + '---' +showWidth);
									//console.log(orgHeight + '---' +showHeight);

									var contentHight = $('#image-result').height() + 100;
									if ($('.list-result').height() > contentHight - 50) {
										$('.list-result').height(contentHight - 50);
									}
									$('.main-content').height(contentHight);

									var widthScale = showWidth / orgWidth;
									var heightScale = showHeight / orgHeight;
									drawImage(listTraffic, widthScale, heightScale);
								}
								orgImage.src = $('#image-result').attr('src');
							}

							//draw rectangle in image result
							function drawImage(listTraffic, widthScale, heightScale) {
								$('.draw-div').html('');
								//console.log(resultJSON.length);
								for (var i = 0; i < listTraffic.length; i++) {
									var style = 'width: ' + listTraffic[i].locate.width * widthScale + 'px;';
									style += 'height: ' + listTraffic[i].locate.height * heightScale + 'px;';
									style += 'margin-left: ' + listTraffic[i].locate.x * widthScale + 'px;';
									style += 'margin-top: ' + listTraffic[i].locate.y * heightScale + 'px;';
									var content = '<div class="tag-image" id="tag-' + (i + 1) + '" style="' + style + '"><span class="image-result-number badge badge-info">' + (i + 1) + '</span></div>';
									//console.log(content);										
									$('.draw-div').append(content);
								}//end for							
							};

							//view detail
							function viewDetail(trafficID) {
								//alert(trafficID);
								var action = "viewDetail";
								$.ajax({
									url : "<%=Constants.CONTROLLER_USER%>",
									type : "GET",
									data : {
										action : action,
										trafficID : trafficID
									},
									success : function(result) {
										$("#myModal").html(result);
										$("#myModal").modal('show');
									}
								});
							};
							
							function reportWrongRecognize(id) {
								//alert('Report wrong recognize: ' + id);
								$('#reference_id').val(id);
								$('#myModal').modal('hide');
								$('#reportModal').modal('show');
							};

							// ajax send report
							function sendReport(trafficID){
								var type = '1';
								var content = document.getElementById("txtContent").value;
								var action = "reportTraffic";
								$.ajax({
									url : '<%=Constants.CONTROLLER_USER%>',
									type : "POST",
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
							};

							$(document).ready(function() {								
								// event on shown to set onclick
								$('#reportModal').on('show', function() {
									document.getElementById("txtContent").value = '';
									var trafficID = document.getElementById("reference_id").value;
									var functionName = 'sendReport("' + trafficID +'")';
									var button = document.getElementById("btnSubmitReport");
									button.getAttributeNode("onclick").value = functionName;
								});																
							});

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
						</script>
					</div>
					<div style="clear: both"></div>
				</div>
				<div class="contentTable "></div>
				<div style="clear: both"></div>
			</div>
		</div>
		<div class="footer-container">
			<div class="footer">
				<div class="footer-brands">
					<div class="brands">
						<a href="#"><img src="Content/Image/brands/brand1.gif" alt=""></a>
						<a href="#"><img src="Content/Image/brands/brand2.gif" alt=""></a>
						<a href="#"><img src="Image/brands/brand3.gif" alt=""></a> <a
							href="#"><img src="Content/Image/brands/brand4.gif" alt=""></a>
						<a href="#"><img src="Content/Image/brands/brand5.gif" alt=""></a>
						<a href="#"><img src="Content/Image/brands/brand6.gif" alt=""></a>
						<a href="#"><img src="Content/Image/brands/brand7.gif" alt=""></a>
						<a href="#"><img src="Content/Image/brands/brand8.gif" alt=""></a>
						<a href="#"><img src="Content/Image/brands/brand9.gif" alt=""></a>
					</div>
				</div>
				<div class="footer-left">
					<p>
						<b>HỆ THỐNG NHẬN DIỆN BIỂN BÁO</b>
					</p>
					<p>"Chúng tôi khác biệt...!" - Sau 3 năm thành lập đến nay hệ
						thống cửa hàng Celebrity đã ngày càng phát triển và hoàn thiện hơn
						so với những năm về trước</p>
				</div>
				<div class="footer-left">
					<p>
						<span style="border-bottom: dotted 1px #fafafa;">TRUNG TÂM
							CELEBRITY</span>
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
			<textarea rows="3" cols="12" id="txtContent"
				style="width: 515px; resize: none;"></textarea>
			<input type="hidden" id="reference_id" />
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary" id="btnSubmitReport" onclick="">Gửi
				Ý Kiến</button>
		</div>
	</div>
</body>
</html>