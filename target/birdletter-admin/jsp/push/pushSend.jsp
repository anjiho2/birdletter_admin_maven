<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<link rel="stylesheet" href="//cdn.rawgit.com/fgelinas/timepicker/master/jquery.ui.timepicker.css">

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/cronManager.js"></script>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/pushService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script type="text/javascript">

function init() {
	daySelecbox(1, "sel_retension", 180);
	if (isCheckedCheckbox("regCheck", "ID")) {
		jAlert("즉시발송시 개발자와 협의하세요.", "sel_retenseion");
		$("#div_retension").hide();
		$("#currentSendBtn").show();
	} else {
		$("#div_retension").show();
		$("#currentSendBtn").hide();
	}
}

function send(sendType) {
	var pushTitle = getInputTextValue("push_title");
	var pushContent = "";
	var sendDate = getInputTextValue("searchDate");
	var time = getInputTextValue("time");
	var retension = getSelectboxValue("sel_retension");
	var sendFunc = "";
	var confirmMent = ""
	
	if (input_blank_check("push_title", "제목을 입력하세요.") == false) return;
	if (input_blank_check("searchDate", "발송일을 선택하세요.") == false) return;
	if (input_blank_check("time", "발송시간을 선택하세요.") == false) return;
	
	if (sendType == "instant") {
		confirmMent = "즉시 보내시겠습니까?";
	} else {
		confirmMent = "예약하시겠습니까?"
	}
	
	var content = "알림 제목 : " + pushTitle;
	content += "\n발송일 : " + sendDate + "일 " + time + "분";
	if (sendType == "reserve") {
		content += "\nN일 리텐션 : " + retension + "일";	
	}
	content += "\n내용이 맞습니까?";
	
	if (confirm(content)) {
		if (sendType == "instant") {
			jConfirm("확인", confirmMent, instantSend);
		} else {
			jConfirm("확인", confirmMent, reserveSend);
		}
	}
}

function instantSend() {
	var pushTitle = getInputTextValue("push_title");
	var pushContent = "";
	var sendDate = getInputTextValue("searchDate");
	var time = getInputTextValue("time");
	var retension = getSelectboxValue("sel_retension");
	
	cronManager.pushSendAllUsers(pushTitle, pushContent, sendDate, time);
	jAlert_Success("즉시발송 됬습니다.");
}

function reserveSend() {
	var pushTitle = getInputTextValue("push_title");
	var pushContent = "";
	var sendDate = getInputTextValue("searchDate");
	var time = getInputTextValue("time");
	var retension = getSelectboxValue("sel_retension");

	pushService.reservePush("단체 알림", pushTitle, sendDate, time, retension, function(result) {
		if (result == 1) {
			jAlert_Success("예약 됬습니다.");
		}
	}); 
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form class="form-horizontal" id="frm"  name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="pushIdx" id="pushIdx">
<!--  S : leftarea-->
	<div class="leftarea">
		<!--  S : nav_area-->
		<jsp:include page="/daul/include/nav.jsp" flush="false"/>
		<!--  E : nav_area-->
	</div>
	<!-- E : lefrarea -->
	<!--  S: right area-->
	<div class="rightarea span" >
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">알림</span>&nbsp;〉&nbsp;<span onclick="javascript:goPush('pushList');">푸시 알림 발송</span> 
				</div>
			<div class="span5">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>단체 알림 발송하기</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="formbox">
									<div class="block_label medium">
											<label class="label">
												<span>즉시 발송 여부</span>
											</label>
											<div class="field">
												<div>
													<label for="checkbox-2" class="checkbox">
														<input type="checkbox" name="regCheck" id="regCheck" value="reg" onchange="javascript:init();">
													</label>
												</div>
											</div>
										</div>
										<div class="block_label medium">
											<label class="label">
												<span>알림 제목</span>
											</label>
											<div class="field">
												<div>
													<input class="input width-xlarge" type="text" id="push_title" onkeyup="javascript:gfn_chkStrLength('push_title',30);">
												</div>
											</div>
										</div>
										<div class="block_label medium">
											<label class="label">
												<span>발송일</span>
											</label>
											<div class="field">
												<div>
													<input type="text" class="input" id="searchDate" placeholder="발송일">
													<input type="text" class="input" id="time" placeholder="발송시간">
													<a href="javascript:send('instant');" class="button compact blue-gradient glossy" id="currentSendBtn" style="display: none;">
														<span class="icon-mail">즉시발송</span>
													</a>
												</div>
											</div>
										</div>
										<div class="block_label medium" id="div_retension">
											<label class="label">
												<span>N일 리텐션</span>
											</label>
											<div class="field">
												<div>
													<span id="sel_retension"></span>
													<a href="javascript:send('reserve');" class="button compact blue-gradient glossy">
														<span class="icon-mail">예약발송</span>
													</a>	
												</div>
											</div>
										</div>
									</div>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<!--  E: contents-->
	</div>
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->
</form>
</body>
</html>
<script>
$("#menu_07_03").addClass("active");
</script>