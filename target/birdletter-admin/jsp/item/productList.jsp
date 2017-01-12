<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>
<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>
<script type="text/javascript">

function init() {
	fn_search("new");
}

//페이징 리스트
function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var storeType = getSelectboxValue("sel_storeType");
	var searchType = getSelectboxValue("sel_searchType");
	var serchValue = getInputTextValue("search_value");
	
	if (searchType == "" && serchValue != "") {
		alert("검색종류를 선택하세요");
		focusInputText("sel_searchType");
		return;
	}
	
	selectList(storeType);//상품종류선택 셀렉트박스
	//searhSelectList(searchType);//검색종류선택 셀렉트박스
	
	//리스트 개수 셀렉트 박스
	var pagingCnt = getSelectboxValue("sel_pagingCnt");
	if (pagingCnt == undefined) pagingCnt = 15;
	pagingListSelectbox(pagingCnt, "l_pageCnt");
	
	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");
	
	var productCode = "";
	var itemCode = "";
	var productName = "";
	
	if (searchType == "productCode") {
		productCode = serchValue;
	} else if (searchType == "productName") {
		productName = serchValue;
	}

	itemService.productListCnt(productCode, itemCode, productName, storeType, function(cnt) {
		if (cnt == 0) {
			gfn_printPageNum_new('0', '15', pagingCnt, '1');
			//gfn_emptyView('v', comment.blank_list);
		} else {
			if (new Number(cnt) < (pagingCnt * sPage)) {
				if (new Number(cnt) < (pagingCnt * (sPage - 1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', pagingCnt, sPage);
		}
		
		itemService.productList(pagingCnt, sPage, productCode, itemCode, productName, storeType, function(selList) {
			var webRoot ='<%=webRoot%>';
			var left = "left";
			var productPopup = "productPopup.jsp";
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];	
					if (cmpList != undefined) { 
						checkHTML = "<input type='checkbox' name='chk' id='chk' class='chkbox' value='"+cmpList.idx+"'/>";
						btnHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/productPopup.jsp?productIdx="+cmpList.idx+"'})\" class=\"modalBtn\">"+cmpList.productCode+"<\/a>";
						
						var cellData = [
						            	function(data) {return checkHTML;},
						            	function(data) {return btnHTML;},
						            	function(data) {return cmpList.productName;},
						            	function(data) {return cmpList.itemCode;},
						            	function(data) {return gfn_zeroToZero(cmpList.cornPrice);},
						            	function(data) {return gfn_zeroToZero(cmpList.popcornPrice);},
						            	function(data) {return gfn_zeroToZero(getStoreConditionName(cmpList.storeCondition));}
						            	//function(data) {return gfn_isData(gfn_zeroToZero(cmpList.viewYn), "1", "판매중", "판매중지")}
									];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

function checkVal(type) {
	var isChecked = isCheckedCheckbox("chk", "NAME");
	if (isChecked == false) {
		if (type == 'delete') {
			jAlert(comment.check_item);
			return;
		} else if (type == 'point_update') {
			var store_type = getSelectboxValue("sel_storeType");
			if (store_type == "") {
				jAlert(comment.select_product, "sel_storeType");
				return;
			}
			jAlert(comment.blank_check_all, "chkAll");
			return;
		}
	}
	
	if (type == "delete") {
		if (confirm(comment.isDelete)) {
			$("input[name=chk]:checked").each(function() {
				var productIdx = $(this).val();
				if (productIdx == "") {
					jAlert(comment.blank_check);
					return;
				}
				itemService.productDelete(productIdx, function(bl) {
					if (bl == true) {
					} else {
						jAlert(comment.error);
						return;
					}
				});
			});
			isReloadPage(true);
		}
	} else if (type == "point_update") {
		$("#modifyBtn").trigger("click");
		$("#product_type").val(store_type);
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
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('productList');">스토어 목록</span>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>스토어 목록</span>
								<span id="l_selStore" style="padding: 3px; 1px;"></span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<a href="javascript:checkVal('delete');" class="button compact red-gradient glossy">
												<span class="icon-trash">삭제</span>
											</a>
											<a href="javascript:checkVal('point_update');" class="button compact blue-gradient glossy">
												<span class="icon-tools"></span>포인트 일괄변경
											</a>
											<!-- <input type="button" class="button compact blue-gradient glossy" onclick="javascript:checkVal('delete');" value="삭제"> -->
											<!-- <input type="button" class="button compact blue-gradient glossy" onclick="javascript:checkVal('point_update');" value="포인트 일괄변경"> -->
										</p>
										<p class="f_R">
											리스트 : <span id="l_pageCnt"></span>
											<a href="javascript:goItem('itemList');" class="button compact blue-gradient glossy">
												<span class="icon-pencil"></span>상품추가
											</a>
										</p>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<col width="2%" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<!-- <col width="*" /> -->
									</colgroup>
									<thead>
									<tr>
										<th>
											<input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');">&nbsp;&nbsp;
										</th>
										<th>상품코드</th>
										<th>상품이름</th>
										<th>아이템 코드</th>
										<th>콘 가격</th>
										<th>팝콘 가격</th>
										<th>상점 상태</th>
										<!-- <th>판매 여부</th> -->
									</tr>
									</thead>
									<tbody id="dataList"></tbody>
									<tr>
										<td id="emptys" colspan='23' bgcolor="#ffffff" align='center' valign='middle' style="visibility:hidden"></td>
									</tr>
								</table>
								<%@ include file="/common/inc/com_pageNavi.inc" %>
							</div>
							<div class="portlet-content" >
								<span id="l_plus"></span>
								<div>
								    <ul id="itemList" class="aaa"></ul>
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
<a href="#modal_point" id="modifyBtn" style="display: none;">포인트 일괄변경</a>
<div id="modal_point" style="display:none">
	<div class="list_search">
		<div class="f_L">
			<h4>포인트 일괄변경</h4>				
		</div>
	</div>
	<ul class="list_basic">
		<li class="group">
			<a href="#none" target="_self" title="" >
				<div class="left">
					<div class="h2">
						콘  &nbsp;&nbsp;&nbsp; <input type="text" class="input width-small" id="corn_point"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						팝콘 &nbsp;&nbsp;&nbsp; <input type="text" class="input width-small" id="popcorn_point"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:productModify();" class="button compact blue-gradient"><span class="icon-tools"></span>수정</a> 
					</div>
				</div>
			</a>
		</li>
	</ul>
</div>
</form>
</body>
</html>
<script>$("#menu_03_02").addClass("active");
$("#modifyBtn").pageslide({direction:"left"});
</script>