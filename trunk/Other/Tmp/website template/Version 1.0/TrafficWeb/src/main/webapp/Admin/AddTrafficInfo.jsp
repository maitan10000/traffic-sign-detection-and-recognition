<%@page import="java.util.ArrayList"%>
<%@page import="json.TrafficInfoShortJSON"%>
<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script type="text/javascript">
	function uploadFile() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showResult(xhr.responseText);
			}
		}
		var tmpForm = document.getElementById("add_traffic_form");
		/* TrafficID : <input type="text" name="trafficID" /><br> Name: <input
		type="text" name="name" /><br> Image : <input type="file"
		name="mainImage" size="45" /><br> CategoryID:<input type="text"
		name="categoryID" /><br> Information:<input type="text"
		name="information" /><br> PenaltyFee: <input type="text"
		name="penaltyfee" /><br> Creator: <input type="text" name="creator"
		value="user1" /><br> */
		
		var formData = new FormData();
		formData.append("trafficID", tmpForm.trafficID.value);
		formData.append("name", tmpForm.name.value);
		console.log(tmpForm.mainImage);
		formData.append('mainImage', tmpForm.mainImage.files[0]);
		formData.append("categoryID", tmpForm.categoryID.value);
		formData.append("information", tmpForm.information.value);
		formData.append("penaltyfee", tmpForm.penaltyfee.value);
		formData.append('creator', tmpForm.creator.value);
		console.log(formData);
		
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
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_ADD%>');
		//xhr.overrideMimeType('text/plain; charset=utf-8');
		xhr.send(formData);
	}

	function showResult(result) {
		if (result.trim() != "Success") {
			$("#AddTrafficModal").modal('hide');
			$("#myAlert .modal-body").text("Thêm mới thất bại!");
			$("#myAlert").modal("show");		

		} else {
			$("#AddTrafficModal").modal('hide');
			$("#myAlert .modal-body").text("Thêm mới thành công!");
			$("#myAlert").modal("show");
			location.reload();
		}
	}
	function validateForm() {
		var x = document.forms["add-traffic"]["trafficID"].value;
		if (x == null || x == "") {
			alert("Số hiệu biển báo phải được điền!!!");
			return false;
		}
	}
</script>
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
				<form name="add-traffic" id="add_traffic_form" method="post"
					class="form-horizontal" onsubmit="return validateForm()">
					<div class="control-group" align="left">
						<label class="control-label">Số hiệu Biển Báo :</label>
						<div class="controls">
							<input id="required" name="trafficID" type="text" class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Tên Biển Báo :</label>
						<div class="controls">
							<input name="name" type="text" class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Hình ảnh:</label>
						<div class="controls">
							<input name="mainImage" type="file" class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Loại biển báo</label>
						<div class="controls">
							<select name="categoryID">
								<option value="1">Biển báo cấm</option>
								<option value="2">Biển báo nguy hiểm</option>
							</select>
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Thông tin</label>
						<div class="controls">
							<textarea style="width: 500px; height: 150px;" class="span4"
								name="information"></textarea>
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Mức phạt</label>
						<div class="controls">
							<input name="penaltyfee" type="text" class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<div class="controls">
							<input type="hidden" name="creator"
								value="<%=(String) session.getAttribute(Constants.SESSION_USERID)%>" />
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id="footerViewDetail" class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
			<button type="submit" class="btn btn-success"
				onclick="uploadFile(); return false;">Lưu</button>
		</div>
	</div>
</div>
</div>