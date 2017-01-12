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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>
<script type="text/javascript">
var webRoot = '<%=webRoot%>';

function init() {
	fn_search("new");
}

//리스트
function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var itemType = getSelectboxValue("sel_itemType"); 
	var searchType = "normal";
	//스토어에 등록안된 아이템 보기 체크박스 체크여부
	if (isCheckedCheckbox("regCheck", "ID")) {
		searchType = getInputTextValue("regCheck");
	}
	
	if (val == "new")	sPage = "1";
	
	if (itemType == undefined) {
		itemType = "";
	}
	if (itemType == "COLLECTION") {
		goItem('collectionList');
	}
	
	selectItemList('l_itemType', 'fn_search("new");', itemType);

	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");	
	
	itemService.itemListCnt(itemType, searchType, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new("0", "15", "15", "1");
			gfn_emptyView("v", comment.blank_list);
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, "15", "15", sPage);
		}
		itemService.itemList(sPage, itemType, searchType, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"'/>";
						itemCodeHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/item_modify_popup.jsp?itemIdx="+cmpList.idx+"'})\" class=\"modalBtn\">"+cmpList.itemCode+"<\/a>";
						var cellData = [
						                function(data) {return checkHTML;},
						                function(data) {return itemCodeHTML;},
						                function(data) {return cmpList.itemName;},
						                function(data) {return cmpList.itemDesc;}
						                ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

//삭제버튼
function deleteItemInfo() {
	var result = true;
	var isChecked = isCheckedCheckbox("chk", "NAME");
	
	if (isChecked == false) {
		alert(comment.check_item);
		return;
	}
	if (confirm(comment.isDelete)) {
		$("input[name=chk]:checked").each(function() {
			var idx = $(this).val();
			if (idx == "") {
				alert(comment.blank_check);
				return;
			}
			itemService.deleteItemInfo(idx, function(bl) {
			});
		});
		if (result > 0) {
			goItem("itemList");
		} else {
			alert(comment.error);
			return;
		}
	}
}

//스토어 등록 버튼
function goInsertStore() {
	var str = "";
	var itemType = getSelectboxValue("sel_itemType");
	if (selectbox_blank_check("sel_itemType", comment.select_item) == false) return;
	$("#store_type").val(itemType);
	
	if (isCheckedCheckbox("chk", "NAME") == false) {
		jAlert("스토어에 등록할 아이템을 체크하세요.");
		return;
	}
	$("input[name=chk]:checked").each(function(index) {
		str += $(this).val()+",";
	});
	innerValue("item_idxs", str);
	
	goItem('storeInsert');
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post" class="form-horizontal">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="resourceCode" id="resourceCode">
<input type="hidden" name="res_type" id="res_type">
<input type="hidden" name="store_type" id="store_type">
<input type="hidden" name="item_idxs" id="item_idxs">
<!--  S : leftarea-->
	<div class="leftarea">
		<!--  S : nav_area-->
		<jsp:include page="/daul/include/nav.jsp" flush="false"/>
		<!--  E : nav_area-->
	</div>
	<!-- E : lefrarea -->
	<!--  S: right area-->
	<div class="rightarea span10" >
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('itemList');">아이템 관리</span> 
				</div>
			<div class="span10">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>아이템 리스트</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
								<div class="list_top">
									<p class="f_L">
										<a href="javascript:deleteItemInfo();" class="button compact red-gradient glossy">
											<span class="icon-trash">삭제</span>
										</a>
										<span id="l_itemType"></span>
										<span style="padding-left: 100px;"></span>
										<label for="checkbox-2" class="checkbox">
											<input type="checkbox" name="regCheck" id="regCheck" value="reg" onchange="javascript:fn_search('new')">스토어에 등록안된 아이템 보기
										</label>
									</p>
									<p class="f_R">
										<a href="javascript:goItem('insertItemInfo');" class="button compact blue-gradient glossy">
											<span class="icon-pencil">아이템 등록</span>
										</a>
										<a href="javascript:goInsertStore();" class="button compact blue-gradient glossy" id="insertBtn">
											<span class="icon-cart">스토어 등록</span>
										</a>
									</p>
								</div>
									<br>
									<div >
									<table class="table_list">
										<colgroup>
											<col width="2%" />
											<col width="*" />
											<col width="*" />
											<col width="*" />
										</colgroup>
										<thead>
											<tr>
												<th>
													<input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');">
												</th>
												<th>아이템 코드</th>
												<th>아이템 이름</th>
												<th>아이템 설명</th>
											</tr>
										</thead>
										<tbody id="dataList"></tbody>
										<tr>
											<td id="emptys" colspan='23' bgcolor="#ffffff" align='center' valign='middle' style="visibility:hidden"></td>
										</tr>
									</table>
									<%@ include file="/common/inc/com_pageNavi.inc" %>
									</div>
								</div>
								<br>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<!--  E: contents-->
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
</script>