<%@page import="utility.Constants"%>
<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String trafficID = (String) request.getAttribute("trafficID");
%>
<script type="text/javascript">
	function uploadTrainImage() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showResult(xhr.responseText);
			}
		}
		var file = document.getElementById("mainImage");
		for( var i = 0; i < file.files.length; i++){
		var tmpForm = document.getElementById("add_traffic_train_image");
		/* TrafficID : <input type="text" name="trafficID" /><br> Name: <input
		type="text" name="name" /><br> Image : <input type="file"
		name="mainImage" size="45" /><br> CategoryID:<input type="text"
		name="categoryID" /><br> Information:<input type="text"
		name="information" /><br> PenaltyFee: <input type="text"
		name="penaltyfee" /><br> Creator: <input type="text" name="creator"
		value="user1" /><br> */		
		var formData = new FormData();
		formData.append("trafficID", tmpForm.trafficID.value);		
		formData.append('mainImage', tmpForm.mainImage.files[i]);		
		xhr.upload.addEventListener("progress", function(e) {
			if (e.lengthComputable) {
				//var percentage = Math.round((e.loaded * 100) / e.total);
				//$("#progress").html('<span class="bar" style="width: ' + percentage + '%"></span>');
				//console.log("%: "+ percentage);
			}
		}, false);
		xhr.upload.addEventListener("load", function(e) {
			//$("#progress").html('<span class="bar" style="width: 100%"></span>');
			//console.log("%: 100");
		}, false);
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_ADDTRAINIMAGE%>');
		//xhr.overrideMimeType('text/plain; charset=utf-8');
		xhr.send(formData);
		}
	}	

	function showResult(result) {
		if (result.trim() != "Success") {
			$("#AddTrainImageModal").modal('hide');
			$("#myAlert .modal-body").text("Thêm mới thất bại!");
			$("#myAlert").modal("show");		

		} else {
			$("#AddTrainImageModal").modal('hide');
			$("#myAlert .modal-body").text("Thêm mới thành công!");
			$("#myAlert").modal("show");			
		}
	}
</script>
<div class="modal-dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Thông Tin Biển Báo</h4>
			</div>
			<div class="modal-body">
				<div class="trafficDetail">
					<div class="contentImgDetails"></div>
					<form id="add_traffic_train_image" method="post"
						class="form-horizontal">
						<div class="control-group" align="left">
						<label class="control-label">Số hiệu biển báo :</label>
						<div class="controls">
							<input name="trafficID" type="text" class="span1"
								value="<%=trafficID%>" disabled="disabled" />
						</div>
						<div class="control-group" align="left">
						<label class="control-label">Ảnh mẫu :</label>
						<div class="controls">
							<input type="file" name="mainImage" id="mainImage" multiple/>
						</div>
					</div>
					</div>
					</form>
				</div>
			</div>
			<div id="footerViewDetail" class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
				<button onclick="uploadTrainImage(); return false;" type="submit"
					class="btn btn-primary">Xác nhận</button>
			</div>
		</div>
	</div>
</div>
