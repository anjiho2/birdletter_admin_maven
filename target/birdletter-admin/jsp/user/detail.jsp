<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@page import="com.challabros.birdletter.admin.dto.AdminDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
	String user_id = request.getParameter(Util.isNullValue("user_id", ""));
	String page_gbn = request.getParameter(Util.isNullValue("page_gbn", ""));
	String search_type = request.getParameter(Util.isNullValue("search_type", ""));
	
	AdminDto vo = (AdminDto)session.getAttribute("adminInfo");
	String adminId = vo.getAdminId();
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/userService.js"></script>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/birdService.js"></script>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/statisticsService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>

<script type="text/javascript" charset="utf-8" src="daul/js/plugins/jquery.slides.js"></script>
<script type="text/javascript" charset="utf-8" src="daul/js/plugins/jquery.pageslide.js"></script>
 
<script type="text/javascript">
var adminId = '<%=adminId%>';
var user_id = '<%=user_id%>';
var search_type = '<%=search_type%>';

function init() {
	$("#user_top_menu_0").attr("class","button silver-gradient glossy active");
	innerValue("user_id", user_id);
	
	var search_value = getInputTextValue("searchValue");
	var user_name = "";
	var phone_number = "";
	if (search_value != "") {
		var keyValue = event.keyCode;
		search_type = getSelectboxValue("sel_searchType");
		if (search_type == "userName") {
			user_name = search_value;
		} else if (search_type == "phoneNumber") {
			if(/[^0123456789]/g.test(search_value)) {
				alert(comment.insert_number);
				return;
			}
			phone_number = search_value;
		}
	}
	
	if (search_value != "") {
		user_id = 0;
	}
	
	memberSearchTypeSelectBox(search_type, "l_searchType");
	
	var letterType = getSelectboxValue("sel_letterType");
	if (letterType == undefined) {
		letterType = "private";
	}
	letterTypeSelectBox("l_letterType", letterType);
	
	userService.userProfileDetail(user_id, user_name, phone_number, function(model) {
		if (model != null) {
			innerValue("user_id", model.userId);
			innerValue("l_phoneNumber", model.userName);
			innerValue("phoneNumber", model.phoneNumber);
			innerValue("change_cornPoint", model.cornPoint);
			innerValue("change_popcornPoint", model.popcornPoint);
			
			innerHTML("userName", model.userName);
			innerHTML("message", model.message);
			innerHTML("sex_sel", gfn_isData(model.gender, "MALE", "남성", "여성"));
			innerHTML("cornPoint", model.cornPoint == 0 ? "0" : addThousandSeparatorCommas(model.cornPoint));
			innerHTML("popcornPoint", model.popcornPoint == 0 ? "0" : addThousandSeparatorCommas(model.popcornPoint));
			if (model.birthDay != null)
				innerHTML("datepicker", model.birthDay.split(" " ,1));
			innerHTML("company", model.company);
			innerHTML("college", model.college);
			
			var os = "";
			if (model.osType == "IOS") os = "애플";
			else os = "안드로이드";
			
			innerHTML("phoneNumber", fn_tel_tag(model.phoneNumber)+" ("+ os +")");
			innerHTML("uuId", model.uuId);
			innerHTML("facebookId", model.facebookId);
			innerHTML("authToken", model.authToken);
			innerHTML("authDatepicker", model.authDate.split(" ",1));
			innerHTML("last_authDate", getDateTimeSplitComma(model.authDate));
			innerHTML("totalHeartPoint", gfn_zeroToZero(addThousandSeparatorCommas(model.totalHeartPoint)));
		} else {
			alert(comment.blank_search_result);
			return;
			//history.back();
		}
	});
}
$(function() {
	$("#authDatepicker").datepicker({
			dateFormat: 'yy-mm-dd',
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		    dayNamesMin: ['일','월','화','수','목','금','토'],
			changeMonth: true, //월변경가능
		    changeYear: true, //년변경가능
			showMonthAfterYear: true, //년 뒤에 월 표시
	});
});

/** 변경버튼 **/
function pointChange(type, hideArea, showArea) {
	if (adminId != "playajin") {
		alert(comment.auth_error);
		return;
	}
	if (type != "") {
		$("#"+hideArea).hide();
		$("#"+showArea).show();
	} else {
		$("#"+hideArea).show();
		$("#"+showArea).hide();
	}
}

