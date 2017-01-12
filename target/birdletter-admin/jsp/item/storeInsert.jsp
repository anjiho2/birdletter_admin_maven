<%@page import="java.net.URLEncoder"%>
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String store_type = Util.isNullValue(request.getParameter("store_type"), "");
	String item_idxs = Util.isNullValue(request.getParameter("item_idxs"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
function init() {
	var item_idxs = '<%=item_idxs%>';
	var store_type = '<%=store_type%>';
	
	if (item_idxs != "") {
		if (store_type != "COLLECTION") {
			itemService.getItemNames(item_idxs, function(itemCodes) {
				if (itemCodes != "") {
					innerHTML("l_itemCodes", itemCodes);
				}
			});
		} else if (store_type == "COLLECTION") {
			var item_code =	item_idxs.substring(0, item_idxs.length-1);
			innerHTML("l_itemCodes", item_code);
			//$("#l_itemCodes").html(item_code);
		} 
	}
	itemCategorySelectBox();
	itemTypeSelectBox();
	insertViewYnSelectBox();
	conditionSelectBox("", "l_condition");
	discountSelectBox();
}

function insertStore() {
	var item_idxs = '<%=item_idxs%>';
	var store_type = '<%=store_type%>';
	var popcornPrice = getInputTextValue("l_popcornPrice");
	var cornPrice = getInputTextValue("l_cornPrice");
	var topMenuId = getSelectboxValue("sel_itemCategory");
	var subMenuId = getSelectboxValue("sel_itemTyepSelectBox");
	var discountPercent = getSelectboxValue("sel_discount");
	var storeCondition = getSelectboxValue("sel_condition");
	var saleYn = getInputTextValue("useYn");
	
	if (isCheckedCheckbox("useYn", "ID")) saleYn = "1";
	
	if (input_blank_check("l_popcornPrice", "팝콘 가격을 입력하세요.") == false) return;
	if (input_blank_check("l_cornPrice", "콘 가격을 입력하세요.") == false) return;
	if (selectbox_blank_check("sel_itemCategory", "상위메뉴를 선택하세요.") == false) return;
	if (selectbox_blank_check("sel_itemTyepSelectBox", "하위메뉴를 선택하세요.") == false) return;
	if (selectbox_blank_check("sel_discount", "할인율을 선택하세요.") == false) return;
	if (selectbox_blank_check("sel_condition", "상점상태를를 선택하세요.") == false) return;
	
	jConfirm("등록", comment.isInsert, insert);
	/*
	if (confirm(comment.isInsert)) {
		if (store_type != "COLLECTION") {
			itemService.insertStore("NORMAL", item_idxs, store_type, cornPrice, popcornPrice, topMenuId, 
					subMenuId, discountPercent, saleYn, storeCondition, function(bl) {
				if (bl == true) {
					alert(comment.success_process);
					goItem('productList');
				} else {
					alert(comment.error);
					return;
				}
			});
		} else if (store_type == "COLLECTION") {
			itemService.insertStore("COLLECTION", item_idxs, store_type, cornPrice, popcornPrice, topMenuId, 
					subMenuId, discountPercent, saleYn, storeCondition, function(bl) {
				if (bl == true) {
					alert(comment.success_process);
					goItem('productList');
				} else {
					alert(comment.error);
					return;
				}
			});
		}
	}
	*/
}

function insert() {
	var item_idxs = '<%=item_idxs%>';
	var store_type = '<%=store_type%>';
	var popcornPrice = getInputTextValue("l_popcornPrice");
	var cornPrice = getInputTextValue("l_cornPrice");
	var topMenuId = getSelectboxValue("sel_itemCategory");
	var subMenuId = getSelectboxValue("sel_itemTyepSelectBox");
	var discountPercent = getSelectboxValue("sel_discount");
	var storeCondition = getSelectboxValue("sel_condition");
	var saleYn = getInputTextValue("useYn");
	
	if (isCheckedCheckbox("useYn", "ID")) saleYn = "1";
	
	if (store_type != "COLLECTION") {
		itemService.insertStore("NORMAL", item_idxs, store_type, cornPrice, popcornPrice, topMenuId, 
				subMenuId, discountPercent, saleYn, storeCondition, function(bl) {
			if (bl == true) {
				jAlert(comment.success_process);
				goItem('productList');
			} else {
				jAlert(comment.error);
				return;
			}
		});
	} else if (store_type == "COLLECTION") {
		itemService.insertStore("COLLECTION", item_idxs, store_type, cornPrice, popcornPrice, topMenuId, 
				subMenuId, discountPercent, saleYn, storeCondition, function(bl) {
			if (bl == true) {
				jAlert(comment.success_process);
				goItem('productList');
			} else {
				jAlert(comment.error);
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
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" id="modify_noticeId">
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
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('collectionList');">콜렉션 목록</span>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>스토어 등록</span>
							</div>
							<div class="portlet-content">
								<div class="formbox">
								<div class="block_label medium">
									<label class="label">
										<span>아이템 코드</span>
									</label>
									<div class="field">
										<div>
											<div><span id="l_itemCodes"></span></div>
										</div>
									</div>
								</div>
								<div class="block_label medium" id="product_desc_div" style="display: none;">
									<label class="label">
										<span>상품 설명</span>
									</label>
									<div class="field">
										<div>
											<textarea rows="6" class="input width-small" id="l_productDesc"></textarea>
										</div>
									</div>
								</div>
								<div class="block_label medium">
									<label class="label">
										<span>팝콘 가격</span>
									</label>
									<div class="field">
										<div>
											<div><input type="text" class="input width-small" id="l_popcornPrice"></div>
										</div>
									</div>
								</div>
								<div class="block_label medium">
									<label class="label">
										<span>콘 가격</span>
									</label>
									<div class="field">
										<div>
											<div><input type="text" class="input width-small" id="l_cornPrice"></div>
										</div>
									</div>
								</div>
								<div class="block_label medium">
									<label class="label">
										<span>상위 메뉴</span>
									</label>
									<div class="field">
										<div>
											<div><span id="l_itemCategotySelect"></span></div>
										</div>
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
								<div class="block_label medium" id="preview_div">
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
										<span>판매여부</span>
									</label>
									<div class="field">
										<div>
											<input type="checkbox" value="0" id="useYn">
											<!-- <span id="l_viewYn"></span> -->
										</div>
									</div>
								</div>
								<div class="group list_bottom">
									<p class="f_L">
										<a href="javascript:insertStore();" class="button blue-gradient glossy"><span class="icon-pencil"></span>스토어 등록</a>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->
</form>
</body>
</html>
<script>
$("#menu_03_08").addClass("active");
$(document).ready(function() {$(':checkbox').iphoneStyle();});
</script>
