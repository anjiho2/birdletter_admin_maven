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

<!-- 그래프 스크립트 -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">
function init() {
	var statType = $("#sel_statType option:selected").val();
	
	if (statType == "") {
		alert("검색종류를 선택하세요.");
		$("#sel_statType").focus();
		return;
	}
	
	if (statType == "count") {
		$("#cntList_div2").hide();
		$("#cntList_div3").hide();
		var phoneNumber = "";
		dwr.util.removeAllRows("dataList", {
			filter:function(tr) {
				return (tr.id != "pattern");
			}
		});
		statisticsService.userProductBuyStatsics(phoneNumber, function(list) {
			if (list.length > 0) {
				// 최대 5명이 인될때
				if (list.length < 5) {
					$("#graph_null").show();
				} else {
					$("#graph_null").hide();
					//그래프
					$("#container").highcharts({
						chart : {
							renderTo : "container",
							type : "column",
							options3d : {
								enabled : true,
								alpha :15,
								beta : 20,
								depth : 70,
							}
						},
						title : {text : "아이템 구매 상위 5위"},
						subtitle : {text : "판매건수"},
						plotOptions : {
							column : {
								depth : 25
							}
						},
						xAxis: {
							categories: [
								list[0].phoneNumber, list[1].phoneNumber, list[2].phoneNumber, list[3].phoneNumber, list[4].phoneNumber
							]
						},
						yAxis: {
				            title: {
				                text: null
				            }
				        },
						series : [{
							name : "건수",
							data : [list[0].buyCnt, list[1].buyCnt, list[2].buyCnt, list[3].buyCnt, list[4].buyCnt] 
							}]
						});
				}
				//리스트 출력
				for (var i = 0; i < list.length; i++) {
					cmpList = list[i];
					if (cmpList != undefined) {
						$("#cntList_div").show();
						var cellData = [
						                	function(data) {return cmpList.phoneNumber;},
						                	function(data) {return cmpList.userName;},
						                	function(data) {return cmpList.buyCnt+"건";},
								];
						dwr.util.addRows("dataList", [0], cellData, {
							cellCreator:function(options) {
								var td = document.createElement("td");
								td.style.width = "33%";
								return td;
							},
							escapeHtml:false
						});
					}
				}
			}
		});
	} else if (statType == "popcorn") {
			dwr.util.removeAllRows("dataList2", {
				filter:function(tr) {
					return (tr.id != "pattern");
				}
			});
			$("#cntList_div").hide();
			$("#cntList_div3").hide();
			statisticsService.userProductBuyStatsicsByPopcorn(phoneNumber, function(list) {
				if (list.length > 0) {
					//최대 5명이 아닐때
					if (list.length < 5) {
						$("#graph_null").show();
					} else {
						$("#graph_null").hide();
						//그래프
						$('#container').highcharts({
					        chart: {
					        	renderTo : "container",
					            type: 'column',
					            options3d : {
					            	enabled : true,
									alpha :15,
									beta : 20,
									depth : 70,
					            }
					        },
					        title : {text : "아이템 구매 상위 5위"},
					        subtitle: {text: "팝콘소비"},
					        plotOptions : {
								column : {
									depth : 25
								}
							},
					        xAxis: {
					            categories: [
										list[0].phoneNumber, list[1].phoneNumber, list[2].phoneNumber, list[3].phoneNumber, list[4].phoneNumber
					            ]
					        },
					        yAxis: {
					            title: {
					                text: null
					            }
					        },
					       series: [
										{
											name: '총액수',
											data: [
												list[0].popcorn, list[1].popcorn, list[2].popcorn, list[3].popcorn, list[4].popcorn
											]
										}
									]
						});
					}
					//리스트 출력
					for (var i = 0; i < list.length; i++) {
						cmpList = list[i];
						var numbers = new Array();
						if (cmpList != undefined) {
							$("#cntList_div2").show();
							numbers = list[i].phoneNumber;
							var cellData = [
							                	function(data) {return cmpList.phoneNumber;},
							                	function(data) {return cmpList.userName;},
							                	function(data) {return addThousandSeparatorCommas(cmpList.popcorn);},
									];
							dwr.util.addRows("dataList2", [0], cellData, {escapeHtml:false});
						}
					}
				}
			});
	} else if (statType == "corn") {
		dwr.util.removeAllRows("dataList3", {
			filter:function(tr) {
				return (tr.id != "pattern");
			}
		});
		$("#cntList_div").hide();
		$("#cntList_div2").hide();
		statisticsService.userProductBuyStatsicsByCorn("", function(list) {
			if (list.length > 0) {
				if (list.length < 5) {
					$("#graph_null").show();
				} else {
					$("#graph_null").hide();
					//그래프
					$('#container').highcharts({
				        chart: {
				        	renderTo : "container",
				            type: 'column',
				            options3d : {
								enabled : true,
								alpha :15,
								beta : 20,
								depth : 70,
							}
				        },
				        title : {text : "아이템 구매 상위 5위"},
						subtitle : {text : "콘소비"},
						plotOptions : {
							column : {
								depth : 25
							}
						},
				        xAxis: {
				            categories: [
									list[0].phoneNumber, list[1].phoneNumber, list[2].phoneNumber, list[3].phoneNumber, list[4].phoneNumber
				            ]
				        },
				        yAxis: {
				            title: {
				                text: null
				            }
				        },
				        series: [
									{
										name: '콘 소비총액수',
										data: [
											list[0].corn, list[1].corn, list[2].corn, list[3].corn, list[4].corn
										]
									}
								]
					});
				}
				for (var i = 0; i < list.length; i++) {
					cmpList = list[i];
					if (cmpList != undefined) {
						$("#cntList_div3").show();
						numbers = list[i].phoneNumber;
						var cellData = [
						                	function(data) {return cmpList.phoneNumber;},
						                	function(data) {return cmpList.userName;},
						                	function(data) {return addThousandSeparatorCommas(cmpList.corn);},
								];
						dwr.util.addRows("dataList3", [0], cellData, { escapeHtml:false });
					}
				}
			}
		});
	}
}

