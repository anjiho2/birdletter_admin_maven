
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<!-- 상단 메뉴 jsp --> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/statisticsService.js"></script>
<!-- 페이지 이동 관련 스크립트 -->
<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>
<!-- 그래프 스크립트 -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">
function init() {
	drawChart();
	drawChart2();
	drawChart3();
}

//성별 가입자 수
function drawChart(val) {
	var termType;
	if (val == undefined) termType = "";
	else termType = val;
	
	//버튼 이미지 변경
	if (termType == "") {
		$("#all_btn").attr("class","button compact blue-gradient glossy active");
		$("#week_btn").attr("class","button compact blue-gradient glossy");
		$("#month_btn").attr("class","button compact blue-gradient glossy");
		$("#year_btn").attr("class","button compact blue-gradient glossy");
	} else if (termType == "week") {
		$("#all_btn").attr("class","button compact blue-gradient glossy");
		$("#week_btn").attr("class","button compact blue-gradient glossy active");
		$("#month_btn").attr("class","button compact blue-gradient glossy");
		$("#year_btn").attr("class","button compact blue-gradient glossy");
	} else if (termType == "month") {
		$("#all_btn").attr("class","button compact blue-gradient glossy");
		$("#week_btn").attr("class","button compact blue-gradient glossy");
		$("#month_btn").attr("class","button compact blue-gradient glossy active");
		$("#year_btn").attr("class","button compact blue-gradient glossy");
	} else if (termType == "year") {
		$("#all_btn").attr("class","button compact blue-gradient glossy");
		$("#week_btn").attr("class","button compact blue-gradient glossy");
		$("#month_btn").attr("class","button compact blue-gradient glossy");
		$("#year_btn").attr("class","button compact blue-gradient glossy active");
	}
	
	var subTitle = "";
	if (termType == "") subTitle = "전체";
	else if (termType == "week") subTitle = "최근 일주일";
	else if (termType == "month") subTitle = "현재월";
	else if (termType == "year") subTitle = "1년";
	
	//성별 가입자 통계 그래프
	statisticsService.userGenderStatsics(termType, function(selList) {
		if (selList.length > 0) {
			var total = selList[0].cnt + selList[1].cnt;
			var melePercent = Math.round(selList[0].cnt / total * 100);
			var femalePercent = Math.round(selList[1].cnt / total * 100);
			
			$("#div_1").show();
			$("#div_1_1").hide();
			for (var i = 0; i < selList.length; i++) {
				$("#container").highcharts({
					chart: {
						type: "pie",
						options3d: {
							enabled: true,
							alpha: 45
						}
					},
					title: {text:'성별 가입자'},
					subtitle: {text: subTitle},
					plotOptions: {
						pie: {
							innerSize : 100,
							depth : 60
						}
					}, 
					credits: {
			            enabled: false
			        },
					series: [{
						name:'건수',
						data: [
							[ "남성("+melePercent+"%)", selList[0].cnt ],
							[ "여성("+femalePercent+"%)", selList[1].cnt ]
						]
					}]
				});
			}
		} else {
			$("#div_1").hide();
			$("#div_1_1").show();
			$("#data_none").html("데이터가 없습니다.");
		}
	});
}
//성별 메세지 발송 건수
function drawChart2(val) {
	var termType;
	if (val == undefined) termType = "";
	else termType = val;
	
	var subTitle = "";
	if (termType == "") subTitle = "전체";
	else if (termType == "week") subTitle = "최근 일주일";
	else if (termType == "month") subTitle = "현재월";
	else if (termType == "year") subTitle = "1년";

	statisticsService.messageSendStaticsGroupByGender(termType, function(selList) {
		console.log("1");
		if (selList.length > 0) {
			console.log("2");
			var maleCnt = selList[0].cnt;
			var femaleCnt = selList[1].cnt;
			var total = maleCnt + femaleCnt;
			var malePercent = Math.round(maleCnt / total * 100);
			var femalePercent = Math.round(femaleCnt / total * 100);
			
			$("#div_2").show();
			$("#div_2_1").hide();
			
			for (var i = 0; i < selList.length; i++) {
				$("#container2").highcharts({
					chart : {
						type : "pie",
						options3d : {
							enabled : true,
							alpha : 45
						}
					},
					title : {text : "성별 메세지 발송"},
					subtitle : {text : subTitle},
					plotOptions : {
						pie : {
							innerSize : 100,
							depth : 60
						}
					},
					credits: {
			            enabled: false
			        },
					series : [{
						name : "건수",
						data : [
								[ "남성("+malePercent+"%)", maleCnt],
								[ "여성("+femalePercent+"%)", femaleCnt]
						]
					}]
				});
			}
		} else {
			$("#div_2").hide();
			$("#div_2_1").show();
		}
	});
}

