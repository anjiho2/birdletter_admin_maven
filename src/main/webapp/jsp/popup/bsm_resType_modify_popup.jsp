<%@page import="java.net.URLEncoder"%>
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String resTypeCode = Util.isNullValue(request.getParameter("resTypeCode"), "");
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
var resTypeCode = "<%=resTypeCode%>";
var awsResourceImageUrl = "<%=awsImageUrl%>" + "<%=awsResourceRoot%>";

function init() {
	modify_modal(resTypeCode);
}
//수정 MODAL 뷰
function modify_modal(resTypeCode) {
	bsmService.defResourceTypeCategoryDetail(resTypeCode, function(list) {
		$("#resTypeCode").val(list.resTypeCode);
		$("#l_korName").val(list.resTypeKorName);
		$("#l_engName").val(list.resTypeEngName);
	});
}

//수정버튼
function modifyDefCategory() {
	var resTypeCode = $("#resTypeCode").val();
	var defKorName = $("#l_korName").val();
	var defEngName = $("#l_engName").val();
	
	if (confirm("수정하시겠습니까?")) {
		bsmService.updateDefResourceTypeCategory(resTypeCode, defKorName, defEngName, function(cnt) {
			if (cnt > 0) {
				alert("수정되었습니다.");
				goBsm("resTypeList");
			} else {
				alert("오류가 발생됬습니다.\n관리자에게 문의하세요.");
				return;
			}
		});
	}
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" id="resTypeCode">
	<div class="title_d1">
		 <h4>이미지타입 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>한글명</span>
			</label>
			<div class="field">
				<div>
					<input type="text" id="l_korName" class="input width-large">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>영문명</span>
			</label>
			<div class="field">
				<div>
					<input type="text" id="l_engName" class="input width-large">
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:modifyDefCategory();" class="button compact blue-gradient glossy">
					<span class="icon-tools">수정</span>
				</a>
			</p>
		</div>
	</div>
</form>
</body>
</html>

