<%@page import="java.io.Console"%>
<%@page import="utility.Constants"%>
<%
	String redirectURL = Constants.CONTROLLER_USER+"?action="+Constants.ACTION_SEARCH_AUTO;
    response.sendRedirect(redirectURL);
 %>