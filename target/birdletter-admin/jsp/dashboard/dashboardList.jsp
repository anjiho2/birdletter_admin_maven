<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<jsp:include page="/daul/include/header.jsp" flush="false"/> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/statisticsService.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script src="<%=webRoot%>/js/highcharts/highcharts.js"></script>
<script src="<%=webRoot%>/js/highcharts/modules/exporting.js"></script>
<script src="<%=webRoot%>/js/highcharts/highcharts-3d.js"></script>

<!-- 다운로드수 로직 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/downloadStatistics.js"></script>
<!-- 가입수 로직 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/memberRegStatistics.js"></script>
<!-- DAU 로직 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/dauStatistics.js"></script>
<!-- MAU 로직 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/mauStatistics.js"></script>
<!-- ARPU 로직 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/arpuStatistics.js"></script>
<!-- ARPPU 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/arppuStatistics.js"></script>
<!-- 편지생성 수 로직 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/letterStatistics.js"></script>
<!-- 연령별 사용자 백분율 로직 스크립트 -->
<script src="<%=webRoot%>/js/dashboard/authStatisticsByAge.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="accumulateList();init();">
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="type" id="type">
<input type="hidden" name="search_date" id="search_date">
<input type="hidden" name="selYear" id="selYear">
<input type="hidden" name="selMonth" id="selMonth">
<input type="hidden" name="search_type" id="search_type">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<div id="loader" style="z-index:20;position:absolute;left:50%;top:50%;margin-left:0px;margin-top:5px;background:#fff;display:none;">
	<span><img src="<%=webRoot%>/img/ajax-loader.gif"></span>
</div>
<!--  S : rightarea -->
<div class="rightarea span10">
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<p class="a_C">
					누적다운로드 수 : <span id="l_totalDownload"></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					현재가입자 수 : <span id="l_totalReg"></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					누적편지생성 수 : <span id="l_totalLetter"></span>
					<span class="f_R">대시보드</span>
					</p>
				</div>
				<div>
					<select class="select" id="sel_searchType" onchange="init();">
						<option value="daily">-일별</option>
						<option value="weekly">-주별</option>
						<option value="monthly">-월별</option>
						<option value="year">-연별</option>
					</select>
					<input type="text" class="input" id="searchDate" placeholder="검색일" style="display: none;">
					<span id="l_year"></span>
					<span id="l_month" style="display: none;"></span>
					<input type="button" class="button compact blue-gradient glossy" id="searchBtn" value="검색" onclick="init();">
					<p class="f_R">
						<a href="javascript:dashboardExcelDownload();" class="button compact black-gradient glossy">
							<span class="icon-download">엑셀다운로드</span>
						</a>
					</p>
				</div>
				<!-- 사용자 리스트 영역 시작 -->
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>다운로드수</span>
							</div>
							<div class="portlet-content" >
								<div id="container" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 사용자 리스트 영역 끝 -->
				<!-- 아이템 리스트 영역 시작 -->
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>가입자 수</span>
							</div>
							<div class="portlet-content" >
								<div id="container2" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 아이템 리스트 영역 끝 -->
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>DAU</span>
							</div>
							<div class="portlet-content" >
								<div id="dau_container" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 사용자 리스트 영역 끝 -->
				<!-- 아이템 리스트 영역 시작 -->
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>MAU</span>
							</div>
							<div class="portlet-content" >
								<div id="mau_container" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>ARPU</span>
							</div>
							<div class="portlet-content" >
								<div id="arpu_container" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 사용자 리스트 영역 끝 -->
				<!-- 아이템 리스트 영역 시작 -->
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>ARPPU</span>
							</div>
							<div class="portlet-content" >
								<div id="arppu_container" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>연령별 사용자백분율</span>
							</div>
							<div class="portlet-content" >
								<div id="age_container" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 사용자 리스트 영역 끝 -->
				<!-- 아이템 리스트 영역 시작 -->
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span>편지 생성수<span></span>
							</div>
							<div class="portlet-content" >
								<div id="letter_container" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
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
<script type="text/javascript">
function init() {
	$("#loader").show();
	var sel_searchType = getSelectboxValue("sel_searchType");
	var searchDate = getInputTextValue("searchDate");
	var selYear = getSelectboxValue("sel_year");
	var selMonth = getSelectboxValue("sel_month");
	var title = "";
	
	if (sel_searchType == "daily") {
		$("#sel_year").val("");
		$("#sel_month").val("");
		
		$("#l_year").hide();
		$("#l_month").hide();
		$("#searchDate").show();
		title = "일별";
	} else if (sel_searchType == "weekly") {
		$("#sel_year").val("");
		$("#sel_month").val("");
		
		$("#l_year").hide();
		$("#l_month").hide();
		$("#searchDate").show();
		title = "주별";
	} else if (sel_searchType == "monthly") {
		$("#searchDate").val("");
		
		$("#searchDate").hide();
		yearSelectBox("l_year", "", "l_year", selYear);
		$("#l_month").show();
		monthSelectBox("l_month", selMonth);
		title = "월별";
	} else if (sel_searchType == "year") {
		$("#searchDate").val("");
		
		$("#searchDate").hide();
		$("#l_month").hide();
		yearSelectBox("l_year", "", "l_year", selYear);
		title = "연별";
	}
	downloadStatistics(searchDate, sel_searchType, title);
	memberRegStatistics(searchDate, sel_searchType, title);
	arpuStatistics(searchDate, sel_searchType, title);
	arppuStatistics(searchDate, sel_searchType, title);
	letterStatistics(searchDate, sel_searchType, title);
	dauStatistics(searchDate, sel_searchType, title);
	mauStatistics(searchDate, sel_searchType, title);
	authStatisticsByAge(searchDate, sel_searchType, title);
}

