<%@page import="utility.GlobalValue"%>
<%@page import="json.FavoriteJSON"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
	ArrayList<FavoriteJSON> listTraffic = (ArrayList<FavoriteJSON>) request.getAttribute("listTraffic");
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
					
						<h2 class="logo" style="color: #FFF;">HỆ THỐNG NHẬN DIỆN, TRA CỨU BIỂN
						BÁO</h2>
					
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
							class="level-top"> <span>Nhận Diện Tự Động</span>
						</a></li>
						<li class="level0 nav-3 level-top"><a
							href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_SEARCH_MANUAL%>"
							class="level-top"> <span>Tra Cứu Biển Báo</span>
						</a></li>
						<%if(userID != null || "".equals(userID)){ %>
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
					<div class="content-title">DANH SÁCH ĐÃ LƯU</div>
					<%
						if( listTraffic != null){
					%>
					<div id="contentTable" class="contentTable "
						style="margin-top: 20px">
						<table id="resultTable"
							class="table table-bordered dataTable">
							<thead>
								<th>Hình Ảnh</th>
								<th>Số Hiệu</th>
								<th>Tên Biển Báo</th>
								<th></th>
							</thead>
							<tbody>
								<%
									if( listTraffic.size()> 0){
																						for(int i = 0; i< listTraffic.size();i++){
								%>

								<tr id="<%=i%>">
									<td><a href="#myModal" data-toggle="modal"
										onclick="showDetails('<%=listTraffic.get(i).getTrafficID()%>')"><img class="trafficImage"
										src="<%=GlobalValue.getServiceAddress()%><%=listTraffic.get(i).getImage()%>"
										alt="Responsive image" /></a></td>
									<td><%=listTraffic.get(i).getTrafficID()%></td>
									<td><a href="#myModal" data-toggle="modal"
										onclick="showDetails('<%=listTraffic.get(i).getTrafficID()%>')"><%=listTraffic.get(i).getName()%></a></td>
									<td><button class="btn btn-inverse"
											onclick="deleteFavorite('<%=listTraffic.get(i).getTrafficID()%>')">Xóa</button></td>

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
					<div style="clear: both"></div>
				</div>
				<div class="footer-container">
					<div class="footer">
						<div class="footer-brands">
							<div class="brands"></div>
						</div>
						<div class="footer-left">
							<p>
								<b>HỆ THỐNG NHẬN DIỆN, TRA CỨU BIỂN BÁO</b>
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
		<!-- Modal for report-->
		<div id="reportModal" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">Gửi Ý Kiến</h3>
			</div>
			<div class="modal-body">
				<strong>Nội dung ý kiến :</strong><br>
				<textarea rows="3" cols="12" id="txtContent"
					style="width: 515px; resize: none;"></textarea>
				<input type="hidden" id="reference_id" />
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Đóng</button>
				<button class="btn btn-primary" id="btnSubmitReport" onclick="">Gửi
					Ý Kiến</button>
			</div>
		</div>

</body>
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
        "sSearch":"Tìm kiếm"
        }
    });
    //$("#select-type").select2('destroy'); 
} );
</script>
<script type="text/javascript">
	//ajax to get traffic detail when click link
	function showDetails(trafficID) {
		var action = "viewDetail";
		$.ajax({
			url : "/TrafficWeb/UserController",
			type : "GET",
			data : {
				action : action,
				trafficID : trafficID
			},
			success : function(result) {
				$("#myModal").html(result);
				checkFavorite(trafficID);
			}

		});
	}
	// ajax to add favorite when click button luu bien bao
	function addFavorite(trafficID) {
		var action = "AddFavorite";
		$
				.ajax({
					url : "/TrafficWeb/UserController",
					type : "GET",
					data : {
						action : action,
						trafficID : trafficID
					},
					success : function(result) {
						// if add ok, change buuton to xoa bien bao
						if ("Success" == result.trim()) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id="btnAddFavorite" type=\42button\42 class=\42btn btn-primary\42 onclick=\42deleteFavorite(\''
													+ trafficID
													+ '\')\42>Xóa biển báo</button>');
							reloadTable();
						}
					}

				});
	}
	// ajax send report
	function sendReport(trafficID) {
		var type = '2';
		var content = document.getElementById("txtContent").value;
		var action = "reportTraffic";
		$.ajax({
			url : "/TrafficWeb/UserController",
			type : "GET",
			data : {
				action : action,
				trafficID : trafficID,
				type : type,
				content : content
			},
			success : function(result) {
				$('#reportModal').modal('hide');
			}

		});

	}
	// function show popup for send report
	function showFromReport(trafficID) {
		$('#reference_id').val(trafficID);
		$('#myModal').modal('hide');
		$('#reportModal').modal('show');

	}
	// event on shown to set onclick
	$('#reportModal').on('show', function() {
		document.getElementById("txtContent").value = '';
		var trafficID = document.getElementById("reference_id").value;
		var functionName = 'sendReport("' + trafficID + '")';
		var button = document.getElementById("btnSubmitReport");
		button.getAttributeNode("onclick").value = functionName;
	});
	// ajax to delete favorite when click button xoa bien bao
	function deleteFavorite(trafficID) {
		var action = "DeleteFavorite";
		$
				.ajax({
					url : "/TrafficWeb/UserController",
					type : "GET",
					data : {
						action : action,
						trafficID : trafficID
					},
					success : function(result) {
						// if delete ok, change buuton to luu bien bao
						if ("Success" == result.trim()) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id="btnAddFavorite" type=\42button\42 class=\42btn btn-primary\42 onclick=\42addFavorite(\''
													+ trafficID
													+ '\')\42>Lưu biển báo</button>');
							reloadTable();
						}
					}

				});
	}
	// ajax to display "luu bien bao" button if traffic is not added
	function checkFavorite(trafficID) {
		var action = "checkFavorite";
		$
				.ajax({
					url : "/TrafficWeb/UserController",
					type : "GET",
					data : {
						action : action,
						trafficID : trafficID
					},
					success : function(result) {
						if ('true' == result) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id=\42btnAddFavorite\42 type=\42button\42 class=\42btn btn-primary\42 onclick=\42addFavorite(\''
													+ trafficID
													+ '\')\42>Lưu biển báo</button>');
						} else if ('false' == result) {
							$("#btnAddFavorite").remove();
							$("#footerViewDetail")
									.append(
											'<button id=\42btnAddFavorite\42 type=\42button\42 class=\42btn btn-primary\42 onclick=\42deleteFavorite(\''
													+ trafficID
													+ '\')\42>Xóa biển báo</button>');
						} else {
							$("#btnAddFavorite").remove();
						}
					}

				});
	}
	// ajax to refresh table
	function reloadTable() {
		var action = "viewFavoriteShort";
		$.ajax({
			url : "/TrafficWeb/UserController",
			type : "GET",
			data : {
				action : action
			},
			success : function(result) {
				$("#contentTable").html(result);

			}

		});
	}
</script>
</html>