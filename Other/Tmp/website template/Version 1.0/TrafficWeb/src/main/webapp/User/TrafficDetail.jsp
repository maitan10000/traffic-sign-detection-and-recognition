<%@page import="utility.GlobalValue"%>
<%@page import="json.TrafficInfoJSON"%>
<%@page import="model.TrafficSign"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	TrafficInfoJSON trafficDetails = (TrafficInfoJSON) request
			.getAttribute("trafficDetail");
%>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">Thông Tin Biển Báo</h4>
		</div>
		<div class="modal-body">
			<div class="trafficDetail">
				<div class="contentImgDetails">
					<img class="imageDetails"
						src="<%=GlobalValue.getServiceAddress() %><%=trafficDetails.getImage()%>?size=big"
						alt="Responsive image" />
				</div>
				<br /> <strong>Số hiệu biển báo:</strong> <font> <%=trafficDetails.getTrafficID()%></font>
				<br /> <br /> <strong>Tên Biển Báo:</strong> <font> <%=trafficDetails.getName()%></font>
				<%if(trafficDetails.getPenaltyfee() != null && trafficDetails.getPenaltyfee().length() > 1){ %>
				- Khi vi phạm phạt : <%=trafficDetails.getPenaltyfee() %>
				<%} %>
				<br /> <br /> <strong>Nội dung:</strong> <font><%=trafficDetails.getInformation()%>
				</font> <br /> <br />
			</div>
		</div>
		<div id="footerViewDetail" class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="btnSend" type="button" class="btn btn-primary"
				onclick="showFromReport('<%=trafficDetails.getTrafficID()%>')">Gửi ý kiến</button>
				<button id="btnAddFavorite" type="button" class="btn btn-primary"
				onclick="addFavorite('<%=trafficDetails.getTrafficID()%>')">Lưu
				biển báo</button>
		</div>
	</div>
</div>