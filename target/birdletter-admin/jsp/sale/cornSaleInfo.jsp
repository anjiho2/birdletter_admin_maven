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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/saleService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script type="text/javascript">
function init() {
	fn_search("new");
}

function fn_search(val) {
	var sPage = $("#sPage").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	/*
	if (startDate == "") {
		alert("시작일을 선택하세요.");
		$("#startDate").focus();
		return;
	}
	if (endDate == "") {
		alert("종료일을 선택하세요.");
		$("#endDate").focus();
		return;
	}
	*/
	if (sPage == "sPage") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	
	$("#emptys").hide();
	
	//전체 판매건수, 전체 판매금액
	saleService.getSumCornPopcornSale("CORN", function(result) {
		$("#l_cnt").html(result.cnt+"건  ").css("color","red");
		$("#totalSum").html(addThousandSeparatorCommas(result.cornCount)+"원").css("color","red");
	});
	
	saleService.countCornPopcornBuyLog(startDate, endDate, "CORN", function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '10', '10', '1');
			//gfn_emptyView("v", "결과가 없습니다");
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					$("#sPage").val(sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
	});
	
	//테이블 리스트
	saleService.totalCornPopcornSaleList(sPage, startDate, endDate, "CORN", function(selList) {
		if (selList.length > 0) {
			var cornSum = "";
			for (var i = 0; i < selList.length; i++) {
				cmpList = selList[i];
				if (cmpList != undefined) {
					
					var cellData = [
							  function(data) {return getDateTimeSplitComma(cmpList.createDate);},
							  function(data) {return cmpList.phoneNumber;},
							  function(data) {return cmpList.userName},
					          function(data) {return addThousandSeparatorCommas(getCornBuyPrice(cmpList.cornPoint));}    
							];
					dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
				}
				cornSum += Number(getCornBuyPrice(cmpList.cornPoint));
				cornSum = parseFloat(cornSum == "" ? 0:cornSum);
			}
			//총합 시작
			var	cellData = [
				function(data) {return cmpList.createDate="총합";},
				function(data) {return "";},   
				function(data) {return "";},
				function(data) {return addThousandSeparatorCommas(cornSum);}
			];
			dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
			//총합 끝
		} else {
			gfn_emptyView('v', "내역이 없습니다.");
		}
	});
}

function cornSaleListExcelDownload() {
	with (document.frm) {
		start_date.value = startDate.value;
		end_date.value = endDate.value;
		action = "excel/cornSaleList.do";
		submit();
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="itemType" id="itemType" value="CORN">
<input type="hidden" name="start_date" id="start_date" value="">
<input type="hidden" name="end_date" id="end_date" value="">
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
					<span class="">판매</span>&nbsp;〉&nbsp;<span onclick="javascript:goSale('cornSaleInfo');">콘 판매 내역</span>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>콘 판매 내역</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<input type="text" class="input" id="startDate" placeholder="시작일">
											<input type="text" class="input" id="endDate" placeholder="종료일">
											<a href="javascript:fn_search();" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
										</p>
										<p class="f_R">
											전체 판매건수 : <span id="l_cnt"></span> / 
											전체 판매금액 : <span id="totalSum"></span>
											<a href="javascript:cornSaleListExcelDownload();" class="button compact black-gradient glossy">
												<span class="icon-download">엑셀다운로드</span>
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
									</colgroup>
									<thead>
									<tr>
										<th>구매일</th>
										<th>핸드폰번호</th>
										<th>이 름</th>
										<th>판매금액</th>
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
$("#menu_04_01").addClass("active");
</script>