/**
 * <PRE>
 * 1. Comment :  스토어 리스트 검색종류 셀렉트박스
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 01. 06
 * </PRE>
 * @param searchVal
 */
function searhSelectList(searchVal) {
	itemService.searchSelectList(function(list) {
		selHTML = "<select class='select' id='sel_storeSearchType'>";
		selHTML+= "<option value=''>▶검색종류선택</option>";
		for(var i=0; i<list.length; i++) {
			if (list[i].itemCode == searchVal)
				selHTML+= "<option value="+list[i].itemCode+" selected>"+ list[i].itemName +"</option>";
			else
				selHTML+= "<option value="+list[i].itemCode+">"+ list[i].itemName +"</option>";
		}
		selHTML+= "</select>";
		$("#l_selSearch").html(selHTML);
	});
}

/**
 * <PRE>
 * 1. Comment :  스토어 리스트 아이템 종류 선택 셀렉트박스
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 01. 06
 * </PRE>
 * @param type
 */
function selectList(type) {
	itemService.storeTypeList(function(list) {
		selHTML = "<select class='select' id='sel_storeType' onChange='javascript:init();'>";
		selHTML+= "<option value=''>▶상품종류선택</option>";
		
		for(var i=0; i<list.length; i++) {
			if (list[i].itemCode == type)
				selHTML+= "<option value="+list[i].itemCode+" selected>"+list[i].itemName+"</option>";
			else
				selHTML+= "<option value="+list[i].itemCode+">"+list[i].itemName+"</option>";
		}
		selHTML+= "</select>";
		$("#l_selStore").html(selHTML);
	});
}

function selectList2(type) {
	itemService.storeTypeList(function(list) {
		var n = "new"
		selHTML = "<select class='select' id='sel_storeType' onChange='javascript:fn_search2("+ '"'+n+'"'+");'>";
		selHTML+= "<option value=''>▶상품종류선택</option>";
		for(var i=0; i<list.length; i++) {
			if (list[i].itemCode == type)
				selHTML+= "<option value="+list[i].itemCode+" selected>"+list[i].itemName+"</option>";
			else
				selHTML+= "<option value="+list[i].itemCode+">"+list[i].itemName+"</option>";
		}
		selHTML+= "</select>";
		$("#l_selStore").html(selHTML);
	});
}


/**
 * <PRE>
 * 1. Comment :  상품수정페이지 상위메뉴 셀렉트박스
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 01. 06
 * </PRE>
 * @param itemCategory
 * @param itemType
 */
function itemCategorySelectBox(itemCategory, itemType) {
	itemService.itemCategotySelectbox(function(list) {
		categoryHTML = "<select class='select' id='sel_itemCategory' onChange='javascript:itemTypeSelectBox(this.value)'>";
		categoryHTML+= "<option value=''>▶상위메뉴선택</option>";
		for(var i=0; i<list.length; i++) {
			if (list[i] == itemCategory)
				categoryHTML+= "<option value="+list[i]+" selected>"+itemCategotyName(list[i])+"</option>";
			else
				categoryHTML+= "<option value="+list[i]+">"+itemCategotyName(list[i])+"</option>";
		}
		categoryHTML+= "</select>";
		$("#l_itemCategotySelect").html(categoryHTML);
	});
}

/**
 * <PRE>
 * 1. Comment :  상품수정페이지 하위메뉴 셀렉트박스
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 01. 06
 * </PRE>
 * @param itemCategory
 * @param itemType
 */