function search() {
	var phoneNumber = $("#phoneNumber").val();
	dwr.util.removeAllRows("dataList", {
		filter:function(tr) {
			return (tr.id != "pattern");
		}
	});
	statisticsService.userProductBuyStatsics(phoneNumber, function(list) {
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				cmpList = list[i];
				if (cmpList != undefined) {
					var cellData = [
					                	function(data) {return cmpList.phoneNumber;},
					                	function(data) {return cmpList.userName;},
					                	function(data) {return cmpList.buyCnt+"건";},
							];
					dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
				}
			}
		}
	});
}

function searchPopcorn() {
	var phoneNumber = $("#popcornPhoneNumber").val();
	dwr.util.removeAllRows("dataList2", {
		filter:function(tr) {
			return (tr.id != "pattern");
		}
	});
	statisticsService.userProductBuyStatsicsByPopcorn(phoneNumber, function(list) {
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				cmpList = list[i];
				if (cmpList != undefined) {
					var cellData = [
					                	function(data) {return cmpList.phoneNumber;},
					                	function(data) {return cmpList.userName;},
					                	function(data) {return addThousandSeparatorCommas(cmpList.popcorn);},
							];
					dwr.util.addRows("dataList2", [0], cellData, { escapeHtml:false});
				}
			}
		}
	});
}

