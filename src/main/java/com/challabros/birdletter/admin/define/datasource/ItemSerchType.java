package com.challabros.birdletter.admin.define.datasource;

public enum ItemSerchType {
	
	PRODUCT_CODE(0, "상품코드"),
	PRODUCT_NAME(1, "상품이름"),
	ITEM_CODE(2, "아이템 코드");
	
	int code;
	
	String searchName;
	
	private ItemSerchType(int code, String searchName) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.searchName = searchName;
	}
	
	public static ItemSerchType codeOf(int code) {
		for (ItemSerchType type : ItemSerchType.values()) {
			if (type.code == code) {
				return type;
			}
		}
		return null;
	}
	
	public static String searchNameOf(int code) {
		for (ItemSerchType type : ItemSerchType.values()) {
			if (type.equals(codeOf(code))) {
				return type.searchName;
			}
		}
		return null;
	}
	
	public static int size() { 
		return ItemSerchType.values().length; 
	}
}