function itemTypeSelectBox(itemCategory, itemType) {
	if (itemCategory == "3") {
		itemService.itemTypeSelectBox(function(list) {
			categoryHTML = "<select class='select' id='sel_itemTyepSelectBox'>";
			categoryHTML+= "<option value=''>▶하위메뉴선택</option>";
			for(var i=0; i<list.length; i++) {
				if (list[i] == itemType)
					categoryHTML+= "<option value="+list[i]+" selected>"+itemTypeName(itemCategory, list[i])+"</option>";
				else
					categoryHTML+= "<option value="+list[i]+">"+itemTypeName(itemCategory, list[i])+"</option>";
			}
			categoryHTML+= "</select>";
			$("#l_itemTypeSelect").html(categoryHTML);
		});
	} else if (itemCategory == "4") {
		itemService.itemTypeSelectBox(function(list) {
			categoryHTML = "<select class='select' id='sel_itemTyepSelectBox'>";
			categoryHTML+= "<option value=''>▶하위메뉴선택</option>";
			for(var i=0; i<3; i++) {
				if (list[i] == itemType)
					categoryHTML+= "<option value="+list[i]+" selected>"+itemTypeName(itemCategory, list[i])+"</option>";
				else
					categoryHTML+= "<option value="+list[i]+">"+itemTypeName(itemCategory, list[i])+"</option>";
			}
			categoryHTML+= "</select>";
			$("#l_itemTypeSelect").html(categoryHTML);
		});
	} else if (itemCategory == "5") {
		itemService.itemTypeSelectBox(function(list) {
			categoryHTML = "<select class='select' id='sel_itemTyepSelectBox'>";
			categoryHTML+= "<option value=''>▶하위메뉴선택</option>";
			for(var i=0; i<2; i++) {
				if (list[i] == itemType)
					categoryHTML+= "<option value="+list[i]+" selected>"+itemTypeName(itemCategory, list[i])+"</option>";
				else
					categoryHTML+= "<option value="+list[i]+">"+itemTypeName(itemCategory, list[i])+"</option>";
			}
			categoryHTML+= "</select>";
			$("#l_itemTypeSelect").html(categoryHTML);
		});
	} else if (itemCategory == "2") {
		itemService.itemTypeSelectBox(function(list) {
			categoryHTML = "<select class='select' id='sel_itemTyepSelectBox'>";
			categoryHTML+= "<option value=''>▶하위메뉴선택</option>";
			for(var i=0; i<list.length; i++) {
				if (list[i] == itemType)
					categoryHTML+= "<option value="+list[i]+" selected>"+itemTypeName(itemCategory, list[i])+"</option>";
				else
					categoryHTML+= "<option value="+list[i]+">"+itemTypeName(itemCategory, list[i])+"</option>";
			}
			categoryHTML+= "</select>";
			$("#l_itemTypeSelect").html(categoryHTML);
		});
	} else {
			categoryHTML = "<select class='select' id='sel_itemTyepSelectBox'>";
			categoryHTML+= "<option value=''>▶하위메뉴선택</option>";
			categoryHTML+= "<option value='1' selected>기본</option>";
			categoryHTML+= "</select>";
			$("#l_itemTypeSelect").html(categoryHTML);
	}
	
}

/**
 * <PRE>
 * 1. Comment :  상품수정페이지 할인률(5%증가) 셀렉트 박스
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 01. 06
 * </PRE>
 * @param discountPercent
 */
function discountSelectBox(discountPercent) {
	discountHTML = "<select class='select' id='sel_discount'>";
	discountHTML+= "<option value=''>▶할인률선택</option>";
	for (var i= 0; i <= 50; i+=5) {
		if (i == discountPercent)
			discountHTML+= "<option value="+i+" selected>"+i+"%</option>";
		else
			discountHTML+= "<option value="+i+">"+i+"%</option>";
	}
	$("#l_discount").html(discountHTML);
}

function discountSelectBoxByOne(discountPercent, tagValue) {
	discountHTML = "<select class='select' id='sel_discount'>";
	discountHTML+= "<option value=''>▶할인률선택</option>";
	for (var i= 0; i <= 50; i++) {
		if (i == discountPercent)
			discountHTML+= "<option value="+i+" selected>"+i+"%</option>";
		else
			discountHTML+= "<option value="+i+">"+i+"%</option>";
	}
	$("#"+tagValue).html(discountHTML);
}
 
function viewYnSelectBox(viewYn) {
	itemService.viewYnSelectBox(function(list) {
		if (viewYn == "1") 
			str = "true";
		else if (viewYn == "0") 
			str = "false";
		
		viewHTML = "<select class='select' id='sel_viewYn'>";
		viewHTML+= "<option value=''>▶판매여부선택</option>";
		for(var i=0; i < list.length; i++) {
			if (list[i] == str) {
				viewHTML+= "<option value="+list[i]+" selected>"+viewYnName(list[i])+"</option>";
			}else{
				viewHTML+= "<option value="+list[i]+">"+viewYnName(list[i])+"</option>";
			}
		}
		viewHTML+= "</select>";
		$("#l_viewYn").html(viewHTML);
	});
}

