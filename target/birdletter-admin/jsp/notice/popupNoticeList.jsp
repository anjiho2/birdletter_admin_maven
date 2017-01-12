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

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/noticeService.js"></script>

<script type="text/javascript">
function init() {
	fn_search("new");
}

function fn_search(val) {
	var sPage = $("#sPage").val();
	var title = $("#search_value").val();
	if (title == undefined) title = "";
	
	if (val == "new") sPage = "1";
	
	dwr.util.removeAllRows("dataList");
	gfn_emptyView("H", "");
	
	noticeService.popupNoticeListCnt(title, function(cnt) {
		if (cnt == "0") {
			gfn_printPageNum_new('0', '10', '10', '1');
			gfn_emptyView("v", "리스트가 없습니다");
		} else {
			if (new Number(cnt) < (10*sPage)) {
				if (new Number(cnt) < (10*(sPage-1))) {
					sPage = 1;
					$("#sPage").val(sPage);
				}
			}
			gfn_printPageNum_new(cnt, '10', '10', sPage);
		}
		noticeService.popupNoticeList(sPage, title, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i < selList.length; i++) {
					cmpList = selList[i];
					if (cmpList != undefined) {
						checkHTML = "<input type='checkbox' name='chk' id='chk' value='"+cmpList.idx+"'/>";
						//titleHTML = '<a href="#" onClick="javascript:goDetailPopupNoticeList('+ "'"+cmpList.idx+"'"+')">'+ellipsis(cmpList.title, 10)+"</a>";
						//urlHTML = '<a href="#" onClick="javascript:goDetailPopupNoticeList('+ "'"+cmpList.idx+"'"+')">'+ellipsis(cmpList.targetUrl,70)+"</a>";
						urlHTML = "<a href=\"javascript:$.pageslide({direction:'left', href:'"+webRoot+"/jsp/popup/detailPopupNotice.jsp?idx="+cmpList.idx+"'})\" class=\"modalBtn\">"+ellipsis(cmpList.targetUrl,70)+"<\/a>";
						var cellData = [
						                function(data) {return checkHTML;},
						                /*
						                function(data) {return titleHTML;},
						                function(data) {return ellipsis(cmpList.content,30);},
						                */
						                function(data) {return urlHTML;},
						                function(data) {return getDateTimeSplitComma(cmpList.startDate);},
						                function(data) {return getDateTimeSplitComma(cmpList.endDate);},
						                function(data) {return getDateTimeSplitComma(cmpList.createDate);},
						                function(data) {return cmpList.imgFileName!=""?cmpList.imgFileName="등록":cmpList.imgFileName="비등록";},
						                function(data) {return cmpList.useYn=="1"?cmpList.useYn="사용":cmpList.useYn="사용안함";}
						                ]
						dwr.util.addRows("dataList", [0], cellData, {escapeHtml:false});
					}
				}
			}
		});
	});
}

//삭제버튼
function popupNoticeDelete() {
	var isChecked = $("input[name=chk]").is(":checked");
	if (isChecked == false) {
		alert("항목을 체크하세요.");
		return;
	}
	if (confirm("선택하신 항목을 삭제하시겠습니까?")) {
		$("input[name=chk]:checked").each(function() {
			var idx = $(this).val();
			if (idx == "") {
				alert("체크된 값이 없습니다");
				return;
			}
			noticeService.deletePopNotice(idx, function(cnt) {
				if (cnt < 1) {
					alert("삭제시 오류가 발생되었습니다.\n관리자에게 문의하세요.");
					return false;
				}
			});
		});
		alert("정상적으로 삭제됬습니다.");
		goNotice("popupNoticeList");
	}
}

