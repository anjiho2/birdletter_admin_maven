package com.challabros.birdletter.admin.util;

import java.util.HashMap;

import com.challabros.birdletter.admin.util.Value;
import com.challabros.birdletter.admin.util.Util;;

public class BirdAdminUtil {
	
	public static String getProductCodeType(String storeType) {
		String productCodeType = "";
		
		if (!"".equals(storeType)) {
			switch (storeType) {
			case Value.COLLECTION:
				productCodeType = Value.CLT;
				break;
			case Value.BODY:
				productCodeType = Value.BIRD;
				break;
			case Value.EYE:
				productCodeType = Value.BIRDEYE;
				break;
			case Value.MOUSE:
				productCodeType = Value.BIRDMOUTH;
				break;
			case Value.FACE:
				productCodeType = Value.BIRDFACE;
				break;
			case Value.CAP:
				productCodeType = Value.DECOTOP;
				break;
			case Value.GLASS:
				productCodeType = Value.DECOFACE;
			case Value.THEME_BODY:
				productCodeType = Value.TDECO;
				break;
			case Value.CLOTH:
				productCodeType = Value.DECOMID;
				break;
			case Value.ROOF:
				productCodeType = Value.HOUSETOP;
				break;
			case Value.HOUSE:
				productCodeType = Value.HOUSELOW;
				break;
			case Value.THEME_HOUSE:
				productCodeType = Value.THOUSE;
				break;
			case Value.BACK:
				productCodeType = Value.BG;
				break;
			case Value.THEME_BACK:
				productCodeType = Value.TBG;
				break;
			default:
				break;
			}
		}
		return productCodeType;
	}
	
	public static String getGiftItemName(int giftType, String productName, int coinCount) {
		String itemName = "";
		if (giftType == 0) {
			itemName = productName;
		} else if (giftType == 1) {
			itemName = coinCount +" 콘";
		} else if (giftType == 2) {
			itemName = coinCount +" 팝콘";
		}
		return itemName;
	}
	
	public static String getGiftState(int giftState, String expireDate) {
		String stateName = "";
		int comapreDate = DateUtils.compareToDateNow(expireDate, 1);
		if (giftState == 0) {
			if (comapreDate < 0) {
				giftState = 2;
			}
		}
		if (giftState == 0) {
			stateName = "수령전";
		} else if (giftState == 1) {
			stateName = "수령완료";
		} else if (giftState == 2) {
			stateName = "만료";
		} else if (giftState == 3) {
			stateName = "삭제";
		} else if (giftState == 4) {
			stateName = "지급취소";
		}
		return stateName;
	}
	
	public static HashMap<String, Object> staticsDateParam(String termType) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		if (!"".equals(termType)) {
			paramMap.put("termType", Util.isNullValue(termType, ""));
			if ("week".equals(termType)) {
				paramMap.put("startDate", Util.isNullValue(Util.day7Ago(), ""));
				paramMap.put("endDate", Util.isNullValue(Util.returnToDayYYMMDD(), ""));
			} else if ("month".equals(termType)) {
				paramMap.put("month", Util.isNullValue(Util.returnToDate("yyyy-MM"), ""));
			} else if ("year".equals(termType)) {
				paramMap.put("year", Util.isNullValue(Util.returnToDate("yyyy"), ""));
			}
		}
		return paramMap;
	}
	
}
