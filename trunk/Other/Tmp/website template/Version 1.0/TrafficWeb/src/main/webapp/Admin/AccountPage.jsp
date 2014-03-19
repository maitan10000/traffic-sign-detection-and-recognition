<%@page import="model.Account"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/tables.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:05 GMT -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Dynamic Tables - Admin 365</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="Admin/Content/css/bootstrap.min.css" rel="stylesheet">
<link href="Admin/Content/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="Admin/Content/css/jasny-bootstrap.min.css" rel="stylesheet">
<link href="Admin/Content/css/jasny-bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="Admin/Content/css/font-awesome.css" rel="stylesheet">

<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<!-- <link href='http://fonts.googleapis.com/css?family=Pontano+Sans' rel='stylesheet' type='text/css'> -->
<link href="Admin/Content/css/admin.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="Content/img/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="Admin/Content/img/ico/apple-touch-icon-144-precomposed.png">
<link rel="Admin/apple-touch-icon-precomposed" sizes="114x114"
	href="Admin/Content/img/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="Admin/Content/img/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="Admin/Content/img/ico/apple-touch-icon-57-precomposed.png">
</head>
<%
	String jsonObject = (String) request.getAttribute("account");
	ArrayList<Account> listAccount = (ArrayList<Account>) request.getAttribute("listAccount");
%>

<body>

	<div id="top-strip">
		<div class="container">
			<div class="row">
				<div class="offset8 span4">
					<div class="pull-right">
						<a href="#">User</a> | <a href="#">Sign Out</a> | <a href="#">Help</a>
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
						<img src="Admin/Content/img/admin365_logo.png" height="40" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="nav-strip">

		<div class="container">
			<div class="row">
				<div class="span12">

					<div class="navbar">
						<div class="navbar-inner">
							<div class="container">
								<div class="nav-collapse">
									<ul class="nav">
										<li><a href="Index.html">Dashboard</a></li>
										<li><a
											href="/TrafficWeb/AdminController?action=listAccount">Nguoi
												Dung</a></li>
										<li><a
											href="/TrafficWeb/AdminController?action=listReport">Phan
												hoi</a></li>
										<li><a href="reports.html">Reports</a></li>
										<li><a href="forms.html">Forms</a></li>
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown">More <b class="caret"></b></a>
											<ul class="dropdown-menu">
												<li><a href="calendar.html">Calendar</a></li>
												<li><a href="signin.html">Sign In</a></li>
												<li><a href="register.html">Registration</a></li>
												<li><a href="error.html">Error</a></li>
												<li class="divider"></li>
												<li class="nav-header">Other</li>
												<li><a href="#">Grid</a></li>
												<li><a href="#">Interface</a></li>
												<li><a href="sidebar.html">Sidebar</a></li>
												<li><a href="faq.html">FAQ</a></li>
												<li><a href="kb.html">Knowledge Base</a></li>
											</ul></li>
									</ul>
									<ul class="nav pull-right">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown">Settings <b class="caret"></b></a>
											<ul class="dropdown-menu">
												<li><a href="#">My Profile</a></li>
												<li><a href="#">Preferences</a></li>
											</ul></li>
									</ul>
								</div>
								<!-- /.nav-collapse -->
							</div>
						</div>
						<!-- /navbar-inner -->
					</div>
					<!-- /navbar -->

				</div>
			</div>
		</div>

	</div>
	<div id="content">
		<div class="container">
			<div class="row">
				<div class="span12">

					<div class="panel">
						<div class="panel-header">
							<i class="icon-sign-blank"></i> Quan ly phan hoi
						</div>
						<form>
							<%
								if( listAccount != null){
							%>
							<div class="panel-content">
								<table cellpadding="0" cellspacing="0" border="0"
									class="table table-bordered" id="excelDataTable">
									<thead>
										<tr>
											<th>User Account</th>
											<th>Email</th>
											<th>Name</th>
											<th>Role</th>
											<th>CreateDate</th>
											<th>IsActive</th>
										</tr>
									</thead>
									<tbody>
										<%
											if( listAccount.size()> 0){for(int i = 0; i< listAccount.size();i++){
										%>
										<tr>
											<td><a href="#myModal" data-toggle="modal"
												onclick="showDetails(<%=listAccount.get(i).getUserID()%>)"><%=listAccount.get(i).getUserID()%></a></td>
											<td><%=listAccount.get(i).getEmail()%></td>
											<td><%=listAccount.get(i).getName()%></td>
											<td><%=listAccount.get(i).getRole()%></td>
											<td><%=listAccount.get(i).getCreateDate()%></td>
											<td><%=listAccount.get(i).getIsActive()%></td>

										</tr>
										<%
											} 
																																																																																																																													}
										%>
									</tbody>
								</table>

							</div>
							<%
								}
							%>
						</form>
					</div>

				</div>
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
						<p></p>
					</div>
					<div class="pull-left">
						<p>&copy; 2012 Admin 365 RELEASE</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="Admin/Content/js/jquery-1.7.2.min.js"></script>
	<script src="Admin/Content/js/bootstrap.js"></script>
	<script src="Admin/Content/js/jquery.dataTables.min.js"></script>
	<script src="Admin/Content/js/dataTables.bootstrap.js"></script>
	<script type="text/javascript">
		var myList = ${account};
		// Builds the HTML Table out of myList.
		function buildAccountTable() {
			var columns = addAllColumnHeaders(myList);

			for (var i = 0; i < myList.length; i++) {
				var row$ = $('<tr/>');
				for (var colIndex = 0; colIndex < columns.length; colIndex++) {
					var cellValue = myList[i][columns[colIndex]];

					if (cellValue == null) {
						cellValue = "";
					}

					row$.append($('<td/>').html(cellValue));
				}
				$("#excelDataTable").append(row$);
			}
		}

		// Adds a header row to the table and returns the set of columns.
		// Need to do union of keys from all records as some records may not contain
		// all records
		function addAllColumnHeaders(myList) {
			var columnSet = [];
			var headerTr$ = $('<tr/>');

			for (var i = 0; i < myList.length; i++) {
				var rowHash = myList[i];
				for ( var key in rowHash) {
					if ($.inArray(key, columnSet) == -1) {
						columnSet.push(key);
						headerTr$.append($('<th/>').html(key));
					}
				}
			}

			return columnSet;
		};
	</script>
	<script type="text/javascript">
	$("#excelDataTable tr:odd").addClass("master");
	$("#excelDataTable tr:not(.master)").hide();
	$("#excelDataTable tr:first-child").show();
	$("#excelDataTable tr.master").click(function(){
	    $(this).next("tr").toggle();
	    $(this).find(".arrow").toggleClass("up");
	}); 	
	</script>

</body>

<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/tables.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:07 GMT -->
</html>