function selectListByInsert(type) {
	itemService.storeTypeList(function(list) {
		selHTML = "<select class='select' id='sel_storeType' onChange='javascript:init(this.value);'>";
		selHTML+= "<option value=''>▶상품종류선택</option>";
		for(var i=0; i<list.length; i++) {
			if (list[i] == type)
				selHTML+= "<option value="+list[i]+" selected>"+storeName(list[i])+"</option>";
			else
				selHTML+= "<option value="+list[i]+">"+storeName(list[i])+"</option>";
		}
		selHTML+= "</select>";
		$("#l_selStore").html(selHTML);
	});
}

function selectItemList(spanId, onchangeFunction, itemType) { 
	itemService.itemTypeList(function(list) {
		selectHTML = "<select class='select' id='sel_itemType' onChange='"+onchangeFunction+"'>";
		selectHTML+= "<option value=''>▶아이템종류선택</option>";
//		selectHTML+= "<option value='COLLECTION'>컬렉션</option>";
		for (var i = 0; i < list.length; i++) {
			if (list[i].itemCode == itemType) {
				selectHTML+= "<option value="+list[i].itemCode+" selected>"+list[i].itemName+"</option>";
			} else {
				selectHTML+= "<option value="+list[i].itemCode+">"+list[i].itemName+"</option>";
			}
		}
		selectHTML+= "</select>";
		$("#"+spanId).html(selectHTML);
	});
}

function insertViewYnSelectBox() {
		viewHTML = "<select class='select' id='sel_viewYn'>";
		viewHTML+= "<option value=''>▶판매여부선택</option>";
		viewHTML+= "<option value='0'>판매중지</option>";
		viewHTML+= "<option value='1'>판매중</option>";
		viewHTML+= "</select>";
		$("#l_viewYn").html(viewHTML);
}


function collectionInsertSelectBox(itemType, span_value, itemCode) {
	itemService.getItemList(itemType, function(list) {
		selHTML = "<select class='select' id='sel_"+itemType+"'>";
		selHTML+= "<option value=''>▶아이템선택</option>";
		for (var i = 0; i < list.length; i++) {
			if (itemCode != "") { 
				if (list[i].itemCode == itemCode)
					selHTML+= "<option value="+list[i].itemCode+" selected>"+list[i].productName+"</option>";
				else 
					selHTML+= "<option value="+list[i].itemCode+">"+list[i].productName+"</option>";
			} else {
				selHTML+= "<option value="+list[i].itemCode+">"+list[i].productName+"</option>";
			}
		}
		selHTML+= "</select>";
		$("#"+span_value).html(selHTML);
	});
}

/**
 * <PRE>
 * 1. Comment : 사용여부 선택 셀렉트 박스(사용함, 사용안함).
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param viewYn
 * @param tagValue
 */
function noticeViewYnSelectBox(viewYn, tagValue) {
	selHTML = "<select class='select' id='select_viewYn'>";
	selHTML+= "<option value=''>▶사용여부선택</option>";
		if (viewYn == "0") {
			selHTML+= "<option value='0' selected>사용안함</option>";
			selHTML+= "<option value='1'>사용함</option>";
		} else if (viewYn == "1") {
			selHTML+= "<option value='0' >사용안함</option>";
			selHTML+= "<option value='1' selected>사용함</option>";
		}
	selHTML+= "</select>";
	$("#"+tagValue).html(selHTML);
}

/**
 * <PRE>
 * 1. Comment : 년도 선택 셀렉트 박스.
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagValue
 * @param onChangeValue
 */
function yearSelectBox(tagValue, onChangeValue, showDiv, selectValue) {
	if (showDiv != "") {
		$("#"+showDiv).show();
	}
	selHTML = "<select class='select' id='sel_year' onChange='"+onChangeValue+"'>";
	selHTML+= "<option value=''>▶년도선택</option>";
	for (var i = 2016; i <= 2020; i++) {
		if (i == selectValue) {
			selHTML+= "<option value="+i+" selected>"+i+"년</option>";
		} else {
			selHTML+= "<option value="+i+">"+i+"년</option>";	
		}
	}
	selHTML+= "</select>";
	$("#"+tagValue).html(selHTML);
}