//연령별 가입자수, 메세지발송수
function drawChart3(val) {
	var termType;
	if (val == undefined) termType = "";
	else termType = val;
	
	//버튼 이미지 변경
	if (termType == "") {
		$("#all_gender_btn").attr("class","button compact blue-gradient glossy active");
		$("#week_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#month_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#year_gender_btn").attr("class","button compact blue-gradient glossy");
	} else if (termType == "week") {
		$("#all_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#week_gender_btn").attr("class","button compact blue-gradient glossy active");
		$("#month_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#year_gender_btn").attr("class","button compact blue-gradient glossy");
	} else if (termType == "month") {
		$("#all_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#week_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#month_gender_btn").attr("class","button compact blue-gradient glossy active");
		$("#year_gender_btn").attr("class","button compact blue-gradient glossy");
	} else if (termType == "year") {
		$("#all_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#week_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#month_gender_btn").attr("class","button compact blue-gradient glossy");
		$("#year_gender_btn").attr("class","button compact blue-gradient glossy active");
	}
	
	var subTitle = "";
	if (termType == "") subTitle = "전체";
	else if (termType == "week") subTitle = "최근 일주일";
	else if (termType == "month") subTitle = "현재월";
	else if (termType == "year") subTitle = "1년";
	
	var letterType = $("#sel_letterType option:selected").val();
	if (letterType == undefined) {
		letterType = "private";
	}
	
	letterTypeSelectBox("l_letterType", letterType);
	
	statisticsService.userAgeStatics(termType, function(selList) {
		if (letterType == "private") {
			statisticsService.messageSendStaticsGroupByAges(termType, function(selList2) {
				if (selList.length> 0 && selList2.length > 0) {
					$("#container3").highcharts({
						chart : {
							type : "column"
						},
						title : {text : "연령별 가입자, 개인 편지 발송"},
						subtitle : {text : subTitle},
						xAxis : {
							categories : [
								"10대","20대","30대","40대","50대","60대","70대","80대",
							],
							crosshair : true
						},
						yAxis : {
							min : 0,
							title : "건수"
						},
						tooltip : {
							headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
				            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				                '<td style="padding:0"><b>{point.y:1f} 건</b></td></tr>',
				            footerFormat: '</table>',
				            shared: true,
				            useHTML: true
						},
						plotOptions : {
							column : {
								pointPadding : 0.2,
								borderWidth : 0
							}
						},
						credits: {
				            enabled: false
				        },
						series : [
							{
								name : "가입자 수",
								data : [
									selList[0].cnt, selList[1].cnt, selList[2].cnt, selList[3].cnt,
									selList[4].cnt, selList[5].cnt, selList[6].cnt, selList[7].cnt
								]
							},
							{
								name : "메세지 발송 수",
								data : [
									selList2[0].cnt, selList2[1].cnt, selList2[2].cnt, selList2[3].cnt,
									selList2[4].cnt, selList2[5].cnt, selList2[6].cnt, selList2[7].cnt
								]
							},
						]
					});
				}
			});
		} else if (letterType == "open") {
			statisticsService.openLetterStaticsGroupByAge(termType, function(selList2) {
				if (selList.length> 0 && selList2.length > 0) {
					$("#container3").highcharts({
						chart : {
							type : "column"
						},
						title : {text : "연령별 가입자, 공개편지 등록"},
						subtitle : {text : subTitle},
						xAxis : {
							categories : [
								"10대","20대","30대","40대","50대","60대","70대","80대",
							],
							crosshair : true
						},
						yAxis : {
							min : 0,
							title : "건수"
						},
						tooltip : {
							headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
				            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				                '<td style="padding:0"><b>{point.y:1f} 건</b></td></tr>',
				            footerFormat: '</table>',
				            shared: true,
				            useHTML: true
						},
						plotOptions : {
							column : {
								pointPadding : 0.2,
								borderWidth : 0
							}
						},
						credits: {
				            enabled: false
				        },
						series : [
							{
								name : "가입자 수",
								data : [
									selList[0].cnt, selList[1].cnt, selList[2].cnt, selList[3].cnt,
									selList[4].cnt, selList[5].cnt, selList[6].cnt, selList[7].cnt
								]
							},
							{
								name : "공개편지 등록 수",
								data : [
									selList2[1].cnt, selList2[2].cnt, selList2[3].cnt, selList2[4].cnt,
									selList2[5].cnt, selList2[6].cnt, selList2[7].cnt, selList2[8].cnt
								]
							},
						]
					});
				}
			});
		}
	});
}

