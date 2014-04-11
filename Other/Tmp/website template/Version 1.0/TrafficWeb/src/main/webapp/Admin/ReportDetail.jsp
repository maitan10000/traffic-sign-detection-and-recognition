<%@page import="json.ReportJSON"%>
<%@page import="utility.Constants"%>
<%@page import="model.TrafficSign"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ReportJSON reportDetails = (ReportJSON) request.getAttribute("reportDetail");
%>

<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myDetailModalLabel">Thông tin phản hồi</h4>
		</div>
		<div class="modal-body">
			<div class="reportDetail">
				<%
					if(reportDetails.getType() == 1){
						int id = Integer.parseInt(reportDetails.getReferenceID());
					
				%>				
				<br><strong>Sửa thông tin: </strong><a  href="<%=Constants.CONTROLLER_ADMIN%>?action=<%=Constants.ACTION_TRAIN_IMAGE_ADD_FROM_REPORT%>&resultID=<%=reportDetails.getReferenceID()%>" data-toggle="modal"  >Sửa</a>
				<%
					}else if (reportDetails.getType() == 2){
				%>
				<br><strong>Sửa thông tin: </strong><a href="#myModal" data-toggle="modal" onclick="showTrafficDetails('<%=reportDetails.getReferenceID()%>'); return false;">Sửa</a>
				<%
					}
				%>
				<%
					if(reportDetails.getType() == 1){
				%>
					<br><strong>Loại phản hồi: </strong> <font>Nhận diện sai</font>
				<%
					}else{
				%>
					<br><strong>Loại phản hồi: </strong><font>Thông tin biển báo sai</font>
				<%
					}
				%>
				<br> <strong>Người gửi:</strong> <font><%=reportDetails.getCreator()%></font>
				<%
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				%>
				<br> <strong>Thời gian gửi:</strong>  <font><%=dateFormat.format(reportDetails.getCreateDate())%>
				<br><br><strong>Nội dung:</strong>  <font><%=reportDetails.getContent()%>
				</font>
			</div>
		</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
				<button type="button" class="btn btn-primary" onclick="deleteReport('<%=reportDetails.getReportID()%>'); return false;">Xóa phản hồi</button>
			</div>
	</div>
</div>