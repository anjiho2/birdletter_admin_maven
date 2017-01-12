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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/statisticsService.js"></script>

<!-- 달력 스크립트 -->
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
function init() {
	deviceTypeSelectBox("l_osType", "");
}
//등록버튼
function insert() {
	var device = getSelectboxValue("sel_deviceType");
	var createDate = getInputTextValue("searchDate");
	var downloadCount = getInputTextValue("l_downloadCount");
	
	if (selectbox_blank_check("sel_deviceType", "OS를 선택하세요.") == false) return;
	if (input_blank_check("searchDate", "날짜를 입력하세요.") == false) return;
	if (input_blank_check("l_downloadCount", "다운로드 수를 입력하세요.") == false) return;
	
	if (confirm(comment.isInsert)) {
		statisticsService.isDownloadCount(device, createDate, function(bl) {
			if (bl == false) {
				alert("이미 등록되어 있는 날짜 입니다.");
				return;
			} else {
				statisticsService.upsultDownloadCount(device, createDate, downloadCount, "INSERT", function(result) {
					if (result < 1) {
						alert(comment.error);
					} else {
						isReloadParentPage();
					}
				});
			}
		});
		
	}
}

function update() {
	var device = getSelectboxValue("sel_deviceType");
	var createDate = getInputTextValue("searchDate");
	var downloadCount = getInputTextValue("l_downloadCount");
	
	if (selectbox_blank_check("sel_deviceType", "OS를 선택하세요.") == false) return;
	if (input_blank_check("searchDate", "날짜를 입력하세요.") == false) return;
	if (input_blank_check("l_downloadCount", "다운로드 수를 입력하세요.") == false) return;
	
	if (confirm(comment.isUpdate)) {
		statisticsService.upsultDownloadCount(device, createDate, downloadCount, "UPDATE", function(result) {
			if (result < 1) {
				alert(comment.error);
			} else {
				isReloadParentPage();
			}
		});
	}
}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" id="modify_noticeId">
	<div class="list_search">
		<div class="f_L">
			<h4>다운로드수 등록</h4>				
		</div>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>OS</span>
			</label>
			<div class="field">
				<div>
					<div>
						<span id="l_osType"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>날짜</span>
			</label>
			<div class="field">
				<div>
					<div>
						<input type="text" class="input" id="searchDate" placeholder="등록일">
					</div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>다운로드 수</span>
			</label>
			<div class="field">
				<div>
					<div><input type="text" class="input width-small" id="l_downloadCount"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:insert();" class="button blue-gradient"><span class="icon-pencil"></span>등록</a>
			<a href="javascript:update();" class="button green-gradient"><span class="icon-tools"></span>수정</a>
		</p>
	</div>
</form>
</body>
</html>