/**
 * <PRE>
 * 1. Comment : 주차 선택 셀렉트 박스.
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagName
 * @param showDiv
 */
function weekSelectBox(tagName, showDiv) {
	if (showDiv != "") {
		$("#"+showDiv).show();
	}
	selHTML = "<select class='select' id='sel_week'>";
	selHTML+= "<option value=''>▶주차선택</option>";
	for (var i = 1; i <= 53; i++) {
		selHTML+= "<option value="+i+">"+i+"주차</option>";
	}
	selHTML+= "</select>";
	$("#"+tagName).html(selHTML);
}

/**
 * <PRE>
 * 1. Comment : 월 선택 셀렉트 박스.
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagValue
 */
function monthSelectBox(tagValue, month) {
	selHTML = "<select class='select' id='sel_month'>";
	selHTML+= "<option value=''>▶월선택</option>";
	for (var i = 1; i <= 12; i++) {
		if (month == i) {
			selHTML+= "<option value="+i+" selected>"+i+"월</option>";	
		} else {
			selHTML+= "<option value="+i+">"+i+"월</option>";
		}
	}
	selHTML+= "</select>";
	$("#"+tagValue).html(selHTML);
}

/**
 * <PRE>
 * 1. Comment : 이미지타입 선택 셀렉트 박스.
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagName
 * @param imgType
 */
function imgTypeSelectBox(tagName, imgType) {
	selHTML = "<select class='select' id='modify_sel_imgType'>";
	selHTML+= "<option value=''>▶이미지타입선택</option>";
	for (var i = 0; i < 4; i++) {
		if (imgType == i)
			selHTML+= "<option value="+i+" selected>"+imgTypeValue(imgType)+"</option>";
		else
			selHTML+= "<option value="+i+">"+imgTypeValue(i)+"</option>";
	}
	selHTML+= "</select>";
	$("#"+tagName).html(selHTML);
}

function resTypeSelectBox(tagName, selList, resType, page) {
	var selectHTML = "<select id='sel_resType' class='select'>";
	if (page == "bsmList")
		selectHTML+= "<option value=''>▶정렬선택</option>";
	else
		selectHTML+= "<option value=''>▶목록선택</option>";
	 
	for (var i=0; i <selList.length; i++) {
		cmpList = selList[i];
		if (cmpList != undefined) {
			if (resType == cmpList.resTypeCode) 
				selectHTML+= "<option value='"+cmpList.resTypeCode+"' selected>"+cmpList.resTypeKorName+"</option>";
			else 
				selectHTML+= "<option value='"+cmpList.resTypeCode+"'>"+cmpList.resTypeKorName+"</option>";
		}
	}
	selectHTML+= "</select>";
	$("#"+tagName).html(selectHTML);
}

/**
 * <PRE>
 * 1. Comment :  BSM 사용여부 셀렉트 박스.
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 02. 25
 * </PRE>
 * @param tagName
 * @param useYn
 */
function bsmUseYnSelectBox(tagName, useYn) {
	var selectHTML = "<select id='sel_use' class='select'>";
	selectHTML+= "<option value=''>▶사용여부선택</option>";
	if (useYn != undefined) {
		if (useYn == "0") {
			selectHTML+= "<option value='1'>사용함</option>";
			selectHTML+= "<option value='0' selected>사용안함</option>";
		} else if (useYn == "1") {
			selectHTML+= "<option value='1' selected>사용함</option>";
			selectHTML+= "<option value='0'>사용안함</option>";
		}
	} else {
		selectHTML+= "<option value='1'>사용함</option>";
		selectHTML+= "<option value='0'>사용안함</option>";
	}
	selectHTML+= "</select>";
	$("#"+tagName).html(selectHTML);
}

/**
 * <PRE>
 * 1. Comment : 아이템 셀렉트 박스.
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagName
 * @param selList
 * @param itemCode
 * @param selectId
 */