function searchCorn() {
	var phoneNumber = $("#cornPhoneNumber").val();
	dwr.util.removeAllRows("dataList3", {
		filter:function(tr) {
			return (tr.id != "pattern");
		}
	});
	statisticsService.userProductBuyStatsicsByCorn(phoneNumber, function(list) {
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				cmpList = list[i];
				if (cmpList != undefined) {
					var cellData = [
					                	function(data) {return cmpList.phoneNumber;},
					                	function(data) {return cmpList.userName;},
					                	function(data) {return addThousandSeparatorCommas(cmpList.corn);},
							];
					dwr.util.addRows("dataList3", [0], cellData, { escapeHtml:false });
				}
			}
		}
	});
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
		<div class="contentbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">통계</span>&nbsp;〉&nbsp;<span onclick="javascript:goStatistics('userProductBuyStatistics');">아이템 판매 사용자별 통계</span>
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
											<option value="popcorn">팝콘소비</option>
											<option value="corn">콘소비</option>
										</select>  
										<span id="l_year"></span>
										<a href="javascript:init();" class="button compact blue-gradient glossy">
											<span class="icon-search">검색</span>
										</a>
									</p>
									</div>
									<div id="container" style="min-width: 310px;  margin: 0 auto"></div>
									<div id="graph_null" style="z-index:20;position:absolute;left:40%;top:35%;margin-left:0px;margin-top:5px;background:#fff;display:none;">
								</div>
								<br>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="span10" id="cntList_div" style="display: none;">
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
										<input type="text" id="phoneNumber" class="input" placeHolder="전화번호" onkeypress="javascript:if(event.keyCode == 13){search(); return false;}">
										<span id="l_year"></span>
										<a href="javascript:search();" class="button compact blue-gradient glossy">
											<span class="icon-search">검색</span>
										</a>
									</p>
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
											<!-- <th><input type="checkbox" id="chkAll" onclick="javascript:checkall();">&nbsp;&nbsp;<input type="button" class="btn btn-primary btn-xs" onclick="javascript:checkVal();" value="삭제"></th> -->
												<th>핸드폰 번호</th>
												<th>이 름</th>
												<th>건 수</th>
											</tr>
										</thead>
										<tbody id="dataList"></tbody>
										<tr>
											<td id="emptys" colspan='23' bgcolor="#ffffff" align='center' valign='middle' style="visibility:hidden"></td>
										</tr>
									</table>
								</div>
								<br>
							</div>
						</div>
					</div>
				</div>
				
				<div class="span10" id="cntList_div2" style="display: none;">
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
										<input type="text" id="popcornPhoneNumber" class="input" placeHolder="전화번호" onkeypress="javascript:if(event.keyCode == 13){searchPopcorn(); return false;}">
										<span id="l_year"></span>
										<a href="javascript:searchPopcorn();" class="button compact blue-gradient glossy">
											<span class="icon-search">검색</span>
										</a>
									</p>
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
											<!-- <th><input type="checkbox" id="chkAll" onclick="javascript:checkall();">&nbsp;&nbsp;<input type="button" class="btn btn-primary btn-xs" onclick="javascript:checkVal();" value="삭제"></th> -->
												<th>핸드폰 번호</th>
												<th>이 름</th>
												<th>팝 콘</th>
											</tr>
										</thead>
										<tbody id="dataList2"></tbody>
										<tr>
											<td id="emptys" colspan='23' bgcolor="#ffffff" align='center' valign='middle' style="visibility:hidden"></td>
										</tr>
									</table>
								</div>
								<br>
							</div>
						</div>
					</div>
				</div>
				
				<div class="span10" id="cntList_div3" style="display: none;">
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
										<input type="text" id="cornPhoneNumber" class="input" placeHolder="전화번호" onkeypress="javascript:if(event.keyCode == 13){searchCorn(); return false;}">
										<span id="l_year"></span>
										<a href="javascript:searchCorn();" class="button compact blue-gradient glossy">
											<span class="icon-search">검색</span>
										</a>
									</p>
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
											<!-- <th><input type="checkbox" id="chkAll" onclick="javascript:checkall();">&nbsp;&nbsp;<input type="button" class="btn btn-primary btn-xs" onclick="javascript:checkVal();" value="삭제"></th> -->
												<th>핸드폰 번호</th>
												<th>이 름</th>
												<th>콘</th>
											</tr>
										</thead>
										<tbody id="dataList3"></tbody>
										<tr>
											<td id="emptys" colspan='23' bgcolor="#ffffff" align='center' valign='middle' style="visibility:hidden"></td>
										</tr>
									</table>
								</div>
								<br>
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
$("#menu_05_03").addClass("active");
</script>