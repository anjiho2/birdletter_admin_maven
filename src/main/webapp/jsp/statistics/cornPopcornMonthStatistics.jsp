<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<jsp:include page="/daul/include/header.jsp" flush="false"/>
 
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/statisticsService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">
function init() {
	yearSelectBox("l_year");
}

function search() {
	var year = $("#sel_year option:selected").val();
	var statType = $("#sel_statType option:selected").val();
	
	if (statType == "") {
		alert("검색종류를 선택하세요.");
		$("#sel_statType").focus();
		return;
	}
	if (year == "") {
		alert("년도를 선택하세요.");
		$("#sel_year").focus();
		return;
	}
	
	if (statType == "count") {
		$("#container2").hide();
		$("#container").show();
		statisticsService.cornSaleStatistics(year, statType, function(selList) {
			statisticsService.popcornSaleStatistics(year, statType, function(selList2) {
			for (var i = 0; i < selList2.length; i++) {
			
		    $('#container').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '콘/팝콘판매 월별 건수통계'
		        },
		        subtitle: {
		            text: year+'년도'
		        },
		        xAxis: {
		            categories: [
		                '1월', '2월', '3월', '4월', '5월', '6월',
		                '7월', '8월', '9월', '10월', '11월', '12월'
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '건수(개)'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b>{point.y:1f} 건</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: [
				                 {
						            name: '콘판매건수',
						            data: [selList[0].cnt, selList[1].cnt, selList[2].cnt, selList[3].cnt, selList[4].cnt, selList[5].cnt, 
				                           selList[6].cnt, selList[7].cnt, selList[8].cnt, selList[9].cnt, selList[10].cnt, selList[11].cnt]
				
				        		},
				        		{
				                    name: '팝콘판매건수',
				                    data: [selList2[0].cnt, selList2[1].cnt, selList2[2].cnt, selList2[3].cnt, selList2[4].cnt, selList2[5].cnt, 
				                           selList2[6].cnt, selList2[7].cnt, selList2[8].cnt, selList2[9].cnt, selList2[10].cnt, selList2[11].cnt]
				
				                }
			       	 		]
		    			});
					}
				});
			});
		} else if (statType == "amount") {
			$("#container2").show();
			$("#container").hide();
			
			statisticsService.cornSaleStatistics(year, statType, function(selList) {
				statisticsService.popcornSaleStatistics(year, statType, function(selList2) {
				for (var i = 0; i < selList2.length; i++) {
			    $('#container2').highcharts({
			        chart: {
			            type: 'column'
			        },
			        title: {
			            text: '콘/팝콘판매 월별 액수통계'
			        },
			        subtitle: {
			            text: year+'년도'
			        },
			        xAxis: {
			            categories: [
			                '1월', '2월', '3월', '4월', '5월', '6월',
			                '7월', '8월', '9월', '10월', '11월', '12월'
			            ],
			            crosshair: true
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '금액(원)'
			            }
			        },
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y:1f} 원</b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        plotOptions: {
			            column: {
			                pointPadding: 0.2,
			                borderWidth: 0
			            }
			        },
			        series: [
					                 {
							            name: '콘판매금액',
							            data: [selList[0].cnt, selList[1].cnt, selList[2].cnt, selList[3].cnt, selList[4].cnt, selList[5].cnt, 
					                           selList[6].cnt, selList[7].cnt, selList[8].cnt, selList[9].cnt, selList[10].cnt, selList[11].cnt]
					
					        		},
					        		{
					                    name: '팝콘판매금액',
					                    data: [selList2[0].cnt, selList2[1].cnt, selList2[2].cnt, selList2[3].cnt, selList2[4].cnt, selList2[5].cnt, 
					                           selList2[6].cnt, selList2[7].cnt, selList2[8].cnt, selList2[9].cnt, selList2[10].cnt, selList2[11].cnt]
					
					                }
				       	 		]
			    			});
						}
					});
				});
			}
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
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
					<span class="">판매</span>&nbsp;〉&nbsp;<span onclick="javascript:goStatistics('cornPopcornMonthStatistics');">콘&amp;팝콘판매 월간통계</span>
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
									<div id="container2" style="min-width: 310px; margin: 0 auto"></div>
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
$("#menu_05_01").addClass("active");
</script>