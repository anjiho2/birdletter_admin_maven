<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@page import="com.challabros.birdletter.admin.dto.AdminDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String userId = Util.isNullValue(request.getParameter("user_id"), "1");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/userService.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();">
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="user_id" id="user_id">
<input type="hidden" name="search_type" id="search_type">
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
					<span class="">사용자</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('list');">사용자 리스트</span> 
				</div>
				<div class="span10" >
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>회원정보</span>
									<select class="select" id="sel_searchType">
										<option value="">▶검색종류선택</option>
										<option value="phoneNumber">핸드폰번호</option>
										<option value="userName" selected>이 름</option>
									</select>
									<input type="text" class="input width-small" id="searchValue" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){fn_search('new'); return false;}"><!-- oninput="javascript:fn_search('new');" -->
									<button type="button" class="button blue-gradient glossy" onclick="javascript:fn_search('new');">검색</button>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L_Large">
										<span style="margin-left: 10px;"></span>
										총 가입자 : <span id="total_cnt"></span>명 &nbsp;&nbsp;
										접속자 수 : <span id="connection_cnt"></span>명 &nbsp;
										검색된 사용자 수 : <span id="search_user_cnt">0</span>명
										</p>
										<p class="f_R">
											<!-- 										
											<a href="javascript:deleteUser();" class="button compact red-gradient glossy">
												<span class="icon-trash">회원탈퇴</span>
											</a>
											 -->
											 리스트 : <span id="l_pageCnt"></span>
											<a href="javascript:excelDownload('excel/userList.do');" class="button compact black-gradient glossy">
												<span class="icon-download">엑셀다운로드</span>
											</a>
										</p>	
									</div>
									<br>
									<table class="table_list">
										<colgroup>
											<!-- <col width="2%" /> -->
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
												<!-- <th><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th> -->
												<th>핸드폰 번호</th>
												<th>이 름</th>
												<th>나 이</th>
												<th>성 별</th>
												<th>앱 버전</th>
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
<script type="text/javascript">

function init() {
	fn_search("new");

	var serverName = '<%=serverName%>';
	var targetUrl1 = "http://30.0.1.202:5000/v103/session/count";
	var targetUrl2 = "http://30.0.2.224:5000/v103/session/count";
	//현재 접속자 수 가져오기(실서버 관리자 일때만 검색)
	if (serverName == "52.78.82.238") {
		userService.userConnectionCnt(targetUrl1, targetUrl2, function(cnt) {
			innerHTML("connection_cnt", cnt==0 ? "0" : addThousandSeparatorCommas(cnt))
		});
	} else {
		innerHTML("connection_cnt", "0");
	}
	
}

//테이블 리스트
function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("searchValue");
	
	if (searchType == "" && searchValue != "") {
		alert(comment.select_search_list);
		focusInputText("sel_searchType");
		return;
	}
	//리스트 개수 셀렉트 박스
	var pagingCnt = getSelectboxValue("sel_pagingCnt");
	if (pagingCnt == undefined) pagingCnt = 10;
	pagingListSelectbox(pagingCnt, "l_pageCnt");

	if (val == "new") sPage = "1";	//페이징 초기값 셋팅
	
	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");
	
	var userName = "";
	var phoneNumber = "";
	
	if (searchType == "userName")	userName = searchValue;
	else if (searchType == "phoneNumber") phoneNumber = searchValue;
	
	//총가입자수 고정
	if (userName == "" && phoneNumber == "") {
		userService.userProfileCnt(userName, phoneNumber, function(cnt) {
			innerHTML("total_cnt", addThousandSeparatorCommas(cnt));
		});
	}
	//사용자 프로필 갯수 가져오기
	userService.userProfileCnt(userName, phoneNumber, function(cnt) {
		if (userName != "" || phoneNumber != "") {
			innerHTML("search_user_cnt", cnt);	
		} else {
			innerHTML("search_user_cnt", "0");
		}
		if (cnt == "0") {
			gfn_printPageNum_new('0', '15', pagingCnt, '1');
			//gfn_emptyView("v", "결과가 없습니다");
		} else {
			if (new Number(cnt) < (pagingCnt * sPage)) {
				if (new Number(cnt) < (pagingCnt * (sPage - 1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', pagingCnt, sPage);
		}
		//사용자 프로필 리스트 가져오기
		userService.userProfileList(sPage, pagingCnt, userName, phoneNumber, function(selList) {
			if (selList.length > 0) {
				dwr.util.removeAllRows("dataList");
				for (var i=0; i<selList.length; i++) {
					cmpList = selList[i];
					if(cmpList == undefined) {
						bid = selList[0].cmpList.rowNum;
					} else {
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.userId+"' />";
						phoneNumberHTML = '<a href="#" onClick="javascript:goUser('+ "'modify'," +"'"+cmpList.userId+"'" + ')">'+leadingZeros(cmpList.phoneNumber,11)+"</a>";
						//phoneNumberHTML = '<a href="#" onClick="javascript:goUser('+ "'modify'," +"'"+cmpList.userId+"',"+ "'"+searchType+"'"+ ')">'+cmpList.phoneNumber+"</a>";
						var cellData = [
						               	/* function(data) {return checkHTML;}, */
							            function(data) {return phoneNumberHTML;},
							            function(data) {return cmpList.userName;},
							            function(data) {return getAge(cmpList.birthDay)+ "세";},
							            function(data) {return gfn_isData(cmpList.gender, "MALE", "남성", "여성");},
							            function(data) {return cmpList.appVersion;},
							            function(data) {return getDateTimeSplitComma(cmpList.createDate);}
						           ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			} else {
				//사용자 프로필 리스트가 없을떄
				gfn_printPageNum_new('0', pagingCnt, pagingCnt, '1');
				gfn_emptyView('V', comment.blank_search_result);
			}
		});
	});
}

function deleteUser() {
	var isChecked = isCheckedCheckbox("chk","NAME");
	if (isChecked == false) {
		alert(comment.check_item);
		return;
	}
	
	if (confirm("모든 데이터가 삭제됩니다.\n회원탈퇴를 진행하시겠습니까?")) {
		$("input[name=chk]:checked").each(function() {
			var userId = $(this).val();
			withdrawralRepository.withDrawal(userId, function(bl) {
				if (bl == false) {
					alert(comment.error);
					return;					
				}
			});	
		});
		alert(comment.success_withdrawral);
		goUser('list');
	}
}
</script>
</html>
<script>$("#menu_02_01").addClass("active");</script>