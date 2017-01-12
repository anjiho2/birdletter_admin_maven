<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String fileName = Util.isNullValue(request.getParameter("fileName"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/bsmService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>

<script type="text/javascript">
var fileName = '<%=fileName%>';
function init() {
	preViewImage(fileName);
}
//이미지 미리보기
function preViewImage(imageFileName) {
	var awsResourceImageUrl = "<%=awsImageUrl%>" + "<%=awsResourceRoot%>";
	$("#preView").attr("src", awsResourceImageUrl + imageFileName);
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post" class="form-horizontal">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="resourceCode" id="resourceCode">
<input type="hidden" name="res_type" id="res_type">
<img alt="" src="" id="preView" width="100%" height="250px">
</form>
</body>
</html>