function changeMessageType() {
	var messageType = $("#sel_letterType option:selected").val();
	
	if (messageType != undefined) {
		drawChart3();
	}
}

function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var searchDate = getInputTextValue("startDate");
	var dayCount = getInputTextValue("dayCount");

	if (input_blank_check("startDate", comment.select_search_date) == false) return;
	if (input_blank_check("dayCount", "N일을 입력하세요.") == false) return;

	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	gfn_emptyView("H", "");
	
	statisticsService.dormantUserStatisticsCnt(searchDate, dayCount, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '20', '20', '1');
		} else {
			if (new Number(cnt) < (20*sPage)) {
				if (new Number(cnt) < (20*(sPage-1))) {
					sPage = 1;
					$("#sPage").val(sPage);
				}
			}
			gfn_printPageNum_new(cnt, '20', '20', sPage);
		}
	});
	statisticsService.dormantUserStatistics(sPage, searchDate, dayCount, function(selList) {
		if (selList.length > 0) {
			for (var i=0; i<selList.length; i++) {
				cmpList = selList[i];
				if (cmpList != undefined) {
					var cellData = [
					                function(data) {return cmpList.userId},
					                function(data) {return cmpList.userName},
					                function(data) {return cmpList.gender},
					                function(data) {return cmpList.birthDay+"세"},
					                function(data) {return getDateTimeSplitComma(cmpList.createDate);}
					                ];
					dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
				}
			}
		} else {
			gfn_emptyView('v', comment.blank_result);
		}
	});
}

function dormantUserExcelDownload() {
	with (document.frm) {
		search_date.value = startDate.value;
		day_count.value = dayCount.value;
		action = "excel/dormantUserList.do";
		submit();
	}
}

