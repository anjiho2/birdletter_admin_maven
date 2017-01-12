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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/noticeService.js"></script>
<script type="text/javascript">

function init() {
	fn_search("new");
}

//페이징 리스트
function fn_search(val) {
	var sPage = $("#sPage").val();
	var title = $("#search_value").val();
	var sortType = getSelectboxValue("sel_sort");
	if (sortType == undefined) sortType = "0";
	
	birdTipSortSelectbox(sortType, "l_sort", "init();");
	
	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	gfn_emptyView("H", "");

	noticeService.birdTooltipListCnt(sortType, function(cnt) {
		if (cnt == 0) {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView('v', comment.blank_list);
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					$("#sPage").val(sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		
		noticeService.birdTooltipList(sPage, sortType, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];	
					if (cmpList != undefined) {
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"'/>";
						btnHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/modify_birdTip_popup.jsp?tipIdx="+cmpList.idx+"'})\" class=\"modalBtn\">"+cmpList.firstTooltip+"<\/a>";
						var cellData = [
						            	function(data) {return checkHTML;},
						            	function(data) {return cmpList.rowNum;},
						            	function(data) {return btnHTML;},
						            	function(data) {return cmpList.secondTooltip;},
						            	function(data) {return cmpList.regularYn == "1" ? "상시" : getDateTimeSplitComma(cmpList.startDate) + " ~ " + getDateTimeSplitComma(cmpList.endDate);},
						            	function(data) {return cmpList.orderPriority == "0" ? "랜덤" : cmpList.orderPriority;},
						            	function(data) {return getTooltipState(cmpList.viewYn, getDateTimeSplitComma(cmpList.endDate));/* cmpList.viewYn == "0" ? "사용안함" : "사용중" */},
										];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

//삭제버튼
function deleteBirdTooltip() {
	var isChecked = isCheckedCheckbox("chk", "NAME");
	if (isChecked == false) {
		alert(comment.check_item);
		return;
	}
	
	if (confirm(comment.isDelete)) {
		$("input[name=chk]:checked").each(function() {
			var idx = $(this).val();
			noticeService.deleteBirdTip(idx, function(bl) {
				if (bl == false) {
					alert(comment.error);
					return;
				}
			});
		});
		alert(comment.success_process);
		goNotice('birdTipList');
	}
}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="noticeIdx" id="noticeIdx" >
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
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">공지사항</span>&nbsp;〉&nbsp;<span onclick="javascript:goNotice('birdTipList');">버드 메시지 Tip목록</span> 
				</div>
			<div class="span10">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>버드 메시지 Tip목록</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
								<div class="list_top">
									<p class="f_L">
										<a href="javascript:deleteBirdTooltip();" class="button compact red-gradient glossy">
											<span class="icon-trash">삭제</span>
										</a>
									</p>
									<p class="f_R">
										<span id="l_sort"></span>
										<a href="jsp/popup/insert_birdTip_popup.jsp" id="insertBtn" class="button compact blue-gradient glossy">
											<span class="icon-pencil">등록</span>
										</a>
									</p>
								</div>
									<br>
								<div >
									<table class="table_list">
										<colgroup>
											<col width="2%" />
											<col width="3%" />
											<col width="*" />
											<col width="*" />
											<col width="18%" />
											<col width="7%" />
											<col width="5%" />
										</colgroup>
										<thead>
											<tr>
												<th>
													<input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');">
												</th>
												<th>No</th>
												<th>메시지_01</th>
												<th>메시지_02</th>
												<th>기 간</th>
												<th>우선 순위</th>
												<th>사용여부</th>
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
$("#menu_06_03").addClass("active");
$("#insertBtn").pageslide({direction:"left"});
</script>