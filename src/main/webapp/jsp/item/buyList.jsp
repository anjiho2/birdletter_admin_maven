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

function init() {
	fn_search("new");
}

function fn_search(val) {
	var sPage = $("#sPage").val();
	var storeType = $("#sel_storeType option:selected").val();
	if (storeType == undefined) storeType = "";
	
	if (val == "new")
		sPage = "1";
	
	selectList(storeType);
	
	dwr.util.removeAllRows("dataList", {
		filter:function(tr) {
			return (tr.id != "pattern");
		}
	});
	gfn_emptyView("H", "");
	
	var phoneNumber = $("#phoneNumber").val();
	
	itemService.itemBuyListCnt(phoneNumber, storeType, function(cnt) {
		$("#total_cnt").html(addThousandSeparatorCommas(cnt));
		if (cnt == "0") {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView('v', "결과값이 없습니다");
		} else {
			if (new Number(cnt) < (10*sPage)) {
				if (new Number(cnt) < (10*sPage)) {
					sPage = 1;
					$("#sPage").val(sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		itemService.itemBuyList(sPage, phoneNumber, storeType, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						/* 
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"'/>";
						btnHTML = '<a href="#" onclick="javascript:itemCode_Modal('+ "'"+cmpList.idx+"'," + "'"+cmpList.itemCode+"'"+ ')">'+cmpList.itemCode+"</a>";           
						 */
						var  cellData = [
						                 function(data) {return cmpList.phoneNumber;},
						                 function(data) {return cmpList.productCode;},
						                 function(data) {return getDateTimeSplitComma(cmpList.createDate);}
						                 ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

function itemCode_Modal(val1, val2) {
	var itemIdx = val1;
	var itemCode = val2;
	
	$(".loginmodal-container #itemCode").val(itemCode);
	$(".loginmodal-container #itemIdx").val(itemIdx);
	
	$("#login-modal").modal('show');
}

function itemCode_modify() {
	var itemIdx = $("#itemIdx").val();
	var itemCode = $("#itemCode").val();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="get">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<div class="rightarea span8">
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('buyList');">아이템 판매 총 구매내역</span>
				</div>
				<div class="span10" >
				<div class="title_d1">
				</div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>아이템 판매 총 내역</span>
									<span id="l_selStore" style="padding: 3px; 1px;"></span>
									<input type="text" class="input" id="phoneNumber" placeholder="전화번호" onkeypress="javascript:if(event.keyCode == 13){fn_search('new'); return false;}">
									<a href="javascript:fn_search('new');" class="button blue-gradient glossy">
										<span class="icon-search"></span>검색
									<!-- <button type="button" class="button blue-gradient glossy" onclick="javascript:fn_search('new');">검색</button> -->
									</a>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_R">
											<a href="javascript:excelDownload('excel/itemSaleList.do');" class="button compact black-gradient glossy">
												<span class="icon-download">엑셀다운로드</span>
											</a>
										</p>
										총 : <span id="total_cnt"></span>건 &nbsp;&nbsp;&nbsp;&nbsp;
									</div>
								</div><br>
									<table class="table_list">
										<colgroup>
											<col width="*" />
											<col width="*" />
											<col width="*" />
										</colgroup>
										<thead>
										<tr>
											<th>전화번호</th>
											<th>상품코드</th>
											<th>구매일</th>
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
						</div>
					</div>
				</div>
			</div>
		</div>
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->
</body>
</html>
<script>
$("#menu_04_04").addClass("active");
</script>