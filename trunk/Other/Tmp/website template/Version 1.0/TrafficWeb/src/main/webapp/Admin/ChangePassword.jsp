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

#changePassForm {
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
			<form id="changePassForm" class="form-vertical"
				action="<%=Constants.CONTROLLER_ADMIN%>" method="post">
				<div class="control-group normal_text">
					<h3>Thay đổi mật khẩu</h3>
				</div>
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Mật khẩu mới:</label>
							<div class="span8" align="left">
								<input type="text" name="txtPassword" id="txtPassword" />
							</div>
						</div>
					</div>
				</div>		
				<div class="control-group">
					<div class="row-fluid">
						<div class="main_input_box">
							<label class="span4">Nhập lại mật khẩu:</label>
							<div class="span8" align="left">
								<input type="text" name="txtRePassword" id="txtRePassword" />
							</div>
						</div>
					</div>
				</div>			
				<div class="form-actions">					
					<span class="pull-right">

						<button type="submit" class="btn btn-success" name="action"
							onclick="changePass(); return false;">Đổi mật khẩu</button>
					</span>
				</div>
			</form>
		</div>
		<div id="messageModal" class="modal hide">
             <div class="modal-header">
               <button data-dismiss="modal" class="close" type="button">×</button>
               <h3 id="message-header">Thông báo</h3>
             </div>
             <div class="modal-body">
               <p id="message">Mật khẩu đã được thay đổi.</p>
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
		var userDefine= '<%=(String)request.getAttribute("userDefine") %>';
		function goBack()
		  {
			  window.history.back();
		  }
		
			$(document).ready(function() {
				$("#changePassForm").validate({
					rules : {
						txtPassword : {
							required : true,
							minlength : 6,
							maxlength : 30
						},
						txtRePassword:{
							required : true,
							equalTo : "#txtPassword"
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

			function changePass() {
				var result = $("#changePassForm").valid();
				if (result == true) {					
					var tmpForm = document.getElementById("changePassForm");
					var url = "<%=GlobalValue.getServiceAddress()%>"
							+"<%=Constants.MANAGE_CHANGE_PASSWORD%>";
					$.ajax({
						url : url,
						type : "POST",
						data : {
							userDefine : userDefine,
							newPassword: tmpForm.txtPassword.value
						},
						success : function(result) {
							console.log(result);
							if ('Success' == result.trim()) {
									$('#messageModal').modal('show');
							} else if ("User and email not exist" == result.trim()) {
								$('#txtUser').focus();
								$.gritter.add({
									title : 'Thông báo',
									text : 'Tên đăng nhập không tồn tại',
									sticky : false
								});
							} else
							{
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
			    window.location.href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGIN%>";
			});
		</script>
</body>
</html>