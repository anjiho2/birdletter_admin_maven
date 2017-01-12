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

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
//등록버튼
function insert() {
	if (input_blank_check("ins_title", "제목을 입력하세요.") == false) return;
	if (input_blank_check("ins_content", "URL을 입력하세요.") == false) return;
	if (selectbox_blank_check("sel_imgType", "이미지타입을 선택하세요.") == false) return;
	if (input_blank_check("sel_viewYn", "사용여부를 선택하세요.") == false) return;
	
	jConfirm("입력", comment.isInsert, noticeInsert);
}

function noticeInsert() {
	var data = new FormData();
    $.each($('#attachFile')[0].files, function(i, file) {          
        data.append('file-' + i, file);
    });
    var attachFile = fn_clearFilePath($('#attachFile').val());
   
    //파일이 있을떄만 파일명 작업 진행
    if (attachFile != "") {
	    var makeFile = attachFile.split(".");
	    var finalFile = makeFile[0]+"_birdletter."+makeFile[1];
    }
    
    var title = getInputTextValue("ins_title");
    var content = getInputTextValue("ins_content");
    var imgType = getSelectboxValue("sel_imgType");
    var viewYn = getInputTextValue("useYn");
    if (isCheckedCheckbox("useYn", "ID")) {
    	viewYn = "1";
    }
	//파일명이 없으면 파일명을 공백값으로셋팅
	if (finalFile == undefined) finalFile = "";
	
	if (finalFile != "") {
		$.ajax({
			url: "<%=webRoot%>/file.do",
            method : "post",
            dataType: "JSON",
            data: data,
            cache: false,
            processData: false,
            contentType: false,
            success: function(data) {
            	if (data.result == "korean") {
					jAlert("이미지 파일에 한글이 포함되어있습니다.");
					return;
				}
			}
		});
	}
	noticeService.notiInsert(title, content, finalFile, imgType, viewYn, function(bl) {
		if (bl == true) {
			jAlert_Success("공지사항이 등록됬습니다");
			parent.document.location.reload();
		} else {
			jAlert("공지사항등록에 실패했습니다.\n관리자에게 문의하세요.");
			return;
		}
	});
}

$(document).ready(function() {
    $('#useYn').iphoneStyle();
 });
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" id="modify_noticeId">
	<div class="title_d1">
		 <h4>공지사항 등록</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>제목</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="ins_title">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>URL</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="ins_content">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 타입</span>
			</label>
			<div class="field">
				<div>
					<select id="sel_imgType" class="select">
						<option value="">▶이미지타입선택</option>
						<option value="0">이미지  없음</option>
						<option value="1">640 * 200</option>
						<option value="2">640 * 300</option>
						<option value="3">640 * 400</option>
					</select>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 첨부</span>
			</label>
			<div class="field">
				<div>
					<input type="file" class="input width-xlarge" id="attachFile" name="attachFile" onchange="preViewImage(this, 'modify_preView', 'preview_div');">
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
					<input type="checkbox" value="0" id="useYn">
					<!-- <select id="sel_viewYn" class="select">
						<option value="">▶사용여부선택</option>
						<option value="1">사용함</option>
						<option value="0">사용안함</option>
					</select> -->
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:insert();" class="button compact blue-gradient glossy">
					<span class="icon-pencil">등록</span>
				</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>

