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

<body onload="buildHtmlTable()">

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
                    <div class="panel-header"><i class="icon-tasks"></i> Forms</div>
                    <div class="panel-content">
                        <!-- File Upload (cosmetic) -->
                        <form class="form-horizontal">
                            <fieldset>
                                <legend>File Input <small>(cosmetic)</legend>
                                <div class="control-group">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="input-append">
                                            <div class="uneditable-input span3"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">Select file</span><span class="fileupload-exists">Change</span><input type="file" /></span><a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                        <!-- Validation -->
                        <form id="example_form" class="form-horizontal">
                            <fieldset>
                                <legend>Validation <small>(will not submit any information)</small></legend>
                                <div class="control-group">
                                    <label class="control-label" for="name">Your Name</label>
                                    <div class="controls">
                                        <input type="text" class="input-xlarge" name="name" id="name">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="email">Email Address</label>
                                    <div class="controls">
                                        <input type="text" class="input-xlarge" name="email" id="email">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="subject">Subject</label>
                                    <div class="controls">
                                        <input type="text" class="input-xlarge" name="subject" id="subject">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="message">Your Message</label>
                                    <div class="controls">
                                        <textarea class="input-xlarge" name="message" id="message" rows="3"></textarea>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <button type="submit" class="btn btn-success">Submit</button>
                                    <button type="reset" class="btn">Cancel</button>
                                </div>
                            </fieldset>
                        </form>
                        <!-- Datepicker -->
                        <form>
                            <legend>Datepicker</legend>
                            <blockquote><a href="http://www.eyecon.ro/bootstrap-datepicker/" target="_blank">Bootstrap Datepicker</a></blockquote>
                            <div class="well">
                                <input type="text" class="span2" value="02-16-2012" id="dp1">
                            </div>
                            <p>Attached to a field with the format specified via data tag</p>
                            <div class="well">
                                <input type="text" class="span2" value="02/16/12" data-date-format="mm/dd/yy" id="dp2" >
                            </div>
                            <p>As a component</p>
                            <div class="well">
                                <div class="input-append date" id="dp3" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
                                    <input class="span2" size="16" type="text" value="12-02-2012" readonly>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>
                            </div>
                        </form>
                        <!-- Wysiwyg -->
                        <form class="form-horizontal">
                            <legend>Wysiwyg</legend>
                            <blockquote><a href="http://redactorjs.com/" target="_blank">Redactor</a></blockquote>
                            <div class="control-group">
                                <label class="control-label">New Post</label>
                                <div class="controls">
                                    <textarea id="editor" name="content"></textarea>
                                </div>
                            </div>                            
                        </form>
                    </div>
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
                </div>
                <div class="pull-left">
                    <p>&copy; 2012 Admin 365 RELEASE</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/bootstrap.js"></script>
<!-- jasny File Upload -->
<script src="js/bootstrap-fileupload.js"></script>
<!-- Validation -->
<script src="js/jquery.validate.min.js"></script>
<!-- Datepicker -->
<script src="js/bootstrap-datepicker.js"></script>
<!-- Redactor -->
<script src="js/redactor.min.js"></script>

<script type="text/javascript">
$(function (){

    // file input
    $('input[id=the_file]').change(function() {
        $('#choose_file').val($(this).val());
    });

    // Redactor Wysiwyg
    $('#editor').redactor();

    // Validation
    $('#example_form').validate({
        rules: {
            name: {
            minlength: 2,
            required: true
        },
        email: {
            required: true,
            email: true
        },
        subject: {
            minlength: 2,
            required: true
        },
        message: {
            minlength: 2,
            required: true
        }
    },
    highlight: function(label) {
        $(label).closest('.control-group').addClass('error');
    },
    success: function(label) {
        label
        .html("<i class='icon-ok icon-large'></i>").addClass('valid')
        .closest('.control-group').addClass('success');
    }
    });

    // Datepicker
    $('#dp1').datepicker({
        format: 'mm-dd-yyyy'
    });
    $('#dp2').datepicker();
    $('#dp3').datepicker();

});
</script>

</body>

<!-- Mirrored from wbpreview.com/previews/WB0CTJ195/forms.html by HTTrack Website Copier/3.x [XR&CO'2013], Tue, 18 Mar 2014 03:37:12 GMT -->
</html>