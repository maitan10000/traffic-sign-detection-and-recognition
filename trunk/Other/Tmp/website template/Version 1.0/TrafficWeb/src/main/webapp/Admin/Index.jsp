<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


<!-- Mirrored from themedesigner.in/demo/maruti-admin/index2.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 24 Mar 2014 10:09:23 GMT -->
<head>
<title>HỆ THỐNG NHẬN DIỆN BIỂN BÁO - TRANG QUẢN LÝ</title><meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="Admin/Content/css/bootstrap.min.css" />
<link rel="stylesheet" href="Admin/Content/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="Admin/Content/css/fullcalendar.css" />
<link rel="stylesheet" href="Admin/Content/css/maruti-style.css" />
<link rel="stylesheet" href="Admin/Content/css/maruti-media.css" class="skin-color" />
</head>
<body>

<!--Header-part-->
<div id="header">
  <h1><a href="dashboard.html">HỆ THỐNG NHẬN DIỆN BIỂN BÁO - TRANG QUẢN LÝ</a></h1>
</div>
<!--close-Header-part--> 

<!--top-Header-messaages-->
<div class="btn-group rightzero"> <a class="top_message tip-left" title="Manage Files"><i class="icon-file"></i></a> <a class="top_message tip-bottom" title="Manage Users"><i class="icon-user"></i></a> <a class="top_message tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a> <a class="top_message tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a> </div>
<!--close-top-Header-messaages--> 

<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li class="" ><a title="" href="#"><i class="icon icon-user"></i> <span class="text">Profile</span></a></li>
    <li class=" dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">Messages</span> <span class="label label-important">5</span> <b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a class="sAdd" title="" href="#">new message</a></li>
        <li><a class="sInbox" title="" href="#">inbox</a></li>
        <li><a class="sOutbox" title="" href="#">outbox</a></li>
        <li><a class="sTrash" title="" href="#">trash</a></li>
      </ul>
    </li>
    <li class=""><a title="" href="#"><i class="icon icon-cog"></i> <span class="text">Settings</span></a></li>
    <li class=""><a title="" href="login.html"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
  </ul>
</div>

<!--close-top-Header-menu-->

<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard2</a>
  <ul>
    <li class="active"><a href="index-2.html"><i class="icon icon-home"></i> <span>Trang chủ</span></a> </li>
    <li><a href="tables.html"><i class="icon icon-th"></i> <span>Quản lý biển báo</span></a></li>
    <li> <a href="charts.html"><i class="icon icon-user"></i> <span>Quản lý người dùng</span></a> </li>
    <li> <a href="widgets.html"><i class="icon icon-exclamation-sign"></i> <span>Quản lý phản hồi</span></a> </li>
    <li><a href="grid.html"><i class="icon icon-signal"></i> <span>Thống kê</span></a></li>
     <li><a href="grid.html"><i class="icon icon-cog"></i> <span>Thiếp lập hệ thống</span></a></li>
    
  </ul>
</div>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="index-2.html" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
  </div>
  <div class="container-fluid">
  
  
  
  
  
  </div>
</div>
<div class="row-fluid">
      <div id="footer" class="span12"> 2012 &copy; Marutii Admin. Brought to you by <a href="http://themedesigner.in/">Themedesigner.in</a> </div>
    </div>
<script src="Admin/Content/js/excanvas.min.js"></script> 
<script src="Admin/Content/js/jquery.min.js"></script> 
<script src="Admin/Content/js/jquery.ui.custom.js"></script> 
<script src="Admin/Content/js/bootstrap.min.js"></script> 
<script src="Admin/Content/js/jquery.flot.min.js"></script> 
<script src="Admin/Content/js/jquery.flot.resize.min.js"></script> 
<script src="Admin/Content/js/jquery.peity.min.js"></script> 
<script src="Admin/Content/js/fullcalendar.min.js"></script> 
<script src="Admin/Content/js/maruti.js"></script> 
<script src="Admin/Content/js/maruti.dashboard.js"></script> 
<script src="Admin/Content/js/maruti.calendar.js"></script> 
<script type="text/javascript">
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
  function goPage (newURL) {

      // if url is empty, skip the menu dividers and reset the menu selection to default
      if (newURL != "") {
      
          // if url is "-", it is this page -- reset the menu:
          if (newURL == "-" ) {
              resetMenu();            
          } 
          // else, send page to designated URL            
          else {  
            document.location.href = newURL;
          }
      }
  }

// resets the menu selection upon entry to this page:
function resetMenu() {
   document.gomenu.selector.selectedIndex = 2;
}
</script>
</body>


<!-- Mirrored from themedesigner.in/demo/maruti-admin/index2.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 24 Mar 2014 10:09:23 GMT -->
</html>
