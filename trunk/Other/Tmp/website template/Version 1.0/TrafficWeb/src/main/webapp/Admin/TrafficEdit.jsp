<%@page import="utility.GlobalValue"%>
<%@page import="json.TrafficInfoJSON"%>
<%@page import="utility.Constants"%>
<%@page import="json.CategoryJSON"%>
<%@page import="java.util.ArrayList"%>
<%@page import="json.TrainImageJSON"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ArrayList<CategoryJSON> listCat = (ArrayList<CategoryJSON>) request.getAttribute("cateList");
	TrafficInfoJSON trafficDetails = (TrafficInfoJSON) request.getAttribute("trafficDetail");
	String trafficID = (String)request.getAttribute("trafficID");
	ArrayList<TrainImageJSON> listTrainImage = (ArrayList<TrainImageJSON>) request.getAttribute("listTrainImage");
%>

<script type="text/javascript">
	function updateTrafficInfo() {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				showResult(xhr.responseText);
			}
		}
		var tmpForm = document.getElementById("update_traffic_form");
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
		xhr.open("POST",'<%=GlobalValue.getServiceAddress()%><%=Constants.TRAFFIC_TRAFFIC_UPDATE%>');
		//xhr.overrideMimeType('text/plain; charset=utf-8');
		xhr.send(formData);
	}

	function showResult(result) {
		if(result.trim() != "Success"){
			$("#myAlert .modal-body").text("Cập nhật thất bại!");
			$("#myAlert").modal("show");
		}
		$("#trafficDetailModal").modal('hide');
		$("#myAlert .modal-body").text("Cập nhật thành công!");
		$("#myAlert").modal("show");
		location.reload(); 
		

	}
	function viewTrainImage(trafficID){
		var action = '<%=Constants.ACTION_TRAFFIC_VIEWTRAINIMAGE%>';
		$.ajax({
			url: '<%=Constants.CONTROLLER_ADMIN%>',
			type: "GET",
			data: {action : action, trafficID : trafficID},
			success: function (result) {				
				$("#modal12").html(result);
				$("#modal12").modal('show');
			}
			
		});
	}
	
$('div').hover(function() {
		   $(this).find('.delete').show();
		}, function() {
		   $(this).find('.delete').hide();
		});

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
				<form id="update_traffic_form" method="post" class="form-horizontal">
					<div class="row-fluid">
						<div class="span5" style="margin-left: -50px">
							<label class="control-label">Số hiệu biển báo :</label>
							<div class="controls">
								<input name="trafficID" type="text" class="span6"
									value="<%=trafficDetails.getTrafficID()%>" disabled="disabled" />

							</div>
						</div>
						<div class="span7" style="margin-left: -50px">
							<label class="control-label">Tên biển báo :</label>
							<div class="controls">
								<input name="name" type="text" class="span12"
									value="<%=trafficDetails.getName()%>" />
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span5" style="margin-left: -50px">
							<label class="control-label" style="margin-left: -50px">Ảnh
								mẫu:</label>
							<div class="controls">
								<a href="#modal12"
									onclick="viewTrainImage(<%=trafficDetails.getTrafficID()%>)">Xem</a>
							</div>

						</div>
						<div class="span7" style="margin-left: -50px">
							<label class="control-label">Loại biển báo: </label>
							<div class="controls">
								<select name="categoryID" class="span12">
									<%
										for (int i = 0; i < listCat.size(); i++) {
									%>
									<option class="font-Style"
										value="<%=listCat.get(i).getCategoryID()%>"><%=listCat.get(i).getCategoryName()%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span5" style="margin-left: -50px">
							<label class="control-label" style="margin-left: -50px">Mức
								phạt: </label>
							<div class="controls">
								<input name="penaltyfee" type="text" class="span12"
									value="<%=trafficDetails.getPenaltyfee()%>" />
							</div>
						</div>
						<div class="span7" style="margin-left: -50px">
							<label class="control-label"><img style="margin: auto;"
								class="imageDetails"
								src="<%=GlobalValue.getServiceAddress()%><%=trafficDetails.getImage()%>?size=small"
								alt="Responsive image" /></label>
							<div class="controls">
								<input type="file" name="mainImage" />
							</div>
						</div>

					</div>
					<div class="control-group" style="margin-left: -50px">
						<label class="control-label" style="margin-left: -50px">Thông
							tin: </label>
						<div class="controls">
							<textarea style="width: 500px; height: 150px;" " class="span12"
								name="information"><%=trafficDetails.getInformation()%></textarea>
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
	</div>
</div>
<div id="footerViewDetail" class="modal-footer">
	<button style="margin-right: 500px" type="submit"
		class="btn btn-primary"
		onclick="addTrafficImage(<%=trafficDetails.getTrafficID()%>); return false;">Thêm
		ảnh mẫu</button>
	<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
	<button type="submit" class="btn btn-primary"
		onclick="updateTrafficInfo(); return false;">Lưu</button>
</div>
</div>
</div>