<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/eventService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/jquery.timepicker.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>
 
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/jquery.timepicker.css" />

<script type="text/javascript">
function insertEvent() {
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
	if (confirm("등록하시겠습니까?")) {
		eventService.insertEventInfo(eventName, popcorn, corn, startDateTime, 
				endDateTime, useYn, function(cnt) {
			if(cnt > 0) {
				alert("등록됬습니다.");
				parent.document.location.reload();
			} else {
				alert("오류가 발생되었습니다. 관리자에게 문의히세요.");
				return;
			}
		});
	}
}

$(document).ready(function() {
    $(':checkbox').iphoneStyle();
 });
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body> 
<form id="frm" name="frm" method="post" enctype="multipart/form-data"  class="form-horizontal">
<input type="hidden" name="page_gbn" id="page_gbn">
<!--  S : leftarea-->
	<div class="leftarea">
		<!--  S : nav_area-->
		<jsp:include page="/daul/include/nav.jsp" flush="false"/>
		<!--  E : nav_area-->
	</div>
<!-- E : lefrarea -->
<!--  S: right area-->
	<div class="rightarea span10" >
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">이벤트</span>&nbsp;〉&nbsp;<span onclick="javascript:goPage('event', 'basicEventList');">기본 이벤트</span> 
				</div>
			<div class="span10">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>기본 이벤트 리스트</span>
							</div>
							<div class="portlet-content" >
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
												<input type="text" class="input" id="startDate" placeholder="시작 날짜">&nbsp;
												<input type="text" class="input" id="startTime" placeholder="시작 시간">				
											</div>
										</div>
									</div>
									<div class="block_label medium">
										<label class="label">
											<span>종료일</span>
										</label>
										<div class="field">
											<div>
												<input type="text" class="input" id="endDate" placeholder="종료 날짜">&nbsp;
												<input type="text" class="input" id="endTime" placeholder="종료 시간">				
											</div>
										</div>
									</div>
									<div class="block_label medium">
										<label class="label">
											<span>사용여부</span>
										</label>
										<div class="field">
											<div>
												<input type="checkbox" value="0" id="useYn">
											</div>
										</div>
									</div>
								</div>
								<div class="group list_bottom">
									<p class="f_L">
										<a href="javascript:insertEvent();" class="button blue-gradient">
										<span class="icon-pencil"></span>등록</a>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<!--  E: contents-->
	</div>
	
<!-- 	
<div class="title_d1">
		 <h4>기본 이벤트 등록</h4>
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
					<input type="text" class="input" id="startDate" placeholder="시작 날짜">&nbsp;
					<input type="text" class="input" id="startTime" placeholder="시작 시간">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>종료일</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input" id="endDate" placeholder="종료 날짜">&nbsp;
					<input type="text" class="input" id="endTime" placeholder="종료 시간">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>사용여부</span>
			</label>
			<div class="field">
				<div>
					<input type="checkbox" value="0" id="useYn">
				</div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:insertEvent();" class="button blue-gradient">
			<span class="icon-pencil"></span>등록</a>
		</p>
	</div> -->
</form>
</body>
</html>
<script>
$("#startTime").timepicker({ 'timeFormat': 'H:i:s' });
$("#endTime").timepicker({ 'timeFormat': 'H:i:s' });
</script>