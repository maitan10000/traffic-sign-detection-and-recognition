<%@page import="utility.GlobalValue"%>
<%@page import="java.util.ArrayList"%>
<%@page import="json.TrainImageJSON"%>
<%@page import="json.ReportJSON"%>
<%@page import="utility.Constants"%>
<%@page import="model.TrafficSign"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String trafficID = (String) request.getAttribute("trafficID");
	ArrayList<TrainImageJSON> listTrainImage = (ArrayList<TrainImageJSON>) request.getAttribute("listTrainImage");
%>
<script>
function deleteTrainImage(trainImageID){
	var action = '<%=Constants.ACTION_DELETE_TRAINIMAGE%>';
	$.ajax({
		url: '<%=Constants.CONTROLLER_ADMIN%>',
			type : "GET",
			data : {
				action : action,
				trainImageID : trainImageID
			},
			success : function(result) {
				$("#modal12").modal("hide");
				$("#myAlert .modal-body").text("Xóa ảnh mẫu thành công!");
				$("#myAlert").modal("show");

			}

		});
	}
</script>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button class="close" type="button" data-dismiss="modal">×</button>
			<h3>Ảnh train</h3>
		</div>
		<%
			if (listTrainImage != null) {
		%>
		<div class="modal-body">
			<div class="modal12">
				<%
					if (listTrainImage.size() > 0) {
							for (int i = 0; i < listTrainImage.size(); i++) {
				%>
				<img style="margin: auto;" class="imageDetails"
					src="<%=GlobalValue.getServiceAddress()%><%=listTrainImage.get(i).getImageName()%>"
					alt="Responsive image" />
				<button class="btn btn-danger btn-mini"
					onclick="deleteTrainImage('<%=listTrainImage.get(i).getImageID()%>')">Xóa</button>
				<%
					}
				%>
			</div>
			<%
				}
			%>
			<div class="modal-footer">
				<a class="btn btn-inverse" href="#" data-dismiss="modal">Đóng</a>
			</div>
		</div>
		<%
			}
		%>
	</div>
</div>

