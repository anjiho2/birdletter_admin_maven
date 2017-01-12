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

function init() {
	fn_search("new");
}

//페이징 리스트
function fn_search(val) {
	var sPage = $("#sPage").val();
	var searchType = "normal";
	//스토어에 등록안된 아이템 보기 체크박스 체크여부
	if ($("#regCheck").is(":checked")) {
		searchType = $("#regCheck").val();
	}
	
	if (val == "new")
		sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	
	gfn_emptyView("H", "");
	
	var productCode = "";
	
	itemService.collectionItemInfoListCnt(productCode, searchType, function(cnt) {
		if (cnt == 0) {
			gfn_printPageNum_new('0', '15', '15', '1');
			gfn_emptyView('v', comment.blank_list);
		} else {
			if (new Number(cnt) < (15*sPage)) {
				if (new Number(cnt) < (15*(sPage-1))) {
					sPage = 1;
					innerValue("sPage", sPage);
				}
			}
			gfn_printPageNum_new(cnt, '15', '15', sPage);
		}
		
		itemService.collectionItemInfoList(sPage, productCode, searchType, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];	
					if (cmpList != undefined) {
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.productCode+"'/>";
						btnHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/collection_modify_popup.jsp?productCode="+cmpList.productCode+"&collectionName="+cmpList.collectionName+"'})\" class=\"modalBtn\">"+cmpList.collectionName+"<\/a>";
						var cellData = [
						                function(data) {return checkHTML;},
						                function(data) {return btnHTML;},
						                function(data) {return cmpList.costumCode;},
						                function(data) {return cmpList.themeHouseCode;},
						                function(data) {return cmpList.themeBgCode;},
						                function(data) {return cmpList.collectionDesc;}
						                ]
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

//삭제버튼
function checkVal() {
	var isChecked = $("input[name=chk]").is(":checked");
	if (isChecked == false) {
		alert(comment.check_item);
		return;
	}
	if (confirm(comment.isDelete)) {
		$("input[name=chk]:checked").each(function() {
			var productCode = $(this).val();
			if (productCode == "") {
				alert(comment.blank_check);
				return;
			}
			itemService.collectionItemDelete(productCode, function(bl) {	//콜렉션정보 삭제
				if (bl == true) {
					location.reload(true);
				} else {
					alert(comment.error);
					return;
				}
			});
		});
	}
}

function goInsertStore() {
	var str = "";
	var isChecked = $("input[name=chk]").is(":checked");
	
	if (isChecked == false) {
		jAlert("스토어에 등록할 콜렉션을 체크하세요.");
		return;
	}
	$("input[name=chk]:checked").each(function(index) {
		str += $(this).val()+",";
	});
	$("#store_type").val('COLLECTION');
	$("#item_idxs").val(str);
	
	goItem('storeInsert');
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="get">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="item_idxs" id="item_idxs">
<input type="hidden" name="store_type" id="store_type">
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
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('collectionList');">콜렉션 목록</span>
				</div>
				<div class="span10" >
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>콜렉션 목록</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<a href="javascript:checkVal('delete');" class="button compact red-gradient glossy">
												<span class="icon-trash">삭제</span>
											</a>
											<span style="padding-left: 100px;"></span>
											<label for="checkbox-2" class="checkbox">
												<input type="checkbox" name="regCheck" id="regCheck" value="reg" onchange="javascript:fn_search('new')">스토어에 등록안된 콜렉션 보기
											</label>
										</p>
										<p class="f_R">
										<!-- <a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/collection_modify_popup.jsp?productCode="+productCode+"&collectionName="+collectionName+"'})\" class=\"modalBtn\">"+collectionName+"<\/a> -->
											<a href="<%=webRoot %>/jsp/popup/collection_modify_popup.jsp" class="button compact blue-gradient glossy" id="insertBtn">
												<span class="icon-pencil">콜렉션 등록</span>
											</a>
											<a href="javascript:goInsertStore();" class="button compact blue-gradient glossy" id="insertBtn">
												<span class="icon-cart">스토어 등록</span>
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
									</colgroup>
									<thead>
										<tr>
											<th>
												<input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');">
											</th>
											<th>콜렉션</th>
											<th>스페셜 의상</th>
											<th>스페셜 둥지</th>
											<th>스페셜 숲</th>
											<th>설 명</th>
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
$("#menu_03_04").addClass("active");
$("#insertBtn").pageslide({direction:"left"});
</script>