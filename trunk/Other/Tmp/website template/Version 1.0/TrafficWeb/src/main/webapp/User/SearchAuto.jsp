<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="Content/Css/Main.css" rel="stylesheet" type="text/css" />
<link href="Content/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="Content/Scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="Content/bootstrap/js/bootstrap.js"></script>
<link href="Content/Css/searchauto.css" rel="stylesheet" type="text/css" />
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
												onclick="reportWrongRecognize(100); return false;">Sai
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
							var server = 'http://bienbaogiaothong.tk/';
							server = 'http://localhost:8080/Traffic/';
							//<!-- Javascript function to add thumbnails and progress bars to the grid -->
							function showThumbnails() {
								$('#preview').html('');
								$('.draw-div').html('');
								$('#preview').show();
								$('#preview').append('<div id="progress" class="progress progress-striped active"><span class="bar" style="width: 0%"></span></div>');
								var image = document.getElementById("image-result");
								var file = document.getElementById('file').files[0];
								var fileReader = new FileReader();
								fileReader.onload = (function(img) {
									return function(e) {
										img.src = e.target.result;
									};
								})(image);
								fileReader.readAsDataURL(file);
								uploadFile(file);
							}
							//<!-- The actual upload function that updates progress bars and uploads the images to a script (in my case php) -->							 
							function uploadFile(file) {
								var xhr = new XMLHttpRequest();
								xhr.onreadystatechange = function() {
									if (xhr.readyState == 4) {
										showResult(xhr.responseText, file.name);
									}
								}
								var formData = new FormData();
								formData.append('file', file);
								formData.append('creator', 'user1');
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
								xhr.open("POST", server + "rest/Service/SearchAuto");
								xhr.overrideMimeType('text/plain; charset=utf-8');
								xhr.send(formData);
							}

							function showResult(resultData, fileName) {
								//add list result
								//console.log(resultData);
								var resultJSON = JSON.parse(resultData);
								$('.list-result ol').html('');
								for (var i = 0; i < resultJSON.length; i++) {
									var trafficName = 'Không rõ';
									var imageLink = 'Content/Image/Traffic/no-image.png';
									var addInfo = '';
									if (resultJSON[i].trafficID != undefined) {
										trafficName = resultJSON[i].trafficName;
										addInfo = 'title="Kích để xem chi tiết" onclick="viewDetail(\'' + resultJSON[i].trafficID + '\');return false;" ';
										imageLink = server + resultJSON[i].trafficImage;
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
									drawImage(resultJSON, widthScale, heightScale);
								}
								orgImage.src = $('#image-result').attr('src');
							}

							//draw rectangle in image result
							function drawImage(resultJSON, widthScale, heightScale) {
								$('.draw-div').html('');
								//console.log(resultJSON.length);
								for (var i = 0; i < resultJSON.length; i++) {
									var style = 'width: ' + resultJSON[i].locate.width * widthScale + 'px;';
									style += 'height: ' + resultJSON[i].locate.height * heightScale + 'px;';
									style += 'margin-left: ' + resultJSON[i].locate.x * widthScale + 'px;';
									style += 'margin-top: ' + resultJSON[i].locate.y * heightScale + 'px;';
									var content = '<div class="tag-image" id="tag-' + (i + 1) + '" style="' + style + '"><span class="image-result-number badge badge-info">' + (i + 1) + '</span></div>';
									//console.log(content);										
									$('.draw-div').append(content);
								}//end for							
							}

							//view detail
							function viewDetail(trafficID) {
								//alert(trafficID);
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
										$("#myModal").modal('show');
									}
								});
							}
							function reportWrongRecognize(id) {
								alert('Report wrong recognize: ' + id);
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
							<a href="#"><img src="Image/brands/brand3.gif" alt=""></a>
							<a href="#"><img src="Content/Image/brands/brand4.gif" alt=""></a>
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
							thống cửa hàng Celebrity đã ngày càng phát triển và hoàn thiện
							hơn so với những năm về trước</p>
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
</body>
</html>