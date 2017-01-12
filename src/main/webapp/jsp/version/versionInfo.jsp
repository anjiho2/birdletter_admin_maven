<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String device = Util.isNullValue(request.getParameter("device"), "IOS");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<!-- 상단 메뉴 jsp --> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/versionService.js"></script>
<!-- 페이지 이동 관련 스크립트 -->
<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />

<script type="text/javascript">
var device = '<%=device%>';

function init() {
	serverKindsSelectBox("l_server_kinds", "aws");
	versionService.birdLetterVersionInfo(device, "aws", function(info) {
		if (info != null) {
			var checked = "";
			deviceTypeSelectBox("l_selectbox", device);
			$("#idx").val(info.idx);
			$("#l_version_number").html(info.versionNumber);
			
			if (info.serverOnoff == "1") {
				checked = "checked";
			}
			var useYnHTML = "<input type='checkbox' value='0' id='useYn' "+checked+" onchange=\"javascript:update('serverOnoff')\";>"+"<script>$(document).ready(function() {$(':checkbox').iphoneStyle();});<\/script>";
			$("#l_server_onff").html(useYnHTML)
			//$("#l_server_onff").html(info.serverOnoff=="0"?info.serverOnoff="점검중":info.serverOnoff="서비스중");
			
			
			/*
			if (info.serverStatusCode == "1")	serverStatusCode = "정상";
			$("#l_server_status_code").html(serverStatusCode);
			*/
			
			$("#l_version_title").html(info.serverStatusTitle);
			
			if (info.serverStatusContent == null)	info.serverStatusContent = "내용 없음";
			$("#l_server_status_content").html(info.serverStatusContent);
			
			$("#l_create_date").html(getDateTimeSplitComma(info.createDate));
			
			if (info.updateDate == null)	info.updateDate = "최초 버전";
			$("#l_update_date").html(getDateTimeSplitComma(info.updateDate));
			
		}
	});
}

function changeDiv(viewDiv, hideDiv) {
	if (viewDiv != "" && hideDiv != "") {
		$("#"+viewDiv).show();
		$("#"+hideDiv).hide();
		
		if (viewDiv == "version_number_input_div") {
			$("#change_version_input").val($("#l_version_number").html());
		} else if (viewDiv == "server_status_content_input_div") {
			$("#change_status_content_input").val($("#l_server_status_content").html());
		} else if (viewDiv == "version_title_input_div") {
			$("#change_title_input").val($("#l_version_title").html());
		}
	}
}

function update(type) {
	var idx = $("#idx").val();
	var versionNumber = "";
	var serverOnoff = "";
	var serverStatusCode = "";
	var serverStatusContent = "";
	var serverStatusTitle = "";
	
	if (type != "") {
		if (type == "versionName") {
			versionNumber = $("#change_version_input").val();
		} else if (type == "serverOnoff") {
			serverOnoff = $("#useYn").val();
			if ($("#useYn").is(":checked")) {
				serverOnoff = "1";
			}
		} else if (type == "serverStatusContent") {
			serverStatusContent = $("#change_status_content_input").val();
		} else if (type == "serverStatusTitle") {
			serverStatusTitle = $("#change_title_input").val();
		}
	}
	if (serverOnoff == "0") {
		if (confirm("점검으로 바꾸면 모든 서비스를 이용할 수 없게 됩니다.\n점검으로 바꾸시겠습니까?")) {
			versionService.updateBirdLetterVersionInfo(idx, versionNumber, serverOnoff, serverStatusCode, serverStatusContent, serverStatusTitle, "aws", function(result) {
				if (result < 1) {
					alert("수정 실패했습니다.");
				} else {
					alert("수정됬습니다.");
					if (type != "serverOnoff") {
						location.reload(true);
					}
				}
			});
		} else {
			alert("취소됬습니다.");
			$("#useYn").attr("checked",true);
		}
	} else {
		if (confirm("수정하시겠습니까?")) {
			versionService.updateBirdLetterVersionInfo(idx, versionNumber, serverOnoff, serverStatusCode, serverStatusContent, serverStatusTitle, "aws", function(result) {
				if (result < 1) {
					alert("수정 실패했습니다.");
				} else {
					alert("수정됬습니다.");	
					if (type != "serverOnoff") {
						location.reload(true);
					}
				}
			});
		}
	}
}

function goDeviceType() {
	var device = $("#sel_deviceType option:selected").val();
	var serverKind = $("#sel_serverKind option:selected").val();
		
	if (device != "" && serverKind == "aws") {
		$("#device").val(device);
		goVersion('versionInfo');		
	} else if (device != "" && serverKind == "test") {
		$("#device").val(device);
		goVersion('versionInfoTest');
	}
}

function goServerKind() {
	var serverKind = $("#sel_serverKind option:selected").val();
	
	if (serverKind != "") {
		if (serverKind == "aws") {
			goVersion('versionInfo');			
		} else if (serverKind == "test") {
			goVersion('versionInfoTest');
		}
	}
}

