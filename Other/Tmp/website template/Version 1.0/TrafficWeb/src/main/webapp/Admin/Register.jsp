<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<script>
function validateForm()
{
var x=document.forms["register-form"]["txtUser"].value;
if (x==null || x=="")
  {
  alert("Tên tài khoản không được để trống");
  return false;
  }
  
var x=document.forms["register-form"]["txtEmail"].value;
var atpos=x.indexOf("@");
var dotpos=x.lastIndexOf(".");
if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length)
  {
  alert("Email không đúng định dạng");
  return false;
  }
  
var x=document.forms["register-form"]["txtPassword"].value;
if (x==null || x=="")
  {
  alert("Xin vui lòng nhập mật khẩu !");
  return false;
  }
  
var x=document.forms["register-form"]["txtName"].value;
if (x==null || x=="")
  {
  alert("Xin vui lòng nhập tên!");
  return false;
  }
  
var x=document.forms["register-form"]["txtConfirmPassword"].value;
var y=document.forms["register-form"]["txtPassword"].value;
if (x != y || x=="")
  {
  alert("Vui lòng nhập lại mật khẩu!");
  return false;
  } 
}
</script>
<head>
<title></title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="Content/css/bootstrap.min.css" />
<link rel="stylesheet" href="Content/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="Content/css/maruti-login.css" />
</head>
<body>
	<div id="loginbox">
		<div class="control-group normal_text">
			<h3>
				<img src="img/logo.png" alt="Đăng kí" />
			</h3>
		</div>
		<form id="register-form" onsubmit="return validateForm()"
			class="form-vertical" method="post" action="<%=Constants.CONTROLLER_ADMIN%>">
			<div class="control-group">
				<div class="row-fluid">
					<label class="span4">Tên đăng nhập:</label>
					<div class="span8" align="left">
						<input type="text" name="txtUser" />
					</div>
				</div>
				<div class="row-fluid">
					<label class="span4">Mật khẩu:</label>
					<div class="span8" align="left">
						<input type="password" name="txtPassword" />
					</div>
				</div>
				<div class="row-fluid">
					<label class="span4">Nhập Lại Mật khẩu:</label>
					<div class="span8" align="left">
						<input type="password" name="txtConfirmPassword" />
					</div>
				</div>
				<div class="row-fluid">
					<label class="span4">Địa chỉ email:</label>
					<div class="span8" align="left">
						<input type="text" name="txtEmail" />
					</div>
				</div>
				<div class="row-fluid">
					<label class="span4">Tên đầy đủ: </label>
					<div class="span8" align="left">
						<input type="text" name="txtName" />
					</div>
				</div>

			</div>
			<div class="form-actions">
				<span class="pull-right"><button class="btn btn-success"
					name="action" value="<%=Constants.ACTION_REGISTER%>">Đăng ký</button></span>
			</div>
		</form>
	</div>

	<script src="Content/js/jquery.min.js"></script>
	<script src="Content/js/maruti.login.js"></script>
</body>
<!-- Mirrored from themedesigner.in/demo/maruti-admin/login.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 24 Mar 2014 10:09:12 GMT -->
</html>