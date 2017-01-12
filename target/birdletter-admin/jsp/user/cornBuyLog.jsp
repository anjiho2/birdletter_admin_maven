<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
	String userId = Util.isNullValue(request.getParameter("user_id"), "1");
	String phoneNumber = Util.isNullValue(request.getParameter("l_phoneNumber"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/userService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>
<script type="text/javascript">

function init() {
	$("#user_top_menu_3").attr("class","button silver-gradient glossy active");
	fn_search("new");
}

//페이징 리스트
function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var userId = '<%=userId%>';
	var searchType = getSelectboxValue("sel_searchType");
	var cornName = getInputTextValue("search_value");
	var startDate = getInputTextValue("startDate"); 	
	var endDate = getInputTextValue("endDate");
	
	/** 검색종류선택 셀렉트 박스 이벤트 **/
	if (searchType == "cornName"){
		$("#search_value").show();
		//$("#cornName_div").show();
		$("#startDate").hide();
		$("#endDate").hide();
	} else if (searchType == "createDate"){
		$("#search_value").hide();
		$("#startDate").show();
		$("#endDate").show();
	}
	
	/** 검색버튼 조건식 **/
	if (searchType == "cornName") {
		if (cornName != "") {
			startDate = "";
			endDate = "";	
		}
	} else if (searchType == "createDate") {
		if (startDate == "" && endDate != "") {
			alert(comment.select_start_date);
			focusInputText("startDate");
			return false;
		}
		if (startDate != "" && endDate == "") {
			alert(comment.select_end_date);
			focusInputText("endtDate");	
			return false;
		}  
		if (startDate != "" && endDate != ""){
			cornName = "";
		}
	}
	
	/** 페이징 시작 **/
	if (val == "new")
		sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");
	
	userService.userCornBuyLogListCnt(userId, cornName, startDate, endDate, function(cnt) {
		cnt == 0 ? $("#total_cnt").html("0") : $("#total_cnt").html(addThousandSeparatorCommas(cnt));
		innerHTMLAddColor("total_cnt", "red");
		if (cnt == 0) {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView("v", comment.blank_list);
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		
		userService.userCornBuyLogList(sPage, userId, cornName, startDate, endDate, function(selList) {
			if (selList.length > 0) {
				var sum = "";
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						var cellData = [
						        	function(data) {return cmpList.cornDesc;},
						        	function(data) {return addThousandSeparatorCommas(cmpList.cornCount);},
						        	function(data) {return cmpList.createDate;}
								];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false})
					}
					sum += Number(cmpList.cornCount);
					sum = parseFloat(gfn_isData(sum, "", "0", sum));
				}
				var cellData = [
				          function(data) {return "총합";},
				          function(data) {return addThousandSeparatorCommas(sum);},
				          function(data) {return "";}
						];
				dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
			}
		});
		userService.findUserCornPopcornBuySum(userId, "CORN", function(sum) {
			innerHTMLAddColor("corn_sum", addThousandSeparatorCommas(sum), "red");
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
		<%@ include file="/common/jsp/user_top_menu.jsp"%>
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">사용자</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('list');">사용자 리스트</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('cornBuyLog');">콘 구매내역</span>
				</div>
				<div class="span10" >
				<div class="title_d1">
					<h4><%=phoneNumber%>님</h4>
				</div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>콘 구매 정보</span>
									<select id="sel_searchType" class="select" onchange="init()">
										<option value="">▶검색종류선택</option>
										<option value="cornName">콘이름</option>
										<option value="createDate">구입일</option>
									</select>
									<input type="text" class="input width-small" id="search_value" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode == 13){fn_search('new'); return false;}">
									<input type="text" class="input" id="startDate" placeholder="시작일" style="display: none;">
									<input type="text" class="input" id="endDate" placeholder="종료일" style="display: none;">
									<button type="button" class="button blue-gradient glossy" onclick="javascript:fn_search('new');">검색</button>
									<!-- <input type="button" class="button blue-gradient glossy" value="엑셀다운로드" onclick="excelDownload('excel/userItemBuyList.do');"> -->
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											총 : <span id="total_cnt"></span>건 &nbsp;&nbsp;&nbsp;&nbsp;
											총 구입금액: <span id="corn_sum"></span>원 &nbsp;&nbsp;&nbsp;&nbsp;
										</p>
											<!-- 
											<p class="f_R">
												<span style="padding-left: 50px;">
													<a href="javascript:excelDownload('excel/userItemBuyList.do');" class="button compact black-gradient glossy">
														<span class="icon-download">엑셀다운로드</span>
													</a>
												</span>
											</p>
											 -->
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
										<!-- <th align="left"><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');">&nbsp;&nbsp;<input type="button" class="btn btn-primary btn-xs" onclick="javascript:checkVal();" value="삭제"></th> -->
										<th>콘 이름</th>
										<th>구입금액</th>
										<th>구입일</th>
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
	$("#menu_02_01").addClass("active");
</script>