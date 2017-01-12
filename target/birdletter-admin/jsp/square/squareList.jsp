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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/letterService.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script type="text/javascript">

function init(val) {
	var letterType = val;
	if (letterType == undefined) {
		letterType = "popular";
	}
	letterTypeSquare(letterType, "l_letterType");
	
	var categoryName = "";
	if (letterType == "popular") categoryName = "인기편지";
	else if (letterType == "new") categoryName = "최신편지";
	else if (letterType == "best") categoryName = "베스트편지";
	else if (letterType == "report") categoryName = "신고편지";
	innerHTML("l_category", categoryName);
	
	memberSearchTypeSelectBox("", "l_letterSearch");
	sortTypeSquare("l_sort");
	
	fn_search("new");
}

function fn_search(val) {
	var phoneNumber = "";
	var userName = "";
	var sPage = getInputTextValue("sPage");
	var letterType = getSelectboxValue("sel_letterTypeSquare");
	var startDate = getInputTextValue("startDate");
	var endDate = getInputTextValue("endDate");
	var sortType = getSelectboxValue("sel_sortTypeSquare");
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("search_value"); 
	
	if (sPage == "sPage") sPage = "1";
	
	if (searchType == "userName") userName = searchValue;
	else if (searchType == "phoneNumber") phoneNumber = searchValue;
	
	dwr.util.removeAllRows("dataList");
	$("#emptys").hide();
	
	//테이블 리스트
	letterService.openLetterListCnt(letterType, startDate, 
			endDate, userName, phoneNumber, sortType, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '10', '10', '1');
		} else {
			if (new Number(cnt) < (10*sPage)) {
				if (new Number(cnt) < (10*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '10', '10', sPage);
		}
		letterService.openLetterList(sPage, letterType, startDate, 
				endDate, userName, phoneNumber, sortType, function(selList) {
			if (selList.length > 0) {
				dwr.util.removeAllRows("dataList");
				for (var i=0; i<selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						//checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.openLetterId+"'/>";
						letterIdHTML = '<a href="#" onClick="javascript:goDetailSquare('+"'"+cmpList.openLetterId+"'"+');">'+cmpList.openLetterId+'</a>';
						var cellData = [
						                //function(data) {return checkHTML;},
						                function(data) {return letterIdHTML},
						                function(data) {return getDateTimeSplitComma(cmpList.createDate)},
						                function(data) {return cmpList.phoneNumber},
						                function(data) {return cmpList.userName},
						                function(data) {return gfn_zeroToPipe(cmpList.weeklyRanking, "등");},
						                function(data) {return gfn_zeroToPipe(cmpList.bestRanking, "등");},
						                function(data) {return addThousandSeparatorCommas(gfn_zeroToZero(cmpList.heartCount));},
						                function(data) {return addThousandSeparatorCommas(gfn_zeroToZero(cmpList.sharedCount));},
						                function(data) {return addThousandSeparatorCommas(gfn_zeroToZero(cmpList.letterCommentCount));},
						                function(data) {return addThousandSeparatorCommas(gfn_zeroToZero(cmpList.letterReportCount));}
						                ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					} 
				}
			} else {
				gfn_printPageNum_new('0', '10', '10', '1');
				gfn_emptyView('v', comment.blank_list);
			}
		});
	});
}

function goDetailSquare(val) {
	var letterId = val;
	innerValue("letter_id", letterId);
	with (document.frm) {
		page_gbn.value = "squareListDetail";
		action = getContextPath()+"/square.do";
		submit();
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init()"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="start_date" id="start_date" value="">
<input type="hidden" name="end_date" id="end_date" value="">
<input type="hidden" name="letter_id" id="letter_id">
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
					<span class="">광장</span>&nbsp;〉&nbsp;<span onclick="javascript:goPage('square','squareList');">편지 리스트</span>&nbsp;〉&nbsp;<span id="l_category"></span>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
					<div><span id="l_letterType"></span></div><br><br>
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>광장 편지 리스트</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<input type="text" class="input" id="startDate" placeholder="시작일">
											<input type="text" class="input" id="endDate" placeholder="종료일">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a> &nbsp;&nbsp;&nbsp;
											
											<span id="l_letterSearch"></span>
											<input type="text" class="input" id="search_value" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){fn_search('new'); return false;}">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
										</p>
										<p class="f_R">
											<span id="l_sort"></span>
										</p>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<!-- <col width="2%" /> -->
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
									</colgroup>
									<thead>
									<tr>
										<!-- <th><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th> -->
										<th>편지번호</th>
										<th>작성일</th>
										<th>핸드폰번호</th>
										<th>이 름</th>
										<th>인기 순위</th>
										<th>베스트 순위</th>
										<th>하 트</th>
										<th>공 유</th>
										<th>댓 글</th>
										<th>신 고</th>
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
$("#menu_13_01").addClass("active");
</script>