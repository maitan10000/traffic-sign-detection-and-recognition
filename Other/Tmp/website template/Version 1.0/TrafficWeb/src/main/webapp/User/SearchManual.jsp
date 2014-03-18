<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="Content/Css/Main.css" rel="stylesheet" type="text/css" />
<link href="Content/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="Content/Scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="Content/bootstrap/js/bootstrap.js"></script>
<title>Traffic Sign Recognition</title>
</head>
<body>
<div class="wrapper">
<div class="page">
  <div class="header-container">
    <header>
      <div class="clearfix">
        <div class="card-top"> </div>
      </div>
      <div class="logo-Container">
        <h1 class="logo"> <a href="#" > <img src="Content/Image/logo.png" /> </a> </h1>
        <!--   _____________ -->
        <ul class="links">
          <li><a href="customer/account/login/index.html" title="Log In" >Đăng Nhập</a></li>
          <li class="separator">|</li>
          <li><a href="customer/account/create/index.html">Đăng Ký</a></li>
          <li class="separator">|</li>
          <li><a href="customer/account/create/index.html">Liên Hệ</a></li>
        </ul>
      </div>
    </header>
    <div class="menu-container">
      <nav class="olegnax">
        <ul id="nav">
          <li class="level0 nav-3 level-top"> <a href="#" class="level-top"> <span>Tra Cứu Biển Báo</span> </a> </li>
          <li class="level0 nav-4 level-top"> <a href="#" class="level-top"> <span>Nhận Diện Tự Động</span> </a> </li>
          <li class="level0 nav-4 level-top"> <a href="#" class="level-top"> <span>Danh Sách Đã Lưu</span> </a> </li>
          <li class="level0 nav-5 level-top last"> <a href="#" class="level-top"> <span>Lịch Sử</span> </a> </li>
        </ul>
      </nav>
      <div style="clear:both"></div>
      <form id="search_mini_form" action="http://celebrity.olegnax.com/catalogsearch/result/" method="get">
        <div class="form-search">
          <input id="search" type="text" name="q" value="" class="input-text" />
          <button type="submit" title="Search" ></button>
        </div>
        <div id="search_autocomplete" class="search-autocomplete"></div>
      </form>
    </div>
  </div>
  <div class="main-container">
    <div class="main-content content-cat notHomepage">
      <div class="content-title"> TRA CỨU BIỂN BÁO </div>
      <div class="options">
        <div class="searchName" style="margin-right:30px;">Tên biển báo:
          <input type="text"/>
        </div>
        <div class="content-Selectbox font-StyleTitle needMargin"> Loại Biển Báo:
          <select class="sortBy font-Style">
            <option class="font-Style"> Tất Cả</option>
            <option class="font-Style">Biển báo nguy hiểm</option>
            <option class="font-Style">Biển báo cấm</option>
            <option class="font-Style">Biển báo hướng dẫn</option>
          </select>
        </div>
        <div class="content-Selectbox font-StyleTitle needMargin"> Sắp xếp theo:
          <select class="sortBy font-Style">
            <option class="font-Style"> Tên biển báo</option>
            <option class="font-Style">Số hiệu</option>
          </select>
        </div>
        <div class="searchName" style="padding-bottom:5px">
          <button type="button" class="btn btn-default btn-sm" >Tìm kiếm</button>
        </div>
        <div style="clear:both"></div>
      </div>
      <div class="contentTable " style="margin-top:20px">
        <table class="table table-striped .table-condensed">
          <thead>
          <th> Hình Ảnh </th>
            <th> Số Hiệu </th>
            <th> Tên Biển Báo </th>
            <th> Danh Mục </th>
            </thead>
          <tbody>
            <tr>
              <td><img class="trafficImage" src="Content/Image/Traffic/Cam di nguoc chieu.png"  alt="Responsive image"/></td>
              <td>ABC 123</td>
              <td>Cấm đi ngược chiều</td>
              <td>Biển báo cấm</td>
            </tr>
            <tr>
              <td><img class="trafficImage" src="Content/Image/Traffic/cam re.jpg" alt="Responsive image"/></td>
              <td>ABC 123</td>
              <td>Cấm Rẽ</td>
              <td>Biển báo cấm</td>
            </tr>
            <tr>
              <td><img class="trafficImage" src="Content/Image/Traffic/bien nguy hiem tre em.jpg"  alt="Responsive image"/></td>
              <td>ABC 123</td>
              <td>Nguy Hiểm Có người Băng Ngang</td>
              <td>Biển Nguy Hiểm</td>
            </tr>
            <tr>
              <td><img class="trafficImage" src="Content/Image/Traffic/Cam di nguoc chieu.png" alt="Responsive image"/></td>
              <td>ABC 123</td>
              <td>Cấm đi ngược chiều</td>
              <td>Biển báo cấm</td>
            </tr>
            <tr>
              <td><img class="trafficImage" src="Content/Image/Traffic/cam re.jpg"  alt="Responsive image"/></td>
              <td>ABC 123</td>
              <td>Cấm Rẽ</td>
              <td>Biển báo cấm</td>
            </tr>
            <tr>
              <td><img class="trafficImage" src="Content/Image/Traffic/bien nguy hiem tre em.jpg"  alt="Responsive image"/></td>
              <td>ABC 123</td>
              <td><a href="#" data-toggle="modal" data-target="#myModal">Nguy Hiểm Có người Băng Ngang</a></td>
              <td>Biển Nguy Hiểm</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div style="clear:both"></div>
      <!-- Modal -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="myModalLabel">Thông Tin Biển Báo</h4>
            </div>
            <div class="modal-body">
              <div class="trafficDetail">
                <div class="contentImgDetails"><img class="imageDetails" src="Content/Image/Traffic/bien nguy hiem tre em.jpg" alt="Responsive image"/> </div>
                <br/>
                <strong>Số hiệu biển báo:</strong>
                 <font> NH001</font>
                 <br/>
                  <br/>
                <strong>Tên Biển Báo:</strong>
                 <font> Biển báo nguy hiểm có người băng ngang</font>
                 <br/>
                  <br/>
                <strong>Nội dung:</strong>
                 <font> Biển này được sử dụng độc lập ở những vị trí sang ngang, đường không có tổ chức điều khiển giao thông hoặc có thể sử dụng phối hợp với vạch kẻ đường. Gặp biển này người lái xe phải điều khiển xe chạy chậm, chú ý quan sát, ưu tiên cho người đi bộ sang ngang. </font>
                  <br/>
                  <br/>
                <strong>Mức phạt:</strong>
                 <font> Biển này được sử dụng độc lập ở những vị trí sang ngang, đường không có tổ chức điều khiển giao thông hoặc có thể sử dụng phối hợp với vạch kẻ đường. Gặp biển này người lái xe phải điều khiển xe chạy chậm, chú ý quan sát, ưu tiên cho người đi bộ sang ngang. </font>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Lưu biển báo</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer-container">
      <div class="footer">
        <div class="footer-brands">
          <div class="brands"> <a href="#"><img src="Content/Image/brands/brand1.gif" alt=""></a> <a href="#"><img src="Content/Image/brands/brand2.gif" alt=""></a> <a href="#"><img src="Image/brands/brand3.gif" alt=""></a> <a href="#"><img src="Content/Image/brands/brand4.gif" alt=""></a> <a href="#"><img src="Content/Image/brands/brand5.gif" alt=""></a> <a href="#"><img src="Content/Image/brands/brand6.gif" alt=""></a> <a href="#"><img src="Content/Image/brands/brand7.gif" alt=""></a> <a href="#"><img src="Content/Image/brands/brand8.gif" alt=""></a> <a href="#"><img src="Content/Image/brands/brand9.gif" alt=""></a> </div>
        </div>
        <div class="footer-left">
          <p> <b>HỆ THỐNG NHẬN DIỆN BIỂN BÁO</b> </p>
          <p> "Chúng tôi khác biệt...!" - Sau 3 năm thành lập đến nay hệ thống cửa hàng Celebrity đã ngày càng phát triển và hoàn thiện hơn so với những năm về trước</p>
        </div>
        <div class="footer-left">
          <p><span style="border-bottom:dotted 1px #fafafa;">TRUNG TÂM CELEBRITY</span></p>
          <p style="padding-bottom:7px;">770F, Sư Vạn Hạnh (ND), P.12, Q.10, Tp. HCM <a class="location" href="#">&nbsp;</a></p>
        </div>
        <div class="footer-left">
          <ul class="social">
            <li> <a href="#"><span class="facebook icon"></span>Join us on Facebook</a> </li>
            <li> <a href="#"><span class="email icon"></span>Send an Email</a> </li>
            <li> <a href="#"><span class="rss icon"></span>Subscrible RSS Feed</a> </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>