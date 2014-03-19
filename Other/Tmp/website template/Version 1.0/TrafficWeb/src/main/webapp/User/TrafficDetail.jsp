<%@page import="model.TrafficSign"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
TrafficSign trafficDetails = (TrafficSign) request.getAttribute("trafficDetail");
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
									src="Content/Image/Traffic/bien nguy hiem tre em.jpg"
									alt="Responsive image" />
							</div>
							<br /> <strong>Số hiệu biển báo:</strong> <font> <%=trafficDetails.getTrafficID() %></font> <br />
							<br /> <strong>Tên Biển Báo:</strong> <font> <%=trafficDetails.getName() %></font> <br /> <br /> <strong>Nội
								dung:</strong> <font><%=trafficDetails.getInformation() %> </font> <br /> <br /> <strong>Mức
								phạt:</strong> <font> Biển này được sử dụng độc lập ở những vị trí
								sang ngang, đường không có tổ chức điều khiển giao thông hoặc có
								thể sử dụng phối hợp với vạch kẻ đường. Gặp biển này người lái
								xe phải điều khiển xe chạy chậm, chú ý quan sát, ưu tiên cho
								người đi bộ sang ngang. </font>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary">Lưu biển
							báo</button>
					</div>
				</div>
			</div>