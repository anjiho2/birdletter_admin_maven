<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String eventIdx = Util.isNullValue(request.getParameter("eventIdx"), "");
%>
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
var eventIdx = '<%=eventIdx%>';

function init() {
	eventService.detailEventInfo(eventIdx, function(eventInfo) {
		if (eventInfo != null) {
			var checked = "";
			$("#eventCode").html(eventInfo.eventCode);
			$("#eventName").val(eventInfo.eventName);
			$("#popcorn").val(eventInfo.popcornPoint);
			$("#corn").val(eventInfo.cornPoint);
			
			var start_date_time = gfn_csplit(eventInfo.startDate, ".");
			var startDateTime = gfn_csplit(start_date_time[0], " ");
			var startDate = startDateTime[0];
			var startTime = startDateTime[1];
			$("#startDate").val(startDate);
			$("#startTime").val(startTime);
			
			var end_date_time = gfn_csplit(eventInfo.endDate, ".");
			var endDateTime = gfn_csplit(end_date_time[0], " ");
			var endDate = endDateTime[0];
			var endTime = endDateTime[1];
			$("#endDate").val(endDate);
			$("#endTime").val(endTime);
			
			if (eventInfo.useYn == "1") {
				checked = "checked";
			}
			var useYnHTML = "<input type='checkbox' value='0' id='useYn' "+checked+">"+"<script>$(document).ready(function() {$(':checkbox').iphoneStyle();});<\/script>";
			$("#l_useYn").html(useYnHTML)
			
		}
	});
}

function updateEvent() {
	var eventName = $("#eventName").val();
	var popcorn = $("#popcorn").val();
	var corn = $("#corn").val();
	var startDate = $("#startDate").val();
	var startTime = $("#startTime").val();
	var startDateTime = startDate + " " + startTime;
	var endDate = $("#endDate").val();
	var endTime = $("#endTime").val();
	var endDateTime = endDate + " " + endTime;
	var useYn = $("#useYn").val();
	if ($("#useYn").is(":checked")) {
		useYn = "1";
	}
		
	if(input_blank_check("eventName", "이벤트 이름을 입력하세요.") == false) {
		return;
	}
	if (input_blank_check("startDate", "시작일 날짜를 선택하세요.") == false) {
		return;
	}
	if (input_blank_check("startTime", "시작일 시간을 선택하세요.") == false) {
		return;
	}
	if (input_blank_check("endDate", "종료일 날짜를 선택하세요.") == false) {
		return;
	}
	if (input_blank_check("endTime", "종료일 시간을 선택하세요.") == false) {
		return;
	}
	if (selectbox_blank_check("sel_useYn", "사용여부를 선택하세요.") == false) {
		return;
	}
	if (confirm("수정하시겠습니까?")) {
		eventService.updateEventInfo(eventIdx, eventName, popcorn, corn, 
				startDateTime, endDateTime, useYn, function(cnt) {
			if(cnt > 0) {
				alert("수정됬습니다.");
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
<body onload="init();"> 
<form id="frm" name="frm" method="post" enctype="multipart/form-data"  class="form-horizontal">
<input type="hidden" name="page_gbn" id="page_gbn">
<div class="title_d1">
		 <h4>기본 이벤트 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>이벤트 코드</span>
			</label>
			<div class="field">
				<div>
					<span id="eventCode"></span>
				</div>
			</div>
		</div>
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
				<span>이벤트 팝콘</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input" id="popcorn">&nbsp;<font color="red">* 비입력시 0으로 셋팅됩니다.</font>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이벤트 콘</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input" id="corn">&nbsp;<font color="red">* 비입력시 0으로 셋팅됩니다.</font>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>시작일</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input" id="startDate" placeholder="날짜">&nbsp;
					<input type="text" class="input" id="startTime" placeholder="시간">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>종료일</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input" id="endDate" placeholder="날짜">&nbsp;
					<input type="text" class="input" id="endTime" placeholder="시간">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>사용여부</span>
			</label>
			<div class="field">
				<div>
					<span id="l_useYn"></span>
				</div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:updateEvent();" class="button blue-gradient">
			<span class="icon-tools"></span>수정</a>
		</p>
	</div>
</form>
</body>
</html>
<script>
$("#startTime").timepicker({ 'timeFormat': 'H:i:s' });
$("#endTime").timepicker({ 'timeFormat': 'H:i:s' });

</script>