<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<!-- 상단 메뉴 jsp --> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/statisticsService.js"></script>
<!-- 달력 스크립트 -->
<script src="<%=webRoot%>/js/datepicker.js"></script>
<!-- 페이지 이동 관련 스크립트 -->
<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/few-weeks.js"></script>
<!-- 그래프 스크립트 -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<!-- 테이블 스크롤 CSS -->
<%-- <link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/table_scroll.css"> --%>

<script type="text/javascript">
function init() {
	chart();
	yearSelectBox("l_year", 'weekSelectBox("l_week", "week_div")');
	fn_search("new");
}

function changeSelectBox() {
	var searchType = $("#sel_searchType").val();
	/** 검색종류선택 셀렉트 박스 이벤트 **/
	if (searchType == "userName"){
		$("#cornName_div").show();
		$("#startDate_div").hide();
		$("#pipe_div").hide();
		$("#endDate_div").hide();
	} else if (searchType == "sendDate"){
		$("#cornName_div").hide();
		$("#startDate_div").show();
		$("#pipe_div").show();
		$("#endDate_div").show();
	}
}

function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var searchType = $("#sel_searchType").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var userName = $("#userName").val();
	var selectGender = $("#select_gender option:selected").val();
	var letterType = "";
	
	letterTypeSelectBox("l_letter", "open");
	
	if (val == "new") sPage = "1";	//페이징 초기값 셋팅
	
	if (searchType == "sendDate") {
		if (startDate == "" && endDate != "") {
			alert("시작일을 선택하세요.");
			$("#startDate").focus();
			return false;
		}
		if (startDate != "" && endDate == "") {
			alert("종료일을 선택하세요.");
			$("#endtDate").focus();
			return false;
		}
		if (startDate != "" && endDate != ""){
			userName = "";
		}
	} else if (searchType == "userName") {
		if (userName != "") {
			startDate = "";
			endDate = "";
		}
	}
	
	dwr.util.removeAllRows("dataList");
	
	statisticsService.userOpenLetterStatisticsCnt(startDate, endDate, userName, selectGender, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '15', '20', '1');
			gfn_emptyView("v", "결과가 없습니다");
		}  else {
			if (new Number(cnt) < (20*sPage)) {
				if (new Number(cnt) < (20*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '20', sPage);
		}
		statisticsService.userOpenLetterStatistics(sPage, startDate, endDate, userName, selectGender, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						var cellData = [
							function(data) {return cmpList.ROWNUM;},
							function(data) {return cmpList.user_name;},
							function(data) {return cmpList.gender == "MALE"?cmpList.gender="남성":cmpList.gender="여성"},
							function(data) {return cmpList.phone_number;},
							function(data) {return cmpList.cnt+"회";},
						]
						dwr.util.addRows("dataList", [0], cellData, {
							cellCreator:function(options) {
								var td = document.createElement("td");
								td.style.width = "20%";
								return td; 
							},
							escapeHtml:false
						});
					}
				}
			} else {
				var cellData = [function(data) {return "목록이 없습니다.";}]
				dwr.util.addRows("dataList", [0], cellData, {
					cellCreator:function(options) {
						var td = document.createElement("td");
						td.style.width = "100%";
						td.align = "center";
						return td; 
					},
					escapeHtml:false
				});
			}		
		});
	});
}

function chart() {
	var startDate = "";
	var endDate = "";
	var userName = "";
	
	statisticsService.userOpenLetterStatistics(0, startDate, endDate, userName, "", function(selList) {
		if (selList != null) {
			if (selList.length < 10) {
				$("#container").hide();
				$("#graph_null").show();
			} else {
				$("#graph_null").hide();
				$("#container").highcharts({
					chart : {
						type : "pie",
						options3d : {
							enabled : true,
							alpha : 45
						}
					},
					title : {text : "공개편지 등록 상위10명"},
					subtitle : {text : ""},
					plotOptions : {
						pie : {
							innerSize : 100,
							depth : 46
						}
					},
					credits: {
			            enabled: false
			        },
					series : [{
						name : "등록횟수",
						data : [
							[selList[0].user_name+"(1위)", selList[0].cnt],
							[selList[1].user_name+"(2위)", selList[1].cnt],
							[selList[2].user_name+"(3위)", selList[2].cnt],
							[selList[3].user_name+"(4위)", selList[3].cnt],
							[selList[4].user_name+"(5위)", selList[4].cnt],
							[selList[5].user_name+"(6위)", selList[5].cnt],
							[selList[6].user_name+"(7위)", selList[6].cnt],
							[selList[7].user_name+"(8위)", selList[7].cnt],
							[selList[8].user_name+"(9위)", selList[8].cnt],
							[selList[9].user_name+"(10위)", selList[9].cnt],
						]
					}]
				});
			}
		} 
	});
}

