<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>



<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/register.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:15 GMT -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8">
<title>Sign In - Admin 365</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="Content/css/bootstrap.min.css" rel="stylesheet">
<link href="Content/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="Content/css/jasny-bootstrap.min.css" rel="stylesheet">
<link href="Content/css/jasny-bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="Content/css/font-awesome.css" rel="stylesheet">

<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<!-- <link href='http://fonts.googleapis.com/css?family=Pontano+Sans' rel='stylesheet' type='text/css'> -->
<link href="Content/css/admin.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="Content/img/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="Content/img/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="Content/img/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="Content/img/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="Content/img/ico/apple-touch-icon-57-precomposed.png">
</head>

<body>

	<div id="top-strip">
		<div class="container">
			<div class="row">
				<div class="offset8 span4">
					<div class="pull-right">
						<a href="signin.html">Sign In</a> | <a href="index-2.html">Home</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="logo-strip">
		<div class="container">
			<div class="row">
				<div class="span12">
					<div class="logo">
						<img src="img/admin365_logo.png" height="40" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="nav-strip">

		<div class="container">
			<div class="row">
				<div class="span12"></div>
			</div>
		</div>

	</div>

	<div class="container-register">
		<div class="panel">
			<div class="panel-header">
				<i class="icon-user icon-large"></i> Registration
			</div>
			<div class="panel-content">
				<form action="/TrafficWeb/AdminController" method="post">
					<div class="control-group">
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-envelope icon-large"></i></span><input
									class="span3" placeholder="userID" name="userID" size="16"
									type="text">
							</div>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-key icon-large"></i></span><input
									class="span3" placeholder="Password" name="password"
									size="16" type="password">
							</div>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on">&nbsp;</span><input class="span3"
									placeholder="Email" size="16" type="text" name="email">
							</div>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on">&nbsp;</span><input class="span3"
									placeholder="Name" size="16" type="text" name="name">
							</div>
						</div>
					</div>		
					<div class="control-group">
						<div class="controls">
							<button class="btn btn-large" name="action" value="register">Register</button>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<span class="text-small">Already registered?</span>&nbsp;&nbsp;&nbsp;<a
								href="signin.html">Sign In</a>
						</div>
					</div>
				</form>				
			</div>
		</div>
	</div>

	<div id="footer">
		<div class="container">
			<div class="row">
				<div class="span12">
					<div class="divider"></div>
					<div class="pull-right">
						<p>Admin 365</p>
					</div>
					<div class="pull-left">
						<p>&copy; 2012 Admin 365 RELEASE</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="Content/js/jquery-1.7.2.min.js"></script>
	<script src="Content/js/bootstrap.js"></script>

</body>

<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/register.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:15 GMT -->
</html>