function itemListSelectBox(tagName, selList, itemCode, selectId) {
	var selectHTML = "<select class='form-control' id='sel_"+selectId+"_list'>";
	selectHTML+= "<option value=''>▶아이템선택</option>";
	for (var i = 0; i < selList.length; i++) {
		if (selList[i].itemCode == itemCode) {
			selectHTML+= "<option value="+selList[i].itemCode+" selected>"+selList[i].productName+"</option>";
		} else {
			selectHTML+= "<option value="+selList[i].itemCode+">"+selList[i].productName+"</option>";
		}
	}
	selectHTML+= "</select>";
	$("#"+tagName).html(selectHTML);
}

/**
 * <PRE>
 * 1. Comment : 메세지 종류 셀렉트 박스(공개편지, 개인편지).
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagName
 * @param type
 */
function letterTypeSelectBox(tagName, type) {
	
	if (tagName == "l_letterType") {
		var selectHTML = "<select class='select' id='sel_letterType' onChange='javascript:changeMessageType()';>";
	} else {
		var selectHTML = "<select class='select' id='sel_letterType' onChange='javascript:goMessage()';>";
	}
	
	selectHTML+= "<option value=''>▶메세지 종류 선택</option>";
		if (type == "private") {
			selectHTML+= "<option value='private' selected>개인편지</option>";	
			selectHTML+= "<option value='open' >공개편지</option>";
		} else if (type == "open"){
			selectHTML+= "<option value='private'>개인편지</option>";
			selectHTML+= "<option value='open' selected>공개편지</option>";
		} else {
			selectHTML+= "<option value='private'>개인편지</option>";
			selectHTML+= "<option value='open' >공개편지</option>";
		}
	selectHTML+= "</select>";
	$("#"+tagName).html(selectHTML);
}

/**
 * <PRE>
 * 1. Comment : 기기종류 셀렉트 박스(애플, 안드로이드).
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagName
 * @param type
 */
function deviceTypeSelectBox(tagName, type) {
	var selectHTML = "<select class='select' id='sel_deviceType' onChange='javascript:goDeviceType()';>";
	selectHTML+= "<option value=''>▶기기선택</option>";
	
	if (type == "ANDROID") {
		selectHTML+= "<option value='IOS'>애플</option>";
		selectHTML+= "<option value='ANDROID' selected>안드로이드</option>";
	} else if (type == "IOS") {
		selectHTML+= "<option value='IOS' selected>애플</option>";
		selectHTML+= "<option value='ANDROID'>안드로이드</option>";
	} else {
		selectHTML+= "<option value='IOS'>애플</option>";
		selectHTML+= "<option value='ANDROID'>안드로이드</option>";
	}
	selectHTML+= "</select>";
	$("#"+tagName).html(selectHTML);
}

/**
 * <PRE>
 * 1. Comment : 서버종류 셀렉트 박스(상용서버, 테스트서버).
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2016. 04. 20
 * </PRE>
 * @param tagName
 * @param serverKind
 */
function serverKindsSelectBox(tagName, serverKind) {
	var selectHTML = "<select class='select' id='sel_serverKind' onChange='javascript:goServerKind()';>";
	selectHTML += "<option value=''>▶서버선택</option>";
	
	if (serverKind == "aws") {
		selectHTML += "<option value='aws' selected>상용서버</option>";
		selectHTML += "<option value='test'>테스트서버</option>";
	} else if (serverKind == "test") {
		selectHTML += "<option value='aws'>상용서버</option>";
		selectHTML += "<option value='test' selected>테스트서버</option>";
	} else {
		selectHTML += "<option value='aws'>상용서버</option>";
		selectHTML += "<option value='test'>테스트서버</option>";
	}
	selectHTML += "</select>";
	$("#"+tagName).html(selectHTML);
}
 
function memberRegStaticsSelectbox(tagName, statType, onChangeValue) {
	var selectHTML = "<select class='select' id='sel_statType' onChange='"+onChangeValue+"'>";
	selectHTML += "<option value=''>▶선택</option>";
	
	if (statType == "week") {
		selectHTML += "<option value='week' selected>최근일주일</option>";
		selectHTML += "<option value='month'>월 별</option>";
	} else if (statType == "month") {
		selectHTML += "<option value='week'>최근일주일</option>";
		selectHTML += "<option value='month' selected>월 별</option>";
	}
	selectHTML += "</select>";
	$("#"+tagName).html(selectHTML);
}

