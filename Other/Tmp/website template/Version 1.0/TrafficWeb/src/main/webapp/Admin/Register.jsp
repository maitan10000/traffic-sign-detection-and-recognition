<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    

<!-- Mirrored from themedesigner.in/demo/maruti-admin/login.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 24 Mar 2014 10:09:12 GMT -->
<head>
        <title>Maruti Admin</title><meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="Content/css/bootstrap.min.css" />
		<link rel="stylesheet" href="Content/css/bootstrap-responsive.min.css" />
        <link rel="stylesheet" href="Content/css/maruti-login.css" />
    </head>
    <body>
        <div id="loginbox">            
            <form id="loginform" class="form-vertical" action="<%=Constants.CONTROLLER_ADMIN%>" method="post">
				 <div class="control-group normal_text"> <h3><img src="img/logo.png" alt="Logo" /></h3></div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on"><i class="icon-user"></i></span><input type="text" placeholder="Tên đăng nhập" name="txtUser"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on"><i class="icon-lock"></i></span><input type="password" placeholder="Mật khẩu" name="txtPassword"/>
                        </div>
                    </div>
                </div>
                   <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on"><i class="icon-envelope"></i></span><input type="text" placeholder="Email" name="txtEmail"/>
                        </div>
                    </div>
                </div>
                   <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on"><i class="icon-user"></i></span><input type="text" placeholder="Tên" name="txtName"/>
                        </div>
                    </div>
                </div>
                <div class="form-actions">                    
                    <span class="pull-right"><button class="btn btn-success"  name="action" value="<%=Constants.ACTION_REGISTER%>">Đăng ký</button></span>
                </div>
            </form>
        
        </div>
        
        <script src="Content/js/jquery.min.js"></script>  
        <script src="Content/js/maruti.login.js"></script> 
    </body>


<!-- Mirrored from themedesigner.in/demo/maruti-admin/login.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 24 Mar 2014 10:09:12 GMT -->
</html>