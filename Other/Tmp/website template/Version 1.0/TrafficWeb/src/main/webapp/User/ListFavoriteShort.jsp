<%@page import="json.FavoriteJSON"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utility.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ArrayList<FavoriteJSON> listTraffic = (ArrayList<FavoriteJSON>) request.getAttribute("listTraffic");
%>

<table id="resultTable" class="table table-striped .table-condensed">
							<thead>
								<th>Hình Ảnh</th>
								<th>Số Hiệu</th>
								<th>Tên Biển Báo</th>
								<th></th>
							</thead>
							<tbody>
								<%
									if( listTraffic.size()> 0){
										for(int i = 0; i< listTraffic.size();i++){
								%>

								<tr>
									<td><img class="trafficImage"
										src="http://bienbaogiaothong.tk/<%=listTraffic.get(i).getTrafficImage()%>"
										alt="Responsive image" /></td>
									<td><%=listTraffic.get(i).getTrafficID()%></td>
									<td><a href="#myModal" data-toggle="modal"
										onclick="showDetails('<%=listTraffic.get(i).getTrafficID()%>')"><%=listTraffic.get(i).getTrafficName()%></a></td>
									<td><button class="btn btn-inverse" onclick="deleteFavorite('<%=listTraffic.get(i).getTrafficID()%>')">Xóa</button></td>
								</tr>
								<%
									} 
																																							}
								%>
							</tbody>
						</table>