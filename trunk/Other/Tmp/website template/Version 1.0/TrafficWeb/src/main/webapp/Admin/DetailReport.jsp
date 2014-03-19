<%@page import="model.Report"%>
<%@page import="model.TrafficSign"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Report reportDetails = (Report) request
			.getAttribute("reportDetail");
%>
<div class="modal-dialog">

	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">DetailReport</h4>
		</div>
		<div class="modal-body">
			<div class="reportDetail">
				<br /> <strong>Ma so phan hoi:</strong> <font><%=reportDetails.getReportID()%></font>
				<br /> <br /> <strong>Ma so lien he:</strong> <font> <%=reportDetails.getReferenceID()%></font>
				<br /> <br /> <strong>Nội dung:</strong> <font><%=reportDetails.getContent()%>
				</font> <br /> <br /> <strong>Nguoi gui:</strong> <font><%=reportDetails.getCreator()%></font><br />
				<br /> <strong>Ngay gui: </strong> <font><%=reportDetails.getCreateDate()%>
				</font> <br /> <br />
			</div>
		</div>
		
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" name="action"
					value="delete">Xoa phan hoi</button>
			</div>
	</div>


</div>