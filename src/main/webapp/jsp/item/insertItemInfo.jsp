<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>
<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>
<script type="text/javascript">
function onload() {
	//selectListByInsert();
	selectItemList("l_selStore", 'init(this.value);');
}

function init(val) {
	var itemType = val;
	
	if (itemType == "COLLECTION") {
		goItem('collectionList');
	}
	
	itemService.getNewItemCode(itemType, function(itemCode) {
		if (itemCode != "") {
			$("#itemCode_tr").show();
			$("#l_itemCode").html(itemCode);
			$("#itemName_tr").show();
			$("#itemDesc_tr").show();
		}
	});
}

function insert() {
	if (input_blank_check("sel_itemType", "아이템 종류를 선택하세요.") == false) return;
	if (input_blank_check("l_itemName", "아이템 이름을 입력하세요.") == false) return;
	if (input_blank_check("l_itemDesc", "아이템 설명을 입력하세요.") == false) return;
	
	jConfirm(comment.insert, comment.isInsert, itemInsert);
}

function itemInsert() {
	var itemType = getSelectboxValue("sel_itemType");
	var itemCode = getInnerHtmlValue("l_itemCode");
	var itemName = getInputTextValue("l_itemName");
	var itemDesc = getInputTextValue("l_itemDesc");
	
	itemService.insertItemInfo(itemCode, itemName, itemDesc, itemType, function(result) {
		if (result > 0) {
			goItem('insertItemInfo');
		} else {
			jAlert(comment.error);
			return;
		}
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="onload();"> 
<form name="frm" method="get">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="productCode" id="productCode">
<input type="hidden" name="itemCode" id="itemCode">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<!--  S : rightarea -->
	<div class="rightarea span8">
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('insertItemInfo');">아이템 등록</span>
				</div>
				<div class="title_d1">
					<div><span id="l_selStore"></span> &nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#collection_modal" id="collection_btn" class="button compact green-gradient" style="display: none;"><span class="icon-pencil"></span>콜렉션 목록 등록</a>
				</div>
				<br><br>
				<div class="formbox">
					<div class="block_label medium" id="itemCode_tr" style="display: none;">
						<label class="label">
							<span>아이템 코드</span>
						</label>
						<div class="field">
							<div><span id="l_itemCode"></span></div>
						</div>
					</div>
					<div class="block_label medium" id="itemName_tr" style="display: none;">
						<label class="label">
							<span>아이템 이름</span>
						</label>
						<div class="field">
							<div><input type="text" class="input width-small" id="l_itemName"></div>
						</div>
					</div>
					<div class="block_label medium" id="itemDesc_tr" style="display: none;">
						<label class="label">
							<span>아이템 설명</span>
						</label>
						<div class="field">
							<div>
								<textarea rows="6" class="input width-small" id="l_itemDesc" onkeypress="javascript:if(event.keyCode == 13){insert(); return false;}"></textarea>
							</div>
							<!-- <div><input type="text" class="input width-small" id="l_productDesc"></div> -->
						</div>
					</div>
				</div>
				<br>
				<p class="f_L">
					<a href="javascript:insert();" class="button blue-gradient glossy"><span class="icon-pencil"></span>등록</a>
					<a href="javascript:location.reload(true);" class="button red-gradient glossy"><span class="icon-refresh"></span>재등록</a>
				</p>
			</div>
		</div>
	</div>
</form>
<!--  E: right area-->
<!--  S: foot-->
<jsp:include page="/daul/include/footer.jsp" flush="false"/>
<!--  E: foot-->
</body>
</html>
<script>
$("#menu_03_08").addClass("active");
</script>