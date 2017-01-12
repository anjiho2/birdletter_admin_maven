<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>
<script type="text/javascript">
function insert() {
	var userId = $("#userId").val();
	var itemList = $("#itemList").val();
	
	if (userId == "") {
		alert("사용자 아이디를 입력하세요.");
		$("#userId").focus();
		return;
	}
	if (itemList == "") {
		alert("아이템코드를 입력하세요.");
		$("#itemList").focus();
		return;
	}
	
	if (confirm("입력하시겠습니까?")) {
		itemService.itemListInsert(userId, itemList, function(bl) {
			if (bl == true) {
				alert("입력 성공했습니다.");
				location.reload();
			} else {
				alert("입력 실패");
				return;
			}
		});
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body> 
<form name="frm" method="get">
<input type="hidden" name="page_gbn" id="page_gbn">

<div class="container">
	<div class="col-xs-2">
		<input type="text" class="form-control" id="userId" name="userId" placeholder="사용자 아이디">
		<textarea class="form-control" rows="20" cols="50" id="itemList" name="itemList" placeholder="아이템 코드"></textarea>
		입력 예)<br>
		아이템코드<엔터><br>
		아이템코드<엔터><br>
		....<br>
		<button type="button" class="btn btn-default" onclick="javascript:insert();">입력</button>
	</div>
	
	
</div>
</form>
</body>
</html>