/** 추가버튼 **/
function addPoint(type) {
	if (adminId != "playajin") {
		alert(comment.auth_error);
		return;
	}
	alert("현재포인트에서 추가로 누적됩니다.");
	$("#add_btn").pageslide({ direction: "left"});
}

/** 콘, 팝콘포인트 수정버튼 **/
function modifyUserPoint(type) {
	if (adminId != "playajin") {
		alert(comment.auth_error);
		return;
	}
	var userId = getInputTextValue("user_id");
	if (type == "CORN") {
		if (confirm(comment.isUpdate)) {
			if (getInputTextValue("change_cornPoint") == "") {
				alert("콘포인트를 입력하세요.");
				focusInputText("change_cornPoint");
				return;
			} else {
				birdService.updateUserPoint(userId, getInputTextValue("change_cornPoint"), type, function(bl) {
					if (bl == true) {
						goUser('modify', userId);
					} else {
						alert(comment.error);
						return;
					}
				});
			}
		}
	} else if (type == "POPCORN") {
		if (confirm(comment.isUpdate)) {
			if (getInputTextValue("change_popcornPoint") == "") {
				alert("팝콘포인트를 입력하세요.");
				focusInputText("change_popcornPoint");
				return;
			} else {
				birdService.updateUserPoint(userId, getInputTextValue("change_popcornPoint"), type, function(bl) {
					if (bl == true) {
						goUser('modify', userId);
					} else {
						alert(comment.error);
						return;
					}
				});
			}
		}
	}
}

/** 콘,팝콘포인트 추가 **/
function addUserPoint(val) {
	if (adminId != "playajin") {
		alert(comment.auth_error);
		return;
	}
	var type = val;
	if (type == "CORN") {
		if (getInputTextValue("add_point") == "") {
			alert("추가할 포인트롤 입력하세요.");
			focusInputText("add_point");
			return;
		}
		if (confirm(comment.isAdd)) {
			birdService.addUserPoint(user_id, getInputTextValue("add_point"), type, function(bl) {
				if (bl == true) {
					goUser('modify', user_id);
				} else {
					alert(comment.error);
					return;
				}
			});
		}
	} else if (type == "POPCORN") {
		if (getInputTextValue("add_point2") == "") {
			alert("추가할 포인트롤 입력하세요.");
			focusInputText("add_point2");
			return;
		}
		if (confirm(comment.isAdd)) {
			birdService.addUserPoint(user_id, getInputTextValue("add_point2"), type, function(bl) {
				if (bl == true) {
					goUser('modify', user_id);
				} else {
					alert(comment.error);
					return;
				}
			});
		}
	}
}

