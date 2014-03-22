<%@page import="model.Result"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Result resultDetails = (Result) request
			.getAttribute("resultDetail");
%>
<div class="modal-dialog">

	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">Thông tin kết quả</h4>
		</div>
		<div class="modal-body">
			<div class="resultDetail">
				<br /> <strong>Hình ảnh:</strong> <font><%=resultDetails.getUploadedImage()%></font>
				<br /> <br /> <strong>Danh sách biển báo:</strong> <font> <%=resultDetails.getListTraffic()%></font>
				<br /> <br /> <strong>Người tạo:</strong> <font><%=resultDetails.getCreator()%>
				</font> <br /> <br /> <strong>Ngày tạo:</strong> <font><%=resultDetails.getCreateDate()%></font><br />
				<br /> <strong>Trạng thái: </strong> <font><%=resultDetails.getIsActive()%>
				</font> <br /> <br />
			</div>
		</div>
		
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<!-- <button type="button" class="btn btn-primary" name="action"
					value="delete">Xoa phan hoi</button> -->
			</div>
	</div>


</div>