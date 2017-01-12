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
	selectListByInsert();
}

function init(val) {
	var productType = val;
	
	itemService.makeProductLastCode(productType, function(map) {
		if ( productType == "") {
			$("#collection_btn").hide();
			$("#productCode_tr").hide();
			$("#itemCode_tr").hide();
			$("#productName_tr").hide();
			$("#productDesc_tr").hide();
			$("#popcorn_tr").hide();
			$("#corn_tr").hide();
			$("#itemCategoty_tr").hide();
			$("#itemType_tr").hide();
			$("#discount_tr").hide();
			$("#viewYn_tr").hide();
			$("#collection_tr").hide();
		}else if (productType != "" && map != null) {
			if (productType == "clt") {
				$("#collection_btn").show();
				collection_insert_modal(map.productCode);
			} else {
				$("#collection_btn").hide();
			}
			
				
			$("#productCode_tr").show();
			$("#itemCode_tr").show();
			$("#productName_tr").show();
			$("#productDesc_tr").show();
			$("#popcorn_tr").show();
			$("#corn_tr").show();
			$("#itemCategoty_tr").show();
			$("#itemType_tr").show();
			$("#discount_tr").show();
			$("#viewYn_tr").show();
		}
			dwr.util.setValue("l_productCode", map.productCode);
			$("#productCode").val(map.productCode);
			dwr.util.setValue("l_itemCode", map.itemCode);
			$("#itemCode").val(map.itemCode);
			
			itemCategorySelectBox();
			itemTypeSelectBox(val);
			insertViewYnSelectBox();
			discountSelectBox();
	});
}

function insert() {
	var productCode = $("#productCode").val();
	var itemCode = $("#itemCode").val();
	var productName = $("#l_productName").val();
	var productDesc = $("#l_productDesc").val();
	var popcornPrice = $("#l_popcornPrice").val();
	var cornPrice = $("#l_cornPrice").val();
	var itemCategory = $("#sel_itemCategory option:selected").val();
	var itemType = $("#sel_itemTyepSelectBox option:selected").val();
	var discount = $("#sel_discount option:selected").val();
	var viewYn = $("#sel_viewYn option:selected").val();
	
	if (productName == "") {
		alert("상품이름을 입력하세요.");
		$("#l_productName").focus();
		return;
	}
	if (productDesc == "") {
		alert("상품설명을 입력하세요.");
		$("#l_productDesc").focus();
		return;
	}
	if (popcornPrice == "") {
		alert("팝콘가격을 입력하세요.");
		$("#l_popcornPrice").focus();
		return;
	} else if (isNaN(popcornPrice)) {
		alert("숫자를 입력하세요.");
		$("#l_popcornPrice").focus();
		return;
	}
	if (cornPrice == "") {
		alert("콘 가격을 입력하세요.");
		$("#l_cornPrice").focus();
		return;
	} else if (isNaN(cornPrice)) {
		alert("숫자를 입력하세요.");
		$("#l_cornPrice").focus();
		return;
	}
	if (itemCategory == "") {
		alert("상위메뉴를 선택하세요.");
		$("#sel_itemCategory").focus();
		return;
	}
	if (itemType == "") {
		alert("하위메뉴를 선택하세요.");
		$("#sel_itemTyepSelectBox").focus();
		return;
	}
	if (discount == "") {
		alert("할인률을 선택하세요.");
		$("#sel_discount").focus();
		return;
	}
	if (viewYn == "") {
		alert("판매여부를 선택하세요.");
		$("#sel_viewYn").focus();
		return;
	}
	
	var Obj = {productCode:productCode, productName:productName, productDesc:productDesc,
			popcornPrice:popcornPrice, cornPrice:cornPrice, itemCode:itemCode,
			itemCategory:itemCategory, itemType:itemType, storeMainMenuIdx:itemCategory,
			storeSubMenuIdx:itemType, discountPercent:discount, viewYn:viewYn
	};
	
	if (confirm("상품을 등록하시겠습니까?")) {
		itemService.productInsert(Obj, function(bl) {
			if (bl == true) {
				alert("정상적으로 등록되었습니다.");
				location.reload(true);
			} else if (bl == false) {
				alert("중복된 아이템이 있거나 시스템 오류입니다.\n관리자에게 문의하세요.");
				return;
			}
		});
	}
}

