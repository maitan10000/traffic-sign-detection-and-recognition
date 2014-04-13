<%@page import="java.text.SimpleDateFormat"%>
<%@page import="json.ResultShortJSON"%>
<%@page import="json.FavoriteJSON"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utility.Constants"%>
<%@page import="utility.GlobalValue"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="User/Content/Css/Main.css" rel="stylesheet" type="text/css" />

<link href="User/Content/bootstrap/css/bootstrap.css" rel="stylesheet"/>
<link href="User/Content/Css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<!-- <link href="User/Content/Css/paging.css" rel="stylesheet" -->
<!-- 	type="text/css" /> -->
<script type="text/javascript"
	src="User/Content/Scripts/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
	src="User/Content/Scripts/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="User/Content/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="User/Content/Scripts/paging.js"></script>

<title>Traffic Sign Recognition</title>
</head>


<%
	ArrayList<ResultShortJSON> listHistory = (ArrayList<ResultShortJSON>) request.getAttribute("listHistory");
%>
<body on>
	<div class="wrapper">
		<div class="page">
			<div class="header-container">
				<header>
				<div class="clearfix">
					<div class="card-top"></div>
				</div>
				<div class="logo-Container">
				<img id="logoImage"  alt="" src="User/Content/Image/eye_logo.png" style="height: 80px; width: 90px;">
					<h2  id="titleHeader" class="logo" style="color: #FFF;">HỆ THỐNG NHẬN DẠNG BIỂN BÁO</h2>
					<!--   _____________ -->
					<%
						String userID = (String)session.getAttribute(Constants.SESSION_USERID);
					if(userID != null && !userID.isEmpty())
					{
					%>
					<ul class="links">
						<li><a href="#"
							title=""><%=userID %></a></li>
						<li class="separator">|</li>
						<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGOUT%> ">Đăng xuất</a></li>
						<li class="separator">
					</ul>
					<%}else{ %>
					<ul class="links">
						<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_LOGIN%>"
							title="Đăng nhập">Đăng Nhập</a></li>
						<li class="separator">|</li>
						<li><a href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_REGISTER%>" title="Đăng ký">Đăng Ký</a></li>
						<li class="separator">
					</ul>
					<%}//end if session useID %>
				</div>
				</header>
				<div class="menu-container">
					<nav class="olegnax">
					<ul id="nav">
						<li class="level0 nav-4 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_SEARCH_AUTO%>
								"
							class="level-top"> <span>Nhận Dạng Tự Động</span>
						</a></li>
						<li class="level0 nav-3 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_SEARCH_MANUAL%>"
							class="level-top"> <span>Tra Cứu Biển Báo</span>
						</a></li>
						<%if(userID != null){ %>
						<li class="level0 nav-4 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_FAVORITE_VIEW%>"
							class="level-top"> <span>Danh Sách Đã Lưu</span>
						</a></li>
						<li class="level0 nav-5 level-top last"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_HISTORY_LIST%>"
							class="level-top"> <span>Lịch Sử</span>
						</a></li>
						<%} %>
					</ul>
					</nav>
					<div style="clear: both"></div>

				</div>
			</div>
			<div class="main-container">
				<div class="main-content content-cat notHomepage">
					<div class="content-title">LỊCH SỬ TÌM KIẾM</div>
					<%
						if( listHistory != null){
					%>
					<div class="contentTable " style="margin-top: 20px">
						<table id="resultTable" 
							class="table table-bordered dataTable" style="width: 700px; margin-left: 80px;">
							<thead>
								<th style="text-align: center;width: 70px;">STT</th>
								<th style="text-align: center;">Ngày tạo</th>
								<th></th>
							</thead>
							<tbody>
								<%								
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									if( listHistory.size()> 0){
															for(int i = listHistory.size() -1; i >= 0 ;i--){
								%>

								<tr id="<%=listHistory.get(i).getResultID()%>">
									<td style="text-align: center; width: 70px;"><%=listHistory.size() - i%></td>
									<td style="text-align: center; width: 200px;"><a href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_HISTORY_VIEW%>&resultID=<%=listHistory.get(i).getResultID()%>" data-toggle="modal"><%=dateFormat.format(listHistory.get(i).getCreateDate()) %></a></td>
									<td style=" width: 100px;"><button style="margin-left: 60px" class="btn btn-inverse"
											onclick="deleteHistory('<%=listHistory.get(i).getResultID()%>')">Xóa</button></td>
								</tr>
								<%
									} 
																																													}
								%>
							</tbody>
						</table>
					</div>
<!-- 					<div id="pageNavPosition" style="padding-top: 20px" align="center"></div> -->
					<%
						}
					%>
					<div style="clear: both"></div>
				</div>
			<div class="footer-container">
					<div class="footer">
						<div class="footer-brands">
							<div class="brands"></div>
						</div>
						<div class="footer-left">
							<p>
								<b>HỆ THỐNG NHẬN DẠNG BIỂN BÁO</b>
							</p>
							<p>"Hệ thống giúp đỡ người dùng tra cứu, học tập biển báo
								giao thông."</p>
						</div>
						<div class="footer-left">
							<p>
								<span style="border-bottom: dotted 1px #fafafa;">TRƯỜNG
									ĐẠI HOC FPT</span>
							</p>
							<p style="padding-bottom: 7px;">
								Công viên phần mềm Quang Trung - Quận 12 - TP Hồ Chí Minh <a
									class="location" href="#">&nbsp;</a>
							</p>
						</div>
						<div class="footer-left">
							<ul class="social">
								<li><a href="#"><span class="facebook icon"></span>Join
										us on Facebook</a></li>
								<li><a href="#"><span class="email icon"></span>Send an
										Email</a></li>
								<li><a href="#"><span class="rss icon"></span>Subscrible
										RSS Feed</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			style="display: none;" aria-labelledby="myModalLabel"
			aria-hidden="true"></div>
	</div>
