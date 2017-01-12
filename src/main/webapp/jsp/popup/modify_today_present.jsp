<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/heartService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/blank-check.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/jquery.timepicker.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/jquery.timepicker.css" />

<script type="text/javascript">
function init() {
	heartService.todayPresentPopcornInfo(function(info) {
		if (info != null) {
			$("#popcornPresentIdx").val(info.idx);
			$("#minimumPopcorn").val(info.minimumPopcorn);
			$("#maxPopcorn").val(info.maxPopcorn);
			hourSelectbox(info.hourLimit, "l_hour");
			minuteSelectbox(info.minuteLimit, "l_minute");
			$("#heartRewardLimit").val(info.heartRewardLimit);
		}
	});
}

function modify() {
	var popcornPresentIdx = $("#popcornPresentIdx").val();
	var minPopcorn = $("#minimumPopcorn").val();
	var maxPopcorn = $("#maxPopcorn").val();
	var hour = $("#sel_hour option:selected").val();
	var minute = $("#sel_minute option:selected").val();
	var heartRewardPoint = $("#heartRewardLimit").val();
	
	if (input_blank_check("minimumPopcorn", "최저 팝콘 값을 입력하세요.") == false) return;
	if (Number(minPopcorn) < 1) {
		alert("입력값이 0보다 커야 됩니다.");
		$("#minimumPopcorn").focus();	
		return;
	}
	if (input_blank_check("maxPopcorn", "최고 팝콘 값을 입력하세요.") == false) return;
	if (Number(maxPopcorn) < 1) {
		alert("입력값이 0보다 커야 됩니다.");
		$("#maxPopcorn").focus();	
		return;
	}
	if (Number(maxPopcorn) < Number(minPopcorn)) {
		alert("최고 팝콘 값은 최저 팝콘 값보다 커야 됩니다.");
		$("#maxPopcorn").focus();	
		return;
	}
	if (selectbox_blank_check("sel_hour", "시간을 선택하세요.") == false) return;
	if (selectbox_blank_check("sel_minute", "분을 선택하세요.") == false) return;
	if (input_blank_check("heartRewardLimit", "보상 가능 하트를 입력하세요.") == false) return;
	
	if (confirm("수정하시겠습니까?")) {
		heartService.updateTodayPresentPopcornInfo(popcornPresentIdx, minPopcorn, maxPopcorn,
				hour, minute, heartRewardPoint, function(result) {
			if (result == 1) {
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
<form id="frm" name="frm" method="post">
<input type="hidden" name="popcornPresentIdx" id="popcornPresentIdx">
<div class="title_d1">
		 <h4>오늘의 선물 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>최저 팝콘 값</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-small" id="minimumPopcorn" onkeypress="return digit_check(event);">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>최고 팝콘 값</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-small" id="maxPopcorn" onkeypress="return digit_check(event);">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>보상 제한 시간</span>
			</label>
			<div class="field">
				<div>
					<span id="l_hour"></span>
					<span id="l_minute"></span>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>보상 가능 하트</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-small" id="heartRewardLimit" onkeypress="return digit_check(event);">
				</div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:modify();" class="button blue-gradient">
			<span class="icon-tools"></span>수정</a>
		</p>
	</div>
</form>
</body>
</html>