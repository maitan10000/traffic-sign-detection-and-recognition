<%@page import="java.text.SimpleDateFormat"%>
<%@page import="json.ResultShortJSON"%>
<%@page import="json.FavoriteJSON"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utility.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ArrayList<ResultShortJSON> listHistory = (ArrayList<ResultShortJSON>) request.getAttribute("listHistory");
%>
<table id="resultTable" class="table table-striped .table-condensed">
	<thead>
		<th style="text-align: center;width: 70px;">STT</th>
		<th style="text-align: center;">Ngày tạo</th>
		<th></th>
	</thead>
	<tbody>
		<%
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			if (listHistory.size() > 0) {
				for (int i = listHistory.size() - 1; i >= 0; i--) {
		%>

		<tr>
			<td style="text-align: center;"><%=listHistory.size() - i%></td>
			<td style="text-align: center;"><a
				href="<%=Constants.CONTROLLER_USER%>?action=<%=Constants.ACTION_HISTORY_VIEW%>&resultID=<%=listHistory.get(i).getResultID()%>"
				data-toggle="modal"><%=dateFormat.format(listHistory.get(i)
							.getCreateDate())%></a></td>
			<td style="text-align: center;"><button class="btn btn-inverse"
					onclick="deleteHistory('<%=listHistory.get(i).getResultID()%>')">Xóa</button></td>
		</tr>
		<%
			}
			}
		%>
	</tbody>
</table>