function goDetailPopupNoticeList(val) {
	$("#idx").val(val);
	with(document.frm) {
		page_gbn.value = "detailPopupNotice";
		action = getContextPath()+"/noti.do";
		submit();
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
<input type="hidden" name="sPage" id="sPage" value="<%=sPage%>">
<input type="hidden" name="idx" id="idx">
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
					<span class="">공지사항</span>&nbsp;〉&nbsp;<span onclick="javascript:goNotice('noticeList');">팝업 공지사항 목록</span> 
				</div>
			<div class="span10">
				<div class="title_d1"></div>
					<div class="content">
						<div class="portlet">
							<div class="portlet-header anthracite-gradient">
								<span class="icon_id"></span><span>팝업 공지사항 리스트</span>
							</div>
							<div class="portlet-content" >
								<div class="content">
								<div class="list_top">
									<p class="f_L">
										<a href="javascript:popupNoticeDelete();" class="button compact red-gradient glossy">
											<span class="icon-trash">삭제</span>
										</a>
										<!-- <input type="button" class="button compact blue-gradient glossy" onclick="javascript:popupNoticeDelete();" value="삭제"> -->
									</p>
									<p class="f_R">
										<span style="padding-left: 50px;">
											<a href="jsp/popup/insertPopupNotice.jsp" id="insertBtn" class="button compact blue-gradient glossy">
												<span class="icon-pencil">등록</span>
											</a>
										</span>
									</p>
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
											<col width="*" />
											<col width="*" />
										</colgroup>
										<thead>
											<tr>
												<th>
													<input type="checkbox" id="chkAll" onclick="javascript:checkall('chkAll');">
												</th>
												<th>URL</th>
												<th>시작일</th>
												<th>종료일</th>
												<th>작성일</th>
												<th>이미지</th>
												<th>사용여부</th>
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
						</div>
					</div>
				</div>
			</div>
		</div>
	<!--  E: contents-->
	</div>
	<div id="modal_insert" style="display:none">
	<div class="title_d1">
		 <h4>공지사항 등록</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>제목</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="ins_title">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>URL</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="ins_content">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 타입</span>
			</label>
			<div class="field">
				<div>
					<select id="sel_imgType" class="select">
						<option value="">▶이미지타입선택</option>
						<option value="0">이미지  없음</option>
						<option value="1">640 * 200</option>
						<option value="2">640 * 300</option>
						<option value="3">640 * 400</option>
					</select>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 첨부</span>
			</label>
			<div class="field">
				<div>
					<input type="file" class="input width-xlarge" id="modify_attachFile" name="modify_attachFile" onchange="preViewImage(this, 'modify_preView', 'preview_div');">
				</div>
			</div>
		</div>
		<div class="block_label medium" id="preview_div" style="display: none;">
			<label class="label">
				<span>이미지</span>
			</label>
			<div class="field">
				<div>
					<img alt="" src="" id="modify_preView" width="100%" height="200">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>사용여부</span>
			</label>
			<div class="field">
				<div>
					<select id="sel_viewYn" class="select">
						<option value="">▶사용여부선택</option>
						<option value="0">사용함</option>
						<option value="1">사용안함</option>
					</select>
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:modify();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
			</p>
		</div>
	</div>
</div>

<div id="modal_insert" style="display:none">
	<div class="title_d1">
		 <h4>공지사항 등록</h4>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>제목</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="ins_title">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>URL</span>
			</label>
			<div class="field">
				<div>
					<input type="text" class="input width-xlarge" id="ins_content">
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 타입</span>
			</label>
			<div class="field">
				<div>
					<select id="sel_imgType" class="select">
						<option value="">▶이미지타입선택</option>
						<option value="0">이미지  없음</option>
						<option value="1">640 * 200</option>
						<option value="2">640 * 300</option>
						<option value="3">640 * 400</option>
					</select>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>이미지 첨부</span>
			</label>
			<div class="field">
				<div>
					<input type="file" class="input width-xlarge" id="modify_attachFile" name="modify_attachFile" onchange="preViewImage(this, 'modify_preView', 'preview_div');">
				</div>
			</div>
		</div>
		<div class="block_label medium" id="preview_div" style="display: none;">
			<label class="label">
				<span>이미지</span>
			</label>
			<div class="field">
				<div>
					<img alt="" src="" id="modify_preView" width="100%" height="200">				
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>사용여부</span>
			</label>
			<div class="field">
				<div>
					<select id="sel_viewYn" class="select">
						<option value="">▶사용여부선택</option>
						<option value="0">사용함</option>
						<option value="1">사용안함</option>
					</select>
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:modify();" class="button blue-gradient"><span class="icon-tools"> </span>수정</a>
			</p>
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
$("#menu_06_02").addClass("active");
$("#insertBtn").pageslide({direction:"left"});
</script>