<script type="text/javascript">
$.extend( $.fn.dataTableExt.oStdClasses, {
	"sSortAsc": "header headerSortDown",
	"sSortDesc": "header headerSortUp",
	"sSortable": "header"
} );

/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{
	return {
		"iStart":         oSettings._iDisplayStart,
		"iEnd":           oSettings.fnDisplayEnd(),
		"iLength":        oSettings._iDisplayLength,
		"iTotal":         oSettings.fnRecordsTotal(),
		"iFilteredTotal": oSettings.fnRecordsDisplay(),
		"iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
		"iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
	};
}

/* Bootstrap style pagination control */
$.extend( $.fn.dataTableExt.oPagination, {
	"bootstrap": {
		"fnInit": function( oSettings, nPaging, fnDraw ) {
			var oLang = oSettings.oLanguage.oPaginate;
			var fnClickHandler = function ( e ) {
				e.preventDefault();
				if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
					fnDraw( oSettings );
				}
			};

			$(nPaging).addClass('pagination').append(
				'<ul>'+
					'<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
					'<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
				'</ul>'
			);
			var els = $('a', nPaging);
			$(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
			$(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
		},

		"fnUpdate": function ( oSettings, fnDraw ) {
			var iListLength = 5;
			var oPaging = oSettings.oInstance.fnPagingInfo();
			var an = oSettings.aanFeatures.p;
			var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

			if ( oPaging.iTotalPages < iListLength) {
				iStart = 1;
				iEnd = oPaging.iTotalPages;
			}
			else if ( oPaging.iPage <= iHalf ) {
				iStart = 1;
				iEnd = iListLength;
			} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
				iStart = oPaging.iTotalPages - iListLength + 1;
				iEnd = oPaging.iTotalPages;
			} else {
				iStart = oPaging.iPage - iHalf + 1;
				iEnd = iStart + iListLength - 1;
			}

			for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
				// Remove the middle elements
				$('li:gt(0)', an[i]).filter(':not(:last)').remove();

				// Add the new list items and their event handlers
				for ( j=iStart ; j<=iEnd ; j++ ) {
					sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
					$('<li '+sClass+'><a href="#">'+j+'</a></li>')
						.insertBefore( $('li:last', an[i])[0] )
						.bind('click', function (e) {
							e.preventDefault();
							oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
							fnDraw( oSettings );
						} );
				}

				// Add / remove disabled classes from the static elements
				if ( oPaging.iPage === 0 ) {
					$('li:first', an[i]).addClass('disabled');
				} else {
					$('li:first', an[i]).removeClass('disabled');
				}

				if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
					$('li:last', an[i]).addClass('disabled');
				} else {
					$('li:last', an[i]).removeClass('disabled');
				}
			}
		}
	}
} );
$(document).ready(function() {
	
	
    oTable = $('#resultTable').dataTable({
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "sDom": '<"F">t<""p>',
        "sPaginationType": "bootstrap",
        "oLanguage": { 
        	"oPaginate": {
        		"sFirst":    "Đầu",
        		"sPrevious": "Trước",
        		"sNext":     "Sau",
        		"sLast":     "Cuối"
        	},
         	"sZeroRecords": "Không có dữ liệu",
       		"sEmptyTable": "Không có dữ liệu",
            "sSearch":"Tìm kiếm"
        }
    });
    //$("#select-type").select2('destroy'); 
} );
</script>
</body>

<script type="text/javascript">
var server = '<%=GlobalValue.getServiceAddress()%>';
var deleteService = '<%=Constants.TRAFFIC_HISTORY_DELETE%>';
	// ajax to delete history
	function deleteHistory(historyID){
		//alert('vao ham delete History');
		console.log('test');
		var url = server + deleteService;
		$.ajax({
			url : url,
			type : "GET",
			data : {
				id : historyID
			},
			success : function(result) {
				if('Success' == result.trim()){
					$( "#"+ historyID ).remove();
				}
			},
			error: function(result)
			{
				if('Success' == result.responseText.trim()){
					$( "#"+ historyID ).remove();
				}
			}
		});
	}
	// ajax reload table
	function reloadTable(){
		var action = "<%=Constants.ACTION_HISTORY_LIST_SHORT%>";
		$.ajax({
			url : "/TrafficWeb/UserController",
			type : "GET",
			data : {
				action : action
			},
			success : function(result) {
				$(".contentTable").html(result);
			}			

		});	
	}
</script>

</html>