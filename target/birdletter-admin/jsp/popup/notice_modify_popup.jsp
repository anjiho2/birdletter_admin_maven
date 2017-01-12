<%@page import="java.net.URLEncoder"%>
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String noticeIdx = Util.isNullValue(request.getParameter("noticeIdx"), "");
	//String collectionName = URLEncoder.encode(Util.isNullValue(request.getParameter("collectionName"), ""), "UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/noticeService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
var noticeIdx = '<%=noticeIdx%>';

function init() {
	show_modal('modify', noticeIdx);
}

function show_modal(type, val) {
	var checked = "";
	var awsResourceImageUrl = "<%=awsImageUrl%>" + "<%=awsNoticeRoot%>";
	if (type == "insert") {
		$("#noticeInsert-modal").modal('show');
	} else if (type = "modify") {
		var noticeIdx = val;
		var imgType = "";
		$("#modify_noticeId").val(noticeIdx);
		
		noticeService.noticeDetail(noticeIdx, function(noticeInfo) {
			if (noticeInfo.fileName == "")	$("#preview_div2").hide();
			
			if (noticeInfo.imgHeight == "0") imgType = "0";
			else if (noticeInfo.imgHeight == "200") imgType = "1";
			else if (noticeInfo.imgHeight == "300") imgType = "2";
			else if (noticeInfo.imgHeight == "400") imgType = "3";
			else imgType = "0";
			
			var webHost = window.location.hostname;
			var webPort = window.location.port;
			if (webPort != "")	webRoot = webHost+":"+webPort;
			else webRoot = webHost;
			
			imgTypeSelectBox("l_sel_imgType", imgType);
			
			$("#modify_title").val(noticeInfo.title);
			$("#modify_content").val(noticeInfo.content);
			
			if (noticeInfo.fileName != "") {
				$("#preview_div").show();
				$("#modify_preView").attr("src", awsResourceImageUrl+noticeInfo.fileName);
			}
			
			if (noticeInfo.viewYn == "1") {
				checked = "checked";
			}
			var useYnHTML = "<input type='checkbox' value='0' id='useYn' "+checked+">"+"<script>$(document).ready(function() {$(':checkbox').iphoneStyle();});<\/script>";
			$("#l_viewYn").html(useYnHTML)
			
			//noticeViewYnSelectBox(noticeInfo.viewYn, 'l_viewYn');
		});
	}
}

//수정버튼
function modify() {
	var data = new FormData();
    $.each($('#modify_attachFile')[0].files, function(i, file) {          
        data.append('file-' + i, file);
    });
    var attachFile = fn_clearFilePath($('#modify_attachFile').val());
    //파일이 있을떄만 파일명 작업
    if (attachFile != "") {
	    var makeFile = attachFile.split(".");
	    var finalFile = makeFile[0]+"_birdletter."+makeFile[1];
    }
	
	var noticeIdx = $("#modify_noticeId").val();
	var title = $("#modify_title").val();
	var content = $("#modify_content").val();
	var imgType = $("#modify_sel_imgType option:selected").val();
	//var viewYn = $("#select_viewYn option:selected").val();
	var viewYn = $("#useYn").val();
	if ($("#useYn").is(":checked")) {
		viewYn = "1";
	}
	//파일명이 없으면 파일명을 공백값으로셋팅
	if (finalFile == undefined)	finalFile = "";
	
	var imgWidth = "0";
	var imgHeight = "0";
	
	if (title == "") {
		alert("제목을 입력하세요.");
		$("#modify_title").focus();
		return;
	}
	if (content == "") {
		alert("URL을 입력하세요.");
		$("#modify_content").focus();
		return;
	}
	if (imgType == "") {
		alert("이미지타입을 선택하세요.");
		$("#modify_sel_imgType").focus();
		return;
	} else {
		if (imgType == "1") {
			imgWidth = "640";
			imgHeight = "200";
		} else if (imgType == "2") {
			imgWidth = "640";
			imgHeight = "300";
		} else if (imgType == "3") {
			imgWidth = "640";
			imgHeight = "400";
		}
	}
	if (viewYn == "") {
		alert("사용여부를 선택하세요.");
		$("#select_viewYn").focus();
		return;
	}
	var dataObj = {idx:noticeIdx, title:title, content:content, viewYn:viewYn, imgWidth:imgWidth, imgHeight:imgHeight, fileName:finalFile};
	
	if (confirm("수정하시겠습니까?")) {
		if (finalFile != "") {
			$.ajax({
				url: '<%=webRoot%>/file.do',
	            method : "post",
	            dataType: "JSON",
	            data: data,
	            cache: false,
	            processData: false,
	            contentType: false,
	            success: function(data) {
				}
			});
		}
		noticeService.noticeModify(dataObj, function(bl) {
			if (bl == true) {
				alert("수정되었습니다.");
				isReloadParentPage();
			} else {
				alert("수정실패했습니다.\n관리자에게 문의하세요.");
				return;
			}
		});
	}
}

//이미지삭제 버튼
function removeImage() {
	var noticeId = $("#modify_noticeId").val();
	
	if (confirm("등록된 이미지를 삭제하시겠습니까?")) {
		noticeService.noticeImageRemove(noticeId, function(bl) {
			if (bl == true) {
				alert("이미지가 삭제되었습니다.");
				isReloadParentPage();
			} else {
				alert("이미지삭제 실패했습니다\n관리자에게 문의하세요.");
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
	<div class="title_d1">
		 <h4>공지사항 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>제목</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="modify_title">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>URL</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="modify_content">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 타입</span>
			</label>
			<div class="field">
				<div>
					<span id="l_sel_imgType"></span>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 첨부</span>
			</label>
			<div class="field">
				<div>
					<input type="file" class="input width-xlarge" id="modify_attachFile" name="modify_attachFile" onchange="preViewImage(this, 'modify_preView', 'preview_div');">&nbsp;&nbsp;&nbsp;
					<a href="javascript:removeImage();" class="button compact red-gradient">
						<span class="icon-trash">이미지삭제</span>
					</a>
					<!-- <input type="button" class="" value="이미지삭제" onclick="javascript:removeImage();"> -->
				</div>
			</div>
		</div>
		<div class="block_label medium" id="preview_div" style="display: none;">
			<label class="label">
				<span>이미지</span>
			</label>
			<div class="field">
				<div>
					<img alt="" src="" id="modify_preView" width="100%" height="200">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>사용여부</span>
			</label>
			<div class="field">
				<div>
					<span id="l_viewYn"></span>
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:modify();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>

