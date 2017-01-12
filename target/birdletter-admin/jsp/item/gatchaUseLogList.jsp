<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>
<script type="text/javascript" src="<%=webRoot%>/js/datepicker.js"></script>

<script type="text/javascript">
function init() {
	fn_search("new");
}

function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var startDate = getInputTextValue("startDate");
	var endDate = getInputTextValue("endDate");
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("search_value");
	var userName = "";
	var phoneNumber = "";
	
	if (searchType == "userName")	userName = searchValue;
	else if (searchType == "phoneNumber") phoneNumber = searchValue;
	
	//리스트 개수 셀렉트 박스
	var pagingCnt = getSelectboxValue("sel_pagingCnt");
	if (pagingCnt == undefined) pagingCnt = 20;
	pagingListSelectbox(pagingCnt, "l_pageCnt");
	//검색종류선택 공백 체크
	if (searchType == "" && searchValue != "") {
		jAlert(comment.select_search_list, "sel_searchType");
		return;
	}
	
	if (val == "new") sPage = "1";	//페이징 초기값 셋팅
	
	dwr.util.removeAllRows("dataList");
	gfn_emptyView("H", "");
	
	itemService.gatchaUseLogListCnt(userName, phoneNumber, startDate, endDate, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new("0", "15", pagingCnt, "1");
			gfn_emptyView("v", comment.blank_list);
		} else {
			if (new Number(cnt) < (pagingCnt * sPage)) {
				if (new Number(cnt) < (pagingCnt * (sPage - 1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', pagingCnt, sPage);
		}
		
		itemService.gatchaUseLogList(sPage, pagingCnt, userName, phoneNumber, startDate, endDate, function(selList) {
			if (selList.length > 0) {
				for (var i=0; i<selList.length; i++) {
					cmpList = selList[i];
					var cellData = [
					            	function(data) {return cmpList.phoneNumber;},
					            	function(data) {return cmpList.userName;},
					            	function(data) {return cmpList.itemName;},
					            	function(data) {return cmpList.itemCode;},
					            	function(data) {return cmpList.cornPrice;},
					            	function(data) {return cmpList.popcornPrice;},
					            	function(data) {return cmpList.rewardPoint;},
					            	function(data) {return getDateTimeSplitComma(cmpList.createDate);}
				                ];
					dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
				}
			}
		});
	});
}

function gatchaUseExcelDownload() {
	jConfirm(comment.download, comment.isExcelDownload, excelDownload);
}

function excelDownload() {
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("search_value");

	if (searchType == "userName") {
		innerValue("user_name", searchValue);
	} else if (searchType == "phoneNumber") {
		innerValue("phone_number", searchValue);
	}
	form_submit("frm", "excel/gatchaUseList.do");
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();">
<form name="frm" id="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="user_name" id="user_name">
<input type="hidden" name="phone_number" id="phone_number">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<!--  S : rightarea -->
<div class="rightarea span10">
	<div class="subbody">
		<div class="row-fluid">
			<div class="breadcrumb span10">
				<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('gatchaUseLogList');">가차 사용 기록</span>
			</div>
			<div class="span10">
			<div class="title_d1"></div>
				<div class="content">
					<div class="portlet">
						<div class="portlet-header anthracite-gradient">
							<span class="icon_id"></span><span>가차 사용 기록</span>
						</div>
						<div class="portlet-content">
							<div class="content">
								<div class="list_top">
									<p class="f_L">
										<input type="text" class="input" id="startDate" placeholder="시작일">
										<input type="text" class="input" id="endDate" placeholder="종료일">
										<select class="select" id="sel_searchType">
											<option value="">▶검색종류선택</option>
											<option value="userName">이 름</option>
											<option value="phoneNumber">전화번호</option>
										</select>
										<input type="text" class="input" id="search_value" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){fn_search('new'); return false;}">
										<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
											<span class="icon-search">검색</span>
										</a>
									</p>
									<p class="f_R">
										리스트 : <span id="l_pageCnt"></span>
										<a href="javascript:gatchaUseExcelDownload();" class="button compact black-gradient glossy">
											<span class="icon-download">엑셀다운로드</span>
										</a>
									</p>
								</div>
							</div>
							<br>
							<table class="table_list">
								<colgroup>
									<col width="*">
									<col width="*">
									<col width="*">
									<col width="*">
									<col width="*">
									<col width="*">
									<col width="*">
									<col width="*">
								</colgroup>
								<thead>
								<tr>
									<th>전화번호</th>
									<th>이 름</th>
									<th>아이템 이름</th>
									<th>아이템 코드</th>
									<th>콘 가격</th>
									<th>팝콘 가격</th>
									<th>획득 팝콘</th>
									<th>사용일</th>
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
</form>	
</body>
</html>
<script>
$("#menu_03_09").addClass("active");</script>