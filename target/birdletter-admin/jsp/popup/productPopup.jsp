<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String productIdx = Util.isNullValue(request.getParameter("productIdx"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
var productIdx = '<%=productIdx%>';

function init() {
	productModify_Modal(productIdx);
}

//수정페이지show
function productModify_Modal(productIdx) {
	itemService.productListDetail(productIdx, function(vo) {
		if (vo != null) {
			var checked = "";
			innerValue("l_productIdx", vo.idx);
			innerHTML("modal_productCode", vo.productCode);
			innerValue("modal_productName", vo.productName);
			innerValue("modal_productDesc", vo.productDesc);
			innerValue("modal_popcornPrice", gfn_zeroToZero(vo.popcornPrice));
			innerValue("modal_cornPrice", gfn_zeroToZero(vo.cornPrice));
			innerValue("modal_itemCode", vo.itemCode);
			
			itemCategorySelectBox(vo.itemCategory, vo.itemType);
			itemTypeSelectBox(vo.itemCategory, vo.itemType);
			discountSelectBox(vo.discountPercent);
			conditionSelectBox(vo.storeCondition, "l_condition");
			
			if (vo.viewYn == "1") {
				checked = "checked";
			}
			var useYnHTML = "<input type='checkbox' value='0' id='useYn' "+checked+">"+"<script>$(document).ready(function() {$(':checkbox').iphoneStyle();});<\/script>";
			innerHTML("l_useYn", useYnHTML);
		}
	});
}

//수정버튼
function productModify() {
	var productName = getInputTextValue("modal_productName");
	var productDesc = getInputTextValue("modal_productDesc");
	var popcornPrice = getInputTextValue("modal_popcornPrice");
	var cornPrice = getInputTextValue("modal_cornPrice");
	var itemCode = getInputTextValue("modal_itemCode");
	var mainMenuIdx = getSelectboxValue("sel_itemCategory");
	var subMenuIdx = getSelectboxValue("sel_itemTyepSelectBox");
	var discountPercent = getSelectboxValue("sel_discount");
	var condition = getSelectboxValue("sel_condition");
	var condition = getSelectboxValue("sel_condition");
	var viewYN = getInputTextValue("useYn");
	if (isCheckedCheckbox("useYn", "ID")) {
		viewYN = "1";
	}
	
	if (input_blank_check("modal_productName", "상품이름을 입력하세요.") == false ) return;
	if (input_blank_check("modal_productDesc", "상품설명을 입력하세요.") == false ) return;
	if (input_blank_check("modal_popcornPrice", "팝콘가격을 입력하세요.") == false ) return;
	if (input_blank_check("modal_cornPrice", "콘가격을 입력하세요.") == false) return;
	if (input_blank_check("modal_itemCode", "아이템코드를 입력하세요.") == false) return;
	if (selectbox_blank_check("sel_itemCategory", "상위메뉴를 선택하세요.") == false) return;
	if (selectbox_blank_check("sel_itemTyepSelectBox", "하위메뉴를 선택하세요.") == false) return;
	if (selectbox_blank_check("sel_discount", "할인률을 선택하세요.") == false) return;
	if (input_blank_check("useYn", "판매여부를 선택하세요.") == false ) return;
	if (discountPercent > 0) {
		if (condition != 4) {
			alert("할인율이 있으면 상점상태를 할인중으로 바꿔야합니다!");
			$("#sel_condition").focus();
			return;
		} 
	}
	
	var Obj = {idx:productIdx, productName:productName, productDesc:productDesc, 
			popcornPrice:popcornPrice, cornPrice:cornPrice, itemCode:itemCode,
			itemCategory:mainMenuIdx, itemType:subMenuIdx,
			storeMainMenuIdx:mainMenuIdx, storeSubMenuIdx:subMenuIdx,
			discountPercent:discountPercent, viewYn:viewYN, storeCondition:condition
	};
	
	if (confirm(comment.isUpdate)) {
		itemService.productModify(Obj, function(bl) {
			if (bl == true) {
				isReloadParentPage() ;
			} else {
				alert(comment.error);
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
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="store_type" id="store_type">
<div class="blockbox white-bg" style="z-index:20;position:absolute;left:25%;top:25%;margin-left:0px;margin-top:5px;background:#fff;display:none;" id="loader">
	<span class="loader big on-dark"></span>
</div>
	<div class="title_d1">
		<h4>상품 수정</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>상품 코드</span>
			</label>
			<div class="field">
				<div><span id="modal_productCode"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label" for="it_1">
				<span>상품 이름</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="modal_productName"></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>상품 설명</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="modal_productDesc"> </div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>팝콘 가격</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="modal_popcornPrice"> </div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>콘 가격</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="modal_cornPrice"> </div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>아이템 코드</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="modal_itemCode"> </div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>상위 메뉴</span>
			</label>
			<div class="field">
				<div><span id="l_itemCategotySelect"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>하위 메뉴</span>
			</label>
			<div class="field">
				<div><span id="l_itemTypeSelect"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>할인율</span>
			</label>
			<div class="field">
				<div><span id="l_discount"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>상점상태</span>
			</label>
			<div class="field">
				<div><span id="l_condition"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>판매 여부</span>
			</label>
			<div class="field">
				<div><span id="l_useYn"></span></div>
			</div>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:productModify();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
		</p>
	</div>
</form>
</body>
</html>

