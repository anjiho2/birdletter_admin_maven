<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>
<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/statisticsService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">
function search() {
	var statType = $("#sel_statType option:selected").val();
	
	if (statType == "") {
		alert("검색종류를 선택하세요.");
		$("#sel_statType").focus();
		return;
	}
	
	if (statType == "count") {
			statisticsService.cornSaleYearStatistics(statType, function(selList) {
				statisticsService.popcornSaleYearStatistics(statType, function(selList2) {
					for (var i=0; i<5; i++) {
						//콘
						var year_cnt1 = 0;
						var year_cnt2 = 0;
						var year_cnt3 = 0;
						var year_cnt4 = 0;
						var year_cnt5 = 0;
						if (selList[0] != null) year_cnt1 = selList[0].cnt;
						if (selList[1] != null) year_cnt2 = selList[1].cnt;
						if (selList[2] != null) year_cnt3 = selList[2].cnt;
						if (selList[3] != null) year_cnt4 = selList[3].cnt;
						if (selList[4] != null) year_cnt5 = selList[4].cnt;
				
						//팝콘
						var year2_cnt1 = 0;
						var year2_cnt2 = 0;
						var year2_cnt3 = 0;
						var year2_cnt4 = 0;
						var year2_cnt5 = 0;
						if (selList2[0] != null) year2_cnt1 = selList2[0].cnt;
						if (selList2[1] != null) year2_cnt2 = selList2[1].cnt;
						if (selList2[2] != null) year2_cnt3 = selList2[2].cnt;
						if (selList2[3] != null) year2_cnt4 = selList2[3].cnt;
						if (selList2[4] != null) year2_cnt5 = selList2[4].cnt;
		
						$('#container').highcharts({
					        title: {
					            text: '콘/팝콘판매 년간통계',
					            x: -20 //center
					        },
					        subtitle: {
					            text: '',
					            x: -20
					        },
					        xAxis: {
					            categories: ['2016', '2017', '2018', '2019', '2020']
					        },
					        yAxis: {
					            title: {
					                text: '건수(건)'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }]
					        },
					        tooltip: {
					            valueSuffix: '건'
					        },
					        legend: {
					            layout: 'vertical',
					            align: 'right',
					            verticalAlign: 'middle',
					            borderWidth: 0
					        },
					        series: [{
					            name: '콘',
					            data: [year_cnt1,year_cnt2,year_cnt3,year_cnt4,year_cnt5]
					        }, {
					            name: '팝콘',
					            data: [year2_cnt1,year2_cnt2,year2_cnt3,year2_cnt4,year2_cnt5]
					        }]
					    });
					}
				});
			});
	} else if (statType == "amount") {
			statisticsService.cornSaleYearStatistics(statType, function(selList) {
				statisticsService.popcornSaleYearStatistics(statType, function(selList2) {
					for (var i=0; i<5; i++) {
						//콘
						var year_cnt1 = 0;
						var year_cnt2 = 0;
						var year_cnt3 = 0;
						var year_cnt4 = 0;
						var year_cnt5 = 0;
						if (selList[0] != null) year_cnt1 = selList[0].price;
						if (selList[1] != null) year_cnt2 = selList[1].price;
						if (selList[2] != null) year_cnt3 = selList[2].price;
						if (selList[3] != null) year_cnt4 = selList[3].price;
						if (selList[4] != null) year_cnt5 = selList[4].price;
				
						//팝콘
						var year2_cnt1 = 0;
						var year2_cnt2 = 0;
						var year2_cnt3 = 0;
						var year2_cnt4 = 0;
						var year2_cnt5 = 0;
						if (selList2[0] != null) year2_cnt1 = selList2[0].price;
						if (selList2[1] != null) year2_cnt2 = selList2[1].price;
						if (selList2[2] != null) year2_cnt3 = selList2[2].price;
						if (selList2[3] != null) year2_cnt4 = selList2[3].price;
						if (selList2[4] != null) year2_cnt5 = selList2[4].price;
		
						$('#container').highcharts({
					        title: {
					            text: '콘/팝콘판매 년간통계',
					            x: -20 //center
					        },
					        subtitle: {
					            text: '',
					            x: -20
					        },
					        xAxis: {
					            categories: ['2016', '2017', '2018', '2019', '2020']
					        },
					        yAxis: {
					            title: {
					                text: '액수(원)'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }]
					        },
					        tooltip: {
					            valueSuffix: ''
					        },
					        legend: {
					            layout: 'vertical',
					            align: 'right',
					            verticalAlign: 'middle',
					            borderWidth: 0
					        },
					        series: [{
					            name: '콘',
					            data: [year_cnt1,year_cnt2,year_cnt3,year_cnt4,year_cnt5]
					        }, {
					            name: '팝콘',
					            data: [year2_cnt1,year2_cnt2,year2_cnt3,year2_cnt4,year2_cnt5]
					        }]
					    });
					}
				});
			});
	}
}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
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
					<span class="">판매</span>&nbsp;〉&nbsp;<span onclick="javascript:goStatistics('cornPopcornYearStatistics');">콘&amp;팝콘판매 년간통계</span>
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
								<div class="list_top">
									<p class="f_L">
										<select class="select" id="sel_statType">
											<option value="">▶검색종류선택</option>
											<option value="count">판매건수</option>
											<option value="amount">판매액수</option>
										</select>  
										<span id="l_year"></span>
										<a href="javascript:search();" class="button compact blue-gradient glossy">
											<span class="icon-search">검색</span>
										</a>
									</p>
									</div>
									<div id="container" style="min-width: 310px;  margin: 0 auto"></div>
									<div id="container2" style="min-width: 310px;  margin: 0 auto"></div>
								</div>
								<br>
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
$("#menu_05_02").addClass("active");
</script>