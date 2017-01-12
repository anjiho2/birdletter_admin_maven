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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/pushService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script type="text/javascript">

function init() {
	fn_search("new");
}

function fn_search(val) {
	var statusSort = $("#statusSort option:selected").val();
	var sPage = $("#sPage").val();
	var pushName = $("#pushName").val();
	
	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList", {
		filter:function(tr) {
			return (tr.id != "pattern");
		}
	});
	gfn_emptyView("H", "");
	
	pushService.birthdayPushInfoListCnt(statusSort, pushName, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView("v", "리스트가 없습니다.");
		} else {
			if (new Number(cnt) < (15*(sPage-1))) {
				sPage = 1;
				$("#sPage").val(sPage);
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		
		pushService.birthdayPushInfoList(sPage, statusSort, pushName, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						var checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"'/>";
						var dateTime = cmpList.sendDateTime.split(" ");
						var cellData = [
							                function(data) {return checkHTML;},
							                function(data) {return cmpList.content;},
							                function(data) {return cmpList.subTitle;},
							                function(data) {return dateTime[0];},
							                function(data) {return cmpList.sendStatus==1?cmpList.sendStatus="발송됨":cmpList.sendStatus="발송전";}
						                ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

function pushModify(pushIdx) {
	$("#pushIdx").val(pushIdx);
	with (document.frm) {
		page_gbn.value = "pushModify";
		action = getContextPath()+"/push.do";
		submit();
	}
}

//삭제버튼
function deletePushInfo() {
	var isChecked = $("input[name=chk]").is(":checked");
	if (isChecked == false) {
		alert("삭제할 항목을 체크하세요.");
		return;
	}
	if (confirm("선택하신 항목을 삭제하시겠습니까?")) {
		var isDelete = true;
		$("input[name=chk]:checked").each(function() {
			var pushIdx = $(this).val();
			if (pushIdx == "") {
				alert("체크된 값이 없습니다.");
				return;
			}
			pushService.deletePushInfo(pushIdx, function(bl) {
				if (bl == false ) isDelete = false;
			});
		});
		if (isDelete == true) {
			alert("목록이 삭제되었습니다.");
			location.reload(true);
		} else {
			alert("삭제 실패하였습니다.\n관리자에게 문의하세요.");
			return;
		}
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form class="form-horizontal" id="frm"  name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="pushIdx" id="pushIdx">
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
					<span class="">알림</span>&nbsp;〉&nbsp;<span onclick="javascript:goPush('birthDayPushList');">생일 알림 목록</span> 
				</div>
			<div class="span10">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>생일 알림 리스트</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
								<div class="list_top">
									<p class="f_L">
										<a href="javascript:deletePushInfo();" class="button compact red-gradient glossy">
											<span class="icon-trash">삭제</span>
										</a>
										<!-- <input type="button" class="button compact blue-gradient glossy" onclick="javascript:deletePushInfo();" value="삭제"> -->
										<!-- 
										<span style="padding-left: 50px;">
											<input type="text" class="input" id="pushName" placeholder="이름" onkeypress="javascript:if(event.keyCode == 13){fn_search('new'); return false;}">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
											<button type="button" class="button compact blue-gradient glossy" onclick="javascript:fn_search('new');">검색</button>
										</span>
										 -->
									</p>
									<!-- 
									<p class="f_R">
										<select class="select" id="statusSort"  onchange="javascript:fn_search();">
											<option value="">▶리스트정렬</option>
											<option value="pre">발송전</option>
											<option value="after">발송됨</option>
										</select>
									</p>
									 -->
									</div>
									<br>
									<div >
									<table class="table_list">
										<colgroup>
											<col width="2%" />
											<col width="*" />
											<col width="*" />
											<col width="*" />
											<col width="*" />
										</colgroup>
										<thead>
											<tr>
												<th>
													<input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');">
												</th>
												<th>제 목</th>
												<th>내 용</th>
												<th>발송일</th>
												<th>상 태</th>
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
							<p class="f_R">
								<font color="red">* 생일알림은 운영자가 셋팅할 수 없습니다.</font>
							</p><br><br>
							<p class="f_R">
								<font color="red">* 생일자 친구들(생일알림 푸시ON)에게만 푸시발송 됩니다.</font>
							</p><br><br>
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
$("#menu_07_02").addClass("active");
</script>