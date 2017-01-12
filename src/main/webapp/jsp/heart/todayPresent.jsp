<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<!-- 상단 메뉴 jsp --> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/heartService.js"></script>
<!-- 그래프 스크립트 -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">
function init() {
	randomPopcornInfo();
}

function randomPopcornInfo() {
	heartService.todayPresentPopcornInfo(function(info) {
		if (info != null) {
			$("#popcornPresentIdx").val(info.idx);
			$("#l_minPopcorn").val(info.minimumPopcorn);
			$("#l_maxPopcorn").val(info.maxPopcorn);
			var updateButton = "<a href='javascript:showUpdate(\"popcorn\", \"on\")' class='button compact blue-gradient glossy'><span class='icon-tools'>수정<\/span><\/a>";
			var cellData = [
			                function(data) {return info.minimumPopcorn;},
			                function(data) {return info.maxPopcorn;},
			                function(data) {return info.hourLimit+"시간 "+info.minuteLimit+"분";},
			                function(data) {return info.heartRewardLimit;}
			                //function(data) {return updateButton;},
			                ];
			dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
		}
	});
}

function showUpdate(val, val2) {
	var type = val;
	var mode = val2;
	
	if (type == "popcorn") {
		if (mode == "on") {
			$("#dataList").hide();
			$("#updateDataList").show();
			$("#minPopcorn").val($("#l_minPopcorn").val());
			$("#maxPopcorn").val($("#l_maxPopcorn").val());
		} else if (mode == "off") {
			$("#dataList").show();
			$("#updateDataList").hide();
		}
	}
}

function update(val) {
	var type = val;
	
	if (type == "popcorn") {
		var idx = $("#popcornPresentIdx").val();
		var minPopcorn = $("#minPopcorn").val();
		var maxPopcorn = $("#maxPopcorn").val();
		
		if (confirm("수정하시겠습니까?")) {
			heartService.updateTodayPresentPopcornInfo(idx, minPopcorn, maxPopcorn, function(result) {
				if (result > 0) {
					alert("수정됬습니다.");
					goPage('heart', 'todayPresent');
				} else {
					alert("오류가 발생됬습니다./n관리자에게 문의하세요.");
					return;
				}
			});
		}
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="l_minPopcorn" id="l_minPopcorn">
<input type="hidden" name="l_maxPopcorn" id="l_maxPopcorn">
<input type="hidden" name="popcornPresentIdx" id="popcornPresentIdx">
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
					<span class="">하트</span>&nbsp;〉&nbsp;<span onclick="javascript:goPage('heart', 'todayPresent');">오늘의 선물</span> 
				</div>
				<div class="span5">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon-tools"></span>
								<span>오늘의 선물 관리</span>
							</div>
							<div class="portlet-content">
								<div class="content">
									<div class="list_top">
										<p class="f_R">
											<span style="padding-left: 50px;">
												<a href="jsp/popup/modify_today_present.jsp" id="modifyBtn" class="button compact blue-gradient glossy">
													<span class="icon-tools">수정</span>
												</a>
											</span>
										</p>
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
													<th>최저 팝콘 값</th>
													<th>최고 팝콘 값</th>
													<th>보상 제한 시간</th>
													<th>보상 가능 하트 수</th>
												</tr>
											</thead>
										<tbody id="dataList"></tbody>
									</table>
								</div>	
							</div>
						</div>
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
</form>
</body>
</html>
<script>
$("#menu_12_01").addClass("active");
$("#modifyBtn").pageslide({direction:"left"});
</script>