<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
	String userId = Util.isNullValue(request.getParameter("user_id"), "1");
	String phoneNumber = Util.isNullValue(request.getParameter("l_phoneNumber"), "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공용 include 모음 -->
<%@ include file="/common/jsp/top.jsp"%>
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>
<!-- 페이지 이동 스크립트 -->
<script src="<%=webRoot%>/js/page.js"></script>
<!-- 셀렉트박스 스크립트 -->
<script src="<%=webRoot%>/js/selectbox.js"></script>
<!-- modal관련 css -->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<!-- 테이블 스크롤 css -->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/table_scroll.css">

<script type="text/javascript">
function init() {
	$("#user_top_menu_5").attr("class","button silver-gradient glossy active");
	
	var storeType = $("#sel_storeType option:selected").val();
	
	//상품종류선택 셀렉트박스
	selectList(storeType);
	//리스트 row삭제
	dwr.util.removeAllRows("dataList");
	dwr.util.removeAllRows("dataList2");
	
	//리스트
	if(storeType != undefined) {
		itemService.productListByStoreType(storeType, function(selList) {
			if (selList.length > 0) {
				$("#btn_div").show();
				$("#list_table").show();
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						console.log(cmpList.productName);
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.itemCode+"'/>";
						var cellData = [
						                function(data) {return checkHTML;},
						                function(data) {return cmpList.itemCode;},
						                function(data) {return cmpList.productName;},
						                function(data) {return cmpList.productDesc;}
						                ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
				//리스트 하단 선물하기 버튼
				presentBTN = "<input type='button' class='btn btn-success btn-xs' onclick='javascript:checkVal();' value='선물하기'>";
				var cellData2 = [function(data) {return presentBTN;}];
				dwr.util.addRows("dataList2", [0], cellData2, {escapeHtml:false});
			} else {
				$("#btn_div").hide();
				$("#list_table").hide();
			}
		});
	}
}

//선물하기 버튼
function checkVal() {
	var isSuccess = true;
	var userId = "<%=userId%>";
	var isChecked = $("input[name=chk]").is(":checked");
	if (isChecked == false) {
		alert("선물할 아이템을 선택하세요.");
		return;
	}
	var checkedCnt = $("input[name=chk]:checked").length;
	if (confirm("선택하신 "+checkedCnt+"개 아이템을 선물하시겠습니까?")) {
		$("input[name=chk]:checked").each(function() {
			var itemCode = $(this).val();
			itemService.presentItem(userId, itemCode, function(bl) {
				if (bl == false) {
					isSuccess = false;
				}
			});
		});
		if (isSuccess == true) {
			alert("선물지급이 완료됬습니다.");
			goUser('itemPresent');
		} else {
			alert("선물지급 오류가 발생됬습니다.\n관리자에게 문의하세요.");
			return;
		}
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="user_id" id="user_id" value="<%=userId%>">
<input type="hidden" name="l_phoneNumber" id="l_phoneNumber" value="<%=phoneNumber%>">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<!--  S : rightarea -->
<div class="rightarea span8">
		<div class="subbody">
		<div class="contentbody">
		<%@ include file="/common/jsp/user_top_menu.jsp"%>
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">사용자</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('list');">사용자 리스트</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('itemPresent');">아이템 선물하기</span>
				</div>
				<div class="span10" >
				<div class="title_d1">
					<h4><%=phoneNumber%>님</h4>
				</div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>아이템 선물하기</span>
								<span id="l_selStore" style="padding: 3px; 1px;"></span>
								<input type="button" id="btn_div" class="button blue-gradient glossy" onclick="javascript:checkVal();" value="선물하기" style="display: none;">
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
									</div>
								</div>
								<table class="table_list">
									<colgroup>
										<col width="2%" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
									</colgroup>
									<thead>
									<tr>
										<th><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th>
										<th>아이팀 코드</th>
										<th>아이템 이름</th>
										<th>아이템 설명</th>
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
	</div>
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->	
</form>
</body>
</html>
<script>$("#menu_02_01").addClass("active");</script>