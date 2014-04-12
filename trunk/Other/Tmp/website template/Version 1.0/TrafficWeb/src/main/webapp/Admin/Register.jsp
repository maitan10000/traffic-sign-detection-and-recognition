<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<link rel="shortcut icon" type="image/png" href="Admin/Content/images/favicon.png"/>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="Admin/Content/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="Admin/Content/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="Admin/Content/css/maruti-login.css" />
<link rel="stylesheet" href="Admin/Content/css/uniform.css" />
<link rel="stylesheet" href="Admin/Content/css/select2.css" />
<link rel="stylesheet" href="Admin/Content/css/maruti-style.css" />
<link rel="stylesheet" href="Admin/Content/css/maruti-media.css"
	class="skin-color" />
<link rel="stylesheet" href="Admin/Content/css/jquery.gritter.css" />
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style>
#content {
	margin: 0px;
	min-height: 96%;
}

#loginbox {
	
}

#registerform {
	margin-top: 50px;
}

#loginbox {
	width: 400px !important;
}

.row-fluid label {
	text-align: right !important;
}
</style>
</head>
<body>
	<div id="content">
		<div id="loginbox">
			<form id="registerform" class="form-vertical"
				action="<%=Constants.CONTROLLER_ADMIN%>" method="post">
				<div class="control-group normal_text">
					<h3>Đăng ký</h3>
				</div>
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Tên đăng nhập:</label>
							<div class="span8" align="left">
								<input type="text" name="txtUser" id="txtUser" />
							</div>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Mật khẩu:</label>
							<div class="span8" align="left">
								<input type="password" name="txtPassword" id="txtPassword" />
							</div>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Nhập lại mật khẩu:</label>
							<div class="span8" align="left">
								<input type="password" name="txtRePassword" id="txtRePassword" />
							</div>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Tên:</label>
							<div class="span8" align="left">
								<input type="text" name="txtName" id="txtName" />
							</div>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Email:</label>
							<div class="span8" align="left">
								<input type="email" name="txtEmail" id="txtEmail" />
							</div>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<span class="pull"> <!-- <a href="#"
					class="flip-link btn btn-inverse" id="to-recover">Quên mật
						khẩu?</a>  --> <a href="#" onclick="goBack()"
						class="flip-link btn btn-info">Trở về</a></span>
					<!--                     <span class="pull-left"><a href="#" class="flip-link btn btn-info" id="to-recover">Quên mật khẩu</a></span> -->
					<span class="pull-right">

						<button type="submit" class="btn btn-success" name="action"
							onclick="register(); return false;">Đăng ký</button>
					</span>
				</div>
			</form>
		</div>
		<div id="messageModal" class="modal hide">
             <div class="modal-header">
               <button data-dismiss="modal" class="close" type="button">×</button>
               <h3 id="message-header"></h3>
             </div>
             <div class="modal-body">
               
             </div>
           </div>
		<script src="Admin/Content/js/jquery.min.js"></script>
		<script src="Admin/Content/js/jquery.ui.custom.js"></script>
		<script src="Admin/Content/js/bootstrap.min.js"></script>
		<script src="Admin/Content/js/jquery.uniform.js"></script>
		<script src="Admin/Content/js/select2.min.js"></script>
		<script src="Admin/Content/js/jquery.validate.js"></script>
		<script src="Admin/Content/js/maruti.js"></script>
		<script src="Admin/Content/js/jquery.gritter.min.js"></script> 
		<script>
		function goBack()
		  {
			  window.history.back();
		  }
		
			$(document).ready(function() {
				$("#registerform").validate({
					rules : {
						txtUser : {
							required : true
						},
						txtPassword : {
							required : true,
							minlength : 6,
							maxlength : 30
						},
						txtRePassword : {
							required : true,
							equalTo : "#txtPassword"
						},
						txtEmail : {
							required : true,
							email : true
						},
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

			function register() {
				var result = $("#registerform").valid();
				if (result == true) {	
					$('#messageModal .modal-body').html('<center><img src="Admin/Content/images/loading2.gif"/></center>');
					$('#messageModal').modal('show');
					var tmpForm = document.getElementById("registerform");
					var url = "<%=GlobalValue.getServiceAddress()%>"
							+"<%=Constants.MANAGE_REGISTER%>";
					$.ajax({
						url : url,
						type : "POST",
						data : {
							userID : tmpForm.txtUser.value,
							password : tmpForm.txtPassword.value,
							name : tmpForm.txtName.value,
							email : tmpForm.txtEmail.value
						},
						success : function(result) {
							if (tmpForm.txtUser.value == result.trim()) {
									$('#messageModal .modal-body').html('<p id="message">Vui lòng kiểm tra email để kich hoạt tài khoản.</p>');
							} else if ("User exist" == result.trim()) {
								$('#txtUser').focus();
								$('#messageModal').modal('hide');
								$.gritter.add({
									title : 'Thông báo',
									text : 'Tên đăng nhập đã được sử dụng',
									sticky : false
								});
							} else if ("Email exist" == result.trim()) {
								$('#txtEmail').focus();
								$('#messageModal').modal('hide');
								$.gritter.add({
									title : 'Thông báo',
									text : 'Email đã được sử dụng',
									sticky : false
								});
							}else
							{
								$('#messageModal').modal('hide');
								$.gritter.add({
									title : 'Thông báo',
									text : 'Đăng ký không thành công vui lòng thử lại sau',
									sticky : false
								});
							}
						}
					});
				}
			}
			$('#messageModal').on('hidden', function () {				
				if($('#messageModal .modal-body').html().indexOf("Vui lòng kiểm tra email để kich hoạt tài khoản") >-1 ){
			    	window.location.href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGIN%>";
				}
			});
		</script>
</body>
</html>