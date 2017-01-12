<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String cornIdx = Util.isNullValue(request.getParameter("cornIdx"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<%-- <jsp:include page="/daul/include/header.jsp" flush="false"/> --%>
<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>


<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
var cornIdx = '<%=cornIdx%>';

function init() {
	modify_modal(cornIdx);
}

//수정페이지show
function modify_modal(cornIdx) {
	$("#l_cornIdx").val(cornIdx);
	itemService.cornInfoDetail(cornIdx, function(cornInfo) {
		$("#modal_cornDesc").val(cornInfo.cornDesc);
		$("#modal_cornCount").val(cornInfo.cornCount);
		discountSelectBoxByOne(cornInfo.discountPercent, 'modal_discountPercent');
	});
}
//수정페이지 수정버튼
function modify() {
	var cornIdx = getInputTextValue("l_cornIdx");
	var cornDesc = getInputTextValue("modal_cornDesc");
	var cornCount = getInputTextValue("modal_cornCount");
	var discountPercent = getSelectboxValue("sel_discount");
	
	if (input_blank_check("modal_cornDesc", "콘이름을 입력하세요.") == false) return;
	if (input_blank_check("modal_cornCount", "콘가격을 입력하세요.") == false) return;
	if (input_blank_check("sel_discount", "할인률을 선택하세요.") == false) return;
	
	var dataObj = {cornIdx:cornIdx, cornDesc:cornDesc, cornCount:cornCount, discountPercent:discountPercent};
	itemService.cornInfoModify(dataObj, function(bl) {
		if (bl == true) {
			alert("수정됬습니다.");
			parent.document.location.reload();
		} else {
			alert("수정 실패하였습니다. 관리자에게 문의하세요.");
			return;
		}
	});
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="store_type" id="store_type">
<input type="hidden" id="l_cornIdx" name="l_cornIdx">
<input type="hidden" id="modal_cornDesc" value="">
<div class="blockbox white-bg" style="z-index:20;position:absolute;left:25%;top:25%;margin-left:0px;margin-top:5px;background:#fff;display:none;" id="loader">
	<span class="loader big on-dark"></span>
</div>
	<div class="title_d1">
		<h4>콘 정보 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label" for="it_1">
				<span>콘 가격</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="modal_cornCount"></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>할인율</span>
			</label>
			<div class="field">
				<div><span id="modal_discountPercent"></span></div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:modify();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
		</p>
	</div>
</form>
</body>
</html>

