<%@page import="java.util.ArrayList"%>
<%@page import="json.TrafficInfoShortJSON"%>
<%@page import="utility.GlobalValue"%>
<%@page import="utility.Constants"%>
<%@page import="json.CategoryJSON"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	ArrayList<CategoryJSON> listCat = (ArrayList<CategoryJSON>) request.getAttribute("cateList");
%>
<script type="text/javascript">
	function addTraffic() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showResult(xhr.responseText);
			}
		}
		var tmpForm = document.getElementById("add_traffic_form");		
		
		var formData = new FormData();
		formData.append("trafficID", tmpForm.trafficID.value);
		formData.append("name", tmpForm.name.value);
		formData.append('mainImage', tmpForm.mainImage.files[0]);
		formData.append("categoryID", tmpForm.categoryID.value);
		formData.append("information", tmpForm.information.value);
		formData.append("penaltyfee", tmpForm.penaltyfee.value);
		formData.append('creator', tmpForm.creator.value);
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_ADD%>');
		xhr.overrideMimeType('text/plain; charset=utf-8');
		xhr.send(formData);
	}

	function showResult(result) {
		console.log(result);
		if (result.trim() != "Success") {
			$.gritter.add({
				title : 'Thông báo',
				text : 'Thêm mới thất bại',
				sticky : false
			});
		} else {
			addTrainImage();
		}
	}
	
	function addTrainImage() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showAddTrainImageResult(xhr.responseText);
			}
		}
		var tmpForm = document.getElementById("add_traffic_form");		
		
		var formData = new FormData();
		console.log( tmpForm.trainImage.files.length);		
		for(var i = 0; i < tmpForm.trainImage.files.length; i++) 
		{
			formData.append('trainImage[]', tmpForm.trainImage.files[i]);
		}
		formData.append("trafficID", tmpForm.trafficID.value);
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_TRAIN_IMAGE_ADD%>');
		xhr.overrideMimeType('text/plain; charset=utf-8');
		xhr.send(formData);
	}
	
	function showAddTrainImageResult(result) {
		console.log(result);
		if (result.trim() != "Success") {
			
		} else {
			location.reload();
		}
	}
	$(document).ready(function(){		
		//$('input[type=checkbox],input[type=radio],input[type=file]').uniform();		
	});

	
</script>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">Thêm mới biển báo</h4>
		</div>
		<div class="modal-body">
			<div class="trafficDetail">
				<div class="contentImgDetails"></div>
				<form id="add_traffic_form" method="post" class="form-horizontal"
					onsubmit="return validateForm()">
					<input type="hidden" name="creator"
								value="<%=(String) session.getAttribute(Constants.SESSION_USERID)%>" />
					<div class="control-group" align="left">
						<label class="control-label">Số hiệu biển báo:</label>
						<div class="controls">
							<input style="width: 300px;" id="required" name="trafficID"
								type="text" class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Tên biển báo:</label>
						<div class="controls">
							<input style="width: 300px;" name="name" type="text"
								class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Hình ảnh:</label>
						<div class="controls">
							<input style="width: 300px;" name="mainImage" type="file"
								class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Loại biển báo: </label>
						<div class="controls">
							<select style="width: 300px;" name="categoryID">
								<%
									for (int i = listCat.size() - 1; i >= 0; i--) {
								%>
								<option class="font-Style"
									value="<%=listCat.get(i).getCategoryID()%>"><%=listCat.get(i).getCategoryName()%></option>
								<%
									}
								%>
							</select>
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Thông tin:</label>
						<div class="controls">
							<textarea style="width: 500px; height: 100px;" class="span4"
								name="information"></textarea>
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Mức phạt tham khảo:</label>
						<div class="controls">
						<textarea style="width: 500px; height: 50px;" class="span4"
								name="penaltyfee"></textarea>						
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Ảnh nhận diện:</label>
						<div class="controls">
							<input type="file" name="trainImage" multiple/>
						</div>
					</div>
				</form>				
				
			</div>
		</div>
		<div id="footerViewDetail" class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
			<button type="submit" class="btn btn-success"
				onclick="addTraffic(); return false;" value="submit">Lưu</button>
		</div>
	</div>
</div>