</script>
<body onload="init();">
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="user_id" id="user_id">
<input type="hidden" name="l_phoneNumber" id="l_phoneNumber">
<input type="hidden" name="authDate" id="authDate" value="">
<input type="hidden" name="search_type" id="search_type">
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
		<%@ include file="/common/jsp/user_top_menu.jsp"%>
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">사용자</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('list');">사용자 리스트</span>&nbsp;〉&nbsp;<span onclick="javascript:goUserDetail('modify');">사용자 상세정보</span>
				</div>
				<div class="title_d1">
					<h4>사용자 상세정보</h4>
				</div>
				<div class="anthracite-gradient">
					<span class="icon_id"></span><span>회원정보</span>
						<span id="l_searchType"></span>
					<input type="text" class="input width-small" id="searchValue" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){init(this); return false;}">
					<button type="button" class="button compact blue-gradient glossy" onclick="javascript:init(this);">검색</button>
				</div>
				<div class="formbox">
					<div class="block_label medium">
						<label class="label">
							<span>전화번호</span>
						</label>
						<div class="field">
							<div><span id="phoneNumber"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>이 름</span>
						</label>
						<div class="field">
							<div><span id="userName"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>성 별</span>
						</label>
						<div class="field">
							<div><span id="sex_sel"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>콘 포인트</span>
						</label>
						<div class="field">
							<div id="corn_div">
								<span id="cornPoint"></span><span style="padding-left: 150px;"></span>
								<input type="button" class="button compact blue-gradient glossy" value="변경" onclick="javascript:pointChange('CORN', 'corn_div', 'cornChage_div');">
								<a href="#modal_content" class="button compact modalBtn blue-gradient glossy">추가</a>
							</div>
							<div id="cornChage_div" style="display: none;">
								<input type="text" class="input width-small" id="change_cornPoint" onkeypress="javascript:if(event.keyCode==13){modifyUserPoint('CORN');return false;}">
								<a href="javascript:modifyUserPoint('CORN');" class="button compact blue-gradient glossy">
									<span class="icon-tools">수정</span>
								</a>
								<!-- <input type="button" class="button compact blue-gradient glossy" value="수정" onclick="javascript:modifyUserPoint('CORN');"> -->
								<a href="javascript:pointChange('', 'corn_div', 'cornChage_div');" class="button compact red-gradient glossy">
									<span class="icon-cross">취소</span>
								</a>
								<!-- <input type="button" class="button compact blue-gradient glossy" value="취소" onclick="javascript:pointChange('', 'corn_div', 'cornChage_div');"> -->
							</div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label">
							<span>팝콘 포인트</span>
						</label>
						<div class="field">
							<div id="popcorn_div">
								<span id="popcornPoint"></span><span style="padding-left: 150px;"></span>
								<input type="button" class="button compact blue-gradient glossy" value="변경" onclick="javascript:pointChange('POPCORN', 'popcorn_div', 'popcornChage_div');">
								<a href="#modal_content2" class="button compact modalBtn2 blue-gradient glossy">추가</a>
							</div>
							<div id="popcornChage_div" style="display: none;">
								<input type="text" class="input width-small" id="change_popcornPoint" onkeypress="javascript:if(event.keyCode==13){modifyUserPoint('POPCORN');return false;}">
								<a href="javascript:modifyUserPoint('POPCORN');" class="button compact blue-gradient glossy">
									<span class="icon-tools">수정</span>
								</a>
								<!-- <input type="button" class="button compact blue-gradient glossy" value="수정" onclick="javascript:modifyUserPoint('POPCORN');"> -->
								<a href="javascript:pointChange('', 'popcorn_div', 'popcornChage_div');" class="button compact red-gradient glossy">
									<span class="icon-cross">취소</span>
								</a>
								<!-- <input type="button" class="button compact blue-gradient glossy" value="취소" onclick="javascript:pointChange('', 'popcorn_div', 'popcornChage_div');"> -->
							</div>
						</div> 
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>생 일</span>
						</label>
						<div class="field">
							<div><span id="datepicker"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>회 사</span>
						</label>
						<div class="field">
							<div><span id="company"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>대학교</span>
						</label>
						<div class="field">
							<div><span id="college"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>기기번호</span>
						</label>
						<div class="field">
							<div><span id="uuId"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>페이스북 아이디</span>
						</label>
						<div class="field">
							<div><span id="uuId"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>총 하트포인트</span>
						</label>
						<div class="field">
							<div><span id="totalHeartPoint"></span></div>
						</div>
					</div>
					<div class="block_label medium">
						<label class="label" for="it_1">
							<span>최근 접속일</span>
						</label>
						<div class="field">
							<div><span id="last_authDate"></span></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
	

<div id="modal_content" style="display:none">
	<div class="list_search">
		<input type="hidden" id="modal_type" name="modal_type" value="CORN">
		<input type="text" class="input width-large" id="add_point" placeholder="추가할 포인트 입력" onkeypress="javascript:if(event.keyCode == 13){addUserPoint('CORN'); return false;}">
		<input type="button" class="button compact blue-gradient glossy" value="추가"  onclick="javascript:addUserPoint('CORN');"/>
	</div>
</div>

<div id="modal_content2" style="display:none">
	<div class="list_search">
		<input type="hidden" id="modal_type2" name="modal_type2" value="POPCORN">
		<input type="text" class="input width-large" id="add_point2" placeholder="추가할 포인트 입력" onkeypress="javascript:if(event.keyCode == 13){addUserPoint('POPCORN'); return false;}">
		<input type="button" class="button compact blue-gradient glossy" value="추가"  onclick="javascript:addUserPoint('POPCORN');"/>
	</div>
</div>
</form>
<jsp:include page="/daul/include/footer.jsp" flush="false"/>
</body>
</html>
<script>
	$("#menu_02_01").addClass("active");
	$(".modalBtn").pageslide({ direction: "left"});
	$(".modalBtn2").pageslide({ direction: "left"});
</script>
