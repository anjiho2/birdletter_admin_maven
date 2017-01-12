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
	fn_search();
}

function fn_search() {
	//콘 판매 정보 리스트
	itemService.cornInfoList(1, function(selList) {
		if (selList.length > 0) {
			for (var i = 0; i < selList.length; i++) {
				cmpList = selList[i];	
				if (cmpList != undefined) {
					checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"'/>";
					//btnHTML = '<a href="#" onclick="javascript:modify_modal('+ "'"+cmpList.cornIdx+"'"+')">'+cmpList.cornDesc+"</a>";
					btnHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/cornInfo_modify_popup.jsp?cornIdx="+cmpList.cornIdx+"'})\" class=\"modalBtn\">"+cmpList.cornDesc+"<\/a>";
					var cellData = [
					            	/* function(data) {return checkHTML;}, */
					            	function(data) {return btnHTML;},
					            	function(data) {return cmpList.cornCount;},
					            	function(data) {return cmpList.price+"원";},
					            	function(data) {return cmpList.discountPercent=='0'?cmpList.discountPercent="0"+"%" : cmpList.discountPercent+"%"}
								];
					dwr.util.addRows("cornDataList", [0], cellData, {escapeHtml:false});
				}
			}
		}
	});
	//팝콘 판매 정보 리스트
	itemService.popcornInfoList(1, function(selList) {
		if (selList.length > 0) {
			for (var i = 0; i < selList.length; i++) {
				cmpList = selList[i];	
				if (cmpList != undefined) {
					checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.popcornIdx+"'/>";
					//btnHTML = '<a href="#" onclick="javascript:modify_modal('+ "'"+cmpList.popcornIdx+"'"+')">'+cmpList.popcornName+"</a>";
					btnHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/popcornInfo_modify_popup.jsp?popcornIdx="+cmpList.popcornIdx+"'})\" class=\"modalBtn\">"+cmpList.popcornName+"<\/a>";
					var cellData = [
					            	/* function(data) {return checkHTML;}, */
					            	function(data) {return btnHTML;},
					            	function(data) {return cmpList.popcornPoint;},
					            	function(data) {return cmpList.cornCount;},
								];
					dwr.util.addRows("popcornDataList", [0], cellData, {escapeHtml:false});
				}
			}
		}
	});
}

//콘 수정페이지show
function modify_modal(cornIdx) {
	$("#l_cornIdx").val(cornIdx);
	itemService.cornInfoDetail(cornIdx, function(cornInfo) {
		$("#modal_cornDesc").val(cornInfo.cornDesc);
		$("#modal_cornCount").val(cornInfo.cornCount);
		discountSelectBoxByOne(cornInfo.discountPercent, 'modal_discountPercent');
	});
	$("#modify_modal").modal('show');
}

//팝콘 수정페이지show
function modify_modal(popcornIdx) {
	$("#l_popcornIdx").val(popcornIdx);
	itemService.popcornInfoDetail(popcornIdx, function(popcornInfo) {
		$("#modal_popcornName").val(popcornInfo.popcornName);
		$("#modal_popcornPoint").val(popcornInfo.popcornPoint);
		$("#modal_cornCount").val(popcornInfo.cornCount);
	});
	$("#modify_modal").modal('show');
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
<div class="rightarea span10">
		<div class="subbody">
			<div class="row-fluid">
				<div class="breadcrumb span10">
					<span class="">아이템</span>&nbsp;〉&nbsp;<span onclick="javascript:goItem('cornInfoList');">콘/팝콘 판매 정보</span>
				</div>
				<div class="span5">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>콘 판매 정보</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
									<div class="list_top">
										<p class="f_L">
											<!-- 
											<a href="javascript:checkVal();" class="button compact red-gradient glossy">
												<span class="icon-trash">삭제</span>
											</a>
											 -->
											<!-- <input type="button" class="button compact blue-gradient glossy" onclick="javascript:checkVal();" value="삭제"> -->
										</p>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
									<!-- 	<col width="2%" /> -->
										<col width="*" />
										<col width="*" />
										<col width="*" />
										<col width="*" />
									</colgroup>
									<thead>
									<tr>
										<!-- <th><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th> -->
										<th>콘 이름</th>
										<th>콘 개수</th>
										<th>콘 가격</th>
										<th>할인률</th>
									</tr>
									</thead>
									<tbody id="cornDataList"></tbody>
									<tr>
										<td id="emptys" colspan='23' bgcolor="#ffffff" align='center' valign='middle' style="visibility:hidden"></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				
				<div class="span5" >
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
										<!-- 
											<a href="javascript:checkVal();" class="button compact red-gradient glossy">
												<span class="icon-trash">삭제</span>
											</a>
										 -->	
											<!-- <input type="button" class="button compact blue-gradient glossy" onclick="javascript:checkVal();" value="삭제"> -->
										</p>
									</div>
								</div>
								<br>
								<table class="table_list">
									<colgroup>
										<!-- <col width="2%" /> -->
										<col width="*" />
										<col width="*" />
										<col width="*" />
									</colgroup>
									<thead>
									<tr>
										<!-- <th><input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');"></th> -->
										<th>팝콘이름</th>
										<th>팝콘가격</th>
										<th>구매가능 콘 포인트</th>
									</tr>
									</thead>
									<tbody id="popcornDataList">
										<tr id="pattern" style="display: none;">
											<!-- <td style="padding-left: 27px;"><span id="chkBox"></span></td> -->
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
<script>$("#menu_03_05").addClass("active");
$("#modifyBtn").pageslide({direction:"left"});
</script>