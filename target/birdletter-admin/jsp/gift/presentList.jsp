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
<script type="text/javascript" src="<%=webRoot%>/js/datepicker.js"></script>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/giftService.js"></script>
<script type="text/javascript">

function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var startDate = getInputTextValue("startDate");
	var endDate = getInputTextValue("endDate");
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("search_value");
	
	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	gfn_emptyView("H", "");
	
	/** 선물함 개수 목록(총, 개봉, 미개봉) **/
	giftService.presentCountList(function(cntList) {
		innerHTML("l_giftTotal", cntList.total_cnt);
		innerHTML("l_giftOpen", cntList.open_cnt);
		innerHTML("l_giftClose", cntList.close_cnt);
	});
	
	giftService.presentListCnt(startDate, endDate, searchType, searchValue, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '15', '15', '1');
			//gfn_emptyView("v", "결과가 없습니다");
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		giftService.presentList(sPage, startDate, endDate, searchType, searchValue, function(selList) {
			if (selList.length > 0) {
				for (var i=0; i<selList.length; i++) {
					cmpList = selList[i];
					checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"' />";
					var cellData = [
					                function(data) {return checkHTML},
					                function(data) {return getDateTimeSplitComma(cmpList.createDate)},
					                function(data) {return cmpList.giftTitle},
					                function(data) {return getGiftItemName(cmpList.giftType, cmpList.productName, cmpList.coinCount)},
					                function(data) {return cmpList.phoneNumber},
					                function(data) {return cmpList.userName},
					                function(data) {return getAge(cmpList.birthDay)+"세"},
					                function(data) {return gfn_isData(cmpList.gender, "MALE", "남성", "여성")},
					                function(data) {return getGiftState(cmpList.giftState, getDateTimeSplitComma(cmpList.expireDate))},
					                function(data) {return gfn_isData(cmpList.receiveDate, null, "수령전", getDateTimeSplitComma(cmpList.receiveDate))}
					                ]
					dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
				}
			} else {
				//사용자 프로필 리스트가 없을떄
				gfn_printPageNum_new('0', '15', '15', '1');
				gfn_emptyView('V', comment.blank_search_result);
			}
		});	
	});
}

function updateGiftState() {
	var isChecked = isCheckedCheckbox("chk", "NAME");
	if (isChecked == false) {
		alert(comment.check_item);
		return;
	}
	var giftIds = [];
	$("input[name=chk]:checked").each(function(i) {
		giftIds[i] = $(this).val();		
	});
	giftService.validateUpdateGiftState(giftIds, function(state_bl) {
		if (state_bl == false) {
			alert("지급취소를 할수없는 데이터가 있습니다.\n선물상태를 확인하세요.");
			return false;
		} else {
			if (confirm("지급취소 하시겠습니까?")) {
				$("input[name=chk]:checked").each(function() {
					var giftId = $(this).val();
					giftService.updateGiftState(giftId, 4, function(bl) {
						if (bl == false) {
							alert(comment.error);
							return;
						}
					});
				});
				alert(comment.success_process);
				isReloadPage(true);
			}
		}
	});
}

function giftBoxSendListExcelDownload() {
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("search_value");
	
	if (confirm(comment.isExcelDownload)) {
		with (document.frm) {
			start_date.value = startDate.value;
			end_date.value = endDate.value;
			search_type.value = searchType;
			search_value2.value = searchValue;
			action = "excel/giftBoxSendList.do";
			submit();
		}	
	} 
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="fn_search('new')"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="start_date" id="start_date" value="">
<input type="hidden" name="end_date" id="end_date" value="">
<input type="hidden" name="search_type" id="search_type" value="">
<input type="hidden" name="search_value2" id="search_value2" value="">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<!--  S : rightarea -->
<div class="rightarea span10">
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">선물</span>&nbsp;〉&nbsp;<span onclick="javascript:goPage('gift','presentList');">선물함 지급관리</span>&nbsp;〉&nbsp;지급관리
				</div>
				<!-- 사용자 리스트 영역 시작 -->
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>선물함 지급관리</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<a href="javascript:updateGiftState();" class="button compact red-gradient glossy">
												<span class="">지급취소</span>
											</a>
											<input type="text" class="input" id="startDate" placeholder="시작일">
											<input type="text" class="input" id="endDate" placeholder="종료일">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a> 
											&nbsp;&nbsp;&nbsp;
											<select class="select" id="sel_searchType">
												<option value="">▶검색종류선택</option>
												<option value="phoneNumber">핸드폰번호</option>
												<option value="userName">이 름</option>
											</select>
											<input type="text" class="input" id="search_value" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){fn_search('new'); return false;}">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
										</p>
										<p class="f_R">
											총 개수 : <span id="l_giftTotal">0</span>건
											&nbsp;&nbsp;&nbsp;
											개봉 선물 개수 : <span id="l_giftOpen">0</span>건
											&nbsp;&nbsp;&nbsp;
											미개봉 선물 개수 : <span id="l_giftClose">0</span>건
											<a href="javascript:giftBoxSendListExcelDownload();" class="button compact black-gradient glossy">
												<span class="icon-download">엑셀다운로드</span>
											</a>
										</p>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<col width="2%" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
									</colgroup>
									<thead>
									<tr>
										<th><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th>
										<th>지급일시</th>
										<th>선물명</th>
										<th>선물 아이템</th>
										<th>전화번호</th>
										<th>이 름</th>
										<th>나 이</th>
										<th>성 별</th>
										<th>선물상태</th>
										<th>수령일시</th>
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
$("#menu_14_02").addClass("active");
</script>