<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String userId = Util.isNullValue(request.getParameter("user_id"), "1");
	String phoneNumber = Util.isNullValue(request.getParameter("l_phoneNumber"), "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<jsp:include page="/daul/include/header.jsp" flush="false"/> 
<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>

<script type="text/javascript" charset="utf-8" src="daul/js/plugins/jquery.slides.js"></script>
<script type="text/javascript" charset="utf-8" src="daul/js/plugins/jquery.pageslide.js"></script>

<!--  그래프 관련 스크립트 -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<%-- <link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css"> --%>
<script type="text/javascript">
function init() {
	$("#user_top_menu_2").attr("class","button silver-gradient glossy active");
	fn_search("new");
}

function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var userId = '<%=userId%>';
	var sel_searchType = getSelectboxValue("sel_searchType");
	var search_value = getInputTextValue("search_value");
	
	if (sel_searchType == "" && search_value != "") {
		alert(comment.select_search_list);
		focusInputText("sel_searchType");
		return;
	}
	
	if (val == "new")
		sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");
	
	var productCode = "";
	var productName = "";
	
	if (sel_searchType == "productCode") {
		productCode = search_value;
	} else if (sel_searchType == "productName") {
		productName = search_value;
	}
	
	itemService.userProductBuyLogListCnt(userId, productCode, productName, sel_searchType,  function(cnt) {
		cnt == 0 ? $("#total_cnt").html("0") : $("#total_cnt").html(addThousandSeparatorCommas(cnt));

		if (cnt == 0) {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView('v', comment.blank_list);
		} else {
			if (new Number(cnt) < (15*(sPage-1))) {
				if (new Number(cnt) < (10*sPage)) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		
		itemService.userProductBuyLogList(sPage, userId, productCode, productName, sel_searchType, function(selList) {
			if (selList.length > 0) {
				var cornSum = "";
				var popcornSum = "";
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];	
					if (cmpList != undefined) {
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.productCode+"'/>";
						btnHTML= "<a href='#modal_content' class='modalBtn'>"+cmpList.productCode+"</a>";
						var cellData = [
									function(data) {return cmpList.productCode;},
									function(data) {return cmpList.productName;},
									function(data) {return gfn_isData(gfn_zeroToZero(cmpList.cornPrice), "0", "0", cmpList.cornPrice);},
									function(data) {return gfn_isData(gfn_zeroToZero(cmpList.popcornPrice), "0", "0", cmpList.popcornPrice);},
									function(data) {return getDateTimeSplitComma(cmpList.createDate);}
									];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
					//콘 총합
					cornSum += Number(cmpList.cornPrice);
					cornSum = parseFloat(gfn_isData(cornSum, "", "0", cornSum));
					//팝콘 총합
					popcornSum += Number(cmpList.popcornPrice);
					popcornSum = parseFloat(gfn_isData(popcornSum, "", "0", popcornSum));
				}
				var cellData = [
				                function(data) {return "총합";},
				                function(data) {return "";},
				                function(data) {return addThousandSeparatorCommas(cornSum);},
				                function(data) {return addThousandSeparatorCommas(popcornSum);},
				                function(data) {return "";}
						];
				dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
			}
		});
		
		itemService.finfUserCornPopcornPayTotal(userId, function(totalPayInfo) {
			innerHTML("sum_corn", gfn_isData(totalPayInfo.cornPrice, null, "0", addThousandSeparatorCommas(totalPayInfo.cornPrice)));
			innerHTML("sum_popcorn", gfn_isData(totalPayInfo.popcornPrice, null, "0", addThousandSeparatorCommas(totalPayInfo.popcornPrice)));
		});
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="user_id" id="user_id" value="<%=userId%>">
<input type="hidden" name="l_phoneNumber" id="l_phoneNumber" value="<%=phoneNumber%>">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>

<div class="rightarea span8">
		<div class="subbody">
		<div class="contentbody">
		<%@ include file="/common/jsp/user_top_menu.jsp"%>
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">사용자</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('list');">사용자 리스트</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('userItemBuyList');">아이템 구매내역</span>
				</div>
				<div class="span10" >
				<div class="title_d1">
					<h4><%=phoneNumber%>님</h4>
				</div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>구매 정보</span>
									<select class="select" id="sel_searchType">
										<option value="">▶검색종류선택</option>
										<option value="productCode">상품코드</option>
										<option value="productName">상품이름</option>
									</select>
									<input type="text" class="input width-small" id="search_value" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode == 13){fn_search('new'); return false;}">
									<a href="javascript:fn_search('new');" class="button blue-gradient glossy">
										<span class="icon-search"></span>검색
									<!-- <button type="button" class="button blue-gradient glossy" onclick="javascript:fn_search('new');">검색</button> -->
									</a>
									<!-- <input type="button" class="button blue-gradient glossy" value="엑셀다운로드" onclick="excelDownload('excel/userItemBuyList.do');"> -->
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
											총 : <span id="total_cnt"></span>건 &nbsp;&nbsp;&nbsp;&nbsp;
											소비한 콘 : <span id="sum_corn"></span> &nbsp;&nbsp;&nbsp;&nbsp;
											소비한 팝콘 : <span id="sum_popcorn"></span>
											<p class="f_R">
												<a href="javascript:excelDownload('excel/userItemBuyList.do');" class="button compact black-gradient glossy">
													<span class="icon-download">엑셀다운로드</span>
												</a>
											</p>	
										</div>
									</div><br>
									<table class="table_list">
										<colgroup>
											<!-- <col width="2%" /> -->
											<col width="*" />
											<col width="*" />
											<col width="*" />
											<col width="*" />
										</colgroup>
										<thead>
										<tr>
											<!-- <th align="left"><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th> -->
											<th>상품코드</th>
											<th>상품이름</th>
											<th>콘 가격</th>
											<th>팝콘 가격</th>
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
		</div>
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->	
</form>
</body>
</html>
<script>
	$("#menu_02_01").addClass("active");
	$(".modalBtn").pageslide({ direction: "left"});
</script>