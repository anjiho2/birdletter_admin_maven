<%@ page import="com.challabros.birdletter.admin.dto.AdminDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/top.jsp"%>
<%
	AdminDto adminDto = (AdminDto)session.getAttribute("adminInfo");
	if (adminDto != null) session.invalidate();
%>
<script>
	location.href="<%=webRoot%>";
</script>