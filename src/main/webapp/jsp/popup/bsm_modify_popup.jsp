<%@page import="java.net.URLEncoder"%>
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String resourceCode = Util.isNullValue(request.getParameter("resourceCode"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/bsmService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>


<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
var resourceCode = "<%=resourceCode%>";
var awsResourceImageUrl = "<%=awsImageUrl%>" + "<%=awsResourceRoot%>";

//수정페이지 내용
function init() {
	bsmService.defBmsResoueceDetail(resourceCode, function(list) {
		if (list != null) {
			var resType = list.resType;
			bsmService.defResourceTypeCategoryList(function(selList) {
				if (selList.length > 0) {
					/** 이미지타입 셀렉트박스 **/
					resTypeSelectBox("l_select", selList, resType);
					if (list.fileName != "") {
						$("#gifPreView_div").show();
					 	$("#gifPreView").attr("src", awsResourceImageUrl+list.fileName);
					 	$("#delete_gif_file").val(list.fileName);
					}
					if (list.thumbFileName != "") {
						$("#thumbPreView_div").show();
						$("#thumbPreView").attr("src", awsResourceImageUrl+list.thumbFileName);
						$("#delete_thumb_file").val(list.thumbFileName);
					}	
				}
			});
			bsmUseYnSelectBox("l_useyn", list.useYn);
		}
	});
}

//수정버튼
function modify() {
	var resType = $("#sel_resType option:selected").val();
	var useYn = $("#sel_use option:selected").val();
	var gifFile = $("#gif_F,ile").val();
	var thumbFile = $("#thumbNail_File").val();
	var deleteGifFile = $("#delete_gif_file").val();
	var deleteThumbFile = $("#delete_thumb_file").val();
	var data = new FormData();
	
	if (resType == "") {
		alert("이미지 타입을 선택하세요.");
		$("#sel_resType").focus();
		return;
	}
	
	data.append("resourceCode", resourceCode);	
	data.append("resType", resType);
	data.append("useYn", useYn);
	data.append("deleteGifFileName", deleteGifFile);
	data.append("deleteThumbFileName", deleteThumbFile);
	//GIF
	$.each($("#gif_File")[0].files, function(i, file) {
		data.append('gif-file', file);
	});
	//썸네일
	$.each($("#thumbNail_File")[0].files, function(i, file) {
		data.append('thumbNail-file', file);
	});
	if (confirm("수정하시겠습니까?")) {
		$("#loader").show();
		$.ajax({
			url : "<%=webRoot%>/bsmModify.do",
			//url : "<%=webRoot%>/s3upload.do",
			method : "post",
			dataType : "json",
			data : data,
			cache : false,
			processData : false,
			contentType : false, 
			success : function(data) {
				if(data.result == "200") {
					$("#loader").hide();
					alert("수정되었습니다.");
					goBsm("bsmList");
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
<form name="frm" method="post">
<input type="hidden" id="modify_noticeId">
	<div class="title_d1">
		 <h4>BSM 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>이미지 타입</span>
			</label>
			<div class="field">
				<div>
					<span id="l_select"></span>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>GIF</span>
			</label>
			<div class="field">
				<div>
					<input type="file" class="input width-xlarge" name="gif_File" id="gif_File" data-buttonText="파일선택" onchange="preViewImage(this, 'gifPreView', 'gifPreView_div')">
				</div>
			</div>
		</div>
		<div class="block_label medium" id="gifPreView_div" style="display: none;">
			<label class="label">
				<span>GIF 이미지</span>
			</label>
			<div class="field">
				<div>
					<img alt="" src="" id="gifPreView" width="100%" height="200px">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>썸네일</span>
			</label>
			<div class="field">
				<div>
				<input type="file" class="input width-xlarge" id="thumbNail_File" data-buttonText="파일선택" onchange="preViewImage(this, 'thumbPreView', 'thumbPreView_div')">
				</div>
			</div>
		</div>
		<div class="block_label medium" id="thumbPreView_div" style="display: none;">
			<label class="label">
				<span>썸네일 이미지</span>
			</label>
			<div class="field">
				<div>
					<img alt="" src="" id="thumbPreView" width="100%" height="200px">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>사용여부</span>
			</label>
			<div class="field">
				<div>
					<span id="l_useyn"></span>
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:modify();" class="button compact blue-gradient glossy">
					<span class="icon-tools">수정</span>
				</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>