function retensionExcelDownload(val) {
	with (document.frm) {
		search_date.value = startDate2.value;
		day_count.value = dayCount2.value;
		retension_type.value = val;
		
		if (val == "dau") {
			action = "excel/retension.do";	
		} else if (val == "reg") {
			action = "excel/retension.do";
		}
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
<input type="hidden" name="search_date" id="search_date">
<input type="hidden" name="day_count" id="day_count">
<input type="hidden" name="retension_type" id="retension_type">
	<!--  S : leftarea-->
	<div class="leftarea">
		<!--  S : nav_area-->
		<jsp:include page="/daul/include/nav.jsp" flush="false"/>
		<!--  E : nav_area-->
	</div>
	<!-- E : lefrarea -->
	<!--  S: right area-->
	<div class="rightarea span8" >
		<div class="subbody">
		<div class="contentbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">통계</span>&nbsp;〉&nbsp;<span onclick="javascript:goStatistics('userStatistics');">사용자 통계</span> 
				</div>
				<div>
				<p class="f_L">
					<input id="all_btn" type="button" class="button compact blue-gradient glossy" value="전체" onclick="drawChart();drawChart2();">
					<input id="week_btn" type="button" class="button compact blue-gradient glossy" value="최근일주일" onclick="drawChart('week');drawChart2('week');">
					<input id="month_btn" type="button" class="button compact blue-gradient glossy" value="현재월" onclick="drawChart('month');drawChart2('month');">
					<input id="year_btn" type="button" class="button compact blue-gradient glossy" value="1년" onclick="drawChart('year');drawChart2('year');">
				</p>
				</div>
				<br><br><br><br><br><br>
				<div class="span5">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_datareport "></span>
								<span>성별 가입자 그래프</span>
							</div>
							<div class="portlet-content">
								<div class="content" id="div_1">
									<div id="container" style="min-width: 100px; height: 400px; margin: 0 auto"></div>
								</div>	
								<div class="content" id="div_1_1" style="display: none;">
									<span id="data_none"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="span5">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_datareport "></span>
								<span>성별 메세지 발송 그래프</span>
							</div>
							<div class="portlet-content">
								<div class="content" id="div_2">
									<div id="container2" style="min-width: 100px; height: 400px; margin: 0 auto"></div>
								</div>
								<div class="content" id="div_2_1" style="display: none;">
									그래프의 데이터가 부족합니다
								</div>		
							</div>
						</div>
					</div>
				</div>
			</div>
			<br><br>
			<div>
			<p class="f_L">
				<input id="all_gender_btn" type="button" class="button compact blue-gradient glossy" value="전체" onclick="drawChart3();">
				<input id="week_gender_btn" type="button" class="button compact blue-gradient glossy" value="최근일주일" onclick="drawChart3('week');">
				<input id="month_gender_btn" type="button" class="button compact blue-gradient glossy" value="현재월" onclick="drawChart3('month');">
				<input id="year_gender_btn" type="button" class="button compact blue-gradient glossy" value="1년" onclick="drawChart3('year');">
			</p>
			</div>
			<br><br><br><br>
				<div class="span10">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_datareport"></span>
								<span>연령별 가입자, 개인 편지 발송 그래프</span>
							</div>
							<div class="portlet-content">
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<span id="l_letterType"></span>
										</p>
									</div>
									<div id="container3" style="min-width: 100px; height: 400px; margin: 0 auto"></div>
								</div>	
							</div>
						</div>
					</div>
				</div>
				<div class="span10">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_datareport "></span>
								<span>휴먼 사용자 지표</span>
							</div>
							<div class="portlet-content">
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<input type="text" class="input" id="startDate" placeholder="검색일">
											<input type="text" class="input" id="dayCount" placeholder="N일">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
										</p>
										<p class="f_R">
											<a href="javascript:dormantUserExcelDownload();" class="button compact black-gradient glossy">
												<span class="icon-download">엑셀다운로드</span>
											</a>
										</p>	
									</div>
									<br>
									<table class="table_list">
										<colgroup>
											<col width="5%" />
											<col width="15%" />
											<col width="5%" />
											<col width="5%" />
											<col width="15%" />
										</colgroup>
										<thead>
											<tr>
												<th>ID</th>
												<th>이 름</th>
												<th>성 별</th>
												<th>나 이</th>
												<th>가입일</th>
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
				
				<div class="span10">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_datareport "></span>
								<span>N일 리텐션 다운로드</span>
							</div>
							<div class="portlet-content">
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<input type="text" class="input" id="startDate2" placeholder="검색일">
											<input type="text" class="input" id="dayCount2" placeholder="N일">
											<a href="javascript:retensionExcelDownload('dau');" class="button compact black-gradient glossy">
												<span class="icon-download">누적 리텐션 엑셀다운로드</span>
											</a>
											<a href="javascript:retensionExcelDownload('reg');" class="button compact black-gradient glossy">
												<span class="icon-download">신규 리텐션 엑셀다운로드</span>
											</a>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div>
					<p class="f_L">
						<a href="jsp/popup/insert_download_count.jsp" id="insertBtn" class="button blue-gradient glossy">
							<span class="icon-pencil">다운로드수 등록</span>
						</a>
					</p>
			</div>
			</div>
		</div>
	<!--  E: contents-->
	</div>
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->
</form>
</body>
</html>
<script>
$("#menu_05_04").addClass("active");
$("#insertBtn").pageslide({direction:"left"});
</script>