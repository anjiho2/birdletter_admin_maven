<%@page import="java.net.URLEncoder"%>
<%@page import="com.challabros.birdletter.admin.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sPage = Util.isNullValue(request.getParameter("sPage"), "1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/jsp/top.jsp"%> 

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/notiService.js"></script>

<script src="<%=webRoot%>/js/page.js"></script>
<script src="<%=webRoot%>/js/selectbox.js"></script>
<script src="<%=webRoot%>/js/jQuery-alert.js"></script>
<script src="<%=webRoot%>/js/iphone-style-checkboxes.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/modal.css">
<script type="text/javascript">
function init() {
	
}
//등록버튼
function insert() {
	var data = new FormData();
    $.each($('#attachFile')[0].files, function(i, file) {          
        data.append('file-' + i, file);
    });
    var attachFile = fn_clearFilePath($('#attachFile').val());
   
    //파일이 있을떄만 파일명 작업 진행
    if (attachFile != "") {
	    var makeFile = attachFile.split(".");
	    var finalFile = makeFile[0]+"_birdletter."+makeFile[1];
    }
    
	var title = $("#ins_title").val();
	var content = $("#ins_content").val();	
	var imgType = $("#sel_imgType option:selected").val();
	var viewYn = $("#sel_viewYn option:selected").val();
	//파일명이 없으면 파일명을 공백값으로셋팅
	if (finalFile == undefined) finalFile = "";

	if (title == "") {
		$.alert("제목을 입력하세요.", "알림");
		$("#title").focus();
		return;
	}
	if (content == "") {
		$.alert("URL을 입력하세요.", "알림");
		$("#ins_content").focus();
		return;
	}
	if (imgType == "") {
		$.alert("이미지타입을 선택하세요.", "알림");
		$("#sel_imgType").focus();
		return;
	}
	if (viewYn == "") {
		$.alert("사용여부를 선택하세요.", "알림");
		$("#sel_viewYn").focus();
		return;
	}
	
	if (confirm("내용을 등록하시겠습니까?")) {
		if (finalFile != "") {
			$.ajax({
				url: "<%=webRoot%>/file.do",
	            method : "post",
	            dataType: "JSON",
	            data: data,
	            cache: false,
	            processData: false,
	            contentType: false,
	            success: function(data) {
				}
			});
		}
		notiService.notiInsert(title, content, finalFile, imgType, viewYn, function(bl) {
			if (bl == true) {
				alert("공지사항이 등록됬습니다");
				parent.document.location.reload();
			} else {
				$.alert("공지사항등록에 실패했습니다.\n관리자에게 문의하세요.", "에러");
				return;
			}
		}); 
	}
}

$(document).ready(function() {
    $(':checkbox').iphoneStyle();
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init();"> 
<form name="frm" method="post">
<input type="hidden" id="modify_noticeId">
	<div class="list_search">
		<div class="f_L">
			<h4>스토어 등록</h4>				
		</div>
	</div>
	<div class="formbox">
		<div class="block_label medium">
			<label class="label">
				<span>팝콘 가격</span>
			</label>
			<div class="field">
				<div>
					<div><input type="text" class="input width-small" id="l_popcornPrice"></div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>콘 가격</span>
			</label>
			<div class="field">
				<div>
					<div><input type="text" class="input width-small" id="l_cornPrice"></div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>상위 메뉴</span>
			</label>
			<div class="field">
				<div>
					<div><span id="l_itemCategotySelect"></span></div>
				</div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>하위 메뉴</span>
			</label>
			<div class="field">
				<div><span id="l_itemTypeSelect"></span></div>
			</div>
		</div>
		<div class="block_label medium" id="preview_div">
			<label class="label">
				<span>할인율</span>
			</label>
			<div class="field">
				<div><span id="l_discount"></span></div>
			</div>
		</div>
		<div class="block_label medium">
			<label class="label">
				<span>판매여부</span>
			</label>
			<div class="field">
				<div>
					<!-- <input type="checkbox" value="0" id="useYn"> -->
					<span id="l_viewYn"></span>
				</div>
			</div>
		</div>
		<div class="group list_bottom">
			<p class="f_L">
				<a href="javascript:insert();" class="button compact blue-gradient glossy">
					<span class="icon-pencil">등록</span>
				</a>
			</p>
		</div>
	</div>
	<div class="group list_bottom">
		<p class="f_L">
			<a href="javascript:collectionInsert();" class="button green-gradient"><span class="icon-pencil"></span>등록</a>
		</p>
	</div>
</form>
</body>
</html>

