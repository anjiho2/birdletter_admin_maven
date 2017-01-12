<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
	String letterId = request.getParameter(Util.isNullValue("letter_id", ""));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<jsp:include page="/daul/include/header.jsp" flush="false"/> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/letterService.js"></script>

<script src="<%=webRoot%>/js/datepicker.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
var letterId = '<%=letterId%>';

function init() {
	letterService.detailOpenLetter(letterId, function(openLetter) {
		var checked = "";
		innerHTML("l_letterId", openLetter.openLetterId);
		innerHTML("l_createDate", getDateTimeSplitComma(openLetter.createDate));
		
		if (openLetter.updateDate != null) {
			innerHTML("l_updateDate", getDateTimeSplitComma(openLetter.updateDate));
		} else {
			innerHTML("l_updateDate", "없음");
		}
		innerHTML("l_userName", openLetter.userName);
		innerHTML("l_rank", gfn_zeroToPipe(openLetter.weeklyRanking, "등"));
		innerHTML("l_bestRank", gfn_zeroToPipe(openLetter.bestRanking, "등"));
		innerHTML("l_heartCount", gfn_zeroToZero(addThousandSeparatorCommas(openLetter.heartCount), "개"));
		innerHTML("l_sharedCount", gfn_zeroToZero(addThousandSeparatorCommas(openLetter.sharedCount), "회"));
		innerHTML("l_letterCommentCount", gfn_zeroToZero(addThousandSeparatorCommas(openLetter.letterCommentCount), "개"));
		innerHTML("l_letterReportCount", gfn_zeroToZero(addThousandSeparatorCommas(openLetter.letterReportCount), "건"));
		
		if (openLetter.blockYn == "1") {
			checked = "checked";
		}
		var useYnHTML = "<input type='checkbox' value='0' id='useYn' "+checked+" onchange=\"javascript:blockLetter('"+letterId+"')\";>"+"<script>$(document).ready(function() {$(':checkbox').iphoneStyle();});<\/script>";
		innerHTML("l_viewYn", useYnHTML);
	});
	fn_search("new");
}

//신고 리스트
function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	if (sPage == "sPage") {
		sPage = "1";
	}
	
	dwr.util.removeAllRows("dataList");
	
	letterService.countSquareLetterReport(letterId, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '5', '5', '1');
			gfn_emptyView("v", comment.blank_list);
		} else {
			if (new Number(cnt) < (5*sPage)) {
				if (new Number(cnt) < (5*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '5', '5', sPage);
		}
	});
	
	letterService.findSquareLetterReport(sPage, letterId, function(selList) {
		if (selList.length > 0) {
			for (var i=0; i<selList.length; i++) {
				cmpList = selList[i];
				if (cmpList != undefined) {
					var cellData = [
					                function(data) {return getDateTimeSplitComma(cmpList.reportDate)},
					                function(data) {return cmpList.phoneNumber},
					                function(data) {return cmpList.userName},
					                function(data) {return getIllegalTypeName(cmpList.letterIllegalType)},
					                function(data) {return cmpList.reportMessage==null?cmpList.reportMessage="-":cmpList.reportMessage}
					                ];
					dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
				}
			}
		} else {
			gfn_printPageNum_new('0', '5', '5', '1');
			gfn_emptyView("v", comment.blank_list);
		}	
	});
}

//차단하기
function blockLetter(val) {
	var letterId = val;
	var blockYn = "0";
	var letterChecked = 0;
	if (isCheckedCheckbox("useYn", "ID") == true) {
		letterChecked = "1";
	}
	if (letterChecked == "1") {
		blockYn = "1";
	}
	
	if (letterId != null) {
		if (letterChecked == "1") {
			if (confirm("광장 편지를 차단하시겠습니까?")) {
				letterService.blockOpenLetter(letterId, blockYn, function(bl) {
					if (bl == false) {
						alert(comment.error);
					} else {
						isReloadPage(true);
					}
				});
			} else {
				alert(comment.cancel);
				isCheckbox("useYn", false);
			}
		} else {
			if (confirm("광장 편지 차단을 취소 하시겠습니까?")) {
				letterService.blockOpenLetter(letterId, blockYn, function(bl) {
					if (bl == false) {
						alert(comment.error);
					} else {
						isReloadPage(true);
					}
				});
			} else {
				alert(comment.cancel);
				isCheckbox("useYn", true);
			}
		}
	}
}

//편지보기
/*
dev : http://106.240.100.50:5000/viewer/letter/%s/bsm
stage : http://dev-viewer.birdletter.co.kr/letter/%s/bsm
product : http://viewer.birdletter.co.kr/letter/%s/bsm
*/
function open_pop(){
	var url = "";
	var serverName = '<%=serverName%>';
	
	if (serverName == "localhost") {
		url = 'http://106.240.100.50:5000/viewer/letter/';
	} else if (serverName == "52.79.120.231") {
		url = 'http://dev-viewer.birdletter.co.kr/letter/';
	} else if (serverName == "52.78.82.238") {
		url = 'http://viewer.birdletter.co.kr/letter/';
	}
	url += letterId+'/bsm'
    gfn_winPop("370", "650", url, "");
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init()"> 
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
	<div class="rightarea span8">
		<div class="subbody">
		<div class="contentbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">광장</span>&nbsp;〉&nbsp;<span onclick="javascript:goPage('square','squareList');">편지 리스트</span>&nbsp;〉&nbsp;<span>광장편지 상세정보</span>
				</div>
				<div class="title_d1">
					<h4>광장편지 상세정보</h4>
				</div>
				<div class="formbox">
					<div class="block_label medium">
						<label class="label">
							<span>편지ID</span>
						</label>
						<div class="field">
							<div>
								<span id="l_letterId"></span>
								<p class="f_R">
									<a href="javascript:open_pop();" class="button compact blue-gradient glossy">
										<span class="icon-search">편지보기</span>
									</a>
									<!-- <a href="javascript:" class="button compact red-gradient glossy">
										<span class="icon-trash">삭제</span>
									</a> -->
								</p>
							</div>
							
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>최초 생성일</span>
						</label>
						<div class="field">
							<div><span id="l_createDate"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>최종 수정일</span>
						</label>
						<div class="field">
							<div><span id="l_updateDate"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>작성자 이름</span>
						</label>
						<div class="field">
							<div><span id="l_userName"></span></div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>인기 순위</span>
						</label>
						<div class="field">
							<div><span id="l_rank"></span></div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>베스트 순위</span>
						</label>
						<div class="field">
							<div><span id="l_bestRank"></span></div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>하트 받은 개수</span>
						</label>
						<div class="field">
							<div><span id="l_heartCount"></span></div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>공유된 횟수</span>
						</label>
						<div class="field">
							<div><span id="l_sharedCount"></span></div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>댓글 갯수</span>
						</label>
						<div class="field">
							<div><span id="l_letterCommentCount"></span></div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>신고 건수</span>
						</label>
						<div class="field">
							<div><span id="l_letterReportCount"></span></div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>차단 여부</span>
						</label>
						<div class="field">
							<span id="l_viewYn"></span>
						</div>
					</div><br><br>
					
					<div class="title_d1">
						<h4>신고 리스트</h4>
					</div>
					
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
								<th>신고일시</th>
								<th>신고자 전화번호</th>
								<th>신고자 이름</th>
								<th>신고 사유</th>
								<th>기타 내용</th>
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
	<!--  E: right area-->
	<!--  S: foot-->
	<jsp:include page="/daul/include/footer.jsp" flush="false"/>
	<!--  E: foot-->
</form>
</body>
</html>
<script>
$("#menu_13_01").addClass("active");
</script>