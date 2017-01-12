<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String idx = request.getParameter(Util.isNullValue("idx", ""));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/noticeService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />

<script type="text/javascript">
var idx = '<%=idx%>';
var awsResourceImageUrl = "<%=awsImageUrl%>" + "<%=awsNoticeRoot%>";

function init() {
	if (idx > 0) {
		noticeService.detailPopupNotice(idx, function(popupNotice) {
			if (popupNotice != null) {
				var checked = "";
				var startDate = popupNotice.startDate.split(" ");
				var endDate = popupNotice.endDate.split(" ");
				
				$("#title").val(popupNotice.title);
				$("#content").val(popupNotice.content);
				$("#url").val(popupNotice.targetUrl);
				$("#startDate").val(startDate[0]);
				$("#endDate").val(endDate[0]);
				//bsmUseYnSelectBox("l_selectbox", popupNotice.useYn);
			
				if (popupNotice.imgFileName != "") {
					$("#imgPreview_tr").show();
					$("#imgPreview").attr("src", awsResourceImageUrl+popupNotice.imgFileName);
				}
				
				if (popupNotice.useYn == "1") {
					checked = "checked";
				}
				var useYnHTML = "<input type='checkbox' value='0' id='useYn' "+checked+">"+"<script>$(document).ready(function() {$(':checkbox').iphoneStyle();});<\/script>";
				$("#l_useYn").html(useYnHTML)
			}
		});
	}
}

function modifyPopupNotice() {
	var data = new FormData();
	var type = "modify";
	var title = $("#title").val();
	var content = $("#content").val();
	var url = $("#url").val();
	var useYn = $("#useYn").val();
	if ($("#useYn").is(":checked")) {
		useYn = "1";
	}
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var image;
	
	$.each($("#imgFile")[0].files, function(i, file) {
		data.append("imgFile", file);
		image = file;
	}) ;
	
	/*
	if (title == "") {
		alert("제목을 입력하세요.");
		$("#title").focus();
		return;
	}
	if (content == "" && image == undefined) {
		alert("내용과 이미지가 없습니다.\n둘중 하나의 항목은 입력 되어야합니다.");
		return;	
	}
	*/
	if (url == "") {
		alert("제목을 입력하세요.");
		$("#url").focus();
		return;
	}
	if (useYn == "") {
		alert("사용여부를 선택하세요.");
		$("#sel_useYn").focus();
		return;	
	}
	if (startDate == "") {
		alert("시작일을 입력하세요.");
		$("#startDate").focus();
		return;		
	}
	if (endDate == "") {
		alert("종료일을 입력하세요.");
		$("#endDate").focus();
		return;		
	}
	
	if (confirm("수정하시겠습니까?")) {
		data.append("type", type);
		data.append("idx", idx);
		data.append("title", title);
		data.append("content", content);
		data.append("url", url);
		data.append("useYn", useYn);
		data.append("startDate", startDate);
		data.append("endDate", endDate);
		
		$.ajax({
			url : "<%=webRoot%>/insertPopupNotice.do",
			method : "post",
			dataType : "json",
			data : data,
			cache : false,
			processData : false,
			contentType : false, 
			success : function(data) {
				if(data.result == "1") {
					alert("팝업공지가 수정됬습니다.");
					parent.document.location.reload();
				} else {
					alert("오류가 발생됬습니다.\n관리자에게 문의하세요.");
				}
			},
			error : function(data) {
				$("#loader").hide();
				alert("오류가 발생됬습니다.\n관리자에게 문의하세요.");
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
		 <h4>팝업 공지사항 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>URL</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="url">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 첨부</span>
			</label>
			<div class="field">
				<div>
					<input type="file" class="input width-xlarge" name="imgFile" id="imgFile" data-buttonText="파일선택" onchange="preViewImage(this, 'imgPreview', 'imgPreview_tr')">
				</div>
			</div>
		</div>
		<div class="block_label medium" id="imgPreview_tr" style="display: none;">
			<label class="label">
				<span>이미지</span>
			</label>
			<div class="field">
				<div>
					<img alt="" src="" id="imgPreview" width="100%" height="400px">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>시작일</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="startDate" placeholder="시작일">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>종료일</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="endDate" placeholder="종료일">				
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
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:modifyPopupNotice();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>