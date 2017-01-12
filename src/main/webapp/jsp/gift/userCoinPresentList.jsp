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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/giftService.js"></script>

<script src="<%=webRoot%>/js/datepicker.js"></script>
<script type="text/javascript">
function init() {
	fn_search("new");
}

function fn_search(val) {
	var type = "COIN";
	var sPage = getInputTextValue("sPage");
	var startDate = getInputTextValue("startDate");
	var endDate = getInputTextValue("endDate");
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("search_value");
	var senderPhoneNumber = "";
	var receiverPhoneNumber = "";

	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	gfn_emptyView("H", "");
	
	if (searchType == "sender") {
		senderPhoneNumber = searchValue;
	} else if (searchType == "receiver") {
		receiverPhoneNumber = searchValue
	}
	giftService.userPresentListCnt(type, startDate, endDate, senderPhoneNumber, receiverPhoneNumber, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '15', '15', '1');
			//gfn_emptyView("v", "결과가 없습니다");
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		giftService.userPresentList(type, sPage, startDate, endDate, senderPhoneNumber, receiverPhoneNumber, function(selList) {
			if (selList.length > 0) {
				for (var i=0; i<selList.length; i++) {
					cmpList = selList[i];
					//checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"' />";
					var cellData = [
						                //function(data) {return checkHTML},
						                function(data) {return cmpList.sender},
						                function(data) {return cmpList.receiver},
						                function(data) {return getDateTimeSplitComma(cmpList.createDate)},
						                function(data) {return cmpList.coin},
						                function(data) {return gfn_isData(cmpList.openYn, 0, "수령전", "수령완료")},
						                function(data) {return gfn_isData(cmpList.openDate, null, "", getDateTimeSplitComma(cmpList.openDate))}
					                ]
					dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
				}
			} else {
				//사용자 프로필 리스트가 없을떄
				gfn_printPageNum_new('0', '15', '15', '1');
				gfn_emptyView('V', comment.blank_search_result);
			}
		});	
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init()"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<!--  S : rightarea -->
<div class="rightarea span10">
		<div class="subbody">
		<%@ include file="/common/jsp/present_top_menu.jsp"%>
			<div class="row-fluid">
				<div class="breadcrumb span10">
					선물&nbsp;〉&nbsp;선물하기 내역조회&nbsp;〉&nbsp;<span onclick="javascript:goPage('gift','userCoinPresentList');">캐시 선물</span>
				</div>
				<div><span id="l_presentType"></span></div><br>
				<!-- 사용자 리스트 영역 시작 -->
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>선물하기 내역조회</span>
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
											
											<select class="select" id="sel_searchType">
												<option value="">▶검색종류선택</option>
												<option value="sender">보낸사람 전화번호</option>
												<option value="receiver">받은사람 전화번호</option>
											</select>
											<input type="text" class="input" id="search_value" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){fn_search('new'); return false;}">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
										</p>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
									</colgroup>
									<thead>
									<tr>
										<th>보낸사람 전화번호</th>
										<th>받은사람 전화번호</th>
										<th>보낸 일시</th>
										<th>팝콘개수</th>
										<th>선물상태</th>
										<th>수령일시</th>
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
$("#present_top_menu_1").attr("class","button silver-gradient glossy active");
$("#menu_14_03").addClass("active");
</script>