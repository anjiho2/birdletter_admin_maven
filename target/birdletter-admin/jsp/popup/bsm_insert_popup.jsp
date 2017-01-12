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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/bsmService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>


<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
function init() {
	bsmService.defResourceTypeCategoryList(function(selList) {
		if (selList.length > 0) {
			var selectHTML = "<select id='select' class='select'>";
			selectHTML+= "<option value=''>목록선택</option>";
			for (var i = 0; i < selList.length; i++) {
				cmpList = selList[i];
				if (cmpList != undefined) {
					selectHTML+= "<option value='"+cmpList.resTypeCode+"'>"+cmpList.resTypeKorName+"</option>";
				}
			}
			selectHTML+= "</select>";
			$("#l_select").html(selectHTML);
		}
	});
	bsmUseYnSelectBox("l_useyn");
}

function insert() {
	var resType = $("#sel_resType option:selected").val();
	var useYn = $("#sel_use option:selected").val();
	var data = new FormData();
	
	if (resType == "") {
		alert("이미지 타입을 선택하세요.");
		$("#sel_resType").focus();
		return;
	}
	if ($("#gif_File").val() == "") {
		alert("gif파일을 등록하세요.");
		$("#gif_File").focus();
		return;
	}
	if ($("#thumbNail_File").val() == "") {
		alert("썸네일파일을 등록하세요.");
		$("#thumbNail_File").focus();
		return;
	}
	if (useYn == "") {
		alert("사용여부를 선택하세요.");
		$("#sel_use").focus();
		return;
	}
	
	data.append("resType", resType);
	data.append("useYn", useYn);
	//GIF
	$.each($("#gif_File")[0].files, function(i, file) {
		data.append('gif-file', file);
	});
	//썸네일
	$.each($("#thumbNail_File")[0].files, function(i, file) {
		data.append('thumbNail-file', file);
	});
	
	if (confirm("등록하시겠습니까?")) {
		$("#loader").show();
		$.ajax({
			url : "<%=webRoot%>/bsmUpload.do",
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
					alert("BSM파일이 등록됬습니다.");
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
		 <h4>BSM 등록</h4>
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
				<a href="javascript:insert();" class="button compact blue-gradient glossy">
					<span class="icon-pencil">등록</span>
				</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>

