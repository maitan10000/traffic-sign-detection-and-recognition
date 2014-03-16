<!DOCTYPE html>
<html>
<head>
<title>Detail Admin - User list</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- bootstrap -->
<link href="Content/css/bootstrap/bootstrap.css" rel="stylesheet" />
<link href="Content/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
<link href="Content/bootstrap/bootstrap-overrides.css" type="text/css"
	rel="stylesheet" />

<!-- global styles -->
<link rel="stylesheet" type="text/css" href="Content/css/layout.css" />
<link rel="stylesheet" type="text/css" href="Content/css/elements.css" />
<link rel="stylesheet" type="text/css" href="Content/css/icons.css" />

<!-- libraries -->
<link href="Content/css/lib/font-awesome.css" type="text/css"
	rel="stylesheet" />

<!-- this page specific styles -->
<link rel="stylesheet" href="Content/Fcss/compiled/user-list.css"
	type="text/css" media="screen" />

<!-- open sans font -->
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css' />

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<!-- navbar -->
	<div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<button type="button" class="btn btn-navbar visible-phone"
				id="menu-toggler">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<a class="brand" href="index.html"><img
				src="Content/img/logo.png" /></a>

			<ul class="nav pull-right">
				<li class="hidden-phone"><input class="search" type="text" />
				</li>
				<li class="dropdown"><a href="#"
					class="dropdown-toggle hidden-phone" data-toggle="dropdown">
						Your account <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="personal-info.html">Personal info</a></li>
						<li><a href="#">Account settings</a></li>
						<li><a href="#">Billing</a></li>
						<li><a href="#">Export your data</a></li>
						<li><a href="#">Send feedback</a></li>
					</ul></li>
				<li class="settings hidden-phone"><a href="personal-info.html"
					role="button"> <i class="icon-cog"></i>
				</a></li>
				<li class="settings hidden-phone"><a href="signin.html"
					role="button"> <i class="icon-share-alt"></i>
				</a></li>
			</ul>
		</div>
	</div>
	<!-- end navbar -->

	<!-- sidebar -->
	<div id="sidebar-nav">
		<ul id="dashboard-menu">
			<li class="active">
				<div class="pointer">
					<div class="arrow"></div>
					<div class="arrow_border"></div>
				</div> <a href="index.html"> <i class="icon-home"></i> <span>Home</span>
			</a>
			</li>
			<li><a href="gallery.html"> <i class="icon-picture"></i> <span>Train
						Image</span>
			</a></li>

			<li><a class="dropdown-toggle" href="#"> <i
					class="icon-group"></i> <span>Users</span> <i
					class="icon-chevron-down"></i>
			</a>
				<ul class="submenu">
					<li><a href="user-list.html">User list</a></li>
					<li><a href="new-user.html">New user form</a></li>
					<li><a href="user-profile.html">User profile</a></li>
				</ul></li>
			<li><a class="dropdown-toggle" href="#"> <i
					class="icon-edit"></i> <span>Report</span> <i
					class="icon-chevron-down"></i>
			</a>
				<ul class="submenu">
					<li><a href="Report.jsp">Report list</a></li>
				</ul></li>
			<li><a href="chart-showcase.html"> <i class="icon-signal"></i>
					<span>Charts</span>
			</a></li>

			<li><a href="tables.html"> <i class="icon-th-large"></i> <span>Tables</span>
					<li><a href="personal-info.html"> <i class="icon-cog"></i>
							<span>My Info</span>
					</a></li>
		</ul>
	</div>
	<!-- end sidebar -->

	<!-- main container -->
	<div class="content">

		<!-- settings changer -->
		<div class="skins-nav">
			<a href="#" class="skin first_nav selected"> <span class="icon"></span><span
				class="text">Default</span>
			</a> <a href="#" class="skin second_nav" data-file="css/skins/dark.css">
				<span class="icon"></span><span class="text">Dark skin</span>
			</a>
		</div>

		<div class="container-fluid">
			<div id="pad-wrapper" class="users-list">
				<div class="row-fluid header">
					<h3>Report</h3>
					<div class="span10 pull-right">
						<input type="text" class="span5 search"
							placeholder="Type of report..." />

						<!-- custom popup filter -->
						<!-- styles are located in css/elements.css -->
						<!-- script that enables this dropdown is located in js/theme.js -->
						<div class="ui-dropdown">
							<div class="head" data-toggle="tooltip" title="Click me!">
								Filter Report <i class="arrow-down"></i>
							</div>
							<div class="dialog">
								<div class="pointer">
									<div class="arrow"></div>
									<div class="arrow_border"></div>
								</div>
								<div class="body">
									<p class="title">Show users where:</p>
									<div class="form">
										<select>
											<option />Name
											<option />Email
											<option />Number of orders
											<option />Signed up
											<option />Last seen
										</select> <select>
											<option />is equal to
											<option />is not equal to
											<option />is greater than
											<option />starts with
											<option />contains
										</select> <input type="text" /> <a class="btn-flat small">Add
											filter</a>
									</div>
								</div>
							</div>
						</div>


					</div>
				</div>

				<!-- Users table -->
				<div class="row-fluid table">
					<table class="table table-hover">
						<thead>
							<tr>
								<th class="span4 sortable">ReportID</th>
								<th class="span3 sortable"><span class="line"></span>Content</th>
								<th class="span2 sortable"><span class="line"></span>Creator
									spent</th>
								<th class="span3 sortable align-right"><span class="line"></span>CreateDate
								</th>
							</tr>
						</thead>

					</table>
				</div>
				<div class="pagination pull-right">
					<ul>
						<li><a href="#">&#8249;</a></li>
						<li><a class="active" href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">&#8250;</a></li>
					</ul>
				</div>
				<!-- end users table -->
			</div>
		</div>
	</div>
	<!-- end main container -->


	<!-- scripts -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="Content/js/bootstrap.min.js"></script>
	<script src="Content/js/theme.js"></script>
</body>
</html>