

function searchName(searchType) {
	var str = "";
	if (searchType != "") {
		if (searchType == "productCode") str = "상품코드";
		else if (searchType == "productName") str = "상품이름";
		else if (searchType == "itemCode") str = "아이템 코드";
		
		return str;
	}
}

function storeName(storeType) {
	var str = "";
	if (storeType != "") {
		if (storeType == "clt") str = "컬렉션";
		else if (storeType == "bird") str = "바디";
		else if (storeType == "decoeye") str = "눈";
		else if (storeType == "decomouth") str = "입";
		else if (storeType == "decoskin") str = "페이스";
		else if (storeType == "decotop") str = "모자";
		else if (storeType == "decoface") str = "안경";
		else if (storeType == "tdeco") str = "스페셜 의상";
		else if (storeType == "decomid") str = "옷";
		else if (storeType == "housetop") str = "지붕";
		else if (storeType == "houselow") str = "벽지";
		else if (storeType == "thouse") str = "스페셜 둥지";
		else if (storeType == "bg") str = "숲";
		else if (storeType == "tbg") str = "스페셜 숲";
		
		return str;
	}
}

function itemKorName(itemType) {
	var str = "";
	if (itemType != "") {
		switch (itemType) {
		case "BODY":
			str = "바디";
			break;
		case "EYE":
			str = "눈";
			break;
		case "MOUTH":
			str = "입";
			break;
		case "FACE":
			str = "페이스";
			break;
		case "CLOTHES":
			str = "옷";
			break;
		case "CAP":
			str = "모자";
			break;
		case "GLASS":
			str = "안경";
			break;
		case "T_BODY":
			str = "스페셜 의상";
			break;
		case "ROOF":
			str = "지붕";
			break;
		case "HOUSE":
			str = "벽지";
			break;
		case "T_HOUSE":
			str = "스페셜 둥지";
			break;
		case "BACK":
			str = "숲";
			break;
		case "T_BACK":
			str = "스페셜 숲";
			break;
		default:
			break;
		}
		return str;
	}
}
  
function itemCategotyName(itemCategory) {
	var str = "";
	if (itemCategory != "") {
		if (itemCategory == "1") str = "컬랙션";
		else if (itemCategory == "2") str = "버드";
		else if (itemCategory == "3") str = "의상";
		else if (itemCategory == "4") str = "둥지";
		else if (itemCategory == "5") str = "숲";
		
		return str;
	}
}

function itemTypeName(itemCategory, itemType) {
	var str = "";
	if (itemCategory != "" && itemType != "") {
		if (itemCategory == "1" && itemType == "2") str = "콜렉션";
		else if (itemCategory == "2" && itemType == "1") str = "바디";
		else if (itemCategory == "2" && itemType == "2") str = "눈";
		else if(itemCategory == "2" && itemType == "3") str = "입";
		else if(itemCategory == "2" && itemType == "4") str = "페이스";
		else if(itemCategory == "3" && itemType == "1") str = "옷";
		else if (itemCategory == "3" && itemType == "2") str = "모자";
		else if (itemCategory == "3" && itemType == "3") str = "안경";
		else if (itemCategory == "3" && itemType == "4") str = "스페셜";
		else if (itemCategory == "4" && itemType == "1") str = "지붕";
		else if (itemCategory == "4" && itemType == "2") str = "벽";
		else if (itemCategory == "4" && itemType == "3") str = "스페셜";
		else if (itemCategory == "5" && itemType == "1") str = "배경";
		else if (itemCategory == "5" && itemType == "2") str = "스페셜";
	}
	return str;
}

function viewYnName(viewYn) {
	var str = "";
	if (viewYn != "") {
		if (viewYn == "true") str = "판매중";
		else if (viewYn == "false") str = "판매중지";
		return str;
	}
}

function imgTypeValue(imgType) {
	var str = "";
		if (imgType == "0") str = "이미지  없음";
		else if (imgType == "1") str = "640 * 200";
		else if (imgType == "2") str = "640 * 300";
		else if (imgType == "3") str = "640 * 400";
		else str = "이미지  없음";
	return str;
}

function useYnValue(useYn) {
	var str = ""
	if (useYn == "0") str = "사용안함";
	else if (useYn == "1") str = "사용함";
	return str;
}

function getCornBuyPrice(cornPoint) {
	var buyPrice = "";
	if (cornPoint == "10") {
		buyPrice = "1100";
	} else if (cornPoint == "22") {
		buyPrice = "2200";
	} else if (cornPoint == "55") {
		buyPrice = "5500";
	} else if (cornPoint == "115") {
		buyPrice = "11000";
	} else if (cornPoint == "240") {
		buyPrice = "22000";
	} else if (cornPoint == "600") {
		buyPrice = "55000";
	}
	return buyPrice;
}

function getIllegalTypeName(illegalType) {
	var korName = "";
	switch (illegalType) {
	case "COMMERCIAL":
		korName = "상업/홍보성";
		break;
	case "SEXUAL":
		korName = "음란/선정성";
		break;
	case "ILLEGAL_INFO":
		korName = "불법정보";
		break;
	case "INSULT":
		korName = "욕설/인신공격";
		break;
	case "PRIVACY":
		korName = "개인정보노출";
		break;
	case "RIGHT":
		korName = "권리침해";
		break;
	case "ETC":
		korName = "기타";
		break;
	default:
		korName = "-";
		break;
	}
	return korName;
}

function getGiftState(giftState, expireDate) {
	var str = "";
	
	var today = new Date();
	var splitDate = new Array(); 
	var expDate = new Date(expireDate);
	if (giftState == "0") {
		if (today > expDate) {
			giftState = "2";
		}	
	}
	
	if (giftState == "0") {
		str = "수령전";
	} else if (giftState == "1") {
		str = "수령완료";
	} else if (giftState == "2") {
		str = "만료";
	} 
	else if (giftState == "3") {
		str = "삭제";
	} else if (giftState == "4") {
		str = "지급취소";
	}  
	return str;
}

function getTooltipState(viewYn, endDate) {
	var str = "";
	var today = new Date();
	var splitDate = new Array(); 
	var expDate = new Date(endDate);
	//alert(viewYn);
	if (viewYn == "1") {
		if (today > expDate) {
			viewYn = "0";
		}	
	}
	if (viewYn == "0") str = "사용안함";
	else if (viewYn == "1") str = "사용중";
	return str;
}

function getGiftItemName(giftType, productName, coinCount) {
	var str = "";
	if (giftType == "0") {
		str = productName;
	} else if (giftType == "1") {
		str = coinCount+" 콘";
	} else if (giftType == "2") {
		str = coinCount+" 팝콘";
	}
	return str;
}

function getStoreConditionName(condition) {
	var str = "";
	if (condition == "1") str = "신규";
	else if (condition == "2") str = "판매중";
	else if (condition == "3") str = "인기";
	else if (condition == "4") str = "할인중";
	else if (condition == "5") str = "판매중지";
	return str;
}