function collection_insert_modal(val) {
	var productCode = val;
	
	$("#l_productCode").val(productCode);
	$("#modal_productCode").html(productCode);
	
	collectionInsertSelectBox("BODY", "modal_body");
	collectionInsertSelectBox("CAP", "modal_cap");
	collectionInsertSelectBox("GLASS", "modal_glass");
	collectionInsertSelectBox("COSTUM", "modal_costum");
	collectionInsertSelectBox("ROOF", "modal_roof");
	collectionInsertSelectBox("HOUSE", "modal_house");
	collectionInsertSelectBox("THEME_HOUSE", "modal_themeHouse");
	collectionInsertSelectBox("BG", "modal_bg");
	collectionInsertSelectBox("THEME_BG", "modal_themeBg");
}

function collectionInsert() {
	var productCode = $("#l_productCode").val();
	var bodyCode = $("#sel_BODY option:selected").val();
	var capCode = $("#sel_CAP option:selected").val();
	var glassCode = $("#sel_GLASS option:selected").val();
	var costumCode = $("#sel_COSTUM option:selected").val();
	var roofCode = $("#sel_ROOF option:selected").val();
	var houseCode = $("#sel_HOUSE option:selected").val();
	var themeHouseCode = $("#sel_THEME_HOUSE option:selected").val();
	var bgCode = $("#sel_BG option:selected").val();
	var themeBgCode = $("#sel_THEME_BG option:selected").val();
	var dataObj = {productCode:productCode, bodyCode:bodyCode, capCode:capCode,
			glassCode:glassCode, costumCode:costumCode, roofCode:roofCode,
			houseCode:houseCode, themeHouseCode:themeHouseCode, bgCode:bgCode,
			themeBgCode:themeBgCode
		};
	
	if (confirm("선택하신 목록으로 등록하시겠습니까?")) {
		itemService.collectionItemInfoInsert(dataObj, function(bl) {
			if (bl == true) {
				alert("등록이 완료됬습니다.\n콜렉션 아이템등록 계속진행해주세요.");
				//$("#collection_insert_modal").modal('hide');
				//parent.document.location.reload();
			} else {
				alert("등록에 실패하였습니다.\n관리자에게 문의하세요.");
				return;
			}
		});
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="onload();"> 
<form name="frm" method="get">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="productCode" id="productCode">
<input type="hidden" name="itemCode" id="itemCode">
<!-- <input type="hidden" id="l_productCode"> -->
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
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('productInsert');">스토어 목록 추가</span>
				</div>
				<div class="title_d1">
					<div><span id="l_selStore"></span> &nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#collection_modal" id="collection_btn" class="button compact green-gradient" style="display: none;"><span class="icon-pencil"></span>콜렉션 목록 등록</a>
				</div>
				<br><br>
				<div class="formbox">
					<div class="block_label medium" id="productCode_tr" style="display: none; ">
						<label class="label">
							<span>상품 코드</span>
						</label>
						<div class="field">
							<div><span id="l_productCode"></span></div>
						</div>
					</div>
					<div class="block_label medium" id="itemCode_tr" style="display: none;">
						<label class="label">
							<span>아이템 코드</span>
						</label>
						<div class="field">
							<div><span id="l_itemCode"></span></div>
						</div>
					</div>
					<div class="block_label medium" id="productName_tr" style="display: none;">
						<label class="label">
							<span>상품 이름</span>
						</label>
						<div class="field">
							<div><input type="text" class="input width-small" id="l_productName"></div>
						</div>
					</div>
					<div class="block_label medium" id="productDesc_tr" style="display: none;">
						<label class="label">
							<span>상품 설명</span>
						</label>
						<div class="field">
							<div><input type="text" class="input width-small" id="l_productDesc"></div>
						</div>
					</div>
					<div class="block_label medium" id="popcorn_tr" style="display: none;">
						<label class="label">
							<span>팝콘 가격</span>
						</label>
						<div class="field">
							<div><input type="text" class="input width-small" id="l_popcornPrice"></div>
						</div>
					</div>
					<div class="block_label medium" id="corn_tr" style="display: none;">
						<label class="label">
							<span>콘 가격</span>
						</label>
						<div class="field">
							<div><input type="text" class="input width-small" id="l_cornPrice"></div>
						</div>
					</div>
					<div class="block_label medium" id="itemCategoty_tr" style="display: none;">
						<label class="label">
							<span>상위 메뉴</span>
						</label>
						<div class="field">
							<div><span id="l_itemCategotySelect"></span></div>
						</div>
					</div>
					<div class="block_label medium" id="itemType_tr" style="display: none;">
						<label class="label">
							<span>하위 메뉴</span>
						</label>
						<div class="field">
							<div><span id="l_itemTypeSelect"></span></div>
						</div>
					</div>
					<div class="block_label medium" id="discount_tr" style="display: none;">
						<label class="label">
							<span>할인율</span>
						</label>
						<div class="field">
							<div><span id="l_discount"></span></div>
						</div>
					</div>
					<div class="block_label medium" id="viewYn_tr" style="display: none;">
						<label class="label">
							<span>판매여부</span>
						</label>
						<div class="field">
							<div><span id="l_viewYn"></span></div>
						</div>
					</div>
				</div>
				<br>
				<p class="f_L">
					<a href="javascript:insert();" class="button green-gradient"><span class="icon-pencil"></span>등록</a>
					<a href="javascript:location.reload(true);" class="button red-gradient"><span class="icon-trash"></span>재등록</a>
				</p>
			</div>
		</div>
	</div>
</form>
<!--  E: right area-->
<!--  S: foot-->
<jsp:include page="/daul/include/footer.jsp" flush="false"/>
<!--  E: foot-->
<div id="collection_modal" style="display:none">
	<div class="list_search">
		<div class="f_L">
			<h4>포인트 일괄변경</h4>				
		</div>
	</div>
	<ul class="list_basic">
		<li class="group">
			<a href="#none" target="_self" title="" >
				<div class="left">
				<!-- <a href="javascript:productModify();" class="button compact blue-gradient"><span class="icon-tools"></span>수정</a> -->
					<div class="h2">
						새  &nbsp;&nbsp;&nbsp; <span id="modal_body"></span>
					</div>
					<div class="h2">
						모자  &nbsp;&nbsp;&nbsp; <span id="modal_cap"></span>
					</div>
					<div class="h2">
						안경 &nbsp;&nbsp;&nbsp; <span id="modal_glass"></span>
					</div>
					<div class="h2">
						코스튬 &nbsp;&nbsp;&nbsp; <span id="modal_costum"></span>
					</div>
					<div class="h2">
						지붕 &nbsp;&nbsp;&nbsp; <span id="modal_roof"></span>
					</div>
					<div class="h2">
						집 &nbsp;&nbsp;&nbsp; <span id="modal_house"></span>
					</div>
					<div class="h2">
						집테마 &nbsp;&nbsp;&nbsp; <span id="modal_themeHouse"></span>
					</div>
					<div class="h2">
						배경 &nbsp;&nbsp;&nbsp; <span id="modal_bg"></span>
					</div>
					<div class="h2">
						배경테마 &nbsp;&nbsp;&nbsp; <span id="modal_themeBg"></span>
					</div>
				</div>
			</a>
		</li>
	</ul>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:collectionInsert();" class="button green-gradient"><span class="icon-pencil"></span>등록</a>
		</p>
	</div>
</div>
</body>
</html>
<script>
$("#menu_03_03").addClass("active");
$("#collection_btn").pageslide({direction:"left"});
</script>