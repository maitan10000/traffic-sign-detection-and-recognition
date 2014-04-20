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
<link rel="stylesheet" href="Admin/Content/css/tsrt-style.css" />
<style>
#content {
	margin: 0px;
	min-height: 96%;
}
#loginbox {
	width: 400px;
}

#loginbox .label {
	text-align: left;
}

#loginform {
	margin-top: 100px;
}
#system-name
{
	text-align:center;
	width: 450px;
	margin: auto;
}
#system-name h3
{
	padding-top: 50px;
}
</style>
</head>
<body>
	<div id="content">
		<div id="system-name">
			<h3><a href="./">HỆ THỐNG NHẬN DẠNG BIỂN BÁO</a></h3>
		</div>
		<div id="loginbox">
			<form id="loginform" class="form-vertical" action="<%=Constants.CONTROLLER_ADMIN%>" method="post">
				<div class="control-group normal_text">
					<h3>Đăng nhập</h3>
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
				<div class="form-actions">
					<span class="pull"> <a
						href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REGISTER%>"
						class="flip-link btn btn-info btn-small">Đăng ký</a>
						<a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_FORGOT_PASSWORD%>"
						class="flip-link btn btn-primary btn-small">Quên mật khẩu</a>
					</span>				
					<span class="pull-right">
						<button type="submit" class="btn btn-success" name="action"
							value="<%=Constants.ACTION_LOGIN%>">Đăng nhập</button>
					</span>
				</div>
			</form>
		</div>
	</div>
	<script src="Admin/Content/js/jquery.min.js"></script>
	<script src="Admin/Content/js/jquery.validate.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginform").validate({
				rules : {
					txtUser : {
						required : true
					},
					txtPassword : {
						required : true
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
	</script>
</body>
</html>