function dailyStats() {
	var searchDate = $("#searchDate").val();
	if (searchDate == "") {
		alert("검색일을 입력하세요.");
		$("#searchDate").focus();
		return;
	}
	var todayLabel = getInputDayLabel(searchDate);	//요일 가져오기
	statisticsService.userMessageSendByDailyStats(searchDate, "OPEN", function(selList) {
		$("#container2").show();
		if (selList.length > 0) {
		    $('#container2').highcharts({
		        chart: {
		            type: 'line'
		        },
		        title: {
		            text: '시간대별 등록 건수'
		        },
		        subtitle: {
		            text: searchDate+"("+todayLabel+")"
		        },
		        xAxis: {
		            categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11',
		                         '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
		        },
		        yAxis: {
		            title: {
		                text: '등록 건수'
		            }
		        },
		        plotOptions: {
		            line: {
		                dataLabels: {
		                    enabled: true
		                },
		                enableMouseTracking: false
		            }
		        },
		        credits: {
		            enabled: false
		        },
		        series: [{
		            name: '등록건수',
		            data: 
		            	 [
		                   selList[0].cnt, selList[1].cnt, selList[2].cnt, selList[3].cnt, selList[4].cnt,
		                   selList[5].cnt, selList[6].cnt, selList[7].cnt, selList[8].cnt, selList[9].cnt,
		                   selList[10].cnt, selList[11].cnt, selList[12].cnt, selList[13].cnt, selList[14].cnt,
		                   selList[15].cnt, selList[16].cnt, selList[17].cnt, selList[18].cnt, selList[19].cnt,
		                   selList[20].cnt, selList[21].cnt, selList[22].cnt, selList[23].cnt
		                   ]
		        }]
		    });
		}
	});
}

