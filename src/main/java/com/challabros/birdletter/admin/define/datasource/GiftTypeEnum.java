package com.challabros.birdletter.admin.define.datasource;

public enum GiftTypeEnum {
	ITEM(0), CORN(1), POPCORN(2);
	
	private int giftTypeCode;
	
	private GiftTypeEnum(int giftTypeCode) {
		this.giftTypeCode = giftTypeCode;
	}
	
	public int getGiftTypeCode() {
		return this.giftTypeCode;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
