<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue("sPage", "1");
	String sPage2 = Util.isNullValue("sPage2", "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%>
<jsp:include page="/daul/include/header.jsp" flush="false"/> 
<script src="<%=webRoot%>/js/datepicker.js"></script>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/userService.js"></script>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/giftService.js"></script>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>

<script type="text/javascript">

function init(val) {
	
	var giftType = val;
	if (giftType == undefined) {
		giftType = "0";
	}
	giftTypeSelectbox(giftType, "l_giftType");
	
	var categoryName = "";
	if (giftType == "0") {
		categoryName = "아이템 지급";
		$("#itemList_div").show();
		$("#present_div").show();
		$("#coin_div").hide();
	} else if (giftType == "1") {
		categoryName = "콘/팝콘 지급";
		$("#itemList_div").hide();
		$("#present_div").hide();
		$("#coin_div").show();
	}
	innerHTML("l_category", categoryName);
	
	fn_search("new");
	fn_search2("new");
	
	daySelecbox("", "l_day", 15);
}

//사용자 테이블 리스트
function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var searchType = getSelectboxValue("sel_searchType");
	var searchValue = getInputTextValue("searchValue");
	
	if (searchType == "" && searchValue != "") {
		alert(comment.select_search_list);
		focusInputText("sel_searchType");
		return;
	}

	if (val == "new") sPage = "1";	//페이징 초기값 셋팅
	
	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");
	
	var userName = "";
	var phoneNumber = "";
	
	if (searchType == "userName")	userName = searchValue;
	else if (searchType == "phoneNumber") phoneNumber = searchValue;
	
	//사용자 프로필 갯수 가져오기
	userService.userProfileCnt(userName, phoneNumber, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '10', '10', '1');
		} else {
			if (new Number(cnt) < (10*sPage)) {
				if (new Number(cnt) < (10*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '10', '10', sPage);
		}
		
		//사용자 프로필 리스트 가져오기
		userService.userProfileList(sPage, 10, userName, phoneNumber, function(selList) {
			if (selList.length > 0) {
				dwr.util.removeAllRows("dataList");
				for (var i=0; i<selList.length; i++) {
					cmpList = selList[i];
					if(cmpList == undefined) {
						bid = selList[0].cmpList.rowNum;
					} else {
						var button = "<a href=\"javascript:addRemoveCheckbox("+"'"+cmpList.phoneNumber+"',"+ "'"+cmpList.userName+"',"+ "'"+cmpList.userId+"'," + "'user'"+")\" class='button compact blue-gradient glossy'><span class='icon_plus_alt'>추가</span></a>"; 
						var cellData = [
							            function(data) {return leadingZeros(cmpList.phoneNumber,11);},
							            function(data) {return cmpList.userName;},
							            function(data) {return getAge(cmpList.birthDay)+ "세";},
							            function(data) {return gfn_isData(cmpList.gender, "MALE", "남성", "여성");},
							            function(data) {return button;}
						           ];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			} else {
				//사용자 프로필 리스트가 없을떄
				gfn_printPageNum_new('0', '10', '10', '1');
				gfn_emptyView('V', comment.blank_search_result);
			}
		});
	});
}

//아이템 페이징 리스트
function fn_search2(val) {
	var sPage = getInputTextValue("sPage2");
	var storeType = getSelectboxValue("sel_storeType");
	var searchType = getSelectboxValue("sel_storeSearchType");
	var searchValue = getInputTextValue("search_value");
	
	if (searchType == "" && searchValue != "") {
		alert("검색종류를 선택하세요");
		focusInputText("sel_searchType");
		return;
	}
	
	selectList2(storeType);//상품종류선택 셀렉트박스
	searhSelectList(searchType);
	
	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList2");
	
	gfn_emptyView("H", "");
	
	var productCode = "";
	var itemCode = "";
	var productName = "";
	
	if (searchType == "PRODUCT_CODE") {
		productCode = searchValue;
	} else if (searchType == "PRODUCT_NAME") {
		productName = searchValue;
	}
	
	itemService.productListCnt(productCode, itemCode, productName, storeType, function(cnt) {
		if (cnt == 0) {
			gfn_printPageNum_new2('0', '10', '10', '1');
			gfn_emptyView('v', comment.blank_list);
		} else {
			if (new Number(cnt) < (10*sPage)) {
				if (new Number(cnt) < (10*(sPage-1))) {
					sPage = 1;
					innerValue("sPage2", sPage);
				}
			}
			gfn_printPageNum_new2(cnt, '10', '10', sPage);
		}
		
		itemService.productList(10, sPage, productCode, itemCode, productName, storeType, function(selList) {
			var webRoot ='<%=webRoot%>';
			var left = "left";
			var productPopup = "productPopup.jsp";
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];	
					if (cmpList != undefined) { 
						var button = "<a href=\"javascript:addRemoveCheckbox("+"'"+cmpList.productName+"',"+ "'"+cmpList.itemCode+"',"+ "'',"+  "'item'"+")\" class='button compact blue-gradient glossy'><span class='icon_plus_alt'>선택</span></a>";
						var cellData = [
						            	function(data) {return cmpList.productName;},
						            	function(data) {return gfn_zeroToZero(cmpList.cornPrice);},
						            	function(data) {return gfn_zeroToZero(cmpList.popcornPrice);},
						            	function(data) {return gfn_isData(gfn_zeroToZero(cmpList.viewYn), "1", "판매중", "판매중지")},
						            	function(data) {return button;}
									];
						dwr.util.addRows("dataList2", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

//추가, 선택 버튼
function addRemoveCheckbox(val, val3, val4, type) {
    var cnt = getInputTextValue("sel_count");
    var checkNum;
    var val2 = leadingZeros(val, 11);
    var tagValue = getInputTextValue(val2);
    
    //사용자 추가일때
    if (type == "user") {
	   	if (Number(tagValue) == Number(val2)) {
	   		if (Number(cnt) >= 0) {
	   			cnt --;
	   			innerValue("sel_count", cnt);
	   			innerHTML("l_countUser", cnt);
	   		}
	   		$("#" + val2).remove();
	   		$("#sub_" + val2).remove();
	   	} else {
	   		if ($("#"+val2).val() == val4) {
	   			alert("이미 선택된 사용자입니다.")
	   			return;
	   		}
			cnt ++;
			innerValue("sel_count", cnt);
			innerHTML("l_countUser", cnt);
			$("#userList").append('<li class="present_li" id="'+val2+'" value="'+val4+'">'+val2+ '('+val3+')'+'<a id="sub_'+val2+'" class="icon_close" onclick\="remove('+"'"+val2+"',"+ "'user'" +')\;"></a></li>&nbsp;&nbsp;&nbsp;');
	   	}
    } else if (type == "item") {	//아이템 추가일때
    	var productName = val;
    	var productCode = val3;
    	var itemCnt = getInputTextValue("sel_item_count");
    	
    	if (itemCnt >= 1) {
    		alert("지급선물은 하나만 등록가능합니다.\n지우고 새로 입력하세요.");
    		return;
    	}
    	itemCnt++;
    	innerValue("sel_item_count", itemCnt);
    	innerValue("present_item_code", productCode);
    	innerValue("present_item_name", productName);
		$("#itemList").append('<li class="item_li" id="'+productCode+'" value="'+productCode+'">'+productName+ '('+productCode+')'+'<a id="sub_'+productCode+'" class="icon_close" onclick\="remove('+"'"+productCode+"',"+ "'item'"+ ')\;"></a></li>');
    }
}

//x버튼 삭제 이벤트
function remove(val, type) {
	var cnt = getInputTextValue("sel_count");
	
	if (type == "user") {
		if (Number(cnt) == 1) {
			cnt --;
			innerValue("sel_count", "0");
			innerHTML("l_countUser", "0");
		} else {
			cnt --;
			innerValue("sel_count", cnt);
			innerHTML("l_countUser", cnt);
		}
	} else if (type == "item") {
		innerValue("present_item_code", " ");
		innerValue("present_item_name", " ");
		innerValue("sel_item_count", "0");
	}
	$("#" + val).remove();
	$("#sub_" + val).remove();
}

//사용자, 아이템 선택된 값 가져오기
function getGiftValue(type) {
	var user_values = [];
	var item_value;
	var coin_value;
	
	if (type == "user") {
		$(".present_li").each(function() {
			user_values.push($(this).val());
		});
		return user_values;
	} else if (type == "item") {
		item_value = getInputTextValue("present_item_code");
		return item_value;
	} else if (type == "coin") {
		coin_value = getInputTextValue("coin_value");
		return coin_value;
	}
}

//선물하기 버튼
function giftPresent() {
	var gift_type = getSelectboxValue("sel_giftType");
	var gift_title = getInputTextValue("gift_title"); 
	var users = getGiftValue("user");
	var users_cnt = getInputTextValue("sel_count");
	var item_name = getInputTextValue("present_item_name");
	var item = getGiftValue("item");
	var coin = getGiftValue("coin");
	var coin_type = getSelectboxValue("sel_coin");
	var sel_expireDay = getSelectboxValue("sel_day");
	var confirm_ment = "▶선물제목 : " + gift_title+ "\n▶지급대상 : " + users_cnt + "명\n▶지급선물 : " + item_name + "\n▶만료일 : " + sel_expireDay + "일 후" + "\n해당 내용으로 지급하시겠습니까?";
	
	//콘/팝콘 일때
	if (gift_type == "1") {
		if (coin_type == "corn") gift_type = "1";
		else if (coin_type == "popcorn") gift_type = "2";
		
		if (coin_type == "corn") {
			confirm_ment = "▶선물제목 : " + gift_title+ "\n▶지급대상 : " + users_cnt + "명\n▶지급선물 : 콘\n▶지급 가격 :" + coin + "\n▶만료일 : " + sel_expireDay + "일 후" + "\n해당 내용으로 지급하시겠습니까?";
		} else {
			confirm_ment = "▶선물제목 : " + gift_title+ "\n▶지급대상 : " + users_cnt + "명\n▶지급선물 : 팝콘\n지급 가격 :" + coin + "\n▶만료일 : " + sel_expireDay + "일 후" + "\n해당 내용으로 지급하시겠습니까?";
		}
	}
	if (coin == "") coin = 0;
	
	if (value_compare_row_check(users_cnt, 1, "지급 대상을 선택하세요.") == false) return;
	if (gift_type == "item") {
		if (value_blank_check(item, "지급 선물을 선택하세요.") == false) return;
	}
	if (input_blank_check("gift_title", "선물 제목을 입력하세요.") == false) return;
	
	if (confirm(confirm_ment)) {	
		giftService.insertGiftBoxList(gift_type, gift_title, users, item, coin, sel_expireDay, function(bl) {
			if (bl == false) {
				alert(comment.error);
			} else {
				alert(comment.success_process);
				isReloadPage(true);
			}
		}); 
	}
}

//글자수 제한
$(document).ready(function() {
	$("#gift_title").on("keyup", function() {
		if ($(this).val().length > 20) {
			alert("선물 제목은 20자까지 입력가능합니다.");
			$(this).val($(this).val().substring(0, 20));
			return;
		}
	});
	$("#coin_value").on("keyup", function() {
		if ($(this).val().length > 5) {
			alert("입력 가능한 자리수가 넘었습니다.");
			$(this).val($(this).val().substring(0, 5));
			return;
		}
	});
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init()"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="sPage2" id="sPage2" value="<%=sPage2%>">
<input type="hidden" name="sel_count" id="sel_count" value="0">
<input type="hidden" name="sel_item_count" id="sel_item_count" value="0">
<input type="hidden" name="search_type" id="search_type">
<input type="hidden" id="present_item_code">
<input type="hidden" id="present_item_name">
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
					<span class="">선물</span>&nbsp;〉&nbsp;<span onclick="javascript:goPage('square','squareList');">선물함 선물지급</span>&nbsp;〉&nbsp;<span id="l_category"></span>
				</div>
				<div><span id="l_giftType"></span></div><br><br>
				<!-- 사용자 리스트 영역 시작 -->
				<div class="span5" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>사용자 목록</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
											<select class="select" id="sel_searchType">
												<option value="">▶검색종류선택</option>
												<option value="phoneNumber">핸드폰번호</option>
												<option value="userName" selected>이 름</option>
											</select>
											<input type="text" class="input width-small" id="searchValue" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){fn_search('new'); return false;}">
											<a href="javascript:fn_search('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="14%" />
									</colgroup>
									<thead>
									<tr>
										<th>전화번호</th>
										<th>이 름</th>
										<th>나 이</th>
										<th>성 별</th>
										<th>버 튼</th>
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
				<!-- 사용자 리스트 영역 끝 -->
				<!-- 아이템 리스트 영역 시작 -->
				<div class="span5" id="itemList_div">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>아이템 목록</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<span id="l_selStore"></span>
										<span id="l_selSearch"></span>
											<input type="text" class="input width-small" id="search_value" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode==13){fn_search2('new'); return false;}">
											<a href="javascript:fn_search2('new');" class="button compact blue-gradient glossy">
												<span class="icon-search">검색</span>
											</a>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="14%" />
									</colgroup>
									<thead>
									<tr>
										<th>상품이름</th>
										<th>콘 가격</th>
										<th>팝콘 가격</th>
										<th>판매 상태</th>
										<th>버 튼</th>
									</tr>
									</thead>
									<tbody id="dataList2"></tbody>
									<tr>
										<td id="emptys2" colspan='23' bgcolor="#ffffff" align='center' valign='middle' style="visibility:hidden"></td>
									</tr>
								</table>
								<%@ include file="/common/inc/com_pageNavi2.inc" %>
							</div>
						</div>
					</div>
				</div>
				<!-- 아이템 리스트 영역 끝 -->
				<!-- 지급 대상 영역 시작 -->
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>지급 대상</span>(총<span id="l_countUser">0</span>명 선택됨)
							</div>
							<div class="portlet-content" >
							<div>
								<!-- <input type="hidden" name="sel_count" id="sel_count" value="0"> -->
		    					<ul id="userList" class="present"></ul>
							</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 지급 대상 영역 끝 -->
				<!-- 지급 선물(아이템) 영역 시작 -->
				<div class="span2" id="present_div">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>지급 선물</span>
							</div>
							<div class="portlet-content" >
							<div>
								<!-- <input type="hidden" name="sel_count" id="sel_count" value="0"> -->
		    					<ul id="itemList" class="present"></ul>
							</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 지급 선물(아이템) 영역 끝 -->
				<!-- 지급 선물(콘/팝콘) 영역 시작 -->
				<div class="span3" id="coin_div" style="display: none;">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>지급 선물</span>
							</div>
							<div class="portlet-content" >
							<div>
								<select id="sel_coin" class="select"">
									<option value="corn">- 콘</option>
									<option value="popcorn">- 팝콘</option>
								</select>
								&nbsp;&nbsp;
								<input type="text" class="input width-large" id="coin_value">
							</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 지급 선물(콘/팝콘) 영역 끝 -->
				<!-- 선물 제목 입력 영역 시작 -->
				<div class="span3" id="coin_div">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>선물 제목</span>
							</div>
							<div class="portlet-content" >
							<div>
								<input type="text" class="input width-xxlarge" id="gift_title">
							</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 선물 제목 입력 영역 끝 -->
				<div class="span2" id="coin_div">
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>만료일 선택</span>
							</div>
							<div class="portlet-content" >
							<div>
								<span id="l_day"></span>	
							</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="span2" id="coin_div">
					<div class="content">
						<a href="javascript:giftPresent();" class="button big green-gradient glossy">
							<span class="">선물지급</span>
						</a>
					</div>
				</div>
				
			</div>
			<!-- 선물지급 버튼 -->
			<!-- 
			<div class="subbody">
				<div class="row-fluid">
					<div class="span2">
						<a href="javascript:giftPresent();" class="button big green-gradient glossy">
							<span class="">선물지급</span>
						</a>
					</div>
				</div>
			</div>
			 -->
			<!-- 선물지급 버튼 끝 -->
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
$("#menu_14_01").addClass("active");
</script>