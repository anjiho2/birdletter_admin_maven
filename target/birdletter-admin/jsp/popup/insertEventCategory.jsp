<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/eventService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/blank-check.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/jquery.timepicker.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/jquery.timepicker.css" />

<script type="text/javascript">
function insertEventCategory() {
	var eventName = $("#eventName").val();
	var giftType = $("#sel_giftType option:selected").val();
	
	if (input_blank_check("eventName", "이벤트 이름을 입력하세요.") == false) {
		return;
	}
	if (selectbox_blank_check("sel_giftType", "선물 종류를 선택하세요.") == false) {
		return;
	}
	
	if (confirm("등록하시겠습니까?")) {
		eventService.insertEventCategory(eventName, giftType, function(result) {
			if(result > 0) {
				alert("등록됬습니다.");
				parent.document.location.reload();
			} else {
				alert("오류가 발생되었습니다. 관리자에게 문의히세요.");
				return;
			}
		});
	}
}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body> 
<form id="frm" name="frm" method="post" enctype="multipart/form-data"  class="form-horizontal">
<input type="hidden" name="page_gbn" id="page_gbn">
<div class="title_d1">
		 <h4>이벤트 카테고리 등록</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>이벤트 이름</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input" id="eventName">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>선물 종류</span>
			</label>
			<div class="field">
				<div>
					<select id="sel_giftType" class="select">
						<option value="">▶선물종류선택</option>
						<option value="POPCORN">팝콘</option>
						<option value="ITEM">아이템</option>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:insertEventCategory();" class="button blue-gradient">
			<span class="icon-pencil"></span>등록</a>
		</p>
	</div>
</form>
</body>
</html>
<script>
$("#startTime").timepicker({ 'timeFormat': 'H:i:s' });
$("#endTime").timepicker({ 'timeFormat': 'H:i:s' });
</script>