package com.challabros.birdletter.admin.define.datasource;

enum CollectionItemName implements CollectionType{
	전체(0), 콜렉션(1), 새(2), 모자(3), 안경(4), 코스튬(5),
	지붕(6), 벽(7), 집테마(8), 배경(9), 테마배경(10);
	
	private int code;

	CollectionItemName(int code) {
		this.code = code;
	}

	@Override
	public int code() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public CollectionType code(int code) {
		// TODO Auto-generated method stub
		CollectionItemName result = null;
		for (CollectionItemName each : CollectionItemName.values()) {
			if (each.code == code) {
				result = each;
				break;
			}
		}
		return result;
	}
	
}

public enum CollectionItem {
	
	CLT(1), BIRD(2), DECOTOP(3), DECOFACE(4), TDECO(5),
	HOUSETOP(6), HOUSELOW(7), THOUSE(8), BG(9), TBG(10);
	
	private int code;
	
	CollectionItem(int code) {
		this.code = code;
	}
	
	public int code() {
		return this.code;
	}
	
	public CollectionItem code(int code) {
		CollectionItem result = null;
		for (CollectionItem each : CollectionItem.values()) {
			if (each.code() == code) {
				result = each;
				break;
			}
		}
		return result;
	}
	
	/**
	 * <PRE>
	 * 1. Comment : 아이템 코드에 따른 스토어 명 가져오기
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 30
	 * </PRE>
	 * @param collectionItemCode
	 * @return
	 * 
	 */
	public static String getStoreName(String storeType) {
		String collectionItemName = "";
		String collectionItemCodeToUpper = storeType.toUpperCase();
		
		if (CollectionItem.CLT.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.콜렉션.name();
		} else if (CollectionItem.BIRD.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.새.name();
		} else if (CollectionItem.DECOTOP.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.모자.name();
		} else if (CollectionItem.DECOFACE.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.안경.name();
		} else if (CollectionItem.TDECO.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.코스튬.name();
		} else if (CollectionItem.HOUSETOP.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.지붕.name();
		} else if (CollectionItem.HOUSELOW.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.벽.name();
		} else if (CollectionItem.THOUSE.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.집테마.name();
		} else if (CollectionItem.BG.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.배경.name();
		} else if (CollectionItem.TBG.name().equals(collectionItemCodeToUpper)) {
			collectionItemName = CollectionItemName.테마배경.name();
		} else {
			collectionItemName = CollectionItemName.전체.name();
		}
		return collectionItemName;
	}
}
