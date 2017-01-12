package com.challabros.birdletter.admin.define.datasource;

public enum StoreType {
	clt(0, "컬렉션"), 
	bird(1, "바디"), 
	decoeye(2, "눈"), 
	decomouth(3, "입"),
	decoskin(4, "페이스"), 
	decotop(5, "모자"), 
	decoface(6, "안경"), 
	tdeco(7, "스페셜 의상"),
	decomid(8, "옷"), 
	housetop(9, "지붕"), 
	houselow(10, "벽지"), 
	thouse(11, "스페셜 둥지"),
	bg(12, "숲"), 
	tbg(13, "스페셜 숲")
	;
	
	int code;
	
	String storeName;
	
	StoreType(int code, String storeName) {
		this.code = code;
		this.storeName = storeName;
	}
	
	public static StoreType codeOf(int code) {
		for (StoreType type : StoreType.values()) {
			if (type.code == code) {
				return type;
			}
		}
		return null;
	}
	
	public static String storeName(int code) {
		for (StoreType type : StoreType.values()) {
			if (type.equals(codeOf(code))) {
				return type.storeName;
			}
		}
		return null;
	}
	
	public static int size() {
		return StoreType.values().length;
	}
	
	public int getCode() { 
		return this.code;
	}
}