function productSelectbox(storeType, tagName) {
	var selHTML = "<select class='select' id='sel_itemCode'>";
	selHTML+= "<option value=''>▶아이템선택</option>";
	itemService.productListByStoreType(function(selList) {
		for (var i=0; i<selList.length; i++) {
			cmpList = selList[i];
			selHTML+= "<option value='"+cmpList.itemCode+"'>"+cmpList.productName+"</option>";
		}
		selHTML+= "</select>";
		$("#"+tagName).html(selHTML);
	});
}

/**
 * 24시간 셀렉트 박스
 * @param hour
 * @param tagName
 */
function hourSelectbox(hour, tagName) {
	var selectbox = "<select class='select width-small' id='sel_hour'>";
	selectbox+= "<option value=''>▶시간선택</option>";
	for (var i = 0; i <= 24; i++) {
		if (hour == i) {
			selectbox+= "<option value='"+hour+"' selected>"+hour+"시간</option>";
		} else {
			selectbox+= "<option value='"+i+"'>"+i+"시간</option>";	
		}
	}
	selectbox+= "</select>";
	$("#"+tagName).html(selectbox);
}

/**
 * 0분~ 59분 셀렉트 박스
 * @param minute
 * @param tagName
 */
function minuteSelectbox(minute, tagName) {
	var selectbox = "<select class='select width-small' id='sel_minute'>";
	selectbox += "<option value=''>▶분선택</option>";
	for (var i = 0; i <= 59; i++) {
		if (minute == i) {
			selectbox+= "<option value='"+minute+"' selected>"+minute+"분</option>";
		} else {
			selectbox+= "<option value='"+i+"'>"+i+"분</option>";	
		}
	}
	selectbox+= "</select>";
	$("#"+tagName).html(selectbox);
}

