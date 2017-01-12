package com.challabros.birdletter.admin.define.datasource;

public enum ExcelMenu {
	PHONE_NUMBER("전화번호"), USER_NAME("이 름"), ITEM_NAME("아이템 이름"), ITEM_CODE("아이템 코드"), 
	BUY_DATE("구매일"), CORN_PRICE("콘 가격"), POPCORN_PRICE("팝콘 가격"), REWARD_POPCORN("획득 팝콘"),
	USE_DATE("사용일"), ID("ID"), GENDER("성 별"), AGE("나 이"), REG_DATE("가입일"), PAY_DATE("지급일"),
	GIFT_NAME("선물명"), GIFT_ITEM("선물 아이템"), GIFT_STATE("선물 상태"), RECEIVE_DATE("수령일"),
	APPLE("애플"), ANDROID("안드로이드"), TOTAL_SUM("총 합"), DATE("날짜"), MALE("남성"), FEMALE("여성"),
	ARPU("ARPU"), ARPPU("ARPPU"), CONNECTION_COUNT("접속수"), PRIVATE_LETTER("개인편지"), OPEN_LETTER("공개편지"),
	SALE_PRICE("판매금액"), PRODUCT_CODE("상품코드"), PRODUCT_NAME("상품이름"), RANKING("순위"), POPCORN_NAME("팝콘 명"), 
	NUMBER("건수"), USER_COUNT("사용자 수");
	
	String excelMenuName;
	
	ExcelMenu(String excelMenuName) {
		this.excelMenuName = excelMenuName;
	}
	
	public String getMenuName() {
		return this.excelMenuName;
	}
	

}
