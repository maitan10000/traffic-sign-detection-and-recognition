<%@page import="javax.swing.text.StyledEditorKit.ForegroundAction"%>
<%@page import="utility.GlobalValue"%>
<%@page import="json.TrafficInfoJSON"%>
<%@page import="utility.Constants"%>
<%@page import="json.CategoryJSON"%>
<%@page import="java.util.ArrayList"%>
<%@page import="json.TrainImageJSON"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	ArrayList<CategoryJSON> listCat = (ArrayList<CategoryJSON>) request.getAttribute("cateList");
	TrafficInfoJSON trafficDetails = (TrafficInfoJSON) request.getAttribute("trafficDetail");
	ArrayList<TrainImageJSON> listTrainImage = (ArrayList<TrainImageJSON>) request.getAttribute("listTrainImage");
%>
<script type="text/javascript">
	function editTraffic() {
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
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_EDIT%>');
		xhr.overrideMimeType('text/plain; charset=utf-8');
		xhr.send(formData);
	}

	function showResult(result) {
		console.log(result);
		if (result.trim() != "Success") {
			$.gritter.add({
				title : 'Thông báo',
				text : 'Sửa thất bại',
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
	
	function deteleTrainImage(trainImageID)
	{
		$.ajax({
			url: '<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_TRAIN_IMAGE_DELETE%>',
			type: "GET",
			data: {trainImageID : trainImageID},
			success: function (result) {
				if("Success" == result.trim())
				{
					$('#'+trainImageID).remove();
				}
			}
			
		});
	}
</script>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">Sửa thông tin biển báo</h4>
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
								type="text" class="span2" value="<%=trafficDetails.getTrafficID()%>" disabled="disabled" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Tên biển báo<span class="required-item">*</span>:</label>
						<div class="controls">
							<input style="width: 300px;" name="name" type="text"
								class="span2" value="<%=trafficDetails.getName()%>" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Hình ảnh<span class="required-item">*</span>:</label>
						<div class="controls">
							<img style="margin: auto;"
								class="imageDetails"
								src="<%=GlobalValue.getServiceAddress()%><%=trafficDetails.getImage()%>?size=small"
								alt="Responsive image" />
							<input style="width: 300px;" name="mainImage" type="file"
								class="span2" />
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Loại biển báo<span class="required-item">*</span>: </label>
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
						<label class="control-label">Thông tin<span class="required-item">*</span>:</label>
						<div class="controls">
							<textarea style="width: 500px; height: 100px;" class="span4"
								name="information"><%=trafficDetails.getInformation()%></textarea>
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Mức phạt tham khảo:</label>
						<div class="controls">
						<textarea style="width: 500px; height: 50px;" class="span4"
								name="penaltyfee"><%=trafficDetails.getPenaltyfee()!=null?trafficDetails.getPenaltyfee():""%></textarea>						
						</div>
					</div>
					<div class="control-group" align="left">
						<label class="control-label">Ảnh nhận dạng:</label>						
						<div class="controls">						
							<input type="file" name="trainImage" multiple/>
						</div>
						<div class="controls">						
							 <ul id="list-train-image" class="thumbnails">
							 <%
							 for(int i = 0; i < listTrainImage.size(); i++)
							 {
							 %>
                                <li id="<%=listTrainImage.get(i).getImageID()%>" class="span2 trainImage-resize">									
										<img class="thumbnail lightbox_trigger" src="<%=GlobalValue.getServiceAddress()%><%=listTrainImage.get(i).getImageName()%>?size=small" alt="" >
									<div class="actions">
										<a title="" href="#" onclick="deteleTrainImage('<%=listTrainImage.get(i).getImageID()%>'); return false;"><i class="icon-remove icon-white"></i></a>
									</div>
								</li>
							<%
							 }//emd for
							%>
							</ul>
						</div>
					</div>
				</form>				
				
			</div>
		</div>
		<div id="footerViewDetail" class="modal-footer">
			<label style="float: left; font-size: 12px;"><span class="required-item">*</span> Trường bắt buộc</label>
			<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
			<button type="submit" class="btn btn-success"
				onclick="editTraffic(); return false;" value="submit">Lưu</button>
		</div>
	</div>
</div>


