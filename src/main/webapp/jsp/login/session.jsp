<%@page import="com.challabros.birdletter.admin.util.CreateSESSION"%>
<%@page import="com.challabros.birdletter.admin.dto.AdminDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	if (request.getProtocol().equals("HTTP/1.1"))
		response.setHeader("Cache-Control", "no-cache");
	
	String webRoot = request.getContextPath();
	AdminDto adminInfo = (AdminDto)session.getAttribute("adminInfo");
	
	String page_gbn = "";
	
	if (adminInfo == null) {
		try {
			adminInfo = CreateSESSION.sessionCF(request);
			session.setAttribute("adminInfo", adminInfo);
			session.setMaxInactiveInterval(60*60);
		}catch (Exception e) {
			System.out.println("SESSION CREATE ERROR.....");
			e.printStackTrace();
		}
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type='text/javascript' src='<%=webRoot%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=webRoot%>/dwr/util.js'></script>
	<script type='text/javascript' src='<%=webRoot%>/common/js/common.js'></script>
</head>
<script type="text/javascript">
var page_gbn = "<%=page_gbn%>";
function init() {
	with(document.frm) {
		action = "<%=webRoot%>/dashboard.do";
		page_gbn.value = "dashboardList";
		submit();
	}
}
</script>
<body onload="init();">
<form name="frm" method="post">
	<input type="hidden" name="page_gbn">
</form>
</body>
</html>