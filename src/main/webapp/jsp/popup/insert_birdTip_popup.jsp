<%@page import="java.net.URLEncoder"%>
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/noticeService.js"></script>

<!-- 달력 스크립트 -->
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
function init() {
	deviceTypeSelectBox("l_osType", "");
}
//등록버튼
function insert() {
	var firstTooltip = getInputTextValue("firstTooltip");
	var secondTooltip = getInputTextValue("secondTooltip");
	var regularYn = getSelectboxValue("sel_regularYn");
	var startDate = getInputTextValue("startDate");
	var endDate = getInputTextValue("endDate");
	var orderYn = getSelectboxValue("sel_orderYn");
	var orderPriority = getSelectboxValue("sel_orderPriority");
	var viewYn = $("#viewYn").val();
	if ($("#viewYn").is(":checked")) {
		viewYn = "1";
	}
	
	if (input_blank_check("firstTooltip", "메시지_01을 입력하세요.") == false) return;
	if (input_blank_check("secondTooltip", "메시지_02을 입력하세요.") == false) return;
	if (selectbox_blank_check("sel_regularYn", "기간을 선택하세요.") == false) return;
	if (regularYn == 0) {
		if (input_blank_check("startDate", comment.select_start_date) == false) return;
		if (input_blank_check("endDate", comment.select_end_date) == false) return;	
	} else {
		startDate = null;
		endDate = null;
	}
	if (selectbox_blank_check("sel_orderYn", "우선여부를 선택하세요.") == false) return;
	if (orderYn == 1) {
		if (selectbox_blank_check("sel_orderPriority", "우선순서 선택하세요.") == false) return;	
	}
	if (input_blank_check("viewYn", "사용여부를 선택하세요.") == false) return;
	
	if (confirm(comment.isInsert)) {
		noticeService.insertBirdTip(firstTooltip, secondTooltip, regularYn, 
				startDate, endDate, orderYn, orderPriority, viewYn, function(bl) {
			if (bl == false) {
				alert(comment.error);
				return;
			} else {
				isReloadParentPage();
			}
		});
	}
}

$(document).ready(function() {
	$("#sel_orderYn").on("change", function() {
		if ($(this).val() == "1") {
			$("#order_priority_div").show();
		} else {
			$("#order_priority_div").hide();	
		}
	});
	$("#sel_regularYn").on("change", function() {
		if ($(this).val() == "0") {
			$("#date_div").show();
		} else {
			$("#date_div").hide();	
		}
	});
	$(':checkbox').iphoneStyle();
	$("#firstTooltip").on("keyup", function() {
		if ($(this).val().length > 12) {
			alert("12글자까지 입력가능합니다.");	
			$(this).val($(this).val().substring(0, 12));
			return;
		}
	});
	$("#secondTooltip").on("keyup", function() {
		if ($(this).val().length > 12) {
			alert("12글자까지 입력가능합니다.");	
			$(this).val($(this).val().substring(0, 12));
			return;
		}
	});
	$("#sel_orderPriority").on("change", function() {
		notiService.isOrderPriority($(this).val(), function(bl) {
			if (bl == false) {
				alert("이미 등록되어 있는 순서입니다.\n다른 순서를 선택하세요.");
				$("#sel_orderPriority").val("");
				return;
			}
		});
	});
});


</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
	<div class="list_search">
		<div class="f_L">
			<h4>버드 메시지 Tip등록</h4>				
		</div>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>메시지_01</span>
			</label>
			<div class="field">
				<div>
					<div>
						<input type="text" class="input width-large" id="firstTooltip" placeholder="(직접입력)">
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>메시지_02</span>
			</label>
			<div class="field">
				<div>
					<div>
						<input type="text" class="input width-large" id="secondTooltip" placeholder="(직접입력)">
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>기간</span>
			</label>
			<div class="field">
				<div>
					<div>
						<select id="sel_regularYn" class="select">
							<option value="">▶직접/상시선택</option>
							<option value="0">- 직접</option>
							<option value="1">- 상시</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium" id="date_div">
			<label class="label">
				<span>기간 설정</span>
			</label>
			<div class="field">
				<div>
					<div>
						<input type="text" class="input" id="startDate" placeholder="시작일">
						<input type="text" class="input" id="endDate" placeholder="종료일">
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>우선여부</span>
			</label>
			<div class="field">
				<div>
					<div>
						<select id="sel_orderYn" class="select">
							<option value="">▶우선/랜덤선택</option>
							<option value="0">- 랜덤</option>
							<option value="1">- 우선</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium" id="order_priority_div" style="display: none;\">
			<label class="label">
				<span>순서선택</span>
			</label>
			<div class="field">
				<div>
					<div>
						<select id="sel_orderPriority" class="select">
							<option value="">▶우선 순서선택</option>
							<option value="1"> 1</option>
							<option value="2"> 2</option>
							<option value="3"> 3</option>
							<option value="4"> 4</option>
							<option value="5"> 5</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>사용여부</span>
			</label>
			<div class="field">
				<div>
					<div>
						<input type="checkbox" value="0" id="viewYn">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:insert();" class="button blue-gradient"><span class="icon-pencil"></span>등록</a>
		</p>
	</div>
</form>
</body>
</html>

