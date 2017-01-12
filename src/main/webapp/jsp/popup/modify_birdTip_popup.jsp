<%@page import="java.net.URLEncoder"%>
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String tipIdx = Util.isNullValue(request.getParameter("tipIdx"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/noticeService.js"></script>

<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
var tipIdx = '<%=tipIdx%>';

function init() {
	show_modal();
}

function show_modal() {
	var checked = "";
	noticeService.detailBirdToolTip(tipIdx, function(info) {
		if (info != null) {
			var start_date = new Array();
			if (info.startDate != null) {
				start_date = gfn_split(info.startDate, " ");
			}
			var end_date = new Array();
			if (info.endDate != null) {
				end_date = gfn_split(info.endDate, " ");
			}
			innerValue("firstTooltip", info.firstTooltip);
			innerValue("secondTooltip", info.secondTooltip);
			regularSelectbox(info.regularYn, "l_regularYn", "regularChange();");
			
			if (info.regularYn == "1") {
				$("#date_div").hide();
			} else {
				$("#date_div").show();
			}
			orderSelectbox(info.orderYn, "l_orderYn", "orderChange();");
			innerValue("startDate", start_date[0]);
			innerValue("endDate", end_date[0]);
			orderPrioritySelectbox(info.orderPriority, "l_orderPriority", "orderPriorityChange("+info.orderPriority+")" );
			if (info.orderYn == "1") $("#order_priority_div").show();
			
			if (info.viewYn == "1") {
				checked = "checked";
			}
			var viewYnHTML = "<input type='checkbox' value='0' id='viewYn' "+checked+">"+"<script>$(document).ready(function() {$(':checkbox').iphoneStyle();});<\/script>";
			$("#l_viewYn").html(viewYnHTML)
		}
	});
}

function update() {
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
	
	if (confirm(comment.isUpdate)) {
		noticeService.updateBirdToolTip(tipIdx, firstTooltip, secondTooltip, regularYn, 
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

function regularChange() {
	var regularYn = getSelectboxValue("sel_regularYn");
	if (regularYn == "0") {
		$("#date_div").show();
	} else {
		$("#date_div").hide();	
	}
}

function orderChange() {
	var orderYn = getSelectboxValue("sel_orderYn");
	if (orderYn == "1") {
		$("#order_priority_div").show();
	} else {
		$("#order_priority_div").hide();	
	}
}

function orderPriorityChange(val) {
	var orderNum = getSelectboxValue("sel_orderPriority");
	var orderPriority = val;
	if (val == 0) orderPriority = "";
	noticeService.isOrderPriority(orderNum, function(bl) {
		if (bl == false) {
			alert("이미 등록되어 있는 순서입니다.\n다른 순서를 선택하세요.");
			$("#sel_orderPriority").val(orderPriority);
			return;
		}
	});
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
	<div class="list_search">
		<div class="f_L">
			<h4>버드 메시지 Tip수정</h4>				
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
						<span id="l_regularYn"></span>
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
						<span id="l_orderYn"></span>
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
						<span id="l_orderPriority"></span>
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
						<span id="l_viewYn"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:update();" class="button blue-gradient"><span class="icon-tools"></span>수정</a>
		</p>
	</div>
</form>
</body>
</html>

