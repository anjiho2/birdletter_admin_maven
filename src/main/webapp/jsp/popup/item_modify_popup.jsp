<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String itemIdx = Util.isNullValue(request.getParameter("itemIdx"), "");
	//String collectionName = URLEncoder.encode(Util.isNullValue(request.getParameter("collectionName"), ""), "UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>


<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
var itemIdx = '<%=itemIdx%>';

function init() {
	show_modal(itemIdx);
}

function show_modal(val) {
		itemService.getItemInfoDetail(itemIdx, function(itemInfo) {
			$("#itemCode").html(itemInfo.itemCode);
			$("#itemName").val(itemInfo.itemName);
			$("#itemDesc").val(itemInfo.itemDesc);
	});
}


//수정버튼
function modify() {
	var itemName = $("#itemName").val();
	var itemDesc = $("#itemDesc").val();
	
	if (itemName == "") {
		alert("아이템 이름을 입력하세요.");
		$("#itemName").focus();
		return;
	}
	if (itemDesc == "") {
		alert("아이템 설명을 입력하세요.");
		$("#itemDesc").focus();
		return;
	}
	
	if (confirm("수정하시겠습니까?")) {
		itemService.modifyItemInfo(itemIdx, itemName, itemDesc, function(result) {
			if (result > 0) {
				alert("수정됬습니다.");
				parent.document.location.reload();
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
		notiService.noticeImageRemove(noticeId, function(bl) {
			if (bl == true) {
				alert("이미지가 삭제되었습니다.");
				$("#preview_div2").hide();
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
		 <h4>아이템 수정</h4>
	</div>
	<div class="formbox">
	<div class="block_label medium">
			<label class="label">
				<span>아이템 코드</span>
			</label>
			<div class="field">
				<div>
					<span id="itemCode"></span>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>아이템 이름</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="itemName">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>아이템 설명</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="itemDesc">
				</div>
			</div>
		</div>
		<!-- 
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
					<input type="button" class="" value="이미지삭제" onclick="javascript:removeImage();">
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
		 -->
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:modify();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>

