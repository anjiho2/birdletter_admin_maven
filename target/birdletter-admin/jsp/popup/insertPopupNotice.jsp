<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<script type="text/javascript">
function insertPopupNotice() {
	var data = new FormData();
	var type = "insert";
	var title = getInputTextValue("title");
	var content = getInputTextValue("content");
	var url = getInputTextValue("url");
	var useYn = getInputTextValue("useYn");
	if (isCheckedCheckbox("useYn", "ID")) {
		useYn = "1";
	}
	/*
	if ($("#useYn").is(":checked")) {
		useYn = "1";
	}
	*/
	var startDate = getInputTextValue("startDate");
	var endDate = getInputTextValue("endDate");
	var image;

	$.each($("#imgFile")[0].files, function(i, file) {
		data.append("imgFile", file);
		image = file;
	});
	
	if (input_blank_check("url", "url을 입력하세요.") == false) return;
	if (input_blank_check("imgFile", "이미지를 첨부하세요.") == false) return;
	if (input_blank_check("startDate", "시작일을 입력하세요.") == false) return;
	if (input_blank_check("endDate", "종료일을 입력하세요.") == false) return;
	if (input_blank_check("sel_useYn", "사용여부를 선택하세요.") == false) return;
	
	if (confirm("등록하시겠습니까?")) {
		$("#loader").show();
		data.append("type", type);
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
					alert(comment.success_process);
					isReloadParentPage();
				} else {
					if (data.result == "korean") {
						alert(comment.error_image_file_include_korean);
					}
					$("#loader").hide();
					return;
				}
			},
			error : function(request, status, error) {
				alert(comment.error);
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
<div class="title_d1">
		 <h4>팝업 공지사항 등록</h4>
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
					<input type="checkbox" value="0" id="useYn">
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:insertPopupNotice();" class="button blue-gradient">
				<span class="icon-pencil"></span>등록</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>