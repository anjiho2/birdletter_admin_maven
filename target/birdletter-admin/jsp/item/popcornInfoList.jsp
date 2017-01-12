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
<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/itemService.js"></script>
<script type="text/javascript">
var webRoot = '<%=webRoot%>';

function init() {
	fn_search("new");
}

//페이징 리스트
function fn_search(val) {
	var sPage = $("#sPage").val();
	
	if (val == "new")
		sPage = "1";
	
	dwr.util.removeAllRows("dataList", {
		filter:function(tr) {
			return (tr.id != "pattern");
		}
	});
	gfn_emptyView("H", "");
	
	itemService.popcornInfoListCnt(function(cnt) {
		if (cnt == 0) {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView('v', "결과값이 없습니다");
		} else {
			if (new Number(cnt) < (10*sPage)) {
				if (new Number(cnt) < (10*sPage)) {
					sPage = 1;
					$("#sPage").val(sPage);
				}
			}
			gfn_printPageNum_new(cnt, '10', '10', sPage);
		}
		
		itemService.popcornInfoList(sPage, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];	
					if (cmpList != undefined) {
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.popcornIdx+"'/>";
						//btnHTML = '<a href="#" onclick="javascript:modify_modal('+ "'"+cmpList.popcornIdx+"'"+')">'+cmpList.popcornName+"</a>";
						btnHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/popcornInfo_modify_popup.jsp?popcornIdx="+cmpList.popcornIdx+"'})\" class=\"modalBtn\">"+cmpList.popcornName+"<\/a>";
						var cellData = [
						            	function(data) {return checkHTML;},
						            	function(data) {return btnHTML;},
						            	function(data) {return cmpList.popcornPoint;},
						            	function(data) {return cmpList.cornCount;},
									];
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

//수정페이지show
function modify_modal(popcornIdx) {
	$("#l_popcornIdx").val(popcornIdx);
	itemService.popcornInfoDetail(popcornIdx, function(popcornInfo) {
		$("#modal_popcornName").val(popcornInfo.popcornName);
		$("#modal_popcornPoint").val(popcornInfo.popcornPoint);
		$("#modal_cornCount").val(popcornInfo.cornCount);
	});
	$("#modify_modal").modal('show');
}

//수정버튼
function modify() {
	var popcornIdx = $("#l_popcornIdx").val();
	var popcornName = $("#modal_popcornName").val();
	var popcornPoint = $("#modal_popcornPoint").val();
	var cornCount = $("#modal_cornCount").val();
	
	if (popcornName == "") {
		alert("팝콘이름을 입력하세요.");
		$("#modal_popcornName").focus();
		return;
	}
	if (popcornPoint == "") {
		alert("팝콘가격을 입력하세요.");
		$("#modal_popcornPoint").focus();
		return;
	}
	if (cornCount == "") {
		alert("구매가능 콘포인트를 선택하세요.");
		$("#modal_cornCount").focus();
		return;
	}
	
	var dataObj = {popcornIdx:popcornIdx, popcornName:popcornName, popcornPoint:popcornPoint, cornCount:cornCount};
	
	if (confirm("수정하시겠습니까?")) {
		itemService.popocornInfoModify(dataObj, function(bl) {
			if (bl == true) {
				alert("수정됬습니다.");
				$("#modify_modal").modal('hide');
				location.reload(true);
			} else {
				alert("수정 실패하였습니다. 관리자에게 문의하세요.");
				return;
			}
		});
	}
}

//삭제버튼
function checkVal() {
	var isChecked = $("input[name=chk]").is(":checked");
	if (isChecked == false) {
		alert("삭제할 항목을 체크하세요.");
		return;
	}
	if (confirm("선택하신 항목을 삭제하시겠습니까?")) {
		
		$("input[name=chk]:checked").each(function() {
			var isDelete = "";
			var popcornIdx = $(this).val();
			if (popcornIdx == "") {
				alert("체크된 값이 없습니다");
				return;
			}
			itemService.cornPopcornInfoDelete(popcornIdx, "POPCORN", function(bl) {
				if (bl == true) {
					isDelete+= "t";
				} else {
					isDelete+= "f";
				}
			});
		});
		alert("목록이 삭제되었습니다.");
		location.reload(true);
	}
}

function insert_modal() {
	$("#insert_modal").modal('show');
}

//신규등록페이지 등록버튼
function insert() {
	var popcornName = $("#insert_popcornName").val();
	var popcornPrice = $("#insert_popcornPrice").val();
	var cornCount = $("#insert_cornCount").val();
	
	if (popcornName == "") {
		alert("팝콘이름을 입력하세요.");
		$("#insert_popcornName").focus();
		return;
	}
	if (popcornPrice == "") {
		alert("팝콘가격을 입력하세요.");
		$("#insert_popcornPrice").focus();
		return;
	}
	if (cornCount == "") {
		alert("콘가격을 입력하세요.");
		$("#insert_cornCount").focus();
		return;
	}
	
	if (confirm("등록하시겠습니까?")) {
		itemService.popcornInfoInsert(popcornName, popcornPrice, cornCount, function(bl) {
			if (bl == true) {
				alert("팝콘정보가 등록됬습니다.");
				$("#insert_modal").modal('hide');
				goItem("popcornInfoList");
			} else {
				alert("등록에 실패하였습니다. 관리자에게 문의하세요.");
				return;
			}
		});
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="get">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<div class="leftarea">
<!--  S : nav_area-->
<jsp:include page="/daul/include/nav.jsp" flush="false"/>
<!--  E : nav_area-->
</div>
<!--  S : rightarea -->
<div class="rightarea span8">
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('popcornInfoList');">팝콘 판매 정보</span>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>팝콘 판매 정보</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<a href="javascript:checkVal();" class="button compact red-gradient glossy">
												<span class="icon-trash">삭제</span>
											</a>
											<!-- <input type="button" class="button compact blue-gradient glossy" onclick="javascript:checkVal();" value="삭제"> -->
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
									</colgroup>
									<thead>
									<tr>
										<th><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th>
										<th>팝콘이름</th>
										<th>팝콘가격</th>
										<th>구매가능 콘 포인트</th>
									</tr>
									</thead>
									<tbody id="dataList">
										<tr id="pattern" style="display: none;">
											<td style="padding-left: 27px;"><span id="chkBox"></span></td>
											<td><span id="l_productCode"></span></td>
											<td><span id="l_costum"></span></td>
											<td><span id="l_themeHouse"></span></td>
											<td><span id="l_themeBg"></span></td>
										</tr>
									</tbody>
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
$("#menu_03_06").addClass("active");
</script>