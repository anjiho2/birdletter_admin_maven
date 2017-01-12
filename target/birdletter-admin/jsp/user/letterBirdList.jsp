<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
	String userId = Util.isNullValue(request.getParameter("user_id"), "1");
	String phoneNumber = Util.isNullValue(request.getParameter("l_phoneNumber"), "1");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 
<jsp:include page="/daul/include/header.jsp" flush="false"/>
<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>
<script type="text/javascript" src="<%=webRoot%>/dwr/interface/birdService.js"></script>
<%-- <script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script> --%>

<script src="<%=webRoot%>/js/page.js"></script>

<script type="text/javascript">

function init() {
	$("#user_top_menu_1").attr("class","button silver-gradient glossy active");
	fn_search("new");  
}
  
function fn_search(val) {
	var sPage = getInputTextValue("sPage");
	var userId = '<%=userId%>';
	var phoneNumber = '<%=phoneNumber%>';

	if (val == "new") sPage = "1";	//페이징 초기값 셋팅
	
	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");
	
	var productName = getInputTextValue("productName");	//검책창 값
	
	//사용자 새 갯수 가져오기
	birdService.userLetterBirdListCnt(userId, productName, function(cnt) {
		innerHTML("total_cnt", cnt);
		if (cnt == 0) {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView("v", comment.blank_list);
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
	
		//사용자 프로필 리스트 가져오기
		birdService.userLetterBirdList(sPage, userId, productName, function(selList) {
			if (selList.length > 0) {
				for (var i=0; i<selList.length; i++) {
					cmpList = selList[i];
					if(cmpList != undefined) {
						checkBoxHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.birdId+"'/>";
						var cellData = [
										function(data) {return checkBoxHTML;},
										function(data) {return gfn_isData(cmpList.birdName, "", "없음", cmpList.birdName);},
										function(data) {return gfn_isData(cmpList.energyCon, "", "없음", cmpList.energyCon);},
										function(data) {return gfn_isData(gfn_zeroToZero(cmpList.birdSlot), "0", "없음", cmpList.birdSlot);},
										function(data) {return gfn_isData(cmpList.productName, null, "기본", cmpList.productName);}
						             ];
						 dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

function checkVal() {
	var isChecked = isCheckedCheckbox("chk", "NAME");
	if (isChecked == false) {
		alert(comment.check_item);
		return;
	}
	if (confirm(comment.isDelete)) {
		$("input[name=chk]:checked").each(function() {
			var birdId = $(this).val();
			if (birdId == "") {
				alert(comment.blank_check);
				return;
			}
			
			$("#loader").show();
			birdService.deleteUserLetterBird(birdId, function(bl) {
				if (bl == true) {
					isReloadPage(true);
				} else {
					alert(comment.error);
					return;
				}
				$("#loader").hide();
			});
		});
	}
}

function itemCodeModify() {
	var birdId = $("#birdId").val();
	var itemCode = $("#sel_itemList option:selected").val();
	var productName = $("#sel_itemList option:selected").text();
	
	if (confirm(productName +"(으)로 수정하시겠습니까?")) {
		birdService.letterBirdBodyTypeModify(birdId, itemCode, function(bl) {
			if (bl == true) {
				alert("수정완료됬습니다.");
				$("#login-modal").modal('hide');
				location.reload(true);
			} else {
				alert("수정 실패하였습니다. 관리자에게 문의하세요.");
				return;
			}
		});
	}
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<!-- E : lefrarea -->
<div class="rightarea span8">
		<div class="subbody">
		<%@ include file="/common/jsp/user_top_menu.jsp"%>
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">사용자</span>&nbsp;〉&nbsp;<span onclick="javascript:goUser('list');">사용자 리스트</span>&nbsp;〉&nbsp;<span onclick="javascript:goUserDetail('modify');">사용자 새정보</span>
				</div>
				<div class="span10" >
				<div class="title_d1">
					<h4><%=phoneNumber%>님</h4>
				</div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>새 정보</span>
									<input type="text" class="input width-small" id="productName" placeholder="검색어 입력" onkeypress="javascript:if(event.keyCode == 13){fn_search('new'); return false;}">
									<button type="button" class="button blue-gradient glossy" onclick="javascript:fn_search('new');">검색</button>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
											총 : <span id="total_cnt"></span>개
										</div>
								</div>
								<p class="f_L">
									<a href="javascript:checkVal();" class="button compact red-gradient glossy">
										<span class="icon-trash">삭제</span>
									</a>
								</p>
								<br><br>
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
											<th align="left"><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th>
											<th>이 름</th>
											<th>에너지콘</th>
											<th>슬 롯</th>
											<th>종 류</th>
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
</body>
</html>
<script>$("#menu_02_01").addClass("active");</script>