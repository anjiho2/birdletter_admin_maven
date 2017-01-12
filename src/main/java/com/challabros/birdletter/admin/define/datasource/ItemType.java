package com.challabros.birdletter.admin.define.datasource;

public enum ItemType {
	COLLECTION(0,"컬렉션"), 
	BODY(1,"바디"), 
	EYE(2,"눈"), 
	MOUTH(3,"입"),
	FACE(4,"페이스"), 
	CLOTHES(5,"옷"), 
	CAP(6,"모자"), 
	GLASS(7,"안경"),
	T_BODY(8,"스페셜 의상"), 
	ROOF(9,"지붕"), 
	HOUSE(10,"벽지"), 
	T_HOUSE(11,"스페셜 둥지"),
	BACK(12,"숲"), 
	T_BACK(13,"스페셜 숲");
	
	int code;
	String itemName;
	
	ItemType(int code, String itemName) {
		this.code = code;
		this.itemName = itemName;
	}
	
	public static ItemType codeOf(int code) {
		for (ItemType type : ItemType.values()) {
			if (type.code == code) {
				return type;
			}
		}
		return null;
	}
	
	public static String itemNameOf(int code) {
		for (ItemType type : ItemType.values()) {
			if (type.equals(codeOf(code))) {
				return type.itemName;
			}
		}
		return null;
	}
	
	public static int size() {
		return ItemType.values().length;
	}
}