function memberSearchTypeSelectBox(searchType, tagName) {
	var selectbox = "<select class='select' id='sel_searchType'>";
	selectbox += "<option value=''>▶검색종류선택</option>";
	
	if (searchType == "userName") {
		selectbox += "<option value='phoneNumber'>핸드폰번호</option>";
		selectbox += "<option value='userName' selected>이 름</option>";
	} else if (searchType == "phoneNumber") {
		selectbox += "<option value='phoneNumber' selected>핸드폰번호</option>";
		selectbox += "<option value='userName'>이 름</option>";
	} else {
		selectbox += "<option value='phoneNumber'>핸드폰번호</option>";
		selectbox += "<option value='userName'>이 름</option>";
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function sortTypeSquare(tagName) {
	var selectbox = "<select class='select' id='sel_sortTypeSquare' onChange='fn_search();'>";
	selectbox += "<option value=''>▶정렬기준</option>";
	selectbox += "<option value='share'>- 공유</option>";
	selectbox += "<option value='reply'>- 댓글</option>";
	selectbox += "<option value='report'>- 신고</option>";
	selectbox += "<option value='week'>- 인기순위</option>";
	selectbox += "<option value='best'>- 베스트순위</option>";
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function letterTypeSquare(value, tagName) {
	var selectbox = "<select class='select' id='sel_letterTypeSquare' onChange='init(this.value);'>";
	selectbox += "<option value=''>▶편지종류</option>";
	if (value == "popular") {
		selectbox += "<option value='popular' selected>- 인기편지</option>";
		selectbox += "<option value='new'>- 최신편지</option>";
		selectbox += "<option value='best'>- 베스트편지</option>";
		selectbox += "<option value='report'>- 신고편지</option>";
	} else if (value == "new") {
		selectbox += "<option value='popular'>- 인기편지</option>";
		selectbox += "<option value='new' selected>- 최신편지</option>";
		selectbox += "<option value='best'>- 베스트편지</option>";
		selectbox += "<option value='report'>- 신고편지</option>";
	} else if (value =="best") {
		selectbox += "<option value='popular'>- 인기편지</option>";
		selectbox += "<option value='new'>- 최신편지</option>";
		selectbox += "<option value='best' selected>- 베스트편지</option>";
		selectbox += "<option value='report'>- 신고편지</option>";
	} else if (value == "report") {
		selectbox += "<option value='popular'>- 인기편지</option>";
		selectbox += "<option value='new'>- 최신편지</option>";
		selectbox += "<option value='best'>- 베스트편지</option>";
		selectbox += "<option value='report' selected>- 신고편지</option>";
	} else {
		selectbox += "<option value='popular'>- 인기편지</option>";
		selectbox += "<option value='new'>- 최신편지</option>";
		selectbox += "<option value='best'>- 베스트편지</option>";
		selectbox += "<option value='report'>- 신고편지</option>";	
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function giftTypeSelectbox(val, tagName) {
	var selectbox = "<select class='select' id='sel_giftType' onChange='init(this.value);'>";
	if (val == "0") {
		selectbox += "<option value='0' selected>- 아이템 지급</option>";
		selectbox += "<option value='1'>- 콘/팝콘 지급</option>";
	} else if (val == "1") {
		selectbox += "<option value='0'>- 아이템 지급</option>";
		selectbox += "<option value='1' selected>- 콘/팝콘 지급</option>";
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function daySelecbox(val, tagName, limitDayCount) {
	var selectbox = "<select class='select' id='sel_day'>";
	for (var i=1; i<=limitDayCount; i++) {
		if (i == val) {
			selectbox += "<option value='"+val+"' selected>"+val+"일</option>";
		} else {
			selectbox += "<option value='"+i+"'>"+i+"일</option>";
		}
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function birdTipSortSelectbox(val, tagName, onChangeFunction) {
	var selectbox = "<select class='select' id='sel_sort' onChange='"+onChangeFunction+"'>";
	if (val == "0") {
		selectbox += "<option value='0' selected>- 기간지정순</option>";
		selectbox += "<option value='1'>- 상시순</option>";
		selectbox += "<option value='2'>- 우선순</option>";
		selectbox += "<option value='3'>- 랜덤순</option>";
		selectbox += "<option value='4'>- 사용중</option>";
		selectbox += "<option value='5'>- 사용안함</option>";
	} else if (val == "1") {
		selectbox += "<option value='0' >- 기간지정순</option>";
		selectbox += "<option value='1' selected>- 상시순</option>";
		selectbox += "<option value='2'>- 우선순</option>";
		selectbox += "<option value='3'>- 랜덤순</option>";
		selectbox += "<option value='4'>- 사용중</option>";
		selectbox += "<option value='5'>- 사용안함</option>";
	} else if (val == "2") {
		selectbox += "<option value='0' >- 기간지정순</option>";
		selectbox += "<option value='1'>- 상시순</option>";
		selectbox += "<option value='2' selected>- 우선순</option>";
		selectbox += "<option value='3'>- 랜덤순</option>";
		selectbox += "<option value='4'>- 사용중</option>";
		selectbox += "<option value='5'>- 사용안함</option>";
	} else if (val == "3") {
		selectbox += "<option value='0' >- 기간지정순</option>";
		selectbox += "<option value='1'>- 상시순</option>";
		selectbox += "<option value='2'>- 우선순</option>";
		selectbox += "<option value='3' selected>- 랜덤순</option>";
		selectbox += "<option value='4'>- 사용중</option>";
		selectbox += "<option value='5'>- 사용안함</option>";
	} else if (val == "4") {
		selectbox += "<option value='0' >- 기간지정순</option>";
		selectbox += "<option value='1'>- 상시순</option>";
		selectbox += "<option value='2'>- 우선순</option>";
		selectbox += "<option value='3'>- 랜덤순</option>";
		selectbox += "<option value='4' selected>- 사용중</option>";
		selectbox += "<option value='5'>- 사용안함</option>";
	} else if (val == "5") {
		selectbox += "<option value='0' >- 기간지정순</option>";
		selectbox += "<option value='1'>- 상시순</option>";
		selectbox += "<option value='2'>- 우선순</option>";
		selectbox += "<option value='3'>- 랜덤순</option>";
		selectbox += "<option value='4'>- 사용중</option>";
		selectbox += "<option value='5' selected>- 사용안함</option>";
	} else {
		selectbox += "<option value='0' >- 기간지정순</option>";
		selectbox += "<option value='1'>- 상시순</option>";
		selectbox += "<option value='2'>- 우선순</option>";
		selectbox += "<option value='3'>- 랜덤순</option>";
		selectbox += "<option value='4'>- 사용중</option>";
		selectbox += "<option value='5'>- 사용안함</option>";
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function regularSelectbox(val, tagName, onChangeFunction) {
	var selectbox = "<select class='select' id='sel_regularYn' onChange='"+onChangeFunction+"'>";
	selectbox += "<option value='' >▶직접/상시선택</option>";
	if (val == "0") {
		selectbox += "<option value='0' selected>- 직접</option>";
		selectbox += "<option value='1' >- 상시</option>";
	} else if (val == "1") {
		selectbox += "<option value='0' >- 직접</option>";
		selectbox += "<option value='1' selected>- 상시</option>";
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function orderSelectbox(val, tagName, onChangeFunction) {
	var selectbox = "<select class='select' id='sel_orderYn' onChange='"+onChangeFunction+"'>";
	selectbox += "<option value='' >▶우선/랜덤선택</option>";
	if (val == "0") {
		selectbox += "<option value='0' selected>- 랜덤</option>";
		selectbox += "<option value='1' >- 우선</option>";
	} else if (val == "1") {
		selectbox += "<option value='0' >- 랜덤</option>";
		selectbox += "<option value='1' selected>- 우선</option>";
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function orderPrioritySelectbox(val, tagName, onChangeFunction) {
	var selectbox = "<select class='select' id='sel_orderPriority' onChange='"+onChangeFunction+"'>";
	selectbox += "<option value='' >▶우선 순서선택</option>";
	for (var i=1; i <=5; i++) {
		if (i == val) {
			selectbox += "<option value='"+val+"' selected>"+ val +"</option>";
		} else {
			selectbox += "<option value='"+i+"' >"+ i +"</option>";
		}
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function userPresentTypeSelectbox(val, tagName, onChangeFunction) {
	var selectbox = "<select class='select' id='sel_presentType' onChange='"+onChangeFunction+"'>";
}

function conditionSelectBox(val, tagName) {
	var selectbox = "<select class='select' id='sel_condition'>";
	selectbox += "<option value=''>▶상점 상태선택</option>";
	
	if (val == 1) {
		selectbox += "<option value='1' selected>신규</option>";
		selectbox += "<option value='2'>판매중</option>";
		selectbox += "<option value='3'>인기</option>";
		selectbox += "<option value='4'>할인중</option>";
		selectbox += "<option value='5'>판매중지</option>";
	} else if (val == 2) { 
		selectbox += "<option value='1'>신규</option>";
		selectbox += "<option value='2' selected>판매중</option>";
		selectbox += "<option value='3'>인기</option>";
		selectbox += "<option value='4'>할인중</option>";
		selectbox += "<option value='5'>판매중지</option>";
	} else if (val == 3) {
		selectbox += "<option value='1'>신규</option>";
		selectbox += "<option value='2'>판매중</option>";
		selectbox += "<option value='3' selected>인기</option>";
		selectbox += "<option value='4'>할인중</option>";
		selectbox += "<option value='5'>판매중지</option>";
	} else if (val == 4) {
		selectbox += "<option value='1'>신규</option>";
		selectbox += "<option value='2'>판매중</option>";
		selectbox += "<option value='3'>인기</option>";
		selectbox += "<option value='4' selected>할인중</option>";
		selectbox += "<option value='5'>판매중지</option>";
	} else if (val == 5) {
		selectbox += "<option value='1'>신규</option>";
		selectbox += "<option value='2'>판매중</option>";
		selectbox += "<option value='3'>인기</option>";
		selectbox += "<option value='4'>할인중</option>";
		selectbox += "<option value='5' selected>판매중지</option>";
	} else {
		selectbox += "<option value='1'>신규</option>";
		selectbox += "<option value='2'>판매중</option>";
		selectbox += "<option value='3'>인기</option>";
		selectbox += "<option value='4'>할인중</option>";
		selectbox += "<option value='5'>판매중지</option>";
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}

function pagingListSelectbox(val, tagName) {
	var selectbox = "<select class='select_small' id='sel_pagingCnt' onChange='javascript:fn_search("+ '"'+"new"+'"'+");'>";
	for (var i=10; i<35; i+=5) {
		if (i == val) {
			selectbox += "<option value='"+i+"' selected>"+ i +"</option>";	
		} else {
			selectbox += "<option value='"+i+"' >"+ i +"</option>";	
		}
	}
	selectbox += "</select>";
	$("#"+tagName).html(selectbox);
}
