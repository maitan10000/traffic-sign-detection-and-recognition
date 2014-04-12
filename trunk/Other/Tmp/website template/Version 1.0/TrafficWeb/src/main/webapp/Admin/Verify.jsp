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
			var key = "<%=request.getAttribute("key")%>";		
			$(document).ready(function() {
				verify();
			});

			function verify() {			
					$('#messageModal .modal-body').html('<center><img src="Admin/Content/images/loading2.gif"/></center>');
					$('#messageModal').modal('show');
					var url = "<%=GlobalValue.getServiceAddress()%>"
							+"<%=Constants.MANAGE_ACCOUNT_VERIFY%>";
					$.ajax({
						url : url,
						type : "GET",
						data : {
							key : key			
						},
						error : function(result) {
							result = result.responseText;
							if ('Success' == result.trim()) {
									$('#messageModal .modal-body').html('<p id="message">Tài khoản đã được kích hoạt.</p>');
							} else
							{
								$('#messageModal .modal-body').html('<p id="message">Kích hoạt không thành công.</p>');								
							}
						}
					});				
			}
			$('#messageModal').on('hidden', function () {				
			    window.location.href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGIN%>";
			});
		</script>
</body>
</html>