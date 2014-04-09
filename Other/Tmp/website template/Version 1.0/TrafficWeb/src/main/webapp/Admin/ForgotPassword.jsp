<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
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

<style>
#content {
	margin: 0px;
	min-height: 96%;
}

#loginbox {
	
}

#forgotPassform {
	margin-top: 100px;
}

#loginbox {
	width: 600px !important;
}

.row-fluid label {
	text-align: right !important;
}
</style>
</head>
<body>
	<div id="content">
		<div id="loginbox">
			<form id="forgotPassform" class="form-vertical"
				action="<%=Constants.CONTROLLER_ADMIN%>" method="post">
				<div class="control-group normal_text">
					<h3>Quên mật khẩu</h3>
				</div>
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Tên đăng nhập/Email:</label>
							<div class="span8" align="left">
								<input type="text" name="txtUser" id="txtUser" />
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
							onclick="forgotPass(); return false;">Yêu cầu đổi mật khẩu</button>
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
               <p id="message">Vui lòng kiểm tra email để thay đổi mật khẩu.</p>
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
				$("#forgotPassform").validate({
					rules : {
						txtUser : {
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

			function forgotPass() {
				var result = $("#forgotPassform").valid();
				if (result == true) {			
					$('#messageModal .modal-body').html('<center><img src="Admin/Content/images/loading2.gif"/></center>');
					$('#messageModal').modal('show');
					var tmpForm = document.getElementById("forgotPassform");
					var url = "<%=GlobalValue.getServiceAddress()%>"
							+"<%=Constants.MANAGE_FORGOT_PASSWORD%>";
					$.ajax({
						url : url,
						type : "GET",
						data : {
							userDefine : tmpForm.txtUser.value							
						},
						error : function(result) {
							result = result.responseText;
							if ('Success' == result.trim()) {
									$('#messageModal .modal-body').html('<p id="message">Vui lòng kiểm tra email để thay đổi mật khẩu.</p>');
							} else if ("User and email not exist" == result.trim()) {
								$('#messageModal').modal('hide');
								$('#txtUser').focus();
								$.gritter.add({
									title : 'Thông báo',
									text : 'Tên đăng nhập/Email không tồn tại',
									sticky : false
								});
							} else
							{
								$('#messageModal').modal('hide');
								$.gritter.add({
									title : 'Thông báo',
									text : 'Không thành công vui lòng thử lại sau',
									sticky : false
								});
							}
						}
					});
				}
			}
			$('#messageModal').on('hidden', function () {
				if($('#messageModal .modal-body').html().indexOf("Vui lòng kiểm tra email") >-1 ){
			    	window.location.href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGIN%>";
				}
			});
		</script>
</body>
</html>