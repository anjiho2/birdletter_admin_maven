
function getContextPath() {
	 var offset=location.href.indexOf(location.host)+location.host.length;
	var ctxPath=location.href.substring(offset,location.href.indexOf('/',offset+1));
	return ctxPath;
}

function goHome() {
	location.href= getContextPath();
}

function goUser(page_value, val1) {
	var selValue = getSelectboxValue("sel_searchType");
	with(document.frm) { 
		if (page_value != "") 	
			page_gbn.value = page_value;
		
			if (page_value == "detail" || page_value == "modify") {
				user_id.value = val1;
			}
			if (selValue == undefined) selValue = "";
			if (selValue != "") {
				search_type.value = selValue;		
			}
		action = getContextPath()+"/user.do";
		submit();
	} 
}
 
function goItem(page_value, val) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		
		action = getContextPath()+"/item.do";
		submit();
	}
}

function goBird(page_value, val) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		
		action = getContextPath()+"/bird.do";
		submit();
	}
}

function goUserBird() {
	with(document.frm) {
		page_gbn.value = "letterBirdList";
		userId = $("#user_id").val();
		phoneNumber = $("#l_phoneNumber").val();
		action = getContextPath()+"/bird.do";
		submit();
	}	
}

function goSale(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/sale.do";
		submit();
	}
}

function goNotice(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/noti.do";
		submit();
	}
}

function goNoticeModify(val) {
	with(document.frm) {
		page_gbn.value = "noticeModify";
		noticeIdx.value = val;
		action = getContextPath()+"/noti.do";
		submit();
	}
}

function goStatistics(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/statistics.do";
		submit();
	}
}

function goPush(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/push.do";
		submit();
	}
}

function goBsm(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/bsm.do";
		submit();
	}
}

function excelDownload(url) {
	if (confirm("전체리스트의 엑셀표를 다운받으시겠습니까?")) {
		with (document.frm) {
			action = getContextPath() + "/" + url;
			submit();
		}
	}
}

function goUserDetail(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/user.do";
		submit();
	}
}

function goVersion(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/version.do";
		submit();
	}
}

function goLetter(page_value) {
	with(document.frm) {
		if (page_value != "")
			page_gbn.value = page_value;
		action = getContextPath()+"/letter.do";
		submit();
	}
}

function goMessage() {
	var page_value = "";
	var val = $("#sel_letterType option:selected").val();
	
	if (val != "") {
		if (val == "private") {
			page_value = "userMessageSendStatistics";
		} else if (val == "open") {
			page_value = "userOpenMessageStatistics";
		}
		with (document.frm) {
			if (page_value != "") {
				page_gbn.value = page_value;
			}
			action = getContextPath()+"/statistics.do";
			submit();
		}
	}
}

function goLogout() {
	if (confirm("로그아웃 하시겠습니까?")) {
		with(document.frm) {
			page_gbn.value = "logout";
			action = getContextPath()+"/login.do";
			submit();
		} 
	}
}

function goPage(mapping_value, page_value) {
	with(document.frm) {
		if (mapping_value != "" && page_value != "") {
			page_gbn.value = page_value; 
		}
		action = getContextPath()+"/"+mapping_value+".do";
		submit();
	}
}

function goDetailSquare(val) {
	var letterId = val;
	$("#letter_id").val(letterId);
	with (document.frm) {
		page_gbn.value = "squareListDetail";
		action = getContextPath()+"/square.do";
		submit();
	}
}