$(document).ready(function() {
    $(':checkbox').iphoneStyle();
 });
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="idx" id="idx">
<input type="hidden" name="device" id="device">
<!--  S : leftarea-->
	<div class="leftarea">
		<!--  S : nav_area-->
		<jsp:include page="/daul/include/nav.jsp" flush="false"/>
		<!--  E : nav_area-->
	</div>
	<!-- E : lefrarea -->
	<div class="rightarea span8">
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">버드레터 서비스</span>&nbsp;〉&nbsp;<span onclick="javascript:goVersion('versionInfo');">버전관리 및 서비스상태</span>
				</div>
				<div class="title_d1">
					<h4>버전관리 및 서비스상태</h4>
				</div>
				<div class="formbox">
					<div class="block_label medium">
						<label class="label">
							<span>서버종류</span>
						</label>
						<div class="field">
							<div>
								<span id="l_server_kinds"></span>
							</div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>기기종류</span>
						</label>
						<div class="field">
							<div>
								<span id="l_selectbox"></span>
							</div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>버전</span>
						</label>
						<div class="field">
							<div id="verion_number_div">
								<span id="l_version_number"></span><span style="padding-left: 150px;"></span>
								<input type="button" class="button compact blue-gradient glossy" value="변경" onclick="javascript:changeDiv('version_number_input_div','verion_number_div');">
							</div>
							<div id="version_number_input_div"  style="display: none;">
								<input type="text" class="input width-small" id="change_version_input">
								<a href="javascript:update('versionName');" class="button compact blue-gradient glossy">
									<span class="icon-tools">수정</span>
								</a>
								<a href="javascript:changeDiv('verion_number_div', 'version_number_input_div');" class="button compact red-gradient glossy">
									<span class="icon-cross">취소</span>
								</a>
								<!-- <input type="button" class="button compact blue-gradient glossy" value="수정" onclick="javascript:update('versionName');">
								<input type="button" class="button compact blue-gradient glossy" value="취소" onclick="javascript:changeDiv('verion_number_div', 'version_number_input_div');"> -->
							</div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>서비스 상태</span>
						</label>
						<div class="field">
							<div>
								<span id="l_server_onff"></span>
							</div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>상태 제목</span>
						</label>
						<div class="field">
							<div id="verion_title_div">
								<span id="l_version_title"></span><span style="padding-left: 150px;"></span>
								<input type="button" class="button compact blue-gradient glossy" value="변경" onclick="javascript:changeDiv('version_title_input_div','verion_title_div');">
							</div>
							<div id="version_title_input_div" style="display: none;">
								<input type="text" id="change_title_input" class="input width-small">
								<a href="javascript:update('serverStatusTitle');" class="button compact blue-gradient glossy">
									<span class="icon-tools">수정</span>
								</a>
								<a href="javascript:changeDiv('verion_title_div', 'version_title_input_div');" class="button compact red-gradient glossy">
									<span class="icon-cross">취소</span>
								</a>
								<!-- <input type="button" class="button compact blue-gradient glossy" value="수정" onclick="javascript:update('serverStatusTitle');">
								<input type="button" class="button compact blue-gradient glossy" value="취소" onclick="javascript:changeDiv('verion_title_div', 'version_title_input_div');"> -->
							</div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>상태 내용</span>
						</label>
						<div class="field">
							<div id="server_status_content_div">
								<textarea rows="6" class="input width-small" id="l_server_status_content" disabled="disabled"></textarea><span style="padding-left: 50px;"></span>
								<input type="button" class="button compact blue-gradient glossy" value="변경" onclick="javascript:changeDiv('server_status_content_input_div','server_status_content_div');">
							</div>
							<div id="server_status_content_input_div" class="col-xs-6" style="display: none;">
								<textarea rows="6" class="input width-small" id="change_status_content_input"></textarea>
								<a href="javascript:update('serverStatusContent');" class="button compact blue-gradient glossy">
									<span class="icon-tools">수정</span>
								</a>
								<a href="javascript:changeDiv('server_status_content_div', 'server_status_content_input_div');" class="button compact red-gradient glossy">
									<span class="icon-cross">취소</span>
								</a>
								<!-- <input type="button" class="button compact blue-gradient glossy" value="수정" onclick="javascript:update('serverStatusContent');">
								<input type="button" class="button compact blue-gradient glossy" value="취소" onclick="javascript:changeDiv('server_status_content_div', 'server_status_content_input_div');"> -->
							</div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>최초 작성날짜</span>
						</label>
						<div class="field">
							<div>
								<span id="l_create_date"></span>
							</div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>업데이트 날짜</span>
						</label>
						<div class="field">
							<div>
								<span id="l_update_date"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="/daul/include/footer.jsp" flush="false"/>
</body>
</html>
<script>
	$("#menu_09_01").addClass("active");
</script>	