function weekStats() {
	var year = $("#sel_year option:selected").val();
	var week = $("#sel_week option:selected").val();
	if (year == "") {
		alert("년도를 선택하세요.");
		$("#sel_year").focus();
		return;
	}
	if (week == "") {
		alert("주차를 선택하세요.");
		$("#sel_week").focus();
		return;
	}
	var startWeek = makeYYYY_MM_DD(find_start_week(year, week));
	var endWeek = makeYYYY_MM_DD(find_end_week(year, week));

	statisticsService.userMessageSendByWeekStats(startWeek, endWeek, "OPEN", function(selList) {
		$("#container3").show();
		$('#container3').highcharts({
	        title: {
	            text: '주차별 통계',
	            x: -20 //center
	        },
	        subtitle: {
	            text: startWeek + " ~ " + endWeek,
	            x: -20
	        },
	        xAxis: {
	            categories: [selList[0].week, selList[1].week, selList[2].week, selList[3].week, 
	                         selList[4].week, selList[5].week, selList[6].week]
	        },
	        yAxis: {
	            title: {
	                text: '등록(건)'
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
	        credits: {
	            enabled: false
	        },
	        series: [{
	            name: '등록',
	            data: [selList[0].cnt, selList[1].cnt, selList[2].cnt, selList[3].cnt, selList[4].cnt, selList[5].cnt, selList[6].cnt]
	      	  }]
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
<!--  S : leftarea-->
	<div class="leftarea">
		<!--  S : nav_area-->
		<jsp:include page="/daul/include/nav.jsp" flush="false"/>
		<!--  E : nav_area-->
	</div>
	<!-- E : lefrarea -->
	<!--  S: right area-->
	<div class="rightarea span10" >
		<div class="subbody">
		<div class="contentbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">통계</span>&nbsp;〉&nbsp;<span onclick="javascript:goStatistics('userMessageSendStatistics');">메세지 발송 통계</span> 
				</div>
				<div>
					<p class="f_L">
						<span id="l_letter"></span>
					</p>
				</div>
				<br><br><br><br><br><br>
				<div class="span10">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_datareport "></span>
								<span>그래프</span>
							</div>
							<div class="portlet-content">
								<div class="content">
									<div id="container" style="min-width: 100px; height: 400px; margin: 0 auto"></div>
									<div id="graph_null" style="display:none;">
										데이터가 충분하지 않습니다..
									</div>
								</div>	
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<br><br>
			<div class="span10">
				<div class="content">
					<div class="portlet">
						<div class="portlet-header anthracite-gradient">
							<span class="icon_datareport"></span>
							<span>공개편지 시간대별 그래프</span> &nbsp;&nbsp;&nbsp;
							<input type="text" class="input" id="searchDate" placeholder="검색일">
							<!-- <input type="text" id="jydate1" class="input" data-minyear="2000" data-maxyear="2014" data-startdate="2010-09-22" readonly=""> -->
							<input type="button" onclick="javascript:dailyStats();" class="button compact blue-gradient glossy" value="검색">
						</div>
						<div class="portlet-content">
							<div class="content">
								<div id="container2" style="min-width: 100px; margin: 0 auto"></div>
							</div>	
						</div>
					</div>
				</div>
			</div>
			
			<br><br>
			<div class="span10">
				<div class="content">
					<div class="portlet">
						<div class="portlet-header anthracite-gradient">
							<span class="icon_datareport"></span>
							<span>공개편지 주차별 그래프</span> &nbsp;&nbsp;&nbsp;
							<span id="l_year" style="padding: 2px;"></span>
							<span id="week_div" style="display: none; padding: 2px;">
								<span id="l_week" style="padding: 2px;"></span>
							</span>
							<input type="button" onclick="javascript:weekStats();" class="button compact blue-gradient glossy" value="검색">
						</div>
						<div class="portlet-content">
							<div class="content">
								<div id="container3" style="min-width: 100px; margin: 0 auto"></div>
							</div>	
						</div>
					</div>
				</div>
			</div>
			
			<br><br>
			<div class="span10">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>리스트</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
								<div class="list_top">
									<p class="f_L">
										<select id="sel_searchType" class="select" onchange="changeSelectBox();">
											<option value="">▶검색종류선택</option>
											<option value="userName">이름</option>
											<option value="sendDate">발송일</option>
										</select>
										<span id="cornName_div">
											<input type="text" class="input" id="userName"  placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode == 13){fn_search('new'); return false;}">
										</span>
										<span id="startDate_div" style="display: none;">
											<input type="text" class="input" id="startDate" placeholder="시작일">
										</span>
										<span id="endDate_div" style="display: none;">
											<input type="text" class="input" id="endDate" placeholder="종료일">
										</span>
										<span id="l_year"></span>
										<a href="javascript:searchCorn();" class="button compact blue-gradient glossy">
											<span class="icon-search">검색</span>
										</a>
									</p>
									<p class="f_R">
										<select id="select_gender" class="select" onchange="fn_search('new');">
											<option value="">▶정렬선택</option>
											<option value="MALE">남성</option>
											<option value="FEMALE">여성</option>
										</select>
									</p>
									</div>
									<br>
									<div >
									<table class="table_list">
										<colgroup>
											<col width="*" />
											<col width="*" />
											<col width="*" />
											<col width="*" />
											<col width="*" />
										</colgroup>
										<thead>
											<tr>
											<th>순위</th>
											<th>이름</th>
											<th>성별</th>
											<th>핸드폰번호</th>
											<th>발송횟수</th>
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
								<br>
							</div>
						</div>
					</div>
				</div>
			</div>
	<!--  E: contents-->
		</div>
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->
	</div>
</form>
</body>
</html>
<script>
$("#menu_05_05").addClass("active");
//기본 사용자 날짜 지정
//customDatepicker("jydate1");
</script>