function accumulateList() {
	statisticsService.getAccumulate(function(result) {
		innerHTML("l_totalDownload", addThousandSeparatorCommas(result.downloadCnt)+"회");
		innerHTML("l_totalReg", addThousandSeparatorCommas(result.userCnt)+"명");
		innerHTML("l_totalLetter", addThousandSeparatorCommas(result.letterCnt)+"건");
	});
}

function getStartDayAndEndDay(val1, val2) {
	var str;
	
	if (40 <= val2 && val2 <= 52) {
		val1 = val1-1;
	}
	if (val2 >= 1 && val2 <= 12)  {
		val2 = val2 - 1;
	}
	
	var startDate = makeYYYY_MM_DD(find_start_week(val1, val2));
	var splitStartDate = startDate.split("-");
	var startDay = splitStartDate[1]+"-"+splitStartDate[2];
	
	var endDate = makeYYYY_MM_DD(find_end_week(val1, val2));
	var splitEndDate = endDate.split("-");
	var endDay = splitEndDate[1]+"-"+splitEndDate[2]
	str = startDay+" ~ "+endDay;
	return str;
}

function dashboardExcelDownload() {
	var type = getSelectboxValue("sel_searchType");
	var date = getInputTextValue("searchDate");
	var thisYear = getSelectboxValue("sel_year");
	var thisMonth = getSelectboxValue("sel_month");
	var typeName = "";
	
	if (thisMonth == undefined) thisMonth = "1";
	if (type == "daily" ) {
		typeName = "일별";
		if (input_blank_check("searchDate", "검색일을 선택하세요.") == false) return;
	} else if (type == "weekly") {
		typeName = "주별";
		if (input_blank_check("searchDate", "검색일을 선택하세요.") == false) return;
	} else if (type == "monthly") {
		typeName = "월별";
		if (selectbox_blank_check("sel_year", "년도를 선택하세요.") == false) return;
		if (selectbox_blank_check("sel_month", "월을 선택하세요.") == false) return;
	} else {
		typeName = "연별";
		if (selectbox_blank_check("sel_year", "년도를 선택하세요.") == false) return;
	}
	jConfirm(comment.download, typeName+" 통계 엑셀표를 다운받으시겠습니까?", excelDownload);
}

function excelDownload() {
	var type = getSelectboxValue("sel_searchType");
	var date = getInputTextValue("searchDate");
	var thisYear = getSelectboxValue("sel_year");
	var thisMonth = getSelectboxValue("sel_month");
	
	with (document.frm) {
		type.value = sel_searchType.value;
		search_date.value = searchDate.value;
		selYear.value = thisYear;
		selMonth.value = thisMonth;
		//action = "excel/test.do";
		action = "excel/dashboardStatistics.do";
		submit();
	}
}
</script>
</html>
<script>
$("#menu_01").addClass("active");
</script>