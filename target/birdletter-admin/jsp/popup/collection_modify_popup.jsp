<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String productCode = Util.isNullValue(request.getParameter("productCode"), "");
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
var productCode = '<%=productCode%>';

function init() {
	if (productCode != "") {
		collectionModify_Modal(productCode);
	} else {
		collectionInsert_Modal();
	}
}

//콜렉션 수정페이지show
function collectionModify_Modal(productCode) {
	itemService.collectionItemInfo(productCode, function(itemInfo) {
		innerValue("modal_productCode", productCode);
		innerValue("modify_collectionName", itemInfo.collectionName);
		collectionInsertSelectBox("COSTUM", "modal_costum", itemInfo.costumCode);
		collectionInsertSelectBox("THEME_HOUSE", "modal_themeHouse", itemInfo.themeHouseCode);
		collectionInsertSelectBox("THEME_BG", "modal_themeBg", itemInfo.themeBgCode);
		innerValue("modify_collectionDesc", itemInfo.collectionDesc);
		
	});
}

//콜렉션 등록페이지
function collectionInsert_Modal() {
	itemService.findLastCollectionCode(function(productCode) {
		innerHTML("l_collectionCode", productCode);
	});
	collectionInsertSelectBox("COSTUM", "modal_costum", '');
	collectionInsertSelectBox("THEME_HOUSE", "modal_themeHouse", '');
	collectionInsertSelectBox("THEME_BG", "modal_themeBg", '');
}

//수정버튼
function collectionModify() {
	var productCode = $("#modal_productCode").val();
	var collectionName = $("#modify_collectionName").val();
	var collectionDesc = $("#modify_collectionDesc").val();
	var bodyCode = $("#sel_BODY option:selected").val();
	var capCode = $("#sel_CAP option:selected").val();
	var glassCode = $("#sel_GLASS option:selected").val();
	var costumCode = $("#sel_COSTUM option:selected").val();
	var roofCode = $("#sel_ROOF option:selected").val();
	var houseCode = $("#sel_HOUSE option:selected").val();
	var themeHouseCode = $("#sel_THEME_HOUSE option:selected").val();
	var bgCode = $("#sel_BG option:selected").val();
	var themeBgCode = $("#sel_THEME_BG option:selected").val();
	var dataObj = {productCode:productCode, collectionName:collectionName,
			collectionDesc:collectionDesc, bodyCode:bodyCode, capCode:capCode,
			glassCode:glassCode, costumCode:costumCode, roofCode:roofCode,
			houseCode:houseCode, themeHouseCode:themeHouseCode, bgCode:bgCode,
			themeBgCode:themeBgCode
		};

	if (confirm("선택하신 목록으로 수정하시겠습니까?")) {
		itemService.collectionItemInfoModify(dataObj, function(bl) {
			if (bl == true) {
				alert("수정되었습니다.");
				parent.document.location.reload();
				//location.reload(true);
			} else {
				alert("수정 실패하였습니다.\n관리자에게 문의하세요.");
				return;
			}
		});
	}
}

//등록버튼
function insert() {
	var collectionCode = $("#l_collectionCode").html();
	var collectionName = $("#l_collectionName").val();
	var collectionDesc = $("#l_collectionDesc").val();
	var costum = $("#sel_COSTUM option:selected").val();
	var houseTheme = $("#sel_THEME_HOUSE option:selected").val();
	var backTheme = $("#sel_THEME_BG option:selected").val();
	
	if (collectionName == "") {
		alert("콜렉션 이름을 입력하세요.");
		$("#l_collectionName").focus();
		return;
	}
	if (costum == "") {
		alert("코스튬을 선택하세요.");
		$("#sel_COSTUM").focus();
		return;
	}
	if (houseTheme == "") {
		alert("집테마를 선택하세요.");
		$("#sel_THEME_HOUSE").focus();
		return;
	}
	if (backTheme == "") {
		alert("배경테마를 선택하세요.");
		$("#sel_THEME_BG").focus();
		return;
	}
	
	var dataObj = {productCode:collectionCode, costumCode:costum ,themeHouseCode:houseTheme, 
			themeBgCode:backTheme, 	collectionName:collectionName, collectionDesc:collectionDesc		
		}
	
	if (confirm("콜렉션을 등록하시겠습니까?")) {
		itemService.collectionItemInfoInsert(dataObj, function(bl) {
			if (bl == true) {
				alert("콜렉션이 등록됬습니다.");
				parent.document.location.reload();
			} else {
				alert("등록실패했습니다.\n관리자에게 문의하세요.");
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
<input type="hidden" id="modal_productCode" name="modal_productCode">
	<div class="title_d1">
		<% if (productCode != "") {%>
			<h4>콜렉션 수정</h4>
		<% } else { %>
			<h4>콜렉션 등록</h4>	
		<% } %>
	</div>
	<div class="formbox">
		<% if ("".equals(productCode)) {%>
		<div class="block_label medium">
			<label class="label">
				<span>콜렉션 코드</span>
			</label>
			<div class="field">
				<div><span id="l_collectionCode"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>콜렉션 이름</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="l_collectionName"></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>콜렉션 설명</span>
			</label>
			<div class="field">
				<div><textarea rows="6" class="input width-large" id="l_collectionDesc"></textarea></div>
			</div>
		</div>
		<% } else {%>
		<div class="block_label medium">
			<label class="label">
				<span>콜렉션 이름</span>
			</label>
			<div class="field">
				<div><input type="text" class="input width-large" id="modify_collectionName"></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>콜렉션 설명</span>
			</label>
			<div class="field">
				<div><textarea rows="6" class="input width-large" id="modify_collectionDesc"></textarea></div>
			</div>
		</div>
		<% } %>
		<div class="block_label medium">
			<label class="label">
				<span>스페셜 의상</span>
			</label>
			<div class="field">
				<div><span id="modal_costum"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label" for="it_1">
				<span>스페셜 둥지</span>
			</label>
			<div class="field">
				<div><span id="modal_themeHouse"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>스페셜 숲</span>
			</label>
			<div class="field">
				<div><span id="modal_themeBg"></span></div>
			</div>
		</div>
		
		<div class="group list_bottom">
			<p class="f_L">
				<% if (productCode != "") {%>
				<a href="javascript:collectionModify();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
				<% } else { %>
				<a href="javascript:insert();" class="button blue-gradient"><span class="icon-pencil"> </span>등록</a>
				<% } %>
			</p>
		</div>
	</div>
</form>
</body>
</html>

