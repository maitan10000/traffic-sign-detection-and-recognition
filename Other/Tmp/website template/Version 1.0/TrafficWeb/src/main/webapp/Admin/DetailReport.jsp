<%@page import="model.Report"%>
<%@page import="model.TrafficSign"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Report reportDetails = (Report) request
			.getAttribute("reportDetail");
%>

<div class="modal-dialog">\
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myDetailModalLabel">Thông tin phản hồi</h4>
		</div>
		<div class="modal-body">
			<div class="reportDetail">
				<br /> <strong>Mã số phản hồi:</strong> <font><%=reportDetails.getReportID()%></font>
				<br /> <br /> <strong>Mã số liên hệ:<a href="#">  <%=reportDetails.getReferenceID()%></a>
				<br /> <br /> <strong>Nội dung:</strong> <font><%=reportDetails.getContent()%>
				</font> <br /> <br /> <strong>Người gửi:</strong> <font><%=reportDetails.getCreator()%></font><br />
				<br /> <strong>Ngày gửi: </strong> <font><%=reportDetails.getCreateDate()%>
				</font>
			</div>
		</div>
		
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" onclick="deleteReport(<%=reportDetails.getReportID()%>)">Xoa phan hoi</button>
			</div>
	</div>

</form>
</div>