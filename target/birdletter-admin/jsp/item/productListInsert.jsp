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
	var itemList = $("#itemList").val();
	if (confirm("입력하시겠습니까?")) {
		$("#loader").show();
		itemService.productListInsert(itemList, function(bl) {
			if (bl == true) {
				$("#loader").hide();
				alert("입력이 완료됬습니다.");
			}
		});
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<div id="loader" style="z-index:20;position:absolute;left:25%;top:25%;margin-left:0px;margin-top:5px;background:#fff;display:none;">
	<span><img src="<%=webRoot%>/img/loadingImg.gif"></span>
</div>
<div class="container">
	<div class="col-xs-10">
		<textarea class="form-control" rows="20" cols="100" id="itemList" name="itemList" placeholder="아이템 코드"></textarea>
		<button type="button" class="btn btn-default" onclick="javascript:insert();">입력</button><br>
		입력 예)<br>
		product_id"<탭>"categoty"<탭>"type"<탭>"main_tab"<탭>"sub_tab"<탭>"item_id"<탭>"name"<탭>"popcorn"<탭>"corn"<탭>"explanation"<엔터>"
		product_id"<탭>"categoty"<탭>"type"<탭>"main_tab"<탭>"sub_tab"<탭>"item_id"<탭>"name"<탭>"popcorn"<탭>"corn"<탭>"explanation"<엔터>"
		....<br>
	</div>
</div>
</form>
</body>
</html>