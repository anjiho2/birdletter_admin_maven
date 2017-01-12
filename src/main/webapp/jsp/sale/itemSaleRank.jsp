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

<!-- 그래프 스크립트 -->
<script src="<%=webRoot%>/js/highcharts/highcharts.js"></script>
<script src="<%=webRoot%>/js/highcharts/modules/exporting.js"></script>
<script src="<%=webRoot%>/js/highcharts/highcharts-3d.js"></script>

<script type="text/javascript">

function init() {
	chart();
	fn_search("new");
}

function chart() {
	saleService.itemBuyTop10(function(selList) {
		for (var i = 0; i < 10; i++) {
			$('#container').highcharts({
		        chart: {
		            type: 'pie',
		            options3d: {
		                enabled: true,
		                alpha: 45
		            }
		        },
		        title: {
		            text: '아이템 판매 10위'
		        },
		        subtitle: {
		            text: ''
		        },
		        plotOptions: { 
		            pie: {
		                innerSize: 100,
		                depth: 45
		            }
		        },
		        credits: {
		            enabled: false
		        },
		        series: [{
		            name: '건수',
		            data: [
		                [selList[0].productName+"("+selList[0].cnt+"건)", selList[0].cnt],
		   			    [selList[1].productName+"("+selList[1].cnt+"건)", selList[1].cnt],
		   			    [selList[2].productName+"("+selList[2].cnt+"건)", selList[2].cnt],
		   			    [selList[3].productName+"("+selList[3].cnt+"건)", selList[3].cnt],
		   			    [selList[4].productName+"("+selList[4].cnt+"건)", selList[4].cnt],
		   			    [selList[5].productName+"("+selList[5].cnt+"건)", selList[5].cnt],
		   			    [selList[6].productName+"("+selList[6].cnt+"건)", selList[6].cnt],
		   			    [selList[7].productName+"("+selList[7].cnt+"건)", selList[7].cnt],
		   			    [selList[8].productName+"("+selList[8].cnt+"건)", selList[8].cnt],
		   			    [selList[9].productName+"("+selList[9].cnt+"건)", selList[9].cnt],
		            ]
		        }]
		    });
		}
	});
}

function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var productName = getInputTextValue("productName");
	
	//리스트 개수 셀렉트 박스
	var pagingCnt = getSelectboxValue("sel_pagingCnt");
	if (pagingCnt == undefined) pagingCnt = 20;
	pagingListSelectbox(pagingCnt, "l_pageCnt");
	
	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	gfn_emptyView("H", "");
	
	saleService.itemBuyRankCnt(productName, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new("0", "15", pagingCnt, "1");
			gfn_emptyView("v", "결과가 없습니다");
		} else {
			if (new Number(cnt) < (pagingCnt * sPage)) {
				if (new Number(cnt) < (pagingCnt * (sPage - 1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', pagingCnt, sPage);
		}
		
		saleService.itemBuyRank(sPage, pagingCnt, productName, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						var itemType = "";
						itemType = itemTypeName(cmpList.itemCategory, cmpList.itemType);
						var cellData = [
										  function(data) {return cmpList.rowNum;},
										  function(data) {return cmpList.productName;},
										  function(data) {return itemType;},
								          function(data) {return cmpList.cnt+"건";}
										 ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
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
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<!--  S : rightarea -->
<div class="rightarea span8">
		<div class="subbody">
		<div class="contentbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">판매</span>&nbsp;〉&nbsp;<span onclick="javascript:goSale('itemSaleRank');">아이템 판매 순위</span>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_datareport"></span><span>그래프</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div id="container" style="height: 400px"></div>
								</div>
								<br>
							</div>
						</div>
					</div>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>아이템 판매 순위</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<input type="text" class="input" id="productName" placeholder="아이템명" onkeypress="javascript:if(event.keyCode == 13){fn_search(); return false;}">
											<a href="javascript:fn_search();" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
										</p>
										<p class="f_R">
											리스트 : <span id="l_pageCnt"></span>
											<a href="javascript:excelDownload('excel/itemSaleRankList.do');" class="button compact black-gradient glossy">
												<span class="icon-download">엑셀다운로드</span>
											</a>
											<!-- <input type="button" class="button compact blue-gradient glossy" value="엑셀다운로드" onclick="excelDownload('excel/itemSaleRankList.do');"> -->
										</p>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<col width="*" />
										<col width="*" />
										<col width="*" />
									</colgroup>
									<thead>
									<tr>
										<th>순위</th>
										<th>아이템 명</th>
										<th>아이템 타입</th>
										<th>판매건수</th>
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
$("#menu_04_03").addClass("active");
</script>